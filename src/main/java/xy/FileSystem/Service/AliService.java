package xy.FileSystem.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
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
		//上传回调
		CompleteMultipartUploadResult cmuResult = store(ufe.getUrl(),ufe.getFileName());
		UploadResult result = new UploadResult();
		if (ufe.getBytes() != null) {
			//TODO byte[] upload
		} else {
			result.fileName = ufe.getFileName();
			result.fsize = ufe.getFile().length();
			result.hash = Integer.toString(ufe.getFile().hashCode());
			result.key = cmuResult.getKey();
			result.bucket = cmuResult.getBucketName();
			result.location = cmuResult.getLocation();
			result.tag = cmuResult.getETag();
		}
		return result;
	}

	@Override
	public void Download(String fileKeyorName) {
		//下载回调
		
	}
		
	public CompleteMultipartUploadResult store(String filePath, String fileName) {
		OSS ossClient = init();
		try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(prop.getAlibucketname(), prop.getAlidownloadkey());
            // The local file to upload---it must exist.
            uploadFileRequest.setUploadFile(filePath);
            // Sets the concurrent upload task number to 5.
            uploadFileRequest.setTaskNum(5);
            // Sets the part size to 1MB.
            uploadFileRequest.setPartSize(1024 * 1024 * 1);
            // Enables the checkpoint file. By default it's off.
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
	
	public void download(String localFile, String fileKey){
		
		OSS ossClient = init();
		try {
			// 下载请求，10个任务并发下载，启动断点续传。
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
