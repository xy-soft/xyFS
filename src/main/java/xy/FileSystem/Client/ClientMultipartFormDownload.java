package xy.FileSystem.Client;

import xy.FileSystem.Utils.HttpHelper;

//下载测试

public class ClientMultipartFormDownload {
	public static void main(String[] args) throws Exception {

		HttpHelper.executeDownloadFile(HttpHelper.createHttpClient(), 
				"http://localhost:9091/files/wangxin_Tigase开发文档.doc", //服务器文件
				"D://wangxin_Tigase开发文档.doc", //下载到本地的文件
				"UTF-8",
				true);

	}

}
