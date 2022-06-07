package com.xiaomi.mico.base.utils;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class ScheduleRefreshManager {
    private static final ScheduleRefreshManager b = new ScheduleRefreshManager();
    private HashMap<String, a> a = new HashMap<>();

    /* loaded from: classes3.dex */
    public interface ScheduleRefreshItem {
        long getDuration();

        String getName();
    }

    public static ScheduleRefreshManager getInstance() {
        return b;
    }

    private ScheduleRefreshManager() {
    }

    public void start(ScheduleRefreshItem scheduleRefreshItem) {
        this.a.put(scheduleRefreshItem.getName(), new a(scheduleRefreshItem));
    }

    public boolean shouldRefresh(ScheduleRefreshItem scheduleRefreshItem) {
        a aVar = this.a.get(scheduleRefreshItem.getName());
        return aVar == null || System.currentTimeMillis() - aVar.c > aVar.b;
    }

    public void setRefreshed(ScheduleRefreshItem scheduleRefreshItem) {
        a aVar = this.a.get(scheduleRefreshItem.getName());
        if (aVar != null) {
            aVar.c = System.currentTimeMillis();
        }
    }

    /* loaded from: classes3.dex */
    private class a {
        public String a;
        public long b;
        public long c;

        public a(ScheduleRefreshItem scheduleRefreshItem) {
            this.a = scheduleRefreshItem.getName();
            this.b = scheduleRefreshItem.getDuration();
        }
    }
}
