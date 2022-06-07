package com.xiaomi.miplay.mylibrary;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miplay.mylibrary.mirror.CmdSessionControl;
import com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager;
import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.BitMapUtils;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MetaInfoManager {
    private static final String b = "MetaInfoManager";
    private DeviceManager c;
    private Context e;
    private final Object a = new Object();
    private MediaMetaData d = null;

    public MediaMetaData getMetadata() {
        return this.d;
    }

    public void setMetadata(MediaMetaData mediaMetaData) {
        Logger.i(b, "setMetadata.", new Object[0]);
        this.d = mediaMetaData;
    }

    public MetaInfoManager(DeviceManager deviceManager, Context context) {
        this.c = deviceManager;
        this.e = context;
    }

    public synchronized com.xiaomi.miplay.audioclient.MediaMetaData parseClientDevice(MediaMetaData mediaMetaData, int i, String str, int i2) {
        com.xiaomi.miplay.audioclient.MediaMetaData mediaMetaData2;
        Logger.d(b, "parseClientDevice.", new Object[0]);
        mediaMetaData2 = new com.xiaomi.miplay.audioclient.MediaMetaData();
        mediaMetaData2.setArtist(mediaMetaData.getArtist());
        mediaMetaData2.setAlbum(mediaMetaData.getAlbum());
        mediaMetaData2.setTitle(mediaMetaData.getTitle());
        mediaMetaData2.setDuration(mediaMetaData.getDuration());
        mediaMetaData2.setmLrc(mediaMetaData.getLrc());
        if (mediaMetaData.getArt() != null) {
            if (i == 4) {
                Logger.i(b, "box type no-set art", new Object[0]);
            } else {
                mediaMetaData2.setArt(mediaMetaData.getArt());
            }
        }
        mediaMetaData2.setPackgeName(str);
        mediaMetaData2.setSourceName(this.c.getLocalPhoneName());
        mediaMetaData2.setDeviceState(i2);
        Logger.d(b, "parseClientDevice end", new Object[0]);
        return mediaMetaData2;
    }

    public boolean MediaMetaDataEquals(MediaMetaData mediaMetaData, MediaMetaData mediaMetaData2) {
        Logger.i(b, "MediaMetaDataEquals.", new Object[0]);
        if (!TextUtils.equals(mediaMetaData.getArtist(), mediaMetaData2.getArtist()) || !TextUtils.equals(mediaMetaData.getAlbum(), mediaMetaData2.getAlbum()) || !TextUtils.equals(mediaMetaData.getTitle(), mediaMetaData2.getTitle()) || mediaMetaData.getDuration() != mediaMetaData2.getDuration()) {
            return false;
        }
        if (mediaMetaData.getArt() == null && mediaMetaData2.getArt() != null) {
            return false;
        }
        if (mediaMetaData.getArt() != null && mediaMetaData2.getArt() == null) {
            return false;
        }
        if (mediaMetaData.getArt() != null && mediaMetaData2.getArt() != null && !Constant.compareBitmap(mediaMetaData.getArt(), mediaMetaData2.getArt())) {
            Logger.i(b, "bitmap change.", new Object[0]);
            return false;
        } else if (!TextUtils.isEmpty(mediaMetaData.getLrc()) || TextUtils.isEmpty(mediaMetaData2.getLrc())) {
            return (TextUtils.isEmpty(mediaMetaData.getLrc()) || !TextUtils.isEmpty(mediaMetaData2.getLrc())) && TextUtils.equals(mediaMetaData.getLrc(), mediaMetaData2.getLrc());
        } else {
            return false;
        }
    }

    public boolean isLastSongArt(MediaMetaData mediaMetaData, MediaMetaData mediaMetaData2) {
        Logger.i(b, "isLastSongArt.", new Object[0]);
        if (mediaMetaData.getArt() == null || mediaMetaData2.getArt() == null) {
            return false;
        }
        return (!TextUtils.equals(mediaMetaData.getTitle(), mediaMetaData2.getTitle()) || mediaMetaData.getDuration() != mediaMetaData2.getDuration()) && Constant.compareBitmap(mediaMetaData.getArt(), mediaMetaData2.getArt());
    }

    public synchronized byte[] mediaMetaDataToJson(com.xiaomi.miplay.audioclient.MediaMetaData mediaMetaData, MiDevice miDevice) {
        Logger.d(b, "mediaMetaDataToJson.", new Object[0]);
        if (mediaMetaData == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("mArtist", mediaMetaData.getArtist());
            jSONObject.putOpt("mAlbum", mediaMetaData.getAlbum());
            jSONObject.putOpt("mTitle", mediaMetaData.getTitle());
            jSONObject.putOpt("mDuration", Long.valueOf(mediaMetaData.getDuration()));
            jSONObject.putOpt("id", mediaMetaData.getId());
            jSONObject.putOpt("mCoverUrl", mediaMetaData.getCoverUrl());
            jSONObject.putOpt("status", Integer.valueOf(mediaMetaData.getStatus()));
            jSONObject.putOpt(SchemaActivity.KEY_VOLUME, Long.valueOf(mediaMetaData.getVolume()));
            if (mediaMetaData.getArt() != null) {
                jSONObject.putOpt("mArt", BitMapUtils.bitmapToBase64(mediaMetaData.getArt()));
            } else {
                jSONObject.putOpt("mArt", "");
            }
            if (miDevice.getDeviceType() == 2 || miDevice.getDeviceType() == 16) {
                String str = mediaMetaData.getmLrc();
                if (TextUtils.isEmpty(str)) {
                    jSONObject.putOpt("mLrc", "");
                    Logger.i(b, "mLrc is null", new Object[0]);
                } else {
                    String str2 = "";
                    if (Build.VERSION.SDK_INT >= 26) {
                        str2 = Constant.stringToBase64(str);
                    }
                    jSONObject.putOpt("mLrc", str2);
                    String str3 = b;
                    Logger.i(str3, "mLrc:" + str2, new Object[0]);
                }
                jSONObject.putOpt("mPackageName", mediaMetaData.getPackgeName());
            }
            jSONObject.putOpt("mSourceName", mediaMetaData.getSourceName());
            jSONObject.putOpt("mDeviceState", Integer.valueOf(mediaMetaData.getDeviceState()));
            String jSONObject2 = jSONObject.toString();
            String str4 = b;
            Logger.d(str4, "jsonStr:" + jSONObject2, new Object[0]);
            return jSONObject2.getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            Logger.e(b, "setMediaMetaData", e);
            return null;
        }
    }

    public void setMetaInfo(final ActiveAudioSessionManager activeAudioSessionManager, final MiDevice miDevice, final Map<String, CmdSessionControl> map, final int i) {
        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MetaInfoManager.1
            @Override // java.lang.Runnable
            public void run() {
                Logger.d(MetaInfoManager.b, "setMetaInfo.", new Object[0]);
                if (activeAudioSessionManager.getTopActiveSessionRecord() == null) {
                    Logger.i(MetaInfoManager.b, "TopActiveSessionRecord is null", new Object[0]);
                    com.xiaomi.miplay.audioclient.MediaMetaData mediaMetaData = new com.xiaomi.miplay.audioclient.MediaMetaData();
                    if (map.get(miDevice.getMac()) == null) {
                        Logger.i(MetaInfoManager.b, "cmdSessionControlMap on a null object", new Object[0]);
                        return;
                    }
                    String str = MetaInfoManager.b;
                    Logger.i(str, "setMediaInfo = " + mediaMetaData.toString(), new Object[0]);
                    ((CmdSessionControl) map.get(miDevice.getMac())).setMediaInfo(MetaInfoManager.this.mediaMetaDataToJson(mediaMetaData, miDevice));
                    MetaInfoManager.this.setMetadata(null);
                    return;
                }
                MediaMetaData mediaMetaData2 = activeAudioSessionManager.getTopActiveSessionRecord().getAudioMediaController().getMediaMetaData();
                com.xiaomi.miplay.audioclient.MediaMetaData parseClientDevice = MetaInfoManager.this.parseClientDevice(mediaMetaData2, miDevice.getDeviceType(), activeAudioSessionManager.getTopActiveSessionRecord().getPackageName(), i);
                if (map.get(miDevice.getMac()) == null) {
                    Logger.i(MetaInfoManager.b, "cmdSessionControlMap on a null object", new Object[0]);
                    return;
                }
                if (MetaInfoManager.this.c.getVolumeMap().get(miDevice.getMac()) != null) {
                    parseClientDevice.setVolume(MetaInfoManager.this.c.getVolumeMap().get(miDevice.getMac()).intValue());
                }
                if (MetaInfoManager.this.c.getPlayStatusMap().get(miDevice.getMac()) != null) {
                    parseClientDevice.setStatus(MetaInfoManager.this.c.getPlayStatusMap().get(miDevice.getMac()).intValue());
                }
                String str2 = MetaInfoManager.b;
                Logger.i(str2, "setMediaInfo = " + parseClientDevice.toString(), new Object[0]);
                MetaInfoManager.this.setMetadata(mediaMetaData2);
                ((CmdSessionControl) map.get(miDevice.getMac())).setMediaInfo(MetaInfoManager.this.mediaMetaDataToJson(parseClientDevice, miDevice));
            }
        });
    }
}
