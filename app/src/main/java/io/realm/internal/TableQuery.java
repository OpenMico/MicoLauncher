package io.realm.internal;

import io.realm.Case;
import java.util.Date;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class TableQuery implements NativeObject {
    private static final long a = nativeGetFinalizerPtr();
    private final NativeContext b;
    private final Table c;
    private final long d;
    private boolean e = true;

    private native void nativeAlwaysFalse(long j);

    private native void nativeAlwaysTrue(long j);

    private native double nativeAverageDouble(long j, long j2);

    private native double nativeAverageFloat(long j, long j2);

    private native double nativeAverageInt(long j, long j2);

    private native void nativeBeginsWith(long j, long[] jArr, long[] jArr2, String str, boolean z);

    private native void nativeBetween(long j, long[] jArr, double d, double d2);

    private native void nativeBetween(long j, long[] jArr, float f, float f2);

    private native void nativeBetween(long j, long[] jArr, long j2, long j3);

    private native void nativeBetweenTimestamp(long j, long[] jArr, long j2, long j3);

    private native void nativeContains(long j, long[] jArr, long[] jArr2, String str, boolean z);

    private native long nativeCount(long j);

    private native void nativeEndGroup(long j);

    private native void nativeEndsWith(long j, long[] jArr, long[] jArr2, String str, boolean z);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, @Nullable String str, boolean z);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, boolean z);

    private native void nativeEqual(long j, long[] jArr, long[] jArr2, byte[] bArr);

    private native void nativeEqualTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native long nativeFind(long j);

    private static native long nativeGetFinalizerPtr();

    private native void nativeGreater(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeGreater(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeGreater(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeGreaterEqual(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeGreaterEqual(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeGreaterEqual(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeGreaterEqualTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeGreaterTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeGroup(long j);

    private native void nativeIsEmpty(long j, long[] jArr, long[] jArr2);

    private native void nativeIsNotEmpty(long j, long[] jArr, long[] jArr2);

    private native void nativeIsNotNull(long j, long[] jArr, long[] jArr2);

    private native void nativeIsNull(long j, long[] jArr, long[] jArr2);

    private native void nativeLess(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeLess(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeLess(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeLessEqual(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeLessEqual(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeLessEqual(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeLessEqualTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeLessTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeLike(long j, long[] jArr, long[] jArr2, String str, boolean z);

    private native Double nativeMaximumDouble(long j, long j2);

    private native Float nativeMaximumFloat(long j, long j2);

    private native Long nativeMaximumInt(long j, long j2);

    private native Long nativeMaximumTimestamp(long j, long j2);

    private native Double nativeMinimumDouble(long j, long j2);

    private native Float nativeMinimumFloat(long j, long j2);

    private native Long nativeMinimumInt(long j, long j2);

    private native Long nativeMinimumTimestamp(long j, long j2);

    private native void nativeNot(long j);

    private native void nativeNotEqual(long j, long[] jArr, long[] jArr2, double d);

    private native void nativeNotEqual(long j, long[] jArr, long[] jArr2, float f);

    private native void nativeNotEqual(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeNotEqual(long j, long[] jArr, long[] jArr2, @Nullable String str, boolean z);

    private native void nativeNotEqual(long j, long[] jArr, long[] jArr2, byte[] bArr);

    private native void nativeNotEqualTimestamp(long j, long[] jArr, long[] jArr2, long j2);

    private native void nativeOr(long j);

    private native long nativeRemove(long j);

    private native double nativeSumDouble(long j, long j2);

    private native double nativeSumFloat(long j, long j2);

    private native long nativeSumInt(long j, long j2);

    private native String nativeValidateQuery(long j);

    public TableQuery(NativeContext nativeContext, Table table, long j) {
        this.b = nativeContext;
        this.c = table;
        this.d = j;
        nativeContext.addReference(this);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.d;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }

    public Table getTable() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (!this.e) {
            String nativeValidateQuery = nativeValidateQuery(this.d);
            if (nativeValidateQuery.equals("")) {
                this.e = true;
                return;
            }
            throw new UnsupportedOperationException(nativeValidateQuery);
        }
    }

    public TableQuery group() {
        nativeGroup(this.d);
        this.e = false;
        return this;
    }

    public TableQuery endGroup() {
        nativeEndGroup(this.d);
        this.e = false;
        return this;
    }

    public TableQuery or() {
        nativeOr(this.d);
        this.e = false;
        return this;
    }

    public TableQuery not() {
        nativeNot(this.d);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, long j) {
        nativeEqual(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, long j) {
        nativeNotEqual(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery greaterThan(long[] jArr, long[] jArr2, long j) {
        nativeGreater(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery greaterThanOrEqual(long[] jArr, long[] jArr2, long j) {
        nativeGreaterEqual(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery lessThan(long[] jArr, long[] jArr2, long j) {
        nativeLess(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery lessThanOrEqual(long[] jArr, long[] jArr2, long j) {
        nativeLessEqual(this.d, jArr, jArr2, j);
        this.e = false;
        return this;
    }

    public TableQuery between(long[] jArr, long j, long j2) {
        nativeBetween(this.d, jArr, j, j2);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, float f) {
        nativeEqual(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, float f) {
        nativeNotEqual(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery greaterThan(long[] jArr, long[] jArr2, float f) {
        nativeGreater(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery greaterThanOrEqual(long[] jArr, long[] jArr2, float f) {
        nativeGreaterEqual(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery lessThan(long[] jArr, long[] jArr2, float f) {
        nativeLess(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery lessThanOrEqual(long[] jArr, long[] jArr2, float f) {
        nativeLessEqual(this.d, jArr, jArr2, f);
        this.e = false;
        return this;
    }

    public TableQuery between(long[] jArr, float f, float f2) {
        nativeBetween(this.d, jArr, f, f2);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, double d) {
        nativeEqual(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, double d) {
        nativeNotEqual(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery greaterThan(long[] jArr, long[] jArr2, double d) {
        nativeGreater(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery greaterThanOrEqual(long[] jArr, long[] jArr2, double d) {
        nativeGreaterEqual(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery lessThan(long[] jArr, long[] jArr2, double d) {
        nativeLess(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery lessThanOrEqual(long[] jArr, long[] jArr2, double d) {
        nativeLessEqual(this.d, jArr, jArr2, d);
        this.e = false;
        return this;
    }

    public TableQuery between(long[] jArr, double d, double d2) {
        nativeBetween(this.d, jArr, d, d2);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, boolean z) {
        nativeEqual(this.d, jArr, jArr2, z);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, @Nullable Date date) {
        if (date == null) {
            nativeIsNull(this.d, jArr, jArr2);
        } else {
            nativeEqualTimestamp(this.d, jArr, jArr2, date.getTime());
        }
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, Date date) {
        if (date != null) {
            nativeNotEqualTimestamp(this.d, jArr, jArr2, date.getTime());
            this.e = false;
            return this;
        }
        throw new IllegalArgumentException("Date value in query criteria must not be null.");
    }

    public TableQuery greaterThan(long[] jArr, long[] jArr2, Date date) {
        if (date != null) {
            nativeGreaterTimestamp(this.d, jArr, jArr2, date.getTime());
            this.e = false;
            return this;
        }
        throw new IllegalArgumentException("Date value in query criteria must not be null.");
    }

    public TableQuery greaterThanOrEqual(long[] jArr, long[] jArr2, Date date) {
        if (date != null) {
            nativeGreaterEqualTimestamp(this.d, jArr, jArr2, date.getTime());
            this.e = false;
            return this;
        }
        throw new IllegalArgumentException("Date value in query criteria must not be null.");
    }

    public TableQuery lessThan(long[] jArr, long[] jArr2, Date date) {
        if (date != null) {
            nativeLessTimestamp(this.d, jArr, jArr2, date.getTime());
            this.e = false;
            return this;
        }
        throw new IllegalArgumentException("Date value in query criteria must not be null.");
    }

    public TableQuery lessThanOrEqual(long[] jArr, long[] jArr2, Date date) {
        if (date != null) {
            nativeLessEqualTimestamp(this.d, jArr, jArr2, date.getTime());
            this.e = false;
            return this;
        }
        throw new IllegalArgumentException("Date value in query criteria must not be null.");
    }

    public TableQuery between(long[] jArr, Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("Date values in query criteria must not be null.");
        }
        nativeBetweenTimestamp(this.d, jArr, date.getTime(), date2.getTime());
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, byte[] bArr) {
        nativeEqual(this.d, jArr, jArr2, bArr);
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, byte[] bArr) {
        nativeNotEqual(this.d, jArr, jArr2, bArr);
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, @Nullable String str, Case r11) {
        nativeEqual(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery equalTo(long[] jArr, long[] jArr2, String str) {
        nativeEqual(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, @Nullable String str, Case r11) {
        nativeNotEqual(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery notEqualTo(long[] jArr, long[] jArr2, @Nullable String str) {
        nativeNotEqual(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery beginsWith(long[] jArr, long[] jArr2, String str, Case r11) {
        nativeBeginsWith(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery beginsWith(long[] jArr, long[] jArr2, String str) {
        nativeBeginsWith(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery endsWith(long[] jArr, long[] jArr2, String str, Case r11) {
        nativeEndsWith(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery endsWith(long[] jArr, long[] jArr2, String str) {
        nativeEndsWith(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery like(long[] jArr, long[] jArr2, String str, Case r11) {
        nativeLike(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery like(long[] jArr, long[] jArr2, String str) {
        nativeLike(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery contains(long[] jArr, long[] jArr2, String str, Case r11) {
        nativeContains(this.d, jArr, jArr2, str, r11.getValue());
        this.e = false;
        return this;
    }

    public TableQuery contains(long[] jArr, long[] jArr2, String str) {
        nativeContains(this.d, jArr, jArr2, str, true);
        this.e = false;
        return this;
    }

    public TableQuery isEmpty(long[] jArr, long[] jArr2) {
        nativeIsEmpty(this.d, jArr, jArr2);
        this.e = false;
        return this;
    }

    public TableQuery isNotEmpty(long[] jArr, long[] jArr2) {
        nativeIsNotEmpty(this.d, jArr, jArr2);
        this.e = false;
        return this;
    }

    public long find() {
        a();
        return nativeFind(this.d);
    }

    public long sumInt(long j) {
        a();
        return nativeSumInt(this.d, j);
    }

    public Long maximumInt(long j) {
        a();
        return nativeMaximumInt(this.d, j);
    }

    public Long minimumInt(long j) {
        a();
        return nativeMinimumInt(this.d, j);
    }

    public double averageInt(long j) {
        a();
        return nativeAverageInt(this.d, j);
    }

    public double sumFloat(long j) {
        a();
        return nativeSumFloat(this.d, j);
    }

    public Float maximumFloat(long j) {
        a();
        return nativeMaximumFloat(this.d, j);
    }

    public Float minimumFloat(long j) {
        a();
        return nativeMinimumFloat(this.d, j);
    }

    public double averageFloat(long j) {
        a();
        return nativeAverageFloat(this.d, j);
    }

    public double sumDouble(long j) {
        a();
        return nativeSumDouble(this.d, j);
    }

    public Double maximumDouble(long j) {
        a();
        return nativeMaximumDouble(this.d, j);
    }

    public Double minimumDouble(long j) {
        a();
        return nativeMinimumDouble(this.d, j);
    }

    public double averageDouble(long j) {
        a();
        return nativeAverageDouble(this.d, j);
    }

    public Date maximumDate(long j) {
        a();
        Long nativeMaximumTimestamp = nativeMaximumTimestamp(this.d, j);
        if (nativeMaximumTimestamp != null) {
            return new Date(nativeMaximumTimestamp.longValue());
        }
        return null;
    }

    public Date minimumDate(long j) {
        a();
        Long nativeMinimumTimestamp = nativeMinimumTimestamp(this.d, j);
        if (nativeMinimumTimestamp != null) {
            return new Date(nativeMinimumTimestamp.longValue());
        }
        return null;
    }

    public TableQuery isNull(long[] jArr, long[] jArr2) {
        nativeIsNull(this.d, jArr, jArr2);
        this.e = false;
        return this;
    }

    public TableQuery isNotNull(long[] jArr, long[] jArr2) {
        nativeIsNotNull(this.d, jArr, jArr2);
        this.e = false;
        return this;
    }

    @Deprecated
    public long count() {
        a();
        return nativeCount(this.d);
    }

    public long remove() {
        a();
        if (this.c.a()) {
            b();
        }
        return nativeRemove(this.d);
    }

    private void b() {
        throw new IllegalStateException("Mutable method call during read transaction.");
    }

    public void alwaysTrue() {
        nativeAlwaysTrue(this.d);
    }

    public void alwaysFalse() {
        nativeAlwaysFalse(this.d);
    }
}
