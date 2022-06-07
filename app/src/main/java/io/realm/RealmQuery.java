package io.realm;

import io.realm.internal.OsList;
import io.realm.internal.OsResults;
import io.realm.internal.PendingRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SubscriptionAwareOsResults;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import io.realm.internal.core.DescriptorOrdering;
import io.realm.internal.core.QueryDescriptor;
import io.realm.internal.fields.FieldDescriptor;
import io.realm.internal.sync.SubscriptionAction;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class RealmQuery<E> {
    private final Table a;
    private final BaseRealm b;
    private final TableQuery c;
    private final RealmObjectSchema d;
    private Class<E> e;
    private String f;
    private final boolean g;
    private final OsList h;
    private DescriptorOrdering i;

    private static native String nativeSerializeQuery(long j, long j2);

    public static <E extends RealmModel> RealmQuery<E> a(Realm realm, Class<E> cls) {
        return new RealmQuery<>(realm, cls);
    }

    public static <E extends RealmModel> RealmQuery<E> a(DynamicRealm dynamicRealm, String str) {
        return new RealmQuery<>(dynamicRealm, str);
    }

    public static <E> RealmQuery<E> a(RealmResults<E> realmResults) {
        if (realmResults.b == null) {
            return new RealmQuery<>((RealmResults<DynamicRealmObject>) realmResults, realmResults.c);
        }
        return new RealmQuery<>(realmResults, realmResults.b);
    }

    public static <E> RealmQuery<E> a(RealmList<E> realmList) {
        if (realmList.clazz == null) {
            return new RealmQuery<>(realmList.realm, realmList.a(), realmList.className);
        }
        return new RealmQuery<>(realmList.realm, realmList.a(), realmList.clazz);
    }

    private static boolean a(Class<?> cls) {
        return RealmModel.class.isAssignableFrom(cls);
    }

    private RealmQuery(Realm realm, Class<E> cls) {
        this.i = new DescriptorOrdering();
        this.b = realm;
        this.e = cls;
        this.g = !a((Class<?>) cls);
        if (this.g) {
            this.d = null;
            this.a = null;
            this.h = null;
            this.c = null;
            return;
        }
        this.d = realm.getSchema().b((Class<? extends RealmModel>) cls);
        this.a = this.d.a();
        this.h = null;
        this.c = this.a.where();
    }

    private RealmQuery(RealmResults<E> realmResults, Class<E> cls) {
        this.i = new DescriptorOrdering();
        this.b = realmResults.a;
        this.e = cls;
        this.g = !a((Class<?>) cls);
        if (this.g) {
            this.d = null;
            this.a = null;
            this.h = null;
            this.c = null;
            return;
        }
        this.d = this.b.getSchema().b((Class<? extends RealmModel>) cls);
        this.a = realmResults.a();
        this.h = null;
        this.c = realmResults.b().where();
    }

    private RealmQuery(BaseRealm baseRealm, OsList osList, Class<E> cls) {
        this.i = new DescriptorOrdering();
        this.b = baseRealm;
        this.e = cls;
        this.g = !a((Class<?>) cls);
        if (this.g) {
            this.d = null;
            this.a = null;
            this.h = null;
            this.c = null;
            return;
        }
        this.d = baseRealm.getSchema().b((Class<? extends RealmModel>) cls);
        this.a = this.d.a();
        this.h = osList;
        this.c = osList.getQuery();
    }

    private RealmQuery(BaseRealm baseRealm, String str) {
        this.i = new DescriptorOrdering();
        this.b = baseRealm;
        this.f = str;
        this.g = false;
        this.d = baseRealm.getSchema().b(str);
        this.a = this.d.a();
        this.c = this.a.where();
        this.h = null;
    }

    private RealmQuery(RealmResults<DynamicRealmObject> realmResults, String str) {
        this.i = new DescriptorOrdering();
        this.b = realmResults.a;
        this.f = str;
        this.g = false;
        this.d = this.b.getSchema().b(str);
        this.a = this.d.a();
        this.c = realmResults.b().where();
        this.h = null;
    }

    private RealmQuery(BaseRealm baseRealm, OsList osList, String str) {
        this.i = new DescriptorOrdering();
        this.b = baseRealm;
        this.f = str;
        this.g = false;
        this.d = baseRealm.getSchema().b(str);
        this.a = this.d.a();
        this.c = osList.getQuery();
        this.h = osList;
    }

    public boolean isValid() {
        BaseRealm baseRealm = this.b;
        if (baseRealm == null || baseRealm.isClosed()) {
            return false;
        }
        OsList osList = this.h;
        if (osList != null) {
            return osList.isValid();
        }
        Table table = this.a;
        return table != null && table.isValid();
    }

    public RealmQuery<E> isNull(String str) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, new RealmFieldType[0]);
        this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> isNotNull(String str) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, new RealmFieldType[0]);
        this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable String str2) {
        return equalTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> equalTo(String str, @Nullable String str2, Case r4) {
        this.b.checkIfValid();
        return a(str, str2, r4);
    }

    private RealmQuery<E> a(String str, @Nullable String str2, Case r7) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Byte b) {
        this.b.checkIfValid();
        return a(str, b);
    }

    private RealmQuery<E> a(String str, @Nullable Byte b) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (b == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), b.byteValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable byte[] bArr) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.BINARY);
        if (bArr == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), bArr);
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Short sh) {
        this.b.checkIfValid();
        return a(str, sh);
    }

    private RealmQuery<E> a(String str, @Nullable Short sh) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (sh == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), sh.shortValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Integer num) {
        this.b.checkIfValid();
        return a(str, num);
    }

    private RealmQuery<E> a(String str, @Nullable Integer num) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (num == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), num.intValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Long l) {
        this.b.checkIfValid();
        return a(str, l);
    }

    private RealmQuery<E> a(String str, @Nullable Long l) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (l == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), l.longValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Double d) {
        this.b.checkIfValid();
        return a(str, d);
    }

    private RealmQuery<E> a(String str, @Nullable Double d) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        if (d == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), d.doubleValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Float f) {
        this.b.checkIfValid();
        return a(str, f);
    }

    private RealmQuery<E> a(String str, @Nullable Float f) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        if (f == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), f.floatValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Boolean bool) {
        this.b.checkIfValid();
        return a(str, bool);
    }

    private RealmQuery<E> a(String str, @Nullable Boolean bool) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.BOOLEAN);
        if (bool == null) {
            this.c.isNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), bool.booleanValue());
        }
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Date date) {
        this.b.checkIfValid();
        return a(str, date);
    }

    private RealmQuery<E> a(String str, @Nullable Date date) {
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable String[] strArr) {
        return in(str, strArr, Case.SENSITIVE);
    }

    public RealmQuery<E> in(String str, @Nullable String[] strArr, Case r6) {
        this.b.checkIfValid();
        if (strArr == null || strArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, strArr[0], r6);
        for (int i = 1; i < strArr.length; i++) {
            c().a(str, strArr[i], r6);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Byte[] bArr) {
        this.b.checkIfValid();
        if (bArr == null || bArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, bArr[0]);
        for (int i = 1; i < bArr.length; i++) {
            c().a(str, bArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Short[] shArr) {
        this.b.checkIfValid();
        if (shArr == null || shArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, shArr[0]);
        for (int i = 1; i < shArr.length; i++) {
            c().a(str, shArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Integer[] numArr) {
        this.b.checkIfValid();
        if (numArr == null || numArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, numArr[0]);
        for (int i = 1; i < numArr.length; i++) {
            c().a(str, numArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Long[] lArr) {
        this.b.checkIfValid();
        if (lArr == null || lArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, lArr[0]);
        for (int i = 1; i < lArr.length; i++) {
            c().a(str, lArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Double[] dArr) {
        this.b.checkIfValid();
        if (dArr == null || dArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, dArr[0]);
        for (int i = 1; i < dArr.length; i++) {
            c().a(str, dArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Float[] fArr) {
        this.b.checkIfValid();
        if (fArr == null || fArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, fArr[0]);
        for (int i = 1; i < fArr.length; i++) {
            c().a(str, fArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Boolean[] boolArr) {
        this.b.checkIfValid();
        if (boolArr == null || boolArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, boolArr[0]);
        for (int i = 1; i < boolArr.length; i++) {
            c().a(str, boolArr[i]);
        }
        return b();
    }

    public RealmQuery<E> in(String str, @Nullable Date[] dateArr) {
        this.b.checkIfValid();
        if (dateArr == null || dateArr.length == 0) {
            alwaysFalse();
            return this;
        }
        a().a(str, dateArr[0]);
        for (int i = 1; i < dateArr.length; i++) {
            c().a(str, dateArr[i]);
        }
        return b();
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable String str2) {
        return notEqualTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable String str2, Case r8) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        if (a.length() <= 1 || r8.getValue()) {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), str2, r8);
            return this;
        }
        throw new IllegalArgumentException("Link queries cannot be case insensitive - coming soon.");
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Byte b) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (b == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), b.byteValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable byte[] bArr) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.BINARY);
        if (bArr == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), bArr);
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Short sh) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (sh == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), sh.shortValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Integer num) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (num == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), num.intValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Long l) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        if (l == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), l.longValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Double d) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        if (d == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), d.doubleValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Float f) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        if (f == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), f.floatValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Boolean bool) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.BOOLEAN);
        if (bool == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.equalTo(a.getColumnKeys(), a.getNativeTablePointers(), !bool.booleanValue());
        }
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Date date) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        if (date == null) {
            this.c.isNotNull(a.getColumnKeys(), a.getNativeTablePointers());
        } else {
            this.c.notEqualTo(a.getColumnKeys(), a.getNativeTablePointers(), date);
        }
        return this;
    }

    public RealmQuery<E> greaterThan(String str, int i) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.greaterThan(a.getColumnKeys(), a.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, long j) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.greaterThan(a.getColumnKeys(), a.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, double d) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        this.c.greaterThan(a.getColumnKeys(), a.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, float f) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        this.c.greaterThan(a.getColumnKeys(), a.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, Date date) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        this.c.greaterThan(a.getColumnKeys(), a.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, int i) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.greaterThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, long j) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.greaterThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, double d) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        this.c.greaterThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, float f) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        this.c.greaterThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, Date date) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        this.c.greaterThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> lessThan(String str, int i) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.lessThan(a.getColumnKeys(), a.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> lessThan(String str, long j) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.lessThan(a.getColumnKeys(), a.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> lessThan(String str, double d) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        this.c.lessThan(a.getColumnKeys(), a.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> lessThan(String str, float f) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        this.c.lessThan(a.getColumnKeys(), a.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> lessThan(String str, Date date) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        this.c.lessThan(a.getColumnKeys(), a.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, int i) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.lessThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), i);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, long j) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.INTEGER);
        this.c.lessThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), j);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, double d) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DOUBLE);
        this.c.lessThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), d);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, float f) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.FLOAT);
        this.c.lessThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), f);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, Date date) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.DATE);
        this.c.lessThanOrEqual(a.getColumnKeys(), a.getNativeTablePointers(), date);
        return this;
    }

    public RealmQuery<E> between(String str, int i, int i2) {
        this.b.checkIfValid();
        this.c.between(this.d.a(str, RealmFieldType.INTEGER).getColumnKeys(), i, i2);
        return this;
    }

    public RealmQuery<E> between(String str, long j, long j2) {
        this.b.checkIfValid();
        this.c.between(this.d.a(str, RealmFieldType.INTEGER).getColumnKeys(), j, j2);
        return this;
    }

    public RealmQuery<E> between(String str, double d, double d2) {
        this.b.checkIfValid();
        this.c.between(this.d.a(str, RealmFieldType.DOUBLE).getColumnKeys(), d, d2);
        return this;
    }

    public RealmQuery<E> between(String str, float f, float f2) {
        this.b.checkIfValid();
        this.c.between(this.d.a(str, RealmFieldType.FLOAT).getColumnKeys(), f, f2);
        return this;
    }

    public RealmQuery<E> between(String str, Date date, Date date2) {
        this.b.checkIfValid();
        this.c.between(this.d.a(str, RealmFieldType.DATE).getColumnKeys(), date, date2);
        return this;
    }

    public RealmQuery<E> contains(String str, String str2) {
        return contains(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> contains(String str, String str2, Case r7) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        this.c.contains(a.getColumnKeys(), a.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> beginsWith(String str, String str2) {
        return beginsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> beginsWith(String str, String str2, Case r7) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        this.c.beginsWith(a.getColumnKeys(), a.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> endsWith(String str, String str2) {
        return endsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> endsWith(String str, String str2, Case r7) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        this.c.endsWith(a.getColumnKeys(), a.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> like(String str, String str2) {
        return like(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> like(String str, String str2, Case r7) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING);
        this.c.like(a.getColumnKeys(), a.getNativeTablePointers(), str2, r7);
        return this;
    }

    public RealmQuery<E> beginGroup() {
        this.b.checkIfValid();
        return a();
    }

    private RealmQuery<E> a() {
        this.c.group();
        return this;
    }

    public RealmQuery<E> endGroup() {
        this.b.checkIfValid();
        return b();
    }

    private RealmQuery<E> b() {
        this.c.endGroup();
        return this;
    }

    public RealmQuery<E> or() {
        this.b.checkIfValid();
        return c();
    }

    private RealmQuery<E> c() {
        this.c.or();
        return this;
    }

    public RealmQuery<E> and() {
        this.b.checkIfValid();
        return this;
    }

    public RealmQuery<E> not() {
        this.b.checkIfValid();
        this.c.not();
        return this;
    }

    public RealmQuery<E> isEmpty(String str) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING, RealmFieldType.BINARY, RealmFieldType.LIST, RealmFieldType.LINKING_OBJECTS);
        this.c.isEmpty(a.getColumnKeys(), a.getNativeTablePointers());
        return this;
    }

    public RealmQuery<E> isNotEmpty(String str) {
        this.b.checkIfValid();
        FieldDescriptor a = this.d.a(str, RealmFieldType.STRING, RealmFieldType.BINARY, RealmFieldType.LIST, RealmFieldType.LINKING_OBJECTS);
        this.c.isNotEmpty(a.getColumnKeys(), a.getNativeTablePointers());
        return this;
    }

    public Number sum(String str) {
        this.b.checkIfValid();
        long a = this.d.a(str);
        switch (this.a.getColumnType(a)) {
            case INTEGER:
                return Long.valueOf(this.c.sumInt(a));
            case FLOAT:
                return Double.valueOf(this.c.sumFloat(a));
            case DOUBLE:
                return Double.valueOf(this.c.sumDouble(a));
            default:
                throw new IllegalArgumentException(String.format(Locale.US, "Field '%s': type mismatch - %s expected.", str, "int, float or double"));
        }
    }

    public double average(String str) {
        this.b.checkIfValid();
        long a = this.d.a(str);
        switch (this.a.getColumnType(a)) {
            case INTEGER:
                return this.c.averageInt(a);
            case FLOAT:
                return this.c.averageFloat(a);
            case DOUBLE:
                return this.c.averageDouble(a);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, "Field '%s': type mismatch - %s expected.", str, "int, float or double"));
        }
    }

    @Nullable
    public Number min(String str) {
        this.b.checkIfValid();
        long a = this.d.a(str);
        switch (this.a.getColumnType(a)) {
            case INTEGER:
                return this.c.minimumInt(a);
            case FLOAT:
                return this.c.minimumFloat(a);
            case DOUBLE:
                return this.c.minimumDouble(a);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, "Field '%s': type mismatch - %s expected.", str, "int, float or double"));
        }
    }

    @Nullable
    public Date minimumDate(String str) {
        this.b.checkIfValid();
        return this.c.minimumDate(this.d.a(str));
    }

    @Nullable
    public Number max(String str) {
        this.b.checkIfValid();
        long a = this.d.a(str);
        switch (this.a.getColumnType(a)) {
            case INTEGER:
                return this.c.maximumInt(a);
            case FLOAT:
                return this.c.maximumFloat(a);
            case DOUBLE:
                return this.c.maximumDouble(a);
            default:
                throw new IllegalArgumentException(String.format(Locale.US, "Field '%s': type mismatch - %s expected.", str, "int, float or double"));
        }
    }

    @Nullable
    public Date maximumDate(String str) {
        this.b.checkIfValid();
        return this.c.maximumDate(this.d.a(str));
    }

    public long count() {
        this.b.checkIfValid();
        return d().size();
    }

    public RealmResults<E> findAll() {
        this.b.checkIfValid();
        return a(this.c, this.i, true, SubscriptionAction.NO_SUBSCRIPTION);
    }

    private OsResults d() {
        this.b.checkIfValid();
        return a(this.c, this.i, false, SubscriptionAction.NO_SUBSCRIPTION).e;
    }

    public RealmResults<E> findAllAsync() {
        SubscriptionAction subscriptionAction;
        this.b.checkIfValid();
        this.b.sharedRealm.capabilities.checkCanDeliverNotification("Async query cannot be created on current thread.");
        if (!this.b.sharedRealm.isPartial() || this.h != null) {
            subscriptionAction = SubscriptionAction.NO_SUBSCRIPTION;
        } else {
            subscriptionAction = SubscriptionAction.ANONYMOUS_SUBSCRIPTION;
        }
        return a(this.c, this.i, false, subscriptionAction);
    }

    public RealmQuery<E> sort(String str) {
        this.b.checkIfValid();
        return sort(str, Sort.ASCENDING);
    }

    public RealmQuery<E> sort(String str, Sort sort) {
        this.b.checkIfValid();
        return sort(new String[]{str}, new Sort[]{sort});
    }

    public RealmQuery<E> sort(String str, Sort sort, String str2, Sort sort2) {
        this.b.checkIfValid();
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    public RealmQuery<E> sort(String[] strArr, Sort[] sortArr) {
        this.b.checkIfValid();
        this.i.appendSort(QueryDescriptor.getInstanceForSort(g(), this.c.getTable(), strArr, sortArr));
        return this;
    }

    public RealmQuery<E> distinct(String str) {
        return distinct(str, new String[0]);
    }

    public RealmQuery<E> distinct(String str, String... strArr) {
        QueryDescriptor queryDescriptor;
        this.b.checkIfValid();
        if (strArr.length == 0) {
            queryDescriptor = QueryDescriptor.getInstanceForDistinct(g(), this.a, str);
        } else {
            String[] strArr2 = new String[strArr.length + 1];
            strArr2[0] = str;
            System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
            queryDescriptor = QueryDescriptor.getInstanceForDistinct(g(), this.a, strArr2);
        }
        this.i.appendDistinct(queryDescriptor);
        return this;
    }

    public RealmQuery<E> limit(long j) {
        this.b.checkIfValid();
        if (j >= 1) {
            this.i.setLimit(j);
            return this;
        }
        throw new IllegalArgumentException("Only positive numbers above 0 is allowed. Yours was: " + j);
    }

    public RealmQuery<E> alwaysTrue() {
        this.b.checkIfValid();
        this.c.alwaysTrue();
        return this;
    }

    public RealmQuery<E> alwaysFalse() {
        this.b.checkIfValid();
        this.c.alwaysFalse();
        return this;
    }

    public Realm getRealm() {
        BaseRealm baseRealm = this.b;
        if (baseRealm == null) {
            return null;
        }
        baseRealm.checkIfValid();
        BaseRealm baseRealm2 = this.b;
        if (baseRealm2 instanceof Realm) {
            return (Realm) baseRealm2;
        }
        throw new IllegalStateException("This method is only available for typed Realms");
    }

    public String getDescription() {
        return nativeSerializeQuery(this.c.getNativePtr(), this.i.getNativePtr());
    }

    public String getTypeQueried() {
        return this.a.getClassName();
    }

    private boolean e() {
        return this.f != null;
    }

    @Nullable
    public E findFirst() {
        this.b.checkIfValid();
        if (this.g) {
            return null;
        }
        long f = f();
        if (f < 0) {
            return null;
        }
        return (E) this.b.a((Class<RealmModel>) this.e, this.f, f);
    }

    public E findFirstAsync() {
        Row row;
        E e;
        this.b.checkIfValid();
        if (!this.g) {
            this.b.sharedRealm.capabilities.checkCanDeliverNotification("Async query cannot be created on current thread.");
            if (this.b.isInTransaction()) {
                row = OsResults.createFromQuery(this.b.sharedRealm, this.c).firstUncheckedRow();
            } else {
                row = new PendingRow(this.b.sharedRealm, this.c, this.i, e());
            }
            if (e()) {
                e = (E) new DynamicRealmObject(this.b, row);
            } else {
                Class<E> cls = this.e;
                RealmProxyMediator schemaMediator = this.b.getConfiguration().getSchemaMediator();
                BaseRealm baseRealm = this.b;
                e = (E) schemaMediator.newInstance(cls, baseRealm, row, baseRealm.getSchema().c((Class<? extends RealmModel>) cls), false, Collections.emptyList());
            }
            if (row instanceof PendingRow) {
                ((PendingRow) row).setFrontEnd(((RealmObjectProxy) e).realmGet$proxyState());
            }
            return e;
        }
        throw new UnsupportedOperationException("findFirstAsync() available only when type parameter 'E' is implementing RealmModel.");
    }

    private RealmResults<E> a(TableQuery tableQuery, DescriptorOrdering descriptorOrdering, boolean z, SubscriptionAction subscriptionAction) {
        OsResults osResults;
        RealmResults<E> realmResults;
        if (subscriptionAction.shouldCreateSubscriptions()) {
            osResults = SubscriptionAwareOsResults.createFromQuery(this.b.sharedRealm, tableQuery, descriptorOrdering, subscriptionAction);
        } else {
            osResults = OsResults.createFromQuery(this.b.sharedRealm, tableQuery, descriptorOrdering);
        }
        if (e()) {
            realmResults = new RealmResults<>(this.b, osResults, this.f);
        } else {
            realmResults = new RealmResults<>(this.b, osResults, this.e);
        }
        if (z) {
            realmResults.load();
        }
        return realmResults;
    }

    private long f() {
        if (this.i.isEmpty()) {
            return this.c.find();
        }
        RealmObjectProxy realmObjectProxy = (RealmObjectProxy) findAll().first(null);
        if (realmObjectProxy != null) {
            return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
        }
        return -1L;
    }

    private o g() {
        return new o(this.b.getSchema());
    }
}
