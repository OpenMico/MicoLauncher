package com.xiaomi.onetrack.util.oaid.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface c extends IInterface {
    String a();

    String a(String str);

    String b();

    String b(String str);

    boolean c();

    /* loaded from: classes4.dex */
    public static abstract class a extends Binder implements c {

        /* renamed from: com.xiaomi.onetrack.util.oaid.a.c$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static class C0180a implements c {
            private IBinder a;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }

            public C0180a(IBinder iBinder) {
                this.a = iBinder;
            }

            /* JADX WARN: Finally extract failed */
            @Override // com.xiaomi.onetrack.util.oaid.a.c
            public String a() {
                Parcel obtain;
                Parcel obtain2;
                try {
                    obtain = Parcel.obtain();
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                        this.a.transact(1, obtain, obtain2, 0);
                        obtain2.readException();
                        String readString = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                        return readString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain2.recycle();
                        obtain.recycle();
                        return null;
                    }
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v2, types: [android.os.Parcel] */
            @Override // com.xiaomi.onetrack.util.oaid.a.c
            public String a(String str) {
                Parcel obtain;
                try {
                    str = Parcel.obtain();
                    obtain = Parcel.obtain();
                    try {
                        str.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                        this.a.transact(4, str, obtain, 0);
                        obtain.readException();
                        String readString = obtain.readString();
                        obtain.recycle();
                        str.recycle();
                        return readString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain.recycle();
                        str.recycle();
                        return null;
                    }
                } catch (Throwable th) {
                    obtain.recycle();
                    str.recycle();
                    throw th;
                }
            }

            /* JADX WARN: Finally extract failed */
            @Override // com.xiaomi.onetrack.util.oaid.a.c
            public String b() {
                Parcel obtain;
                Parcel obtain2;
                try {
                    obtain = Parcel.obtain();
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                        this.a.transact(2, obtain, obtain2, 0);
                        obtain2.readException();
                        String readString = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                        return readString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain2.recycle();
                        obtain.recycle();
                        return null;
                    }
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v2, types: [android.os.Parcel] */
            @Override // com.xiaomi.onetrack.util.oaid.a.c
            public String b(String str) {
                Parcel obtain;
                try {
                    str = Parcel.obtain();
                    obtain = Parcel.obtain();
                    try {
                        str.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                        this.a.transact(5, str, obtain, 0);
                        obtain.readException();
                        String readString = obtain.readString();
                        obtain.recycle();
                        str.recycle();
                        return readString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain.recycle();
                        str.recycle();
                        return null;
                    }
                } catch (Throwable th) {
                    obtain.recycle();
                    str.recycle();
                    throw th;
                }
            }

            @Override // com.xiaomi.onetrack.util.oaid.a.c
            public boolean c() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                boolean z = false;
                try {
                    obtain.writeInterfaceToken("com.zui.deviceidservice.IDeviceidInterface");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                } catch (Throwable unused) {
                    obtain2.recycle();
                    obtain.recycle();
                }
                return z;
            }
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                        String a = a();
                        parcel2.writeNoException();
                        parcel2.writeString(a);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                        String b = b();
                        parcel2.writeNoException();
                        parcel2.writeString(b);
                        return true;
                    case 3:
                        parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                        boolean c = c();
                        parcel2.writeNoException();
                        parcel2.writeInt(c ? 1 : 0);
                        return true;
                    case 4:
                        parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                        String a2 = a(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(a2);
                        return true;
                    case 5:
                        parcel.enforceInterface("com.zui.deviceidservice.IDeviceidInterface");
                        String b2 = b(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeString(b2);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.zui.deviceidservice.IDeviceidInterface");
                return true;
            }
        }

        public static c a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.zui.deviceidservice.IDeviceidInterface");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof c)) {
                return new C0180a(iBinder);
            }
            return (c) queryLocalInterface;
        }
    }
}
