package com.xiaomi.analytics;

import com.xiaomi.analytics.internal.v1.AnalyticsInterface;

/* loaded from: classes3.dex */
public class PolicyConfiguration {
    private Privacy a;

    /* loaded from: classes3.dex */
    public enum Privacy {
        NO,
        USER
    }

    public PolicyConfiguration setPrivacy(Privacy privacy) {
        this.a = privacy;
        return this;
    }

    public void apply(AnalyticsInterface analyticsInterface) {
        if (analyticsInterface != null) {
            a(analyticsInterface);
        }
    }

    private void a(AnalyticsInterface analyticsInterface) {
        Privacy privacy = this.a;
        if (privacy != null && analyticsInterface != null) {
            if (privacy == Privacy.NO) {
                analyticsInterface.setDefaultPolicy("privacy_policy", "privacy_no");
            } else {
                analyticsInterface.setDefaultPolicy("privacy_policy", "privacy_user");
            }
        }
    }
}
