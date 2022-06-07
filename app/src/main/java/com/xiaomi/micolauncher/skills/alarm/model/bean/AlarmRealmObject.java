package com.xiaomi.micolauncher.skills.alarm.model.bean;

import android.text.TextUtils;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmEntity;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.util.Objects;

/* loaded from: classes3.dex */
public class AlarmRealmObject extends RealmObject implements com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface {
    private long A;
    private long B;
    private long C;
    private String D;
    private String E;
    private String F;
    private String G;
    private byte[] H;
    private byte[] I;
    @PrimaryKey
    private int a;
    private String b;
    private String c;
    private long d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private int m;
    private String n;
    private String o;
    private String p;
    private String q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private int v;
    private int w;
    private int x;
    private int y;
    private long z;

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$alarmType() {
        return this.c;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$circle() {
        return this.e;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$circleExtra() {
        return this.f;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$displayTxt() {
        return this.o;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$event() {
        return this.h;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$id() {
        return this.a;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$idStr() {
        return this.b;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$position() {
        return this.j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reminder() {
        return this.i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public byte[] realmGet$reservByteArray1() {
        return this.H;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public byte[] realmGet$reservByteArray2() {
        return this.I;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean1() {
        return this.r;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean2() {
        return this.s;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean3() {
        return this.t;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean4() {
        return this.u;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt1() {
        return this.v;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt2() {
        return this.w;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt3() {
        return this.x;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt4() {
        return this.y;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong1() {
        return this.z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong2() {
        return this.A;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong3() {
        return this.B;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong4() {
        return this.C;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString1() {
        return this.D;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString2() {
        return this.E;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString3() {
        return this.F;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString4() {
        return this.G;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtone() {
        return this.k;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneType() {
        return this.n;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneVideo() {
        return this.p;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneVideoImage() {
        return this.q;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$status() {
        return this.g;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$timeReminder() {
        return this.l;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$timestamp() {
        return this.d;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$volume() {
        return this.m;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$alarmType(String str) {
        this.c = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$circle(String str) {
        this.e = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$circleExtra(String str) {
        this.f = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$displayTxt(String str) {
        this.o = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$event(String str) {
        this.h = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$id(int i) {
        this.a = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$idStr(String str) {
        this.b = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$position(String str) {
        this.j = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reminder(String str) {
        this.i = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reservByteArray1(byte[] bArr) {
        this.H = bArr;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reservByteArray2(byte[] bArr) {
        this.I = bArr;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean1(boolean z) {
        this.r = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean2(boolean z) {
        this.s = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean3(boolean z) {
        this.t = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean4(boolean z) {
        this.u = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt1(int i) {
        this.v = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt2(int i) {
        this.w = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt3(int i) {
        this.x = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt4(int i) {
        this.y = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong1(long j) {
        this.z = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong2(long j) {
        this.A = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong3(long j) {
        this.B = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong4(long j) {
        this.C = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString1(String str) {
        this.D = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString2(String str) {
        this.E = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString3(String str) {
        this.F = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString4(String str) {
        this.G = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtone(String str) {
        this.k = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneType(String str) {
        this.n = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneVideo(String str) {
        this.p = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneVideoImage(String str) {
        this.q = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$status(String str) {
        this.g = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$timeReminder(String str) {
        this.l = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$timestamp(long j) {
        this.d = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$volume(int i) {
        this.m = i;
    }

    public AlarmRealmObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public AlarmRealmObject(AlarmRealmObjectBean alarmRealmObjectBean) {
        this(alarmRealmObjectBean.getId(), alarmRealmObjectBean.getIdStr(), alarmRealmObjectBean.getAlarmType(), alarmRealmObjectBean.getTimestamp(), alarmRealmObjectBean.getReminder(), alarmRealmObjectBean.getPosition(), alarmRealmObjectBean.getCircle(), alarmRealmObjectBean.getCircleExtra(), alarmRealmObjectBean.getStatus(), alarmRealmObjectBean.getEvent(), alarmRealmObjectBean.getRingtone(), alarmRealmObjectBean.getTimeReminder(), alarmRealmObjectBean.getVolume());
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public AlarmRealmObject(int i, String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2) {
        this(i, str, str2, j, str3, str4, str5, str6, str7, str8, str9, str10, i2, "", "", "", "");
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public AlarmRealmObject(int i, String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, String str14) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$id(i);
        realmSet$idStr(str);
        realmSet$alarmType(str2);
        realmSet$timestamp(j);
        realmSet$reminder(str3);
        realmSet$position(str4);
        realmSet$circle(str5);
        realmSet$circleExtra(str6);
        realmSet$status(str7);
        realmSet$event(str8);
        realmSet$ringtone(str9);
        realmSet$timeReminder(str10);
        realmSet$volume(i2);
        realmSet$ringtoneType(str11);
        realmSet$displayTxt(str12);
        realmSet$ringtoneVideo(str13);
        realmSet$ringtoneVideoImage(str14);
        realmSet$reserveLong1(System.currentTimeMillis());
    }

    public AlarmRealmObject(AlarmEntity alarmEntity) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$id(alarmEntity.idInt);
        realmSet$idStr(alarmEntity.getIdentify());
        realmSet$alarmType(alarmEntity.type);
        if (alarmEntity.timestamp == 0) {
            realmSet$timestamp(alarmEntity.timestampMillis);
        } else {
            realmSet$timestamp(alarmEntity.getTimestampMillisTimeZone());
        }
        realmSet$reminder(alarmEntity.reminder);
        realmSet$position(alarmEntity.position);
        realmSet$circle(alarmEntity.getCircleString());
        realmSet$circleExtra(alarmEntity.getCircleExtra());
        realmSet$status(alarmEntity.getStatus());
        realmSet$event(alarmEntity.event);
        realmSet$ringtone(alarmEntity.ringtoneQuery);
        realmSet$timeReminder(alarmEntity.timeReminder);
        realmSet$volume(alarmEntity.volume);
        realmSet$ringtoneType(alarmEntity.ringtoneType);
        realmSet$displayTxt(alarmEntity.displayTxt);
        realmSet$ringtoneVideo(alarmEntity.ringtoneVideo);
        realmSet$ringtoneVideoImage(alarmEntity.ringtoneVideoImage);
        if (!TextUtils.isEmpty(alarmEntity.dateTime)) {
            realmSet$reserveString1(alarmEntity.dateTime);
        } else if (!TextUtils.isEmpty(alarmEntity.offset)) {
            realmSet$reserveString1(alarmEntity.offset);
        }
        realmSet$reserveLong1(System.currentTimeMillis());
    }

    public int getId() {
        return realmGet$id();
    }

    public String getIdStr() {
        return realmGet$idStr();
    }

    public String getAlarmType() {
        return realmGet$alarmType();
    }

    public long getTimestamp() {
        return realmGet$timestamp();
    }

    public String getReminder() {
        return realmGet$reminder();
    }

    public String getPosition() {
        return realmGet$position();
    }

    public String getCircle() {
        return realmGet$circle();
    }

    public String getCircleExtra() {
        return realmGet$circleExtra();
    }

    public String getStatus() {
        return realmGet$status();
    }

    public String getEvent() {
        return realmGet$event();
    }

    public String getRingtone() {
        return realmGet$ringtone();
    }

    public String getRingtoneType() {
        return realmGet$ringtoneType();
    }

    public String getTimeReminder() {
        return realmGet$timeReminder();
    }

    public int getVolume() {
        return realmGet$volume();
    }

    public String toString() {
        return "id:" + realmGet$id() + ", idStr=" + realmGet$idStr() + ", alarmType=" + realmGet$alarmType() + ", timestamp=" + realmGet$timestamp() + ", circle=" + realmGet$circle() + ", circleExtra=" + realmGet$circleExtra() + ", status=" + realmGet$status() + ", event=" + realmGet$event() + ", reminder=" + realmGet$reminder() + ", timeReminder=" + realmGet$timeReminder() + ", position=" + realmGet$position() + ", ringtoneType=" + realmGet$ringtoneType() + ", ringtone=" + realmGet$ringtone() + ", displayTxt=" + realmGet$displayTxt() + ", ringtoneVideo=" + realmGet$ringtoneVideo() + ", ringtoneVideoImage=" + realmGet$ringtoneVideoImage();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AlarmRealmObject alarmRealmObject = (AlarmRealmObject) obj;
        return realmGet$timestamp() == alarmRealmObject.realmGet$timestamp() && realmGet$volume() == alarmRealmObject.realmGet$volume() && Objects.equals(realmGet$alarmType(), alarmRealmObject.realmGet$alarmType()) && Objects.equals(realmGet$circle(), alarmRealmObject.realmGet$circle()) && Objects.equals(realmGet$circleExtra(), alarmRealmObject.realmGet$circleExtra()) && Objects.equals(realmGet$status(), alarmRealmObject.realmGet$status()) && Objects.equals(realmGet$event(), alarmRealmObject.realmGet$event()) && Objects.equals(realmGet$reminder(), alarmRealmObject.realmGet$reminder()) && Objects.equals(realmGet$position(), alarmRealmObject.realmGet$position()) && Objects.equals(realmGet$ringtone(), alarmRealmObject.realmGet$ringtone()) && Objects.equals(realmGet$timeReminder(), alarmRealmObject.realmGet$timeReminder()) && Objects.equals(realmGet$ringtoneType(), alarmRealmObject.realmGet$ringtoneType()) && Objects.equals(realmGet$displayTxt(), alarmRealmObject.realmGet$displayTxt()) && Objects.equals(realmGet$ringtoneVideo(), alarmRealmObject.realmGet$ringtoneVideo()) && Objects.equals(realmGet$ringtoneVideoImage(), alarmRealmObject.realmGet$ringtoneVideoImage());
    }

    public int hashCode() {
        return Objects.hash(realmGet$alarmType(), Long.valueOf(realmGet$timestamp()), realmGet$circle(), realmGet$circleExtra(), realmGet$status(), realmGet$event(), realmGet$reminder(), realmGet$position(), realmGet$ringtone(), realmGet$timeReminder(), Integer.valueOf(realmGet$volume()), realmGet$ringtoneType(), realmGet$displayTxt(), realmGet$ringtoneVideo(), realmGet$ringtoneVideoImage());
    }
}
