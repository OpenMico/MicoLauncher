package com.xiaomi.onetrack.util.oaid.a;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes4.dex */
public interface a extends IInterface {
    String a();

    /* renamed from: com.xiaomi.onetrack.util.oaid.a.a$a  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    public static final class C0179a implements a {
        private IBinder a;

        public C0179a(IBinder iBinder) {
            this.a = iBinder;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }

        @Override // com.xiaomi.onetrack.util.oaid.a.a
        public String a() {
            String str;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.asus.msa.SupplementaryDID.IDidAidlInterface");
                this.a.transact(3, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
            } catch (Throwable th) {
                obtain.recycle();
                obtain2.recycle();
                th.printStackTrace();
                str = null;
            }
            obtain.recycle();
            obtain2.recycle();
            return str;
        }
    }
}
