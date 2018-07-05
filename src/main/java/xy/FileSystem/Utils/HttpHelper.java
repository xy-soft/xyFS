package xy.FileSystem.Utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xy.FileSystem.File.HttpResult;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 

public class HttpHelper {
    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);
    private static final String DEFAULT_CHARSET = "UTF-8";// 默认请求编码
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;// 默认等待响应时间(毫秒)
    private static final int DEFAULT_RETRY_TIMES = 0;// 默认执行重试的次数
 
    /**
     * 创建一个默认的可关闭的HttpClient
     *
     * @return
     */
    public static CloseableHttpClient createHttpClient() {
        return createHttpClient(DEFAULT_RETRY_TIMES, DEFAULT_SOCKET_TIMEOUT);
    }
 
    /**
     * 创建一个可关闭的HttpClient
     *
     * @param socketTimeout 请求获取数据的超时时间
     * @return
     */
    public static CloseableHttpClient createHttpClient(int socketTimeout) {
        return createHttpClient(DEFAULT_RETRY_TIMES, socketTimeout);
    }
 
    /**
     * 创建一个可关闭的HttpClient
     *
     * @param socketTimeout 请求获取数据的超时时间
     * @param retryTimes    重试次数，小于等于0表示不重试
     * @return
     */
    public static CloseableHttpClient createHttpClient(int retryTimes, int socketTimeout) {
        Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(5000);// 设置连接超时时间，单位毫秒
        builder.setConnectionRequestTimeout(1000);// 设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
        if (socketTimeout >= 0) {
            builder.setSocketTimeout(socketTimeout);// 请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        }
        RequestConfig defaultRequestConfig = builder.setCookieSpec(CookieSpecs.STANDARD_STRICT).setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
        // 开启HTTPS支持
        enableSSL();
        // 创建可用Scheme
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        // 创建ConnectionManager，添加Connection配置信息
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (retryTimes > 0) {
            setRetryHandler(httpClientBuilder, retryTimes);
        }
        CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
        return httpClient;
    }
 
    /**
     * 执行GET请求
     *
     * @param url           远程URL地址
     * @param charset       请求的编码，默认UTF-8
     * @param socketTimeout 超时时间（毫秒）
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executeGet(String url, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executeGet(httpClient, url, null, null, charset, true);
    }
 
    /**
     * 执行GET请求
     *
     * @param url           远程URL地址
     * @param charset       请求的编码，默认UTF-8
     * @param socketTimeout 超时时间（毫秒）
     * @return String
     * @throws IOException
     */
    public static String executeGetString(String url, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executeGetString(httpClient, url, null, null, charset, true);
    }
 
    /**
     * 执行HttpGet请求
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param url             请求的远程地址
     * @param referer         referer信息，可传null
     * @param cookie          cookies信息，可传null
     * @param charset         请求编码，默认UTF8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return HttpResult
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeGet(CloseableHttpClient httpClient, String url, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executeGetResponse(httpClient, url, referer, cookie);
            //Http请求状态码
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * @param httpClient httpclient对象
     * @param url        执行GET的URL地址
     * @param referer    referer地址
     * @param cookie     cookie信息
     * @return CloseableHttpResponse
     * @throws IOException
     */
    public static CloseableHttpResponse executeGetResponse(CloseableHttpClient httpClient, String url, String referer, String cookie) throws IOException {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        HttpGet get = new HttpGet(url);
        if (cookie != null && !"".equals(cookie)) {
            get.setHeader("Cookie", cookie);
        }
        if (referer != null && !"".equals(referer)) {
            get.setHeader("referer", referer);
        }
        return httpClient.execute(get);
    }
 
    /**
     * 执行HttpGet请求
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param url             请求的远程地址
     * @param referer         referer信息，可传null
     * @param cookie          cookies信息，可传null
     * @param charset         请求编码，默认UTF8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return String
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String executeGetString(CloseableHttpClient httpClient, String url, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executeGetResponse(httpClient, url, referer, cookie);
            return getResult(httpResponse, charset);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * 简单方式执行POST请求
     *
     * @param url           远程URL地址
     * @param paramsObj     post的参数，支持map<String,String>,JSON,XML
     * @param charset       请求的编码，默认UTF-8
     * @param socketTimeout 超时时间(毫秒)
     * @return HttpResult
     * @throws IOException
     */
    public static HttpResult executePost(String url, Object paramsObj, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executePost(httpClient, url, paramsObj, null, null, charset, true);
    }
 
    /**
     * 简单方式执行POST请求
     *
     * @param url           远程URL地址
     * @param paramsObj     post的参数，支持map<String,String>,JSON,XML
     * @param charset       请求的编码，默认UTF-8
     * @param socketTimeout 超时时间(毫秒)
     * @return HttpResult
     * @throws IOException
     */
    public static String executePostString(String url, Object paramsObj, String charset, int socketTimeout) throws IOException {
        CloseableHttpClient httpClient = createHttpClient(socketTimeout);
        return executePostString(httpClient, url, paramsObj, null, null, charset, true);
    }
 
    /**
     * 执行HttpPost请求
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param url             请求的远程地址
     * @param paramsObj       提交的参数信息，目前支持Map,和String(JSON\xml)
     * @param referer         referer信息，可传null
     * @param cookie          cookies信息，可传null
     * @param charset         请求编码，默认UTF8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static HttpResult executePost(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executePostResponse(httpClient, url, paramsObj, referer, cookie, charset);
            //Http请求状态码
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
 
    /**
     * 执行HttpPost请求
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param url             请求的远程地址
     * @param paramsObj       提交的参数信息，目前支持Map,和String(JSON\xml)
     * @param referer         referer信息，可传null
     * @param cookie          cookies信息，可传null
     * @param charset         请求编码，默认UTF8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return String
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String executePostString(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            charset = getCharset(charset);
            httpResponse = executePostResponse(httpClient, url, paramsObj, referer, cookie, charset);
            return getResult(httpResponse, charset);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
 
    /**
     * @param httpClient HttpClient对象
     * @param url        请求的网络地址
     * @param paramsObj  参数信息
     * @param referer    来源地址
     * @param cookie     cookie信息
     * @param charset    通信编码
     * @return CloseableHttpResponse
     * @throws IOException
     */
    private static CloseableHttpResponse executePostResponse(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset) throws IOException {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        HttpPost post = new HttpPost(url);
        if (cookie != null && !"".equals(cookie)) {
            post.setHeader("Cookie", cookie);
        }
        if (referer != null && !"".equals(referer)) {
            post.setHeader("referer", referer);
        }
        // 设置参数
        HttpEntity httpEntity = getEntity(paramsObj, charset);
        if (httpEntity != null) {
            post.setEntity(httpEntity);
        }
        return httpClient.execute(post);
    }
 
    /**
     * 执行文件上传
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param remoteFileUrl   远程接收文件的地址
     * @param localFilePath   本地文件地址
     * @param charset         请求编码，默认UTF-8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeUploadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient) throws IOException {
        CloseableHttpResponse httpResponse = null;
        try {
            if (httpClient == null) {
                httpClient = createHttpClient();
            }
            // 把文件转换成流对象FileBody
            File localFile = new File(localFilePath);
            FileBody fileBody = new FileBody(localFile);
            // 以浏览器兼容模式运行，防止文件名乱码。
            HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadFile", fileBody).setCharset(CharsetUtils.get("UTF-8")).build();
            // uploadFile对应服务端类的同名属性<File类型>
            // .addPart("uploadFileName", uploadFileName)
            // uploadFileName对应服务端类的同名属性<String类型>
            HttpPost httpPost = new HttpPost(remoteFileUrl);
            httpPost.setEntity(reqEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }
 
    /**
     * 执行文件上传(以二进制流方式)
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param remoteFileUrl   远程接收文件的地址
     * @param localFilePath   本地文件地址
     * @param charset         请求编码，默认UTF-8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult executeUploadFileStream(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
        CloseableHttpResponse httpResponse = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            if (httpClient == null) {
                httpClient = createHttpClient();
            }
            // 把文件转换成流对象FileBody
            File localFile = new File(localFilePath);
            fis = new FileInputStream(localFile);
            byte[] tmpBytes = new byte[1024];
            byte[] resultBytes = null;
            baos = new ByteArrayOutputStream();
            int len;
            while ((len = fis.read(tmpBytes, 0, 1024)) != -1) {
                baos.write(tmpBytes, 0, len);
            }
            resultBytes = baos.toByteArray();
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(resultBytes, ContentType.APPLICATION_OCTET_STREAM);
            HttpPost httpPost = new HttpPost(remoteFileUrl);
            httpPost.setEntity(byteArrayEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = getResult(httpResponse, charset);
            return new HttpResult(statusCode, content);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }
 
    /**
     * 执行文件下载
     *
     * @param httpClient      HttpClient客户端实例，传入null会自动创建一个
     * @param remoteFileUrl   远程下载文件地址
     * @param localFilePath   本地存储文件地址
     * @param charset         请求编码，默认UTF-8
     * @param closeHttpClient 执行请求结束后是否关闭HttpClient客户端实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static boolean executeDownloadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        InputStream in = null;
        FileOutputStream fout = null;
        try {
            HttpGet httpget = new HttpGet(remoteFileUrl);
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return false;
            }
            in = entity.getContent();
            File file = new File(localFilePath);
            fout = new FileOutputStream(file);
            int l;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
                // 注意这里如果用OutputStream.write(buff)的话，图片会失真
            }
            // 将文件输出到本地
            fout.flush();
            EntityUtils.consume(entity);
            return true;
        } finally {
            // 关闭低层流。
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                }
            }
            if (closeHttpClient && httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                }
            }
        }
    }
 
    /**
     * 根据参数获取请求的Entity
     *
     * @param paramsObj
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static HttpEntity getEntity(Object paramsObj, String charset) throws UnsupportedEncodingException {
        if (paramsObj == null) {
            logger.info("当前未传入参数信息，无法生成HttpEntity");
            return null;
        }
        if (Map.class.isInstance(paramsObj)) {// 当前是map数据
            @SuppressWarnings("unchecked")
            Map<String, String> paramsMap = (Map<String, String>) paramsObj;
            List<NameValuePair> list = getNameValuePairs(paramsMap);
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list, charset);
            httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            return httpEntity;
        } else if (String.class.isInstance(paramsObj)) {// 当前是string对象，可能是
            String paramsStr = (String) paramsObj;
            StringEntity httpEntity = new StringEntity(paramsStr, charset);
            if (paramsStr.startsWith("{")) {
                httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            } else if (paramsStr.startsWith("<")) {
                httpEntity.setContentType(ContentType.APPLICATION_XML.getMimeType());
            } else {
                httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            }
            return httpEntity;
        } else {
            logger.info("当前传入参数不能识别类型，无法生成HttpEntity");
        }
        return null;
    }
 
    /**
     * 从结果中获取出String数据
     *
     * @param httpResponse http结果对象
     * @param charset      编码信息
     * @return String
     * @throws ParseException
     * @throws IOException
     */
    private static String getResult(CloseableHttpResponse httpResponse, String charset) throws ParseException, IOException {
        String result = null;
        if (httpResponse == null) {
            return result;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return result;
        }
        result = EntityUtils.toString(entity, charset);
        EntityUtils.consume(entity);// 关闭应该关闭的资源，适当的释放资源 ;也可以把底层的流给关闭了
        return result;
    }
 
    /**
     * 转化请求编码
     *
     * @param charset 编码信息
     * @return String
     */
    private static String getCharset(String charset) {
        return charset == null ? DEFAULT_CHARSET : charset;
    }
 
    /**
     * 将map类型参数转化为NameValuePair集合方式
     *
     * @param paramsMap
     * @return
     */
    private static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap) {
        List<NameValuePair> list = new ArrayList<>();
        if (paramsMap == null || paramsMap.isEmpty()) {
            return list;
        }
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }
 
    /**
     * 开启SSL支持
     */
    private static void enableSSL() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{manager}, null);
            socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private static SSLConnectionSocketFactory socketFactory;
 
    // HTTPS网站一般情况下使用了安全系数较低的SHA-1签名，因此首先我们在调用SSL之前需要重写验证方法，取消检测SSL。
    private static TrustManager manager = new X509TrustManager() {
 
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //
 
        }
 
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //
 
        }
    };
 
    /**
     * 为httpclient设置重试信息
     *
     * @param httpClientBuilder
     * @param retryTimes
     */
    private static void setRetryHandler(HttpClientBuilder httpClientBuilder, final int retryTimes) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= retryTimes) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 如果请求被认为是幂等的，那么就重试
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        httpClientBuilder.setRetryHandler(myRetryHandler);
    }
 
}
