package com.xiaomi.micolauncher.module.music.utils;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.stat.StatKeyGenerateName;
import com.xiaomi.micolauncher.common.stat.StatUtils;

/* loaded from: classes3.dex */
public class MusicStatHelper {

    /* loaded from: classes3.dex */
    public enum MusicVal {
        MUSIC_PRIVATE,
        MUSIC_LIKE,
        MUSIC_RECENT,
        MUSIC_COLLECTION
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum a implements StatKeyGenerateName {
        MUSIC_RECOMMEND_CLICK,
        MUSIC_RECOMMEND_SHOW,
        MUSIC_PLAY_SHOW,
        MUSIC_PLAY_CLICK;

        @Override // com.xiaomi.micolauncher.common.stat.StatKeyGenerateName
        public String lowerCaseName() {
            return name().toLowerCase();
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayerAction implements StatKeyGenerateName {
        RECYCLE,
        PREV,
        NEXT,
        PAUSE,
        PLAY,
        COLLECT,
        CANCEL_COLLECT;

        @Override // com.xiaomi.micolauncher.common.stat.StatKeyGenerateName
        public String lowerCaseName() {
            return name().toLowerCase();
        }
    }

    /* loaded from: classes3.dex */
    public enum PlayerPage implements StatKeyGenerateName {
        PLAYLIST,
        MUSIC_DETAIL,
        LYRIC;

        @Override // com.xiaomi.micolauncher.common.stat.StatKeyGenerateName
        public String lowerCaseName() {
            return name().toLowerCase();
        }
    }

    private static String a(a aVar) {
        return aVar.name().toLowerCase();
    }

    private static String a(MusicVal musicVal) {
        return musicVal.name().toLowerCase();
    }

    public static void recordMusicClick(MusicVal musicVal) {
        StatUtils.recordCountEvent("music", a(a.MUSIC_RECOMMEND_CLICK), "category", a(musicVal));
    }

    public static void recordMusicClick(String str, String str2) {
        if (!ContainerUtil.isEmpty(str)) {
            StatUtils.recordCountEvent("music", a(a.MUSIC_RECOMMEND_CLICK), "category", str, "item", str2);
        }
    }

    public static void recordPatchWallShow() {
        StatUtils.recordCountEvent("music", a(a.MUSIC_RECOMMEND_SHOW));
    }

    public static void recordPlayerClick(StatKeyGenerateName statKeyGenerateName) {
        recordCount(a.MUSIC_PLAY_CLICK, statKeyGenerateName);
    }

    public static void recordPlayerShow(StatKeyGenerateName statKeyGenerateName) {
        recordCount(a.MUSIC_PLAY_SHOW, statKeyGenerateName);
    }

    public static void recordCount(StatKeyGenerateName statKeyGenerateName, StatKeyGenerateName statKeyGenerateName2) {
        StatUtils.recordCountEvent("music", statKeyGenerateName.lowerCaseName(), "category", statKeyGenerateName2.lowerCaseName());
    }
}
