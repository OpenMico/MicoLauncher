package com.efs.sdk.base.a.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.c.c;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.newsharedpreferences.SharedPreferencesUtils;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class b extends Handler {
    private static final Map<String, Long> a = new HashMap<String, Long>() { // from class: com.efs.sdk.base.a.e.b.1
        {
            put("flow_5min", 300000L);
            put("flow_hour", 3600000L);
            put("flow_day", 86400000L);
        }
    };
    private static final Map<String, Long> b = new HashMap<String, Long>() { // from class: com.efs.sdk.base.a.e.b.2
        {
            put("flow_5min", 1048576L);
            put("flow_hour", 1048576L);
            put("flow_day", Long.valueOf((long) PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE));
        }
    };
    private Map<String, AtomicInteger> c;
    private volatile SharedPreferences d;
    private volatile SharedPreferences.Editor e;
    private Context f;
    private String g;

    /* synthetic */ b(byte b2) {
        this();
    }

    private b() {
        super(com.efs.sdk.base.a.h.a.a.a.getLooper());
        this.c = new ConcurrentHashMap(5);
        this.f = com.efs.sdk.base.a.d.a.a().c;
        this.g = com.efs.sdk.base.a.d.a.a().a;
        b();
        File c = com.efs.sdk.base.a.h.a.c(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a);
        if (c.exists()) {
            com.efs.sdk.base.a.h.b.b(c);
        }
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final b a = new b((byte) 0);
    }

    public static b a() {
        return a.a;
    }

    @Override // android.os.Handler
    public void handleMessage(@NonNull Message message) {
        c cVar;
        super.handleMessage(message);
        switch (message.what) {
            case 0:
                b();
                if (this.d == null) {
                    d.a("efs.flow", "sharedpreferences is null, cann't get last flow stat", null);
                    return;
                } else if (this.e == null) {
                    d.a("efs.flow", "sharedpreferences editor is null, cann't refresh flow stat", null);
                    return;
                } else {
                    String valueOf = String.valueOf(message.obj);
                    long j = message.arg1;
                    cVar = c.a.a;
                    String a2 = cVar.a();
                    for (String str : a.keySet()) {
                        String concat = "curr_time_".concat(String.valueOf(str));
                        if (!this.d.contains(concat)) {
                            this.e.putLong(concat, System.currentTimeMillis());
                        }
                        for (String str2 : a(str, valueOf, a2)) {
                            this.e.putLong(str2, this.d.getLong(str2, 0L) + j);
                        }
                    }
                    this.e.apply();
                    return;
                }
            case 1:
                String valueOf2 = String.valueOf(message.obj);
                long j2 = message.arg1;
                b();
                if (this.d == null) {
                    d.a("efs.flow", "sharedpreferences is null, cann't get last refresh timestamp", null);
                    return;
                } else if (this.e == null) {
                    d.a("efs.flow", "sharedpreferences editor is null, cann't refresh timestamp", null);
                    return;
                } else {
                    String concat2 = "curr_time_".concat(String.valueOf(valueOf2));
                    if (Math.abs(System.currentTimeMillis() - this.d.getLong(concat2, System.currentTimeMillis())) >= j2) {
                        for (String str3 : this.d.getAll().keySet()) {
                            if (str3.startsWith(valueOf2)) {
                                this.e.putLong(str3, 0L);
                            }
                        }
                        this.e.putLong(concat2, System.currentTimeMillis());
                        this.e.apply();
                        this.c.clear();
                        return;
                    }
                    return;
                }
            default:
                d.a("efs.flow", "flow stat listener not support action '" + message.what + LrcRow.SINGLE_QUOTE, null);
                return;
        }
    }

    private void b() {
        try {
            c();
        } catch (Throwable th) {
            d.b("efs.flow", "init sharedpreferences error", th);
        }
    }

    private void c() {
        if (this.d == null) {
            synchronized (b.class) {
                if (this.d == null) {
                    this.d = SharedPreferencesUtils.getSharedPreferences(this.f, this.g.toLowerCase() + "_flow");
                }
            }
        }
        if (this.e == null) {
            synchronized (b.class) {
                if (this.e == null) {
                    this.e = this.d.edit();
                }
            }
        }
    }

    private boolean a(@NonNull String str, long j, @NonNull String str2, @NonNull String str3, long j2) {
        b();
        if (this.d == null) {
            d.a("efs.flow", "sharedpreferences is null, cann't get last flow stat", null);
            return false;
        }
        List<String> a2 = a(str, str2, str3);
        Map<String, String> c = com.efs.sdk.base.a.c.a.c.a().c();
        for (String str4 : a2) {
            if (Math.abs(System.currentTimeMillis() - this.d.getLong("curr_time_".concat(String.valueOf(str)), System.currentTimeMillis())) > j) {
                Message obtain = Message.obtain();
                obtain.what = 1;
                obtain.obj = str;
                obtain.arg1 = Long.valueOf(j).intValue();
                sendMessage(obtain);
            }
            long a3 = a(c, str, str4);
            long j3 = this.d.getLong(str4, 0L);
            if (j3 + j2 > a3) {
                d.a("efs.flow", "flow limit, key: " + str4 + ", max: " + a3 + ", now: " + j3 + ", size: " + j2);
                a(str4);
                return false;
            }
        }
        return true;
    }

    private void a(String str) {
        f fVar;
        f fVar2;
        if (!this.c.containsKey(str) || this.c.get(str) == null || this.c.get(str).get() <= 10) {
            fVar = f.a.a;
            fVar.a(com.efs.sdk.base.a.c.a.c.a().d.a, str);
            if (str.equals("flow_day")) {
                fVar2 = f.a.a;
                fVar2.a(com.efs.sdk.base.a.c.a.c.a().d.a);
            }
            if (!this.c.containsKey(str)) {
                this.c.put(str, new AtomicInteger());
            }
            this.c.get(str).incrementAndGet();
        }
    }

    private static long a(Map<String, String> map, @NonNull String str, @NonNull String str2) {
        long longValue = b.get(str).longValue();
        if (map == null || !map.containsKey(str2) || TextUtils.isEmpty(map.get(str2))) {
            return longValue;
        }
        try {
            return Long.parseLong(map.get(str2));
        } catch (Throwable th) {
            d.a("efs.flow", "get max flow error", th);
            return longValue;
        }
    }

    private static List<String> a(String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str + "_" + str2);
        }
        if (!TextUtils.isEmpty(str3) && !"unknown".equalsIgnoreCase(str3)) {
            arrayList.add(str + "_" + str3);
        }
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            arrayList.add(str + "_" + str2 + "_" + str3);
        }
        return arrayList;
    }

    public final boolean a(@NonNull String str, long j) {
        c cVar;
        cVar = c.a.a;
        String a2 = cVar.a();
        boolean z = true;
        for (Map.Entry<String, Long> entry : a.entrySet()) {
            z = a(entry.getKey(), entry.getValue().longValue(), str, a2, j);
            if (!z) {
                break;
            }
        }
        return z;
    }
}
