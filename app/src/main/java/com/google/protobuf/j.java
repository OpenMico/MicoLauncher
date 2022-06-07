package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.FieldSet.FieldDescriptorLite;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExtensionSchema.java */
/* loaded from: classes2.dex */
public abstract class j<T extends FieldSet.FieldDescriptorLite<T>> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int a(Map.Entry<?, ?> entry);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract FieldSet<T> a(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Object a(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <UT, UB> UB a(ak akVar, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet, UB ub, ar<UT, UB> arVar) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(Writer writer, Map.Entry<?, ?> entry) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(ak akVar, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a(MessageLite messageLite);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract FieldSet<T> b(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void c(Object obj);
}
