package com.ron.utils;//package com.ron.ssm.util;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.*;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.net.ssl.SSLContext;
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * http工具类
// * <p/>
// * 采用线程池及http线程池，如要实现自定义的返回结果处理，请调用带有handler参数的方法
// * 并实现ResponseHandler接口
// * <p/>
// * Created by moon
// * on 2016/1/30.
// */
//public class HttpUtil {
//    protected static Logger logger = LogManager.getLogger(HttpUtil.class);
//    public static final int CORE_POOL_SIZE = 100;// 线程池的核心线程数
//    public static final int MAX_POOL_SIZE = 100;// 线程池的最大线程数
//    public static final int HTTP_CLIENT_POOL_SIZE = 300;// HTTP线程池的容量
//    public static final int HTTP_CLIENT_POOL_PER_ROUTE = 100;// 每个路由地址并发数
//    //启用的HTTPS协议版本与加密信息，默认获取所有的支持类型.
//    public static final String[] HTTPS_PROTOCOL = new String[]{"SSLv3","TLSv1","TLSv1.1","TLSv1.2"};
//    public static final String[] HTTPS_CIPHER_SUITES = getInitCipherSuites();
//    public static final String CHARSET = "UTF-8";
//    public static final Charset DEFAULT_CHARSET = Charset.forName(CHARSET);
//
//    private static ThreadPoolExecutor executor = null;
//    private static PoolingHttpClientConnectionManager clientConnectionManager = null;
//    private static HttpClient client = null;
//
//    static {
//        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
//                10000L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>());
//        try {
//            //必须在这里的构造函数中启用https的所有协议和加密算法,满足TeleSign平台
//            clientConnectionManager = new PoolingHttpClientConnectionManager(getRegistry());
//            clientConnectionManager.setMaxTotal(HTTP_CLIENT_POOL_SIZE);
//            clientConnectionManager.setDefaultMaxPerRoute(HTTP_CLIENT_POOL_PER_ROUTE);
//            client = HttpClients.custom().setConnectionManager(clientConnectionManager).build();
//        } catch (Exception e) {
//            logger.error("HttpClient 初始化失败!", e);
//        }
//    }
//
//    //从系统中获取所有支持的SSL协议,用于注册到连接池启用它
//    private static String[] getInitProtocols() {
//        try {
//            SSLContext context = SSLContext.getInstance("TLSv1");
//            context.init(null, null, null);
//            String[] supportedProtocols = context.getSupportedSSLParameters().getProtocols();
//            logger.info("支持的SSL加密协议:" + Arrays.deepToString(supportedProtocols));
//            return supportedProtocols;
//        } catch (Exception e) {
//            logger.error("SSL协议版本初始化获取失败!", e);
//        }
//        return null;
//    }
//
//    //从系统中获取所有支持的SSL加密算法,用于注册到连接池启用它
//    private static String[] getInitCipherSuites() {
//        try {
//            SSLContext context = SSLContext.getInstance("TLSv1");
//            context.init(null, null, null);
//            String[] supportedCipherSuites = context.getSupportedSSLParameters().getCipherSuites();
//            logger.info("支持的SSL加密算法:" + Arrays.deepToString(supportedCipherSuites));
//            return supportedCipherSuites;
//        } catch (Exception e) {
//            logger.error("SSL加密算法初始化获取失败!", e);
//        }
//        return null;
//    }
//
//    //修改https的启用的协议和加密算法
//    private static Registry<ConnectionSocketFactory> getRegistry() throws Exception {
//        SSLContext sslContext = SSLContexts.custom().build();
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
//                HTTPS_PROTOCOL, HTTPS_CIPHER_SUITES, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//        return RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", sslConnectionSocketFactory)
//                .build();
//    }
//
//    public static ThreadPoolExecutor getExecutor() {
//        return executor;
//    }
//
//    public static PoolingHttpClientConnectionManager getClientConnectionManager() {
//        return clientConnectionManager;
//    }
//
//    private HttpUtil() {
//    }
//
//    //默认的Response处理器
//    private static ResponseHandler<String> defaultResponseHandler = new ResponseHandler<String>() {
//        @Override
//        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//            int status = response.getStatusLine().getStatusCode();
//            HttpEntity entity = response.getEntity();
//            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
//                String result = entity != null ? EntityUtils.toString(entity) : null;
//                logger.info("默认处理,返回数据:" + result);
//                return result;
//            } else {
//                logger.error("默认处理,状态码:" + status + ",返回数据:" + EntityUtils.toString(entity));
//                throw new ClientProtocolException("Unexpected handler status: " + status);
//            }
//        }
//    };
//
//    public static void get(String url) {
//        if (url == null || url.isEmpty()) {
//            throw new RuntimeException("url can not by null or empty");
//        }
//        get(url, parseParams(url));
//    }
//
//    public static void get(String url, ResponseHandler handler) {
//        if (url == null || url.isEmpty()) {
//            throw new RuntimeException("url can not by null or empty");
//        }
//        String[] urls = new String[]{url};
//        get(urls, parseParams(url), handler);
//    }
//
//    public static void get(String uri, Map<String, String> params) {
//        if (uri == null || uri.isEmpty()) {
//            throw new RuntimeException("url can not by null or empty");
//        }
//        String[] uris = new String[]{uri};
//        get(uris, params, defaultResponseHandler);
//    }
//
//    public static void get(String url, Map<String, String> params, ResponseHandler handler) {
//        if (url == null || url.isEmpty()) {
//            throw new RuntimeException("url can not by null or empty");
//        }
//        String[] urls = new String[]{url};
//        get(urls, params, handler);
//    }
//
//    public static void get(String[] uris, Map<String, String> params, ResponseHandler handler) {
//        if (uris == null || uris.length == 0) {
//            throw new RuntimeException("uris can not by null or empty");
//        }
//        String queryString = formatParams(params, CHARSET);
//        for (String uri : uris) {
//            uri = uri.contains("?") ? uri.substring(0, uri.indexOf("?")) : uri;
//            HttpGet httpget = new HttpGet(completionUrl(uri) + "?" + queryString);
//            executor.execute(new GetThread(client, httpget, handler));
//        }
//    }
//
//    public static void post(String[] uris, Map<String, String> params, ResponseHandler handler) throws UnsupportedEncodingException {
//        if (uris == null || uris.length == 0) {
//            throw new RuntimeException("uris can not by null or empty");
//        }
//        List<NameValuePair> nameValuePairList = format(params);
//        for (String uri : uris) {
//            uri = uri.contains("?") ? uri.substring(0, uri.indexOf("?")) : uri;
//            HttpPost httpPost = new HttpPost(completionUrl(uri));
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
//            executor.execute(new PostThread(client, httpPost, handler));
//        }
//    }
//
//    public static void post(String[] uris, Map<String, String> params, Map<String, String> headersMap, ResponseHandler handler) throws UnsupportedEncodingException {
//        if (uris == null || uris.length == 0) {
//            throw new RuntimeException("uris can not by null or empty");
//        }
//        List<NameValuePair> nameValuePairList = format(params);
//        Header[] headers = null;
//        if (headersMap != null && !headersMap.isEmpty()) {
//            int size = headersMap.size();
//            headers = new Header[size];
//            for (String key : headersMap.keySet()) {
//                headers[--size] = new BasicHeader(key, headersMap.get(key));
//            }
//        }
//        for (String uri : uris) {
//            uri = uri.contains("?") ? uri.substring(0, uri.indexOf("?")) : uri;
//            HttpPost httpPost = new HttpPost(completionUrl(uri));
//            if (headers != null) {
//                httpPost.setHeaders(headers);
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, CHARSET));
//            executor.execute(new PostThread(client, httpPost, handler));
//        }
//    }
//
//
//    public static String formatParams(Map<String, String> params, String charset) {
//        if (params == null || params.isEmpty()) {
//            return "";
//        }
//        return URLEncodedUtils.format(format(params), charset);
//    }
//
//    public static List<NameValuePair> format(Map<String, String> params) {
//        List<NameValuePair> nameValuePairList = new ArrayList<>();
//        if (params == null || params.isEmpty()) {
//            return nameValuePairList;
//        }
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//        return nameValuePairList;
//    }
//
//    public static Map<String, String> parseParams(String url) {
//        Map<String, String> result = new HashMap<>();
//        if (url == null || url.isEmpty() || !url.contains("?")) {
//            return result;
//        }
//        url = url.substring(url.indexOf("?") + 1);
//        List<NameValuePair> nameValuePairList = URLEncodedUtils.parse(url, DEFAULT_CHARSET);
//        if (nameValuePairList.isEmpty()) {
//            return result;
//        }
//        for (NameValuePair nameValuePair : nameValuePairList) {
//            result.put(nameValuePair.getName(), nameValuePair.getValue());
//        }
//        return result;
//    }
//
//    /**
//     * 补全http协议
//     *
//     * @param url 原始url
//     * @return 补全后的url
//     */
//    private static String completionUrl(String url) {
//        if (url.startsWith("http:") || url.startsWith("https:")) {
//            return url;
//        }
//        return "http://" + url;
//    }
//
//    static class GetThread extends Thread {
//
//        private final HttpClient httpClient;
//        private final HttpContext context;
//        private final HttpGet httpGet;
//        private ResponseHandler handler = null;
//
//        public GetThread(HttpClient httpClient, HttpGet httpget) {
//            this.httpClient = httpClient;
//            this.context = new BasicHttpContext();
//            this.httpGet = httpget;
//        }
//
//        public GetThread(HttpClient httpClient, HttpGet httpget, ResponseHandler handler) {
//            this.httpClient = httpClient;
//            this.context = new BasicHttpContext();
//            this.httpGet = httpget;
//            this.handler = handler;
//        }
//
//        @Override
//        public void run() {
//            try {
//                logger.info("http get请求url:{}", httpGet.getURI());
//                this.httpClient.execute(this.httpGet, handler == null ? defaultResponseHandler : handler, this.context);
//            } catch (Exception ex) {
//                this.httpGet.abort();
//            } finally {
//                httpGet.releaseConnection();
//            }
//        }
//    }
//
//    static class PostThread extends Thread {
//
//        private final HttpClient httpClient;
//        private final HttpContext context;
//        private final HttpPost httpPost;
//        private ResponseHandler handler = null;
//
//        public PostThread(HttpClient httpClient, HttpPost httpPost) {
//            this.httpClient = httpClient;
//            this.context = new BasicHttpContext();
//            this.httpPost = httpPost;
//        }
//
//        public PostThread(HttpClient httpClient, HttpPost httpPost, ResponseHandler handler) {
//            this.httpClient = httpClient;
//            this.context = new BasicHttpContext();
//            this.httpPost = httpPost;
//            this.handler = handler;
//        }
//
//        @Override
//        public void run() {
//            try {
//                String entityStr = IOUtils.toString(httpPost.getEntity().getContent());
//                logger.info("http post请求url:{},内容:{}", httpPost.getURI(), entityStr);
//                this.httpClient.execute(this.httpPost, handler == null ? defaultResponseHandler : handler, this.context);
//            } catch (Exception ex) {
//                this.httpPost.abort();
//                logger.error(ex);
//            } finally {
//                httpPost.releaseConnection();
//            }
//        }
//    }
//
//    public static String doHttpPost(String urlString, String requestString) {
//        try {
//            URL url = new URL(urlString);
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write(requestString);
//            wr.flush();
//            // Get the response
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            String response = "";
//            while ((line = rd.readLine()) != null) {
//                response += line;
//            }
//            wr.close();
//            rd.close();
//
//            return response;
//        } catch (IOException ex) {
//            System.err.println(ex); return ex.toString();
//        }
//    }
//
//}
