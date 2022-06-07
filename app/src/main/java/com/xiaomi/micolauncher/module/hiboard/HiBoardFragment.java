package com.xiaomi.micolauncher.module.hiboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.widget.HiboardItem;
import com.xiaomi.micolauncher.module.miot.MiotMainActivity;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmListActivity;
import com.xiaomi.micolauncher.skills.music.PlayerApi;

/* loaded from: classes3.dex */
public class HiBoardFragment extends BaseFragment {
    HiboardItem a;
    HiboardItem b;
    HiboardItem c;
    HiboardItem d;
    HiboardItem e;
    HiboardItem f;
    HiboardItem g;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_hiboard, viewGroup, false);
        this.a = (HiboardItem) inflate.findViewById(R.id.call);
        this.b = (HiboardItem) inflate.findViewById(R.id.message);
        this.c = (HiboardItem) inflate.findViewById(R.id.miot);
        this.d = (HiboardItem) inflate.findViewById(R.id.music);
        this.e = (HiboardItem) inflate.findViewById(R.id.calendar);
        this.f = (HiboardItem) inflate.findViewById(R.id.note);
        this.g = (HiboardItem) inflate.findViewById(R.id.open_platform);
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.hiboard.-$$Lambda$sPJE9A8rATT6Wi010YcbcFXZ5dI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HiBoardFragment.this.onViewClicked(view);
            }
        });
        a();
        inflate.setTag(this);
        return inflate;
    }

    private void a() {
        int foregroundColor = SystemSetting.getWallpaper().getThemeStyle().getForegroundColor(getContext());
        this.a.setItemTextColor(foregroundColor);
        this.b.setItemTextColor(foregroundColor);
        this.c.setItemTextColor(foregroundColor);
        this.d.setItemTextColor(foregroundColor);
        this.e.setItemTextColor(foregroundColor);
        this.f.setItemTextColor(foregroundColor);
        this.g.setItemTextColor(foregroundColor);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calendar /* 2131362115 */:
                if (AlarmModel.getInstance().getAlarmSize() > 0) {
                    startActivity(new Intent(getContext(), AlarmListActivity.class));
                    return;
                } else {
                    Toast.makeText(getContext(), "闹钟列表为空。", 1).show();
                    return;
                }
            case R.id.call /* 2131362116 */:
            case R.id.message /* 2131362896 */:
            case R.id.note /* 2131363022 */:
            default:
                return;
            case R.id.miot /* 2131362907 */:
                startActivity(new Intent(getContext(), MiotMainActivity.class));
                return;
            case R.id.music /* 2131362980 */:
                PlayerApi.playOrPlayRecommend();
                return;
        }
    }
}
