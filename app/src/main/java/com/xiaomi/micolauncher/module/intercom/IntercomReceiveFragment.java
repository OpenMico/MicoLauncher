package com.xiaomi.micolauncher.module.intercom;

import android.app.UiModeManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.module.intercom.PushIntercom;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class IntercomReceiveFragment extends IntercomBaseFragment implements View.OnClickListener, PushIntercom.a {
    private LottieAnimationView a;
    private volatile boolean b = true;
    private boolean c = false;
    private IntercomReceiveModel d;

    public static IntercomReceiveFragment newInstance(@NonNull IntercomReceiveModel intercomReceiveModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", intercomReceiveModel);
        IntercomReceiveFragment intercomReceiveFragment = new IntercomReceiveFragment();
        intercomReceiveFragment.setArguments(bundle);
        return intercomReceiveFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    @org.jetbrains.annotations.Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater layoutInflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup viewGroup, @Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_intercom_receive, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.sub_title);
        TextView textView3 = (TextView) inflate.findViewById(R.id.play_btn_text);
        this.d = (IntercomReceiveModel) getArguments().getParcelable("model");
        String fromStr = this.d.getFromStr();
        if (fromStr.length() > 18) {
            fromStr = fromStr.substring(0, 17) + "...";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getString(R.string.intercom_record_receive_from, fromStr));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.intercom_record_time_color, null)), 4, fromStr.length() + 5, 18);
        textView.setText(spannableStringBuilder);
        long j = this.d.receivedTime;
        if (j == 0) {
            j = System.currentTimeMillis();
        }
        textView2.setText(TimeUtils.transformTimestamp(j, "aHH:mm"));
        textView3.setText((this.d.audioDuration / 1000) + "\"");
        this.a = (LottieAnimationView) inflate.findViewById(R.id.play_btn_img);
        inflate.findViewById(R.id.play_btn).setOnClickListener(this);
        inflate.findViewById(R.id.reply_btn).setOnClickListener(this);
        List<IntercomReceiveModel> pushModelList = PushIntercom.getInstance().getPushModelList();
        if (!pushModelList.isEmpty()) {
            int i = 0;
            while (true) {
                if (i >= pushModelList.size()) {
                    i = -1;
                    break;
                }
                IntercomReceiveModel intercomReceiveModel = pushModelList.get(i);
                if (intercomReceiveModel != null && this.d.message_id.equals(intercomReceiveModel.message_id)) {
                    break;
                }
                i++;
            }
            if (i != -1) {
                PushIntercom.getInstance().playAudioByPushModel(pushModelList.remove(i));
            }
        }
        L.push.w("IntercomReceiveFragment onCreateView，cancel push notificationId=%s", Integer.valueOf(this.d.getNotificationId()));
        PushIntercom.getInstance().cancelPushNotificationById(getContext(), this.d.getNotificationId());
        a();
        PushIntercom.getInstance().uploadMsgStatus(this.d.message_id, "read");
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.module.intercom.IntercomBaseFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        L.push.i("IntercomReceiveFragment onStart notification id=%s", Integer.valueOf(this.d.getNotificationId()));
        super.onStart();
        PushIntercom.getInstance().setShowNotification(true);
        PushIntercom.getInstance().setListener(this);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        L.push.i("IntercomReceiveFragment onStop notification id=%s", Integer.valueOf(this.d.getNotificationId()));
        super.onStop();
        dismissMicMuteDialog();
        PushIntercom.getInstance().setShowNotification(false);
        PushIntercom.getInstance().setListener(null);
        if (this.c) {
            PushIntercom.getInstance().autoProcessNextPush(1);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.play_btn) {
            PushIntercom.getInstance().cancelProcessNextPush();
            if (this.b) {
                b();
                PushIntercom.getInstance().pausePlay();
                this.c = true;
            } else {
                a();
                PushIntercom.getInstance().rePlay();
                this.c = false;
            }
            this.b = !this.b;
        } else if (id == R.id.reply_btn) {
            if (Mic.getInstance().isMicMute()) {
                showMicMuteDialog();
                return;
            }
            PushIntercom.getInstance().cancelProcessNextPush();
            ((IntercomActivity) getActivity()).detachSendFragment();
            showFragment(1, IntercomRecordFragment.newInstance(true, this.d), false);
        }
    }

    @Override // com.xiaomi.micolauncher.module.intercom.PushIntercom.a
    public void onAllPushProcessComplete() {
        this.b = false;
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomReceiveFragment$26UIs4UDHjhxvS0uLotRPZXAaJE
            @Override // java.lang.Runnable
            public final void run() {
                IntercomReceiveFragment.this.c();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        b();
        scheduleToClose();
    }

    private void a() {
        if (((UiModeManager) getActivity().getApplicationContext().getSystemService("uimode")).getNightMode() != 2) {
            this.a.setAnimation("record/audio_record_play_ani_red.json");
        } else {
            this.a.setAnimation("record/audio_record_play_ani_blue.json");
        }
        this.a.setRepeatCount(-1);
        this.a.playAnimation();
    }

    private void b() {
        this.a.pauseAnimation();
        this.a.clearAnimation();
        this.a.setImageResource(R.drawable.intercom_audio_play);
    }
}
