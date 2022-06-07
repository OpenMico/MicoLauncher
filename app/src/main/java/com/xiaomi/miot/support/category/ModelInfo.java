package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.mipush.sdk.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ModelInfo implements Parcelable {
    public static final Parcelable.Creator<ModelInfo> CREATOR = new Parcelable.Creator<ModelInfo>() { // from class: com.xiaomi.miot.support.category.ModelInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelInfo createFromParcel(Parcel parcel) {
            return new ModelInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelInfo[] newArray(int i) {
            return new ModelInfo[i];
        }
    };
    private String categoryName;
    private String deviceType;
    private String model;
    private String name;
    private String specUrn;
    private String subCategoryId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ModelInfo() {
    }

    protected ModelInfo(Parcel parcel) {
        this.model = parcel.readString();
        this.specUrn = parcel.readString();
        this.name = parcel.readString();
        this.subCategoryId = parcel.readString();
        this.categoryName = parcel.readString();
        this.deviceType = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.model);
        parcel.writeString(this.specUrn);
        parcel.writeString(this.name);
        parcel.writeString(this.subCategoryId);
        parcel.writeString(this.categoryName);
        parcel.writeString(this.deviceType);
    }

    private ModelInfo setModel(String str) {
        this.model = str;
        return this;
    }

    private ModelInfo setSpecUrn(String str) {
        this.specUrn = str;
        String[] split = str.split(Constants.COLON_SEPARATOR);
        if (split == null || split.length <= 3) {
            this.deviceType = "";
        } else {
            this.deviceType = split[3];
        }
        return this;
    }

    private ModelInfo setName(String str) {
        this.name = str;
        return this;
    }

    private ModelInfo setSubCategoryId(String str) {
        this.subCategoryId = str;
        return this;
    }

    private ModelInfo setCategoryName(String str) {
        this.categoryName = str;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    public String getSpecUrn() {
        return this.specUrn;
    }

    public String getName() {
        return this.name;
    }

    public String getSubCategoryId() {
        return this.subCategoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public static ModelInfo createFromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            return new ModelInfo().setModel(jSONObject.optString("model")).setSpecUrn(jSONObject.optString("specUrn")).setName(jSONObject.optString("name")).setSubCategoryId(jSONObject.optString("subCategoryId")).setCategoryName(jSONObject.optString("categoryName"));
        }
        MiotLog.d("modelInfo json is null");
        return null;
    }
}
