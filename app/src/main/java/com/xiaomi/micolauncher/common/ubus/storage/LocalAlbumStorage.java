package com.xiaomi.micolauncher.common.ubus.storage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.AtomicFile;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.base.utils.IOUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.LocalAlbumFilesUpdated;
import com.xiaomi.micolauncher.common.event.LocalAlbumSingleFileAdded;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.ubus.helpers.LocalAlbumHandleHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class LocalAlbumStorage {
    public static final String LOCAL_ALBUM_FILES_DIR_NAME = "ReceivedImages";
    private AtomicReference<File> a;
    private final Object b;
    @GuardedBy("downloadingLock")
    private int c;

    /* loaded from: classes3.dex */
    public static class AlbumStorageQuota {
        @SerializedName("timestamp")
        public long timestamp;
        @SerializedName("totalStorageSpace")
        public int totalStorageSpace = 300;
        @SerializedName("usedStorageSpace")
        public int usedStorageSpace = 0;
        @SerializedName("noStorageSpaceFlag")
        int a = 0;
    }

    /* loaded from: classes3.dex */
    public static class a {
        static final LocalAlbumStorage a = new LocalAlbumStorage();
    }

    private LocalAlbumStorage() {
        this.a = new AtomicReference<>();
        this.b = new Object();
        this.c = 0;
    }

    public static LocalAlbumStorage getInstance() {
        return a.a;
    }

    public AlbumStorageQuota getStorageQuota(Context context) {
        AlbumStorageQuota albumStorageQuota = new AlbumStorageQuota();
        albumStorageQuota.usedStorageSpace = getUsedQuota(context);
        albumStorageQuota.timestamp = System.currentTimeMillis();
        albumStorageQuota.a = isFull() ? 1 : 0;
        return albumStorageQuota;
    }

    public List<File> getDownloadedFiles() {
        return a(true);
    }

    @NonNull
    private List<File> a(final boolean z) {
        File[] listFiles = a().listFiles(new FilenameFilter() { // from class: com.xiaomi.micolauncher.common.ubus.storage.-$$Lambda$LocalAlbumStorage$1mxhke8WBPGizmnvCIyiF_WCB08
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                boolean a2;
                a2 = LocalAlbumStorage.a(z, file, str);
                return a2;
            }
        });
        if (ContainerUtil.isEmpty(listFiles)) {
            return ContainerUtil.getEmptyList();
        }
        List<File> asList = Arrays.asList(listFiles);
        asList.sort($$Lambda$LocalAlbumStorage$HakWbvF4k55AUN2jGKEdQNxjbQ.INSTANCE);
        return asList;
    }

    public static /* synthetic */ boolean a(boolean z, File file, String str) {
        return !z || (str != null && str.endsWith(".webp"));
    }

    public static /* synthetic */ int a(File file, File file2) {
        return Long.signum(file2.lastModified() - file.lastModified());
    }

    @Nullable
    public File getCoverFile() {
        File file = this.a.get();
        if (file != null && file.exists()) {
            return file;
        }
        List<File> downloadedFiles = getDownloadedFiles();
        File file2 = null;
        if (ContainerUtil.isEmpty(downloadedFiles)) {
            return null;
        }
        long j = 0;
        for (File file3 : downloadedFiles) {
            if (file3.lastModified() > j) {
                j = file3.lastModified();
                file2 = file3;
            }
        }
        return file2;
    }

    public AlbumStorageQuota downloadFiles(Context context, List<LocalAlbumHandleHelper.OneUrlInfo> list) {
        L.localalbum.d("download files %s", TextUtils.join(StringUtils.SPACE, list));
        if (ContainerUtil.isEmpty(list)) {
            return getStorageQuota(context);
        }
        int usedQuota = getUsedQuota(context) + list.size();
        L.localalbum.d("used increased by %s to %s", Integer.valueOf(list.size()), Integer.valueOf(usedQuota));
        a(context, usedQuota);
        synchronized (this.b) {
            this.c += ContainerUtil.getSize(list);
        }
        Observable<Pair<LocalAlbumHandleHelper.OneUrlInfo, ResponseBody>> observable = null;
        for (final LocalAlbumHandleHelper.OneUrlInfo oneUrlInfo : list) {
            Observable<Pair<LocalAlbumHandleHelper.OneUrlInfo, ResponseBody>> onErrorReturn = ApiManager.albumImageDownloadService.downloadFile(oneUrlInfo.url).concatMap(new Function() { // from class: com.xiaomi.micolauncher.common.ubus.storage.-$$Lambda$LocalAlbumStorage$NAspPt2c_6TAj7asuCdrb2bdIr4
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a2;
                    a2 = LocalAlbumStorage.a(LocalAlbumHandleHelper.OneUrlInfo.this, (ResponseBody) obj);
                    return a2;
                }
            }).onErrorReturn(new Function() { // from class: com.xiaomi.micolauncher.common.ubus.storage.-$$Lambda$LocalAlbumStorage$oFpXZ1ZXeGILPHktv8UFEAmQvH4
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Pair a2;
                    a2 = LocalAlbumStorage.a(LocalAlbumHandleHelper.OneUrlInfo.this, (Throwable) obj);
                    return a2;
                }
            });
            observable = observable == null ? onErrorReturn : Observable.merge(observable, onErrorReturn);
        }
        if (observable != null) {
            a(context, list, observable);
            return getStorageQuota(context);
        }
        throw new IllegalStateException("we have checked urls, should not be empty");
    }

    public static /* synthetic */ ObservableSource a(LocalAlbumHandleHelper.OneUrlInfo oneUrlInfo, ResponseBody responseBody) throws Exception {
        return Observable.just(new Pair(oneUrlInfo, responseBody));
    }

    public static /* synthetic */ Pair a(LocalAlbumHandleHelper.OneUrlInfo oneUrlInfo, Throwable th) throws Exception {
        L.localalbum.e("failed to download %s for %s", oneUrlInfo.url, th);
        return null;
    }

    public void a(Context context) {
        int usedQuota = getUsedQuota(context) - 1;
        L.localalbum.d("download failed : used quota decreased by 1 to %s", Integer.valueOf(usedQuota));
        a(context, usedQuota);
    }

    private void a(final Context context, final List<LocalAlbumHandleHelper.OneUrlInfo> list, Observable<Pair<LocalAlbumHandleHelper.OneUrlInfo, ResponseBody>> observable) {
        observable.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.io()).subscribe(new Observer<Pair<LocalAlbumHandleHelper.OneUrlInfo, ResponseBody>>() { // from class: com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage.1
            private volatile Throwable d;

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            /* renamed from: a */
            public void onNext(Pair<LocalAlbumHandleHelper.OneUrlInfo, ResponseBody> pair) {
                if (pair == null) {
                    a();
                    return;
                }
                LocalAlbumHandleHelper.OneUrlInfo oneUrlInfo = (LocalAlbumHandleHelper.OneUrlInfo) pair.first;
                ResponseBody responseBody = (ResponseBody) pair.second;
                L.localalbum.d("save file for %s", oneUrlInfo);
                File a2 = LocalAlbumStorage.this.a(oneUrlInfo);
                if (!(responseBody != null && LocalAlbumStorage.this.a(responseBody.byteStream(), a2))) {
                    L.localalbum.e("Failed to save url %s stream to file", oneUrlInfo);
                    LocalAlbumStorage.this.a(context);
                    return;
                }
                L.localalbum.i("Successfully saved url %s, %s bytes", oneUrlInfo, Long.valueOf(a2.length()));
                LocalAlbumStorage.this.a.set(a2);
                EventBusRegistry.getEventBus().post(new LocalAlbumSingleFileAdded(a2));
                LocalAlbumStorage.this.a(context, a2);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                this.d = th;
                L.localalbum.w("onError %s", th);
                a();
            }

            private void a() {
                synchronized (LocalAlbumStorage.this.b) {
                    LocalAlbumStorage.this.c = 0;
                }
                LocalAlbumStorage.this.c(context);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (this.d == null) {
                    synchronized (LocalAlbumStorage.this.b) {
                        LocalAlbumStorage.this.c = 0;
                    }
                    LocalAlbumStorage.this.c(context);
                    EventBusRegistry.getEventBus().post(new LocalAlbumFilesUpdated(ContainerUtil.getSize(list)));
                    L.localalbum.i("send event LocalAlbumFilesUpdated");
                    return;
                }
                throw new IllegalStateException("Unexpected, downloadingCount will be wrong");
            }
        });
    }

    public boolean a(InputStream inputStream, File file) {
        IOException e;
        AtomicFile atomicFile;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                atomicFile = new AtomicFile(file);
                try {
                    fileOutputStream = atomicFile.startWrite();
                    L.localalbum.i("copied %s bytes to %s", Long.valueOf(IOUtils.copy(inputStream, fileOutputStream)), file);
                    atomicFile.finishWrite(fileOutputStream);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    L.localalbum.e("failed to save input stream to %s for %s", file, e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    return false;
                }
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        } catch (IOException e3) {
            e = e3;
            atomicFile = null;
        }
    }

    public File a(LocalAlbumHandleHelper.OneUrlInfo oneUrlInfo) {
        String replace = oneUrlInfo.objectName.replace(JsonPointer.SEPARATOR, '_');
        if (a(replace)) {
            File a2 = a();
            return new File(a2, replace + ".webp");
        }
        String encodeToString = Base64.encodeToString(oneUrlInfo.objectName.getBytes(), 2);
        File a3 = a();
        return new File(a3, encodeToString + ".webp");
    }

    private static boolean a(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && !((charAt >= '0' && charAt <= '9') || charAt == '.' || charAt == '-' || charAt == '_'))) {
                return false;
            }
        }
        return true;
    }

    private File a() {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = new File(externalStoragePublicDirectory, LOCAL_ALBUM_FILES_DIR_NAME);
        L.localalbum.i("directory to store album images is %s", file);
        if (!file.isDirectory()) {
            L.localalbum.i("create directory to store album images %s", file);
            if (file.isFile()) {
                L.localalbum.e("a same named file existed?");
                return externalStoragePublicDirectory;
            } else if (!file.mkdir()) {
                L.localalbum.e("failed to create directory %s", file);
            }
        }
        return file;
    }

    public int getUsedQuota(Context context) {
        c(context);
        return b(context);
    }

    private int b(Context context) {
        return d(context).getInt("Used", 0);
    }

    public void c(Context context) {
        synchronized (this.b) {
            if (this.c != 0) {
                L.localalbum.d("do not update for downloading count is not 0 : it is %s", Integer.valueOf(this.c));
                return;
            }
            int size = ContainerUtil.getSize(getDownloadedFiles());
            int b = b(context);
            if (b != size) {
                L.localalbum.w("used quota in pref %s Not equal to files %s", Integer.valueOf(b), Integer.valueOf(size));
                a(context, size);
            }
        }
    }

    private void a(Context context, int i) {
        if (i < 0) {
            L.localalbum.e("used space is negative");
            i = 0;
        }
        d(context).edit().putInt("Used", i).apply();
        L.localalbum.d("update used quota to %s", Integer.valueOf(i));
    }

    private SharedPreferences d(Context context) {
        return context.getSharedPreferences("LocalAlbumStorage", 0);
    }

    public void a(Context context, File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
        L.localalbum.d("sent scan media scan broadcast");
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, $$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSRtLtE.INSTANCE);
        L.localalbum.d("scan %s cost time nanos %s", file, Long.valueOf(SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos));
    }

    public static /* synthetic */ void a(String str, Uri uri) {
        L.localalbum.v("Scan singele file complete %s %s", str, uri);
    }

    @VisibleForTesting
    public boolean isFull() {
        long b = b();
        return Hardware.isX08() ? b >= 104857600 : Hardware.isX10() ? b >= 209715200 : b >= 52428800;
    }

    @VisibleForTesting
    private long b() {
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        List<File> a2 = a(false);
        long j = 0;
        if (ContainerUtil.isEmpty(a2)) {
            return 0L;
        }
        for (File file : a2) {
            j += file.length();
        }
        L.localalbum.d("counting file bytes costs time nanos %s, bytes %s", Long.valueOf(SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos), Long.valueOf(j));
        return j;
    }
}
