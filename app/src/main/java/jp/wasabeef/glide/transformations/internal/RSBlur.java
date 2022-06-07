package jp.wasabeef.glide.transformations.internal;

@Deprecated
/* loaded from: classes5.dex */
public class RSBlur {
    /* JADX WARN: Removed duplicated region for block: B:29:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0077  */
    @android.annotation.TargetApi(18)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap blur(android.content.Context r5, android.graphics.Bitmap r6, int r7) throws android.renderscript.RSRuntimeException {
        /*
            r0 = 23
            r1 = 0
            android.renderscript.RenderScript r5 = android.renderscript.RenderScript.create(r5)     // Catch: all -> 0x005a
            android.renderscript.RenderScript$RSMessageHandler r2 = new android.renderscript.RenderScript$RSMessageHandler     // Catch: all -> 0x0057
            r2.<init>()     // Catch: all -> 0x0057
            r5.setMessageHandler(r2)     // Catch: all -> 0x0057
            android.renderscript.Allocation$MipmapControl r2 = android.renderscript.Allocation.MipmapControl.MIPMAP_NONE     // Catch: all -> 0x0057
            r3 = 1
            android.renderscript.Allocation r2 = android.renderscript.Allocation.createFromBitmap(r5, r6, r2, r3)     // Catch: all -> 0x0057
            android.renderscript.Type r3 = r2.getType()     // Catch: all -> 0x0054
            android.renderscript.Allocation r3 = android.renderscript.Allocation.createTyped(r5, r3)     // Catch: all -> 0x0054
            android.renderscript.Element r4 = android.renderscript.Element.U8_4(r5)     // Catch: all -> 0x0050
            android.renderscript.ScriptIntrinsicBlur r1 = android.renderscript.ScriptIntrinsicBlur.create(r5, r4)     // Catch: all -> 0x0050
            r1.setInput(r2)     // Catch: all -> 0x0050
            float r7 = (float) r7     // Catch: all -> 0x0050
            r1.setRadius(r7)     // Catch: all -> 0x0050
            r1.forEach(r3)     // Catch: all -> 0x0050
            r3.copyTo(r6)     // Catch: all -> 0x0050
            if (r5 == 0) goto L_0x0040
            int r7 = android.os.Build.VERSION.SDK_INT
            if (r7 < r0) goto L_0x003d
            android.renderscript.RenderScript.releaseAllContexts()
            goto L_0x0040
        L_0x003d:
            r5.destroy()
        L_0x0040:
            if (r2 == 0) goto L_0x0045
            r2.destroy()
        L_0x0045:
            if (r3 == 0) goto L_0x004a
            r3.destroy()
        L_0x004a:
            if (r1 == 0) goto L_0x004f
            r1.destroy()
        L_0x004f:
            return r6
        L_0x0050:
            r6 = move-exception
            r7 = r1
            r1 = r3
            goto L_0x005e
        L_0x0054:
            r6 = move-exception
            r7 = r1
            goto L_0x005e
        L_0x0057:
            r6 = move-exception
            r7 = r1
            goto L_0x005d
        L_0x005a:
            r6 = move-exception
            r5 = r1
            r7 = r5
        L_0x005d:
            r2 = r7
        L_0x005e:
            if (r5 == 0) goto L_0x006b
            int r3 = android.os.Build.VERSION.SDK_INT
            if (r3 < r0) goto L_0x0068
            android.renderscript.RenderScript.releaseAllContexts()
            goto L_0x006b
        L_0x0068:
            r5.destroy()
        L_0x006b:
            if (r2 == 0) goto L_0x0070
            r2.destroy()
        L_0x0070:
            if (r1 == 0) goto L_0x0075
            r1.destroy()
        L_0x0075:
            if (r7 == 0) goto L_0x007a
            r7.destroy()
        L_0x007a:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.wasabeef.glide.transformations.internal.RSBlur.blur(android.content.Context, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }
}
