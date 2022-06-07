package com.xiaomi.idm.trans;

/* loaded from: classes3.dex */
public class IDMTransClient {
    private int a;
    private IDMTransCallback b;

    /* loaded from: classes3.dex */
    public interface IDMTransCallback {
        void onReceiveData(String str, int i, int i2, byte[] bArr);

        void onReceiveStatus(String str, int i, int i2, String str2);
    }

    public native int IDMTransCreate(String str, int i, String str2);

    public native int IDMTransDisconnect();

    public native int IDMTransRelease();

    public native int IDMTransSend(byte[] bArr, long j);

    public native int IDMTransStart();

    public native int IDMTransStop();

    static {
        System.loadLibrary("idmtrans");
    }

    public IDMTransClient(String str, int i, String str2) {
        this.a = IDMTransCreate(str, i, str2);
    }

    public int getCreateRet() {
        return this.a;
    }

    public void setCallback(IDMTransCallback iDMTransCallback) {
        this.b = iDMTransCallback;
    }
}
