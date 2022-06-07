package com.xiaomi.phonenum.obtain;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.phonenum.Constant;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.bean.Sim;
import com.xiaomi.phonenum.http.HttpFactory;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import com.xiaomi.phonenum.utils.MapUtil;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.hapjs.features.channel.IChannel;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ObtainHandler {
    private PhoneUtil a;
    private HttpFactory b;
    private String c;
    private Context d;
    private Logger e = LoggerManager.getLogger();
    private Parser f;

    public ObtainHandler(Context context, @NonNull String str, @NonNull PhoneUtil phoneUtil, @NonNull HttpFactory httpFactory) {
        this.d = context;
        this.c = str;
        this.a = phoneUtil;
        this.b = httpFactory;
    }

    public void setParser(Parser parser) {
        Parser parser2 = this.f;
        if (parser2 == null) {
            this.f = parser;
        } else {
            parser2.setNext(parser);
        }
    }

    public PhoneNum obtainPhoneNumber(int i, PhoneLevel phoneLevel) throws IOException, PhoneException {
        a();
        a(i);
        String substring = UUID.randomUUID().toString().replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "").substring(0, 15);
        Logger logger = this.e;
        logger.i("ObtainHandler", "**traceId**:" + substring);
        Response a = a(i, substring, phoneLevel.param);
        while (!a(a)) {
            try {
                a = this.f.parse(i, a.body);
            } catch (JSONException e) {
                this.e.e("ObtainHandler", "parse response", e);
                throw new PhoneException(Error.JSON, "", e);
            }
        }
        return a(i, a.body, substring, phoneLevel);
    }

    private boolean a(Response response) throws PhoneException, IOException {
        if (response == null || response.code != 200 || response.body == null) {
            throw new IOException("getInNetTime ObtainStrategy response:" + response);
        }
        try {
            JSONObject jSONObject = new JSONObject(response.body);
            int i = jSONObject.getInt("code");
            if (i == 0) {
                return "phoneNumber".equals(jSONObject.getString("result"));
            }
            throw new PhoneException(Error.codeToError(i), jSONObject.optString(IChannel.EXTRA_ERROR_DESC));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new PhoneException(Error.JSON, "", e);
        }
    }

    private void a(int i) throws PhoneException {
        if (this.a.isSimStateReadyForSubId(i)) {
            try {
                if (!this.a.waitForServiceForSubId(i, 3000L)) {
                    throw new PhoneException(Error.SIM_NOT_READY);
                }
            } catch (InterruptedException e) {
                throw new PhoneException(Error.SIM_NOT_READY, "", e);
            }
        } else {
            throw new PhoneException(Error.SIM_NOT_READY);
        }
    }

    private void a() throws PhoneException {
        if (!this.a.checkPermission("android.permission.READ_PHONE_STATE")) {
            throw new PhoneException(Error.NO_READ_PHONE_STATE_PERMISSION);
        }
    }

    private PhoneNum a(int i, String str, String str2, PhoneLevel phoneLevel) throws IOException, PhoneException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject("phoneNumber");
            String string = jSONObject2.getString("number");
            String string2 = jSONObject2.getString("numberHash");
            String string3 = jSONObject2.getString("token");
            String string4 = jSONObject2.getString(ai.aa);
            String optString = jSONObject.optString("copywriter", null);
            return new PhoneNum.Builder().subId(i).traceId(str2).number(string).numberHash(string2).iccid(string4).token(string3).copywriter(optString).operatorLink(jSONObject.optString(AuthorizeActivityBase.KEY_OPERATORLINK, null)).isVerified(false).phoneLevel(phoneLevel.value).build();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new PhoneException(Error.JSON, "", e);
        }
    }

    private Response a(int i, String str, String str2) throws IOException {
        HashMap hashMap = new HashMap();
        Sim simForSubId = this.a.getSimForSubId(i);
        if (simForSubId != null) {
            a(hashMap, ai.aa, simForSubId.iccid);
            a(hashMap, "imsi", simForSubId.imsi);
            a(hashMap, "simMccmnc", simForSubId.mccmnc);
            a(hashMap, "line1Number", simForSubId.line1Number);
        }
        a(hashMap, "networkMccmnc", this.a.getNetworkMccMncForSubId(i));
        a(hashMap, "appId", this.c);
        a(hashMap, OneTrack.Param.IMEI_MD5, this.a.getImei());
        a(hashMap, SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, this.a.getDeviceId());
        a(hashMap, "phoneType", "" + this.a.getPhoneTypeForSubId(i));
        a(hashMap, "traceId", str);
        a(hashMap, "versionCode", "6");
        a(hashMap, "phoneLevel", str2);
        a(hashMap, "pip", b());
        a(hashMap, "packageName", this.d.getPackageName());
        String joinToQuery = MapUtil.joinToQuery(hashMap);
        Logger logger = this.e;
        logger.d("ObtainHandler", "params:" + joinToQuery);
        return this.b.createHttpClient().excute(new Request.Builder().url(Constant.OBTAIN_STRATEGY_URL).queryParams(hashMap).ua(a(this.d)).build());
    }

    private String b() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (!nextElement.getName().toLowerCase().contains("wlan")) {
                    Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if (!nextElement2.isLoopbackAddress() && (nextElement2 instanceof Inet4Address)) {
                            return nextElement2.getHostAddress();
                        }
                    }
                    continue;
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    private void a(Map<String, String> map, String str, String str2) {
        if (str != null && str2 != null) {
            map.put(str, str2);
        }
    }

    private String a(Context context) {
        return "Android/" + Build.VERSION.RELEASE + " SDK_INT/" + Build.VERSION.SDK_INT + " BRAND/" + Build.BRAND + " PRODUCT/" + Build.PRODUCT + " MODEL/" + Build.MODEL + " INCREMENTAL/" + Build.VERSION.INCREMENTAL + " APP/" + context.getPackageName();
    }
}
