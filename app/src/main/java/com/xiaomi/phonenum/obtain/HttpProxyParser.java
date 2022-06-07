package com.xiaomi.phonenum.obtain;

import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import com.xiaomi.phonenum.utils.MapUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class HttpProxyParser extends Parser {
    private Logger a = LoggerManager.getLogger();
    private HttpFactory b;

    public HttpProxyParser(HttpFactory httpFactory) {
        this.b = httpFactory;
    }

    @Override // com.xiaomi.phonenum.obtain.Parser
    public Response parse(int i, String str) throws IOException, JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if ("http".equals(jSONObject.getString("result"))) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("httpRequest");
            return a(i, a(jSONObject2.getJSONObject("request")), jSONObject2.getString("followup"));
        } else if (this.next != null) {
            return this.next.parse(i, str);
        } else {
            throw new JSONException("result not support" + jSONObject);
        }
    }

    private Response a(int i, @NonNull Request request, @NonNull String str) throws IOException {
        return a(str, a(i, request));
    }

    private Response a(int i, @NonNull Request request) {
        try {
            Response excute = this.b.createHttpClient().excute(request);
            Logger logger = this.a;
            logger.d("HttpProxyParser", "response " + excute);
            return excute;
        } catch (IOException e) {
            this.a.e("HttpProxyParser", "request ", e);
            return HttpError.CELLULAR_NETWORK_IO_EXCEPTION.result();
        }
    }

    @NonNull
    private Response a(@NonNull String str, @NonNull Response response) throws IOException {
        HashMap hashMap = new HashMap();
        hashMap.put("requestTime", "" + response.time);
        hashMap.put("code", "" + response.code);
        hashMap.put("body", a(response.body));
        hashMap.put("headers", a(a(response.headers)));
        return this.b.createHttpClient().excute(new Request.Builder().url(str).formBody(hashMap).build());
    }

    private String a(String str) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Base64.encodeToString(str.getBytes("utf-8"), 10);
    }

    private String a(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        try {
            JSONObject joinToJson = MapUtil.joinToJson(map);
            if (joinToJson != null) {
                return joinToJson.toString().replace("\\", "");
            }
        } catch (JSONException e) {
            this.a.e("HttpProxyParser", "joinToJson", e);
        }
        return null;
    }

    private Request a(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString("url");
        boolean z = true;
        if (jSONObject.optInt("followRedirects") != 1) {
            z = false;
        }
        Map<String, String> jsonToMap = MapUtil.jsonToMap(jSONObject.optJSONObject("headers"));
        return new Request.Builder().url(string).headers(jsonToMap).formBody(MapUtil.jsonToMap(jSONObject.optJSONObject("formBody"))).followRedirects(z).build();
    }
}
