package org.repackage.a.a.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: IOpenID.java */
/* loaded from: classes3.dex */
public interface a extends IInterface {

    /* compiled from: IOpenID.java */
    /* renamed from: org.repackage.a.a.a.a$a  reason: collision with other inner class name */
    /* loaded from: classes3.dex */
    public static abstract class AbstractBinderC0386a extends Binder implements a {

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: IOpenID.java */
        /* renamed from: org.repackage.a.a.a.a$a$a  reason: collision with other inner class name */
        /* loaded from: classes3.dex */
        public static class C0387a implements a {
            public IBinder a;

            public C0387a(IBinder iBinder) {
                this.a = iBinder;
            }

            public String a(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.heytap.openid.IOpenID");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof a)) {
                return new C0387a(iBinder);
            }
            return (a) queryLocalInterface;
        }
    }
}
