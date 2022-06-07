package com.xiaomi.phonenum.phone;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: PhoneInServiceHelper.java */
@RequiresApi(api = 21)
/* loaded from: classes4.dex */
class a {
    private static final Handler a;
    private final CountDownLatch b = new CountDownLatch(1);
    private final int c;
    private PhoneStateListener d;
    private TelephonyManager e;

    static {
        HandlerThread handlerThread = new HandlerThread("PhoneInServiceListener");
        handlerThread.start();
        a = new Handler(handlerThread.getLooper());
    }

    private a(Context context, int i) {
        this.c = i;
        this.e = (TelephonyManager) context.getSystemService("phone");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(Context context, int i, long j) throws InterruptedException {
        return new a(context, i).a(j);
    }

    private boolean a(long j) throws InterruptedException {
        a();
        try {
            return this.b.await(j, TimeUnit.MILLISECONDS);
        } finally {
            b();
        }
    }

    private void a() {
        a.post(new Runnable() { // from class: com.xiaomi.phonenum.phone.a.1
            @Override // java.lang.Runnable
            public void run() {
                a aVar = a.this;
                aVar.d = new C0186a(aVar.c) { // from class: com.xiaomi.phonenum.phone.a.1.1
                    {
                        a aVar2 = a.this;
                    }

                    @Override // android.telephony.PhoneStateListener
                    public void onServiceStateChanged(ServiceState serviceState) {
                        if (serviceState.getState() == 0) {
                            a.this.b.countDown();
                        }
                    }
                };
                a.this.e.listen(a.this.d, 1);
            }
        });
    }

    private void b() {
        a.post(new Runnable() { // from class: com.xiaomi.phonenum.phone.a.2
            @Override // java.lang.Runnable
            public void run() {
                a.this.e.listen(a.this.d, 0);
            }
        });
    }

    /* compiled from: PhoneInServiceHelper.java */
    /* loaded from: classes4.dex */
    static class b {
        static void a(Object obj, String str, Object obj2) {
            Field a = a(obj, str);
            if (a != null) {
                try {
                    a.set(obj, obj2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException("Could not find field [" + str + "] on target [" + obj + "]");
            }
        }

        private static Field a(Object obj, String str) {
            if (obj == null) {
                throw new IllegalArgumentException("object can'd be null");
            } else if (str == null || str.length() <= 0) {
                throw new IllegalArgumentException("fieldName can'd be blank");
            } else {
                for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
                    try {
                        Field declaredField = cls.getDeclaredField(str);
                        a(declaredField);
                        return declaredField;
                    } catch (NoSuchFieldException unused) {
                    }
                }
                return null;
            }
        }

        private static void a(Field field) {
            if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
                field.setAccessible(true);
            }
        }
    }

    /* compiled from: PhoneInServiceHelper.java */
    /* renamed from: com.xiaomi.phonenum.phone.a$a  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    private class C0186a extends PhoneStateListener {
        public C0186a(int i) {
            b.a(this, "mSubId", Integer.valueOf(i));
        }
    }
}
