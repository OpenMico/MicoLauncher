package com.xiaomi.micolauncher.skills.alarm.model;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class AlarmMusicController {
    List<AudioPlayer.AudioItemV1> a;
    private BaseExoPlayer b;
    private volatile boolean c;
    private int d;
    private int e;
    private boolean f;
    private boolean g;
    private MusicControllerListener h;
    private Disposable i;
    private final int j = 3;

    /* loaded from: classes3.dex */
    public interface MusicControllerListener {
        void onMusicRingtoneError();

        void onResourceChange(String str);

        void onStartMusicRingtone();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AlarmMusicController(BaseExoPlayer baseExoPlayer, MusicControllerListener musicControllerListener) {
        this.b = baseExoPlayer;
        this.h = musicControllerListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(BaseExoPlayer baseExoPlayer) {
        this.b = baseExoPlayer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(List<Instruction> list) {
        Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.AudioPlayer.Play);
        if (intention != null) {
            return a(intention);
        }
        L.alarm.i("AudioPlayer Play instruction is empty");
        m();
        return false;
    }

    private boolean a(Instruction instruction) {
        List<AudioPlayer.AudioItemV1> audioItems = ((AudioPlayer.Play) instruction.getPayload()).getAudioItems();
        if (!ContainerUtil.hasData(audioItems)) {
            return false;
        }
        this.a = audioItems;
        i();
        return true;
    }

    public void a() {
        this.c = true;
        a(false);
        Disposable disposable = this.i;
        if (disposable != null && !disposable.isDisposed()) {
            this.i.dispose();
        }
        List<AudioPlayer.AudioItemV1> list = this.a;
        if (list != null) {
            list.clear();
            this.a = null;
        }
        LocalPlayerManager.getInstance().setAlarmAudioItem(null);
    }

    public void b() {
        a();
        this.c = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return ContainerUtil.hasData(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e() {
        this.d++;
        a(this.d);
    }

    private void i() {
        this.d = 0;
        this.f = true;
        this.e = 0;
        a(this.d);
    }

    private void a(int i) {
        if (j()) {
            L.alarm.i("playNext isReleased");
        } else if (ContainerUtil.isEmpty(this.a)) {
            L.alarm.i("musicList  isEmpty");
            m();
        } else if (this.e >= 3) {
            m();
            this.f = false;
            L.alarm.i("music resource is not Directive.Resource or Audio");
        } else if (i >= this.a.size()) {
            L.alarm.i("musicList isEmpty index=%s", Integer.valueOf(i));
            l();
        } else {
            AudioPlayer.AudioItemV1 audioItemV1 = this.a.get(i);
            String str = null;
            if (audioItemV1.getStream() != null) {
                str = audioItemV1.getStream().getUrl();
            }
            if (audioItemV1.getItemId() != null) {
                audioItemV1.getItemId().getAudioId();
            }
            LocalPlayerManager.getInstance().setAlarmAudioItem(audioItemV1);
            if (!TextUtils.isEmpty(str)) {
                a(audioItemV1.getStream());
                return;
            }
            f();
            e();
        }
    }

    @SuppressLint({"CheckResult"})
    private void a(AudioPlayer.Stream stream) {
        StreamHelper.buildUrl(stream).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmMusicController$csb3TkooFjflcVtsOsgBgibl4aU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AlarmMusicController.this.a((String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$AlarmMusicController$99f7RxAigAZH3Ww8tEfbngscaK8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AlarmMusicController.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        k();
        L.player.i("doAuth", th);
    }

    private boolean j() {
        return this.c;
    }

    private void k() {
        if (this.e == 0) {
            this.f = false;
            m();
            return;
        }
        f();
        e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f() {
        this.e++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        L.alarm.i("onPlayResource url=%s", str);
        if (j()) {
            L.alarm.i("onPlayResource isReleased");
        } else if (this.b == null) {
            L.alarm.i("ringtonePlayer is null");
            m();
        } else if (!TextUtils.isEmpty(str)) {
            this.b.setDataSource(str);
        } else {
            f();
            e();
        }
    }

    private void l() {
        i();
    }

    private void m() {
        L.alarm.i("AlarmMusicController playDefaultAlarm");
        a(true);
        MusicControllerListener musicControllerListener = this.h;
        if (musicControllerListener != null) {
            musicControllerListener.onMusicRingtoneError();
        }
    }

    public int g() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean h() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.g = z;
    }
}
