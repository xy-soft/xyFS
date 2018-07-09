package xy.FileSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import xy.FileSystem.Cache.FileCache;
import xy.FileSystem.File.FileListener;
import xy.FileSystem.File.UploadFileExt;
import xy.FileSystem.File.UploadResult;
import xy.FileSystem.Propert.StorageProperties;

@Service
public class QiniuService implements FileListener {

	private Zone zone = Zone.huadong();// 七牛华东存储区
	
	@Autowired
	StorageProperties prop;

	private Auth init() {

		if (FileCache.qiniuAuth != null)
			return FileCache.qiniuAuth; // 注入缓存

		String accessKey = prop.getQiniuak();
		String secretKey = prop.getQiniusk();

		Auth auth = Auth.create(accessKey, secretKey);
		FileCache.qiniuAuth = auth;

		return auth;
	}

	@Override
	public UploadResult Store(UploadFileExt ufe) {
		UploadResult result = new UploadResult();
		if (ufe.getBytes() != null) {
			System.out.println("qiniu upload:"+ufe.getFileName());
			DefaultPutRet dpr = store(ufe.getBytes(), ufe.getFileName());
			result.fileName = ufe.getFileName();
			result.fsize = ufe.getSize();
			result.hash = dpr.hash;
			result.key = dpr.key;
			result.bucket = prop.getQiniubucket();
			result.location = prop.getQiniuprefix() + ufe.getFileName();
			result.tag = "";
		} else {
			DefaultPutRet dpr = store(ufe.getUrl(), ufe.getFileName());
			result.fileName = ufe.getFileName();
			result.fsize = ufe.getFile().length();
			result.hash = dpr.hash;
			result.key = dpr.key;
			result.bucket = prop.getQiniubucket();
			result.location = prop.getQiniuprefix() + ufe.getFileName();
			result.tag = "";
		}
		System.out.println("#########################");
		System.out.println("qiniu upload success,download url:"+ result.location);
		System.out.println("#########################");

		return result;
	}

	@Override
	public void Download(String fileKeyorName) {
		// TODO 下载回调

	}

	public DefaultPutRet store(String filePath, String fileName) {
		Auth auth = init();
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(zone);
		UploadManager uploadManager = new UploadManager(cfg);
		String upToken = auth.uploadToken(prop.getQiniubucket());
		try {
			Response response = uploadManager.put(filePath, fileName, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			return putRet;
			// System.out.println(putRet.key);
			// System.out.println(putRet.hash);
		} catch (QiniuException ex) {

			Response r = ex.response;
			try {
				DefaultPutRet errorRet = new Gson().fromJson(r.bodyString(), DefaultPutRet.class);
				return errorRet;
			} catch (JsonSyntaxException | QiniuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println(r.toString());
		}
		return null;

	}

	public DefaultPutRet store(byte[] bytes, String fileName) {
		Auth auth = init();
		Configuration cfg = new Configuration(zone);
		UploadManager uploadManager = new UploadManager(cfg);
		// 生成上传凭证，然后准备上传
		String bucket = prop.getQiniubucket();

		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = fileName;
		byte[] uploadBytes = bytes;// "aaa".getBytes("utf-8");
		// ByteArrayInputStream byteInputStream=new
		// ByteArrayInputStream(uploadBytes);

		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(uploadBytes, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			// System.out.println(putRet.key);
			// System.out.println(putRet.hash);
			return putRet;
		} catch (QiniuException ex) {
			Response r = ex.response;
			try {
				DefaultPutRet errorRet = new Gson().fromJson(r.bodyString(), DefaultPutRet.class);
				return errorRet;
			} catch (JsonSyntaxException | QiniuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println(r.toString());
		}
		return null;

	}

}
