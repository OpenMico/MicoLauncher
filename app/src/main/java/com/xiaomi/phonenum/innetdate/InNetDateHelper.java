package com.xiaomi.phonenum.innetdate;

import android.content.Context;
import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.http.UrlConnHttpFactory;
import com.xiaomi.phonenum.phone.PhoneInfoManager;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class InNetDateHelper {
    private Logger a = LoggerManager.getLogger();
    private HttpFactory b;
    private PhoneUtil c;

    public InNetDateHelper(Context context) {
        this.b = new UrlConnHttpFactory(context);
        this.c = PhoneInfoManager.getDefaultPhoneUtil(context);
    }

    public InNetDateResult getInNetDate(int i) throws IOException {
        int subIdForSlotId = this.c.getSubIdForSlotId(i);
        if (this.c.getSimForSubId(subIdForSlotId) == null) {
            this.a.i("InNetTimeHelper", "step 0 sim not ready");
            return new InNetDateResult(Error.SIM_NOT_READY);
        }
        b(subIdForSlotId);
        this.a.i("InNetTimeHelper", "step 1 login");
        String a = a(subIdForSlotId);
        Logger logger = this.a;
        logger.i("InNetTimeHelper", "step 2 get phone " + a);
        if (TextUtils.isEmpty(a)) {
            return new InNetDateResult(Error.UNKNOW);
        }
        long a2 = a(subIdForSlotId, a);
        Logger logger2 = this.a;
        logger2.i("InNetTimeHelper", "step 3 get inNetDate:" + a2);
        if (a2 == 0) {
            return new InNetDateResult(Error.UNKNOW);
        }
        return new InNetDateResult(a2, a);
    }

    private long a(int i, String str) throws IOException {
        Request.Builder builder = new Request.Builder();
        Response excute = this.b.createDataHttpClient(i).excute(builder.url("http://touch.10086.cn/i/v1/cust/info/" + str + "?channel=02&time=" + c()).build());
        Logger logger = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append("getInNetDate response:");
        sb.append(excute);
        logger.d("InNetTimeHelper", sb.toString());
        try {
            return a(new JSONObject(excute.body).getJSONObject("data").getString("inNetDate"));
        } catch (JSONException e) {
            this.a.e("InNetTimeHelper", "JSONException:", e);
            return 0L;
        }
    }

    private String a(int i) throws IOException {
        Request.Builder builder = new Request.Builder();
        Response excute = this.b.createDataHttpClient(i).excute(builder.url("http://touch.10086.cn/i/v1/auth/loginfo?channel=02&time=" + c()).headers(b()).build());
        Logger logger = this.a;
        logger.d("InNetTimeHelper", "getPhoneNum response:" + excute);
        try {
            return new JSONObject(excute.body).getJSONObject("data").getString("loginValue");
        } catch (JSONException e) {
            this.a.e("InNetTimeHelper", "JSONException:", e);
            return null;
        }
    }

    private void b(int i) throws IOException {
        Response excute = this.b.createDataHttpClient(i).excute(new Request.Builder().url("http://touch.10086.cn/i/v1/auth/getFreeAuthArtifact").appendQuery("backUrl=http%3A%2F%2Ftouch.10086.cn%2Fi%2Fmobile%2Fcustinfoqry.html").headers(a()).build());
        if (excute.location != null) {
            Logger logger = this.a;
            logger.d("InNetTimeHelper", "login location:" + excute.location);
            excute = this.b.createDataHttpClient(i).excute(new Request.Builder().url(excute.location).build());
        }
        Logger logger2 = this.a;
        logger2.d("InNetTimeHelper", "login response:" + excute);
    }

    private Map<String, String> a() {
        HashMap hashMap = new HashMap();
        hashMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        hashMap.put("Accept-Encoding", "gzip, deflate, sdch");
        hashMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        hashMap.put("Connection", "keep-alive");
        hashMap.put("Upgrade-Insecure-Requests", "1");
        hashMap.put("Referer", "http://touch.10086.cn/i/mobile/custinfoqry.html");
        return hashMap;
    }

    private Map<String, String> b() {
        HashMap hashMap = new HashMap();
        hashMap.put("connection", "Keep-Alive");
        hashMap.put("Cache-Control", "no-store, must-revalidate");
        hashMap.put("pragma", "no-cache");
        hashMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
        hashMap.put(HttpHeaders.X_REQUESTED_WITH, "XMLHttpRequest");
        hashMap.put("If-Modified-Since", "0e");
        hashMap.put("expires", "0");
        hashMap.put("Referer", "http://touch.10086.cn/i/mobile/custinfoqry.html");
        hashMap.put("Accept-Encoding", "gzip, deflate, sdch");
        hashMap.put("Accept-Language", "Keep-Alive");
        hashMap.put("connection", "zh-CN,zh;q=0.8");
        return hashMap;
    }

    private String c() {
        return new SimpleDateFormat("yyyyMdHHmmssS").format(new Date());
    }

    private long a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            Date parse = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
            if (parse != null) {
                return parse.getTime();
            }
        } catch (ParseException e) {
            this.a.e("InNetTimeHelper", "ParseException", e);
        }
        return 0L;
    }
}
