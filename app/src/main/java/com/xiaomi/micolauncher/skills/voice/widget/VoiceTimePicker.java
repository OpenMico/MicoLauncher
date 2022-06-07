package com.xiaomi.micolauncher.skills.voice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.xiaomi.micolauncher.R;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VoiceTimePicker extends RelativeLayout {
    private OnTimePickedListener a;

    /* loaded from: classes3.dex */
    public interface OnTimePickedListener {
        void onCancel();

        void onTimePicked(long j);
    }

    public VoiceTimePicker(Context context) {
        this(context, null);
        a();
    }

    public VoiceTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        a();
    }

    public VoiceTimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_voice_time_picker, this);
        findViewById(R.id.time_30).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.widget.-$$Lambda$M3IcVESBmjozQ9r6nu2kTMhZ-sk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VoiceTimePicker.this.onViewClicked(view);
            }
        });
        findViewById(R.id.time_60).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.widget.-$$Lambda$M3IcVESBmjozQ9r6nu2kTMhZ-sk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VoiceTimePicker.this.onViewClicked(view);
            }
        });
        findViewById(R.id.time_90).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.widget.-$$Lambda$M3IcVESBmjozQ9r6nu2kTMhZ-sk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VoiceTimePicker.this.onViewClicked(view);
            }
        });
        findViewById(R.id.time_unlimited).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.widget.-$$Lambda$M3IcVESBmjozQ9r6nu2kTMhZ-sk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VoiceTimePicker.this.onViewClicked(view);
            }
        });
        findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.widget.-$$Lambda$M3IcVESBmjozQ9r6nu2kTMhZ-sk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VoiceTimePicker.this.onViewClicked(view);
            }
        });
    }

    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.root_view) {
            setVisibility(8);
            OnTimePickedListener onTimePickedListener = this.a;
            if (onTimePickedListener != null) {
                onTimePickedListener.onCancel();
            }
        } else if (id != R.id.time_unlimited) {
            switch (id) {
                case R.id.time_30 /* 2131363573 */:
                    a(TimeUnit.MINUTES.toMillis(30L));
                    return;
                case R.id.time_60 /* 2131363574 */:
                    a(TimeUnit.MINUTES.toMillis(60L));
                    return;
                case R.id.time_90 /* 2131363575 */:
                    a(TimeUnit.MINUTES.toMillis(90L));
                    return;
                default:
                    return;
            }
        } else {
            a(-1L);
        }
    }

    private void a(long j) {
        setVisibility(8);
        OnTimePickedListener onTimePickedListener = this.a;
        if (onTimePickedListener != null) {
            onTimePickedListener.onTimePicked(j);
        }
    }

    public void setTimePickerListener(OnTimePickedListener onTimePickedListener) {
        this.a = onTimePickedListener;
    }
}
