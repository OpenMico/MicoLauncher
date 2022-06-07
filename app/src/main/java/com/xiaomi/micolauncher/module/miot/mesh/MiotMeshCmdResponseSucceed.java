package com.xiaomi.micolauncher.module.miot.mesh;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.Gsons;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotMeshCmdResponseSucceed {
    @SerializedName("id")
    private int a;
    @SerializedName("result")
    private List<String> b;

    public int getId() {
        return this.a;
    }

    public void setId(int i) {
        this.a = i;
    }

    public List<String> getResult() {
        return this.b;
    }

    public void setResult(List<String> list) {
        this.b = list;
    }

    public String displayToString() {
        return Gsons.getGson().toJson(this);
    }
}
