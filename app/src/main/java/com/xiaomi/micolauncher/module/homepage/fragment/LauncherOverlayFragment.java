package com.xiaomi.micolauncher.module.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.elvishew.xlog.Logger;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.overlay.client.LauncherClient;
import com.xiaomi.micolauncher.overlay.client.LauncherClientCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class LauncherOverlayFragment extends Fragment implements View.OnAttachStateChangeListener, LauncherClientCallback {
    private View a;
    private LauncherClient b;
    private boolean c = false;

    protected abstract String getOverlayPackageName();

    public abstract View onCreateHolderView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle);

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        L.launcheroverlay.d("LauncherClient create.");
        this.b = new LauncherClient(requireActivity(), getOverlayPackageName(), this);
        EventBusRegistry.getEventBus().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public final View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = onCreateHolderView(layoutInflater, viewGroup, bundle);
        this.a.addOnAttachStateChangeListener(this);
        return this.a;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.tab_width);
        FragmentActivity requireActivity = requireActivity();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(requireActivity.getWindow().getAttributes());
        layoutParams.width = requireActivity.getResources().getDisplayMetrics().widthPixels - dimensionPixelSize;
        layoutParams.height = -1;
        layoutParams.x = dimensionPixelSize;
        layoutParams.gravity = BadgeDrawable.TOP_START;
        L.launcheroverlay.d("LauncherClient onAttachedToWindow.");
        this.b.onAttachedToWindow(layoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.b.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        L.launcheroverlay.d("LauncherClient onResume.");
        this.b.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        L.launcheroverlay.d("LauncherClient onPause.");
        this.b.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        L.launcheroverlay.d("LauncherClient onStop.");
        this.b.onStop();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        L.launcheroverlay.d("LauncherClient onDetachedFormWindow.");
        this.b.onDetachedFormWindow();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.a.removeOnAttachStateChangeListener(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBusRegistry.getEventBus().unregister(this);
        L.launcheroverlay.d("LauncherClient onDestroy.");
        this.b.onDestroy();
    }

    public String identifier() {
        return getClass().getSimpleName();
    }

    @Override // com.xiaomi.micolauncher.overlay.client.LauncherClientCallback
    public void onOverlayStateChanged(boolean z) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherClientCallback onOverlayStateChanged: overlayAttached: " + z + ", mWasOverlayAttached: " + this.c);
        if (z != this.c) {
            this.c = z;
            Logger logger2 = L.launcheroverlay;
            logger2.d("LauncherOverlayFragment is hide ? " + isHidden());
            if (isHidden()) {
                return;
            }
            if (z) {
                this.b.showOverlay(true);
            } else {
                this.b.reconnect();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.overlay.client.LauncherClientCallback
    public void onOverlayTransitionStart(float f) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherClientCallback onOverlayTransitionStart: " + f);
    }

    @Override // com.xiaomi.micolauncher.overlay.client.LauncherClientCallback
    public void onOverlayTransitionChanged(float f) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherClientCallback onOverlayTransitionChanged: " + f);
    }

    @Override // com.xiaomi.micolauncher.overlay.client.LauncherClientCallback
    public void onOverlayTransitionEnd(float f) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherClientCallback onOverlayTransitionEnd: " + f);
        if (!isHidden() && f == 0.0f) {
            a(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherOverlayFragment onHiddenChanged: " + z);
        super.onHiddenChanged(z);
        a(z ^ true);
    }

    private void a(boolean z) {
        Logger logger = L.launcheroverlay;
        logger.d("LauncherOverlayFragment switchOverlay: " + z + ", mWasOverlayAttached: " + this.c);
        if (!z) {
            this.b.hideOverlay(true);
        } else if (this.c) {
            this.b.showOverlay(true);
        } else {
            this.b.reconnect();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        LauncherClient launcherClient = this.b;
        if (launcherClient != null) {
            launcherClient.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onNewIntent(Intent intent) {
        L.launcheroverlay.d("LauncherOverlayFragment onNewIntent.");
        LauncherClient launcherClient = this.b;
        if (launcherClient != null) {
            launcherClient.onNewIntent(intent);
        }
    }
}
