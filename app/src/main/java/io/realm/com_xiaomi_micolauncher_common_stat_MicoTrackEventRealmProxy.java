package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.common.stat.MicoTrackEvent;
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
public class com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy extends MicoTrackEvent implements com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a b;
    private ProxyState<MicoTrackEvent> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "MicoTrackEvent";
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
            this.a = addColumnDetails("eventId", "eventId", objectSchemaInfo);
            this.b = addColumnDetails("event", "event", objectSchemaInfo);
            this.c = addColumnDetails("data", "data", objectSchemaInfo);
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
    public com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy() {
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

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$eventId() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$eventId(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'eventId' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$event() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.b);
    }

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$event(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.b);
            } else {
                this.c.getRow$realm().setString(this.b.b, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.b, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.b, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public String realmGet$data() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.c);
    }

    @Override // com.xiaomi.micolauncher.common.stat.MicoTrackEvent, io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface
    public void realmSet$data(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.c);
            } else {
                this.c.getRow$realm().setString(this.b.c, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.c, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.c, row$realm.getObjectKey(), str, true);
            }
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 3, 0);
        builder.addPersistedProperty("eventId", RealmFieldType.STRING, true, false, false);
        builder.addPersistedProperty("event", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("data", RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.common.stat.MicoTrackEvent createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            java.util.List r0 = java.util.Collections.emptyList()
            r1 = 0
            if (r13 == 0) goto L_0x0066
            java.lang.Class<com.xiaomi.micolauncher.common.stat.MicoTrackEvent> r13 = com.xiaomi.micolauncher.common.stat.MicoTrackEvent.class
            io.realm.internal.Table r13 = r11.a(r13)
            io.realm.RealmSchema r2 = r11.getSchema()
            java.lang.Class<com.xiaomi.micolauncher.common.stat.MicoTrackEvent> r3 = com.xiaomi.micolauncher.common.stat.MicoTrackEvent.class
            io.realm.internal.ColumnInfo r2 = r2.c(r3)
            io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy$a r2 = (io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.a) r2
            long r2 = r2.a
            java.lang.String r4 = "eventId"
            boolean r4 = r12.isNull(r4)
            if (r4 == 0) goto L_0x0028
            long r2 = r13.findFirstNull(r2)
            goto L_0x0032
        L_0x0028:
            java.lang.String r4 = "eventId"
            java.lang.String r4 = r12.getString(r4)
            long r2 = r13.findFirstString(r2, r4)
        L_0x0032:
            r4 = -1
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0066
            io.realm.BaseRealm$a r4 = io.realm.BaseRealm.objectContext
            java.lang.Object r4 = r4.get()
            io.realm.BaseRealm$RealmObjectContext r4 = (io.realm.BaseRealm.RealmObjectContext) r4
            io.realm.internal.UncheckedRow r7 = r13.getUncheckedRow(r2)     // Catch: all -> 0x0061
            io.realm.RealmSchema r13 = r11.getSchema()     // Catch: all -> 0x0061
            java.lang.Class<com.xiaomi.micolauncher.common.stat.MicoTrackEvent> r2 = com.xiaomi.micolauncher.common.stat.MicoTrackEvent.class
            io.realm.internal.ColumnInfo r8 = r13.c(r2)     // Catch: all -> 0x0061
            r9 = 0
            java.util.List r10 = java.util.Collections.emptyList()     // Catch: all -> 0x0061
            r5 = r4
            r6 = r11
            r5.set(r6, r7, r8, r9, r10)     // Catch: all -> 0x0061
            io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy r13 = new io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy     // Catch: all -> 0x0061
            r13.<init>()     // Catch: all -> 0x0061
            r4.clear()
            goto L_0x0067
        L_0x0061:
            r11 = move-exception
            r4.clear()
            throw r11
        L_0x0066:
            r13 = r1
        L_0x0067:
            if (r13 != 0) goto L_0x009c
            java.lang.String r13 = "eventId"
            boolean r13 = r12.has(r13)
            if (r13 == 0) goto L_0x0094
            java.lang.String r13 = "eventId"
            boolean r13 = r12.isNull(r13)
            r2 = 1
            if (r13 == 0) goto L_0x0084
            java.lang.Class<com.xiaomi.micolauncher.common.stat.MicoTrackEvent> r13 = com.xiaomi.micolauncher.common.stat.MicoTrackEvent.class
            io.realm.RealmModel r11 = r11.a(r13, r1, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy) r13
            goto L_0x009c
        L_0x0084:
            java.lang.Class<com.xiaomi.micolauncher.common.stat.MicoTrackEvent> r13 = com.xiaomi.micolauncher.common.stat.MicoTrackEvent.class
            java.lang.String r3 = "eventId"
            java.lang.String r3 = r12.getString(r3)
            io.realm.RealmModel r11 = r11.a(r13, r3, r2, r0)
            r13 = r11
            io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy r13 = (io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy) r13
            goto L_0x009c
        L_0x0094:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "JSON object doesn't have the primary key field 'eventId'."
            r11.<init>(r12)
            throw r11
        L_0x009c:
            r11 = r13
            io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface r11 = (io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxyInterface) r11
            java.lang.String r0 = "event"
            boolean r0 = r12.has(r0)
            if (r0 == 0) goto L_0x00bc
            java.lang.String r0 = "event"
            boolean r0 = r12.isNull(r0)
            if (r0 == 0) goto L_0x00b3
            r11.realmSet$event(r1)
            goto L_0x00bc
        L_0x00b3:
            java.lang.String r0 = "event"
            java.lang.String r0 = r12.getString(r0)
            r11.realmSet$event(r0)
        L_0x00bc:
            java.lang.String r0 = "data"
            boolean r0 = r12.has(r0)
            if (r0 == 0) goto L_0x00d9
            java.lang.String r0 = "data"
            boolean r0 = r12.isNull(r0)
            if (r0 == 0) goto L_0x00d0
            r11.realmSet$data(r1)
            goto L_0x00d9
        L_0x00d0:
            java.lang.String r0 = "data"
            java.lang.String r12 = r12.getString(r0)
            r11.realmSet$data(r12)
        L_0x00d9:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.common.stat.MicoTrackEvent");
    }

    @TargetApi(11)
    public static MicoTrackEvent createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        MicoTrackEvent micoTrackEvent = new MicoTrackEvent();
        MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("eventId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    micoTrackEvent2.realmSet$eventId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    micoTrackEvent2.realmSet$eventId(null);
                }
                z = true;
            } else if (nextName.equals("event")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    micoTrackEvent2.realmSet$event(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    micoTrackEvent2.realmSet$event(null);
                }
            } else if (!nextName.equals("data")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                micoTrackEvent2.realmSet$data(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                micoTrackEvent2.realmSet$data(null);
            }
        }
        jsonReader.endObject();
        if (z) {
            return (MicoTrackEvent) realm.copyToRealm((Realm) micoTrackEvent, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'eventId'.");
    }

    private static com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(MicoTrackEvent.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy = new com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static MicoTrackEvent copyOrUpdate(Realm realm, a aVar, MicoTrackEvent micoTrackEvent, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy;
        boolean z2;
        long j;
        if ((micoTrackEvent instanceof RealmObjectProxy) && !RealmObject.isFrozen(micoTrackEvent)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) micoTrackEvent;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return micoTrackEvent;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(micoTrackEvent);
        if (realmObjectProxy2 != null) {
            return (MicoTrackEvent) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(MicoTrackEvent.class);
            long j2 = aVar.a;
            String realmGet$eventId = micoTrackEvent.realmGet$eventId();
            if (realmGet$eventId == null) {
                j = a2.findFirstNull(j2);
            } else {
                j = a2.findFirstString(j2, realmGet$eventId);
            }
            if (j == -1) {
                z2 = false;
                com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(j), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy2 = new com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy();
                    map.put(micoTrackEvent, com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy = com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy, micoTrackEvent, map, set) : copy(realm, aVar, micoTrackEvent, z, map, set);
    }

    public static MicoTrackEvent copy(Realm realm, a aVar, MicoTrackEvent micoTrackEvent, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(micoTrackEvent);
        if (realmObjectProxy != null) {
            return (MicoTrackEvent) realmObjectProxy;
        }
        MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(MicoTrackEvent.class), set);
        osObjectBuilder.addString(aVar.a, micoTrackEvent2.realmGet$eventId());
        osObjectBuilder.addString(aVar.b, micoTrackEvent2.realmGet$event());
        osObjectBuilder.addString(aVar.c, micoTrackEvent2.realmGet$data());
        com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(micoTrackEvent, a2);
        return a2;
    }

    public static long insert(Realm realm, MicoTrackEvent micoTrackEvent, Map<RealmModel, Long> map) {
        long j;
        long j2;
        if ((micoTrackEvent instanceof RealmObjectProxy) && !RealmObject.isFrozen(micoTrackEvent)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) micoTrackEvent;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(MicoTrackEvent.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(MicoTrackEvent.class);
        long j3 = aVar.a;
        MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
        String realmGet$eventId = micoTrackEvent2.realmGet$eventId();
        if (realmGet$eventId == null) {
            j = Table.nativeFindFirstNull(nativePtr, j3);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j3, realmGet$eventId);
        }
        if (j == -1) {
            j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$eventId);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$eventId);
            j2 = j;
        }
        map.put(micoTrackEvent, Long.valueOf(j2));
        String realmGet$event = micoTrackEvent2.realmGet$event();
        if (realmGet$event != null) {
            Table.nativeSetString(nativePtr, aVar.b, j2, realmGet$event, false);
        }
        String realmGet$data = micoTrackEvent2.realmGet$data();
        if (realmGet$data != null) {
            Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$data, false);
        }
        return j2;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        long j2;
        Table a2 = realm.a(MicoTrackEvent.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(MicoTrackEvent.class);
        long j3 = aVar.a;
        while (it.hasNext()) {
            MicoTrackEvent micoTrackEvent = (MicoTrackEvent) it.next();
            if (!map.containsKey(micoTrackEvent)) {
                if ((micoTrackEvent instanceof RealmObjectProxy) && !RealmObject.isFrozen(micoTrackEvent)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) micoTrackEvent;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(micoTrackEvent, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
                String realmGet$eventId = micoTrackEvent2.realmGet$eventId();
                if (realmGet$eventId == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j3);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j3, realmGet$eventId);
                }
                if (j == -1) {
                    j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$eventId);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$eventId);
                    j2 = j;
                }
                map.put(micoTrackEvent, Long.valueOf(j2));
                String realmGet$event = micoTrackEvent2.realmGet$event();
                if (realmGet$event != null) {
                    Table.nativeSetString(nativePtr, aVar.b, j2, realmGet$event, false);
                }
                String realmGet$data = micoTrackEvent2.realmGet$data();
                if (realmGet$data != null) {
                    Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$data, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, MicoTrackEvent micoTrackEvent, Map<RealmModel, Long> map) {
        long j;
        if ((micoTrackEvent instanceof RealmObjectProxy) && !RealmObject.isFrozen(micoTrackEvent)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) micoTrackEvent;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(MicoTrackEvent.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(MicoTrackEvent.class);
        long j2 = aVar.a;
        MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
        String realmGet$eventId = micoTrackEvent2.realmGet$eventId();
        if (realmGet$eventId == null) {
            j = Table.nativeFindFirstNull(nativePtr, j2);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j2, realmGet$eventId);
        }
        long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$eventId) : j;
        map.put(micoTrackEvent, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$event = micoTrackEvent2.realmGet$event();
        if (realmGet$event != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$event, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        String realmGet$data = micoTrackEvent2.realmGet$data();
        if (realmGet$data != null) {
            Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$data, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
        }
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(MicoTrackEvent.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(MicoTrackEvent.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            MicoTrackEvent micoTrackEvent = (MicoTrackEvent) it.next();
            if (!map.containsKey(micoTrackEvent)) {
                if ((micoTrackEvent instanceof RealmObjectProxy) && !RealmObject.isFrozen(micoTrackEvent)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) micoTrackEvent;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(micoTrackEvent, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                MicoTrackEvent micoTrackEvent2 = micoTrackEvent;
                String realmGet$eventId = micoTrackEvent2.realmGet$eventId();
                if (realmGet$eventId == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j2);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j2, realmGet$eventId);
                }
                long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$eventId) : j;
                map.put(micoTrackEvent, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$event = micoTrackEvent2.realmGet$event();
                if (realmGet$event != null) {
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$event, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                String realmGet$data = micoTrackEvent2.realmGet$data();
                if (realmGet$data != null) {
                    Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$data, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
                }
            }
        }
    }

    public static MicoTrackEvent createDetachedCopy(MicoTrackEvent micoTrackEvent, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        MicoTrackEvent micoTrackEvent2;
        if (i > i2 || micoTrackEvent == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(micoTrackEvent);
        if (cacheData == null) {
            micoTrackEvent2 = new MicoTrackEvent();
            map.put(micoTrackEvent, new RealmObjectProxy.CacheData<>(i, micoTrackEvent2));
        } else if (i >= cacheData.minDepth) {
            return (MicoTrackEvent) cacheData.object;
        } else {
            micoTrackEvent2 = (MicoTrackEvent) cacheData.object;
            cacheData.minDepth = i;
        }
        MicoTrackEvent micoTrackEvent3 = micoTrackEvent2;
        MicoTrackEvent micoTrackEvent4 = micoTrackEvent;
        micoTrackEvent3.realmSet$eventId(micoTrackEvent4.realmGet$eventId());
        micoTrackEvent3.realmSet$event(micoTrackEvent4.realmGet$event());
        micoTrackEvent3.realmSet$data(micoTrackEvent4.realmGet$data());
        return micoTrackEvent2;
    }

    static MicoTrackEvent a(Realm realm, a aVar, MicoTrackEvent micoTrackEvent, MicoTrackEvent micoTrackEvent2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        MicoTrackEvent micoTrackEvent3 = micoTrackEvent2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(MicoTrackEvent.class), set);
        osObjectBuilder.addString(aVar.a, micoTrackEvent3.realmGet$eventId());
        osObjectBuilder.addString(aVar.b, micoTrackEvent3.realmGet$event());
        osObjectBuilder.addString(aVar.c, micoTrackEvent3.realmGet$data());
        osObjectBuilder.updateExistingObject();
        return micoTrackEvent;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("MicoTrackEvent = proxy[");
        sb.append("{eventId:");
        sb.append(realmGet$eventId() != null ? realmGet$eventId() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{event:");
        sb.append(realmGet$event() != null ? realmGet$event() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{data:");
        sb.append(realmGet$data() != null ? realmGet$data() : "null");
        sb.append("}");
        sb.append("]");
        return sb.toString();
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
        com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy = (com_xiaomi_micolauncher_common_stat_MicoTrackEventRealmProxy) obj;
        BaseRealm realm$realm = this.c.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy.c.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.c.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy.c.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.c.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_common_stat_micotrackeventrealmproxy.c.getRow$realm().getObjectKey();
        }
        return false;
    }
}
