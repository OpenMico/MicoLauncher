package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class Funnels {
    private Funnels() {
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return a.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum a implements Funnel<byte[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }

        /* renamed from: a */
        public void funnel(byte[] bArr, PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(bArr);
        }
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return g.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum g implements Funnel<CharSequence> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }

        /* renamed from: a */
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new f(charset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class f implements Funnel<CharSequence>, Serializable {
        private final Charset charset;

        f(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        /* renamed from: a */
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.charset);
        }

        public String toString() {
            return "Funnels.stringFunnel(" + this.charset.name() + ")";
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof f) {
                return this.charset.equals(((f) obj).charset);
            }
            return false;
        }

        public int hashCode() {
            return f.class.hashCode() ^ this.charset.hashCode();
        }

        Object writeReplace() {
            return new a(this.charset);
        }

        /* loaded from: classes2.dex */
        private static class a implements Serializable {
            private static final long serialVersionUID = 0;
            private final String charsetCanonicalName;

            a(Charset charset) {
                this.charsetCanonicalName = charset.name();
            }

            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
            }
        }
    }

    public static Funnel<Integer> integerFunnel() {
        return b.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum b implements Funnel<Integer> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.integerFunnel()";
        }

        /* renamed from: a */
        public void funnel(Integer num, PrimitiveSink primitiveSink) {
            primitiveSink.putInt(num.intValue());
        }
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> funnel) {
        return new d(funnel);
    }

    /* loaded from: classes2.dex */
    private static class d<E> implements Funnel<Iterable<? extends E>>, Serializable {
        private final Funnel<E> elementFunnel;

        d(Funnel<E> funnel) {
            this.elementFunnel = (Funnel) Preconditions.checkNotNull(funnel);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void funnel(Iterable<? extends E> iterable, PrimitiveSink primitiveSink) {
            Iterator<? extends E> it = iterable.iterator();
            while (it.hasNext()) {
                this.elementFunnel.funnel(it.next(), primitiveSink);
            }
        }

        public String toString() {
            return "Funnels.sequentialFunnel(" + this.elementFunnel + ")";
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof d) {
                return this.elementFunnel.equals(((d) obj).elementFunnel);
            }
            return false;
        }

        public int hashCode() {
            return d.class.hashCode() ^ this.elementFunnel.hashCode();
        }
    }

    public static Funnel<Long> longFunnel() {
        return c.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum c implements Funnel<Long> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Funnels.longFunnel()";
        }

        /* renamed from: a */
        public void funnel(Long l, PrimitiveSink primitiveSink) {
            primitiveSink.putLong(l.longValue());
        }
    }

    public static OutputStream asOutputStream(PrimitiveSink primitiveSink) {
        return new e(primitiveSink);
    }

    /* loaded from: classes2.dex */
    private static class e extends OutputStream {
        final PrimitiveSink a;

        e(PrimitiveSink primitiveSink) {
            this.a = (PrimitiveSink) Preconditions.checkNotNull(primitiveSink);
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            this.a.putByte((byte) i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            this.a.putBytes(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            this.a.putBytes(bArr, i, i2);
        }

        public String toString() {
            return "Funnels.asOutputStream(" + this.a + ")";
        }
    }
}
