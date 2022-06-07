package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.Table;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* compiled from: DynamicFieldDescriptor.java */
/* loaded from: classes5.dex */
class b extends FieldDescriptor {
    private final Table a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Table table, String str, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        super(str, set, set2);
        this.a = table;
    }

    @Override // io.realm.internal.fields.FieldDescriptor
    protected void compileFieldDescription(List<String> list) {
        int size = list.size();
        long[] jArr = new long[size];
        String str = null;
        Table table = this.a;
        String str2 = null;
        RealmFieldType realmFieldType = null;
        for (int i = 0; i < size; i++) {
            str2 = list.get(i);
            if (str2 == null || str2.length() <= 0) {
                throw new IllegalArgumentException("Invalid query: Field descriptor contains an empty field.  A field description may not begin with or contain adjacent periods ('.').");
            }
            str = table.getClassName();
            long columnKey = table.getColumnKey(str2);
            if (columnKey >= 0) {
                realmFieldType = table.getColumnType(columnKey);
                if (i < size - 1) {
                    verifyInternalColumnType(str, str2, realmFieldType);
                    table = table.getLinkTarget(columnKey);
                }
                jArr[i] = columnKey;
            } else {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' not found in table '%s'.", str2, str));
            }
        }
        setCompilationResults(str, str2, realmFieldType, jArr, new long[size]);
    }
}
