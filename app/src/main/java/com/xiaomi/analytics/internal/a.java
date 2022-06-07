package com.xiaomi.analytics.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.analytics.pro.ai;
import com.xiaomi.analytics.Analytics;
import com.xiaomi.analytics.internal.util.ALog;
import com.xiaomi.analytics.internal.util.AndroidUtils;
import com.xiaomi.analytics.internal.util.CertificateUtils;
import com.xiaomi.analytics.internal.util.IOUtil;
import com.xiaomi.analytics.internal.util.MIUI;
import com.xiaomi.analytics.internal.util.NetworkUtils;
import com.xiaomi.analytics.internal.util.SysUtils;
import com.xiaomi.analytics.internal.util.TaskRunner;
import com.xiaomi.analytics.internal.util.TimeUtils;
import com.xiaomi.analytics.internal.util.Utils;
import com.xiaomi.micolauncher.overlay.Constants;
import com.xiaomi.onetrack.a.k;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UpdateManager.java */
/* loaded from: classes3.dex */
public class a {
    private static final long a = TimeUtils.HALF_DAY_IN_MS;
    private static volatile a b;
    private Context c;
    private String f;
    private int g;
    private AbstractC0159a h;
    private String d = "";
    private String e = "";
    private Runnable i = new Runnable() { // from class: com.xiaomi.analytics.internal.a.1
        @Override // java.lang.Runnable
        public void run() {
            Exception e;
            JSONObject jSONObject;
            String optString;
            int optInt;
            String optString2;
            AnonymousClass1 r1 = this;
            Version version = Constants.API_VER;
            Version version2 = SdkManager.getInstance(a.this.c).getVersion();
            long currentTimeMillis = System.currentTimeMillis();
            int i = 0;
            while (true) {
                int i2 = i + 1;
                if (i < 2) {
                    try {
                        String androidVersionNumber = SysUtils.getAndroidVersionNumber();
                        String mIUIBuild = SysUtils.getMIUIBuild();
                        String hashedIMEI = SysUtils.getHashedIMEI(a.this.c);
                        String deviceModel = SysUtils.getDeviceModel();
                        int networkType = NetworkUtils.getNetworkType(a.this.c);
                        String c = a.this.c();
                        String packageName = a.this.c.getPackageName();
                        String region = SysUtils.getRegion();
                        String mIUIVersion = SysUtils.getMIUIVersion();
                        i = i2;
                        try {
                            String string = Settings.Secure.getString(a.this.c.getContentResolver(), "android_id");
                            String md5 = Utils.getMd5(packageName + string);
                            try {
                                ALog.d("UpdateManager", "i=" + md5 + ", orig=" + hashedIMEI);
                                StringBuilder sb = new StringBuilder();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(k.f);
                                sb2.append(version);
                                sb.append(sb2.toString());
                                sb.append(Constants.SERVER_DATA_PARAM_CLIENT_VERSION + version2);
                                sb.append("d" + androidVersionNumber);
                                sb.append("f" + mIUIBuild);
                                if (!MIUI.isInternationalBuild()) {
                                    sb.append(ai.aA + md5);
                                }
                                sb.append(MessageElement.XPATH_PREFIX + deviceModel);
                                sb.append("n" + networkType);
                                sb.append("nonce" + c);
                                sb.append(ai.av + packageName);
                                sb.append("r" + region);
                                sb.append("ts" + currentTimeMillis);
                                sb.append(ai.aC + mIUIVersion);
                                sb.append("miui_sdkconfig_jafej!@#)(*e@!#");
                                String md52 = Utils.getMd5(sb.toString());
                                StringBuilder sb3 = new StringBuilder(MIUI.isInternationalBuild() ? "https://sdkconfig.ad.intl.xiaomi.com/api/checkupdate/lastusefulversion2?" : "https://sdkconfig.ad.xiaomi.com/api/checkupdate/lastusefulversion2?");
                                sb3.append("av=" + version);
                                sb3.append("&cv=" + version2);
                                sb3.append("&d=" + androidVersionNumber);
                                sb3.append("&f=" + mIUIBuild);
                                if (!MIUI.isInternationalBuild()) {
                                    sb3.append("&i=" + md5);
                                }
                                sb3.append("&m=" + deviceModel);
                                sb3.append("&n=" + networkType);
                                sb3.append("&nonce=" + c);
                                sb3.append("&p=" + packageName);
                                sb3.append("&r=" + region);
                                sb3.append("&ts=" + currentTimeMillis);
                                sb3.append("&v=" + mIUIVersion);
                                sb3.append("&sign=" + md52);
                                ALog.d("UpdateManager", sb3.toString());
                                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb3.toString()).openConnection();
                                httpURLConnection.setRequestMethod("GET");
                                httpURLConnection.setConnectTimeout(Constants.CONNECT_TIME_OUT_MILLIS);
                                httpURLConnection.connect();
                                String str = new String(IOUtil.inputStream2ByteArray(httpURLConnection.getInputStream()));
                                ALog.d("UpdateManager", "result " + str);
                                jSONObject = new JSONObject(str);
                                optString = jSONObject.optString("url");
                            } catch (Exception e2) {
                                e = e2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            r1 = r1;
                            a.this.a(0L);
                            ALog.e("UpdateManager", "exception ", e);
                        }
                        try {
                            optInt = jSONObject.optInt("code", 0);
                            optString2 = jSONObject.optString(ai.aC);
                            r1 = this;
                        } catch (Exception e4) {
                            e = e4;
                            r1 = this;
                            a.this.a(0L);
                            ALog.e("UpdateManager", "exception ", e);
                        }
                        try {
                            a.this.g = jSONObject.optInt("force", 0);
                        } catch (Exception e5) {
                            e = e5;
                            a.this.a(0L);
                            ALog.e("UpdateManager", "exception ", e);
                        }
                    } catch (Exception e6) {
                        e = e6;
                        r1 = r1;
                        i = i2;
                    }
                    if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                        Version version3 = new Version(optString2);
                        if (MIUI.isAlphaBuild() || version3.build == 0) {
                            a.this.e = jSONObject.optString("md5");
                            a.this.d = optString;
                            TaskRunner.execute(a.this.j);
                            return;
                        }
                        return;
                    } else if (optInt == -8) {
                        currentTimeMillis = a.this.b(jSONObject.optString("failMsg"));
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    };
    private Runnable j = new Runnable() { // from class: com.xiaomi.analytics.internal.a.2
        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            Exception e;
            FileOutputStream fileOutputStream;
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(a.this.d).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(Constants.CONNECT_TIME_OUT_MILLIS);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    byte[] inputStream2ByteArray = IOUtil.inputStream2ByteArray(httpURLConnection.getInputStream());
                    FileOutputStream fileOutputStream2 = null;
                    if (!TextUtils.isEmpty(a.this.e)) {
                        if (!a.this.e.equalsIgnoreCase(Utils.getMd5(inputStream2ByteArray))) {
                            inputStream2ByteArray = null;
                        }
                    }
                    if (inputStream2ByteArray != null) {
                        Log.d(ALog.addPrefix("UpdateManager"), "download apk success.");
                        File file = new File(a.this.f + DiskFileUpload.postfix);
                        try {
                            try {
                                fileOutputStream = new FileOutputStream(file);
                            } catch (Exception e2) {
                                e = e2;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            fileOutputStream.write(inputStream2ByteArray);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            if (CertificateUtils.isXiaomiPlatformCertificate(AndroidUtils.getSignature(a.this.c, file))) {
                                Log.d(ALog.addPrefix("UpdateManager"), "verify signature success");
                                file.renameTo(new File(a.this.f));
                                a.this.d();
                            } else {
                                Log.e(ALog.addPrefix("UpdateManager"), "verify signature failed");
                            }
                        } catch (Exception e3) {
                            e = e3;
                            fileOutputStream2 = fileOutputStream;
                            Log.e(ALog.addPrefix("UpdateManager"), "mDownloader e", e);
                            IOUtil.closeSafely(fileOutputStream2);
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream2 = fileOutputStream;
                            IOUtil.closeSafely(fileOutputStream2);
                            throw th;
                        }
                        IOUtil.closeSafely(fileOutputStream2);
                    }
                }
            } catch (Exception e4) {
                Log.w(ALog.addPrefix("UpdateManager"), "mDownloader exception", e4);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: UpdateManager.java */
    /* renamed from: com.xiaomi.analytics.internal.a$a  reason: collision with other inner class name */
    /* loaded from: classes3.dex */
    public interface AbstractC0159a {
        void a(String str, boolean z);
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a(context);
            }
            aVar = b;
        }
        return aVar;
    }

    private a(Context context) {
        this.c = AndroidUtils.getApplicationContext(context);
    }

    public void a(AbstractC0159a aVar) {
        this.h = aVar;
    }

    public void a(String str) {
        if (!MIUI.shouldNotAccessNetworkOrLocation(this.c, "UpdateManager")) {
            ALog.d("UpdateManager", "checkUpdate ");
            this.f = str;
            TaskRunner.execute(this.i);
            a(System.currentTimeMillis());
        }
    }

    public boolean a() {
        if (MIUI.shouldNotAccessNetworkOrLocation(this.c, "UpdateManager")) {
            return false;
        }
        if (!Analytics.isUpdateEnable()) {
            ALog.d("UpdateManager", "Updating is disabled.");
            return false;
        }
        long b2 = b();
        ALog.d("UpdateManager", "last update check time is " + new Date(b2).toString());
        return System.currentTimeMillis() - b2 >= a;
    }

    private synchronized long b() {
        return this.c.getSharedPreferences("analytics_updater", 0).getLong("updateTime", 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(long j) {
        SharedPreferences.Editor edit = this.c.getSharedPreferences("analytics_updater", 0).edit();
        edit.putLong("updateTime", j);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String c() {
        Random random = new Random(System.nanoTime());
        try {
            String packageName = this.c.getPackageName();
            return Utils.getMd5(packageName + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + random.nextLong());
        } catch (Exception unused) {
            return Utils.getMd5(random.nextLong() + "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long b(String str) {
        try {
            return Long.parseLong(str.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER)[1]);
        } catch (Exception unused) {
            return System.currentTimeMillis();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        AbstractC0159a aVar = this.h;
        if (aVar != null) {
            String str = this.f;
            boolean z = true;
            if (this.g != 1) {
                z = false;
            }
            aVar.a(str, z);
        }
    }
}
