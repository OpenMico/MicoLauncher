package com.xiaomi.micolauncher.skills.alarm.view;

import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public class AlarmUiUtil {
    public static LinkedList<Long> getDayList() {
        return getDayList(TimeUtils.getMidnightTimeStamp());
    }

    public static LinkedList<Long> getDayList(long j) {
        LinkedList<Long> linkedList = new LinkedList<>();
        for (int i = -30; i <= 60; i++) {
            linkedList.add(Long.valueOf((AlarmModel.DAY_MILLISECONDS * i) + j));
        }
        return linkedList;
    }
}
