package site.yan.httpclient.builder;

import org.apache.http.Header;
import site.yan.httpclient.client.AbstractHttpClient;
import site.yan.httpclient.client.TSHttpClientIN;
import site.yan.httpclient.client.TSHttpClientOUT;

import java.util.function.Function;

public class HttpClientBuilder {

    private AbstractHttpClient httpClient;

    private HttpClientBuilder() {
        // Nothing to do.
    }

    public static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }

    public HttpClientBuilder targetLocation(TargetLocationType type) {
        this.httpClient =  new TSHttpClientOUT();
        return this;
    }

    public HttpClientBuilder setHeader(Header... headers) {
        this.httpClient.addHeader(headers);
        return this;
    }

    public AbstractHttpClient build() {
        return this.httpClient;
    }
}
