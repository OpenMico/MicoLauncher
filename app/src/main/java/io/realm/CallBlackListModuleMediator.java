package io.realm;

import android.util.JsonReader;
import com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@RealmModule
/* loaded from: classes5.dex */
class CallBlackListModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> a;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    CallBlackListModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet(1);
        hashSet.add(CallBlackListRealmObject.class);
        a = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap(1);
        hashMap.put(CallBlackListRealmObject.class, com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        checkClass(cls);
        if (cls.equals(CallBlackListRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(CallBlackListRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(CallBlackListRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy());
            }
            throw getMissingProxyClassException(cls);
        } finally {
            realmObjectContext.clear();
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return a;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<?> superclass = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(CallBlackListRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.a) realm.getSchema().c(CallBlackListRealmObject.class), (CallBlackListRealmObject) e, z, map, set)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(CallBlackListRealmObject.class)) {
            com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, (CallBlackListRealmObject) realmModel, map);
            return;
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, (CallBlackListRealmObject) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(CallBlackListRealmObject.class)) {
                    com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insert(realm, it, hashMap);
                    return;
                }
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(CallBlackListRealmObject.class)) {
            com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, (CallBlackListRealmObject) realmModel, map);
            return;
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(CallBlackListRealmObject.class)) {
                com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, (CallBlackListRealmObject) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(CallBlackListRealmObject.class)) {
                    com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
                    return;
                }
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        checkClass(cls);
        if (cls.equals(CallBlackListRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(CallBlackListRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(CallBlackListRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createDetachedCopy((CallBlackListRealmObject) e, 0, i, map)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }
}
