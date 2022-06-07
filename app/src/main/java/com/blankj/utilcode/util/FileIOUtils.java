package com.blankj.utilcode.util;

import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/* loaded from: classes.dex */
public final class FileIOUtils {
    private static int a = 524288;

    /* loaded from: classes.dex */
    public interface OnProgressUpdateListener {
        void onProgressUpdate(double d);
    }

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream) {
        return writeFileFromIS(b.f(str), inputStream, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z) {
        return writeFileFromIS(b.f(str), inputStream, z, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream) {
        return writeFileFromIS(file, inputStream, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, boolean z) {
        return writeFileFromIS(file, inputStream, z, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(b.f(str), inputStream, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(b.f(str), inputStream, z, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(file, inputStream, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, boolean z, OnProgressUpdateListener onProgressUpdateListener) {
        Throwable th;
        IOException e;
        if (inputStream == null || !b.d(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, z), a);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (onProgressUpdateListener != null) {
                double available = inputStream.available();
                onProgressUpdateListener.onProgressUpdate(0.0d);
                byte[] bArr = new byte[a];
                int i = 0;
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, read);
                    i += read;
                    onProgressUpdateListener.onProgressUpdate(i / available);
                }
            } else {
                byte[] bArr2 = new byte[a];
                while (true) {
                    int read2 = inputStream.read(bArr2);
                    if (read2 == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr2, 0, read2);
                }
            }
            try {
                inputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            try {
                bufferedOutputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return true;
        } catch (IOException e5) {
            e = e5;
            bufferedOutputStream = bufferedOutputStream;
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            try {
                inputStream.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr) {
        return writeFileFromBytesByStream(b.f(str), bArr, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByStream(b.f(str), bArr, z, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr) {
        return writeFileFromBytesByStream(file, bArr, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z) {
        return writeFileFromBytesByStream(file, bArr, z, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(b.f(str), bArr, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(b.f(str), bArr, z, onProgressUpdateListener);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(file, bArr, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z, OnProgressUpdateListener onProgressUpdateListener) {
        if (bArr == null) {
            return false;
        }
        return writeFileFromIS(file, new ByteArrayInputStream(bArr), z, onProgressUpdateListener);
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByChannel(b.f(str), bArr, false, z);
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z, boolean z2) {
        return writeFileFromBytesByChannel(b.f(str), bArr, z, z2);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z) {
        return writeFileFromBytesByChannel(file, bArr, false, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.nio.channels.FileChannel] */
    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z, boolean z2) {
        if (bArr == null) {
            Log.e("FileIOUtils", "bytes is null.");
            return false;
        }
        FileChannel d = b.d(file);
        if (d == 0) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        try {
            d = 0;
            try {
                FileChannel channel = new FileOutputStream(file, z).getChannel();
                if (channel == null) {
                    Log.e("FileIOUtils", "fc is null.");
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
                channel.position(channel.size());
                channel.write(ByteBuffer.wrap(bArr));
                if (z2) {
                    channel.force(true);
                }
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return true;
            } catch (IOException e3) {
                e3.printStackTrace();
                if (0 != 0) {
                    try {
                        d.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (d != 0) {
                try {
                    d.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z) {
        return writeFileFromBytesByMap(str, bArr, false, z);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z, boolean z2) {
        return writeFileFromBytesByMap(b.f(str), bArr, z, z2);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z) {
        return writeFileFromBytesByMap(file, bArr, false, z);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z, boolean z2) {
        Throwable th;
        IOException e;
        if (bArr == null || !b.d(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        FileChannel fileChannel = null;
        try {
            try {
                fileChannel = new FileOutputStream(file, z).getChannel();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (fileChannel == null) {
                Log.e("FileIOUtils", "fc is null.");
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
            }
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, fileChannel.size(), bArr.length);
            map.put(bArr);
            if (z2) {
                map.force();
            }
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return true;
        } catch (IOException e5) {
            e = e5;
            fileChannel = fileChannel;
            e.printStackTrace();
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromString(String str, String str2) {
        return writeFileFromString(b.f(str), str2, false);
    }

    public static boolean writeFileFromString(String str, String str2, boolean z) {
        return writeFileFromString(b.f(str), str2, z);
    }

    public static boolean writeFileFromString(File file, String str) {
        return writeFileFromString(file, str, false);
    }

    public static boolean writeFileFromString(File file, String str, boolean z) {
        Throwable th;
        IOException e;
        BufferedWriter bufferedWriter;
        if (file == null || str == null) {
            return false;
        }
        if (!b.d(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file, z));
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.write(str);
            try {
                bufferedWriter.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return true;
        } catch (IOException e4) {
            e = e4;
            bufferedWriter2 = bufferedWriter;
            e.printStackTrace();
            if (bufferedWriter2 != null) {
                try {
                    bufferedWriter2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
                try {
                    bufferedWriter2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static List<String> readFile2List(String str) {
        return readFile2List(b.f(str), (String) null);
    }

    public static List<String> readFile2List(String str, String str2) {
        return readFile2List(b.f(str), str2);
    }

    public static List<String> readFile2List(File file) {
        return readFile2List(file, 0, Integer.MAX_VALUE, (String) null);
    }

    public static List<String> readFile2List(File file, String str) {
        return readFile2List(file, 0, Integer.MAX_VALUE, str);
    }

    public static List<String> readFile2List(String str, int i, int i2) {
        return readFile2List(b.f(str), i, i2, (String) null);
    }

    public static List<String> readFile2List(String str, int i, int i2, String str2) {
        return readFile2List(b.f(str), i, i2, str2);
    }

    public static List<String> readFile2List(File file, int i, int i2) {
        return readFile2List(file, i, i2, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> readFile2List(java.io.File r6, int r7, int r8, java.lang.String r9) {
        /*
            boolean r0 = com.blankj.utilcode.util.b.b(r6)
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            if (r7 <= r8) goto L_0x000b
            return r1
        L_0x000b:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: IOException -> 0x0059, all -> 0x0057
            r0.<init>()     // Catch: IOException -> 0x0059, all -> 0x0057
            boolean r2 = com.blankj.utilcode.util.b.o(r9)     // Catch: IOException -> 0x0059, all -> 0x0057
            r3 = 1
            if (r2 == 0) goto L_0x0027
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch: IOException -> 0x0059, all -> 0x0057
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: IOException -> 0x0059, all -> 0x0057
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: IOException -> 0x0059, all -> 0x0057
            r4.<init>(r6)     // Catch: IOException -> 0x0059, all -> 0x0057
            r2.<init>(r4)     // Catch: IOException -> 0x0059, all -> 0x0057
            r9.<init>(r2)     // Catch: IOException -> 0x0059, all -> 0x0057
            goto L_0x0037
        L_0x0027:
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: IOException -> 0x0059, all -> 0x0057
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: IOException -> 0x0059, all -> 0x0057
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: IOException -> 0x0059, all -> 0x0057
            r5.<init>(r6)     // Catch: IOException -> 0x0059, all -> 0x0057
            r4.<init>(r5, r9)     // Catch: IOException -> 0x0059, all -> 0x0057
            r2.<init>(r4)     // Catch: IOException -> 0x0059, all -> 0x0057
            r9 = r2
        L_0x0037:
            java.lang.String r6 = r9.readLine()     // Catch: IOException -> 0x0055, all -> 0x0069
            if (r6 == 0) goto L_0x004a
            if (r3 <= r8) goto L_0x0040
            goto L_0x004a
        L_0x0040:
            if (r7 > r3) goto L_0x0047
            if (r3 > r8) goto L_0x0047
            r0.add(r6)     // Catch: IOException -> 0x0055, all -> 0x0069
        L_0x0047:
            int r3 = r3 + 1
            goto L_0x0037
        L_0x004a:
            if (r9 == 0) goto L_0x0054
            r9.close()     // Catch: IOException -> 0x0050
            goto L_0x0054
        L_0x0050:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0054:
            return r0
        L_0x0055:
            r6 = move-exception
            goto L_0x005b
        L_0x0057:
            r6 = move-exception
            goto L_0x006b
        L_0x0059:
            r6 = move-exception
            r9 = r1
        L_0x005b:
            r6.printStackTrace()     // Catch: all -> 0x0069
            if (r9 == 0) goto L_0x0068
            r9.close()     // Catch: IOException -> 0x0064
            goto L_0x0068
        L_0x0064:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0068:
            return r1
        L_0x0069:
            r6 = move-exception
            r1 = r9
        L_0x006b:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch: IOException -> 0x0071
            goto L_0x0075
        L_0x0071:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0075:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileIOUtils.readFile2List(java.io.File, int, int, java.lang.String):java.util.List");
    }

    public static String readFile2String(String str) {
        return readFile2String(b.f(str), (String) null);
    }

    public static String readFile2String(String str, String str2) {
        return readFile2String(b.f(str), str2);
    }

    public static String readFile2String(File file) {
        return readFile2String(file, (String) null);
    }

    public static String readFile2String(File file, String str) {
        byte[] readFile2BytesByStream = readFile2BytesByStream(file);
        if (readFile2BytesByStream == null) {
            return null;
        }
        if (b.o(str)) {
            return new String(readFile2BytesByStream);
        }
        try {
            return new String(readFile2BytesByStream, str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readFile2BytesByStream(String str) {
        return readFile2BytesByStream(b.f(str), (OnProgressUpdateListener) null);
    }

    public static byte[] readFile2BytesByStream(File file) {
        return readFile2BytesByStream(file, (OnProgressUpdateListener) null);
    }

    public static byte[] readFile2BytesByStream(String str, OnProgressUpdateListener onProgressUpdateListener) {
        return readFile2BytesByStream(b.f(str), onProgressUpdateListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] readFile2BytesByStream(java.io.File r10, com.blankj.utilcode.util.FileIOUtils.OnProgressUpdateListener r11) {
        /*
            boolean r0 = com.blankj.utilcode.util.b.b(r10)
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch: FileNotFoundException -> 0x0090
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: FileNotFoundException -> 0x0090
            r2.<init>(r10)     // Catch: FileNotFoundException -> 0x0090
            int r10 = com.blankj.utilcode.util.FileIOUtils.a     // Catch: FileNotFoundException -> 0x0090
            r0.<init>(r2, r10)     // Catch: FileNotFoundException -> 0x0090
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream     // Catch: IOException -> 0x0064, all -> 0x0061
            r10.<init>()     // Catch: IOException -> 0x0064, all -> 0x0061
            int r2 = com.blankj.utilcode.util.FileIOUtils.a     // Catch: IOException -> 0x005f, all -> 0x007c
            byte[] r2 = new byte[r2]     // Catch: IOException -> 0x005f, all -> 0x007c
            r3 = -1
            r4 = 0
            if (r11 != 0) goto L_0x002d
        L_0x0021:
            int r11 = com.blankj.utilcode.util.FileIOUtils.a     // Catch: IOException -> 0x005f, all -> 0x007c
            int r11 = r0.read(r2, r4, r11)     // Catch: IOException -> 0x005f, all -> 0x007c
            if (r11 == r3) goto L_0x004a
            r10.write(r2, r4, r11)     // Catch: IOException -> 0x005f, all -> 0x007c
            goto L_0x0021
        L_0x002d:
            int r5 = r0.available()     // Catch: IOException -> 0x005f, all -> 0x007c
            double r5 = (double) r5     // Catch: IOException -> 0x005f, all -> 0x007c
            r7 = 0
            r11.onProgressUpdate(r7)     // Catch: IOException -> 0x005f, all -> 0x007c
            r7 = r4
        L_0x0038:
            int r8 = com.blankj.utilcode.util.FileIOUtils.a     // Catch: IOException -> 0x005f, all -> 0x007c
            int r8 = r0.read(r2, r4, r8)     // Catch: IOException -> 0x005f, all -> 0x007c
            if (r8 == r3) goto L_0x004a
            r10.write(r2, r4, r8)     // Catch: IOException -> 0x005f, all -> 0x007c
            int r7 = r7 + r8
            double r8 = (double) r7     // Catch: IOException -> 0x005f, all -> 0x007c
            double r8 = r8 / r5
            r11.onProgressUpdate(r8)     // Catch: IOException -> 0x005f, all -> 0x007c
            goto L_0x0038
        L_0x004a:
            byte[] r11 = r10.toByteArray()     // Catch: IOException -> 0x005f, all -> 0x007c
            r0.close()     // Catch: IOException -> 0x0052, FileNotFoundException -> 0x0090
            goto L_0x0056
        L_0x0052:
            r0 = move-exception
            r0.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x0056:
            r10.close()     // Catch: IOException -> 0x005a, FileNotFoundException -> 0x0090
            goto L_0x005e
        L_0x005a:
            r10 = move-exception
            r10.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x005e:
            return r11
        L_0x005f:
            r11 = move-exception
            goto L_0x0066
        L_0x0061:
            r11 = move-exception
            r10 = r1
            goto L_0x007d
        L_0x0064:
            r11 = move-exception
            r10 = r1
        L_0x0066:
            r11.printStackTrace()     // Catch: all -> 0x007c
            r0.close()     // Catch: IOException -> 0x006d, FileNotFoundException -> 0x0090
            goto L_0x0071
        L_0x006d:
            r11 = move-exception
            r11.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x0071:
            if (r10 == 0) goto L_0x007b
            r10.close()     // Catch: IOException -> 0x0077, FileNotFoundException -> 0x0090
            goto L_0x007b
        L_0x0077:
            r10 = move-exception
            r10.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x007b:
            return r1
        L_0x007c:
            r11 = move-exception
        L_0x007d:
            r0.close()     // Catch: IOException -> 0x0081, FileNotFoundException -> 0x0090
            goto L_0x0085
        L_0x0081:
            r0 = move-exception
            r0.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x0085:
            if (r10 == 0) goto L_0x008f
            r10.close()     // Catch: IOException -> 0x008b, FileNotFoundException -> 0x0090
            goto L_0x008f
        L_0x008b:
            r10 = move-exception
            r10.printStackTrace()     // Catch: FileNotFoundException -> 0x0090
        L_0x008f:
            throw r11     // Catch: FileNotFoundException -> 0x0090
        L_0x0090:
            r10 = move-exception
            r10.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileIOUtils.readFile2BytesByStream(java.io.File, com.blankj.utilcode.util.FileIOUtils$OnProgressUpdateListener):byte[]");
    }

    public static byte[] readFile2BytesByChannel(String str) {
        return readFile2BytesByChannel(b.f(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6 */
    public static byte[] readFile2BytesByChannel(File file) {
        Throwable th;
        FileChannel fileChannel;
        IOException e;
        try {
            if (!b.b(file)) {
                return null;
            }
            try {
                fileChannel = new RandomAccessFile(file, "r").getChannel();
                try {
                    if (fileChannel == null) {
                        Log.e("FileIOUtils", "fc is null.");
                        byte[] bArr = new byte[0];
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return bArr;
                    }
                    ByteBuffer allocate = ByteBuffer.allocate((int) fileChannel.size());
                    do {
                    } while (fileChannel.read(allocate) > 0);
                    byte[] array = allocate.array();
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return array;
                } catch (IOException e4) {
                    e = e4;
                    e.printStackTrace();
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (IOException e6) {
                e = e6;
                fileChannel = null;
            } catch (Throwable th2) {
                th = th2;
                file = 0;
                if (file != 0) {
                    try {
                        file.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static byte[] readFile2BytesByMap(String str) {
        return readFile2BytesByMap(b.f(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v6 */
    public static byte[] readFile2BytesByMap(File file) {
        Throwable th;
        FileChannel fileChannel;
        IOException e;
        try {
            if (!b.b(file)) {
                return null;
            }
            try {
                fileChannel = new RandomAccessFile(file, "r").getChannel();
                try {
                    if (fileChannel == null) {
                        Log.e("FileIOUtils", "fc is null.");
                        byte[] bArr = new byte[0];
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return bArr;
                    }
                    int size = (int) fileChannel.size();
                    byte[] bArr2 = new byte[size];
                    fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, size).load().get(bArr2, 0, size);
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return bArr2;
                } catch (IOException e4) {
                    e = e4;
                    e.printStackTrace();
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (IOException e6) {
                e = e6;
                fileChannel = null;
            } catch (Throwable th2) {
                th = th2;
                file = 0;
                if (file != 0) {
                    try {
                        file.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void setBufferSize(int i) {
        a = i;
    }
}
