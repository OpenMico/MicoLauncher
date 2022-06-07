package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public final class v {
    private static final Set<String> e = Collections.synchronizedSet(new HashSet());
    private Context a;
    private FileLock b;
    private String c;
    private RandomAccessFile d;

    private v(Context context) {
        this.a = context;
    }

    public static v a(Context context, File file) {
        b.c("Locking: " + file.getAbsolutePath());
        String str = file.getAbsolutePath() + ".LOCK";
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.getParentFile().mkdirs();
            file2.createNewFile();
        }
        if (e.add(str)) {
            v vVar = new v(context);
            vVar.c = str;
            try {
                vVar.d = new RandomAccessFile(file2, "rw");
                vVar.b = vVar.d.getChannel().lock();
                b.c("Locked: " + str + " :" + vVar.b);
                return vVar;
            } finally {
                if (vVar.b == null) {
                    RandomAccessFile randomAccessFile = vVar.d;
                    if (randomAccessFile != null) {
                        z.a(randomAccessFile);
                    }
                    e.remove(vVar.c);
                }
            }
        } else {
            throw new IOException("abtain lock failure");
        }
    }

    public void a() {
        b.c("unLock: " + this.b);
        FileLock fileLock = this.b;
        if (fileLock != null && fileLock.isValid()) {
            try {
                this.b.release();
            } catch (IOException unused) {
            }
            this.b = null;
        }
        RandomAccessFile randomAccessFile = this.d;
        if (randomAccessFile != null) {
            z.a(randomAccessFile);
        }
        e.remove(this.c);
    }
}
