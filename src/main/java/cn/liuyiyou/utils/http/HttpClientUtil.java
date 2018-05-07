package cn.liuyiyou.utils.http;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class HttpClientUtil {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 连接超时时间，单位为毫秒：{@value}
     */
    public static final int CONNECT_TIMEOUT = 10000;

    /**
     * 数据传输超时时间，单位为毫秒：{@value}
     */
    public static final int SOCKET_TIMEOUT = 5000;

    /**
     * 私有的构造函数
     */
    private HttpClientUtil() {

    }

    /**
     * 获取httpclient对象，支持https
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustStrategy anyTrustStrategy = new TrustAllTrustStrategy();
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy)
                    .build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (Exception e) {
            logger.error("注册 https 连接的套字节工厂失败。", e);
            throw new RuntimeException("注册 https 连接的套字节工厂失败", e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        // 设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        // 构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }

    /**
     * 发起 get 请求
     * @param url     请求的url，需要http（https）前缀
     * @param queries 请求的参数， 可为 null
     * @return 返回数据
     */
    public static String get(String url, Map<String, ?> queries) {
        String responseBody = "";
        // 支持https
        CloseableHttpClient httpClient = getHttpClient();

        StringBuilder sb = new StringBuilder(url);
        if (queries != null && !queries.isEmpty()) {
            boolean firstFlag = true;
            for (Map.Entry<String, ?> entry : queries.entrySet()) {
                sb.append(firstFlag ? "?" : "&");
                firstFlag = false;
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).build();
        HttpGet httpGet = new HttpGet(sb.toString());
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            logger.debug("Executing request {}", httpGet.getRequestLine());
            // 请求数据
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            logger.info("Http Get 请求 [{}] 的响应：{}", sb, statusLine);
            int status = statusLine.getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                responseBody = EntityUtils.toString(entity, Consts.UTF_8);
            } else {
                logger.error("Http Get 请求 [{}] 返回的错误码： {}", sb, status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            logger.error("执行 Http Get 请求 [" + sb + "] 失败。", e);
        } finally {
            close(response);
            close(httpClient);
        }
        return responseBody;
    }

    /**
     * 发起 post 请求
     *
     * @param url    请求的 url
     * @param params 请求参数，可为 null
     * @return 返回数据
     */
    public static String post(String url, Map<String, ?> params) {
        String responseBody = "";
        // 支持https
        CloseableHttpClient httpClient = getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).build();// 设置请求和传输超时时间
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(),
                        entry.getValue() == null ? "" : entry.getValue().toString()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        // 请求数据
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            logger.info("Http Post 请求 [{}], {} 的响应：{}", url, params, statusLine);
            int status = statusLine.getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                responseBody = EntityUtils.toString(entity, Consts.UTF_8);
            } else {
                logger.error("Http Post 请求 [{}], [] 返回的错误码： {}", url, params, status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            logger.error("执行 Http Post 请求 [" + url + "], " + params + " 失败。", e);
        } finally {
            close(response);
            close(httpClient);
        }
        return responseBody;
    }


    private static void close(Closeable something) {
        if (something != null) {
            try {
                something.close();
            } catch (IOException e) {
            }
        }
    }

    private static final class TrustAllTrustStrategy implements TrustStrategy {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return true;
        }
    }
}
