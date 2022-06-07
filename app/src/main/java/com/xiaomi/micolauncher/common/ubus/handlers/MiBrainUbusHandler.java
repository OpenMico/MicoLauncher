package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.MiBrainUbusHandler;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class MiBrainUbusHandler extends AbstractGsonUbusHandler {
    private static final int DELAY_AFTER_NLP_QUIT = (int) TimeUnit.SECONDS.toMillis(2);
    private static final int DELAY_STOP_ASYNC = 500;
    private static final String METHOD_AI_SERVICE = "ai_service";
    private static final String METHOD_AI_TTS_REQUEST = "ai_tts_request";
    private static final String METHOD_SHOW_TTS_VENDOR = "tts_vendor_show";
    private static final String METHOD_SWITCH_TTS_VENDOR = "tts_vendor_switch";
    private static final int NO = 0;
    private static final String PATH = "mibrain";
    private static final int YES = 1;
    private Mic micInstance;

    /* loaded from: classes3.dex */
    public static class NlpSkillParams {
        @SerializedName("for_test")
        public boolean forTest;
        @SerializedName("nlp_execute")
        public boolean isExecuteNlp;
        @SerializedName("nlp")
        public boolean isWithNlp;
        @SerializedName(VoicePrintRegisterController.PARAMS_TTS)
        public boolean isWithTts;
        @SerializedName("nlp_text")
        public String queryNlp;
        @SerializedName("uuid")
        public String uuid;
    }

    /* loaded from: classes3.dex */
    public static class TtsRequestParams {
        @SerializedName("show_ui")
        public boolean isShowUi;
        @SerializedName("tts_text")
        public String queryTts;
    }

    /* loaded from: classes3.dex */
    public static class TtsVendorParams {
        @SerializedName("vendor_name")
        String vendorName;
    }

    public MiBrainUbusHandler() {
        super(1);
        this.micInstance = Mic.getInstance();
    }

    @VisibleForTesting
    public MiBrainUbusHandler(Mic mic) {
        super(1);
        this.micInstance = mic;
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler, com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return PATH.equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler
    public Object handleProto(Context context, String str, String str2, String str3, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        final NlpSkillParams nlpSkillParams = (NlpSkillParams) Gsons.getIntAsBoolGson().fromJson(str3, (Class<Object>) NlpSkillParams.class);
        if (!DebugHelper.isAutomatorRun() && this.micInstance.isMicMute() && METHOD_AI_SERVICE.equals(str2) && !nlpSkillParams.forTest) {
            return new MuteResposne();
        }
        if (METHOD_SHOW_TTS_VENDOR.equals(str2) || METHOD_SWITCH_TTS_VENDOR.equals(str2)) {
            return handleTtsVendorMethod(context, str2, str3, handleResultCodeOnError);
        }
        if (METHOD_AI_TTS_REQUEST.equals(str2)) {
            return handleTtsRequestMethod(context, str2, str3, handleResultCodeOnError);
        }
        if (ContainerUtil.isEmpty(str3)) {
            handleResultCodeOnError.errorCode = -3;
            return null;
        }
        new FakePlayer(AudioSource.AUDIO_SOURCE_WAKEUP).start();
        if (OpenPlatformModel.getInstance().isStarted()) {
            L.openplatform.i("open platform was started, quit, query %s", nlpSkillParams.queryNlp);
            OpenPlatformModel.getInstance().quit(OpenPlatformModel.QuitType.NLP_QUIET, true);
            Threads.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.common.ubus.handlers.-$$Lambda$MiBrainUbusHandler$YAtCXukJwAj5EuUagNfzjCQF-M8
                @Override // java.lang.Runnable
                public final void run() {
                    MiBrainUbusHandler.lambda$handleProto$0(MiBrainUbusHandler.NlpSkillParams.this);
                }
            }, DELAY_AFTER_NLP_QUIT);
        } else {
            L.openplatform.i("open platform not started, query %s", nlpSkillParams.queryNlp);
            if (!nlpSkillParams.forTest) {
                SpeechManager.getInstance().setNewSession();
            }
            SpeechManager.getInstance().stopTts();
            SystemClock.sleep(500L);
            if (TextUtils.isEmpty(nlpSkillParams.queryNlp)) {
                L.ubus.e("ai_service.nlpQuery, but queryNlp is null");
            } else if (nlpSkillParams.isWithTts) {
                SpeechManager.getInstance().nlpTtsRequest(nlpSkillParams.queryNlp, nlpSkillParams.uuid, true);
            } else {
                SpeechManager.getInstance().nlpRequest(nlpSkillParams.queryNlp, nlpSkillParams.uuid);
            }
        }
        MuteResposne muteResposne = new MuteResposne();
        muteResposne.isMicMute = Mic.getInstance().isMicMute() ? 1 : 0;
        return muteResposne;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$handleProto$0(NlpSkillParams nlpSkillParams) {
        if (TextUtils.isEmpty(nlpSkillParams.queryNlp)) {
            L.ubus.e("ai_service.nlpQuery, but queryNlp is null");
        } else if (nlpSkillParams.isWithTts) {
            SpeechManager.getInstance().nlpTtsRequest(nlpSkillParams.queryNlp, nlpSkillParams.uuid, true);
        } else {
            SpeechManager.getInstance().nlpRequest(nlpSkillParams.queryNlp, nlpSkillParams.uuid);
        }
    }

    private Object handleTtsVendorMethod(Context context, String str, String str2, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 323077960) {
            if (hashCode == 1253948607 && str.equals(METHOD_SWITCH_TTS_VENDOR)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(METHOD_SHOW_TTS_VENDOR)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                TtsVendorResponse ttsVendorResponse = new TtsVendorResponse(SoundToneManager.ToneType.values().length);
                SoundToneManager.ToneType[] values = SoundToneManager.ToneType.values();
                for (SoundToneManager.ToneType toneType : values) {
                    ttsVendorResponse.addVendor(toneType.name(), toneType.getNameZh(), toneType.name().equals(SoundToneManager.getCurrentTone()));
                }
                return ttsVendorResponse;
            case 1:
                TtsVendorParams ttsVendorParams = (TtsVendorParams) Gsons.getIntAsBoolGson().fromJson(str2, (Class<Object>) TtsVendorParams.class);
                if (ttsVendorParams != null && !TextUtils.isEmpty(ttsVendorParams.vendorName)) {
                    SoundToneManager.onToneTypeChanged(ttsVendorParams.vendorName, context);
                    break;
                } else {
                    handleResultCodeOnError.errorCode = -3;
                    break;
                }
                break;
        }
        return null;
    }

    private Object handleTtsRequestMethod(Context context, String str, String str2, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        if (!METHOD_AI_TTS_REQUEST.equals(str)) {
            return null;
        }
        TtsRequestParams ttsRequestParams = (TtsRequestParams) Gsons.getIntAsBoolGson().fromJson(str2, (Class<Object>) TtsRequestParams.class);
        SpeechManager.getInstance().ttsRequest(ttsRequestParams.queryTts, ttsRequestParams.isShowUi);
        return null;
    }

    /* loaded from: classes3.dex */
    private static class MuteResposne {
        @SerializedName("is_mic_mute")
        int isMicMute;

        private MuteResposne() {
            this.isMicMute = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class TtsVendorResponse {
        @SerializedName("vendor")
        private List<Vendor> vendor;

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public class Vendor {
            @SerializedName(IChannel.EXTRA_ERROR_DESC)
            String desc;
            @SerializedName("name")
            String name;
            @SerializedName("selected")
            boolean selected;

            private Vendor(String str, String str2, boolean z) {
                this.name = str;
                this.desc = str2;
                this.selected = z;
            }
        }

        private TtsVendorResponse(int i) {
            this.vendor = new ArrayList(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addVendor(String str, String str2, boolean z) {
            this.vendor.add(new Vendor(str, str2, z));
        }
    }
}
