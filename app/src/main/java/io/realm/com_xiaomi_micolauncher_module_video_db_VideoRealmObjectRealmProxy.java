package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.module.video.db.VideoRealmObject;
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
public class com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy extends VideoRealmObject implements com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo a = a();
    private a b;
    private ProxyState<VideoRealmObject> c;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "VideoRealmObject";
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

        a(OsSchemaInfo osSchemaInfo) {
            super(23);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("mediaId", "mediaId", objectSchemaInfo);
            this.b = addColumnDetails("metaId", "metaId", objectSchemaInfo);
            this.c = addColumnDetails("isVip", "isVip", objectSchemaInfo);
            this.d = addColumnDetails("cpType", "cpType", objectSchemaInfo);
            this.e = addColumnDetails("episodesIds", "episodesIds", objectSchemaInfo);
            this.f = addColumnDetails("lastEpisod", "lastEpisod", objectSchemaInfo);
            this.g = addColumnDetails("videoUrl", "videoUrl", objectSchemaInfo);
            this.h = addColumnDetails("title", "title", objectSchemaInfo);
            this.i = addColumnDetails("imageUrl", "imageUrl", objectSchemaInfo);
            this.j = addColumnDetails("horizontalImageUrl", "horizontalImageUrl", objectSchemaInfo);
            this.k = addColumnDetails("category", "category", objectSchemaInfo);
            this.l = addColumnDetails("type", "type", objectSchemaInfo);
            this.m = addColumnDetails("year", "year", objectSchemaInfo);
            this.n = addColumnDetails("playLength", "playLength", objectSchemaInfo);
            this.o = addColumnDetails("setcount", "setcount", objectSchemaInfo);
            this.p = addColumnDetails(SkillStore.SkillDetailSection.TYPE_RATING, SkillStore.SkillDetailSection.TYPE_RATING, objectSchemaInfo);
            this.q = addColumnDetails("directors", "directors", objectSchemaInfo);
            this.r = addColumnDetails("mainActors", "mainActors", objectSchemaInfo);
            this.s = addColumnDetails("actors", "actors", objectSchemaInfo);
            this.t = addColumnDetails("summary", "summary", objectSchemaInfo);
            this.u = addColumnDetails("ci", "ci", objectSchemaInfo);
            this.v = addColumnDetails("lastUpdateTime", "lastUpdateTime", objectSchemaInfo);
            this.w = addColumnDetails("pCode", "pCode", objectSchemaInfo);
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
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy() {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$mediaId() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.a);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$mediaId(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'mediaId' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$metaId() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.b);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$metaId(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public boolean realmGet$isVip() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getBoolean(this.b.c);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$isVip(boolean z) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setBoolean(this.b.c, z);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setBoolean(this.b.c, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$cpType() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.d);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$cpType(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$episodesIds() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.e);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$episodesIds(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$lastEpisod() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.f);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$lastEpisod(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$videoUrl() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.g);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$videoUrl(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$title() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.h);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$title(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$imageUrl() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.i);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$imageUrl(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$horizontalImageUrl() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.j);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$horizontalImageUrl(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$category() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.k);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$category(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$type() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.l);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$type(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$year() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.m);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$year(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.m, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.m, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$playLength() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.n);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$playLength(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.n, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.n, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$setcount() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.o);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$setcount(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.o, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.o, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$rating() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.p);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$rating(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$directors() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.q);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$directors(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$mainActors() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.r);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$mainActors(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.r);
            } else {
                this.c.getRow$realm().setString(this.b.r, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.r, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.r, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$actors() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.s);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$actors(String str) {
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

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$summary() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.t);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$summary(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.t);
            } else {
                this.c.getRow$realm().setString(this.b.t, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.t, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.t, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$ci() {
        this.c.getRealm$realm().checkIfValid();
        return (int) this.c.getRow$realm().getLong(this.b.u);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$ci(int i) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.u, i);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.u, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getLong(this.b.v);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            this.c.getRow$realm().setLong(this.b.v, j);
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            row$realm.getTable().setLong(this.b.v, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$pCode() {
        this.c.getRealm$realm().checkIfValid();
        return this.c.getRow$realm().getString(this.b.w);
    }

    @Override // com.xiaomi.micolauncher.module.video.db.VideoRealmObject, io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$pCode(String str) {
        if (!this.c.isUnderConstruction()) {
            this.c.getRealm$realm().checkIfValid();
            if (str == null) {
                this.c.getRow$realm().setNull(this.b.w);
            } else {
                this.c.getRow$realm().setString(this.b.w, str);
            }
        } else if (this.c.getAcceptDefaultValue$realm()) {
            Row row$realm = this.c.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.b.w, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.b.w, row$realm.getObjectKey(), str, true);
            }
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 23, 0);
        builder.addPersistedProperty("mediaId", RealmFieldType.STRING, true, false, true);
        builder.addPersistedProperty("metaId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("isVip", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("cpType", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("episodesIds", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("lastEpisod", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("videoUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("title", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("imageUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("horizontalImageUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("category", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("type", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("year", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("playLength", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("setcount", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty(SkillStore.SkillDetailSection.TYPE_RATING, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("directors", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("mainActors", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("actors", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("summary", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("ci", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("lastUpdateTime", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("pCode", RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return a;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.module.video.db.VideoRealmObject createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 825
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.module.video.db.VideoRealmObject");
    }

    @TargetApi(11)
    public static VideoRealmObject createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        VideoRealmObject videoRealmObject = new VideoRealmObject();
        VideoRealmObject videoRealmObject2 = videoRealmObject;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("mediaId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$mediaId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$mediaId(null);
                }
                z = true;
            } else if (nextName.equals("metaId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$metaId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$metaId(null);
                }
            } else if (nextName.equals("isVip")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$isVip(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isVip' to null.");
                }
            } else if (nextName.equals("cpType")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$cpType(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$cpType(null);
                }
            } else if (nextName.equals("episodesIds")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$episodesIds(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$episodesIds(null);
                }
            } else if (nextName.equals("lastEpisod")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$lastEpisod(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$lastEpisod(null);
                }
            } else if (nextName.equals("videoUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$videoUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$videoUrl(null);
                }
            } else if (nextName.equals("title")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$title(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$title(null);
                }
            } else if (nextName.equals("imageUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$imageUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$imageUrl(null);
                }
            } else if (nextName.equals("horizontalImageUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$horizontalImageUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$horizontalImageUrl(null);
                }
            } else if (nextName.equals("category")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$category(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$category(null);
                }
            } else if (nextName.equals("type")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$type(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$type(null);
                }
            } else if (nextName.equals("year")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$year(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'year' to null.");
                }
            } else if (nextName.equals("playLength")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$playLength(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'playLength' to null.");
                }
            } else if (nextName.equals("setcount")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$setcount(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'setcount' to null.");
                }
            } else if (nextName.equals(SkillStore.SkillDetailSection.TYPE_RATING)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$rating(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$rating(null);
                }
            } else if (nextName.equals("directors")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$directors(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$directors(null);
                }
            } else if (nextName.equals("mainActors")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$mainActors(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$mainActors(null);
                }
            } else if (nextName.equals("actors")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$actors(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$actors(null);
                }
            } else if (nextName.equals("summary")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$summary(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoRealmObject2.realmSet$summary(null);
                }
            } else if (nextName.equals("ci")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$ci(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ci' to null.");
                }
            } else if (nextName.equals("lastUpdateTime")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoRealmObject2.realmSet$lastUpdateTime(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'lastUpdateTime' to null.");
                }
            } else if (!nextName.equals("pCode")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                videoRealmObject2.realmSet$pCode(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                videoRealmObject2.realmSet$pCode(null);
            }
        }
        jsonReader.endObject();
        if (z) {
            return (VideoRealmObject) realm.copyToRealm((Realm) videoRealmObject, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'mediaId'.");
    }

    private static com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(VideoRealmObject.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy = new com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static VideoRealmObject copyOrUpdate(Realm realm, a aVar, VideoRealmObject videoRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy;
        boolean z2;
        if ((videoRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return videoRealmObject;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(videoRealmObject);
        if (realmObjectProxy2 != null) {
            return (VideoRealmObject) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(VideoRealmObject.class);
            long findFirstString = a2.findFirstString(aVar.a, videoRealmObject.realmGet$mediaId());
            if (findFirstString == -1) {
                z2 = false;
                com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(findFirstString), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy2 = new com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy();
                    map.put(videoRealmObject, com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy = com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy, videoRealmObject, map, set) : copy(realm, aVar, videoRealmObject, z, map, set);
    }

    public static VideoRealmObject copy(Realm realm, a aVar, VideoRealmObject videoRealmObject, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(videoRealmObject);
        if (realmObjectProxy != null) {
            return (VideoRealmObject) realmObjectProxy;
        }
        VideoRealmObject videoRealmObject2 = videoRealmObject;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(VideoRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, videoRealmObject2.realmGet$mediaId());
        osObjectBuilder.addString(aVar.b, videoRealmObject2.realmGet$metaId());
        osObjectBuilder.addBoolean(aVar.c, Boolean.valueOf(videoRealmObject2.realmGet$isVip()));
        osObjectBuilder.addString(aVar.d, videoRealmObject2.realmGet$cpType());
        osObjectBuilder.addString(aVar.e, videoRealmObject2.realmGet$episodesIds());
        osObjectBuilder.addString(aVar.f, videoRealmObject2.realmGet$lastEpisod());
        osObjectBuilder.addString(aVar.g, videoRealmObject2.realmGet$videoUrl());
        osObjectBuilder.addString(aVar.h, videoRealmObject2.realmGet$title());
        osObjectBuilder.addString(aVar.i, videoRealmObject2.realmGet$imageUrl());
        osObjectBuilder.addString(aVar.j, videoRealmObject2.realmGet$horizontalImageUrl());
        osObjectBuilder.addString(aVar.k, videoRealmObject2.realmGet$category());
        osObjectBuilder.addString(aVar.l, videoRealmObject2.realmGet$type());
        osObjectBuilder.addInteger(aVar.m, Integer.valueOf(videoRealmObject2.realmGet$year()));
        osObjectBuilder.addInteger(aVar.n, Integer.valueOf(videoRealmObject2.realmGet$playLength()));
        osObjectBuilder.addInteger(aVar.o, Integer.valueOf(videoRealmObject2.realmGet$setcount()));
        osObjectBuilder.addString(aVar.p, videoRealmObject2.realmGet$rating());
        osObjectBuilder.addString(aVar.q, videoRealmObject2.realmGet$directors());
        osObjectBuilder.addString(aVar.r, videoRealmObject2.realmGet$mainActors());
        osObjectBuilder.addString(aVar.s, videoRealmObject2.realmGet$actors());
        osObjectBuilder.addString(aVar.t, videoRealmObject2.realmGet$summary());
        osObjectBuilder.addInteger(aVar.u, Integer.valueOf(videoRealmObject2.realmGet$ci()));
        osObjectBuilder.addInteger(aVar.v, Long.valueOf(videoRealmObject2.realmGet$lastUpdateTime()));
        osObjectBuilder.addString(aVar.w, videoRealmObject2.realmGet$pCode());
        com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(videoRealmObject, a2);
        return a2;
    }

    public static long insert(Realm realm, VideoRealmObject videoRealmObject, Map<RealmModel, Long> map) {
        long j;
        if ((videoRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(VideoRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoRealmObject.class);
        long j2 = aVar.a;
        VideoRealmObject videoRealmObject2 = videoRealmObject;
        String realmGet$mediaId = videoRealmObject2.realmGet$mediaId();
        long nativeFindFirstString = realmGet$mediaId != null ? Table.nativeFindFirstString(nativePtr, j2, realmGet$mediaId) : -1L;
        if (nativeFindFirstString == -1) {
            j = OsObject.createRowWithPrimaryKey(a2, j2, realmGet$mediaId);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$mediaId);
            j = nativeFindFirstString;
        }
        map.put(videoRealmObject, Long.valueOf(j));
        String realmGet$metaId = videoRealmObject2.realmGet$metaId();
        if (realmGet$metaId != null) {
            Table.nativeSetString(nativePtr, aVar.b, j, realmGet$metaId, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.c, j, videoRealmObject2.realmGet$isVip(), false);
        String realmGet$cpType = videoRealmObject2.realmGet$cpType();
        if (realmGet$cpType != null) {
            Table.nativeSetString(nativePtr, aVar.d, j, realmGet$cpType, false);
        }
        String realmGet$episodesIds = videoRealmObject2.realmGet$episodesIds();
        if (realmGet$episodesIds != null) {
            Table.nativeSetString(nativePtr, aVar.e, j, realmGet$episodesIds, false);
        }
        String realmGet$lastEpisod = videoRealmObject2.realmGet$lastEpisod();
        if (realmGet$lastEpisod != null) {
            Table.nativeSetString(nativePtr, aVar.f, j, realmGet$lastEpisod, false);
        }
        String realmGet$videoUrl = videoRealmObject2.realmGet$videoUrl();
        if (realmGet$videoUrl != null) {
            Table.nativeSetString(nativePtr, aVar.g, j, realmGet$videoUrl, false);
        }
        String realmGet$title = videoRealmObject2.realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(nativePtr, aVar.h, j, realmGet$title, false);
        }
        String realmGet$imageUrl = videoRealmObject2.realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(nativePtr, aVar.i, j, realmGet$imageUrl, false);
        }
        String realmGet$horizontalImageUrl = videoRealmObject2.realmGet$horizontalImageUrl();
        if (realmGet$horizontalImageUrl != null) {
            Table.nativeSetString(nativePtr, aVar.j, j, realmGet$horizontalImageUrl, false);
        }
        String realmGet$category = videoRealmObject2.realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(nativePtr, aVar.k, j, realmGet$category, false);
        }
        String realmGet$type = videoRealmObject2.realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(nativePtr, aVar.l, j, realmGet$type, false);
        }
        Table.nativeSetLong(nativePtr, aVar.m, j, videoRealmObject2.realmGet$year(), false);
        Table.nativeSetLong(nativePtr, aVar.n, j, videoRealmObject2.realmGet$playLength(), false);
        Table.nativeSetLong(nativePtr, aVar.o, j, videoRealmObject2.realmGet$setcount(), false);
        String realmGet$rating = videoRealmObject2.realmGet$rating();
        if (realmGet$rating != null) {
            Table.nativeSetString(nativePtr, aVar.p, j, realmGet$rating, false);
        }
        String realmGet$directors = videoRealmObject2.realmGet$directors();
        if (realmGet$directors != null) {
            Table.nativeSetString(nativePtr, aVar.q, j, realmGet$directors, false);
        }
        String realmGet$mainActors = videoRealmObject2.realmGet$mainActors();
        if (realmGet$mainActors != null) {
            Table.nativeSetString(nativePtr, aVar.r, j, realmGet$mainActors, false);
        }
        String realmGet$actors = videoRealmObject2.realmGet$actors();
        if (realmGet$actors != null) {
            Table.nativeSetString(nativePtr, aVar.s, j, realmGet$actors, false);
        }
        String realmGet$summary = videoRealmObject2.realmGet$summary();
        if (realmGet$summary != null) {
            Table.nativeSetString(nativePtr, aVar.t, j, realmGet$summary, false);
        }
        Table.nativeSetLong(nativePtr, aVar.u, j, videoRealmObject2.realmGet$ci(), false);
        Table.nativeSetLong(nativePtr, aVar.v, j, videoRealmObject2.realmGet$lastUpdateTime(), false);
        String realmGet$pCode = videoRealmObject2.realmGet$pCode();
        if (realmGet$pCode != null) {
            Table.nativeSetString(nativePtr, aVar.w, j, realmGet$pCode, false);
        }
        return j;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(VideoRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoRealmObject.class);
        long j2 = aVar.a;
        while (it.hasNext()) {
            VideoRealmObject videoRealmObject = (VideoRealmObject) it.next();
            if (!map.containsKey(videoRealmObject)) {
                if ((videoRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(videoRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                VideoRealmObject videoRealmObject2 = videoRealmObject;
                String realmGet$mediaId = videoRealmObject2.realmGet$mediaId();
                long nativeFindFirstString = realmGet$mediaId != null ? Table.nativeFindFirstString(nativePtr, j2, realmGet$mediaId) : -1L;
                if (nativeFindFirstString == -1) {
                    j = OsObject.createRowWithPrimaryKey(a2, j2, realmGet$mediaId);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$mediaId);
                    j = nativeFindFirstString;
                }
                map.put(videoRealmObject, Long.valueOf(j));
                String realmGet$metaId = videoRealmObject2.realmGet$metaId();
                if (realmGet$metaId != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.b, j, realmGet$metaId, false);
                } else {
                    j2 = j2;
                }
                Table.nativeSetBoolean(nativePtr, aVar.c, j, videoRealmObject2.realmGet$isVip(), false);
                String realmGet$cpType = videoRealmObject2.realmGet$cpType();
                if (realmGet$cpType != null) {
                    Table.nativeSetString(nativePtr, aVar.d, j, realmGet$cpType, false);
                }
                String realmGet$episodesIds = videoRealmObject2.realmGet$episodesIds();
                if (realmGet$episodesIds != null) {
                    Table.nativeSetString(nativePtr, aVar.e, j, realmGet$episodesIds, false);
                }
                String realmGet$lastEpisod = videoRealmObject2.realmGet$lastEpisod();
                if (realmGet$lastEpisod != null) {
                    Table.nativeSetString(nativePtr, aVar.f, j, realmGet$lastEpisod, false);
                }
                String realmGet$videoUrl = videoRealmObject2.realmGet$videoUrl();
                if (realmGet$videoUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.g, j, realmGet$videoUrl, false);
                }
                String realmGet$title = videoRealmObject2.realmGet$title();
                if (realmGet$title != null) {
                    Table.nativeSetString(nativePtr, aVar.h, j, realmGet$title, false);
                }
                String realmGet$imageUrl = videoRealmObject2.realmGet$imageUrl();
                if (realmGet$imageUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.i, j, realmGet$imageUrl, false);
                }
                String realmGet$horizontalImageUrl = videoRealmObject2.realmGet$horizontalImageUrl();
                if (realmGet$horizontalImageUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.j, j, realmGet$horizontalImageUrl, false);
                }
                String realmGet$category = videoRealmObject2.realmGet$category();
                if (realmGet$category != null) {
                    Table.nativeSetString(nativePtr, aVar.k, j, realmGet$category, false);
                }
                String realmGet$type = videoRealmObject2.realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(nativePtr, aVar.l, j, realmGet$type, false);
                }
                Table.nativeSetLong(nativePtr, aVar.m, j, videoRealmObject2.realmGet$year(), false);
                Table.nativeSetLong(nativePtr, aVar.n, j, videoRealmObject2.realmGet$playLength(), false);
                Table.nativeSetLong(nativePtr, aVar.o, j, videoRealmObject2.realmGet$setcount(), false);
                String realmGet$rating = videoRealmObject2.realmGet$rating();
                if (realmGet$rating != null) {
                    Table.nativeSetString(nativePtr, aVar.p, j, realmGet$rating, false);
                }
                String realmGet$directors = videoRealmObject2.realmGet$directors();
                if (realmGet$directors != null) {
                    Table.nativeSetString(nativePtr, aVar.q, j, realmGet$directors, false);
                }
                String realmGet$mainActors = videoRealmObject2.realmGet$mainActors();
                if (realmGet$mainActors != null) {
                    Table.nativeSetString(nativePtr, aVar.r, j, realmGet$mainActors, false);
                }
                String realmGet$actors = videoRealmObject2.realmGet$actors();
                if (realmGet$actors != null) {
                    Table.nativeSetString(nativePtr, aVar.s, j, realmGet$actors, false);
                }
                String realmGet$summary = videoRealmObject2.realmGet$summary();
                if (realmGet$summary != null) {
                    Table.nativeSetString(nativePtr, aVar.t, j, realmGet$summary, false);
                }
                Table.nativeSetLong(nativePtr, aVar.u, j, videoRealmObject2.realmGet$ci(), false);
                Table.nativeSetLong(nativePtr, aVar.v, j, videoRealmObject2.realmGet$lastUpdateTime(), false);
                String realmGet$pCode = videoRealmObject2.realmGet$pCode();
                if (realmGet$pCode != null) {
                    Table.nativeSetString(nativePtr, aVar.w, j, realmGet$pCode, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, VideoRealmObject videoRealmObject, Map<RealmModel, Long> map) {
        if ((videoRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoRealmObject)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoRealmObject;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(VideoRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoRealmObject.class);
        long j = aVar.a;
        VideoRealmObject videoRealmObject2 = videoRealmObject;
        String realmGet$mediaId = videoRealmObject2.realmGet$mediaId();
        long nativeFindFirstString = realmGet$mediaId != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$mediaId) : -1L;
        long createRowWithPrimaryKey = nativeFindFirstString == -1 ? OsObject.createRowWithPrimaryKey(a2, j, realmGet$mediaId) : nativeFindFirstString;
        map.put(videoRealmObject, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$metaId = videoRealmObject2.realmGet$metaId();
        if (realmGet$metaId != null) {
            Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$metaId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.c, createRowWithPrimaryKey, videoRealmObject2.realmGet$isVip(), false);
        String realmGet$cpType = videoRealmObject2.realmGet$cpType();
        if (realmGet$cpType != null) {
            Table.nativeSetString(nativePtr, aVar.d, createRowWithPrimaryKey, realmGet$cpType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.d, createRowWithPrimaryKey, false);
        }
        String realmGet$episodesIds = videoRealmObject2.realmGet$episodesIds();
        if (realmGet$episodesIds != null) {
            Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$episodesIds, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
        }
        String realmGet$lastEpisod = videoRealmObject2.realmGet$lastEpisod();
        if (realmGet$lastEpisod != null) {
            Table.nativeSetString(nativePtr, aVar.f, createRowWithPrimaryKey, realmGet$lastEpisod, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.f, createRowWithPrimaryKey, false);
        }
        String realmGet$videoUrl = videoRealmObject2.realmGet$videoUrl();
        if (realmGet$videoUrl != null) {
            Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$videoUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
        }
        String realmGet$title = videoRealmObject2.realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$title, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
        }
        String realmGet$imageUrl = videoRealmObject2.realmGet$imageUrl();
        if (realmGet$imageUrl != null) {
            Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$imageUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
        }
        String realmGet$horizontalImageUrl = videoRealmObject2.realmGet$horizontalImageUrl();
        if (realmGet$horizontalImageUrl != null) {
            Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$horizontalImageUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
        }
        String realmGet$category = videoRealmObject2.realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$category, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
        }
        String realmGet$type = videoRealmObject2.realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$type, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.m, createRowWithPrimaryKey, videoRealmObject2.realmGet$year(), false);
        Table.nativeSetLong(nativePtr, aVar.n, createRowWithPrimaryKey, videoRealmObject2.realmGet$playLength(), false);
        Table.nativeSetLong(nativePtr, aVar.o, createRowWithPrimaryKey, videoRealmObject2.realmGet$setcount(), false);
        String realmGet$rating = videoRealmObject2.realmGet$rating();
        if (realmGet$rating != null) {
            Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$rating, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
        }
        String realmGet$directors = videoRealmObject2.realmGet$directors();
        if (realmGet$directors != null) {
            Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$directors, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
        }
        String realmGet$mainActors = videoRealmObject2.realmGet$mainActors();
        if (realmGet$mainActors != null) {
            Table.nativeSetString(nativePtr, aVar.r, createRowWithPrimaryKey, realmGet$mainActors, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.r, createRowWithPrimaryKey, false);
        }
        String realmGet$actors = videoRealmObject2.realmGet$actors();
        if (realmGet$actors != null) {
            Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$actors, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
        }
        String realmGet$summary = videoRealmObject2.realmGet$summary();
        if (realmGet$summary != null) {
            Table.nativeSetString(nativePtr, aVar.t, createRowWithPrimaryKey, realmGet$summary, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.t, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.u, createRowWithPrimaryKey, videoRealmObject2.realmGet$ci(), false);
        Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, videoRealmObject2.realmGet$lastUpdateTime(), false);
        String realmGet$pCode = videoRealmObject2.realmGet$pCode();
        if (realmGet$pCode != null) {
            Table.nativeSetString(nativePtr, aVar.w, createRowWithPrimaryKey, realmGet$pCode, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.w, createRowWithPrimaryKey, false);
        }
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table a2 = realm.a(VideoRealmObject.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoRealmObject.class);
        long j = aVar.a;
        while (it.hasNext()) {
            VideoRealmObject videoRealmObject = (VideoRealmObject) it.next();
            if (!map.containsKey(videoRealmObject)) {
                if ((videoRealmObject instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoRealmObject)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoRealmObject;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(videoRealmObject, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                VideoRealmObject videoRealmObject2 = videoRealmObject;
                String realmGet$mediaId = videoRealmObject2.realmGet$mediaId();
                long nativeFindFirstString = realmGet$mediaId != null ? Table.nativeFindFirstString(nativePtr, j, realmGet$mediaId) : -1L;
                long createRowWithPrimaryKey = nativeFindFirstString == -1 ? OsObject.createRowWithPrimaryKey(a2, j, realmGet$mediaId) : nativeFindFirstString;
                map.put(videoRealmObject, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$metaId = videoRealmObject2.realmGet$metaId();
                if (realmGet$metaId != null) {
                    j = j;
                    Table.nativeSetString(nativePtr, aVar.b, createRowWithPrimaryKey, realmGet$metaId, false);
                } else {
                    j = j;
                    Table.nativeSetNull(nativePtr, aVar.b, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.c, createRowWithPrimaryKey, videoRealmObject2.realmGet$isVip(), false);
                String realmGet$cpType = videoRealmObject2.realmGet$cpType();
                if (realmGet$cpType != null) {
                    Table.nativeSetString(nativePtr, aVar.d, createRowWithPrimaryKey, realmGet$cpType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.d, createRowWithPrimaryKey, false);
                }
                String realmGet$episodesIds = videoRealmObject2.realmGet$episodesIds();
                if (realmGet$episodesIds != null) {
                    Table.nativeSetString(nativePtr, aVar.e, createRowWithPrimaryKey, realmGet$episodesIds, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.e, createRowWithPrimaryKey, false);
                }
                String realmGet$lastEpisod = videoRealmObject2.realmGet$lastEpisod();
                if (realmGet$lastEpisod != null) {
                    Table.nativeSetString(nativePtr, aVar.f, createRowWithPrimaryKey, realmGet$lastEpisod, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.f, createRowWithPrimaryKey, false);
                }
                String realmGet$videoUrl = videoRealmObject2.realmGet$videoUrl();
                if (realmGet$videoUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.g, createRowWithPrimaryKey, realmGet$videoUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.g, createRowWithPrimaryKey, false);
                }
                String realmGet$title = videoRealmObject2.realmGet$title();
                if (realmGet$title != null) {
                    Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$title, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
                }
                String realmGet$imageUrl = videoRealmObject2.realmGet$imageUrl();
                if (realmGet$imageUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$imageUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
                }
                String realmGet$horizontalImageUrl = videoRealmObject2.realmGet$horizontalImageUrl();
                if (realmGet$horizontalImageUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.j, createRowWithPrimaryKey, realmGet$horizontalImageUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.j, createRowWithPrimaryKey, false);
                }
                String realmGet$category = videoRealmObject2.realmGet$category();
                if (realmGet$category != null) {
                    Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$category, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
                }
                String realmGet$type = videoRealmObject2.realmGet$type();
                if (realmGet$type != null) {
                    Table.nativeSetString(nativePtr, aVar.l, createRowWithPrimaryKey, realmGet$type, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.l, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.m, createRowWithPrimaryKey, videoRealmObject2.realmGet$year(), false);
                Table.nativeSetLong(nativePtr, aVar.n, createRowWithPrimaryKey, videoRealmObject2.realmGet$playLength(), false);
                Table.nativeSetLong(nativePtr, aVar.o, createRowWithPrimaryKey, videoRealmObject2.realmGet$setcount(), false);
                String realmGet$rating = videoRealmObject2.realmGet$rating();
                if (realmGet$rating != null) {
                    Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$rating, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
                }
                String realmGet$directors = videoRealmObject2.realmGet$directors();
                if (realmGet$directors != null) {
                    Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$directors, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
                }
                String realmGet$mainActors = videoRealmObject2.realmGet$mainActors();
                if (realmGet$mainActors != null) {
                    Table.nativeSetString(nativePtr, aVar.r, createRowWithPrimaryKey, realmGet$mainActors, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.r, createRowWithPrimaryKey, false);
                }
                String realmGet$actors = videoRealmObject2.realmGet$actors();
                if (realmGet$actors != null) {
                    Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$actors, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
                }
                String realmGet$summary = videoRealmObject2.realmGet$summary();
                if (realmGet$summary != null) {
                    Table.nativeSetString(nativePtr, aVar.t, createRowWithPrimaryKey, realmGet$summary, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.t, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.u, createRowWithPrimaryKey, videoRealmObject2.realmGet$ci(), false);
                Table.nativeSetLong(nativePtr, aVar.v, createRowWithPrimaryKey, videoRealmObject2.realmGet$lastUpdateTime(), false);
                String realmGet$pCode = videoRealmObject2.realmGet$pCode();
                if (realmGet$pCode != null) {
                    Table.nativeSetString(nativePtr, aVar.w, createRowWithPrimaryKey, realmGet$pCode, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.w, createRowWithPrimaryKey, false);
                }
            }
        }
    }

    public static VideoRealmObject createDetachedCopy(VideoRealmObject videoRealmObject, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        VideoRealmObject videoRealmObject2;
        if (i > i2 || videoRealmObject == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(videoRealmObject);
        if (cacheData == null) {
            videoRealmObject2 = new VideoRealmObject();
            map.put(videoRealmObject, new RealmObjectProxy.CacheData<>(i, videoRealmObject2));
        } else if (i >= cacheData.minDepth) {
            return (VideoRealmObject) cacheData.object;
        } else {
            videoRealmObject2 = (VideoRealmObject) cacheData.object;
            cacheData.minDepth = i;
        }
        VideoRealmObject videoRealmObject3 = videoRealmObject2;
        VideoRealmObject videoRealmObject4 = videoRealmObject;
        videoRealmObject3.realmSet$mediaId(videoRealmObject4.realmGet$mediaId());
        videoRealmObject3.realmSet$metaId(videoRealmObject4.realmGet$metaId());
        videoRealmObject3.realmSet$isVip(videoRealmObject4.realmGet$isVip());
        videoRealmObject3.realmSet$cpType(videoRealmObject4.realmGet$cpType());
        videoRealmObject3.realmSet$episodesIds(videoRealmObject4.realmGet$episodesIds());
        videoRealmObject3.realmSet$lastEpisod(videoRealmObject4.realmGet$lastEpisod());
        videoRealmObject3.realmSet$videoUrl(videoRealmObject4.realmGet$videoUrl());
        videoRealmObject3.realmSet$title(videoRealmObject4.realmGet$title());
        videoRealmObject3.realmSet$imageUrl(videoRealmObject4.realmGet$imageUrl());
        videoRealmObject3.realmSet$horizontalImageUrl(videoRealmObject4.realmGet$horizontalImageUrl());
        videoRealmObject3.realmSet$category(videoRealmObject4.realmGet$category());
        videoRealmObject3.realmSet$type(videoRealmObject4.realmGet$type());
        videoRealmObject3.realmSet$year(videoRealmObject4.realmGet$year());
        videoRealmObject3.realmSet$playLength(videoRealmObject4.realmGet$playLength());
        videoRealmObject3.realmSet$setcount(videoRealmObject4.realmGet$setcount());
        videoRealmObject3.realmSet$rating(videoRealmObject4.realmGet$rating());
        videoRealmObject3.realmSet$directors(videoRealmObject4.realmGet$directors());
        videoRealmObject3.realmSet$mainActors(videoRealmObject4.realmGet$mainActors());
        videoRealmObject3.realmSet$actors(videoRealmObject4.realmGet$actors());
        videoRealmObject3.realmSet$summary(videoRealmObject4.realmGet$summary());
        videoRealmObject3.realmSet$ci(videoRealmObject4.realmGet$ci());
        videoRealmObject3.realmSet$lastUpdateTime(videoRealmObject4.realmGet$lastUpdateTime());
        videoRealmObject3.realmSet$pCode(videoRealmObject4.realmGet$pCode());
        return videoRealmObject2;
    }

    static VideoRealmObject a(Realm realm, a aVar, VideoRealmObject videoRealmObject, VideoRealmObject videoRealmObject2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        VideoRealmObject videoRealmObject3 = videoRealmObject2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(VideoRealmObject.class), set);
        osObjectBuilder.addString(aVar.a, videoRealmObject3.realmGet$mediaId());
        osObjectBuilder.addString(aVar.b, videoRealmObject3.realmGet$metaId());
        osObjectBuilder.addBoolean(aVar.c, Boolean.valueOf(videoRealmObject3.realmGet$isVip()));
        osObjectBuilder.addString(aVar.d, videoRealmObject3.realmGet$cpType());
        osObjectBuilder.addString(aVar.e, videoRealmObject3.realmGet$episodesIds());
        osObjectBuilder.addString(aVar.f, videoRealmObject3.realmGet$lastEpisod());
        osObjectBuilder.addString(aVar.g, videoRealmObject3.realmGet$videoUrl());
        osObjectBuilder.addString(aVar.h, videoRealmObject3.realmGet$title());
        osObjectBuilder.addString(aVar.i, videoRealmObject3.realmGet$imageUrl());
        osObjectBuilder.addString(aVar.j, videoRealmObject3.realmGet$horizontalImageUrl());
        osObjectBuilder.addString(aVar.k, videoRealmObject3.realmGet$category());
        osObjectBuilder.addString(aVar.l, videoRealmObject3.realmGet$type());
        osObjectBuilder.addInteger(aVar.m, Integer.valueOf(videoRealmObject3.realmGet$year()));
        osObjectBuilder.addInteger(aVar.n, Integer.valueOf(videoRealmObject3.realmGet$playLength()));
        osObjectBuilder.addInteger(aVar.o, Integer.valueOf(videoRealmObject3.realmGet$setcount()));
        osObjectBuilder.addString(aVar.p, videoRealmObject3.realmGet$rating());
        osObjectBuilder.addString(aVar.q, videoRealmObject3.realmGet$directors());
        osObjectBuilder.addString(aVar.r, videoRealmObject3.realmGet$mainActors());
        osObjectBuilder.addString(aVar.s, videoRealmObject3.realmGet$actors());
        osObjectBuilder.addString(aVar.t, videoRealmObject3.realmGet$summary());
        osObjectBuilder.addInteger(aVar.u, Integer.valueOf(videoRealmObject3.realmGet$ci()));
        osObjectBuilder.addInteger(aVar.v, Long.valueOf(videoRealmObject3.realmGet$lastUpdateTime()));
        osObjectBuilder.addString(aVar.w, videoRealmObject3.realmGet$pCode());
        osObjectBuilder.updateExistingObject();
        return videoRealmObject;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("VideoRealmObject = proxy[");
        sb.append("{mediaId:");
        sb.append(realmGet$mediaId());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{metaId:");
        sb.append(realmGet$metaId() != null ? realmGet$metaId() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{isVip:");
        sb.append(realmGet$isVip());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{cpType:");
        sb.append(realmGet$cpType() != null ? realmGet$cpType() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{episodesIds:");
        sb.append(realmGet$episodesIds() != null ? realmGet$episodesIds() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{lastEpisod:");
        sb.append(realmGet$lastEpisod() != null ? realmGet$lastEpisod() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{videoUrl:");
        sb.append(realmGet$videoUrl() != null ? realmGet$videoUrl() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{title:");
        sb.append(realmGet$title() != null ? realmGet$title() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{imageUrl:");
        sb.append(realmGet$imageUrl() != null ? realmGet$imageUrl() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{horizontalImageUrl:");
        sb.append(realmGet$horizontalImageUrl() != null ? realmGet$horizontalImageUrl() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{category:");
        sb.append(realmGet$category() != null ? realmGet$category() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{type:");
        sb.append(realmGet$type() != null ? realmGet$type() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{year:");
        sb.append(realmGet$year());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{playLength:");
        sb.append(realmGet$playLength());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{setcount:");
        sb.append(realmGet$setcount());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{rating:");
        sb.append(realmGet$rating() != null ? realmGet$rating() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{directors:");
        sb.append(realmGet$directors() != null ? realmGet$directors() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{mainActors:");
        sb.append(realmGet$mainActors() != null ? realmGet$mainActors() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{actors:");
        sb.append(realmGet$actors() != null ? realmGet$actors() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{summary:");
        sb.append(realmGet$summary() != null ? realmGet$summary() : "null");
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{ci:");
        sb.append(realmGet$ci());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{lastUpdateTime:");
        sb.append(realmGet$lastUpdateTime());
        sb.append("}");
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append("{pCode:");
        sb.append(realmGet$pCode() != null ? realmGet$pCode() : "null");
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
        com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy = (com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxy) obj;
        BaseRealm realm$realm = this.c.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy.c.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.c.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy.c.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.c.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_module_video_db_videorealmobjectrealmproxy.c.getRow$realm().getObjectKey();
        }
        return false;
    }
}
