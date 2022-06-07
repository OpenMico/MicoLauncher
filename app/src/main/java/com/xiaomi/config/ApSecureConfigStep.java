package com.xiaomi.config;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.renderscript.ScriptIntrinsicBLAS;
import com.xiaomi.config.ECCurve;
import com.xiaomi.config.utils.ECCPointConvert;
import com.xiaomi.config.utils.SecurityChipUtil;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.miio.MiioLocalAPI;
import com.xiaomi.miio.MiioLocalRpcResponse;
import com.xiaomi.miot.host.lan.impl.MiotStore;
import com.xiaomi.onetrack.OneTrack;
import java.security.interfaces.ECPublicKey;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ApSecureConfigStep {
    public static final int MSG_CHECK_SUPPORT_ECDH = 129;
    public static final int MSG_CHOOSE_ECDH = 130;
    public static final int MSG_GET_MODEL = 127;
    public static final int MSG_GET_TOKEN = 128;
    public static final int MSG_NORMAL_SEND_WIFI = 132;
    public static final int MSG_OOB_STEP_ONE = 133;
    public static final int MSG_OOB_STEP_THREE = 135;
    public static final int MSG_OOB_STEP_TWO = 134;
    protected static final int TIMEOUT = 5000;
    private static int l = -1;
    private static Handler o;
    private static ApSecureConfigStep p;
    String a;
    byte[] b;
    private String c;
    private String d;
    private String e;
    private String f;
    private long g;
    private String h;
    private String i;
    private String j;
    private String k;
    private GetPincodeHander m;
    protected long mDid;
    protected String mModel;
    protected String mToken;
    private ConfigCompletedHandler n;
    private int q = 0;

    /* loaded from: classes3.dex */
    public interface ConfigCompletedHandler {
        void onSuccess(long j);

        void onfailed(int i, String str);
    }

    /* loaded from: classes3.dex */
    public interface GetModelHandler {
        void onSuccess(String str);

        void onfailed(int i, String str);
    }

    /* loaded from: classes3.dex */
    public interface GetPincodeHander {
        String getPincodeFromUser();
    }

    private ApSecureConfigStep() {
    }

    public static ApSecureConfigStep getInstance() {
        if (p == null) {
            synchronized (ApSecureConfigStep.class) {
                if (p == null) {
                    p = new ApSecureConfigStep();
                }
            }
        }
        return p;
    }

    public void setConfig(String str, String str2, String str3, String str4, long j, String str5, String str6, String str7) {
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = j;
        this.mModel = "";
        this.h = str5;
        this.i = str6;
        this.j = str7;
        synchronized (ApSecureConfigStep.class) {
            if (o == null) {
                o = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.config.ApSecureConfigStep.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        if (ApSecureConfigStep.this.b() != null) {
                            ApSecureConfigStep.this.b().removeMessages(message.what);
                        }
                        switch (message.what) {
                            case 128:
                                ApSecureConfigStep.this.d();
                                return;
                            case 129:
                                ApSecureConfigStep.this.e();
                                return;
                            case 130:
                                ApSecureConfigStep.this.f();
                                return;
                            case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
                            default:
                                super.handleMessage(message);
                                return;
                            case 132:
                                ApSecureConfigStep.this.h();
                                return;
                        }
                    }
                };
            }
        }
    }

    public void destroy() {
        Handler handler = o;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public final int generateNonce() {
        l++;
        if (l <= 0) {
            l = new Random().nextInt(10000) + 1;
        }
        return l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Handler b() {
        return o;
    }

    public void start(GetPincodeHander getPincodeHander, ConfigCompletedHandler configCompletedHandler) {
        this.m = getPincodeHander;
        this.n = configCompletedHandler;
        if (b() != null) {
            b().sendEmptyMessage(128);
        }
    }

    public void getModel(final GetModelHandler getModelHandler) {
        if (TextUtils.isEmpty(this.mModel)) {
            MiioLocalAPI.async_get_token(this.c, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.5
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                    if (parseRpcResponse == null) {
                        GetModelHandler getModelHandler2 = getModelHandler;
                        if (getModelHandler2 != null) {
                            getModelHandler2.onfailed(128, str);
                            return;
                        }
                        return;
                    }
                    ApSecureConfigStep.this.mDid = Long.valueOf(parseRpcResponse.optString("did")).longValue();
                    ApSecureConfigStep.this.mToken = parseRpcResponse.optString("token");
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("id", ApSecureConfigStep.this.generateNonce());
                        jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", 1);
                        jSONObject.put("params", jSONObject2);
                        String jSONObject3 = jSONObject.toString();
                        Log.i("ApSecureConfigStep", "get model:" + jSONObject3);
                        MiioLocalAPI.async_rpc(ApSecureConfigStep.this.c, jSONObject3, ApSecureConfigStep.this.mDid, ApSecureConfigStep.this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.5.1
                            @Override // com.xiaomi.miio.MiioLocalRpcResponse
                            public void onResponse(String str2) {
                                JSONObject parseRpcResponse2 = ApSecureConfigStep.parseRpcResponse(str2);
                                if (parseRpcResponse2 != null) {
                                    Log.i("ApSecureConfigStep", "getModel result:" + parseRpcResponse2);
                                    JSONObject optJSONObject = parseRpcResponse2.optJSONObject("info");
                                    ApSecureConfigStep.this.mModel = optJSONObject.optString("model");
                                    if (getModelHandler != null) {
                                        getModelHandler.onSuccess(ApSecureConfigStep.this.mModel);
                                    }
                                } else if (getModelHandler != null) {
                                    getModelHandler.onfailed(127, str2);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 3);
        } else if (getModelHandler != null) {
            getModelHandler.onSuccess(this.mModel);
        }
    }

    private JSONObject c() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("bind_key", this.d);
            jSONObject.put("config_type", this.h);
            if (!TextUtils.isEmpty(this.i)) {
                jSONObject.put(MiotStore.COUNTRYDOMAIN, this.i);
            }
            TimeZone timeZone = new GregorianCalendar().getTimeZone();
            jSONObject.put(OneTrack.Param.TZ, timeZone.getID());
            jSONObject.put("gmt_offset", (int) TimeUnit.SECONDS.convert(timeZone.getRawOffset(), TimeUnit.MILLISECONDS));
            jSONObject.put("passwd", this.f);
            jSONObject.put("ssid", this.e);
            jSONObject.put(OneTrack.Param.UID, this.g);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("cc", this.j);
            jSONObject.put("wifi_config", jSONObject2);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        MiioLocalAPI.async_get_token(this.c, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.6
            @Override // com.xiaomi.miio.MiioLocalRpcResponse
            public void onResponse(String str) {
                JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                if (parseRpcResponse != null) {
                    ApSecureConfigStep.this.mDid = Long.valueOf(parseRpcResponse.optString("did")).longValue();
                    ApSecureConfigStep.this.mToken = parseRpcResponse.optString("token");
                    ApSecureConfigStep.this.b().sendEmptyMessage(130);
                } else if (ApSecureConfigStep.this.n != null) {
                    ApSecureConfigStep.this.n.onfailed(128, str);
                }
            }
        }, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.info");
            MiioLocalAPI.async_rpc(this.c, jSONObject.toString(), this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.7
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                    if (parseRpcResponse != null) {
                        Log.i("ApSecureConfigStep", "checkSupportECDH onSuccess:" + parseRpcResponse);
                        String optString = parseRpcResponse.optString("miio_ver");
                        if (!TextUtils.isEmpty(optString)) {
                            String[] split = optString.split("\\.");
                            if (split.length < 3) {
                                return;
                            }
                            if (Integer.valueOf(split[2]).intValue() >= 2 || Integer.valueOf(split[1]).intValue() > 0 || Integer.valueOf(split[0]).intValue() > 0) {
                                ApSecureConfigStep.this.b().sendEmptyMessage(130);
                                return;
                            }
                            return;
                        }
                        ApSecureConfigStep.this.b().sendEmptyMessageDelayed(132, 1000L);
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(129, str);
                    }
                }
            });
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", "checkSupportECDH onFail:" + Log.getStackTraceString(e));
            b().sendEmptyMessageDelayed(132, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.q++;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 1);
            jSONObject.put("params", jSONObject2);
            String jSONObject3 = jSONObject.toString();
            Log.i("ApSecureConfigStep", "chooseECDH0:" + jSONObject3);
            MiioLocalAPI.async_rpc(this.c, jSONObject3, this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.8
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                    if (parseRpcResponse == null) {
                        Log.e("ApSecureConfigStep", "chooseECDH0, send normal wifi!");
                        ApSecureConfigStep.this.b().sendEmptyMessageDelayed(132, 1000L);
                        return;
                    }
                    Log.i("ApSecureConfigStep", "chooseECDH0 result:" + parseRpcResponse);
                    JSONObject optJSONObject = parseRpcResponse.optJSONObject("ecdh");
                    JSONArray optJSONArray = optJSONObject.optJSONArray("curve_suites");
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("sign_suites");
                    ECCurve eCCurve = null;
                    for (int i = 0; i < optJSONArray.length() && (eCCurve = ECCurve.search(optJSONArray.optInt(i))) == null; i++) {
                    }
                    int i2 = 0;
                    for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                        i2 = optJSONArray2.optInt(i3);
                        if (ECCurve.SignType.index(i2) != null) {
                            break;
                        }
                    }
                    if (!(eCCurve == null || i2 == 0)) {
                        JSONObject optJSONObject2 = parseRpcResponse.optJSONObject("oob");
                        if (optJSONObject2 != null) {
                            JSONArray optJSONArray3 = optJSONObject2.optJSONArray("modes");
                            JSONArray optJSONArray4 = optJSONObject2.optJSONArray("extents");
                            ApSecureConfigStep.this.a(eCCurve, i2, (optJSONArray3 == null || optJSONArray3.length() <= 0) ? optJSONObject2.optInt("modes") : optJSONArray3.optInt(0), (optJSONArray4 == null || optJSONArray4.length() <= 0) ? optJSONObject2.optInt("extents") : optJSONArray4.optInt(0));
                            return;
                        }
                        ApSecureConfigStep.this.a(eCCurve, i2, 0, 0);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        b().sendEmptyMessageDelayed(130, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final ECCurve eCCurve, final int i, final int i2, int i3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("curve_suite", eCCurve.getIndex());
            jSONObject3.put("sign_suite", i);
            jSONObject3.put("public_key", Base64.encodeToString(ECCPointConvert.publicKeyToBytes((ECPublicKey) eCCurve.generateKeyPair().getPublic()), 2));
            jSONObject2.put("ecdh", jSONObject3);
            jSONObject.put("params", jSONObject2);
            String jSONObject4 = jSONObject.toString();
            Log.i("ApSecureConfigStep", "chooseECDH:" + jSONObject4);
            MiioLocalAPI.async_rpc(this.c, jSONObject4, this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.9
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str) {
                    final JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                    if (parseRpcResponse != null) {
                        ApSecureConfigStep.o.post(new Runnable() { // from class: com.xiaomi.config.ApSecureConfigStep.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Log.i("ApSecureConfigStep", "chooseECDH result:" + parseRpcResponse);
                                JSONObject optJSONObject = parseRpcResponse.optJSONObject("ecdh");
                                String str2 = "";
                                String str3 = "";
                                if (optJSONObject != null) {
                                    str2 = optJSONObject.optString("public_key");
                                    str3 = optJSONObject.optString("sign");
                                }
                                byte[] verify = eCCurve.verify(ECCurve.SignType.index(i), ApSecureConfigStep.this.mToken, Base64.decode(str2, 2), Base64.decode(str3, 2));
                                if (verify == null) {
                                    ApSecureConfigStep.this.g();
                                } else if (i2 > 0) {
                                    switch (i2) {
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4:
                                            ApSecureConfigStep.this.b = verify;
                                            ApSecureConfigStep.this.i();
                                            return;
                                        default:
                                            return;
                                    }
                                } else {
                                    ApSecureConfigStep.this.a(verify, (String) null);
                                }
                            }
                        });
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(130, str);
                    }
                }
            }, 5000);
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.config_router");
            jSONObject.put("params", c());
        } catch (JSONException e) {
            Log.i("ApSecureConfigStep", "makeNormalWifiJson error:" + Log.getStackTraceString(e));
        }
        String jSONObject2 = jSONObject.toString();
        Log.i("ApSecureConfigStep", "Enter Normal wifi config:" + jSONObject2);
        MiioLocalAPI.async_rpc(this.c, jSONObject2, this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.10
            @Override // com.xiaomi.miio.MiioLocalRpcResponse
            public void onResponse(String str) {
                if (ApSecureConfigStep.parseRpcResponse(str) != null) {
                    Log.i("ApSecureConfigStep", "sendWifi, onSuccess");
                    if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onSuccess(ApSecureConfigStep.this.mDid);
                    }
                } else if (ApSecureConfigStep.this.n != null) {
                    ApSecureConfigStep.this.n.onfailed(130, str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            byte[] bArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            if (!TextUtils.isEmpty(str)) {
                bArr2 = Base64.decode(str, 2);
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(1, secretKeySpec, new IvParameterSpec(bArr2));
            byte[] doFinal = instance.doFinal(c().toString().getBytes());
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.config_router_safe");
            JSONObject jSONObject2 = new JSONObject();
            String encodeToString = Base64.encodeToString(doFinal, 2);
            jSONObject2.put("data", encodeToString);
            Log.i("ApSecureConfigStep", "sendECDHWifi, datastr= " + encodeToString);
            String encodeToString2 = Base64.encodeToString(SecurityChipUtil.sha256HMAC(bArr, doFinal), 2);
            jSONObject2.put("sign", encodeToString2);
            Log.i("ApSecureConfigStep", "sendECDHWifi, signStr= " + encodeToString2);
            jSONObject.put("params", jSONObject2);
            MiioLocalAPI.async_rpc(this.c, jSONObject.toString(), this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.11
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str2) {
                    if (ApSecureConfigStep.parseRpcResponse(str2) != null) {
                        Log.i("ApSecureConfigStep", "sendECDHWifi, onSuccess");
                        if (ApSecureConfigStep.this.n != null) {
                            ApSecureConfigStep.this.n.onSuccess(ApSecureConfigStep.this.mDid);
                        }
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(130, str2);
                    }
                }
            }, 5000);
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 3);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("step", 1);
            jSONObject2.put("oob", jSONObject3);
            jSONObject.put("params", jSONObject2);
            MiioLocalAPI.async_rpc(this.c, jSONObject.toString(), this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.12
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str);
                    if (parseRpcResponse != null) {
                        Log.i("ApSecureConfigStep", "oobGetDevSign, response= " + parseRpcResponse);
                        ApSecureConfigStep.this.k = parseRpcResponse.optJSONObject("oob").optString("sign");
                        if (ApSecureConfigStep.this.m != null) {
                            ApSecureConfigStep.this.a(ApSecureConfigStep.this.m.getPincodeFromUser());
                        } else if (ApSecureConfigStep.this.n != null) {
                            ApSecureConfigStep.this.n.onfailed(133, "mGetPincodeHander can not be null!");
                        }
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(133, str);
                    }
                }
            }, 5000);
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        final byte[] bytes = str.getBytes();
        final byte[] bArr = new byte[16];
        new Random().nextBytes(bArr);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 3);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("step", 2);
            jSONObject3.put("sign", Base64.encodeToString(a(bytes, bArr), 2));
            jSONObject2.put("oob", jSONObject3);
            jSONObject.put("params", jSONObject2);
            String jSONObject4 = jSONObject.toString();
            Log.i("ApSecureConfigStep", "oobGetRandomDev, gatewayAddr:" + this.c + " request= " + jSONObject4 + "  pincode:" + str);
            MiioLocalAPI.async_rpc(this.c, jSONObject4, this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.2
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str2) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str2);
                    if (parseRpcResponse != null) {
                        String encodeToString = Base64.encodeToString(ApSecureConfigStep.this.a(bytes, Base64.decode(parseRpcResponse.optJSONObject("oob").optString(LoopType.RANDOM), 2)), 2);
                        Log.i("ApSecureConfigStep", "oobGetRandomDev, response= " + parseRpcResponse + "  deviceSign:" + ApSecureConfigStep.this.k + "  appSign:" + encodeToString);
                        if (TextUtils.equals(ApSecureConfigStep.this.k, encodeToString)) {
                            ApSecureConfigStep.this.b(Base64.encodeToString(bArr, 2));
                        } else if (ApSecureConfigStep.this.n != null) {
                            ApSecureConfigStep.this.n.onfailed(134, "app sign and device sign are not same!");
                        }
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(134, str2);
                    }
                }
            }, 5000);
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr2.length + bArr.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
        return SecurityChipUtil.sha256HMAC(this.b, bArr3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", generateNonce());
            jSONObject.put(SchemaActivity.KEY_METHOD, "miIO.handshake");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", 3);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("step", 3);
            jSONObject3.put(LoopType.RANDOM, str);
            jSONObject2.put("oob", jSONObject3);
            jSONObject.put("params", jSONObject2);
            String jSONObject4 = jSONObject.toString();
            Log.i("ApSecureConfigStep", "oobGetIV, gatewayAddr:" + this.c + " request= " + jSONObject4);
            MiioLocalAPI.async_rpc(this.c, jSONObject4, this.mDid, this.mToken, new MiioLocalRpcResponse() { // from class: com.xiaomi.config.ApSecureConfigStep.3
                @Override // com.xiaomi.miio.MiioLocalRpcResponse
                public void onResponse(String str2) {
                    JSONObject parseRpcResponse = ApSecureConfigStep.parseRpcResponse(str2);
                    if (parseRpcResponse != null) {
                        Log.i("ApSecureConfigStep", "oobGetIV, response= " + parseRpcResponse);
                        ApSecureConfigStep.this.a = parseRpcResponse.optJSONObject("oob").optString("iv");
                        ApSecureConfigStep apSecureConfigStep = ApSecureConfigStep.this;
                        apSecureConfigStep.a(apSecureConfigStep.b, ApSecureConfigStep.this.a);
                    } else if (ApSecureConfigStep.this.n != null) {
                        ApSecureConfigStep.this.n.onfailed(135, str2);
                    }
                }
            }, 5000);
        } catch (Exception e) {
            Log.i("ApSecureConfigStep", Log.getStackTraceString(e));
        }
    }

    public static JSONObject parseRpcResponse(String str) {
        JSONArray optJSONArray;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (AnonymousClass4.a[ErrorCode.valueof(jSONObject.optInt("code")).ordinal()] != 1) {
                return null;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject == null && (optJSONArray = jSONObject.optJSONArray("result")) != null) {
                optJSONObject = new JSONObject();
                optJSONObject.put("result", optJSONArray);
            }
            return optJSONObject == null ? jSONObject : optJSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.config.ApSecureConfigStep$4  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[ErrorCode.values().length];

        static {
            try {
                a[ErrorCode.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }
}
