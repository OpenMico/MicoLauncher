package com.xiaomi.micolauncher.module.miot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.event.MiotConnectedEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.StatKey;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.OtDefs;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeMode;
import com.xiaomi.miot.host.manager.MiotHostHelper;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.host.service.listener.ITransparentListener;
import com.xiaomi.miot.support.DoorbellPdidsCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.AccessTokenListener;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler;
import com.xiaomi.miot.typedef.listener.MiotConnectedListener;
import com.xiaomi.miot.typedef.listener.SyncTimeListener;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

/* loaded from: classes3.dex */
public class MiotDeviceManager implements Handler.Callback {
    public static final String SE_ELEMENT_SERVICE = "miot_secure_element";
    public static final String SE_HARDWARE_FEATURE = "android.hardware.miotse";
    private static final long a = TimeUnit.SECONDS.toMillis(600);
    private static final long b = TimeUnit.SECONDS.toMillis(3);
    private static final long c = TimeUnit.DAYS.toMillis(2);
    private static final int d = (int) TimeUnit.SECONDS.toMillis(10);
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MiotDeviceManager e;
    private final Context f;
    private volatile int g;
    private volatile MiotDevice k;
    private MiotAccessToken l;
    private int m;
    private volatile boolean n;
    private volatile boolean o;
    private OnStartDeviceListener s;
    private List<String> w;
    private ITransparentListener.Stub x;
    private String y;
    private long z;
    private volatile boolean p = false;
    private volatile boolean q = false;
    private volatile boolean r = false;
    private int t = 0;
    private long u = 0;
    private boolean v = true;
    private long A = 60000;
    private Handler j = new MicoHandler(ThreadUtil.getWorkHandler().getLooper(), "MiotHandler", this) { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.1
        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return "MiotHandler";
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
        }
    };
    private long i = SystemClock.elapsedRealtime();
    private boolean h = true;

    /* loaded from: classes3.dex */
    public interface OnBindListener {
        void onBindFailed(int i);

        void onBindSuccess();

        void onStartDevice(boolean z);
    }

    /* loaded from: classes3.dex */
    public interface OnOtStatusListener {
        void onStatus(String str);
    }

    /* loaded from: classes3.dex */
    public interface OnStartDeviceListener {
        void onStartDevice(boolean z);
    }

    static /* synthetic */ int e(MiotDeviceManager miotDeviceManager) {
        int i = miotDeviceManager.t;
        miotDeviceManager.t = i + 1;
        return i;
    }

    static /* synthetic */ int j(MiotDeviceManager miotDeviceManager) {
        int i = miotDeviceManager.g;
        miotDeviceManager.g = i + 1;
        return i;
    }

    static /* synthetic */ int m(MiotDeviceManager miotDeviceManager) {
        int i = miotDeviceManager.m;
        miotDeviceManager.m = i + 1;
        return i;
    }

    public boolean isOtConnected() {
        return this.r;
    }

    public boolean isServiceBind() {
        return this.p;
    }

    public MiotDevice getDevice() {
        return this.k;
    }

    public void setWifi(String str) {
        this.y = str;
        L.ot.i("set wifi for wifi-change-password-feature ssid=%s", str);
    }

    public void setOnStartDeviceListener(OnStartDeviceListener onStartDeviceListener) {
        this.s = onStartDeviceListener;
    }

    public static void init(Context context, OnBindListener onBindListener) {
        if (e == null) {
            synchronized (MiotDeviceManager.class) {
                if (e == null) {
                    if (e == null) {
                        e = new MiotDeviceManager(context, onBindListener);
                    }
                }
            }
        }
    }

    public static MiotDeviceManager getInstance() {
        if (e != null) {
            return e;
        }
        synchronized (MiotDeviceManager.class) {
            if (e != null) {
                return e;
            }
            throw new RuntimeException("Please first call MiotDeviceManager.init");
        }
    }

    private MiotDeviceManager(Context context, final OnBindListener onBindListener) {
        this.f = context;
        this.j.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$ZWoMQEAwb8cRk6S8cWxdTRE50D8
            @Override // java.lang.Runnable
            public final void run() {
                MiotDeviceManager.this.a(onBindListener);
            }
        });
    }

    public /* synthetic */ void a(OnBindListener onBindListener) {
        L.ot.i("MiotDeviceManager init MiotDeviceManager bind MioT Service");
        startService(onBindListener);
    }

    public void initialize(List<String> list, ITransparentListener.Stub stub) {
        this.w = list;
        this.x = stub;
    }

    public void refreshMIoTDevice() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.z;
        if (j == 0 || elapsedRealtime - j > this.A) {
            this.z = elapsedRealtime;
            Log.i(MicoSupConstants.TAG_LAU, "Info: refresh devices in MiotDeviceManager");
            MiotManager.refreshDevices();
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                L.ot.i("MiotDeviceManager on message  REFRESH_SERVICE_TOKEN");
                h();
                return true;
            case 2:
                L.ot.d("MiotDeviceManager try to valid miot token");
                if (getMiotAccessToken() == null || !getMiotAccessToken().isValid()) {
                    L.ot.w("MiotDeviceManager token is expired, try to refresh");
                    if (!this.h) {
                        this.i = SystemClock.elapsedRealtime();
                        this.h = true;
                    }
                    h();
                } else {
                    this.j.sendEmptyMessageDelayed(2, a);
                }
                return true;
            default:
                return false;
        }
    }

    @Nullable
    public MiotAccessToken getMiotAccessToken() {
        return this.l;
    }

    public void restartServiceAfterLogin() {
        L.ot.i("MiotDeviceManager restartServiceAfterLogin");
        ThreadUtil.getWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$RdFAbHnNOJl0-h8WnJoeUdLBmuU
            @Override // java.lang.Runnable
            public final void run() {
                MiotDeviceManager.this.r();
            }
        });
    }

    public void startService(OnBindListener onBindListener) {
        a(false, "", "", "", onBindListener);
    }

    public void startService(boolean z, String str, String str2, String str3) {
        a(z, str, str2, str3, null);
    }

    private void a(boolean z, final String str, final String str2, final String str3, final OnBindListener onBindListener) {
        L.ot.i("MiotDeviceManager startService, needReBind=%s mIsServiceBind=%s", Boolean.valueOf(z), Boolean.valueOf(this.p));
        try {
            if (this.p) {
                if (z) {
                    L.ot.e("MiotDeviceManager unbind before startService, partnerId=%s", str2);
                    MiotHostManager.getInstance().unbind(this.f);
                } else {
                    L.ot.w("MiotDeviceManager MiotService has bind, ignore startService. bindKey=%s, partnerId=%s", str, str2);
                    if (onBindListener != null) {
                        onBindListener.onBindSuccess();
                    }
                    ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$8QUgixI70V1_ApzuV1u6ln3QyDA
                        @Override // java.lang.Runnable
                        public final void run() {
                            MiotDeviceManager.this.a(str, str2, str3, onBindListener);
                        }
                    });
                    return;
                }
            }
        } catch (MiotException e2) {
            L.ot.e("MiotDeviceManager unbind service exception", e2);
        }
        try {
            this.g = 0;
            MiotHostManager.getInstance().bind(this.f, new AnonymousClass6(z, str, onBindListener, str2, str3), new GetDeviceInfoHandler() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$rmLsOrY1RmtBRIIlHQwcgf0h8Os
                @Override // com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler
                public final String onRequestRouterInfo() {
                    String q;
                    q = MiotDeviceManager.this.q();
                    return q;
                }
            });
        } catch (MiotException e3) {
            L.ot.e("MiotDeviceManager bind OT MiotException", e3);
        }
    }

    public /* synthetic */ void a(String str, String str2, String str3, OnBindListener onBindListener) {
        b(false, str, str2, str3, onBindListener);
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.MiotDeviceManager$6 */
    /* loaded from: classes3.dex */
    public class AnonymousClass6 implements CompletedListener {
        final /* synthetic */ boolean a;
        final /* synthetic */ String b;
        final /* synthetic */ OnBindListener c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;

        AnonymousClass6(boolean z, String str, OnBindListener onBindListener, String str2, String str3) {
            MiotDeviceManager.this = r1;
            this.a = z;
            this.b = str;
            this.c = onBindListener;
            this.d = str2;
            this.e = str3;
        }

        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
        public void onSucceed(String str) {
            L.ot.i("MiotDeviceManager MiotService bind success result=%s, needReBind=%s, bindKey=%s", str, Boolean.valueOf(this.a), this.b);
            OnBindListener onBindListener = this.c;
            if (onBindListener != null) {
                onBindListener.onBindSuccess();
            }
            MiotDeviceManager.this.p = true;
            MiotDeviceManager.this.j();
            ExecutorService computationThreadPool = ThreadUtil.getComputationThreadPool();
            final boolean z = this.a;
            final String str2 = this.b;
            final String str3 = this.d;
            final String str4 = this.e;
            final OnBindListener onBindListener2 = this.c;
            computationThreadPool.submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$6$U27KZcDR2B-P6WNcvfOb0RHYXP4
                @Override // java.lang.Runnable
                public final void run() {
                    MiotDeviceManager.AnonymousClass6.this.a(z, str2, str3, str4, onBindListener2);
                }
            });
        }

        public /* synthetic */ void a(boolean z, String str, String str2, String str3, OnBindListener onBindListener) {
            MiotDeviceManager.this.b(z, str, str2, str3, onBindListener);
        }

        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
        public void onFailed(MiotError miotError) {
            MiotDeviceManager.this.i();
            L.ot.e("MiotDeviceManager MiotService service disconnected  %s", miotError);
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - MiotDeviceManager.this.u) > 300000) {
                MiotDeviceManager.this.t = 1;
                MiotDeviceManager.this.u = currentTimeMillis;
                MiotDeviceManager.this.g();
                return;
            }
            MiotDeviceManager.e(MiotDeviceManager.this);
            if (MiotDeviceManager.this.t < 5) {
                MiotDeviceManager.this.g();
                return;
            }
            L.ot.e("MiotDeviceManager ot service disconnect frequently");
            OnBindListener onBindListener = this.c;
            if (onBindListener != null) {
                onBindListener.onBindFailed(miotError.getCode());
            }
        }
    }

    public /* synthetic */ String q() {
        if (!NetworkMonitor.getInstance().isWifiConnected()) {
            return "";
        }
        if (ContainerUtil.hasData(this.y)) {
            return e();
        }
        return f();
    }

    private String e() {
        if (!ContainerUtil.hasData(this.y)) {
            return "";
        }
        a aVar = new a();
        aVar.a = this.y;
        L.ot.i("MiotDeviceManager getDeviceInfoFromCurrent ssid=%s", this.y);
        return Gsons.getGson().toJson(aVar);
    }

    private String f() {
        Context context = this.f;
        NetworkMonitor.WifiSsidPassword wifiSsidPassword = NetworkMonitor.getWifiSsidPassword(context, WiFiUtil.getSSID(context), L.ot);
        if (!wifiSsidPassword.isValid() || ContainerUtil.isEmpty(wifiSsidPassword.getPassword())) {
            return "";
        }
        a aVar = new a();
        aVar.a = WiFiUtil.stripSSID(wifiSsidPassword.getSsid());
        L.ot.i("MiotDeviceManager getDeviceInfoFromWifiPwdStore ssid=%s password=%s", aVar.a, wifiSsidPassword.getPassword());
        return Gsons.getGson().toJson(aVar);
    }

    public void g() {
        this.j.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$fL7W5aGB6VDt_IptFeXdZ7Ly1ck
            @Override // java.lang.Runnable
            public final void run() {
                MiotDeviceManager.this.p();
            }
        }, d);
    }

    public /* synthetic */ void p() {
        if (!this.p) {
            L.ot.i("MiotDeviceManager restart ot service");
            startService(false, "", "", "");
        }
    }

    public void startDevice() {
        L.ot.d("MiotDeviceManager startDevice()");
        b(false, "", "", "", null);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00a6 A[Catch: MiotException -> 0x00b3, TryCatch #0 {MiotException -> 0x00b3, blocks: (B:2:0x0000, B:5:0x0007, B:7:0x0010, B:8:0x0013, B:10:0x0017, B:12:0x001d, B:14:0x0025, B:16:0x002f, B:19:0x0036, B:20:0x0046, B:21:0x0054, B:23:0x005f, B:25:0x0065, B:26:0x0084, B:27:0x0094, B:29:0x00a6, B:30:0x00a9, B:32:0x00ad), top: B:42:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ad A[Catch: MiotException -> 0x00b3, TRY_LEAVE, TryCatch #0 {MiotException -> 0x00b3, blocks: (B:2:0x0000, B:5:0x0007, B:7:0x0010, B:8:0x0013, B:10:0x0017, B:12:0x001d, B:14:0x0025, B:16:0x002f, B:19:0x0036, B:20:0x0046, B:21:0x0054, B:23:0x005f, B:25:0x0065, B:26:0x0084, B:27:0x0094, B:29:0x00a6, B:30:0x00a9, B:32:0x00ad), top: B:42:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean b(boolean r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, com.xiaomi.micolauncher.module.miot.MiotDeviceManager.OnBindListener r8) {
        /*
            r3 = this;
            boolean r0 = r3.q     // Catch: MiotException -> 0x00b3
            r1 = 1
            if (r0 == 0) goto L_0x001d
            if (r4 != 0) goto L_0x001d
            com.elvishew.xlog.Logger r4 = com.xiaomi.micolauncher.common.L.ot     // Catch: MiotException -> 0x00b3
            java.lang.String r5 = "MiotDeviceManager startDeviceCalled=true and needReBind=false, return!"
            r4.w(r5)     // Catch: MiotException -> 0x00b3
            if (r8 == 0) goto L_0x0013
            r8.onStartDevice(r1)     // Catch: MiotException -> 0x00b3
        L_0x0013:
            com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnStartDeviceListener r4 = r3.s     // Catch: MiotException -> 0x00b3
            if (r4 == 0) goto L_0x001c
            com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnStartDeviceListener r4 = r3.s     // Catch: MiotException -> 0x00b3
            r4.onStartDevice(r1)     // Catch: MiotException -> 0x00b3
        L_0x001c:
            return r1
        L_0x001d:
            android.content.Context r0 = r3.f     // Catch: MiotException -> 0x00b3
            boolean r0 = com.xiaomi.micolauncher.common.setting.SystemSetting.isInitialized(r0)     // Catch: MiotException -> 0x00b3
            if (r0 != 0) goto L_0x0046
            com.xiaomi.micolauncher.module.NetworkMonitor r0 = com.xiaomi.micolauncher.module.NetworkMonitor.getInstance()     // Catch: MiotException -> 0x00b3
            boolean r0 = r0.isWifiConnected(r1)     // Catch: MiotException -> 0x00b3
            if (r0 != 0) goto L_0x0046
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch: MiotException -> 0x00b3
            if (r0 != 0) goto L_0x0036
            goto L_0x0046
        L_0x0036:
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.ot     // Catch: MiotException -> 0x00b3
            java.lang.String r2 = "MiotDeviceManager processAfterBind setWifiMode mode=HIDE_SSID_MODE"
            r0.i(r2)     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.host.manager.MiotHostManager r0 = com.xiaomi.miot.host.manager.MiotHostManager.getInstance()     // Catch: MiotException -> 0x00b3
            r2 = 3
            r0.setWifiMode(r2)     // Catch: MiotException -> 0x00b3
            goto L_0x0054
        L_0x0046:
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.ot     // Catch: MiotException -> 0x00b3
            java.lang.String r2 = "MiotDeviceManager processAfterBind setWifiMode mode=STA_MODE"
            r0.d(r2)     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.host.manager.MiotHostManager r0 = com.xiaomi.miot.host.manager.MiotHostManager.getInstance()     // Catch: MiotException -> 0x00b3
            r0.setWifiMode(r1)     // Catch: MiotException -> 0x00b3
        L_0x0054:
            com.xiaomi.micolauncher.module.miot.MiotDevice r0 = new com.xiaomi.micolauncher.module.miot.MiotDevice     // Catch: MiotException -> 0x00b3
            android.content.Context r2 = r3.f     // Catch: MiotException -> 0x00b3
            r0.<init>(r2)     // Catch: MiotException -> 0x00b3
            r3.k = r0     // Catch: MiotException -> 0x00b3
            if (r4 == 0) goto L_0x0084
            boolean r4 = android.text.TextUtils.isEmpty(r6)     // Catch: MiotException -> 0x00b3
            if (r4 != 0) goto L_0x0084
            com.xiaomi.miot.typedef.device.BindParams r4 = new com.xiaomi.miot.typedef.device.BindParams     // Catch: MiotException -> 0x00b3
            r4.<init>()     // Catch: MiotException -> 0x00b3
            r4.setBindKey(r5)     // Catch: MiotException -> 0x00b3
            r4.setPartnerId(r6)     // Catch: MiotException -> 0x00b3
            r4.setPartnerToken(r7)     // Catch: MiotException -> 0x00b3
            com.elvishew.xlog.Logger r5 = com.xiaomi.micolauncher.common.L.ot     // Catch: MiotException -> 0x00b3
            java.lang.String r6 = "MiotDeviceManager MiotHostManager startWithBindParams MiotDevice"
            r5.i(r6)     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.host.manager.MiotHostManager r5 = com.xiaomi.miot.host.manager.MiotHostManager.getInstance()     // Catch: MiotException -> 0x00b3
            com.xiaomi.micolauncher.module.miot.MiotDevice r6 = r3.k     // Catch: MiotException -> 0x00b3
            r5.startWithBindParams(r6, r4)     // Catch: MiotException -> 0x00b3
            goto L_0x0094
        L_0x0084:
            com.elvishew.xlog.Logger r4 = com.xiaomi.micolauncher.common.L.ot     // Catch: MiotException -> 0x00b3
            java.lang.String r5 = "MiotDeviceManager MiotHostManager start MiotDevice"
            r4.i(r5)     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.host.manager.MiotHostManager r4 = com.xiaomi.miot.host.manager.MiotHostManager.getInstance()     // Catch: MiotException -> 0x00b3
            com.xiaomi.micolauncher.module.miot.MiotDevice r5 = r3.k     // Catch: MiotException -> 0x00b3
            r4.start(r5)     // Catch: MiotException -> 0x00b3
        L_0x0094:
            com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation r4 = com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation.getInstance()     // Catch: MiotException -> 0x00b3
            r4.registerProperty()     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.support.ICameraStatus r4 = com.xiaomi.micolauncher.skills.miot.model.MiotModel.getCameraStatus()     // Catch: MiotException -> 0x00b3
            com.xiaomi.miot.support.MiotManager.setCameraStatus(r4)     // Catch: MiotException -> 0x00b3
            r3.q = r1     // Catch: MiotException -> 0x00b3
            if (r8 == 0) goto L_0x00a9
            r8.onStartDevice(r1)     // Catch: MiotException -> 0x00b3
        L_0x00a9:
            com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnStartDeviceListener r4 = r3.s     // Catch: MiotException -> 0x00b3
            if (r4 == 0) goto L_0x00b2
            com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnStartDeviceListener r4 = r3.s     // Catch: MiotException -> 0x00b3
            r4.onStartDevice(r1)     // Catch: MiotException -> 0x00b3
        L_0x00b2:
            return r1
        L_0x00b3:
            r4 = move-exception
            com.elvishew.xlog.Logger r5 = com.xiaomi.micolauncher.common.L.ot
            java.lang.String r6 = "MiotDeviceManager processAfterBind exception of miot"
            r5.e(r6, r4)
            r4 = 0
            r3.q = r4
            if (r8 == 0) goto L_0x00c3
            r8.onStartDevice(r4)
        L_0x00c3:
            com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnStartDeviceListener r5 = r3.s
            if (r5 == 0) goto L_0x00ca
            r5.onStartDevice(r4)
        L_0x00ca:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.b(boolean, java.lang.String, java.lang.String, java.lang.String, com.xiaomi.micolauncher.module.miot.MiotDeviceManager$OnBindListener):boolean");
    }

    @SuppressLint({"CheckResult"})
    private void h() {
        L.ot.i("MiotDeviceManager try to run refresh service token process");
        if (!isOtConnected()) {
            L.ot.e("MiotDeviceManager OT not connected");
        } else if (this.o) {
            L.ot.e("MiotDeviceManager refresh service token process has already started, skip");
        } else if (!LoginModel.getInstance().hasValidAccount()) {
            L.ot.w("MiotDeviceManager no ValidA account, do not getVoiceCtrlAccessToken,return!");
        } else {
            final long millis = TimeUnit.SECONDS.toMillis(30L);
            this.j.removeMessages(2);
            this.j.removeMessages(1);
            if (this.p) {
                this.o = true;
                try {
                    MiotHostManager.getInstance().getVoiceCtrlAccessToken(new AccessTokenListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.7
                        @Override // com.xiaomi.miot.typedef.listener.AccessTokenListener
                        public void onSucceed(String str, int i) {
                            L.ot.d("MiotDeviceManager getVoiceCtrlAccessToken onSucceed expiresTime:%d", Integer.valueOf(i));
                            MiotDeviceManager.this.o = false;
                            MiotDeviceManager miotDeviceManager = MiotDeviceManager.this;
                            miotDeviceManager.l = new MiotAccessToken(str, System.currentTimeMillis() + (i * 1000));
                            if (NetworkMonitor.getInstance().isWifiConnected() && LoginModel.getInstance().hasUid()) {
                                L.ot.d("MiotDeviceManager getVoiceCtrlAccessToken onSucceed uploadMiotToken");
                                ApiHelper.uploadMiotAccessToken(MiotDeviceManager.this.l.c, MiotDeviceManager.this.l.b);
                            }
                            MiotDeviceManager.this.j.sendEmptyMessageDelayed(2, MiotDeviceManager.a);
                            if (MiotDeviceManager.this.h) {
                                StatUtils.recordCountEvent(StatKey.MIOT_TOKEN_RESULT, "result", StatConstants.BIND_SUCCESS);
                                MiotDeviceManager.this.h = false;
                            }
                        }

                        @Override // com.xiaomi.miot.typedef.listener.AccessTokenListener
                        public void onFailed(MiotError miotError) {
                            MiotDeviceManager.this.o = false;
                            L.ot.e("MiotDeviceManager refreshServiceToken failed %s", miotError);
                            if (miotError.getCode() == r2) {
                                MiotDeviceManager.j(MiotDeviceManager.this);
                                if (MiotDeviceManager.this.g >= r3) {
                                    if (MiotDeviceManager.this.m < 3) {
                                        MiotDeviceManager.m(MiotDeviceManager.this);
                                        L.ot.w("MiotDeviceManager getVoiceCtrlAccessToken failed 3 times. so reset and rebind device");
                                        MiotDeviceManager.this.a(true);
                                    }
                                    MiotDeviceManager.this.g = 0;
                                    return;
                                }
                                Logger logger = L.ot;
                                logger.w("MiotDeviceManager miotException.getErrorCode() == -6, retry count:" + MiotDeviceManager.this.g);
                            } else if (miotError.getCode() == MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode()) {
                                MiotDeviceManager.this.i();
                            } else if (miotError.getCode() == MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode()) {
                                MiotDeviceManager.this.m();
                            }
                            if (MiotDeviceManager.this.h && SystemClock.elapsedRealtime() - MiotDeviceManager.this.i > millis) {
                                StatUtils.recordCountEvent(StatKey.MIOT_TOKEN_RESULT, "result", "fail");
                                MiotDeviceManager.this.h = false;
                            }
                            MiotDeviceManager.this.j.sendEmptyMessageDelayed(1, MiotDeviceManager.b);
                        }
                    });
                } catch (MiotException e2) {
                    L.ot.e("MiotDeviceManager Miot getAccessToken exception", e2);
                }
            }
        }
    }

    public void i() {
        L.ot.w("MiotDeviceManager onServiceDisconnected");
        this.r = false;
        this.p = false;
        this.q = false;
    }

    public void j() {
        try {
            MiotHostManager.getInstance().registerMiotLogListener($$Lambda$MiotDeviceManager$dmNaQT7mdeOZjzWIDzMUrmd7Fjo.INSTANCE);
        } catch (MiotException e2) {
            L.ot.e("MiotDeviceManager registerMiotLogListener error", e2);
        }
        try {
            L.ot.i("MiotDeviceManager register DeviceBindListener");
            MiotHostManager.getInstance().registerBindListener(new com.xiaomi.miot.typedef.listener.OnBindListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.8
                @Override // com.xiaomi.miot.typedef.listener.OnBindListener
                public void onBind() {
                    L.ot.i("MiotDeviceManager registerBindListener onBind");
                    PreferenceUtils.setSettingBoolean(MiotDeviceManager.this.f, "device_bind_to_miot", true);
                    MiotDeviceManager.this.k();
                }

                @Override // com.xiaomi.miot.typedef.listener.OnBindListener
                public void onUnBind() {
                    L.ot.w("MiotDeviceManager registerBindListener onUnBind");
                }
            }, new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.9
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    L.ot.i("MiotDeviceManager registerBindListener CompletedListener onSucceed result: %s", str);
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    L.ot.e("MiotDeviceManager registerBindListener CompletedListener onFailed", miotError);
                }
            });
        } catch (MiotException e3) {
            L.ot.e("MiotDeviceManager register DeviceBindListener error", e3);
        }
        try {
            L.ot.i("MiotDeviceManager registerMiotConnectedListener, network connected %s", Boolean.valueOf(NetworkUtil.isNetworkConnected(this.f)));
            MiotHostManager.getInstance().registerMiotConnectedListener(new MiotConnectedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.10
                @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
                public void onConnected() {
                    L.ot.i("MiotDeviceManager registerMiotConnectedListener onConnected to MIOT server");
                    MiotDeviceManager.this.r = true;
                    EventBusRegistry.getEventBus().post(new MiotConnectedEvent());
                    if (PreferenceUtils.getSettingBoolean(MiotDeviceManager.this.f, "device_bind_to_miot", false)) {
                        L.ot.i("%s do syncIoTAfterBind when Connected to MIOT server", "MiotDeviceManager ");
                        MiotDeviceManager.this.k();
                    }
                    if (MiotProvisionManagerWrapper.isMeshEnable()) {
                        L.mesh.i("MiotDeviceManager initMiotMeshManager after MiotConnectedListener-onConnected");
                        MiotProvisionManagerWrapper.getInstance().initMiotMeshManager(false);
                    }
                }

                @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
                public void onDisconnected() {
                    L.ot.e("MiotDeviceManager registerMiotConnectedListener onDisconnected to MIOT server");
                    MiotDeviceManager.this.r = false;
                    MiotDeviceManager.this.m();
                }

                @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
                public void onTokenException() {
                    L.ot.e("MiotDeviceManager registerMiotConnectedListener onTokenException");
                }
            });
        } catch (MiotException e4) {
            L.ot.e("MiotDeviceManager registerMiotConnectedListener", e4);
        }
        try {
            if (ContainerUtil.hasData(this.w)) {
                L.ot.i("MiotDeviceManager register messages %s", TextUtils.join(StringUtils.SPACE, this.w));
                MiotHostManager.getInstance().registerMiotTransparentRequestListener(this.w, new ITransparentListener.Stub() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.11
                    @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                    public void onMessage(String str) throws RemoteException {
                        L.ot.i("MiotDeviceManager registerMiotTransparentRequestListener onMessage received=%s", str);
                        if (MiotDeviceManager.this.x != null) {
                            MiotDeviceManager.this.x.onMessage(str);
                        }
                    }

                    @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                    public void onFailed(MiotError miotError) throws RemoteException {
                        L.ot.e("MiotDeviceManager failed to receive transparent message, error code %s", miotError);
                        ITransparentListener.Stub stub = MiotDeviceManager.this.x;
                        if (stub != null) {
                            stub.onFailed(miotError);
                        }
                    }
                });
                return;
            }
            L.ot.e("MiotDeviceManager empty messages, do not register MIOT transparent request listener");
        } catch (MiotException e5) {
            L.ot.e("MiotDeviceManager register miot transparent request listener", e5);
        }
    }

    public static /* synthetic */ void a(int i, String str, String str2) {
        L.miotSdk.log(i, "%s %s", str, str2);
    }

    public void k() {
        if (NetworkMonitor.getInstance().isWifiConnected()) {
            L.ot.i("MiotDeviceManager syncIoTAfterBind");
            Observable.timer(RandomTimeUtils.getRandomMillisInNextMin(16), TimeUnit.MILLISECONDS).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$AIpaUp_ylQ0z6oRC-mc8HhNbdbI
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiotDeviceManager.this.a((Long) obj);
                }
            });
            n();
            h();
            l();
        }
    }

    public /* synthetic */ void a(Long l) throws Exception {
        a(this.f);
    }

    private void l() {
        L.ot.d("MiotDeviceManager getSupportDoorbellPdids");
        MiotManager.getSupportDoorbellPdids(new DoorbellPdidsCallback() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.12
            @Override // com.xiaomi.miot.support.DoorbellPdidsCallback
            public void onSuccess(List<Integer> list) {
                JSONArray jSONArray = new JSONArray();
                if (list != null && list.size() > 0) {
                    for (Integer num : list) {
                        if (num != null) {
                            jSONArray.put(num);
                        }
                    }
                }
                try {
                    L.ot.d("MiotDeviceManager sendSupportDoorbellPdids");
                    MiotHostManager.getInstance().send("event.camera_doorbell_pdids", jSONArray.toString(), new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.12.1
                        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                        public void onSucceed(String str) {
                            L.ot.d("MiotDeviceManager sendSupportDoorbellPdids success: %s", str);
                        }

                        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                        public void onFailed(MiotError miotError) {
                            L.ot.e("MiotDeviceManager sendSupportDoorbellPdids failed: %s", miotError.toString());
                        }
                    });
                } catch (MiotException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // com.xiaomi.miot.support.DoorbellPdidsCallback
            public void onFailed(String str) {
                L.ot.e("MiotDeviceManager getSupportDoorbellPdids failed %s", str);
            }
        });
    }

    private void a(Context context) {
        JsonObject jsonObject = new JsonObject();
        String macAddress = WiFiUtil.getMacAddress();
        String bluetoothMacAddress = BluetoothSettingManager.getInstance().getBluetoothMacAddress();
        if (!TextUtils.isEmpty(bluetoothMacAddress)) {
            jsonObject.addProperty("bt_mac", bluetoothMacAddress.toLowerCase());
        } else {
            L.miot.e("getBluetoothMacAddress is null !");
        }
        if (!TextUtils.isEmpty(macAddress)) {
            jsonObject.addProperty("wifi_mac", macAddress.toLowerCase());
        } else {
            L.miot.e("getMacAddress is null !");
        }
        jsonObject.addProperty("id_hash", "");
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("Idminfo", jsonObject.toString());
        try {
            MiotHostManager.getInstance().send("props", jsonObject2.toString(), new AnonymousClass13(context));
        } catch (MiotException e2) {
            L.miot.e("MiotDeviceManager sendReport2Mijia exception : ", e2);
        }
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.MiotDeviceManager$13 */
    /* loaded from: classes3.dex */
    public class AnonymousClass13 implements CompletedListener {
        final /* synthetic */ Context a;

        AnonymousClass13(Context context) {
            MiotDeviceManager.this = r1;
            this.a = context;
        }

        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
        public void onSucceed(String str) {
            L.ot.d("%s send wifi&bt mac to iot onSucceed", "MiotDeviceManager ");
            PreferenceUtils.setSettingLong(this.a, "upload_idminfo", System.currentTimeMillis());
            if (!MiotDeviceManager.this.n) {
                Observable<Long> subscribeOn = Observable.interval(0L, MiotDeviceManager.c, TimeUnit.MILLISECONDS).subscribeOn(MicoSchedulers.io());
                final Context context = this.a;
                subscribeOn.subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.miot.-$$Lambda$MiotDeviceManager$13$w_ccJQPQ0HypEUhoXPVjekvafcY
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MiotDeviceManager.AnonymousClass13.this.a(context, (Long) obj);
                    }
                });
            }
            MiotDeviceManager.this.n = true;
        }

        public /* synthetic */ void a(Context context, Long l) throws Exception {
            MiotDeviceManager.this.b(context);
        }

        @Override // com.xiaomi.miot.typedef.listener.CompletedListener
        public void onFailed(MiotError miotError) {
            L.miot.e("MiotDeviceManager sendReport2Mijia error : %s", miotError);
        }
    }

    public void b(Context context) {
        long settingLong = PreferenceUtils.getSettingLong(context, "upload_idminfo", 0L);
        if (settingLong != 0 && System.currentTimeMillis() - settingLong >= c) {
            a(context);
        }
    }

    public void m() {
        L.ot.e("MiotDeviceManager disconnected from MIOT server");
    }

    public void disableSyncTime() {
        this.v = false;
    }

    private void n() {
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                L.ot.i("MiotDeviceManager syncTimeWithServer");
                MiotHostManager.getInstance().getSyncTime(new AnonymousClass2());
            }
        } catch (MiotException e2) {
            e2.printStackTrace();
            L.ot.e("MiotDeviceManager getSyncTime", e2);
        }
    }

    /* renamed from: com.xiaomi.micolauncher.module.miot.MiotDeviceManager$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 implements SyncTimeListener {
        AnonymousClass2() {
            MiotDeviceManager.this = r1;
        }

        @Override // com.xiaomi.miot.typedef.listener.SyncTimeListener
        public void onSucceed(long j) {
            if (MiotDeviceManager.this.v) {
                L.ot.i("MiotDeviceManager syncTimeWithServer success, serverTime=%d", Long.valueOf(j));
                SystemClock.setCurrentTimeMillis(j);
                ThreadUtil.getLightWorkHandler().post($$Lambda$MiotDeviceManager$2$AGX1PsHMX3wgLINtuOJFfUCJDEg.INSTANCE);
                SystemSetting.setLastSyncTime(j);
            }
        }

        public static /* synthetic */ void a() {
            AlarmModel.getInstance().resetAlarmAfterTimeSync();
            HourlyChimeMode.getInstance().resetHourlyChimeAfterTimeSync();
        }

        @Override // com.xiaomi.miot.typedef.listener.SyncTimeListener
        public void onFailed(MiotError miotError) {
            L.ot.i("MiotDeviceManager syncTimeWithServer failed", miotError);
        }
    }

    public void a(boolean z) {
        if (z) {
            try {
                if (LoginModel.getInstance().hasValidAccount()) {
                    L.ot.w("MiotDeviceManager reset, getAppBindKey");
                    r();
                }
            } catch (MiotException e2) {
                L.ot.e("MiotDeviceManager reset MiotException", e2);
                return;
            }
        }
        L.ot.w("MiotDeviceManager MiotHostManager reset");
        MiotHostManager.getInstance().reset(new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.3
            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onSucceed(String str) {
                L.ot.w("MiotDeviceManager reset success result: %s", str);
                try {
                    MiotHostManager.getInstance().stop();
                    MiotHostManager.getInstance().unbind(MiotDeviceManager.this.f);
                    MiotDeviceManager.this.startService(false, "", "", "");
                } catch (MiotException e3) {
                    L.ot.e("MiotDeviceManager MiotHostManager.getInstance().stop or unbind failed", e3);
                }
            }

            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onFailed(MiotError miotError) {
                L.ot.e("MiotDeviceManager reset onFailed:%s", miotError);
            }
        });
    }

    /* renamed from: o */
    public void r() {
        String userId = TokenManager.getInstance().getUserId();
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken("xiaomiio");
        if (TextUtils.isEmpty(userId) || blockGetServiceToken == null) {
            L.ot.e("getAppBindKey error userId=%s or serviceTokenResult is null", userId);
        } else {
            MiotHostHelper.getAppBindKey(this.f, userId, blockGetServiceToken.security, blockGetServiceToken.serviceToken, new MiotHostHelper.AppBindKeyCallback() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.4
                @Override // com.xiaomi.miot.host.manager.MiotHostHelper.AppBindKeyCallback
                public void onSuccess(String str) {
                    L.ot.i("MiotDeviceManager getAppBindKey onSuccess bindKey=%s", str);
                    MiotDeviceManager.this.startService(true, str, SystemSetting.getDeviceID(), "");
                }

                @Override // com.xiaomi.miot.host.manager.MiotHostHelper.AppBindKeyCallback
                public void onFailed(String str) {
                    L.ot.e("MiotDeviceManager getAppBindKey onFailed %s", str);
                }
            });
        }
    }

    public boolean a() {
        if (!this.q) {
            L.ot.i("MiotDeviceManager check hasSetWifiStatus : call bind and start first");
            return false;
        }
        if (!this.r) {
            L.ot.w("MiotDeviceManager hasSetWifiStatus : mIsOtConnected is false");
        }
        try {
            boolean hasSetWifiStatus = MiotHostManager.getInstance().hasSetWifiStatus();
            if (!hasSetWifiStatus) {
                L.ot.i("MiotDeviceManager has set wifi status, startDevice called=%B, OTConnected=%B", Boolean.valueOf(this.q), Boolean.valueOf(this.r));
            }
            return hasSetWifiStatus;
        } catch (MiotException e2) {
            L.ot.e("MiotDeviceManager  hasSetWifiStatus", e2);
            return false;
        }
    }

    public void getOtLocalStatus(final OnOtStatusListener onOtStatusListener) {
        if (!this.q) {
            L.ot.i("MiotDeviceManager not started, to start OT");
            startDevice();
        }
        try {
            MiotHostManager.getInstance().getOtLocalStatus(new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDeviceManager.5
                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onSucceed(String str) {
                    L.ot.i("MiotDeviceManager get ot local status %s", str);
                    onOtStatusListener.onStatus(str);
                }

                @Override // com.xiaomi.miot.typedef.listener.CompletedListener
                public void onFailed(MiotError miotError) {
                    L.ot.w("MiotDeviceManager failed to get local status, error code %s, message %s", Integer.valueOf(miotError.getCode()), miotError.getMessage());
                    OtDefs.OtLocalStatus otLocalStatus = new OtDefs.OtLocalStatus();
                    otLocalStatus.a = miotError.getMessage();
                    onOtStatusListener.onStatus(Gsons.getGson().toJson(otLocalStatus));
                }
            });
        } catch (MiotException e2) {
            L.ot.e("MiotDeviceManager failed to get ot status", e2);
            OtDefs.OtLocalStatus otLocalStatus = new OtDefs.OtLocalStatus();
            otLocalStatus.a = e2.toString();
            onOtStatusListener.onStatus(Gsons.getGson().toJson(otLocalStatus));
        }
    }

    /* loaded from: classes3.dex */
    public class MiotAccessToken {
        private final String b;
        private final String c;
        private final long d;

        MiotAccessToken(String str, long j) {
            MiotDeviceManager.this = r1;
            String[] split = str.substring(2).split(Constants.WAVE_SEPARATOR);
            this.b = split[0];
            this.c = split[1];
            this.d = j;
        }

        public boolean isValid() {
            return this.d - System.currentTimeMillis() > 1800000;
        }

        public String getAccessToken() {
            return this.c;
        }

        public String getSessionId() {
            return this.b;
        }
    }

    /* loaded from: classes3.dex */
    public static class a {
        @SerializedName("ssid")
        String a;

        private a() {
        }
    }
}
