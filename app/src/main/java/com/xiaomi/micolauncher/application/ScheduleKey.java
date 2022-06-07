package com.xiaomi.micolauncher.application;

import com.xiaomi.mico.base.utils.ScheduleRefreshManager;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public enum ScheduleKey implements ScheduleRefreshManager.ScheduleRefreshItem {
    MUSIC_CP_INFO(TimeUnit.HOURS.toMillis(12)),
    INSTRUCTION(TimeUnit.MINUTES.toMillis(30)),
    MUSIC_SOURCE(TimeUnit.HOURS.toMillis(1));
    
    private final long duration;

    ScheduleKey(long j) {
        this.duration = j;
    }

    @Override // com.xiaomi.mico.base.utils.ScheduleRefreshManager.ScheduleRefreshItem
    public String getName() {
        return name();
    }

    @Override // com.xiaomi.mico.base.utils.ScheduleRefreshManager.ScheduleRefreshItem
    public long getDuration() {
        return this.duration;
    }
}
