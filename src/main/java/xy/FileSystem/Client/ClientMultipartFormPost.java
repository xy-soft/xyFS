package xy.FileSystem.Client;


import xy.FileSystem.File.HttpResult;
import xy.FileSystem.Utils.HttpHelper;

//上传测试

public class ClientMultipartFormPost {

	public static void main(String[] args) throws Exception {

		HttpResult  result = HttpHelper.executeUploadFile(HttpHelper.createHttpClient(), 
				"http://localhost:9091/", 
				"D://nginx.conf", //要上传的本地文件
				"UTF-8",
				true);
		System.out.println(result.getStatusCode());
		System.out.println(result.getContent());

	}

}