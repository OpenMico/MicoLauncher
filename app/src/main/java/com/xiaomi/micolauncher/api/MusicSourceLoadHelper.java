package com.xiaomi.micolauncher.api;

import android.content.Context;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class MusicSourceLoadHelper {
    private Disposable a;
    private Context b;

    /* loaded from: classes3.dex */
    public interface MusicSourceLoadCallback {
        void onMusicSourceChanged(String str);
    }

    public MusicSourceLoadHelper(Context context) {
        this.b = context;
    }

    public void loadMusicSource(final MusicSourceLoadCallback musicSourceLoadCallback) {
        this.a = ApiManager.userProfileService.getMusicSource().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$MusicSourceLoadHelper$vwLjIwjh9RbKoqMIvawh1Bp8T8c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicSourceLoadHelper.this.a(musicSourceLoadCallback, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MusicSourceLoadCallback musicSourceLoadCallback, String str) throws Exception {
        if (!str.equals(PreferenceUtils.getSettingString(getContext(), HomePageUtils.MUSIC_SOURCE, "QQ"))) {
            HomePageUtils.setMusicSource(getContext(), str);
            musicSourceLoadCallback.onMusicSourceChanged(str);
        }
    }

    public Context getContext() {
        return this.b;
    }

    public void release() {
        Disposable disposable = this.a;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
