package com.xiaomi.push.service;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ct;
import com.xiaomi.push.ee;
import com.xiaomi.push.gz;
import com.xiaomi.push.m;
import com.xiaomi.push.z;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class ac {
    private static final Pattern a = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
    private static long b = 0;
    private static ThreadPoolExecutor c = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue(), new m("nc"));

    public static void a() {
        ee.a a2;
        long currentTimeMillis = System.currentTimeMillis();
        if ((c.getActiveCount() <= 0 || currentTimeMillis - b >= 1800000) && gz.m951a().m952a() && (a2 = ba.a().m1146a()) != null && a2.e() > 0) {
            b = currentTimeMillis;
            a(a2.mo888a(), true);
        }
    }

    public static void a(List<String> list, boolean z) {
        c.execute(new h(list, z));
    }

    public static void b() {
        String c2 = c("/proc/self/net/tcp");
        if (!TextUtils.isEmpty(c2)) {
            b.m149a("dump tcp for uid = " + Process.myUid());
            b.m149a(c2);
        }
        String c3 = c("/proc/self/net/tcp6");
        if (!TextUtils.isEmpty(c3)) {
            b.m149a("dump tcp6 for uid = " + Process.myUid());
            b.m149a(c3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            b.m149a("ConnectivityTest: begin to connect to " + str);
            Socket socket = new Socket();
            socket.connect(ct.m813a(str, 5222), 5000);
            socket.setTcpNoDelay(true);
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            b.m149a("ConnectivityTest: connect to " + str + " in " + currentTimeMillis2);
            socket.close();
            return true;
        } catch (Throwable th) {
            b.d("ConnectivityTest: could not connect to:" + str + " exception: " + th.getClass().getSimpleName() + " description: " + th.getMessage());
            return false;
        }
    }

    private static String c(String str) {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append("\n");
                        sb.append(readLine);
                    } else {
                        String sb2 = sb.toString();
                        z.a(bufferedReader);
                        return sb2;
                    }
                }
            } catch (Exception unused) {
                z.a(bufferedReader);
                return null;
            } catch (Throwable th2) {
                th = th2;
                z.a(bufferedReader);
                throw th;
            }
        } catch (Exception unused2) {
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }
}
