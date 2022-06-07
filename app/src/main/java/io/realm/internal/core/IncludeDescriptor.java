package io.realm.internal.core;

import io.realm.RealmFieldType;
import io.realm.internal.NativeObject;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;
import java.util.EnumSet;

/* loaded from: classes5.dex */
public class IncludeDescriptor implements NativeObject {
    private static final long a = nativeGetFinalizerMethodPtr();
    private final long b;

    private static native long nativeCreate(long j, long[] jArr, long[] jArr2);

    private static native long nativeGetFinalizerMethodPtr();

    public static IncludeDescriptor createInstance(FieldDescriptor.SchemaProxy schemaProxy, Table table, String str) {
        FieldDescriptor createFieldDescriptor = FieldDescriptor.createFieldDescriptor(schemaProxy, table, str, EnumSet.of(RealmFieldType.OBJECT, RealmFieldType.LIST, RealmFieldType.LINKING_OBJECTS), EnumSet.of(RealmFieldType.LINKING_OBJECTS));
        return new IncludeDescriptor(table, createFieldDescriptor.getColumnKeys(), createFieldDescriptor.getNativeTablePointers());
    }

    private IncludeDescriptor(Table table, long[] jArr, long[] jArr2) {
        this.b = nativeCreate(table.getNativePtr(), jArr, jArr2);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.b;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }
}
