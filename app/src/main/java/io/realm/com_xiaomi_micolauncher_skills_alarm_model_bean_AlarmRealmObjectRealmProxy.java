package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject;
import io.realm.BaseRealm;
import io.realm.exceptions.RealmException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy extends AlarmRealmObject implements com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a b;
    private ProxyState<AlarmRealmObject> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "AlarmRealmObject";
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ColumnInfo {
        long A;
        long B;
        long C;
        long D;
        long E;
        long F;
        long G;
        long H;
        long I;
        long a;
        long b;
        long c;
        long d;
        long e;
        long f;
        long g;
        long h;
        long i;
        long j;
        long k;
        long l;
        long m;
        long n;
        long o;
        long p;
        long q;
        long r;
        long s;
        long t;
        long u;
        long v;
        long w;
        long x;
        long y;
        long z;

        a(OsSchemaInfo osSchemaInfo) {
            super(35);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("id", "id", objectSchemaInfo);
            this.b = addColumnDetails("idStr", "idStr", objectSchemaInfo);
            this.c = addColumnDetails("alarmType", "alarmType", objectSchemaInfo);
            this.d = addColumnDetails("timestamp", "timestamp", objectSchemaInfo);
            this.e = addColumnDetails("circle", "circle", objectSchemaInfo);
            this.f = addColumnDetails("circleExtra", "circleExtra", objectSchemaInfo);
            this.g = addColumnDetails("status", "status", objectSchemaInfo);
            this.h = addColumnDetails("event", "event", objectSchemaInfo);
            this.i = addColumnDetails("reminder", "reminder", objectSchemaInfo);
            this.j = addColumnDetails("position", "position", objectSchemaInfo);
            this.k = addColumnDetails("ringtone", "ringtone", objectSchemaInfo);
            this.l = addColumnDetails("timeReminder", "timeReminder", objectSchemaInfo);
            this.m = addColumnDetails(SchemaActivity.KEY_VOLUME, SchemaActivity.KEY_VOLUME, objectSchemaInfo);
            this.n = addColumnDetails("ringtoneType", "ringtoneType", objectSchemaInfo);
            this.o = addColumnDetails("displayTxt", "displayTxt", objectSchemaInfo);
            this.p = addColumnDetails("ringtoneVideo", "ringtoneVideo", objectSchemaInfo);
            this.q = addColumnDetails("ringtoneVideoImage", "ringtoneVideoImage", objectSchemaInfo);
            this.r = addColumnDetails("reserveBoolean1", "reserveBoolean1", objectSchemaInfo);
            this.s = addColumnDetails("reserveBoolean2", "reserveBoolean2", objectSchemaInfo);
            this.t = addColumnDetails("reserveBoolean3", "reserveBoolean3", objectSchemaInfo);
            this.u = addColumnDetails("reserveBoolean4", "reserveBoolean4", objectSchemaInfo);
            this.v = addColumnDetails("reserveInt1", "reserveInt1", objectSchemaInfo);
            this.w = addColumnDetails("reserveInt2", "reserveInt2", objectSchemaInfo);
            this.x = addColumnDetails("reserveInt3", "reserveInt3", objectSchemaInfo);
            this.y = addColumnDetails("reserveInt4", "reserveInt4", objectSchemaInfo);
            this.z = addColumnDetails("reserveLong1", "reserveLong1", objectSchemaInfo);
            this.A = addColumnDetails("reserveLong2", "reserveLong2", objectSchemaInfo);
            this.B = addColumnDetails("reserveLong3", "reserveLong3", objectSchemaInfo);
            this.C = addColumnDetails("reserveLong4", "reserveLong4", objectSchemaInfo);
            this.D = addColumnDetails("reserveString1", "reserveString1", objectSchemaInfo);
            this.E = addColumnDetails("reserveString2", "reserveString2", objectSchemaInfo);
            this.F = addColumnDetails("reserveString3", "reserveString3", objectSchemaInfo);
            this.G = addColumnDetails("reserveString4", "reserveString4", objectSchemaInfo);
            this.H = addColumnDetails("reservByteArray1", "reservByteArray1", objectSchemaInfo);
            this.I = addColumnDetails("reservByteArray2", "reservByteArray2", objectSchemaInfo);
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
            aVar2.e = aVar.e;
            aVar2.f = aVar.f;
            aVar2.g = aVar.g;
            aVar2.h = aVar.h;
            aVar2.i = aVar.i;
            aVar2.j = aVar.j;
            aVar2.k = aVar.k;
            aVar2.l = aVar.l;
            aVar2.m = aVar.m;
            aVar2.n = aVar.n;
            aVar2.o = aVar.o;
            aVar2.p = aVar.p;
            aVar2.q = aVar.q;
            aVar2.r = aVar.r;
            aVar2.s = aVar.s;
            aVar2.t = aVar.t;
            aVar2.u = aVar.u;
            aVar2.v = aVar.v;
            aVar2.w = aVar.w;
            aVar2.x = aVar.x;
            aVar2.y = aVar.y;
            aVar2.z = aVar.z;
            aVar2.A = aVar.A;
            aVar2.B = aVar.B;
            aVar2.C = aVar.C;
            aVar2.D = aVar.D;
            aVar2.E = aVar.E;
            aVar2.F = aVar.F;
            aVar2.G = aVar.G;
            aVar2.H = aVar.H;
            aVar2.I = aVar.I;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy() {
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

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$id() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$id(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'id' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$idStr() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.b);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$idStr(String str) {
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

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$alarmType() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.c);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$alarmType(String str) {
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

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$timestamp() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.d);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$timestamp(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.d, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.d, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$circle() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.e);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$circle(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.e);
            } else {
                this.c.getRow$realm().setString(this.b.e, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.e, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.e, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$circleExtra() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.f);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$circleExtra(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.f);
            } else {
                this.c.getRow$realm().setString(this.b.f, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.f, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.f, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$status() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.g);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$status(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.g);
            } else {
                this.c.getRow$realm().setString(this.b.g, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.g, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.g, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$event() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.h);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$event(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.h);
            } else {
                this.c.getRow$realm().setString(this.b.h, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.h, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.h, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reminder() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.i);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reminder(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.i);
            } else {
                this.c.getRow$realm().setString(this.b.i, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.i, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.i, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$position() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.j);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$position(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.j);
            } else {
                this.c.getRow$realm().setString(this.b.j, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.j, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.j, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtone() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.k);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtone(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.k);
            } else {
                this.c.getRow$realm().setString(this.b.k, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.k, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.k, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$timeReminder() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.l);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$timeReminder(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.l);
            } else {
                this.c.getRow$realm().setString(this.b.l, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.l, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.l, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$volume() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.m);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$volume(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.m, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.m, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneType() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.n);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneType(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.n);
            } else {
                this.c.getRow$realm().setString(this.b.n, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.n, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.n, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$displayTxt() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.o);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$displayTxt(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.o);
            } else {
                this.c.getRow$realm().setString(this.b.o, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.o, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.o, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneVideo() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.p);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneVideo(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.p);
            } else {
                this.c.getRow$realm().setString(this.b.p, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.p, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.p, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$ringtoneVideoImage() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.q);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$ringtoneVideoImage(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.q);
            } else {
                this.c.getRow$realm().setString(this.b.q, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.q, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.q, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean1() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.r);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean1(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.r, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.r, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean2() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.s);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean2(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.s, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.s, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean3() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.t);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean3(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.t, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.t, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public boolean realmGet$reserveBoolean4() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.u);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveBoolean4(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.u, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.u, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt1() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.v);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt1(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.v, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.v, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt2() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.w);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt2(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.w, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.w, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt3() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.x);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt3(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.x, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.x, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public int realmGet$reserveInt4() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.y);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveInt4(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.y, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.y, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong1() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.z);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong1(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.z, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.z, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong2() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.A);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong2(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.A, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.A, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong3() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.B);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong3(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.B, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.B, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public long realmGet$reserveLong4() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.C);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveLong4(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.C, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.C, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString1() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.D);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString1(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.D);
            } else {
                this.c.getRow$realm().setString(this.b.D, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.D, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.D, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString2() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.E);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString2(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.E);
            } else {
                this.c.getRow$realm().setString(this.b.E, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.E, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.E, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString3() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.F);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString3(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.F);
            } else {
                this.c.getRow$realm().setString(this.b.F, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.F, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.F, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public String realmGet$reserveString4() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.G);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reserveString4(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.G);
            } else {
                this.c.getRow$realm().setString(this.b.G, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.G, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.G, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public byte[] realmGet$reservByteArray1() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBinaryByteArray(this.b.H);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reservByteArray1(byte[] bArr) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (bArr == null) {
                this.c.getRow$realm().setNull(this.b.H);
            } else {
                this.c.getRow$realm().setBinaryByteArray(this.b.H, bArr);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (bArr == null) {
                row$realm.getTable().setNull(this.b.H, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setBinaryByteArray(this.b.H, row$realm.getObjectKey(), bArr, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public byte[] realmGet$reservByteArray2() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBinaryByteArray(this.b.I);
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject, io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxyInterface
    public void realmSet$reservByteArray2(byte[] bArr) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (bArr == null) {
                this.c.getRow$realm().setNull(this.b.I);
            } else {
                this.c.getRow$realm().setBinaryByteArray(this.b.I, bArr);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (bArr == null) {
                row$realm.getTable().setNull(this.b.I, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setBinaryByteArray(this.b.I, row$realm.getObjectKey(), bArr, true);
            }
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 35, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, true, true, true);
        builder.addPersistedProperty("idStr", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("alarmType", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("timestamp", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("circle", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("circleExtra", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("status", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("event", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reminder", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("position", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("ringtone", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("timeReminder", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty(SchemaActivity.KEY_VOLUME, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("ringtoneType", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("displayTxt", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("ringtoneVideo", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("ringtoneVideoImage", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reserveBoolean1", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("reserveBoolean2", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("reserveBoolean3", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("reserveBoolean4", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("reserveInt1", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveInt2", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveInt3", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveInt4", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveLong1", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveLong2", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveLong3", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveLong4", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("reserveString1", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reserveString2", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reserveString3", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reserveString4", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("reservByteArray1", RealmFieldType.BINARY, false, false, false);
        builder.addPersistedProperty("reservByteArray2", RealmFieldType.BINARY, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0471  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 1225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObject");
    }

    @TargetApi(11)
    public static AlarmRealmObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        AlarmRealmObject alarmRealmObject = new AlarmRealmObject();
        AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$id(jsonReader.nextInt());
                    z = true;
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
            } else if (nextName.equals("idStr")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$idStr(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$idStr(null);
                }
            } else if (nextName.equals("alarmType")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$alarmType(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$alarmType(null);
                }
            } else if (nextName.equals("timestamp")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$timestamp(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'timestamp' to null.");
                }
            } else if (nextName.equals("circle")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$circle(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$circle(null);
                }
            } else if (nextName.equals("circleExtra")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$circleExtra(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$circleExtra(null);
                }
            } else if (nextName.equals("status")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$status(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$status(null);
                }
            } else if (nextName.equals("event")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$event(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$event(null);
                }
            } else if (nextName.equals("reminder")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reminder(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reminder(null);
                }
            } else if (nextName.equals("position")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$position(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$position(null);
                }
            } else if (nextName.equals("ringtone")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$ringtone(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$ringtone(null);
                }
            } else if (nextName.equals("timeReminder")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$timeReminder(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$timeReminder(null);
                }
            } else if (nextName.equals(SchemaActivity.KEY_VOLUME)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$volume(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'volume' to null.");
                }
            } else if (nextName.equals("ringtoneType")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$ringtoneType(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$ringtoneType(null);
                }
            } else if (nextName.equals("displayTxt")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$displayTxt(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$displayTxt(null);
                }
            } else if (nextName.equals("ringtoneVideo")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$ringtoneVideo(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$ringtoneVideo(null);
                }
            } else if (nextName.equals("ringtoneVideoImage")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$ringtoneVideoImage(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$ringtoneVideoImage(null);
                }
            } else if (nextName.equals("reserveBoolean1")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveBoolean1(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveBoolean1' to null.");
                }
            } else if (nextName.equals("reserveBoolean2")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveBoolean2(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveBoolean2' to null.");
                }
            } else if (nextName.equals("reserveBoolean3")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveBoolean3(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveBoolean3' to null.");
                }
            } else if (nextName.equals("reserveBoolean4")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveBoolean4(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveBoolean4' to null.");
                }
            } else if (nextName.equals("reserveInt1")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveInt1(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveInt1' to null.");
                }
            } else if (nextName.equals("reserveInt2")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveInt2(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveInt2' to null.");
                }
            } else if (nextName.equals("reserveInt3")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveInt3(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveInt3' to null.");
                }
            } else if (nextName.equals("reserveInt4")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveInt4(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveInt4' to null.");
                }
            } else if (nextName.equals("reserveLong1")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveLong1(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveLong1' to null.");
                }
            } else if (nextName.equals("reserveLong2")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveLong2(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveLong2' to null.");
                }
            } else if (nextName.equals("reserveLong3")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveLong3(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveLong3' to null.");
                }
            } else if (nextName.equals("reserveLong4")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveLong4(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reserveLong4' to null.");
                }
            } else if (nextName.equals("reserveString1")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveString1(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reserveString1(null);
                }
            } else if (nextName.equals("reserveString2")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveString2(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reserveString2(null);
                }
            } else if (nextName.equals("reserveString3")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveString3(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reserveString3(null);
                }
            } else if (nextName.equals("reserveString4")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reserveString4(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reserveString4(null);
                }
            } else if (nextName.equals("reservByteArray1")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    alarmRealmObject2.realmSet$reservByteArray1(JsonUtils.stringToBytes(jsonReader.nextString()));
                } else {
                    jsonReader.skipValue();
                    alarmRealmObject2.realmSet$reservByteArray1(null);
                }
            } else if (!nextName.equals("reservByteArray2")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                alarmRealmObject2.realmSet$reservByteArray2(JsonUtils.stringToBytes(jsonReader.nextString()));
            } else {
                jsonReader.skipValue();
                alarmRealmObject2.realmSet$reservByteArray2(null);
            }
        }
        jsonReader.endObject();
        if (z) {
            return (AlarmRealmObject) realm.copyToRealm((Realm) alarmRealmObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
    }

    private static com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(AlarmRealmObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy = new com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static AlarmRealmObject copyOrUpdate(Realm realm, a aVar, AlarmRealmObject alarmRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy;
        boolean z2;
        if ((alarmRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(alarmRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) alarmRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return alarmRealmObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(alarmRealmObject);
        if (realmObjectProxy2 != null) {
            return (AlarmRealmObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(AlarmRealmObject.class);
            long findFirstLong = a2.findFirstLong(aVar.a, alarmRealmObject.realmGet$id());
            if (findFirstLong == -1) {
                z2 = false;
                com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(findFirstLong), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy2 = new com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy();
                    map.put(alarmRealmObject, com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy = com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_skills_alarm_model_bean_alarmrealmobjectrealmproxy, alarmRealmObject, map, set) : copy(realm, aVar, alarmRealmObject, z, map, set);
    }

    public static AlarmRealmObject copy(Realm realm, a aVar, AlarmRealmObject alarmRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(alarmRealmObject);
        if (realmObjectProxy != null) {
            return (AlarmRealmObject) realmObjectProxy;
        }
        AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(AlarmRealmObject.class), set);
        osObjectBuilder.addInteger(aVar.a, Integer.valueOf(alarmRealmObject2.realmGet$id()));
        osObjectBuilder.addString(aVar.b, alarmRealmObject2.realmGet$idStr());
        osObjectBuilder.addString(aVar.c, alarmRealmObject2.realmGet$alarmType());
        osObjectBuilder.addInteger(aVar.d, Long.valueOf(alarmRealmObject2.realmGet$timestamp()));
        osObjectBuilder.addString(aVar.e, alarmRealmObject2.realmGet$circle());
        osObjectBuilder.addString(aVar.f, alarmRealmObject2.realmGet$circleExtra());
        osObjectBuilder.addString(aVar.g, alarmRealmObject2.realmGet$status());
        osObjectBuilder.addString(aVar.h, alarmRealmObject2.realmGet$event());
        osObjectBuilder.addString(aVar.i, alarmRealmObject2.realmGet$reminder());
        osObjectBuilder.addString(aVar.j, alarmRealmObject2.realmGet$position());
        osObjectBuilder.addString(aVar.k, alarmRealmObject2.realmGet$ringtone());
        osObjectBuilder.addString(aVar.l, alarmRealmObject2.realmGet$timeReminder());
        osObjectBuilder.addInteger(aVar.m, Integer.valueOf(alarmRealmObject2.realmGet$volume()));
        osObjectBuilder.addString(aVar.n, alarmRealmObject2.realmGet$ringtoneType());
        osObjectBuilder.addString(aVar.o, alarmRealmObject2.realmGet$displayTxt());
        osObjectBuilder.addString(aVar.p, alarmRealmObject2.realmGet$ringtoneVideo());
        osObjectBuilder.addString(aVar.q, alarmRealmObject2.realmGet$ringtoneVideoImage());
        osObjectBuilder.addBoolean(aVar.r, Boolean.valueOf(alarmRealmObject2.realmGet$reserveBoolean1()));
        osObjectBuilder.addBoolean(aVar.s, Boolean.valueOf(alarmRealmObject2.realmGet$reserveBoolean2()));
        osObjectBuilder.addBoolean(aVar.t, Boolean.valueOf(alarmRealmObject2.realmGet$reserveBoolean3()));
        osObjectBuilder.addBoolean(aVar.u, Boolean.valueOf(alarmRealmObject2.realmGet$reserveBoolean4()));
        osObjectBuilder.addInteger(aVar.v, Integer.valueOf(alarmRealmObject2.realmGet$reserveInt1()));
        osObjectBuilder.addInteger(aVar.w, Integer.valueOf(alarmRealmObject2.realmGet$reserveInt2()));
        osObjectBuilder.addInteger(aVar.x, Integer.valueOf(alarmRealmObject2.realmGet$reserveInt3()));
        osObjectBuilder.addInteger(aVar.y, Integer.valueOf(alarmRealmObject2.realmGet$reserveInt4()));
        osObjectBuilder.addInteger(aVar.z, Long.valueOf(alarmRealmObject2.realmGet$reserveLong1()));
        osObjectBuilder.addInteger(aVar.A, Long.valueOf(alarmRealmObject2.realmGet$reserveLong2()));
        osObjectBuilder.addInteger(aVar.B, Long.valueOf(alarmRealmObject2.realmGet$reserveLong3()));
        osObjectBuilder.addInteger(aVar.C, Long.valueOf(alarmRealmObject2.realmGet$reserveLong4()));
        osObjectBuilder.addString(aVar.D, alarmRealmObject2.realmGet$reserveString1());
        osObjectBuilder.addString(aVar.E, alarmRealmObject2.realmGet$reserveString2());
        osObjectBuilder.addString(aVar.F, alarmRealmObject2.realmGet$reserveString3());
        osObjectBuilder.addString(aVar.G, alarmRealmObject2.realmGet$reserveString4());
        osObjectBuilder.addByteArray(aVar.H, alarmRealmObject2.realmGet$reservByteArray1());
        osObjectBuilder.addByteArray(aVar.I, alarmRealmObject2.realmGet$reservByteArray2());
        com_xiaomi_micolauncher_skills_alarm_model_bean_AlarmRealmObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(alarmRealmObject, a2);
        return a2;
    }

    public static long insert(Realm realm, AlarmRealmObject alarmRealmObject, Map<RealmModel, Long> map) {
        long j;
        if ((alarmRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(alarmRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) alarmRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(AlarmRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AlarmRealmObject.class);
        long j2 = aVar.a;
        AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
        Integer valueOf = Integer.valueOf(alarmRealmObject2.realmGet$id());
        long nativeFindFirstInt = valueOf != null ? Table.nativeFindFirstInt(nativePtr, j2, alarmRealmObject2.realmGet$id()) : -1L;
        if (nativeFindFirstInt == -1) {
            j = OsObject.createRowWithPrimaryKey(a2, j2, Integer.valueOf(alarmRealmObject2.realmGet$id()));
        } else {
            Table.throwDuplicatePrimaryKeyException(valueOf);
            j = nativeFindFirstInt;
        }
        map.put(alarmRealmObject, Long.valueOf(j));
        String realmGet$idStr = alarmRealmObject2.realmGet$idStr();
        if (realmGet$idStr != null) {
            Table.nativeSetString(nativePtr, aVar.b, j, realmGet$idStr, false);
        }
        String realmGet$alarmType = alarmRealmObject2.realmGet$alarmType();
        if (realmGet$alarmType != null) {
            Table.nativeSetString(nativePtr, aVar.c, j, realmGet$alarmType, false);
        }
        Table.nativeSetLong(nativePtr, aVar.d, j, alarmRealmObject2.realmGet$timestamp(), false);
        String realmGet$circle = alarmRealmObject2.realmGet$circle();
        if (realmGet$circle != null) {
            Table.nativeSetString(nativePtr, aVar.e, j, realmGet$circle, false);
        }
        String realmGet$circleExtra = alarmRealmObject2.realmGet$circleExtra();
        if (realmGet$circleExtra != null) {
            Table.nativeSetString(nativePtr, aVar.f, j, realmGet$circleExtra, false);
        }
        String realmGet$status = alarmRealmObject2.realmGet$status();
        if (realmGet$status != null) {
            Table.nativeSetString(nativePtr, aVar.g, j, realmGet$status, false);
        }
        String realmGet$event = alarmRealmObject2.realmGet$event();
        if (realmGet$event != null) {
            Table.nativeSetString(nativePtr, aVar.h, j, realmGet$event, false);
        }
        String realmGet$reminder = alarmRealmObject2.realmGet$reminder();
        if (realmGet$reminder != null) {
            Table.nativeSetString(nativePtr, aVar.i, j, realmGet$reminder, false);
        }
        String realmGet$position = alarmRealmObject2.realmGet$position();
        if (realmGet$position != null) {
            Table.nativeSetString(nativePtr, aVar.j, j, realmGet$position, false);
        }
        String realmGet$ringtone = alarmRealmObject2.realmGet$ringtone();
        if (realmGet$ringtone != null) {
            Table.nativeSetString(nativePtr, aVar.k, j, realmGet$ringtone, false);
        }
        String realmGet$timeReminder = alarmRealmObject2.realmGet$timeReminder();
        if (realmGet$timeReminder != null) {
            Table.nativeSetString(nativePtr, aVar.l, j, realmGet$timeReminder, false);
        }
        Table.nativeSetLong(nativePtr, aVar.m, j, alarmRealmObject2.realmGet$volume(), false);
        String realmGet$ringtoneType = alarmRealmObject2.realmGet$ringtoneType();
        if (realmGet$ringtoneType != null) {
            Table.nativeSetString(nativePtr, aVar.n, j, realmGet$ringtoneType, false);
        }
        String realmGet$displayTxt = alarmRealmObject2.realmGet$displayTxt();
        if (realmGet$displayTxt != null) {
            Table.nativeSetString(nativePtr, aVar.o, j, realmGet$displayTxt, false);
        }
        String realmGet$ringtoneVideo = alarmRealmObject2.realmGet$ringtoneVideo();
        if (realmGet$ringtoneVideo != null) {
            Table.nativeSetString(nativePtr, aVar.p, j, realmGet$ringtoneVideo, false);
        }
        String realmGet$ringtoneVideoImage = alarmRealmObject2.realmGet$ringtoneVideoImage();
        if (realmGet$ringtoneVideoImage != null) {
            Table.nativeSetString(nativePtr, aVar.q, j, realmGet$ringtoneVideoImage, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.r, j, alarmRealmObject2.realmGet$reserveBoolean1(), false);
        Table.nativeSetBoolean(nativePtr, aVar.s, j, alarmRealmObject2.realmGet$reserveBoolean2(), false);
        Table.nativeSetBoolean(nativePtr, aVar.t, j, alarmRealmObject2.realmGet$reserveBoolean3(), false);
        Table.nativeSetBoolean(nativePtr, aVar.u, j, alarmRealmObject2.realmGet$reserveBoolean4(), false);
        Table.nativeSetLong(nativePtr, aVar.v, j, alarmRealmObject2.realmGet$reserveInt1(), false);
        Table.nativeSetLong(nativePtr, aVar.w, j, alarmRealmObject2.realmGet$reserveInt2(), false);
        Table.nativeSetLong(nativePtr, aVar.x, j, alarmRealmObject2.realmGet$reserveInt3(), false);
        Table.nativeSetLong(nativePtr, aVar.y, j, alarmRealmObject2.realmGet$reserveInt4(), false);
        Table.nativeSetLong(nativePtr, aVar.z, j, alarmRealmObject2.realmGet$reserveLong1(), false);
        Table.nativeSetLong(nativePtr, aVar.A, j, alarmRealmObject2.realmGet$reserveLong2(), false);
        Table.nativeSetLong(nativePtr, aVar.B, j, alarmRealmObject2.realmGet$reserveLong3(), false);
        Table.nativeSetLong(nativePtr, aVar.C, j, alarmRealmObject2.realmGet$reserveLong4(), false);
        String realmGet$reserveString1 = alarmRealmObject2.realmGet$reserveString1();
        if (realmGet$reserveString1 != null) {
            Table.nativeSetString(nativePtr, aVar.D, j, realmGet$reserveString1, false);
        }
        String realmGet$reserveString2 = alarmRealmObject2.realmGet$reserveString2();
        if (realmGet$reserveString2 != null) {
            Table.nativeSetString(nativePtr, aVar.E, j, realmGet$reserveString2, false);
        }
        String realmGet$reserveString3 = alarmRealmObject2.realmGet$reserveString3();
        if (realmGet$reserveString3 != null) {
            Table.nativeSetString(nativePtr, aVar.F, j, realmGet$reserveString3, false);
        }
        String realmGet$reserveString4 = alarmRealmObject2.realmGet$reserveString4();
        if (realmGet$reserveString4 != null) {
            Table.nativeSetString(nativePtr, aVar.G, j, realmGet$reserveString4, false);
        }
        byte[] realmGet$reservByteArray1 = alarmRealmObject2.realmGet$reservByteArray1();
        if (realmGet$reservByteArray1 != null) {
            Table.nativeSetByteArray(nativePtr, aVar.H, j, realmGet$reservByteArray1, false);
        }
        byte[] realmGet$reservByteArray2 = alarmRealmObject2.realmGet$reservByteArray2();
        if (realmGet$reservByteArray2 != null) {
            Table.nativeSetByteArray(nativePtr, aVar.I, j, realmGet$reservByteArray2, false);
        }
        return j;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(AlarmRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AlarmRealmObject.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            AlarmRealmObject alarmRealmObject = (AlarmRealmObject) it.next();
            if (!map.containsKey(alarmRealmObject)) {
                if ((alarmRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(alarmRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) alarmRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(alarmRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
                Integer valueOf = Integer.valueOf(alarmRealmObject2.realmGet$id());
                long nativeFindFirstInt = valueOf != null ? Table.nativeFindFirstInt(nativePtr, j2, alarmRealmObject2.realmGet$id()) : -1L;
                if (nativeFindFirstInt == -1) {
                    j = OsObject.createRowWithPrimaryKey(a2, j2, Integer.valueOf(alarmRealmObject2.realmGet$id()));
                } else {
                    Table.throwDuplicatePrimaryKeyException(valueOf);
                    j = nativeFindFirstInt;
                }
                map.put(alarmRealmObject, Long.valueOf(j));
                String realmGet$idStr = alarmRealmObject2.realmGet$idStr();
                if (realmGet$idStr != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.b, j, realmGet$idStr, false);
                } else {
                    j2 = j2;
                }
                String realmGet$alarmType = alarmRealmObject2.realmGet$alarmType();
                if (realmGet$alarmType != null) {
                    Table.nativeSetString(nativePtr, aVar.c, j, realmGet$alarmType, false);
                }
                Table.nativeSetLong(nativePtr, aVar.d, j, alarmRealmObject2.realmGet$timestamp(), false);
                String realmGet$circle = alarmRealmObject2.realmGet$circle();
                if (realmGet$circle != null) {
                    Table.nativeSetString(nativePtr, aVar.e, j, realmGet$circle, false);
                }
                String realmGet$circleExtra = alarmRealmObject2.realmGet$circleExtra();
                if (realmGet$circleExtra != null) {
                    Table.nativeSetString(nativePtr, aVar.f, j, realmGet$circleExtra, false);
                }
                String realmGet$status = alarmRealmObject2.realmGet$status();
                if (realmGet$status != null) {
                    Table.nativeSetString(nativePtr, aVar.g, j, realmGet$status, false);
                }
                String realmGet$event = alarmRealmObject2.realmGet$event();
                if (realmGet$event != null) {
                    Table.nativeSetString(nativePtr, aVar.h, j, realmGet$event, false);
                }
                String realmGet$reminder = alarmRealmObject2.realmGet$reminder();
                if (realmGet$reminder != null) {
                    Table.nativeSetString(nativePtr, aVar.i, j, realmGet$reminder, false);
                }
                String realmGet$position = alarmRealmObject2.realmGet$position();
                if (realmGet$position != null) {
                    Table.nativeSetString(nativePtr, aVar.j, j, realmGet$position, false);
                }
                String realmGet$ringtone = alarmRealmObject2.realmGet$ringtone();
                if (realmGet$ringtone != null) {
                    Table.nativeSetString(nativePtr, aVar.k, j, realmGet$ringtone, false);
                }
                String realmGet$timeReminder = alarmRealmObject2.realmGet$timeReminder();
                if (realmGet$timeReminder != null) {
                    Table.nativeSetString(nativePtr, aVar.l, j, realmGet$timeReminder, false);
                }
                Table.nativeSetLong(nativePtr, aVar.m, j, alarmRealmObject2.realmGet$volume(), false);
                String realmGet$ringtoneType = alarmRealmObject2.realmGet$ringtoneType();
                if (realmGet$ringtoneType != null) {
                    Table.nativeSetString(nativePtr, aVar.n, j, realmGet$ringtoneType, false);
                }
                String realmGet$displayTxt = alarmRealmObject2.realmGet$displayTxt();
                if (realmGet$displayTxt != null) {
                    Table.nativeSetString(nativePtr, aVar.o, j, realmGet$displayTxt, false);
                }
                String realmGet$ringtoneVideo = alarmRealmObject2.realmGet$ringtoneVideo();
                if (realmGet$ringtoneVideo != null) {
                    Table.nativeSetString(nativePtr, aVar.p, j, realmGet$ringtoneVideo, false);
                }
                String realmGet$ringtoneVideoImage = alarmRealmObject2.realmGet$ringtoneVideoImage();
                if (realmGet$ringtoneVideoImage != null) {
                    Table.nativeSetString(nativePtr, aVar.q, j, realmGet$ringtoneVideoImage, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.r, j, alarmRealmObject2.realmGet$reserveBoolean1(), false);
                Table.nativeSetBoolean(nativePtr, aVar.s, j, alarmRealmObject2.realmGet$reserveBoolean2(), false);
                Table.nativeSetBoolean(nativePtr, aVar.t, j, alarmRealmObject2.realmGet$reserveBoolean3(), false);
                Table.nativeSetBoolean(nativePtr, aVar.u, j, alarmRealmObject2.realmGet$reserveBoolean4(), false);
                Table.nativeSetLong(nativePtr, aVar.v, j, alarmRealmObject2.realmGet$reserveInt1(), false);
                Table.nativeSetLong(nativePtr, aVar.w, j, alarmRealmObject2.realmGet$reserveInt2(), false);
                Table.nativeSetLong(nativePtr, aVar.x, j, alarmRealmObject2.realmGet$reserveInt3(), false);
                Table.nativeSetLong(nativePtr, aVar.y, j, alarmRealmObject2.realmGet$reserveInt4(), false);
                Table.nativeSetLong(nativePtr, aVar.z, j, alarmRealmObject2.realmGet$reserveLong1(), false);
                Table.nativeSetLong(nativePtr, aVar.A, j, alarmRealmObject2.realmGet$reserveLong2(), false);
                Table.nativeSetLong(nativePtr, aVar.B, j, alarmRealmObject2.realmGet$reserveLong3(), false);
                Table.nativeSetLong(nativePtr, aVar.C, j, alarmRealmObject2.realmGet$reserveLong4(), false);
                String realmGet$reserveString1 = alarmRealmObject2.realmGet$reserveString1();
                if (realmGet$reserveString1 != null) {
                    Table.nativeSetString(nativePtr, aVar.D, j, realmGet$reserveString1, false);
                }
                String realmGet$reserveString2 = alarmRealmObject2.realmGet$reserveString2();
                if (realmGet$reserveString2 != null) {
                    Table.nativeSetString(nativePtr, aVar.E, j, realmGet$reserveString2, false);
                }
                String realmGet$reserveString3 = alarmRealmObject2.realmGet$reserveString3();
                if (realmGet$reserveString3 != null) {
                    Table.nativeSetString(nativePtr, aVar.F, j, realmGet$reserveString3, false);
                }
                String realmGet$reserveString4 = alarmRealmObject2.realmGet$reserveString4();
                if (realmGet$reserveString4 != null) {
                    Table.nativeSetString(nativePtr, aVar.G, j, realmGet$reserveString4, false);
                }
                byte[] realmGet$reservByteArray1 = alarmRealmObject2.realmGet$reservByteArray1();
                if (realmGet$reservByteArray1 != null) {
                    Table.nativeSetByteArray(nativePtr, aVar.H, j, realmGet$reservByteArray1, false);
                }
                byte[] realmGet$reservByteArray2 = alarmRealmObject2.realmGet$reservByteArray2();
                if (realmGet$reservByteArray2 != null) {
                    Table.nativeSetByteArray(nativePtr, aVar.I, j, realmGet$reservByteArray2, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, AlarmRealmObject alarmRealmObject, Map<RealmModel, Long> map) {
        if ((alarmRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(alarmRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) alarmRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(AlarmRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AlarmRealmObject.class);
        long j = aVar.a;
        AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
        long nativeFindFirstInt = Integer.valueOf(alarmRealmObject2.realmGet$id()) != null ? Table.nativeFindFirstInt(nativePtr, j, alarmRealmObject2.realmGet$id()) : -1L;
        long createRowWithPrimaryKey = nativeFindFirstInt == -1 ? OsObject.createRowWithPrimaryKey(a2, j, Integer.valueOf(alarmRealmObject2.realmGet$id())) : nativeFindFirstInt;
        map.put(alarmRealmObject, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$idStr = alarmRealmObject2.realmGet$idStr();
        if (realmGet$idStr != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$idStr, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        String realmGet$alarmType = alarmRealmObject2.realmGet$alarmType();
        if (realmGet$alarmType != null) {
            Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$alarmType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.d, createRowWithPrimaryKey, alarmRealmObject2.realmGet$timestamp(), false);
        String realmGet$circle = alarmRealmObject2.realmGet$circle();
        if (realmGet$circle != null) {
            Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$circle, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
        }
        String realmGet$circleExtra = alarmRealmObject2.realmGet$circleExtra();
        if (realmGet$circleExtra != null) {
            Table.nativeSetString(nativePtr, aVar.f, createRowWithPrimaryKey, realmGet$circleExtra, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.f, createRowWithPrimaryKey, false);
        }
        String realmGet$status = alarmRealmObject2.realmGet$status();
        if (realmGet$status != null) {
            Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$status, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
        }
        String realmGet$event = alarmRealmObject2.realmGet$event();
        if (realmGet$event != null) {
            Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$event, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
        }
        String realmGet$reminder = alarmRealmObject2.realmGet$reminder();
        if (realmGet$reminder != null) {
            Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$reminder, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
        }
        String realmGet$position = alarmRealmObject2.realmGet$position();
        if (realmGet$position != null) {
            Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$position, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
        }
        String realmGet$ringtone = alarmRealmObject2.realmGet$ringtone();
        if (realmGet$ringtone != null) {
            Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$ringtone, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
        }
        String realmGet$timeReminder = alarmRealmObject2.realmGet$timeReminder();
        if (realmGet$timeReminder != null) {
            Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$timeReminder, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.m, createRowWithPrimaryKey, alarmRealmObject2.realmGet$volume(), false);
        String realmGet$ringtoneType = alarmRealmObject2.realmGet$ringtoneType();
        if (realmGet$ringtoneType != null) {
            Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$ringtoneType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
        }
        String realmGet$displayTxt = alarmRealmObject2.realmGet$displayTxt();
        if (realmGet$displayTxt != null) {
            Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$displayTxt, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
        }
        String realmGet$ringtoneVideo = alarmRealmObject2.realmGet$ringtoneVideo();
        if (realmGet$ringtoneVideo != null) {
            Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$ringtoneVideo, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
        }
        String realmGet$ringtoneVideoImage = alarmRealmObject2.realmGet$ringtoneVideoImage();
        if (realmGet$ringtoneVideoImage != null) {
            Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$ringtoneVideoImage, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.r, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean1(), false);
        Table.nativeSetBoolean(nativePtr, aVar.s, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean2(), false);
        Table.nativeSetBoolean(nativePtr, aVar.t, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean3(), false);
        Table.nativeSetBoolean(nativePtr, aVar.u, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean4(), false);
        Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt1(), false);
        Table.nativeSetLong(nativePtr, aVar.w, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt2(), false);
        Table.nativeSetLong(nativePtr, aVar.x, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt3(), false);
        Table.nativeSetLong(nativePtr, aVar.y, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt4(), false);
        Table.nativeSetLong(nativePtr, aVar.z, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong1(), false);
        Table.nativeSetLong(nativePtr, aVar.A, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong2(), false);
        Table.nativeSetLong(nativePtr, aVar.B, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong3(), false);
        Table.nativeSetLong(nativePtr, aVar.C, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong4(), false);
        String realmGet$reserveString1 = alarmRealmObject2.realmGet$reserveString1();
        if (realmGet$reserveString1 != null) {
            Table.nativeSetString(nativePtr, aVar.D, createRowWithPrimaryKey, realmGet$reserveString1, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.D, createRowWithPrimaryKey, false);
        }
        String realmGet$reserveString2 = alarmRealmObject2.realmGet$reserveString2();
        if (realmGet$reserveString2 != null) {
            Table.nativeSetString(nativePtr, aVar.E, createRowWithPrimaryKey, realmGet$reserveString2, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.E, createRowWithPrimaryKey, false);
        }
        String realmGet$reserveString3 = alarmRealmObject2.realmGet$reserveString3();
        if (realmGet$reserveString3 != null) {
            Table.nativeSetString(nativePtr, aVar.F, createRowWithPrimaryKey, realmGet$reserveString3, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.F, createRowWithPrimaryKey, false);
        }
        String realmGet$reserveString4 = alarmRealmObject2.realmGet$reserveString4();
        if (realmGet$reserveString4 != null) {
            Table.nativeSetString(nativePtr, aVar.G, createRowWithPrimaryKey, realmGet$reserveString4, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.G, createRowWithPrimaryKey, false);
        }
        byte[] realmGet$reservByteArray1 = alarmRealmObject2.realmGet$reservByteArray1();
        if (realmGet$reservByteArray1 != null) {
            Table.nativeSetByteArray(nativePtr, aVar.H, createRowWithPrimaryKey, realmGet$reservByteArray1, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.H, createRowWithPrimaryKey, false);
        }
        byte[] realmGet$reservByteArray2 = alarmRealmObject2.realmGet$reservByteArray2();
        if (realmGet$reservByteArray2 != null) {
            Table.nativeSetByteArray(nativePtr, aVar.I, createRowWithPrimaryKey, realmGet$reservByteArray2, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.I, createRowWithPrimaryKey, false);
        }
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(AlarmRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AlarmRealmObject.class);
        long j = aVar.a;
        while (it.hasNext()) {
            AlarmRealmObject alarmRealmObject = (AlarmRealmObject) it.next();
            if (!map.containsKey(alarmRealmObject)) {
                if ((alarmRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(alarmRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) alarmRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(alarmRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                AlarmRealmObject alarmRealmObject2 = alarmRealmObject;
                long nativeFindFirstInt = Integer.valueOf(alarmRealmObject2.realmGet$id()) != null ? Table.nativeFindFirstInt(nativePtr, j, alarmRealmObject2.realmGet$id()) : -1L;
                long createRowWithPrimaryKey = nativeFindFirstInt == -1 ? OsObject.createRowWithPrimaryKey(a2, j, Integer.valueOf(alarmRealmObject2.realmGet$id())) : nativeFindFirstInt;
                map.put(alarmRealmObject, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$idStr = alarmRealmObject2.realmGet$idStr();
                if (realmGet$idStr != null) {
                    j = j;
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$idStr, false);
                } else {
                    j = j;
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                String realmGet$alarmType = alarmRealmObject2.realmGet$alarmType();
                if (realmGet$alarmType != null) {
                    Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$alarmType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.d, createRowWithPrimaryKey, alarmRealmObject2.realmGet$timestamp(), false);
                String realmGet$circle = alarmRealmObject2.realmGet$circle();
                if (realmGet$circle != null) {
                    Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$circle, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
                }
                String realmGet$circleExtra = alarmRealmObject2.realmGet$circleExtra();
                if (realmGet$circleExtra != null) {
                    Table.nativeSetString(nativePtr, aVar.f, createRowWithPrimaryKey, realmGet$circleExtra, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.f, createRowWithPrimaryKey, false);
                }
                String realmGet$status = alarmRealmObject2.realmGet$status();
                if (realmGet$status != null) {
                    Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$status, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
                }
                String realmGet$event = alarmRealmObject2.realmGet$event();
                if (realmGet$event != null) {
                    Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$event, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
                }
                String realmGet$reminder = alarmRealmObject2.realmGet$reminder();
                if (realmGet$reminder != null) {
                    Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$reminder, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
                }
                String realmGet$position = alarmRealmObject2.realmGet$position();
                if (realmGet$position != null) {
                    Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$position, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
                }
                String realmGet$ringtone = alarmRealmObject2.realmGet$ringtone();
                if (realmGet$ringtone != null) {
                    Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$ringtone, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
                }
                String realmGet$timeReminder = alarmRealmObject2.realmGet$timeReminder();
                if (realmGet$timeReminder != null) {
                    Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$timeReminder, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.m, createRowWithPrimaryKey, alarmRealmObject2.realmGet$volume(), false);
                String realmGet$ringtoneType = alarmRealmObject2.realmGet$ringtoneType();
                if (realmGet$ringtoneType != null) {
                    Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$ringtoneType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
                }
                String realmGet$displayTxt = alarmRealmObject2.realmGet$displayTxt();
                if (realmGet$displayTxt != null) {
                    Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$displayTxt, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
                }
                String realmGet$ringtoneVideo = alarmRealmObject2.realmGet$ringtoneVideo();
                if (realmGet$ringtoneVideo != null) {
                    Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$ringtoneVideo, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
                }
                String realmGet$ringtoneVideoImage = alarmRealmObject2.realmGet$ringtoneVideoImage();
                if (realmGet$ringtoneVideoImage != null) {
                    Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$ringtoneVideoImage, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.r, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean1(), false);
                Table.nativeSetBoolean(nativePtr, aVar.s, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean2(), false);
                Table.nativeSetBoolean(nativePtr, aVar.t, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean3(), false);
                Table.nativeSetBoolean(nativePtr, aVar.u, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveBoolean4(), false);
                Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt1(), false);
                Table.nativeSetLong(nativePtr, aVar.w, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt2(), false);
                Table.nativeSetLong(nativePtr, aVar.x, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt3(), false);
                Table.nativeSetLong(nativePtr, aVar.y, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveInt4(), false);
                Table.nativeSetLong(nativePtr, aVar.z, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong1(), false);
                Table.nativeSetLong(nativePtr, aVar.A, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong2(), false);
                Table.nativeSetLong(nativePtr, aVar.B, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong3(), false);
                Table.nativeSetLong(nativePtr, aVar.C, createRowWithPrimaryKey, alarmRealmObject2.realmGet$reserveLong4(), false);
                String realmGet$reserveString1 = alarmRealmObject2.realmGet$reserveString1();
                if (realmGet$reserveString1 != null) {
                    Table.nativeSetString(nativePtr, aVar.D, createRowWithPrimaryKey, realmGet$reserveString1, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.D, createRowWithPrimaryKey, false);
                }
                String realmGet$reserveString2 = alarmRealmObject2.realmGet$reserveString2();
                if (realmGet$reserveString2 != null) {
                    Table.nativeSetString(nativePtr, aVar.E, createRowWithPrimaryKey, realmGet$reserveString2, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.E, createRowWithPrimaryKey, false);
                }
                String realmGet$reserveString3 = alarmRealmObject2.realmGet$reserveString3();
                if (realmGet$reserveString3 != null) {
                    Table.nativeSetString(nativePtr, aVar.F, createRowWithPrimaryKey, realmGet$reserveString3, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.F, createRowWithPrimaryKey, false);
                }
                String realmGet$reserveString4 = alarmRealmObject2.realmGet$reserveString4();
                if (realmGet$reserveString4 != null) {
                    Table.nativeSetString(nativePtr, aVar.G, createRowWithPrimaryKey, realmGet$reserveString4, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.G, createRowWithPrimaryKey, false);
                }
                byte[] realmGet$reservByteArray1 = alarmRealmObject2.realmGet$reservByteArray1();
                if (realmGet$reservByteArray1 != null) {
                    Table.nativeSetByteArray(nativePtr, aVar.H, createRowWithPrimaryKey, realmGet$reservByteArray1, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.H, createRowWithPrimaryKey, false);
                }
                byte[] realmGet$reservByteArray2 = alarmRealmObject2.realmGet$reservByteArray2();
                if (realmGet$reservByteArray2 != null) {
                    Table.nativeSetByteArray(nativePtr, aVar.I, createRowWithPrimaryKey, realmGet$reservByteArray2, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.I, createRowWithPrimaryKey, false);
                }
            }
        }
    }

    public static AlarmRealmObject createDetachedCopy(AlarmRealmObject alarmRealmObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        AlarmRealmObject alarmRealmObject2;
        if (i > i2 || alarmRealmObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(alarmRealmObject);
        if (cacheData == null) {
            alarmRealmObject2 = new AlarmRealmObject();
            map.put(alarmRealmObject, new RealmObjectProxy.CacheData<>(i, alarmRealmObject2));
        } else if (i >= cacheData.minDepth) {
            return (AlarmRealmObject) cacheData.object;
        } else {
            alarmRealmObject2 = (AlarmRealmObject) cacheData.object;
            cacheData.minDepth = i;
        }
        AlarmRealmObject alarmRealmObject3 = alarmRealmObject2;
        AlarmRealmObject alarmRealmObject4 = alarmRealmObject;
        alarmRealmObject3.realmSet$id(alarmRealmObject4.realmGet$id());
        alarmRealmObject3.realmSet$idStr(alarmRealmObject4.realmGet$idStr());
        alarmRealmObject3.realmSet$alarmType(alarmRealmObject4.realmGet$alarmType());
        alarmRealmObject3.realmSet$timestamp(alarmRealmObject4.realmGet$timestamp());
        alarmRealmObject3.realmSet$circle(alarmRealmObject4.realmGet$circle());
        alarmRealmObject3.realmSet$circleExtra(alarmRealmObject4.realmGet$circleExtra());
        alarmRealmObject3.realmSet$status(alarmRealmObject4.realmGet$status());
        alarmRealmObject3.realmSet$event(alarmRealmObject4.realmGet$event());
        alarmRealmObject3.realmSet$reminder(alarmRealmObject4.realmGet$reminder());
        alarmRealmObject3.realmSet$position(alarmRealmObject4.realmGet$position());
        alarmRealmObject3.realmSet$ringtone(alarmRealmObject4.realmGet$ringtone());
        alarmRealmObject3.realmSet$timeReminder(alarmRealmObject4.realmGet$timeReminder());
        alarmRealmObject3.realmSet$volume(alarmRealmObject4.realmGet$volume());
        alarmRealmObject3.realmSet$ringtoneType(alarmRealmObject4.realmGet$ringtoneType());
        alarmRealmObject3.realmSet$displayTxt(alarmRealmObject4.realmGet$displayTxt());
        alarmRealmObject3.realmSet$ringtoneVideo(alarmRealmObject4.realmGet$ringtoneVideo());
        alarmRealmObject3.realmSet$ringtoneVideoImage(alarmRealmObject4.realmGet$ringtoneVideoImage());
        alarmRealmObject3.realmSet$reserveBoolean1(alarmRealmObject4.realmGet$reserveBoolean1());
        alarmRealmObject3.realmSet$reserveBoolean2(alarmRealmObject4.realmGet$reserveBoolean2());
        alarmRealmObject3.realmSet$reserveBoolean3(alarmRealmObject4.realmGet$reserveBoolean3());
        alarmRealmObject3.realmSet$reserveBoolean4(alarmRealmObject4.realmGet$reserveBoolean4());
        alarmRealmObject3.realmSet$reserveInt1(alarmRealmObject4.realmGet$reserveInt1());
        alarmRealmObject3.realmSet$reserveInt2(alarmRealmObject4.realmGet$reserveInt2());
        alarmRealmObject3.realmSet$reserveInt3(alarmRealmObject4.realmGet$reserveInt3());
        alarmRealmObject3.realmSet$reserveInt4(alarmRealmObject4.realmGet$reserveInt4());
        alarmRealmObject3.realmSet$reserveLong1(alarmRealmObject4.realmGet$reserveLong1());
        alarmRealmObject3.realmSet$reserveLong2(alarmRealmObject4.realmGet$reserveLong2());
        alarmRealmObject3.realmSet$reserveLong3(alarmRealmObject4.realmGet$reserveLong3());
        alarmRealmObject3.realmSet$reserveLong4(alarmRealmObject4.realmGet$reserveLong4());
        alarmRealmObject3.realmSet$reserveString1(alarmRealmObject4.realmGet$reserveString1());
        alarmRealmObject3.realmSet$reserveString2(alarmRealmObject4.realmGet$reserveString2());
        alarmRealmObject3.realmSet$reserveString3(alarmRealmObject4.realmGet$reserveString3());
        alarmRealmObject3.realmSet$reserveString4(alarmRealmObject4.realmGet$reserveString4());
        alarmRealmObject3.realmSet$reservByteArray1(alarmRealmObject4.realmGet$reservByteArray1());
        alarmRealmObject3.realmSet$reservByteArray2(alarmRealmObject4.realmGet$reservByteArray2());
        return alarmRealmObject2;
    }

    static AlarmRealmObject a(Realm realm, a aVar, AlarmRealmObject alarmRealmObject, AlarmRealmObject alarmRealmObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        AlarmRealmObject alarmRealmObject3 = alarmRealmObject2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(AlarmRealmObject.class), set);
        osObjectBuilder.addInteger(aVar.a, Integer.valueOf(alarmRealmObject3.realmGet$id()));
        osObjectBuilder.addString(aVar.b, alarmRealmObject3.realmGet$idStr());
        osObjectBuilder.addString(aVar.c, alarmRealmObject3.realmGet$alarmType());
        osObjectBuilder.addInteger(aVar.d, Long.valueOf(alarmRealmObject3.realmGet$timestamp()));
        osObjectBuilder.addString(aVar.e, alarmRealmObject3.realmGet$circle());
        osObjectBuilder.addString(aVar.f, alarmRealmObject3.realmGet$circleExtra());
        osObjectBuilder.addString(aVar.g, alarmRealmObject3.realmGet$status());
        osObjectBuilder.addString(aVar.h, alarmRealmObject3.realmGet$event());
        osObjectBuilder.addString(aVar.i, alarmRealmObject3.realmGet$reminder());
        osObjectBuilder.addString(aVar.j, alarmRealmObject3.realmGet$position());
        osObjectBuilder.addString(aVar.k, alarmRealmObject3.realmGet$ringtone());
        osObjectBuilder.addString(aVar.l, alarmRealmObject3.realmGet$timeReminder());
        osObjectBuilder.addInteger(aVar.m, Integer.valueOf(alarmRealmObject3.realmGet$volume()));
        osObjectBuilder.addString(aVar.n, alarmRealmObject3.realmGet$ringtoneType());
        osObjectBuilder.addString(aVar.o, alarmRealmObject3.realmGet$displayTxt());
        osObjectBuilder.addString(aVar.p, alarmRealmObject3.realmGet$ringtoneVideo());
        osObjectBuilder.addString(aVar.q, alarmRealmObject3.realmGet$ringtoneVideoImage());
        osObjectBuilder.addBoolean(aVar.r, Boolean.valueOf(alarmRealmObject3.realmGet$reserveBoolean1()));
        osObjectBuilder.addBoolean(aVar.s, Boolean.valueOf(alarmRealmObject3.realmGet$reserveBoolean2()));
        osObjectBuilder.addBoolean(aVar.t, Boolean.valueOf(alarmRealmObject3.realmGet$reserveBoolean3()));
        osObjectBuilder.addBoolean(aVar.u, Boolean.valueOf(alarmRealmObject3.realmGet$reserveBoolean4()));
        osObjectBuilder.addInteger(aVar.v, Integer.valueOf(alarmRealmObject3.realmGet$reserveInt1()));
        osObjectBuilder.addInteger(aVar.w, Integer.valueOf(alarmRealmObject3.realmGet$reserveInt2()));
        osObjectBuilder.addInteger(aVar.x, Integer.valueOf(alarmRealmObject3.realmGet$reserveInt3()));
        osObjectBuilder.addInteger(aVar.y, Integer.valueOf(alarmRealmObject3.realmGet$reserveInt4()));
        osObjectBuilder.addInteger(aVar.z, Long.valueOf(alarmRealmObject3.realmGet$reserveLong1()));
        osObjectBuilder.addInteger(aVar.A, Long.valueOf(alarmRealmObject3.realmGet$reserveLong2()));
        osObjectBuilder.addInteger(aVar.B, Long.valueOf(alarmRealmObject3.realmGet$reserveLong3()));
        osObjectBuilder.addInteger(aVar.C, Long.valueOf(alarmRealmObject3.realmGet$reserveLong4()));
        osObjectBuilder.addString(aVar.D, alarmRealmObject3.realmGet$reserveString1());
        osObjectBuilder.addString(aVar.E, alarmRealmObject3.realmGet$reserveString2());
        osObjectBuilder.addString(aVar.F, alarmRealmObject3.realmGet$reserveString3());
        osObjectBuilder.addString(aVar.G, alarmRealmObject3.realmGet$reserveString4());
        osObjectBuilder.addByteArray(aVar.H, alarmRealmObject3.realmGet$reservByteArray1());
        osObjectBuilder.addByteArray(aVar.I, alarmRealmObject3.realmGet$reservByteArray2());
        osObjectBuilder.updateExistingObject();
        return alarmRealmObject;
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.c;
    }
}
