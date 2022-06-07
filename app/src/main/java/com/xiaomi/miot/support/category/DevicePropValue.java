package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DevicePropValue implements Parcelable {
    public static final Parcelable.Creator<DevicePropValue> CREATOR = new Parcelable.Creator<DevicePropValue>() { // from class: com.xiaomi.miot.support.category.DevicePropValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePropValue createFromParcel(Parcel parcel) {
            return new DevicePropValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePropValue[] newArray(int i) {
            return new DevicePropValue[i];
        }
    };
    private String format;
    private String unit;
    private Object value;
    private ValueConstrain valueConstrain;
    private String valueStr;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePropValue() {
    }

    protected DevicePropValue(Parcel parcel) {
        this.valueStr = parcel.readString();
        this.unit = parcel.readString();
        this.format = parcel.readString();
        this.valueConstrain = (ValueConstrain) parcel.readParcelable(ValueConstrain.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.value.toString());
        parcel.writeString(this.unit);
        parcel.writeString(this.format);
        parcel.writeParcelable(this.valueConstrain, 0);
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public String getValueStr() {
        return this.valueStr;
    }

    public void setValueStr(String str) {
        this.valueStr = str;
    }

    public ValueConstrain getValueConstrain() {
        return this.valueConstrain;
    }

    public void setValueConstrain(ValueConstrain valueConstrain) {
        this.valueConstrain = valueConstrain;
    }

    public static DevicePropValue createFromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        DevicePropValue devicePropValue = new DevicePropValue();
        devicePropValue.value = jSONObject.optString(b.p);
        devicePropValue.unit = jSONObject.optString("unit");
        devicePropValue.format = jSONObject.optString("format");
        JSONObject optJSONObject = jSONObject.optJSONObject("constrain");
        if (optJSONObject != null) {
            ValueConstrain valueConstrain = new ValueConstrain();
            int optInt = optJSONObject.optInt("valueType");
            valueConstrain.setValueType(optInt);
            if (optInt == 1) {
                String optString = optJSONObject.optString("rangeValue");
                if (!TextUtils.isEmpty(optString)) {
                    ValueRange valueRange = new ValueRange();
                    String[] split = optString.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    int length = split.length;
                    if (length == 2) {
                        valueRange.setMinValue(split[0]);
                        valueRange.setMaxValue(split[1]);
                    } else if (length == 3) {
                        valueRange.setMinValue(split[0]);
                        valueRange.setMaxValue(split[1]);
                        valueRange.setStep(split[2]);
                    }
                    valueConstrain.setValueRange(valueRange);
                }
            } else if (optInt == 2) {
                String optString2 = optJSONObject.optString("enumValue");
                if (!TextUtils.isEmpty(optString2)) {
                    valueConstrain.setEnumValueByString(optString2);
                }
            }
            devicePropValue.valueConstrain = valueConstrain;
        }
        return devicePropValue;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005a A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.value
            r1 = 0
            if (r0 == 0) goto L_0x0057
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: JSONException -> 0x0038
            r0.<init>()     // Catch: JSONException -> 0x0038
            java.lang.String r1 = "value"
            java.lang.Object r2 = r6.value     // Catch: JSONException -> 0x0036
            r0.put(r1, r2)     // Catch: JSONException -> 0x0036
            java.lang.String r1 = "unit"
            java.lang.String r2 = r6.unit     // Catch: JSONException -> 0x0036
            r0.put(r1, r2)     // Catch: JSONException -> 0x0036
            java.lang.String r1 = "format"
            java.lang.String r2 = r6.format     // Catch: JSONException -> 0x0036
            r0.put(r1, r2)     // Catch: JSONException -> 0x0036
            com.xiaomi.miot.support.category.ValueConstrain r1 = r6.valueConstrain     // Catch: JSONException -> 0x0036
            if (r1 == 0) goto L_0x0058
            java.lang.String r1 = "constrain"
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: JSONException -> 0x0036
            com.xiaomi.miot.support.category.ValueConstrain r3 = r6.valueConstrain     // Catch: JSONException -> 0x0036
            java.lang.String r3 = r3.toString()     // Catch: JSONException -> 0x0036
            r2.<init>(r3)     // Catch: JSONException -> 0x0036
            r0.put(r1, r2)     // Catch: JSONException -> 0x0036
            goto L_0x0058
        L_0x0036:
            r1 = move-exception
            goto L_0x003c
        L_0x0038:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x003c:
            java.lang.String r2 = "MICO_ME"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "DevicePropValue JsonException: "
            r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.e(r2, r1)
            goto L_0x0058
        L_0x0057:
            r0 = r1
        L_0x0058:
            if (r0 != 0) goto L_0x005e
            java.lang.String r0 = "{}"
            goto L_0x0062
        L_0x005e:
            java.lang.String r0 = r0.toString()
        L_0x0062:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miot.support.category.DevicePropValue.toString():java.lang.String");
    }
}
