package com.xiaomi.micolauncher.skills.video.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class MiTvPlayerPresenter extends VideoPlayerPresenter {
    /* JADX INFO: Access modifiers changed from: package-private */
    public MiTvPlayerPresenter(Context context, IVideoPlayerView iVideoPlayerView) {
        super(context, iVideoPlayerView);
    }

    @SuppressLint({"CheckResult"})
    public void getSevenDayVip() {
        addToDisposeBag(ChildVideoDataManager.getManager().getSevenDayVip(WiFiUtil.getMacAddress(), ChildVideoDataManager.PCODE_ERTONG).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$MiTvPlayerPresenter$Fm1PuFmywvkTByO-VTa17bil5kc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvPlayerPresenter.this.b((ChildVideo.VipStatus) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$MiTvPlayerPresenter$eXqhDxx-8r2wSViXuhvV80_IgMs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvPlayerPresenter.this.b((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ChildVideo.VipStatus vipStatus) throws Exception {
        if (vipStatus.status == 0) {
            L.video.i("get seven day vip succeed");
            SystemSetting.setKeyGetSevenVipDialogShowTimesMax();
            VideoModel.getInstance().clearPlayList();
            playByIndex(VideoModel.getInstance().getPlayIndex());
            return;
        }
        ToastUtil.showToast((int) R.string.get_failed_retry);
        showSevenVipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        L.video.e("get seven day vip error ", th);
        ToastUtil.showToast((int) R.string.get_failed_retry);
        showSevenVipDialog();
    }

    public void showVipDialog(boolean z) {
        pauseVideo();
        setFocusChangeResume(false);
        if (getVideoPlayerView() != null) {
            getVideoPlayerView().showVipDialog();
            if (z) {
                ChildStatHelper.recordChildVideoVipPopExpose(this.a);
            }
        }
    }

    public void showSevenVipDialog() {
        L.video.i("show seven day vip dialog");
        pauseVideo();
        setFocusChangeResume(false);
        if (getVideoPlayerView() != null) {
            getVideoPlayerView().showSevenVipDialog();
        }
    }

    @SuppressLint({"CheckResult"})
    protected void handleMiTvVipError() {
        if (!ChildVideo.MiTvType.CHILD_VIDEO.equals(VideoModel.getInstance().getMiTvType())) {
            showVipDialog(true);
        } else if (SystemSetting.getKeyHasPurchasedVip() || !SystemSetting.getSevenVipDialogShowTimesNotEnough()) {
            L.video.i("has purchased mitv vip");
            showVipDialog(true);
        } else {
            addToDisposeBag(ChildVideoDataManager.getManager().getVipStatus(WiFiUtil.getMacAddress(), ChildVideoDataManager.PCODE_ERTONG).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$MiTvPlayerPresenter$C95xixnKs4SkutPOJTXDQNszyg4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiTvPlayerPresenter.this.a((ChildVideo.VipStatus) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$MiTvPlayerPresenter$wJ_wFpW2xc-UbbfoYlVFSeYKJ3U
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiTvPlayerPresenter.this.a((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo.VipStatus vipStatus) throws Exception {
        if (vipStatus.status == 1) {
            SystemSetting.setKeyHasPurchasedVip(true);
            showVipDialog(true);
            return;
        }
        showSevenVipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.video.e("get vip status error ", th);
        showVipDialog(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.video.player.VideoPlayerPresenter
    public void handleActivityResult(int i, int i2, Intent intent) {
        if (i != 291) {
            return;
        }
        if (i2 == 273) {
            L.video.i("onActivityResult vip result   %d     %d  buy success", Integer.valueOf(i), Integer.valueOf(i2));
            playByIndex(VideoModel.getInstance().getPlayIndex());
            return;
        }
        L.video.i("onActivityResult vip result   %d     %d  buy failed", Integer.valueOf(i), Integer.valueOf(i2));
        setAutoPlayWhenViewResume(false);
        showVipDialog(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.video.player.VideoPlayerPresenter
    public void onLoadResourceError(Throwable th) {
        if ((th instanceof HttpException) && this.videoMode == VideoMode.MITV_SERIAL) {
            HttpException httpException = (HttpException) th;
            if (httpException.code() == 403) {
                try {
                    ResponseBody errorBody = httpException.response().errorBody();
                    if (errorBody != null) {
                        String string = errorBody.string();
                        if (!TextUtils.isEmpty(string)) {
                            MIBrain.CpResource cpResource = (MIBrain.CpResource) Gsons.getGson().fromJson(string, (Class<Object>) MIBrain.CpResource.class);
                            if (cpResource.cpinfo != null && cpResource.cpinfo.cpcode == 27) {
                                handleMiTvVipError();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onLoadResourceError(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.video.player.VideoPlayerPresenter
    public void completionVideo() {
        if (!this.a.canTrial() || SystemSetting.getKeyMiTvVipStatus()) {
            super.completionVideo();
            return;
        }
        L.video.i("trial end show vip dialog");
        handleMiTvVipError();
    }
}
