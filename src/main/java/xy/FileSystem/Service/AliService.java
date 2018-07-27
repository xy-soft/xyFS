package xy.FileSystem.Service;


import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import xy.FileSystem.File.FileListener;
import xy.FileSystem.File.UploadFileExt;
import xy.FileSystem.File.UploadResult;
import xy.FileSystem.Propert.StorageProperties;

@Service
public class AliService implements FileListener{
	
	@Autowired
	StorageProperties prop;
	
	private OSS init(){

		return new OSSClientBuilder().build(prop.getAliendpoint(),
				prop.getAliaccesskeyid(), 
				prop.getAliaccesskeysecret());
	}
	
	@Override
	public UploadResult Store(UploadFileExt ufe) {
		//上传回调,断点续传
		//CompleteMultipartUploadResult cmuResult = store(ufe.getUrl());
		//上传回调,普通上传

		PutObjectResult cmuResult = store(ufe);
		UploadResult result = new UploadResult();

		result.fileName = ufe.getFileName();
		
		if (ufe.getBytes() != null) {
			result.fsize = ufe.getSize();
		}
		else{
			result.fsize = ufe.getFile().length();		
		}

		result.key = ufe.getFileName();
		result.bucket = prop.getAlibucketname();
		result.location = prop.getAlibucketname();
		result.tag = cmuResult.getETag();
		
		System.out.println("#########################");
		System.out.println("Ali OSS upload success,file id:"+ result.key);
		System.out.println("#########################");
		return result;
	}

	@Override
	public void Download(String fileKeyorName) {
		//下载回调
		
	}
	
	//断点续传上传,多线程
	public CompleteMultipartUploadResult store(String filePath) {
		OSS ossClient = init();
		try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(prop.getAlibucketname(), prop.getAlidownloadkey());
            //local file
            uploadFileRequest.setUploadFile(filePath);
            
            //5线程
            uploadFileRequest.setTaskNum(5);
            // 切分  1MB.
            uploadFileRequest.setPartSize(1024 * 1024 * 1);
            // 检查点
            uploadFileRequest.setEnableCheckpoint(true);
            
            UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);
            
            CompleteMultipartUploadResult multipartUploadResult = 
                    uploadResult.getMultipartUploadResult();
            System.out.println(multipartUploadResult.getETag());
            return multipartUploadResult;
            
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
		return null;
	}
	
	//断点续传上传,单线程
	public PutObjectResult store(UploadFileExt object) {
		try {
			PutObjectRequest request = null;
			if(object.getFile() != null){
				request = new PutObjectRequest(prop.getAlibucketname(), object.getFileName(), object.getFile());
			}else if(object.getBytes() != null){
				request = new PutObjectRequest(prop.getAlibucketname(), object.getFileName(), new ByteArrayInputStream(object.getBytes()));
			}else if(object.getInputStream() != null){
				request = new PutObjectRequest(prop.getAlibucketname(), object.getFileName(), object.getInputStream());
			}else{
				throw new IllegalArgumentException("upload object is NULL");
			}
			
			PutObjectResult result = init().putObject(request);
			return result;
			
		} catch (OSSException e) {
			throw new RuntimeException(e.getErrorMessage());
		}
	}
	
	public void download(String localFile, String fileKey){
		
		OSS ossClient = init();
		try {
			// 下载请求，启动断点续传。
			DownloadFileRequest downloadFileRequest = new DownloadFileRequest(prop.getAlibucketname(), fileKey);
			downloadFileRequest.setDownloadFile(localFile);
			downloadFileRequest.setPartSize(1 * 1024 * 1024);
			downloadFileRequest.setTaskNum(5);//5线程
			downloadFileRequest.setEnableCheckpoint(true);
			DownloadFileResult downloadResult = ossClient.downloadFile(downloadFileRequest);
	        
	        ObjectMetadata objectMetadata = downloadResult.getObjectMetadata();
	        System.out.println(objectMetadata.getETag());
	        System.out.println(objectMetadata.getLastModified());
	        System.out.println(objectMetadata.getUserMetadata().get("meta"));
        
	    } catch (OSSException oe) {
	        System.out.println("Caught an OSSException, which means your request made it to OSS, "
	                + "but was rejected with an error response for some reason.");
	        System.out.println("Error Message: " + oe.getErrorCode());
	        System.out.println("Error Code:       " + oe.getErrorCode());
	        System.out.println("Request ID:      " + oe.getRequestId());
	        System.out.println("Host ID:           " + oe.getHostId());
	    } catch (ClientException ce) {
	        System.out.println("Caught an ClientException, which means the client encountered "
	                + "a serious internal problem while trying to communicate with OSS, "
	                + "such as not being able to access the network.");
	        System.out.println("Error Message: " + ce.getMessage());
	    } catch (Throwable e) {
	        e.printStackTrace();
	    } finally {
	        ossClient.shutdown();
	    }
	}


	
	
}
