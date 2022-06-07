package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: ExtensionSchemaLite.java */
/* loaded from: classes2.dex */
final class k extends j<GeneratedMessageLite.a> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public boolean a(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public FieldSet<GeneratedMessageLite.a> a(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public FieldSet<GeneratedMessageLite.a> b(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public void c(Object obj) {
        a(obj).d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public <UT, UB> UB a(ak akVar, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.a> fieldSet, UB ub, ar<UT, UB> arVar) throws IOException {
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        int number = generatedExtension.getNumber();
        if (!generatedExtension.d.isRepeated() || !generatedExtension.d.isPacked()) {
            Object obj2 = null;
            if (generatedExtension.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (generatedExtension.getLiteType()) {
                    case DOUBLE:
                        obj2 = Double.valueOf(akVar.d());
                        break;
                    case FLOAT:
                        obj2 = Float.valueOf(akVar.e());
                        break;
                    case INT64:
                        obj2 = Long.valueOf(akVar.g());
                        break;
                    case UINT64:
                        obj2 = Long.valueOf(akVar.f());
                        break;
                    case INT32:
                        obj2 = Integer.valueOf(akVar.h());
                        break;
                    case FIXED64:
                        obj2 = Long.valueOf(akVar.i());
                        break;
                    case FIXED32:
                        obj2 = Integer.valueOf(akVar.j());
                        break;
                    case BOOL:
                        obj2 = Boolean.valueOf(akVar.k());
                        break;
                    case UINT32:
                        obj2 = Integer.valueOf(akVar.o());
                        break;
                    case SFIXED32:
                        obj2 = Integer.valueOf(akVar.q());
                        break;
                    case SFIXED64:
                        obj2 = Long.valueOf(akVar.r());
                        break;
                    case SINT32:
                        obj2 = Integer.valueOf(akVar.s());
                        break;
                    case SINT64:
                        obj2 = Long.valueOf(akVar.t());
                        break;
                    case ENUM:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case BYTES:
                        obj2 = akVar.n();
                        break;
                    case STRING:
                        obj2 = akVar.l();
                        break;
                    case GROUP:
                        obj2 = akVar.b(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                    case MESSAGE:
                        obj2 = akVar.a(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                }
            } else {
                int h = akVar.h();
                if (generatedExtension.d.getEnumType().findValueByNumber(h) == null) {
                    return (UB) ao.a(number, h, ub, arVar);
                }
                obj2 = Integer.valueOf(h);
            }
            if (generatedExtension.isRepeated()) {
                fieldSet.b((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, obj2);
            } else {
                switch (generatedExtension.getLiteType()) {
                    case GROUP:
                    case MESSAGE:
                        Object b = fieldSet.b((FieldSet<GeneratedMessageLite.a>) generatedExtension.d);
                        if (b != null) {
                            obj2 = Internal.a(b, obj2);
                            break;
                        }
                        break;
                }
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, obj2);
            }
        } else {
            switch (generatedExtension.getLiteType()) {
                case DOUBLE:
                    arrayList = new ArrayList();
                    akVar.a(arrayList);
                    break;
                case FLOAT:
                    arrayList = new ArrayList();
                    akVar.b(arrayList);
                    break;
                case INT64:
                    arrayList = new ArrayList();
                    akVar.d(arrayList);
                    break;
                case UINT64:
                    arrayList = new ArrayList();
                    akVar.c(arrayList);
                    break;
                case INT32:
                    arrayList = new ArrayList();
                    akVar.e(arrayList);
                    break;
                case FIXED64:
                    arrayList = new ArrayList();
                    akVar.f(arrayList);
                    break;
                case FIXED32:
                    arrayList = new ArrayList();
                    akVar.g(arrayList);
                    break;
                case BOOL:
                    arrayList = new ArrayList();
                    akVar.h(arrayList);
                    break;
                case UINT32:
                    arrayList = new ArrayList();
                    akVar.l(arrayList);
                    break;
                case SFIXED32:
                    arrayList = new ArrayList();
                    akVar.n(arrayList);
                    break;
                case SFIXED64:
                    arrayList = new ArrayList();
                    akVar.o(arrayList);
                    break;
                case SINT32:
                    arrayList = new ArrayList();
                    akVar.p(arrayList);
                    break;
                case SINT64:
                    arrayList = new ArrayList();
                    akVar.q(arrayList);
                    break;
                case ENUM:
                    arrayList = new ArrayList();
                    akVar.m(arrayList);
                    ub = (UB) ao.a(number, arrayList, generatedExtension.d.getEnumType(), ub, arVar);
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + generatedExtension.d.getLiteType());
            }
            fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, arrayList);
        }
        return ub;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public int a(Map.Entry<?, ?> entry) {
        return ((GeneratedMessageLite.a) entry.getKey()).getNumber();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public void a(Writer writer, Map.Entry<?, ?> entry) throws IOException {
        GeneratedMessageLite.a aVar = (GeneratedMessageLite.a) entry.getKey();
        if (aVar.isRepeated()) {
            switch (aVar.getLiteType()) {
                case DOUBLE:
                    ao.a(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case FLOAT:
                    ao.b(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case INT64:
                    ao.c(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case UINT64:
                    ao.d(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case INT32:
                    ao.h(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case FIXED64:
                    ao.f(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case FIXED32:
                    ao.k(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case BOOL:
                    ao.n(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case UINT32:
                    ao.i(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case SFIXED32:
                    ao.l(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case SFIXED64:
                    ao.g(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case SINT32:
                    ao.j(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case SINT64:
                    ao.e(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case ENUM:
                    ao.h(aVar.getNumber(), (List) entry.getValue(), writer, aVar.isPacked());
                    return;
                case BYTES:
                    ao.b(aVar.getNumber(), (List) entry.getValue(), writer);
                    return;
                case STRING:
                    ao.a(aVar.getNumber(), (List) entry.getValue(), writer);
                    return;
                case GROUP:
                    List list = (List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        ao.b(aVar.getNumber(), (List) entry.getValue(), writer, ah.a().a((Class) list.get(0).getClass()));
                        return;
                    }
                    return;
                case MESSAGE:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        ao.a(aVar.getNumber(), (List) entry.getValue(), writer, ah.a().a((Class) list2.get(0).getClass()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            switch (aVar.getLiteType()) {
                case DOUBLE:
                    writer.a(aVar.getNumber(), ((Double) entry.getValue()).doubleValue());
                    return;
                case FLOAT:
                    writer.a(aVar.getNumber(), ((Float) entry.getValue()).floatValue());
                    return;
                case INT64:
                    writer.a(aVar.getNumber(), ((Long) entry.getValue()).longValue());
                    return;
                case UINT64:
                    writer.c(aVar.getNumber(), ((Long) entry.getValue()).longValue());
                    return;
                case INT32:
                    writer.c(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case FIXED64:
                    writer.d(aVar.getNumber(), ((Long) entry.getValue()).longValue());
                    return;
                case FIXED32:
                    writer.d(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case BOOL:
                    writer.a(aVar.getNumber(), ((Boolean) entry.getValue()).booleanValue());
                    return;
                case UINT32:
                    writer.e(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case SFIXED32:
                    writer.a(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case SFIXED64:
                    writer.b(aVar.getNumber(), ((Long) entry.getValue()).longValue());
                    return;
                case SINT32:
                    writer.f(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case SINT64:
                    writer.e(aVar.getNumber(), ((Long) entry.getValue()).longValue());
                    return;
                case ENUM:
                    writer.c(aVar.getNumber(), ((Integer) entry.getValue()).intValue());
                    return;
                case BYTES:
                    writer.a(aVar.getNumber(), (ByteString) entry.getValue());
                    return;
                case STRING:
                    writer.a(aVar.getNumber(), (String) entry.getValue());
                    return;
                case GROUP:
                    writer.b(aVar.getNumber(), entry.getValue(), ah.a().a((Class) entry.getValue().getClass()));
                    return;
                case MESSAGE:
                    writer.a(aVar.getNumber(), entry.getValue(), ah.a().a((Class) entry.getValue().getClass()));
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public Object a(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i) {
        return extensionRegistryLite.findLiteExtensionByNumber(messageLite, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public void a(ak akVar, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.a> fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, akVar.a(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.j
    public void a(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.a> fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        MessageLite buildPartial = generatedExtension.getMessageDefaultInstance().newBuilderForType().buildPartial();
        d a = d.a(ByteBuffer.wrap(byteString.toByteArray()), true);
        ah.a().a(buildPartial, a, extensionRegistryLite);
        fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, buildPartial);
        if (a.a() != Integer.MAX_VALUE) {
            throw InvalidProtocolBufferException.e();
        }
    }
}
