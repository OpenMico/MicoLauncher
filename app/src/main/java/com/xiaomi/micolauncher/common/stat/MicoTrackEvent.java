package com.xiaomi.micolauncher.common.stat;

import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.stat.StatModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.util.List;

/* loaded from: classes3.dex */
public class MicoTrackEvent extends RealmObject implements com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface {
    public String data;
    public String event;
    @PrimaryKey
    public String eventId;

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$data() {
        return this.data;
    }

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$event() {
        return this.event;
    }

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$eventId() {
        return this.eventId;
    }

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$data(String str) {
        this.data = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$event(String str) {
        this.event = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$eventId(String str) {
        this.eventId = str;
    }

    public MicoTrackEvent() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public MicoTrackEvent(StatModel.StatEvent statEvent) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$eventId(statEvent.eventId);
        realmSet$event(statEvent.event);
        realmSet$data(Gsons.getGson().toJson(statEvent.data));
    }

    public StatModel.StatEvent toStatEvent() {
        StatModel.StatEvent statEvent = new StatModel.StatEvent();
        statEvent.eventId = realmGet$eventId();
        statEvent.event = realmGet$event();
        statEvent.data = (List) Gsons.getGson().fromJson(realmGet$data(), new TypeToken<List<StatModel.StatValue>>() { // from class: com.xiaomi.micolauncher.common.stat.MicoTrackEvent.1
        }.getType());
        return statEvent;
    }
}
