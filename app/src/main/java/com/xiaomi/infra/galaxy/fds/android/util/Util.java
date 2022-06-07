package com.xiaomi.infra.galaxy.fds.android.util;

import android.webkit.MimeTypeMap;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.FDSObject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class Util {
    private static final ThreadLocal<SimpleDateFormat> a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.android.util.Util.1
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };

    public static void downloadObjectToFile(FDSObject fDSObject, File file, boolean z) throws GalaxyFDSClientException {
        Throwable th;
        IOException e;
        File parentFile = file.getParentFile();
        if (!z && parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        byte[] bArr = new byte[4096];
        InputStream objectContent = fDSObject.getObjectContent();
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z));
                while (true) {
                    try {
                        int read = objectContent.read(bArr, 0, 4096);
                        if (read != -1) {
                            bufferedOutputStream2.write(bArr, 0, read);
                        } else {
                            try {
                                objectContent.close();
                                bufferedOutputStream2.close();
                                return;
                            } catch (IOException unused) {
                                return;
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedOutputStream = bufferedOutputStream2;
                        throw new GalaxyFDSClientException("Unable to store object[" + fDSObject.getBucketName() + "/" + fDSObject.getObjectName() + "] content to disk:" + e.getMessage(), e);
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = bufferedOutputStream2;
                        try {
                            objectContent.close();
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } catch (IOException unused2) {
                        }
                        throw th;
                    }
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String getStackTrace(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exc.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static String getMimeType(File file) {
        int i;
        String mimeTypeFromExtension;
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        return (lastIndexOf <= 0 || (i = lastIndexOf + 1) >= name.length() || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(i))) == null) ? Consts.APPLICATION_OCTET_STREAM : mimeTypeFromExtension;
    }

    public static Date parseDate(String str) throws ParseException {
        return a.get().parse(str);
    }

    public static String formatDateString(Date date) {
        return a.get().format(date);
    }
}
