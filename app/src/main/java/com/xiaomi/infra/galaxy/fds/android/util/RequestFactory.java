package com.xiaomi.infra.galaxy.fds.android.util;

import com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import java.util.Date;
import java.util.Map;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

/* loaded from: classes3.dex */
public class RequestFactory {
    public static HttpUriRequest createRequest(String str, GalaxyFDSCredential galaxyFDSCredential, HttpMethod httpMethod, Map<String, String> map) throws GalaxyFDSClientException {
        HttpUriRequest httpUriRequest;
        String addParam = galaxyFDSCredential.addParam(str);
        switch (httpMethod) {
            case GET:
                httpUriRequest = new HttpGet(addParam);
                break;
            case PUT:
                httpUriRequest = new HttpPut(addParam);
                break;
            case POST:
                httpUriRequest = new HttpPost(addParam);
                break;
            case DELETE:
                httpUriRequest = new HttpDelete(addParam);
                break;
            case HEAD:
                httpUriRequest = new HttpHead(addParam);
                break;
            default:
                httpUriRequest = null;
                break;
        }
        if (httpUriRequest != null) {
            if (map != null) {
                map.remove("Content-Length");
                map.remove("Content-Length".toLowerCase());
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpUriRequest.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpUriRequest.addHeader("Date", Util.formatDateString(new Date()));
            galaxyFDSCredential.addHeader(httpUriRequest);
        }
        return httpUriRequest;
    }
}
