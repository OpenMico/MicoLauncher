package com.efs.sdk.base.a.b;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.a.h.g;
import com.efs.sdk.base.a.i.f;
import java.io.File;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public final class a {
    public boolean a;
    public boolean b;
    public b c;
    public C0049a d;

    /* synthetic */ a(byte b2) {
        this();
    }

    private a() {
        this.a = false;
        this.b = true;
        this.c = new b();
        this.d = new C0049a();
    }

    /* loaded from: classes.dex */
    public static class b {
        private static final a a = new a((byte) 0);
    }

    public final void a() {
        String[] list;
        File d = com.efs.sdk.base.a.h.a.d(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a);
        if (d.exists() && d.isDirectory() && (list = d.list()) != null && list.length > 0) {
            for (String str : list) {
                if (!g.a(com.efs.sdk.base.a.d.a.a().c, str)) {
                    File file = new File(d, str);
                    List<File> d2 = com.efs.sdk.base.a.h.b.d(file);
                    if (!d2.isEmpty()) {
                        for (File file2 : d2) {
                            if (a(file2.getName())) {
                                c(file2);
                            } else {
                                com.efs.sdk.base.a.f.b b2 = com.efs.sdk.base.a.h.b.b(file2.getName());
                                if (b2 == null) {
                                    b(file2);
                                } else {
                                    e a = this.c.a(b2.a.b);
                                    if (a == null) {
                                        b(file2);
                                    } else {
                                        a.a(file2);
                                    }
                                }
                            }
                        }
                    }
                    com.efs.sdk.base.a.h.b.b(file);
                }
            }
        }
    }

    public static boolean a(String str) {
        try {
            long parseLong = Long.parseLong(str.substring(str.lastIndexOf("_") + 1));
            com.efs.sdk.base.a.a.a.a();
            return Math.abs(com.efs.sdk.base.a.a.a.b() - parseLong) >= 604800000;
        } catch (Throwable unused) {
            return true;
        }
    }

    @Nullable
    public final com.efs.sdk.base.a.f.b a(File file) {
        try {
            if (!file.exists()) {
                return null;
            }
            if (a(file.getName())) {
                c(file);
                return null;
            }
            com.efs.sdk.base.a.f.b b2 = com.efs.sdk.base.a.h.b.b(file.getName());
            if (b2 == null) {
                b(file);
                return null;
            }
            e a = this.c.a(b2.a.b);
            if (a == null) {
                b(file);
                return null;
            } else if (a.a(file, b2)) {
                return b2;
            } else {
                b(file);
                return null;
            }
        } catch (Throwable th) {
            d.a("efs.base", "efs.cache", th);
            b(file);
            return null;
        }
    }

    public static void b(@NonNull File file) {
        f fVar;
        if (!file.getName().startsWith("wa_")) {
            fVar = f.a.a;
            fVar.c.e();
        }
        com.efs.sdk.base.a.h.b.b(file);
    }

    public static void c(File file) {
        f fVar;
        StringBuilder sb = new StringBuilder("file is expire: ");
        sb.append(file.getName());
        sb.append(", now is ");
        com.efs.sdk.base.a.a.a.a();
        sb.append(com.efs.sdk.base.a.a.a.b());
        d.a("efs.cache", sb.toString());
        if (!file.getName().startsWith("wa_")) {
            fVar = f.a.a;
            fVar.c.d();
        }
        com.efs.sdk.base.a.h.b.b(file);
    }

    /* renamed from: com.efs.sdk.base.a.b.a$a */
    /* loaded from: classes.dex */
    public static class C0049a implements Comparator<File> {
        @Override // java.util.Comparator
        public final /* synthetic */ int compare(File file, File file2) {
            int i = ((file.lastModified() - file2.lastModified()) > 0L ? 1 : ((file.lastModified() - file2.lastModified()) == 0L ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i == 0 ? 0 : -1;
        }
    }
}
