package com.xiaomi.micolauncher.module.appstore.manager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.LongSparseArray;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.appstore.bean.DownloadRequest;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.library.common.util.DownloadManagerPro;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class AppDownloadManager {
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    private final LongSparseArray<DownloadRequest> a;
    private volatile Context c;
    private AtomicBoolean d;
    private AtomicBoolean e;
    private Handler f;

    public static /* synthetic */ void b() throws Exception {
    }

    private AppDownloadManager() {
        this.d = new AtomicBoolean(false);
        this.e = new AtomicBoolean(false);
        this.f = new MicoHandler(ThreadUtil.getWorkHandler().getLooper()) { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppDownloadManager.1
            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public String getLogTag() {
                return "AppDownloadManager";
            }

            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public void processMessage(Message message) {
                if (message.what == 0) {
                    AppDownloadManager.this.a();
                }
            }
        };
        this.a = new LongSparseArray<>();
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final AppDownloadManager a = new AppDownloadManager();
    }

    public static AppDownloadManager getAppDownloadManager() {
        return a.a;
    }

    public void init(Context context) {
        this.c = context;
        this.f.sendEmptyMessageDelayed(0, b);
    }

    public DownloadRequest getDownloadRequestByDownloadId(long j) {
        DownloadRequest downloadRequest;
        synchronized (this.a) {
            downloadRequest = this.a.get(j);
        }
        return downloadRequest;
    }

    public boolean checkAppInstalled(Context context, String str) {
        List<PackageInfo> installedPackages;
        if (str == null || str.isEmpty() || (installedPackages = context.getPackageManager().getInstalledPackages(0)) == null || installedPackages.isEmpty()) {
            return false;
        }
        for (int i = 0; i < installedPackages.size(); i++) {
            if (TextUtils.equals(str, installedPackages.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }

    public void inalidateHistoryState() {
        this.e.set(false);
    }

    public Observable<Boolean> clearDownloadDirectory(final File file) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$CeuWwiv4Vbhgc_hOJofqwQt9m5s
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppDownloadManager.this.a(file, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(File file, ObservableEmitter observableEmitter) throws Exception {
        a(file);
        observableEmitter.onNext(true);
        observableEmitter.onComplete();
    }

    private void a(File file) {
        String[] list;
        if (!(file == null || !file.isDirectory() || (list = file.list()) == null)) {
            for (String str : list) {
                new File(file, str).delete();
            }
        }
    }

    public Observable<Boolean> deleteByDownloadId(final Context context, final long[] jArr) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$qdkcbH8sRj0iOsiNe8aQlBovucs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppDownloadManager.this.a(context, jArr, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(Context context, long[] jArr, ObservableEmitter observableEmitter) throws Exception {
        ((DownloadManager) context.getSystemService(OneTrack.Event.DOWNLOAD)).remove(jArr);
        for (long j : jArr) {
            L.storage.i("AppDownloadManager deleteByDownloadId %d", Long.valueOf(j));
        }
        synchronized (this.a) {
            for (long j2 : jArr) {
                this.a.delete(j2);
            }
        }
        observableEmitter.onNext(true);
        observableEmitter.onComplete();
    }

    public void a() {
        LongSparseArray<DownloadRequest> clone;
        if (this.c == null) {
            L.storage.w("cannot check download task status for context is null");
            return;
        }
        synchronized (this.a) {
            clone = this.a.clone();
        }
        DownloadManager downloadManager = (DownloadManager) this.c.getSystemService(OneTrack.Event.DOWNLOAD);
        int i = 0;
        while (true) {
            if (i >= clone.size()) {
                break;
            }
            DownloadRequest valueAt = clone.valueAt(i);
            Cursor cursor = null;
            try {
                try {
                    cursor = downloadManager.query(new DownloadManager.Query().setFilterById(valueAt.getDownloadId()));
                    if (cursor != null && cursor.moveToFirst()) {
                        cursor.getColumnIndex("_id");
                        int columnIndex = cursor.getColumnIndex("bytes_so_far");
                        int columnIndex2 = cursor.getColumnIndex(IChannel.EXTRA_CLOSE_REASON);
                        int columnIndex3 = cursor.getColumnIndex("total_size");
                        int columnIndex4 = cursor.getColumnIndex("status");
                        int columnIndex5 = cursor.getColumnIndex(DownloadManagerPro.COLUMN_LOCAL_URI);
                        do {
                            valueAt.setDownloadedSize(cursor.getLong(columnIndex));
                            valueAt.setDownloadReason(cursor.getInt(columnIndex2));
                            valueAt.setTotalSize(cursor.getLong(columnIndex3));
                            valueAt.setDownloadStatus(cursor.getInt(columnIndex4));
                            valueAt.setLocalUri(cursor.getString(columnIndex5));
                        } while (cursor.moveToNext());
                        final long downloadId = valueAt.getDownloadId();
                        if ((valueAt.getDownloadStatus() & 8) != 0 && this.d.compareAndSet(false, true)) {
                            AppInstallManager.getManager().silentInstall(this.c, valueAt).subscribeOn(MicoSchedulers.computation()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$cQvNveX26K4f7ZptP728Gbe6r8Y
                                @Override // io.reactivex.functions.Consumer
                                public final void accept(Object obj) {
                                    AppDownloadManager.this.a(downloadId, (Integer) obj);
                                }
                            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$qMlcq07Mrn5Fvl4jl7K8T4KI374
                                @Override // io.reactivex.functions.Consumer
                                public final void accept(Object obj) {
                                    AppDownloadManager.this.a(downloadId, (Throwable) obj);
                                }
                            }, $$Lambda$AppDownloadManager$jqLMJqXhF7zdRhWcQK4TRi3tZp0.INSTANCE);
                            if (cursor != null && !cursor.isClosed()) {
                                cursor.close();
                            }
                        } else if ((valueAt.getDownloadStatus() & 16) != 0) {
                            deleteByDownloadId(this.c, new long[]{downloadId}).subscribeOn(MicoSchedulers.io()).subscribe();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (cursor != null) {
                        if (cursor.isClosed()) {
                        }
                    }
                }
                if (cursor != null) {
                    if (cursor.isClosed()) {
                    }
                    cursor.close();
                }
                i++;
            } catch (Throwable th) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                throw th;
            }
        }
        if (ContainerUtil.hasData(clone)) {
            this.f.sendEmptyMessageDelayed(0, b);
        }
    }

    public /* synthetic */ void a(long j, Integer num) throws Exception {
        deleteByDownloadId(this.c, new long[]{j}).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$vvkj69JRP-8WYhR29lYLRi5dqjQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppDownloadManager.this.b((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void b(Boolean bool) throws Exception {
        this.d.set(false);
    }

    public /* synthetic */ void a(long j, Throwable th) throws Exception {
        deleteByDownloadId(this.c, new long[]{j}).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$ii8PH994DkGTUJ0cl7Dm_mIfXcY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppDownloadManager.this.a((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void a(Boolean bool) throws Exception {
        this.d.set(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0210, code lost:
        if (r4.isClosed() == false) goto L_0x0200;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01cd A[Catch: Exception -> 0x0206, all -> 0x0204, TryCatch #4 {Exception -> 0x0206, blocks: (B:6:0x001b, B:8:0x0021, B:10:0x0027, B:11:0x005d, B:13:0x00bf, B:14:0x00cb, B:16:0x00d3, B:19:0x00e3, B:22:0x0109, B:25:0x0113, B:28:0x012b, B:30:0x0137, B:31:0x013b, B:33:0x014f, B:34:0x0171, B:36:0x017a, B:38:0x0180, B:39:0x0199, B:41:0x01b8, B:46:0x01cd, B:47:0x01e2, B:49:0x01e8), top: B:77:0x001b, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01ec A[LOOP:0: B:11:0x005d->B:50:0x01ec, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01e8 A[EDGE_INSN: B:79:0x01e8->B:49:0x01e8 ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkHisDownloadTasks(final android.content.Context r26, java.util.List<com.xiaomi.mico.appstore.bean.AppInfo> r27) {
        /*
            Method dump skipped, instructions count: 551
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.appstore.manager.AppDownloadManager.checkHisDownloadTasks(android.content.Context, java.util.List):void");
    }

    public /* synthetic */ void a(Context context, long j, Integer num) throws Exception {
        deleteByDownloadId(context, new long[]{j}).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    public /* synthetic */ void a(Context context, long j, Throwable th) throws Exception {
        deleteByDownloadId(context, new long[]{j}).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    void a(Context context, HashSet<String> hashSet) {
        String[] list;
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!(externalFilesDir == null || !externalFilesDir.isDirectory() || (list = externalFilesDir.list()) == null)) {
            for (int i = 0; i < list.length; i++) {
                String str = externalFilesDir.getPath() + "/" + list[i];
                if (!hashSet.contains(str)) {
                    L.storage.i("AppDownloadManager ClearDownloadDirectory del " + str);
                    new File(str).delete();
                }
            }
        }
    }

    AppInfo a(List<AppInfo> list, String str) {
        for (AppInfo appInfo : list) {
            if (TextUtils.equals(appInfo.getDownloadUrl(), str)) {
                return appInfo;
            }
        }
        return null;
    }

    public boolean fileIsExists(String str) {
        try {
            return new File(str).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    public Observable<DownloadRequest> queryDownloadDetailInfoByDownloadId(final Context context, final long j) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$aAwglQp6ytJqjURxn-100zrA5L4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppDownloadManager.this.a(j, context, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(long j, Context context, ObservableEmitter observableEmitter) throws Exception {
        DownloadRequest downloadRequest;
        synchronized (this.a) {
            downloadRequest = this.a.get(j);
        }
        if (downloadRequest == null || downloadRequest.getDownloadId() == 0) {
            observableEmitter.onError(new IllegalArgumentException("Download Request is invalid " + j));
            observableEmitter.onComplete();
            return;
        }
        Cursor query = ((DownloadManager) context.getSystemService(OneTrack.Event.DOWNLOAD)).query(new DownloadManager.Query().setFilterById(downloadRequest.getDownloadId()));
        Throwable th = null;
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    query.getColumnIndex("_id");
                    int columnIndex = query.getColumnIndex("bytes_so_far");
                    int columnIndex2 = query.getColumnIndex(IChannel.EXTRA_CLOSE_REASON);
                    int columnIndex3 = query.getColumnIndex("total_size");
                    int columnIndex4 = query.getColumnIndex("status");
                    int columnIndex5 = query.getColumnIndex(DownloadManagerPro.COLUMN_LOCAL_URI);
                    do {
                        downloadRequest.setDownloadedSize(query.getLong(columnIndex));
                        downloadRequest.setDownloadReason(query.getInt(columnIndex2));
                        downloadRequest.setTotalSize(query.getLong(columnIndex3));
                        downloadRequest.setDownloadStatus(query.getInt(columnIndex4));
                        downloadRequest.setLocalUri(query.getString(columnIndex5));
                    } while (query.moveToNext());
                }
            } catch (Throwable th2) {
                if (query != null) {
                    if (0 != 0) {
                        try {
                            query.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    } else {
                        query.close();
                    }
                }
                throw th2;
            }
        }
        if (query != null) {
            query.close();
        }
        observableEmitter.onNext(downloadRequest);
        observableEmitter.onComplete();
    }

    public Observable<Long> postDownloadApp(final Context context, final AppInfo appInfo, final DownloadRequest.IDownloadResult iDownloadResult) {
        L.storage.i("AppDownloadManager postDownloadApp %s from %s version=%s", appInfo.getPackageName(), appInfo.getDownloadUrl(), Long.valueOf(appInfo.getVersionCode()));
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppDownloadManager$fbBV3qNxsc63_URfbYMdghsZ_2I
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AppDownloadManager.this.a(context, appInfo, iDownloadResult, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(Context context, AppInfo appInfo, DownloadRequest.IDownloadResult iDownloadResult, ObservableEmitter observableEmitter) throws Exception {
        if (!this.e.get()) {
            checkHisDownloadTasks(context, AppStoreManager.getManager().getAppInfoList().blockingSingle());
        }
        long j = -1;
        synchronized (this.a) {
            int i = 0;
            while (true) {
                if (i >= this.a.size()) {
                    break;
                }
                DownloadRequest valueAt = this.a.valueAt(i);
                if (TextUtils.equals(valueAt.getAppInfo().getDownloadUrl(), appInfo.getDownloadUrl())) {
                    j = valueAt.getDownloadId();
                    valueAt.setDownloadCallback(iDownloadResult);
                    break;
                }
                i++;
            }
        }
        if (j >= 0) {
            observableEmitter.onNext(Long.valueOf(j));
        } else {
            String packageName = appInfo.getPackageName();
            DownloadRequest downloadRequest = new DownloadRequest(appInfo);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(OneTrack.Event.DOWNLOAD);
            Uri parse = Uri.parse(downloadRequest.getAppInfo().getDownloadUrl());
            if (!parse.getScheme().equals("https")) {
                observableEmitter.onError(new IllegalArgumentException("Download Request url is invalid " + parse.toString()));
                observableEmitter.onComplete();
            } else {
                DownloadManager.Request request = new DownloadManager.Request(parse);
                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, String.format("%s-%d.apk", packageName, Long.valueOf(appInfo.getVersionCode())));
                long enqueue = downloadManager.enqueue(request);
                downloadRequest.setDownloadId(enqueue);
                downloadRequest.setDownloadCallback(iDownloadResult);
                synchronized (this.a) {
                    this.a.put(enqueue, downloadRequest);
                }
                observableEmitter.onNext(Long.valueOf(enqueue));
            }
        }
        this.f.sendEmptyMessageDelayed(0, b);
        observableEmitter.onComplete();
    }

    DownloadRequest a(AppInfo appInfo, long j, String str) {
        DownloadRequest downloadRequest = new DownloadRequest(appInfo);
        downloadRequest.setDownloadId(j);
        downloadRequest.setLocalUri(str);
        synchronized (this.a) {
            this.a.put(j, downloadRequest);
        }
        this.f.sendEmptyMessageDelayed(0, b);
        return downloadRequest;
    }
}
