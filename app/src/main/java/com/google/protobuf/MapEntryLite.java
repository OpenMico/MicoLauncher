package com.google.protobuf;

import com.google.protobuf.MessageLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class MapEntryLite<K, V> {
    private final a<K, V> a;
    private final K b;
    private final V c;

    /* loaded from: classes2.dex */
    public static class a<K, V> {
        public final WireFormat.FieldType a;
        public final K b;
        public final WireFormat.FieldType c;
        public final V d;

        public a(WireFormat.FieldType fieldType, K k, WireFormat.FieldType fieldType2, V v) {
            this.a = fieldType;
            this.b = k;
            this.c = fieldType2;
            this.d = v;
        }
    }

    private MapEntryLite(WireFormat.FieldType fieldType, K k, WireFormat.FieldType fieldType2, V v) {
        this.a = new a<>(fieldType, k, fieldType2, v);
        this.b = k;
        this.c = v;
    }

    public K getKey() {
        return this.b;
    }

    public V getValue() {
        return this.c;
    }

    public static <K, V> MapEntryLite<K, V> newDefaultInstance(WireFormat.FieldType fieldType, K k, WireFormat.FieldType fieldType2, V v) {
        return new MapEntryLite<>(fieldType, k, fieldType2, v);
    }

    public static <K, V> void a(CodedOutputStream codedOutputStream, a<K, V> aVar, K k, V v) throws IOException {
        FieldSet.a(codedOutputStream, aVar.a, 1, k);
        FieldSet.a(codedOutputStream, aVar.c, 2, v);
    }

    public static <K, V> int a(a<K, V> aVar, K k, V v) {
        return FieldSet.a(aVar.a, 1, k) + FieldSet.a(aVar.c, 2, v);
    }

    static <T> T a(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, WireFormat.FieldType fieldType, T t) throws IOException {
        switch (fieldType) {
            case MESSAGE:
                MessageLite.Builder builder = ((MessageLite) t).toBuilder();
                codedInputStream.readMessage(builder, extensionRegistryLite);
                return (T) builder.buildPartial();
            case ENUM:
                return (T) Integer.valueOf(codedInputStream.readEnum());
            case GROUP:
                throw new RuntimeException("Groups are not allowed in maps.");
            default:
                return (T) FieldSet.a(codedInputStream, fieldType, true);
        }
    }

    public void serializeTo(CodedOutputStream codedOutputStream, int i, K k, V v) throws IOException {
        codedOutputStream.writeTag(i, 2);
        codedOutputStream.writeUInt32NoTag(a(this.a, k, v));
        a(codedOutputStream, this.a, k, v);
    }

    public int computeMessageSize(int i, K k, V v) {
        return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(a(this.a, k, v));
    }

    public Map.Entry<K, V> parseEntry(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return a(byteString.newCodedInput(), this.a, extensionRegistryLite);
    }

    static <K, V> Map.Entry<K, V> a(CodedInputStream codedInputStream, a<K, V> aVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        Object obj = aVar.b;
        Object obj2 = aVar.d;
        while (true) {
            int readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == WireFormat.a(1, aVar.a.getWireType())) {
                obj = a(codedInputStream, extensionRegistryLite, aVar.a, obj);
            } else if (readTag == WireFormat.a(2, aVar.c.getWireType())) {
                obj2 = a(codedInputStream, extensionRegistryLite, aVar.c, obj2);
            } else if (!codedInputStream.skipField(readTag)) {
                break;
            }
        }
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void parseInto(MapFieldLite<K, V> mapFieldLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int pushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
        Object obj = this.a.b;
        Object obj2 = this.a.d;
        while (true) {
            int readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            } else if (readTag == WireFormat.a(1, this.a.a.getWireType())) {
                obj = a(codedInputStream, extensionRegistryLite, this.a.a, obj);
            } else if (readTag == WireFormat.a(2, this.a.c.getWireType())) {
                obj2 = a(codedInputStream, extensionRegistryLite, this.a.c, obj2);
            } else if (!codedInputStream.skipField(readTag)) {
                break;
            }
        }
        codedInputStream.checkLastTagWas(0);
        codedInputStream.popLimit(pushLimit);
        mapFieldLite.put(obj, obj2);
    }

    public a<K, V> a() {
        return this.a;
    }
}
