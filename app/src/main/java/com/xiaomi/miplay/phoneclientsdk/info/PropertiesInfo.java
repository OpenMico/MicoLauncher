package com.xiaomi.miplay.phoneclientsdk.info;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PropertiesInfo implements Parcelable {
    public static final Parcelable.Creator<PropertiesInfo> CREATOR = new Parcelable.Creator<PropertiesInfo>() { // from class: com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo.1
        /* renamed from: a */
        public PropertiesInfo createFromParcel(Parcel parcel) {
            return new PropertiesInfo(parcel);
        }

        /* renamed from: a */
        public PropertiesInfo[] newArray(int i) {
            return new PropertiesInfo[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public String getPrivateInfo() {
        return this.b;
    }

    public void setPrivateInfo(String str) {
        this.b = str;
    }

    public String getPlatform() {
        return this.c;
    }

    public void setPlatform(String str) {
        this.c = str;
    }

    public String getVersion() {
        return this.d;
    }

    public void setVersion(String str) {
        this.d = str;
    }

    public PropertiesInfo() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
    }

    protected PropertiesInfo(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }

    @SuppressLint({"LongLogTag"})
    public String toJSONString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", getId());
            jSONObject.put("privateInfo", getPrivateInfo());
            jSONObject.put("platform", getPlatform());
            jSONObject.put("version", getVersion());
            Log.i("miplay_client_propertiesinfo", "Encode JSON String success");
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("miplay_client_propertiesinfo", "Encode JSON String Error!");
            return null;
        }
    }
}
