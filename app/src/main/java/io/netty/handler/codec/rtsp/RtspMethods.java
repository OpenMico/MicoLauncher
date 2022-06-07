package io.netty.handler.codec.rtsp;

import com.xiaomi.micolauncher.common.build.BuildSettings;
import io.netty.handler.codec.http.HttpMethod;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class RtspMethods {
    public static final HttpMethod OPTIONS = HttpMethod.OPTIONS;
    public static final HttpMethod DESCRIBE = new HttpMethod("DESCRIBE");
    public static final HttpMethod ANNOUNCE = new HttpMethod("ANNOUNCE");
    public static final HttpMethod SETUP = new HttpMethod("SETUP");
    public static final HttpMethod PLAY = new HttpMethod(BuildSettings.PLAY);
    public static final HttpMethod PAUSE = new HttpMethod("PAUSE");
    public static final HttpMethod TEARDOWN = new HttpMethod("TEARDOWN");
    public static final HttpMethod GET_PARAMETER = new HttpMethod("GET_PARAMETER");
    public static final HttpMethod SET_PARAMETER = new HttpMethod("SET_PARAMETER");
    public static final HttpMethod REDIRECT = new HttpMethod("REDIRECT");
    public static final HttpMethod RECORD = new HttpMethod("RECORD");
    private static final Map<String, HttpMethod> a = new HashMap();

    static {
        a.put(DESCRIBE.toString(), DESCRIBE);
        a.put(ANNOUNCE.toString(), ANNOUNCE);
        a.put(GET_PARAMETER.toString(), GET_PARAMETER);
        a.put(OPTIONS.toString(), OPTIONS);
        a.put(PAUSE.toString(), PAUSE);
        a.put(PLAY.toString(), PLAY);
        a.put(RECORD.toString(), RECORD);
        a.put(REDIRECT.toString(), REDIRECT);
        a.put(SETUP.toString(), SETUP);
        a.put(SET_PARAMETER.toString(), SET_PARAMETER);
        a.put(TEARDOWN.toString(), TEARDOWN);
    }

    public static HttpMethod valueOf(String str) {
        if (str != null) {
            String upperCase = str.trim().toUpperCase();
            if (!upperCase.isEmpty()) {
                HttpMethod httpMethod = a.get(upperCase);
                return httpMethod != null ? httpMethod : new HttpMethod(upperCase);
            }
            throw new IllegalArgumentException("empty name");
        }
        throw new NullPointerException("name");
    }

    private RtspMethods() {
    }
}
