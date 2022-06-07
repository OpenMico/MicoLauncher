package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AlarmHandler implements UBus.UbusHandler {
    private static final String ALARM_CLOSE = "alarm_close";
    private static final String ALARM_CREATE = "alarm_create";
    private static final String ALARM_DELETE = "alarm_delete";
    private static final String ALARM_GET_VOLUM = "alarm_get_volume";
    private static final String ALARM_MODIFY = "alarm_modify";
    private static final String ALARM_OPEN = "alarm_open";
    private static final String ALARM_QUERY = "alarm_query";
    private static final String ALARM_SET_VOLUM = "alarm_set_volume";
    private static final String KEY_ALARM_ID_FOR_BABY_COURSE = "id";
    private static final int MAX_UBUS_DELAY_SECONDS = 5;
    private static final AlarmStatHelper.Source STAT_SOURCE = AlarmStatHelper.Source.APP;
    public static final String TYPE_ALARM = "alarm";
    public static final String TYPE_BABY_COURSE = "baby_course";
    private static final String TYPE_TIMER = "timer";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.equals("alarm");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x04a1, code lost:
        if (r1.equals(com.xiaomi.micolauncher.common.ubus.handlers.AlarmHandler.TYPE_BABY_COURSE) != false) goto L_0x04a5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x010b, code lost:
        if (r1.equals(com.xiaomi.micolauncher.common.ubus.handlers.AlarmHandler.TYPE_BABY_COURSE) != false) goto L_0x010f;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03ec  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0640  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0212  */
    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String handle(android.content.Context r36, java.lang.String r37, java.lang.String r38, java.lang.String r39) {
        /*
            Method dump skipped, instructions count: 1840
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.ubus.handlers.AlarmHandler.handle(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private int correctDuration(long j) {
        return (int) TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j));
    }

    private long curTimeInSec() {
        return System.currentTimeMillis() / 1000;
    }
}
