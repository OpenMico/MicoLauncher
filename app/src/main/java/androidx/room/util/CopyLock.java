package androidx.room.util;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class CopyLock {
    private static final Map<String, Lock> a = new HashMap();
    private final File b;
    private final Lock c;
    private final boolean d;
    private FileChannel e;

    public CopyLock(@NonNull String str, @NonNull File file, boolean z) {
        this.b = new File(file, str + ".lck");
        this.c = a(this.b.getAbsolutePath());
        this.d = z;
    }

    public void lock() {
        this.c.lock();
        if (this.d) {
            try {
                this.e = new FileOutputStream(this.b).getChannel();
                this.e.lock();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to grab copy lock.", e);
            }
        }
    }

    public void unlock() {
        FileChannel fileChannel = this.e;
        if (fileChannel != null) {
            try {
                fileChannel.close();
            } catch (IOException unused) {
            }
        }
        this.c.unlock();
    }

    private static Lock a(String str) {
        Lock lock;
        synchronized (a) {
            lock = a.get(str);
            if (lock == null) {
                lock = new ReentrantLock();
                a.put(str, lock);
            }
        }
        return lock;
    }
}
