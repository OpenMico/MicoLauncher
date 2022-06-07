package com.xiaomi.micolauncher.module.multicp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.module.multicp.widget.AccurateMultiShotView;
import com.xiaomi.micolauncher.module.multicp.widget.AccurateSingleShotView;
import com.xiaomi.micolauncher.module.multicp.widget.MoreView;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;

/* loaded from: classes3.dex */
public class FloatingContentProviderView extends Dialog {
    public static final int DISMISS_DURATION = 15000;
    private final AccurateSingleShotView a;
    private final AccurateMultiShotView b;
    private final MoreView c;
    private boolean d;
    private boolean e;
    private String g = "";
    private final CountDownHandler f = new CountDownHandler();

    public FloatingContentProviderView(@NonNull Context context) {
        super(context, R.style.DialogFullScreen);
        this.a = new AccurateSingleShotView(context);
        this.b = new AccurateMultiShotView(context);
        this.c = new MoreView(context);
        setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.micolauncher.module.multicp.-$$Lambda$FloatingContentProviderView$TAR-zVzrlvcXxtUcdlcMYsZxWdk
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FloatingContentProviderView.this.a(dialogInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(DialogInterface dialogInterface) {
        this.d = false;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        L.accessibility.i("%s onCreate", "[FloatingContentProviderView]:");
        requestWindowFeature(1);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.animWakeupFade);
            window.setType(2038);
        }
        setCanceledOnTouchOutside(true);
        setContentView(this.b);
    }

    public void update(boolean z) {
        if (z || !TextUtils.equals(this.g, VideoModel.getInstance().getDialogId())) {
            this.g = VideoModel.getInstance().getDialogId();
            VideoModel.ShotType shotType = VideoModel.getInstance().getShotType();
            Logger logger = L.multicp;
            logger.d("[FloatingContentProviderView]:update shotType is " + shotType);
            switch (shotType) {
                case TYPE_SINGLE_MULTI:
                    VideoItem videoItem = VideoModel.getInstance().getMultiCpRecommendationList().get(0);
                    this.a.updateData(videoItem);
                    VideoTrackHelper.postSearchResultTrack(EventType.EXPOSE, videoItem, 0, "voice", null, VideoTrackHelper.PAGE_SUPERNATANT, VideoTrackHelper.FLOAT_SINGLE, VideoTrackHelper.SWITCH_TYPE_DIRECT_PLAY);
                    setContentView(this.a);
                    a(2);
                    return;
                case TYPE_MULTI_MULTI:
                    this.b.updateData(VideoModel.getInstance().getMultiCpRecommendationList());
                    VideoTrackHelper.postSearchResultTrack(EventType.EXPOSE, VideoModel.getInstance().getCurrentPlayingVideoItem(), VideoModel.getInstance().getPlayIndex(), "voice", null, VideoTrackHelper.PAGE_SUPERNATANT, VideoTrackHelper.FLOAT_MULTI, VideoTrackHelper.SWITCH_TYPE_DIRECT_PLAY);
                    setContentView(this.b);
                    a(2);
                    return;
                case TYPE_SINGLE_SINGLE:
                case TYPE_NONE:
                    if (isShowing()) {
                        dismiss();
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            L.multicp.d("current dialog id is %s", this.g);
        }
    }

    private void a(int i) {
        this.f.removeMessages(i);
        this.f.sendEmptyMessageDelayed(i, 15000L);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        a();
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            switch (VideoModel.getInstance().getShotType()) {
                case TYPE_SINGLE_MULTI:
                case TYPE_MULTI_MULTI:
                    a(2);
                    break;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    private void a() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = this.d ? -2 : -1;
            attributes.height = -2;
            attributes.y = this.d ? 200 : 0;
            attributes.flags = 8;
            window.setAttributes(attributes);
            window.setGravity(this.d ? BadgeDrawable.BOTTOM_END : 80);
        }
    }

    public void clearDelayedMsg() {
        this.f.removeMessages(2);
    }

    public void toggleCollapse(boolean z) {
        this.d = z;
        if (z) {
            Logger logger = L.multicp;
            logger.d("toggleCollapse() isMoreBtnShouldBeVisible=" + this.e);
            this.c.setVisibility(this.e ? 0 : 8);
            setContentView(this.c);
        } else {
            switch (VideoModel.getInstance().getShotType()) {
                case TYPE_SINGLE_MULTI:
                    this.a.updateData(VideoModel.getInstance().getMultiCpRecommendationList().get(0));
                    setContentView(this.a);
                    break;
                case TYPE_MULTI_MULTI:
                    this.b.updateData(VideoModel.getInstance().getMultiCpRecommendationList());
                    setContentView(this.b);
                    break;
            }
            a(2);
        }
        a();
        VideoItem currentPlayingVideoItem = VideoModel.getInstance().getCurrentPlayingVideoItem();
        if (currentPlayingVideoItem != null) {
            L.multicp.d("%s toggleCollapse currentPlaying item is %s", "[FloatingContentProviderView]:", currentPlayingVideoItem.getTitle());
        }
    }

    public void setMoreBtnShouldBeVisible(boolean z) {
        this.e = z;
        Logger logger = L.multicp;
        logger.d("setMoreBtnShouldBeVisible() couldBeVisible=" + this.e);
        this.c.setVisibility(this.e ? 0 : 8);
    }
}
