package com.google.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* compiled from: BinaryReader.java */
/* loaded from: classes2.dex */
abstract class d implements ak {
    public static d a(ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.hasArray()) {
            return new a(byteBuffer, z);
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }

    private d() {
    }

    /* compiled from: BinaryReader.java */
    /* loaded from: classes2.dex */
    private static final class a extends d {
        private final boolean a;
        private final byte[] b;
        private int c;
        private final int d;
        private int e;
        private int f;
        private int g;

        public a(ByteBuffer byteBuffer, boolean z) {
            super();
            this.a = z;
            this.b = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            this.c = arrayOffset;
            this.d = arrayOffset;
            this.e = byteBuffer.arrayOffset() + byteBuffer.limit();
        }

        private boolean v() {
            return this.c == this.e;
        }

        @Override // com.google.protobuf.ak
        public int a() throws IOException {
            if (v()) {
                return Integer.MAX_VALUE;
            }
            this.f = w();
            int i = this.f;
            if (i == this.g) {
                return Integer.MAX_VALUE;
            }
            return WireFormat.getTagFieldNumber(i);
        }

        @Override // com.google.protobuf.ak
        public int b() {
            return this.f;
        }

        @Override // com.google.protobuf.ak
        public boolean c() throws IOException {
            int i;
            if (v() || (i = this.f) == this.g) {
                return false;
            }
            int tagWireType = WireFormat.getTagWireType(i);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        D();
                        return true;
                    case 1:
                        a(8);
                        return true;
                    case 2:
                        a(w());
                        return true;
                    case 3:
                        F();
                        return true;
                    default:
                        throw InvalidProtocolBufferException.f();
                }
            } else {
                a(4);
                return true;
            }
        }

        @Override // com.google.protobuf.ak
        public double d() throws IOException {
            c(1);
            return Double.longBitsToDouble(A());
        }

        @Override // com.google.protobuf.ak
        public float e() throws IOException {
            c(5);
            return Float.intBitsToFloat(z());
        }

        @Override // com.google.protobuf.ak
        public long f() throws IOException {
            c(0);
            return u();
        }

        @Override // com.google.protobuf.ak
        public long g() throws IOException {
            c(0);
            return u();
        }

        @Override // com.google.protobuf.ak
        public int h() throws IOException {
            c(0);
            return w();
        }

        @Override // com.google.protobuf.ak
        public long i() throws IOException {
            c(1);
            return A();
        }

        @Override // com.google.protobuf.ak
        public int j() throws IOException {
            c(5);
            return z();
        }

        @Override // com.google.protobuf.ak
        public boolean k() throws IOException {
            c(0);
            return w() != 0;
        }

        @Override // com.google.protobuf.ak
        public String l() throws IOException {
            return a(false);
        }

        @Override // com.google.protobuf.ak
        public String m() throws IOException {
            return a(true);
        }

        public String a(boolean z) throws IOException {
            c(2);
            int w = w();
            if (w == 0) {
                return "";
            }
            b(w);
            if (z) {
                byte[] bArr = this.b;
                int i = this.c;
                if (!au.a(bArr, i, i + w)) {
                    throw InvalidProtocolBufferException.j();
                }
            }
            String str = new String(this.b, this.c, w, Internal.a);
            this.c += w;
            return str;
        }

        @Override // com.google.protobuf.ak
        public <T> T a(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            c(2);
            return (T) c(ah.a().a((Class) cls), extensionRegistryLite);
        }

        @Override // com.google.protobuf.ak
        public <T> T a(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            c(2);
            return (T) c(amVar, extensionRegistryLite);
        }

        private <T> T c(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int w = w();
            b(w);
            int i = this.e;
            int i2 = this.c + w;
            this.e = i2;
            try {
                T a = amVar.a();
                amVar.a(a, this, extensionRegistryLite);
                amVar.d(a);
                if (this.c == i2) {
                    return a;
                }
                throw InvalidProtocolBufferException.i();
            } finally {
                this.e = i;
            }
        }

        @Override // com.google.protobuf.ak
        public <T> T b(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            c(3);
            return (T) d(ah.a().a((Class) cls), extensionRegistryLite);
        }

        @Override // com.google.protobuf.ak
        public <T> T b(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            c(3);
            return (T) d(amVar, extensionRegistryLite);
        }

        private <T> T d(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i = this.g;
            this.g = WireFormat.a(WireFormat.getTagFieldNumber(this.f), 4);
            try {
                T a = amVar.a();
                amVar.a(a, this, extensionRegistryLite);
                amVar.d(a);
                if (this.f == this.g) {
                    return a;
                }
                throw InvalidProtocolBufferException.i();
            } finally {
                this.g = i;
            }
        }

        @Override // com.google.protobuf.ak
        public ByteString n() throws IOException {
            ByteString byteString;
            c(2);
            int w = w();
            if (w == 0) {
                return ByteString.EMPTY;
            }
            b(w);
            if (this.a) {
                byteString = ByteString.a(this.b, this.c, w);
            } else {
                byteString = ByteString.copyFrom(this.b, this.c, w);
            }
            this.c += w;
            return byteString;
        }

        @Override // com.google.protobuf.ak
        public int o() throws IOException {
            c(0);
            return w();
        }

        @Override // com.google.protobuf.ak
        public int p() throws IOException {
            c(0);
            return w();
        }

        @Override // com.google.protobuf.ak
        public int q() throws IOException {
            c(5);
            return z();
        }

        @Override // com.google.protobuf.ak
        public long r() throws IOException {
            c(1);
            return A();
        }

        @Override // com.google.protobuf.ak
        public int s() throws IOException {
            c(0);
            return CodedInputStream.decodeZigZag32(w());
        }

        @Override // com.google.protobuf.ak
        public long t() throws IOException {
            c(0);
            return CodedInputStream.decodeZigZag64(u());
        }

        @Override // com.google.protobuf.ak
        public void a(List<Double> list) throws IOException {
            int i;
            int i2;
            if (list instanceof h) {
                h hVar = (h) list;
                switch (WireFormat.getTagWireType(this.f)) {
                    case 1:
                        break;
                    case 2:
                        int w = w();
                        d(w);
                        int i3 = this.c + w;
                        while (this.c < i3) {
                            hVar.addDouble(Double.longBitsToDouble(C()));
                        }
                        return;
                    default:
                        throw InvalidProtocolBufferException.f();
                }
                do {
                    hVar.addDouble(d());
                    if (!v()) {
                        i2 = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i2;
                return;
            }
            switch (WireFormat.getTagWireType(this.f)) {
                case 1:
                    break;
                case 2:
                    int w2 = w();
                    d(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Double.valueOf(Double.longBitsToDouble(C())));
                    }
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                list.add(Double.valueOf(d()));
                if (!v()) {
                    i = this.c;
                } else {
                    return;
                }
            } while (w() == this.f);
            this.c = i;
        }

        @Override // com.google.protobuf.ak
        public void b(List<Float> list) throws IOException {
            int i;
            int i2;
            if (list instanceof m) {
                m mVar = (m) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 2) {
                    int w = w();
                    e(w);
                    int i3 = this.c + w;
                    while (this.c < i3) {
                        mVar.addFloat(Float.intBitsToFloat(B()));
                    }
                } else if (tagWireType == 5) {
                    do {
                        mVar.addFloat(e());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 2) {
                    int w2 = w();
                    e(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Float.valueOf(Float.intBitsToFloat(B())));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        list.add(Float.valueOf(e()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void c(List<Long> list) throws IOException {
            int i;
            int i2;
            if (list instanceof r) {
                r rVar = (r) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        rVar.addLong(f());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        rVar.addLong(u());
                    }
                    f(w);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Long.valueOf(f()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Long.valueOf(u()));
                    }
                    f(w2);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void d(List<Long> list) throws IOException {
            int i;
            int i2;
            if (list instanceof r) {
                r rVar = (r) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        rVar.addLong(g());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        rVar.addLong(u());
                    }
                    f(w);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Long.valueOf(g()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Long.valueOf(u()));
                    }
                    f(w2);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void e(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        oVar.addInt(h());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        oVar.addInt(w());
                    }
                    f(w);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Integer.valueOf(h()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Integer.valueOf(w()));
                    }
                    f(w2);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void f(List<Long> list) throws IOException {
            int i;
            int i2;
            if (list instanceof r) {
                r rVar = (r) list;
                switch (WireFormat.getTagWireType(this.f)) {
                    case 1:
                        break;
                    case 2:
                        int w = w();
                        d(w);
                        int i3 = this.c + w;
                        while (this.c < i3) {
                            rVar.addLong(C());
                        }
                        return;
                    default:
                        throw InvalidProtocolBufferException.f();
                }
                do {
                    rVar.addLong(i());
                    if (!v()) {
                        i2 = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i2;
                return;
            }
            switch (WireFormat.getTagWireType(this.f)) {
                case 1:
                    break;
                case 2:
                    int w2 = w();
                    d(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Long.valueOf(C()));
                    }
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                list.add(Long.valueOf(i()));
                if (!v()) {
                    i = this.c;
                } else {
                    return;
                }
            } while (w() == this.f);
            this.c = i;
        }

        @Override // com.google.protobuf.ak
        public void g(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 2) {
                    int w = w();
                    e(w);
                    int i3 = this.c + w;
                    while (this.c < i3) {
                        oVar.addInt(B());
                    }
                } else if (tagWireType == 5) {
                    do {
                        oVar.addInt(j());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 2) {
                    int w2 = w();
                    e(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Integer.valueOf(B()));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        list.add(Integer.valueOf(j()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void h(List<Boolean> list) throws IOException {
            int i;
            int i2;
            if (list instanceof e) {
                e eVar = (e) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        eVar.addBoolean(k());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        eVar.addBoolean(w() != 0);
                    }
                    f(w);
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Boolean.valueOf(k()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Boolean.valueOf(w() != 0));
                    }
                    f(w2);
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
            int i;
            int i2;
            if (WireFormat.getTagWireType(this.f) != 2) {
                throw InvalidProtocolBufferException.f();
            } else if (!(list instanceof LazyStringList) || z) {
                do {
                    list.add(a(z));
                    if (!v()) {
                        i = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i;
            } else {
                LazyStringList lazyStringList = (LazyStringList) list;
                do {
                    lazyStringList.add(n());
                    if (!v()) {
                        i2 = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i2;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.ak
        public <T> void a(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i;
            if (WireFormat.getTagWireType(this.f) == 2) {
                int i2 = this.f;
                do {
                    list.add(c(amVar, extensionRegistryLite));
                    if (!v()) {
                        i = this.c;
                    } else {
                        return;
                    }
                } while (w() == i2);
                this.c = i;
                return;
            }
            throw InvalidProtocolBufferException.f();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.ak
        public <T> void b(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i;
            if (WireFormat.getTagWireType(this.f) == 3) {
                int i2 = this.f;
                do {
                    list.add(d(amVar, extensionRegistryLite));
                    if (!v()) {
                        i = this.c;
                    } else {
                        return;
                    }
                } while (w() == i2);
                this.c = i;
                return;
            }
            throw InvalidProtocolBufferException.f();
        }

        @Override // com.google.protobuf.ak
        public void k(List<ByteString> list) throws IOException {
            int i;
            if (WireFormat.getTagWireType(this.f) == 2) {
                do {
                    list.add(n());
                    if (!v()) {
                        i = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i;
                return;
            }
            throw InvalidProtocolBufferException.f();
        }

        @Override // com.google.protobuf.ak
        public void l(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        oVar.addInt(o());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        oVar.addInt(w());
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Integer.valueOf(o()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Integer.valueOf(w()));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void m(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        oVar.addInt(p());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        oVar.addInt(w());
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Integer.valueOf(p()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Integer.valueOf(w()));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void n(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 2) {
                    int w = w();
                    e(w);
                    int i3 = this.c + w;
                    while (this.c < i3) {
                        oVar.addInt(B());
                    }
                } else if (tagWireType == 5) {
                    do {
                        oVar.addInt(q());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 2) {
                    int w2 = w();
                    e(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Integer.valueOf(B()));
                    }
                } else if (tagWireType2 == 5) {
                    do {
                        list.add(Integer.valueOf(q()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void o(List<Long> list) throws IOException {
            int i;
            int i2;
            if (list instanceof r) {
                r rVar = (r) list;
                switch (WireFormat.getTagWireType(this.f)) {
                    case 1:
                        break;
                    case 2:
                        int w = w();
                        d(w);
                        int i3 = this.c + w;
                        while (this.c < i3) {
                            rVar.addLong(C());
                        }
                        return;
                    default:
                        throw InvalidProtocolBufferException.f();
                }
                do {
                    rVar.addLong(r());
                    if (!v()) {
                        i2 = this.c;
                    } else {
                        return;
                    }
                } while (w() == this.f);
                this.c = i2;
                return;
            }
            switch (WireFormat.getTagWireType(this.f)) {
                case 1:
                    break;
                case 2:
                    int w2 = w();
                    d(w2);
                    int i4 = this.c + w2;
                    while (this.c < i4) {
                        list.add(Long.valueOf(C()));
                    }
                    return;
                default:
                    throw InvalidProtocolBufferException.f();
            }
            do {
                list.add(Long.valueOf(r()));
                if (!v()) {
                    i = this.c;
                } else {
                    return;
                }
            } while (w() == this.f);
            this.c = i;
        }

        @Override // com.google.protobuf.ak
        public void p(List<Integer> list) throws IOException {
            int i;
            int i2;
            if (list instanceof o) {
                o oVar = (o) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        oVar.addInt(s());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        oVar.addInt(CodedInputStream.decodeZigZag32(w()));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Integer.valueOf(s()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Integer.valueOf(CodedInputStream.decodeZigZag32(w())));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        @Override // com.google.protobuf.ak
        public void q(List<Long> list) throws IOException {
            int i;
            int i2;
            if (list instanceof r) {
                r rVar = (r) list;
                int tagWireType = WireFormat.getTagWireType(this.f);
                if (tagWireType == 0) {
                    do {
                        rVar.addLong(t());
                        if (!v()) {
                            i2 = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i2;
                } else if (tagWireType == 2) {
                    int w = this.c + w();
                    while (this.c < w) {
                        rVar.addLong(CodedInputStream.decodeZigZag64(u()));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            } else {
                int tagWireType2 = WireFormat.getTagWireType(this.f);
                if (tagWireType2 == 0) {
                    do {
                        list.add(Long.valueOf(t()));
                        if (!v()) {
                            i = this.c;
                        } else {
                            return;
                        }
                    } while (w() == this.f);
                    this.c = i;
                } else if (tagWireType2 == 2) {
                    int w2 = this.c + w();
                    while (this.c < w2) {
                        list.add(Long.valueOf(CodedInputStream.decodeZigZag64(u())));
                    }
                } else {
                    throw InvalidProtocolBufferException.f();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.ak
        public <K, V> void a(Map<K, V> map, MapEntryLite.a<K, V> aVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            c(2);
            int w = w();
            b(w);
            int i = this.e;
            this.e = this.c + w;
            try {
                Object obj = aVar.b;
                Object obj2 = aVar.d;
                while (true) {
                    int a = a();
                    if (a == Integer.MAX_VALUE) {
                        map.put(obj, obj2);
                        return;
                    }
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
                this.e = i;
            }
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

        private int w() throws IOException {
            int i;
            int i2 = this.c;
            int i3 = this.e;
            if (i3 != i2) {
                byte[] bArr = this.b;
                int i4 = i2 + 1;
                byte b = bArr[i2];
                if (b >= 0) {
                    this.c = i4;
                    return b;
                } else if (i3 - i4 < 9) {
                    return (int) x();
                } else {
                    int i5 = i4 + 1;
                    int i6 = b ^ (bArr[i4] << 7);
                    if (i6 < 0) {
                        i = i6 ^ (-128);
                    } else {
                        int i7 = i5 + 1;
                        int i8 = i6 ^ (bArr[i5] << 14);
                        if (i8 >= 0) {
                            i = i8 ^ 16256;
                            i5 = i7;
                        } else {
                            i5 = i7 + 1;
                            int i9 = i8 ^ (bArr[i7] << 21);
                            if (i9 < 0) {
                                i = i9 ^ (-2080896);
                            } else {
                                i5++;
                                byte b2 = bArr[i5];
                                i = (i9 ^ (b2 << 28)) ^ 266354560;
                                if (b2 < 0) {
                                    i5++;
                                    if (bArr[i5] < 0) {
                                        i5++;
                                        if (bArr[i5] < 0) {
                                            i5++;
                                            if (bArr[i5] < 0) {
                                                i5++;
                                                if (bArr[i5] < 0) {
                                                    i5++;
                                                    if (bArr[i5] < 0) {
                                                        throw InvalidProtocolBufferException.c();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.c = i5;
                    return i;
                }
            } else {
                throw InvalidProtocolBufferException.a();
            }
        }

        public long u() throws IOException {
            long j;
            int i = this.c;
            int i2 = this.e;
            if (i2 != i) {
                byte[] bArr = this.b;
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    this.c = i3;
                    return b;
                } else if (i2 - i3 < 9) {
                    return x();
                } else {
                    int i4 = i3 + 1;
                    int i5 = b ^ (bArr[i3] << 7);
                    if (i5 < 0) {
                        j = i5 ^ (-128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = i5 ^ (bArr[i4] << 14);
                        if (i7 >= 0) {
                            j = i7 ^ 16256;
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = i7 ^ (bArr[i6] << 21);
                            if (i8 < 0) {
                                j = i8 ^ (-2080896);
                            } else {
                                long j2 = i8;
                                int i9 = i4 + 1;
                                long j3 = j2 ^ (bArr[i4] << 28);
                                if (j3 >= 0) {
                                    j = j3 ^ 266354560;
                                    i4 = i9;
                                } else {
                                    i4 = i9 + 1;
                                    long j4 = j3 ^ (bArr[i9] << 35);
                                    if (j4 < 0) {
                                        j = j4 ^ (-34093383808L);
                                    } else {
                                        int i10 = i4 + 1;
                                        long j5 = j4 ^ (bArr[i4] << 42);
                                        if (j5 >= 0) {
                                            j = j5 ^ 4363953127296L;
                                            i4 = i10;
                                        } else {
                                            i4 = i10 + 1;
                                            long j6 = j5 ^ (bArr[i10] << 49);
                                            if (j6 < 0) {
                                                j = j6 ^ (-558586000294016L);
                                            } else {
                                                int i11 = i4 + 1;
                                                long j7 = (j6 ^ (bArr[i4] << 56)) ^ 71499008037633920L;
                                                if (j7 < 0) {
                                                    i4 = i11 + 1;
                                                    if (bArr[i11] >= 0) {
                                                        j = j7;
                                                    } else {
                                                        throw InvalidProtocolBufferException.c();
                                                    }
                                                } else {
                                                    i4 = i11;
                                                    j = j7;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.c = i4;
                    return j;
                }
            } else {
                throw InvalidProtocolBufferException.a();
            }
        }

        private long x() throws IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                byte y = y();
                j |= (y & Byte.MAX_VALUE) << i;
                if ((y & 128) == 0) {
                    return j;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private byte y() throws IOException {
            int i = this.c;
            if (i != this.e) {
                byte[] bArr = this.b;
                this.c = i + 1;
                return bArr[i];
            }
            throw InvalidProtocolBufferException.a();
        }

        private int z() throws IOException {
            b(4);
            return B();
        }

        private long A() throws IOException {
            b(8);
            return C();
        }

        private int B() {
            int i = this.c;
            byte[] bArr = this.b;
            this.c = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        }

        private long C() {
            int i = this.c;
            byte[] bArr = this.b;
            this.c = i + 8;
            return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
        }

        private void D() throws IOException {
            int i = this.e;
            int i2 = this.c;
            if (i - i2 >= 10) {
                byte[] bArr = this.b;
                int i3 = 0;
                while (i3 < 10) {
                    int i4 = i2 + 1;
                    if (bArr[i2] >= 0) {
                        this.c = i4;
                        return;
                    } else {
                        i3++;
                        i2 = i4;
                    }
                }
            }
            E();
        }

        private void E() throws IOException {
            for (int i = 0; i < 10; i++) {
                if (y() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private void a(int i) throws IOException {
            b(i);
            this.c += i;
        }

        private void F() throws IOException {
            int i = this.g;
            this.g = WireFormat.a(WireFormat.getTagFieldNumber(this.f), 4);
            while (a() != Integer.MAX_VALUE && c()) {
            }
            if (this.f == this.g) {
                this.g = i;
                return;
            }
            throw InvalidProtocolBufferException.i();
        }

        private void b(int i) throws IOException {
            if (i < 0 || i > this.e - this.c) {
                throw InvalidProtocolBufferException.a();
            }
        }

        private void c(int i) throws IOException {
            if (WireFormat.getTagWireType(this.f) != i) {
                throw InvalidProtocolBufferException.f();
            }
        }

        private void d(int i) throws IOException {
            b(i);
            if ((i & 7) != 0) {
                throw InvalidProtocolBufferException.i();
            }
        }

        private void e(int i) throws IOException {
            b(i);
            if ((i & 3) != 0) {
                throw InvalidProtocolBufferException.i();
            }
        }

        private void f(int i) throws IOException {
            if (this.c != i) {
                throw InvalidProtocolBufferException.a();
            }
        }
    }
}
