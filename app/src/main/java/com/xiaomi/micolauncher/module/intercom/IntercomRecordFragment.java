package com.xiaomi.micolauncher.module.intercom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.WeakHandler;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.zlw.main.recorderlib.RecordManager;
import java.io.File;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class IntercomRecordFragment extends IntercomBaseFragment {
    private boolean a = true;
    private boolean b = false;
    private int c;
    private WeakHandler d;

    public static IntercomRecordFragment newInstance(boolean z, IntercomReceiveModel intercomReceiveModel) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("reply", z);
        bundle.putParcelable("model", intercomReceiveModel);
        IntercomRecordFragment intercomRecordFragment = new IntercomRecordFragment();
        intercomRecordFragment.setArguments(bundle);
        return intercomRecordFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    @org.jetbrains.annotations.Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater layoutInflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup viewGroup, @Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        String str = null;
        View inflate = layoutInflater.inflate(R.layout.fragment_intercom_record, (ViewGroup) null);
        SpeechManager.getInstance().setUnWakeup();
        SpeechManager.getInstance().setVpmWorking(false);
        TextView textView = (TextView) inflate.findViewById(R.id.title);
        boolean z = getArguments().getBoolean("reply");
        final IntercomReceiveModel intercomReceiveModel = (IntercomReceiveModel) getArguments().getParcelable("model");
        if (intercomReceiveModel != null) {
            String fromStr = intercomReceiveModel.getFromStr();
            if (fromStr.length() > 20) {
                fromStr = fromStr.substring(0, 20) + "...";
            }
            str = getString(R.string.intercom_record_title_target, fromStr);
        }
        if (!z || TextUtils.isEmpty(str)) {
            textView.setText(R.string.intercom_record_title);
        } else {
            textView.setText(str);
        }
        RecordManager.getInstance().start();
        final TextView textView2 = (TextView) inflate.findViewById(R.id.record_time);
        this.d = new WeakHandler(new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomRecordFragment$UA5bu25Z-MnO_euGPNd4qrDPsP4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a;
                a = IntercomRecordFragment.this.a(intercomReceiveModel, textView2, message);
                return a;
            }
        });
        this.d.sendEmptyMessageDelayed(20, 0L);
        ((ImageView) inflate.findViewById(R.id.record_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomRecordFragment$DvNZ-Kw1CAra8FDzooDpudfCbRQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IntercomRecordFragment.this.a(intercomReceiveModel, view);
            }
        });
        return inflate;
    }

    public /* synthetic */ boolean a(IntercomReceiveModel intercomReceiveModel, TextView textView, Message message) {
        if (message.what == 20) {
            int i = this.c;
            if (i >= 30) {
                a(intercomReceiveModel);
                return true;
            }
            this.c = i + 1;
            textView.setText(String.format("0:%02d", Integer.valueOf(this.c)));
            this.d.sendEmptyMessageDelayed(20, 1000L);
        }
        return false;
    }

    public /* synthetic */ void a(IntercomReceiveModel intercomReceiveModel, View view) {
        a(intercomReceiveModel);
    }

    private void a(IntercomReceiveModel intercomReceiveModel) {
        RecordManager.getInstance().stop();
        this.d.removeMessages(20);
        this.a = false;
        this.b = true;
        showFragment(2, IntercomSendFragment.newInstance(intercomReceiveModel, this.c), true);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        RecordManager.getInstance().stop();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.d.removeMessages(20);
        if (!this.b) {
            RecordManager.getInstance().setRecordResultListener($$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938.INSTANCE);
        }
        if (this.a) {
            ToastUtil.showToast("录音已停止");
        }
    }

    public static /* synthetic */ void a(File file) {
        file.delete();
        L.push.w("record audio file is not used, so delete it onStop");
        RecordManager.getInstance().setRecordResultListener(null);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        SpeechManager.getInstance().setVpmWorking(true);
        super.onDestroyView();
    }
}
