package com.efs.sdk.base.a.b;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.g.a.c;
import com.efs.sdk.base.a.g.a.d;
import com.efs.sdk.base.a.i.f;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class g extends Handler implements e {
    private final ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();
    private d b = new d();
    private c c = new c();

    /* JADX INFO: Access modifiers changed from: package-private */
    public g() {
        super(com.efs.sdk.base.a.h.a.a.a.getLooper());
    }

    @Override // android.os.Handler
    public final void handleMessage(@NonNull Message message) {
        switch (message.what) {
            case 0:
                b bVar = (b) message.obj;
                for (int i = 0; i < 3; i++) {
                    try {
                        a b = b(bVar);
                        if (b == null) {
                            com.efs.sdk.base.a.h.d.a("efs.cache", "writer is null for type " + bVar.a.a, null);
                            return;
                        }
                        if (b.getChannel().position() + bVar.c.length > 819200) {
                            c(bVar.a.a);
                            b = b(bVar);
                            if (b == null) {
                                com.efs.sdk.base.a.h.d.a("efs.cache", "writer is null for type " + bVar.a.a, null);
                                return;
                            }
                        }
                        b.write(Base64.encode(bVar.c, 11));
                        b.write("\n".getBytes());
                        return;
                    } catch (Throwable th) {
                        com.efs.sdk.base.a.h.d.b("efs.cache", "cache file error", th);
                    }
                }
                return;
            case 1:
                if (message.obj instanceof String) {
                    c(message.obj.toString());
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // com.efs.sdk.base.a.b.e
    public final void a(b bVar) {
        Message obtain = Message.obtain();
        obtain.obj = bVar;
        obtain.what = 0;
        sendMessage(obtain);
    }

    @Override // com.efs.sdk.base.a.b.e
    public final boolean a(File file, b bVar) {
        if (!bVar.b()) {
            a(file);
            return false;
        } else if (!file.exists()) {
            return false;
        } else {
            bVar.d = file;
            bVar.d();
            bVar.b(1);
            return true;
        }
    }

    @Override // com.efs.sdk.base.a.b.e
    public final void a(@NonNull String str) {
        if (!TextUtils.isEmpty(str)) {
            Message obtain = Message.obtain();
            obtain.obj = str;
            obtain.what = 1;
            sendMessage(obtain);
        }
    }

    private static long b(String str) {
        Map<String, String> c = com.efs.sdk.base.a.c.a.c.a().c();
        String concat = "record_accumulation_time_".concat(String.valueOf(str));
        if (!c.containsKey(concat)) {
            return 60000L;
        }
        String str2 = c.get(concat);
        if (TextUtils.isEmpty(str2)) {
            return 60000L;
        }
        try {
            return Math.max(Long.parseLong(str2) * 1000, 1000L);
        } catch (Throwable th) {
            com.efs.sdk.base.a.h.d.b("efs.cache", "get cache interval error", th);
            return 60000L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.efs.sdk.base.a.b.g.a b(com.efs.sdk.base.a.f.b r6) {
        /*
            r5 = this;
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.efs.sdk.base.a.b.g$a> r0 = r5.a
            com.efs.sdk.base.a.f.a r1 = r6.a
            java.lang.String r1 = r1.a
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto L_0x0019
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.efs.sdk.base.a.b.g$a> r0 = r5.a
            com.efs.sdk.base.a.f.a r6 = r6.a
            java.lang.String r6 = r6.a
            java.lang.Object r6 = r0.get(r6)
            com.efs.sdk.base.a.b.g$a r6 = (com.efs.sdk.base.a.b.g.a) r6
            return r6
        L_0x0019:
            java.lang.String r0 = com.efs.sdk.base.a.h.b.a(r6)
            java.io.File r1 = new java.io.File
            com.efs.sdk.base.a.c.a r2 = com.efs.sdk.base.a.d.a.a()
            android.content.Context r2 = r2.c
            com.efs.sdk.base.a.c.a r3 = com.efs.sdk.base.a.d.a.a()
            java.lang.String r3 = r3.a
            java.io.File r2 = com.efs.sdk.base.a.h.a.e(r2, r3)
            r1.<init>(r2, r0)
            r0 = 0
            com.efs.sdk.base.a.b.g$a r2 = new com.efs.sdk.base.a.b.g$a     // Catch: Throwable -> 0x0068
            r2.<init>(r1)     // Catch: Throwable -> 0x0068
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.efs.sdk.base.a.b.g$a> r0 = r5.a     // Catch: Throwable -> 0x0066
            com.efs.sdk.base.a.f.a r3 = r6.a     // Catch: Throwable -> 0x0066
            java.lang.String r3 = r3.a     // Catch: Throwable -> 0x0066
            java.lang.Object r0 = r0.putIfAbsent(r3, r2)     // Catch: Throwable -> 0x0066
            com.efs.sdk.base.a.b.g$a r0 = (com.efs.sdk.base.a.b.g.a) r0     // Catch: Throwable -> 0x0066
            if (r0 == 0) goto L_0x004d
            com.efs.sdk.base.a.h.b.a(r2)     // Catch: Throwable -> 0x0066
            com.efs.sdk.base.a.h.b.b(r1)     // Catch: Throwable -> 0x0066
            return r0
        L_0x004d:
            android.os.Message r0 = android.os.Message.obtain()     // Catch: Throwable -> 0x0066
            com.efs.sdk.base.a.f.a r1 = r6.a     // Catch: Throwable -> 0x0066
            java.lang.String r1 = r1.a     // Catch: Throwable -> 0x0066
            r0.obj = r1     // Catch: Throwable -> 0x0066
            r1 = 1
            r0.what = r1     // Catch: Throwable -> 0x0066
            com.efs.sdk.base.a.f.a r1 = r6.a     // Catch: Throwable -> 0x0066
            java.lang.String r1 = r1.a     // Catch: Throwable -> 0x0066
            long r3 = b(r1)     // Catch: Throwable -> 0x0066
            r5.sendMessageDelayed(r0, r3)     // Catch: Throwable -> 0x0066
            goto L_0x006e
        L_0x0066:
            r0 = move-exception
            goto L_0x006b
        L_0x0068:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x006b:
            r0.printStackTrace()
        L_0x006e:
            java.lang.String r0 = "wa"
            com.efs.sdk.base.a.f.a r6 = r6.a
            java.lang.String r6 = r6.a
            boolean r6 = r0.equalsIgnoreCase(r6)
            if (r6 != 0) goto L_0x0084
            com.efs.sdk.base.a.i.f r6 = com.efs.sdk.base.a.i.f.a.a()
            com.efs.sdk.base.a.i.d r6 = r6.c
            r6.b()
        L_0x0084:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.a.b.g.b(com.efs.sdk.base.a.f.b):com.efs.sdk.base.a.b.g$a");
    }

    private void c(String str) {
        a aVar;
        f fVar;
        if (this.a.containsKey(str) && (aVar = this.a.get(str)) != null) {
            try {
                aVar.flush();
                com.efs.sdk.base.a.h.b.a(aVar);
                a(aVar.b);
            } finally {
                this.a.remove(str);
                if (!"wa".equalsIgnoreCase(str)) {
                    fVar = f.a.a;
                    fVar.c.c();
                }
            }
        }
    }

    @Override // com.efs.sdk.base.a.b.e
    public final void a(File file) {
        a unused;
        a unused2;
        b b = com.efs.sdk.base.a.h.b.b(file.getName());
        if (b == null) {
            unused = a.b.a;
            a.b(file);
        } else if (!a(b, file) || b.c == null || b.c.length <= 0) {
            unused2 = a.b.a;
            a.b(file);
        } else {
            com.efs.sdk.base.a.h.b.a(new File(com.efs.sdk.base.a.h.a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a), com.efs.sdk.base.a.h.b.a(b)), b.c);
            com.efs.sdk.base.a.h.b.b(file);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x0070: MOVE  (r3 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:26:0x0070
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private boolean a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x0070: MOVE  (r3 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:26:0x0070
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends FileOutputStream {
        long a = System.currentTimeMillis();
        File b;

        a(@NonNull File file) {
            super(file);
            this.b = file;
        }
    }
}
