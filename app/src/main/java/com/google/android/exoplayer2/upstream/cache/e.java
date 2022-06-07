package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: SimpleCacheSpan.java */
/* loaded from: classes2.dex */
final class e extends CacheSpan {
    private static final Pattern a = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v1\\.exo$", 32);
    private static final Pattern b = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v2\\.exo$", 32);
    private static final Pattern c = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.v3\\.exo$", 32);

    public static File a(File file, int i, long j, long j2) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(i);
        sb.append(".");
        sb.append(j);
        sb.append(".");
        sb.append(j2);
        sb.append(".v3.exo");
        return new File(file, sb.toString());
    }

    public static e a(String str, long j) {
        return new e(str, j, -1L, C.TIME_UNSET, null);
    }

    public static e a(String str, long j, long j2) {
        return new e(str, j, j2, C.TIME_UNSET, null);
    }

    @Nullable
    public static e a(File file, long j, d dVar) {
        return a(file, j, (long) C.TIME_UNSET, dVar);
    }

    @Nullable
    public static e a(File file, long j, long j2, d dVar) {
        File file2;
        String a2;
        String name = file.getName();
        if (!name.endsWith(".v3.exo")) {
            File a3 = a(file, dVar);
            if (a3 == null) {
                return null;
            }
            file2 = a3;
            name = a3.getName();
        } else {
            file2 = file;
        }
        Matcher matcher = c.matcher(name);
        if (!matcher.matches() || (a2 = dVar.a(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))))) == null) {
            return null;
        }
        long length = j == -1 ? file2.length() : j;
        if (length == 0) {
            return null;
        }
        return new e(a2, Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))), length, j2 == C.TIME_UNSET ? Long.parseLong((String) Assertions.checkNotNull(matcher.group(3))) : j2, file2);
    }

    @Nullable
    private static File a(File file, d dVar) {
        String str;
        String name = file.getName();
        Matcher matcher = b.matcher(name);
        if (matcher.matches()) {
            str = Util.unescapeFileName((String) Assertions.checkNotNull(matcher.group(1)));
        } else {
            matcher = a.matcher(name);
            str = matcher.matches() ? (String) Assertions.checkNotNull(matcher.group(1)) : null;
        }
        if (str == null) {
            return null;
        }
        File a2 = a((File) Assertions.checkStateNotNull(file.getParentFile()), dVar.d(str), Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))), Long.parseLong((String) Assertions.checkNotNull(matcher.group(3))));
        if (!file.renameTo(a2)) {
            return null;
        }
        return a2;
    }

    private e(String str, long j, long j2, long j3, @Nullable File file) {
        super(str, j, j2, j3, file);
    }

    public e a(File file, long j) {
        Assertions.checkState(this.isCached);
        return new e(this.key, this.position, this.length, j, file);
    }
}
