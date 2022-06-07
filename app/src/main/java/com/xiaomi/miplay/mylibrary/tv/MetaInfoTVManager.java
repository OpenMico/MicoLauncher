package com.xiaomi.miplay.mylibrary.tv;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miplay.audioclient.tv.TVMediaMetaData;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.BitMapUtils;
import com.xiaomi.miplay.mylibrary.utils.ByteUtils;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MetaInfoTVManager {
    private static final String a = "MetaInfoTVManager";
    private Map<String, String> b = new ConcurrentHashMap();

    /* loaded from: classes4.dex */
    private static class a {
        public static MetaInfoTVManager a = new MetaInfoTVManager();
    }

    public static MetaInfoTVManager getInstance() {
        return a.a;
    }

    public synchronized TVMediaMetaData analysisMediaInfo(byte[] bArr) {
        int deviceKeyLen;
        int i;
        if (bArr == null) {
            Logger.d(a, "mediainfo==null", new Object[0]);
            return null;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= bArr.length || (deviceKeyLen = ByteUtils.getDeviceKeyLen(bArr, i3)) == -1) {
                break;
            }
            int i4 = i3 + 1;
            String deviceValue = ByteUtils.getDeviceValue(bArr, deviceKeyLen, i4);
            if (TextUtils.isEmpty(deviceValue)) {
                break;
            }
            int i5 = i4 + deviceKeyLen;
            Logger.d(a, "key:" + deviceValue, new Object[0]);
            int deviceTypeLen = ByteUtils.getDeviceTypeLen(bArr, i5);
            Logger.i(a, "typeLen:" + deviceTypeLen, new Object[0]);
            int i6 = i5 + 1;
            if (deviceTypeLen == 20) {
                i = ByteUtils.getDeviceValueInfoLen(bArr, i6, 4);
            } else {
                i = ByteUtils.getDeviceValueInfoLen(bArr, i6, 2);
            }
            if (i == -1) {
                break;
            }
            int i7 = deviceTypeLen == 20 ? i6 + 4 : i6 + 2;
            String deviceValue2 = ByteUtils.getDeviceValue(bArr, i, i7);
            i3 = i7 + i;
            if (!deviceValue.equals("mArt")) {
                Logger.d(a, "value :" + deviceValue2, new Object[0]);
            }
            if (!TextUtils.isEmpty(deviceValue)) {
                this.b.put(deviceValue, deviceValue2);
            }
            if (i3 == bArr.length) {
                Logger.i(a, "exit MediaInfo analysis", new Object[0]);
                break;
            }
            i2++;
        }
        if (this.b.size() == 0) {
            Logger.i(a, "analysisMediaInfo fail", new Object[0]);
            return null;
        }
        TVMediaMetaData tVMediaMetaData = new TVMediaMetaData();
        for (String str : this.b.keySet()) {
            if (str.equals("mArtist")) {
                tVMediaMetaData.setArtist(this.b.get(str));
            } else if (str.equals("mAlbum")) {
                tVMediaMetaData.setAlbum(this.b.get(str));
            } else if (str.equals("mTitle")) {
                tVMediaMetaData.setTitle(this.b.get(str));
            } else if (str.equals("mDuration")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setDuration(Long.parseLong(this.b.get(str)));
                }
            } else if (str.equals("id")) {
                tVMediaMetaData.setId(this.b.get(str));
            } else if (str.equals("mCoverUrl")) {
                tVMediaMetaData.setCoverUrl(this.b.get(str));
            } else if (str.equals("status")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setStatus(Integer.parseInt(this.b.get(str)));
                }
            } else if (str.equals(SchemaActivity.KEY_VOLUME)) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setVolume(Long.parseLong(this.b.get(str)));
                }
            } else if (str.equals("mArt")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setArt(BitMapUtils.base64ToBitmap(this.b.get(str)));
                } else {
                    Logger.d(a, "art is null", new Object[0]);
                }
            } else if (str.equals("mPosition")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setPosition(Long.parseLong(this.b.get(str)));
                }
            } else if (str.equals("mLrc")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    String str2 = "";
                    if (Build.VERSION.SDK_INT >= 26) {
                        str2 = Constant.base64ToString(this.b.get(str));
                    }
                    tVMediaMetaData.setLrc(str2);
                } else {
                    Logger.d(a, "metaData is null", new Object[0]);
                }
            } else if (str.equals("mPackageName")) {
                if (!TextUtils.isEmpty(this.b.get(str))) {
                    tVMediaMetaData.setAudioChannel(this.b.get(str));
                }
            } else if (str.equals("mDeviceID")) {
                tVMediaMetaData.setDeviceID(this.b.get(str));
            } else if (str.equals("mSourceName")) {
                tVMediaMetaData.setSourceName(this.b.get(str));
            } else if (str.equals("mSourceBtMac")) {
                tVMediaMetaData.setSourceBtMac(this.b.get(str));
            } else if (!str.equals("mDeviceState")) {
                Log.e(a, "invalid field:" + str);
            } else if (!TextUtils.isEmpty(this.b.get(str))) {
                tVMediaMetaData.setDeviceState(Integer.parseInt(this.b.get(str)));
            }
        }
        this.b.clear();
        return tVMediaMetaData;
    }

    public synchronized byte[] mediaTVMetaDataToJson(TVMediaMetaData tVMediaMetaData) {
        Logger.d(a, "mediaTVMetaDataToJson.", new Object[0]);
        if (tVMediaMetaData == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("mArtist", tVMediaMetaData.getArtist());
            jSONObject.putOpt("mAlbum", tVMediaMetaData.getAlbum());
            jSONObject.putOpt("mTitle", tVMediaMetaData.getTitle());
            jSONObject.putOpt("mDuration", Long.valueOf(tVMediaMetaData.getDuration()));
            jSONObject.putOpt("id", tVMediaMetaData.getId());
            jSONObject.putOpt("mCoverUrl", tVMediaMetaData.getCoverUrl());
            jSONObject.putOpt("status", Integer.valueOf(tVMediaMetaData.getStatus()));
            jSONObject.putOpt(SchemaActivity.KEY_VOLUME, Long.valueOf(tVMediaMetaData.getVolume()));
            if (tVMediaMetaData.getArt() != null) {
                jSONObject.putOpt("mArt", BitMapUtils.bitmapToBase64(tVMediaMetaData.getArt()));
            } else {
                jSONObject.putOpt("mArt", "");
            }
            String lrc = tVMediaMetaData.getLrc();
            if (TextUtils.isEmpty(lrc)) {
                jSONObject.putOpt("mLrc", "");
                Logger.i(a, "mLrc is null", new Object[0]);
            } else {
                String str = "";
                if (Build.VERSION.SDK_INT >= 26) {
                    str = Constant.stringToBase64(lrc);
                }
                jSONObject.putOpt("mLrc", str);
                String str2 = a;
                Logger.i(str2, "mLrc:" + str, new Object[0]);
            }
            String jSONObject2 = jSONObject.toString();
            String str3 = a;
            Logger.d(str3, "jsonStr:" + jSONObject2, new Object[0]);
            return jSONObject2.getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            Logger.e(a, "setMediaMetaData", e);
            return null;
        }
    }
}
