package com.xiaomi.ai.android.core;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.xiaomi.ai.android.capability.TrackCapability;
import com.xiaomi.ai.android.netbeans.AivsCloudConfigBean;
import com.xiaomi.ai.android.utils.d;
import com.xiaomi.ai.b.c;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.transport.a;
import com.xiaomi.onetrack.OneTrack;
import java.security.SecureRandom;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class b {
    private d a;
    private Context b;
    private Runnable c;
    private String d;
    private String e;
    private String f;
    private OkHttpClient g;
    private ScheduledFuture i;
    private int h = 0;
    private SecureRandom j = new SecureRandom();

    public b(d dVar) {
        this.a = dVar;
        this.b = dVar.a();
        AivsConfig b = dVar.b();
        this.d = b.getString(AivsConfig.Auth.CLIENT_ID);
        this.f = b.getString("user_id");
        this.e = dVar.n().getDeviceId().isPresent() ? dVar.n().getDeviceId().get() : "";
        this.g = new OkHttpClient.Builder().addInterceptor(new a()).connectTimeout(b.getInt(AivsConfig.Connection.CONNECT_TIMEOUT), TimeUnit.SECONDS).build();
        this.c = new Runnable() { // from class: com.xiaomi.ai.android.core.b.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (b.class) {
                    b.this.a(0);
                    long b2 = b.this.b(b.this.h);
                    Logger.b("CloudControlManager", "mRequestInterval : " + b.this.h + " min");
                    if (b2 > 0) {
                        b.this.a(b2);
                    } else {
                        b.this.c();
                        b.this.a(b.this.b(b.this.h));
                    }
                }
            }
        };
    }

    private long a(String str, long j) {
        String a = d.a(this.b, "aivs_cloud_control", str);
        try {
            return !f.a(a) ? Long.parseLong(a) : j;
        } catch (NumberFormatException e) {
            Logger.c("CloudControlManager", "get key error key:" + str + " error:" + Logger.throwableToString(e));
            return j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        if (i > 0) {
            int i2 = i / 2;
            this.h = i2 + this.j.nextInt(i2);
            b("cloud_config_interval", this.h);
            return;
        }
        this.h = (int) a("cloud_config_interval", 0L);
        if (this.h <= 0) {
            this.h = this.j.nextInt(60) + 60;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        Logger.b("CloudControlManager", "startNext: " + ((j / 1000) / 60) + " min");
        if (this.c == null) {
            Logger.b("CloudControlManager", "startNext error ,has been released");
            return;
        }
        if (this.i != null) {
            Logger.b("CloudControlManager", "remove last task");
            c.a(this.i);
        }
        try {
            this.i = c.a(this.c, j);
        } catch (RejectedExecutionException e) {
            Logger.d("CloudControlManager", Logger.throwableToString(e));
        }
    }

    private void a(AivsCloudConfigBean.ClearBean clearBean) {
        if (clearBean.isHttpdns()) {
            e();
        }
        if (clearBean.isPublickey()) {
            f();
        }
        if (clearBean.isAESkey()) {
            g();
        }
        if (clearBean.isNmapcache()) {
            h();
        }
        if (clearBean.isLogcache()) {
            i();
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            Logger.b("CloudControlManager", "updateLink mode:set link mode");
            d.a(this.b, "aivs_cloud_control", "link_mode", str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long b(int i) {
        int i2;
        long a = a("cloud_config_last_request_time", 0L);
        if (a >= System.currentTimeMillis() || a < 0) {
            Logger.b("CloudControlManager", "getNextInterval remove error num");
            b("cloud_config_last_request_time");
            return 0L;
        } else if (i2 == 0) {
            return 0L;
        } else {
            return ((i * 60) * 1000) - (System.currentTimeMillis() - a);
        }
    }

    private void b(String str) {
        d.b(this.b, "aivs_cloud_control", str);
    }

    private void b(String str, long j) {
        d.a(this.b, "aivs_cloud_control", str, String.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        b("cloud_config_last_request_time", System.currentTimeMillis());
        AivsCloudConfigBean d = d();
        if (d != null && this.c != null) {
            Logger.b("CloudControlManager", "getCloudConfigFromNet aivsCloudConfigBean:" + d);
            int version = d.getVersion();
            if (version <= 0) {
                Logger.d("CloudControlManager", "getCloudConfig error,illegal version :" + version);
                return;
            }
            int a = (int) a("cloud_config_version", -1L);
            if (!f.a(d.a(this.b, "aivs_cloud_control", "link_mode")) && version == a) {
                Logger.c("CloudControlManager", "getCloudConfig with old version :localVersion " + a + " cloudVersion " + version);
            } else if (f.a(d.getLinkMode())) {
                Logger.c("CloudControlManager", "getCloudConfig error,illegal LinkMode ");
            } else {
                Logger.a("CloudControlManager", "cloudVersion:" + version + " localVersion:" + a);
                b("cloud_config_version", (long) version);
                if (d.getClear() != null) {
                    a(d.getClear());
                }
                a(d.getLinkMode());
                a(d.getRequestInterval());
                Logger.b("CloudControlManager", "next interval:" + this.h);
            }
        }
    }

    private AivsCloudConfigBean d() {
        HttpUrl.Builder newBuilder = HttpUrl.parse(new com.xiaomi.ai.core.c(this.a.b()).j()).newBuilder();
        newBuilder.addQueryParameter("client_id", this.d);
        newBuilder.addQueryParameter("did", this.e);
        newBuilder.addQueryParameter(OneTrack.Param.UID, this.f);
        Logger.a("CloudControlManager", "getCloudConfigFromNet client_id: " + this.d + " did: " + this.e + " uid:" + this.f);
        try {
            Response execute = this.g.newCall(new Request.Builder().url(newBuilder.build()).get().build()).execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
                Logger.b("CloudControlManager", "getCloudConfigFromNet body: " + string);
                return (AivsCloudConfigBean) new Gson().fromJson(string, (Class<Object>) AivsCloudConfigBean.class);
            }
            String response = execute.toString();
            if (execute.body() != null) {
                response = response + ", body=" + execute.body().string();
            }
            Logger.d("CloudControlManager", "parse error" + response);
            return null;
        } catch (Exception e) {
            Logger.d("CloudControlManager", Log.getStackTraceString(e));
            return null;
        }
    }

    private void e() {
        Logger.b("CloudControlManager", "clear httpdns cache");
        com.xiaomi.ai.core.a g = this.a.g();
        if (g == null) {
            Logger.d("CloudControlManager", "clearHttpDns: Channel is null");
            return;
        }
        g.getListener().b(g, "http_dns_cache");
        g.getListener().b(g, "ipv6_http_dns_cache");
    }

    private void f() {
        Logger.b("CloudControlManager", "clear PublicKey");
        synchronized ("pubkey_info") {
            com.xiaomi.ai.core.a g = this.a.g();
            if (g == null) {
                Logger.d("CloudControlManager", "clearPublicKey: Channel is null");
            } else {
                g.getListener().b(g, "pubkey_info");
            }
        }
    }

    private void g() {
        Logger.b("CloudControlManager", "clear AESKey");
        synchronized ("aes_key_info") {
            com.xiaomi.ai.core.a g = this.a.g();
            if (g == null) {
                Logger.d("CloudControlManager", "clearAESKey: Channel is null");
                return;
            }
            g.getListener().b(g, "aes_key");
            g.getListener().b(g, "aes_token");
            g.getListener().b(g, "aes_expire_at");
        }
    }

    private void h() {
        Logger.a("CloudControlManager", "clear NmapCache");
    }

    private void i() {
        Logger.b("CloudControlManager", "clear LogCache");
        TrackCapability trackCapability = (TrackCapability) this.a.a(TrackCapability.class);
        if (trackCapability != null) {
            trackCapability.removeKeyValue("track_failed_info");
        }
        d.b(this.b, "common_track", "track_cached_info");
    }

    public boolean a() {
        if (!this.a.g().getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_CLOUD_CONTROL)) {
            Logger.c("CloudControlManager", "start error ,CloudConfigManager not enable");
            return false;
        } else if (this.c == null) {
            Logger.d("CloudControlManager", "start error ,CloudConfigManager has been released");
            return false;
        } else {
            c.a.execute(this.c);
            return true;
        }
    }

    public void b() {
        ScheduledFuture scheduledFuture = this.i;
        if (scheduledFuture != null) {
            c.a(scheduledFuture);
            this.i = null;
        }
        this.c = null;
    }
}
