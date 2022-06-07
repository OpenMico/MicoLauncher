package com.idb.device;

import android.util.Log;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import org.json.JSONException;
import org.json.JSONObject;
import xcrash.TombstoneParser;

/* loaded from: classes2.dex */
public class IotDeviceInfo {
    private String a;
    private long b;
    private String c;
    private String d;
    private int e;
    private String f;
    private String g;
    private String h;
    private String i;
    private int j;
    private String k;
    private String l;
    private String m;
    private int n;
    private String o;
    private int p;
    private boolean q;
    private String r;
    private int s;

    private IotDeviceInfo() {
    }

    public static final IotDeviceInfo buildFromJson(String str) {
        try {
            return buildFromJson(new JSONObject(str));
        } catch (JSONException e) {
            Log.e("IotDeviceInfo", "", e);
            return null;
        }
    }

    public static final IotDeviceInfo buildFromJson(JSONObject jSONObject) {
        try {
            IotDeviceInfo iotDeviceInfo = new IotDeviceInfo();
            iotDeviceInfo.a = jSONObject.getString("did");
            iotDeviceInfo.b = jSONObject.getLong(OneTrack.Param.UID);
            iotDeviceInfo.c = jSONObject.getString("token");
            iotDeviceInfo.d = jSONObject.getString("name");
            iotDeviceInfo.e = jSONObject.getInt(TombstoneParser.keyProcessId);
            iotDeviceInfo.f = jSONObject.optString("localip");
            iotDeviceInfo.g = jSONObject.getString(b.B);
            iotDeviceInfo.h = jSONObject.optString("ssid");
            iotDeviceInfo.i = jSONObject.getString("bssid");
            iotDeviceInfo.j = jSONObject.optInt("rssi");
            iotDeviceInfo.k = jSONObject.optString("longitude");
            iotDeviceInfo.l = jSONObject.optString("latitude");
            iotDeviceInfo.m = jSONObject.optString("parent_id");
            iotDeviceInfo.n = jSONObject.getInt("show_mode");
            iotDeviceInfo.o = jSONObject.getString("model");
            iotDeviceInfo.p = jSONObject.getInt("permitLevel");
            iotDeviceInfo.q = jSONObject.getBoolean("isOnline");
            iotDeviceInfo.r = jSONObject.optString("spec_type");
            iotDeviceInfo.s = jSONObject.optInt("voice_ctrl");
            return iotDeviceInfo;
        } catch (JSONException e) {
            Log.e("IotDeviceInfo", "Error when create IotDeviceInfo", e);
            return null;
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            a(jSONObject, "did", this.a);
            a(jSONObject, OneTrack.Param.UID, Long.valueOf(this.b));
            a(jSONObject, "token", this.c);
            a(jSONObject, "name", this.d);
            a(jSONObject, TombstoneParser.keyProcessId, Integer.valueOf(this.e));
            a(jSONObject, "localip", this.f);
            a(jSONObject, b.B, this.g);
            a(jSONObject, "ssid", this.h);
            a(jSONObject, "bssid", this.i);
            a(jSONObject, "rssi", Integer.valueOf(this.j));
            a(jSONObject, "longitude", this.k);
            a(jSONObject, "latitude", this.l);
            a(jSONObject, "parent_id", this.m);
            a(jSONObject, "show_mode", Integer.valueOf(this.n));
            a(jSONObject, "model", this.o);
            a(jSONObject, "permitLevel", Integer.valueOf(this.p));
            a(jSONObject, "isOnline", Boolean.valueOf(this.q));
            a(jSONObject, "spec_type", this.r);
            a(jSONObject, "voice_ctrl", Integer.valueOf(this.s));
        } catch (JSONException e) {
            Log.e("IotDeviceInfo", "", e);
        }
        return jSONObject;
    }

    public String toJsonString() {
        return toJson().toString();
    }

    private static void a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj != null) {
            jSONObject.put(str, obj);
        }
    }

    public String getDid() {
        return this.a;
    }

    public long getUid() {
        return this.b;
    }

    public String getToken() {
        return this.c;
    }

    public String getName() {
        return this.d;
    }

    public int getPid() {
        return this.e;
    }

    public String getLocalip() {
        return this.f;
    }

    public String getMac() {
        return this.g;
    }

    public String getSsid() {
        return this.h;
    }

    public String getBssid() {
        return this.i;
    }

    public int getRssi() {
        return this.j;
    }

    public String getLongitude() {
        return this.k;
    }

    public String getLatitude() {
        return this.l;
    }

    public String getParent_id() {
        return this.m;
    }

    public int getShow_mode() {
        return this.n;
    }

    public String getModel() {
        return this.o;
    }

    public int getPermitLevel() {
        return this.p;
    }

    public boolean isOnline() {
        return this.q;
    }

    public String getSpec_type() {
        return this.r;
    }

    public int getVoice_ctrl() {
        return this.s;
    }
}
