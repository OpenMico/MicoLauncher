package com.xiaomi.micolauncher.module.homepage.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class PhoneOverlayFragment extends LauncherOverlayFragment {
    private ConstraintLayout a;

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.LauncherOverlayFragment
    protected String getOverlayPackageName() {
        return Constants.MICO_VOIP_PKG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.LauncherOverlayFragment
    public View onCreateHolderView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_phone, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.a = (ConstraintLayout) view.findViewById(R.id.phone_loading);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.LauncherOverlayFragment, com.xiaomi.micolauncher.overlay.client.LauncherClientCallback
    public void onOverlayStateChanged(boolean z) {
        super.onOverlayStateChanged(z);
        if (z) {
            this.a.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.PhoneOverlayFragment.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PhoneOverlayFragment.this.a.setVisibility(8);
                }
            }).start();
        } else {
            this.a.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.PhoneOverlayFragment.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PhoneOverlayFragment.this.a.setVisibility(0);
                }
            }).start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetRecyclerViewPosition(ResetScrollViewPositionEvent resetScrollViewPositionEvent) {
        if (!isDetached()) {
            getActivity().sendBroadcast(new Intent(Constants.ACTION_RESET_VIEW_POSITION));
        }
    }
}
