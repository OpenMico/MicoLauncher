package com.google.protobuf;

import com.google.protobuf.FieldSet.FieldDescriptorLite;
import com.google.protobuf.Internal;
import com.google.protobuf.LazyField;
import com.google.protobuf.MessageLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class FieldSet<T extends FieldDescriptorLite<T>> {
    private static final FieldSet d = new FieldSet(true);
    private final ap<T, Object> a;
    private boolean b;
    private boolean c;

    /* loaded from: classes2.dex */
    public interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T> {
        Internal.EnumLiteMap<?> getEnumType();

        WireFormat.JavaType getLiteJavaType();

        WireFormat.FieldType getLiteType();

        int getNumber();

        MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite);

        boolean isPacked();

        boolean isRepeated();
    }

    private FieldSet() {
        this.a = ap.a(16);
    }

    private FieldSet(boolean z) {
        this(ap.a(0));
        d();
    }

    private FieldSet(ap<T, Object> apVar) {
        this.a = apVar;
        d();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> a() {
        return new FieldSet<>();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> b() {
        return d;
    }

    public boolean c() {
        return this.a.isEmpty();
    }

    public void d() {
        if (!this.b) {
            this.a.a();
            this.b = true;
        }
    }

    public boolean e() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FieldSet)) {
            return false;
        }
        return this.a.equals(((FieldSet) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    /* renamed from: f */
    public FieldSet<T> clone() {
        FieldSet<T> a = a();
        for (int i = 0; i < this.a.c(); i++) {
            Map.Entry<T, Object> b = this.a.b(i);
            a.a((FieldSet<T>) b.getKey(), b.getValue());
        }
        for (Map.Entry<T, Object> entry : this.a.e()) {
            a.a((FieldSet<T>) entry.getKey(), entry.getValue());
        }
        a.c = this.c;
        return a;
    }

    public Iterator<Map.Entry<T, Object>> g() {
        if (this.c) {
            return new LazyField.b(this.a.entrySet().iterator());
        }
        return this.a.entrySet().iterator();
    }

    public Iterator<Map.Entry<T, Object>> h() {
        if (this.c) {
            return new LazyField.b(this.a.f().iterator());
        }
        return this.a.f().iterator();
    }

    public boolean a(T t) {
        if (!t.isRepeated()) {
            return this.a.get(t) != null;
        }
        throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
    }

    public Object b(T t) {
        Object obj = this.a.get(t);
        return obj instanceof LazyField ? ((LazyField) obj).getValue() : obj;
    }

    public void a(T t, Object obj) {
        if (!t.isRepeated()) {
            b(t.getLiteType(), obj);
        } else if (obj instanceof List) {
            ArrayList<Object> arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            for (Object obj2 : arrayList) {
                b(t.getLiteType(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof LazyField) {
            this.c = true;
        }
        this.a.a((ap<T, Object>) t, (T) obj);
    }

    public void c(T t) {
        this.a.remove(t);
        if (this.a.isEmpty()) {
            this.c = false;
        }
    }

    public int d(T t) {
        if (t.isRepeated()) {
            Object b = b((FieldSet<T>) t);
            if (b == null) {
                return 0;
            }
            return ((List) b).size();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public Object a(T t, int i) {
        if (t.isRepeated()) {
            Object b = b((FieldSet<T>) t);
            if (b != null) {
                return ((List) b).get(i);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void a(T t, int i, Object obj) {
        if (t.isRepeated()) {
            Object b = b((FieldSet<T>) t);
            if (b != null) {
                b(t.getLiteType(), obj);
                ((List) b).set(i, obj);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void b(T t, Object obj) {
        List list;
        if (t.isRepeated()) {
            b(t.getLiteType(), obj);
            Object b = b((FieldSet<T>) t);
            if (b == null) {
                list = new ArrayList();
                this.a.a((ap<T, Object>) t, (T) list);
            } else {
                list = (List) b;
            }
            list.add(obj);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    private void b(WireFormat.FieldType fieldType, Object obj) {
        if (!c(fieldType, obj)) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private static boolean c(WireFormat.FieldType fieldType, Object obj) {
        Internal.a(obj);
        switch (fieldType.getJavaType()) {
            case INT:
                return obj instanceof Integer;
            case LONG:
                return obj instanceof Long;
            case FLOAT:
                return obj instanceof Float;
            case DOUBLE:
                return obj instanceof Double;
            case BOOLEAN:
                return obj instanceof Boolean;
            case STRING:
                return obj instanceof String;
            case BYTE_STRING:
                return (obj instanceof ByteString) || (obj instanceof byte[]);
            case ENUM:
                return (obj instanceof Integer) || (obj instanceof Internal.EnumLite);
            case MESSAGE:
                return (obj instanceof MessageLite) || (obj instanceof LazyField);
            default:
                return false;
        }
    }

    public boolean i() {
        for (int i = 0; i < this.a.c(); i++) {
            if (!a((Map.Entry) this.a.b(i))) {
                return false;
            }
        }
        for (Map.Entry<T, Object> entry : this.a.e()) {
            if (!a((Map.Entry) entry)) {
                return false;
            }
        }
        return true;
    }

    private static <T extends FieldDescriptorLite<T>> boolean a(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (key.isRepeated()) {
                for (MessageLite messageLite : (List) entry.getValue()) {
                    if (!messageLite.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof MessageLite) {
                    if (!((MessageLite) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof LazyField) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public static int a(WireFormat.FieldType fieldType, boolean z) {
        if (z) {
            return 2;
        }
        return fieldType.getWireType();
    }

    public void a(FieldSet<T> fieldSet) {
        for (int i = 0; i < fieldSet.a.c(); i++) {
            b(fieldSet.a.b(i));
        }
        for (Map.Entry<T, Object> entry : fieldSet.a.e()) {
            b(entry);
        }
    }

    private static Object a(Object obj) {
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private void b(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            value = ((LazyField) value).getValue();
        }
        if (key.isRepeated()) {
            Object b = b((FieldSet<T>) key);
            if (b == null) {
                b = new ArrayList();
            }
            for (Object obj : (List) value) {
                ((List) b).add(a(obj));
            }
            this.a.a((ap<T, Object>) key, (T) b);
        } else if (key.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            Object b2 = b((FieldSet<T>) key);
            if (b2 == null) {
                this.a.a((ap<T, Object>) key, (T) a(value));
                return;
            }
            this.a.a((ap<T, Object>) key, (T) key.internalMergeFrom(((MessageLite) b2).toBuilder(), (MessageLite) value).build());
        } else {
            this.a.a((ap<T, Object>) key, (T) a(value));
        }
    }

    public static Object a(CodedInputStream codedInputStream, WireFormat.FieldType fieldType, boolean z) throws IOException {
        if (z) {
            return WireFormat.a(codedInputStream, fieldType, WireFormat.a.STRICT);
        }
        return WireFormat.a(codedInputStream, fieldType, WireFormat.a.LOOSE);
    }

    public static void a(CodedOutputStream codedOutputStream, WireFormat.FieldType fieldType, int i, Object obj) throws IOException {
        if (fieldType == WireFormat.FieldType.GROUP) {
            codedOutputStream.writeGroup(i, (MessageLite) obj);
            return;
        }
        codedOutputStream.writeTag(i, a(fieldType, false));
        a(codedOutputStream, fieldType, obj);
    }

    static void a(CodedOutputStream codedOutputStream, WireFormat.FieldType fieldType, Object obj) throws IOException {
        switch (fieldType) {
            case DOUBLE:
                codedOutputStream.writeDoubleNoTag(((Double) obj).doubleValue());
                return;
            case FLOAT:
                codedOutputStream.writeFloatNoTag(((Float) obj).floatValue());
                return;
            case INT64:
                codedOutputStream.writeInt64NoTag(((Long) obj).longValue());
                return;
            case UINT64:
                codedOutputStream.writeUInt64NoTag(((Long) obj).longValue());
                return;
            case INT32:
                codedOutputStream.writeInt32NoTag(((Integer) obj).intValue());
                return;
            case FIXED64:
                codedOutputStream.writeFixed64NoTag(((Long) obj).longValue());
                return;
            case FIXED32:
                codedOutputStream.writeFixed32NoTag(((Integer) obj).intValue());
                return;
            case BOOL:
                codedOutputStream.writeBoolNoTag(((Boolean) obj).booleanValue());
                return;
            case GROUP:
                codedOutputStream.writeGroupNoTag((MessageLite) obj);
                return;
            case MESSAGE:
                codedOutputStream.writeMessageNoTag((MessageLite) obj);
                return;
            case STRING:
                if (obj instanceof ByteString) {
                    codedOutputStream.writeBytesNoTag((ByteString) obj);
                    return;
                } else {
                    codedOutputStream.writeStringNoTag((String) obj);
                    return;
                }
            case BYTES:
                if (obj instanceof ByteString) {
                    codedOutputStream.writeBytesNoTag((ByteString) obj);
                    return;
                } else {
                    codedOutputStream.writeByteArrayNoTag((byte[]) obj);
                    return;
                }
            case UINT32:
                codedOutputStream.writeUInt32NoTag(((Integer) obj).intValue());
                return;
            case SFIXED32:
                codedOutputStream.writeSFixed32NoTag(((Integer) obj).intValue());
                return;
            case SFIXED64:
                codedOutputStream.writeSFixed64NoTag(((Long) obj).longValue());
                return;
            case SINT32:
                codedOutputStream.writeSInt32NoTag(((Integer) obj).intValue());
                return;
            case SINT64:
                codedOutputStream.writeSInt64NoTag(((Long) obj).longValue());
                return;
            case ENUM:
                if (obj instanceof Internal.EnumLite) {
                    codedOutputStream.writeEnumNoTag(((Internal.EnumLite) obj).getNumber());
                    return;
                } else {
                    codedOutputStream.writeEnumNoTag(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public static void a(FieldDescriptorLite<?> fieldDescriptorLite, Object obj, CodedOutputStream codedOutputStream) throws IOException {
        WireFormat.FieldType liteType = fieldDescriptorLite.getLiteType();
        int number = fieldDescriptorLite.getNumber();
        if (fieldDescriptorLite.isRepeated()) {
            List<Object> list = (List) obj;
            if (fieldDescriptorLite.isPacked()) {
                codedOutputStream.writeTag(number, 2);
                int i = 0;
                for (Object obj2 : list) {
                    i += a(liteType, obj2);
                }
                codedOutputStream.writeRawVarint32(i);
                for (Object obj3 : list) {
                    a(codedOutputStream, liteType, obj3);
                }
                return;
            }
            for (Object obj4 : list) {
                a(codedOutputStream, liteType, number, obj4);
            }
        } else if (obj instanceof LazyField) {
            a(codedOutputStream, liteType, number, ((LazyField) obj).getValue());
        } else {
            a(codedOutputStream, liteType, number, obj);
        }
    }

    public int j() {
        int i = 0;
        for (int i2 = 0; i2 < this.a.c(); i2++) {
            Map.Entry<T, Object> b = this.a.b(i2);
            i += c((FieldDescriptorLite<?>) b.getKey(), b.getValue());
        }
        for (Map.Entry<T, Object> entry : this.a.e()) {
            i += c((FieldDescriptorLite<?>) entry.getKey(), entry.getValue());
        }
        return i;
    }

    public int k() {
        int i = 0;
        for (int i2 = 0; i2 < this.a.c(); i2++) {
            i += c(this.a.b(i2));
        }
        for (Map.Entry<T, Object> entry : this.a.e()) {
            i += c(entry);
        }
        return i;
    }

    private int c(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.getLiteJavaType() != WireFormat.JavaType.MESSAGE || key.isRepeated() || key.isPacked()) {
            return c((FieldDescriptorLite<?>) key, value);
        }
        if (value instanceof LazyField) {
            return CodedOutputStream.computeLazyFieldMessageSetExtensionSize(entry.getKey().getNumber(), (LazyField) value);
        }
        return CodedOutputStream.computeMessageSetExtensionSize(entry.getKey().getNumber(), (MessageLite) value);
    }

    public static int a(WireFormat.FieldType fieldType, int i, Object obj) {
        int computeTagSize = CodedOutputStream.computeTagSize(i);
        if (fieldType == WireFormat.FieldType.GROUP) {
            computeTagSize *= 2;
        }
        return computeTagSize + a(fieldType, obj);
    }

    static int a(WireFormat.FieldType fieldType, Object obj) {
        switch (fieldType) {
            case DOUBLE:
                return CodedOutputStream.computeDoubleSizeNoTag(((Double) obj).doubleValue());
            case FLOAT:
                return CodedOutputStream.computeFloatSizeNoTag(((Float) obj).floatValue());
            case INT64:
                return CodedOutputStream.computeInt64SizeNoTag(((Long) obj).longValue());
            case UINT64:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) obj).longValue());
            case INT32:
                return CodedOutputStream.computeInt32SizeNoTag(((Integer) obj).intValue());
            case FIXED64:
                return CodedOutputStream.computeFixed64SizeNoTag(((Long) obj).longValue());
            case FIXED32:
                return CodedOutputStream.computeFixed32SizeNoTag(((Integer) obj).intValue());
            case BOOL:
                return CodedOutputStream.computeBoolSizeNoTag(((Boolean) obj).booleanValue());
            case GROUP:
                return CodedOutputStream.computeGroupSizeNoTag((MessageLite) obj);
            case MESSAGE:
                if (obj instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) obj);
                }
                return CodedOutputStream.computeMessageSizeNoTag((MessageLite) obj);
            case STRING:
                if (obj instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
                }
                return CodedOutputStream.computeStringSizeNoTag((String) obj);
            case BYTES:
                if (obj instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
                }
                return CodedOutputStream.computeByteArraySizeNoTag((byte[]) obj);
            case UINT32:
                return CodedOutputStream.computeUInt32SizeNoTag(((Integer) obj).intValue());
            case SFIXED32:
                return CodedOutputStream.computeSFixed32SizeNoTag(((Integer) obj).intValue());
            case SFIXED64:
                return CodedOutputStream.computeSFixed64SizeNoTag(((Long) obj).longValue());
            case SINT32:
                return CodedOutputStream.computeSInt32SizeNoTag(((Integer) obj).intValue());
            case SINT64:
                return CodedOutputStream.computeSInt64SizeNoTag(((Long) obj).longValue());
            case ENUM:
                if (obj instanceof Internal.EnumLite) {
                    return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite) obj).getNumber());
                }
                return CodedOutputStream.computeEnumSizeNoTag(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int c(FieldDescriptorLite<?> fieldDescriptorLite, Object obj) {
        WireFormat.FieldType liteType = fieldDescriptorLite.getLiteType();
        int number = fieldDescriptorLite.getNumber();
        if (!fieldDescriptorLite.isRepeated()) {
            return a(liteType, number, obj);
        }
        int i = 0;
        if (fieldDescriptorLite.isPacked()) {
            for (Object obj2 : (List) obj) {
                i += a(liteType, obj2);
            }
            return CodedOutputStream.computeTagSize(number) + i + CodedOutputStream.computeRawVarint32Size(i);
        }
        for (Object obj3 : (List) obj) {
            i += a(liteType, number, obj3);
        }
        return i;
    }
}
