package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class QiyiAppCommandProcessor extends BaseAppCommandProcessor {
    public static final String KEY_ALBUM_ID = "aid";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_TV_ID = "tvid";
    public static final String PACKAGE_NAME_QIYI = "com.qiyi.video.speaker";
    public static final String URL_IQIYI_APP_PREFIX = "iqiyi://com.qiyi.video.speaker/app?from=xiaoai&command=";

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return "com.qiyi.video.speaker";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportBackward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCast() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportForward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportGesture() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportNext() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportPrevious() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportRepeat() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSeek() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSelect() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSkipStart() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras) {
        super.startApp(context, onFillIntentExtras);
    }

    public static Intent createStartVideoIntent(@NonNull String str, String str2, int i, long j) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.qiyi.video.speaker", "org.qiyi.video.PadPlayerBridgeActivity"));
        intent.addFlags(268435456);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ALBUM_ID, str);
        bundle.putString(KEY_TV_ID, str2);
        if (j > 0) {
            bundle.putLong("offset", j);
        }
        if (i > 0) {
            bundle.putInt("isFromTvHistory", 1);
        } else {
            bundle.putInt("isFromTvHistory", 0);
        }
        intent.putExtras(bundle);
        L.video.i("iqiyi start video with splash screen, aid : %s, tvid : %s, offset : %s", str, str2, Long.valueOf(j));
        return intent;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        Intent intent;
        ThirdPartyAppProxy.OnCustomStartVideoCallback onCustomStartVideoCallback = startArgs.getOnCustomStartVideoCallback();
        if (onCustomStartVideoCallback != null) {
            intent = onCustomStartVideoCallback.createIntent();
        } else {
            intent = startArgs.getUri();
        }
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void forward(Context context, long j) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=fast_forward&param=" + j)));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void backward(Context context, long j) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=fast_backward&param=" + j)));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=pause")));
        VideoMediaSession.getInstance().setPaused();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=play")));
        VideoMediaSession.getInstance().setPlaying();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/app?from=xiaoai&command=back")));
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stopFromUI(Context context) {
        super.doStop(context);
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quit(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/app?from=xiaoai&command=exit_app")));
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quitFromUI(Context context) {
        super.doQuit(context);
        VideoMediaSession.getInstance().setStopped();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void setResolution(Context context, String str) {
        String a = a(context, str);
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=coderate&param=" + a)));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void next(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=next")));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void select(Context context, int i) {
        Uri parse = Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=episode&param=" + i);
        Intent intent = new Intent("android.intent.action.VIEW", parse);
        L.video.i("Iqiyi play video by index %s, uri %s", Integer.valueOf(i), parse);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void repeat(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=seek&param=0")));
    }

    private String a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (PlaybackControllerInfo.RESOLUTION_SD.equalsIgnoreCase(str)) {
            return context.getResources().getString(R.string.video_standard);
        }
        if (PlaybackControllerInfo.RESOLUTION_FD.equalsIgnoreCase(str)) {
            return context.getResources().getString(R.string.video_topspeed);
        }
        if (PlaybackControllerInfo.RESOLUTION_HD.equalsIgnoreCase(str)) {
            return context.getResources().getString(R.string.video_high);
        }
        if (PlaybackControllerInfo.RESOLUTION_FULL_HD.equalsIgnoreCase(str)) {
            return context.getResources().getString(R.string.video_very_high);
        }
        if (PlaybackControllerInfo.RESOLUTION_BD.equalsIgnoreCase(str)) {
            return context.getResources().getString(R.string.video_blue_high);
        }
        return PlaybackControllerInfo.RESOLUTION_4K.equalsIgnoreCase(str) ? context.getResources().getString(R.string.video_4k) : "";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void skipStart(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=auto_skip&param=true")));
        SpeechManager.getInstance().ttsRequest(context.getString(R.string.tts_local_has_opened_skip_start));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void seek(Context context, long j) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=seek&param=" + j)));
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void cast(Context context, boolean z) {
        if (z) {
            a("iqiyi://com.qiyi.video.speaker/player?command=start_cast");
        } else {
            a("iqiyi://com.qiyi.video.speaker/player?command=end_cast");
        }
    }

    private void a(String str) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }
}
