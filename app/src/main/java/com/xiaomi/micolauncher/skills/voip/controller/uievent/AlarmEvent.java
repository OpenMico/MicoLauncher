package com.xiaomi.micolauncher.skills.voip.controller.uievent;

import android.content.Context;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AlarmEvent implements NotificationHelper.AlarmContentText {
    public static String KEY_ALARM_ID = "alarmId";
    public String alarmId;
    public String event;
    public String time;
    public AlarmType type;

    /* loaded from: classes3.dex */
    public enum AlarmType {
        ALARM,
        COUNTDOWN,
        BABY_COURSE,
        HOURLY_CHIME
    }

    @Override // com.xiaomi.micolauncher.common.push.NotificationHelper.AlarmContentText
    public NotificationHelper.AlarmContent getAlarmContentText(Context context, boolean z) {
        String str;
        L.alarm.d("getAlarmContentText %s %s", this.time, this.event);
        if (z) {
            return new NotificationHelper.AlarmContent(this.time, this.event);
        }
        if (this.type == AlarmType.ALARM) {
            str = context.getString(R.string.hiboard_item_calendar);
        } else if (this.type == AlarmType.HOURLY_CHIME) {
            str = context.getString(R.string.hourly_chime_title);
        } else {
            str = context.getString(R.string.timer);
        }
        return new NotificationHelper.AlarmContent(str + StringUtils.SPACE + this.time + StringUtils.SPACE + this.event, "");
    }

    @Override // com.xiaomi.micolauncher.common.push.NotificationHelper.AlarmContentText
    public String alarmId() {
        return this.alarmId;
    }

    @Override // com.xiaomi.micolauncher.common.push.NotificationHelper.AlarmContentText
    public int notificationType() {
        if (this.type == AlarmType.BABY_COURSE) {
            return 4;
        }
        return this.type == AlarmType.HOURLY_CHIME ? 6 : 2;
    }
}
