package com.xiaomi.miio;

/* loaded from: classes3.dex */
public class MiioClient {
    private static final Object a = MiioClient.class;
    private static MiioClient b;
    private boolean c = false;

    public native int Destroy();

    public native int Initialize();

    public native int Start(String str);

    public native int Stop();

    static {
        System.loadLibrary("miio_linux");
    }

    public static MiioClient getInstance() {
        MiioClient miioClient;
        synchronized (a) {
            if (b == null) {
                b = new MiioClient();
            }
            miioClient = b;
        }
        return miioClient;
    }

    private MiioClient() {
    }

    public void start(String str) {
        this.c = true;
        Start(str);
    }

    public boolean isStarted() {
        return this.c;
    }
}
