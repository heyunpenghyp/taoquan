package com.sb.taoquan.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by heyunpeng.com on 15-3-26. 496215095@qq.com
 */
public class HttpUtil {
    /**
     * 连接池里的最大连接数
     */
    public static final int MAX_TOTAL_CONNECTIONS = 100;

    /**
     * 每个路由的默认最大连接数
     */
    public static final int MAX_ROUTE_CONNECTIONS = 50;

    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT = 50000;

    /**
     * 套接字超时时间
     */
    public static final int SOCKET_TIMEOUT = 50000;

    /**
     * 连接池中 连接请求执行被阻塞的超时时间
     */
    public static final long CONN_MANAGER_TIMEOUT = 60000;

    /**
     * http连接相关参数
     */
    private static HttpParams parentParams;

    /**
     * http线程池管理器
     */
    private static PoolingClientConnectionManager cm;

    /**
     * http客户端
     */
    private static DefaultHttpClient httpClient;

    /**
     * 默认目标主机
     */
    private static final HttpHost DEFAULT_TARGETHOST = new HttpHost("http://www.taobao.com", 80);

    public final static String PROXY_HOST = "proxyHost";
    public final static String PROXY_PORT = "proxyPort";
    public final static String REFERER = "Referer";
    public static String CHARSET = "charset";
    public static String USER_AGENT = "User-Agent";
    public final static String COOKIE = "Cookie";

    /**
     * 初始化http连接池，设置参数、http头等等信息
     */
    static {

        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(
                new Scheme("https", 443, socketFactory));



        cm = new PoolingClientConnectionManager(schemeRegistry);

        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        cm.setMaxPerRoute(new HttpRoute(DEFAULT_TARGETHOST), 20);		//设置对目标主机的最大连接数

        parentParams = new BasicHttpParams();
        parentParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        parentParams.setParameter(ClientPNames.DEFAULT_HOST, DEFAULT_TARGETHOST);	//设置默认targetHost

        parentParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

        parentParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT);

        parentParams.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        parentParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);

        //设置头信息,模拟浏览器
        Collection<Header> collection = new ArrayList<Header>();
        collection.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"));
        collection.add(new BasicHeader("Accept", "application/json, text/javascript, */*; q=0.01"));
        collection.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
        collection.add(new BasicHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7"));
        collection.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));

        parentParams.setParameter(ClientPNames.DEFAULT_HEADERS, collection);
        //请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 1) {
                    // 如果超过最大重试次数，那么就不要继续了
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // 不要重试SSL握手异常
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 如果请求被认为是幂等的，那么就重试
                    return true;
                }
                return false;
            }
        };

        httpClient = new DefaultHttpClient(cm, parentParams);

        httpClient.setHttpRequestRetryHandler(httpRequestRetryHandler);
    }


    /**
     * 抓取url所指的页面代码
     * @param url 目标页面的url
     * @return 页面代码
     */
    public static String[]  executeGet(String url, Map<String, String> args) throws Exception {
        String[] result = new String[2];
        String html = null;
        HttpGet httpGet = new HttpGet(url);
        if (StringUtils.isNotEmpty(args.get(PROXY_HOST))) {
            HttpHost proxyHost = new HttpHost(args.get(PROXY_HOST), Integer.parseInt(args.get(PROXY_PORT)));
            httpGet.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);//设置代理
        }
        if (StringUtils.isNotEmpty(args.get(REFERER))) {
            httpGet.setHeader("Referer", args.get(REFERER));
        }
        if (StringUtils.isNotEmpty(args.get(USER_AGENT))) {
            httpGet.setHeader(USER_AGENT, args.get(USER_AGENT));
        }
        if (StringUtils.isNotEmpty(args.get(COOKIE))) {
            httpGet.setHeader(COOKIE, args.get(COOKIE));
        }

        HttpResponse httpResponse;
        HttpEntity httpEntity;
        try {
            httpResponse = httpClient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(200 != statusCode) {
                return result;
            }

            httpEntity = httpResponse.getEntity();
            if(httpEntity != null){
                html = readHtmlContentFromEntity(httpEntity, args);
            }

            String cookie = generateCookie(httpClient);
            result[0] = html;
            result[1] = cookie;

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if(httpGet != null){
                httpGet.releaseConnection();
            }
        }

        return result;
    }


    /**
     * 抓取url所指的页面代码
     * @param url 目标页面的url
     * @return 页面代码
     */
    public static String executeJsonPost(String url, String json) throws Exception {

        HttpPost httppost = new HttpPost(url);
        String html=null;
        try {
            //创建post请求

            //json
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httppost.setEntity(entity);
            //执行post请求
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {

                //状态
                if (200!=response.getStatusLine().getStatusCode()){
                    return html;
                }

                //响应实体
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                   html = EntityUtils.toString(resEntity);
                }
                //关闭HttpEntity流
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            if(httppost != null){
                httppost.releaseConnection();
            }
        }

        return html;
    }

    private static String generateCookie(DefaultHttpClient httpClient) {
        StringBuffer sb = new StringBuffer();
        CookieStore cookieStore  = httpClient.getCookieStore();
        if (cookieStore != null) {
            List<Cookie> cookieList = cookieStore.getCookies();
            if (CollectionUtils.isNotEmpty(cookieList)) {
                for (Cookie cookie : cookieList) {
                    sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
                }
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length()-1).toString();
        }
        return null;
    }

    /**
     * 从response返回的实体中读取页面代码
     * @param httpEntity Http实体
     * @return 页面代码
     * @throws ParseException
     * @throws IOException
     */
    private static String readHtmlContentFromEntity(HttpEntity httpEntity, Map<String, String> args) throws ParseException, IOException {
        String html = "";
        Header header = httpEntity.getContentEncoding();
        if(httpEntity.getContentLength() < 2147483647L){			//EntityUtils无法处理ContentLength超过2147483647L的Entity
            if(header != null && "gzip".equals(header.getValue())){
                if (args.get(CHARSET) != null) {
                    html = EntityUtils.toString(new GzipDecompressingEntity(httpEntity), args.get(CHARSET));
                } else {
                    html = EntityUtils.toString(new GzipDecompressingEntity(httpEntity));
                }
            } else {
                if (args.get(CHARSET) != null) {
                    html = EntityUtils.toString(httpEntity, args.get(CHARSET));
                } else {
                    html = EntityUtils.toString(httpEntity);
                }
            }
        } else {
            InputStream in = httpEntity.getContent();
            if(header != null && "gzip".equals(header.getValue())){
                if (args.get(CHARSET) != null) {
                    html = unZip(in, args.get(CHARSET));
                } else {
                    html = unZip(in, ContentType.getOrDefault(httpEntity).getCharset().toString());
                }

            } else {
                if (args.get(CHARSET) != null) {
                    html = readInStreamToString(in, args.get(CHARSET));
                } else {
                    html = readInStreamToString(in, ContentType.getOrDefault(httpEntity).getCharset().toString());
                }
            }
            if(in != null){
                in.close();
            }
        }
        return html;
    }

    /**
     * 解压服务器返回的gzip流
     * @param in 抓取返回的InputStream流
     * @param charSet 页面内容编码
     * @return 页面内容的String格式
     * @throws IOException
     */
    private static String unZip(InputStream in, String charSet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(in);
            byte[] _byte = new byte[1024];
            int len = 0;
            while ((len = gis.read(_byte)) != -1) {
                baos.write(_byte, 0, len);
            }
            String unzipString = new String(baos.toByteArray(), charSet);
            return unzipString;
        } finally {
            if (gis != null) {
                gis.close();
            }
            if(baos != null){
                baos.close();
            }
        }
    }

    /**
     * 读取InputStream流
     * @param in InputStream流
     * @return 从流中读取的String
     * @throws IOException
     */
    private static String readInStreamToString(InputStream in, String charSet) throws IOException {
        StringBuilder str = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, charSet));
        while((line = bufferedReader.readLine()) != null){
            str.append(line);
            str.append("\n");
        }
        if(bufferedReader != null) {
            bufferedReader.close();
        }
        return str.toString();
    }

    /*
    *
    */
    public static String post(String url, Map map) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // 超过最大次数则不需要重试
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 服务停掉则重新尝试连接
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // SSL异常不需要重试
                    return false;
                }
                HttpRequest request = (HttpRequest) context
                        .getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 请求内容相同则重试
                    return true;
                }
                return false;
            }
        };
        httpclient.setHttpRequestRetryHandler(myRetryHandler);

        String body = null;
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                formParams.add(new BasicNameValuePair(key, (String) map
                        .get(key)));
            }

            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(formParams, "utf-8"));
            // add by zouqiang
            // 请求超时
            httpclient.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            // 读取超时
            httpclient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 30000);
            // 发送请求
            HttpResponse httpresponse = httpclient.execute(httppost);
            System.out.println(httpresponse.getStatusLine().getStatusCode());
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity, "utf-8");
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }finally{
            if(null != httpclient){
                httpclient.getConnectionManager().shutdown();
            }
        }
        return body;

    }

    /**
     * 获取HttpRequestRetryHandler
     *
     * @return
     */
    private static HttpRequestRetryHandler getHttpRequestRetryHandler() {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // 超过最大次数则不需要重试
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 服务停掉则重新尝试连接
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // SSL异常不需要重试
                    return false;
                }
                HttpRequest request = (HttpRequest) context
                        .getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 请求内容相同则重试
                    return true;
                }
                return false;
            }
        };
        return myRetryHandler;
    }

    public static String get(String url) throws Exception {
        HttpRequestRetryHandler myRetryHandler = getHttpRequestRetryHandler();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.setHttpRequestRetryHandler(myRetryHandler);

        String responseResult = null;
        try {
            // Post请求
            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
            httpGet.setHeader("Accept-Language","zh-CN");
            httpGet.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; QQWubi 87; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
            httpGet.setHeader("Accept-Encoding","gzip, deflate");
            httpGet.setHeader("Host","you never be know");
            httpGet.setHeader("Connection","Keep-Alive");

            // 请求超时
            httpclient.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            // 读取超时
            httpclient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 30000);
            // 发送请求
            HttpResponse httpresponse = httpclient.execute(httpGet);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            responseResult = EntityUtils.toString(entity, "utf-8");
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }finally{
            if(null != httpclient){
                httpclient.getConnectionManager().shutdown();
            }

        }
        return responseResult;

    }
}
