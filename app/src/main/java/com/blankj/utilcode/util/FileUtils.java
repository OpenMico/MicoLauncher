package com.blankj.utilcode.util;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import com.blankj.utilcode.constant.RegexConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes.dex */
public final class FileUtils {
    private static final String a = System.getProperty("line.separator");

    /* loaded from: classes.dex */
    public interface OnReplaceListener {
        boolean onReplace(File file, File file2);
    }

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static File getFileByPath(String str) {
        if (b.o(str)) {
            return null;
        }
        return new File(str);
    }

    public static boolean isFileExists(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return isFileExists(file.getAbsolutePath());
    }

    public static boolean isFileExists(String str) {
        File fileByPath = getFileByPath(str);
        if (fileByPath == null) {
            return false;
        }
        if (fileByPath.exists()) {
            return true;
        }
        return a(str);
    }

    private static boolean a(String str) {
        if (Build.VERSION.SDK_INT < 29) {
            return false;
        }
        try {
            AssetFileDescriptor openAssetFileDescriptor = Utils.getApp().getContentResolver().openAssetFileDescriptor(Uri.parse(str), "r");
            if (openAssetFileDescriptor == null) {
                return false;
            }
            try {
                openAssetFileDescriptor.close();
                return true;
            } catch (IOException unused) {
                return true;
            }
        } catch (FileNotFoundException unused2) {
            return false;
        }
    }

    public static boolean rename(String str, String str2) {
        return rename(getFileByPath(str), str2);
    }

    public static boolean rename(File file, String str) {
        if (file == null || !file.exists() || b.o(str)) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        return !file2.exists() && file.renameTo(file2);
    }

    public static boolean isDir(String str) {
        return isDir(getFileByPath(str));
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(String str) {
        return isFile(getFileByPath(str));
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    public static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createFileByDeleteOldFile(String str) {
        return createFileByDeleteOldFile(getFileByPath(str));
    }

    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copy(String str, String str2) {
        return copy(getFileByPath(str), getFileByPath(str2), (OnReplaceListener) null);
    }

    public static boolean copy(String str, String str2, OnReplaceListener onReplaceListener) {
        return copy(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean copy(File file, File file2) {
        return copy(file, file2, (OnReplaceListener) null);
    }

    public static boolean copy(File file, File file2, OnReplaceListener onReplaceListener) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return a(file, file2, onReplaceListener);
        }
        return b(file, file2, onReplaceListener);
    }

    private static boolean a(File file, File file2, OnReplaceListener onReplaceListener) {
        return a(file, file2, onReplaceListener, false);
    }

    private static boolean b(File file, File file2, OnReplaceListener onReplaceListener) {
        return b(file, file2, onReplaceListener, false);
    }

    public static boolean move(String str, String str2) {
        return move(getFileByPath(str), getFileByPath(str2), (OnReplaceListener) null);
    }

    public static boolean move(String str, String str2, OnReplaceListener onReplaceListener) {
        return move(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean move(File file, File file2) {
        return move(file, file2, (OnReplaceListener) null);
    }

    public static boolean move(File file, File file2, OnReplaceListener onReplaceListener) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return moveDir(file, file2, onReplaceListener);
        }
        return moveFile(file, file2, onReplaceListener);
    }

    public static boolean moveDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return a(file, file2, onReplaceListener, true);
    }

    public static boolean moveFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return b(file, file2, onReplaceListener, true);
    }

    private static boolean a(File file, File file2, OnReplaceListener onReplaceListener, boolean z) {
        if (file == null || file2 == null) {
            return false;
        }
        String str = file2.getPath() + File.separator;
        if (!(!str.contains(file.getPath() + File.separator) && file.exists() && file.isDirectory() && createOrExistsDir(file2))) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file3 : listFiles) {
                File file4 = new File(str + file3.getName());
                if (file3.isFile()) {
                    if (!b(file3, file4, onReplaceListener, z)) {
                        return false;
                    }
                } else if (file3.isDirectory() && !a(file3, file4, onReplaceListener, z)) {
                    return false;
                }
            }
        }
        return !z || a(file);
    }

    private static boolean b(File file, File file2, OnReplaceListener onReplaceListener, boolean z) {
        if (file == null || file2 == null || file.equals(file2) || !file.exists() || !file.isFile()) {
            return false;
        }
        if (file2.exists()) {
            if (onReplaceListener != null && !onReplaceListener.onReplace(file, file2)) {
                return true;
            }
            if (!file2.delete()) {
                return false;
            }
        }
        if (!createOrExistsDir(file2.getParentFile())) {
            return false;
        }
        try {
            if (!b.a(file2.getAbsolutePath(), new FileInputStream(file))) {
                return false;
            }
            if (z) {
                if (!b(file)) {
                    return false;
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String str) {
        return delete(getFileByPath(str));
    }

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return a(file);
        }
        return b(file);
    }

    private static boolean a(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !a(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    private static boolean b(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean deleteAllInDir(String str) {
        return deleteAllInDir(getFileByPath(str));
    }

    public static boolean deleteAllInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    public static boolean deleteFilesInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.2
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    public static boolean deleteFilesInDirWithFilter(File file, FileFilter fileFilter) {
        if (file == null || fileFilter == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (!(listFiles == null || listFiles.length == 0)) {
            for (File file2 : listFiles) {
                if (fileFilter.accept(file2)) {
                    if (file2.isFile()) {
                        if (!file2.delete()) {
                            return false;
                        }
                    } else if (file2.isDirectory() && !a(file2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<File> listFilesInDir(String str) {
        return listFilesInDir(str, (Comparator<File>) null);
    }

    public static List<File> listFilesInDir(File file) {
        return listFilesInDir(file, (Comparator<File>) null);
    }

    public static List<File> listFilesInDir(String str, Comparator<File> comparator) {
        return listFilesInDir(getFileByPath(str), false, comparator);
    }

    public static List<File> listFilesInDir(File file, Comparator<File> comparator) {
        return listFilesInDir(file, false, comparator);
    }

    public static List<File> listFilesInDir(String str, boolean z) {
        return listFilesInDir(getFileByPath(str), z);
    }

    public static List<File> listFilesInDir(File file, boolean z) {
        return listFilesInDir(file, z, (Comparator<File>) null);
    }

    public static List<File> listFilesInDir(String str, boolean z, Comparator<File> comparator) {
        return listFilesInDir(getFileByPath(str), z, comparator);
    }

    public static List<File> listFilesInDir(File file, boolean z, Comparator<File> comparator) {
        return listFilesInDirWithFilter(file, new FileFilter() { // from class: com.blankj.utilcode.util.FileUtils.3
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        }, z, comparator);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter) {
        return listFilesInDirWithFilter(file, fileFilter, false, (Comparator<File>) null);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, Comparator<File> comparator) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, comparator);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, Comparator<File> comparator) {
        return listFilesInDirWithFilter(file, fileFilter, false, comparator);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z) {
        return listFilesInDirWithFilter(file, fileFilter, z, (Comparator<File>) null);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z, Comparator<File> comparator) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z, comparator);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z, Comparator<File> comparator) {
        List<File> a2 = a(file, fileFilter, z);
        if (comparator != null) {
            Collections.sort(a2, comparator);
        }
        return a2;
    }

    private static List<File> a(File file, FileFilter fileFilter, boolean z) {
        File[] listFiles;
        ArrayList arrayList = new ArrayList();
        if (isDir(file) && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
                if (z && file2.isDirectory()) {
                    arrayList.addAll(a(file2, fileFilter, true));
                }
            }
        }
        return arrayList;
    }

    public static long getFileLastModified(String str) {
        return getFileLastModified(getFileByPath(str));
    }

    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1L;
        }
        return file.lastModified();
    }

    public static String getFileCharsetSimple(String str) {
        return getFileCharsetSimple(getFileByPath(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getFileCharsetSimple(java.io.File r4) {
        /*
            if (r4 != 0) goto L_0x0005
            java.lang.String r4 = ""
            return r4
        L_0x0005:
            boolean r0 = isUtf8(r4)
            if (r0 == 0) goto L_0x000e
            java.lang.String r4 = "UTF-8"
            return r4
        L_0x000e:
            r0 = 0
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: IOException -> 0x0036, all -> 0x0034
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: IOException -> 0x0036, all -> 0x0034
            r3.<init>(r4)     // Catch: IOException -> 0x0036, all -> 0x0034
            r2.<init>(r3)     // Catch: IOException -> 0x0036, all -> 0x0034
            int r4 = r2.read()     // Catch: IOException -> 0x0031, all -> 0x002e
            int r4 = r4 << 8
            int r0 = r2.read()     // Catch: IOException -> 0x0031, all -> 0x002e
            int r0 = r0 + r4
            r2.close()     // Catch: IOException -> 0x0029
            goto L_0x003f
        L_0x0029:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x003f
        L_0x002e:
            r4 = move-exception
            r1 = r2
            goto L_0x0052
        L_0x0031:
            r4 = move-exception
            r1 = r2
            goto L_0x0037
        L_0x0034:
            r4 = move-exception
            goto L_0x0052
        L_0x0036:
            r4 = move-exception
        L_0x0037:
            r4.printStackTrace()     // Catch: all -> 0x0034
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch: IOException -> 0x0029
        L_0x003f:
            r4 = 65279(0xfeff, float:9.1475E-41)
            if (r0 == r4) goto L_0x004f
            r4 = 65534(0xfffe, float:9.1833E-41)
            if (r0 == r4) goto L_0x004c
            java.lang.String r4 = "GBK"
            return r4
        L_0x004c:
            java.lang.String r4 = "Unicode"
            return r4
        L_0x004f:
            java.lang.String r4 = "UTF-16BE"
            return r4
        L_0x0052:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch: IOException -> 0x0058
            goto L_0x005c
        L_0x0058:
            r0 = move-exception
            r0.printStackTrace()
        L_0x005c:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String");
    }

    public static boolean isUtf8(String str) {
        return isUtf8(getFileByPath(str));
    }

    public static boolean isUtf8(File file) {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        IOException e;
        byte[] bArr;
        BufferedInputStream bufferedInputStream2;
        boolean z = false;
        if (file == null) {
            return false;
        }
        try {
            bufferedInputStream = null;
            try {
                bArr = new byte[24];
                bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            int read = bufferedInputStream2.read(bArr);
            if (read != -1) {
                byte[] bArr2 = new byte[read];
                System.arraycopy(bArr, 0, bArr2, 0, read);
                if (a(bArr2) == 100) {
                    z = true;
                }
                try {
                    bufferedInputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return z;
            }
            try {
                bufferedInputStream2.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return false;
        } catch (IOException e5) {
            e = e5;
            bufferedInputStream = bufferedInputStream2;
            e.printStackTrace();
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (bufferedInputStream2 != null) {
                try {
                    bufferedInputStream2.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static int a(byte[] bArr) {
        if (bArr.length > 3 && bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) {
            return 100;
        }
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i < length) {
            if ((bArr[i] & (-1)) == -1 || (bArr[i] & (-2)) == -2) {
                return 0;
            }
            if (i4 == 0) {
                if ((bArr[i] & Byte.MAX_VALUE) == bArr[i] && bArr[i] != 0) {
                    i2++;
                } else if ((bArr[i] & (-64)) == -64) {
                    i4 = i4;
                    int i5 = 0;
                    while (i5 < 8) {
                        byte b = (byte) (128 >> i5);
                        if ((bArr[i] & b) != b) {
                            break;
                        }
                        i5++;
                        i4 = i5;
                    }
                    i3++;
                }
                i++;
            } else {
                if (bArr.length - i <= i4) {
                    i4 = bArr.length - i;
                }
                boolean z = false;
                int i6 = i2;
                for (int i7 = 0; i7 < i4; i7++) {
                    int i8 = i + i7;
                    if ((bArr[i8] & Byte.MIN_VALUE) != -128) {
                        if ((bArr[i8] & Byte.MAX_VALUE) == bArr[i8] && bArr[i] != 0) {
                            i6++;
                        }
                        z = true;
                    }
                }
                if (z) {
                    i3--;
                    i++;
                } else {
                    i3 += i4;
                    i += i4;
                }
                i4 = 0;
                i2 = i6;
            }
        }
        if (i2 == length) {
            return 100;
        }
        return (int) (((i3 + i2) / length) * 100.0f);
    }

    public static int getFileLines(String str) {
        return getFileLines(getFileByPath(str));
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 6, insn: 0x0033: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:17:0x0032
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static int getFileLines(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 6, insn: 0x0033: MOVE  (r0 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:17:0x0032
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r9v0 ??
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

    public static String getSize(String str) {
        return getSize(getFileByPath(str));
    }

    public static String getSize(File file) {
        if (file == null) {
            return "";
        }
        if (file.isDirectory()) {
            return c(file);
        }
        return d(file);
    }

    private static String c(File file) {
        long e = e(file);
        return e == -1 ? "" : b.a(e);
    }

    private static String d(File file) {
        long f = f(file);
        return f == -1 ? "" : b.a(f);
    }

    public static long getLength(String str) {
        return getLength(getFileByPath(str));
    }

    public static long getLength(File file) {
        if (file == null) {
            return 0L;
        }
        if (file.isDirectory()) {
            return e(file);
        }
        return f(file);
    }

    private static long e(File file) {
        long j;
        long j2 = 0;
        if (!isDir(file)) {
            return 0L;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    j = e(file2);
                } else {
                    j = file2.length();
                }
                j2 += j;
            }
        }
        return j2;
    }

    public static long getFileLength(String str) {
        if (str.matches(RegexConstants.REGEX_URL)) {
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                httpsURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpsURLConnection.connect();
                if (httpsURLConnection.getResponseCode() == 200) {
                    return httpsURLConnection.getContentLength();
                }
                return -1L;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f(getFileByPath(str));
    }

    private static long f(File file) {
        if (!isFile(file)) {
            return -1L;
        }
        return file.length();
    }

    public static String getFileMD5ToString(String str) {
        return getFileMD5ToString(b.o(str) ? null : new File(str));
    }

    public static String getFileMD5ToString(File file) {
        return b.a(getFileMD5(file));
    }

    public static byte[] getFileMD5(String str) {
        return getFileMD5(getFileByPath(str));
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x0048: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:28:0x0048
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static byte[] getFileMD5(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x0048: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:28:0x0048
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r3v0 ??
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

    public static String getDirName(File file) {
        return file == null ? "" : getDirName(file.getAbsolutePath());
    }

    public static String getDirName(String str) {
        int lastIndexOf;
        return (!b.o(str) && (lastIndexOf = str.lastIndexOf(File.separator)) != -1) ? str.substring(0, lastIndexOf + 1) : "";
    }

    public static String getFileName(File file) {
        return file == null ? "" : getFileName(file.getAbsolutePath());
    }

    public static String getFileName(String str) {
        if (b.o(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    public static String getFileNameNoExtension(File file) {
        return file == null ? "" : getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(String str) {
        if (b.o(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        int lastIndexOf2 = str.lastIndexOf(File.separator);
        if (lastIndexOf2 == -1) {
            return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
        }
        if (lastIndexOf == -1 || lastIndexOf2 > lastIndexOf) {
            return str.substring(lastIndexOf2 + 1);
        }
        return str.substring(lastIndexOf2 + 1, lastIndexOf);
    }

    public static String getFileExtension(File file) {
        return file == null ? "" : getFileExtension(file.getPath());
    }

    public static String getFileExtension(String str) {
        if (b.o(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf == -1 || str.lastIndexOf(File.separator) >= lastIndexOf) ? "" : str.substring(lastIndexOf + 1);
    }

    public static void notifySystemToScan(String str) {
        notifySystemToScan(getFileByPath(str));
    }

    public static void notifySystemToScan(File file) {
        if (file != null && file.exists()) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.parse("file://" + file.getAbsolutePath()));
            Utils.getApp().sendBroadcast(intent);
        }
    }

    public static long getFsTotalSize(String str) {
        long j;
        long j2;
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        StatFs statFs = new StatFs(str);
        if (Build.VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong();
            j = statFs.getBlockCountLong();
        } else {
            j2 = statFs.getBlockSize();
            j = statFs.getBlockCount();
        }
        return j2 * j;
    }

    public static long getFsAvailableSize(String str) {
        long j;
        long j2;
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        StatFs statFs = new StatFs(str);
        if (Build.VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong();
            j = statFs.getAvailableBlocksLong();
        } else {
            j2 = statFs.getBlockSize();
            j = statFs.getAvailableBlocks();
        }
        return j2 * j;
    }
}
