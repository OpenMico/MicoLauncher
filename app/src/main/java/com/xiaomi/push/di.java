package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class di implements LoggerInterface {
    private String d;
    private Context e;
    private String f = "";
    private static final SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
    private static am c = new am(true);
    public static String a = "/MiPushLog";
    private static List<Pair<String, Throwable>> g = Collections.synchronizedList(new ArrayList());

    public di(Context context) {
        this.e = context;
        if (context.getApplicationContext() != null) {
            this.e = context.getApplicationContext();
        }
        this.d = this.e.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        BufferedWriter bufferedWriter;
        RandomAccessFile randomAccessFile;
        FileLock fileLock;
        Throwable th;
        Exception e;
        File file;
        File externalFilesDir;
        try {
            bufferedWriter = null;
            try {
                if (TextUtils.isEmpty(this.f) && (externalFilesDir = this.e.getExternalFilesDir(null)) != null) {
                    this.f = externalFilesDir.getAbsolutePath() + "";
                }
                file = new File(this.f + a);
            } catch (Exception e2) {
                e = e2;
                fileLock = null;
                randomAccessFile = null;
            } catch (Throwable th2) {
                th = th2;
                fileLock = null;
                randomAccessFile = null;
            }
        } catch (IOException e3) {
            Log.e(this.d, "", e3);
        }
        if ((!file.exists() || !file.isDirectory()) && !file.mkdirs()) {
            Log.w(this.d, "Create mipushlog directory fail.");
            return;
        }
        File file2 = new File(file, "log.lock");
        if (!file2.exists() || file2.isDirectory()) {
            file2.createNewFile();
        }
        randomAccessFile = new RandomAccessFile(file2, "rw");
        try {
            fileLock = randomAccessFile.getChannel().lock();
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file, "log1.txt"), true)));
                    while (!g.isEmpty()) {
                        try {
                            Pair<String, Throwable> remove = g.remove(0);
                            String str = (String) remove.first;
                            if (remove.second != null) {
                                str = (str + "\n") + Log.getStackTraceString((Throwable) remove.second);
                            }
                            bufferedWriter2.write(str + "\n");
                        } catch (Exception e4) {
                            e = e4;
                            bufferedWriter = bufferedWriter2;
                            Log.e(this.d, "", e);
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e5) {
                                    Log.e(this.d, "", e5);
                                }
                            }
                            if (fileLock != null && fileLock.isValid()) {
                                try {
                                    fileLock.release();
                                } catch (IOException e6) {
                                    Log.e(this.d, "", e6);
                                }
                            }
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return;
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e7) {
                                    Log.e(this.d, "", e7);
                                }
                            }
                            if (fileLock != null && fileLock.isValid()) {
                                try {
                                    fileLock.release();
                                } catch (IOException e8) {
                                    Log.e(this.d, "", e8);
                                }
                            }
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e9) {
                                    Log.e(this.d, "", e9);
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    File file3 = new File(file, "log1.txt");
                    if (file3.length() >= 1048576) {
                        File file4 = new File(file, "log0.txt");
                        if (file4.exists() && file4.isFile()) {
                            file4.delete();
                        }
                        file3.renameTo(file4);
                    }
                    if (0 != 0) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e10) {
                            Log.e(this.d, "", e10);
                        }
                    }
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e11) {
                            Log.e(this.d, "", e11);
                        }
                    }
                    randomAccessFile.close();
                } catch (Exception e12) {
                    e = e12;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Exception e13) {
            e = e13;
            fileLock = null;
        } catch (Throwable th5) {
            th = th5;
            fileLock = null;
        }
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str) {
        log(str, null);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void log(String str, Throwable th) {
        g.add(new Pair<>(String.format("%1$s %2$s %3$s ", b.format(new Date()), this.d, str), th));
        c.a(new dc(this));
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public final void setTag(String str) {
        this.d = str;
    }
}
