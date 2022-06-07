package com.efs.sdk.base.a.c.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import com.efs.sdk.base.IConfigRefreshAction;
import com.efs.sdk.base.a.e.f;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.observer.IConfigCallback;
import com.efs.sdk.base.observer.IEfsReporterObserver;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class c implements Handler.Callback {
    public static final Random a = new Random();
    public IConfigRefreshAction b;
    public boolean c;
    public b d;
    public Map<IConfigCallback, String[]> e;
    private Handler f;
    private e g;
    private long h;

    /* synthetic */ c(byte b) {
        this();
    }

    private c() {
        this.c = true;
        this.e = new HashMap();
        this.f = new Handler(com.efs.sdk.base.a.h.a.a.a.getLooper(), this);
        this.g = new e();
        this.d = b.a();
        this.h = com.efs.sdk.base.a.d.a.a.j;
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final c a = new c((byte) 0);
    }

    public static c a() {
        return a.a;
    }

    public final void b() {
        this.f.sendEmptyMessage(0);
        this.f.sendEmptyMessageDelayed(2, this.h);
    }

    public final void a(int i) {
        if (i <= this.d.a) {
            d.a("efs.config", "current config version is " + i + ", no need to refresh");
            return;
        }
        Message obtain = Message.obtain();
        obtain.arg1 = i;
        obtain.what = 1;
        this.f.sendMessage(obtain);
    }

    public final Map<String, String> c() {
        return new HashMap(this.d.e);
    }

    public final String a(boolean z) {
        if (z) {
            return "https://" + this.d.c;
        }
        return this.d.b + this.d.c;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(@NonNull Message message) {
        b bVar;
        f fVar;
        switch (message.what) {
            case 0:
                boolean a2 = e.a();
                d.a("efs.config", "--->>> 删除旧的配置文件 ".concat(String.valueOf(a2)));
                if (a2) {
                    this.f.sendEmptyMessage(1);
                    break;
                } else {
                    e eVar = this.g;
                    eVar.c();
                    if (eVar.a == null) {
                        bVar = null;
                    } else {
                        b a3 = b.a();
                        a3.a = eVar.a.getInt("cver", -1);
                        Set<String> keySet = eVar.a.getAll().keySet();
                        HashMap hashMap = new HashMap();
                        for (String str : keySet) {
                            String string = eVar.a.getString(str, "");
                            if (!TextUtils.isEmpty(string)) {
                                hashMap.put(str, string);
                            }
                        }
                        a3.a(hashMap);
                        bVar = a3;
                    }
                    if (bVar != null) {
                        if (a(bVar)) {
                            d.a("efs.config", "--->>> 一致 ");
                            break;
                        } else {
                            this.d = bVar;
                            String str2 = "load config from storage";
                            if (-1 != this.d.a) {
                                i();
                                d();
                                str2 = str2 + " and notify observer";
                            }
                            d.a("efs.config", str2);
                            break;
                        }
                    } else {
                        d.a("efs.config", "--->>> 首次启动本地无配置或加载失败 ");
                        break;
                    }
                }
            case 1:
                int i = message.arg1;
                if (i <= this.d.a) {
                    d.a("efs.config", "current config version is " + i + ", no need to refresh");
                    d.a("efs.config", "current config version(" + this.d.a + ") is " + i + ", no need to refresh");
                    break;
                } else {
                    e();
                    break;
                }
            case 2:
                fVar = f.a.a;
                if (fVar.a()) {
                    if (!h()) {
                        d.a("efs.config", "No update is required, less than 8h since the last update");
                        break;
                    } else {
                        e();
                        break;
                    }
                }
                break;
            case 3:
                f();
                break;
        }
        return true;
    }

    public final void a(String str) {
        b a2 = b.a();
        if (!d.a(str, a2)) {
            this.f.sendEmptyMessageDelayed(1, 3000L);
        } else if (!a(a2)) {
            this.d = a2;
            f();
            i();
            d();
        }
    }

    private void f() {
        boolean z;
        try {
            z = this.g.a(this.d);
        } catch (Throwable unused) {
            z = false;
        }
        if (!z) {
            this.f.sendEmptyMessageDelayed(3, 3000L);
        }
    }

    @NonNull
    private IConfigRefreshAction g() {
        IConfigRefreshAction iConfigRefreshAction = this.b;
        return iConfigRefreshAction == null ? a.a() : iConfigRefreshAction;
    }

    private boolean h() {
        e.b();
        long j = 0;
        try {
            e eVar = this.g;
            eVar.c();
            if (eVar.a != null) {
                j = eVar.a.getLong("last_refresh_time", 0L);
            }
        } catch (Throwable unused) {
        }
        return System.currentTimeMillis() - j >= 28800000;
    }

    private boolean a(b bVar) {
        if (this.d.a >= bVar.a) {
            return true;
        }
        d.a("efs.config", "current config version (" + this.d.a + ") is older than another (" + bVar.a + ")");
        return false;
    }

    public final void d() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.efs.sdk.base.a.c.a.c.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    for (IConfigCallback iConfigCallback : c.this.e.keySet()) {
                        String[] strArr = (String[]) c.this.e.get(iConfigCallback);
                        HashMap hashMap = new HashMap();
                        if (!(strArr == null || strArr.length == 0)) {
                            for (String str : strArr) {
                                if (c.this.d.e.containsKey(str)) {
                                    hashMap.put(str, c.this.c().get(str));
                                    d.a("efs.config", "--->>> configCallback key is " + str + " ## value is " + c.this.c().get(str));
                                }
                            }
                        }
                        iConfigCallback.onChange(hashMap);
                    }
                    c.this.e.clear();
                } catch (Throwable unused) {
                }
            }
        });
    }

    private void e() {
        f fVar;
        fVar = f.a.a;
        if (!fVar.a()) {
            d.a("efs.config", "has no permission to refresh config from remote");
        } else if (!this.c) {
            d.a("efs.config", "disable refresh config from remote");
        } else {
            String refresh = g().refresh();
            d.a("efs.config", "efs config is ".concat(String.valueOf(refresh)));
            if (!TextUtils.isEmpty(refresh)) {
                a(refresh);
            }
        }
    }

    private void i() {
        try {
            for (ValueCallback<Pair<Message, Message>> valueCallback : com.efs.sdk.base.a.d.a.a.a(1)) {
                Message obtain = Message.obtain(null, 1, new JSONObject((Map) this.d.e).toString());
                Message obtain2 = Message.obtain();
                valueCallback.onReceiveValue(new Pair<>(obtain, obtain2));
                obtain.recycle();
                obtain2.recycle();
            }
            for (IEfsReporterObserver iEfsReporterObserver : com.efs.sdk.base.a.d.a.a.o) {
                iEfsReporterObserver.onConfigChange();
            }
        } catch (Throwable th) {
            d.b("efs.base", "efs.config", th);
        }
    }
}
