package com.xiaomi.micolauncher.api.cache;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* loaded from: classes3.dex */
public class ApiRealmObject extends RealmObject implements com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface {
    @Required
    public String content;
    public long lastUpdateTime;
    @PrimaryKey
    @Required
    public String url;

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public String realmGet$content() {
        return this.content;
    }

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        return this.lastUpdateTime;
    }

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public String realmGet$url() {
        return this.url;
    }

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$content(String str) {
        this.content = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        this.lastUpdateTime = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$url(String str) {
        this.url = str;
    }

    public ApiRealmObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }
}
