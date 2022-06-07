package com.efs.sdk.base.a.h.b;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.a.h.b;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.http.HttpResponse;
import com.efs.sdk.base.http.IHttpUtil;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public final class a implements IHttpUtil {
    /* synthetic */ a(byte b) {
        this();
    }

    private a() {
    }

    /* renamed from: com.efs.sdk.base.a.h.b.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static class C0051a {
        private static final a a = new a((byte) 0);
    }

    public static a a() {
        return C0051a.a;
    }

    private static HttpURLConnection a(String str, Map<String, String> map) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        if (map == null) {
            map = Collections.emptyMap();
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return httpURLConnection;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x0052: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:25:0x0052
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private static com.efs.sdk.base.http.HttpResponse a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x0052: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:25:0x0052
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r5v0 ??
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

    @Override // com.efs.sdk.base.http.IHttpUtil
    @NonNull
    public final HttpResponse get(String str, Map<String, String> map) {
        HttpURLConnection httpURLConnection;
        HttpResponse httpResponse = new HttpResponse();
        int i = 0;
        while (true) {
            if (i >= 3) {
                break;
            }
            httpURLConnection = null;
            try {
                try {
                    httpURLConnection = a(str, map);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestProperty("Connection", "close");
                    httpURLConnection.connect();
                    httpResponse = a(httpURLConnection);
                    break;
                } catch (SocketTimeoutException e) {
                    httpResponse.setHttpCode(-3);
                    d.b("efs.util.http", "post file '" + str + "' error", e);
                }
            } catch (UnknownHostException e2) {
                httpResponse.setHttpCode(-2);
                d.b("efs.util.http", "get request '" + str + "' error， maybe network is disconnect", e2);
            } catch (Throwable th) {
                try {
                    d.b("efs.util.http", "get request '" + str + "' error", th);
                    i++;
                } finally {
                    b(httpURLConnection);
                }
            }
            b(httpURLConnection);
        }
        httpResponse.setReqUrl(str);
        return httpResponse;
    }

    @Override // com.efs.sdk.base.http.IHttpUtil
    @NonNull
    public final HttpResponse postAsFile(String str, Map<String, String> map, byte[] bArr) {
        return a(str, map, null, bArr);
    }

    @Override // com.efs.sdk.base.http.IHttpUtil
    @NonNull
    public final HttpResponse post(@NonNull String str, @Nullable Map<String, String> map, @NonNull File file) {
        return a(str, map, file, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23, types: [java.io.DataOutputStream, java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r9v0, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v26, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.io.Closeable] */
    /* JADX WARN: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.efs.sdk.base.http.HttpResponse a(@androidx.annotation.NonNull java.lang.String r6, @androidx.annotation.Nullable java.util.Map<java.lang.String, java.lang.String> r7, @androidx.annotation.Nullable java.io.File r8, @androidx.annotation.Nullable byte[] r9) {
        /*
            Method dump skipped, instructions count: 357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.a.h.b.a.a(java.lang.String, java.util.Map, java.io.File, byte[]):com.efs.sdk.base.http.HttpResponse");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.util.Map<java.lang.String, java.lang.String>, java.util.Map] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v9 */
    @Override // com.efs.sdk.base.http.IHttpUtil
    @NonNull
    public final HttpResponse post(@NonNull String str, @Nullable Map<String, String> map, @NonNull byte[] bArr) {
        Throwable th;
        OutputStream outputStream;
        HttpResponse httpResponse;
        UnknownHostException e;
        HttpURLConnection httpURLConnection;
        SocketTimeoutException e2;
        Throwable th2;
        try {
            httpResponse = new HttpResponse();
            outputStream = null;
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            httpURLConnection = a(str, map);
            try {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "close");
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(bArr);
                httpResponse = a(httpURLConnection);
                map = httpURLConnection;
            } catch (SocketTimeoutException e3) {
                e2 = e3;
                httpResponse.setHttpCode(-3);
                d.b("efs.util.http", "post file '" + str + "' error", e2);
                map = httpURLConnection;
                b.a(outputStream);
                b(map);
                httpResponse.setReqUrl(str);
                return httpResponse;
            } catch (UnknownHostException e4) {
                e = e4;
                httpResponse.setHttpCode(-2);
                d.b("efs.util.http", "post data to '" + str + "' error， maybe network is disconnect", e);
                map = httpURLConnection;
                b.a(outputStream);
                b(map);
                httpResponse.setReqUrl(str);
                return httpResponse;
            } catch (Throwable th4) {
                th2 = th4;
                d.b("efs.util.http", "post data '" + str + "' error", th2);
                map = httpURLConnection;
                b.a(outputStream);
                b(map);
                httpResponse.setReqUrl(str);
                return httpResponse;
            }
        } catch (SocketTimeoutException e5) {
            e2 = e5;
            httpURLConnection = null;
        } catch (UnknownHostException e6) {
            e = e6;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th2 = th5;
            httpURLConnection = null;
        }
        b.a(outputStream);
        b(map);
        httpResponse.setReqUrl(str);
        return httpResponse;
    }

    private static void b(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            try {
                b.a(httpURLConnection.getInputStream());
            } catch (Throwable unused) {
            }
        }
    }
}
