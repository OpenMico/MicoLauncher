package org.conscrypt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;

/* loaded from: classes5.dex */
public final class FileClientSessionCache {
    public static final int MAX_SIZE = 12;
    private static final Logger logger = Logger.getLogger(FileClientSessionCache.class.getName());
    static final Map<File, Impl> caches = new HashMap();

    private FileClientSessionCache() {
    }

    /* loaded from: classes5.dex */
    static class Impl implements SSLClientSessionCache {
        Map<String, File> accessOrder = newAccessOrder();
        final File directory;
        String[] initialFiles;
        int size;

        Impl(File file) throws IOException {
            boolean exists = file.exists();
            if (!exists || file.isDirectory()) {
                if (exists) {
                    this.initialFiles = file.list();
                    String[] strArr = this.initialFiles;
                    if (strArr != null) {
                        Arrays.sort(strArr);
                        this.size = this.initialFiles.length;
                    } else {
                        throw new IOException(file + " exists but cannot list contents.");
                    }
                } else if (file.mkdirs()) {
                    this.size = 0;
                } else {
                    throw new IOException("Creation of " + file + " directory failed.");
                }
                this.directory = file;
                return;
            }
            throw new IOException(file + " exists but is not a directory.");
        }

        private static Map<String, File> newAccessOrder() {
            return new LinkedHashMap(12, 0.75f, true);
        }

        private static String fileName(String str, int i) {
            if (str != null) {
                return str + "." + i;
            }
            throw new NullPointerException("host == null");
        }

        @Override // org.conscrypt.SSLClientSessionCache
        public synchronized byte[] getSessionData(String str, int i) {
            String fileName = fileName(str, i);
            File file = this.accessOrder.get(fileName);
            if (file == null) {
                if (this.initialFiles == null) {
                    return null;
                }
                if (Arrays.binarySearch(this.initialFiles, fileName) < 0) {
                    return null;
                }
                file = new File(this.directory, fileName);
                this.accessOrder.put(fileName, file);
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[(int) file.length()];
                    new DataInputStream(fileInputStream).readFully(bArr);
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                    }
                    return bArr;
                } catch (IOException e) {
                    logReadError(str, file, e);
                    try {
                        fileInputStream.close();
                    } catch (Exception unused2) {
                    }
                    return null;
                }
            } catch (FileNotFoundException e2) {
                logReadError(str, file, e2);
                return null;
            }
        }

        static void logReadError(String str, File file, Throwable th) {
            Logger logger = FileClientSessionCache.logger;
            Level level = Level.WARNING;
            logger.log(level, "FileClientSessionCache: Error reading session data for " + str + " from " + file + ".", th);
        }

        @Override // org.conscrypt.SSLClientSessionCache
        public synchronized void putSessionData(SSLSession sSLSession, byte[] bArr) {
            String peerHost = sSLSession.getPeerHost();
            if (bArr != null) {
                String fileName = fileName(peerHost, sSLSession.getPeerPort());
                File file = new File(this.directory, fileName);
                boolean exists = file.exists();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    if (!exists) {
                        this.size++;
                        makeRoom();
                    }
                    try {
                        fileOutputStream.write(bArr);
                        try {
                            fileOutputStream.close();
                            this.accessOrder.put(fileName, file);
                        } catch (IOException e) {
                            logWriteError(peerHost, file, e);
                            delete(file);
                        }
                    } catch (IOException e2) {
                        logWriteError(peerHost, file, e2);
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            logWriteError(peerHost, file, e3);
                        }
                    }
                } catch (FileNotFoundException e4) {
                    logWriteError(peerHost, file, e4);
                }
            } else {
                throw new NullPointerException("sessionData == null");
            }
        }

        private void makeRoom() {
            if (this.size > 12) {
                indexFiles();
                int i = this.size - 12;
                Iterator<File> it = this.accessOrder.values().iterator();
                do {
                    delete(it.next());
                    it.remove();
                    i--;
                } while (i > 0);
            }
        }

        private void indexFiles() {
            String[] strArr = this.initialFiles;
            if (strArr != null) {
                this.initialFiles = null;
                TreeSet<CacheFile> treeSet = new TreeSet();
                for (String str : strArr) {
                    if (!this.accessOrder.containsKey(str)) {
                        treeSet.add(new CacheFile(this.directory, str));
                    }
                }
                if (!treeSet.isEmpty()) {
                    Map<String, File> newAccessOrder = newAccessOrder();
                    for (CacheFile cacheFile : treeSet) {
                        newAccessOrder.put(cacheFile.name, cacheFile);
                    }
                    newAccessOrder.putAll(this.accessOrder);
                    this.accessOrder = newAccessOrder;
                }
            }
        }

        private void delete(File file) {
            if (!file.delete()) {
                IOException iOException = new IOException("FileClientSessionCache: Failed to delete " + file + ".");
                FileClientSessionCache.logger.log(Level.WARNING, iOException.getMessage(), (Throwable) iOException);
            }
            this.size--;
        }

        static void logWriteError(String str, File file, Throwable th) {
            Logger logger = FileClientSessionCache.logger;
            Level level = Level.WARNING;
            logger.log(level, "FileClientSessionCache: Error writing session data for " + str + " to " + file + ".", th);
        }
    }

    public static synchronized SSLClientSessionCache usingDirectory(File file) throws IOException {
        Impl impl;
        synchronized (FileClientSessionCache.class) {
            impl = caches.get(file);
            if (impl == null) {
                impl = new Impl(file);
                caches.put(file, impl);
            }
        }
        return impl;
    }

    static synchronized void reset() {
        synchronized (FileClientSessionCache.class) {
            caches.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class CacheFile extends File {
        long lastModified = -1;
        final String name;

        CacheFile(File file, String str) {
            super(file, str);
            this.name = str;
        }

        @Override // java.io.File
        public long lastModified() {
            long j = this.lastModified;
            if (j != -1) {
                return j;
            }
            long lastModified = super.lastModified();
            this.lastModified = lastModified;
            return lastModified;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.io.File
        public int compareTo(File file) {
            int i = ((lastModified() - file.lastModified()) > 0L ? 1 : ((lastModified() - file.lastModified()) == 0L ? 0 : -1));
            if (i == 0) {
                return super.compareTo(file);
            }
            return i < 0 ? -1 : 1;
        }
    }
}
