package com.xiaomi.micolauncher.common.stat;

import com.xiaomi.miot.host.lan.impl.MiotStore;

/* loaded from: classes3.dex */
public enum StatKey {
    MIOT_TOKEN_RESULT(StatCategory.MIOT, "miot_token_result");
    
    private final StatCategory mCategory;
    private final String mKey;

    StatKey(StatCategory statCategory, String str) {
        this.mCategory = statCategory;
        this.mKey = str;
    }

    public String getCategory() {
        return this.mCategory.getName();
    }

    public String getKey() {
        return this.mKey;
    }

    /* loaded from: classes3.dex */
    public enum StatCategory {
        MIOT(MiotStore.PREFS_MIOT);
        
        private final String mName;

        public String getName() {
            return this.mName;
        }

        StatCategory(String str) {
            this.mName = str;
        }
    }
}
