package org.repackage.com.vivo.identifier;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes3.dex */
public class IdentifierIdClient {
    private static Context a = null;
    private static boolean b = false;
    private static IdentifierIdObserver c;
    private static IdentifierIdObserver d;
    private static IdentifierIdObserver e;
    private static Object f = new Object();
    private static HandlerThread g = null;
    private static Handler h = null;
    private static String i = null;
    private static String j = null;
    private static String k = null;
    private static String l = null;
    private static String m = null;
    private static volatile IdentifierIdClient n = null;
    private static volatile DataBaseOperation o = null;

    private IdentifierIdClient() {
    }

    public static IdentifierIdClient a(Context context) {
        if (n == null) {
            synchronized (IdentifierIdClient.class) {
                a = context.getApplicationContext();
                n = new IdentifierIdClient();
            }
        }
        if (o == null) {
            synchronized (IdentifierIdClient.class) {
                a = context.getApplicationContext();
                g();
                o = new DataBaseOperation(a);
                d();
            }
        }
        return n;
    }

    private static void g() {
        g = new HandlerThread("SqlWorkThread");
        g.start();
        h = new Handler(g.getLooper()) { // from class: org.repackage.com.vivo.identifier.IdentifierIdClient.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 11) {
                    String unused = IdentifierIdClient.i = IdentifierIdClient.o.a(message.getData().getInt("type"), message.getData().getString("appid"));
                    synchronized (IdentifierIdClient.f) {
                        IdentifierIdClient.f.notify();
                    }
                    return;
                }
                Log.e("VMS_IDLG_SDK_Client", "message type valid");
            }
        };
    }

    public boolean a() {
        return b;
    }

    public String b() {
        if (!a()) {
            return null;
        }
        String str = j;
        if (str != null) {
            return str;
        }
        a(0, (String) null);
        if (c == null) {
            a(a, 0, null);
        }
        return j;
    }

    public String a(String str) {
        if (!a()) {
            return null;
        }
        String str2 = k;
        if (str2 != null) {
            return str2;
        }
        a(1, str);
        if (d == null && k != null) {
            a(a, 1, str);
        }
        return k;
    }

    public String b(String str) {
        if (!a()) {
            return null;
        }
        String str2 = l;
        if (str2 != null) {
            return str2;
        }
        a(2, str);
        if (e == null && l != null) {
            a(a, 2, str);
        }
        return l;
    }

    public String c() {
        if (!a()) {
            return null;
        }
        a(4, (String) null);
        return m;
    }

    public void a(int i2, String str) {
        synchronized (f) {
            b(i2, str);
            long uptimeMillis = SystemClock.uptimeMillis();
            try {
                f.wait(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            if (SystemClock.uptimeMillis() - uptimeMillis < SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                if (i2 != 4) {
                    switch (i2) {
                        case 0:
                            j = i;
                            i = null;
                            break;
                        case 1:
                            if (i == null) {
                                Log.e("VMS_IDLG_SDK_Client", "get vaid failed");
                                break;
                            } else {
                                k = i;
                                i = null;
                                break;
                            }
                        case 2:
                            if (i == null) {
                                Log.e("VMS_IDLG_SDK_Client", "get aaid failed");
                                break;
                            } else {
                                l = i;
                                i = null;
                                break;
                            }
                    }
                }
                m = i;
                i = null;
            } else {
                Log.d("VMS_IDLG_SDK_Client", "query timeout");
            }
        }
    }

    private void b(int i2, String str) {
        Message obtainMessage = h.obtainMessage();
        obtainMessage.what = 11;
        Bundle bundle = new Bundle();
        bundle.putInt("type", i2);
        if (i2 == 1 || i2 == 2) {
            bundle.putString("appid", str);
        }
        obtainMessage.setData(bundle);
        h.sendMessage(obtainMessage);
    }

    public static void d() {
        b = "1".equals(a("persist.sys.identifierid.supported", "0"));
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod(BluetoothConstants.GET, String.class, String.class).invoke(cls, str, "unknown");
            } catch (Exception e2) {
                e2.printStackTrace();
                return str2;
            }
        } catch (Throwable unused) {
            return str2;
        }
    }

    private static void a(Context context, int i2, String str) {
        switch (i2) {
            case 0:
                c = new IdentifierIdObserver(n, 0, null);
                context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), true, c);
                return;
            case 1:
                d = new IdentifierIdObserver(n, 1, str);
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str), false, d);
                return;
            case 2:
                e = new IdentifierIdObserver(n, 2, str);
                ContentResolver contentResolver2 = context.getContentResolver();
                contentResolver2.registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str), false, e);
                return;
            default:
                return;
        }
    }
}
