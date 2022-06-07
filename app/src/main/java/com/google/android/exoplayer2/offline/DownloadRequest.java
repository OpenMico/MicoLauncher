package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>() { // from class: com.google.android.exoplayer2.offline.DownloadRequest.1
        /* renamed from: a */
        public DownloadRequest createFromParcel(Parcel parcel) {
            return new DownloadRequest(parcel);
        }

        /* renamed from: a */
        public DownloadRequest[] newArray(int i) {
            return new DownloadRequest[i];
        }
    };
    @Nullable
    public final String customCacheKey;
    public final byte[] data;
    public final String id;
    @Nullable
    public final byte[] keySetId;
    @Nullable
    public final String mimeType;
    public final List<StreamKey> streamKeys;
    public final Uri uri;

    /* loaded from: classes2.dex */
    public static class UnsupportedRequestException extends IOException {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private final String a;
        private final Uri b;
        @Nullable
        private String c;
        @Nullable
        private List<StreamKey> d;
        @Nullable
        private byte[] e;
        @Nullable
        private String f;
        @Nullable
        private byte[] g;

        public Builder(String str, Uri uri) {
            this.a = str;
            this.b = uri;
        }

        public Builder setMimeType(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            this.d = list;
            return this;
        }

        public Builder setKeySetId(@Nullable byte[] bArr) {
            this.e = bArr;
            return this;
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.f = str;
            return this;
        }

        public Builder setData(@Nullable byte[] bArr) {
            this.g = bArr;
            return this;
        }

        public DownloadRequest build() {
            String str = this.a;
            Uri uri = this.b;
            String str2 = this.c;
            List list = this.d;
            if (list == null) {
                list = ImmutableList.of();
            }
            return new DownloadRequest(str, uri, str2, list, this.e, this.f, this.g);
        }
    }

    private DownloadRequest(String str, Uri uri, @Nullable String str2, List<StreamKey> list, @Nullable byte[] bArr, @Nullable String str3, @Nullable byte[] bArr2) {
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(uri, str2);
        boolean z = true;
        if (inferContentTypeForUriAndMimeType == 0 || inferContentTypeForUriAndMimeType == 2 || inferContentTypeForUriAndMimeType == 1) {
            z = str3 != null ? false : z;
            StringBuilder sb = new StringBuilder(49);
            sb.append("customCacheKey must be null for type: ");
            sb.append(inferContentTypeForUriAndMimeType);
            Assertions.checkArgument(z, sb.toString());
        }
        this.id = str;
        this.uri = uri;
        this.mimeType = str2;
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList);
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        this.customCacheKey = str3;
        this.data = bArr2 != null ? Arrays.copyOf(bArr2, bArr2.length) : Util.EMPTY_BYTE_ARRAY;
    }

    DownloadRequest(Parcel parcel) {
        this.id = (String) Util.castNonNull(parcel.readString());
        this.uri = Uri.parse((String) Util.castNonNull(parcel.readString()));
        this.mimeType = parcel.readString();
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add((StreamKey) parcel.readParcelable(StreamKey.class.getClassLoader()));
        }
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = parcel.createByteArray();
        this.customCacheKey = parcel.readString();
        this.data = (byte[]) Util.castNonNull(parcel.createByteArray());
    }

    public DownloadRequest copyWithId(String str) {
        return new DownloadRequest(str, this.uri, this.mimeType, this.streamKeys, this.keySetId, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithKeySetId(@Nullable byte[] bArr) {
        return new DownloadRequest(this.id, this.uri, this.mimeType, this.streamKeys, bArr, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithMergedRequest(DownloadRequest downloadRequest) {
        ArrayList arrayList;
        Assertions.checkArgument(this.id.equals(downloadRequest.id));
        if (this.streamKeys.isEmpty() || downloadRequest.streamKeys.isEmpty()) {
            arrayList = Collections.emptyList();
        } else {
            ArrayList arrayList2 = new ArrayList(this.streamKeys);
            for (int i = 0; i < downloadRequest.streamKeys.size(); i++) {
                StreamKey streamKey = downloadRequest.streamKeys.get(i);
                if (!arrayList2.contains(streamKey)) {
                    arrayList2.add(streamKey);
                }
            }
            arrayList = arrayList2;
        }
        return new DownloadRequest(this.id, downloadRequest.uri, downloadRequest.mimeType, arrayList, downloadRequest.keySetId, downloadRequest.customCacheKey, downloadRequest.data);
    }

    public MediaItem toMediaItem() {
        return new MediaItem.Builder().setMediaId(this.id).setUri(this.uri).setCustomCacheKey(this.customCacheKey).setMimeType(this.mimeType).setStreamKeys(this.streamKeys).setDrmKeySetId(this.keySetId).build();
    }

    public String toString() {
        String str = this.mimeType;
        String str2 = this.id;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(str2);
        return sb.toString();
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest downloadRequest = (DownloadRequest) obj;
        return this.id.equals(downloadRequest.id) && this.uri.equals(downloadRequest.uri) && Util.areEqual(this.mimeType, downloadRequest.mimeType) && this.streamKeys.equals(downloadRequest.streamKeys) && Arrays.equals(this.keySetId, downloadRequest.keySetId) && Util.areEqual(this.customCacheKey, downloadRequest.customCacheKey) && Arrays.equals(this.data, downloadRequest.data);
    }

    public final int hashCode() {
        int hashCode = ((this.id.hashCode() * 31 * 31) + this.uri.hashCode()) * 31;
        String str = this.mimeType;
        int i = 0;
        int hashCode2 = (((((hashCode + (str != null ? str.hashCode() : 0)) * 31) + this.streamKeys.hashCode()) * 31) + Arrays.hashCode(this.keySetId)) * 31;
        String str2 = this.customCacheKey;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return ((hashCode2 + i) * 31) + Arrays.hashCode(this.data);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.uri.toString());
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.streamKeys.size());
        for (int i2 = 0; i2 < this.streamKeys.size(); i2++) {
            parcel.writeParcelable(this.streamKeys.get(i2), 0);
        }
        parcel.writeByteArray(this.keySetId);
        parcel.writeString(this.customCacheKey);
        parcel.writeByteArray(this.data);
    }
}
