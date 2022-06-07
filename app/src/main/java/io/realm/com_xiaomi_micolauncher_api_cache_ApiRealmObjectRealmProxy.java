package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.api.cache.ApiRealmObject;
import com.xiaomi.mipush.sdk.Constants;
import io.realm.BaseRealm;
import io.realm.exceptions.RealmException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy extends ApiRealmObject implements com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a b;
    private ProxyState<ApiRealmObject> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "ApiRealmObject";
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ColumnInfo {
        long a;
        long b;
        long c;

        a(OsSchemaInfo osSchemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("url", "url", objectSchemaInfo);
            this.b = addColumnDetails("content", "content", objectSchemaInfo);
            this.c = addColumnDetails("lastUpdateTime", "lastUpdateTime", objectSchemaInfo);
        }

        a(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new a(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            a aVar = (a) columnInfo;
            a aVar2 = (a) columnInfo2;
            aVar2.a = aVar.a;
            aVar2.b = aVar.b;
            aVar2.c = aVar.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy() {
        this.c.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.c == null) {
            BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
            this.b = (a) realmObjectContext.getColumnInfo();
            this.c = new ProxyState<>(this);
            this.c.setRealm$realm(realmObjectContext.a());
            this.c.setRow$realm(realmObjectContext.getRow());
            this.c.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
            this.c.setExcludeFields$realm(realmObjectContext.getExcludeFields());
        }
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public String realmGet$url() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$url(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'url' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public String realmGet$content() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.b);
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$content(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str != null) {
                this.c.getRow$realm().setString(this.b.b, str);
                return;
            }
            throw new IllegalArgumentException("Trying to set non-nullable field 'content' to null.");
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str != null) {
                row$realm.getTable().setString(this.b.b, row$realm.getObjectKey(), str, true);
                return;
            }
            throw new IllegalArgumentException("Trying to set non-nullable field 'content' to null.");
        }
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.c);
    }

    @Override // com.xiaomi.micolauncher.api.cache.ApiRealmObject, io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.c, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.c, row$realm.getObjectKey(), j, true);
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 3, 0);
        builder.addPersistedProperty("url", RealmFieldType.STRING, true, false, true);
        builder.addPersistedProperty("content", RealmFieldType.STRING, false, false, true);
        builder.addPersistedProperty("lastUpdateTime", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.api.cache.ApiRealmObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            java.util.List r0 = java.util.Collections.emptyList()
            r1 = 0
            if (r13 == 0) goto L_0x0063
            java.lang.Class<com.xiaomi.micolauncher.api.cache.ApiRealmObject> r13 = com.xiaomi.micolauncher.api.cache.ApiRealmObject.class
            io.realm.internal.Table r13 = r11.a(r13)
            io.realm.RealmSchema r2 = r11.getSchema()
            java.lang.Class<com.xiaomi.micolauncher.api.cache.ApiRealmObject> r3 = com.xiaomi.micolauncher.api.cache.ApiRealmObject.class
            io.realm.internal.ColumnInfo r2 = r2.c(r3)
            io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy$a r2 = (io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.a) r2
            long r2 = r2.a
            java.lang.String r4 = "url"
            boolean r4 = r12.isNull(r4)
            r5 = -1
            if (r4 != 0) goto L_0x0030
            java.lang.String r4 = "url"
            java.lang.String r4 = r12.getString(r4)
            long r2 = r13.findFirstString(r2, r4)
            goto L_0x0031
        L_0x0030:
            r2 = r5
        L_0x0031:
            int r4 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r4 == 0) goto L_0x0063
            io.realm.BaseRealm$a r4 = io.realm.BaseRealm.objectContext
            java.lang.Object r4 = r4.get()
            io.realm.BaseRealm$RealmObjectContext r4 = (io.realm.BaseRealm.RealmObjectContext) r4
            io.realm.internal.UncheckedRow r7 = r13.getUncheckedRow(r2)     // Catch: all -> 0x005e
            io.realm.RealmSchema r13 = r11.getSchema()     // Catch: all -> 0x005e
            java.lang.Class<com.xiaomi.micolauncher.api.cache.ApiRealmObject> r2 = com.xiaomi.micolauncher.api.cache.ApiRealmObject.class
            io.realm.internal.ColumnInfo r8 = r13.c(r2)     // Catch: all -> 0x005e
            r9 = 0
            java.util.List r10 = java.util.Collections.emptyList()     // Catch: all -> 0x005e
            r5 = r4
            r6 = r11
            r5.set(r6, r7, r8, r9, r10)     // Catch: all -> 0x005e
            io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy r13 = new io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy     // Catch: all -> 0x005e
            r13.<init>()     // Catch: all -> 0x005e
            r4.clear()
            goto L_0x0064
        L_0x005e:
            r11 = move-exception
            r4.clear()
            throw r11
        L_0x0063:
            r13 = r1
        L_0x0064:
            if (r13 != 0) goto L_0x0099
            java.lang.String r13 = "url"
            boolean r13 = r12.has(r13)
            if (r13 == 0) goto L_0x0091
            java.lang.String r13 = "url"
            boolean r13 = r12.isNull(r13)
            r2 = 1
            if (r13 == 0) goto L_0x0081
            java.lang.Class<com.xiaomi.micolauncher.api.cache.ApiRealmObject> r13 = com.xiaomi.micolauncher.api.cache.ApiRealmObject.class
            io.realm.RealmModel r11 = r11.a(r13, r1, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy) r13
            goto L_0x0099
        L_0x0081:
            java.lang.Class<com.xiaomi.micolauncher.api.cache.ApiRealmObject> r13 = com.xiaomi.micolauncher.api.cache.ApiRealmObject.class
            java.lang.String r3 = "url"
            java.lang.String r3 = r12.getString(r3)
            io.realm.RealmModel r11 = r11.a(r13, r3, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy) r13
            goto L_0x0099
        L_0x0091:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "JSON object doesn't have the primary key field 'url'."
            r11.<init>(r12)
            throw r11
        L_0x0099:
            r11 = r13
            io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface r11 = (io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxyInterface) r11
            java.lang.String r0 = "content"
            boolean r0 = r12.has(r0)
            if (r0 == 0) goto L_0x00b9
            java.lang.String r0 = "content"
            boolean r0 = r12.isNull(r0)
            if (r0 == 0) goto L_0x00b0
            r11.realmSet$content(r1)
            goto L_0x00b9
        L_0x00b0:
            java.lang.String r0 = "content"
            java.lang.String r0 = r12.getString(r0)
            r11.realmSet$content(r0)
        L_0x00b9:
            java.lang.String r0 = "lastUpdateTime"
            boolean r0 = r12.has(r0)
            if (r0 == 0) goto L_0x00db
            java.lang.String r0 = "lastUpdateTime"
            boolean r0 = r12.isNull(r0)
            if (r0 != 0) goto L_0x00d3
            java.lang.String r0 = "lastUpdateTime"
            long r0 = r12.getLong(r0)
            r11.realmSet$lastUpdateTime(r0)
            goto L_0x00db
        L_0x00d3:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "Trying to set non-nullable field 'lastUpdateTime' to null."
            r11.<init>(r12)
            throw r11
        L_0x00db:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.api.cache.ApiRealmObject");
    }

    @TargetApi(11)
    public static ApiRealmObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        ApiRealmObject apiRealmObject = new ApiRealmObject();
        ApiRealmObject apiRealmObject2 = apiRealmObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("url")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    apiRealmObject2.realmSet$url(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    apiRealmObject2.realmSet$url(null);
                }
                z = true;
            } else if (nextName.equals("content")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    apiRealmObject2.realmSet$content(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    apiRealmObject2.realmSet$content(null);
                }
            } else if (!nextName.equals("lastUpdateTime")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                apiRealmObject2.realmSet$lastUpdateTime(jsonReader.nextLong());
            } else {
                jsonReader.skipValue();
                throw new IllegalArgumentException("Trying to set non-nullable field 'lastUpdateTime' to null.");
            }
        }
        jsonReader.endObject();
        if (z) {
            return (ApiRealmObject) realm.copyToRealm((Realm) apiRealmObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'url'.");
    }

    private static com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(ApiRealmObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy = new com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static ApiRealmObject copyOrUpdate(Realm realm, a aVar, ApiRealmObject apiRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy;
        boolean z2;
        if ((apiRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(apiRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) apiRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return apiRealmObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(apiRealmObject);
        if (realmObjectProxy2 != null) {
            return (ApiRealmObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(ApiRealmObject.class);
            long findFirstString = a2.findFirstString(aVar.a, apiRealmObject.realmGet$url());
            if (findFirstString == -1) {
                z2 = false;
                com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(findFirstString), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy2 = new com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy();
                    map.put(apiRealmObject, com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy = com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy, apiRealmObject, map, set) : copy(realm, aVar, apiRealmObject, z, map, set);
    }

    public static ApiRealmObject copy(Realm realm, a aVar, ApiRealmObject apiRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(apiRealmObject);
        if (realmObjectProxy != null) {
            return (ApiRealmObject) realmObjectProxy;
        }
        ApiRealmObject apiRealmObject2 = apiRealmObject;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(ApiRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, apiRealmObject2.realmGet$url());
        osObjectBuilder.addString(aVar.b, apiRealmObject2.realmGet$content());
        osObjectBuilder.addInteger(aVar.c, Long.valueOf(apiRealmObject2.realmGet$lastUpdateTime()));
        com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(apiRealmObject, a2);
        return a2;
    }

    public static long insert(Realm realm, ApiRealmObject apiRealmObject, Map<RealmModel, Long> map) {
        long j;
        if ((apiRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(apiRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) apiRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(ApiRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(ApiRealmObject.class);
        long j2 = aVar.a;
        ApiRealmObject apiRealmObject2 = apiRealmObject;
        String realmGet$url = apiRealmObject2.realmGet$url();
        long nativeFindFirstString = realmGet$url != null ? Table.nativeFindFirstString(nativePtr, j2, realmGet$url) : -1L;
        if (nativeFindFirstString == -1) {
            j = OsObject.createRowWithPrimaryKey(a2, j2, realmGet$url);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$url);
            j = nativeFindFirstString;
        }
        map.put(apiRealmObject, Long.valueOf(j));
        String realmGet$content = apiRealmObject2.realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(nativePtr, aVar.b, j, realmGet$content, false);
        }
        Table.nativeSetLong(nativePtr, aVar.c, j, apiRealmObject2.realmGet$lastUpdateTime(), false);
        return j;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(ApiRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(ApiRealmObject.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            ApiRealmObject apiRealmObject = (ApiRealmObject) it.next();
            if (!map.containsKey(apiRealmObject)) {
                if ((apiRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(apiRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) apiRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(apiRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                ApiRealmObject apiRealmObject2 = apiRealmObject;
                String realmGet$url = apiRealmObject2.realmGet$url();
                long nativeFindFirstString = realmGet$url != null ? Table.nativeFindFirstString(nativePtr, j2, realmGet$url) : -1L;
                if (nativeFindFirstString == -1) {
                    j = OsObject.createRowWithPrimaryKey(a2, j2, realmGet$url);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$url);
                    j = nativeFindFirstString;
                }
                map.put(apiRealmObject, Long.valueOf(j));
                String realmGet$content = apiRealmObject2.realmGet$content();
                if (realmGet$content != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.b, j, realmGet$content, false);
                } else {
                    j2 = j2;
                }
                Table.nativeSetLong(nativePtr, aVar.c, j, apiRealmObject2.realmGet$lastUpdateTime(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, ApiRealmObject apiRealmObject, Map<RealmModel, Long> map) {
        if ((apiRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(apiRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) apiRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(ApiRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(ApiRealmObject.class);
        long j = aVar.a;
        ApiRealmObject apiRealmObject2 = apiRealmObject;
        String realmGet$url = apiRealmObject2.realmGet$url();
        long nativeFindFirstString = realmGet$url != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$url) : -1L;
        long createRowWithPrimaryKey = nativeFindFirstString == -1 ? OsObject.createRowWithPrimaryKey(a2, j, realmGet$url) : nativeFindFirstString;
        map.put(apiRealmObject, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$content = apiRealmObject2.realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$content, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.c, createRowWithPrimaryKey, apiRealmObject2.realmGet$lastUpdateTime(), false);
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(ApiRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(ApiRealmObject.class);
        long j = aVar.a;
        while (it.hasNext()) {
            ApiRealmObject apiRealmObject = (ApiRealmObject) it.next();
            if (!map.containsKey(apiRealmObject)) {
                if ((apiRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(apiRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) apiRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(apiRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                ApiRealmObject apiRealmObject2 = apiRealmObject;
                String realmGet$url = apiRealmObject2.realmGet$url();
                long nativeFindFirstString = realmGet$url != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$url) : -1L;
                long createRowWithPrimaryKey = nativeFindFirstString == -1 ? OsObject.createRowWithPrimaryKey(a2, j, realmGet$url) : nativeFindFirstString;
                map.put(apiRealmObject, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$content = apiRealmObject2.realmGet$content();
                if (realmGet$content != null) {
                    j = j;
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$content, false);
                } else {
                    j = j;
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.c, createRowWithPrimaryKey, apiRealmObject2.realmGet$lastUpdateTime(), false);
            }
        }
    }

    public static ApiRealmObject createDetachedCopy(ApiRealmObject apiRealmObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        ApiRealmObject apiRealmObject2;
        if (i > i2 || apiRealmObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(apiRealmObject);
        if (cacheData == null) {
            apiRealmObject2 = new ApiRealmObject();
            map.put(apiRealmObject, new RealmObjectProxy.CacheData<>(i, apiRealmObject2));
        } else if (i >= cacheData.minDepth) {
            return (ApiRealmObject) cacheData.object;
        } else {
            apiRealmObject2 = (ApiRealmObject) cacheData.object;
            cacheData.minDepth = i;
        }
        ApiRealmObject apiRealmObject3 = apiRealmObject2;
        ApiRealmObject apiRealmObject4 = apiRealmObject;
        apiRealmObject3.realmSet$url(apiRealmObject4.realmGet$url());
        apiRealmObject3.realmSet$content(apiRealmObject4.realmGet$content());
        apiRealmObject3.realmSet$lastUpdateTime(apiRealmObject4.realmGet$lastUpdateTime());
        return apiRealmObject2;
    }

    static ApiRealmObject a(Realm realm, a aVar, ApiRealmObject apiRealmObject, ApiRealmObject apiRealmObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        ApiRealmObject apiRealmObject3 = apiRealmObject2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(ApiRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, apiRealmObject3.realmGet$url());
        osObjectBuilder.addString(aVar.b, apiRealmObject3.realmGet$content());
        osObjectBuilder.addInteger(aVar.c, Long.valueOf(apiRealmObject3.realmGet$lastUpdateTime()));
        osObjectBuilder.updateExistingObject();
        return apiRealmObject;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "ApiRealmObject = proxy[{url:" + realmGet$url() + "}" + Constants.ACCEPT_TIME_SEPARATOR_SP + "{content:" + realmGet$content() + "}" + Constants.ACCEPT_TIME_SEPARATOR_SP + "{lastUpdateTime:" + realmGet$lastUpdateTime() + "}]";
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.c;
    }

    public int hashCode() {
        String path = this.c.getRealm$realm().getPath();
        String name = this.c.getRow$realm().getTable().getName();
        long objectKey = this.c.getRow$realm().getObjectKey();
        int i = 0;
        int hashCode = (527 + (path != null ? path.hashCode() : 0)) * 31;
        if (name != null) {
            i = name.hashCode();
        }
        return ((hashCode + i) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy = (com_xiaomi_micolauncher_api_cache_ApiRealmObjectRealmProxy) obj;
        BaseRealm realm$realm = this.c.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy.c.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.c.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy.c.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.c.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_api_cache_apirealmobjectrealmproxy.c.getRow$realm().getObjectKey();
        }
        return false;
    }
}
