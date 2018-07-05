package xy.FileSystem.Service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import xy.FileSystem.Propert.StorageProperties;

@Service
public class QiniuService {
	
    @Autowired
    private  StorageProperties prop;
	
	public void store(String filePath, String fileName){
		
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.huadong());//俺家是华东区的
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//生成上传凭证，然后准备上传

		String bucket = prop.getQiniubucket();
		String accessKey = prop.getQiniuak();
		String secretKey = prop.getQiniusk();
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		//如果是linux，格式是/home/qiniu/test.png
		String localFilePath = filePath;
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = fileName;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
		    Response response = uploadManager.put(localFilePath, key, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    System.out.println(putRet.key);
		    System.out.println(putRet.hash);
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}
		
	}

	public void store(byte[] bytes, String fileName) {
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//生成上传凭证，然后准备上传
		String bucket = prop.getQiniubucket();
		String accessKey = prop.getQiniuak();
		String secretKey = prop.getQiniusk();
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = fileName;
		byte[] uploadBytes = bytes;//"hello qiniu cloud".getBytes("utf-8");
		//ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
		    Response response = uploadManager.put(uploadBytes, key, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    System.out.println(putRet.key);
		    System.out.println(putRet.hash);
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}
		
	}

}
