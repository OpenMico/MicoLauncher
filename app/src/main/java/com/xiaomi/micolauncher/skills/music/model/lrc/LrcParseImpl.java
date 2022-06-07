package com.xiaomi.micolauncher.skills.music.model.lrc;

import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class LrcParseImpl {
    public List<LrcRow> getLrcRows(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return new ArrayList();
        }
        BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    List<LrcRow> a = LrcRow.a(readLine);
                    if (a != null && a.size() > 0) {
                        arrayList.addAll(a);
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                L.player.e("parse lrc error", e2);
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return null;
            }
        }
        Collections.sort(arrayList);
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            int i3 = i2 + 1;
            ((LrcRow) arrayList.get(i2)).setTotalTime(((LrcRow) arrayList.get(i3)).getTimeMillis() - ((LrcRow) arrayList.get(i2)).getTimeMillis());
            i2 = i3;
        }
        if (size >= 1) {
            ((LrcRow) arrayList.get(i)).setTotalTime(5000);
        }
        try {
            bufferedReader.close();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return arrayList;
    }
}
