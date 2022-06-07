package com.xiaomi.micolauncher.module.intercom;

import android.app.Application;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class SendIntercom {
    private static volatile SendIntercom a;
    private volatile boolean b;
    private volatile boolean c;
    private List<IntercomSendModel> d;
    private a e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface a {
        void onSendError(IntercomSendModel intercomSendModel, Throwable th);

        void onSendSuccess(IntercomSendModel intercomSendModel);
    }

    public a getSendIntercomListener() {
        return this.e;
    }

    public void setSendIntercomListener(a aVar) {
        this.e = aVar;
    }

    public static SendIntercom getInstance() {
        if (a == null) {
            synchronized (SendIntercom.class) {
                if (a == null) {
                    a = new SendIntercom();
                }
            }
        }
        return a;
    }

    public void initRecordConfig(Application application) {
        if (!this.b) {
            this.b = true;
            RecordManager instance = RecordManager.getInstance();
            RecordConfig recordConfig = new RecordConfig(RecordConfig.RecordFormat.MP3, 16, 2, 16000, 1, new File(application.getExternalCacheDir(), "audio_record").getAbsolutePath() + File.separator);
            boolean isDebugInConfg = DebugHelper.isDebugInConfg();
            L.push.i("initRecordConfig record config RecordFormat=%s, sampleRate=%s, configVersion=%s, debug=%s", recordConfig.getFormat().getExtension(), Integer.valueOf(recordConfig.getSampleRate()), Integer.valueOf(recordConfig.getConfigVersion()), Boolean.valueOf(isDebugInConfg));
            instance.init(application, recordConfig, isDebugInConfg);
        }
    }

    public void sendIntercom(IntercomSendModel intercomSendModel, boolean z) {
        if (this.d == null) {
            this.d = new ArrayList();
        }
        if (z) {
            this.d.add(0, intercomSendModel);
        } else {
            this.d.add(intercomSendModel);
        }
        if (!this.c) {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.d.isEmpty()) {
            this.c = false;
            L.push.d("%s intercom send task is all over complete", "SendIntercom");
            return;
        }
        this.c = true;
        final IntercomSendModel remove = this.d.remove(0);
        final File file = new File(remove.a);
        ApiHelper.uploadIntercomAudio(file, remove.b, remove.c, remove.d, remove.e, remove.f, remove.g).retry(1L).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() { // from class: com.xiaomi.micolauncher.module.intercom.SendIntercom.1
            /* renamed from: a */
            public void accept(ResponseBody responseBody) throws Exception {
                if (SendIntercom.this.e != null) {
                    SendIntercom.this.e.onSendSuccess(remove);
                }
                L.push.d("%s uploadIntercomAudio success, delete record file, and send next", "SendIntercom");
                file.delete();
                SendIntercom.this.a();
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$SendIntercom$8VsD9D5nP8Ecdtx7exYNbH-w9I4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SendIntercom.this.a(remove, file, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(IntercomSendModel intercomSendModel, File file, Throwable th) throws Exception {
        a aVar = this.e;
        if (aVar != null) {
            aVar.onSendError(intercomSendModel, th);
        } else {
            file.delete();
        }
        a();
        L.push.e("send1By1 uploadIntercomAudio error", th);
    }
}
