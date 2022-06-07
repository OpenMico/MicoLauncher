package com.xiaomi.micolauncher.module.intercom;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.base.utils.WeakHandler;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.appstore.NotifyMessageDialog;
import com.xiaomi.micolauncher.module.intercom.IntercomSendModel;
import com.xiaomi.micolauncher.module.intercom.SendIntercom;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import io.reactivex.functions.Consumer;
import java.io.File;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class IntercomSendFragment extends IntercomBaseFragment implements View.OnClickListener, SendIntercom.a {
    private TextView a;
    private LottieAnimationView b;
    private NotifyMessageDialog c;
    private CustomDialog d;
    private BaseExoPlayer e;
    private int f;
    private IntercomReceiveModel g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    private File l;
    private WeakHandler m;
    private int n = 0;

    public static IntercomSendFragment newInstance(IntercomReceiveModel intercomReceiveModel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", intercomReceiveModel);
        bundle.putInt("time", i);
        IntercomSendFragment intercomSendFragment = new IntercomSendFragment();
        intercomSendFragment.setArguments(bundle);
        return intercomSendFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        super.onCreate(bundle);
        RecordManager.getInstance().setRecordResultListener(new RecordResultListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomSendFragment$Dc8c8g_CpLxgIbRppSXTvnws5S8
            @Override // com.zlw.main.recorderlib.recorder.listener.RecordResultListener
            public final void onResult(File file) {
                IntercomSendFragment.this.a(file);
            }
        });
        this.e = new BaseExoPlayer(AudioSource.AUDIO_SOURCE_NULL) { // from class: com.xiaomi.micolauncher.module.intercom.IntercomSendFragment.1
            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
            protected void onStarted() {
                setVolume(1.0f);
            }
        };
        this.e.setListener(new BaseExoPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.module.intercom.IntercomSendFragment.2
            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onPrepared(Player player) {
                IntercomSendFragment.this.j = true;
                player.start();
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onComplete(Player player) {
                IntercomSendFragment.this.d();
                IntercomSendFragment.this.j = false;
                IntercomSendFragment.this.i = true;
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onError(Player player, int i, Exception exc) {
                L.push.e("IntercomSendFragment local play onError error=%s extra=%s", Integer.valueOf(i), exc);
                IntercomSendFragment.this.d();
                IntercomSendFragment.this.j = false;
                IntercomSendFragment.this.i = true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(File file) {
        this.l = file;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    @org.jetbrains.annotations.Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater layoutInflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup viewGroup, @Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        String str = null;
        View inflate = layoutInflater.inflate(R.layout.fragment_intercom_send, (ViewGroup) null);
        ((IntercomActivity) getActivity()).setMarkRecordAudioIsSending(false);
        this.g = (IntercomReceiveModel) getArguments().getParcelable("model");
        IntercomReceiveModel intercomReceiveModel = this.g;
        if (intercomReceiveModel != null) {
            String fromStr = intercomReceiveModel.getFromStr();
            if (fromStr.length() > 20) {
                fromStr = fromStr.substring(0, 20) + "...";
            }
            str = getString(R.string.intercom_record_title_target, fromStr);
        }
        this.f = getArguments().getInt("time");
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        if (TextUtils.isEmpty(str)) {
            textView.setText(R.string.intercom_record_title);
        } else {
            textView.setText(str);
        }
        this.b = (LottieAnimationView) inflate.findViewById(R.id.play_btn_img);
        ((TextView) inflate.findViewById(R.id.play_btn_text)).setText(String.format("00:%02d", Integer.valueOf(this.f)));
        inflate.findViewById(R.id.cancel_btn).setOnClickListener(this);
        this.a = (TextView) inflate.findViewById(R.id.send_btn);
        this.m = new WeakHandler(new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomSendFragment$bMtfCvPqZ3ts8MVc36xeUKY65Sw
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a;
                a = IntercomSendFragment.this.a(message);
                return a;
            }
        });
        this.m.sendEmptyMessageDelayed(10, 0L);
        this.a.setOnClickListener(this);
        inflate.findViewById(R.id.play_btn).setOnClickListener(this);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Message message) {
        if (message.what == 10) {
            int i = this.n;
            if (i >= 3) {
                this.a.setText(R.string.intercom_record_send_btn_ing);
                this.a.performClick();
                return true;
            }
            this.a.setText(getString(R.string.intercom_record_send_btn_3s, Integer.valueOf(3 - i)));
            this.n++;
            this.m.sendEmptyMessageDelayed(10, 1000L);
        }
        return false;
    }

    @Override // com.xiaomi.micolauncher.module.intercom.IntercomBaseFragment, com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        SendIntercom.getInstance().setSendIntercomListener(this);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        b();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        SendIntercom.getInstance().setSendIntercomListener(null);
        this.m.removeMessages(10);
        NotifyMessageDialog notifyMessageDialog = this.c;
        if (notifyMessageDialog != null) {
            notifyMessageDialog.dismiss();
            this.c = null;
        }
        CustomDialog customDialog = this.d;
        if (customDialog != null) {
            customDialog.dismiss();
            this.d = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        c();
        if (!this.k) {
            f();
            L.push.w("IntercomSendFragment onDetach, and audioFile will delete, cause of unused");
        }
        RecordManager.getInstance().setRecordResultListener(null);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        IntercomSendModel.a aVar;
        int id = view.getId();
        if (id == R.id.cancel_btn) {
            f();
            getActivity().onBackPressed();
        } else if (id == R.id.play_btn) {
            if (this.j) {
                d();
                this.e.pause();
                this.i = true;
            } else {
                File file = this.l;
                if (file != null && file.exists()) {
                    e();
                    if (!this.h) {
                        this.m.removeMessages(10);
                        this.a.setText(R.string.button_send);
                        this.e.setDataSource(this.l.getAbsolutePath());
                        this.h = true;
                    } else if (this.i) {
                        this.e.restart();
                        this.i = false;
                    }
                }
            }
            this.j = !this.j;
        } else if (id == R.id.send_btn) {
            this.m.removeMessages(10);
            File file2 = this.l;
            if (file2 == null || !file2.exists()) {
                L.push.e("record audio file is not exist when click send button!");
                return;
            }
            b();
            this.k = true;
            ((IntercomActivity) getActivity()).setMarkRecordAudioIsSending(true);
            a();
            String str = "";
            String str2 = "";
            String aiDeviceId = SpeechEngineHelper.getAiDeviceId();
            IntercomReceiveModel intercomReceiveModel = this.g;
            if (intercomReceiveModel != null) {
                if (intercomReceiveModel.send_type.equals(Template.MessageSendType.GROUP.name())) {
                    str2 = !TextUtils.isEmpty(this.g.home_id) ? this.g.home_id : "DEFAULT";
                } else if (this.g.send_type.equals(Template.MessageSendType.SINGLE.name())) {
                    str = this.g.sender_device_id;
                }
                aVar = new IntercomSendModel.a(this.g.send_type, Template.MessageSendDeviceType.FAMILY.name(), Template.MessageSendDeviceCategory.SOUNDBOX.name(), SpeechEngineHelper.getAiDeviceId());
            } else {
                str2 = "DEFAULT";
                aVar = new IntercomSendModel.a(Template.MessageSendType.GROUP.name(), Template.MessageSendDeviceType.FAMILY.name(), Template.MessageSendDeviceCategory.SOUNDBOX.name(), SpeechEngineHelper.getAiDeviceId());
                str = str;
            }
            SendIntercom.getInstance().sendIntercom(new IntercomSendModel(this.l.getAbsolutePath(), Gsons.getGson().toJson(aVar), str, str2, "DEFAULT", aiDeviceId, this.f * 1000), false);
        }
    }

    @Override // com.xiaomi.micolauncher.module.intercom.SendIntercom.a
    public void onSendSuccess(IntercomSendModel intercomSendModel) {
        NotifyMessageDialog notifyMessageDialog = this.c;
        if (notifyMessageDialog != null) {
            notifyMessageDialog.dismiss();
            this.c = null;
        }
        getActivity().onBackPressed();
        PushIntercom.getInstance().autoProcessNextPush(0);
    }

    @Override // com.xiaomi.micolauncher.module.intercom.SendIntercom.a
    public void onSendError(final IntercomSendModel intercomSendModel, Throwable th) {
        NotifyMessageDialog notifyMessageDialog = this.c;
        if (notifyMessageDialog != null) {
            notifyMessageDialog.dismiss();
            this.c = null;
        }
        if (Hardware.isBigScreen()) {
            if (this.d == null) {
                this.d = new CustomDialog.Builder().setTitleResId(R.string.intercom_record_send_error_title).setMessageResId(R.string.intercom_record_send_error_msg).setPositiveResId(R.string.intercom_record_send_error_positive).setNegativeResId(R.string.intercom_record_send_error_negative).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomSendFragment$PdUslxlR6OGc45eLq8-yZUGUzqs
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        IntercomSendFragment.this.a(intercomSendModel, view);
                    }
                }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomSendFragment$8wWz1awXSateoqy_HpVD4_3edRU
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        IntercomSendFragment.this.a(view);
                    }
                }).setHasNegativeButton(true).build();
            }
            scheduleToClose();
            this.a.setText(R.string.button_send);
            this.d.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(IntercomSendModel intercomSendModel, View view) {
        this.a.setText(R.string.intercom_record_send_btn_ing);
        a();
        SendIntercom.getInstance().sendIntercom(intercomSendModel, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        f();
        getActivity().onBackPressed();
    }

    private void a() {
        if (this.c == null) {
            this.c = new NotifyMessageDialog(getContext());
            this.c.setCanceledOnTouchOutside(false);
            this.c.setMessage(R.string.intercom_record_sending);
        }
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            this.c.show();
        }
    }

    private void b() {
        d();
        this.e.pause();
        this.i = true;
        this.j = false;
        this.h = false;
    }

    private void c() {
        this.e.stop();
        this.e.release();
        this.e.setListener((BaseExoPlayer.PlayerListener) null);
        this.e = null;
        this.i = false;
        this.j = false;
        this.h = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.b.pauseAnimation();
        this.b.clearAnimation();
        this.b.setImageResource(R.drawable.intercom_audio_play);
    }

    private void e() {
        if (((UiModeManager) getActivity().getApplicationContext().getSystemService("uimode")).getNightMode() != 2) {
            this.b.setAnimation("record/audio_record_play_ani_red.json");
        } else {
            this.b.setAnimation("record/audio_record_play_ani_blue.json");
        }
        this.b.setRepeatCount(-1);
        this.b.playAnimation();
    }

    @SuppressLint({"CheckResult"})
    private void f() {
        File file = this.l;
        if (file != null && file.exists()) {
            Util.asyncDeleteFile(this.l).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomSendFragment$-poDkdwKSAl_A7iifmMsWIQOqRA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IntercomSendFragment.this.a((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) throws Exception {
        this.l = null;
    }
}
