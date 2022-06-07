package com.ktcp.voicedevice.device;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.ktcp.aiagent.device.aidl.IDeviceAudioEventAidl;
import com.ktcp.aiagent.device.aidl.IDeviceCallbackAidl;
import com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TencentVideoAppCommandProcessor;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VoiceDeviceService extends Service {
    public static final String ASR_TEXT = "asrText";
    public static final String TENCENT_VOICE_ACTION = "com.ktcp.voicedevice.ACTION_BIND_SERVICE";
    private a a = new a();
    private volatile IDeviceAudioEventAidl b;
    private IDeviceCallbackAidl c;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        L.video.i("%s onDestroy", "VoiceDeviceService");
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        L.video.i("onStartCommand: %s", intent);
        a(intent);
        return 1;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        L.video.i("onBind: %s", intent);
        return this.a;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        L.video.i("onUnbind: %s", intent);
        if (TENCENT_VOICE_ACTION.equals(intent.getAction())) {
            this.b = null;
            this.c = null;
            stopSelf();
        }
        return super.onUnbind(intent);
    }

    /* loaded from: classes2.dex */
    private class a extends IDeviceVoiceServiceAidl.Stub {
        private a() {
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void requestAudio(int i, int i2, int i3, IDeviceAudioEventAidl iDeviceAudioEventAidl) throws RemoteException {
            L.video.i("call requestAudio sampleRate : %d, channel : %d, encoding : %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
            VoiceDeviceService.this.b = iDeviceAudioEventAidl;
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void abandonAudio() throws RemoteException {
            L.video.i("call abandonAudio");
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public int readAudioData(byte[] bArr) throws RemoteException {
            L.video.i("call readAudioData");
            return 0;
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void onAsrResult(String str) throws RemoteException {
            L.video.i("call onAsrResult result： %s", str);
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void playTTS(String str) throws RemoteException {
            L.video.i("call playTTS json:%s", str);
            try {
                SpeechManager.getInstance().ttsRequest(new JSONObject(str).optString("text"), true);
            } catch (JSONException e) {
                Logger logger = L.video;
                logger.e("tencent video " + str, e);
            }
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void openLight() throws RemoteException {
            L.video.i("call openLight");
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void closeLight() throws RemoteException {
            L.video.i("call closeLight");
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void setCallback(IDeviceCallbackAidl iDeviceCallbackAidl) throws RemoteException {
            L.video.i("call setCallback");
            VoiceDeviceService.this.c = iDeviceCallbackAidl;
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public String getAuthConfig() throws RemoteException {
            L.video.i("call getAuthConfig");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("appId", "40011");
                jSONObject.put("secretKey", "aa5ec4d7d22bafb8f140301d43e03a67");
            } catch (JSONException e) {
                L.video.e("tencent video", e);
            }
            return jSONObject.toString();
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public String call(String str, String str2) throws RemoteException {
            L.video.i("call method: %s, params: %s", str, str2);
            return "";
        }

        @Override // com.ktcp.aiagent.device.aidl.IDeviceVoiceServiceAidl
        public void callAsrEvent(String str) throws RemoteException {
            L.video.i("call callAsrEvent,asrText: %s", str);
            if (!TextUtils.isEmpty(str)) {
                VoiceDeviceService voiceDeviceService = VoiceDeviceService.this;
                voiceDeviceService.a(3, voiceDeviceService.a(str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ASR_TEXT, str);
        } catch (JSONException e) {
            L.video.e("VoiceDeviceService,getAsrTextJsonParams", e);
        }
        return jSONObject.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        if (this.b != null) {
            try {
                L.video.i("callAudioEvent event:%d,jsonParams:%s", Integer.valueOf(i), str);
                this.b.onAudioEvent(i, str);
            } catch (RemoteException e) {
                L.video.e("VoiceDeviceService,callAudioEvent", e);
                b(str);
            }
        } else {
            L.video.i("audioEventInterface is null,launchTencentVideo,jsonParams:%s", str);
            b(str);
        }
    }

    private void b(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            Logger logger = L.video;
            logger.e("tencent video" + str, e);
            jSONObject = null;
        }
        String optString = jSONObject.optString(ASR_TEXT);
        if (optString != null) {
            TencentVideoAppCommandProcessor.launchTencentVideo(this, optString);
        }
    }

    private void a(String str, String str2) {
        if (this.c != null) {
            try {
                L.video.i("onCallback method: %s, jsonParams: %s", str, str2);
                this.c.onCallback(str, str2);
            } catch (RemoteException e) {
                L.video.e("VoiceDeviceService", e);
            }
        }
    }

    private void a(Intent intent) {
        if (intent != null && "com.ktcp.voicedevice.ACTION_TEST".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("type");
            if ("command".equals(stringExtra)) {
                String stringExtra2 = intent.getStringExtra("text");
                if (!TextUtils.isEmpty(stringExtra2)) {
                    a(3, a(stringExtra2));
                }
            } else if ("callback".equals(stringExtra)) {
                String stringExtra3 = intent.getStringExtra(SchemaActivity.KEY_METHOD);
                String stringExtra4 = intent.getStringExtra("jsonParams");
                if (!TextUtils.isEmpty(stringExtra3)) {
                    a(stringExtra3, stringExtra4);
                }
            }
        }
    }
}
