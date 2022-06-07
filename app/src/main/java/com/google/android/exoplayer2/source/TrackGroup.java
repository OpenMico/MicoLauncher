package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class TrackGroup implements Parcelable {
    public static final Parcelable.Creator<TrackGroup> CREATOR = new Parcelable.Creator<TrackGroup>() { // from class: com.google.android.exoplayer2.source.TrackGroup.1
        /* renamed from: a */
        public TrackGroup createFromParcel(Parcel parcel) {
            return new TrackGroup(parcel);
        }

        /* renamed from: a */
        public TrackGroup[] newArray(int i) {
            return new TrackGroup[i];
        }
    };
    private final Format[] a;
    private int b;
    public final int length;

    private static int a(int i) {
        return i | 16384;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TrackGroup(Format... formatArr) {
        Assertions.checkState(formatArr.length > 0);
        this.a = formatArr;
        this.length = formatArr.length;
        a();
    }

    TrackGroup(Parcel parcel) {
        this.length = parcel.readInt();
        this.a = new Format[this.length];
        for (int i = 0; i < this.length; i++) {
            this.a[i] = (Format) parcel.readParcelable(Format.class.getClassLoader());
        }
    }

    public Format getFormat(int i) {
        return this.a[i];
    }

    public int indexOf(Format format) {
        int i = 0;
        while (true) {
            Format[] formatArr = this.a;
            if (i >= formatArr.length) {
                return -1;
            }
            if (format == formatArr[i]) {
                return i;
            }
            i++;
        }
    }

    public int hashCode() {
        if (this.b == 0) {
            this.b = 527 + Arrays.hashCode(this.a);
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
        TrackGroup trackGroup = (TrackGroup) obj;
        return this.length == trackGroup.length && Arrays.equals(this.a, trackGroup.a);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.length);
        for (int i2 = 0; i2 < this.length; i2++) {
            parcel.writeParcelable(this.a[i2], 0);
        }
    }

    private void a() {
        String a = a(this.a[0].language);
        int a2 = a(this.a[0].roleFlags);
        int i = 1;
        while (true) {
            Format[] formatArr = this.a;
            if (i >= formatArr.length) {
                return;
            }
            if (!a.equals(a(formatArr[i].language))) {
                a("languages", this.a[0].language, this.a[i].language, i);
                return;
            } else if (a2 != a(this.a[i].roleFlags)) {
                a("role flags", Integer.toBinaryString(this.a[0].roleFlags), Integer.toBinaryString(this.a[i].roleFlags), i);
                return;
            } else {
                i++;
            }
        }
    }

    private static String a(@Nullable String str) {
        return (str == null || str.equals(C.LANGUAGE_UNDETERMINED)) ? "" : str;
    }

    private static void a(String str, @Nullable String str2, @Nullable String str3, int i) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append("Different ");
        sb.append(str);
        sb.append(" combined in one TrackGroup: '");
        sb.append(str2);
        sb.append("' (track 0) and '");
        sb.append(str3);
        sb.append("' (track ");
        sb.append(i);
        sb.append(")");
        Log.e("TrackGroup", "", new IllegalStateException(sb.toString()));
    }
}
