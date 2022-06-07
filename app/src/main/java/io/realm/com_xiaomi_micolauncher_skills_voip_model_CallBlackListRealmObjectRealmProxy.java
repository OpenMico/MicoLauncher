package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject;
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
public class com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy extends CallBlackListRealmObject implements com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = b();
    private a b;
    private ProxyState<CallBlackListRealmObject> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "CallBlackListRealmObject";
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ColumnInfo {
        long a;

        a(OsSchemaInfo osSchemaInfo) {
            super(1);
            this.a = addColumnDetails("blackListNum", "blackListNum", osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME));
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
            ((a) columnInfo2).a = ((a) columnInfo).a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy() {
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

    @Override // com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject, io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface
    public String realmGet$blackListNum() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject, io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface
    public void realmSet$blackListNum(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'blackListNum' cannot be changed after object was created.");
        }
    }

    private static OsObjectSchemaInfo b() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 1, 0);
        builder.addPersistedProperty("blackListNum", RealmFieldType.STRING, true, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            java.util.List r0 = java.util.Collections.emptyList()
            r1 = 0
            if (r13 == 0) goto L_0x0063
            java.lang.Class<com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject> r13 = com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject.class
            io.realm.internal.Table r13 = r11.a(r13)
            io.realm.RealmSchema r2 = r11.getSchema()
            java.lang.Class<com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject> r3 = com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject.class
            io.realm.internal.ColumnInfo r2 = r2.c(r3)
            io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy$a r2 = (io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.a) r2
            long r2 = r2.a
            java.lang.String r4 = "blackListNum"
            boolean r4 = r12.isNull(r4)
            r5 = -1
            if (r4 != 0) goto L_0x0030
            java.lang.String r4 = "blackListNum"
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
            java.lang.Class<com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject> r2 = com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject.class
            io.realm.internal.ColumnInfo r8 = r13.c(r2)     // Catch: all -> 0x005e
            r9 = 0
            java.util.List r10 = java.util.Collections.emptyList()     // Catch: all -> 0x005e
            r5 = r4
            r6 = r11
            r5.set(r6, r7, r8, r9, r10)     // Catch: all -> 0x005e
            io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy r13 = new io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy     // Catch: all -> 0x005e
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
            java.lang.String r13 = "blackListNum"
            boolean r13 = r12.has(r13)
            if (r13 == 0) goto L_0x0091
            java.lang.String r13 = "blackListNum"
            boolean r13 = r12.isNull(r13)
            r2 = 1
            if (r13 == 0) goto L_0x0081
            java.lang.Class<com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject> r12 = com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject.class
            io.realm.RealmModel r11 = r11.a(r12, r1, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy) r13
            goto L_0x0099
        L_0x0081:
            java.lang.Class<com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject> r13 = com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject.class
            java.lang.String r1 = "blackListNum"
            java.lang.String r12 = r12.getString(r1)
            io.realm.RealmModel r11 = r11.a(r13, r12, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy) r13
            goto L_0x0099
        L_0x0091:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "JSON object doesn't have the primary key field 'blackListNum'."
            r11.<init>(r12)
            throw r11
        L_0x0099:
            r11 = r13
            io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface r11 = (io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxyInterface) r11
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject");
    }

    @TargetApi(11)
    public static CallBlackListRealmObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        CallBlackListRealmObject callBlackListRealmObject = new CallBlackListRealmObject();
        CallBlackListRealmObject callBlackListRealmObject2 = callBlackListRealmObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("blackListNum")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    callBlackListRealmObject2.realmSet$blackListNum(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    callBlackListRealmObject2.realmSet$blackListNum(null);
                }
                z = true;
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        if (z) {
            return (CallBlackListRealmObject) realm.copyToRealm((Realm) callBlackListRealmObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'blackListNum'.");
    }

    private static com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(CallBlackListRealmObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy = new com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static CallBlackListRealmObject copyOrUpdate(Realm realm, a aVar, CallBlackListRealmObject callBlackListRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy;
        boolean z2;
        if ((callBlackListRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(callBlackListRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) callBlackListRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return callBlackListRealmObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(callBlackListRealmObject);
        if (realmObjectProxy2 != null) {
            return (CallBlackListRealmObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(CallBlackListRealmObject.class);
            long findFirstString = a2.findFirstString(aVar.a, callBlackListRealmObject.realmGet$blackListNum());
            if (findFirstString == -1) {
                z2 = false;
                com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(findFirstString), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy2 = new com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy();
                    map.put(callBlackListRealmObject, com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy = com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy, callBlackListRealmObject, map, set) : copy(realm, aVar, callBlackListRealmObject, z, map, set);
    }

    public static CallBlackListRealmObject copy(Realm realm, a aVar, CallBlackListRealmObject callBlackListRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(callBlackListRealmObject);
        if (realmObjectProxy != null) {
            return (CallBlackListRealmObject) realmObjectProxy;
        }
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(CallBlackListRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, callBlackListRealmObject.realmGet$blackListNum());
        com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(callBlackListRealmObject, a2);
        return a2;
    }

    public static long insert(Realm realm, CallBlackListRealmObject callBlackListRealmObject, Map<RealmModel, Long> map) {
        if ((callBlackListRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(callBlackListRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) callBlackListRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(CallBlackListRealmObject.class);
        long nativePtr = a2.getNativePtr();
        long j = ((a) realm.getSchema().c(CallBlackListRealmObject.class)).a;
        String realmGet$blackListNum = callBlackListRealmObject.realmGet$blackListNum();
        long nativeFindFirstString = realmGet$blackListNum != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$blackListNum) : -1L;
        if (nativeFindFirstString == -1) {
            nativeFindFirstString = OsObject.createRowWithPrimaryKey(a2, j, realmGet$blackListNum);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$blackListNum);
        }
        map.put(callBlackListRealmObject, Long.valueOf(nativeFindFirstString));
        return nativeFindFirstString;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(CallBlackListRealmObject.class);
        long nativePtr = a2.getNativePtr();
        long j = ((a) realm.getSchema().c(CallBlackListRealmObject.class)).a;
        while (it.hasNext()) {
            CallBlackListRealmObject callBlackListRealmObject = (CallBlackListRealmObject) it.next();
            if (!map.containsKey(callBlackListRealmObject)) {
                if ((callBlackListRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(callBlackListRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) callBlackListRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(callBlackListRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                String realmGet$blackListNum = callBlackListRealmObject.realmGet$blackListNum();
                long nativeFindFirstString = realmGet$blackListNum != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$blackListNum) : -1L;
                if (nativeFindFirstString == -1) {
                    nativeFindFirstString = OsObject.createRowWithPrimaryKey(a2, j, realmGet$blackListNum);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$blackListNum);
                }
                map.put(callBlackListRealmObject, Long.valueOf(nativeFindFirstString));
            }
        }
    }

    public static long insertOrUpdate(Realm realm, CallBlackListRealmObject callBlackListRealmObject, Map<RealmModel, Long> map) {
        if ((callBlackListRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(callBlackListRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) callBlackListRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(CallBlackListRealmObject.class);
        long nativePtr = a2.getNativePtr();
        long j = ((a) realm.getSchema().c(CallBlackListRealmObject.class)).a;
        String realmGet$blackListNum = callBlackListRealmObject.realmGet$blackListNum();
        long nativeFindFirstString = realmGet$blackListNum != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$blackListNum) : -1L;
        if (nativeFindFirstString == -1) {
            nativeFindFirstString = OsObject.createRowWithPrimaryKey(a2, j, realmGet$blackListNum);
        }
        map.put(callBlackListRealmObject, Long.valueOf(nativeFindFirstString));
        return nativeFindFirstString;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(CallBlackListRealmObject.class);
        long nativePtr = a2.getNativePtr();
        long j = ((a) realm.getSchema().c(CallBlackListRealmObject.class)).a;
        while (it.hasNext()) {
            CallBlackListRealmObject callBlackListRealmObject = (CallBlackListRealmObject) it.next();
            if (!map.containsKey(callBlackListRealmObject)) {
                if ((callBlackListRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(callBlackListRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) callBlackListRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(callBlackListRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                String realmGet$blackListNum = callBlackListRealmObject.realmGet$blackListNum();
                long nativeFindFirstString = realmGet$blackListNum != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$blackListNum) : -1L;
                if (nativeFindFirstString == -1) {
                    nativeFindFirstString = OsObject.createRowWithPrimaryKey(a2, j, realmGet$blackListNum);
                }
                map.put(callBlackListRealmObject, Long.valueOf(nativeFindFirstString));
            }
        }
    }

    public static CallBlackListRealmObject createDetachedCopy(CallBlackListRealmObject callBlackListRealmObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        CallBlackListRealmObject callBlackListRealmObject2;
        if (i > i2 || callBlackListRealmObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(callBlackListRealmObject);
        if (cacheData == null) {
            callBlackListRealmObject2 = new CallBlackListRealmObject();
            map.put(callBlackListRealmObject, new RealmObjectProxy.CacheData<>(i, callBlackListRealmObject2));
        } else if (i >= cacheData.minDepth) {
            return (CallBlackListRealmObject) cacheData.object;
        } else {
            callBlackListRealmObject2 = (CallBlackListRealmObject) cacheData.object;
            cacheData.minDepth = i;
        }
        callBlackListRealmObject2.realmSet$blackListNum(callBlackListRealmObject.realmGet$blackListNum());
        return callBlackListRealmObject2;
    }

    static CallBlackListRealmObject a(Realm realm, a aVar, CallBlackListRealmObject callBlackListRealmObject, CallBlackListRealmObject callBlackListRealmObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(CallBlackListRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, callBlackListRealmObject2.realmGet$blackListNum());
        osObjectBuilder.updateExistingObject();
        return callBlackListRealmObject;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        return "CallBlackListRealmObject = proxy[{blackListNum:" + realmGet$blackListNum() + "}]";
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
        com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy = (com_xiaomi_micolauncher_skills_voip_model_CallBlackListRealmObjectRealmProxy) obj;
        BaseRealm realm$realm = this.c.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy.c.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.c.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy.c.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.c.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_skills_voip_model_callblacklistrealmobjectrealmproxy.c.getRow$realm().getObjectKey();
        }
        return false;
    }
}
