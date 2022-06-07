package com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private String[] a(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (Build.VERSION.SDK_INT < 21 || applicationInfo.splitSourceDirs == null || applicationInfo.splitSourceDirs.length == 0) {
            return new String[]{applicationInfo.sourceDir};
        }
        String[] strArr = new String[applicationInfo.splitSourceDirs.length + 1];
        strArr[0] = applicationInfo.sourceDir;
        System.arraycopy(applicationInfo.splitSourceDirs, 0, strArr, 1, applicationInfo.splitSourceDirs.length);
        return strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        public ZipFile a;
        public ZipEntry b;

        public a(ZipFile zipFile, ZipEntry zipEntry) {
            this.a = zipFile;
            this.b = zipEntry;
        }
    }

    private a a(Context context, String[] strArr, String str, ReLinkerInstance reLinkerInstance) {
        int i;
        String[] a2 = a(context);
        int length = a2.length;
        int i2 = 0;
        ZipFile zipFile = null;
        int i3 = 0;
        while (i3 < length) {
            String str2 = a2[i3];
            int i4 = i2;
            while (true) {
                i4++;
                i = 5;
                if (i4 >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(str2), 1);
                    break;
                } catch (IOException unused) {
                }
            }
            if (zipFile != null) {
                int i5 = i2;
                while (true) {
                    i5++;
                    if (i5 < i) {
                        int length2 = strArr.length;
                        int i6 = i2;
                        while (i6 < length2) {
                            String str3 = "lib" + File.separatorChar + strArr[i6] + File.separatorChar + str;
                            Object[] objArr = new Object[2];
                            objArr[i2] = str3;
                            objArr[1] = str2;
                            reLinkerInstance.log("Looking for %s in APK %s...", objArr);
                            ZipEntry entry = zipFile.getEntry(str3);
                            if (entry != null) {
                                return new a(zipFile, entry);
                            }
                            i6++;
                            i2 = 0;
                            i = 5;
                        }
                        i2 = 0;
                    } else {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException unused2) {
                        }
                    }
                }
            }
            i3++;
            i2 = 0;
        }
        return null;
    }

    private String[] a(Context context, String str) {
        Pattern compile = Pattern.compile("lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + str);
        HashSet hashSet = new HashSet();
        for (String str2 : a(context)) {
            try {
                Enumeration<? extends ZipEntry> entries = new ZipFile(new File(str2), 1).entries();
                while (entries.hasMoreElements()) {
                    Matcher matcher = compile.matcher(((ZipEntry) entries.nextElement()).getName());
                    if (matcher.matches()) {
                        hashSet.add(matcher.group(1));
                    }
                }
            } catch (IOException unused) {
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    public void installLibrary(Context context, String[] strArr, String str, File file, ReLinkerInstance reLinkerInstance) {
        Throwable th;
        a aVar;
        String[] strArr2;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        Throwable th2;
        long a2;
        Closeable closeable = null;
        try {
            aVar = a(context, strArr, str, reLinkerInstance);
        } catch (Throwable th3) {
            th = th3;
            aVar = null;
        }
        try {
            if (aVar != null) {
                int i = 0;
                while (true) {
                    i++;
                    if (i < 5) {
                        reLinkerInstance.log("Found %s! Extracting...", str);
                        try {
                            if (file.exists() || file.createNewFile()) {
                                try {
                                    inputStream = aVar.a.getInputStream(aVar.b);
                                    try {
                                        fileOutputStream = new FileOutputStream(file);
                                    } catch (FileNotFoundException unused) {
                                        fileOutputStream = null;
                                    } catch (IOException unused2) {
                                        fileOutputStream = null;
                                    } catch (Throwable th4) {
                                        th2 = th4;
                                    }
                                } catch (FileNotFoundException unused3) {
                                    inputStream = null;
                                    fileOutputStream = null;
                                } catch (IOException unused4) {
                                    inputStream = null;
                                    fileOutputStream = null;
                                } catch (Throwable th5) {
                                    th2 = th5;
                                    inputStream = null;
                                }
                                try {
                                    a2 = a(inputStream, fileOutputStream);
                                    fileOutputStream.getFD().sync();
                                } catch (FileNotFoundException unused5) {
                                    a(inputStream);
                                    a(fileOutputStream);
                                } catch (IOException unused6) {
                                    a(inputStream);
                                    a(fileOutputStream);
                                } catch (Throwable th6) {
                                    th2 = th6;
                                    closeable = fileOutputStream;
                                    a(inputStream);
                                    a(closeable);
                                    throw th2;
                                }
                                if (a2 != file.length()) {
                                    a(inputStream);
                                    a(fileOutputStream);
                                } else {
                                    a(inputStream);
                                    a(fileOutputStream);
                                    file.setReadable(true, false);
                                    file.setExecutable(true, false);
                                    file.setWritable(true);
                                    if (aVar != null) {
                                        try {
                                            if (aVar.a != null) {
                                                aVar.a.close();
                                                return;
                                            }
                                            return;
                                        } catch (IOException unused7) {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                }
                            }
                        } catch (IOException unused8) {
                        }
                    } else {
                        reLinkerInstance.log("FATAL! Couldn't extract the library from the APK!");
                        if (aVar != null) {
                            try {
                                if (aVar.a != null) {
                                    aVar.a.close();
                                    return;
                                }
                                return;
                            } catch (IOException unused9) {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
            } else {
                try {
                    strArr2 = a(context, str);
                } catch (Exception e) {
                    strArr2 = new String[]{e.toString()};
                }
                throw new MissingLibraryException(str, strArr, strArr2);
            }
        } catch (Throwable th7) {
            th = th7;
            if (aVar != null) {
                try {
                    if (aVar.a != null) {
                        aVar.a.close();
                    }
                } catch (IOException unused10) {
                }
            }
            throw th;
        }
    }

    private long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += read;
        }
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
