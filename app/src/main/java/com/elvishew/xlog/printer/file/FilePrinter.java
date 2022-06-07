package com.elvishew.xlog.printer.file;

import android.util.Log;
import com.elvishew.xlog.flattener.Flattener;
import com.elvishew.xlog.flattener.Flattener2;
import com.elvishew.xlog.internal.DefaultsFactory;
import com.elvishew.xlog.internal.Platform;
import com.elvishew.xlog.internal.printer.file.backup.BackupStrategyWrapper;
import com.elvishew.xlog.internal.printer.file.backup.BackupUtil;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.backup.BackupStrategy;
import com.elvishew.xlog.printer.file.backup.BackupStrategy2;
import com.elvishew.xlog.printer.file.clean.CleanStrategy;
import com.elvishew.xlog.printer.file.naming.FileNameGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter.class */
public class FilePrinter implements Printer {
    private static final boolean USE_WORKER = true;
    private final String folderPath;
    private final FileNameGenerator fileNameGenerator;
    private final BackupStrategy2 backupStrategy;
    private final CleanStrategy cleanStrategy;
    private Flattener2 flattener;
    private Writer writer = new Writer();
    private volatile Worker worker = new Worker();

    public FilePrinter(Builder builder) {
        this.folderPath = builder.folderPath;
        this.fileNameGenerator = builder.fileNameGenerator;
        this.backupStrategy = builder.backupStrategy;
        this.cleanStrategy = builder.cleanStrategy;
        this.flattener = builder.flattener;
        checkLogFolder();
    }

    private void checkLogFolder() {
        File folder = new File(this.folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override // com.elvishew.xlog.printer.Printer
    public void println(int logLevel, String tag, String msg) {
        long timeMillis = System.currentTimeMillis();
        if (!this.worker.isStarted()) {
            this.worker.start();
        }
        this.worker.enqueue(new LogItem(timeMillis, logLevel, tag, msg));
    }

    public void doPrintln(long timeMillis, int logLevel, String tag, String msg) {
        String lastFileName = this.writer.getLastFileName();
        boolean isWriterClosed = !this.writer.isOpened();
        if (lastFileName == null || isWriterClosed || this.fileNameGenerator.isFileNameChangeable()) {
            String newFileName = this.fileNameGenerator.generateFileName(logLevel, System.currentTimeMillis());
            if (newFileName == null || newFileName.trim().length() == 0) {
                Platform.get().error("File name should not be empty, ignore log: " + msg);
                return;
            } else if (!newFileName.equals(lastFileName) || isWriterClosed) {
                this.writer.close();
                cleanLogFilesIfNecessary();
                if (this.writer.open(newFileName)) {
                    lastFileName = newFileName;
                } else {
                    return;
                }
            }
        }
        File lastFile = this.writer.getFile();
        if (this.backupStrategy.shouldBackup(lastFile)) {
            this.writer.close();
            BackupUtil.backup(lastFile, this.backupStrategy);
            if (!this.writer.open(lastFileName)) {
                return;
            }
        }
        this.writer.appendLog(this.flattener.flatten(timeMillis, logLevel, tag, msg).toString());
    }

    private void cleanLogFilesIfNecessary() {
        File[] files = new File(this.folderPath).listFiles();
        if (files != null) {
            for (File file : files) {
                if (this.cleanStrategy.shouldClean(file)) {
                    file.delete();
                }
            }
        }
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter$Builder.class */
    public static class Builder {
        String folderPath;
        FileNameGenerator fileNameGenerator;
        BackupStrategy2 backupStrategy;
        CleanStrategy cleanStrategy;
        Flattener2 flattener;

        public Builder(String folderPath) {
            this.folderPath = folderPath;
        }

        public Builder fileNameGenerator(FileNameGenerator fileNameGenerator) {
            this.fileNameGenerator = fileNameGenerator;
            return this;
        }

        public Builder backupStrategy(BackupStrategy backupStrategy) {
            if (!(backupStrategy instanceof BackupStrategy2)) {
                backupStrategy = new BackupStrategyWrapper(backupStrategy);
            }
            this.backupStrategy = (BackupStrategy2) backupStrategy;
            BackupUtil.verifyBackupStrategy(this.backupStrategy);
            return this;
        }

        public Builder cleanStrategy(CleanStrategy cleanStrategy) {
            this.cleanStrategy = cleanStrategy;
            return this;
        }

        /* renamed from: com.elvishew.xlog.printer.file.FilePrinter$Builder$1 */
        /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter$Builder$1.class */
        public class AnonymousClass1 implements Flattener2 {
            final /* synthetic */ Flattener val$flattener;

            public AnonymousClass1(Flattener flattener) {
                Builder.this = this$0;
                this.val$flattener = flattener;
            }

            @Override // com.elvishew.xlog.flattener.Flattener2
            public CharSequence flatten(long timeMillis, int logLevel, String tag, String message) {
                return this.val$flattener.flatten(logLevel, tag, message);
            }
        }

        @Deprecated
        public Builder logFlattener(Flattener flattener) {
            return flattener(new AnonymousClass1(flattener));
        }

        public Builder flattener(Flattener2 flattener) {
            this.flattener = flattener;
            return this;
        }

        public FilePrinter build() {
            fillEmptyFields();
            return new FilePrinter(this);
        }

        public void fillEmptyFields() {
            if (this.fileNameGenerator == null) {
                this.fileNameGenerator = DefaultsFactory.createFileNameGenerator();
            }
            if (this.backupStrategy == null) {
                this.backupStrategy = DefaultsFactory.createBackupStrategy();
            }
            if (this.cleanStrategy == null) {
                this.cleanStrategy = DefaultsFactory.createCleanStrategy();
            }
            if (this.flattener == null) {
                this.flattener = DefaultsFactory.createFlattener2();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        long a;
        int b;
        String c;
        String d;

        a(long j, int i, String str, String str2) {
            this.a = j;
            this.b = i;
            this.c = str;
            this.d = str2;
        }
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter$LogItem.class */
    public static class LogItem {
        long timeMillis;
        int level;
        String tag;
        String msg;

        LogItem(long timeMillis, int level, String tag, String msg) {
            this.timeMillis = timeMillis;
            this.level = level;
            this.tag = tag;
            this.msg = msg;
        }
    }

    /* loaded from: classes.dex */
    private class b implements Runnable {
        private BlockingQueue<a> b;
        private volatile boolean c;

        private b() {
            FilePrinter.this = r1;
            this.b = new LinkedBlockingQueue();
        }

        void a(a aVar) {
            try {
                this.b.put(aVar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean a() {
            boolean z;
            synchronized (this) {
                z = this.c;
            }
            return z;
        }

        void b() {
            synchronized (this) {
                if (!this.c) {
                    new Thread(this).start();
                    this.c = true;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    a take = this.b.take();
                    if (take != null) {
                        FilePrinter.a(FilePrinter.this, take.a, take.b, take.c, take.d);
                    } else {
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    synchronized (this) {
                        this.c = false;
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter$Worker.class */
    public class Worker implements Runnable {
        private BlockingQueue<LogItem> logs;
        private volatile boolean started;
        private static final int MAX_SIZE = 1000;

        private Worker() {
            FilePrinter.this = r5;
            this.logs = new LinkedBlockingQueue();
        }

        void enqueue(LogItem log) {
            try {
                if (this.logs.size() < 1000) {
                    this.logs.put(log);
                } else {
                    Log.e("Xlog", "logs size max");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        boolean isStarted() {
            boolean z;
            synchronized (this) {
                z = this.started;
            }
            return z;
        }

        void start() {
            synchronized (this) {
                if (!this.started) {
                    Thread t = new Thread(this);
                    t.setName("XLogFilePrinter");
                    t.start();
                    this.started = true;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    LogItem log = this.logs.take();
                    if (log != null) {
                        FilePrinter.this.doPrintln(log.timeMillis, log.level, log.tag, log.msg);
                    } else {
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    synchronized (this) {
                        this.started = false;
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes.dex */
    private class c {
        private String b;
        private File c;
        private BufferedWriter d;

        private c() {
            FilePrinter.this = r1;
        }

        boolean a() {
            return this.d != null && this.c.exists();
        }

        String b() {
            return this.b;
        }

        File c() {
            return this.c;
        }

        boolean a(String str) {
            this.b = str;
            this.c = new File(FilePrinter.a(FilePrinter.this), str);
            if (!this.c.exists()) {
                try {
                    File parentFile = this.c.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    this.c.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    d();
                    return false;
                }
            }
            try {
                this.d = new BufferedWriter(new FileWriter(this.c, true));
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                d();
                return false;
            }
        }

        boolean d() {
            BufferedWriter bufferedWriter = this.d;
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.d = null;
            this.b = null;
            this.c = null;
            return true;
        }

        void b(String str) {
            try {
                this.d.write(str);
                this.d.newLine();
                this.d.flush();
            } catch (IOException unused) {
            }
        }
    }

    /* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/FilePrinter$Writer.class */
    public class Writer {
        private String lastFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        private Writer() {
            FilePrinter.this = r4;
        }

        boolean isOpened() {
            return this.bufferedWriter != null && this.logFile.exists();
        }

        String getLastFileName() {
            return this.lastFileName;
        }

        File getFile() {
            return this.logFile;
        }

        boolean open(String newFileName) {
            this.lastFileName = newFileName;
            this.logFile = new File(FilePrinter.this.folderPath, newFileName);
            if (!this.logFile.exists()) {
                try {
                    File parent = this.logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    this.logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                    return false;
                }
            }
            try {
                this.bufferedWriter = new BufferedWriter(new FileWriter(this.logFile, true));
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                close();
                return false;
            }
        }

        boolean close() {
            if (this.bufferedWriter != null) {
                try {
                    this.bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.bufferedWriter = null;
            this.lastFileName = null;
            this.logFile = null;
            return true;
        }

        void appendLog(String flattenedLog) {
            try {
                this.bufferedWriter.write(flattenedLog);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            } catch (IOException e) {
            }
        }
    }
}
