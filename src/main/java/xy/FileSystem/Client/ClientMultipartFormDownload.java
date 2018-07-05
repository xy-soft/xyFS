package xy.FileSystem.Client;

import xy.FileSystem.Utils.HttpHelper;

//下载测试

public class ClientMultipartFormDownload {
	public static void main(String[] args) throws Exception {

		HttpHelper.executeDownloadFile(HttpHelper.createHttpClient(), 
				"http://localhost:9091/files/abc.txt", 
				"D://XXX.txt", //要保存在本地的文件
				"UTF-8",
				true);

	}

}
