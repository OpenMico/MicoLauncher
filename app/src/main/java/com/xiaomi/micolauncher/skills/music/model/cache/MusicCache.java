package com.xiaomi.micolauncher.skills.music.model.cache;

import android.text.TextUtils;
import com.xiaomi.mico.base.utils.SimpleDiskCache;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicCache {
    public static final int AUDIO_INFO_API_MAX_SIZE = 49;
    private static SimpleDiskCache<String, Music.Song> a = new SimpleDiskCache<>(MicoApplication.getGlobalContext(), new File(Constants.getCacheDirectory(), "song_audio"), 10485760);

    /* JADX INFO: Access modifiers changed from: protected */
    public static void addSong(Music.Song song) {
        if (!TextUtils.isEmpty(song.audioID)) {
            a.put(song.audioID, song);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void clearDiskCacheIfNecessary() {
        a.clearAll();
    }

    public static Observable<List<Music.Song>> getSongs(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList();
        for (String str : list) {
            Music.Song song = a.get(str);
            if (song != null) {
                arrayList.add(song);
            } else {
                arrayList2.add(str);
            }
        }
        if (arrayList2.isEmpty()) {
            return Observable.just(arrayList);
        }
        return ApiManager.minaService.audioInfo(Gsons.getGson().toJson(list)).doOnNext($$Lambda$MusicCache$77lFmg6K2xNyS4CCCRn2ZmR_Vig.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list) throws Exception {
        list.forEach($$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4.INSTANCE);
    }
}
