package io.realm.internal;

import com.xiaomi.mipush.sdk.Constants;
import io.realm.RealmFieldType;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public abstract class ColumnInfo {
    private final Map<String, ColumnDetails> a;
    private final Map<String, ColumnDetails> b;
    private final Map<String, String> c;
    private final boolean d;

    protected abstract ColumnInfo copy(boolean z);

    protected abstract void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2);

    /* loaded from: classes5.dex */
    public static final class ColumnDetails {
        public final long columnKey;
        public final RealmFieldType columnType;
        public final String linkedClassName;

        private ColumnDetails(long j, RealmFieldType realmFieldType, @Nullable String str) {
            this.columnKey = j;
            this.columnType = realmFieldType;
            this.linkedClassName = str;
        }

        ColumnDetails(Property property) {
            this(property.getColumnKey(), property.getType(), property.getLinkedObjectName());
        }

        public String toString() {
            return "ColumnDetails[" + this.columnKey + ", " + this.columnType + ", " + this.linkedClassName + "]";
        }
    }

    public ColumnInfo(int i) {
        this(i, true);
    }

    public ColumnInfo(@Nullable ColumnInfo columnInfo, boolean z) {
        this(columnInfo == null ? 0 : columnInfo.a.size(), z);
        if (columnInfo != null) {
            this.a.putAll(columnInfo.a);
        }
    }

    private ColumnInfo(int i, boolean z) {
        this.a = new HashMap(i);
        this.b = new HashMap(i);
        this.c = new HashMap(i);
        this.d = z;
    }

    public final boolean isMutable() {
        return this.d;
    }

    public long getColumnKey(String str) {
        ColumnDetails columnDetails = this.a.get(str);
        if (columnDetails == null) {
            return -1L;
        }
        return columnDetails.columnKey;
    }

    @Nullable
    public ColumnDetails getColumnDetails(String str) {
        return this.a.get(str);
    }

    @Nullable
    public String getInternalFieldName(String str) {
        return this.c.get(str);
    }

    public void copyFrom(ColumnInfo columnInfo) {
        if (!this.d) {
            throw new UnsupportedOperationException("Attempt to modify an immutable ColumnInfo");
        } else if (columnInfo != null) {
            this.a.clear();
            this.a.putAll(columnInfo.a);
            this.b.clear();
            this.b.putAll(columnInfo.b);
            this.c.clear();
            this.c.putAll(columnInfo.c);
            copy(columnInfo, this);
        } else {
            throw new NullPointerException("Attempt to copy null ColumnInfo");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnInfo[");
        sb.append("mutable=" + this.d);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        boolean z = false;
        if (this.a != null) {
            sb.append("JavaFieldNames=[");
            boolean z2 = false;
            for (Map.Entry<String, ColumnDetails> entry : this.a.entrySet()) {
                if (z2) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                sb.append(entry.getKey());
                sb.append("->");
                sb.append(entry.getValue());
                z2 = true;
            }
            sb.append("]");
        }
        if (this.b != null) {
            sb.append(", InternalFieldNames=[");
            for (Map.Entry<String, ColumnDetails> entry2 : this.b.entrySet()) {
                if (z) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                sb.append(entry2.getKey());
                sb.append("->");
                sb.append(entry2.getValue());
                z = true;
            }
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    protected final long addColumnDetails(String str, String str2, OsObjectSchemaInfo osObjectSchemaInfo) {
        Property property = osObjectSchemaInfo.getProperty(str2);
        ColumnDetails columnDetails = new ColumnDetails(property);
        this.a.put(str, columnDetails);
        this.b.put(str2, columnDetails);
        this.c.put(str, str2);
        return property.getColumnKey();
    }

    protected final void addBacklinkDetails(OsSchemaInfo osSchemaInfo, String str, String str2, String str3) {
        this.a.put(str, new ColumnDetails(osSchemaInfo.getObjectSchemaInfo(str2).getProperty(str3).getColumnKey(), RealmFieldType.LINKING_OBJECTS, str2));
    }

    public Map<String, ColumnDetails> getColumnKeysMap() {
        return this.a;
    }
}
