package org.fourthline.cling.model.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class UpnpRequest extends UpnpOperation {
    private Method method;
    private URI uri;

    /* loaded from: classes5.dex */
    public enum Method {
        GET("GET"),
        POST("POST"),
        NOTIFY("NOTIFY"),
        MSEARCH("M-SEARCH"),
        SUBSCRIBE("SUBSCRIBE"),
        UNSUBSCRIBE("UNSUBSCRIBE"),
        UNKNOWN("UNKNOWN");
        
        private static Map<String, Method> byName = new HashMap<String, Method>() { // from class: org.fourthline.cling.model.message.UpnpRequest.Method.1
            {
                Method[] values = Method.values();
                for (Method method : values) {
                    put(method.getHttpName(), method);
                }
            }
        };
        private String httpName;

        Method(String str) {
            this.httpName = str;
        }

        public String getHttpName() {
            return this.httpName;
        }

        public static Method getByHttpName(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            Method method = byName.get(str.toUpperCase(Locale.ROOT));
            return method != null ? method : UNKNOWN;
        }
    }

    public UpnpRequest(Method method) {
        this.method = method;
    }

    public UpnpRequest(Method method, URI uri) {
        this.method = method;
        this.uri = uri;
    }

    public UpnpRequest(Method method, URL url) {
        this.method = method;
        if (url != null) {
            try {
                this.uri = url.toURI();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public Method getMethod() {
        return this.method;
    }

    public String getHttpMethodName() {
        return this.method.getHttpName();
    }

    public URI getURI() {
        return this.uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(getHttpMethodName());
        if (getURI() != null) {
            str = StringUtils.SPACE + getURI();
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }
}
