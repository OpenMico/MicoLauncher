package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntRange;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TimeModel.java */
/* loaded from: classes2.dex */
public class d implements Parcelable {
    public static final Parcelable.Creator<d> CREATOR = new Parcelable.Creator<d>() { // from class: com.google.android.material.timepicker.d.1
        /* renamed from: a */
        public d createFromParcel(Parcel parcel) {
            return new d(parcel);
        }

        /* renamed from: a */
        public d[] newArray(int i) {
            return new d[i];
        }
    };
    final int a;
    int b;
    int c;
    int d;
    int e;
    private final b f;
    private final b g;

    private static int e(int i) {
        return i >= 12 ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public d() {
        this(0);
    }

    public d(int i) {
        this(0, 0, 10, i);
    }

    public d(int i, int i2, int i3, int i4) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.a = i4;
        this.e = e(i);
        this.f = new b(59);
        this.g = new b(i4 == 1 ? 24 : 12);
    }

    protected d(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public void a(int i) {
        this.e = e(i);
        this.b = i;
    }

    public void b(int i) {
        if (this.a == 1) {
            this.b = i;
            return;
        }
        int i2 = 12;
        int i3 = i % 12;
        if (this.e != 1) {
            i2 = 0;
        }
        this.b = i3 + i2;
    }

    public void c(@IntRange(from = 0, to = 60) int i) {
        this.c = i % 60;
    }

    public int a() {
        if (this.a == 1) {
            return this.b % 24;
        }
        int i = this.b;
        if (i % 12 == 0) {
            return 12;
        }
        return this.e == 1 ? i - 12 : i;
    }

    public b b() {
        return this.f;
    }

    public b c() {
        return this.g;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.d)});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return this.b == dVar.b && this.c == dVar.c && this.a == dVar.a && this.d == dVar.d;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.a);
    }

    public void d(int i) {
        if (i != this.e) {
            this.e = i;
            int i2 = this.b;
            if (i2 >= 12 || i != 1) {
                int i3 = this.b;
                if (i3 >= 12 && i == 0) {
                    this.b = i3 - 12;
                    return;
                }
                return;
            }
            this.b = i2 + 12;
        }
    }

    public static String a(Resources resources, CharSequence charSequence) {
        return a(resources, charSequence, "%02d");
    }

    public static String a(Resources resources, CharSequence charSequence, String str) {
        return String.format(resources.getConfiguration().locale, str, Integer.valueOf(Integer.parseInt(String.valueOf(charSequence))));
    }
}
