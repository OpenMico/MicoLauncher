package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.micolauncher.skills.voiceprint.model.VoicePrintRegisterModel;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes3.dex */
public class VoicePrintRegisterHandler extends AbstractGsonUbusHandler {
    private static final int ERROR_CODE_MIC_MUTE = -10;
    private static final int ERROR_CODE_REGISTERING = -11;
    private static final String METHOD_GET = "get_status";
    private static final String METHOD_GET_NETWORK_INFO = "get_network_info";
    private static final String METHOD_VOICE_PAYMENT = "register_voice_payment";
    private static final String METHOD_VOICE_PAYMENT_CANCEL = "register_voice_payment_cancel";
    private static final String METHOD_VOICE_RECOGNIZE = "register_voice_recognize";
    private static final String METHOD_VOICE_RECOGNIZE_CANCEL = "register_voice_recognize_cancel";
    private static final String VOICE_PRINT = "voice_print";

    /* loaded from: classes3.dex */
    private final class VoicePrintProtocol {
        @SerializedName(VoicePrintRegisterController.PARAMS_NICK_NAME)
        private String nickName;
        @SerializedName("status")
        private String status;

        VoicePrintProtocol(String str, String str2) {
            this.nickName = str2;
            this.status = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String int2ip(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    /* loaded from: classes3.dex */
    private final class NetWorkInfoProtocol {
        @SerializedName("bssid")
        private String bssid;
        @SerializedName("ip")
        private String ip;
        @SerializedName("ssid")
        private String ssid;

        NetWorkInfoProtocol(Context context) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
            if (wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                this.bssid = connectionInfo.getBSSID();
                this.ssid = connectionInfo.getSSID();
                this.ip = VoicePrintRegisterHandler.int2ip(connectionInfo.getIpAddress());
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler, com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return VOICE_PRINT.equals(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler
    public Object handleProto(Context context, String str, String str2, String str3, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        char c;
        switch (str2.hashCode()) {
            case -935227555:
                if (str2.equals(METHOD_VOICE_PAYMENT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -656491564:
                if (str2.equals(METHOD_VOICE_RECOGNIZE_CANCEL)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -35296219:
                if (str2.equals(METHOD_VOICE_RECOGNIZE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 254928488:
                if (str2.equals(METHOD_GET_NETWORK_INFO)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1188196603:
                if (str2.equals(METHOD_GET)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1902578076:
                if (str2.equals(METHOD_VOICE_PAYMENT_CANCEL)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new VoicePrintProtocol(VoicePrintRegisterModel.getInstance().getRegisterStatus(), VoicePrintRegisterModel.getInstance().getNickName());
            case 1:
                return new NetWorkInfoProtocol(context);
            case 2:
                if (Mic.getInstance().isMicMute()) {
                    SpeechManager.getInstance().ttsRequest(context.getString(R.string.skill_voice_print_open_mic_then_retry), true);
                    VoicePrintRegisterModel.getInstance().setRegisterFail();
                    if (handleResultCodeOnError == null) {
                        return null;
                    }
                    handleResultCodeOnError.errorCode = -10;
                    return null;
                } else if (!VoicePrintRegisterModel.getInstance().isRegistering()) {
                    VoicePrintRegisterModel.getInstance().startRegisterRecognize(context);
                    return null;
                } else if (handleResultCodeOnError == null) {
                    return null;
                } else {
                    handleResultCodeOnError.errorCode = -11;
                    return null;
                }
            case 3:
                if (Mic.getInstance().isMicMute()) {
                    SpeechManager.getInstance().ttsRequest(context.getString(R.string.skill_voice_print_open_mic_then_retry), true);
                    VoicePrintRegisterModel.getInstance().setRegisterFail();
                    if (handleResultCodeOnError == null) {
                        return null;
                    }
                    handleResultCodeOnError.errorCode = -10;
                    return null;
                } else if (!VoicePrintRegisterModel.getInstance().isRegistering()) {
                    VoicePrintRegisterModel.getInstance().startRegisterPayment(context);
                    return null;
                } else if (handleResultCodeOnError == null) {
                    return null;
                } else {
                    handleResultCodeOnError.errorCode = -11;
                    return null;
                }
            case 4:
                if (Mic.getInstance().isMicMute()) {
                    return null;
                }
                VoicePrintRegisterModel.getInstance().cancelRegisterPayment(context);
                return null;
            case 5:
                if (Mic.getInstance().isMicMute()) {
                    return null;
                }
                VoicePrintRegisterModel.getInstance().cancelRegisterRecognize(context);
                return null;
            default:
                return null;
        }
    }
}
