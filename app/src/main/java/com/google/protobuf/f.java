package com.google.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.xiaomi.idm.service.iot.InputMethodService;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CodedInputStreamReader.java */
/* loaded from: classes2.dex */
public final class f implements ak {
    private final CodedInputStream a;
    private int b;
    private int c;
    private int d = 0;

    public static f a(CodedInputStream codedInputStream) {
        if (codedInputStream.d != null) {
            return codedInputStream.d;
        }
        return new f(codedInputStream);
    }

    private f(CodedInputStream codedInputStream) {
        this.a = (CodedInputStream) Internal.a(codedInputStream, InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC);
        this.a.d = this;
    }

    @Override // com.google.protobuf.ak
    public int a() throws IOException {
        int i = this.d;
        if (i != 0) {
            this.b = i;
            this.d = 0;
        } else {
            this.b = this.a.readTag();
        }
        int i2 = this.b;
        if (i2 == 0 || i2 == this.c) {
            return Integer.MAX_VALUE;
        }
        return WireFormat.getTagFieldNumber(i2);
    }

    @Override // com.google.protobuf.ak
    public int b() {
        return this.b;
    }

    @Override // com.google.protobuf.ak
    public boolean c() throws IOException {
        int i;
        if (this.a.isAtEnd() || (i = this.b) == this.c) {
            return false;
        }
        return this.a.skipField(i);
    }

    private void a(int i) throws IOException {
        if (WireFormat.getTagWireType(this.b) != i) {
            throw InvalidProtocolBufferException.f();
        }
    }

    @Override // com.google.protobuf.ak
    public double d() throws IOException {
        a(1);
        return this.a.readDouble();
    }

    @Override // com.google.protobuf.ak
    public float e() throws IOException {
        a(5);
        return this.a.readFloat();
    }

    @Override // com.google.protobuf.ak
    public long f() throws IOException {
        a(0);
        return this.a.readUInt64();
    }

    @Override // com.google.protobuf.ak
    public long g() throws IOException {
        a(0);
        return this.a.readInt64();
    }

    @Override // com.google.protobuf.ak
    public int h() throws IOException {
        a(0);
        return this.a.readInt32();
    }

    @Override // com.google.protobuf.ak
    public long i() throws IOException {
        a(1);
        return this.a.readFixed64();
    }

    @Override // com.google.protobuf.ak
    public int j() throws IOException {
        a(5);
        return this.a.readFixed32();
    }

    @Override // com.google.protobuf.ak
    public boolean k() throws IOException {
        a(0);
        return this.a.readBool();
    }

    @Override // com.google.protobuf.ak
    public String l() throws IOException {
        a(2);
        return this.a.readString();
    }

    @Override // com.google.protobuf.ak
    public String m() throws IOException {
        a(2);
        return this.a.readStringRequireUtf8();
    }

    @Override // com.google.protobuf.ak
    public <T> T a(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(2);
        return (T) c(ah.a().a((Class) cls), extensionRegistryLite);
    }

    @Override // com.google.protobuf.ak
    public <T> T a(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(2);
        return (T) c(amVar, extensionRegistryLite);
    }

    @Override // com.google.protobuf.ak
    public <T> T b(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(3);
        return (T) d(ah.a().a((Class) cls), extensionRegistryLite);
    }

    @Override // com.google.protobuf.ak
    public <T> T b(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(3);
        return (T) d(amVar, extensionRegistryLite);
    }

    private <T> T c(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readUInt32 = this.a.readUInt32();
        if (this.a.a < this.a.b) {
            int pushLimit = this.a.pushLimit(readUInt32);
            T a = amVar.a();
            this.a.a++;
            amVar.a(a, this, extensionRegistryLite);
            amVar.d(a);
            this.a.checkLastTagWas(0);
            CodedInputStream codedInputStream = this.a;
            codedInputStream.a--;
            this.a.popLimit(pushLimit);
            return a;
        }
        throw InvalidProtocolBufferException.g();
    }

    private <T> T d(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int i = this.c;
        this.c = WireFormat.a(WireFormat.getTagFieldNumber(this.b), 4);
        try {
            T a = amVar.a();
            amVar.a(a, this, extensionRegistryLite);
            amVar.d(a);
            if (this.b == this.c) {
                return a;
            }
            throw InvalidProtocolBufferException.i();
        } finally {
            this.c = i;
        }
    }

    @Override // com.google.protobuf.ak
    public ByteString n() throws IOException {
        a(2);
        return this.a.readBytes();
    }

    @Override // com.google.protobuf.ak
    public int o() throws IOException {
        a(0);
        return this.a.readUInt32();
    }

    @Override // com.google.protobuf.ak
    public int p() throws IOException {
        a(0);
        return this.a.readEnum();
    }

    @Override // com.google.protobuf.ak
    public int q() throws IOException {
        a(5);
        return this.a.readSFixed32();
    }

    @Override // com.google.protobuf.ak
    public long r() throws IOException {
        a(1);
        return this.a.readSFixed64();
    }

    @Override // com.google.protobuf.ak
    public int s() throws IOException {
        a(0);
        return this.a.readSInt32();
    }

    @Override // com.google.protobuf.ak
    public long t() throws IOException {
        a(0);
        return this.a.readSInt64();
    }

    @Override // com.google.protobuf.ak
    public void a(List<Double> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof h) {
            h hVar = (h) list;
            switch (WireFormat.getTagWireType(this.b)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.a.readUInt32();
                    b(readUInt32);
                    int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                    do {
                        hVar.addDouble(this.a.readDouble());
                    } while (this.a.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                hVar.addDouble(this.a.readDouble());
                if (!this.a.isAtEnd()) {
                    readTag2 = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag2 == this.b);
            this.d = readTag2;
            return;
        }
        switch (WireFormat.getTagWireType(this.b)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.a.readUInt32();
                b(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Double.valueOf(this.a.readDouble()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw InvalidProtocolBufferException.f();
        }
        do {
            list.add(Double.valueOf(this.a.readDouble()));
            if (!this.a.isAtEnd()) {
                readTag = this.a.readTag();
            } else {
                return;
            }
        } while (readTag == this.b);
        this.d = readTag;
    }

    @Override // com.google.protobuf.ak
    public void b(List<Float> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof m) {
            m mVar = (m) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 2) {
                int readUInt32 = this.a.readUInt32();
                c(readUInt32);
                int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                do {
                    mVar.addFloat(this.a.readFloat());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
            } else if (tagWireType == 5) {
                do {
                    mVar.addFloat(this.a.readFloat());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 2) {
                int readUInt322 = this.a.readUInt32();
                c(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Float.valueOf(this.a.readFloat()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
            } else if (tagWireType2 == 5) {
                do {
                    list.add(Float.valueOf(this.a.readFloat()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void c(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof r) {
            r rVar = (r) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    rVar.addLong(this.a.readUInt64());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    rVar.addLong(this.a.readUInt64());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Long.valueOf(this.a.readUInt64()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Long.valueOf(this.a.readUInt64()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void d(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof r) {
            r rVar = (r) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    rVar.addLong(this.a.readInt64());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    rVar.addLong(this.a.readInt64());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Long.valueOf(this.a.readInt64()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Long.valueOf(this.a.readInt64()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void e(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    oVar.addInt(this.a.readInt32());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    oVar.addInt(this.a.readInt32());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Integer.valueOf(this.a.readInt32()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Integer.valueOf(this.a.readInt32()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void f(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof r) {
            r rVar = (r) list;
            switch (WireFormat.getTagWireType(this.b)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.a.readUInt32();
                    b(readUInt32);
                    int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                    do {
                        rVar.addLong(this.a.readFixed64());
                    } while (this.a.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                rVar.addLong(this.a.readFixed64());
                if (!this.a.isAtEnd()) {
                    readTag2 = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag2 == this.b);
            this.d = readTag2;
            return;
        }
        switch (WireFormat.getTagWireType(this.b)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.a.readUInt32();
                b(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Long.valueOf(this.a.readFixed64()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw InvalidProtocolBufferException.f();
        }
        do {
            list.add(Long.valueOf(this.a.readFixed64()));
            if (!this.a.isAtEnd()) {
                readTag = this.a.readTag();
            } else {
                return;
            }
        } while (readTag == this.b);
        this.d = readTag;
    }

    @Override // com.google.protobuf.ak
    public void g(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 2) {
                int readUInt32 = this.a.readUInt32();
                c(readUInt32);
                int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                do {
                    oVar.addInt(this.a.readFixed32());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
            } else if (tagWireType == 5) {
                do {
                    oVar.addInt(this.a.readFixed32());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 2) {
                int readUInt322 = this.a.readUInt32();
                c(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Integer.valueOf(this.a.readFixed32()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
            } else if (tagWireType2 == 5) {
                do {
                    list.add(Integer.valueOf(this.a.readFixed32()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void h(List<Boolean> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof e) {
            e eVar = (e) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    eVar.addBoolean(this.a.readBool());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    eVar.addBoolean(this.a.readBool());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Boolean.valueOf(this.a.readBool()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Boolean.valueOf(this.a.readBool()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void i(List<String> list) throws IOException {
        a(list, false);
    }

    @Override // com.google.protobuf.ak
    public void j(List<String> list) throws IOException {
        a(list, true);
    }

    public void a(List<String> list, boolean z) throws IOException {
        int readTag;
        int readTag2;
        if (WireFormat.getTagWireType(this.b) != 2) {
            throw InvalidProtocolBufferException.f();
        } else if (!(list instanceof LazyStringList) || z) {
            do {
                list.add(z ? m() : l());
                if (!this.a.isAtEnd()) {
                    readTag = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag == this.b);
            this.d = readTag;
        } else {
            LazyStringList lazyStringList = (LazyStringList) list;
            do {
                lazyStringList.add(n());
                if (!this.a.isAtEnd()) {
                    readTag2 = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag2 == this.b);
            this.d = readTag2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.protobuf.ak
    public <T> void a(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.b) == 2) {
            int i = this.b;
            do {
                list.add(c(amVar, extensionRegistryLite));
                if (!this.a.isAtEnd() && this.d == 0) {
                    readTag = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag == i);
            this.d = readTag;
            return;
        }
        throw InvalidProtocolBufferException.f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.protobuf.ak
    public <T> void b(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.b) == 3) {
            int i = this.b;
            do {
                list.add(d(amVar, extensionRegistryLite));
                if (!this.a.isAtEnd() && this.d == 0) {
                    readTag = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag == i);
            this.d = readTag;
            return;
        }
        throw InvalidProtocolBufferException.f();
    }

    @Override // com.google.protobuf.ak
    public void k(List<ByteString> list) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.b) == 2) {
            do {
                list.add(n());
                if (!this.a.isAtEnd()) {
                    readTag = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag == this.b);
            this.d = readTag;
            return;
        }
        throw InvalidProtocolBufferException.f();
    }

    @Override // com.google.protobuf.ak
    public void l(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    oVar.addInt(this.a.readUInt32());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    oVar.addInt(this.a.readUInt32());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Integer.valueOf(this.a.readUInt32()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Integer.valueOf(this.a.readUInt32()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void m(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    oVar.addInt(this.a.readEnum());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    oVar.addInt(this.a.readEnum());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Integer.valueOf(this.a.readEnum()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Integer.valueOf(this.a.readEnum()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void n(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 2) {
                int readUInt32 = this.a.readUInt32();
                c(readUInt32);
                int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                do {
                    oVar.addInt(this.a.readSFixed32());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
            } else if (tagWireType == 5) {
                do {
                    oVar.addInt(this.a.readSFixed32());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 2) {
                int readUInt322 = this.a.readUInt32();
                c(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Integer.valueOf(this.a.readSFixed32()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
            } else if (tagWireType2 == 5) {
                do {
                    list.add(Integer.valueOf(this.a.readSFixed32()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void o(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof r) {
            r rVar = (r) list;
            switch (WireFormat.getTagWireType(this.b)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.a.readUInt32();
                    b(readUInt32);
                    int totalBytesRead = this.a.getTotalBytesRead() + readUInt32;
                    do {
                        rVar.addLong(this.a.readSFixed64());
                    } while (this.a.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                rVar.addLong(this.a.readSFixed64());
                if (!this.a.isAtEnd()) {
                    readTag2 = this.a.readTag();
                } else {
                    return;
                }
            } while (readTag2 == this.b);
            this.d = readTag2;
            return;
        }
        switch (WireFormat.getTagWireType(this.b)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.a.readUInt32();
                b(readUInt322);
                int totalBytesRead2 = this.a.getTotalBytesRead() + readUInt322;
                do {
                    list.add(Long.valueOf(this.a.readSFixed64()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw InvalidProtocolBufferException.f();
        }
        do {
            list.add(Long.valueOf(this.a.readSFixed64()));
            if (!this.a.isAtEnd()) {
                readTag = this.a.readTag();
            } else {
                return;
            }
        } while (readTag == this.b);
        this.d = readTag;
    }

    @Override // com.google.protobuf.ak
    public void p(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof o) {
            o oVar = (o) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    oVar.addInt(this.a.readSInt32());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    oVar.addInt(this.a.readSInt32());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Integer.valueOf(this.a.readSInt32()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Integer.valueOf(this.a.readSInt32()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    @Override // com.google.protobuf.ak
    public void q(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof r) {
            r rVar = (r) list;
            int tagWireType = WireFormat.getTagWireType(this.b);
            if (tagWireType == 0) {
                do {
                    rVar.addLong(this.a.readSInt64());
                    if (!this.a.isAtEnd()) {
                        readTag2 = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag2 == this.b);
                this.d = readTag2;
            } else if (tagWireType == 2) {
                int totalBytesRead = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    rVar.addLong(this.a.readSInt64());
                } while (this.a.getTotalBytesRead() < totalBytesRead);
                d(totalBytesRead);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        } else {
            int tagWireType2 = WireFormat.getTagWireType(this.b);
            if (tagWireType2 == 0) {
                do {
                    list.add(Long.valueOf(this.a.readSInt64()));
                    if (!this.a.isAtEnd()) {
                        readTag = this.a.readTag();
                    } else {
                        return;
                    }
                } while (readTag == this.b);
                this.d = readTag;
            } else if (tagWireType2 == 2) {
                int totalBytesRead2 = this.a.getTotalBytesRead() + this.a.readUInt32();
                do {
                    list.add(Long.valueOf(this.a.readSInt64()));
                } while (this.a.getTotalBytesRead() < totalBytesRead2);
                d(totalBytesRead2);
            } else {
                throw InvalidProtocolBufferException.f();
            }
        }
    }

    private void b(int i) throws IOException {
        if ((i & 7) != 0) {
            throw InvalidProtocolBufferException.i();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.protobuf.ak
    public <K, V> void a(Map<K, V> map, MapEntryLite.a<K, V> aVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(2);
        int pushLimit = this.a.pushLimit(this.a.readUInt32());
        Object obj = aVar.b;
        Object obj2 = aVar.d;
        while (true) {
            try {
                int a = a();
                if (a != Integer.MAX_VALUE && !this.a.isAtEnd()) {
                    switch (a) {
                        case 1:
                            obj = a(aVar.a, (Class<?>) null, (ExtensionRegistryLite) null);
                            break;
                        case 2:
                            obj2 = a(aVar.c, aVar.d.getClass(), extensionRegistryLite);
                            break;
                        default:
                            try {
                                if (!c()) {
                                    throw new InvalidProtocolBufferException("Unable to parse map entry.");
                                    break;
                                } else {
                                    break;
                                }
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused) {
                                if (c()) {
                                    break;
                                } else {
                                    throw new InvalidProtocolBufferException("Unable to parse map entry.");
                                }
                            }
                    }
                }
            } finally {
                this.a.popLimit(pushLimit);
            }
        }
        map.put(obj, obj2);
    }

    private Object a(WireFormat.FieldType fieldType, Class<?> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        switch (fieldType) {
            case BOOL:
                return Boolean.valueOf(k());
            case BYTES:
                return n();
            case DOUBLE:
                return Double.valueOf(d());
            case ENUM:
                return Integer.valueOf(p());
            case FIXED32:
                return Integer.valueOf(j());
            case FIXED64:
                return Long.valueOf(i());
            case FLOAT:
                return Float.valueOf(e());
            case INT32:
                return Integer.valueOf(h());
            case INT64:
                return Long.valueOf(g());
            case MESSAGE:
                return a(cls, extensionRegistryLite);
            case SFIXED32:
                return Integer.valueOf(q());
            case SFIXED64:
                return Long.valueOf(r());
            case SINT32:
                return Integer.valueOf(s());
            case SINT64:
                return Long.valueOf(t());
            case STRING:
                return m();
            case UINT32:
                return Integer.valueOf(o());
            case UINT64:
                return Long.valueOf(f());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private void c(int i) throws IOException {
        if ((i & 3) != 0) {
            throw InvalidProtocolBufferException.i();
        }
    }

    private void d(int i) throws IOException {
        if (this.a.getTotalBytesRead() != i) {
            throw InvalidProtocolBufferException.a();
        }
    }
}
