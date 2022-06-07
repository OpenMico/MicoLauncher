package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.ColumnInfo;
import io.realm.internal.fields.FieldDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* compiled from: CachedFieldDescriptor.java */
/* loaded from: classes5.dex */
class a extends FieldDescriptor {
    private final FieldDescriptor.SchemaProxy a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(FieldDescriptor.SchemaProxy schemaProxy, String str, String str2, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        super(str2, set, set2);
        this.b = str;
        this.a = schemaProxy;
    }

    @Override // io.realm.internal.fields.FieldDescriptor
    protected void compileFieldDescription(List<String> list) {
        int size = list.size();
        long[] jArr = new long[size];
        long[] jArr2 = new long[size];
        String str = null;
        RealmFieldType realmFieldType = null;
        String str2 = this.b;
        int i = 0;
        while (i < size) {
            str = list.get(i);
            if (str == null || str.length() <= 0) {
                throw new IllegalArgumentException("Invalid query: Field descriptor contains an empty field.  A field description may not begin with or contain adjacent periods ('.').");
            }
            ColumnInfo columnInfo = this.a.getColumnInfo(str2);
            if (columnInfo != null) {
                ColumnInfo.ColumnDetails columnDetails = columnInfo.getColumnDetails(str);
                if (columnDetails != null) {
                    RealmFieldType realmFieldType2 = columnDetails.columnType;
                    if (i < size - 1) {
                        verifyInternalColumnType(str2, str, realmFieldType2);
                        str2 = columnDetails.linkedClassName;
                    }
                    jArr[i] = columnDetails.columnKey;
                    jArr2[i] = realmFieldType2 != RealmFieldType.LINKING_OBJECTS ? 0L : this.a.getNativeTablePtr(columnDetails.linkedClassName);
                    i++;
                    realmFieldType = realmFieldType2;
                } else {
                    throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' not found in class '%s'.", str, str2));
                }
            } else {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: class '%s' not found in this schema.", str2));
            }
        }
        setCompilationResults(str2, str, realmFieldType, jArr, jArr2);
    }
}
