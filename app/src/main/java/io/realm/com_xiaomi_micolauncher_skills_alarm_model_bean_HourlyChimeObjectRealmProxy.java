package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
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
public class com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy extends HourlyChimeObject implements com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a columnInfo;
    private ProxyState<HourlyChimeObject> proxyState;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "HourlyChimeObject";
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
        long d;

        a(OsSchemaInfo osSchemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("id", "id", objectSchemaInfo);
            this.b = addColumnDetails("content", "content", objectSchemaInfo);
            this.c = addColumnDetails("status", "status", objectSchemaInfo);
            this.d = addColumnDetails("position", "position", objectSchemaInfo);
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
            aVar2.d = aVar.d;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState == null) {
            BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
            this.columnInfo = (a) realmObjectContext.getColumnInfo();
            this.proxyState = new ProxyState<>(this);
            this.proxyState.setRealm$realm(realmObjectContext.a());
            this.proxyState.setRow$realm(realmObjectContext.getRow());
            this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
            this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public int realmGet$id() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.a);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$id(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'id' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public String realmGet$content() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.b);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$content(String str) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            if (str == null) {
                this.proxyState.getRow$realm().setNull(this.columnInfo.b);
            } else {
                this.proxyState.getRow$realm().setString(this.columnInfo.b, str);
            }
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.columnInfo.b, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.columnInfo.b, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public boolean realmGet$status() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.c);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$status(boolean z) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setBoolean(this.columnInfo.c, z);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setBoolean(this.columnInfo.c, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public int realmGet$position() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.d);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxyInterface
    public void realmSet$position(int i) {
        if (!this.proxyState.isUnderConstruction()) {
            this.proxyState.getRealm$realm().checkIfValid();
            this.proxyState.getRow$realm().setLong(this.columnInfo.d, i);
        } else if (this.proxyState.getAcceptDefaultValue$realm()) {
            Row row$realm = this.proxyState.getRow$realm();
            row$realm.getTable().setLong(this.columnInfo.d, row$realm.getObjectKey(), i, true);
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 4, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, true, true, true);
        builder.addPersistedProperty("content", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("status", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("position", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject");
    }

    @TargetApi(11)
    public static HourlyChimeObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        HourlyChimeObject hourlyChimeObject = new HourlyChimeObject();
        HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    hourlyChimeObject2.realmSet$id(jsonReader.nextInt());
                    z = true;
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
            } else if (nextName.equals("content")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    hourlyChimeObject2.realmSet$content(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    hourlyChimeObject2.realmSet$content(null);
                }
            } else if (nextName.equals("status")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    hourlyChimeObject2.realmSet$status(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (!nextName.equals("position")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                hourlyChimeObject2.realmSet$position(jsonReader.nextInt());
            } else {
                jsonReader.skipValue();
                throw new IllegalArgumentException("Trying to set non-nullable field 'position' to null.");
            }
        }
        jsonReader.endObject();
        if (z) {
            return (HourlyChimeObject) realm.copyToRealm((Realm) hourlyChimeObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
    }

    private static com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(HourlyChimeObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy = new com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static HourlyChimeObject copyOrUpdate(Realm realm, a aVar, HourlyChimeObject hourlyChimeObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy;
        boolean z2;
        if ((hourlyChimeObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(hourlyChimeObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) hourlyChimeObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return hourlyChimeObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(hourlyChimeObject);
        if (realmObjectProxy2 != null) {
            return (HourlyChimeObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(HourlyChimeObject.class);
            long findFirstLong = a2.findFirstLong(aVar.a, hourlyChimeObject.realmGet$id());
            if (findFirstLong == -1) {
                z2 = false;
                com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(findFirstLong), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy2 = new com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy();
                    map.put(hourlyChimeObject, com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy = com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy, hourlyChimeObject, map, set) : copy(realm, aVar, hourlyChimeObject, z, map, set);
    }

    public static HourlyChimeObject copy(Realm realm, a aVar, HourlyChimeObject hourlyChimeObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(hourlyChimeObject);
        if (realmObjectProxy != null) {
            return (HourlyChimeObject) realmObjectProxy;
        }
        HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(HourlyChimeObject.class), set);
        osObjectBuilder.addInteger(aVar.a, Integer.valueOf(hourlyChimeObject2.realmGet$id()));
        osObjectBuilder.addString(aVar.b, hourlyChimeObject2.realmGet$content());
        osObjectBuilder.addBoolean(aVar.c, Boolean.valueOf(hourlyChimeObject2.realmGet$status()));
        osObjectBuilder.addInteger(aVar.d, Integer.valueOf(hourlyChimeObject2.realmGet$position()));
        com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(hourlyChimeObject, a2);
        return a2;
    }

    public static long insert(Realm realm, HourlyChimeObject hourlyChimeObject, Map<RealmModel, Long> map) {
        long j;
        if ((hourlyChimeObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(hourlyChimeObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) hourlyChimeObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(HourlyChimeObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(HourlyChimeObject.class);
        long j2 = aVar.a;
        HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
        Integer valueOf = Integer.valueOf(hourlyChimeObject2.realmGet$id());
        long nativeFindFirstInt = valueOf != null ? Table.nativeFindFirstInt(nativePtr, j2, hourlyChimeObject2.realmGet$id()) : -1L;
        if (nativeFindFirstInt == -1) {
            j = OsObject.createRowWithPrimaryKey(a2, j2, Integer.valueOf(hourlyChimeObject2.realmGet$id()));
        } else {
            Table.throwDuplicatePrimaryKeyException(valueOf);
            j = nativeFindFirstInt;
        }
        map.put(hourlyChimeObject, Long.valueOf(j));
        String realmGet$content = hourlyChimeObject2.realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(nativePtr, aVar.b, j, realmGet$content, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.c, j, hourlyChimeObject2.realmGet$status(), false);
        Table.nativeSetLong(nativePtr, aVar.d, j, hourlyChimeObject2.realmGet$position(), false);
        return j;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(HourlyChimeObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(HourlyChimeObject.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            HourlyChimeObject hourlyChimeObject = (HourlyChimeObject) it.next();
            if (!map.containsKey(hourlyChimeObject)) {
                if ((hourlyChimeObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(hourlyChimeObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) hourlyChimeObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(hourlyChimeObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
                Integer valueOf = Integer.valueOf(hourlyChimeObject2.realmGet$id());
                long nativeFindFirstInt = valueOf != null ? Table.nativeFindFirstInt(nativePtr, j2, hourlyChimeObject2.realmGet$id()) : -1L;
                if (nativeFindFirstInt == -1) {
                    j = OsObject.createRowWithPrimaryKey(a2, j2, Integer.valueOf(hourlyChimeObject2.realmGet$id()));
                } else {
                    Table.throwDuplicatePrimaryKeyException(valueOf);
                    j = nativeFindFirstInt;
                }
                map.put(hourlyChimeObject, Long.valueOf(j));
                String realmGet$content = hourlyChimeObject2.realmGet$content();
                if (realmGet$content != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.b, j, realmGet$content, false);
                } else {
                    j2 = j2;
                }
                Table.nativeSetBoolean(nativePtr, aVar.c, j, hourlyChimeObject2.realmGet$status(), false);
                Table.nativeSetLong(nativePtr, aVar.d, j, hourlyChimeObject2.realmGet$position(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, HourlyChimeObject hourlyChimeObject, Map<RealmModel, Long> map) {
        if ((hourlyChimeObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(hourlyChimeObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) hourlyChimeObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(HourlyChimeObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(HourlyChimeObject.class);
        long j = aVar.a;
        HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
        long nativeFindFirstInt = Integer.valueOf(hourlyChimeObject2.realmGet$id()) != null ? Table.nativeFindFirstInt(nativePtr, j, hourlyChimeObject2.realmGet$id()) : -1L;
        long createRowWithPrimaryKey = nativeFindFirstInt == -1 ? OsObject.createRowWithPrimaryKey(a2, j, Integer.valueOf(hourlyChimeObject2.realmGet$id())) : nativeFindFirstInt;
        map.put(hourlyChimeObject, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$content = hourlyChimeObject2.realmGet$content();
        if (realmGet$content != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$content, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.c, createRowWithPrimaryKey, hourlyChimeObject2.realmGet$status(), false);
        Table.nativeSetLong(nativePtr, aVar.d, createRowWithPrimaryKey, hourlyChimeObject2.realmGet$position(), false);
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(HourlyChimeObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(HourlyChimeObject.class);
        long j = aVar.a;
        while (it.hasNext()) {
            HourlyChimeObject hourlyChimeObject = (HourlyChimeObject) it.next();
            if (!map.containsKey(hourlyChimeObject)) {
                if ((hourlyChimeObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(hourlyChimeObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) hourlyChimeObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(hourlyChimeObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                HourlyChimeObject hourlyChimeObject2 = hourlyChimeObject;
                long nativeFindFirstInt = Integer.valueOf(hourlyChimeObject2.realmGet$id()) != null ? Table.nativeFindFirstInt(nativePtr, j, hourlyChimeObject2.realmGet$id()) : -1L;
                long createRowWithPrimaryKey = nativeFindFirstInt == -1 ? OsObject.createRowWithPrimaryKey(a2, j, Integer.valueOf(hourlyChimeObject2.realmGet$id())) : nativeFindFirstInt;
                map.put(hourlyChimeObject, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$content = hourlyChimeObject2.realmGet$content();
                if (realmGet$content != null) {
                    j = j;
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$content, false);
                } else {
                    j = j;
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.c, createRowWithPrimaryKey, hourlyChimeObject2.realmGet$status(), false);
                Table.nativeSetLong(nativePtr, aVar.d, createRowWithPrimaryKey, hourlyChimeObject2.realmGet$position(), false);
            }
        }
    }

    public static HourlyChimeObject createDetachedCopy(HourlyChimeObject hourlyChimeObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        HourlyChimeObject hourlyChimeObject2;
        if (i > i2 || hourlyChimeObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(hourlyChimeObject);
        if (cacheData == null) {
            hourlyChimeObject2 = new HourlyChimeObject();
            map.put(hourlyChimeObject, new RealmObjectProxy.CacheData<>(i, hourlyChimeObject2));
        } else if (i >= cacheData.minDepth) {
            return (HourlyChimeObject) cacheData.object;
        } else {
            hourlyChimeObject2 = (HourlyChimeObject) cacheData.object;
            cacheData.minDepth = i;
        }
        HourlyChimeObject hourlyChimeObject3 = hourlyChimeObject2;
        HourlyChimeObject hourlyChimeObject4 = hourlyChimeObject;
        hourlyChimeObject3.realmSet$id(hourlyChimeObject4.realmGet$id());
        hourlyChimeObject3.realmSet$content(hourlyChimeObject4.realmGet$content());
        hourlyChimeObject3.realmSet$status(hourlyChimeObject4.realmGet$status());
        hourlyChimeObject3.realmSet$position(hourlyChimeObject4.realmGet$position());
        return hourlyChimeObject2;
    }

    static HourlyChimeObject a(Realm realm, a aVar, HourlyChimeObject hourlyChimeObject, HourlyChimeObject hourlyChimeObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        HourlyChimeObject hourlyChimeObject3 = hourlyChimeObject2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(HourlyChimeObject.class), set);
        osObjectBuilder.addInteger(aVar.a, Integer.valueOf(hourlyChimeObject3.realmGet$id()));
        osObjectBuilder.addString(aVar.b, hourlyChimeObject3.realmGet$content());
        osObjectBuilder.addBoolean(aVar.c, Boolean.valueOf(hourlyChimeObject3.realmGet$status()));
        osObjectBuilder.addInteger(aVar.d, Integer.valueOf(hourlyChimeObject3.realmGet$position()));
        osObjectBuilder.updateExistingObject();
        return hourlyChimeObject;
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    public int hashCode() {
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long objectKey = this.proxyState.getRow$realm().getObjectKey();
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
        com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy = (com_xiaomi_micolauncher_skills_alarm_model_bean_HourlyChimeObjectRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_skills_alarm_model_bean_hourlychimeobjectrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}
