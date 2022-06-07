package org.hapjs.sdk.platform;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes5.dex */
class b {
    public static String a() {
        return a("https://statres.quickapp.cn/quickapp/quickapptool/release/platform/platforms");
    }

    private static String a(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.getResponseCode();
                if (httpURLConnection.getResponseCode() != 200) {
                    httpURLConnection.disconnect();
                    return null;
                }
                byte[] a = a(httpURLConnection.getInputStream());
                httpURLConnection.disconnect();
                if (a != null && a.length != 0) {
                    return new String(a);
                }
                Log.d("ContentValues", "Response data is empty");
                return null;
            } catch (Exception e) {
                Log.d("ContentValues", "connect exception ", e);
                httpURLConnection.disconnect();
                return null;
            }
        } catch (Exception e2) {
            Log.d("ContentValues", "connect exception ", e2);
            return null;
        }
    }

    private static byte[] a(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
        }
    }
}
