package com.xiaomi.micolauncher.skills.alarm.model;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AlarmEntity {
    public int advanceReminder;
    public int circle;
    public String dateTime;
    @SerializedName("display_txt")
    public String displayTxt;
    public String event;
    public String extra;
    public String id;
    public int idInt;
    public String offset;
    public int offsetSeconds;
    public String position;
    public String reminder;
    @SerializedName("ringtone_query")
    public String ringtoneQuery;
    @SerializedName("ringtone_type")
    public String ringtoneType;
    @SerializedName("ringtone_video")
    public String ringtoneVideo;
    @SerializedName("ringtone_video_image")
    public String ringtoneVideoImage;
    public AlarmStatHelper.Source source;
    public String status;
    @SerializedName("time_reminder")
    public String timeReminder;
    public long timestamp;
    public long timestampMillis;
    public String type;
    public int volume = -1;

    public String getStatus() {
        return (TextUtils.isEmpty(this.status) || "0".equals(this.status)) ? "on" : this.status;
    }

    public String buildReminder(Context context) {
        String str;
        if (TextUtils.isEmpty(this.reminder) && (str = this.event) != null && str.trim().length() > 0) {
            this.reminder = context.getString(R.string.alarm_remainder_str, this.event);
        }
        return this.reminder;
    }

    public String buildTimeReminder(Context context) {
        if (TextUtils.isEmpty(this.timeReminder)) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date(TimeUnit.SECONDS.toMillis(this.timestamp)));
            this.timeReminder = context.getString(R.string.alarm_time_remainder_str, Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)));
            String str = this.reminder;
            if (str != null && str.length() > 0) {
                this.timeReminder += "，" + this.reminder;
            }
        }
        return this.timeReminder;
    }

    public String getCircleString() {
        return AlarmRealmObjectBean.getCircleString(this.circle);
    }

    public String getCircleExtra() {
        if (!TextUtils.isEmpty(this.extra)) {
            return AlarmRealmObjectBean.convertCircleExtraFromAppToBrain(this.extra);
        }
        return this.extra;
    }

    public String getCircleExtraFromAppToBrain() {
        return AlarmRealmObjectBean.convertCircleExtraFromAppToBrain(this.extra);
    }

    public long getTimestamp() {
        long a = a();
        long j = this.timestamp;
        if (a > j) {
            this.timestamp = j + AlarmModel.DAY_SECONDS;
        }
        return this.timestamp;
    }

    public void calibrateTimestamp() {
        long a = a();
        long j = this.timestamp;
        if (a > j) {
            this.timestamp = j + AlarmModel.DAY_SECONDS;
        }
    }

    public long getTimestampMillisTimeZone() {
        return TimeUnit.SECONDS.toMillis(this.timestamp) - TimeUtils.getTimeZoneOffsetMillis();
    }

    private long a() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public int getVolume() {
        return this.volume;
    }

    public String getIdentify() {
        if (TextUtils.isEmpty(this.id)) {
            this.id = UUID.randomUUID().toString();
        }
        return this.id;
    }

    public String toString() {
        return "AlarmEntity{id='" + this.id + "', idInt=" + this.idInt + ", type='" + this.type + "', reminder='" + this.reminder + "', circle=" + this.circle + ", extra='" + this.extra + "', event='" + this.event + "', timestamp=" + this.timestamp + ", timestampMillis=" + this.timestampMillis + ", volume=" + this.volume + ", timeReminder='" + this.timeReminder + "', ringtoneQuery='" + this.ringtoneQuery + "', ringtoneType='" + this.ringtoneType + "', displayTxt='" + this.displayTxt + "', ringtoneVideo='" + this.ringtoneVideo + "', ringtoneVideoImage='" + this.ringtoneVideoImage + "', source=" + this.source + ", position='" + this.position + "', advanceReminder=" + this.advanceReminder + '}';
    }
}
