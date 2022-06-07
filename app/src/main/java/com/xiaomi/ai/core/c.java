package com.xiaomi.ai.core;

import com.xiaomi.ai.core.AivsConfig;

/* loaded from: classes3.dex */
public class c {
    private AivsConfig a;

    public c(AivsConfig aivsConfig) {
        this.a = aivsConfig;
    }

    public String a() {
        if (this.a.containsKey(AivsConfig.Connection.EXTERNAL_CONNECT_URL)) {
            return this.a.getString(AivsConfig.Connection.EXTERNAL_CONNECT_URL);
        }
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 2 ? "ws://speech-staging.ai.xiaomi.com/speech/v1.0/longaccess" : i == 1 ? "wss://speech-preview.ai.xiaomi.com/speech/v1.0/longaccess" : i == 3 ? "wss://preview4test-access-speech.ai.xiaomi.com/speech/v1.0/longaccess" : this.a.getBoolean(AivsConfig.Connection.ENABLE_ABROAD_URL, false) ? "wss://tw.speech.ai.xiaomi.com/speech/v1.0/longaccess" : "wss://speech.ai.xiaomi.com/speech/v1.0/longaccess";
    }

    public String b() {
        if (this.a.containsKey(AivsConfig.Connection.EXTERNAL_CONNECT_URL)) {
            return this.a.getString(AivsConfig.Connection.EXTERNAL_CONNECT_URL);
        }
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 2 ? "ws://staging.access.speech.ai.xiaomi.com/speech/v1.0/longaccess" : i == 1 ? "ws://preview.access.speech.ai.xiaomi.com/speech/v1.0/longaccess" : i == 3 ? "ws://preview4test.access.speech.ai.xiaomi.com/speech/v1.0/longaccess" : this.a.getBoolean(AivsConfig.Connection.ENABLE_ABROAD_URL, false) ? "ws://tw.access.speech.ai.xiaomi.com/speech/v1.0/longaccess" : "ws://access.speech.ai.xiaomi.com/speech/v1.0/longaccess";
    }

    public String c() {
        if (this.a.containsKey(AivsConfig.Connection.EXTERNAL_CONNECT_URL)) {
            return this.a.getString(AivsConfig.Connection.EXTERNAL_CONNECT_URL);
        }
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 2 ? "xmd://staging.accessxmd.speech.ai.xiaomi.com/speech/v1.0/longaccess" : i == 1 ? "xmd://preview.accessxmd.speech.ai.xiaomi.com/speech/v1.0/longaccess" : i == 3 ? "xmd://preview4test.accessxmd.speech.ai.xiaomi.com/speech/v1.0/longaccess" : this.a.getBoolean(AivsConfig.Connection.ENABLE_ABROAD_URL, false) ? "xmd://tw.accessxmd.speech.ai.xiaomi.com/speech/v1.0/longaccess" : "xmd://accessxmd.speech.ai.xiaomi.com/speech/v1.0/longaccess";
    }

    public String d() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 2 ? "http://open.account.preview.n.xiaomi.net" : (i == 1 || i == 3) ? "https://account.xiaomi.com" : "https://account.xiaomi.com";
    }

    public String e() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 2 ? "http://account-staging.ai.xiaomi.com" : (i == 1 || i == 3) ? "https://account-preview.ai.xiaomi.com" : "https://account.ai.xiaomi.com";
    }

    public String f() {
        return e();
    }

    public String g() {
        return "https://open.account.xiaomi.com/user/profile";
    }

    public String h() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "https://tracker.ai.xiaomi.com/track/perf/v2" : (i == 1 || i == 3) ? "https://tracker-preview.ai.xiaomi.com/track/perf/v2" : "http://tracker-staging.ai.srv/track/perf/v2";
    }

    public String i() {
        return this.a.getInt(AivsConfig.ENV) == 0 ? "https://tracker.ai.xiaomi.com/track/v3" : (this.a.getInt(AivsConfig.ENV) == 1 || this.a.getInt(AivsConfig.ENV) == 3) ? "https://tracker-preview.ai.xiaomi.com/track/v3" : "http://tracker-staging.ai.srv/track/v3";
    }

    public String j() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "https://cloudcontrol.ai.xiaomi.com/aivs/v1.0/config" : (i == 1 || i == 3) ? "https://cloudcontrol-preview.ai.xiaomi.com/aivs/v1.0/config" : "http://cloudcontrol-staging.ai.xiaomi.com/aivs/v1.0/config";
    }

    public String k() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "https://nlp.ai.xiaomi.com/voiceassistant/privacy/v2" : (i == 1 || i == 3) ? "https://nlp-preview.ai.xiaomi.com/voiceassistant/privacy/v2" : "http://nlp-staging.ai.srv/voiceassistant/privacy/v2";
    }

    public String l() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "http://query-suggestion.ai.xiaomi.com/simple/litecrypto/uniform/suggest/" : i == 1 ? "http://query-suggestion-preview.ai.xiaomi.com/simple/litecrypto/uniform/suggest/" : i == 3 ? "http://preview4test-query-suggestion.ai.xiaomi.com/simple/litecrypto/uniform/suggest/" : "http://query-suggestion-staging.ai.xiaomi.com/simple/litecrypto/uniform/suggest/";
    }

    public String m() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "https://speech.ai.xiaomi.com/speech/v1.0/dump" : i == 1 ? "https://speech-preview.ai.xiaomi.com/speech/v1.0/dump" : i == 3 ? "https://preview4test-speech.ai.xiaomi.com/speech/v1.0/dump" : "http://speech-staging.ai.xiaomi.com/speech/v1.0/dump";
    }

    public String n() {
        int i = this.a.getInt(AivsConfig.ENV);
        return i == 0 ? "https://speech.ai.xiaomi.com/speech/v1.0/wensheng/store" : (i == 1 || i == 3) ? "https://speech-preview.ai.xiaomi.com/speech/v1.0/wensheng/store" : "http://speech-staging.ai.srv/speech/v1.0/wensheng/store";
    }
}
