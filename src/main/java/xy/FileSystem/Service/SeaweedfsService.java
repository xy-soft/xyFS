package xy.FileSystem.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.lokra.seaweedfs.core.FileSource;
import org.lokra.seaweedfs.core.FileTemplate;
import org.lokra.seaweedfs.core.file.FileHandleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xy.FileSystem.File.FileListener;
import xy.FileSystem.File.UploadFileExt;
import xy.FileSystem.File.UploadResult;
import xy.FileSystem.Propert.StorageProperties;

@Service
public class SeaweedfsService  implements FileListener {
	
	@Autowired
	private StorageProperties prop;
	
	private FileTemplate init(){
		FileSource fileSource = new FileSource();
		fileSource.setHost(prop.getSeaweedfshost());
		fileSource.setPort(prop.getSeaweedfsport());
		fileSource.setConnectionTimeout(prop.getSeaweedfstimeout());		

		try {
			fileSource.startup();
			FileTemplate template = new FileTemplate(fileSource.getConnection());
			return template;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public UploadResult Store(UploadFileExt ufe) {
		UploadResult result = new UploadResult();
		if (ufe.getBytes() != null) {
			FileHandleStatus dpr = store(ufe.getBytes(), ufe.getFileName());
			result.fileName = ufe.getFileName();
			result.fsize = ufe.getSize();
			result.hash = Integer.toString(dpr.hashCode());;
			result.key = dpr.getFileId();
			result.bucket = prop.getQiniubucket();
			result.location = "";
			result.tag = "";
		} else {
			FileHandleStatus dpr = store(ufe.getUrl(), ufe.getFileName());
			result.fileName = ufe.getFileName();
			result.fsize = ufe.getFile().length();
			result.hash = Integer.toString(dpr.hashCode());
			result.key = dpr.getFileId();
			result.bucket = prop.getQiniubucket();
			result.location = "";
			result.tag = "";
		}
		System.out.println("#########################");
		System.out.println("Seaweedfs upload success,fid:"+ result.key);
		System.out.println("#########################");
		return result;
	}

	@Override
	public void Download(String fileKeyorName) {
		// TODO Auto-generated method stub
		
	}
	
	public FileHandleStatus store(String filePath, String fileName) {
		FileTemplate template = init();
		if (template == null)
			return null;
		
		byte[] bytes;
		try {
			bytes = FileUtils.readFileToByteArray(new File(filePath));
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
			return template.saveFileByStream(fileName, byteInputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public FileHandleStatus store(byte[] bytes, String fileName){
		
		FileTemplate template = init();
		if (template == null)
			return null;
		
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
		
		try {
			return template.saveFileByStream(fileName, byteInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	public FileHandleStatus store(byte[] bytes, String fileName,ContentType contentType){
		
		FileTemplate template = init();
		if (template == null)
			return null;
		
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
		
		try {
			return template.saveFileByStream(fileName, byteInputStream, contentType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String download(String fileId){
		FileTemplate template = init();
		if (template == null)
			return null;
		
		try {
			return template.getFileUrl(fileId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String delete(String fileId){
		FileTemplate template = init();
		if (template == null)
			return null;
		
		try {
			template.deleteFile(fileId);
			return "ok";
		} catch (IOException e) {
			return "error";
		}
		
	}



}
