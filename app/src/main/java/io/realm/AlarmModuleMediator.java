package io.realm;

import android.util.JsonReader;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy;
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
class AlarmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> a;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    AlarmModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet(1);
        hashSet.add(AlarmRealmObject.class);
        a = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap(1);
        hashMap.put(AlarmRealmObject.class, com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        checkClass(cls);
        if (cls.equals(AlarmRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(AlarmRealmObject.class)) {
            return com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(AlarmRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy());
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
        if (superclass.equals(AlarmRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.a) realm.getSchema().c(AlarmRealmObject.class), (AlarmRealmObject) e, z, map, set)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(AlarmRealmObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, (AlarmRealmObject) realmModel, map);
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
            if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, (AlarmRealmObject) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(AlarmRealmObject.class)) {
                    com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insert(realm, it, hashMap);
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
        if (superclass.equals(AlarmRealmObject.class)) {
            com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, (AlarmRealmObject) realmModel, map);
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
            if (superclass.equals(AlarmRealmObject.class)) {
                com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, (AlarmRealmObject) realmModel, hashMap);
                if (!it.hasNext()) {
                    return;
                }
                if (superclass.equals(AlarmRealmObject.class)) {
                    com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
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
        if (cls.equals(AlarmRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(AlarmRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(AlarmRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createDetachedCopy((AlarmRealmObject) e, 0, i, map)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }
}
