package com.xiaomi.micolauncher.skills.music.controller;

import android.media.MediaMetadata;
import android.support.v4.media.MediaMetadataCompat;
import com.xiaomi.micolauncher.api.model.Remote;

/* loaded from: classes3.dex */
public class MediaHelper {
    public static MediaMetadata createMediaMetadata(Remote.Response.PlayingData playingData) {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, playingData.title);
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, playingData.getSubTitle());
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, playingData.albumName);
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, playingData.cover);
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, playingData.outCoverBitmap);
        return builder.build();
    }
}
