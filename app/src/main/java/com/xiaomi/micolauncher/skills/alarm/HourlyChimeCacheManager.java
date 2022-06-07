package com.xiaomi.micolauncher.skills.alarm;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.MD5;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.alarm.HourlyChimeCacheManager;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/* loaded from: classes3.dex */
public class HourlyChimeCacheManager {
    private static volatile HourlyChimeCacheManager a;
    private HashMap<String, File> c = new HashMap<>();
    private List<Instruction> b = new ArrayList();

    private HourlyChimeCacheManager() {
        a();
    }

    public static HourlyChimeCacheManager getInstance() {
        if (a == null) {
            synchronized (HourlyChimeCacheManager.class) {
                if (a == null) {
                    a = new HourlyChimeCacheManager();
                }
            }
        }
        return a;
    }

    public List<Instruction> getHourlyChimeCache() {
        return this.b;
    }

    public void setHourlyChimeCacheList(List<Instruction> list) {
        if (ContainerUtil.hasData(list)) {
            this.b.clear();
            this.b.addAll(list);
        }
        for (Instruction instruction : list) {
            if (instruction.getFullName().equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
                speak.getText();
                if (speak.getUrl().isPresent()) {
                    String str = speak.getUrl().get();
                    if (!TextUtils.isEmpty(str)) {
                        a(str);
                    }
                }
            }
        }
    }

    private void a(String str) {
        ApiManager.createOkHttpClientBuilderWithNetworkInterceptor().build().newCall(new Request.Builder().url(str).build()).enqueue(new AnonymousClass1(str));
    }

    /* renamed from: com.xiaomi.micolauncher.skills.alarm.HourlyChimeCacheManager$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements Callback {
        final /* synthetic */ String a;

        AnonymousClass1(String str) {
            HourlyChimeCacheManager.this = r1;
            this.a = str;
        }

        @Override // okhttp3.Callback
        public void onFailure(@NonNull Call call, @NonNull IOException iOException) {
            L.hourlychime.i("%s download fail", this.a);
        }

        @Override // okhttp3.Callback
        public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
            ExecutorService ioThreadPool = ThreadUtil.getIoThreadPool();
            final String str = this.a;
            ioThreadPool.submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.-$$Lambda$HourlyChimeCacheManager$1$gFk5IIHuJkKXx28Vxgk6kEplSM8
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeCacheManager.AnonymousClass1.this.a(str, response);
                }
            });
        }

        public /* synthetic */ void a(String str, @NonNull Response response) {
            Throwable th;
            File file;
            File file2;
            File file3 = null;
            try {
                file = new File(MicoApplication.getApp().getExternalFilesDir("hourly"), MD5.MD5_16(str) + DiskFileUpload.postfix);
                try {
                    try {
                        file2 = new File(MicoApplication.getApp().getExternalFilesDir("hourly"), MD5.MD5_16(str) + ".mp3");
                    } catch (IOException unused) {
                    }
                    try {
                        BufferedSink buffer = Okio.buffer(Okio.sink(file));
                        buffer.writeAll(response.body().source());
                        buffer.close();
                        file.renameTo(file2);
                        HourlyChimeCacheManager.this.c.put(str, file2);
                        L.hourlychime.i("download success");
                    } catch (IOException unused2) {
                        file3 = file2;
                        if (file3 != null) {
                            file3.delete();
                        }
                        L.hourlychime.i("%s download write fail", str);
                        if (file == null) {
                            return;
                        }
                        file.delete();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (file != null) {
                        file.delete();
                    }
                    throw th;
                }
            } catch (IOException unused3) {
                file = null;
            } catch (Throwable th3) {
                th = th3;
                file = null;
            }
            file.delete();
        }
    }

    public void clearMusicCache() {
        this.b.clear();
    }

    public File getLocalAudioFile(String str) {
        return this.c.get(str);
    }

    public void deleteLocalAudioFile(String str) {
        File localAudioFile = getLocalAudioFile(str);
        if (localAudioFile != null) {
            localAudioFile.delete();
            this.c.remove(str);
        }
    }

    private void a() {
        ThreadUtil.getIoThreadPool().submit($$Lambda$HourlyChimeCacheManager$VWax7QD5MqosVMxes0WgdLPi2t0.INSTANCE);
    }

    public static /* synthetic */ void b() {
        for (File file : MicoApplication.getApp().getExternalFilesDir("hourly").listFiles()) {
            file.delete();
        }
    }
}
