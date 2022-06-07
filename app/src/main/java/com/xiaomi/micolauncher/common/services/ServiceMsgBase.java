package com.xiaomi.micolauncher.common.services;

import android.os.Bundle;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class ServiceMsgBase implements Serializable {
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", this);
        return bundle;
    }
}
