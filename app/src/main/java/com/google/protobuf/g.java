package com.google.protobuf;

import com.google.protobuf.MapEntryLite;
import com.google.protobuf.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CodedOutputStreamWriter.java */
/* loaded from: classes2.dex */
public final class g implements Writer {
    private final CodedOutputStream a;

    public static g a(CodedOutputStream codedOutputStream) {
        if (codedOutputStream.a != null) {
            return codedOutputStream.a;
        }
        return new g(codedOutputStream);
    }

    private g(CodedOutputStream codedOutputStream) {
        this.a = (CodedOutputStream) Internal.a(codedOutputStream, "output");
        this.a.a = this;
    }

    @Override // com.google.protobuf.Writer
    public Writer.FieldOrder a() {
        return Writer.FieldOrder.ASCENDING;
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, int i2) throws IOException {
        this.a.writeSFixed32(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, long j) throws IOException {
        this.a.writeInt64(i, j);
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, long j) throws IOException {
        this.a.writeSFixed64(i, j);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, float f) throws IOException {
        this.a.writeFloat(i, f);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, double d) throws IOException {
        this.a.writeDouble(i, d);
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, int i2) throws IOException {
        this.a.writeEnum(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void c(int i, long j) throws IOException {
        this.a.writeUInt64(i, j);
    }

    @Override // com.google.protobuf.Writer
    public void c(int i, int i2) throws IOException {
        this.a.writeInt32(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void d(int i, long j) throws IOException {
        this.a.writeFixed64(i, j);
    }

    @Override // com.google.protobuf.Writer
    public void d(int i, int i2) throws IOException {
        this.a.writeFixed32(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, boolean z) throws IOException {
        this.a.writeBool(i, z);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, String str) throws IOException {
        this.a.writeString(i, str);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, ByteString byteString) throws IOException {
        this.a.writeBytes(i, byteString);
    }

    @Override // com.google.protobuf.Writer
    public void e(int i, int i2) throws IOException {
        this.a.writeUInt32(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void f(int i, int i2) throws IOException {
        this.a.writeSInt32(i, i2);
    }

    @Override // com.google.protobuf.Writer
    public void e(int i, long j) throws IOException {
        this.a.writeSInt64(i, j);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, Object obj, am amVar) throws IOException {
        this.a.a(i, (MessageLite) obj, amVar);
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, Object obj, am amVar) throws IOException {
        this.a.c(i, (MessageLite) obj, amVar);
    }

    @Override // com.google.protobuf.Writer
    public void a(int i) throws IOException {
        this.a.writeTag(i, 3);
    }

    @Override // com.google.protobuf.Writer
    public void b(int i) throws IOException {
        this.a.writeTag(i, 4);
    }

    @Override // com.google.protobuf.Writer
    public final void a(int i, Object obj) throws IOException {
        if (obj instanceof ByteString) {
            this.a.writeRawMessageSetExtension(i, (ByteString) obj);
        } else {
            this.a.writeMessageSetExtension(i, (MessageLite) obj);
        }
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeInt32SizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeInt32NoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeInt32(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeFixed32SizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeFixed32NoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeFixed32(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void c(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeInt64SizeNoTag(list.get(i4).longValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeInt64NoTag(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeInt64(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void d(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt64SizeNoTag(list.get(i4).longValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeUInt64NoTag(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeUInt64(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void e(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeFixed64SizeNoTag(list.get(i4).longValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeFixed64NoTag(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeFixed64(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void f(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeFloatSizeNoTag(list.get(i4).floatValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeFloatNoTag(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeFloat(i, list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void g(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeDoubleSizeNoTag(list.get(i4).doubleValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeDoubleNoTag(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeDouble(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void h(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeEnumSizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeEnumNoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeEnum(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void i(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeBoolSizeNoTag(list.get(i4).booleanValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeBoolNoTag(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeBool(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof LazyStringList) {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i2 < list.size()) {
                b(i, lazyStringList.getRaw(i2));
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeString(i, list.get(i2));
            i2++;
        }
    }

    private void b(int i, Object obj) throws IOException {
        if (obj instanceof String) {
            this.a.writeString(i, (String) obj);
        } else {
            this.a.writeBytes(i, (ByteString) obj);
        }
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, List<ByteString> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.a.writeBytes(i, list.get(i2));
        }
    }

    @Override // com.google.protobuf.Writer
    public void j(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt32SizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeUInt32NoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeUInt32(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void k(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSFixed32SizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeSFixed32NoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeSFixed32(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void l(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSFixed64SizeNoTag(list.get(i4).longValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeSFixed64NoTag(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeSFixed64(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void m(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSInt32SizeNoTag(list.get(i4).intValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeSInt32NoTag(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeSInt32(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void n(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.a.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSInt64SizeNoTag(list.get(i4).longValue());
            }
            this.a.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                this.a.writeSInt64NoTag(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.a.writeSInt64(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.protobuf.Writer
    public void a(int i, List<?> list, am amVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            a(i, list.get(i2), amVar);
        }
    }

    @Override // com.google.protobuf.Writer
    public void b(int i, List<?> list, am amVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            b(i, list.get(i2), amVar);
        }
    }

    @Override // com.google.protobuf.Writer
    public <K, V> void a(int i, MapEntryLite.a<K, V> aVar, Map<K, V> map) throws IOException {
        if (this.a.a()) {
            b(i, aVar, map);
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.a.writeTag(i, 2);
            this.a.writeUInt32NoTag(MapEntryLite.a(aVar, entry.getKey(), entry.getValue()));
            MapEntryLite.a(this.a, aVar, entry.getKey(), entry.getValue());
        }
    }

    private <K, V> void b(int i, MapEntryLite.a<K, V> aVar, Map<K, V> map) throws IOException {
        switch (aVar.a) {
            case BOOL:
                V v = map.get(Boolean.FALSE);
                if (v != null) {
                    a(i, false, v, aVar);
                }
                V v2 = map.get(Boolean.TRUE);
                if (v2 != null) {
                    a(i, true, v2, aVar);
                    return;
                }
                return;
            case FIXED32:
            case INT32:
            case SFIXED32:
            case SINT32:
            case UINT32:
                c(i, aVar, map);
                return;
            case FIXED64:
            case INT64:
            case SFIXED64:
            case SINT64:
            case UINT64:
                d(i, aVar, map);
                return;
            case STRING:
                e(i, aVar, map);
                return;
            default:
                throw new IllegalArgumentException("does not support key type: " + aVar.a);
        }
    }

    private <V> void a(int i, boolean z, V v, MapEntryLite.a<Boolean, V> aVar) throws IOException {
        this.a.writeTag(i, 2);
        this.a.writeUInt32NoTag(MapEntryLite.a(aVar, Boolean.valueOf(z), v));
        MapEntryLite.a(this.a, aVar, Boolean.valueOf(z), v);
    }

    private <V> void c(int i, MapEntryLite.a<Integer, V> aVar, Map<Integer, V> map) throws IOException {
        int[] iArr = new int[map.size()];
        int i2 = 0;
        for (Integer num : map.keySet()) {
            i2++;
            iArr[i2] = num.intValue();
        }
        Arrays.sort(iArr);
        for (int i3 : iArr) {
            V v = map.get(Integer.valueOf(i3));
            this.a.writeTag(i, 2);
            this.a.writeUInt32NoTag(MapEntryLite.a(aVar, Integer.valueOf(i3), v));
            MapEntryLite.a(this.a, aVar, Integer.valueOf(i3), v);
        }
    }

    private <V> void d(int i, MapEntryLite.a<Long, V> aVar, Map<Long, V> map) throws IOException {
        long[] jArr = new long[map.size()];
        int i2 = 0;
        for (Long l : map.keySet()) {
            i2++;
            jArr[i2] = l.longValue();
        }
        Arrays.sort(jArr);
        for (long j : jArr) {
            V v = map.get(Long.valueOf(j));
            this.a.writeTag(i, 2);
            this.a.writeUInt32NoTag(MapEntryLite.a(aVar, Long.valueOf(j), v));
            MapEntryLite.a(this.a, aVar, Long.valueOf(j), v);
        }
    }

    private <V> void e(int i, MapEntryLite.a<String, V> aVar, Map<String, V> map) throws IOException {
        String[] strArr = new String[map.size()];
        int i2 = 0;
        for (String str : map.keySet()) {
            i2++;
            strArr[i2] = str;
        }
        Arrays.sort(strArr);
        for (String str2 : strArr) {
            V v = map.get(str2);
            this.a.writeTag(i, 2);
            this.a.writeUInt32NoTag(MapEntryLite.a(aVar, str2, v));
            MapEntryLite.a(this.a, aVar, str2, v);
        }
    }
}
