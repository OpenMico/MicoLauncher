package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

/* compiled from: BackStackState.java */
@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
public final class b implements Parcelable {
    public static final Parcelable.Creator<b> CREATOR = new Parcelable.Creator<b>() { // from class: androidx.fragment.app.b.1
        /* renamed from: a */
        public b createFromParcel(Parcel parcel) {
            return new b(parcel);
        }

        /* renamed from: a */
        public b[] newArray(int i) {
            return new b[i];
        }
    };
    final int[] a;
    final ArrayList<String> b;
    final int[] c;
    final int[] d;
    final int e;
    final String f;
    final int g;
    final int h;
    final CharSequence i;
    final int j;
    final CharSequence k;
    final ArrayList<String> l;
    final ArrayList<String> m;
    final boolean n;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public b(a aVar) {
        int size = aVar.d.size();
        this.a = new int[size * 5];
        if (aVar.j) {
            this.b = new ArrayList<>(size);
            this.c = new int[size];
            this.d = new int[size];
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                FragmentTransaction.a aVar2 = (FragmentTransaction.a) aVar.d.get(i2);
                int i3 = i + 1;
                this.a[i] = aVar2.a;
                this.b.add(aVar2.b != null ? aVar2.b.mWho : null);
                int i4 = i3 + 1;
                this.a[i3] = aVar2.c;
                int i5 = i4 + 1;
                this.a[i4] = aVar2.d;
                int i6 = i5 + 1;
                this.a[i5] = aVar2.e;
                i = i6 + 1;
                this.a[i6] = aVar2.f;
                this.c[i2] = aVar2.g.ordinal();
                this.d[i2] = aVar2.h.ordinal();
            }
            this.e = aVar.i;
            this.f = aVar.l;
            this.g = aVar.c;
            this.h = aVar.m;
            this.i = aVar.n;
            this.j = aVar.o;
            this.k = aVar.p;
            this.l = aVar.q;
            this.m = aVar.r;
            this.n = aVar.s;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public b(Parcel parcel) {
        this.a = parcel.createIntArray();
        this.b = parcel.createStringArrayList();
        this.c = parcel.createIntArray();
        this.d = parcel.createIntArray();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt();
        this.h = parcel.readInt();
        this.i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.j = parcel.readInt();
        this.k = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.l = parcel.createStringArrayList();
        this.m = parcel.createStringArrayList();
        this.n = parcel.readInt() != 0;
    }

    public a a(FragmentManager fragmentManager) {
        a aVar = new a(fragmentManager);
        int i = 0;
        int i2 = 0;
        while (i < this.a.length) {
            FragmentTransaction.a aVar2 = new FragmentTransaction.a();
            int i3 = i + 1;
            aVar2.a = this.a[i];
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Instantiate " + aVar + " op #" + i2 + " base fragment #" + this.a[i3]);
            }
            String str = this.b.get(i2);
            if (str != null) {
                aVar2.b = fragmentManager.b(str);
            } else {
                aVar2.b = null;
            }
            aVar2.g = Lifecycle.State.values()[this.c[i2]];
            aVar2.h = Lifecycle.State.values()[this.d[i2]];
            int[] iArr = this.a;
            int i4 = i3 + 1;
            aVar2.c = iArr[i3];
            int i5 = i4 + 1;
            aVar2.d = iArr[i4];
            int i6 = i5 + 1;
            aVar2.e = iArr[i5];
            i = i6 + 1;
            aVar2.f = iArr[i6];
            aVar.e = aVar2.c;
            aVar.f = aVar2.d;
            aVar.g = aVar2.e;
            aVar.h = aVar2.f;
            aVar.a(aVar2);
            i2++;
        }
        aVar.i = this.e;
        aVar.l = this.f;
        aVar.c = this.g;
        aVar.j = true;
        aVar.m = this.h;
        aVar.n = this.i;
        aVar.o = this.j;
        aVar.p = this.k;
        aVar.q = this.l;
        aVar.r = this.m;
        aVar.s = this.n;
        aVar.a(1);
        return aVar;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.a);
        parcel.writeStringList(this.b);
        parcel.writeIntArray(this.c);
        parcel.writeIntArray(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
        parcel.writeInt(this.j);
        TextUtils.writeToParcel(this.k, parcel, 0);
        parcel.writeStringList(this.l);
        parcel.writeStringList(this.m);
        parcel.writeInt(this.n ? 1 : 0);
    }
}
