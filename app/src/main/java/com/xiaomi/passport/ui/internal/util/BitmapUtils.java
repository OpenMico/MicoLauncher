package com.xiaomi.passport.ui.internal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/* loaded from: classes4.dex */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap getImageBitmap(Context context, String str) {
        File fileStreamPath = context.getFileStreamPath(str);
        if (!fileStreamPath.isFile() || !fileStreamPath.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(fileStreamPath.getAbsolutePath());
    }

    public static File saveAsFile(Context context, InputStream inputStream, String str) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(context.openFileOutput(str, 0));
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = bufferedInputStream.read(bArr);
                if (read != -1) {
                    bufferedOutputStream.write(bArr, 0, read);
                } else {
                    try {
                        break;
                    } catch (IOException unused) {
                    }
                }
            } finally {
                try {
                    bufferedOutputStream.flush();
                } catch (IOException unused2) {
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused3) {
                }
            }
        }
        return context.getFileStreamPath(str);
    }

    /* JADX WARN: Finally extract failed */
    public static Bitmap getUserAvatarByAbsPath(Context context, String str) {
        try {
            FileInputStream openFileInput = context.openFileInput(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                try {
                    int read = openFileInput.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } catch (Throwable th) {
                    closeQuietly(byteArrayOutputStream);
                    closeQuietly(openFileInput);
                    throw th;
                }
            }
            closeQuietly(byteArrayOutputStream);
            closeQuietly(openFileInput);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArray != null) {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            return null;
        } catch (FileNotFoundException unused) {
            AccountLog.e(TAG, "File is not found");
            return null;
        } catch (IOException unused2) {
            AccountLog.e(TAG, "Read data error");
            return null;
        }
    }

    public static String saveUserAvatarByUrl(Context context, String str, String str2) {
        String str3;
        Exception e;
        Bitmap decodeStream;
        FileOutputStream fileOutputStream = null;
        try {
            decodeStream = BitmapFactory.decodeStream(new URL(str).openStream());
        } catch (Exception e2) {
            e = e2;
            str3 = null;
        }
        if (decodeStream == null) {
            return null;
        }
        Uri parse = Uri.parse(str);
        str3 = str2 + "_" + parse.getLastPathSegment();
        try {
            try {
                try {
                    fileOutputStream = context.openFileOutput(str3, 0);
                    decodeStream.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                } catch (IOException e3) {
                    AccountLog.e(TAG, "Save file exception", e3);
                }
            } catch (FileNotFoundException e4) {
                AccountLog.e(TAG, "File is not found", e4);
            }
            closeQuietly(fileOutputStream);
        } catch (Exception e5) {
            e = e5;
            AccountLog.e(TAG, "Get data exception", e);
            return str3;
        }
        return str3;
    }

    private static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException unused) {
            }
            try {
                outputStream.close();
            } catch (IOException unused2) {
            }
        }
    }
}
