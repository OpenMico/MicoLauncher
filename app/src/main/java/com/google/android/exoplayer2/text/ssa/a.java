package com.google.android.exoplayer2.text.ssa;

/* compiled from: SsaDialogueFormat.java */
/* loaded from: classes2.dex */
final class a {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;

    private a(int i, int i2, int i3, int i4, int i5) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075 A[SYNTHETIC] */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.exoplayer2.text.ssa.a a(java.lang.String r10) {
        /*
            java.lang.String r0 = "Format:"
            boolean r0 = r10.startsWith(r0)
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            r0 = 7
            java.lang.String r10 = r10.substring(r0)
            java.lang.String r0 = ","
            java.lang.String[] r10 = android.text.TextUtils.split(r10, r0)
            r0 = 0
            r1 = -1
            r2 = r0
            r4 = r1
            r5 = r4
            r6 = r5
            r7 = r6
        L_0x001b:
            int r3 = r10.length
            if (r2 >= r3) goto L_0x0078
            r3 = r10[r2]
            java.lang.String r3 = r3.trim()
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase(r3)
            int r8 = r3.hashCode()
            r9 = 100571(0x188db, float:1.4093E-40)
            if (r8 == r9) goto L_0x005f
            r9 = 3556653(0x36452d, float:4.983932E-39)
            if (r8 == r9) goto L_0x0055
            r9 = 109757538(0x68ac462, float:5.219839E-35)
            if (r8 == r9) goto L_0x004b
            r9 = 109780401(0x68b1db1, float:5.2329616E-35)
            if (r8 == r9) goto L_0x0041
            goto L_0x0069
        L_0x0041:
            java.lang.String r8 = "style"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0069
            r3 = 2
            goto L_0x006a
        L_0x004b:
            java.lang.String r8 = "start"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0069
            r3 = r0
            goto L_0x006a
        L_0x0055:
            java.lang.String r8 = "text"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0069
            r3 = 3
            goto L_0x006a
        L_0x005f:
            java.lang.String r8 = "end"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0069
            r3 = 1
            goto L_0x006a
        L_0x0069:
            r3 = r1
        L_0x006a:
            switch(r3) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0072;
                case 2: goto L_0x0070;
                case 3: goto L_0x006e;
                default: goto L_0x006d;
            }
        L_0x006d:
            goto L_0x0075
        L_0x006e:
            r7 = r2
            goto L_0x0075
        L_0x0070:
            r6 = r2
            goto L_0x0075
        L_0x0072:
            r5 = r2
            goto L_0x0075
        L_0x0074:
            r4 = r2
        L_0x0075:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0078:
            if (r4 == r1) goto L_0x0086
            if (r5 == r1) goto L_0x0086
            if (r7 == r1) goto L_0x0086
            com.google.android.exoplayer2.text.ssa.a r0 = new com.google.android.exoplayer2.text.ssa.a
            int r8 = r10.length
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            goto L_0x0087
        L_0x0086:
            r0 = 0
        L_0x0087:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.a.a(java.lang.String):com.google.android.exoplayer2.text.ssa.a");
    }
}
