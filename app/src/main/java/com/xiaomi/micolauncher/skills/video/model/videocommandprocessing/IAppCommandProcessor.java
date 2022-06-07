package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public interface IAppCommandProcessor {
    void backward(Context context, long j);

    void cast(Context context, boolean z);

    void communicate(Context context, String str);

    void forward(Context context, long j);

    String getPackage();

    void like(Context context);

    boolean needCamera();

    void next(Context context);

    void pause(Context context);

    void play(Context context);

    boolean preferPreviousAndNextOnGesture();

    void previous(Context context);

    void quit(Context context);

    void quitFromUI(Context context);

    void repeat(Context context);

    void seek(Context context, long j);

    void select(Context context, int i);

    void setResolution(Context context, String str);

    boolean shouldCountTimeOnChildMode();

    void skipStart(Context context);

    void startApp(Context context, ThirdPartyAppProxy.OnFillIntentExtras onFillIntentExtras);

    void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs);

    void stop(Context context);

    void stopFromUI(Context context);

    boolean supportBackward();

    boolean supportCast();

    boolean supportCommandSdk();

    boolean supportCommunicate();

    boolean supportForward();

    boolean supportGesture();

    boolean supportLike();

    boolean supportNext();

    boolean supportPrevious();

    boolean supportRepeat();

    boolean supportSeek();

    boolean supportSelect();

    boolean supportSetResolution();

    boolean supportSkipStart();

    boolean supportSoundboxControlAction(PlaybackControllerInfo playbackControllerInfo);

    boolean supportStartApp();
}
