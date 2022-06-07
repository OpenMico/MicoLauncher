package com.xiaomi.infra.galaxy.fds.android;

import android.util.Log;
import com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.FDSObject;
import com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata;
import com.xiaomi.infra.galaxy.fds.android.model.ProgressListener;
import com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult;
import com.xiaomi.infra.galaxy.fds.android.model.ThumbParam;
import com.xiaomi.infra.galaxy.fds.android.model.UserParam;
import com.xiaomi.infra.galaxy.fds.android.util.Args;
import com.xiaomi.infra.galaxy.fds.android.util.RequestFactory;
import com.xiaomi.infra.galaxy.fds.android.util.Util;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/* loaded from: classes3.dex */
public class GalaxyFDSClientImpl implements GalaxyFDSClient {
    private static final boolean a;
    private final FDSClientConfiguration b;
    private final HttpClient c;
    private ThreadPoolExecutor d;

    static {
        String property = System.getProperty("java.runtime.name");
        if (property == null || !property.equals("android runtime")) {
            a = true;
        } else {
            a = false;
        }
    }

    public GalaxyFDSClientImpl(FDSClientConfiguration fDSClientConfiguration) {
        this.b = fDSClientConfiguration;
        this.c = a(this.b);
        this.d = new ThreadPoolExecutor(fDSClientConfiguration.getThreadPoolCoreSize(), fDSClientConfiguration.getThreadPoolMaxSize(), fDSClientConfiguration.getThreadPoolKeepAliveSecs(), TimeUnit.SECONDS, new ArrayBlockingQueue(fDSClientConfiguration.getWorkQueueCapacity(), true), new ThreadFactory() { // from class: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "FDS-multipart-upload-thread");
            }
        });
    }

    @Deprecated
    public GalaxyFDSClientImpl(String str, GalaxyFDSCredential galaxyFDSCredential, FDSClientConfiguration fDSClientConfiguration) {
        this.b = fDSClientConfiguration;
        this.b.setCredential(galaxyFDSCredential);
        this.c = a(this.b);
    }

    private HttpClient a(FDSClientConfiguration fDSClientConfiguration) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, fDSClientConfiguration.getConnectionTimeoutMs());
        HttpConnectionParams.setSoTimeout(basicHttpParams, fDSClientConfiguration.getSocketTimeoutMs());
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        int i = fDSClientConfiguration.getSocketBufferSizeHints()[0];
        int i2 = fDSClientConfiguration.getSocketBufferSizeHints()[1];
        if (i > 0 || i2 > 0) {
            HttpConnectionParams.setSocketBufferSize(basicHttpParams, Math.max(i, i2));
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        if (fDSClientConfiguration.isHttpsEnabled()) {
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme("https", socketFactory, 443));
        }
        return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
    }

    private boolean a(List<UserParam> list) {
        if (list != null) {
            for (UserParam userParam : list) {
                if (userParam instanceof ThumbParam) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2) throws GalaxyFDSClientException {
        return getObject(str, str2, 0L, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2, long j, List<UserParam> list) throws GalaxyFDSClientException {
        return getObject(str, str2, j, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public FDSObject getObject(String str, String str2, long j, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        Args.notNull(str, "bucket name");
        Args.notEmpty(str, "bucket name");
        Args.notNull(str2, "object name");
        Args.notEmpty(str2, "object name");
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.c());
        sb.append("/" + str + "/" + str2);
        return getObject(sb.toString(), j, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    @Deprecated
    public FDSObject getObject(String str, String str2, long j, List<UserParam> list, ProgressListener progressListener, boolean z) throws GalaxyFDSClientException {
        return getObject(str, str2, j, list, progressListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0151 A[ADDED_TO_REGION] */
    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.FDSObject getObject(java.lang.String r8, long r9, java.util.List<com.xiaomi.infra.galaxy.fds.android.model.UserParam> r11, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener r12) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.getObject(java.lang.String, long, java.util.List, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener):com.xiaomi.infra.galaxy.fds.android.model.FDSObject");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file) throws GalaxyFDSClientException {
        return getObject(str, str2, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return getObject(str, str2, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        long length;
        Args.notNull(file, "Destination file");
        boolean a2 = a(list);
        int i = 0;
        while (true) {
            boolean z = i != 0 && !a2;
            if (z) {
                try {
                    length = file.length();
                } catch (GalaxyFDSClientException e) {
                    i++;
                    if (i >= this.b.getMaxRetryTimes()) {
                        throw e;
                    } else if (!a) {
                        Log.i("GalaxyFDSClientImpl", "Retry the download of object:" + str2 + " bucket" + Constants.COLON_SEPARATOR + str + " to file: " + file.getAbsolutePath() + " cause:" + Util.getStackTrace(e));
                    }
                }
            } else {
                length = 0;
            }
            FDSObject object = getObject(str, str2, length, list, progressListener);
            Util.downloadObjectToFile(object, file, z);
            return object.getObjectMetadata();
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    @Deprecated
    public ObjectMetadata getObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener, boolean z) throws GalaxyFDSClientException {
        return getObject(str, str2, file, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public ObjectMetadata getObject(String str, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        long length;
        Args.notNull(file, "Destination file");
        boolean a2 = a(list);
        int i = 0;
        while (true) {
            boolean z = i != 0 && !a2;
            if (z) {
                try {
                    length = file.length();
                } catch (GalaxyFDSClientException e) {
                    i++;
                    if (i >= this.b.getMaxRetryTimes()) {
                        throw e;
                    } else if (!a) {
                        Log.i("GalaxyFDSClientImpl", "Retry the download of object:" + str + " to file: " + file.getAbsolutePath() + " cause:" + Util.getStackTrace(e));
                    }
                }
            } else {
                length = 0;
            }
            FDSObject object = getObject(str, length, list, progressListener);
            Util.downloadObjectToFile(object, file, z);
            return object.getObjectMetadata();
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file) throws GalaxyFDSClientException {
        return putObject(str, str2, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, str2, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        Args.notNull(file, "file");
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.length());
            objectMetadata.setContentType(Util.getMimeType(file));
            objectMetadata.setLastModified(new Date(file.lastModified()));
            return putObject(str, str2, bufferedInputStream, objectMetadata, list, progressListener);
        } catch (FileNotFoundException e) {
            throw new GalaxyFDSClientException("Unable to find the file to be uploaded:" + file.getAbsolutePath(), e);
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata) throws GalaxyFDSClientException {
        return putObject(str, str2, inputStream, objectMetadata, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, str2, inputStream, objectMetadata, list, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00fd  */
    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult putObject(final java.lang.String r22, java.lang.String r23, java.io.InputStream r24, final com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata r25, java.util.List<com.xiaomi.infra.galaxy.fds.android.model.UserParam> r26, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener r27) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.putObject(java.lang.String, java.lang.String, java.io.InputStream, com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata, java.util.List, com.xiaomi.infra.galaxy.fds.android.model.ProgressListener):com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.infra.galaxy.fds.android.model.InitMultipartUploadResult a(java.lang.String r5, java.lang.String r6, long r7) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.a(java.lang.String, java.lang.String, long):com.xiaomi.infra.galaxy.fds.android.model.InitMultipartUploadResult");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x010a, code lost:
        throw new com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException("Fail to parse the result of uploading part. bucket name:" + r18 + ", object name:" + r19 + ", upload ID:" + r17);
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0179 A[Catch: all -> 0x016a, TryCatch #7 {all -> 0x016a, blocks: (B:16:0x0087, B:17:0x009e, B:19:0x00b8, B:21:0x00cc, B:23:0x00d2, B:30:0x00e4, B:31:0x010a, B:32:0x010b, B:33:0x014b, B:37:0x0151, B:38:0x0167, B:44:0x0171, B:46:0x0179, B:48:0x017d, B:51:0x01c3), top: B:63:0x0087 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01c3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.infra.galaxy.fds.android.model.UploadPartResult a(java.lang.String r17, java.lang.String r18, java.lang.String r19, int r20, com.xiaomi.infra.galaxy.fds.android.util.ObjectInputStream r21, long r22) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.a(java.lang.String, java.lang.String, java.lang.String, int, com.xiaomi.infra.galaxy.fds.android.util.ObjectInputStream, long):com.xiaomi.infra.galaxy.fds.android.model.UploadPartResult");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.util.Map, java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.util.Map] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult a(java.lang.String r6, java.lang.String r7, java.lang.String r8, com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata r9, com.xiaomi.infra.galaxy.fds.android.model.UploadPartResultList r10, java.util.List<com.xiaomi.infra.galaxy.fds.android.model.UserParam> r11) throws com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClientImpl.a(java.lang.String, java.lang.String, java.lang.String, com.xiaomi.infra.galaxy.fds.android.model.ObjectMetadata, com.xiaomi.infra.galaxy.fds.android.model.UploadPartResultList, java.util.List):com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult");
    }

    private void a(String str, String str2, String str3) throws GalaxyFDSClientException {
        String str4 = this.b.d() + "/" + str + "/" + str2 + "?uploadId=" + str3;
        InputStream inputStream = null;
        try {
            try {
                HttpResponse execute = this.c.execute(RequestFactory.createRequest(str4, this.b.getCredential(), HttpMethod.DELETE, null));
                inputStream = execute.getEntity().getContent();
                if (execute.getStatusLine().getStatusCode() != 200) {
                    throw new GalaxyFDSClientException("Unable to upload object[" + str + "/" + str2 + "] to URI :" + str4 + ". Fail to abort multipart upload: " + execute.getStatusLine().toString());
                } else if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException e) {
                throw new GalaxyFDSClientException("Fail to abort multipart upload. URI:" + str4, e);
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file) throws GalaxyFDSClientException {
        return putObject(str, file, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, file, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, File file, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        return putObject(str, (String) null, file, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata) throws GalaxyFDSClientException {
        return putObject(str, inputStream, objectMetadata, (List<UserParam>) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list) throws GalaxyFDSClientException {
        return putObject(str, inputStream, objectMetadata, list, (ProgressListener) null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public PutObjectResult putObject(String str, InputStream inputStream, ObjectMetadata objectMetadata, List<UserParam> list, ProgressListener progressListener) throws GalaxyFDSClientException {
        return putObject(str, null, inputStream, objectMetadata, list, progressListener);
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.GalaxyFDSClient
    public boolean doesObjectExist(String str, String str2) throws GalaxyFDSClientException {
        Args.notNull(str, "bucket name");
        Args.notEmpty(str, "bucket name");
        Args.notNull(str2, "object name");
        Args.notEmpty(str2, "object name");
        String str3 = this.b.a() + "/" + str + "/" + str2;
        try {
            HttpResponse execute = this.c.execute(RequestFactory.createRequest(str3, this.b.getCredential(), HttpMethod.HEAD, null));
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            }
            if (statusCode == 404) {
                return false;
            }
            throw new GalaxyFDSClientException("Unable to head object[" + str + "/" + str2 + "] from URI :" + str3 + ". Cause:" + execute.getStatusLine().toString());
        } catch (IOException e) {
            throw new GalaxyFDSClientException("Unable to head object[" + str + "/" + str2 + "] from URI :" + str3 + " Exception:" + e.getMessage(), e);
        }
    }
}
