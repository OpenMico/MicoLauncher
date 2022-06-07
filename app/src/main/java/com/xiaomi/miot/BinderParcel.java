package com.xiaomi.miot;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class BinderParcel implements Parcelable {
    public static final Parcelable.Creator<BinderParcel> CREATOR = new Parcelable.Creator<BinderParcel>() { // from class: com.xiaomi.miot.BinderParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinderParcel createFromParcel(Parcel parcel) {
            return new BinderParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BinderParcel[] newArray(int i) {
            return new BinderParcel[i];
        }
    };
    IBinder binder;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BinderParcel(IBinder iBinder) {
        this.binder = iBinder;
    }

    public IBinder getBinder() {
        return this.binder;
    }

    protected BinderParcel(Parcel parcel) {
        this.binder = parcel.readStrongBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.binder);
    }
}
