package com.xiaomi.micolauncher.skills.translation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.event.TranslationPlayFinishEvent;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.skills.translation.view.TranslationFragment;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class TranslationFragment extends BaseFragment {
    public static final String KEY_AUTO_FINISH = "autoFinish";
    public static final String KEY_DIALOG_ID = "dialogId";
    public static final String KEY_SOURCE_TEXT = "sourceText";
    public static final String KEY_TARGET_TEXT = "targetText";
    private String b;
    public static final long AUTO_FINISH_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(7);
    private static final int[] a = {R.color.color_00857E, R.color.color_5785EB, R.color.color_6F40E4};

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_translation, viewGroup, false);
        inflate.setBackgroundColor(getContext().getColor(a[new Random().nextInt(a.length)]));
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.b = arguments.getString(KEY_DIALOG_ID);
            a(new a((TextView) view.findViewById(R.id.source_text), arguments.getString(KEY_SOURCE_TEXT), a(R.dimen.text_size_40), a(R.dimen.text_size_34)), new a((TextView) view.findViewById(R.id.target_text), arguments.getString(KEY_TARGET_TEXT), a(R.dimen.text_size_66), a(R.dimen.text_size_40)), arguments.getBoolean(KEY_AUTO_FINISH) ? new Runnable() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationFragment$QdJzv2YbsdcSGhkWaEDMaj_NPG4
                @Override // java.lang.Runnable
                public final void run() {
                    TranslationFragment.this.a();
                }
            } : null);
        }
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        TrackStat.postTransExpose(this.b);
    }

    private float a(int i) {
        return getContext().getResources().getDimensionPixelSize(i);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    private void a(final a aVar, final a aVar2, final Runnable runnable) {
        UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationFragment$DbYTa7f4cxKusUacNsGkTeqRwyM
            @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
            public final void onLayoutComplete(boolean z) {
                TranslationFragment.a(TranslationFragment.a.this, aVar2, runnable, z);
            }
        }, aVar.a, aVar2.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(a aVar, a aVar2, Runnable runnable, boolean z) {
        boolean b = aVar.b();
        boolean b2 = aVar2.b();
        if (b || b2) {
            aVar.a(false);
            aVar2.a(false);
        }
        aVar.a();
        aVar2.a();
        if (runnable != null) {
            runnable.run();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTranslationPlayFinish(TranslationPlayFinishEvent translationPlayFinishEvent) {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationFragment$m1y1YuGe9CnfC34gOm-zwTfhuGk
            @Override // java.lang.Runnable
            public final void run() {
                TranslationFragment.this.b();
            }
        }, AUTO_FINISH_DELAY_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isDestroyed()) {
            activity.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a {
        TextView a;
        String b;
        float c;
        float d;

        a(TextView textView, String str, float f, float f2) {
            this.a = textView;
            this.b = str;
            this.c = f;
            this.d = f2;
        }

        public void a(boolean z) {
            UiUtils.setTextSizeInPx(this.a, z ? this.c : this.d);
        }

        public void a() {
            this.a.setText(this.b);
        }

        boolean b() {
            a(true);
            int width = this.a.getWidth();
            int measureText = (int) this.a.getPaint().measureText(this.b);
            XLog.v("With big size font, measure text size %s,  text view width %s", Integer.valueOf(measureText), Integer.valueOf(width));
            return measureText > width;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayerStateChangeEvent(AudioPlayerStateChangeEvent audioPlayerStateChangeEvent) {
        L.base.i("event.audioState=%s", audioPlayerStateChangeEvent);
        MicoMultiAudioPlayer.AudioState audioState = audioPlayerStateChangeEvent.audioState;
        if ((audioState == MicoMultiAudioPlayer.AudioState.LIST_FINISH || audioState == MicoMultiAudioPlayer.AudioState.STOPPED || audioState == MicoMultiAudioPlayer.AudioState.PAUSED || audioState == MicoMultiAudioPlayer.AudioState.SUSPEND) && audioPlayerStateChangeEvent.dialogId != null && audioPlayerStateChangeEvent.dialogId.equalsIgnoreCase(this.b)) {
            a();
            MicoMultiAudioPlayer.getInstance().release(this.b);
        }
    }
}
