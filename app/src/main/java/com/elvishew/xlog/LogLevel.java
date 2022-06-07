package com.elvishew.xlog;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/LogLevel.class */
public class LogLevel {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ALL = Integer.MIN_VALUE;
    public static final int NONE = Integer.MAX_VALUE;

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('logLevel' int)]), block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    /* renamed from: <init>  reason: not valid java name */
    public static void m14init() {
        /*
            r0 = r4
            switch(r0) {
                case 2: goto L_0x0024;
                case 3: goto L_0x002a;
                case 4: goto L_0x0030;
                case 5: goto L_0x0036;
                case 6: goto L_0x003c;
                default: goto L_0x0042;
            }
        L_0x0024:
            java.lang.String r0 = "VERBOSE"
            r5 = r0
            goto L_0x0077
        L_0x002a:
            java.lang.String r0 = "DEBUG"
            r5 = r0
            goto L_0x0077
        L_0x0030:
            java.lang.String r0 = "INFO"
            r5 = r0
            goto L_0x0077
        L_0x0036:
            java.lang.String r0 = "WARN"
            r5 = r0
            goto L_0x0077
        L_0x003c:
            java.lang.String r0 = "ERROR"
            r5 = r0
            goto L_0x0077
        L_0x0042:
            r0 = r4
            r1 = 2
            if (r0 >= r1) goto L_0x0060
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            java.lang.String r1 = "VERBOSE-"
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = 2
            r2 = r4
            int r1 = r1 - r2
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5 = r0
            goto L_0x0077
        L_0x0060:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            java.lang.String r1 = "ERROR+"
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = r4
            r2 = 6
            int r1 = r1 - r2
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5 = r0
        L_0x0077:
            r0 = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.LogLevel.m14init():void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 4, insn: 0x0000: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('logLevel' int)]), block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    /* renamed from: <init>  reason: not valid java name */
    public static void m15init() {
        /*
            r0 = r4
            switch(r0) {
                case 2: goto L_0x0024;
                case 3: goto L_0x002a;
                case 4: goto L_0x0030;
                case 5: goto L_0x0036;
                case 6: goto L_0x003c;
                default: goto L_0x0042;
            }
        L_0x0024:
            java.lang.String r0 = "V"
            r5 = r0
            goto L_0x0077
        L_0x002a:
            java.lang.String r0 = "D"
            r5 = r0
            goto L_0x0077
        L_0x0030:
            java.lang.String r0 = "I"
            r5 = r0
            goto L_0x0077
        L_0x0036:
            java.lang.String r0 = "W"
            r5 = r0
            goto L_0x0077
        L_0x003c:
            java.lang.String r0 = "E"
            r5 = r0
            goto L_0x0077
        L_0x0042:
            r0 = r4
            r1 = 2
            if (r0 >= r1) goto L_0x0060
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            java.lang.String r1 = "V-"
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = 2
            r2 = r4
            int r1 = r1 - r2
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5 = r0
            goto L_0x0077
        L_0x0060:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            java.lang.String r1 = "E+"
            java.lang.StringBuilder r0 = r0.append(r1)
            r1 = r4
            r2 = 6
            int r1 = r1 - r2
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5 = r0
        L_0x0077:
            r0 = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.elvishew.xlog.LogLevel.m15init():void");
    }
}
