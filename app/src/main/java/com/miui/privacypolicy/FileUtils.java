package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* loaded from: classes2.dex */
public class FileUtils {
    protected static final String ERROR_FILE_NAME = "privacy_agree_error";
    private static final String FOLDER_NAME = "privacypolicy";
    protected static final String INFO_FILE_NAME = "privacy_update";
    private static final String TAG = "Privacy_FileUtils";
    protected static final String TEMP_UPDATE_VERSION_FILE_NAME = "privacy_temp_update_version";
    protected static final String VERSION_FILE_NAME = "privacy_version";

    private FileUtils() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String readData(Context context, String str, String str2) {
        FileInputStream fileInputStream;
        Throwable th;
        Exception e;
        String str3 = context.getFilesDir().getPath() + File.separator + FOLDER_NAME + File.separator + str2 + File.separator + str;
        if (!new File(str3).exists()) {
            return "";
        }
        String str4 = null;
        try {
            fileInputStream = new FileInputStream(str3);
            try {
                try {
                    str4 = readInputStream(fileInputStream);
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "readData fail!", e);
                    closeQuietly(fileInputStream);
                    return str4;
                }
            } catch (Throwable th2) {
                th = th2;
                closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            closeQuietly(fileInputStream);
            throw th;
        }
        closeQuietly(fileInputStream);
        return str4;
    }

    /* JADX WARN: Finally extract failed */
    private static String readInputStream(FileInputStream fileInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                try {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                        closeQuietly(byteArrayOutputStream);
                        return byteArrayOutputStream2;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "readInputStream fail!", e);
                    closeQuietly(byteArrayOutputStream);
                    return null;
                }
            } catch (Throwable th) {
                closeQuietly(byteArrayOutputStream);
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void saveData(String str, Context context, String str2, String str3) {
        FileOutputStream fileOutputStream;
        Throwable th;
        Exception e;
        BufferedWriter bufferedWriter;
        File file = new File(context.getFilesDir().getPath() + File.separator + FOLDER_NAME + File.separator + str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedWriter bufferedWriter2 = null;
        try {
            fileOutputStream = new FileOutputStream(new File(file, str2));
            try {
                try {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedWriter.write(str);
                closeQuietly(bufferedWriter);
            } catch (Exception e3) {
                e = e3;
                bufferedWriter2 = bufferedWriter;
                Log.e(TAG, "saveData fail!", e);
                closeQuietly(bufferedWriter2);
                closeQuietly(fileOutputStream);
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter2 = bufferedWriter;
                closeQuietly(bufferedWriter2);
                closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
        closeQuietly(fileOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean deleteFile(Context context, String str, String str2) {
        File file = new File(context.getFilesDir().getPath() + File.separator + FOLDER_NAME + File.separator + str2 + File.separator + str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean isVersionFileExit(Context context, String str) {
        return !TextUtils.isEmpty(readData(context, VERSION_FILE_NAME, str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean isAgreeErrorFileExit(Context context, String str) {
        return !TextUtils.isEmpty(readData(context, ERROR_FILE_NAME, str));
    }

    protected static void closeQuietly(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly Writer error " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly InputStream error " + e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly OutputStream error " + e);
            }
            try {
                outputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, "closeQuietly OutputStream error " + e2);
            }
        }
    }
}
