package com.xiaomi.micolauncher.module.homelock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.module.SmartHomeLockControl;
import com.xiaomi.micolauncher.module.setting.widget.InputPassView;

/* loaded from: classes3.dex */
public class SmartHomeLockFragment extends Fragment {
    public static final String TAG = "SmartHomeLockFragment";
    private InputPassView a;
    private View b;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.b = layoutInflater.inflate(R.layout.fragment_smart_home_lock, viewGroup, false);
        a();
        return this.b;
    }

    private void a() {
        this.a = (InputPassView) this.b.findViewById(R.id.input_pass_view);
        this.a.setOnInputPassClickListener(new InputPassView.OnInputPassClickListener() { // from class: com.xiaomi.micolauncher.module.homelock.-$$Lambda$SmartHomeLockFragment$GuKZizX1CrIAdVyuOqFbGXiDA_4
            @Override // com.xiaomi.micolauncher.module.setting.widget.InputPassView.OnInputPassClickListener
            public final void onInputDone(String str, boolean z) {
                SmartHomeLockFragment.this.a(str, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, boolean z) {
        if (LockSetting.getLockPass(getContext()).equals(str)) {
            this.a.setSuccess();
            SmartHomeLockControl.getIns().setNeedShowLock(false);
            this.a.reset();
            EventBusRegistry.getEventBus().post(new StartMiotUiEvent());
            return;
        }
        this.a.setFailed();
    }

    public void setTip(String str) {
        this.a.setTip(str);
    }
}
