package com.xiaomi.miot.lib;

import com.xiaomi.miot.lib.impl.MiotManagerBase;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotManager extends MiotManagerBase {
    private static final MiotManager instance = new MiotManager();

    @Override // com.xiaomi.miot.lib.impl.MiotManagerBase
    protected void beforeInitialize() {
    }

    public static final MiotManager getInstance() {
        return instance;
    }

    private MiotManager() {
    }

    public boolean initialize(MiotConfig miotConfig) {
        return doInitialize(miotConfig);
    }

    @Override // com.xiaomi.miot.lib.impl.MiotManagerBase
    protected boolean afterIntialized() {
        this.isInited = true;
        return true;
    }

    public MiotConfig getConfiguration() {
        return this.config;
    }

    public boolean sendRequest(String str, JSONObject jSONObject, MiotCallback<JSONObject> miotCallback) {
        return sendRequestExec(str, jSONObject, miotCallback);
    }
}
