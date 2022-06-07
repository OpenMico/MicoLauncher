package com.xiaomi.miot.scene;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CommonUsedScene implements Parcelable {
    public static final Parcelable.Creator<CommonUsedScene> CREATOR = new Parcelable.Creator<CommonUsedScene>() { // from class: com.xiaomi.miot.scene.CommonUsedScene.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonUsedScene createFromParcel(Parcel parcel) {
            return new CommonUsedScene(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonUsedScene[] newArray(int i) {
            return new CommonUsedScene[i];
        }
    };
    public static final int OLD_TYPE = 1;
    public static final int SPEC_SCENE_TYPE = 2;
    public String iconUrl;
    public long sceneId;
    public String sceneName;
    public int sceneType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CommonUsedScene() {
    }

    public CommonUsedScene(Parcel parcel) {
        this.sceneId = parcel.readLong();
        this.sceneName = parcel.readString();
        this.sceneType = parcel.readInt();
        this.iconUrl = parcel.readString();
    }

    public static CommonUsedScene parseItemFrom(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        CommonUsedScene commonUsedScene = new CommonUsedScene();
        commonUsedScene.sceneId = Long.parseLong(jSONObject.optString("scene_id"));
        commonUsedScene.sceneName = jSONObject.optString("scene_name");
        commonUsedScene.sceneType = jSONObject.optInt("scene_type");
        commonUsedScene.iconUrl = jSONObject.optString("icon");
        return commonUsedScene;
    }

    public static List<CommonUsedScene> parseListFrom(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                CommonUsedScene commonUsedScene = new CommonUsedScene();
                commonUsedScene.sceneId = Long.parseLong(optJSONObject.optString("scene_id"));
                commonUsedScene.sceneName = optJSONObject.optString("scene_name");
                commonUsedScene.sceneType = optJSONObject.optInt("scene_type");
                commonUsedScene.iconUrl = optJSONObject.optString("icon");
                arrayList.add(commonUsedScene);
            }
        }
        return arrayList;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("scene_id", this.sceneId + "");
            jSONObject.put("scene_type", this.sceneType);
            jSONObject.put("scene_name", this.sceneName);
            jSONObject.put("icon", this.iconUrl);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getCommonUsed() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("scene_id", this.sceneId + "");
            jSONObject.put("scene_type", this.sceneType);
            jSONObject.put("common_use", true);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getUnCommonUsed() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("scene_id", this.sceneId + "");
            jSONObject.put("scene_type", this.sceneType);
            jSONObject.put("common_use", false);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.sceneId);
        parcel.writeString(this.sceneName);
        parcel.writeInt(this.sceneType);
        parcel.writeString(this.iconUrl);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CommonUsedScene commonUsedScene = (CommonUsedScene) obj;
        if (this.sceneId == commonUsedScene.sceneId && this.sceneType == commonUsedScene.sceneType && Objects.equals(this.sceneName, commonUsedScene.sceneName)) {
            return Objects.equals(this.iconUrl, commonUsedScene.iconUrl);
        }
        return false;
    }

    public int hashCode() {
        long j = this.sceneId;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        String str = this.sceneName;
        int i2 = 0;
        int hashCode = (((i + (str != null ? str.hashCode() : 0)) * 31) + this.sceneType) * 31;
        String str2 = this.iconUrl;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return hashCode + i2;
    }
}
