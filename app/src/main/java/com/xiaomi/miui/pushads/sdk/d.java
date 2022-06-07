package com.xiaomi.miui.pushads.sdk;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class d {
    public static int a(Context context, File file, String str, h hVar) {
        int i;
        String str2 = file.getAbsolutePath() + "/" + b(str);
        InputStream inputStream = null;
        try {
            if (new File(str2).exists()) {
                i = 0;
            } else {
                j.a("从sever 下载文件 debug 模式");
                inputStream = a(str);
                i = a(context, str2, inputStream);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception unused) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            i = -1;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        if (i == 0) {
            hVar.a(str2);
        }
        return i;
    }

    private static int a(Context context, String str, InputStream inputStream) {
        Throwable th;
        File file;
        OutputStream outputStream;
        int read;
        int i = -1;
        if (inputStream == null) {
            return -1;
        }
        try {
            try {
                file = new File(str + "_" + System.currentTimeMillis());
                try {
                    outputStream = new FileOutputStream(file);
                    try {
                        byte[] bArr = new byte[i.a];
                        while (true) {
                            boolean a = f.m749a(context);
                            read = inputStream.read(bArr);
                            if (read == -1 || !a) {
                                break;
                            }
                            outputStream.write(bArr, 0, read);
                        }
                        outputStream.flush();
                        if (read == -1) {
                            file.renameTo(new File(str));
                            i = 0;
                        }
                        outputStream.close();
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Exception unused) {
                        outputStream.close();
                        if (file.exists()) {
                            file.delete();
                        }
                        return i;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            outputStream.close();
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    outputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = null;
                }
            } catch (Exception unused3) {
                outputStream = null;
                file = null;
            } catch (Throwable th4) {
                th = th4;
                outputStream = null;
                file = null;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return i;
    }

    private static InputStream a(String str) {
        try {
            return ((HttpURLConnection) new URL(str).openConnection()).getInputStream();
        } catch (IOException unused) {
            return null;
        }
    }

    private static String b(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return str.substring(lastIndexOf < 0 ? 0 : lastIndexOf + 1);
    }
}
