package com.xiaomi.micolauncher.common.speech;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xiaomi.ai.api.Application;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Dialog;
import com.xiaomi.ai.api.Education;
import com.xiaomi.ai.api.Execution;
import com.xiaomi.ai.api.General;
import com.xiaomi.ai.api.Scene;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Context;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.utils.BatteryUtils;
import com.xiaomi.micolauncher.common.utils.SoundToneManager;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.mitv.MiTvManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SpeechContextHelper {
    private static boolean a = false;
    private static AudioPlayer.AudioItemV1 b;
    private static List<Application.AppItem> c;
    public static volatile Scene.SceneType sceneType;

    public static Settings.TtsConfig ttsConfig() {
        Settings.TtsConfig ttsConfig = new Settings.TtsConfig();
        ttsConfig.setVendor(SoundToneManager.getToneVendor());
        ttsConfig.setSpeaker(SoundToneManager.getToneName());
        return ttsConfig;
    }

    public static List<Context> getContexts(boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(tvControllerContext());
        arrayList.add(playbackStateContext());
        arrayList.add(videoPlaybackStateContext());
        arrayList.add(a());
        arrayList.add(b());
        arrayList.add(a(z));
        arrayList.add(c());
        arrayList.add(deviceStateContext());
        arrayList.add(appDetailContext());
        if (OperationManager.getInstance().isPhoneRunning()) {
            arrayList.add(requestControlContext(true, false));
        }
        return arrayList;
    }

    public static Context tvControllerContext() {
        return APIUtils.buildContext(MiTvManager.getInstance().getTVControllerState());
    }

    public static Context requestControlContext(boolean z, boolean z2) {
        Execution.RequestControl requestControl = new Execution.RequestControl();
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(Execution.RequestControlType.TTS);
        }
        if (z2) {
            arrayList.add(Execution.RequestControlType.NLP);
        }
        requestControl.setDisabled(arrayList);
        return APIUtils.buildContext(requestControl);
    }

    private static Context a() {
        Application.State state = new Application.State();
        ArrayList arrayList = new ArrayList(SoundToneManager.ToneType.values().length);
        ArrayList arrayList2 = new ArrayList();
        boolean isChildMode = ChildModeManager.getManager().isChildMode();
        arrayList2.add(new Application.SwitchStatus(Application.SwitchFeature.SMART_KID_PROTECTION, SystemSetting.getKeyChildContentRecommendationEnable()));
        state.setParentalControlMode(isChildMode);
        if (SystemSetting.getKeyMiTvVipStatus()) {
            Application.UserState userState = new Application.UserState();
            Application.UserVipState userVipState = new Application.UserVipState();
            userVipState.setVipType(Application.UserVipType.KIDS);
            userVipState.setIsValid(true);
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(userVipState);
            userState.setUserVipStates(arrayList3);
            state.setUserState(userState);
        }
        state.setCurrentToneId(SoundToneManager.getCurrentTone());
        SoundToneManager.ToneType[] values = SoundToneManager.ToneType.values();
        for (SoundToneManager.ToneType toneType : values) {
            Application.ToneInfo toneInfo = new Application.ToneInfo();
            toneInfo.setId(toneType.name());
            switch (toneType) {
                case Default:
                    toneInfo.setType(Application.ToneType.FEMALE);
                    toneInfo.setName(Application.ToneName.MI_TANG);
                    break;
                case XiaoMi:
                    toneInfo.setType(Application.ToneType.FEMALE);
                    toneInfo.setName(Application.ToneName.MO_LI);
                    break;
                case XiaoMi_M88:
                    toneInfo.setType(Application.ToneType.MALE);
                    toneInfo.setName(Application.ToneName.QING_CONG);
                    break;
                case XiaoMi_xinran:
                    toneInfo.setType(Application.ToneType.CHILD);
                    toneInfo.setName(Application.ToneName.PAO_FU);
                    break;
                default:
            }
            arrayList.add(toneInfo);
        }
        state.setSupportTones(arrayList);
        state.setSwitchStatus(arrayList2);
        Application.CpState cpState = new Application.CpState();
        List<String> supportedCps = ThirdPartyAppProxy.getSupportedCps();
        cpState.setCpLevel(Application.CpLevel.AGGREGATE);
        cpState.setCps(supportedCps);
        state.setCpState(cpState);
        return APIUtils.buildContext(state);
    }

    public static Context appDetailContext() {
        Application.AppDetailV1 appDetailV1 = new Application.AppDetailV1();
        List<PackageInfo> installedApps = ActivityUtil.getInstalledApps(MicoApplication.getApp());
        List<Application.AppItem> list = c;
        if (list == null || list.size() == 0) {
            c = new ArrayList();
            for (PackageInfo packageInfo : installedApps) {
                final Application.AppItem appItem = new Application.AppItem(packageInfo.packageName, packageInfo.versionCode);
                appItem.setVersionName(packageInfo.versionName);
                Optional.ofNullable(ApplicationInfo.getCategoryTitle(MicoApplication.getApp(), packageInfo.applicationInfo.category)).ifPresent(new Consumer() { // from class: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechContextHelper$X8g1O-ncY5Ip88J1YICXDbiVVY8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SpeechContextHelper.a(Application.AppItem.this, (CharSequence) obj);
                    }
                });
                c.add(appItem);
            }
        }
        appDetailV1.setAvailableApps(c);
        List<String> recentUsedApps = ActivityUtil.getRecentUsedApps(MicoApplication.getApp());
        appDetailV1.setRecentlyUsedApps(recentUsedApps);
        appDetailV1.setForegroundApp(recentUsedApps.get(recentUsedApps.size() - 1));
        return APIUtils.buildContext(appDetailV1);
    }

    public static /* synthetic */ void a(Application.AppItem appItem, CharSequence charSequence) {
        appItem.setCategory(charSequence.toString());
    }

    private static Context b() {
        Application.ResourceInfo resourceInfo = new Application.ResourceInfo();
        resourceInfo.setNeedPaidResource(true);
        return APIUtils.buildContext(resourceInfo);
    }

    private static Context a(boolean z) {
        Dialog.DialogState dialogState = new Dialog.DialogState();
        dialogState.setContinuousDialog(SpeechManager.getInstance().isDialogMode() && z);
        dialogState.setIsInterruptable(false);
        if (sceneType != null) {
            dialogState.setScene(sceneType);
        }
        return APIUtils.buildContext(dialogState);
    }

    private static Context c() {
        General.RequestState requestState = new General.RequestState();
        requestState.setIsInitWakeup(SpeechManager.getInstance().isVoiceWakeup());
        requestState.setTtsVendor(SoundToneManager.getToneVendor());
        requestState.setSpeaker(SoundToneManager.getToneName());
        return APIUtils.buildContext(requestState);
    }

    public static Context renewSessionContext() {
        return APIUtils.buildContext(new General.RenewSession());
    }

    public static Context deviceStateContext() {
        Sys.DeviceState deviceState = new Sys.DeviceState();
        Sys.DeviceScreen deviceScreen = new Sys.DeviceScreen();
        deviceScreen.setScreenLock(ChildModeManager.getManager().isScreenLock());
        deviceState.setScreen(deviceScreen);
        Sys.DevicePowerInfo devicePowerInfo = new Sys.DevicePowerInfo();
        devicePowerInfo.setHasBattery(BatteryUtils.isBatteryPresent(MicoApplication.getGlobalContext()));
        devicePowerInfo.setIsConnectPower(BatteryUtils.isPowerConnected(MicoApplication.getGlobalContext()));
        deviceState.setPowerInfo(devicePowerInfo);
        return APIUtils.buildContext(deviceState);
    }

    public static Context videoPlaybackStateContext() {
        return APIUtils.buildContext(videoPlaybackState());
    }

    public static VideoPlayer.VideoPlaybackState videoPlaybackState() {
        VideoPlayer.VideoPlaybackState videoPlaybackState = new VideoPlayer.VideoPlaybackState();
        if (VideoMediaSession.getInstance().isPlaying()) {
            videoPlaybackState.setStatus(VideoPlayer.VideoPlayerStatus.PLAYING);
        } else {
            videoPlaybackState.setStatus(VideoPlayer.VideoPlayerStatus.UNKNOWN);
        }
        return videoPlaybackState;
    }

    public static Context playbackStateContext() {
        return APIUtils.buildContext(audioPlaybackState());
    }

    public static AudioPlayer.PlaybackState audioPlaybackState() {
        AudioPlayer.PlaybackState playbackState = new AudioPlayer.PlaybackState();
        playbackState.setVolume(SystemVolume.getInstance().getVolume());
        if (a) {
            a = false;
            playbackState.setStatus(AudioPlayer.AudioPlayerStatus.PLAYING);
            playbackState.setPlayMode(AudioPlayer.AudioPlayerPlayMode.LIST);
            AudioPlayer.PlaybackAudioMeta playbackAudioMeta = new AudioPlayer.PlaybackAudioMeta();
            playbackAudioMeta.setIsAlarm(true);
            AudioPlayer.AudioItemV1 audioItemV1 = b;
            if (audioItemV1 != null) {
                playbackAudioMeta.setAudioId(audioItemV1.getItemId().getAudioId());
                playbackAudioMeta.setAudioType(Common.AudioType.MUSIC);
                if (b.getItemId().getCp().isPresent()) {
                    playbackAudioMeta.setCp(b.getItemId().getCp().get());
                }
                b = null;
            }
            playbackState.setAudioMeta(playbackAudioMeta);
            return playbackState;
        }
        MediaSessionController instance = MediaSessionController.getInstance();
        if (instance == null) {
            L.speech.w("MediaSessionController is null, regarded as stopped");
            playbackState.setStatus(AudioPlayer.AudioPlayerStatus.STOP);
        } else {
            if (instance.getPlayStatus() == 1) {
                playbackState.setStatus(AudioPlayer.AudioPlayerStatus.PLAYING);
            } else {
                playbackState.setStatus(AudioPlayer.AudioPlayerStatus.PAUSE);
            }
            int loopType = instance.getLoopType();
            if (loopType == LoopType.LIST_LOOP.ordinal()) {
                playbackState.setPlayMode(AudioPlayer.AudioPlayerPlayMode.LIST);
            } else if (loopType == LoopType.SINGLE_LOOP.ordinal()) {
                playbackState.setPlayMode(AudioPlayer.AudioPlayerPlayMode.SINGLE);
            } else if (loopType == LoopType.ORDER.ordinal()) {
                playbackState.setPlayMode(AudioPlayer.AudioPlayerPlayMode.SEQUENCE);
            } else if (loopType == LoopType.SHUFFLE.ordinal()) {
                playbackState.setPlayMode(AudioPlayer.AudioPlayerPlayMode.RANDOM);
            }
            Remote.Response.PlayingData currentPlayingData = instance.getCurrentPlayingData();
            if (currentPlayingData != null) {
                AudioPlayer.PlaybackAudioMeta playbackAudioMeta2 = new AudioPlayer.PlaybackAudioMeta();
                playbackAudioMeta2.setAudioId(currentPlayingData.audioID);
                String str = currentPlayingData.audioType;
                if (!TextUtils.isEmpty(str)) {
                    playbackAudioMeta2.setAudioType(Common.AudioType.valueOf(str.toUpperCase()));
                }
                AudioPlayer.ContentProvider contentProvider = new AudioPlayer.ContentProvider();
                contentProvider.setName(TextUtils.isEmpty(currentPlayingData.cpOrigin) ? "" : currentPlayingData.cpOrigin);
                contentProvider.setId(TextUtils.isEmpty(currentPlayingData.cpID) ? "" : currentPlayingData.cpID);
                playbackAudioMeta2.setCp(contentProvider);
                if (currentPlayingData.screenExtend != null && currentPlayingData.screenExtend.favoritesId > 0) {
                    playbackAudioMeta2.setFavoritesId(currentPlayingData.screenExtend.favoritesId);
                }
                playbackAudioMeta2.setIsAlarm(false);
                L.player.i("%s audioMeta.id: %s, curPlayerId: %s, curPlayerTitle: %s", "[SpeechContextHelper]", playbackAudioMeta2.getAudioId(), currentPlayingData.audioID, currentPlayingData.title);
                playbackState.setAudioMeta(playbackAudioMeta2);
            }
        }
        return playbackState;
    }

    public static void cacheContext() {
        boolean isFiring = AlarmModel.getInstance().getIsFiring();
        L.speech.d("cacheContext alarm=%s", Boolean.valueOf(isFiring));
        if (isFiring) {
            a = true;
            b = LocalPlayerManager.getInstance().getAlarmAudioItem();
            LocalPlayerManager.getInstance().setAlarmAudioItem(null);
        }
    }

    public static void clearCachedContext() {
        a = false;
    }

    public static boolean isAlarmRunningWhenPeak() {
        if (!a) {
            return false;
        }
        int i = Calendar.getInstance().get(11);
        int i2 = Calendar.getInstance().get(12);
        int i3 = (i * 60) + i2;
        L.speech.d("isAlarmRunningWhenPeak.hour=%s, .minute=%s", Integer.valueOf(i), Integer.valueOf(i2));
        return i3 >= 355 && i3 <= 515;
    }

    public static Context educationControllerContext() {
        Education.EduCurrentPageState eduCurrentPageState = new Education.EduCurrentPageState();
        eduCurrentPageState.setPageType(Video.PageType.SEARCH_PAGE);
        return APIUtils.buildContext(eduCurrentPageState);
    }
}
