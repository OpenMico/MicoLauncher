package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* compiled from: AbstractCompositeHashFunction.java */
@Immutable
/* loaded from: classes2.dex */
abstract class b extends c {
    final HashFunction[] a;

    abstract HashCode a(Hasher[] hasherArr);

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(HashFunction... hashFunctionArr) {
        for (HashFunction hashFunction : hashFunctionArr) {
            Preconditions.checkNotNull(hashFunction);
        }
        this.a = hashFunctionArr;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        Hasher[] hasherArr = new Hasher[this.a.length];
        for (int i = 0; i < hasherArr.length; i++) {
            hasherArr[i] = this.a[i].newHasher();
        }
        return b(hasherArr);
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public Hasher newHasher(int i) {
        Preconditions.checkArgument(i >= 0);
        Hasher[] hasherArr = new Hasher[this.a.length];
        for (int i2 = 0; i2 < hasherArr.length; i2++) {
            hasherArr[i2] = this.a[i2].newHasher(i);
        }
        return b(hasherArr);
    }

    private Hasher b(final Hasher[] hasherArr) {
        return new Hasher() { // from class: com.google.common.hash.b.1
            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putByte(byte b) {
                for (Hasher hasher : hasherArr) {
                    hasher.putByte(b);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putBytes(byte[] bArr) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBytes(bArr);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putBytes(byte[] bArr, int i, int i2) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBytes(bArr, i, i2);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putBytes(ByteBuffer byteBuffer) {
                int position = byteBuffer.position();
                Hasher[] hasherArr2 = hasherArr;
                for (Hasher hasher : hasherArr2) {
                    byteBuffer.position(position);
                    hasher.putBytes(byteBuffer);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putShort(short s) {
                for (Hasher hasher : hasherArr) {
                    hasher.putShort(s);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putInt(int i) {
                for (Hasher hasher : hasherArr) {
                    hasher.putInt(i);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putLong(long j) {
                for (Hasher hasher : hasherArr) {
                    hasher.putLong(j);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putFloat(float f) {
                for (Hasher hasher : hasherArr) {
                    hasher.putFloat(f);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putDouble(double d) {
                for (Hasher hasher : hasherArr) {
                    hasher.putDouble(d);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putBoolean(boolean z) {
                for (Hasher hasher : hasherArr) {
                    hasher.putBoolean(z);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putChar(char c) {
                for (Hasher hasher : hasherArr) {
                    hasher.putChar(c);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putUnencodedChars(CharSequence charSequence) {
                for (Hasher hasher : hasherArr) {
                    hasher.putUnencodedChars(charSequence);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
            public Hasher putString(CharSequence charSequence, Charset charset) {
                for (Hasher hasher : hasherArr) {
                    hasher.putString(charSequence, charset);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher
            public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
                for (Hasher hasher : hasherArr) {
                    hasher.putObject(t, funnel);
                }
                return this;
            }

            @Override // com.google.common.hash.Hasher
            public HashCode hash() {
                return b.this.a(hasherArr);
            }
        };
    }
}
