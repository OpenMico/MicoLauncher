package org.eclipse.jetty.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Scanner extends AbstractLifeCycle {
    private static final Logger LOG = Log.getLogger(Scanner.class);
    private static int __scannerId = 0;
    private FilenameFilter _filter;
    private int _scanInterval;
    private TimerTask _task;
    private Timer _timer;
    private int _scanCount = 0;
    private final List<Listener> _listeners = new ArrayList();
    private final Map<String, TimeNSize> _prevScan = new HashMap();
    private final Map<String, TimeNSize> _currentScan = new HashMap();
    private final List<File> _scanDirs = new ArrayList();
    private volatile boolean _running = false;
    private boolean _reportExisting = true;
    private boolean _reportDirs = true;
    private int _scanDepth = 0;
    private final Map<String, Notification> _notifications = new HashMap();

    /* loaded from: classes5.dex */
    public interface BulkListener extends Listener {
        void filesChanged(List<String> list) throws Exception;
    }

    /* loaded from: classes5.dex */
    public interface DiscreteListener extends Listener {
        void fileAdded(String str) throws Exception;

        void fileChanged(String str) throws Exception;

        void fileRemoved(String str) throws Exception;
    }

    /* loaded from: classes5.dex */
    public interface Listener {
    }

    /* loaded from: classes5.dex */
    public enum Notification {
        ADDED,
        CHANGED,
        REMOVED
    }

    /* loaded from: classes5.dex */
    public interface ScanCycleListener extends Listener {
        void scanEnded(int i) throws Exception;

        void scanStarted(int i) throws Exception;
    }

    /* loaded from: classes5.dex */
    public interface ScanListener extends Listener {
        void scan();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class TimeNSize {
        final long _lastModified;
        final long _size;

        public TimeNSize(long j, long j2) {
            this._lastModified = j;
            this._size = j2;
        }

        public int hashCode() {
            return ((int) this._lastModified) ^ ((int) this._size);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TimeNSize)) {
                return false;
            }
            TimeNSize timeNSize = (TimeNSize) obj;
            return timeNSize._lastModified == this._lastModified && timeNSize._size == this._size;
        }

        public String toString() {
            return "[lm=" + this._lastModified + ",s=" + this._size + "]";
        }
    }

    public int getScanInterval() {
        return this._scanInterval;
    }

    public synchronized void setScanInterval(int i) {
        this._scanInterval = i;
        schedule();
    }

    @Deprecated
    public void setScanDir(File file) {
        this._scanDirs.clear();
        this._scanDirs.add(file);
    }

    @Deprecated
    public File getScanDir() {
        List<File> list = this._scanDirs;
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public void setScanDirs(List<File> list) {
        this._scanDirs.clear();
        this._scanDirs.addAll(list);
    }

    public synchronized void addScanDir(File file) {
        this._scanDirs.add(file);
    }

    public List<File> getScanDirs() {
        return Collections.unmodifiableList(this._scanDirs);
    }

    public void setRecursive(boolean z) {
        this._scanDepth = z ? -1 : 0;
    }

    public boolean getRecursive() {
        return this._scanDepth == -1;
    }

    public int getScanDepth() {
        return this._scanDepth;
    }

    public void setScanDepth(int i) {
        this._scanDepth = i;
    }

    public void setFilenameFilter(FilenameFilter filenameFilter) {
        this._filter = filenameFilter;
    }

    public FilenameFilter getFilenameFilter() {
        return this._filter;
    }

    public void setReportExistingFilesOnStartup(boolean z) {
        this._reportExisting = z;
    }

    public boolean getReportExistingFilesOnStartup() {
        return this._reportExisting;
    }

    public void setReportDirs(boolean z) {
        this._reportDirs = z;
    }

    public boolean getReportDirs() {
        return this._reportDirs;
    }

    public synchronized void addListener(Listener listener) {
        if (listener != null) {
            this._listeners.add(listener);
        }
    }

    public synchronized void removeListener(Listener listener) {
        if (listener != null) {
            this._listeners.remove(listener);
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStart() {
        if (!this._running) {
            this._running = true;
            if (this._reportExisting) {
                scan();
                scan();
            } else {
                scanFiles();
                this._prevScan.putAll(this._currentScan);
            }
            schedule();
        }
    }

    public TimerTask newTimerTask() {
        return new TimerTask() { // from class: org.eclipse.jetty.util.Scanner.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Scanner.this.scan();
            }
        };
    }

    public Timer newTimer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scanner-");
        int i = __scannerId;
        __scannerId = i + 1;
        sb.append(i);
        return new Timer(sb.toString(), true);
    }

    public void schedule() {
        if (this._running) {
            Timer timer = this._timer;
            if (timer != null) {
                timer.cancel();
            }
            TimerTask timerTask = this._task;
            if (timerTask != null) {
                timerTask.cancel();
            }
            if (getScanInterval() > 0) {
                this._timer = newTimer();
                this._task = newTimerTask();
                this._timer.schedule(this._task, getScanInterval() * 1010, 1010 * getScanInterval());
            }
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStop() {
        if (this._running) {
            this._running = false;
            if (this._timer != null) {
                this._timer.cancel();
            }
            if (this._task != null) {
                this._task.cancel();
            }
            this._task = null;
            this._timer = null;
        }
    }

    public synchronized void scan() {
        int i = this._scanCount + 1;
        this._scanCount = i;
        reportScanStart(i);
        scanFiles();
        reportDifferences(this._currentScan, this._prevScan);
        this._prevScan.clear();
        this._prevScan.putAll(this._currentScan);
        reportScanEnd(this._scanCount);
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof ScanListener) {
                    ((ScanListener) listener).scan();
                }
            } catch (Error e) {
                LOG.warn(e);
            } catch (Exception e2) {
                LOG.warn(e2);
            }
        }
    }

    public synchronized void scanFiles() {
        if (this._scanDirs != null) {
            this._currentScan.clear();
            for (File file : this._scanDirs) {
                if (file != null && file.exists()) {
                    try {
                        scanFile(file.getCanonicalFile(), this._currentScan, 0);
                    } catch (IOException e) {
                        LOG.warn("Error scanning files.", e);
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0134 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0135 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0139 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x013d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void reportDifferences(java.util.Map<java.lang.String, org.eclipse.jetty.util.Scanner.TimeNSize> r7, java.util.Map<java.lang.String, org.eclipse.jetty.util.Scanner.TimeNSize> r8) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.Scanner.reportDifferences(java.util.Map, java.util.Map):void");
    }

    private void scanFile(File file, Map<String, TimeNSize> map, int i) {
        try {
            if (file.exists()) {
                if ((file.isFile() || (i > 0 && this._reportDirs && file.isDirectory())) && (this._filter == null || (this._filter != null && this._filter.accept(file.getParentFile(), file.getName())))) {
                    map.put(file.getCanonicalPath(), new TimeNSize(file.lastModified(), file.length()));
                }
                if (!file.isDirectory()) {
                    return;
                }
                if (i < this._scanDepth || this._scanDepth == -1 || this._scanDirs.contains(file)) {
                    for (File file2 : file.listFiles()) {
                        scanFile(file2, map, i + 1);
                    }
                }
            }
        } catch (IOException e) {
            LOG.warn("Error scanning watched files", e);
        }
    }

    private void warn(Object obj, String str, Throwable th) {
        Logger logger = LOG;
        logger.warn(obj + " failed on '" + str, th);
    }

    private void reportAddition(String str) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof DiscreteListener) {
                    ((DiscreteListener) listener).fileAdded(str);
                }
            } catch (Error e) {
                warn(listener, str, e);
            } catch (Exception e2) {
                warn(listener, str, e2);
            }
        }
    }

    private void reportRemoval(String str) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof DiscreteListener) {
                    ((DiscreteListener) listener).fileRemoved(str);
                }
            } catch (Error e) {
                warn(listener, str, e);
            } catch (Exception e2) {
                warn(listener, str, e2);
            }
        }
    }

    private void reportChange(String str) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof DiscreteListener) {
                    ((DiscreteListener) listener).fileChanged(str);
                }
            } catch (Error e) {
                warn(listener, str, e);
            } catch (Exception e2) {
                warn(listener, str, e2);
            }
        }
    }

    private void reportBulkChanges(List<String> list) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof BulkListener) {
                    ((BulkListener) listener).filesChanged(list);
                }
            } catch (Error e) {
                warn(listener, list.toString(), e);
            } catch (Exception e2) {
                warn(listener, list.toString(), e2);
            }
        }
    }

    private void reportScanStart(int i) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof ScanCycleListener) {
                    ((ScanCycleListener) listener).scanStarted(i);
                }
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn(listener + " failed on scan start for cycle " + i, e);
            }
        }
    }

    private void reportScanEnd(int i) {
        for (Listener listener : this._listeners) {
            try {
                if (listener instanceof ScanCycleListener) {
                    ((ScanCycleListener) listener).scanEnded(i);
                }
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn(listener + " failed on scan end for cycle " + i, e);
            }
        }
    }
}
