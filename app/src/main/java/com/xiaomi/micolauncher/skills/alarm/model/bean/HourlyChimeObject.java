package com.xiaomi.micolauncher.skills.alarm.model.bean;

import com.xiaomi.micolauncher.skills.alarm.model.HourEntity;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class HourlyChimeObject extends RealmObject implements com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface, Serializable {
    private static final long a = TimeUnit.MINUTES.toMillis(2);
    @Ignore
    private boolean boom;
    private String content;
    @PrimaryKey
    private int id;
    private int position;
    private boolean status;

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public String realmGet$content() {
        return this.content;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public int realmGet$id() {
        return this.id;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public int realmGet$position() {
        return this.position;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public boolean realmGet$status() {
        return this.status;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$content(String str) {
        this.content = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$id(int i) {
        this.id = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$position(int i) {
        this.position = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$status(boolean z) {
        this.status = z;
    }

    public HourlyChimeObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public HourlyChimeObject(HourlyChimeObject hourlyChimeObject) {
        this(hourlyChimeObject.getId(), hourlyChimeObject.getContent(), hourlyChimeObject.getStatus(), hourlyChimeObject.getPosition());
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public HourlyChimeObject(int i, String str, boolean z, int i2) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$id(i);
        realmSet$content(str);
        realmSet$status(z);
        realmSet$position(i2);
        this.boom = false;
    }

    public HourlyChimeObject(HourEntity hourEntity) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$id(hourEntity.id);
        realmSet$content(hourEntity.content);
        realmSet$status(hourEntity.status);
        realmSet$position(hourEntity.position);
        this.boom = false;
    }

    public int getId() {
        return realmGet$id();
    }

    public void setId(int i) {
        realmSet$id(i);
    }

    public String getContent() {
        return realmGet$content();
    }

    public void setContent(String str) {
        realmSet$content(str);
    }

    public boolean getStatus() {
        return realmGet$status();
    }

    public void setStatus(boolean z) {
        realmSet$status(z);
    }

    public int getPosition() {
        return realmGet$position();
    }

    public void setPosition(int i) {
        realmSet$position(i);
    }

    public boolean isBoom() {
        return this.boom;
    }

    public void setBoom(boolean z) {
        this.boom = z;
    }

    public String toString() {
        return "id:" + realmGet$id() + " content:" + realmGet$content() + " status:" + realmGet$status() + " position:" + realmGet$position() + " boom:" + this.boom;
    }
}
