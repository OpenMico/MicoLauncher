package com.efs.sdk.pa.a;

import android.util.Log;
import com.efs.sdk.pa.PAMsgListener;
import com.efs.sdk.pa.b;
import java.io.BufferedOutputStream;

/* loaded from: classes.dex */
final class f implements d {
    PAMsgListener a;
    boolean b;
    String c;
    BufferedOutputStream d;

    @Override // com.efs.sdk.pa.a.d
    public final void a(String str, long j, long j2) {
        b b = b(str, j, j2);
        if (b != null) {
            if (this.b) {
                Log.e("PerformanceAnalyze", b.toString());
            }
            if (this.c != null) {
                try {
                    BufferedOutputStream bufferedOutputStream = this.d;
                    bufferedOutputStream.write((b.toString() + "\n").getBytes());
                } catch (Exception unused) {
                }
            }
            this.a.msg(b);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0095 A[Catch: Exception -> 0x00c1, TryCatch #0 {Exception -> 0x00c1, blocks: (B:6:0x0009, B:8:0x0012, B:10:0x001a, B:12:0x0025, B:14:0x002f, B:16:0x0037, B:19:0x0047, B:22:0x0051, B:23:0x0056, B:25:0x005f, B:27:0x0068, B:30:0x007a, B:33:0x0084, B:35:0x008b, B:37:0x0095, B:39:0x009e, B:41:0x00a7, B:43:0x00af, B:45:0x00ba), top: B:51:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.efs.sdk.pa.b b(java.lang.String r6, long r7, long r9) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.efs.sdk.pa.b r1 = new com.efs.sdk.pa.b
            r1.<init>()
            java.lang.String r2 = ":"
            int r2 = r6.indexOf(r2)     // Catch: Exception -> 0x00c1
            r3 = -1
            if (r2 == r3) goto L_0x0023
            int r2 = r2 + 2
            int r4 = r6.length()     // Catch: Exception -> 0x00c1
            if (r2 >= r4) goto L_0x0023
            int r4 = r6.length()     // Catch: Exception -> 0x00c1
            java.lang.String r2 = r6.substring(r2, r4)     // Catch: Exception -> 0x00c1
            goto L_0x0025
        L_0x0023:
            java.lang.String r2 = ""
        L_0x0025:
            r1.a = r2     // Catch: Exception -> 0x00c1
            java.lang.String r2 = "("
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 == 0) goto L_0x0056
            java.lang.String r2 = ")"
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 == 0) goto L_0x0056
            java.lang.String r2 = "("
            int r2 = r6.indexOf(r2)     // Catch: Exception -> 0x00c1
            java.lang.String r4 = ")"
            int r4 = r6.indexOf(r4)     // Catch: Exception -> 0x00c1
            if (r2 == r3) goto L_0x0089
            if (r4 == r3) goto L_0x0089
            int r2 = r2 + 1
            int r5 = r6.length()     // Catch: Exception -> 0x00c1
            if (r2 >= r5) goto L_0x0089
            if (r4 <= r2) goto L_0x0089
            java.lang.String r2 = r6.substring(r2, r4)     // Catch: Exception -> 0x00c1
            goto L_0x008b
        L_0x0056:
            java.lang.String r2 = "{"
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 == 0) goto L_0x0089
            java.lang.String r2 = "}"
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 == 0) goto L_0x0089
            java.lang.String r2 = "{"
            int r2 = r6.indexOf(r2)     // Catch: Exception -> 0x00c1
            java.lang.String r4 = "}"
            int r4 = r6.indexOf(r4)     // Catch: Exception -> 0x00c1
            if (r2 == r3) goto L_0x0089
            if (r4 == r3) goto L_0x0089
            int r2 = r2 + 1
            int r5 = r6.length()     // Catch: Exception -> 0x00c1
            if (r2 >= r5) goto L_0x0089
            if (r4 <= r2) goto L_0x0089
            java.lang.String r2 = r6.substring(r2, r4)     // Catch: Exception -> 0x00c1
            goto L_0x008b
        L_0x0089:
            java.lang.String r2 = ""
        L_0x008b:
            r1.b = r2     // Catch: Exception -> 0x00c1
            java.lang.String r2 = "null"
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 != 0) goto L_0x00b8
            java.lang.String r2 = "}"
            boolean r2 = r6.contains(r2)     // Catch: Exception -> 0x00c1
            if (r2 == 0) goto L_0x00b8
            java.lang.String r2 = "}"
            int r2 = r6.lastIndexOf(r2)     // Catch: Exception -> 0x00c1
            if (r2 == r3) goto L_0x00b8
            int r2 = r2 + 1
            int r3 = r6.length()     // Catch: Exception -> 0x00c1
            if (r2 >= r3) goto L_0x00b8
            int r3 = r6.length()     // Catch: Exception -> 0x00c1
            java.lang.String r6 = r6.substring(r2, r3)     // Catch: Exception -> 0x00c1
            goto L_0x00ba
        L_0x00b8:
            java.lang.String r6 = ""
        L_0x00ba:
            r1.c = r6     // Catch: Exception -> 0x00c1
            r1.d = r7
            r1.e = r9
            return r1
        L_0x00c1:
            r6 = move-exception
            r6.printStackTrace()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.pa.a.f.b(java.lang.String, long, long):com.efs.sdk.pa.b");
    }
}
