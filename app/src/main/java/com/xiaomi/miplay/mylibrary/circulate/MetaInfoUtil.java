package com.xiaomi.miplay.mylibrary.circulate;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.BitMapUtils;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MetaInfoUtil {
    private static final String a = "MetaInfoUtil";

    /* loaded from: classes4.dex */
    public static class a {
        public static MetaInfoUtil a = new MetaInfoUtil();
    }

    public static MetaInfoUtil getInstance() {
        return a.a;
    }

    public byte[] metaInfoToJson(MetaData metaData) {
        Logger.d(a, "metaInfoToJson.", new Object[0]);
        if (metaData == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("mPlatform", metaData.getPlatform());
            jSONObject.putOpt("mTitle", metaData.getTitle());
            jSONObject.putOpt("mDuration", Long.valueOf(metaData.getDuration()));
            jSONObject.putOpt("mPosition", Long.valueOf(metaData.getPosition()));
            jSONObject.putOpt("mURL", metaData.getURL());
            jSONObject.putOpt("mMux", metaData.getMux());
            jSONObject.putOpt("mCodec", metaData.getCodec());
            jSONObject.putOpt("mReverso", metaData.getReverso());
            if (metaData.getArt() != null) {
                jSONObject.putOpt("mArt", BitMapUtils.bitmapToBase64(metaData.getArt()));
            } else {
                jSONObject.putOpt("mArt", "");
            }
            jSONObject.putOpt("mPropertiesInfo", metaData.getPropertiesInfo());
            String jSONObject2 = jSONObject.toString();
            String str = a;
            Logger.i(str, "jsonStr:" + jSONObject2, new Object[0]);
            return jSONObject2.getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            Logger.e(a, "setMediaMetaData", e);
            return null;
        }
    }
}
