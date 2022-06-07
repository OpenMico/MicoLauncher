package com.xiaomi.onetrack.util.oaid.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface g extends IInterface {
    boolean a();

    String b();

    boolean c();

    void d();

    /* loaded from: classes4.dex */
    public static abstract class a extends Binder implements g {

        /* renamed from: com.xiaomi.onetrack.util.oaid.a.g$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static class C0183a implements g {
            private IBinder a;

            @Override // com.xiaomi.onetrack.util.oaid.a.g
            public boolean c() {
                return false;
            }

            public C0183a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.g
            public boolean a() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        return false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return true;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    th.printStackTrace();
                    return false;
                }
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.g
            public String b() {
                String str;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    str = obtain2.readString();
                } catch (Throwable unused) {
                    obtain2.recycle();
                    obtain.recycle();
                    str = null;
                }
                obtain2.recycle();
                obtain.recycle();
                return str;
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.g
            public void d() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } catch (Throwable unused) {
                    obtain2.recycle();
                    obtain.recycle();
                }
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }
}
