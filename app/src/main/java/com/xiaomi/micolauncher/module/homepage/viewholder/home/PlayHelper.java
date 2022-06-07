package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.util.ChildHelper;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BiliAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.QiyiAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TencentVideoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.YoukuAppCommandProcessor;

/* loaded from: classes3.dex */
public class PlayHelper {
    public static void play(Context context, MainPage.LongVideo longVideo) {
        if (longVideo != null) {
            if ("iqiyi".equalsIgnoreCase(longVideo.getCp())) {
                c(context, longVideo);
            } else if ("bilibili".equalsIgnoreCase(longVideo.getCp())) {
                d(context, longVideo);
            } else if ("youku".equalsIgnoreCase(longVideo.getCp())) {
                e(context, longVideo);
            } else if ("mitv".equalsIgnoreCase(longVideo.getCp())) {
                b(context, longVideo);
            } else if ("mangotv".equalsIgnoreCase(longVideo.getCp())) {
                a(context, longVideo);
            } else {
                L.video.e("cannot play %s", longVideo.getCp(), longVideo.getCpId());
            }
        }
    }

    private static void a(Context context, MainPage.LongVideo longVideo) {
        String cpId = longVideo.getCpId();
        if (ContainerUtil.isEmpty(cpId)) {
            L.video.e("empty media id %s", longVideo);
            return;
        }
        String url = MangguoAppCommandProcessor.getUrl(cpId);
        L.video.d("manguo uri=%s", url);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO, url));
    }

    private static void b(Context context, MainPage.LongVideo longVideo) {
        ChildPlayUtil.playVideo(context, String.valueOf(longVideo.getCpId()), longVideo.getTitle(), longVideo.getImageURL(), ChildVideo.MiTvType.CHILD_VIDEO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Intent a(MainPage.LongVideo longVideo) {
        return QiyiAppCommandProcessor.createStartVideoIntent(longVideo.getCpId(), null, 0, 0L);
    }

    private static void c(Context context, final MainPage.LongVideo longVideo) {
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("com.qiyi.video.speaker", new ThirdPartyAppProxy.OnCustomStartVideoCallback() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$PlayHelper$M11RK28RTuVk0ig1SlICOz7Hfyw
            @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.OnCustomStartVideoCallback
            public final Intent createIntent() {
                Intent a;
                a = PlayHelper.a(MainPage.LongVideo.this);
                return a;
            }
        }));
    }

    private static void d(Context context, MainPage.LongVideo longVideo) {
        Uri buildBiliAppUrl = BiliAppCommandProcessor.buildBiliAppUrl(false, longVideo.getCpId(), null);
        L.video.d("bili uri=%s", buildBiliAppUrl);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("tv.danmaku.bili", buildBiliAppUrl));
    }

    private static void e(Context context, MainPage.LongVideo longVideo) {
        String url = YoukuAppCommandProcessor.getUrl(longVideo.getCpId());
        L.video.d("youku uri=%s", url);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("com.youku.iot", url));
    }

    public static void play(Context context, VideoData videoData) {
        if (videoData != null) {
            if ("iqiyi".equalsIgnoreCase(videoData.getCp())) {
                b(context, videoData);
            } else if ("bilibili".equalsIgnoreCase(videoData.getCp())) {
                c(context, videoData);
            } else if ("youku".equalsIgnoreCase(videoData.getCp())) {
                d(context, videoData);
            } else if ("mitv".equalsIgnoreCase(videoData.getCp())) {
                a(context, videoData);
            } else if ("mangotv".equalsIgnoreCase(videoData.getCp())) {
                e(context, videoData);
            } else if ("tencent".equalsIgnoreCase(videoData.getCp())) {
                ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("com.tencent.qqlive.audiobox", TencentVideoAppCommandProcessor.getUrl(videoData.getCpId())));
            } else {
                L.video.e("cannot play %s", videoData.getCp(), videoData.getCpId());
            }
        }
    }

    private static void a(Context context, VideoData videoData) {
        String pCode = videoData.getPCode();
        ChildPlayUtil.playVideo(context, String.valueOf(videoData.getCpId()), videoData.getName(), videoData.getImage(), pCode == null ? ChildVideo.MiTvType.CHILD_VIDEO : ChildHelper.parsePCode(pCode));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Intent a(VideoData videoData) {
        return QiyiAppCommandProcessor.createStartVideoIntent(videoData.getCpId(), null, 0, 0L);
    }

    private static void b(Context context, final VideoData videoData) {
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("com.qiyi.video.speaker", new ThirdPartyAppProxy.OnCustomStartVideoCallback() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$PlayHelper$-9A79gASAqt5qZ7xBoUhx8834DA
            @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.OnCustomStartVideoCallback
            public final Intent createIntent() {
                Intent a;
                a = PlayHelper.a(VideoData.this);
                return a;
            }
        }));
    }

    private static void c(Context context, VideoData videoData) {
        Uri buildBiliAppUrl = BiliAppCommandProcessor.buildBiliAppUrl(false, videoData.getCpId(), null);
        L.video.d("bili uri=%s", buildBiliAppUrl);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("tv.danmaku.bili", buildBiliAppUrl));
    }

    private static void d(Context context, VideoData videoData) {
        String url = YoukuAppCommandProcessor.getUrl(videoData.getCpId());
        L.video.d("youku uri=%s", url);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from("com.youku.iot", url));
    }

    private static void e(Context context, VideoData videoData) {
        String cpId = videoData.getCpId();
        if (ContainerUtil.isEmpty(cpId)) {
            L.video.e("empty media id %s", videoData);
            return;
        }
        String url = MangguoAppCommandProcessor.getUrl(cpId);
        L.video.d("manguo uri=%s", url);
        ThirdPartyAppProxy.getInstance().startVideo(context, ThirdPartyAppProxy.StartArgs.from(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO, url));
    }
}
