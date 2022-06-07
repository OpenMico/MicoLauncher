package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.be;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class he {
    private static HashMap<String, ArrayList<hl>> a(Context context, List<hl> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap<String, ArrayList<hl>> hashMap = new HashMap<>();
        for (hl hlVar : list) {
            a(context, hlVar);
            ArrayList<hl> arrayList = hashMap.get(hlVar.c());
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                hashMap.put(hlVar.c(), arrayList);
            }
            arrayList.add(hlVar);
        }
        return hashMap;
    }

    private static void a(Context context, hg hgVar, HashMap<String, ArrayList<hl>> hashMap) {
        for (Map.Entry<String, ArrayList<hl>> entry : hashMap.entrySet()) {
            try {
                ArrayList<hl> value = entry.getValue();
                if (!(value == null || value.size() == 0)) {
                    b.m149a("TinyData is uploaded immediately item size:" + value.size());
                    hgVar.a(value, value.get(0).e(), entry.getKey());
                }
            } catch (Exception unused) {
            }
        }
    }

    public static void a(Context context, hg hgVar, List<hl> list) {
        HashMap<String, ArrayList<hl>> a = a(context, list);
        if (a == null || a.size() == 0) {
            b.m149a("TinyData TinyDataCacheUploader.uploadTinyData itemsUploading == null || itemsUploading.size() == 0  ts:" + System.currentTimeMillis());
            return;
        }
        a(context, hgVar, a);
    }

    private static void a(Context context, hl hlVar) {
        if (hlVar.f56a) {
            hlVar.a("push_sdk_channel");
        }
        if (TextUtils.isEmpty(hlVar.d())) {
            hlVar.f(be.a());
        }
        hlVar.b(System.currentTimeMillis());
        if (TextUtils.isEmpty(hlVar.e())) {
            hlVar.e(context.getPackageName());
        }
        if (TextUtils.isEmpty(hlVar.c())) {
            hlVar.e(hlVar.e());
        }
    }
}
