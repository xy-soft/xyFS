package xy.FileSystem.Controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.annotations.ApiOperation;
import xy.FileSystem.Cache.UsesCache;
import xy.FileSystem.Entity.Diskfile;
import xy.FileSystem.Entity.DiskfileRepository;
import xy.FileSystem.Exception.StorageFileNotFoundException;
import xy.FileSystem.File.FileListener;
import xy.FileSystem.File.StoreSource;
import xy.FileSystem.File.UploadFileExt;
import xy.FileSystem.Propert.StorageProperties;
import xy.FileSystem.Service.FileSystemStorageService;
import xy.FileSystem.Service.QiniuService;
import xy.FileSystem.Utils.HttpHelper;

@Controller
public class FileUploadDownloadController {
	@Autowired
	private FileSystemStorageService storageService;
	@Autowired
	private StorageProperties prop;
	@Autowired
	private DiskfileRepository diskfileRepository;
	
	ExecutorService executorService = Executors.newFixedThreadPool(5);

	@ApiOperation(value="文件上传后在上传页面展示文件")
	@GetMapping("/files")
	public String listUploadedFiles(Model model) throws IOException {
		
		System.out.println("UsesCache.files:"+UsesCache.files);
		System.out.println("UsesCache.usedspace:"+UsesCache.usedspace);

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadDownloadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return "file/"+prop.getTemplate()+"/uploadForm";
	}

	@ApiOperation(value="通过HttpHeaders下载文件")
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ApiOperation(value="文件上传Demo,用于上传测试，上传后将重定向")
	@PostMapping("/fileUpload")
	public String handleFileUpload(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam int appid, @RequestParam String username, @RequestParam String groupid) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next()); // 只取一个文件，不取多个
		String fileName = file.getOriginalFilename();

		if (prop.isRename()) {
			fileName = username + "_" + file.getOriginalFilename();
			if (groupid != null && !groupid.isEmpty()) {
				fileName = groupid + "_" + file.getOriginalFilename();
			}
		}

		final String finalFilename = fileName;

		doUpload(file, finalFilename);

		dbSave(appid, username, groupid, file, fileName);

		redirectAttributes.addFlashAttribute("message", "上传成功: " + file.getOriginalFilename());

		return "redirect:/files";
	}

	
	@ApiOperation(value="用于外接Post上传请求，不重定向")
	@PostMapping("/fileUploadPost")
	public ResponseEntity<String> handleFileUploadPost(MultipartHttpServletRequest request, 
					@RequestParam int appid,
					@RequestParam String username, 
					@RequestParam String groupid) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile file = request.getFile(itr.next()); // 只取一个文件，不取多个
		String fileName = file.getOriginalFilename();

		if (prop.isRename()) {
			fileName = username + "_" + file.getOriginalFilename();
			if (groupid != null && !groupid.isEmpty()) {
				fileName = groupid + "_" + file.getOriginalFilename();
			}
		}

		final String finalFilename = fileName;

		doUpload(file, finalFilename);
		dbSave(appid, username, groupid, file, fileName);

		return new ResponseEntity<String>(fileName,HttpStatus.OK);
	}


	public void doUpload(MultipartFile file, final String finalFilename) {

		// 磁盘存储
		if (prop.isTodisk()) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					storageService.store(file, finalFilename);
				}
			});

		}
		
		//第三方存储
		UploadFileExt ufe;
		try {
			ufe = new UploadFileExt(finalFilename, file.getBytes(), file.getContentType(), file.getSize());
			
			if (ufe != null) {
				for (FileListener fl : StoreSource.getListensers()) {
										
					executorService.execute(new Runnable() {
						@Override
						public void run() {
							fl.Store(ufe);
						}
					});
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void dbSave(int appid, String username, String groupid, MultipartFile file, String fileName) {
		// 数据库存储
		Diskfile dbFile = new Diskfile();
		String fileId = UUID.randomUUID().toString();

		dbFile.setFileid(fileId);
		dbFile.setAppid(appid);
		dbFile.setFileExt(file.getContentType());
		dbFile.setFileFlag("1");
		dbFile.setFileName(fileName);
		dbFile.setFileSize(BigInteger.valueOf(file.getSize()));
		dbFile.setIspublic("1");
		dbFile.setUploadDate(new Date());
		dbFile.setUploadUser(username);
		dbFile.setUrldisk(prop.getDiskprefix() + fileName);

		// 更新缓存
		UsesCache.files++;
		UsesCache.usedspace = UsesCache.usedspace + file.getSize();

		if (prop.isToqiniu()) {
			dbFile.setUrlqiniu(prop.getQiniuprefix() + fileName);
		}

		if (prop.isTomongodb()) {
			dbFile.setUrlmongodb(fileId);
		}

		diskfileRepository.save(dbFile);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value="文件下载")
	@GetMapping("/downloadByFilename")
	public ResponseEntity<Boolean> downloadByFilename(String filename) throws IOException {
		
		Boolean downloadSuccess = false;	
		downloadSuccess = HttpHelper.executeDownloadFile(HttpHelper.createHttpClient(), 
				"http://localhost:9091/files/wangxin_Tigase开发文档.doc", //服务器文件
				//"/files/"+filename, //服务器文件
				prop.getDownloadto() + filename, //下载到本地的文件
				"UTF-8",
				true);

		return new ResponseEntity<Boolean>(downloadSuccess,HttpStatus.OK);
	}	

	@ApiOperation(value="文件下载，通过七牛云下载")
	@GetMapping("/downloadQiniu")
	public String downloadQiniu(String fileId) throws IOException {

		return "";
	}	

	@ApiOperation(value="文件下载，通过阿里云下载")
	@GetMapping("/downloadAli")
	public String downloadAli(String fileId) throws IOException {

		return "";
	}
	
	@ApiOperation(value="文件下载，通过FastDFS下载")
	@GetMapping("/downloadFastDFS")
	public String downloadFastDFS(String fileId) throws IOException {

		return "";
	}	
	
	@ApiOperation(value="文件下载，通过MongoDB下载")
	@GetMapping("/downloadMongoDB")
	public String downloadMongoDB(String fileId) throws IOException {

		return "";
	}	

	@ApiOperation(value="文件下载，通过SeaweedFS下载")
	@GetMapping("/downloadSeaweedFS")
	public String downloadSeaweedFS(String fileId) throws IOException {

		return "";
	}	

}
