package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class DownloadManager {
    public static final int DEFAULT_MAX_PARALLEL_DOWNLOADS = 3;
    public static final int DEFAULT_MIN_RETRY_COUNT = 5;
    public static final Requirements DEFAULT_REQUIREMENTS = new Requirements(1);
    private final Context a;
    private final WritableDownloadIndex b;
    private final Handler c;
    private final b d;
    private final RequirementsWatcher.Listener e;
    private final CopyOnWriteArraySet<Listener> f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private List<Download> o;
    private RequirementsWatcher p;

    /* loaded from: classes2.dex */
    public interface Listener {
        default void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc) {
        }

        default void onDownloadRemoved(DownloadManager downloadManager, Download download) {
        }

        default void onDownloadsPausedChanged(DownloadManager downloadManager, boolean z) {
        }

        default void onIdle(DownloadManager downloadManager) {
        }

        default void onInitialized(DownloadManager downloadManager) {
        }

        default void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i) {
        }

        default void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z) {
        }
    }

    @Deprecated
    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory) {
        this(context, databaseProvider, cache, factory, $$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE);
    }

    public DownloadManager(Context context, DatabaseProvider databaseProvider, Cache cache, DataSource.Factory factory, Executor executor) {
        this(context, new DefaultDownloadIndex(databaseProvider), new DefaultDownloaderFactory(new CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(factory), executor));
    }

    public DownloadManager(Context context, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory) {
        this.a = context.getApplicationContext();
        this.b = writableDownloadIndex;
        this.k = 3;
        this.l = 5;
        this.j = true;
        this.o = Collections.emptyList();
        this.f = new CopyOnWriteArraySet<>();
        Handler createHandlerForCurrentOrMainLooper = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadManager$dBaaSNzUK-4qeli8JgLWD08IJV4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = DownloadManager.this.a(message);
                return a2;
            }
        });
        this.c = createHandlerForCurrentOrMainLooper;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadManager");
        handlerThread.start();
        this.d = new b(handlerThread, writableDownloadIndex, downloaderFactory, createHandlerForCurrentOrMainLooper, this.k, this.l, this.j);
        RequirementsWatcher.Listener listener = new RequirementsWatcher.Listener() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadManager$wCJOi82BnZLprOrVV0PQOEs2o1E
            @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
            public final void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i) {
                DownloadManager.this.a(requirementsWatcher, i);
            }
        };
        this.e = listener;
        this.p = new RequirementsWatcher(context, listener, DEFAULT_REQUIREMENTS);
        this.m = this.p.start();
        this.g = 1;
        this.d.obtainMessage(0, this.m, 0).sendToTarget();
    }

    public Looper getApplicationLooper() {
        return this.c.getLooper();
    }

    public boolean isInitialized() {
        return this.i;
    }

    public boolean isIdle() {
        return this.h == 0 && this.g == 0;
    }

    public boolean isWaitingForRequirements() {
        return this.n;
    }

    public void addListener(Listener listener) {
        Assertions.checkNotNull(listener);
        this.f.add(listener);
    }

    public void removeListener(Listener listener) {
        this.f.remove(listener);
    }

    public Requirements getRequirements() {
        return this.p.getRequirements();
    }

    public int getNotMetRequirements() {
        return this.m;
    }

    public void setRequirements(Requirements requirements) {
        if (!requirements.equals(this.p.getRequirements())) {
            this.p.stop();
            this.p = new RequirementsWatcher(this.a, this.e, requirements);
            a(this.p, this.p.start());
        }
    }

    public int getMaxParallelDownloads() {
        return this.k;
    }

    public void setMaxParallelDownloads(int i) {
        Assertions.checkArgument(i > 0);
        if (this.k != i) {
            this.k = i;
            this.g++;
            this.d.obtainMessage(4, i, 0).sendToTarget();
        }
    }

    public int getMinRetryCount() {
        return this.l;
    }

    public void setMinRetryCount(int i) {
        Assertions.checkArgument(i >= 0);
        if (this.l != i) {
            this.l = i;
            this.g++;
            this.d.obtainMessage(5, i, 0).sendToTarget();
        }
    }

    public DownloadIndex getDownloadIndex() {
        return this.b;
    }

    public List<Download> getCurrentDownloads() {
        return this.o;
    }

    public boolean getDownloadsPaused() {
        return this.j;
    }

    public void resumeDownloads() {
        a(false);
    }

    public void pauseDownloads() {
        a(true);
    }

    public void setStopReason(@Nullable String str, int i) {
        this.g++;
        this.d.obtainMessage(3, i, 0, str).sendToTarget();
    }

    public void addDownload(DownloadRequest downloadRequest) {
        addDownload(downloadRequest, 0);
    }

    public void addDownload(DownloadRequest downloadRequest, int i) {
        this.g++;
        this.d.obtainMessage(6, i, 0, downloadRequest).sendToTarget();
    }

    public void removeDownload(String str) {
        this.g++;
        this.d.obtainMessage(7, str).sendToTarget();
    }

    public void removeAllDownloads() {
        this.g++;
        this.d.obtainMessage(8).sendToTarget();
    }

    public void release() {
        synchronized (this.d) {
            if (!this.d.a) {
                this.d.sendEmptyMessage(12);
                boolean z = false;
                while (!this.d.a) {
                    try {
                        this.d.wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
                this.c.removeCallbacksAndMessages(null);
                this.o = Collections.emptyList();
                this.g = 0;
                this.h = 0;
                this.i = false;
                this.m = 0;
                this.n = false;
            }
        }
    }

    private void a(boolean z) {
        if (this.j != z) {
            this.j = z;
            this.g++;
            this.d.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
            boolean a2 = a();
            Iterator<Listener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onDownloadsPausedChanged(this, z);
            }
            if (a2) {
                b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(RequirementsWatcher requirementsWatcher, int i) {
        Requirements requirements = requirementsWatcher.getRequirements();
        if (this.m != i) {
            this.m = i;
            this.g++;
            this.d.obtainMessage(2, i, 0).sendToTarget();
        }
        boolean a2 = a();
        Iterator<Listener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onRequirementsStateChanged(this, requirements, i);
        }
        if (a2) {
            b();
        }
    }

    private boolean a() {
        boolean z;
        boolean z2 = true;
        if (!this.j && this.m != 0) {
            for (int i = 0; i < this.o.size(); i++) {
                if (this.o.get(i).state == 0) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (this.n == z) {
            z2 = false;
        }
        this.n = z;
        return z2;
    }

    private void b() {
        Iterator<Listener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onWaitingForRequirementsChanged(this, this.n);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Message message) {
        switch (message.what) {
            case 0:
                a((List) message.obj);
                return true;
            case 1:
                a(message.arg1, message.arg2);
                return true;
            case 2:
                a((a) message.obj);
                return true;
            default:
                throw new IllegalStateException();
        }
    }

    private void a(List<Download> list) {
        this.i = true;
        this.o = Collections.unmodifiableList(list);
        boolean a2 = a();
        Iterator<Listener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onInitialized(this);
        }
        if (a2) {
            b();
        }
    }

    private void a(a aVar) {
        this.o = Collections.unmodifiableList(aVar.c);
        Download download = aVar.a;
        boolean a2 = a();
        if (aVar.b) {
            Iterator<Listener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onDownloadRemoved(this, download);
            }
        } else {
            Iterator<Listener> it2 = this.f.iterator();
            while (it2.hasNext()) {
                it2.next().onDownloadChanged(this, download, aVar.d);
            }
        }
        if (a2) {
            b();
        }
    }

    private void a(int i, int i2) {
        this.g -= i;
        this.h = i2;
        if (isIdle()) {
            Iterator<Listener> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().onIdle(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Download a(Download download, DownloadRequest downloadRequest, int i, long j) {
        int i2 = download.state;
        return new Download(download.request.copyWithMergedRequest(downloadRequest), (i2 == 5 || i2 == 7) ? 7 : i != 0 ? 1 : 0, (i2 == 5 || download.isTerminalState()) ? j : download.startTimeMs, j, -1L, i, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends Handler {
        public boolean a;
        private final HandlerThread b;
        private final WritableDownloadIndex c;
        private final DownloaderFactory d;
        private final Handler e;
        private final ArrayList<Download> f = new ArrayList<>();
        private final HashMap<String, c> g = new HashMap<>();
        private int h;
        private boolean i;
        private int j;
        private int k;
        private int l;

        public b(HandlerThread handlerThread, WritableDownloadIndex writableDownloadIndex, DownloaderFactory downloaderFactory, Handler handler, int i, int i2, boolean z) {
            super(handlerThread.getLooper());
            this.b = handlerThread;
            this.c = writableDownloadIndex;
            this.d = downloaderFactory;
            this.e = handler;
            this.j = i;
            this.k = i2;
            this.i = z;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z = false;
            int i = 0;
            switch (message.what) {
                case 0:
                    a(message.arg1);
                    i = 1;
                    break;
                case 1:
                    if (message.arg1 != 0) {
                        z = true;
                    }
                    a(z);
                    i = 1;
                    break;
                case 2:
                    b(message.arg1);
                    i = 1;
                    break;
                case 3:
                    a((String) message.obj, message.arg1);
                    i = 1;
                    break;
                case 4:
                    c(message.arg1);
                    i = 1;
                    break;
                case 5:
                    d(message.arg1);
                    i = 1;
                    break;
                case 6:
                    a((DownloadRequest) message.obj, message.arg1);
                    i = 1;
                    break;
                case 7:
                    a((String) message.obj);
                    i = 1;
                    break;
                case 8:
                    a();
                    i = 1;
                    break;
                case 9:
                    b((c) message.obj);
                    break;
                case 10:
                    a((c) message.obj, Util.toLong(message.arg1, message.arg2));
                    return;
                case 11:
                    d();
                    return;
                case 12:
                    b();
                    return;
                default:
                    throw new IllegalStateException();
            }
            this.e.obtainMessage(1, i, this.g.size()).sendToTarget();
        }

        /* JADX WARN: Finally extract failed */
        private void a(int i) {
            DownloadCursor downloadCursor;
            try {
                this.h = i;
                downloadCursor = null;
                try {
                    this.c.setDownloadingStatesToQueued();
                    downloadCursor = this.c.getDownloads(0, 1, 2, 5, 7);
                    while (downloadCursor.moveToNext()) {
                        this.f.add(downloadCursor.getDownload());
                    }
                } catch (IOException e) {
                    Log.e("DownloadManager", "Failed to load index.", e);
                    this.f.clear();
                }
                Util.closeQuietly(downloadCursor);
                this.e.obtainMessage(0, new ArrayList(this.f)).sendToTarget();
                c();
            } catch (Throwable th) {
                Util.closeQuietly(downloadCursor);
                throw th;
            }
        }

        private void a(boolean z) {
            this.i = z;
            c();
        }

        private void b(int i) {
            this.h = i;
            c();
        }

        private void a(@Nullable String str, int i) {
            if (str == null) {
                for (int i2 = 0; i2 < this.f.size(); i2++) {
                    a(this.f.get(i2), i);
                }
                try {
                    this.c.setStopReason(i);
                } catch (IOException e) {
                    Log.e("DownloadManager", "Failed to set manual stop reason", e);
                }
            } else {
                Download a = a(str, false);
                if (a != null) {
                    a(a, i);
                } else {
                    try {
                        this.c.setStopReason(str, i);
                    } catch (IOException e2) {
                        String valueOf = String.valueOf(str);
                        Log.e("DownloadManager", valueOf.length() != 0 ? "Failed to set manual stop reason: ".concat(valueOf) : new String("Failed to set manual stop reason: "), e2);
                    }
                }
            }
            c();
        }

        private void a(Download download, int i) {
            if (i == 0) {
                if (download.state == 1) {
                    a(download, 0, 0);
                }
            } else if (i != download.stopReason) {
                int i2 = download.state;
                if (i2 == 0 || i2 == 2) {
                    i2 = 1;
                }
                b(new Download(download.request, i2, download.startTimeMs, System.currentTimeMillis(), download.contentLength, i, 0, download.a));
            }
        }

        private void c(int i) {
            this.j = i;
            c();
        }

        private void d(int i) {
            this.k = i;
        }

        private void a(DownloadRequest downloadRequest, int i) {
            int i2 = 1;
            Download a = a(downloadRequest.id, true);
            long currentTimeMillis = System.currentTimeMillis();
            if (a != null) {
                b(DownloadManager.a(a, downloadRequest, i, currentTimeMillis));
            } else {
                if (i == 0) {
                    i2 = 0;
                }
                b(new Download(downloadRequest, i2, currentTimeMillis, currentTimeMillis, -1L, i, 0));
            }
            c();
        }

        private void a(String str) {
            Download a = a(str, true);
            if (a == null) {
                String valueOf = String.valueOf(str);
                Log.e("DownloadManager", valueOf.length() != 0 ? "Failed to remove nonexistent download: ".concat(valueOf) : new String("Failed to remove nonexistent download: "));
                return;
            }
            a(a, 5, 0);
            c();
        }

        private void a() {
            ArrayList arrayList = new ArrayList();
            try {
                DownloadCursor downloads = this.c.getDownloads(3, 4);
                while (downloads.moveToNext()) {
                    arrayList.add(downloads.getDownload());
                }
                if (downloads != null) {
                    downloads.close();
                }
            } catch (IOException unused) {
                Log.e("DownloadManager", "Failed to load downloads.");
            }
            for (int i = 0; i < this.f.size(); i++) {
                ArrayList<Download> arrayList2 = this.f;
                arrayList2.set(i, b(arrayList2.get(i), 5, 0));
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.f.add(b((Download) arrayList.get(i2), 5, 0));
            }
            Collections.sort(this.f, $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg.INSTANCE);
            try {
                this.c.setStatesToRemoving();
            } catch (IOException e) {
                Log.e("DownloadManager", "Failed to update index.", e);
            }
            ArrayList arrayList3 = new ArrayList(this.f);
            for (int i3 = 0; i3 < this.f.size(); i3++) {
                this.e.obtainMessage(2, new a(this.f.get(i3), false, arrayList3, null)).sendToTarget();
            }
            c();
        }

        private void b() {
            for (c cVar : this.g.values()) {
                cVar.a(true);
            }
            try {
                this.c.setDownloadingStatesToQueued();
            } catch (IOException e) {
                Log.e("DownloadManager", "Failed to update index.", e);
            }
            this.f.clear();
            this.b.quit();
            synchronized (this) {
                this.a = true;
                notifyAll();
            }
        }

        private void c() {
            int i = 0;
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Download download = this.f.get(i2);
                c cVar = this.g.get(download.request.id);
                int i3 = download.state;
                if (i3 == 5 || i3 == 7) {
                    b(cVar, download);
                } else {
                    switch (i3) {
                        case 0:
                            cVar = a(cVar, download);
                            break;
                        case 1:
                            a(cVar);
                            break;
                        case 2:
                            Assertions.checkNotNull(cVar);
                            a(cVar, download, i);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
                if (cVar != null && !cVar.d) {
                    i++;
                }
            }
        }

        private void a(@Nullable c cVar) {
            if (cVar != null) {
                Assertions.checkState(!cVar.d);
                cVar.a(false);
            }
        }

        @Nullable
        @CheckResult
        private c a(@Nullable c cVar, Download download) {
            if (cVar != null) {
                Assertions.checkState(!cVar.d);
                cVar.a(false);
                return cVar;
            } else if (!e() || this.l >= this.j) {
                return null;
            } else {
                Download a = a(download, 2, 0);
                c cVar2 = new c(a.request, this.d.createDownloader(a.request), a.a, false, this.k, this);
                this.g.put(a.request.id, cVar2);
                int i = this.l;
                this.l = i + 1;
                if (i == 0) {
                    sendEmptyMessageDelayed(11, 5000L);
                }
                cVar2.start();
                return cVar2;
            }
        }

        private void a(c cVar, Download download, int i) {
            Assertions.checkState(!cVar.d);
            if (!e() || i >= this.j) {
                a(download, 0, 0);
                cVar.a(false);
            }
        }

        private void b(@Nullable c cVar, Download download) {
            if (cVar == null) {
                c cVar2 = new c(download.request, this.d.createDownloader(download.request), download.a, true, this.k, this);
                this.g.put(download.request.id, cVar2);
                cVar2.start();
            } else if (!cVar.d) {
                cVar.a(false);
            }
        }

        private void a(c cVar, long j) {
            Download download = (Download) Assertions.checkNotNull(a(cVar.a.id, false));
            if (j != download.contentLength && j != -1) {
                b(new Download(download.request, download.state, download.startTimeMs, System.currentTimeMillis(), j, download.stopReason, download.failureReason, download.a));
            }
        }

        private void b(c cVar) {
            String str = cVar.a.id;
            this.g.remove(str);
            boolean z = cVar.d;
            if (!z) {
                int i = this.l - 1;
                this.l = i;
                if (i == 0) {
                    removeMessages(11);
                }
            }
            if (cVar.g) {
                c();
                return;
            }
            Exception exc = cVar.h;
            if (exc != null) {
                String valueOf = String.valueOf(cVar.a);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                sb.append("Task failed: ");
                sb.append(valueOf);
                sb.append(", ");
                sb.append(z);
                Log.e("DownloadManager", sb.toString(), exc);
            }
            Download download = (Download) Assertions.checkNotNull(a(str, false));
            int i2 = download.state;
            if (i2 == 2) {
                Assertions.checkState(!z);
                a(download, exc);
            } else if (i2 == 5 || i2 == 7) {
                Assertions.checkState(z);
                a(download);
            } else {
                throw new IllegalStateException();
            }
            c();
        }

        private void a(Download download, @Nullable Exception exc) {
            Download download2 = new Download(download.request, exc == null ? 3 : 4, download.startTimeMs, System.currentTimeMillis(), download.contentLength, download.stopReason, exc == null ? 0 : 1, download.a);
            this.f.remove(b(download2.request.id));
            try {
                this.c.putDownload(download2);
            } catch (IOException e) {
                Log.e("DownloadManager", "Failed to update index.", e);
            }
            this.e.obtainMessage(2, new a(download2, false, new ArrayList(this.f), exc)).sendToTarget();
        }

        private void a(Download download) {
            int i = 1;
            if (download.state == 7) {
                if (download.stopReason == 0) {
                    i = 0;
                }
                a(download, i, download.stopReason);
                c();
                return;
            }
            this.f.remove(b(download.request.id));
            try {
                this.c.removeDownload(download.request.id);
            } catch (IOException unused) {
                Log.e("DownloadManager", "Failed to remove from database");
            }
            this.e.obtainMessage(2, new a(download, true, new ArrayList(this.f), null)).sendToTarget();
        }

        private void d() {
            for (int i = 0; i < this.f.size(); i++) {
                Download download = this.f.get(i);
                if (download.state == 2) {
                    try {
                        this.c.putDownload(download);
                    } catch (IOException e) {
                        Log.e("DownloadManager", "Failed to update index.", e);
                    }
                }
            }
            sendEmptyMessageDelayed(11, 5000L);
        }

        private boolean e() {
            return !this.i && this.h == 0;
        }

        private Download a(Download download, int i, int i2) {
            Assertions.checkState((i == 3 || i == 4) ? false : true);
            return b(b(download, i, i2));
        }

        private Download b(Download download) {
            boolean z = true;
            Assertions.checkState((download.state == 3 || download.state == 4) ? false : true);
            int b = b(download.request.id);
            if (b == -1) {
                this.f.add(download);
                Collections.sort(this.f, $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg.INSTANCE);
            } else {
                if (download.startTimeMs == this.f.get(b).startTimeMs) {
                    z = false;
                }
                this.f.set(b, download);
                if (z) {
                    Collections.sort(this.f, $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg.INSTANCE);
                }
            }
            try {
                this.c.putDownload(download);
            } catch (IOException e) {
                Log.e("DownloadManager", "Failed to update index.", e);
            }
            this.e.obtainMessage(2, new a(download, false, new ArrayList(this.f), null)).sendToTarget();
            return download;
        }

        @Nullable
        private Download a(String str, boolean z) {
            int b = b(str);
            if (b != -1) {
                return this.f.get(b);
            }
            if (!z) {
                return null;
            }
            try {
                return this.c.getDownload(str);
            } catch (IOException e) {
                String valueOf = String.valueOf(str);
                Log.e("DownloadManager", valueOf.length() != 0 ? "Failed to load download: ".concat(valueOf) : new String("Failed to load download: "), e);
                return null;
            }
        }

        private int b(String str) {
            for (int i = 0; i < this.f.size(); i++) {
                if (this.f.get(i).request.id.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        private static Download b(Download download, int i, int i2) {
            return new Download(download.request, i, download.startTimeMs, System.currentTimeMillis(), download.contentLength, i2, 0, download.a);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int a(Download download, Download download2) {
            return Util.compareLong(download.startTimeMs, download2.startTimeMs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class c extends Thread implements Downloader.ProgressListener {
        private final DownloadRequest a;
        private final Downloader b;
        private final DownloadProgress c;
        private final boolean d;
        private final int e;
        @Nullable
        private volatile b f;
        private volatile boolean g;
        @Nullable
        private Exception h;
        private long i;

        private c(DownloadRequest downloadRequest, Downloader downloader, DownloadProgress downloadProgress, boolean z, int i, b bVar) {
            this.a = downloadRequest;
            this.b = downloader;
            this.c = downloadProgress;
            this.d = z;
            this.e = i;
            this.f = bVar;
            this.i = -1L;
        }

        public void a(boolean z) {
            if (z) {
                this.f = null;
            }
            if (!this.g) {
                this.g = true;
                this.b.cancel();
                interrupt();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.d) {
                    this.b.remove();
                } else {
                    long j = -1;
                    int i = 0;
                    while (!this.g) {
                        try {
                            this.b.download(this);
                            break;
                        } catch (IOException e) {
                            if (!this.g) {
                                long j2 = this.c.bytesDownloaded;
                                if (j2 != j) {
                                    i = 0;
                                    j = j2;
                                }
                                i++;
                                if (i <= this.e) {
                                    Thread.sleep(a(i));
                                } else {
                                    throw e;
                                }
                            }
                        }
                    }
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception e2) {
                this.h = e2;
            }
            b bVar = this.f;
            if (bVar != null) {
                bVar.obtainMessage(9, this).sendToTarget();
            }
        }

        @Override // com.google.android.exoplayer2.offline.Downloader.ProgressListener
        public void onProgress(long j, long j2, float f) {
            DownloadProgress downloadProgress = this.c;
            downloadProgress.bytesDownloaded = j2;
            downloadProgress.percentDownloaded = f;
            if (j != this.i) {
                this.i = j;
                b bVar = this.f;
                if (bVar != null) {
                    bVar.obtainMessage(10, (int) (j >> 32), (int) j, this).sendToTarget();
                }
            }
        }

        private static int a(int i) {
            return Math.min((i - 1) * 1000, 5000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        public final Download a;
        public final boolean b;
        public final List<Download> c;
        @Nullable
        public final Exception d;

        public a(Download download, boolean z, List<Download> list, @Nullable Exception exc) {
            this.a = download;
            this.b = z;
            this.c = list;
            this.d = exc;
        }
    }
}
