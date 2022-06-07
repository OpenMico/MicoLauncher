package com.xiaomi.miplay.mylibrary.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.xiaomi.miplay.mylibrary.screenbox.PlayStateConfig;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;

/* loaded from: classes4.dex */
public class Constant {
    public static final int COUNTDOWNINTERVAL = 10000;
    public static final int DELAY_TIME = 0;
    public static final int DISCOVERY_FREQUENCY = 60;
    public static final String FM_HIMALAYA = "com.ximalaya.ting.android";
    public static final String FM_QINGTING = "fm.qingting.qtradio";
    public static final int MAX_CONNECTION_NUM = 20;
    public static final int MILLISINFUTURE = 600000;
    public static final String MUSIC_KUGOU = "com.kugou.android";
    public static final String MUSIC_KUWO = "cn.kuwo.player";
    public static final String MUSIC_MIUI = "com.miui.player";
    public static final String MUSIC_QQ = "com.tencent.qqmusic";
    public static final String MUSIC_WANGYIYUN = "com.netease.cloudmusic";
    public static final String PACKAGENAME_FARFIELD = "com.xiaomi.smarthome";
    public static final String PACKAGENAME_GALLERY = "com.miui.gallery";
    public static final String PACKAGENAME_HOME = "com.xiaomi.eetv.home";
    public static final String PACKAGENAME_NEARFIELD = "com.milink.service.nearfield";
    public static final String PACKAGENAME_QIYI_VIDEO = "com.qiyi.video";
    public static final String PACKAGENAME_SMARTPANEL = "com.xiaomi.mitv.smartpanel";
    public static final String PACKAGENAME_SMARTSHARE = "com.xiaomi.mitv.smartshare";
    public static final String PACKAGENAME_SYSTEMUI = "com.milink.service";
    public static final String PACKAGENAME_VIDEO_AUDIO_CIRCULATION = "com.milink.service.circulation";
    public static final String PACKAGENAME_XIAOAI = "com.miui.voiceassist";
    public static final String PACKAGENAME_XIAOMI_VIDEO = "com.miui.video";
    public static final int PERIOD_TIME = 5;
    public static final String SUPPORTMPABILITY_AUDIO = "audio";
    public static final String SUPPORTMPABILITY_VIDEO = "video";
    private static final String a = "Constant";
    private static int b;
    private boolean c;
    private PlayStateConfig d;
    public int disctype = 2;

    /* loaded from: classes4.dex */
    private static class a {
        public static Constant a = new Constant();
    }

    public static Constant getInstance() {
        return a.a;
    }

    public int getDisctype() {
        return this.disctype;
    }

    public void setDisctype(int i) {
        this.disctype = i;
    }

    public boolean isStartDiscoveryInit() {
        return this.c;
    }

    public void setStartDiscoveryInit(boolean z) {
        this.c = z;
    }

    public PlayStateConfig getPlayStateConfig() {
        return this.d;
    }

    public void setPlayStateConfig(PlayStateConfig playStateConfig) {
        this.d = playStateConfig;
    }

    public static String getAppVersionName(Context context) {
        String str = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            str = packageInfo.versionName;
            b = packageInfo.versionCode;
            String str2 = a;
            Logger.i(str2, "versionName:" + str, new Object[0]);
            String str3 = a;
            Logger.i(str3, "versioncode:" + b, new Object[0]);
        } catch (Exception e) {
            Logger.e("VersionInfo", "Exception", e);
        }
        return str != null ? str.length() <= 0 ? "" : str : "";
    }

    public String convertMac(String str) {
        if (str.contains(Constants.COLON_SEPARATOR)) {
            return str.replace(Constants.COLON_SEPARATOR, "");
        }
        return "";
    }

    @RequiresApi(api = 26)
    public static String stringToBase64(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = 26)
    public static String base64ToString(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new String(Base64.getDecoder().decode(str.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String ReadTxtFile(String str) {
        String str2 = "";
        File file = new File(str);
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    str2 = str2 + readLine + "\n";
                }
                fileInputStream.close();
            } catch (FileNotFoundException unused) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return str2;
    }

    public static String getTxtContent(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean isBitMapEqual(Bitmap bitmap, Bitmap bitmap2) {
        Logger.i(a, "compare bitmap.", new Object[0]);
        if (bitmap == null || bitmap2 == null) {
            return false;
        }
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int[] iArr2 = new int[bitmap2.getWidth() * bitmap2.getHeight()];
        bitmap2.getPixels(iArr2, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        if (iArr.length != iArr2.length) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                i++;
            }
        }
        return i <= 10;
    }

    public static boolean compareBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Logger.i(a, "compareBitmap.", new Object[0]);
        if (bitmap == bitmap2) {
            return true;
        }
        if (bitmap == null || bitmap2 == null) {
            Logger.i(a, "a == null || b == null", new Object[0]);
            return false;
        }
        byte[] bitmap2Bytes = bitmap2Bytes(bitmap);
        byte[] bitmap2Bytes2 = bitmap2Bytes(bitmap2);
        if (bitmap2Bytes.length != bitmap2Bytes2.length) {
            Logger.i(a, "bytes1.length != bytes2.length", new Object[0]);
            return false;
        }
        for (int i = 0; i < bitmap2Bytes.length; i++) {
            if (bitmap2Bytes[i] != bitmap2Bytes2[i]) {
                Logger.i(a, "bytes1[" + i + "] != bytes2[]", new Object[0]);
                return false;
            }
        }
        Logger.i(a, "compareBitmap ans = true", new Object[0]);
        return true;
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Throwable, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v2, types: [void, java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1, types: [void, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r4v2, types: [void] */
    /* JADX WARN: Type inference failed for: r4v4, types: [android.accounts.Account, java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getXiaomiAccount(android.content.Context r4) {
        /*
            java.lang.String r0 = com.xiaomi.miplay.mylibrary.utils.Constant.a
            java.lang.String r1 = "getXiaomiAccount."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            void r4 = com.elvishew.xlog.Logger.i(r4, r0)
            java.lang.String r0 = "com.xiaomi"
            void r4 = r4.w(r0, r0)
            int r0 = r4.length
            if (r0 <= 0) goto L_0x0038
            r4 = r4[r2]
            java.lang.String r0 = com.xiaomi.miplay.mylibrary.utils.Constant.a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "account:"
            r1.append(r3)
            void r3 = r4.<init>()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.d(r0, r1, r2)
            java.lang.String r4 = r4.name
            return r4
        L_0x0038:
            java.lang.String r4 = ""
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.utils.Constant.getXiaomiAccount(android.content.Context):java.lang.String");
    }

    @SuppressLint({"HardwareIds"})
    public static String getBluetoothMac(@NonNull Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        if (bluetoothManager == null) {
            Log.e(a, "getBluetoothMac: get BluetoothManager is null");
            return null;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter != null) {
            return adapter.getAddress();
        }
        Log.e(a, "getBluetoothMac: BluetoothAdapter is null");
        return null;
    }

    public static String getBluetoothMacAddress() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Field declaredField = defaultAdapter.getClass().getDeclaredField("mService");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(defaultAdapter);
            return obj != null ? (String) obj.getClass().getMethod("getAddress", new Class[0]).invoke(obj, new Object[0]) : "";
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException unused) {
            return "";
        }
    }
}
