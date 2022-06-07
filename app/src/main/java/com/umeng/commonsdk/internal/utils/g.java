package com.umeng.commonsdk.internal.utils;

import android.os.Process;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/* compiled from: ProcessUtil.java */
/* loaded from: classes2.dex */
public class g {
    private static final String a = "\n";
    private static final byte[] b = "\nexit\n".getBytes();
    private static byte[] c = new byte[32];

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
        if (r9 != null) goto L_0x0063;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
        c(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00c6, code lost:
        if (r9 != null) goto L_0x0063;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d3, code lost:
        if (r9 != null) goto L_0x0063;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d6, code lost:
        if (r7 != null) goto L_0x00d9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d8, code lost:
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00dd, code lost:
        return r7.toString();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String... r9) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.internal.utils.g.a(java.lang.String[]):java.lang.String");
    }

    private static void a(OutputStream outputStream, InputStream inputStream, InputStream inputStream2, InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused2) {
            }
        }
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException unused3) {
            }
        }
        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException unused4) {
            }
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused5) {
            }
        }
    }

    private static void a(Process process) {
        int b2 = b(process);
        if (b2 != 0) {
            try {
                try {
                    Process.killProcess(b2);
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                process.destroy();
            }
        }
    }

    private static int b(Process process) {
        String obj = process.toString();
        try {
            return Integer.parseInt(obj.substring(obj.indexOf("=") + 1, obj.indexOf("]")));
        } catch (Exception unused) {
            return 0;
        }
    }

    private static void c(Process process) {
        if (process != null) {
            try {
                if (process.exitValue() != 0) {
                    a(process);
                }
            } catch (IllegalThreadStateException unused) {
                a(process);
            }
        }
    }
}
