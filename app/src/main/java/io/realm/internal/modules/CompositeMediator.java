package io.realm.internal.modules;

import android.util.JsonReader;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.Util;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CompositeMediator extends RealmProxyMediator {
    private final Map<Class<? extends RealmModel>, RealmProxyMediator> a;
    private final Map<String, Class<? extends RealmModel>> b = new HashMap();

    public CompositeMediator(RealmProxyMediator... realmProxyMediatorArr) {
        HashMap hashMap = new HashMap();
        if (realmProxyMediatorArr != null) {
            for (RealmProxyMediator realmProxyMediator : realmProxyMediatorArr) {
                for (Class<? extends RealmModel> cls : realmProxyMediator.getModelClasses()) {
                    String simpleClassName = realmProxyMediator.getSimpleClassName(cls);
                    Class<? extends RealmModel> cls2 = this.b.get(simpleClassName);
                    if (cls2 == null || cls2.equals(cls)) {
                        hashMap.put(cls, realmProxyMediator);
                        this.b.put(simpleClassName, cls);
                    } else {
                        throw new IllegalStateException(String.format("It is not allowed for two different model classes to share the same internal name in Realm. The classes %s and %s are being included from the modules '%s' and '%s' and they share the same internal name '%s'.", cls2, cls, hashMap.get(cls2), realmProxyMediator, simpleClassName));
                    }
                }
            }
        }
        this.a = Collections.unmodifiableMap(hashMap);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap();
        for (RealmProxyMediator realmProxyMediator : this.a.values()) {
            hashMap.putAll(realmProxyMediator.getExpectedObjectSchemaInfoMap());
        }
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        return a(cls).createColumnInfo(cls, osSchemaInfo);
    }

    @Override // io.realm.internal.RealmProxyMediator
    protected String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        return a(cls).getSimpleClassName(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        return (E) a(cls).newInstance(cls, obj, row, columnInfo, z, list);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return this.a.keySet();
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        return (E) a(Util.getOriginalModelClass(e.getClass())).copyOrUpdate(realm, e, z, map, set);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        a(Util.getOriginalModelClass(realmModel.getClass())).insert(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        a(Util.getOriginalModelClass(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()))).insert(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        a(Util.getOriginalModelClass(realmModel.getClass())).insertOrUpdate(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        a(Util.getOriginalModelClass(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()))).insertOrUpdate(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        return (E) a(cls).createOrUpdateUsingJsonObject(cls, realm, jSONObject, z);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        return (E) a(cls).createUsingJsonStream(cls, realm, jsonReader);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        return (E) a(Util.getOriginalModelClass(e.getClass())).createDetachedCopy(e, i, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        for (Map.Entry<Class<? extends RealmModel>, RealmProxyMediator> entry : this.a.entrySet()) {
            if (!entry.getValue().transformerApplied()) {
                return false;
            }
        }
        return true;
    }

    private RealmProxyMediator a(Class<? extends RealmModel> cls) {
        RealmProxyMediator realmProxyMediator = this.a.get(cls);
        if (realmProxyMediator != null) {
            return realmProxyMediator;
        }
        throw new RealmException(cls.getSimpleName() + " is not part of the schema for this Realm");
    }
}
