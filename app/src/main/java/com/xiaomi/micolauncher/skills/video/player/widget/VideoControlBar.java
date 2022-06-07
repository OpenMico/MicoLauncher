package com.xiaomi.micolauncher.skills.video.player.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.databinding.DataBindingUtil;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.VideoControllerBarBinding;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.player.utils.Utils;

/* loaded from: classes3.dex */
public class VideoControlBar extends FrameLayout implements SeekBar.OnSeekBarChangeListener {
    private VideoControllerBarBinding a;
    private VideoControllerControl b;
    private VideoControlBarActionListener c;
    private int d;
    private final Runnable e = new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.VideoControlBar.1
        @Override // java.lang.Runnable
        public void run() {
            if (VideoControlBar.this.b != null) {
                VideoControlBar.this.b();
                if (VideoControlBar.this.b.isPlaying()) {
                    VideoControlBar videoControlBar = VideoControlBar.this;
                    videoControlBar.postDelayed(videoControlBar.e, 1000L);
                }
            }
        }
    };

    /* loaded from: classes3.dex */
    public interface VideoControlBarActionListener {
        void onClickPlayButton();

        void onProgressChanged(int i);

        void onStartTracking();

        void onStopTracking(int i, boolean z, boolean z2);
    }

    public VideoControlBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, -1);
        a(context);
    }

    private void a(Context context) {
        this.a = (VideoControllerBarBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.video_controller_bar, this, true);
        this.a.shortVideoControllerBar.seekBar.setOnSeekBarChangeListener(this);
        reset(true);
    }

    public void reset(boolean z) {
        a();
        setPlayButtonState(z);
        setSeekTime(0);
        setDuration(0);
        this.a.shortVideoControllerBar.ivPlayState.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$VideoControlBar$8AfT3yivUByWx_WFseyWMa0bJY4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoControlBar.this.a(view);
            }
        });
        this.a.longVideoControllerBar.ivPlayState.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$VideoControlBar$8AfT3yivUByWx_WFseyWMa0bJY4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoControlBar.this.a(view);
            }
        });
        this.a.shortVideoControllerBar.seekBar.setOnSeekBarChangeListener(this);
        this.a.longVideoControllerBar.seekBar.setOnSeekBarChangeListener(this);
        this.a.shortVideoControllerBar.seekBar.setSecondaryProgress(0);
        this.a.shortVideoControllerBar.seekBar.setProgress(0);
        this.a.longVideoControllerBar.seekBar.setSecondaryProgress(0);
        this.a.longVideoControllerBar.seekBar.setProgress(0);
        this.a.longVideoControllerBar.tvOpenSelections.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$VideoControlBar$b19PR_N1TUCUnBaY1_aNRJu5tO0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoControlBar.this.c(view);
            }
        });
        this.a.longVideoControllerBar.ivNext.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$VideoControlBar$ttYaWaC64RVuvkcusDwlYpUmmoM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoControlBar.this.b(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        if (this.b != null && !UiUtils.isFastClick()) {
            this.b.openSelectionList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        VideoControllerControl videoControllerControl = this.b;
        if (videoControllerControl != null) {
            videoControllerControl.next();
        }
    }

    private void a() {
        boolean isLongVideo = VideoModel.getInstance().isLongVideo();
        int i = 8;
        this.a.shortVideoControllerBar.getRoot().setVisibility(isLongVideo ? 8 : 0);
        this.a.longVideoControllerBar.getRoot().setVisibility(isLongVideo ? 0 : 8);
        this.a.longVideoControllerBar.tvOpenSelections.setVisibility(VideoModel.getInstance().hasResource() && VideoModel.getInstance().getPlayList().size() > 1 ? 0 : 8);
        boolean z = !VideoModel.getInstance().isLastResource(VideoModel.getInstance().getPlayIndex());
        ImageView imageView = this.a.longVideoControllerBar.ivNext;
        if (z) {
            i = 0;
        }
        imageView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view) {
        VideoControlBarActionListener videoControlBarActionListener = this.c;
        if (videoControlBarActionListener != null) {
            videoControlBarActionListener.onClickPlayButton();
        }
    }

    public void setController(VideoControllerControl videoControllerControl) {
        this.b = videoControllerControl;
    }

    public void setVideoControlBarActionListener(VideoControlBarActionListener videoControlBarActionListener) {
        this.c = videoControlBarActionListener;
    }

    public void setDuration(int i) {
        this.a.shortVideoControllerBar.tvDurationTime.setText(Utils.formatTime(i));
        this.a.shortVideoControllerBar.seekBar.setMax(i);
        this.a.longVideoControllerBar.tvDurationTime.setText(Utils.formatTime(i));
        this.a.longVideoControllerBar.seekBar.setMax(i);
    }

    public void setProgress(int i) {
        this.a.shortVideoControllerBar.seekBar.setProgress(i);
        this.a.longVideoControllerBar.seekBar.setProgress(i);
        setSeekTime(i);
    }

    private void setSeekTime(int i) {
        String formatTime = Utils.formatTime(i);
        this.a.shortVideoControllerBar.tvSeekTime.setText(formatTime);
        this.a.longVideoControllerBar.tvSeekTime.setText(formatTime);
    }

    public void setPlayButtonState(boolean z) {
        Logger logger = L.video;
        logger.i("VideoControlBar setPlayButtonState playing=" + z);
        if (z) {
            setPauseImage(R.drawable.icon_pause);
        } else {
            setPauseImage(R.drawable.icon_play);
        }
    }

    public void show() {
        setVisibility(0);
        a();
        c();
    }

    public void hide() {
        L.video.d("VideoControlBar PlayerSeekBar hide");
        if (isShown()) {
            setVisibility(8);
            d();
        }
    }

    private void setPauseImage(int i) {
        if (this.a != null) {
            GlideUtils.bindImageView(getContext(), i, this.a.shortVideoControllerBar.ivPlayState);
            GlideUtils.bindImageView(getContext(), i, this.a.longVideoControllerBar.ivPlayState);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        L.video.d("VideoControlBar onStartTrackingTouch");
        d();
        this.d = seekBar.getProgress();
        VideoControlBarActionListener videoControlBarActionListener = this.c;
        if (videoControlBarActionListener != null) {
            videoControlBarActionListener.onStartTracking();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            Logger logger = L.video;
            logger.d("VideoControlBar PlayerSeekBar onProgressChanged: " + i);
            if (i >= 0 && i <= this.b.getDuration()) {
                setSeekTime(i);
            }
            VideoControlBarActionListener videoControlBarActionListener = this.c;
            if (videoControlBarActionListener != null) {
                videoControlBarActionListener.onProgressChanged(i);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        L.video.d("VideoControlBar onStopTrackingTouch");
        if (this.c != null) {
            int progress = seekBar.getProgress();
            boolean z = true;
            boolean z2 = progress - this.d > 0;
            VideoControlBarActionListener videoControlBarActionListener = this.c;
            if (progress != seekBar.getMax()) {
                z = false;
            }
            videoControlBarActionListener.onStopTracking(progress, z, z2);
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        VideoControllerControl videoControllerControl = this.b;
        if (videoControllerControl != null) {
            int currentPosition = videoControllerControl.getCurrentPosition();
            int duration = this.b.getDuration();
            Logger logger = L.video;
            logger.d("VideoControlBar updatePlayingTime: " + currentPosition + ", duration: " + duration);
            if (currentPosition >= 0 && currentPosition <= duration) {
                setProgress(currentPosition);
            }
        }
    }

    private void c() {
        removeCallbacks(this.e);
        post(this.e);
    }

    private void d() {
        removeCallbacks(this.e);
    }
}
