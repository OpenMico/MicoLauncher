package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.az;
import com.xiaomi.push.hk;
import com.xiaomi.push.hl;
import com.xiaomi.push.hr;
import com.xiaomi.push.ig;
import com.xiaomi.push.ir;
import com.xiaomi.push.u;
import com.xiaomi.push.z;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class be {
    private static AtomicLong a = new AtomicLong(0);
    private static SimpleDateFormat b = new SimpleDateFormat("yyyy/MM/dd");
    private static String c = b.format(Long.valueOf(System.currentTimeMillis()));

    public static synchronized String a() {
        String str;
        synchronized (be.class) {
            String format = b.format(Long.valueOf(System.currentTimeMillis()));
            if (!TextUtils.equals(c, format)) {
                a.set(0L);
                c = format;
            }
            str = format + Constants.ACCEPT_TIME_SEPARATOR_SERVER + a.incrementAndGet();
        }
        return str;
    }

    public static ArrayList<ig> a(List<hl> list, String str, String str2, int i) {
        String str3;
        if (list == null) {
            str3 = "requests can not be null in TinyDataHelper.transToThriftObj().";
        } else if (list.size() == 0) {
            str3 = "requests.length is 0 in TinyDataHelper.transToThriftObj().";
        } else {
            ArrayList<ig> arrayList = new ArrayList<>();
            hk hkVar = new hk();
            int i2 = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                hl hlVar = list.get(i3);
                if (hlVar != null) {
                    int length = ir.a(hlVar).length;
                    if (length > i) {
                        b.d("TinyData is too big, ignore upload request item:" + hlVar.d());
                    } else {
                        if (i2 + length > i) {
                            ig igVar = new ig(DataModel.CIRCULATEFAIL_NO_SUPPORT, false);
                            igVar.d(str);
                            igVar.b(str2);
                            igVar.c(hr.UploadTinyData.f67a);
                            igVar.a(z.a(ir.a(hkVar)));
                            arrayList.add(igVar);
                            hkVar = new hk();
                            i2 = 0;
                        }
                        hkVar.a(hlVar);
                        i2 += length;
                    }
                }
            }
            if (hkVar.a() != 0) {
                ig igVar2 = new ig(DataModel.CIRCULATEFAIL_NO_SUPPORT, false);
                igVar2.d(str);
                igVar2.b(str2);
                igVar2.c(hr.UploadTinyData.f67a);
                igVar2.a(z.a(ir.a(hkVar)));
                arrayList.add(igVar2);
            }
            return arrayList;
        }
        b.d(str3);
        return null;
    }

    public static void a(Context context, String str, String str2, long j, String str3) {
        hl hlVar = new hl();
        hlVar.d(str);
        hlVar.c(str2);
        hlVar.a(j);
        hlVar.b(str3);
        hlVar.a("push_sdk_channel");
        hlVar.g(context.getPackageName());
        hlVar.e(context.getPackageName());
        hlVar.a(true);
        hlVar.b(System.currentTimeMillis());
        hlVar.f(a());
        bf.a(context, hlVar);
    }

    public static boolean a(hl hlVar, boolean z) {
        String str;
        if (hlVar == null) {
            str = "item is null, verfiy ClientUploadDataItem failed.";
        } else if (!z && TextUtils.isEmpty(hlVar.f53a)) {
            str = "item.channel is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(hlVar.d)) {
            str = "item.category is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(hlVar.c)) {
            str = "item.name is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (!az.m761a(hlVar.d)) {
            str = "item.category can only contain ascii char, verfiy ClientUploadDataItem failed.";
        } else if (!az.m761a(hlVar.c)) {
            str = "item.name can only contain ascii char, verfiy ClientUploadDataItem failed.";
        } else if (hlVar.f57b == null || hlVar.f57b.length() <= 10240) {
            return false;
        } else {
            str = "item.data is too large(" + hlVar.f57b.length() + "), max size for data is 10240 , verfiy ClientUploadDataItem failed.";
        }
        b.m149a(str);
        return true;
    }

    public static boolean a(String str) {
        return !u.b() || Constants.HYBRID_PACKAGE_NAME.equals(str);
    }
}
