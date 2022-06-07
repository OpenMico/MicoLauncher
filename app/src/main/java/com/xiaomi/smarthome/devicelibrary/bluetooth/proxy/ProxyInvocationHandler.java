package com.xiaomi.smarthome.devicelibrary.bluetooth.proxy;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class ProxyInvocationHandler implements Handler.Callback, ProxyInterceptor, InvocationHandler {
    private Object a;
    private ProxyInterceptor b;
    private boolean c;
    private boolean d;
    private Handler e;

    public ProxyInvocationHandler(Object obj) {
        this(obj, null);
    }

    public ProxyInvocationHandler(Object obj, ProxyInterceptor proxyInterceptor) {
        this(obj, proxyInterceptor, false);
    }

    public ProxyInvocationHandler(Object obj, ProxyInterceptor proxyInterceptor, boolean z) {
        this(obj, proxyInterceptor, z, false);
    }

    public ProxyInvocationHandler(Object obj, ProxyInterceptor proxyInterceptor, boolean z, boolean z2) {
        this.c = z;
        this.b = proxyInterceptor;
        this.d = z2;
        this.a = a(obj);
        this.e = new Handler(Looper.getMainLooper(), this);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        Object a = a();
        if (onIntercept(a, method, objArr)) {
            return null;
        }
        ProxyBulk proxyBulk = new ProxyBulk(a, method, objArr);
        return this.d ? a(proxyBulk) : b(proxyBulk);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.proxy.ProxyInterceptor
    public boolean onIntercept(Object obj, Method method, Object[] objArr) {
        ProxyInterceptor proxyInterceptor = this.b;
        if (proxyInterceptor == null) {
            return false;
        }
        try {
            return proxyInterceptor.onIntercept(obj, method, objArr);
        } catch (Exception e) {
            BluetoothLog.e(e);
            return false;
        }
    }

    private Object a(Object obj) {
        return this.c ? new WeakReference(obj) : obj;
    }

    private Object a() {
        if (this.c) {
            return ((WeakReference) this.a).get();
        }
        return this.a;
    }

    private Object a(ProxyBulk proxyBulk) {
        this.e.obtainMessage(0, proxyBulk).sendToTarget();
        return null;
    }

    private Object b(ProxyBulk proxyBulk) {
        try {
            return proxyBulk.safeInvoke();
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        ProxyBulk.safeInvoke(message.obj);
        return true;
    }
}
