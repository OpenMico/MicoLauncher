package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.hl;
import com.xiaomi.push.z;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class aa implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ hl b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(Context context, hl hlVar) {
        this.a = context;
        this.b = hlVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        RandomAccessFile randomAccessFile;
        Throwable th;
        Exception e;
        synchronized (bf.a) {
            FileLock fileLock = null;
            try {
                File file = new File(this.a.getFilesDir(), "tiny_data.lock");
                z.m1176a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    try {
                        fileLock = randomAccessFile.getChannel().lock();
                        bf.c(this.a, this.b);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e2) {
                                b.a(e2);
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        b.a(e);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e4) {
                                b.a(e4);
                            }
                        }
                        z.a(randomAccessFile);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e5) {
                            b.a(e5);
                        }
                    }
                    z.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                randomAccessFile = null;
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                if (fileLock != null) {
                    fileLock.release();
                }
                z.a(randomAccessFile);
                throw th;
            }
            z.a(randomAccessFile);
        }
    }
}
