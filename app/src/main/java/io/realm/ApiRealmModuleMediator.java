package io.realm;

import android.util.JsonReader;
import com.xiaomi.micolauncher.api.cache.ApiRealmObject;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy;
import io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy;
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
class ApiRealmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> a;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    ApiRealmModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet(2);
        hashSet.add(VideoData.class);
        hashSet.add(ApiRealmObject.class);
        a = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap(2);
        hashMap.put(VideoData.class, com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(ApiRealmObject.class, com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        checkClass(cls);
        if (cls.equals(VideoData.class)) {
            return com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(ApiRealmObject.class)) {
            return com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(VideoData.class)) {
            return com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(ApiRealmObject.class)) {
            return com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(VideoData.class)) {
                return cls.cast(new com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy());
            }
            if (cls.equals(ApiRealmObject.class)) {
                return cls.cast(new com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy());
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
        if (superclass.equals(VideoData.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.a) realm.getSchema().c(VideoData.class), (VideoData) e, z, map, set)));
        }
        if (superclass.equals(ApiRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.copyOrUpdate(realm, (com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.a) realm.getSchema().c(ApiRealmObject.class), (ApiRealmObject) e, z, map, set)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(VideoData.class)) {
            com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, (VideoData) realmModel, map);
        } else if (superclass.equals(ApiRealmObject.class)) {
            com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, (ApiRealmObject) realmModel, map);
        } else {
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, (VideoData) realmModel, hashMap);
            } else if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, (ApiRealmObject) realmModel, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (!it.hasNext()) {
                return;
            }
            if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insert(realm, it, hashMap);
            } else if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insert(realm, it, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(VideoData.class)) {
            com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, (VideoData) realmModel, map);
        } else if (superclass.equals(ApiRealmObject.class)) {
            com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, (ApiRealmObject) realmModel, map);
        } else {
            throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap hashMap = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel realmModel = (RealmModel) it.next();
            Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
            if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, (VideoData) realmModel, hashMap);
            } else if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, (ApiRealmObject) realmModel, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (!it.hasNext()) {
                return;
            }
            if (superclass.equals(VideoData.class)) {
                com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else if (superclass.equals(ApiRealmObject.class)) {
                com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.insertOrUpdate(realm, it, hashMap);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        checkClass(cls);
        if (cls.equals(VideoData.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(ApiRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(VideoData.class)) {
            return cls.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(ApiRealmObject.class)) {
            return cls.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(VideoData.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createDetachedCopy((VideoData) e, 0, i, map)));
        }
        if (superclass.equals(ApiRealmObject.class)) {
            return (E) ((RealmModel) superclass.cast(com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createDetachedCopy((ApiRealmObject) e, 0, i, map)));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }
}
