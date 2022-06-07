package com.xiaomi.ai.android.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.umeng.analytics.pro.c;
import com.xiaomi.ai.android.capability.ConnectionCapability;
import com.xiaomi.ai.android.codec.AudioEncoder;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.android.vad.IVad;
import com.xiaomi.ai.android.vad.Vad;
import com.xiaomi.ai.android.vad.Vad2;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.a;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class j extends Handler {
    private d a;
    private AudioEncoder b;
    private IVad c;
    private boolean d;
    private String e;
    private LinkedList<Message> f = new LinkedList<>();

    public j(d dVar, Looper looper) {
        super(looper);
        String str;
        String str2;
        this.a = dVar;
        AivsConfig b = this.a.b();
        this.e = b.getString(AivsConfig.Asr.CODEC, AivsConfig.Asr.CODEC_PCM);
        boolean z = false;
        if (!b.getBoolean(AivsConfig.Asr.ENCODED_BY_CLIENT, false) && (this.e.equals(AivsConfig.Asr.CODEC_BV32_FLOAT) || this.e.equals(AivsConfig.Asr.CODEC_OPUS))) {
            this.b = new AudioEncoder(dVar);
            if (!this.b.a()) {
                this.b.c();
                this.b = null;
            }
        }
        this.d = b.getInt(AivsConfig.Asr.VAD_TYPE) == 1 ? true : z;
        if (this.d) {
            if (b.getBoolean(AivsConfig.Asr.ENABLE_NEW_VAD)) {
                this.c = new Vad2(b.getInt(AivsConfig.Asr.MIN_VOICE), b.getInt(AivsConfig.Asr.MIN_SIL), b.getInt(AivsConfig.Asr.MAX_VOICE), b.getInt(AivsConfig.Asr.MAX_LENGTH_RESET));
                str2 = "UploadHandler";
                str = "use new vad";
            } else {
                this.c = new Vad(600, 200, 4.0f);
                str2 = "UploadHandler";
                str = "use default vad";
            }
            Logger.b(str2, str);
        }
    }

    private void a(byte[] bArr, boolean z) {
        if (bArr == null && this.e.equals(AivsConfig.Asr.CODEC_BV32_FLOAT)) {
            return;
        }
        if (bArr == null && !z) {
            Logger.d("UploadHandler", "postEncodedData: data error");
        } else if (bArr == null || bArr.length <= 32768) {
            int a = this.b.a(bArr, z);
            byte[] b = this.b.b();
            a g = this.a.g();
            if (g == null) {
                Logger.d("UploadHandler", "postEncodedData: engine has been released!");
            } else if (a <= 0 || a > b.length) {
                Logger.a("UploadHandler", "postEncodedData: encodedSize:" + a);
            } else {
                this.a.k().a(this.a.e().c());
                g.postData(b, 0, a);
            }
        } else {
            Logger.d("UploadHandler", "postEncodedData: data oversize, " + bArr.length + ">32768");
        }
    }

    private void d() {
        if (!this.f.isEmpty()) {
            Logger.b("UploadHandler", "flushCacheQueue: queue size=" + this.f.size());
            e();
            Iterator<Message> it = this.f.iterator();
            while (it.hasNext()) {
                it.next().sendToTarget();
            }
            this.f.clear();
        }
    }

    private void e() {
        if (!this.f.isEmpty()) {
            Message element = this.f.element();
            if (element.what == 1 || (element.what == 0 && ((Event) element.obj).getFullName().equals(AIApiConstants.SpeechRecognizer.RecognizeStreamFinished))) {
                while (true) {
                    Message peek = this.f.peek();
                    if (peek == null) {
                        return;
                    }
                    if (peek.what == 1) {
                        this.f.poll();
                    } else if (peek.what == 0) {
                        Event event = (Event) peek.obj;
                        if (event.getFullName().equals(AIApiConstants.SpeechRecognizer.RecognizeStreamFinished)) {
                            Logger.b("UploadHandler", "removeUnfinishedAsr: remove " + event);
                            this.f.poll();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    public void a() {
        synchronized (this) {
            if (!this.f.isEmpty()) {
                Logger.b("UploadHandler", "release: cache queue size=" + this.f.size());
                this.f.clear();
            }
        }
        removeMessages(0);
        removeMessages(1);
        removeMessages(3);
    }

    public void a(Message message) {
        if (message.what == 0) {
            this.a.j().a((Event) message.obj);
        }
        a g = this.a.g();
        if (g == null) {
            Logger.d("UploadHandler", "queue: engine has been released!");
        } else if (!g.isConnected() || !NetworkUtils.a(this.a.a())) {
            if (Logger.getLogLevel() == 3) {
                Logger.a("UploadHandler", "queue: cache " + message);
            } else {
                Logger.b("UploadHandler", "queue: cache " + message.hashCode());
            }
            synchronized (this) {
                this.f.add(message);
            }
        } else {
            synchronized (this) {
                if (!this.f.isEmpty()) {
                    d();
                }
            }
            Logger.a("UploadHandler", "queue: send to target " + message);
            message.sendToTarget();
        }
    }

    public int b() {
        int size;
        synchronized (this) {
            size = this.f.size();
        }
        return size;
    }

    public void c() {
        synchronized (this) {
            d();
            removeMessages(3);
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        ConnectionCapability connectionCapability;
        Logger.a("UploadHandler", "handleMessage:" + message.what);
        a g = this.a.g();
        if (g == null) {
            Logger.d("UploadHandler", "handleMessage: engine has been released!");
            return;
        }
        switch (message.what) {
            case 0:
            case 2:
                Event event = (Event) message.obj;
                if (this.d && AIApiConstants.SpeechRecognizer.Recognize.equals(event.getFullName())) {
                    this.c.release();
                    this.c.init();
                }
                if (this.b != null && this.e.equals(AivsConfig.Asr.CODEC_OPUS) && (AIApiConstants.SpeechRecognizer.RecognizeStreamFinished.equals(event.getFullName()) || AIApiConstants.SpeechWakeup.WakeupStreamFinished.equals(event.getFullName()))) {
                    Logger.a("UploadHandler", "handleMessage: send eofMsg");
                    a(null, true);
                }
                this.a.k().b(event);
                if (AIApiConstants.SpeechRecognizer.RecognizeStreamFinished.equals(event.getFullName()) && (connectionCapability = (ConnectionCapability) this.a.a(ConnectionCapability.class)) != null) {
                    connectionCapability.onLastPackageSend(event.getId());
                }
                g.postEvent(event);
                return;
            case 1:
                Bundle data = message.getData();
                byte[] byteArray = data.getByteArray("data");
                boolean z = data.getBoolean("raw");
                if (this.b != null && !z) {
                    a(byteArray, data.getBoolean(c.aB));
                } else if (byteArray != null) {
                    this.a.k().a(this.a.e().c());
                    g.postData(byteArray);
                    Logger.a("UploadHandler", "post data without encode");
                } else {
                    return;
                }
                IVad iVad = this.c;
                if (iVad != null && byteArray != null && iVad.checkVad(byteArray)) {
                    Logger.b("UploadHandler", "detect vad, stop capture");
                    Instruction buildInstruction = APIUtils.buildInstruction(new SpeechRecognizer.StopCapture());
                    buildInstruction.getHeader().setDialogId(this.a.e().c());
                    this.a.k().c(buildInstruction);
                    this.a.d().obtainMessage(1, buildInstruction).sendToTarget();
                    this.c.release();
                    return;
                }
                return;
            case 3:
                if (!NetworkUtils.a(this.a.a())) {
                    Logger.d("UploadHandler", "UploadHandler time out : network not available , feed error");
                    this.a.a(new AivsError(StdStatuses.NETWORK_DISABLED, "network not available"));
                    a();
                    return;
                }
                this.a.h().b(false);
                return;
            default:
                Logger.d("UploadHandler", "handleMessage: unknown message:" + message.what);
                return;
        }
    }
}
