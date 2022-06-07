package io.realm.internal.async;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class RealmThreadPoolExecutor extends ThreadPoolExecutor {
    private static final int a = a();
    private boolean b;
    private ReentrantLock c = new ReentrantLock();
    private Condition d = this.c.newCondition();

    public static RealmThreadPoolExecutor newDefaultExecutor() {
        int i = a;
        return new RealmThreadPoolExecutor(i, i);
    }

    public static RealmThreadPoolExecutor newSingleThreadExecutor() {
        return new RealmThreadPoolExecutor(1, 1);
    }

    @SuppressFBWarnings({"DMI_HARDCODED_ABSOLUTE_FILENAME"})
    private static int a() {
        int a2 = a("/sys/devices/system/cpu/", "cpu[0-9]+");
        if (a2 <= 0) {
            a2 = Runtime.getRuntime().availableProcessors();
        }
        if (a2 <= 0) {
            return 1;
        }
        return 1 + (a2 * 2);
    }

    private static int a(String str, String str2) {
        final Pattern compile = Pattern.compile(str2);
        try {
            File[] listFiles = new File(str).listFiles(new FileFilter() { // from class: io.realm.internal.async.RealmThreadPoolExecutor.1
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return compile.matcher(file.getName()).matches();
                }
            });
            if (listFiles == null) {
                return 0;
            }
            return listFiles.length;
        } catch (SecurityException unused) {
            return 0;
        }
    }

    private RealmThreadPoolExecutor(int i, int i2) {
        super(i, i2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100));
    }

    public Future<?> submitTransaction(Runnable runnable) {
        return super.submit(new BgPriorityRunnable(runnable));
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.c.lock();
        while (this.b) {
            try {
                try {
                    this.d.await();
                } catch (InterruptedException unused) {
                    thread.interrupt();
                }
            } finally {
                this.c.unlock();
            }
        }
    }

    public void pause() {
        this.c.lock();
        try {
            this.b = true;
        } finally {
            this.c.unlock();
        }
    }

    public void resume() {
        this.c.lock();
        try {
            this.b = false;
            this.d.signalAll();
        } finally {
            this.c.unlock();
        }
    }
}
