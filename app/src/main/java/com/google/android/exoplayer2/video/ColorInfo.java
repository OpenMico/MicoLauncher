package com.google.android.exoplayer2.video;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes2.dex */
public final class ColorInfo implements Parcelable {
    public static final Parcelable.Creator<ColorInfo> CREATOR = new Parcelable.Creator<ColorInfo>() { // from class: com.google.android.exoplayer2.video.ColorInfo.1
        /* renamed from: a */
        public ColorInfo createFromParcel(Parcel parcel) {
            return new ColorInfo(parcel);
        }

        /* renamed from: a */
        public ColorInfo[] newArray(int i) {
            return new ColorInfo[i];
        }
    };
    private int a;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    @Nullable
    public final byte[] hdrStaticInfo;

    @Pure
    public static int isoColorPrimariesToColorSpace(int i) {
        if (i == 1) {
            return 1;
        }
        if (i == 9) {
            return 6;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                return 2;
            default:
                return -1;
        }
    }

    @Pure
    public static int isoTransferCharacteristicsToColorTransfer(int i) {
        if (i == 1) {
            return 3;
        }
        if (i == 16) {
            return 6;
        }
        if (i == 18) {
            return 7;
        }
        switch (i) {
            case 6:
            case 7:
                return 3;
            default:
                return -1;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ColorInfo(int i, int i2, int i3, @Nullable byte[] bArr) {
        this.colorSpace = i;
        this.colorRange = i2;
        this.colorTransfer = i3;
        this.hdrStaticInfo = bArr;
    }

    ColorInfo(Parcel parcel) {
        this.colorSpace = parcel.readInt();
        this.colorRange = parcel.readInt();
        this.colorTransfer = parcel.readInt();
        this.hdrStaticInfo = Util.readBoolean(parcel) ? parcel.createByteArray() : null;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        return this.colorSpace == colorInfo.colorSpace && this.colorRange == colorInfo.colorRange && this.colorTransfer == colorInfo.colorTransfer && Arrays.equals(this.hdrStaticInfo, colorInfo.hdrStaticInfo);
    }

    public String toString() {
        int i = this.colorSpace;
        int i2 = this.colorRange;
        int i3 = this.colorTransfer;
        boolean z = this.hdrStaticInfo != null;
        StringBuilder sb = new StringBuilder(55);
        sb.append("ColorInfo(");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(z);
        sb.append(")");
        return sb.toString();
    }

    public int hashCode() {
        if (this.a == 0) {
            this.a = ((((((527 + this.colorSpace) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo);
        }
        return this.a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.colorSpace);
        parcel.writeInt(this.colorRange);
        parcel.writeInt(this.colorTransfer);
        Util.writeBoolean(parcel, this.hdrStaticInfo != null);
        byte[] bArr = this.hdrStaticInfo;
        if (bArr != null) {
            parcel.writeByteArray(bArr);
        }
    }
}
