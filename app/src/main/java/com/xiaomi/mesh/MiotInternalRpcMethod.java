package com.xiaomi.mesh;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;

/* loaded from: classes3.dex */
public class MiotInternalRpcMethod {
    @SerializedName("id")
    private int id;
    @SerializedName(SchemaActivity.KEY_METHOD)
    private String method;

    public void setMethod(String str) {
        this.method = str;
    }

    public String getMethod() {
        return this.method;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String toString() {
        return "MiotInternalRpcMethod{ id=" + this.id + ", method='" + this.method + "'}";
    }
}
