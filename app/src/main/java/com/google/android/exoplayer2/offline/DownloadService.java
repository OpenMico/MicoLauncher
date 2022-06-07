package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class DownloadService extends Service {
    public static final String ACTION_ADD_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    public static final String ACTION_PAUSE_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS";
    public static final String ACTION_REMOVE_ALL_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.REMOVE_ALL_DOWNLOADS";
    public static final String ACTION_REMOVE_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD";
    public static final String ACTION_RESUME_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS";
    public static final String ACTION_SET_REQUIREMENTS = "com.google.android.exoplayer.downloadService.action.SET_REQUIREMENTS";
    public static final String ACTION_SET_STOP_REASON = "com.google.android.exoplayer.downloadService.action.SET_STOP_REASON";
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    public static final int FOREGROUND_NOTIFICATION_ID_NONE = 0;
    public static final String KEY_CONTENT_ID = "content_id";
    public static final String KEY_DOWNLOAD_REQUEST = "download_request";
    public static final String KEY_FOREGROUND = "foreground";
    public static final String KEY_REQUIREMENTS = "requirements";
    public static final String KEY_STOP_REASON = "stop_reason";
    private static final HashMap<Class<? extends DownloadService>, a> a = new HashMap<>();
    @Nullable
    private final b b;
    @Nullable
    private final String c;
    @StringRes
    private final int d;
    @StringRes
    private final int e;
    private DownloadManager f;
    private int g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(int i) {
        return i == 2 || i == 5 || i == 7;
    }

    protected abstract DownloadManager getDownloadManager();

    protected abstract Notification getForegroundNotification(List<Download> list);

    @Nullable
    protected abstract Scheduler getScheduler();

    @Deprecated
    protected void onDownloadChanged(Download download) {
    }

    @Deprecated
    protected void onDownloadRemoved(Download download) {
    }

    protected DownloadService(int i) {
        this(i, 1000L);
    }

    protected DownloadService(int i, long j) {
        this(i, j, null, 0, 0);
    }

    @Deprecated
    protected DownloadService(int i, long j, @Nullable String str, @StringRes int i2) {
        this(i, j, str, i2, 0);
    }

    protected DownloadService(int i, long j, @Nullable String str, @StringRes int i2, @StringRes int i3) {
        if (i == 0) {
            this.b = null;
            this.c = null;
            this.d = 0;
            this.e = 0;
            return;
        }
        this.b = new b(i, j);
        this.c = str;
        this.d = i2;
        this.e = i3;
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z) {
        return buildAddDownloadIntent(context, cls, downloadRequest, 0, z);
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i, boolean z) {
        return a(context, cls, ACTION_ADD_DOWNLOAD, z).putExtra(KEY_DOWNLOAD_REQUEST, downloadRequest).putExtra(KEY_STOP_REASON, i);
    }

    public static Intent buildRemoveDownloadIntent(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        return a(context, cls, ACTION_REMOVE_DOWNLOAD, z).putExtra(KEY_CONTENT_ID, str);
    }

    public static Intent buildRemoveAllDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return a(context, cls, ACTION_REMOVE_ALL_DOWNLOADS, z);
    }

    public static Intent buildResumeDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return a(context, cls, ACTION_RESUME_DOWNLOADS, z);
    }

    public static Intent buildPauseDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z) {
        return a(context, cls, ACTION_PAUSE_DOWNLOADS, z);
    }

    public static Intent buildSetStopReasonIntent(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i, boolean z) {
        return a(context, cls, ACTION_SET_STOP_REASON, z).putExtra(KEY_CONTENT_ID, str).putExtra(KEY_STOP_REASON, i);
    }

    public static Intent buildSetRequirementsIntent(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z) {
        return a(context, cls, ACTION_SET_REQUIREMENTS, z).putExtra(KEY_REQUIREMENTS, requirements);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z) {
        a(context, buildAddDownloadIntent(context, cls, downloadRequest, z), z);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i, boolean z) {
        a(context, buildAddDownloadIntent(context, cls, downloadRequest, i, z), z);
    }

    public static void sendRemoveDownload(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        a(context, buildRemoveDownloadIntent(context, cls, str, z), z);
    }

    public static void sendRemoveAllDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        a(context, buildRemoveAllDownloadsIntent(context, cls, z), z);
    }

    public static void sendResumeDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        a(context, buildResumeDownloadsIntent(context, cls, z), z);
    }

    public static void sendPauseDownloads(Context context, Class<? extends DownloadService> cls, boolean z) {
        a(context, buildPauseDownloadsIntent(context, cls, z), z);
    }

    public static void sendSetStopReason(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i, boolean z) {
        a(context, buildSetStopReasonIntent(context, cls, str, i, z), z);
    }

    public static void sendSetRequirements(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z) {
        a(context, buildSetRequirementsIntent(context, cls, requirements, z), z);
    }

    public static void start(Context context, Class<? extends DownloadService> cls) {
        context.startService(b(context, cls, ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> cls) {
        Util.startForegroundService(context, a(context, cls, ACTION_INIT, true));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Service
    public void onCreate() {
        String str = this.c;
        if (str != null) {
            NotificationUtil.createNotificationChannel(this, str, this.d, this.e, 2);
        }
        Class<?> cls = getClass();
        a aVar = a.get(cls);
        if (aVar == null) {
            boolean z = this.b != null;
            Scheduler scheduler = z ? getScheduler() : null;
            this.f = getDownloadManager();
            this.f.resumeDownloads();
            aVar = new a(getApplicationContext(), this.f, z, scheduler, cls);
            a.put(cls, aVar);
        } else {
            this.f = aVar.b;
        }
        aVar.a(this);
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i, int i2) {
        String str;
        b bVar;
        this.g = i2;
        this.i = false;
        String str2 = null;
        if (intent != null) {
            str2 = intent.getAction();
            str = intent.getStringExtra(KEY_CONTENT_ID);
            this.h |= intent.getBooleanExtra("foreground", false) || "com.google.android.exoplayer.downloadService.action.RESTART".equals(str2);
        } else {
            str = null;
        }
        if (str2 == null) {
            str2 = ACTION_INIT;
        }
        DownloadManager downloadManager = (DownloadManager) Assertions.checkNotNull(this.f);
        char c = 65535;
        switch (str2.hashCode()) {
            case -1931239035:
                if (str2.equals(ACTION_ADD_DOWNLOAD)) {
                    c = 2;
                    break;
                }
                break;
            case -932047176:
                if (str2.equals(ACTION_RESUME_DOWNLOADS)) {
                    c = 5;
                    break;
                }
                break;
            case -871181424:
                if (str2.equals("com.google.android.exoplayer.downloadService.action.RESTART")) {
                    c = 1;
                    break;
                }
                break;
            case -650547439:
                if (str2.equals(ACTION_REMOVE_ALL_DOWNLOADS)) {
                    c = 4;
                    break;
                }
                break;
            case -119057172:
                if (str2.equals(ACTION_SET_REQUIREMENTS)) {
                    c = '\b';
                    break;
                }
                break;
            case 191112771:
                if (str2.equals(ACTION_PAUSE_DOWNLOADS)) {
                    c = 6;
                    break;
                }
                break;
            case 671523141:
                if (str2.equals(ACTION_SET_STOP_REASON)) {
                    c = 7;
                    break;
                }
                break;
            case 1015676687:
                if (str2.equals(ACTION_INIT)) {
                    c = 0;
                    break;
                }
                break;
            case 1547520644:
                if (str2.equals(ACTION_REMOVE_DOWNLOAD)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
                break;
            case 2:
                DownloadRequest downloadRequest = (DownloadRequest) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra(KEY_DOWNLOAD_REQUEST);
                if (downloadRequest != null) {
                    downloadManager.addDownload(downloadRequest, intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                } else {
                    Log.e("DownloadService", "Ignored ADD_DOWNLOAD: Missing download_request extra");
                    break;
                }
            case 3:
                if (str != null) {
                    downloadManager.removeDownload(str);
                    break;
                } else {
                    Log.e("DownloadService", "Ignored REMOVE_DOWNLOAD: Missing content_id extra");
                    break;
                }
            case 4:
                downloadManager.removeAllDownloads();
                break;
            case 5:
                downloadManager.resumeDownloads();
                break;
            case 6:
                downloadManager.pauseDownloads();
                break;
            case 7:
                if (((Intent) Assertions.checkNotNull(intent)).hasExtra(KEY_STOP_REASON)) {
                    downloadManager.setStopReason(str, intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                } else {
                    Log.e("DownloadService", "Ignored SET_STOP_REASON: Missing stop_reason extra");
                    break;
                }
            case '\b':
                Requirements requirements = (Requirements) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra(KEY_REQUIREMENTS);
                if (requirements != null) {
                    Scheduler scheduler = getScheduler();
                    if (scheduler != null) {
                        Requirements supportedRequirements = scheduler.getSupportedRequirements(requirements);
                        if (!supportedRequirements.equals(requirements)) {
                            StringBuilder sb = new StringBuilder(65);
                            sb.append("Ignoring requirements not supported by the Scheduler: ");
                            sb.append(requirements.getRequirements() ^ supportedRequirements.getRequirements());
                            Log.w("DownloadService", sb.toString());
                            requirements = supportedRequirements;
                        }
                    }
                    downloadManager.setRequirements(requirements);
                    break;
                } else {
                    Log.e("DownloadService", "Ignored SET_REQUIREMENTS: Missing requirements extra");
                    break;
                }
            default:
                String valueOf = String.valueOf(str2);
                Log.e("DownloadService", valueOf.length() != 0 ? "Ignored unrecognized action: ".concat(valueOf) : new String("Ignored unrecognized action: "));
                break;
        }
        if (Util.SDK_INT >= 26 && this.h && (bVar = this.b) != null) {
            bVar.c();
        }
        this.j = false;
        if (downloadManager.isIdle()) {
            b();
        }
        return 1;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        this.i = true;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.k = true;
        ((a) Assertions.checkNotNull(a.get(getClass()))).b(this);
        b bVar = this.b;
        if (bVar != null) {
            bVar.b();
        }
    }

    @Override // android.app.Service
    @Nullable
    public final IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    protected final void invalidateForegroundNotification() {
        b bVar = this.b;
        if (bVar != null && !this.k) {
            bVar.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<Download> list) {
        if (this.b != null) {
            for (int i = 0; i < list.size(); i++) {
                if (b(list.get(i).state)) {
                    this.b.a();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Download download) {
        onDownloadChanged(download);
        if (this.b == null) {
            return;
        }
        if (b(download.state)) {
            this.b.a();
        } else {
            this.b.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Download download) {
        onDownloadRemoved(download);
        b bVar = this.b;
        if (bVar != null) {
            bVar.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        b bVar = this.b;
        if (bVar != null) {
            bVar.b();
        }
        if (Util.SDK_INT >= 28 || !this.i) {
            this.j |= stopSelfResult(this.g);
            return;
        }
        stopSelf();
        this.j = true;
    }

    private static Intent a(Context context, Class<? extends DownloadService> cls, String str, boolean z) {
        return b(context, cls, str).putExtra("foreground", z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Intent b(Context context, Class<? extends DownloadService> cls, String str) {
        return new Intent(context, cls).setAction(str);
    }

    private static void a(Context context, Intent intent, boolean z) {
        if (z) {
            Util.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b {
        private final int b;
        private final long c;
        private final Handler d = new Handler(Looper.getMainLooper());
        private boolean e;
        private boolean f;

        public b(int i, long j) {
            this.b = i;
            this.c = j;
        }

        public void a() {
            this.e = true;
            e();
        }

        public void b() {
            this.e = false;
            this.d.removeCallbacksAndMessages(null);
        }

        public void c() {
            if (!this.f) {
                e();
            }
        }

        public void d() {
            if (this.f) {
                e();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            List<Download> currentDownloads = ((DownloadManager) Assertions.checkNotNull(DownloadService.this.f)).getCurrentDownloads();
            DownloadService downloadService = DownloadService.this;
            downloadService.startForeground(this.b, downloadService.getForegroundNotification(currentDownloads));
            this.f = true;
            if (this.e) {
                this.d.removeCallbacksAndMessages(null);
                this.d.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadService$b$QrLYgxHtQpLURn8-IG8Qj-uFUzk
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadService.b.this.e();
                    }
                }, this.c);
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class a implements DownloadManager.Listener {
        private final Context a;
        private final DownloadManager b;
        private final boolean c;
        @Nullable
        private final Scheduler d;
        private final Class<? extends DownloadService> e;
        @Nullable
        private DownloadService f;

        private a(Context context, DownloadManager downloadManager, boolean z, @Nullable Scheduler scheduler, Class<? extends DownloadService> cls) {
            this.a = context;
            this.b = downloadManager;
            this.c = z;
            this.d = scheduler;
            this.e = cls;
            downloadManager.addListener(this);
            c();
        }

        public void a(final DownloadService downloadService) {
            Assertions.checkState(this.f == null);
            this.f = downloadService;
            if (this.b.isInitialized()) {
                Util.createHandlerForCurrentOrMainLooper().postAtFrontOfQueue(new Runnable() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadService$a$C5rTUvO0MxGORFFGnsyd3BtUoPc
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadService.a.this.c(downloadService);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(DownloadService downloadService) {
            downloadService.a(this.b.getCurrentDownloads());
        }

        public void b(DownloadService downloadService) {
            Assertions.checkState(this.f == downloadService);
            this.f = null;
            if (this.d != null && !this.b.isWaitingForRequirements()) {
                this.d.cancel();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onInitialized(DownloadManager downloadManager) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                downloadService.a(downloadManager.getCurrentDownloads());
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                downloadService.a(download);
            }
            if (a() && DownloadService.b(download.state)) {
                Log.w("DownloadService", "DownloadService wasn't running. Restarting.");
                b();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onDownloadRemoved(DownloadManager downloadManager, Download download) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                downloadService.b(download);
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public final void onIdle(DownloadManager downloadManager) {
            DownloadService downloadService = this.f;
            if (downloadService != null) {
                downloadService.b();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z) {
            if (!z && !downloadManager.getDownloadsPaused() && a()) {
                List<Download> currentDownloads = downloadManager.getCurrentDownloads();
                int i = 0;
                while (true) {
                    if (i >= currentDownloads.size()) {
                        break;
                    } else if (currentDownloads.get(i).state == 0) {
                        b();
                        break;
                    } else {
                        i++;
                    }
                }
            }
            c();
        }

        private boolean a() {
            DownloadService downloadService = this.f;
            return downloadService == null || downloadService.a();
        }

        private void b() {
            if (this.c) {
                Util.startForegroundService(this.a, DownloadService.b(this.a, this.e, "com.google.android.exoplayer.downloadService.action.RESTART"));
                return;
            }
            try {
                this.a.startService(DownloadService.b(this.a, this.e, DownloadService.ACTION_INIT));
            } catch (IllegalStateException unused) {
                Log.w("DownloadService", "Failed to restart DownloadService (process is idle).");
            }
        }

        private void c() {
            if (this.d != null) {
                if (this.b.isWaitingForRequirements()) {
                    String packageName = this.a.getPackageName();
                    if (!this.d.schedule(this.b.getRequirements(), packageName, "com.google.android.exoplayer.downloadService.action.RESTART")) {
                        Log.e("DownloadService", "Scheduling downloads failed.");
                        return;
                    }
                    return;
                }
                this.d.cancel();
            }
        }
    }
}
