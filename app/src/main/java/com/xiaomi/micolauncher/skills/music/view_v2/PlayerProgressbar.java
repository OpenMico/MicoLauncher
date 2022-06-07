package com.xiaomi.micolauncher.skills.music.view_v2;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.TimeUtils;

/* loaded from: classes3.dex */
public class PlayerProgressbar extends LinearLayout implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    private View a;
    private LinearLayout b;
    private TextView c;
    private TextView d;
    private SeekBar e;
    private int f = 50;
    private boolean g;
    private Rect h;
    private Remote.Response.PlayerStatus i;
    private OnPlayerProgressbarChangeListener j;

    /* loaded from: classes3.dex */
    public interface OnPlayerProgressbarChangeListener {
        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }

    public void setPlayerProgressbarChangeListener(OnPlayerProgressbarChangeListener onPlayerProgressbarChangeListener) {
        this.j = onPlayerProgressbarChangeListener;
    }

    public void setPlayerStatus(final Remote.Response.PlayerStatus playerStatus) {
        if (!ThreadUtil.isMainThread()) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerProgressbar$LrhDo274yP-FelaXziyUjg6bN0w
                @Override // java.lang.Runnable
                public final void run() {
                    PlayerProgressbar.this.b(playerStatus);
                }
            });
        } else {
            b(playerStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(Remote.Response.PlayerStatus playerStatus) {
        Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
        if (playingData == null || (TextUtils.isEmpty(playingData.audioID) && playingData.screenExtend.mediaType != 21)) {
            setSeekBarEnable(false);
            return;
        }
        this.i = playerStatus;
        if (this.b.getVisibility() == 0) {
            this.c.setText(TimeUtils.getMusicPlayTime(playingData.position));
            this.d.setText(TimeUtils.getMusicPlayTime(playingData.duration));
        }
        if (playingData.isPlayingFM() || playingData.isBookLocked()) {
            this.e.setProgress(0);
            this.e.setEnabled(false);
            return;
        }
        this.e.setEnabled(true);
        if (playingData.duration <= 1) {
            this.e.setProgress(0);
        } else if (this.g) {
        } else {
            if (playerStatus.status == 1 || playerStatus.status == 2) {
                this.e.setProgress((int) ((playingData.position * 100) / playingData.duration));
            }
        }
    }

    public long getNowPosition() {
        return (this.i.play_song_detail.duration * this.e.getProgress()) / 100;
    }

    public PlayerProgressbar(Context context) {
        super(context);
        init(context);
    }

    public PlayerProgressbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public PlayerProgressbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    protected void init(Context context) {
        this.a = View.inflate(context, R.layout.layout_music_progressbar, this);
        this.b = (LinearLayout) this.a.findViewById(R.id.progressbar_time_view);
        this.c = (TextView) this.a.findViewById(R.id.progressbar_current_time);
        this.d = (TextView) this.a.findViewById(R.id.progressbar_total_time);
        this.e = (SeekBar) this.a.findViewById(R.id.player_seek_bar_v2);
        this.e.setOnSeekBarChangeListener(this);
        setOnTouchListener(this);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        Remote.Response.PlayerStatus playerStatus = this.i;
        if (playerStatus != null) {
            Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
            this.c.setText(TimeUtils.getMusicPlayTime((playingData.duration * i) / 100));
            this.d.setText(TimeUtils.getMusicPlayTime(playingData.duration));
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.a.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.player_progress_bar_bg_color));
        Remote.Response.PlayerStatus playerStatus = this.i;
        if (playerStatus != null) {
            this.g = true;
            Remote.Response.PlayingData playingData = playerStatus.play_song_detail;
            this.b.setVisibility(0);
            this.c.setText(TimeUtils.getMusicPlayTime((playingData.duration * seekBar.getProgress()) / 100));
            this.d.setText(TimeUtils.getMusicPlayTime(playingData.duration));
            OnPlayerProgressbarChangeListener onPlayerProgressbarChangeListener = this.j;
            if (onPlayerProgressbarChangeListener != null) {
                onPlayerProgressbarChangeListener.onStartTrackingTouch(seekBar);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.a.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.white_transparent_mask_shape));
        if (this.i != null) {
            this.g = false;
            this.b.setVisibility(8);
            OnPlayerProgressbarChangeListener onPlayerProgressbarChangeListener = this.j;
            if (onPlayerProgressbarChangeListener != null) {
                onPlayerProgressbarChangeListener.onStopTrackingTouch(seekBar);
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float f;
        if (this.h == null) {
            this.h = new Rect();
        }
        this.e.getHitRect(this.h);
        if (motionEvent.getY() < this.h.top - this.f || motionEvent.getY() > this.h.bottom + this.f) {
            this.e.setPressed(false);
            this.a.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.white_transparent_mask_shape));
            this.b.setVisibility(8);
            return false;
        }
        float height = this.h.top + (this.h.height() / 2);
        float x = motionEvent.getX() - this.h.left;
        if (x < 0.0f) {
            f = 0.0f;
        } else {
            f = x > ((float) this.h.width()) ? this.h.width() : x;
        }
        return this.e.onTouchEvent(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), f, height, motionEvent.getMetaState()));
    }

    public void setSeekBarEnable(boolean z) {
        if (!z) {
            this.e.setProgress(0);
        }
        this.e.setEnabled(z);
    }
}
