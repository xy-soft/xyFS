package xy.FileSystem.Utils;

public class FilePathUtil {

	public static final String DIR_SPLITER = "/";
	public static final String HTTP_PREFIX = "http://";
	public static final String HTTPS_PREFIX = "https://";

	public static String parseFileExtension(String filePath){
		if(filePath.contains("/")){
			filePath = filePath.substring(filePath.lastIndexOf("/"));
		}
		filePath = filePath.split("\\?")[0];
		if(filePath.contains(".")){			
			return filePath.substring(filePath.lastIndexOf(".") + 1);
		}
		return null;
	}
	
	public static String  parseFileName(String filePath){
		filePath = filePath.split("\\?")[0];
		int index = filePath.lastIndexOf("/") + 1;
		if(index > 0){
			return filePath.substring(index);
		}
		return filePath;
	}
}
