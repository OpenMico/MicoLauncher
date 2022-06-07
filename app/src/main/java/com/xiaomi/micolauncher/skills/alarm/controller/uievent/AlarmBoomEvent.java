package com.xiaomi.micolauncher.skills.alarm.controller.uievent;

import android.content.Context;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AlarmBoomEvent implements NotificationHelper.AlarmContentText {
    private boolean a;
    private String b;
    private String c;

    public static AlarmBoomEvent ofAlarm(String str, String str2) {
        return new AlarmBoomEvent(true, str, str2);
    }

    public static AlarmBoomEvent ofCountDown(String str, String str2) {
        return new AlarmBoomEvent(false, str, str2);
    }

    private AlarmBoomEvent(boolean z, String str, String str2) {
        this.a = z;
        this.b = str;
        this.c = str2;
    }

    public boolean isAlarm() {
        return this.a;
    }

    public String getEvent() {
        return this.b;
    }

    public String getTime() {
        return this.c;
    }

    public String toString() {
        return "AlarmBoomEvent{isAlarm=" + this.a + ", event='" + this.b + "', time='" + this.c + "'}";
    }

    @Override // com.xiaomi.micolauncher.common.push.NotificationHelper.AlarmContentText
    public NotificationHelper.AlarmContent getAlarmContentText(Context context, boolean z) {
        String str;
        L.alarm.d("getAlarmContentText %s %s", this.c, this.b);
        if (z) {
            return new NotificationHelper.AlarmContent(getTime(), getEvent());
        }
        if (isAlarm()) {
            str = context.getString(R.string.hiboard_item_calendar);
        } else {
            str = context.getString(R.string.timer);
        }
        return new NotificationHelper.AlarmContent(str + StringUtils.SPACE + getTime() + StringUtils.SPACE + getEvent(), "");
    }
}
