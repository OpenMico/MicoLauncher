package com.xiaomi.miot.support.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceInfoLocalCacheManager;
import com.xiaomi.miot.support.core.MiotDeviceCore;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.ui.header.MiotHeaderData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotUtil {
    public static final String EMPTY_STRING = "";
    private static volatile Handler sHandler;
    private static volatile HandlerThread sHandlerThread;

    public static void execute(Runnable runnable) {
        if (runnable != null) {
            checkInit();
            sHandler.post(runnable);
        }
    }

    public static void postDelayed(Runnable runnable, long j) {
        if (runnable != null) {
            checkInit();
            sHandler.postDelayed(runnable, j);
        }
    }

    public static void removeCallback(Runnable runnable) {
        if (runnable != null && sHandler != null) {
            sHandler.removeCallbacks(runnable);
        }
    }

    private static void checkInit() {
        if (sHandlerThread == null) {
            synchronized (MiotUtil.class) {
                if (sHandlerThread == null) {
                    sHandlerThread = new HandlerThread("MICO_SUP_THREAD");
                    sHandlerThread.start();
                    sHandler = new Handler(sHandlerThread.getLooper());
                }
            }
        }
    }

    public static int dp2px(Context context, float f) {
        return (int) (context.getResources().getDisplayMetrics().density * f);
    }

    public static MiotHeaderData parseEvents(String str, List<MiotDeviceEvent> list, MiotDeviceCore miotDeviceCore) {
        Exception e;
        if (list == null || list.isEmpty()) {
            return null;
        }
        MiotHeaderData.Item item = null;
        MiotHeaderData.Item item2 = null;
        MiotHeaderData.Item item3 = null;
        for (MiotDeviceEvent miotDeviceEvent : list) {
            for (MiotDeviceEvent.Attr attr : miotDeviceEvent.attrs) {
                try {
                    int intValue = Float.valueOf(attr.value).intValue();
                    if (attr.type == MiotDeviceEvent.Type.TEMPERATURE) {
                        if (item == null) {
                            MiotHeaderData.Item item4 = new MiotHeaderData.Item();
                            try {
                                item4.did = miotDeviceEvent.did;
                                item4.roomId = miotDeviceEvent.roomId;
                                item4.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item4.value = intValue;
                                item = item4;
                            } catch (Exception e2) {
                                e = e2;
                                item = item4;
                                MiotLog.e("format attr value to int failed: " + e.getMessage());
                            }
                        } else if (TextUtils.equals(miotDeviceEvent.roomId, str)) {
                            if (!TextUtils.equals(item.roomId, str)) {
                                item.did = miotDeviceEvent.did;
                                item.roomId = miotDeviceEvent.roomId;
                                item.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item.value = intValue;
                            } else if (item.value == 0) {
                                item.value = intValue;
                            }
                        }
                    } else if (attr.type == MiotDeviceEvent.Type.HUMIDITY) {
                        if (item2 == null) {
                            MiotHeaderData.Item item5 = new MiotHeaderData.Item();
                            try {
                                item5.did = miotDeviceEvent.did;
                                item5.roomId = miotDeviceEvent.roomId;
                                item5.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item5.value = intValue;
                                item2 = item5;
                            } catch (Exception e3) {
                                e = e3;
                                item2 = item5;
                                MiotLog.e("format attr value to int failed: " + e.getMessage());
                            }
                        } else if (TextUtils.equals(miotDeviceEvent.roomId, str)) {
                            if (!TextUtils.equals(item2.roomId, str)) {
                                item2.did = miotDeviceEvent.did;
                                item2.roomId = miotDeviceEvent.roomId;
                                item2.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item2.value = intValue;
                            } else if (item2.value == 0) {
                                item2.value = intValue;
                            }
                        }
                    } else if (attr.type == MiotDeviceEvent.Type.PM25) {
                        if (item3 == null) {
                            MiotHeaderData.Item item6 = new MiotHeaderData.Item();
                            try {
                                item6.did = miotDeviceEvent.did;
                                item6.roomId = miotDeviceEvent.roomId;
                                item6.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item6.value = intValue;
                                item3 = item6;
                            } catch (Exception e4) {
                                e = e4;
                                item3 = item6;
                                MiotLog.e("format attr value to int failed: " + e.getMessage());
                            }
                        } else if (TextUtils.equals(miotDeviceEvent.roomId, str)) {
                            if (!TextUtils.equals(item3.roomId, str)) {
                                item3.did = miotDeviceEvent.did;
                                item3.roomId = miotDeviceEvent.roomId;
                                item3.roomInfo = miotDeviceCore.getRoomInfoByRoomId(miotDeviceEvent.roomId);
                                item3.value = intValue;
                            } else if (item3.value == 0) {
                                item3.value = intValue;
                            }
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            }
        }
        if (item == null && item2 == null && item3 == null) {
            return null;
        }
        MiotHeaderData miotHeaderData = new MiotHeaderData();
        miotHeaderData.mTemperature = item;
        miotHeaderData.mHumidity = item2;
        miotHeaderData.mPM25 = item3;
        return miotHeaderData;
    }

    public static String MD5_32(String str) {
        return TextUtils.isEmpty(str) ? "" : getBytesMD5(str.getBytes());
    }

    public static String getBytesMD5(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr, 0, bArr.length);
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(byte2Hex(b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    private static String byte2Hex(byte b) {
        int i = (b & Byte.MAX_VALUE) + (b < 0 ? 128 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append(i < 16 ? "0" : "");
        sb.append(Integer.toHexString(i).toLowerCase());
        return sb.toString();
    }

    public static boolean hasNetWork(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
        } catch (Exception unused) {
            return false;
        }
    }

    public static File createOrLoadCacheFile(String str) {
        Context context = MiotManager.getContext();
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(context.getCacheDir().getAbsolutePath() + File.separator + str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                MiotLog.e("Error: create prop cahce file error; " + e.getMessage());
                return null;
            }
        }
        return file;
    }

    public static void saveDeviceCache(String str) {
        saveDeviceCache(str, 1);
    }

    public static void saveDeviceListInfoCache(String str) {
        saveDeviceCache(str, 2);
    }

    private static void saveDeviceCache(String str, int i) {
        Throwable th;
        IOException e;
        StringBuilder sb;
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str)) {
            MiotLog.e("Error: save device cache error; null");
            return;
        }
        String str2 = DeviceInfoLocalCacheManager.FILE_NAME_PROP_CACHE;
        if (i == 2) {
            str2 = DeviceInfoLocalCacheManager.FILE_NAME_DEVICE_CACHE;
        }
        File createOrLoadCacheFile = createOrLoadCacheFile(str2);
        if (createOrLoadCacheFile == null) {
            MiotLog.e("Error: create or load prop cache file fail, null");
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(createOrLoadCacheFile);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            fileOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
            } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder();
                sb.append("Error: close file error; ");
                sb.append(e.getMessage());
                MiotLog.e(sb.toString());
            }
        } catch (IOException e4) {
            e = e4;
            fileOutputStream2 = fileOutputStream;
            MiotLog.e("Error: write device prop cache error; " + e.getMessage());
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e = e5;
                    sb = new StringBuilder();
                    sb.append("Error: close file error; ");
                    sb.append(e.getMessage());
                    MiotLog.e(sb.toString());
                }
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e6) {
                    MiotLog.e("Error: close file error; " + e6.getMessage());
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String loadInfoFromCache(java.lang.String r4) {
        /*
            java.io.File r4 = createOrLoadCacheFile(r4)
            r0 = 0
            if (r4 != 0) goto L_0x0008
            return r0
        L_0x0008:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: IOException -> 0x007f, all -> 0x007c
            r1.<init>(r4)     // Catch: IOException -> 0x007f, all -> 0x007c
            int r4 = r1.available()     // Catch: IOException -> 0x007a, all -> 0x00b9
            if (r4 > 0) goto L_0x0031
            r1.close()     // Catch: IOException -> 0x0017
            goto L_0x0030
        L_0x0017:
            r4 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error: close cache file input error: "
            r1.append(r2)
            java.lang.String r4 = r4.getMessage()
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            com.xiaomi.miot.support.MiotLog.e(r4)
        L_0x0030:
            return r0
        L_0x0031:
            byte[] r4 = new byte[r4]     // Catch: IOException -> 0x007a, all -> 0x00b9
            int r2 = r1.read(r4)     // Catch: IOException -> 0x007a, all -> 0x00b9
            if (r2 <= 0) goto L_0x005c
            java.lang.String r2 = new java.lang.String     // Catch: IOException -> 0x007a, all -> 0x00b9
            r2.<init>(r4)     // Catch: IOException -> 0x007a, all -> 0x00b9
            r1.close()     // Catch: IOException -> 0x0042
            goto L_0x005b
        L_0x0042:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Error: close cache file input error: "
            r0.append(r1)
            java.lang.String r4 = r4.getMessage()
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            com.xiaomi.miot.support.MiotLog.e(r4)
        L_0x005b:
            return r2
        L_0x005c:
            r1.close()     // Catch: IOException -> 0x0060
            goto L_0x0079
        L_0x0060:
            r4 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error: close cache file input error: "
            r1.append(r2)
            java.lang.String r4 = r4.getMessage()
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            com.xiaomi.miot.support.MiotLog.e(r4)
        L_0x0079:
            return r0
        L_0x007a:
            r4 = move-exception
            goto L_0x0081
        L_0x007c:
            r4 = move-exception
            r1 = r0
            goto L_0x00ba
        L_0x007f:
            r4 = move-exception
            r1 = r0
        L_0x0081:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: all -> 0x00b9
            r2.<init>()     // Catch: all -> 0x00b9
            java.lang.String r3 = "Error: read cache file error: "
            r2.append(r3)     // Catch: all -> 0x00b9
            java.lang.String r4 = r4.getMessage()     // Catch: all -> 0x00b9
            r2.append(r4)     // Catch: all -> 0x00b9
            java.lang.String r4 = r2.toString()     // Catch: all -> 0x00b9
            com.xiaomi.miot.support.MiotLog.e(r4)     // Catch: all -> 0x00b9
            if (r1 == 0) goto L_0x00b8
            r1.close()     // Catch: IOException -> 0x009f
            goto L_0x00b8
        L_0x009f:
            r4 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error: close cache file input error: "
            r1.append(r2)
            java.lang.String r4 = r4.getMessage()
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            com.xiaomi.miot.support.MiotLog.e(r4)
        L_0x00b8:
            return r0
        L_0x00b9:
            r4 = move-exception
        L_0x00ba:
            if (r1 == 0) goto L_0x00d9
            r1.close()     // Catch: IOException -> 0x00c0
            goto L_0x00d9
        L_0x00c0:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Error: close cache file input error: "
            r1.append(r2)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.xiaomi.miot.support.MiotLog.e(r0)
        L_0x00d9:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miot.support.util.MiotUtil.loadInfoFromCache(java.lang.String):java.lang.String");
    }
}
