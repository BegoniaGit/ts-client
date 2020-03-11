package site.yan.httpclient.client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import site.yan.core.utils.CollectionsUtil;
import site.yan.httpclient.model.ClientResp;

import java.io.IOException;
import java.util.*;


public abstract class AbstractHttpClient {
    protected List<Header> headers = new ArrayList(16);
    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    public abstract String doGet(String url);

    public abstract String doPost(String httpUrl);

    public abstract String doPost(String httpUrl, String params);

    public abstract String doPost(String httpUrl, Map<String, String> maps);


    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     */
    protected ClientResp sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    protected ClientResp sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     */
    protected ClientResp sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    protected ClientResp sendHttpGet(String httpUrl)  {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet);
    }

    protected abstract List<Header> headerConfig();

    public void addHeader(Header... headers) {
        Collections.addAll(this.headers, headers);
    }

    private void setPostHeader(HttpPost httpPost) {
        List<Header> headers = setHeadersAndGet();
        if (Objects.isNull(headers)) return;
        for (Header header : headers) {
            httpPost.setHeader(header);
        }
    }

    private void setGetHeader(HttpGet httpGet) {
        List<Header> headers = setHeadersAndGet();
        if (Objects.isNull(headers)) return;
        for (Header header : headers) {
            httpGet.setHeader(header);
        }
    }

    private List<Header> setHeadersAndGet() {
        if (CollectionsUtil.isNotBlank(headerConfig())) {
            this.headers.addAll(headerConfig());
        }
        return this.headers;
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private ClientResp sendHttpPost(HttpPost httpPost) {
        setPostHeader(httpPost);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ClientResp clientResp = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            clientResp = new ClientResp(responseContent, response.getStatusLine().getStatusCode(), entity.getContentLength());
        } catch (Exception exc) {
            clientResp = ClientResp.BuildException(exc);
            exc.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clientResp;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private ClientResp sendHttpGet(HttpGet httpGet) {
        setGetHeader(httpGet);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ClientResp clientResp = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            clientResp = new ClientResp(responseContent, response.getStatusLine().getStatusCode(), responseContent.length());
        } catch (Exception exc) {
            clientResp = ClientResp.BuildException(exc);
            exc.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clientResp;
    }

}