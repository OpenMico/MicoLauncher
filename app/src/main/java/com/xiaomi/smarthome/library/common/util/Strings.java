package com.xiaomi.smarthome.library.common.util;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class Strings {
    public static List<String> seperateValues(String str, String str2) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, str.split(str2));
        return arrayList;
    }

    public static String convertStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[512];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException e) {
                Log.e("common/Strings", e.toString());
            }
        }
        return byteArrayOutputStream.toString();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
