package com.xiaomi.miot;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PropValueConstrain {
    private String enumValue;
    private String rangeValue;
    private int valueType;

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int i) {
        this.valueType = i;
    }

    public String getEnumValue() {
        return this.enumValue;
    }

    public void setEnumValue(String str) {
        this.enumValue = str;
    }

    public String getRangeValue() {
        return this.rangeValue;
    }

    public void setRangeValue(String str) {
        this.rangeValue = str;
    }

    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("valueType", this.valueType);
            if (!TextUtils.isEmpty(this.enumValue)) {
                jSONObject.put("enumValue", this.enumValue);
            }
            if (!TextUtils.isEmpty(this.rangeValue)) {
                jSONObject.put("rangeValue", this.rangeValue);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.e(MicoSupConstants.TAG_SUP_MIJIA, "PropValueConstrain json error: " + e.getMessage());
            return "{}";
        }
    }
}
