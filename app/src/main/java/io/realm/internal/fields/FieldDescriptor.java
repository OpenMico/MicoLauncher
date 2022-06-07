package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public abstract class FieldDescriptor {
    public static final Set<RealmFieldType> ALL_LINK_FIELD_TYPES;
    public static final Set<RealmFieldType> LIST_LINK_FIELD_TYPE;
    public static final Set<RealmFieldType> OBJECT_LINK_FIELD_TYPE;
    public static final Set<RealmFieldType> SIMPLE_LINK_FIELD_TYPES;
    private final List<String> b;
    private final Set<RealmFieldType> c;
    private final Set<RealmFieldType> d;
    private String e;
    private RealmFieldType f;
    private long[] g;
    private long[] h;
    private static final Pattern a = Pattern.compile("\\.");
    public static final Set<RealmFieldType> NO_LINK_FIELD_TYPE = Collections.emptySet();

    /* loaded from: classes5.dex */
    public interface SchemaProxy {
        ColumnInfo getColumnInfo(String str);

        long getNativeTablePtr(String str);

        boolean hasCache();
    }

    protected abstract void compileFieldDescription(List<String> list);

    static {
        HashSet hashSet = new HashSet(3);
        hashSet.add(RealmFieldType.OBJECT);
        hashSet.add(RealmFieldType.LIST);
        hashSet.add(RealmFieldType.LINKING_OBJECTS);
        ALL_LINK_FIELD_TYPES = Collections.unmodifiableSet(hashSet);
        HashSet hashSet2 = new HashSet(2);
        hashSet2.add(RealmFieldType.OBJECT);
        hashSet2.add(RealmFieldType.LIST);
        SIMPLE_LINK_FIELD_TYPES = Collections.unmodifiableSet(hashSet2);
        HashSet hashSet3 = new HashSet(1);
        hashSet3.add(RealmFieldType.LIST);
        LIST_LINK_FIELD_TYPE = Collections.unmodifiableSet(hashSet3);
        HashSet hashSet4 = new HashSet(1);
        hashSet4.add(RealmFieldType.OBJECT);
        OBJECT_LINK_FIELD_TYPE = Collections.unmodifiableSet(hashSet4);
    }

    public static FieldDescriptor createStandardFieldDescriptor(SchemaProxy schemaProxy, Table table, String str, RealmFieldType... realmFieldTypeArr) {
        return createFieldDescriptor(schemaProxy, table, str, null, new HashSet(Arrays.asList(realmFieldTypeArr)));
    }

    public static FieldDescriptor createFieldDescriptor(SchemaProxy schemaProxy, Table table, String str, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        FieldDescriptor fieldDescriptor;
        if (schemaProxy == null || !schemaProxy.hasCache()) {
            if (set == null) {
                set = SIMPLE_LINK_FIELD_TYPES;
            }
            fieldDescriptor = new b(table, str, set, set2);
        } else {
            String className = table.getClassName();
            if (set == null) {
                set = ALL_LINK_FIELD_TYPES;
            }
            fieldDescriptor = new a(schemaProxy, className, str, set, set2);
        }
        return fieldDescriptor;
    }

    public FieldDescriptor(String str, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        this.b = a(str);
        if (this.b.size() > 0) {
            this.c = set;
            this.d = set2;
            return;
        }
        throw new IllegalArgumentException("Invalid query: Empty field descriptor");
    }

    public final int length() {
        return this.b.size();
    }

    public final long[] getColumnKeys() {
        a();
        long[] jArr = this.g;
        return Arrays.copyOf(jArr, jArr.length);
    }

    public final long[] getNativeTablePointers() {
        a();
        long[] jArr = this.h;
        return Arrays.copyOf(jArr, jArr.length);
    }

    public final String getFinalColumnName() {
        a();
        return this.e;
    }

    public final RealmFieldType getFinalColumnType() {
        a();
        return this.f;
    }

    protected final void verifyInternalColumnType(String str, String str2, RealmFieldType realmFieldType) {
        a(str, str2, realmFieldType, this.c);
    }

    protected final void setCompilationResults(String str, String str2, RealmFieldType realmFieldType, long[] jArr, long[] jArr2) {
        Set<RealmFieldType> set = this.d;
        if (set != null && set.size() > 0) {
            a(str, str2, realmFieldType, this.d);
        }
        this.e = str2;
        this.f = realmFieldType;
        this.g = jArr;
        this.h = jArr2;
    }

    private List<String> a(String str) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Invalid query: field name is empty");
        }
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf == str.length() - 1) {
            throw new IllegalArgumentException("Invalid query: field name must not end with a period ('.')");
        } else if (lastIndexOf > -1) {
            return Arrays.asList(a.split(str));
        } else {
            return Collections.singletonList(str);
        }
    }

    private void a(String str, String str2, RealmFieldType realmFieldType, Set<RealmFieldType> set) {
        if (!set.contains(realmFieldType)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' in class '%s' is of invalid type '%s'.", str2, str, realmFieldType.toString()));
        }
    }

    private void a() {
        if (this.f == null) {
            compileFieldDescription(this.b);
        }
    }
}
