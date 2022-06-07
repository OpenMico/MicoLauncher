package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.push.am;
import com.xiaomi.push.service.ba;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class de {
    private static volatile de c;
    private final ConcurrentLinkedQueue<b> a = new ConcurrentLinkedQueue<>();
    private Context b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class a extends b {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super();
            de.this = r1;
        }

        @Override // com.xiaomi.push.de.b, com.xiaomi.push.am.b
        public void b() {
            de.this.b();
        }
    }

    /* loaded from: classes4.dex */
    public class b extends am.b {
        long b = System.currentTimeMillis();

        public b() {
            de.this = r3;
        }

        @Override // com.xiaomi.push.am.b
        public void b() {
        }

        public boolean d() {
            return true;
        }

        final boolean e() {
            return System.currentTimeMillis() - this.b > 172800000;
        }
    }

    /* loaded from: classes4.dex */
    class c extends b {
        String a;
        String d;
        File e;
        int f;
        boolean g;
        boolean h;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(String str, String str2, File file, boolean z) {
            super();
            de.this = r1;
            this.a = str;
            this.d = str2;
            this.e = file;
            this.h = z;
        }

        private boolean f() {
            int i;
            SharedPreferences sharedPreferences = de.this.b.getSharedPreferences("log.timestamp", 0);
            String string = sharedPreferences.getString("log.requst", "");
            long currentTimeMillis = System.currentTimeMillis();
            try {
                JSONObject jSONObject = new JSONObject(string);
                currentTimeMillis = jSONObject.getLong("time");
                i = jSONObject.getInt("times");
            } catch (JSONException unused) {
                i = 0;
            }
            if (System.currentTimeMillis() - currentTimeMillis >= 86400000) {
                currentTimeMillis = System.currentTimeMillis();
                i = 0;
            } else if (i > 10) {
                return false;
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("time", currentTimeMillis);
                jSONObject2.put("times", i + 1);
                sharedPreferences.edit().putString("log.requst", jSONObject2.toString()).commit();
            } catch (JSONException e) {
                com.xiaomi.channel.commonutils.logger.b.c("JSONException on put " + e.getMessage());
            }
            return true;
        }

        @Override // com.xiaomi.push.de.b, com.xiaomi.push.am.b
        public void b() {
            try {
                if (f()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(OneTrack.Param.UID, ba.m1145a());
                    hashMap.put("token", this.d);
                    hashMap.put(OneTrack.Param.NET, at.m756a(de.this.b));
                    at.a(this.a, hashMap, this.e, "file");
                }
                this.g = true;
            } catch (IOException unused) {
            }
        }

        @Override // com.xiaomi.push.am.b
        public void c() {
            if (!this.g) {
                this.f++;
                if (this.f < 3) {
                    de.this.a.add(this);
                }
            }
            if (this.g || this.f >= 3) {
                this.e.delete();
            }
            de.this.a((1 << this.f) * 1000);
        }

        @Override // com.xiaomi.push.de.b
        public boolean d() {
            return at.d(de.this.b) || (this.h && at.b(de.this.b));
        }
    }

    private de(Context context) {
        this.b = context;
        this.a.add(new a());
        b(0L);
    }

    public static de a(Context context) {
        if (c == null) {
            synchronized (de.class) {
                if (c == null) {
                    c = new de(context);
                }
            }
        }
        c.b = context;
        return c;
    }

    public void a(long j) {
        b peek = this.a.peek();
        if (peek != null && peek.d()) {
            b(j);
        }
    }

    public void b() {
        if (!ab.b() && !ab.m753a()) {
            try {
                File file = new File(this.b.getExternalFilesDir(null) + "/.logcache");
                if (file.exists() && file.isDirectory()) {
                    for (File file2 : file.listFiles()) {
                        file2.delete();
                    }
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    private void b(long j) {
        if (!this.a.isEmpty()) {
            gq.a(new da(this), j);
        }
    }

    private void c() {
        while (!this.a.isEmpty()) {
            b peek = this.a.peek();
            if (peek != null) {
                if (peek.e() || this.a.size() > 6) {
                    com.xiaomi.channel.commonutils.logger.b.c("remove Expired task");
                    this.a.remove(peek);
                } else {
                    return;
                }
            }
        }
    }

    public void a() {
        c();
        a(0L);
    }

    public void a(String str, String str2, Date date, Date date2, int i, boolean z) {
        this.a.add(new cx(this, i, date, date2, str, str2, z));
        b(0L);
    }
}
