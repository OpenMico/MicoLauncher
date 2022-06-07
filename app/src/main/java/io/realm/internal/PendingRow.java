package io.realm.internal;

import io.realm.FrozenPendingRow;
import io.realm.RealmChangeListener;
import io.realm.RealmFieldType;
import io.realm.internal.core.DescriptorOrdering;
import java.lang.ref.WeakReference;
import java.util.Date;

/* loaded from: classes5.dex */
public class PendingRow implements Row {
    private OsSharedRealm a;
    private OsResults b;
    private RealmChangeListener<PendingRow> c = new RealmChangeListener<PendingRow>() { // from class: io.realm.internal.PendingRow.1
        /* renamed from: a */
        public void onChange(PendingRow pendingRow) {
            PendingRow.this.b();
        }
    };
    private WeakReference<FrontEnd> d;
    private boolean e;

    /* loaded from: classes5.dex */
    public interface FrontEnd {
        void onQueryFinished(Row row);
    }

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return false;
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        return false;
    }

    public PendingRow(OsSharedRealm osSharedRealm, TableQuery tableQuery, DescriptorOrdering descriptorOrdering, boolean z) {
        this.a = osSharedRealm;
        this.b = OsResults.createFromQuery(osSharedRealm, tableQuery, descriptorOrdering);
        this.b.addListener((OsResults) this, (RealmChangeListener<OsResults>) this.c);
        this.e = z;
        osSharedRealm.addPendingRow(this);
    }

    public void setFrontEnd(FrontEnd frontEnd) {
        this.d = new WeakReference<>(frontEnd);
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public boolean isNullLink(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public OsList getModelList(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setString(long j, String str) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, byte[] bArr) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public boolean isNull(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void setNull(long j) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override // io.realm.internal.Row
    public Row freeze(OsSharedRealm osSharedRealm) {
        return FrozenPendingRow.INSTANCE;
    }

    private void a() {
        this.b.removeListener((OsResults) this, (RealmChangeListener<OsResults>) this.c);
        this.b = null;
        this.c = null;
        this.a.removePendingRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        WeakReference<FrontEnd> weakReference = this.d;
        if (weakReference != null) {
            FrontEnd frontEnd = weakReference.get();
            if (frontEnd == null) {
                a();
            } else if (this.b.isValid()) {
                UncheckedRow firstUncheckedRow = this.b.firstUncheckedRow();
                a();
                if (firstUncheckedRow != null) {
                    if (this.e) {
                        firstUncheckedRow = CheckedRow.getFromRow(firstUncheckedRow);
                    }
                    frontEnd.onQueryFinished(firstUncheckedRow);
                    return;
                }
                frontEnd.onQueryFinished(InvalidRow.INSTANCE);
            } else {
                a();
            }
        } else {
            throw new IllegalStateException("The 'frontEnd' has not been set.");
        }
    }

    public void executeQuery() {
        if (this.b != null) {
            b();
            return;
        }
        throw new IllegalStateException("The query has been executed. This 'PendingRow' is not valid anymore.");
    }
}
