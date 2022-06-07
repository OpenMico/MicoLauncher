package com.xiaomi.onetrack.util.oaid.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface e extends IInterface {

    /* loaded from: classes4.dex */
    public static abstract class a extends Binder implements e {

        /* renamed from: com.xiaomi.onetrack.util.oaid.a.e$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static class C0182a implements e {
            public IBinder a;

            public C0182a(IBinder iBinder) {
                this.a = iBinder;
            }

            /* JADX WARN: Finally extract failed */
            public String a(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    try {
                        obtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                        obtain.writeString(str);
                        obtain.writeString(str2);
                        obtain.writeString(str3);
                        this.a.transact(1, obtain, obtain2, 0);
                        obtain2.readException();
                        String readString = obtain2.readString();
                        obtain.recycle();
                        obtain2.recycle();
                        return readString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain.recycle();
                        obtain2.recycle();
                        return null;
                    }
                } catch (Throwable th) {
                    obtain.recycle();
                    obtain2.recycle();
                    throw th;
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }
        }

        public static e a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.heytap.openid.IOpenID");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof e)) {
                return new C0182a(iBinder);
            }
            return (e) queryLocalInterface;
        }
    }
}
