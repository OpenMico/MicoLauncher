package com.xiaomi.micolauncher.common.speech.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes3.dex */
public class SpeechEventHandler extends Handler {
    protected static final int MSG_ASR_ERROR = 0;
    protected static final int MSG_ASR_EVENT = 1;
    public static final int MSG_ASR_FINAL_RESULT = 5;
    public static final int MSG_ASR_FINAL_RESULT_FOR_LOG = 7;
    public static final int MSG_ASR_PARTIAL_RESULT = 4;
    public static final int MSG_ASR_PARTIAL_RESULT_FOR_LOG = 6;
    protected static final int MSG_ASR_SPEECH_END = 2;
    protected static final int MSG_ASR_SPEECH_START = 3;
    public static final int MSG_ASR_STOP_CAPTURE = 9;
    public static final int MSG_ASR_TRUNCATION_NOTIFICATION = 8;
    public static final int MSG_DIALOG_CLOSE = 3001;
    public static final int MSG_DIALOG_OPEN = 3000;
    public static final int MSG_INSTRUCTION_CMD = 4000;
    public static final int MSG_INSTRUCTION_DISABLE_VOICE_WAKEUP = 4004;
    protected static final int MSG_INSTRUCTION_OP = 4001;
    public static final int MSG_INSTRUCTION_OPEN_MIC = 4002;
    public static final int MSG_INSTRUCTION_SCENE_ENTER = 4003;
    public static final int MSG_INTERNAL_SHORT_AUDIO_FINISH = 6001;
    public static final int MSG_INTERNAL_VPM_ENTER_SLEEP = 6002;
    public static final int MSG_INTERNAL_VPM_SET_WAKEUP = 6000;
    protected static final int MSG_NLP_ERROR = 2000;
    public static final int MSG_NLP_REQUEST = 2005;
    protected static final int MSG_NLP_REQ_ERROR = 2002;
    protected static final int MSG_NLP_RESULT = 2001;
    public static final int MSG_NLP_RES_END = 2004;
    public static final int MSG_NLP_RES_START = 2003;
    public static final int MSG_TTS_CONTENT = 1007;
    public static final int MSG_TTS_ERROR = 1000;
    public static final int MSG_TTS_PCM_DATA = 1005;
    protected static final int MSG_TTS_PLAY_ERROR = 1006;
    public static final int MSG_TTS_PLAY_FINISH = 1002;
    public static final int MSG_TTS_PLAY_NO_TTS = 1008;
    public static final int MSG_TTS_PLAY_START = 1001;
    public static final int MSG_TTS_PLAY_URL = 1003;
    public static final int MSG_TTS_REQUEST = 1004;
    public static final int MSG_UI_SHOW = 7000;
    protected static final int MSG_VPM_ASR_CANCEL = 5004;
    public static final int MSG_VPM_ASR_END = 5003;
    protected static final int MSG_VPM_ASR_START = 5002;
    protected static final int MSG_VPM_EXIT_SLEEP = 5014;
    protected static final int MSG_VPM_MULTI_WUW = 5013;
    protected static final int MSG_VPM_REFRESH_TOKEN = 5010;
    public static final int MSG_VPM_START = 5000;
    protected static final int MSG_VPM_STAT_POINTS = 5007;
    protected static final int MSG_VPM_VERSION = 5005;
    protected static final int MSG_VPM_WAKEUP = 5000;
    protected static final int MSG_VPM_WAKEUP_CANCEL = 5009;
    protected static final int MSG_VPM_WAKEUP_LOCAL = 5008;
    protected static final int MSG_VPM_WAKEUP_TIMEOUT = 5001;
    protected static final int MSG_VPM_WUW = 5006;

    @Override // android.os.Handler
    public void handleMessage(Message message) {
    }

    public SpeechEventHandler(Looper looper) {
        super(looper);
    }
}
