package com.xiaomi.micolauncher.skills.common;

import android.media.MediaPlayer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.AudioFocusUtil;
import com.xiaomi.micolauncher.common.skill.InstructionConfig;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

@Deprecated
/* loaded from: classes3.dex */
public class AudioPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, AudioFocusUtil.AudioFocusListener {
    private static AudioPlayer b;
    private MediaPlayer a;
    private Queue<a> c = new LinkedBlockingDeque();
    private AudioFocusUtil d = new AudioFocusUtil(MicoApplication.getGlobalContext(), "skills.common.AudioPlayer", true, 2);
    private boolean e = true;
    private a f = null;

    private AudioPlayer() {
        this.d.setListener(this);
    }

    public static AudioPlayer getInstance() {
        if (b == null) {
            b = new AudioPlayer();
        }
        return b;
    }

    private void a(JSONObject jSONObject) {
        String string;
        if (jSONObject != null) {
            L.base.d("[AudioPlayer]: handlePlay");
            if (AnonymousClass1.a[InstructionConfig.AudioPlayer.getBehavior(jSONObject.getString("play_behavior")).ordinal()] == 1) {
                this.c.clear();
            }
            JSONArray jSONArray = jSONObject.getJSONArray("audio_items");
            if (jSONArray != null && jSONArray.size() > 0) {
                for (int i = 0; i < jSONArray.size(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i).getJSONObject(AivsConfig.Tts.AUDIO_TYPE_STREAM);
                    if (!(jSONObject2 == null || (string = jSONObject2.getString("url")) == null || string.isEmpty())) {
                        this.c.add(new a(string, jSONObject2.getIntValue("offset_in_ms"), jSONObject2.getIntValue("duration_in_ms")));
                    }
                }
            }
        }
    }

    public void updatePlayList(Instruction instruction) {
        String str;
        String namespace = instruction.getNamespace();
        String name = instruction.getName();
        if (namespace == null || !namespace.equals("AudioPlayer") || name == null) {
            L.base.d("[AudioPlayer]: updatePlayList: params is error");
            return;
        }
        try {
            str = APIUtils.toJsonString(instruction.getPayload());
        } catch (Exception unused) {
            str = "";
        }
        if (AnonymousClass1.b[InstructionConfig.AudioPlayer.getName(name).ordinal()] == 1 && str != null && !str.isEmpty()) {
            a(JSONObject.parseObject(str));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.skills.common.AudioPlayer$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[InstructionConfig.AudioPlayer.Name.values().length];

        static {
            try {
                b[InstructionConfig.AudioPlayer.Name.Play.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[InstructionConfig.AudioPlayer.Name.unknown.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            a = new int[InstructionConfig.AudioPlayer.Behavior.values().length];
            try {
                a[InstructionConfig.AudioPlayer.Behavior.REPLACE_ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[InstructionConfig.AudioPlayer.Behavior.unknown.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        a();
        return true;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        a();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        MediaPlayer mediaPlayer2;
        if (!this.e && (mediaPlayer2 = this.a) != null) {
            mediaPlayer2.start();
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.AudioFocusUtil.AudioFocusListener
    public void onLoss(boolean z) {
        this.e = true;
        this.c.clear();
        MediaPlayer mediaPlayer = this.a;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        this.a = null;
    }

    private void a() {
        if (this.c.isEmpty()) {
            this.d.abandonAudioFocus();
            this.e = true;
            return;
        }
        this.f = this.c.poll();
        MediaPlayer mediaPlayer = this.a;
        if (mediaPlayer == null) {
            this.a = new MediaPlayer();
            this.a.setAudioStreamType(3);
            this.a.setOnErrorListener(this);
            this.a.setOnCompletionListener(this);
            this.a.setOnPreparedListener(this);
        } else {
            mediaPlayer.reset();
        }
        try {
            Logger logger = L.base;
            logger.d("[AudioPlayer]: url=" + this.f.a());
            this.a.setDataSource(this.f.a());
            this.a.prepareAsync();
        } catch (IOException e) {
            Logger logger2 = L.base;
            logger2.d("[AudioPlayer]: exception: " + e.toString());
            this.c.clear();
            this.d.abandonAudioFocus();
            this.a = null;
        }
    }

    public void doPlay() {
        if (!this.c.isEmpty()) {
            this.d.requestAudioFocus(false);
            this.e = false;
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class a {
        private String b;
        private int c;
        private int d;

        a(String str, int i, int i2) {
            this.b = str;
            this.c = i;
            this.d = i2;
        }

        public String a() {
            return this.b;
        }
    }
}
