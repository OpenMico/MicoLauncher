package com.xiaomi.micolauncher.api;

import android.content.Context;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.account.sdk.ServiceToken;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.model.Admin;
import com.xiaomi.micolauncher.api.model.Miot;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.smarthome.core.entity.net.KeyValuePair;
import com.xiaomi.smarthome.core.entity.net.NetError;
import com.xiaomi.smarthome.core.entity.net.NetRequest;
import com.xiaomi.smarthome.core.entity.net.NetResult;
import com.xiaomi.smarthome.core.server.internal.NetCallback;
import com.xiaomi.smarthome.core.server.internal.account.AccountCallBack;
import com.xiaomi.smarthome.core.server.internal.account.AccountManager;
import com.xiaomi.smarthome.core.server.internal.account.NetApiConfig;
import com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api;
import com.xiaomi.smarthome.setting.LoginSetting;
import com.xiaomi.smarthome.setting.ServerSetting;
import com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SmartHomeRc4Manager implements AccountCallBack {
    private static SmartHomeRc4Manager b = null;
    private static String c = "xiaomiio";
    private static String d = "/homeroom/getroom";
    private static String e = "/homeroom/gethome";
    private static String f = "/home/device_list";
    private static String g = "/homeroom/bind_device_to_room";
    private static String h = "/v2/voicectrl/ai_dev";
    private Logger a = XLog.tag("MICO.SmartHome").build();
    private boolean i;
    private String j;

    private SmartHomeRc4Manager() {
    }

    public static SmartHomeRc4Manager getInstance() {
        if (b == null) {
            synchronized (SmartHomeRc4Manager.class) {
                if (b == null) {
                    b = new SmartHomeRc4Manager();
                }
            }
        }
        return b;
    }

    public void setTokenInfo(String str, String str2, String str3, String str4, String str5, long j) {
        NetApiConfig.Builder local = new NetApiConfig.Builder().init(MicoApplication.getGlobalContext().getApplicationContext()).setIsDebug(true).setServer(ServerSetting.SERVER_CN).setServerEnv("release").setLocal(MicoApplication.getGlobalContext().getResources().getConfiguration().locale);
        String str6 = c;
        MiServiceTokenInfo miServiceTokenInfo = new MiServiceTokenInfo(str, str6, str2, str4, str5, LoginSetting.getDomainBySid(str6), j);
        AccountManager.init(local.build());
        AccountManager.getInstance().setMiServiceToken(miServiceTokenInfo);
        AccountManager.getInstance().setAccountCallBack(this);
        this.i = true;
    }

    public Observable<String> refreshServiceToken() {
        return TokenManager.getInstance().refreshServiceToken(c);
    }

    @Override // com.xiaomi.smarthome.core.server.internal.account.AccountCallBack
    public void doUnAuthorized(int i) {
        L.miot.i("SmartHomeRc4Manager doUnAuthorized code=%d", Integer.valueOf(i));
        TokenManager.getInstance().removeServiceInfo(c);
        TokenManager.getInstance().refreshServiceToken(c);
        this.i = false;
    }

    public Observable<Optional<Miot.Room>> getRoomInfo() {
        this.j = "";
        return ApiManager.minaService.getDeviceList().doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$cnnWD2EvOWjhffGwMlnADMwQ-9U
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SmartHomeRc4Manager.this.e((List) obj);
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$4cuwu5tml0UuuEgPdlEqSEjzhOA
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource d2;
                d2 = SmartHomeRc4Manager.this.d((List) obj);
                return d2;
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$5-5Zs75qIZYYmEoLyNpNg5-D8k8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SmartHomeRc4Manager.this.h((String) obj);
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$DfjeAuY27Z-FSJsYjhqNu8DXfF0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource g2;
                g2 = SmartHomeRc4Manager.this.g((String) obj);
                return g2;
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$7N-_H1w9Ig4zd-HfDCJoJWXrGuc
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                Optional f2;
                f2 = SmartHomeRc4Manager.this.f((String) obj);
                return f2;
            }
        });
    }

    public /* synthetic */ ObservableSource d(List list) throws Exception {
        return refreshServiceToken();
    }

    public /* synthetic */ void h(String str) throws Exception {
        a();
    }

    public /* synthetic */ ObservableSource g(String str) throws Exception {
        return getHomeInfo();
    }

    public Observable<Miot.Home> getCurrentDeviceHomeInfo() {
        return ApiManager.minaService.getDeviceList().doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$f-vYDrxT9LrDOB_ZrhAYgU5-MPs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SmartHomeRc4Manager.this.c((List) obj);
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$cwC0h-SQU2S4g621tvUz6jmhO3Q
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b2;
                b2 = SmartHomeRc4Manager.this.b((List) obj);
                return b2;
            }
        }).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$e1ICb85VHBUSyG0xYCyqaoyLCCw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SmartHomeRc4Manager.this.e((String) obj);
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$WDlcyy8TlTELqkbr3Xxbd_FGp-A
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource d2;
                d2 = SmartHomeRc4Manager.this.d((String) obj);
                return d2;
            }
        }).map(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$GMc3JDHRym6hIydtyiEhjnMa64A
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                Miot.Home c2;
                c2 = SmartHomeRc4Manager.this.c((String) obj);
                return c2;
            }
        });
    }

    public /* synthetic */ ObservableSource b(List list) throws Exception {
        return refreshServiceToken();
    }

    public /* synthetic */ void e(String str) throws Exception {
        a();
    }

    public /* synthetic */ ObservableSource d(String str) throws Exception {
        return getHomeInfo();
    }

    /* renamed from: a */
    public Miot.Home c(String str) {
        if (str == null) {
            return null;
        }
        for (Miot.Home home : ((Miot.HomeResult) Gsons.getGson().fromJson(str, (Class<Object>) Miot.HomeResult.class)).result.homelist) {
            for (String str2 : home.dids) {
                if (str2.equals(this.j)) {
                    return home;
                }
            }
            for (Miot.Room room : home.roomlist) {
                for (String str3 : room.dids) {
                    if (str3.equals(this.j)) {
                        return home;
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: b */
    public Optional<Miot.Room> f(String str) {
        Miot.Room room = null;
        if (str != null) {
            for (Miot.Home home : ((Miot.HomeResult) Gsons.getGson().fromJson(str, (Class<Object>) Miot.HomeResult.class)).result.homelist) {
                for (Miot.Room room2 : home.roomlist) {
                    for (String str2 : room2.dids) {
                        if (str2.equals(this.j)) {
                            room = room2;
                        }
                    }
                }
            }
        }
        return Optional.of(room);
    }

    /* renamed from: a */
    public void e(List<Admin.Mico> list) {
        String deviceID = SystemSetting.getDeviceID();
        if (deviceID != null) {
            for (Admin.Mico mico : list) {
                if (mico.deviceID.equals(deviceID)) {
                    this.j = mico.miotDID;
                }
            }
        }
    }

    private void a() {
        ServiceToken serviceInfo = TokenManager.getInstance().getServiceInfo(c);
        if (serviceInfo != null) {
            setTokenInfo(TokenManager.getInstance().getUserId(), TokenManager.getInstance().getCUserId(), serviceInfo.getSid(), serviceInfo.getServiceToken(), serviceInfo.getSsecurity(), -5L);
        }
    }

    public Observable<String> getHomeInfo() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$om50-mdFhg6YOLXR6M9iGeWA4bA
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SmartHomeRc4Manager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final ObservableEmitter observableEmitter) throws Exception {
        SmartHomeRc4Api.getInstance().sendRequest(new NetRequest.Builder().method("GET").path(e).build(), new NetCallback<NetResult, NetError>() { // from class: com.xiaomi.micolauncher.api.SmartHomeRc4Manager.1
            /* renamed from: a */
            public void onCache(NetResult netResult) {
            }

            /* renamed from: b */
            public void onSuccess(NetResult netResult) {
                if (!observableEmitter.isDisposed()) {
                    if (netResult == null || netResult.mCode != 0) {
                        observableEmitter.onError(new IllegalStateException("SmartHomeRc4Api result not 0"));
                        return;
                    }
                    observableEmitter.onNext(netResult.mResponse);
                    observableEmitter.onComplete();
                }
            }

            /* renamed from: a */
            public void onFailure(NetError netError) {
                observableEmitter.onError(new IllegalStateException("on failure"));
            }
        });
    }

    public void test(Context context) {
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject();
        try {
            String bssid = WiFiUtil.getBSSID(context.getApplicationContext());
            String ssid = WiFiUtil.getSSID(context.getApplicationContext());
            if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(bssid) && !TextUtils.equals(bssid, "02:00:00:00:00:00")) {
                jSONObject.put("ssid", ssid);
                jSONObject.put("bssid", bssid.toUpperCase());
            }
            jSONObject.put("getVirtualModel", true);
            jSONObject.put("getHuamiDevices", 1);
        } catch (JSONException unused) {
        }
        arrayList.add(new KeyValuePair("data", jSONObject.toString()));
        SmartHomeRc4Api.getInstance().sendRequest(new NetRequest.Builder().method("POST").path("/home/device_list").addQueries(arrayList).build(), new NetCallback<NetResult, NetError>() { // from class: com.xiaomi.micolauncher.api.SmartHomeRc4Manager.2
            /* renamed from: a */
            public void onCache(NetResult netResult) {
            }

            /* renamed from: b */
            public void onSuccess(NetResult netResult) {
                SmartHomeRc4Manager.this.a.e("onSuccess");
            }

            /* renamed from: a */
            public void onFailure(NetError netError) {
                Logger logger = SmartHomeRc4Manager.this.a;
                logger.e("onFailure:" + netError.getDetail());
            }
        });
    }

    public Observable<String> bindDeviceToRome(final String str, final String str2) {
        return Observable.unsafeCreate(new ObservableSource() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$jiJCogZdpeA4NEmEdrQhBOUafMo
            @Override // io.reactivex.ObservableSource
            public final void subscribe(Observer observer) {
                SmartHomeRc4Manager.this.a(str, str2, observer);
            }
        });
    }

    public /* synthetic */ void a(String str, String str2, final Observer observer) {
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            jSONObject.put("did", str2);
        } catch (JSONException unused) {
        }
        arrayList.add(new KeyValuePair("data", jSONObject.toString()));
        L.miot.i("bindDeviceToRome called id %s, did %s", str, str2);
        SmartHomeRc4Api.getInstance().sendRequest(new NetRequest.Builder().method("POST").path(g).addQueries(arrayList).build(), new NetCallback<NetResult, NetError>() { // from class: com.xiaomi.micolauncher.api.SmartHomeRc4Manager.3
            /* renamed from: a */
            public void onCache(NetResult netResult) {
            }

            /* renamed from: b */
            public void onSuccess(NetResult netResult) {
                if (netResult == null || netResult.mCode != 0) {
                    observer.onError(new IllegalStateException("SmartHomeRc4Api result not 0"));
                    return;
                }
                observer.onNext(netResult.mResponse);
                observer.onComplete();
            }

            /* renamed from: a */
            public void onFailure(NetError netError) {
                observer.onError(new IllegalStateException("on failure"));
            }
        });
    }

    public Observable<String> getVoiceCtrlTips(final String str, final String str2, final int i, final String str3) {
        return Observable.unsafeCreate(new ObservableSource() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$SmartHomeRc4Manager$8QK4A2ktkpZMaGNVWoiKFOOJEwY
            @Override // io.reactivex.ObservableSource
            public final void subscribe(Observer observer) {
                SmartHomeRc4Manager.this.a(str, str2, i, str3, observer);
            }
        });
    }

    public /* synthetic */ void a(String str, String str2, int i, String str3, final Observer observer) {
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("voice_id", str);
            jSONObject.put("control_id", str2);
            jSONObject.put(SchemaActivity.KEY_ENV, i);
            jSONObject.put("tip_type", str3);
        } catch (JSONException unused) {
        }
        arrayList.add(new KeyValuePair("data", jSONObject.toString()));
        L.miot.i("getVoiceCtrlTips called voice_id %s, control_id %s env %d tipType %s", str, str2, Integer.valueOf(i), str3);
        SmartHomeRc4Api.getInstance().sendRequest(new NetRequest.Builder().method("POST").path(h).addQueries(arrayList).build(), new NetCallback<NetResult, NetError>() { // from class: com.xiaomi.micolauncher.api.SmartHomeRc4Manager.4
            /* renamed from: a */
            public void onCache(NetResult netResult) {
            }

            /* renamed from: b */
            public void onSuccess(NetResult netResult) {
                if (netResult == null || netResult.mCode != 0) {
                    observer.onError(new IllegalStateException("SmartHomeRc4Api result not 0"));
                    return;
                }
                observer.onNext(netResult.mResponse);
                observer.onComplete();
            }

            /* renamed from: a */
            public void onFailure(NetError netError) {
                observer.onError(new IllegalStateException("on failure"));
            }
        });
    }
}
