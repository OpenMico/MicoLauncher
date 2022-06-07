package com.xiaomi.miplay.mylibrary;

import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class MiplayMdns {
    public static final int MDNS_RECORDTYPE_A = 1;
    public static final int MDNS_RECORDTYPE_AAAA = 28;
    public static final int MDNS_RECORDTYPE_ANY = 255;
    public static final int MDNS_RECORDTYPE_PTR = 12;
    public static final int MDNS_RECORDTYPE_SRV = 33;
    public static final int MDNS_RECORDTYPE_TXT = 16;
    private final AtomicBoolean a = new AtomicBoolean(false);
    private Thread b = null;
    private final Object c = new Object();
    private long d = alloc();
    private MdnsCallback e;

    /* loaded from: classes4.dex */
    public interface MdnsCallback {
        void onServiceFound(int i, String str, int i2, String str2, String str3, int i3, byte[] bArr, byte[] bArr2);
    }

    private native long alloc();

    private native int free(long j);

    private native int send_query(long j, int i, String str, int i2, int i3);

    static {
        System.loadLibrary("audiomirror-jni");
    }

    public int query(int i, String str, int i2, int i3) {
        long j = this.d;
        if (j <= 0) {
            return -1;
        }
        return send_query(j, i, str, i2, i3);
    }

    public int free() {
        this.a.set(true);
        long j = this.d;
        if (j <= 0) {
            return -1;
        }
        free(j);
        this.d = 0L;
        return 0;
    }

    public void onServiceFound(int i, String str, int i2, String str2, String str3, int i3, byte[] bArr, byte[] bArr2) {
        Log.d("MiplayMdns", "onServiceFound() called with: query_id = [" + i + "], addr = [" + str + "], port = [" + i2 + "], hostname = [" + str2 + "], service_name = [" + str3 + "], service_port = [" + i3 + "], appdata = [" + bArr.length + "], raw_data = [" + bArr2.length + "]");
        MdnsCallback mdnsCallback = this.e;
        if (mdnsCallback != null) {
            mdnsCallback.onServiceFound(i, str, i2, str2, str3, i3, bArr, bArr2);
        }
    }

    public void setMdnsCallback(MdnsCallback mdnsCallback) {
        this.e = mdnsCallback;
    }

    public void startQuery(final int i, final String str, final int i2, final int i3, final int i4) {
        Log.i("MiplayMdns", "start mdns query");
        synchronized (this.c) {
            try {
                if (this.b != null) {
                    this.a.set(true);
                    this.b.join(1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.a.set(false);
            this.b = new Thread(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiplayMdns.1
                @Override // java.lang.Runnable
                public void run() {
                    while (!MiplayMdns.this.a.get()) {
                        try {
                            Log.d("MiplayMdns", "process mdns query");
                            MiplayMdns.this.query(i, str, i2, i3);
                            for (int i5 = 0; !MiplayMdns.this.a.get() && i5 <= i4; i5 += 100) {
                                Thread.sleep(100);
                            }
                        } catch (Exception e2) {
                            Log.e("MiplayMdns", "runCaptureAudio Exception:" + e2);
                            e2.printStackTrace();
                        }
                    }
                    Log.i("MiplayMdns", "exit mdns query");
                }
            }, "run_mdns_query");
            this.b.start();
        }
    }

    public void stopQuery() {
        Log.i("MiplayMdns", "stop mdns query");
        synchronized (this.c) {
            this.a.set(true);
        }
    }
}
