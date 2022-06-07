package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.ai.android.utils.DeviceUtils;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.log.LoggerHooker;
import com.xiaomi.mico.base.utils.VersionUtils;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public class SpeechEngineHelper {
    public static final boolean WAKE_UP_GUIDE = true;
    private static String a;

    public static int getSpeechEnv() {
        if (ApiConstants.isProductionEnv()) {
            return 0;
        }
        if (ApiConstants.isPrevEnv()) {
            return 1;
        }
        return ApiConstants.isPreview4testEnv() ? 3 : 0;
    }

    public static AivsConfig createConfiguration(Context context, boolean z) {
        AivsConfig aivsConfig = new AivsConfig();
        aivsConfig.putInt(AivsConfig.ENV, getSpeechEnv());
        String aivsConnectUrl = SpeechConfig.getAivsConnectUrl();
        if (aivsConnectUrl != null) {
            L.speech.i("aivs connect url change to %s", aivsConnectUrl);
            aivsConfig.putBoolean(AivsConfig.Connection.ENABLE_HTTP_DNS, false);
            aivsConfig.putString(AivsConfig.Connection.EXTERNAL_CONNECT_URL, aivsConnectUrl);
        }
        if (SpeechConfig.isUsingCloudVad()) {
            aivsConfig.putInt(AivsConfig.Asr.VAD_TYPE, 0);
        } else {
            aivsConfig.putInt(AivsConfig.Asr.VAD_TYPE, 2);
        }
        aivsConfig.putStringList(AivsConfig.Locale.LANGS, a());
        aivsConfig.putString(AivsConfig.Locale.LOCATION, "CN");
        aivsConfig.putEnum(AivsConfig.Locale.REGION, Common.LocaleRegion.MAINLAND);
        aivsConfig.putString(AivsConfig.Asr.CODEC, AivsConfig.Asr.CODEC_OPUS);
        aivsConfig.putString(AivsConfig.Connection.USER_AGENT, getUserAgent());
        aivsConfig.putInt(AivsConfig.Connection.CONNECT_TIMEOUT, 15);
        aivsConfig.putBoolean(AivsConfig.Asr.ENABLE_TIMEOUT, true);
        aivsConfig.putInt(AivsConfig.Asr.RECV_TIMEOUT, 10);
        aivsConfig.putBoolean(AivsConfig.Tts.ENABLE_INTERNAL_PLAYER, false);
        if (z) {
            aivsConfig.putString(AivsConfig.Tts.AUDIO_TYPE, AivsConfig.Tts.AUDIO_TYPE_STREAM);
        } else {
            aivsConfig.putString(AivsConfig.Tts.AUDIO_TYPE, "url");
        }
        aivsConfig.putString(AivsConfig.Tts.AUDIO_VENDOR, SoundToneManager.getToneVendor());
        aivsConfig.putString(AivsConfig.Tts.AUDIO_SPEAKER, SoundToneManager.getToneName());
        MibrainConfig mibrainConfig = MibrainConfig.getMibrainConfig(context);
        aivsConfig.putString(AivsConfig.Auth.CLIENT_ID, mibrainConfig.clientId);
        aivsConfig.putString(AivsConfig.Auth.OAuth.REDIRECT_URL, Constants.XM_APP_REDIRECT);
        aivsConfig.putString(AivsConfig.Auth.OAuth.CLIENT_SECRET, mibrainConfig.clientKey);
        aivsConfig.putBoolean(AivsConfig.Auth.OAuth.ENABLE_UPLOAD_MIOT_DID, true);
        aivsConfig.putInt(AivsConfig.Auth.REQ_TOKEN_MODE, 2);
        aivsConfig.putInt(AivsConfig.Connection.KEEP_ALIVE_TYPE, 0);
        aivsConfig.putBoolean(AivsConfig.Tts.ENABLE_PLAY_DIALOG_ID, true);
        return aivsConfig;
    }

    private static List<String> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("zh-CN");
        return arrayList;
    }

    public static Settings.ClientInfo createClientInfo(Context context) {
        Settings.ClientInfo clientInfo = new Settings.ClientInfo();
        clientInfo.setMiotDid(SystemSetting.getMiotDeviceId());
        clientInfo.setSn(Constants.getSn());
        clientInfo.setCapabilitiesVersion(13L);
        L.speech.d("createClientInfo did=%s sn=%s", SystemSetting.getMiotDeviceId(), Constants.getSn());
        clientInfo.setDeviceId(getDeviceId(context));
        clientInfo.setBindId(SystemSetting.getDeviceID());
        L.speech.d("createClientInfo DeviceId=%s BindId=%s", getDeviceId(context), SystemSetting.getDeviceID());
        clientInfo.setEngineId(UUID.randomUUID().toString());
        return clientInfo;
    }

    public static void setLoggerLevelAndHooker() {
        if (DebugHelper.isDebugVersion()) {
            Logger.setLogLevel(3);
        } else {
            Logger.setLogLevel(2);
        }
        Logger.setLogHooker(new LoggerHooker() { // from class: com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper.1
            @Override // com.xiaomi.ai.log.LoggerHooker
            public void d(String str, String str2) {
            }

            @Override // com.xiaomi.ai.log.LoggerHooker
            public void i(String str, String str2) {
            }

            @Override // com.xiaomi.ai.log.LoggerHooker
            public void w(String str, String str2) {
                L.sdklog.w("%s:%s", str, str2);
            }

            @Override // com.xiaomi.ai.log.LoggerHooker
            public void e(String str, String str2) {
                L.sdklog.e("%s:%s", str, str2);
            }
        });
    }

    @Nullable
    public static String getAiDeviceId() {
        return a;
    }

    public static String getDeviceId(Context context) {
        if (TextUtils.isEmpty(a)) {
            a = DeviceUtils.getDeviceId(context);
        }
        return a;
    }

    public static String getUserAgent() {
        return SystemSetting.getHardWareModel() + "; OS/Android-" + Build.VERSION.RELEASE + " Channel/" + SystemSetting.getRomChannel() + " Rom/" + SystemSetting.getRomVersion() + " SSE/horizon Build/" + (Build.TIME / 1000) + " VERSION/" + SystemSetting.getRomVersion() + " APP/" + VersionUtils.getFormatVersionCode(MicoApplication.getGlobalContext()) + " SN/" + Constants.getSn();
    }
}
