package com.xiaomi.miot;

/* loaded from: classes2.dex */
public class DevicePropValue {
    private String format;
    private String unit;
    private Object value;
    private PropValueConstrain valueConstrain;

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

    public PropValueConstrain getValueConstrain() {
        return this.valueConstrain;
    }

    public void setValueConstrain(PropValueConstrain propValueConstrain) {
        this.valueConstrain = propValueConstrain;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0058 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.value
            r1 = 0
            if (r0 == 0) goto L_0x0055
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: JSONException -> 0x0036
            r0.<init>()     // Catch: JSONException -> 0x0036
            java.lang.String r1 = "value"
            java.lang.Object r2 = r6.value     // Catch: JSONException -> 0x0034
            r0.put(r1, r2)     // Catch: JSONException -> 0x0034
            java.lang.String r1 = "unit"
            java.lang.String r2 = r6.unit     // Catch: JSONException -> 0x0034
            r0.put(r1, r2)     // Catch: JSONException -> 0x0034
            java.lang.String r1 = "format"
            java.lang.String r2 = r6.format     // Catch: JSONException -> 0x0034
            r0.put(r1, r2)     // Catch: JSONException -> 0x0034
            com.xiaomi.miot.PropValueConstrain r1 = r6.valueConstrain     // Catch: JSONException -> 0x0034
            if (r1 == 0) goto L_0x0056
            java.lang.String r1 = "constrain"
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: JSONException -> 0x0034
            com.xiaomi.miot.PropValueConstrain r3 = r6.valueConstrain     // Catch: JSONException -> 0x0034
            java.lang.String r3 = r3.toString()     // Catch: JSONException -> 0x0034
            r2.<init>(r3)     // Catch: JSONException -> 0x0034
            r0.put(r1, r2)     // Catch: JSONException -> 0x0034
            goto L_0x0056
        L_0x0034:
            r1 = move-exception
            goto L_0x003a
        L_0x0036:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L_0x003a:
            java.lang.String r2 = "MICO_SUP_MIJIA"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "DevicePropValue JsonException: "
            r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Log.e(r2, r1)
            goto L_0x0056
        L_0x0055:
            r0 = r1
        L_0x0056:
            if (r0 != 0) goto L_0x005b
            java.lang.String r0 = "{}"
            goto L_0x005f
        L_0x005b:
            java.lang.String r0 = r0.toString()
        L_0x005f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miot.DevicePropValue.toString():java.lang.String");
    }
}
