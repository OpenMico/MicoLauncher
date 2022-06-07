package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

/* compiled from: CachedContent.java */
/* loaded from: classes2.dex */
public final class c {
    public final int a;
    public final String b;
    private final TreeSet<e> c;
    private final ArrayList<a> d;
    private DefaultContentMetadata e;

    public c(int i, String str) {
        this(i, str, DefaultContentMetadata.EMPTY);
    }

    public c(int i, String str, DefaultContentMetadata defaultContentMetadata) {
        this.a = i;
        this.b = str;
        this.e = defaultContentMetadata;
        this.c = new TreeSet<>();
        this.d = new ArrayList<>();
    }

    public DefaultContentMetadata a() {
        return this.e;
    }

    public boolean a(ContentMetadataMutations contentMetadataMutations) {
        DefaultContentMetadata defaultContentMetadata = this.e;
        this.e = defaultContentMetadata.copyWithMutationsApplied(contentMetadataMutations);
        return !this.e.equals(defaultContentMetadata);
    }

    public boolean b() {
        return this.d.isEmpty();
    }

    public boolean a(long j, long j2) {
        for (int i = 0; i < this.d.size(); i++) {
            if (this.d.get(i).a(j, j2)) {
                return true;
            }
        }
        return false;
    }

    public boolean b(long j, long j2) {
        for (int i = 0; i < this.d.size(); i++) {
            if (this.d.get(i).b(j, j2)) {
                return false;
            }
        }
        this.d.add(new a(j, j2));
        return true;
    }

    public void a(long j) {
        for (int i = 0; i < this.d.size(); i++) {
            if (this.d.get(i).a == j) {
                this.d.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public void a(e eVar) {
        this.c.add(eVar);
    }

    public TreeSet<e> c() {
        return this.c;
    }

    public e c(long j, long j2) {
        e a2 = e.a(this.b, j);
        e floor = this.c.floor(a2);
        if (floor != null && floor.position + floor.length > j) {
            return floor;
        }
        e ceiling = this.c.ceiling(a2);
        if (ceiling != null) {
            long j3 = ceiling.position - j;
            j2 = j2 == -1 ? j3 : Math.min(j3, j2);
        }
        return e.a(this.b, j, j2);
    }

    public long d(long j, long j2) {
        boolean z = true;
        Assertions.checkArgument(j >= 0);
        if (j2 < 0) {
            z = false;
        }
        Assertions.checkArgument(z);
        e c = c(j, j2);
        long j3 = Long.MAX_VALUE;
        if (c.isHoleSpan()) {
            if (!c.isOpenEnded()) {
                j3 = c.length;
            }
            return -Math.min(j3, j2);
        }
        long j4 = j + j2;
        if (j4 >= 0) {
            j3 = j4;
        }
        long j5 = c.position + c.length;
        if (j5 < j3) {
            for (e eVar : this.c.tailSet(c, false)) {
                if (eVar.position > j5) {
                    break;
                }
                j5 = Math.max(j5, eVar.position + eVar.length);
                if (j5 >= j3) {
                    break;
                }
            }
        }
        return Math.min(j5 - j, j2);
    }

    public e a(e eVar, long j, boolean z) {
        File file;
        Assertions.checkState(this.c.remove(eVar));
        File file2 = (File) Assertions.checkNotNull(eVar.file);
        if (z) {
            file = e.a((File) Assertions.checkNotNull(file2.getParentFile()), this.a, eVar.position, j);
            if (!file2.renameTo(file)) {
                String valueOf = String.valueOf(file2);
                String valueOf2 = String.valueOf(file);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21 + String.valueOf(valueOf2).length());
                sb.append("Failed to rename ");
                sb.append(valueOf);
                sb.append(" to ");
                sb.append(valueOf2);
                Log.w("CachedContent", sb.toString());
            }
            e a2 = eVar.a(file, j);
            this.c.add(a2);
            return a2;
        }
        file = file2;
        e a22 = eVar.a(file, j);
        this.c.add(a22);
        return a22;
    }

    public boolean d() {
        return this.c.isEmpty();
    }

    public boolean a(CacheSpan cacheSpan) {
        if (!this.c.remove(cacheSpan)) {
            return false;
        }
        if (cacheSpan.file == null) {
            return true;
        }
        cacheSpan.file.delete();
        return true;
    }

    public int hashCode() {
        return (((this.a * 31) + this.b.hashCode()) * 31) + this.e.hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        c cVar = (c) obj;
        return this.a == cVar.a && this.b.equals(cVar.b) && this.c.equals(cVar.c) && this.e.equals(cVar.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CachedContent.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final long a;
        public final long b;

        public a(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        public boolean a(long j, long j2) {
            long j3 = this.b;
            if (j3 == -1) {
                return j >= this.a;
            }
            if (j2 == -1) {
                return false;
            }
            long j4 = this.a;
            return j4 <= j && j + j2 <= j4 + j3;
        }

        public boolean b(long j, long j2) {
            long j3 = this.a;
            if (j3 > j) {
                return j2 == -1 || j + j2 > j3;
            }
            long j4 = this.b;
            return j4 == -1 || j3 + j4 > j;
        }
    }
}
