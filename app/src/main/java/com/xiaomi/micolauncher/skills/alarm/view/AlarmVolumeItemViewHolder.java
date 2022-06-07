package com.xiaomi.micolauncher.skills.alarm.view;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.VerticalSeekBar;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;

/* loaded from: classes3.dex */
public class AlarmVolumeItemViewHolder extends RecyclerView.ViewHolder {
    VerticalSeekBar a;
    private boolean b;
    private boolean c;
    private Activity d;

    public AlarmVolumeItemViewHolder(final Activity activity, View view) {
        super(view);
        this.d = activity;
        this.a = (VerticalSeekBar) view.findViewById(R.id.volume_seekBar);
        this.a.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmVolumeItemViewHolder.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i < 5) {
                    seekBar.setProgress(5);
                    return;
                }
                if (i < 25) {
                    if (!AlarmVolumeItemViewHolder.this.c) {
                        AlarmVolumeItemViewHolder.this.c = true;
                        ToastUtil.showToast((int) R.string.alarm_volume_low_des);
                    }
                    seekBar.setProgressDrawable(ContextCompat.getDrawable(AlarmVolumeItemViewHolder.this.a.getContext(), R.drawable.vp_progressbar_volume_red_big_screen));
                } else if (i > 80) {
                    if (!AlarmVolumeItemViewHolder.this.b) {
                        AlarmVolumeItemViewHolder.this.b = true;
                        ToastUtil.showToast((int) R.string.alarm_volume_hight_des);
                    }
                    seekBar.setProgressDrawable(ContextCompat.getDrawable(AlarmVolumeItemViewHolder.this.a.getContext(), R.drawable.vp_progressbar_volume_red_big_screen));
                } else {
                    seekBar.setProgressDrawable(ContextCompat.getDrawable(AlarmVolumeItemViewHolder.this.a.getContext(), R.drawable.vp_progressbar_volume_big_screen));
                }
                SystemVolume.getInstance().setAlarmVolume(i, false);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                ((AlarmListBigScreenActivity) activity).flushView(false);
                ((AlarmListBigScreenActivity) activity).startPreview();
                L.alarm.d("AlarmVolumeItemViewHolder onStartTrackingTouch  ");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                L.alarm.d("AlarmVolumeItemViewHolder onStopTrackingTouch ");
                ((AlarmListBigScreenActivity) activity).flushView(true);
            }
        });
        refreshView();
    }

    public void refreshView() {
        L.alarm.d("AlarmVolumeItemViewHolder getVolume = %d ", Integer.valueOf(SystemVolume.getInstance().getAlarmVolume()));
        this.a.setProgress(SystemVolume.getInstance().getAlarmVolume());
    }
}
