package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentManagerState.java */
@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class i implements Parcelable {
    public static final Parcelable.Creator<i> CREATOR = new Parcelable.Creator<i>() { // from class: androidx.fragment.app.i.1
        /* renamed from: a */
        public i createFromParcel(Parcel parcel) {
            return new i(parcel);
        }

        /* renamed from: a */
        public i[] newArray(int i) {
            return new i[i];
        }
    };
    ArrayList<k> a;
    ArrayList<String> b;
    b[] c;
    int d;
    String e;
    ArrayList<String> f;
    ArrayList<Bundle> g;
    ArrayList<FragmentManager.b> h;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public i() {
        this.e = null;
        this.f = new ArrayList<>();
        this.g = new ArrayList<>();
    }

    public i(Parcel parcel) {
        this.e = null;
        this.f = new ArrayList<>();
        this.g = new ArrayList<>();
        this.a = parcel.createTypedArrayList(k.CREATOR);
        this.b = parcel.createStringArrayList();
        this.c = (b[]) parcel.createTypedArray(b.CREATOR);
        this.d = parcel.readInt();
        this.e = parcel.readString();
        this.f = parcel.createStringArrayList();
        this.g = parcel.createTypedArrayList(Bundle.CREATOR);
        this.h = parcel.createTypedArrayList(FragmentManager.b.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
        parcel.writeStringList(this.b);
        parcel.writeTypedArray(this.c, i);
        parcel.writeInt(this.d);
        parcel.writeString(this.e);
        parcel.writeStringList(this.f);
        parcel.writeTypedList(this.g);
        parcel.writeTypedList(this.h);
    }
}
