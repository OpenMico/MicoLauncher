package com.xiaomi.ai.b;

import com.xiaomi.ai.log.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes3.dex */
public final class d {
    public static String a(final String str, int i) {
        String str2;
        Throwable e;
        String str3;
        StringBuilder sb;
        Future submit = c.a.submit(new Callable<String>() { // from class: com.xiaomi.ai.b.d.1
            /* renamed from: a */
            public String call() {
                InetAddress[] allByName = InetAddress.getAllByName(str);
                for (InetAddress inetAddress : allByName) {
                    if (d.a(inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
                return null;
            }
        });
        try {
            return (String) submit.get(i, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
            e = e2;
            str2 = "NetworkUtils";
            sb = new StringBuilder();
            str3 = "interrupted:";
            sb.append(str3);
            sb.append(Logger.throwableToString(e));
            Logger.d(str2, sb.toString());
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            str2 = "NetworkUtils";
            sb = new StringBuilder();
            str3 = "execution:";
            sb.append(str3);
            sb.append(Logger.throwableToString(e));
            Logger.d(str2, sb.toString());
            return null;
        } catch (TimeoutException e4) {
            submit.cancel(true);
            Logger.d("NetworkUtils", "timeout:" + Logger.throwableToString(e4));
            return null;
        }
    }

    public static String a(String str, Map<String, String> map) {
        InputStreamReader inputStreamReader;
        StringBuffer stringBuffer = new StringBuffer();
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        if (map != null) {
            for (String str2 : map.keySet()) {
                httpURLConnection.setRequestProperty(str2, map.get(str2));
                Logger.b("NetworkUtils", "header:" + str2 + Constants.ACCEPT_TIME_SEPARATOR_SP + map.get(str2));
            }
        }
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() != 200) {
            inputStreamReader = new InputStreamReader(httpURLConnection.getErrorStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine + "\n");
            }
        } else {
            inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine2 = bufferedReader2.readLine();
                if (readLine2 == null) {
                    break;
                }
                stringBuffer.append(readLine2 + "\n");
            }
        }
        inputStreamReader.close();
        return stringBuffer.toString();
    }

    public static boolean a(String str) {
        return str.matches("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }
}
