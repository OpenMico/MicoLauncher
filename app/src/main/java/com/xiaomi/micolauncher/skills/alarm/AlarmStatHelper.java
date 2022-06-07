package com.xiaomi.micolauncher.skills.alarm;

import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class AlarmStatHelper {

    /* loaded from: classes3.dex */
    public enum AlarmCategory {
        alarm_show,
        alarm_click
    }

    /* loaded from: classes3.dex */
    public enum AlarmKey {
        alarm_ring,
        alarm_add,
        alarm_delete,
        alarm_modify,
        timer_ring,
        timer_add,
        timer_delete,
        timer_modify,
        normal,
        ad,
        stop
    }

    /* loaded from: classes3.dex */
    public enum Source {
        APP(0, "app"),
        QUERY(1, "mibrain");
        
        private int id;
        private String name;

        Source(int i, String str) {
            this.id = i;
            this.name = str;
        }
    }

    public static void recordAlarmRing() {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.alarm_ring, "1");
    }

    public static void recordAlarmAdd(@NotNull AlarmRealmObjectBean alarmRealmObjectBean, @NotNull Source source) {
        if (alarmRealmObjectBean != null && source != null) {
            StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.alarm_add, "1|" + alarmRealmObjectBean.getCircle() + "|" + alarmRealmObjectBean.getVolume() + "|" + alarmRealmObjectBean.getTimestamp() + "|" + source.name);
        }
    }

    public static void recordAlarmModify(@NotNull Source source) {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.alarm_modify, "1|" + source.name);
    }

    public static void recordAlarmDelete(@NotNull Source source) {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.alarm_delete, "1|" + source.name);
    }

    public static void recordTimerRing() {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.timer_ring, "1");
    }

    public static void recordTimerAdd(@NotNull AlarmRealmObjectBean alarmRealmObjectBean, @NotNull Source source) {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.timer_add, "1|" + alarmRealmObjectBean.getTimestamp() + "|" + source.name);
    }

    public static void recordTimerModify(@NotNull Source source) {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.timer_modify, "1|" + source.name);
    }

    public static void recordTimerDelete(@NotNull Source source) {
        StatPoints.recordPoint(StatPoints.Event.spk, AlarmKey.timer_delete, "1|" + source.name);
    }

    public static void recordAlarmCount(Enum r0, Enum r1) {
        StatUtils.recordCountEvent(r0.name(), r1.name());
    }
}
