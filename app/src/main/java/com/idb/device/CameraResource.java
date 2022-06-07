package com.idb.device;

import com.xiaomi.mi_connect_sdk.util.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CameraResource {
    CameraControl a;

    public String getType() {
        return ResourceType.TYPE_P2PSTREAM;
    }

    public void startVideoAndAudio(int i) {
        this.a = new CameraControl(CameraControl.CMD_START_VIDEOANDAUDIO);
        this.a.c = i;
    }

    public void startVideo(int i) {
        this.a = new CameraControl(CameraControl.CMD_START_VIDEO);
        this.a.c = i;
    }

    public void stopVideo() {
        this.a = new CameraControl(CameraControl.CMD_STOP_VIDEO);
    }

    public void startAudio() {
        this.a = new CameraControl(CameraControl.CMD_START_AUDIO);
    }

    public void stopAudio() {
        this.a = new CameraControl(CameraControl.CMD_STOP_AUDIO);
    }

    public void stopVideoAndAudio() {
        this.a = new CameraControl(CameraControl.CMD_STOP_VIDEOANDAUDIO);
    }

    public String getCommand() {
        CameraControl cameraControl = this.a;
        if (cameraControl == null) {
            return null;
        }
        return cameraControl.b;
    }

    public int getVideoQuality() {
        CameraControl cameraControl = this.a;
        return (cameraControl == null ? null : Integer.valueOf(cameraControl.c)).intValue();
    }

    /* loaded from: classes2.dex */
    public class CameraControl {
        public static final String CMD_START_AUDIO = "CMD_START_AUDIO";
        public static final String CMD_START_VIDEO = "CMD_START_VIDEO";
        public static final String CMD_START_VIDEOANDAUDIO = "CMD_START_VIDEOANDAUDIO";
        public static final String CMD_STOP_AUDIO = "CMD_STOP_AUDIO";
        public static final String CMD_STOP_VIDEO = "CMD_STOP_VIDEO";
        public static final String CMD_STOP_VIDEOANDAUDIO = "CMD_STOP_VIDEOANDAUDIO";
        public static final int VIDEOQUALITY_1080P = 3;
        public static final int VIDEOQUALITY_360P = 1;
        public static final int VIDEOQUALITY_720P = 2;
        public static final int VIDEOQUALITY_DEFAULT = 0;
        private String b;
        private int c;

        CameraControl(String str) {
            this.b = str;
            this.c = 0;
        }

        CameraControl(JSONObject jSONObject) {
            this.b = jSONObject.optString("command");
            this.c = jSONObject.optInt("videoQuality", 0);
        }

        public JSONObject toJson() {
            JSONObject jSONObject = new JSONObject();
            try {
                if (this.b == null) {
                    return jSONObject;
                }
                jSONObject.put("command", this.b);
                jSONObject.put("videoQuality", this.c);
                return jSONObject;
            } catch (JSONException e) {
                LogUtil.e("CameraResource", "", e);
                return null;
            }
        }
    }

    public JSONObject toJson() {
        JSONObject json;
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.a == null || (json = this.a.toJson()) == null) {
                return jSONObject;
            }
            jSONObject.put("cameraControl", json);
            return jSONObject;
        } catch (JSONException e) {
            LogUtil.e("CameraResource", "", e);
            return null;
        }
    }

    public String toJsonString() {
        JSONObject json = toJson();
        if (json == null) {
            return null;
        }
        return json.toString();
    }

    public static CameraResource buildFromJson(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            LogUtil.e("CameraResource", "", e);
            jSONObject = null;
        }
        return buildFromJson(jSONObject);
    }

    public static CameraResource buildFromJson(JSONObject jSONObject) {
        CameraResource cameraResource = new CameraResource();
        JSONObject optJSONObject = jSONObject.optJSONObject("cameraControl");
        if (optJSONObject != null) {
            cameraResource.getClass();
            cameraResource.a = new CameraControl(optJSONObject);
        }
        return cameraResource;
    }
}
