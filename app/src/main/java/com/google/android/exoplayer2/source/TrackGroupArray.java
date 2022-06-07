package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class TrackGroupArray implements Parcelable {
    private final TrackGroup[] a;
    private int b;
    public final int length;
    public static final TrackGroupArray EMPTY = new TrackGroupArray(new TrackGroup[0]);
    public static final Parcelable.Creator<TrackGroupArray> CREATOR = new Parcelable.Creator<TrackGroupArray>() { // from class: com.google.android.exoplayer2.source.TrackGroupArray.1
        /* renamed from: a */
        public TrackGroupArray createFromParcel(Parcel parcel) {
            return new TrackGroupArray(parcel);
        }

        /* renamed from: a */
        public TrackGroupArray[] newArray(int i) {
            return new TrackGroupArray[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TrackGroupArray(TrackGroup... trackGroupArr) {
        this.a = trackGroupArr;
        this.length = trackGroupArr.length;
    }

    TrackGroupArray(Parcel parcel) {
        this.length = parcel.readInt();
        this.a = new TrackGroup[this.length];
        for (int i = 0; i < this.length; i++) {
            this.a[i] = (TrackGroup) parcel.readParcelable(TrackGroup.class.getClassLoader());
        }
    }

    public TrackGroup get(int i) {
        return this.a[i];
    }

    public int indexOf(TrackGroup trackGroup) {
        for (int i = 0; i < this.length; i++) {
            if (this.a[i] == trackGroup) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int hashCode() {
        if (this.b == 0) {
            this.b = Arrays.hashCode(this.a);
        }
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackGroupArray trackGroupArray = (TrackGroupArray) obj;
        return this.length == trackGroupArray.length && Arrays.equals(this.a, trackGroupArray.a);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.length);
        for (int i2 = 0; i2 < this.length; i2++) {
            parcel.writeParcelable(this.a[i2], 0);
        }
    }
}
