package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.mico.base.utils.SimpleDiskCache;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.MinaResponse;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.Observable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioInfoCache extends MusicCache {
    private static SimpleDiskCache<String, Music.AudioInfo> a = new SimpleDiskCache<>(MicoApplication.getGlobalContext(), new File(Constants.getCacheDirectory(), "audio_info_cache"), 10485760);

    public static void clearDiskCacheIfNecessary() {
        a.clearAll();
        MusicCache.clearDiskCacheIfNecessary();
    }

    public static void addAudioInfo(Music.AudioInfo audioInfo) {
        if (audioInfo != null) {
            a.put(audioInfo.audioID, audioInfo);
            if ("MUSIC".equals(audioInfo.audioType)) {
                MusicCache.addSong(Music.Song.from(audioInfo));
            }
        }
    }

    public static Observable<Music.AudioInfo> getAudioInfo(String str) {
        Music.AudioInfo audioInfo = a.get(str);
        if (audioInfo != null) {
            return Observable.just(audioInfo);
        }
        return ApiHelper.requestAudioInfo(str).doOnNext($$Lambda$3RLlGnpWHOq2Df_JUb2pLQgjc.INSTANCE);
    }

    public static Observable<List<Music.AudioInfo>> getAudioInfoList(List<String> list) {
        return ApiManager.minaService.getAudioInfoV3(Gsons.getGson().toJson(list)).map($$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk.INSTANCE).doOnNext($$Lambda$AudioInfoCache$0Yy9__v6R8vf9xjNsRzWFwCV3aw.INSTANCE);
    }

    public static /* synthetic */ List a(MinaResponse minaResponse) throws Exception {
        return (List) minaResponse.data;
    }

    public static /* synthetic */ void a(List list) throws Exception {
        list.forEach($$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4.INSTANCE);
    }

    public static List<Music.AudioInfo> matchAudioInfo(List<List<Music.AudioInfo>> list, List<String> list2) {
        HashMap hashMap = new HashMap(list.size() * 49);
        if (ContainerUtil.hasData(list)) {
            for (List<Music.AudioInfo> list3 : list) {
                for (Music.AudioInfo audioInfo : list3) {
                    hashMap.put(audioInfo.audioID, audioInfo);
                }
            }
        }
        ArrayList arrayList = new ArrayList(hashMap.size());
        if (ContainerUtil.hasData(hashMap) && ContainerUtil.hasData(list2)) {
            for (String str : list2) {
                arrayList.add(hashMap.get(str));
            }
        }
        return arrayList;
    }
}
