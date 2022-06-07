package com.xiaomi.smarthome.core.entity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.smarthome.core.utils.BitmapUtils;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeBeans.kt */
@Parcelize
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007Bc\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000e\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0000H\u0002J\t\u0010*\u001a\u00020\tHÆ\u0003J\t\u0010+\u001a\u00020\u000bHÆ\u0003J\t\u0010,\u001a\u00020\u000bHÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0010HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\t\u00101\u001a\u00020\u0014HÆ\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u000eHÆ\u0003Jm\u00103\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000eHÆ\u0001J\t\u00104\u001a\u00020\u0014HÖ\u0001J\u0013\u00105\u001a\u00020(2\b\u00106\u001a\u0004\u0018\u000107HÖ\u0003J\t\u00108\u001a\u00020\u0014HÖ\u0001J\u0010\u00109\u001a\u00020(2\b\u00106\u001a\u0004\u0018\u000107J\t\u0010:\u001a\u00020\u000eHÖ\u0001J\b\u0010;\u001a\u00020<H\u0016J\u0019\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\f\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001eR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0018¨\u0006B"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "Landroid/os/Parcelable;", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "event", "Lcom/xiaomi/micolauncher/common/media/MicoMediaEvent;", "(Lcom/xiaomi/micolauncher/common/media/MicoMediaEvent;)V", "Lcom/xiaomi/micolauncher/common/media/RelayMediaEvent;", "(Lcom/xiaomi/micolauncher/common/media/RelayMediaEvent;)V", "mediaType", "Lcom/xiaomi/smarthome/core/entity/MediaType;", "title", "", "artist", "cover", "", "bitmap", "Landroid/graphics/Bitmap;", "source", SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, "playState", "", "sourceBtMac", "(Lcom/xiaomi/smarthome/core/entity/MediaType;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/String;Landroid/graphics/Bitmap;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getArtist", "()Ljava/lang/CharSequence;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "getCover", "()Ljava/lang/String;", "getDeviceId", "getMediaType", "()Lcom/xiaomi/smarthome/core/entity/MediaType;", "getPlayState", "()I", "getSource", "getSourceBtMac", "getTitle", "bitmapChange", "", "that", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "other", "", "hashCode", "isSameItem", "toString", "type", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public final class MicoMediaData extends ISmartHomeModel implements Parcelable {
    public static final Parcelable.Creator<MicoMediaData> CREATOR = new Creator();
    @NotNull
    private final CharSequence artist;
    @Nullable
    private Bitmap bitmap;
    @Nullable
    private final String cover;
    @Nullable
    private final String deviceId;
    @NotNull
    private final MediaType mediaType;
    private final int playState;
    @Nullable
    private final String source;
    @Nullable
    private final String sourceBtMac;
    @NotNull
    private final CharSequence title;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<MicoMediaData> {
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final MicoMediaData createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new MicoMediaData((MediaType) Enum.valueOf(MediaType.class, in.readString()), (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in), (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in), in.readString(), in.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(in) : null, in.readString(), in.readString(), in.readInt(), in.readString());
        }

        @Override // android.os.Parcelable.Creator
        @NotNull
        public final MicoMediaData[] newArray(int i) {
            return new MicoMediaData[i];
        }
    }

    @NotNull
    public final MediaType component1() {
        return this.mediaType;
    }

    @NotNull
    public final CharSequence component2() {
        return this.title;
    }

    @NotNull
    public final CharSequence component3() {
        return this.artist;
    }

    @Nullable
    public final String component4() {
        return this.cover;
    }

    @Nullable
    public final Bitmap component5() {
        return this.bitmap;
    }

    @Nullable
    public final String component6() {
        return this.source;
    }

    @Nullable
    public final String component7() {
        return this.deviceId;
    }

    public final int component8() {
        return this.playState;
    }

    @Nullable
    public final String component9() {
        return this.sourceBtMac;
    }

    @NotNull
    public final MicoMediaData copy(@NotNull MediaType mediaType, @NotNull CharSequence title, @NotNull CharSequence artist, @Nullable String str, @Nullable Bitmap bitmap, @Nullable String str2, @Nullable String str3, int i, @Nullable String str4) {
        Intrinsics.checkNotNullParameter(mediaType, "mediaType");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(artist, "artist");
        return new MicoMediaData(mediaType, title, artist, str, bitmap, str2, str3, i, str4);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MicoMediaData)) {
            return false;
        }
        MicoMediaData micoMediaData = (MicoMediaData) obj;
        return Intrinsics.areEqual(this.mediaType, micoMediaData.mediaType) && Intrinsics.areEqual(this.title, micoMediaData.title) && Intrinsics.areEqual(this.artist, micoMediaData.artist) && Intrinsics.areEqual(this.cover, micoMediaData.cover) && Intrinsics.areEqual(this.bitmap, micoMediaData.bitmap) && Intrinsics.areEqual(this.source, micoMediaData.source) && Intrinsics.areEqual(this.deviceId, micoMediaData.deviceId) && this.playState == micoMediaData.playState && Intrinsics.areEqual(this.sourceBtMac, micoMediaData.sourceBtMac);
    }

    public int hashCode() {
        MediaType mediaType = this.mediaType;
        int i = 0;
        int hashCode = (mediaType != null ? mediaType.hashCode() : 0) * 31;
        CharSequence charSequence = this.title;
        int hashCode2 = (hashCode + (charSequence != null ? charSequence.hashCode() : 0)) * 31;
        CharSequence charSequence2 = this.artist;
        int hashCode3 = (hashCode2 + (charSequence2 != null ? charSequence2.hashCode() : 0)) * 31;
        String str = this.cover;
        int hashCode4 = (hashCode3 + (str != null ? str.hashCode() : 0)) * 31;
        Bitmap bitmap = this.bitmap;
        int hashCode5 = (hashCode4 + (bitmap != null ? bitmap.hashCode() : 0)) * 31;
        String str2 = this.source;
        int hashCode6 = (hashCode5 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.deviceId;
        int hashCode7 = (((hashCode6 + (str3 != null ? str3.hashCode() : 0)) * 31) + Integer.hashCode(this.playState)) * 31;
        String str4 = this.sourceBtMac;
        if (str4 != null) {
            i = str4.hashCode();
        }
        return hashCode7 + i;
    }

    @NotNull
    public String toString() {
        return "MicoMediaData(mediaType=" + this.mediaType + ", title=" + this.title + ", artist=" + this.artist + ", cover=" + this.cover + ", bitmap=" + this.bitmap + ", source=" + this.source + ", deviceId=" + this.deviceId + ", playState=" + this.playState + ", sourceBtMac=" + this.sourceBtMac + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.mediaType.name());
        TextUtils.writeToParcel(this.title, parcel, 0);
        TextUtils.writeToParcel(this.artist, parcel, 0);
        parcel.writeString(this.cover);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            parcel.writeInt(1);
            bitmap.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.source);
        parcel.writeString(this.deviceId);
        parcel.writeInt(this.playState);
        parcel.writeString(this.sourceBtMac);
    }

    public /* synthetic */ MicoMediaData(MediaType mediaType, CharSequence charSequence, CharSequence charSequence2, String str, Bitmap bitmap, String str2, String str3, int i, String str4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? MediaType.NONE : mediaType, charSequence, charSequence2, str, (i2 & 16) != 0 ? null : bitmap, (i2 & 32) != 0 ? null : str2, (i2 & 64) != 0 ? null : str3, (i2 & 128) != 0 ? 0 : i, (i2 & 256) != 0 ? null : str4);
    }

    @NotNull
    public final MediaType getMediaType() {
        return this.mediaType;
    }

    @NotNull
    public final CharSequence getTitle() {
        return this.title;
    }

    @NotNull
    public final CharSequence getArtist() {
        return this.artist;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Nullable
    public final String getSource() {
        return this.source;
    }

    @Nullable
    public final String getDeviceId() {
        return this.deviceId;
    }

    public final int getPlayState() {
        return this.playState;
    }

    @Nullable
    public final String getSourceBtMac() {
        return this.sourceBtMac;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MicoMediaData(@NotNull MediaType mediaType, @NotNull CharSequence title, @NotNull CharSequence artist, @Nullable String str, @Nullable Bitmap bitmap, @Nullable String str2, @Nullable String str3, int i, @Nullable String str4) {
        super(0, 1, null);
        Intrinsics.checkNotNullParameter(mediaType, "mediaType");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(artist, "artist");
        this.mediaType = mediaType;
        this.title = title;
        this.artist = artist;
        this.cover = str;
        this.bitmap = bitmap;
        this.source = str2;
        this.deviceId = str3;
        this.playState = i;
        this.sourceBtMac = str4;
    }

    @Override // com.xiaomi.smarthome.ui.model.ISmartHomeModel
    @NotNull
    public CategoryDic type() {
        return CategoryDic.CATEGORY_MEDIA;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MicoMediaData(@org.jetbrains.annotations.NotNull com.xiaomi.micolauncher.common.media.MicoMediaEvent r15) {
        /*
            r14 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r15, r0)
            java.lang.String r0 = r15.getMediaType()
            java.lang.String r1 = "event.mediaType"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            com.xiaomi.smarthome.core.entity.MediaType r3 = com.xiaomi.smarthome.core.entity.MediaType.valueOf(r0)
            java.lang.CharSequence r4 = r15.getTitle()
            java.lang.String r0 = "event.title"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r0)
            java.lang.CharSequence r5 = r15.getArtist()
            java.lang.String r0 = "event.artist"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r0)
            java.lang.String r6 = r15.getCover()
            android.graphics.Bitmap r7 = r15.getBitmap()
            java.lang.String r8 = r15.getSource()
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 448(0x1c0, float:6.28E-43)
            r13 = 0
            r2 = r14
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.core.entity.MicoMediaData.<init>(com.xiaomi.micolauncher.common.media.MicoMediaEvent):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MicoMediaData(@org.jetbrains.annotations.NotNull com.xiaomi.micolauncher.common.media.RelayMediaEvent r12) {
        /*
            r11 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)
            com.xiaomi.smarthome.core.entity.MediaType r2 = com.xiaomi.smarthome.core.entity.MediaType.RELAY
            java.lang.CharSequence r3 = r12.getTitle()
            java.lang.String r0 = "event.title"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r0)
            java.lang.CharSequence r4 = r12.getArtist()
            java.lang.String r0 = "event.artist"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r0)
            java.lang.String r5 = ""
            android.graphics.Bitmap r6 = r12.getBitmap()
            java.lang.String r7 = r12.getSource()
            java.lang.String r8 = r12.getDeviceId()
            int r9 = r12.getDeviceState()
            java.lang.String r10 = r12.getSourceBtMac()
            r1 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.core.entity.MicoMediaData.<init>(com.xiaomi.micolauncher.common.media.RelayMediaEvent):void");
    }

    private final boolean bitmapChange(MicoMediaData micoMediaData) {
        return !BitmapUtils.compareBitmapByPixel(this.bitmap, micoMediaData.bitmap);
    }

    public final boolean isSameItem(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MicoMediaData)) {
            return false;
        }
        MicoMediaData micoMediaData = (MicoMediaData) obj;
        if (TextUtils.equals(this.title, micoMediaData.title) && TextUtils.equals(this.artist, micoMediaData.artist) && TextUtils.equals(this.cover, micoMediaData.cover) && this.mediaType == micoMediaData.mediaType && !bitmapChange(micoMediaData)) {
            String str = this.source;
            if (TextUtils.equals(str, str)) {
                return true;
            }
        }
        return false;
    }
}
