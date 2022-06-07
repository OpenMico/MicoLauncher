package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ad;
import com.xiaomi.push.aj;
import com.xiaomi.push.aw;
import com.xiaomi.push.az;
import com.xiaomi.push.h;
import com.xiaomi.push.hl;
import com.xiaomi.push.ir;
import com.xiaomi.push.q;
import com.xiaomi.push.z;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class bf {
    public static final Object a = new Object();

    public static void a(Context context, hl hlVar) {
        if (be.a(hlVar.e())) {
            b.m149a("TinyData TinyDataStorage.cacheTinyData cache data to file begin item:" + hlVar.d() + "  ts:" + System.currentTimeMillis());
            aj.a(context).a(new aa(context, hlVar));
        }
    }

    public static byte[] a(Context context) {
        String a2 = q.a(context).a("mipush", "td_key", "");
        if (TextUtils.isEmpty(a2)) {
            a2 = az.a(20);
            q.a(context).m1116a("mipush", "td_key", a2);
        }
        return a(a2);
    }

    private static byte[] a(String str) {
        byte[] copyOf = Arrays.copyOf(aw.a(str), 16);
        copyOf[0] = 68;
        copyOf[15] = 84;
        return copyOf;
    }

    public static void c(Context context, hl hlVar) {
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        String str;
        Exception e;
        String str2;
        try {
            try {
                byte[] b = h.b(a(context), ir.a(hlVar));
                if (b != null && b.length >= 1) {
                    if (b.length > 10240) {
                        str2 = "TinyData write to cache file failed case too much data content item:" + hlVar.d() + "  ts:" + System.currentTimeMillis();
                        b.m149a(str2);
                        z.a((Closeable) null);
                        z.a((Closeable) null);
                    }
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(new File(context.getFilesDir(), "tiny_data.data"), true));
                    try {
                        bufferedOutputStream2.write(ad.a(b.length));
                        bufferedOutputStream2.write(b);
                        bufferedOutputStream2.flush();
                        b.m149a("TinyData write to cache file success item:" + hlVar.d() + "  ts:" + System.currentTimeMillis());
                        z.a((Closeable) null);
                        z.a(bufferedOutputStream2);
                        return;
                    } catch (IOException e2) {
                        e = e2;
                        bufferedOutputStream = bufferedOutputStream2;
                        str = "TinyData write to cache file failed cause io exception item:" + hlVar.d();
                        b.a(str, e);
                        z.a((Closeable) null);
                        z.a(bufferedOutputStream);
                        return;
                    } catch (Exception e3) {
                        e = e3;
                        bufferedOutputStream = bufferedOutputStream2;
                        str = "TinyData write to cache file  failed item:" + hlVar.d();
                        b.a(str, e);
                        z.a((Closeable) null);
                        z.a(bufferedOutputStream);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = bufferedOutputStream2;
                        z.a((Closeable) null);
                        z.a(bufferedOutputStream);
                        throw th;
                    }
                }
                str2 = "TinyData write to cache file failed case encryption fail item:" + hlVar.d() + "  ts:" + System.currentTimeMillis();
                b.m149a(str2);
                z.a((Closeable) null);
                z.a((Closeable) null);
            } catch (IOException e4) {
                e = e4;
                bufferedOutputStream = null;
            } catch (Exception e5) {
                e = e5;
                bufferedOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }
}
