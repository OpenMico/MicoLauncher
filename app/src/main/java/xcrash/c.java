package xcrash;

import android.util.Log;

/* compiled from: DefaultLogger.java */
/* loaded from: classes6.dex */
class c implements ILogger {
    @Override // xcrash.ILogger
    public void v(String str, String str2) {
        Log.v(str, str2);
    }

    @Override // xcrash.ILogger
    public void v(String str, String str2, Throwable th) {
        Log.v(str, str2, th);
    }

    @Override // xcrash.ILogger
    public void d(String str, String str2) {
        Log.d(str, str2);
    }

    @Override // xcrash.ILogger
    public void d(String str, String str2, Throwable th) {
        Log.d(str, str2, th);
    }

    @Override // xcrash.ILogger
    public void i(String str, String str2) {
        Log.i(str, str2);
    }

    @Override // xcrash.ILogger
    public void i(String str, String str2, Throwable th) {
        Log.i(str, str2, th);
    }

    @Override // xcrash.ILogger
    public void w(String str, String str2) {
        Log.w(str, str2);
    }

    @Override // xcrash.ILogger
    public void w(String str, String str2, Throwable th) {
        Log.w(str, str2, th);
    }

    @Override // xcrash.ILogger
    public void e(String str, String str2) {
        Log.e(str, str2);
    }

    @Override // xcrash.ILogger
    public void e(String str, String str2, Throwable th) {
        Log.e(str, str2, th);
    }
}
