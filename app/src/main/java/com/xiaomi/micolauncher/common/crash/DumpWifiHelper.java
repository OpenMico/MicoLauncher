package com.xiaomi.micolauncher.common.crash;

import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.utils.FileUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.crash.DumpWifiHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes3.dex */
public class DumpWifiHelper {
    private static final int MAX_WIFI_LOG_FILES = 5;
    private final DumpWifiWork dumpWifiWork;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Holder {
        private static final DumpWifiHelper INSTANCE = new DumpWifiHelper();

        private Holder() {
        }
    }

    public static DumpWifiHelper getInstance() {
        return Holder.INSTANCE;
    }

    private DumpWifiHelper() {
        this.dumpWifiWork = new DumpWifiWork();
    }

    public File[] getWifiDumpFiles() {
        return this.dumpWifiWork.getWifiLogFiles();
    }

    public void dumpOnce() {
        ExecutorService ioThreadPool = Threads.getIoThreadPool();
        final DumpWifiWork dumpWifiWork = this.dumpWifiWork;
        dumpWifiWork.getClass();
        ioThreadPool.submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.crash.-$$Lambda$_EuCdipHzZ7maj01Kt2c0JcFG5Q
            @Override // java.lang.Runnable
            public final void run() {
                DumpWifiHelper.DumpWifiWork.this.dump();
            }
        });
    }

    public String getLogDirectory() {
        return this.dumpWifiWork.getParentDir();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class DumpWifiWork {
        static final String DUMPSYS_WIFI = "dumpsys wifi";
        @GuardedBy("readLock, writeLock")
        private boolean isDumping;
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
        private ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();

        DumpWifiWork() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void dump() {
            if (this.writeLock.tryLock()) {
                try {
                    dumpLocked();
                } finally {
                    this.writeLock.unlock();
                }
            }
        }

        boolean isDumping() {
            if (!this.readLock.tryLock()) {
                return true;
            }
            boolean z = this.isDumping;
            this.readLock.unlock();
            return z;
        }

        void dumpLocked() {
            if (!this.isDumping) {
                this.isDumping = true;
                deleteOldLogsIfNeedLocked();
                File fileNameLocked = getFileNameLocked();
                if (fileNameLocked == null) {
                    this.isDumping = false;
                    return;
                }
                File fetchResultFile = DumpCmd.fetchResultFile(DUMPSYS_WIFI, fileNameLocked);
                this.isDumping = false;
                if (fetchResultFile == null) {
                    L.wlan.e("failed to dump wifi log");
                }
            }
        }

        File[] getWifiLogFiles() {
            if (!this.readLock.tryLock()) {
                return null;
            }
            try {
                return getWifiLogFilesLocked();
            } finally {
                this.readLock.unlock();
            }
        }

        File[] getWifiLogFilesLocked() {
            String parentDir = getParentDir();
            if (!FileUtils.existsDirectory(parentDir)) {
                return null;
            }
            return new File(parentDir).listFiles($$Lambda$DumpWifiHelper$DumpWifiWork$fl7ontjjVSAqbgQSb1BXJhOIgio.INSTANCE);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ boolean lambda$getWifiLogFilesLocked$0(File file, String str) {
            return str != null && TextUtils.isDigitsOnly(str);
        }

        private void deleteOldLogsIfNeedLocked() {
            int length;
            File[] wifiLogFiles = getWifiLogFiles();
            if (!ContainerUtil.isEmpty(wifiLogFiles) && wifiLogFiles.length - 4 > 0) {
                ArrayList arrayList = ContainerUtil.toArrayList(wifiLogFiles);
                Collections.sort(arrayList, $$Lambda$DumpWifiHelper$DumpWifiWork$34MZQcBihB_NWPf0QF9Ucoo0I.INSTANCE);
                for (int i = 0; i < length; i++) {
                    File file = (File) arrayList.get(i);
                    if (!file.delete()) {
                        L.wlan.e("failed to delete file %s", file);
                    }
                }
            }
        }

        private File getFileNameLocked() {
            String parentDir = getParentDir();
            if (!FileUtils.createDir(parentDir)) {
                return null;
            }
            return new File(parentDir, String.valueOf(System.currentTimeMillis()));
        }

        @NonNull
        String getParentDir() {
            return FileDirectoryManager.getWifiLogDir().getPath();
        }
    }
}
