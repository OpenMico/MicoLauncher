package com.xiaomi.phonenum.bean;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;

/* loaded from: classes4.dex */
public class Sim {
    public final String iccid;
    public final String imsi;
    public final String line1Number;
    public final String mccmnc;

    public Sim(@NonNull String str, String str2, String str3, String str4) {
        this.iccid = str;
        this.imsi = str2;
        this.mccmnc = str3;
        this.line1Number = str4;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(ai.aa, this.iccid);
        bundle.putString("imsi", this.imsi);
        bundle.putString(ai.B, this.mccmnc);
        bundle.putString("line1Number", this.line1Number);
        return bundle;
    }

    public String toString() {
        return toBundle().toString();
    }
}
