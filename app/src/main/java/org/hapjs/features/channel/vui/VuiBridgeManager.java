package org.hapjs.features.channel.vui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.vui.a;
import org.hapjs.features.channel.vui.data.Location;
import org.hapjs.features.channel.vui.data.Message;

/* loaded from: classes5.dex */
public class VuiBridgeManager {
    private a b;
    private IWorker d;
    private Context e;
    private int a = 0;
    private Map<String, Map<Long, b>> c = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        static VuiBridgeManager a = new VuiBridgeManager();
    }

    public static VuiBridgeManager getInstance() {
        return a.a;
    }

    public void onInit(Context context) {
        this.e = context;
        this.b = new a(this);
        this.b.a(context);
    }

    public void setWorker(IWorker iWorker) {
        this.d = iWorker;
    }

    public boolean isChannelConnected(String str) {
        return this.c.get(str) != null;
    }

    public void sendRawData(String str, String str2, String str3, final VuiBridgeCallback<? super JsonNode> vuiBridgeCallback) {
        Message b = b("send_raw_data", str2);
        Map<Long, b> map = this.c.get(str);
        if (map != null) {
            b bVar = new b() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.1
                @Override // org.hapjs.features.channel.vui.b
                public void a() {
                }

                @Override // org.hapjs.features.channel.vui.b
                public void b() {
                    VuiBridgeCallback vuiBridgeCallback2 = vuiBridgeCallback;
                    if (vuiBridgeCallback2 != null) {
                        vuiBridgeCallback2.onFail(2001, VuiErrorCode.getErrorMessage(2001));
                    }
                }

                @Override // org.hapjs.features.channel.vui.b
                public void a(JsonNode jsonNode) {
                    VuiBridgeCallback vuiBridgeCallback2 = vuiBridgeCallback;
                    if (vuiBridgeCallback2 != null) {
                        vuiBridgeCallback2.onSuccess(jsonNode);
                    }
                }

                @Override // org.hapjs.features.channel.vui.b
                public void a(int i, String str4) {
                    VuiBridgeCallback vuiBridgeCallback2 = vuiBridgeCallback;
                    if (vuiBridgeCallback2 != null) {
                        vuiBridgeCallback2.onFail(i, str4);
                    }
                }
            };
            map.put(Long.valueOf(b.getSeq()), bVar);
            this.b.a(str, a(b), bVar);
            return;
        }
        Log.d("VUI:VuiBridgeManager", "channel not exist, go deeplink");
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("hap://app/");
            stringBuffer.append(str);
            stringBuffer.append(str3);
            stringBuffer.append("?data=");
            stringBuffer.append(URLEncoder.encode(b.toString(), "UTF-8"));
            Intent parseUri = Intent.parseUri(stringBuffer.toString(), 1);
            parseUri.addFlags(268435456);
            if (this.e != null) {
                this.e.startActivity(parseUri);
            }
        } catch (ActivityNotFoundException | UnsupportedEncodingException | URISyntaxException e) {
            Log.e("VUI:VuiBridgeManager", "sendRawData", e);
        }
    }

    private Message b(String str, String str2) {
        Message message = new Message();
        message.setSeq(a());
        message.setAction(str);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode createObjectNode = objectMapper.createObjectNode();
        try {
            createObjectNode.set("raw_data", objectMapper.readTree(str2));
            message.setPayload(createObjectNode);
        } catch (JsonProcessingException e) {
            Log.e("VUI:VuiBridgeManager", "createRawMessage", e);
        }
        return message;
    }

    public void a(String str, String str2, final a.AbstractC0385a aVar) {
        final Message c = c(str2);
        if (c.getResp() > 0) {
            long resp = c.getResp();
            Map<Long, b> map = this.c.get(str);
            if (map != null) {
                b bVar = map.get(Long.valueOf(resp));
                if (bVar == null) {
                    Log.d("VUI:VuiBridgeManager", "vuiBridgeInnerCallback null");
                } else if (c.getCode() > 0) {
                    bVar.a(c.getCode(), c.getMessage());
                } else {
                    bVar.a((JsonNode) c.getPayload());
                }
            } else {
                Log.d("VUI:VuiBridgeManager", "seqCallbackMap null");
            }
        } else if (this.d != null) {
            String action = c.getAction();
            char c2 = 65535;
            switch (action.hashCode()) {
                case -934426579:
                    if (action.equals(Commands.RESUME)) {
                        c2 = 7;
                        break;
                    }
                    break;
                case -546151792:
                    if (action.equals("unregister_location_update")) {
                        c2 = '\t';
                        break;
                    }
                    break;
                case -504314158:
                    if (action.equals("open_mic")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -482150528:
                    if (action.equals("close_mic")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 115187:
                    if (action.equals(VoicePrintRegisterController.PARAMS_TTS)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 72642707:
                    if (action.equals("location_update")) {
                        c2 = '\b';
                        break;
                    }
                    break;
                case 106440182:
                    if (action.equals("pause")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 107944136:
                    if (action.equals("query")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 951351530:
                    if (action.equals("connect")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1715152950:
                    if (action.equals("stop_tts")) {
                        c2 = 2;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.d.connect(((JsonNode) c.getPayload()).findPath("version").asInt(), new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.6
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 1:
                    this.d.tts(((JsonNode) c.getPayload()).findPath("text").asText(), new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.7
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 2:
                    this.d.stopTts(new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.8
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 3:
                    this.d.openMic(new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.9
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 4:
                    this.d.closeMic(new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.10
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 5:
                    this.d.query(((JsonNode) c.getPayload()).findPath("text").asText(), new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.11
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 6:
                    this.d.pause(str, new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.2
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case 7:
                    this.d.resume(str, new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.3
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case '\b':
                    this.d.registerLocationUpdate(c.getSeq(), new VuiCallback<Location>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.4
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Location> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                case '\t':
                    this.d.unregisterLocationUpdate(((JsonNode) c.getPayload()).findPath("subscribed_seq_id").asLong(), new VuiCallback<Boolean>() { // from class: org.hapjs.features.channel.vui.VuiBridgeManager.5
                        @Override // org.hapjs.features.channel.vui.VuiCallback
                        public void callback(VuiResult<Boolean> vuiResult) {
                            VuiBridgeManager.this.a(aVar, c, vuiResult);
                        }
                    });
                    return;
                default:
                    return;
            }
        } else {
            Log.d("VUI:VuiBridgeManager", "mWorker null");
        }
    }

    public <T> void a(a.AbstractC0385a aVar, Message message, VuiResult<T> vuiResult) {
        if (vuiResult.getCode() > 0) {
            Message message2 = new Message();
            message2.setSeq(a());
            message2.setResp(message.getSeq());
            message2.setCode(vuiResult.getCode());
            message2.setMode(Message.MODE_DEFAULT);
            message2.setMessage(VuiErrorCode.getErrorMessage(vuiResult.getCode()));
            aVar.a(a(message2));
            return;
        }
        Message message3 = new Message();
        message3.setSeq(a());
        message3.setResp(message.getSeq());
        message3.setCode(vuiResult.getCode());
        message3.setMode(Message.MODE_DEFAULT);
        if (vuiResult.getData() != null) {
            message3.setPayload(vuiResult.getData());
        }
        aVar.a(a(message3));
    }

    public void a(String str) {
        this.c.put(str, new ConcurrentHashMap());
    }

    private synchronized int a() {
        this.a++;
        return this.a;
    }

    public void b(String str) {
        this.c.remove(str);
    }

    private Message c(String str) {
        JsonProcessingException e;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Message message = null;
        try {
            TypeFactory.defaultInstance();
            message = (Message) objectMapper.readValue(str, Message.class);
            try {
                message.setPayload((JsonNode) objectMapper.convertValue(message.getPayload(), JsonNode.class));
            } catch (JsonProcessingException e2) {
                e = e2;
                Log.e("VUI:VuiBridgeManager", "parse: ", e);
                return message;
            }
        } catch (JsonProcessingException e3) {
            e = e3;
        }
        return message;
    }

    public boolean a(String str, String str2) {
        return this.d.onAccept(str, str2);
    }

    private ChannelMessage a(Message message) {
        Bundle bundle = new Bundle();
        bundle.putInt("code", 0);
        bundle.putString("data", message.toString());
        return ChannelMessage.parse(bundle);
    }
}
