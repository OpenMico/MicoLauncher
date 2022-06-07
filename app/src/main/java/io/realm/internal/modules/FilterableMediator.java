package io.realm.internal.modules;

import android.util.JsonReader;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmModel;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class FilterableMediator extends RealmProxyMediator {
    private final RealmProxyMediator a;
    private final Set<Class<? extends RealmModel>> b;

    public FilterableMediator(RealmProxyMediator realmProxyMediator, Collection<Class<? extends RealmModel>> collection) {
        this.a = realmProxyMediator;
        HashSet hashSet = new HashSet();
        if (realmProxyMediator != null) {
            Set<Class<? extends RealmModel>> modelClasses = realmProxyMediator.getModelClasses();
            for (Class<? extends RealmModel> cls : collection) {
                if (modelClasses.contains(cls)) {
                    hashSet.add(cls);
                }
            }
        }
        this.b = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap hashMap = new HashMap();
        for (Map.Entry<Class<? extends RealmModel>, OsObjectSchemaInfo> entry : this.a.getExpectedObjectSchemaInfoMap().entrySet()) {
            if (this.b.contains(entry.getKey())) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return hashMap;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        a(cls);
        return this.a.createColumnInfo(cls, osSchemaInfo);
    }

    @Override // io.realm.internal.RealmProxyMediator
    protected String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        a(cls);
        return this.a.getSimpleClassName(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        a(cls);
        return (E) this.a.newInstance(cls, obj, row, columnInfo, z, list);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return this.b;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        a(Util.getOriginalModelClass(e.getClass()));
        return (E) this.a.copyOrUpdate(realm, e, z, map, set);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        a(Util.getOriginalModelClass(realmModel.getClass()));
        this.a.insert(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        a(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()));
        this.a.insert(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        a(Util.getOriginalModelClass(realmModel.getClass()));
        this.a.insertOrUpdate(realm, realmModel, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        a(Util.getOriginalModelClass(((RealmModel) collection.iterator().next()).getClass()));
        this.a.insertOrUpdate(realm, collection);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        a(cls);
        return (E) this.a.createOrUpdateUsingJsonObject(cls, realm, jSONObject, z);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        a(cls);
        return (E) this.a.createUsingJsonStream(cls, realm, jsonReader);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        a(Util.getOriginalModelClass(e.getClass()));
        return (E) this.a.createDetachedCopy(e, i, map);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        RealmProxyMediator realmProxyMediator = this.a;
        if (realmProxyMediator == null) {
            return true;
        }
        return realmProxyMediator.transformerApplied();
    }

    private void a(Class<? extends RealmModel> cls) {
        if (!this.b.contains(cls)) {
            throw new IllegalArgumentException(cls.getSimpleName() + " is not part of the schema for this Realm");
        }
    }
}
