package com.xiaomi.micolauncher.module.miot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.LauncherFragment;
import com.xiaomi.miot.support.ui.grid.MiotDeviceGridView;

/* loaded from: classes3.dex */
public class MiotFragment extends LauncherFragment {
    public static int sDeviceCount;
    MiotDeviceGridView a;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_miot, viewGroup, false);
        this.a = (MiotDeviceGridView) inflate.findViewById(R.id.device_list);
        inflate.setTag(this);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        MiotDeviceGridView miotDeviceGridView = this.a;
        if (miotDeviceGridView != null) {
            miotDeviceGridView.refreshDevices();
        }
    }
}
