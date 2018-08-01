package xy.FileSystem.Client;

import xy.FileSystem.File.HttpResult;
import xy.FileSystem.Utils.HttpHelper;

//Post上传演示
public class ClientMultipartFormPost {

	public static void main(String[] args) throws Exception {

		HttpResult  result = HttpHelper.executeUploadFile(HttpHelper.createHttpClient(), 
				"http://localhost:9091/fileUploadPost", //post路径url
				"D://Tigase开发文档.doc", //要上传的本地文件全路径
				"1234", // appid
				"wangxin", //上传者username
				"", //groupid,如果不涉及群组，则无需传此参数
				true,//执行请求结束后是否关闭HttpClient客户端实例
				"UTF-8" );

		System.out.println(result.toString());

	}

}