package com.xiaomi.micolauncher.common.crash;

import android.content.Context;
import android.os.StrictMode;
import com.elvishew.xlog.XLog;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mico.base.utils.Base64Coder;
import com.xiaomi.mico.base.utils.XMStringUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.common.utils.FileSizeUtil;
import com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingCompleteActivity;
import io.realm.exceptions.RealmError;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    private static String CRASH_REPORT_SALT = "846964fe-db28-85b1-fc2b-e5f28501ec7d";
    private static String CRASH_REPORT_URL_PREFIX = "http://log.miwifi.com/log/crash/";
    private static final String KEY_SD_FREEE_SIZE = "sdFreeSize";
    private static final String KEY_SD_TOTAL_SIZE = "sdTotalSize";
    private static final String REALM = "realm";
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    public AppCrashHandler(Context context) {
        this.context = context;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        handleUncaughtException(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.defaultHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    private void handleUncaughtException(Throwable th) {
        String encodeBytes = Base64Coder.encodeBytes(getRandomBytes(20), 16);
        String valueOf = String.valueOf(System.currentTimeMillis() / 1000);
        AppCrashPayload appCrashPayload = new AppCrashPayload();
        appCrashPayload.exception = th.getClass().getName();
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        appCrashPayload.callstack = stringWriter.toString();
        String encodeBytes2 = Base64Coder.encodeBytes(Gsons.getGson().toJson(appCrashPayload).getBytes(StandardCharsets.UTF_8), 16);
        XLog.e("Crash Exception: %s", appCrashPayload.callstack);
        String str = "";
        if (TokenManager.getInstance().hasValidAccount()) {
            str = TokenManager.getInstance().getUserId();
        }
        reportRealmError(appCrashPayload);
        String sHA1Digest = XMStringUtils.getSHA1Digest(str);
        String str2 = null;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("suffix", "text");
            jSONObject.put("version", SystemSetting.getRomVersion());
            jSONObject.put(IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO, new JSONObject(Gsons.getGson().toJson(DeviceInfo.get(this.context))));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, "");
            jSONObject2.put("serialNumber", Constants.getSn());
            jSONObject2.put(PlaybackTrackHelper.ROM_TAG, SystemSetting.getRomVersion());
            jSONObject2.put("hardware", Hardware.getBuildModel());
            jSONObject.put("routerInfo", jSONObject2);
            String jSONObject3 = jSONObject.toString();
            XLog.i(jSONObject3);
            str2 = Base64Coder.encodeBytes(jSONObject3.getBytes(StandardCharsets.UTF_8), 16);
        } catch (JSONException e) {
            XLog.e(e);
        }
        String encodeBytes3 = Base64Coder.encodeBytes(md5Digest(("_n=" + encodeBytes + "&_t=" + valueOf + "&extra_data=" + str2 + "&id=" + sHA1Digest + "&payload=" + encodeBytes2 + CRASH_REPORT_SALT).getBytes(StandardCharsets.UTF_8)), 16);
        try {
            if (DebugHelper.isDebugVersion()) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            }
            new OkHttpClient().newCall(new Request.Builder().url(getCrashReportUrl(this.context)).post(new FormBody.Builder().add(SchemaActivity.KEY_PAYLOAD, encodeBytes2).add("_n", encodeBytes).add("_t", valueOf).add("extra_data", str2).add("id", sHA1Digest).add("_s", encodeBytes3).build()).build()).enqueue(new Callback() { // from class: com.xiaomi.micolauncher.common.crash.AppCrashHandler.1
                @Override // okhttp3.Callback
                public void onFailure(@NotNull Call call, @NotNull IOException iOException) {
                    XLog.e("upload crash failed", iOException);
                }

                @Override // okhttp3.Callback
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        XLog.i("upload crash log success");
                    }
                    response.close();
                }
            });
            ActivityLifeCycleManager.getInstance().destroyActivities();
        } catch (Exception e2) {
            XLog.e(e2);
        }
        if (!DebugHelper.isDebugVersion()) {
            UMCrashManager.reportCrash(this.context, th);
        }
        ProcessPhoenix.triggerRebirth(this.context);
    }

    private void reportRealmError(AppCrashPayload appCrashPayload) {
        if (appCrashPayload.exception.equals(RealmError.class.getName())) {
            File[] listFiles = this.context.getFilesDir().listFiles();
            if (!ContainerUtil.isEmpty(listFiles)) {
                HashMap hashMap = new HashMap();
                for (File file : listFiles) {
                    String name = file.getName();
                    if (name.contains(REALM)) {
                        hashMap.put(name, String.valueOf(FileSizeUtil.toUnitOf1K(FileSizeUtil.getFileSize(file))));
                    }
                }
                hashMap.put(KEY_SD_TOTAL_SIZE, String.valueOf(FileSizeUtil.toUnitOf16M(FileSizeUtil.getSdCardTotalSize())));
                hashMap.put(KEY_SD_FREEE_SIZE, String.valueOf(FileSizeUtil.toUnitOf16M(FileSizeUtil.getSdCardFreeSize())));
            }
        }
    }

    private static byte[] getRandomBytes(int i) {
        byte[] bArr = new byte[i];
        Random random = new Random();
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) random.nextInt(256);
        }
        return bArr;
    }

    private static byte[] md5Digest(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCrashReportUrl(Context context) {
        return CRASH_REPORT_URL_PREFIX + Hardware.current(context).getName();
    }

    /* loaded from: classes3.dex */
    public class AppCrashPayload {
        public String callstack;
        public String exception;

        public AppCrashPayload() {
        }
    }
}
