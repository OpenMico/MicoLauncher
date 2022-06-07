package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject;
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
public class com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy extends AppRealmObject implements com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a b;
    private ProxyState<AppRealmObject> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "AppRealmObject";
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

        a(OsSchemaInfo osSchemaInfo) {
            super(25);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("appName", "appName", objectSchemaInfo);
            this.b = addColumnDetails("updateDate", "updateDate", objectSchemaInfo);
            this.c = addColumnDetails("appType", "appType", objectSchemaInfo);
            this.d = addColumnDetails("iconUrl", "iconUrl", objectSchemaInfo);
            this.e = addColumnDetails("packageName", "packageName", objectSchemaInfo);
            this.f = addColumnDetails("versionCode", "versionCode", objectSchemaInfo);
            this.g = addColumnDetails("versionName", "versionName", objectSchemaInfo);
            this.h = addColumnDetails("onlineStatus", "onlineStatus", objectSchemaInfo);
            this.i = addColumnDetails("removable", "removable", objectSchemaInfo);
            this.j = addColumnDetails("appCategory", "appCategory", objectSchemaInfo);
            this.k = addColumnDetails("downloadUrl", "downloadUrl", objectSchemaInfo);
            this.l = addColumnDetails("micoAction", "micoAction", objectSchemaInfo);
            this.m = addColumnDetails("hardware", "hardware", objectSchemaInfo);
            this.n = addColumnDetails("channel", "channel", objectSchemaInfo);
            this.o = addColumnDetails("MD5", "MD5", objectSchemaInfo);
            this.p = addColumnDetails("hidden", "hidden", objectSchemaInfo);
            this.q = addColumnDetails("portion", "portion", objectSchemaInfo);
            this.r = addColumnDetails("appKey", "appKey", objectSchemaInfo);
            this.s = addColumnDetails("hardwareVersion", "hardwareVersion", objectSchemaInfo);
            this.t = addColumnDetails("iconResId", "iconResId", objectSchemaInfo);
            this.u = addColumnDetails("sizeCache", "sizeCache", objectSchemaInfo);
            this.v = addColumnDetails("sizeApp", "sizeApp", objectSchemaInfo);
            this.w = addColumnDetails("sizeData", "sizeData", objectSchemaInfo);
            this.x = addColumnDetails("position", "position", objectSchemaInfo);
            this.y = addColumnDetails("modeType", "modeType", objectSchemaInfo);
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
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy() {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appName() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appName(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'appName' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$updateDate() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.b);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$updateDate(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appType() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.c);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appType(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$iconUrl() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.d);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$iconUrl(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.d);
            } else {
                this.c.getRow$realm().setString(this.b.d, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.d, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.d, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$packageName() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.e);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$packageName(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$versionCode() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.f);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$versionCode(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.f, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.f, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$versionName() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.g);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$versionName(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$onlineStatus() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.h);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$onlineStatus(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public boolean realmGet$removable() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$removable(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.i, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.i, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appCategory() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.j);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appCategory(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$downloadUrl() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.k);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$downloadUrl(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$micoAction() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.l);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$micoAction(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$hardware() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.m);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hardware(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.m);
            } else {
                this.c.getRow$realm().setString(this.b.m, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.m, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.m, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$channel() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.n);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$channel(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$MD5() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.o);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$MD5(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public boolean realmGet$hidden() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.p);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hidden(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.p, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.p, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$portion() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.q);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$portion(String str) {
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

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$appKey() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.r);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appKey(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.r, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.r, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$hardwareVersion() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.s);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hardwareVersion(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.s);
            } else {
                this.c.getRow$realm().setString(this.b.s, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.s, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.s, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public int realmGet$iconResId() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.t);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$iconResId(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.t, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.t, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeCache() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.u);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeCache(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.u, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.u, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeApp() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.v);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeApp(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.v, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.v, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeData() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.w);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeData(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.w, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.w, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public int realmGet$position() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.x);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$position(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.x, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.x, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$modeType() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.y);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject, io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$modeType(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.y);
            } else {
                this.c.getRow$realm().setString(this.b.y, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.y, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.y, row$realm.getObjectKey(), str, true);
            }
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 25, 0);
        builder.addPersistedProperty("appName", RealmFieldType.STRING, true, false, false);
        builder.addPersistedProperty("updateDate", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("appType", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("iconUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("packageName", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("versionCode", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("versionName", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("onlineStatus", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("removable", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("appCategory", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("downloadUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("micoAction", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("hardware", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("channel", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("MD5", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("hidden", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("portion", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("appKey", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("hardwareVersion", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("iconResId", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("sizeCache", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("sizeApp", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("sizeData", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("position", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("modeType", RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 901
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.module.homepage.cache.AppRealmObject");
    }

    @TargetApi(11)
    public static AppRealmObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        AppRealmObject appRealmObject = new AppRealmObject();
        AppRealmObject appRealmObject2 = appRealmObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("appName")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$appName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$appName(null);
                }
                z = true;
            } else if (nextName.equals("updateDate")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$updateDate(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$updateDate(null);
                }
            } else if (nextName.equals("appType")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$appType(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$appType(null);
                }
            } else if (nextName.equals("iconUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$iconUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$iconUrl(null);
                }
            } else if (nextName.equals("packageName")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$packageName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$packageName(null);
                }
            } else if (nextName.equals("versionCode")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$versionCode(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'versionCode' to null.");
                }
            } else if (nextName.equals("versionName")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$versionName(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$versionName(null);
                }
            } else if (nextName.equals("onlineStatus")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$onlineStatus(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$onlineStatus(null);
                }
            } else if (nextName.equals("removable")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$removable(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'removable' to null.");
                }
            } else if (nextName.equals("appCategory")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$appCategory(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$appCategory(null);
                }
            } else if (nextName.equals("downloadUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$downloadUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$downloadUrl(null);
                }
            } else if (nextName.equals("micoAction")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$micoAction(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$micoAction(null);
                }
            } else if (nextName.equals("hardware")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$hardware(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$hardware(null);
                }
            } else if (nextName.equals("channel")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$channel(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$channel(null);
                }
            } else if (nextName.equals("MD5")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$MD5(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$MD5(null);
                }
            } else if (nextName.equals("hidden")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$hidden(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'hidden' to null.");
                }
            } else if (nextName.equals("portion")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$portion(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$portion(null);
                }
            } else if (nextName.equals("appKey")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$appKey(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'appKey' to null.");
                }
            } else if (nextName.equals("hardwareVersion")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$hardwareVersion(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    appRealmObject2.realmSet$hardwareVersion(null);
                }
            } else if (nextName.equals("iconResId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$iconResId(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'iconResId' to null.");
                }
            } else if (nextName.equals("sizeCache")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$sizeCache(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sizeCache' to null.");
                }
            } else if (nextName.equals("sizeApp")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$sizeApp(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sizeApp' to null.");
                }
            } else if (nextName.equals("sizeData")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$sizeData(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sizeData' to null.");
                }
            } else if (nextName.equals("position")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    appRealmObject2.realmSet$position(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'position' to null.");
                }
            } else if (!nextName.equals("modeType")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                appRealmObject2.realmSet$modeType(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                appRealmObject2.realmSet$modeType(null);
            }
        }
        jsonReader.endObject();
        if (z) {
            return (AppRealmObject) realm.copyToRealm((Realm) appRealmObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'appName'.");
    }

    private static com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(AppRealmObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy = new com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static AppRealmObject copyOrUpdate(Realm realm, a aVar, AppRealmObject appRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy;
        boolean z2;
        long j;
        if ((appRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(appRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) appRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return appRealmObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(appRealmObject);
        if (realmObjectProxy2 != null) {
            return (AppRealmObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(AppRealmObject.class);
            long j2 = aVar.a;
            String realmGet$appName = appRealmObject.realmGet$appName();
            if (realmGet$appName == null) {
                j = a2.findFirstNull(j2);
            } else {
                j = a2.findFirstString(j2, realmGet$appName);
            }
            if (j == -1) {
                z2 = false;
                com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(j), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy2 = new com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy();
                    map.put(appRealmObject, com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy = com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy, appRealmObject, map, set) : copy(realm, aVar, appRealmObject, z, map, set);
    }

    public static AppRealmObject copy(Realm realm, a aVar, AppRealmObject appRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(appRealmObject);
        if (realmObjectProxy != null) {
            return (AppRealmObject) realmObjectProxy;
        }
        AppRealmObject appRealmObject2 = appRealmObject;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(AppRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, appRealmObject2.realmGet$appName());
        osObjectBuilder.addString(aVar.b, appRealmObject2.realmGet$updateDate());
        osObjectBuilder.addString(aVar.c, appRealmObject2.realmGet$appType());
        osObjectBuilder.addString(aVar.d, appRealmObject2.realmGet$iconUrl());
        osObjectBuilder.addString(aVar.e, appRealmObject2.realmGet$packageName());
        osObjectBuilder.addInteger(aVar.f, Long.valueOf(appRealmObject2.realmGet$versionCode()));
        osObjectBuilder.addString(aVar.g, appRealmObject2.realmGet$versionName());
        osObjectBuilder.addString(aVar.h, appRealmObject2.realmGet$onlineStatus());
        osObjectBuilder.addBoolean(aVar.i, Boolean.valueOf(appRealmObject2.realmGet$removable()));
        osObjectBuilder.addString(aVar.j, appRealmObject2.realmGet$appCategory());
        osObjectBuilder.addString(aVar.k, appRealmObject2.realmGet$downloadUrl());
        osObjectBuilder.addString(aVar.l, appRealmObject2.realmGet$micoAction());
        osObjectBuilder.addString(aVar.m, appRealmObject2.realmGet$hardware());
        osObjectBuilder.addString(aVar.n, appRealmObject2.realmGet$channel());
        osObjectBuilder.addString(aVar.o, appRealmObject2.realmGet$MD5());
        osObjectBuilder.addBoolean(aVar.p, Boolean.valueOf(appRealmObject2.realmGet$hidden()));
        osObjectBuilder.addString(aVar.q, appRealmObject2.realmGet$portion());
        osObjectBuilder.addInteger(aVar.r, Long.valueOf(appRealmObject2.realmGet$appKey()));
        osObjectBuilder.addString(aVar.s, appRealmObject2.realmGet$hardwareVersion());
        osObjectBuilder.addInteger(aVar.t, Integer.valueOf(appRealmObject2.realmGet$iconResId()));
        osObjectBuilder.addInteger(aVar.u, Long.valueOf(appRealmObject2.realmGet$sizeCache()));
        osObjectBuilder.addInteger(aVar.v, Long.valueOf(appRealmObject2.realmGet$sizeApp()));
        osObjectBuilder.addInteger(aVar.w, Long.valueOf(appRealmObject2.realmGet$sizeData()));
        osObjectBuilder.addInteger(aVar.x, Integer.valueOf(appRealmObject2.realmGet$position()));
        osObjectBuilder.addString(aVar.y, appRealmObject2.realmGet$modeType());
        com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(appRealmObject, a2);
        return a2;
    }

    public static long insert(Realm realm, AppRealmObject appRealmObject, Map<RealmModel, Long> map) {
        long j;
        long j2;
        if ((appRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(appRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) appRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(AppRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AppRealmObject.class);
        long j3 = aVar.a;
        AppRealmObject appRealmObject2 = appRealmObject;
        String realmGet$appName = appRealmObject2.realmGet$appName();
        if (realmGet$appName == null) {
            j = Table.nativeFindFirstNull(nativePtr, j3);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j3, realmGet$appName);
        }
        if (j == -1) {
            j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$appName);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$appName);
            j2 = j;
        }
        map.put(appRealmObject, Long.valueOf(j2));
        String realmGet$updateDate = appRealmObject2.realmGet$updateDate();
        if (realmGet$updateDate != null) {
            Table.nativeSetString(nativePtr, aVar.b, j2, realmGet$updateDate, false);
        }
        String realmGet$appType = appRealmObject2.realmGet$appType();
        if (realmGet$appType != null) {
            Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$appType, false);
        }
        String realmGet$iconUrl = appRealmObject2.realmGet$iconUrl();
        if (realmGet$iconUrl != null) {
            Table.nativeSetString(nativePtr, aVar.d, j2, realmGet$iconUrl, false);
        }
        String realmGet$packageName = appRealmObject2.realmGet$packageName();
        if (realmGet$packageName != null) {
            Table.nativeSetString(nativePtr, aVar.e, j2, realmGet$packageName, false);
        }
        Table.nativeSetLong(nativePtr, aVar.f, j2, appRealmObject2.realmGet$versionCode(), false);
        String realmGet$versionName = appRealmObject2.realmGet$versionName();
        if (realmGet$versionName != null) {
            Table.nativeSetString(nativePtr, aVar.g, j2, realmGet$versionName, false);
        }
        String realmGet$onlineStatus = appRealmObject2.realmGet$onlineStatus();
        if (realmGet$onlineStatus != null) {
            Table.nativeSetString(nativePtr, aVar.h, j2, realmGet$onlineStatus, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.i, j2, appRealmObject2.realmGet$removable(), false);
        String realmGet$appCategory = appRealmObject2.realmGet$appCategory();
        if (realmGet$appCategory != null) {
            Table.nativeSetString(nativePtr, aVar.j, j2, realmGet$appCategory, false);
        }
        String realmGet$downloadUrl = appRealmObject2.realmGet$downloadUrl();
        if (realmGet$downloadUrl != null) {
            Table.nativeSetString(nativePtr, aVar.k, j2, realmGet$downloadUrl, false);
        }
        String realmGet$micoAction = appRealmObject2.realmGet$micoAction();
        if (realmGet$micoAction != null) {
            Table.nativeSetString(nativePtr, aVar.l, j2, realmGet$micoAction, false);
        }
        String realmGet$hardware = appRealmObject2.realmGet$hardware();
        if (realmGet$hardware != null) {
            Table.nativeSetString(nativePtr, aVar.m, j2, realmGet$hardware, false);
        }
        String realmGet$channel = appRealmObject2.realmGet$channel();
        if (realmGet$channel != null) {
            Table.nativeSetString(nativePtr, aVar.n, j2, realmGet$channel, false);
        }
        String realmGet$MD5 = appRealmObject2.realmGet$MD5();
        if (realmGet$MD5 != null) {
            Table.nativeSetString(nativePtr, aVar.o, j2, realmGet$MD5, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.p, j2, appRealmObject2.realmGet$hidden(), false);
        String realmGet$portion = appRealmObject2.realmGet$portion();
        if (realmGet$portion != null) {
            Table.nativeSetString(nativePtr, aVar.q, j2, realmGet$portion, false);
        }
        Table.nativeSetLong(nativePtr, aVar.r, j2, appRealmObject2.realmGet$appKey(), false);
        String realmGet$hardwareVersion = appRealmObject2.realmGet$hardwareVersion();
        if (realmGet$hardwareVersion != null) {
            Table.nativeSetString(nativePtr, aVar.s, j2, realmGet$hardwareVersion, false);
        }
        Table.nativeSetLong(nativePtr, aVar.t, j2, appRealmObject2.realmGet$iconResId(), false);
        Table.nativeSetLong(nativePtr, aVar.u, j2, appRealmObject2.realmGet$sizeCache(), false);
        Table.nativeSetLong(nativePtr, aVar.v, j2, appRealmObject2.realmGet$sizeApp(), false);
        Table.nativeSetLong(nativePtr, aVar.w, j2, appRealmObject2.realmGet$sizeData(), false);
        Table.nativeSetLong(nativePtr, aVar.x, j2, appRealmObject2.realmGet$position(), false);
        String realmGet$modeType = appRealmObject2.realmGet$modeType();
        if (realmGet$modeType != null) {
            Table.nativeSetString(nativePtr, aVar.y, j2, realmGet$modeType, false);
        }
        return j2;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        long j2;
        Table a2 = realm.a(AppRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AppRealmObject.class);
        long j3 = aVar.a;
        while (it.hasNext()) {
            AppRealmObject appRealmObject = (AppRealmObject) it.next();
            if (!map.containsKey(appRealmObject)) {
                if ((appRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(appRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) appRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(appRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                AppRealmObject appRealmObject2 = appRealmObject;
                String realmGet$appName = appRealmObject2.realmGet$appName();
                if (realmGet$appName == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j3);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j3, realmGet$appName);
                }
                if (j == -1) {
                    j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$appName);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$appName);
                    j2 = j;
                }
                map.put(appRealmObject, Long.valueOf(j2));
                String realmGet$updateDate = appRealmObject2.realmGet$updateDate();
                if (realmGet$updateDate != null) {
                    j3 = j3;
                    Table.nativeSetString(nativePtr, aVar.b, j2, realmGet$updateDate, false);
                } else {
                    j3 = j3;
                }
                String realmGet$appType = appRealmObject2.realmGet$appType();
                if (realmGet$appType != null) {
                    Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$appType, false);
                }
                String realmGet$iconUrl = appRealmObject2.realmGet$iconUrl();
                if (realmGet$iconUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.d, j2, realmGet$iconUrl, false);
                }
                String realmGet$packageName = appRealmObject2.realmGet$packageName();
                if (realmGet$packageName != null) {
                    Table.nativeSetString(nativePtr, aVar.e, j2, realmGet$packageName, false);
                }
                Table.nativeSetLong(nativePtr, aVar.f, j2, appRealmObject2.realmGet$versionCode(), false);
                String realmGet$versionName = appRealmObject2.realmGet$versionName();
                if (realmGet$versionName != null) {
                    Table.nativeSetString(nativePtr, aVar.g, j2, realmGet$versionName, false);
                }
                String realmGet$onlineStatus = appRealmObject2.realmGet$onlineStatus();
                if (realmGet$onlineStatus != null) {
                    Table.nativeSetString(nativePtr, aVar.h, j2, realmGet$onlineStatus, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.i, j2, appRealmObject2.realmGet$removable(), false);
                String realmGet$appCategory = appRealmObject2.realmGet$appCategory();
                if (realmGet$appCategory != null) {
                    Table.nativeSetString(nativePtr, aVar.j, j2, realmGet$appCategory, false);
                }
                String realmGet$downloadUrl = appRealmObject2.realmGet$downloadUrl();
                if (realmGet$downloadUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.k, j2, realmGet$downloadUrl, false);
                }
                String realmGet$micoAction = appRealmObject2.realmGet$micoAction();
                if (realmGet$micoAction != null) {
                    Table.nativeSetString(nativePtr, aVar.l, j2, realmGet$micoAction, false);
                }
                String realmGet$hardware = appRealmObject2.realmGet$hardware();
                if (realmGet$hardware != null) {
                    Table.nativeSetString(nativePtr, aVar.m, j2, realmGet$hardware, false);
                }
                String realmGet$channel = appRealmObject2.realmGet$channel();
                if (realmGet$channel != null) {
                    Table.nativeSetString(nativePtr, aVar.n, j2, realmGet$channel, false);
                }
                String realmGet$MD5 = appRealmObject2.realmGet$MD5();
                if (realmGet$MD5 != null) {
                    Table.nativeSetString(nativePtr, aVar.o, j2, realmGet$MD5, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.p, j2, appRealmObject2.realmGet$hidden(), false);
                String realmGet$portion = appRealmObject2.realmGet$portion();
                if (realmGet$portion != null) {
                    Table.nativeSetString(nativePtr, aVar.q, j2, realmGet$portion, false);
                }
                Table.nativeSetLong(nativePtr, aVar.r, j2, appRealmObject2.realmGet$appKey(), false);
                String realmGet$hardwareVersion = appRealmObject2.realmGet$hardwareVersion();
                if (realmGet$hardwareVersion != null) {
                    Table.nativeSetString(nativePtr, aVar.s, j2, realmGet$hardwareVersion, false);
                }
                Table.nativeSetLong(nativePtr, aVar.t, j2, appRealmObject2.realmGet$iconResId(), false);
                Table.nativeSetLong(nativePtr, aVar.u, j2, appRealmObject2.realmGet$sizeCache(), false);
                Table.nativeSetLong(nativePtr, aVar.v, j2, appRealmObject2.realmGet$sizeApp(), false);
                Table.nativeSetLong(nativePtr, aVar.w, j2, appRealmObject2.realmGet$sizeData(), false);
                Table.nativeSetLong(nativePtr, aVar.x, j2, appRealmObject2.realmGet$position(), false);
                String realmGet$modeType = appRealmObject2.realmGet$modeType();
                if (realmGet$modeType != null) {
                    Table.nativeSetString(nativePtr, aVar.y, j2, realmGet$modeType, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, AppRealmObject appRealmObject, Map<RealmModel, Long> map) {
        long j;
        if ((appRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(appRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) appRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(AppRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AppRealmObject.class);
        long j2 = aVar.a;
        AppRealmObject appRealmObject2 = appRealmObject;
        String realmGet$appName = appRealmObject2.realmGet$appName();
        if (realmGet$appName == null) {
            j = Table.nativeFindFirstNull(nativePtr, j2);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j2, realmGet$appName);
        }
        long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$appName) : j;
        map.put(appRealmObject, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$updateDate = appRealmObject2.realmGet$updateDate();
        if (realmGet$updateDate != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$updateDate, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        String realmGet$appType = appRealmObject2.realmGet$appType();
        if (realmGet$appType != null) {
            Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$appType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
        }
        String realmGet$iconUrl = appRealmObject2.realmGet$iconUrl();
        if (realmGet$iconUrl != null) {
            Table.nativeSetString(nativePtr, aVar.d, createRowWithPrimaryKey, realmGet$iconUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.d, createRowWithPrimaryKey, false);
        }
        String realmGet$packageName = appRealmObject2.realmGet$packageName();
        if (realmGet$packageName != null) {
            Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$packageName, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.f, createRowWithPrimaryKey, appRealmObject2.realmGet$versionCode(), false);
        String realmGet$versionName = appRealmObject2.realmGet$versionName();
        if (realmGet$versionName != null) {
            Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$versionName, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
        }
        String realmGet$onlineStatus = appRealmObject2.realmGet$onlineStatus();
        if (realmGet$onlineStatus != null) {
            Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$onlineStatus, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.i, createRowWithPrimaryKey, appRealmObject2.realmGet$removable(), false);
        String realmGet$appCategory = appRealmObject2.realmGet$appCategory();
        if (realmGet$appCategory != null) {
            Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$appCategory, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
        }
        String realmGet$downloadUrl = appRealmObject2.realmGet$downloadUrl();
        if (realmGet$downloadUrl != null) {
            Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$downloadUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
        }
        String realmGet$micoAction = appRealmObject2.realmGet$micoAction();
        if (realmGet$micoAction != null) {
            Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$micoAction, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
        }
        String realmGet$hardware = appRealmObject2.realmGet$hardware();
        if (realmGet$hardware != null) {
            Table.nativeSetString(nativePtr, aVar.m, createRowWithPrimaryKey, realmGet$hardware, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.m, createRowWithPrimaryKey, false);
        }
        String realmGet$channel = appRealmObject2.realmGet$channel();
        if (realmGet$channel != null) {
            Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$channel, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
        }
        String realmGet$MD5 = appRealmObject2.realmGet$MD5();
        if (realmGet$MD5 != null) {
            Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$MD5, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.p, createRowWithPrimaryKey, appRealmObject2.realmGet$hidden(), false);
        String realmGet$portion = appRealmObject2.realmGet$portion();
        if (realmGet$portion != null) {
            Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$portion, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.r, createRowWithPrimaryKey, appRealmObject2.realmGet$appKey(), false);
        String realmGet$hardwareVersion = appRealmObject2.realmGet$hardwareVersion();
        if (realmGet$hardwareVersion != null) {
            Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$hardwareVersion, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.t, createRowWithPrimaryKey, appRealmObject2.realmGet$iconResId(), false);
        Table.nativeSetLong(nativePtr, aVar.u, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeCache(), false);
        Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeApp(), false);
        Table.nativeSetLong(nativePtr, aVar.w, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeData(), false);
        Table.nativeSetLong(nativePtr, aVar.x, createRowWithPrimaryKey, appRealmObject2.realmGet$position(), false);
        String realmGet$modeType = appRealmObject2.realmGet$modeType();
        if (realmGet$modeType != null) {
            Table.nativeSetString(nativePtr, aVar.y, createRowWithPrimaryKey, realmGet$modeType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.y, createRowWithPrimaryKey, false);
        }
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(AppRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(AppRealmObject.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            AppRealmObject appRealmObject = (AppRealmObject) it.next();
            if (!map.containsKey(appRealmObject)) {
                if ((appRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(appRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) appRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(appRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                AppRealmObject appRealmObject2 = appRealmObject;
                String realmGet$appName = appRealmObject2.realmGet$appName();
                if (realmGet$appName == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j2);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j2, realmGet$appName);
                }
                long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$appName) : j;
                map.put(appRealmObject, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$updateDate = appRealmObject2.realmGet$updateDate();
                if (realmGet$updateDate != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$updateDate, false);
                } else {
                    j2 = j2;
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                String realmGet$appType = appRealmObject2.realmGet$appType();
                if (realmGet$appType != null) {
                    Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$appType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
                }
                String realmGet$iconUrl = appRealmObject2.realmGet$iconUrl();
                if (realmGet$iconUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.d, createRowWithPrimaryKey, realmGet$iconUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.d, createRowWithPrimaryKey, false);
                }
                String realmGet$packageName = appRealmObject2.realmGet$packageName();
                if (realmGet$packageName != null) {
                    Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$packageName, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.f, createRowWithPrimaryKey, appRealmObject2.realmGet$versionCode(), false);
                String realmGet$versionName = appRealmObject2.realmGet$versionName();
                if (realmGet$versionName != null) {
                    Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$versionName, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
                }
                String realmGet$onlineStatus = appRealmObject2.realmGet$onlineStatus();
                if (realmGet$onlineStatus != null) {
                    Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$onlineStatus, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.i, createRowWithPrimaryKey, appRealmObject2.realmGet$removable(), false);
                String realmGet$appCategory = appRealmObject2.realmGet$appCategory();
                if (realmGet$appCategory != null) {
                    Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$appCategory, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
                }
                String realmGet$downloadUrl = appRealmObject2.realmGet$downloadUrl();
                if (realmGet$downloadUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$downloadUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
                }
                String realmGet$micoAction = appRealmObject2.realmGet$micoAction();
                if (realmGet$micoAction != null) {
                    Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$micoAction, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
                }
                String realmGet$hardware = appRealmObject2.realmGet$hardware();
                if (realmGet$hardware != null) {
                    Table.nativeSetString(nativePtr, aVar.m, createRowWithPrimaryKey, realmGet$hardware, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.m, createRowWithPrimaryKey, false);
                }
                String realmGet$channel = appRealmObject2.realmGet$channel();
                if (realmGet$channel != null) {
                    Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$channel, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
                }
                String realmGet$MD5 = appRealmObject2.realmGet$MD5();
                if (realmGet$MD5 != null) {
                    Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$MD5, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.p, createRowWithPrimaryKey, appRealmObject2.realmGet$hidden(), false);
                String realmGet$portion = appRealmObject2.realmGet$portion();
                if (realmGet$portion != null) {
                    Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$portion, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.r, createRowWithPrimaryKey, appRealmObject2.realmGet$appKey(), false);
                String realmGet$hardwareVersion = appRealmObject2.realmGet$hardwareVersion();
                if (realmGet$hardwareVersion != null) {
                    Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$hardwareVersion, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.t, createRowWithPrimaryKey, appRealmObject2.realmGet$iconResId(), false);
                Table.nativeSetLong(nativePtr, aVar.u, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeCache(), false);
                Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeApp(), false);
                Table.nativeSetLong(nativePtr, aVar.w, createRowWithPrimaryKey, appRealmObject2.realmGet$sizeData(), false);
                Table.nativeSetLong(nativePtr, aVar.x, createRowWithPrimaryKey, appRealmObject2.realmGet$position(), false);
                String realmGet$modeType = appRealmObject2.realmGet$modeType();
                if (realmGet$modeType != null) {
                    Table.nativeSetString(nativePtr, aVar.y, createRowWithPrimaryKey, realmGet$modeType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.y, createRowWithPrimaryKey, false);
                }
            }
        }
    }

    public static AppRealmObject createDetachedCopy(AppRealmObject appRealmObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        AppRealmObject appRealmObject2;
        if (i > i2 || appRealmObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(appRealmObject);
        if (cacheData == null) {
            appRealmObject2 = new AppRealmObject();
            map.put(appRealmObject, new RealmObjectProxy.CacheData<>(i, appRealmObject2));
        } else if (i >= cacheData.minDepth) {
            return (AppRealmObject) cacheData.object;
        } else {
            appRealmObject2 = (AppRealmObject) cacheData.object;
            cacheData.minDepth = i;
        }
        AppRealmObject appRealmObject3 = appRealmObject2;
        AppRealmObject appRealmObject4 = appRealmObject;
        appRealmObject3.realmSet$appName(appRealmObject4.realmGet$appName());
        appRealmObject3.realmSet$updateDate(appRealmObject4.realmGet$updateDate());
        appRealmObject3.realmSet$appType(appRealmObject4.realmGet$appType());
        appRealmObject3.realmSet$iconUrl(appRealmObject4.realmGet$iconUrl());
        appRealmObject3.realmSet$packageName(appRealmObject4.realmGet$packageName());
        appRealmObject3.realmSet$versionCode(appRealmObject4.realmGet$versionCode());
        appRealmObject3.realmSet$versionName(appRealmObject4.realmGet$versionName());
        appRealmObject3.realmSet$onlineStatus(appRealmObject4.realmGet$onlineStatus());
        appRealmObject3.realmSet$removable(appRealmObject4.realmGet$removable());
        appRealmObject3.realmSet$appCategory(appRealmObject4.realmGet$appCategory());
        appRealmObject3.realmSet$downloadUrl(appRealmObject4.realmGet$downloadUrl());
        appRealmObject3.realmSet$micoAction(appRealmObject4.realmGet$micoAction());
        appRealmObject3.realmSet$hardware(appRealmObject4.realmGet$hardware());
        appRealmObject3.realmSet$channel(appRealmObject4.realmGet$channel());
        appRealmObject3.realmSet$MD5(appRealmObject4.realmGet$MD5());
        appRealmObject3.realmSet$hidden(appRealmObject4.realmGet$hidden());
        appRealmObject3.realmSet$portion(appRealmObject4.realmGet$portion());
        appRealmObject3.realmSet$appKey(appRealmObject4.realmGet$appKey());
        appRealmObject3.realmSet$hardwareVersion(appRealmObject4.realmGet$hardwareVersion());
        appRealmObject3.realmSet$iconResId(appRealmObject4.realmGet$iconResId());
        appRealmObject3.realmSet$sizeCache(appRealmObject4.realmGet$sizeCache());
        appRealmObject3.realmSet$sizeApp(appRealmObject4.realmGet$sizeApp());
        appRealmObject3.realmSet$sizeData(appRealmObject4.realmGet$sizeData());
        appRealmObject3.realmSet$position(appRealmObject4.realmGet$position());
        appRealmObject3.realmSet$modeType(appRealmObject4.realmGet$modeType());
        return appRealmObject2;
    }

    static AppRealmObject a(Realm realm, a aVar, AppRealmObject appRealmObject, AppRealmObject appRealmObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        AppRealmObject appRealmObject3 = appRealmObject2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(AppRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, appRealmObject3.realmGet$appName());
        osObjectBuilder.addString(aVar.b, appRealmObject3.realmGet$updateDate());
        osObjectBuilder.addString(aVar.c, appRealmObject3.realmGet$appType());
        osObjectBuilder.addString(aVar.d, appRealmObject3.realmGet$iconUrl());
        osObjectBuilder.addString(aVar.e, appRealmObject3.realmGet$packageName());
        osObjectBuilder.addInteger(aVar.f, Long.valueOf(appRealmObject3.realmGet$versionCode()));
        osObjectBuilder.addString(aVar.g, appRealmObject3.realmGet$versionName());
        osObjectBuilder.addString(aVar.h, appRealmObject3.realmGet$onlineStatus());
        osObjectBuilder.addBoolean(aVar.i, Boolean.valueOf(appRealmObject3.realmGet$removable()));
        osObjectBuilder.addString(aVar.j, appRealmObject3.realmGet$appCategory());
        osObjectBuilder.addString(aVar.k, appRealmObject3.realmGet$downloadUrl());
        osObjectBuilder.addString(aVar.l, appRealmObject3.realmGet$micoAction());
        osObjectBuilder.addString(aVar.m, appRealmObject3.realmGet$hardware());
        osObjectBuilder.addString(aVar.n, appRealmObject3.realmGet$channel());
        osObjectBuilder.addString(aVar.o, appRealmObject3.realmGet$MD5());
        osObjectBuilder.addBoolean(aVar.p, Boolean.valueOf(appRealmObject3.realmGet$hidden()));
        osObjectBuilder.addString(aVar.q, appRealmObject3.realmGet$portion());
        osObjectBuilder.addInteger(aVar.r, Long.valueOf(appRealmObject3.realmGet$appKey()));
        osObjectBuilder.addString(aVar.s, appRealmObject3.realmGet$hardwareVersion());
        osObjectBuilder.addInteger(aVar.t, Integer.valueOf(appRealmObject3.realmGet$iconResId()));
        osObjectBuilder.addInteger(aVar.u, Long.valueOf(appRealmObject3.realmGet$sizeCache()));
        osObjectBuilder.addInteger(aVar.v, Long.valueOf(appRealmObject3.realmGet$sizeApp()));
        osObjectBuilder.addInteger(aVar.w, Long.valueOf(appRealmObject3.realmGet$sizeData()));
        osObjectBuilder.addInteger(aVar.x, Integer.valueOf(appRealmObject3.realmGet$position()));
        osObjectBuilder.addString(aVar.y, appRealmObject3.realmGet$modeType());
        osObjectBuilder.updateExistingObject();
        return appRealmObject;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("AppRealmObject = proxy[");
        sb.append("{appName:");
        sb.append(realmGet$appName() != null ? realmGet$appName() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{updateDate:");
        sb.append(realmGet$updateDate() != null ? realmGet$updateDate() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{appType:");
        sb.append(realmGet$appType() != null ? realmGet$appType() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{iconUrl:");
        sb.append(realmGet$iconUrl() != null ? realmGet$iconUrl() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{packageName:");
        sb.append(realmGet$packageName() != null ? realmGet$packageName() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{versionCode:");
        sb.append(realmGet$versionCode());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{versionName:");
        sb.append(realmGet$versionName() != null ? realmGet$versionName() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{onlineStatus:");
        sb.append(realmGet$onlineStatus() != null ? realmGet$onlineStatus() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{removable:");
        sb.append(realmGet$removable());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{appCategory:");
        sb.append(realmGet$appCategory() != null ? realmGet$appCategory() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{downloadUrl:");
        sb.append(realmGet$downloadUrl() != null ? realmGet$downloadUrl() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{micoAction:");
        sb.append(realmGet$micoAction() != null ? realmGet$micoAction() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{hardware:");
        sb.append(realmGet$hardware() != null ? realmGet$hardware() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{channel:");
        sb.append(realmGet$channel() != null ? realmGet$channel() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{MD5:");
        sb.append(realmGet$MD5() != null ? realmGet$MD5() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{hidden:");
        sb.append(realmGet$hidden());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{portion:");
        sb.append(realmGet$portion() != null ? realmGet$portion() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{appKey:");
        sb.append(realmGet$appKey());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{hardwareVersion:");
        sb.append(realmGet$hardwareVersion() != null ? realmGet$hardwareVersion() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{iconResId:");
        sb.append(realmGet$iconResId());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{sizeCache:");
        sb.append(realmGet$sizeCache());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{sizeApp:");
        sb.append(realmGet$sizeApp());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{sizeData:");
        sb.append(realmGet$sizeData());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{position:");
        sb.append(realmGet$position());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{modeType:");
        sb.append(realmGet$modeType() != null ? realmGet$modeType() : "null");
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
        com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy = (com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxy) obj;
        BaseRealm realm$realm = this.c.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy.c.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.c.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy.c.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.c.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_module_homepage_cache_apprealmobjectrealmproxy.c.getRow$realm().getObjectKey();
        }
        return false;
    }
}
