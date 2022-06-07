package com.xiaomi.onetrack;

import com.xiaomi.onetrack.OneTrack;

/* loaded from: classes4.dex */
public class DefaultEventHook implements OneTrack.IEventHook {
    @Override // com.xiaomi.onetrack.OneTrack.IEventHook
    public boolean isCustomDauEvent(String str) {
        return false;
    }

    @Override // com.xiaomi.onetrack.OneTrack.IEventHook
    public boolean isRecommendEvent(String str) {
        return false;
    }
}
