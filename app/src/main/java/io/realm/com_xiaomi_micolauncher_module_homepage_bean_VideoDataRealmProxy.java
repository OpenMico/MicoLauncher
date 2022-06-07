package io.realm;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
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
import org.hapjs.features.channel.IChannel;

/* loaded from: classes5.dex */
public class com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy extends VideoData implements com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface, RealmObjectProxy {
    private static final OsObjectSchemaInfo b = a();
    private a c;
    private ProxyState<VideoData> d;

    /* loaded from: classes5.dex */
    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "VideoData";
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
        long J;
        long K;
        long L;
        long M;
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
            super(39);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.a = addColumnDetails("id", "id", objectSchemaInfo);
            this.b = addColumnDetails("name", "name", objectSchemaInfo);
            this.c = addColumnDetails("category", "category", objectSchemaInfo);
            this.d = addColumnDetails(SkillStore.SkillDetailSection.TYPE_RATING, SkillStore.SkillDetailSection.TYPE_RATING, objectSchemaInfo);
            this.e = addColumnDetails("latestEpisode", "latestEpisode", objectSchemaInfo);
            this.f = addColumnDetails("totalEpisode", "totalEpisode", objectSchemaInfo);
            this.g = addColumnDetails("year", "year", objectSchemaInfo);
            this.h = addColumnDetails("area", "area", objectSchemaInfo);
            this.i = addColumnDetails(MimeTypes.BASE_TYPE_IMAGE, MimeTypes.BASE_TYPE_IMAGE, objectSchemaInfo);
            this.j = addColumnDetails("season", "season", objectSchemaInfo);
            this.k = addColumnDetails("mainActor", "mainActor", objectSchemaInfo);
            this.l = addColumnDetails("isShort", "isShort", objectSchemaInfo);
            this.m = addColumnDetails(TraceConstants.Result.CP, TraceConstants.Result.CP, objectSchemaInfo);
            this.n = addColumnDetails("cpId", "cpId", objectSchemaInfo);
            this.o = addColumnDetails("idV2", "idV2", objectSchemaInfo);
            this.p = addColumnDetails("horizontalImgUrl", "horizontalImgUrl", objectSchemaInfo);
            this.q = addColumnDetails(IChannel.EXTRA_ERROR_DESC, IChannel.EXTRA_ERROR_DESC, objectSchemaInfo);
            this.r = addColumnDetails("verticalImgUrl", "verticalImgUrl", objectSchemaInfo);
            this.s = addColumnDetails("source", "source", objectSchemaInfo);
            this.t = addColumnDetails("subCategory", "subCategory", objectSchemaInfo);
            this.u = addColumnDetails("webUrl", "webUrl", objectSchemaInfo);
            this.v = addColumnDetails("audioId", "audioId", objectSchemaInfo);
            this.w = addColumnDetails("resourceId", "resourceId", objectSchemaInfo);
            this.x = addColumnDetails("mediaType", "mediaType", objectSchemaInfo);
            this.y = addColumnDetails("from", "from", objectSchemaInfo);
            this.z = addColumnDetails("isHotPlay", "isHotPlay", objectSchemaInfo);
            this.A = addColumnDetails("style", "style", objectSchemaInfo);
            this.B = addColumnDetails("isVip", "isVip", objectSchemaInfo);
            this.C = addColumnDetails("showStatus", "showStatus", objectSchemaInfo);
            this.D = addColumnDetails("trackKey", "trackKey", objectSchemaInfo);
            this.E = addColumnDetails("duration", "duration", objectSchemaInfo);
            this.F = addColumnDetails("lastUpdateTime", "lastUpdateTime", objectSchemaInfo);
            this.G = addColumnDetails("text", "text", objectSchemaInfo);
            this.H = addColumnDetails("traceId", "traceId", objectSchemaInfo);
            this.I = addColumnDetails("expId", "expId", objectSchemaInfo);
            this.J = addColumnDetails("adInfoJson", "adInfoJson", objectSchemaInfo);
            this.K = addColumnDetails("titleText", "titleText", objectSchemaInfo);
            this.L = addColumnDetails("ratingText", "ratingText", objectSchemaInfo);
            this.M = addColumnDetails("pCode", "pCode", objectSchemaInfo);
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
            aVar2.J = aVar.J;
            aVar2.K = aVar.K;
            aVar2.L = aVar.L;
            aVar2.M = aVar.M;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy() {
        this.d.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.d == null) {
            BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
            this.c = (a) realmObjectContext.getColumnInfo();
            this.d = new ProxyState<>(this);
            this.d.setRealm$realm(realmObjectContext.a());
            this.d.setRow$realm(realmObjectContext.getRow());
            this.d.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
            this.d.setExcludeFields$realm(realmObjectContext.getExcludeFields());
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$id() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.a);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$id(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.a);
            } else {
                this.d.getRow$realm().setString(this.c.a, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.a, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.a, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$name() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.b);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$name(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            throw new RealmException("Primary key field 'name' cannot be changed after object was created.");
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$category() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.c);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$category(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.c);
            } else {
                this.d.getRow$realm().setString(this.c.c, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.c, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.c, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public double realmGet$rating() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getDouble(this.c.d);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$rating(double d) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setDouble(this.c.d, d);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setDouble(this.c.d, row$realm.getObjectKey(), d, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$latestEpisode() {
        this.d.getRealm$realm().checkIfValid();
        return (int) this.d.getRow$realm().getLong(this.c.e);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$latestEpisode(int i) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.e, i);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.e, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$totalEpisode() {
        this.d.getRealm$realm().checkIfValid();
        return (int) this.d.getRow$realm().getLong(this.c.f);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$totalEpisode(int i) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.f, i);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.f, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$year() {
        this.d.getRealm$realm().checkIfValid();
        return (int) this.d.getRow$realm().getLong(this.c.g);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$year(int i) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.g, i);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.g, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$area() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.h);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$area(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.h);
            } else {
                this.d.getRow$realm().setString(this.c.h, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.h, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.h, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$image() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$image(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.i);
            } else {
                this.d.getRow$realm().setString(this.c.i, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.i, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.i, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$season() {
        this.d.getRealm$realm().checkIfValid();
        return (int) this.d.getRow$realm().getLong(this.c.j);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$season(int i) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.j, i);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.j, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$mainActor() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.k);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$mainActor(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.k);
            } else {
                this.d.getRow$realm().setString(this.c.k, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.k, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.k, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isShort() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getBoolean(this.c.l);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isShort(boolean z) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setBoolean(this.c.l, z);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setBoolean(this.c.l, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$cp() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.m);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$cp(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.m);
            } else {
                this.d.getRow$realm().setString(this.c.m, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.m, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.m, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$cpId() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.n);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$cpId(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.n);
            } else {
                this.d.getRow$realm().setString(this.c.n, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.n, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.n, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$idV2() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.o);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$idV2(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.o);
            } else {
                this.d.getRow$realm().setString(this.c.o, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.o, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.o, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$horizontalImgUrl() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.p);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$horizontalImgUrl(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.p);
            } else {
                this.d.getRow$realm().setString(this.c.p, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.p, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.p, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$desc() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.q);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$desc(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.q);
            } else {
                this.d.getRow$realm().setString(this.c.q, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.q, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.q, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$verticalImgUrl() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.r);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$verticalImgUrl(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.r);
            } else {
                this.d.getRow$realm().setString(this.c.r, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.r, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.r, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$source() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.s);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$source(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.s);
            } else {
                this.d.getRow$realm().setString(this.c.s, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.s, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.s, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$subCategory() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.t);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$subCategory(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.t);
            } else {
                this.d.getRow$realm().setString(this.c.t, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.t, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.t, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$webUrl() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.u);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$webUrl(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.u);
            } else {
                this.d.getRow$realm().setString(this.c.u, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.u, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.u, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$audioId() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.v);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$audioId(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.v);
            } else {
                this.d.getRow$realm().setString(this.c.v, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.v, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.v, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$resourceId() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.w);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$resourceId(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.w);
            } else {
                this.d.getRow$realm().setString(this.c.w, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.w, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.w, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$mediaType() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.x);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$mediaType(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.x);
            } else {
                this.d.getRow$realm().setString(this.c.x, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.x, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.x, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$from() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.y);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$from(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.y);
            } else {
                this.d.getRow$realm().setString(this.c.y, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.y, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.y, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isHotPlay() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getBoolean(this.c.z);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isHotPlay(boolean z) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setBoolean(this.c.z, z);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setBoolean(this.c.z, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$style() {
        this.d.getRealm$realm().checkIfValid();
        return (int) this.d.getRow$realm().getLong(this.c.A);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$style(int i) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.A, i);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.A, row$realm.getObjectKey(), i, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isVip() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getBoolean(this.c.B);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isVip(boolean z) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setBoolean(this.c.B, z);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setBoolean(this.c.B, row$realm.getObjectKey(), z, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$showStatus() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.C);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$showStatus(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.C);
            } else {
                this.d.getRow$realm().setString(this.c.C, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.C, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.C, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$trackKey() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.D);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$trackKey(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.D);
            } else {
                this.d.getRow$realm().setString(this.c.D, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.D, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.D, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public long realmGet$duration() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getLong(this.c.E);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$duration(long j) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.E, j);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.E, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getLong(this.c.F);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            this.d.getRow$realm().setLong(this.c.F, j);
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            row$realm.getTable().setLong(this.c.F, row$realm.getObjectKey(), j, true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$text() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.G);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$text(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.G);
            } else {
                this.d.getRow$realm().setString(this.c.G, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.G, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.G, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$traceId() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.H);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$traceId(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.H);
            } else {
                this.d.getRow$realm().setString(this.c.H, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.H, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.H, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$expId() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.I);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$expId(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.I);
            } else {
                this.d.getRow$realm().setString(this.c.I, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.I, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.I, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$adInfoJson() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.J);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$adInfoJson(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.J);
            } else {
                this.d.getRow$realm().setString(this.c.J, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.J, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.J, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$titleText() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.K);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$titleText(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.K);
            } else {
                this.d.getRow$realm().setString(this.c.K, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.K, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.K, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$ratingText() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.L);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$ratingText(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.L);
            } else {
                this.d.getRow$realm().setString(this.c.L, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.L, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.L, row$realm.getObjectKey(), str, true);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$pCode() {
        this.d.getRealm$realm().checkIfValid();
        return this.d.getRow$realm().getString(this.c.M);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.VideoData, io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$pCode(String str) {
        if (!this.d.isUnderConstruction()) {
            this.d.getRealm$realm().checkIfValid();
            if (str == null) {
                this.d.getRow$realm().setNull(this.c.M);
            } else {
                this.d.getRow$realm().setString(this.c.M, str);
            }
        } else if (this.d.getAcceptDefaultValue$realm()) {
            Row row$realm = this.d.getRow$realm();
            if (str == null) {
                row$realm.getTable().setNull(this.c.M, row$realm.getObjectKey(), true);
            } else {
                row$realm.getTable().setString(this.c.M, row$realm.getObjectKey(), str, true);
            }
        }
    }

    private static OsObjectSchemaInfo a() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(ClassNameHelper.INTERNAL_CLASS_NAME, 39, 0);
        builder.addPersistedProperty("id", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("name", RealmFieldType.STRING, true, false, false);
        builder.addPersistedProperty("category", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty(SkillStore.SkillDetailSection.TYPE_RATING, RealmFieldType.DOUBLE, false, false, true);
        builder.addPersistedProperty("latestEpisode", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("totalEpisode", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("year", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("area", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty(MimeTypes.BASE_TYPE_IMAGE, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("season", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("mainActor", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("isShort", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty(TraceConstants.Result.CP, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("cpId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("idV2", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("horizontalImgUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty(IChannel.EXTRA_ERROR_DESC, RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("verticalImgUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("source", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("subCategory", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("webUrl", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("audioId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("resourceId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("mediaType", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("from", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("isHotPlay", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("style", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("isVip", RealmFieldType.BOOLEAN, false, false, true);
        builder.addPersistedProperty("showStatus", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("trackKey", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("duration", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("lastUpdateTime", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("text", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("traceId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("expId", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("adInfoJson", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("titleText", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("ratingText", RealmFieldType.STRING, false, false, false);
        builder.addPersistedProperty("pCode", RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return b;
    }

    public static a createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new a(osSchemaInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x037d  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x047e  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x049b  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.xiaomi.micolauncher.module.homepage.bean.VideoData createOrUpdateUsingJsonObject(io.realm.Realm r11, org.json.JSONObject r12, boolean r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 1317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy.createOrUpdateUsingJsonObject(io.realm.Realm, org.json.JSONObject, boolean):com.xiaomi.micolauncher.module.homepage.bean.VideoData");
    }

    @TargetApi(11)
    public static VideoData createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        VideoData videoData = new VideoData();
        VideoData videoData2 = videoData;
        jsonReader.beginObject();
        boolean z = false;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("id")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$id(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$id(null);
                }
            } else if (nextName.equals("name")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$name(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$name(null);
                }
                z = true;
            } else if (nextName.equals("category")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$category(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$category(null);
                }
            } else if (nextName.equals(SkillStore.SkillDetailSection.TYPE_RATING)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$rating(jsonReader.nextDouble());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rating' to null.");
                }
            } else if (nextName.equals("latestEpisode")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$latestEpisode(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'latestEpisode' to null.");
                }
            } else if (nextName.equals("totalEpisode")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$totalEpisode(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'totalEpisode' to null.");
                }
            } else if (nextName.equals("year")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$year(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'year' to null.");
                }
            } else if (nextName.equals("area")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$area(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$area(null);
                }
            } else if (nextName.equals(MimeTypes.BASE_TYPE_IMAGE)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$image(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$image(null);
                }
            } else if (nextName.equals("season")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$season(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'season' to null.");
                }
            } else if (nextName.equals("mainActor")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$mainActor(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$mainActor(null);
                }
            } else if (nextName.equals("isShort")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$isShort(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isShort' to null.");
                }
            } else if (nextName.equals(TraceConstants.Result.CP)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$cp(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$cp(null);
                }
            } else if (nextName.equals("cpId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$cpId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$cpId(null);
                }
            } else if (nextName.equals("idV2")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$idV2(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$idV2(null);
                }
            } else if (nextName.equals("horizontalImgUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$horizontalImgUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$horizontalImgUrl(null);
                }
            } else if (nextName.equals(IChannel.EXTRA_ERROR_DESC)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$desc(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$desc(null);
                }
            } else if (nextName.equals("verticalImgUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$verticalImgUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$verticalImgUrl(null);
                }
            } else if (nextName.equals("source")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$source(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$source(null);
                }
            } else if (nextName.equals("subCategory")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$subCategory(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$subCategory(null);
                }
            } else if (nextName.equals("webUrl")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$webUrl(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$webUrl(null);
                }
            } else if (nextName.equals("audioId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$audioId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$audioId(null);
                }
            } else if (nextName.equals("resourceId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$resourceId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$resourceId(null);
                }
            } else if (nextName.equals("mediaType")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$mediaType(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$mediaType(null);
                }
            } else if (nextName.equals("from")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$from(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$from(null);
                }
            } else if (nextName.equals("isHotPlay")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$isHotPlay(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isHotPlay' to null.");
                }
            } else if (nextName.equals("style")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$style(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'style' to null.");
                }
            } else if (nextName.equals("isVip")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$isVip(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isVip' to null.");
                }
            } else if (nextName.equals("showStatus")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$showStatus(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$showStatus(null);
                }
            } else if (nextName.equals("trackKey")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$trackKey(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$trackKey(null);
                }
            } else if (nextName.equals("duration")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$duration(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'duration' to null.");
                }
            } else if (nextName.equals("lastUpdateTime")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$lastUpdateTime(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'lastUpdateTime' to null.");
                }
            } else if (nextName.equals("text")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$text(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$text(null);
                }
            } else if (nextName.equals("traceId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$traceId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$traceId(null);
                }
            } else if (nextName.equals("expId")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$expId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$expId(null);
                }
            } else if (nextName.equals("adInfoJson")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$adInfoJson(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$adInfoJson(null);
                }
            } else if (nextName.equals("titleText")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$titleText(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$titleText(null);
                }
            } else if (nextName.equals("ratingText")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    videoData2.realmSet$ratingText(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    videoData2.realmSet$ratingText(null);
                }
            } else if (!nextName.equals("pCode")) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() != JsonToken.NULL) {
                videoData2.realmSet$pCode(jsonReader.nextString());
            } else {
                jsonReader.skipValue();
                videoData2.realmSet$pCode(null);
            }
        }
        jsonReader.endObject();
        if (z) {
            return (VideoData) realm.copyToRealm((Realm) videoData, new ImportFlag[0]);
        }
        throw new IllegalArgumentException("JSON object doesn't have the primary key field 'name'.");
    }

    private static com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy a(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().c(VideoData.class), false, Collections.emptyList());
        com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy = new com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy();
        realmObjectContext.clear();
        return com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy;
    }

    /* JADX WARN: Finally extract failed */
    public static VideoData copyOrUpdate(Realm realm, a aVar, VideoData videoData, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy;
        boolean z2;
        long j;
        if ((videoData instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoData)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoData;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.d != realm.d) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                } else if (realm$realm.getPath().equals(realm.getPath())) {
                    return videoData;
                }
            }
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        RealmObjectProxy realmObjectProxy2 = map.get(videoData);
        if (realmObjectProxy2 != null) {
            return (VideoData) realmObjectProxy2;
        }
        if (z) {
            Table a2 = realm.a(VideoData.class);
            long j2 = aVar.b;
            String realmGet$name = videoData.realmGet$name();
            if (realmGet$name == null) {
                j = a2.findFirstNull(j2);
            } else {
                j = a2.findFirstString(j2, realmGet$name);
            }
            if (j == -1) {
                z2 = false;
                com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy = null;
            } else {
                try {
                    realmObjectContext.set(realm, a2.getUncheckedRow(j), aVar, false, Collections.emptyList());
                    com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy2 = new com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy();
                    map.put(videoData, com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy2);
                    realmObjectContext.clear();
                    z2 = z;
                    com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy = com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy2;
                } catch (Throwable th) {
                    realmObjectContext.clear();
                    throw th;
                }
            }
        } else {
            z2 = z;
            com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy = null;
        }
        return z2 ? a(realm, aVar, com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy, videoData, map, set) : copy(realm, aVar, videoData, z, map, set);
    }

    public static VideoData copy(Realm realm, a aVar, VideoData videoData, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(videoData);
        if (realmObjectProxy != null) {
            return (VideoData) realmObjectProxy;
        }
        VideoData videoData2 = videoData;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(VideoData.class), set);
        osObjectBuilder.addString(aVar.a, videoData2.realmGet$id());
        osObjectBuilder.addString(aVar.b, videoData2.realmGet$name());
        osObjectBuilder.addString(aVar.c, videoData2.realmGet$category());
        osObjectBuilder.addDouble(aVar.d, Double.valueOf(videoData2.realmGet$rating()));
        osObjectBuilder.addInteger(aVar.e, Integer.valueOf(videoData2.realmGet$latestEpisode()));
        osObjectBuilder.addInteger(aVar.f, Integer.valueOf(videoData2.realmGet$totalEpisode()));
        osObjectBuilder.addInteger(aVar.g, Integer.valueOf(videoData2.realmGet$year()));
        osObjectBuilder.addString(aVar.h, videoData2.realmGet$area());
        osObjectBuilder.addString(aVar.i, videoData2.realmGet$image());
        osObjectBuilder.addInteger(aVar.j, Integer.valueOf(videoData2.realmGet$season()));
        osObjectBuilder.addString(aVar.k, videoData2.realmGet$mainActor());
        osObjectBuilder.addBoolean(aVar.l, Boolean.valueOf(videoData2.realmGet$isShort()));
        osObjectBuilder.addString(aVar.m, videoData2.realmGet$cp());
        osObjectBuilder.addString(aVar.n, videoData2.realmGet$cpId());
        osObjectBuilder.addString(aVar.o, videoData2.realmGet$idV2());
        osObjectBuilder.addString(aVar.p, videoData2.realmGet$horizontalImgUrl());
        osObjectBuilder.addString(aVar.q, videoData2.realmGet$desc());
        osObjectBuilder.addString(aVar.r, videoData2.realmGet$verticalImgUrl());
        osObjectBuilder.addString(aVar.s, videoData2.realmGet$source());
        osObjectBuilder.addString(aVar.t, videoData2.realmGet$subCategory());
        osObjectBuilder.addString(aVar.u, videoData2.realmGet$webUrl());
        osObjectBuilder.addString(aVar.v, videoData2.realmGet$audioId());
        osObjectBuilder.addString(aVar.w, videoData2.realmGet$resourceId());
        osObjectBuilder.addString(aVar.x, videoData2.realmGet$mediaType());
        osObjectBuilder.addString(aVar.y, videoData2.realmGet$from());
        osObjectBuilder.addBoolean(aVar.z, Boolean.valueOf(videoData2.realmGet$isHotPlay()));
        osObjectBuilder.addInteger(aVar.A, Integer.valueOf(videoData2.realmGet$style()));
        osObjectBuilder.addBoolean(aVar.B, Boolean.valueOf(videoData2.realmGet$isVip()));
        osObjectBuilder.addString(aVar.C, videoData2.realmGet$showStatus());
        osObjectBuilder.addString(aVar.D, videoData2.realmGet$trackKey());
        osObjectBuilder.addInteger(aVar.E, Long.valueOf(videoData2.realmGet$duration()));
        osObjectBuilder.addInteger(aVar.F, Long.valueOf(videoData2.realmGet$lastUpdateTime()));
        osObjectBuilder.addString(aVar.G, videoData2.realmGet$text());
        osObjectBuilder.addString(aVar.H, videoData2.realmGet$traceId());
        osObjectBuilder.addString(aVar.I, videoData2.realmGet$expId());
        osObjectBuilder.addString(aVar.J, videoData2.realmGet$adInfoJson());
        osObjectBuilder.addString(aVar.K, videoData2.realmGet$titleText());
        osObjectBuilder.addString(aVar.L, videoData2.realmGet$ratingText());
        osObjectBuilder.addString(aVar.M, videoData2.realmGet$pCode());
        com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy a2 = a(realm, osObjectBuilder.createNewObject());
        map.put(videoData, a2);
        return a2;
    }

    public static long insert(Realm realm, VideoData videoData, Map<RealmModel, Long> map) {
        long j;
        long j2;
        if ((videoData instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoData)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoData;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(VideoData.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoData.class);
        long j3 = aVar.b;
        VideoData videoData2 = videoData;
        String realmGet$name = videoData2.realmGet$name();
        if (realmGet$name == null) {
            j = Table.nativeFindFirstNull(nativePtr, j3);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j3, realmGet$name);
        }
        if (j == -1) {
            j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$name);
        } else {
            Table.throwDuplicatePrimaryKeyException(realmGet$name);
            j2 = j;
        }
        map.put(videoData, Long.valueOf(j2));
        String realmGet$id = videoData2.realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(nativePtr, aVar.a, j2, realmGet$id, false);
        }
        String realmGet$category = videoData2.realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$category, false);
        }
        Table.nativeSetDouble(nativePtr, aVar.d, j2, videoData2.realmGet$rating(), false);
        Table.nativeSetLong(nativePtr, aVar.e, j2, videoData2.realmGet$latestEpisode(), false);
        Table.nativeSetLong(nativePtr, aVar.f, j2, videoData2.realmGet$totalEpisode(), false);
        Table.nativeSetLong(nativePtr, aVar.g, j2, videoData2.realmGet$year(), false);
        String realmGet$area = videoData2.realmGet$area();
        if (realmGet$area != null) {
            Table.nativeSetString(nativePtr, aVar.h, j2, realmGet$area, false);
        }
        String realmGet$image = videoData2.realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(nativePtr, aVar.i, j2, realmGet$image, false);
        }
        Table.nativeSetLong(nativePtr, aVar.j, j2, videoData2.realmGet$season(), false);
        String realmGet$mainActor = videoData2.realmGet$mainActor();
        if (realmGet$mainActor != null) {
            Table.nativeSetString(nativePtr, aVar.k, j2, realmGet$mainActor, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.l, j2, videoData2.realmGet$isShort(), false);
        String realmGet$cp = videoData2.realmGet$cp();
        if (realmGet$cp != null) {
            Table.nativeSetString(nativePtr, aVar.m, j2, realmGet$cp, false);
        }
        String realmGet$cpId = videoData2.realmGet$cpId();
        if (realmGet$cpId != null) {
            Table.nativeSetString(nativePtr, aVar.n, j2, realmGet$cpId, false);
        }
        String realmGet$idV2 = videoData2.realmGet$idV2();
        if (realmGet$idV2 != null) {
            Table.nativeSetString(nativePtr, aVar.o, j2, realmGet$idV2, false);
        }
        String realmGet$horizontalImgUrl = videoData2.realmGet$horizontalImgUrl();
        if (realmGet$horizontalImgUrl != null) {
            Table.nativeSetString(nativePtr, aVar.p, j2, realmGet$horizontalImgUrl, false);
        }
        String realmGet$desc = videoData2.realmGet$desc();
        if (realmGet$desc != null) {
            Table.nativeSetString(nativePtr, aVar.q, j2, realmGet$desc, false);
        }
        String realmGet$verticalImgUrl = videoData2.realmGet$verticalImgUrl();
        if (realmGet$verticalImgUrl != null) {
            Table.nativeSetString(nativePtr, aVar.r, j2, realmGet$verticalImgUrl, false);
        }
        String realmGet$source = videoData2.realmGet$source();
        if (realmGet$source != null) {
            Table.nativeSetString(nativePtr, aVar.s, j2, realmGet$source, false);
        }
        String realmGet$subCategory = videoData2.realmGet$subCategory();
        if (realmGet$subCategory != null) {
            Table.nativeSetString(nativePtr, aVar.t, j2, realmGet$subCategory, false);
        }
        String realmGet$webUrl = videoData2.realmGet$webUrl();
        if (realmGet$webUrl != null) {
            Table.nativeSetString(nativePtr, aVar.u, j2, realmGet$webUrl, false);
        }
        String realmGet$audioId = videoData2.realmGet$audioId();
        if (realmGet$audioId != null) {
            Table.nativeSetString(nativePtr, aVar.v, j2, realmGet$audioId, false);
        }
        String realmGet$resourceId = videoData2.realmGet$resourceId();
        if (realmGet$resourceId != null) {
            Table.nativeSetString(nativePtr, aVar.w, j2, realmGet$resourceId, false);
        }
        String realmGet$mediaType = videoData2.realmGet$mediaType();
        if (realmGet$mediaType != null) {
            Table.nativeSetString(nativePtr, aVar.x, j2, realmGet$mediaType, false);
        }
        String realmGet$from = videoData2.realmGet$from();
        if (realmGet$from != null) {
            Table.nativeSetString(nativePtr, aVar.y, j2, realmGet$from, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.z, j2, videoData2.realmGet$isHotPlay(), false);
        Table.nativeSetLong(nativePtr, aVar.A, j2, videoData2.realmGet$style(), false);
        Table.nativeSetBoolean(nativePtr, aVar.B, j2, videoData2.realmGet$isVip(), false);
        String realmGet$showStatus = videoData2.realmGet$showStatus();
        if (realmGet$showStatus != null) {
            Table.nativeSetString(nativePtr, aVar.C, j2, realmGet$showStatus, false);
        }
        String realmGet$trackKey = videoData2.realmGet$trackKey();
        if (realmGet$trackKey != null) {
            Table.nativeSetString(nativePtr, aVar.D, j2, realmGet$trackKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.E, j2, videoData2.realmGet$duration(), false);
        Table.nativeSetLong(nativePtr, aVar.F, j2, videoData2.realmGet$lastUpdateTime(), false);
        String realmGet$text = videoData2.realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(nativePtr, aVar.G, j2, realmGet$text, false);
        }
        String realmGet$traceId = videoData2.realmGet$traceId();
        if (realmGet$traceId != null) {
            Table.nativeSetString(nativePtr, aVar.H, j2, realmGet$traceId, false);
        }
        String realmGet$expId = videoData2.realmGet$expId();
        if (realmGet$expId != null) {
            Table.nativeSetString(nativePtr, aVar.I, j2, realmGet$expId, false);
        }
        String realmGet$adInfoJson = videoData2.realmGet$adInfoJson();
        if (realmGet$adInfoJson != null) {
            Table.nativeSetString(nativePtr, aVar.J, j2, realmGet$adInfoJson, false);
        }
        String realmGet$titleText = videoData2.realmGet$titleText();
        if (realmGet$titleText != null) {
            Table.nativeSetString(nativePtr, aVar.K, j2, realmGet$titleText, false);
        }
        String realmGet$ratingText = videoData2.realmGet$ratingText();
        if (realmGet$ratingText != null) {
            Table.nativeSetString(nativePtr, aVar.L, j2, realmGet$ratingText, false);
        }
        String realmGet$pCode = videoData2.realmGet$pCode();
        if (realmGet$pCode != null) {
            Table.nativeSetString(nativePtr, aVar.M, j2, realmGet$pCode, false);
        }
        return j2;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        long j2;
        Table a2 = realm.a(VideoData.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoData.class);
        long j3 = aVar.b;
        while (it.hasNext()) {
            VideoData videoData = (VideoData) it.next();
            if (!map.containsKey(videoData)) {
                if ((videoData instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoData)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoData;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(videoData, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                VideoData videoData2 = videoData;
                String realmGet$name = videoData2.realmGet$name();
                if (realmGet$name == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j3);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j3, realmGet$name);
                }
                if (j == -1) {
                    j2 = OsObject.createRowWithPrimaryKey(a2, j3, realmGet$name);
                } else {
                    Table.throwDuplicatePrimaryKeyException(realmGet$name);
                    j2 = j;
                }
                map.put(videoData, Long.valueOf(j2));
                String realmGet$id = videoData2.realmGet$id();
                if (realmGet$id != null) {
                    j3 = j3;
                    Table.nativeSetString(nativePtr, aVar.a, j2, realmGet$id, false);
                } else {
                    j3 = j3;
                }
                String realmGet$category = videoData2.realmGet$category();
                if (realmGet$category != null) {
                    Table.nativeSetString(nativePtr, aVar.c, j2, realmGet$category, false);
                }
                Table.nativeSetDouble(nativePtr, aVar.d, j2, videoData2.realmGet$rating(), false);
                Table.nativeSetLong(nativePtr, aVar.e, j2, videoData2.realmGet$latestEpisode(), false);
                Table.nativeSetLong(nativePtr, aVar.f, j2, videoData2.realmGet$totalEpisode(), false);
                Table.nativeSetLong(nativePtr, aVar.g, j2, videoData2.realmGet$year(), false);
                String realmGet$area = videoData2.realmGet$area();
                if (realmGet$area != null) {
                    Table.nativeSetString(nativePtr, aVar.h, j2, realmGet$area, false);
                }
                String realmGet$image = videoData2.realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(nativePtr, aVar.i, j2, realmGet$image, false);
                }
                Table.nativeSetLong(nativePtr, aVar.j, j2, videoData2.realmGet$season(), false);
                String realmGet$mainActor = videoData2.realmGet$mainActor();
                if (realmGet$mainActor != null) {
                    Table.nativeSetString(nativePtr, aVar.k, j2, realmGet$mainActor, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.l, j2, videoData2.realmGet$isShort(), false);
                String realmGet$cp = videoData2.realmGet$cp();
                if (realmGet$cp != null) {
                    Table.nativeSetString(nativePtr, aVar.m, j2, realmGet$cp, false);
                }
                String realmGet$cpId = videoData2.realmGet$cpId();
                if (realmGet$cpId != null) {
                    Table.nativeSetString(nativePtr, aVar.n, j2, realmGet$cpId, false);
                }
                String realmGet$idV2 = videoData2.realmGet$idV2();
                if (realmGet$idV2 != null) {
                    Table.nativeSetString(nativePtr, aVar.o, j2, realmGet$idV2, false);
                }
                String realmGet$horizontalImgUrl = videoData2.realmGet$horizontalImgUrl();
                if (realmGet$horizontalImgUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.p, j2, realmGet$horizontalImgUrl, false);
                }
                String realmGet$desc = videoData2.realmGet$desc();
                if (realmGet$desc != null) {
                    Table.nativeSetString(nativePtr, aVar.q, j2, realmGet$desc, false);
                }
                String realmGet$verticalImgUrl = videoData2.realmGet$verticalImgUrl();
                if (realmGet$verticalImgUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.r, j2, realmGet$verticalImgUrl, false);
                }
                String realmGet$source = videoData2.realmGet$source();
                if (realmGet$source != null) {
                    Table.nativeSetString(nativePtr, aVar.s, j2, realmGet$source, false);
                }
                String realmGet$subCategory = videoData2.realmGet$subCategory();
                if (realmGet$subCategory != null) {
                    Table.nativeSetString(nativePtr, aVar.t, j2, realmGet$subCategory, false);
                }
                String realmGet$webUrl = videoData2.realmGet$webUrl();
                if (realmGet$webUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.u, j2, realmGet$webUrl, false);
                }
                String realmGet$audioId = videoData2.realmGet$audioId();
                if (realmGet$audioId != null) {
                    Table.nativeSetString(nativePtr, aVar.v, j2, realmGet$audioId, false);
                }
                String realmGet$resourceId = videoData2.realmGet$resourceId();
                if (realmGet$resourceId != null) {
                    Table.nativeSetString(nativePtr, aVar.w, j2, realmGet$resourceId, false);
                }
                String realmGet$mediaType = videoData2.realmGet$mediaType();
                if (realmGet$mediaType != null) {
                    Table.nativeSetString(nativePtr, aVar.x, j2, realmGet$mediaType, false);
                }
                String realmGet$from = videoData2.realmGet$from();
                if (realmGet$from != null) {
                    Table.nativeSetString(nativePtr, aVar.y, j2, realmGet$from, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.z, j2, videoData2.realmGet$isHotPlay(), false);
                Table.nativeSetLong(nativePtr, aVar.A, j2, videoData2.realmGet$style(), false);
                Table.nativeSetBoolean(nativePtr, aVar.B, j2, videoData2.realmGet$isVip(), false);
                String realmGet$showStatus = videoData2.realmGet$showStatus();
                if (realmGet$showStatus != null) {
                    Table.nativeSetString(nativePtr, aVar.C, j2, realmGet$showStatus, false);
                }
                String realmGet$trackKey = videoData2.realmGet$trackKey();
                if (realmGet$trackKey != null) {
                    Table.nativeSetString(nativePtr, aVar.D, j2, realmGet$trackKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.E, j2, videoData2.realmGet$duration(), false);
                Table.nativeSetLong(nativePtr, aVar.F, j2, videoData2.realmGet$lastUpdateTime(), false);
                String realmGet$text = videoData2.realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(nativePtr, aVar.G, j2, realmGet$text, false);
                }
                String realmGet$traceId = videoData2.realmGet$traceId();
                if (realmGet$traceId != null) {
                    Table.nativeSetString(nativePtr, aVar.H, j2, realmGet$traceId, false);
                }
                String realmGet$expId = videoData2.realmGet$expId();
                if (realmGet$expId != null) {
                    Table.nativeSetString(nativePtr, aVar.I, j2, realmGet$expId, false);
                }
                String realmGet$adInfoJson = videoData2.realmGet$adInfoJson();
                if (realmGet$adInfoJson != null) {
                    Table.nativeSetString(nativePtr, aVar.J, j2, realmGet$adInfoJson, false);
                }
                String realmGet$titleText = videoData2.realmGet$titleText();
                if (realmGet$titleText != null) {
                    Table.nativeSetString(nativePtr, aVar.K, j2, realmGet$titleText, false);
                }
                String realmGet$ratingText = videoData2.realmGet$ratingText();
                if (realmGet$ratingText != null) {
                    Table.nativeSetString(nativePtr, aVar.L, j2, realmGet$ratingText, false);
                }
                String realmGet$pCode = videoData2.realmGet$pCode();
                if (realmGet$pCode != null) {
                    Table.nativeSetString(nativePtr, aVar.M, j2, realmGet$pCode, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, VideoData videoData, Map<RealmModel, Long> map) {
        long j;
        if ((videoData instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoData)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoData;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table a2 = realm.a(VideoData.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoData.class);
        long j2 = aVar.b;
        VideoData videoData2 = videoData;
        String realmGet$name = videoData2.realmGet$name();
        if (realmGet$name == null) {
            j = Table.nativeFindFirstNull(nativePtr, j2);
        } else {
            j = Table.nativeFindFirstString(nativePtr, j2, realmGet$name);
        }
        long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$name) : j;
        map.put(videoData, Long.valueOf(createRowWithPrimaryKey));
        String realmGet$id = videoData2.realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(nativePtr, aVar.a, createRowWithPrimaryKey, realmGet$id, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.a, createRowWithPrimaryKey, false);
        }
        String realmGet$category = videoData2.realmGet$category();
        if (realmGet$category != null) {
            Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$category, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
        }
        Table.nativeSetDouble(nativePtr, aVar.d, createRowWithPrimaryKey, videoData2.realmGet$rating(), false);
        Table.nativeSetLong(nativePtr, aVar.e, createRowWithPrimaryKey, videoData2.realmGet$latestEpisode(), false);
        Table.nativeSetLong(nativePtr, aVar.f, createRowWithPrimaryKey, videoData2.realmGet$totalEpisode(), false);
        Table.nativeSetLong(nativePtr, aVar.g, createRowWithPrimaryKey, videoData2.realmGet$year(), false);
        String realmGet$area = videoData2.realmGet$area();
        if (realmGet$area != null) {
            Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$area, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
        }
        String realmGet$image = videoData2.realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$image, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.j, createRowWithPrimaryKey, videoData2.realmGet$season(), false);
        String realmGet$mainActor = videoData2.realmGet$mainActor();
        if (realmGet$mainActor != null) {
            Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$mainActor, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.l, createRowWithPrimaryKey, videoData2.realmGet$isShort(), false);
        String realmGet$cp = videoData2.realmGet$cp();
        if (realmGet$cp != null) {
            Table.nativeSetString(nativePtr, aVar.m, createRowWithPrimaryKey, realmGet$cp, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.m, createRowWithPrimaryKey, false);
        }
        String realmGet$cpId = videoData2.realmGet$cpId();
        if (realmGet$cpId != null) {
            Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$cpId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
        }
        String realmGet$idV2 = videoData2.realmGet$idV2();
        if (realmGet$idV2 != null) {
            Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$idV2, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
        }
        String realmGet$horizontalImgUrl = videoData2.realmGet$horizontalImgUrl();
        if (realmGet$horizontalImgUrl != null) {
            Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$horizontalImgUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
        }
        String realmGet$desc = videoData2.realmGet$desc();
        if (realmGet$desc != null) {
            Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$desc, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
        }
        String realmGet$verticalImgUrl = videoData2.realmGet$verticalImgUrl();
        if (realmGet$verticalImgUrl != null) {
            Table.nativeSetString(nativePtr, aVar.r, createRowWithPrimaryKey, realmGet$verticalImgUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.r, createRowWithPrimaryKey, false);
        }
        String realmGet$source = videoData2.realmGet$source();
        if (realmGet$source != null) {
            Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$source, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
        }
        String realmGet$subCategory = videoData2.realmGet$subCategory();
        if (realmGet$subCategory != null) {
            Table.nativeSetString(nativePtr, aVar.t, createRowWithPrimaryKey, realmGet$subCategory, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.t, createRowWithPrimaryKey, false);
        }
        String realmGet$webUrl = videoData2.realmGet$webUrl();
        if (realmGet$webUrl != null) {
            Table.nativeSetString(nativePtr, aVar.u, createRowWithPrimaryKey, realmGet$webUrl, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.u, createRowWithPrimaryKey, false);
        }
        String realmGet$audioId = videoData2.realmGet$audioId();
        if (realmGet$audioId != null) {
            Table.nativeSetString(nativePtr, aVar.v, createRowWithPrimaryKey, realmGet$audioId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.v, createRowWithPrimaryKey, false);
        }
        String realmGet$resourceId = videoData2.realmGet$resourceId();
        if (realmGet$resourceId != null) {
            Table.nativeSetString(nativePtr, aVar.w, createRowWithPrimaryKey, realmGet$resourceId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.w, createRowWithPrimaryKey, false);
        }
        String realmGet$mediaType = videoData2.realmGet$mediaType();
        if (realmGet$mediaType != null) {
            Table.nativeSetString(nativePtr, aVar.x, createRowWithPrimaryKey, realmGet$mediaType, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.x, createRowWithPrimaryKey, false);
        }
        String realmGet$from = videoData2.realmGet$from();
        if (realmGet$from != null) {
            Table.nativeSetString(nativePtr, aVar.y, createRowWithPrimaryKey, realmGet$from, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.y, createRowWithPrimaryKey, false);
        }
        Table.nativeSetBoolean(nativePtr, aVar.z, createRowWithPrimaryKey, videoData2.realmGet$isHotPlay(), false);
        Table.nativeSetLong(nativePtr, aVar.A, createRowWithPrimaryKey, videoData2.realmGet$style(), false);
        Table.nativeSetBoolean(nativePtr, aVar.B, createRowWithPrimaryKey, videoData2.realmGet$isVip(), false);
        String realmGet$showStatus = videoData2.realmGet$showStatus();
        if (realmGet$showStatus != null) {
            Table.nativeSetString(nativePtr, aVar.C, createRowWithPrimaryKey, realmGet$showStatus, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.C, createRowWithPrimaryKey, false);
        }
        String realmGet$trackKey = videoData2.realmGet$trackKey();
        if (realmGet$trackKey != null) {
            Table.nativeSetString(nativePtr, aVar.D, createRowWithPrimaryKey, realmGet$trackKey, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.D, createRowWithPrimaryKey, false);
        }
        Table.nativeSetLong(nativePtr, aVar.E, createRowWithPrimaryKey, videoData2.realmGet$duration(), false);
        Table.nativeSetLong(nativePtr, aVar.F, createRowWithPrimaryKey, videoData2.realmGet$lastUpdateTime(), false);
        String realmGet$text = videoData2.realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(nativePtr, aVar.G, createRowWithPrimaryKey, realmGet$text, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.G, createRowWithPrimaryKey, false);
        }
        String realmGet$traceId = videoData2.realmGet$traceId();
        if (realmGet$traceId != null) {
            Table.nativeSetString(nativePtr, aVar.H, createRowWithPrimaryKey, realmGet$traceId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.H, createRowWithPrimaryKey, false);
        }
        String realmGet$expId = videoData2.realmGet$expId();
        if (realmGet$expId != null) {
            Table.nativeSetString(nativePtr, aVar.I, createRowWithPrimaryKey, realmGet$expId, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.I, createRowWithPrimaryKey, false);
        }
        String realmGet$adInfoJson = videoData2.realmGet$adInfoJson();
        if (realmGet$adInfoJson != null) {
            Table.nativeSetString(nativePtr, aVar.J, createRowWithPrimaryKey, realmGet$adInfoJson, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.J, createRowWithPrimaryKey, false);
        }
        String realmGet$titleText = videoData2.realmGet$titleText();
        if (realmGet$titleText != null) {
            Table.nativeSetString(nativePtr, aVar.K, createRowWithPrimaryKey, realmGet$titleText, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.K, createRowWithPrimaryKey, false);
        }
        String realmGet$ratingText = videoData2.realmGet$ratingText();
        if (realmGet$ratingText != null) {
            Table.nativeSetString(nativePtr, aVar.L, createRowWithPrimaryKey, realmGet$ratingText, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.L, createRowWithPrimaryKey, false);
        }
        String realmGet$pCode = videoData2.realmGet$pCode();
        if (realmGet$pCode != null) {
            Table.nativeSetString(nativePtr, aVar.M, createRowWithPrimaryKey, realmGet$pCode, false);
        } else {
            Table.nativeSetNull(nativePtr, aVar.M, createRowWithPrimaryKey, false);
        }
        return createRowWithPrimaryKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        long j;
        Table a2 = realm.a(VideoData.class);
        long nativePtr = a2.getNativePtr();
        a aVar = (a) realm.getSchema().c(VideoData.class);
        long j2 = aVar.b;
        while (it.hasNext()) {
            VideoData videoData = (VideoData) it.next();
            if (!map.containsKey(videoData)) {
                if ((videoData instanceof RealmObjectProxy) && !RealmObject.isFrozen(videoData)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) videoData;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(videoData, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                VideoData videoData2 = videoData;
                String realmGet$name = videoData2.realmGet$name();
                if (realmGet$name == null) {
                    j = Table.nativeFindFirstNull(nativePtr, j2);
                } else {
                    j = Table.nativeFindFirstString(nativePtr, j2, realmGet$name);
                }
                long createRowWithPrimaryKey = j == -1 ? OsObject.createRowWithPrimaryKey(a2, j2, realmGet$name) : j;
                map.put(videoData, Long.valueOf(createRowWithPrimaryKey));
                String realmGet$id = videoData2.realmGet$id();
                if (realmGet$id != null) {
                    j2 = j2;
                    Table.nativeSetString(nativePtr, aVar.a, createRowWithPrimaryKey, realmGet$id, false);
                } else {
                    j2 = j2;
                    Table.nativeSetNull(nativePtr, aVar.a, createRowWithPrimaryKey, false);
                }
                String realmGet$category = videoData2.realmGet$category();
                if (realmGet$category != null) {
                    Table.nativeSetString(nativePtr, aVar.c, createRowWithPrimaryKey, realmGet$category, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.c, createRowWithPrimaryKey, false);
                }
                Table.nativeSetDouble(nativePtr, aVar.d, createRowWithPrimaryKey, videoData2.realmGet$rating(), false);
                Table.nativeSetLong(nativePtr, aVar.e, createRowWithPrimaryKey, videoData2.realmGet$latestEpisode(), false);
                Table.nativeSetLong(nativePtr, aVar.f, createRowWithPrimaryKey, videoData2.realmGet$totalEpisode(), false);
                Table.nativeSetLong(nativePtr, aVar.g, createRowWithPrimaryKey, videoData2.realmGet$year(), false);
                String realmGet$area = videoData2.realmGet$area();
                if (realmGet$area != null) {
                    Table.nativeSetString(nativePtr, aVar.h, createRowWithPrimaryKey, realmGet$area, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.h, createRowWithPrimaryKey, false);
                }
                String realmGet$image = videoData2.realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(nativePtr, aVar.i, createRowWithPrimaryKey, realmGet$image, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.i, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.j, createRowWithPrimaryKey, videoData2.realmGet$season(), false);
                String realmGet$mainActor = videoData2.realmGet$mainActor();
                if (realmGet$mainActor != null) {
                    Table.nativeSetString(nativePtr, aVar.k, createRowWithPrimaryKey, realmGet$mainActor, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.k, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.l, createRowWithPrimaryKey, videoData2.realmGet$isShort(), false);
                String realmGet$cp = videoData2.realmGet$cp();
                if (realmGet$cp != null) {
                    Table.nativeSetString(nativePtr, aVar.m, createRowWithPrimaryKey, realmGet$cp, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.m, createRowWithPrimaryKey, false);
                }
                String realmGet$cpId = videoData2.realmGet$cpId();
                if (realmGet$cpId != null) {
                    Table.nativeSetString(nativePtr, aVar.n, createRowWithPrimaryKey, realmGet$cpId, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.n, createRowWithPrimaryKey, false);
                }
                String realmGet$idV2 = videoData2.realmGet$idV2();
                if (realmGet$idV2 != null) {
                    Table.nativeSetString(nativePtr, aVar.o, createRowWithPrimaryKey, realmGet$idV2, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.o, createRowWithPrimaryKey, false);
                }
                String realmGet$horizontalImgUrl = videoData2.realmGet$horizontalImgUrl();
                if (realmGet$horizontalImgUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.p, createRowWithPrimaryKey, realmGet$horizontalImgUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.p, createRowWithPrimaryKey, false);
                }
                String realmGet$desc = videoData2.realmGet$desc();
                if (realmGet$desc != null) {
                    Table.nativeSetString(nativePtr, aVar.q, createRowWithPrimaryKey, realmGet$desc, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.q, createRowWithPrimaryKey, false);
                }
                String realmGet$verticalImgUrl = videoData2.realmGet$verticalImgUrl();
                if (realmGet$verticalImgUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.r, createRowWithPrimaryKey, realmGet$verticalImgUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.r, createRowWithPrimaryKey, false);
                }
                String realmGet$source = videoData2.realmGet$source();
                if (realmGet$source != null) {
                    Table.nativeSetString(nativePtr, aVar.s, createRowWithPrimaryKey, realmGet$source, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.s, createRowWithPrimaryKey, false);
                }
                String realmGet$subCategory = videoData2.realmGet$subCategory();
                if (realmGet$subCategory != null) {
                    Table.nativeSetString(nativePtr, aVar.t, createRowWithPrimaryKey, realmGet$subCategory, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.t, createRowWithPrimaryKey, false);
                }
                String realmGet$webUrl = videoData2.realmGet$webUrl();
                if (realmGet$webUrl != null) {
                    Table.nativeSetString(nativePtr, aVar.u, createRowWithPrimaryKey, realmGet$webUrl, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.u, createRowWithPrimaryKey, false);
                }
                String realmGet$audioId = videoData2.realmGet$audioId();
                if (realmGet$audioId != null) {
                    Table.nativeSetString(nativePtr, aVar.v, createRowWithPrimaryKey, realmGet$audioId, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.v, createRowWithPrimaryKey, false);
                }
                String realmGet$resourceId = videoData2.realmGet$resourceId();
                if (realmGet$resourceId != null) {
                    Table.nativeSetString(nativePtr, aVar.w, createRowWithPrimaryKey, realmGet$resourceId, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.w, createRowWithPrimaryKey, false);
                }
                String realmGet$mediaType = videoData2.realmGet$mediaType();
                if (realmGet$mediaType != null) {
                    Table.nativeSetString(nativePtr, aVar.x, createRowWithPrimaryKey, realmGet$mediaType, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.x, createRowWithPrimaryKey, false);
                }
                String realmGet$from = videoData2.realmGet$from();
                if (realmGet$from != null) {
                    Table.nativeSetString(nativePtr, aVar.y, createRowWithPrimaryKey, realmGet$from, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.y, createRowWithPrimaryKey, false);
                }
                Table.nativeSetBoolean(nativePtr, aVar.z, createRowWithPrimaryKey, videoData2.realmGet$isHotPlay(), false);
                Table.nativeSetLong(nativePtr, aVar.A, createRowWithPrimaryKey, videoData2.realmGet$style(), false);
                Table.nativeSetBoolean(nativePtr, aVar.B, createRowWithPrimaryKey, videoData2.realmGet$isVip(), false);
                String realmGet$showStatus = videoData2.realmGet$showStatus();
                if (realmGet$showStatus != null) {
                    Table.nativeSetString(nativePtr, aVar.C, createRowWithPrimaryKey, realmGet$showStatus, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.C, createRowWithPrimaryKey, false);
                }
                String realmGet$trackKey = videoData2.realmGet$trackKey();
                if (realmGet$trackKey != null) {
                    Table.nativeSetString(nativePtr, aVar.D, createRowWithPrimaryKey, realmGet$trackKey, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.D, createRowWithPrimaryKey, false);
                }
                Table.nativeSetLong(nativePtr, aVar.E, createRowWithPrimaryKey, videoData2.realmGet$duration(), false);
                Table.nativeSetLong(nativePtr, aVar.F, createRowWithPrimaryKey, videoData2.realmGet$lastUpdateTime(), false);
                String realmGet$text = videoData2.realmGet$text();
                if (realmGet$text != null) {
                    Table.nativeSetString(nativePtr, aVar.G, createRowWithPrimaryKey, realmGet$text, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.G, createRowWithPrimaryKey, false);
                }
                String realmGet$traceId = videoData2.realmGet$traceId();
                if (realmGet$traceId != null) {
                    Table.nativeSetString(nativePtr, aVar.H, createRowWithPrimaryKey, realmGet$traceId, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.H, createRowWithPrimaryKey, false);
                }
                String realmGet$expId = videoData2.realmGet$expId();
                if (realmGet$expId != null) {
                    Table.nativeSetString(nativePtr, aVar.I, createRowWithPrimaryKey, realmGet$expId, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.I, createRowWithPrimaryKey, false);
                }
                String realmGet$adInfoJson = videoData2.realmGet$adInfoJson();
                if (realmGet$adInfoJson != null) {
                    Table.nativeSetString(nativePtr, aVar.J, createRowWithPrimaryKey, realmGet$adInfoJson, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.J, createRowWithPrimaryKey, false);
                }
                String realmGet$titleText = videoData2.realmGet$titleText();
                if (realmGet$titleText != null) {
                    Table.nativeSetString(nativePtr, aVar.K, createRowWithPrimaryKey, realmGet$titleText, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.K, createRowWithPrimaryKey, false);
                }
                String realmGet$ratingText = videoData2.realmGet$ratingText();
                if (realmGet$ratingText != null) {
                    Table.nativeSetString(nativePtr, aVar.L, createRowWithPrimaryKey, realmGet$ratingText, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.L, createRowWithPrimaryKey, false);
                }
                String realmGet$pCode = videoData2.realmGet$pCode();
                if (realmGet$pCode != null) {
                    Table.nativeSetString(nativePtr, aVar.M, createRowWithPrimaryKey, realmGet$pCode, false);
                } else {
                    Table.nativeSetNull(nativePtr, aVar.M, createRowWithPrimaryKey, false);
                }
            }
        }
    }

    public static VideoData createDetachedCopy(VideoData videoData, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        VideoData videoData2;
        if (i > i2 || videoData == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(videoData);
        if (cacheData == null) {
            videoData2 = new VideoData();
            map.put(videoData, new RealmObjectProxy.CacheData<>(i, videoData2));
        } else if (i >= cacheData.minDepth) {
            return (VideoData) cacheData.object;
        } else {
            videoData2 = (VideoData) cacheData.object;
            cacheData.minDepth = i;
        }
        VideoData videoData3 = videoData2;
        VideoData videoData4 = videoData;
        videoData3.realmSet$id(videoData4.realmGet$id());
        videoData3.realmSet$name(videoData4.realmGet$name());
        videoData3.realmSet$category(videoData4.realmGet$category());
        videoData3.realmSet$rating(videoData4.realmGet$rating());
        videoData3.realmSet$latestEpisode(videoData4.realmGet$latestEpisode());
        videoData3.realmSet$totalEpisode(videoData4.realmGet$totalEpisode());
        videoData3.realmSet$year(videoData4.realmGet$year());
        videoData3.realmSet$area(videoData4.realmGet$area());
        videoData3.realmSet$image(videoData4.realmGet$image());
        videoData3.realmSet$season(videoData4.realmGet$season());
        videoData3.realmSet$mainActor(videoData4.realmGet$mainActor());
        videoData3.realmSet$isShort(videoData4.realmGet$isShort());
        videoData3.realmSet$cp(videoData4.realmGet$cp());
        videoData3.realmSet$cpId(videoData4.realmGet$cpId());
        videoData3.realmSet$idV2(videoData4.realmGet$idV2());
        videoData3.realmSet$horizontalImgUrl(videoData4.realmGet$horizontalImgUrl());
        videoData3.realmSet$desc(videoData4.realmGet$desc());
        videoData3.realmSet$verticalImgUrl(videoData4.realmGet$verticalImgUrl());
        videoData3.realmSet$source(videoData4.realmGet$source());
        videoData3.realmSet$subCategory(videoData4.realmGet$subCategory());
        videoData3.realmSet$webUrl(videoData4.realmGet$webUrl());
        videoData3.realmSet$audioId(videoData4.realmGet$audioId());
        videoData3.realmSet$resourceId(videoData4.realmGet$resourceId());
        videoData3.realmSet$mediaType(videoData4.realmGet$mediaType());
        videoData3.realmSet$from(videoData4.realmGet$from());
        videoData3.realmSet$isHotPlay(videoData4.realmGet$isHotPlay());
        videoData3.realmSet$style(videoData4.realmGet$style());
        videoData3.realmSet$isVip(videoData4.realmGet$isVip());
        videoData3.realmSet$showStatus(videoData4.realmGet$showStatus());
        videoData3.realmSet$trackKey(videoData4.realmGet$trackKey());
        videoData3.realmSet$duration(videoData4.realmGet$duration());
        videoData3.realmSet$lastUpdateTime(videoData4.realmGet$lastUpdateTime());
        videoData3.realmSet$text(videoData4.realmGet$text());
        videoData3.realmSet$traceId(videoData4.realmGet$traceId());
        videoData3.realmSet$expId(videoData4.realmGet$expId());
        videoData3.realmSet$adInfoJson(videoData4.realmGet$adInfoJson());
        videoData3.realmSet$titleText(videoData4.realmGet$titleText());
        videoData3.realmSet$ratingText(videoData4.realmGet$ratingText());
        videoData3.realmSet$pCode(videoData4.realmGet$pCode());
        return videoData2;
    }

    static VideoData a(Realm realm, a aVar, VideoData videoData, VideoData videoData2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        VideoData videoData3 = videoData2;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.a(VideoData.class), set);
        osObjectBuilder.addString(aVar.a, videoData3.realmGet$id());
        osObjectBuilder.addString(aVar.b, videoData3.realmGet$name());
        osObjectBuilder.addString(aVar.c, videoData3.realmGet$category());
        osObjectBuilder.addDouble(aVar.d, Double.valueOf(videoData3.realmGet$rating()));
        osObjectBuilder.addInteger(aVar.e, Integer.valueOf(videoData3.realmGet$latestEpisode()));
        osObjectBuilder.addInteger(aVar.f, Integer.valueOf(videoData3.realmGet$totalEpisode()));
        osObjectBuilder.addInteger(aVar.g, Integer.valueOf(videoData3.realmGet$year()));
        osObjectBuilder.addString(aVar.h, videoData3.realmGet$area());
        osObjectBuilder.addString(aVar.i, videoData3.realmGet$image());
        osObjectBuilder.addInteger(aVar.j, Integer.valueOf(videoData3.realmGet$season()));
        osObjectBuilder.addString(aVar.k, videoData3.realmGet$mainActor());
        osObjectBuilder.addBoolean(aVar.l, Boolean.valueOf(videoData3.realmGet$isShort()));
        osObjectBuilder.addString(aVar.m, videoData3.realmGet$cp());
        osObjectBuilder.addString(aVar.n, videoData3.realmGet$cpId());
        osObjectBuilder.addString(aVar.o, videoData3.realmGet$idV2());
        osObjectBuilder.addString(aVar.p, videoData3.realmGet$horizontalImgUrl());
        osObjectBuilder.addString(aVar.q, videoData3.realmGet$desc());
        osObjectBuilder.addString(aVar.r, videoData3.realmGet$verticalImgUrl());
        osObjectBuilder.addString(aVar.s, videoData3.realmGet$source());
        osObjectBuilder.addString(aVar.t, videoData3.realmGet$subCategory());
        osObjectBuilder.addString(aVar.u, videoData3.realmGet$webUrl());
        osObjectBuilder.addString(aVar.v, videoData3.realmGet$audioId());
        osObjectBuilder.addString(aVar.w, videoData3.realmGet$resourceId());
        osObjectBuilder.addString(aVar.x, videoData3.realmGet$mediaType());
        osObjectBuilder.addString(aVar.y, videoData3.realmGet$from());
        osObjectBuilder.addBoolean(aVar.z, Boolean.valueOf(videoData3.realmGet$isHotPlay()));
        osObjectBuilder.addInteger(aVar.A, Integer.valueOf(videoData3.realmGet$style()));
        osObjectBuilder.addBoolean(aVar.B, Boolean.valueOf(videoData3.realmGet$isVip()));
        osObjectBuilder.addString(aVar.C, videoData3.realmGet$showStatus());
        osObjectBuilder.addString(aVar.D, videoData3.realmGet$trackKey());
        osObjectBuilder.addInteger(aVar.E, Long.valueOf(videoData3.realmGet$duration()));
        osObjectBuilder.addInteger(aVar.F, Long.valueOf(videoData3.realmGet$lastUpdateTime()));
        osObjectBuilder.addString(aVar.G, videoData3.realmGet$text());
        osObjectBuilder.addString(aVar.H, videoData3.realmGet$traceId());
        osObjectBuilder.addString(aVar.I, videoData3.realmGet$expId());
        osObjectBuilder.addString(aVar.J, videoData3.realmGet$adInfoJson());
        osObjectBuilder.addString(aVar.K, videoData3.realmGet$titleText());
        osObjectBuilder.addString(aVar.L, videoData3.realmGet$ratingText());
        osObjectBuilder.addString(aVar.M, videoData3.realmGet$pCode());
        osObjectBuilder.updateExistingObject();
        return videoData;
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.d;
    }

    public int hashCode() {
        String path = this.d.getRealm$realm().getPath();
        String name = this.d.getRow$realm().getTable().getName();
        long objectKey = this.d.getRow$realm().getObjectKey();
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
        com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy = (com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxy) obj;
        BaseRealm realm$realm = this.d.getRealm$realm();
        BaseRealm realm$realm2 = com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy.d.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.d.getRow$realm().getTable().getName();
        String name2 = com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy.d.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.d.getRow$realm().getObjectKey() == com_xiaomi_micolauncher_module_homepage_bean_videodatarealmproxy.d.getRow$realm().getObjectKey();
        }
        return false;
    }
}
