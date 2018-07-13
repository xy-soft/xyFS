package xy.FileSystem.Service;


import org.springframework.stereotype.Service;

import xy.FileSystem.File.FileListener;
import xy.FileSystem.File.UploadFileExt;
import xy.FileSystem.File.UploadResult;

@Service
public class FastdfsServcice implements FileListener {

	public void store(String filePath, String finalFilename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UploadResult Store(UploadFileExt ufe) {
		//TODO 上传回调
		return null;
	}

	@Override
	public void Download(String fileKeyorName) {
		// TODO 下载
		
	}

}
