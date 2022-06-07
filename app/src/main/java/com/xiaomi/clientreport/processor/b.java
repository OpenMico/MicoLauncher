package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.data.a;
import com.xiaomi.push.bf;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class b implements IPerfProcessor {
    protected Context a;
    private HashMap<String, HashMap<String, a>> b;

    public b(Context context) {
        this.a = context;
    }

    public static String a(a aVar) {
        return String.valueOf(aVar.production) + "#" + aVar.clientInterfaceId;
    }

    private String b(a aVar) {
        String str = "";
        int i = aVar.production;
        String str2 = aVar.clientInterfaceId;
        if (i > 0 && !TextUtils.isEmpty(str2)) {
            str = String.valueOf(i) + "#" + str2;
        }
        File externalFilesDir = this.a.getExternalFilesDir("perf");
        if (externalFilesDir == null) {
            com.xiaomi.channel.commonutils.logger.b.d("cannot get folder when to write perf");
            return null;
        }
        if (!externalFilesDir.exists()) {
            externalFilesDir.mkdirs();
        }
        return new File(externalFilesDir, str).getAbsolutePath();
    }

    private String c(a aVar) {
        String b = b(aVar);
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        for (int i = 0; i < 20; i++) {
            String str = b + i;
            if (bf.m778a(this.a, str)) {
                return str;
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.c
    public void a() {
        bf.a(this.a, "perf", "perfUploading");
        File[] a = bf.m779a(this.a, "perfUploading");
        if (a != null && a.length > 0) {
            for (File file : a) {
                if (file != null) {
                    List<String> a2 = e.a(this.a, file.getAbsolutePath());
                    file.delete();
                    a(a2);
                }
            }
        }
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a */
    public void mo151a(a aVar) {
        if ((aVar instanceof PerfClientReport) && this.b != null) {
            PerfClientReport perfClientReport = (PerfClientReport) aVar;
            String a = a((a) perfClientReport);
            String a2 = e.a(perfClientReport);
            HashMap<String, a> hashMap = this.b.get(a);
            if (hashMap == null) {
                hashMap = new HashMap<>();
            }
            PerfClientReport perfClientReport2 = (PerfClientReport) hashMap.get(a2);
            if (perfClientReport2 != null) {
                perfClientReport.perfCounts += perfClientReport2.perfCounts;
                perfClientReport.perfLatencies += perfClientReport2.perfLatencies;
            }
            hashMap.put(a2, perfClientReport);
            this.b.put(a, hashMap);
        }
    }

    public void a(List<String> list) {
        bf.a(this.a, list);
    }

    public void a(a[] aVarArr) {
        String c = c(aVarArr[0]);
        if (!TextUtils.isEmpty(c)) {
            e.a(c, aVarArr);
        }
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() {
        HashMap<String, HashMap<String, a>> hashMap = this.b;
        if (hashMap != null) {
            if (hashMap.size() > 0) {
                for (String str : this.b.keySet()) {
                    HashMap<String, a> hashMap2 = this.b.get(str);
                    if (hashMap2 != null && hashMap2.size() > 0) {
                        a[] aVarArr = new a[hashMap2.size()];
                        hashMap2.values().toArray(aVarArr);
                        a(aVarArr);
                    }
                }
            }
            this.b.clear();
        }
    }

    @Override // com.xiaomi.clientreport.processor.IPerfProcessor
    public void setPerfMap(HashMap<String, HashMap<String, a>> hashMap) {
        this.b = hashMap;
    }
}
