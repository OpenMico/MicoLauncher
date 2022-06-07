package com.google.android.exoplayer2.metadata.icy;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class IcyInfo implements Metadata.Entry {
    public static final Parcelable.Creator<IcyInfo> CREATOR = new Parcelable.Creator<IcyInfo>() { // from class: com.google.android.exoplayer2.metadata.icy.IcyInfo.1
        /* renamed from: a */
        public IcyInfo createFromParcel(Parcel parcel) {
            return new IcyInfo(parcel);
        }

        /* renamed from: a */
        public IcyInfo[] newArray(int i) {
            return new IcyInfo[i];
        }
    };
    public final byte[] rawMetadata;
    @Nullable
    public final String title;
    @Nullable
    public final String url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IcyInfo(byte[] bArr, @Nullable String str, @Nullable String str2) {
        this.rawMetadata = bArr;
        this.title = str;
        this.url = str2;
    }

    IcyInfo(Parcel parcel) {
        this.rawMetadata = (byte[]) Assertions.checkNotNull(parcel.createByteArray());
        this.title = parcel.readString();
        this.url = parcel.readString();
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public void populateMediaMetadata(MediaMetadata.Builder builder) {
        String str = this.title;
        if (str != null) {
            builder.setTitle(str);
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.rawMetadata, ((IcyInfo) obj).rawMetadata);
    }

    public int hashCode() {
        return Arrays.hashCode(this.rawMetadata);
    }

    public String toString() {
        return String.format("ICY: title=\"%s\", url=\"%s\", rawMetadata.length=\"%s\"", this.title, this.url, Integer.valueOf(this.rawMetadata.length));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.rawMetadata);
        parcel.writeString(this.title);
        parcel.writeString(this.url);
    }
}
