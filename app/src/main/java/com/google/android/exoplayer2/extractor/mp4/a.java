package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: Atom.java */
/* loaded from: classes2.dex */
abstract class a {
    public final int a;

    public static int a(int i) {
        return (i >> 24) & 255;
    }

    public static int b(int i) {
        return i & 16777215;
    }

    public a(int i) {
        this.a = i;
    }

    public String toString() {
        return c(this.a);
    }

    /* compiled from: Atom.java */
    /* loaded from: classes2.dex */
    static final class b extends a {
        public final ParsableByteArray b;

        public b(int i, ParsableByteArray parsableByteArray) {
            super(i);
            this.b = parsableByteArray;
        }
    }

    /* compiled from: Atom.java */
    /* renamed from: com.google.android.exoplayer2.extractor.mp4.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    static final class C0060a extends a {
        public final long b;
        public final List<b> c = new ArrayList();
        public final List<C0060a> d = new ArrayList();

        public C0060a(int i, long j) {
            super(i);
            this.b = j;
        }

        public void a(b bVar) {
            this.c.add(bVar);
        }

        public void a(C0060a aVar) {
            this.d.add(aVar);
        }

        @Nullable
        public b d(int i) {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                b bVar = this.c.get(i2);
                if (bVar.a == i) {
                    return bVar;
                }
            }
            return null;
        }

        @Nullable
        public C0060a e(int i) {
            int size = this.d.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0060a aVar = this.d.get(i2);
                if (aVar.a == i) {
                    return aVar;
                }
            }
            return null;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.a
        public String toString() {
            String c = c(this.a);
            String arrays = Arrays.toString(this.c.toArray());
            String arrays2 = Arrays.toString(this.d.toArray());
            StringBuilder sb = new StringBuilder(String.valueOf(c).length() + 22 + String.valueOf(arrays).length() + String.valueOf(arrays2).length());
            sb.append(c);
            sb.append(" leaves: ");
            sb.append(arrays);
            sb.append(" containers: ");
            sb.append(arrays2);
            return sb.toString();
        }
    }

    public static String c(int i) {
        StringBuilder sb = new StringBuilder(4);
        sb.append((char) ((i >> 24) & 255));
        sb.append((char) ((i >> 16) & 255));
        sb.append((char) ((i >> 8) & 255));
        sb.append((char) (i & 255));
        return sb.toString();
    }
}
