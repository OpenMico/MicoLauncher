package com.xiaomi.smarthome.library.common.util;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.lang.reflect.Method;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes4.dex */
public class DownloadManagerPro {
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String METHOD_NAME_PAUSE_DOWNLOAD = "pauseDownload";
    public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";
    private DownloadManager e;
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private static boolean a = false;
    private static boolean b = false;
    private static Method c = null;
    private static Method d = null;

    public DownloadManagerPro(DownloadManager downloadManager) {
        this.e = downloadManager;
    }

    public DownloadManager getDownloadManager() {
        return this.e;
    }

    public int getStatusById(long j) {
        return b(j, "status");
    }

    public int[] getDownloadBytes(long j) {
        int[] bytesAndStatus = getBytesAndStatus(j);
        return new int[]{bytesAndStatus[0], bytesAndStatus[1]};
    }

    public int[] getBytesAndStatus(long j) {
        int[] iArr = {-1, -1, 0};
        Cursor cursor = null;
        try {
            cursor = this.e.query(new DownloadManager.Query().setFilterById(j));
            if (cursor != null && cursor.moveToFirst()) {
                iArr[0] = cursor.getInt(cursor.getColumnIndexOrThrow("bytes_so_far"));
                iArr[1] = cursor.getInt(cursor.getColumnIndexOrThrow("total_size"));
                iArr[2] = cursor.getInt(cursor.getColumnIndex("status"));
            }
            return iArr;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int pauseDownload(long... jArr) {
        a();
        Method method = c;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.e, jArr)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int resumeDownload(long... jArr) {
        b();
        Method method = d;
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(this.e, jArr)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean isExistPauseAndResumeMethod() {
        a();
        b();
        return (c == null || d == null) ? false : true;
    }

    private static void a() {
        if (!a) {
            a = true;
            try {
                c = DownloadManager.class.getMethod(METHOD_NAME_PAUSE_DOWNLOAD, long[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void b() {
        if (!b) {
            b = true;
            try {
                d = DownloadManager.class.getMethod(METHOD_NAME_RESUME_DOWNLOAD, long[].class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(long j) {
        return a(j, Build.VERSION.SDK_INT < 11 ? COLUMN_LOCAL_URI : COLUMN_LOCAL_FILENAME);
    }

    public String getUri(long j) {
        return a(j, MiSoundBoxCommandExtras.URI);
    }

    public int getReason(long j) {
        return b(j, IChannel.EXTRA_CLOSE_REASON);
    }

    public int getPausedReason(long j) {
        return b(j, IChannel.EXTRA_CLOSE_REASON);
    }

    public int getErrorCode(long j) {
        return b(j, IChannel.EXTRA_CLOSE_REASON);
    }

    /* loaded from: classes4.dex */
    public static class RequestPro extends DownloadManager.Request {
        public static final String METHOD_NAME_SET_NOTI_CLASS = "setNotiClass";
        public static final String METHOD_NAME_SET_NOTI_EXTRAS = "setNotiExtras";
        private static boolean a = false;
        private static boolean b = false;
        private static Method c;
        private static Method d;

        public RequestPro(Uri uri) {
            super(uri);
        }

        public void setNotiClass(String str) {
            synchronized (this) {
                if (!a) {
                    a = true;
                    try {
                        c = DownloadManager.Request.class.getMethod(METHOD_NAME_SET_NOTI_CLASS, CharSequence.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Method method = c;
            if (method != null) {
                try {
                    method.invoke(this, str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public void setNotiExtras(String str) {
            synchronized (this) {
                if (!b) {
                    b = true;
                    try {
                        d = DownloadManager.Request.class.getMethod(METHOD_NAME_SET_NOTI_EXTRAS, CharSequence.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Method method = d;
            if (method != null) {
                try {
                    method.invoke(this, str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private String a(long j, String str) {
        Throwable th;
        Cursor cursor;
        String str2 = null;
        try {
            cursor = this.e.query(new DownloadManager.Query().setFilterById(j));
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        str2 = cursor.getString(cursor.getColumnIndex(str));
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return str2;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }

    private int b(long j, String str) {
        Cursor cursor = null;
        try {
            cursor = this.e.query(new DownloadManager.Query().setFilterById(j));
            return (cursor == null || !cursor.moveToFirst()) ? -1 : cursor.getInt(cursor.getColumnIndex(str));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
