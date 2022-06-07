package com.xiaomi.miplay.mylibrary.session.data;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class MediaMetaData {
    @Nullable
    private String a;
    @Nullable
    private String b;
    @Nullable
    private String c;
    @Nullable
    private Bitmap d;
    private long e;
    private String f;

    public MediaMetaData() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.e = 0L;
        this.d = null;
        this.f = "";
    }

    public MediaMetaData(@Nullable MediaMetadata mediaMetadata) {
        if (mediaMetadata != null) {
            Bitmap bitmap = mediaMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ART);
            bitmap = bitmap == null ? mediaMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART) : bitmap;
            this.a = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
            this.c = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
            this.b = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);
            this.e = mediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            this.f = mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION);
            this.d = bitmap;
            return;
        }
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = null;
        this.e = 0L;
        this.f = "";
    }

    public static boolean isMediaMetadataInvalid(@Nullable MediaMetadata mediaMetadata) {
        if (mediaMetadata == null) {
            return true;
        }
        return TextUtils.isEmpty(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)) && TextUtils.isEmpty(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE)) && TextUtils.isEmpty(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM)) && mediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION) <= 0;
    }

    @Nullable
    public Bitmap getArt() {
        return this.d;
    }

    public void setArt(@Nullable Bitmap bitmap) {
        this.d = bitmap;
    }

    @Nullable
    public String getArtist() {
        return this.a;
    }

    public void setArtist(@Nullable String str) {
        this.a = str;
    }

    @Nullable
    public String getAlbum() {
        return this.b;
    }

    public void setAlbum(@Nullable String str) {
        this.b = str;
    }

    @Nullable
    public String getTitle() {
        return this.c;
    }

    public void setTitle(@Nullable String str) {
        this.c = str;
    }

    public long getDuration() {
        return this.e;
    }

    public void setDuration(long j) {
        this.e = j;
    }

    public String getLrc() {
        return this.f;
    }

    public void setLrc(String str) {
        this.f = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MediaMetaData{mArtist='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", mAlbum='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", mTitle='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", mArt=");
        sb.append(this.d == null ? "null" : "valid");
        sb.append(", mDuration=");
        sb.append(this.e);
        sb.append(", mLrc='");
        sb.append(this.f);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
