package com.xiaomi.micolauncher.common;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes3.dex */
public class AssetsUtil {
    public static String getStringFromAssets(Context context, String str) {
        Throwable th;
        InputStreamReader inputStreamReader;
        String str2;
        IOException e;
        InputStreamReader inputStreamReader2;
        String readLine;
        try {
            try {
                str2 = "";
                inputStreamReader = null;
                try {
                    inputStreamReader2 = new InputStreamReader(context.getResources().getAssets().open(str));
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader2);
            StringBuilder sb = new StringBuilder();
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            str2 = sb.toString();
            inputStreamReader2.close();
            inputStreamReader = readLine;
        } catch (IOException e4) {
            e = e4;
            inputStreamReader = inputStreamReader2;
            L.base.e("AssetsUtil$getStringFromAssets failed: ", e);
            if (inputStreamReader != null) {
                inputStreamReader.close();
                inputStreamReader = inputStreamReader;
            }
            return str2;
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = inputStreamReader2;
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
        return str2;
    }
}
