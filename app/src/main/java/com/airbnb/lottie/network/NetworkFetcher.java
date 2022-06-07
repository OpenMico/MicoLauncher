package com.airbnb.lottie.network;

import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class NetworkFetcher {
    @NonNull
    private final LottieNetworkFetcher fetcher;
    @NonNull
    private final NetworkCache networkCache;

    public NetworkFetcher(@NonNull NetworkCache networkCache, @NonNull LottieNetworkFetcher lottieNetworkFetcher) {
        this.networkCache = networkCache;
        this.fetcher = lottieNetworkFetcher;
    }

    @NonNull
    @WorkerThread
    public LottieResult<LottieComposition> fetchSync(@NonNull String str, @Nullable String str2) {
        LottieComposition fetchFromCache = fetchFromCache(str, str2);
        if (fetchFromCache != null) {
            return new LottieResult<>(fetchFromCache);
        }
        Logger.debug("Animation for " + str + " not found in cache. Fetching from network.");
        return fetchFromNetwork(str, str2);
    }

    @Nullable
    @WorkerThread
    private LottieComposition fetchFromCache(@NonNull String str, @Nullable String str2) {
        Pair<FileExtension, InputStream> fetch;
        LottieResult<LottieComposition> lottieResult;
        if (str2 == null || (fetch = this.networkCache.fetch(str)) == null) {
            return null;
        }
        FileExtension fileExtension = (FileExtension) fetch.first;
        InputStream inputStream = (InputStream) fetch.second;
        if (fileExtension == FileExtension.ZIP) {
            lottieResult = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), str);
        } else {
            lottieResult = LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
        }
        if (lottieResult.getValue() != null) {
            return lottieResult.getValue();
        }
        return null;
    }

    @NonNull
    @WorkerThread
    private LottieResult<LottieComposition> fetchFromNetwork(@NonNull String str, @Nullable String str2) {
        LottieFetchResult lottieFetchResult;
        try {
            Logger.debug("Fetching " + str);
            lottieFetchResult = null;
            try {
                LottieFetchResult fetchSync = this.fetcher.fetchSync(str);
                if (fetchSync.isSuccessful()) {
                    LottieResult<LottieComposition> fromInputStream = fromInputStream(str, fetchSync.bodyByteStream(), fetchSync.contentType(), str2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Completed fetch from network. Success: ");
                    sb.append(fromInputStream.getValue() != null);
                    Logger.debug(sb.toString());
                    if (fetchSync != null) {
                        try {
                            fetchSync.close();
                        } catch (IOException e) {
                            Logger.warning("LottieFetchResult close failed ", e);
                        }
                    }
                    return fromInputStream;
                }
                LottieResult<LottieComposition> lottieResult = new LottieResult<>(new IllegalArgumentException(fetchSync.error()));
                if (fetchSync != null) {
                    try {
                        fetchSync.close();
                    } catch (IOException e2) {
                        Logger.warning("LottieFetchResult close failed ", e2);
                    }
                }
                return lottieResult;
            } catch (Exception e3) {
                LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e3);
                if (0 != 0) {
                    try {
                        lottieFetchResult.close();
                    } catch (IOException e4) {
                        Logger.warning("LottieFetchResult close failed ", e4);
                    }
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    lottieFetchResult.close();
                } catch (IOException e5) {
                    Logger.warning("LottieFetchResult close failed ", e5);
                }
            }
            throw th;
        }
    }

    @NonNull
    private LottieResult<LottieComposition> fromInputStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2, @Nullable String str3) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> lottieResult;
        if (str2 == null) {
            str2 = "application/json";
        }
        if (str2.contains("application/zip") || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            lottieResult = fromZipStream(str, inputStream, str3);
        } else {
            Logger.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            lottieResult = fromJsonStream(str, inputStream, str3);
        }
        if (!(str3 == null || lottieResult.getValue() == null)) {
            this.networkCache.renameTempFile(str, fileExtension);
        }
        return lottieResult;
    }

    @NonNull
    private LottieResult<LottieComposition> fromZipStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2) throws IOException {
        if (str2 == null) {
            return LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), null);
        }
        return LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.ZIP))), str);
    }

    @NonNull
    private LottieResult<LottieComposition> fromJsonStream(@NonNull String str, @NonNull InputStream inputStream, @Nullable String str2) throws IOException {
        if (str2 == null) {
            return LottieCompositionFactory.fromJsonInputStreamSync(inputStream, null);
        }
        return LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.JSON).getAbsolutePath())), str);
    }
}
