package com.bumptech.glide.load.data.mediastore;

import android.content.ContentResolver;
import android.net.Uri;
import android.text.TextUtils;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/* compiled from: ThumbnailStreamOpener.java */
/* loaded from: classes.dex */
class c {
    private static final a a = new a();
    private final a b;
    private final b c;
    private final ArrayPool d;
    private final ContentResolver e;
    private final List<ImageHeaderParser> f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(List<ImageHeaderParser> list, b bVar, ArrayPool arrayPool, ContentResolver contentResolver) {
        this(list, a, bVar, arrayPool, contentResolver);
    }

    c(List<ImageHeaderParser> list, a aVar, b bVar, ArrayPool arrayPool, ContentResolver contentResolver) {
        this.b = aVar;
        this.c = bVar;
        this.d = arrayPool;
        this.e = contentResolver;
        this.f = list;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0040: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:17:0x0045, block:B:15:0x0040
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    int a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0040: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:17:0x0045, block:B:15:0x0040
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

    public InputStream b(Uri uri) throws FileNotFoundException {
        String c = c(uri);
        if (TextUtils.isEmpty(c)) {
            return null;
        }
        File a2 = this.b.a(c);
        if (!a(a2)) {
            return null;
        }
        Uri fromFile = Uri.fromFile(a2);
        try {
            return this.e.openInputStream(fromFile);
        } catch (NullPointerException e) {
            throw ((FileNotFoundException) new FileNotFoundException("NPE opening uri: " + uri + " -> " + fromFile).initCause(e));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004f  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String c(@androidx.annotation.NonNull android.net.Uri r7) {
        /*
            r6 = this;
            r0 = 0
            com.bumptech.glide.load.data.mediastore.b r1 = r6.c     // Catch: SecurityException -> 0x0025, all -> 0x0022
            android.database.Cursor r1 = r1.a(r7)     // Catch: SecurityException -> 0x0025, all -> 0x0022
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1.moveToFirst()     // Catch: SecurityException -> 0x001a, all -> 0x004c
            if (r2 == 0) goto L_0x001c
            r2 = 0
            java.lang.String r7 = r1.getString(r2)     // Catch: SecurityException -> 0x001a, all -> 0x004c
            if (r1 == 0) goto L_0x0019
            r1.close()
        L_0x0019:
            return r7
        L_0x001a:
            r2 = move-exception
            goto L_0x0027
        L_0x001c:
            if (r1 == 0) goto L_0x0021
            r1.close()
        L_0x0021:
            return r0
        L_0x0022:
            r7 = move-exception
            r1 = r0
            goto L_0x004d
        L_0x0025:
            r2 = move-exception
            r1 = r0
        L_0x0027:
            java.lang.String r3 = "ThumbStreamOpener"
            r4 = 3
            boolean r3 = android.util.Log.isLoggable(r3, r4)     // Catch: all -> 0x004c
            if (r3 == 0) goto L_0x0046
            java.lang.String r3 = "ThumbStreamOpener"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x004c
            r4.<init>()     // Catch: all -> 0x004c
            java.lang.String r5 = "Failed to query for thumbnail for Uri: "
            r4.append(r5)     // Catch: all -> 0x004c
            r4.append(r7)     // Catch: all -> 0x004c
            java.lang.String r7 = r4.toString()     // Catch: all -> 0x004c
            android.util.Log.d(r3, r7, r2)     // Catch: all -> 0x004c
        L_0x0046:
            if (r1 == 0) goto L_0x004b
            r1.close()
        L_0x004b:
            return r0
        L_0x004c:
            r7 = move-exception
        L_0x004d:
            if (r1 == 0) goto L_0x0052
            r1.close()
        L_0x0052:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.data.mediastore.c.c(android.net.Uri):java.lang.String");
    }

    private boolean a(File file) {
        return this.b.a(file) && 0 < this.b.b(file);
    }
}
