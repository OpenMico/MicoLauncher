package com.xiaomi.micolauncher.skills.voip.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* loaded from: classes3.dex */
public class CallBlackListRealmObject extends RealmObject implements com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface {
    @PrimaryKey
    @Required
    private String a;

    @Override // io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface
    public String realmGet$blackListNum() {
        return this.a;
    }

    @Override // io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface
    public void realmSet$blackListNum(String str) {
        this.a = str;
    }

    public CallBlackListRealmObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a() {
        return realmGet$blackListNum();
    }

    public void setBlackListNum(String str) {
        realmSet$blackListNum(str);
    }
}
