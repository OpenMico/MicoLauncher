package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Month.java */
/* loaded from: classes2.dex */
public final class f implements Parcelable, Comparable<f> {
    public static final Parcelable.Creator<f> CREATOR = new Parcelable.Creator<f>() { // from class: com.google.android.material.datepicker.f.1
        @NonNull
        /* renamed from: a */
        public f createFromParcel(@NonNull Parcel parcel) {
            return f.a(parcel.readInt(), parcel.readInt());
        }

        @NonNull
        /* renamed from: a */
        public f[] newArray(int i) {
            return new f[i];
        }
    };
    final int a;
    final int b;
    final int c;
    final int d;
    final long e;
    @NonNull
    private final Calendar f;
    @Nullable
    private String g;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private f(@NonNull Calendar calendar) {
        calendar.set(5, 1);
        this.f = k.b(calendar);
        this.a = this.f.get(2);
        this.b = this.f.get(1);
        this.c = this.f.getMaximum(7);
        this.d = this.f.getActualMaximum(5);
        this.e = this.f.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static f a(long j) {
        Calendar c = k.c();
        c.setTimeInMillis(j);
        return new f(c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static f a(int i, int i2) {
        Calendar c = k.c();
        c.set(1, i);
        c.set(2, i2);
        return new f(c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static f a() {
        return new f(k.b());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        int firstDayOfWeek = this.f.get(7) - this.f.getFirstDayOfWeek();
        return firstDayOfWeek < 0 ? firstDayOfWeek + this.c : firstDayOfWeek;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return this.a == fVar.a && this.b == fVar.b;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b)});
    }

    /* renamed from: a */
    public int compareTo(@NonNull f fVar) {
        return this.f.compareTo(fVar.f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(@NonNull f fVar) {
        if (this.f instanceof GregorianCalendar) {
            return ((fVar.b - this.b) * 12) + (fVar.a - this.a);
        }
        throw new IllegalArgumentException("Only Gregorian calendars are supported.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long c() {
        return this.f.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a(int i) {
        Calendar b = k.b(this.f);
        b.set(5, i);
        return b.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(long j) {
        Calendar b = k.b(this.f);
        b.setTimeInMillis(j);
        return b.get(5);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public f b(int i) {
        Calendar b = k.b(this.f);
        b.add(2, i);
        return new f(b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public String a(Context context) {
        if (this.g == null) {
            this.g = d.a(context, this.f.getTimeInMillis());
        }
        return this.g;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.b);
        parcel.writeInt(this.a);
    }
}
