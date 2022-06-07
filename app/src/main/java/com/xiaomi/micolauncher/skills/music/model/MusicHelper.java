package com.xiaomi.micolauncher.skills.music.model;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.StringRes;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MusicHelper {
    public static final String MUSIC_SOURCE_QQ = "QQ";
    public static final String QQ_MUSIC_CP = "xiaowei";
    public static final String RADIO = "radio";
    public static final String SOUND = "sound";
    public static final Map<String, ThirdParty.CpInfo> CP_INFO_MAP = new HashMap();
    public static final Map<String, String> CP_TO_NAME_SERVER = new HashMap();
    private static final Map<String, Integer> a = new HashMap<String, Integer>() { // from class: com.xiaomi.micolauncher.skills.music.model.MusicHelper.1
        private static final long serialVersionUID = -5820401825011438334L;

        {
            put(OneTrack.Param.MIUI, Integer.valueOf((int) R.string.music_cp_miui));
            put("miuiv2", Integer.valueOf((int) R.string.music_cp_miui));
            put("xiaoai", Integer.valueOf((int) R.string.music_cp_miui));
            put("mi", Integer.valueOf((int) R.string.music_cp_mi));
            put("kuwo", Integer.valueOf((int) R.string.music_cp_kuwo));
            put("beiwa", Integer.valueOf((int) R.string.music_cp_beiwa));
            put("xiami", Integer.valueOf((int) R.string.music_cp_xiami));
            put("kuke", Integer.valueOf((int) R.string.music_cp_kuke));
            put("leting", Integer.valueOf((int) R.string.music_cp_leting));
            put("tingwen", Integer.valueOf((int) R.string.music_cp_tingwen));
            put("ximalaya", Integer.valueOf((int) R.string.music_cp_ximalaya));
            put("qingting", Integer.valueOf((int) R.string.music_cp_qingting));
            put(ServerSetting.SERVER_CN, Integer.valueOf((int) R.string.music_cp_cn));
            put("dedao", Integer.valueOf((int) R.string.music_cp_dedao));
            put("xinhua", Integer.valueOf((int) R.string.music_cp_xinhua));
            put("qqnews", Integer.valueOf((int) R.string.music_cp_qqnews));
            put("boyakids", Integer.valueOf((int) R.string.music_cp_boyakids));
            put("qqfm", Integer.valueOf((int) R.string.music_cp_qqfm));
            put("kaishu", Integer.valueOf((int) R.string.music_cp_kaishu));
            put("beiwaradio", Integer.valueOf((int) R.string.music_cp_beiwaradio));
            put("ifeng", Integer.valueOf((int) R.string.music_cp_ifeng));
            put("jingyasiting", Integer.valueOf((int) R.string.music_cp_jingyasiting));
            put("haijiaoquan", Integer.valueOf((int) R.string.music_cp_haijiaoquan));
            put("dedaotingshu", Integer.valueOf((int) R.string.music_cp_dedaotingshu));
            put("storysuperman", Integer.valueOf((int) R.string.music_cp_storysuperman));
            put("dedaocolumn", Integer.valueOf((int) R.string.music_cp_dedaocolumn));
            put("gongba", Integer.valueOf((int) R.string.music_cp_gongba));
            put("haobai", Integer.valueOf((int) R.string.music_cp_haobai));
            put("hujiang", Integer.valueOf((int) R.string.music_cp_hujiang));
            put("lanrentingshu", Integer.valueOf((int) R.string.music_cp_lanrentingshu));
            put("dedaocourse", Integer.valueOf((int) R.string.music_cp_dedaocourse));
            put(MusicHelper.QQ_MUSIC_CP, Integer.valueOf((int) R.string.music_cp_xiaowei));
        }
    };
    private static final Map<String, ThirdParty.CpInfo> b = new HashMap<String, ThirdParty.CpInfo>() { // from class: com.xiaomi.micolauncher.skills.music.model.MusicHelper.2
        {
            put("iqiyi", new ThirdParty.CpInfo("爱奇艺", "https://cdn.cnbj1.fds.api.mi-img.com/mico/33bfeb1c-2eff-47fb-a827-247f27f0379f"));
            put(MusicHelper.QQ_MUSIC_CP, new ThirdParty.CpInfo("QQ音乐", "https://cdn.cnbj1.fds.api.mi-img.com/mico/8f923ea8-cced-41de-bf11-527d5a27bb9a"));
        }
    };

    public static boolean canResetLoop(int i) {
        return i == 3 || i == 9 || i == 8 || i == 6 || i == 5 || i == 4;
    }

    public static int getPlayingStationType(int i, int i2) {
        if (i == 4) {
            return 2;
        }
        if (i == 5) {
            return 0;
        }
        if (i == 6) {
            return 1;
        }
        return i2;
    }

    public static boolean isPlayingAlbum(int i) {
        return i == 8;
    }

    public static boolean isPlayingBluetoothOrDlna(int i) {
        return i == 13 || i == 20;
    }

    public static boolean isPlayingMiPlay(int i) {
        return i == 21;
    }

    public static boolean isPlayingRadioStation(int i) {
        return i == 6 || i == 5 || i == 4;
    }

    public static boolean isPlayingSheet(int i) {
        return i == 9;
    }

    public static boolean mediaTypeUnknown(int i) {
        return i == 0;
    }

    public static String getCpName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (CP_TO_NAME_SERVER.containsKey(str)) {
            return CP_TO_NAME_SERVER.get(str);
        }
        for (Map.Entry<String, Integer> entry : a.entrySet()) {
            if (str.equalsIgnoreCase(entry.getKey())) {
                return MicoApplication.getGlobalContext().getResources().getString(entry.getValue().intValue());
            }
        }
        return null;
    }

    public static boolean isPlayRemote(int i) {
        return isPlayingBluetoothOrDlna(i) || isPlayingMiPlay(i);
    }

    public static boolean isPlayingSong(int i) {
        return i == 3 || isPlayingAlbum(i) || isPlayingSheet(i);
    }

    public static Object getTitle(Object obj) {
        if (obj instanceof Music.Song) {
            return ((Music.Song) obj).name;
        }
        if (obj instanceof Music.Album) {
            return ((Music.Album) obj).name;
        }
        if (obj instanceof Music.Artist) {
            return ((Music.Artist) obj).name;
        }
        if (obj instanceof Music.Sheet) {
            return ((Music.Sheet) obj).name;
        }
        if (obj instanceof Music.Station) {
            return ((Music.Station) obj).title;
        }
        if (!(obj instanceof Music.Channel)) {
            return null;
        }
        Music.Channel channel = (Music.Channel) obj;
        if (!channel.isDefault) {
            return channel.name;
        }
        if (SystemSetting.getUserProfile().getMusicSource() == SystemSetting.MusicSuorce.MI) {
            return MicoApplication.getGlobalContext().getString(R.string.music_channel_default_mi);
        }
        return MicoApplication.getGlobalContext().getString(R.string.music_channel_default);
    }

    public static String getCpUrl(String str) {
        ThirdParty.CpInfo cpInfo;
        ThirdParty.CpInfo cpInfo2;
        if (CP_INFO_MAP.isEmpty() && (cpInfo2 = b.get(str)) != null && cpInfo2.icons != null && cpInfo2.icons.size() > 0) {
            return cpInfo2.icons.get(0);
        }
        if (!CP_INFO_MAP.containsKey(str) || (cpInfo = CP_INFO_MAP.get(str)) == null || cpInfo.icons == null || cpInfo.icons.size() <= 0) {
            return null;
        }
        return cpInfo.icons.get(0);
    }

    public static void playMV(Context context, PatchWall.Item item) {
        if (item != null) {
            String cpFromTarget = item.getCpFromTarget();
            String str = item.images.poster.url;
            String str2 = item.id;
            String str3 = item.title;
            String miPlayUrl = item.extra != null ? item.getMiPlayUrl() : "";
            L.player.d("MusicHelper playMV title=%s,cp=%s,mvId=%s,playUrl=%s", str3, cpFromTarget, str2, miPlayUrl);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new VideoItem(1, cpFromTarget, str2, str3, str, miPlayUrl));
            VideoPlayerApi.play(context, arrayList, 0, VideoMode.MV);
        }
    }

    public static void playMV(Context context, String str, StreamInfo streamInfo, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new VideoItem(str, streamInfo, str2, str3));
        VideoPlayerApi.play(context, arrayList, 0, VideoMode.MV);
    }

    public static void playMV(Context context, Remote.Response.TrackData trackData) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new VideoItem(trackData.audioID, trackData.mvStream, trackData.title, trackData.cover));
        VideoPlayerApi.play(context, arrayList, 0, VideoMode.MV);
        PlaybackTrackHelper.postPlayMV(trackData);
    }

    public static void postPlayError(Context context, @StringRes int i) {
        L.player.d("MusicHelper postPlayError");
        if (context != null) {
            a();
            if (!WiFiUtil.isWifiConnected(context)) {
                PromptSoundPlayer.getInstance().play(context, R.raw.network_error);
            } else if (i != -1) {
                SpeechManager.getInstance().ttsRequest(context.getString(i), true);
            } else {
                PromptSoundPlayer.getInstance().play(context, R.raw.music_player_error);
            }
        }
    }

    public static void postNetworkError(Context context) {
        L.player.d("MusicHelper postNetworkError");
        a();
        PromptSoundPlayer.getInstance().play(context, R.raw.network_error);
    }

    public static void postMusicAuthError(Context context, boolean z) {
        L.player.d("MusicHelper postMusicAuthError");
        PlayerApi.clearPlayerStatus();
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnMusicAuthInvalid());
        if (z) {
            PromptSoundPlayer.getInstance().play(context, R.raw.qq_music_not_auth);
        } else {
            PromptSoundPlayer.getInstance().play(context, R.raw.qq_music_auth_expried);
        }
    }

    private static void a() {
        L.player.w("MusicHelper postOnPlayError will clearPlayerStatus");
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
        PlayerApi.clearPlayerStatus();
    }

    public static void postPlayListFinish() {
        PlayerApi.clearPlayerStatus();
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayFinish());
    }

    public static boolean isQQResource(String str) {
        return QQ_MUSIC_CP.equalsIgnoreCase(str);
    }
}
