package xy.FileSystem.File;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import xy.FileSystem.Utils.FilePathUtil;
import xy.FileSystem.Utils.MimeTypeUtil;

//上传文件扩展模型
public class UploadFileExt {
	private String fileName;
	private String mimeType;
	private String catalog;
	private String url;

	private byte[] bytes;
	private File file;
	private InputStream inputStream;
	private Map<String, Object> metadata = new HashMap<String, Object>();
	private long size;

	public UploadFileExt(String filePath) {
		if (filePath.startsWith(FilePathUtil.HTTP_PREFIX) || filePath.startsWith(FilePathUtil.HTTPS_PREFIX)) {
			this.url = filePath;
			this.fileName = FilePathUtil.parseFileName(this.url);
		} else {
			this.file = new File(filePath);
			this.fileName = file.getName();
		}
	}

	public UploadFileExt(File file) {
		this.fileName = file.getName();

		this.file = file;
	}

	public UploadFileExt(String fileName, File file) {
		this.fileName = fileName;
		this.file = file;
	}

	public UploadFileExt(String fileName, InputStream inputStream, String mimeType) {
		this.fileName = fileName;
		this.inputStream = inputStream;
		this.mimeType = mimeType;
	}

	public UploadFileExt(String fileName, byte[] bytes, String mimeType,long size) {
		this.fileName = fileName;
		this.bytes = bytes;
		this.mimeType = mimeType;
		this.size = size;
	}

//	public UploadFileExt(String fileName, byte[] bytes) {
//		this.fileName = fileName;
//		this.bytes = bytes;
//		this.mimeType = perseMimeType(bytes);
//	}

	public String getFileName() {
		if (fileName.isEmpty()) {
			fileName = UUID.randomUUID().toString().replaceAll("\\-", "");
		}
		if (mimeType != null && !fileName.contains(".")) {
			String fileExtension = MimeTypeUtil.getFileExtension(mimeType);
			if(fileExtension != null)fileName = fileName + fileExtension;
		}
		
		return fileName;
	}


	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}


	public byte[] getBytes() {
		return bytes;
	}

	public File getFile() {
		return file;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setString(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public UploadFileExt addMetaData(String key, Object value) {
		metadata.put(key, value);
		return this;
	}
	
	public String getMimeType(){
		return mimeType;
	}


	public String getCatalog() {
		return catalog;
	}

	public UploadFileExt toCatalog(String catalog) {
		this.catalog = catalog;
		return this;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}


//	private static String perseMimeType(byte[] bytes){
//		try {
//			MagicMatch match = Magic.getMagicMatch(bytes);
//			String mimeType = match.getMimeType();
//			return mimeType;
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
