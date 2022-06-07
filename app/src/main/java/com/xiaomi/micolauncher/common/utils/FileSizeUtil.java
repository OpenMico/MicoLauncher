package com.xiaomi.micolauncher.common.utils;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.v4.media.session.PlaybackStateCompat;
import com.xiaomi.micolauncher.common.L;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/* loaded from: classes3.dex */
public class FileSizeUtil {
    public static long getFileSize(File file) {
        if (file.exists()) {
            return file.length();
        }
        return 0L;
    }

    public static long getSdCardFreeSize() {
        return getFileSize(Environment.getExternalStorageDirectory());
    }

    public static long getDirectorySize(File file) {
        if (!file.exists()) {
            return 0L;
        }
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
    }

    public static long getSdCardTotalSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
    }

    public static long toUnitOf16M(long j) {
        return j / 16777216;
    }

    public static long toUnitOf1K(long j) {
        return j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public static long getStorageFreeSizeSync(Context context) {
        StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService("storagestats");
        long j = 0;
        for (StorageVolume storageVolume : ((StorageManager) context.getSystemService("storage")).getStorageVolumes()) {
            String uuid = storageVolume.getUuid();
            try {
                j += storageStatsManager.getFreeBytes(uuid == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuid));
            } catch (IOException e) {
                L.storage.e("FileSizeUtil deleteApplicationCacheFiles", e);
            }
        }
        L.storage.i("FileSizeUtil getStorageFreeSize free space is %s", Long.valueOf(j));
        return j;
    }

    public static String printSDCardSpaceInfo() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return "NotMounted";
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        String str = "block大小:" + blockSizeLong + ",block数目:" + blockCountLong + ",总大小:" + ((blockCountLong * blockSizeLong) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "KB";
        String str2 = "可用的block数目：:" + availableBlocksLong + ",剩余空间:" + ((availableBlocksLong * blockSizeLong) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "KB";
        L.storage.d(str);
        L.storage.d(str2);
        return str + "\n" + str2;
    }

    public static String printSystemSpaceInfo() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        String str = "block大小:" + blockSizeLong + ",block数目:" + blockCountLong + ",总大小:" + ((blockCountLong * blockSizeLong) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "KB";
        String str2 = "可用的block数目：:" + availableBlocksLong + ",可用大小:" + ((availableBlocksLong * blockSizeLong) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "KB";
        L.storage.d(str);
        L.storage.d(str2);
        return str + "\n" + str2;
    }
}
