package com.xiaomi.smarthome.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes4.dex */
public abstract class BaseFragment extends Fragment {
    private Handler a;
    private long b;
    private String c;
    protected Context context;
    private int d;

    protected void initViews() {
    }

    protected void loadData() {
    }

    protected void observeData() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Configs.Instance.setupRxJavaErrorHandler();
        this.a = new Handler(Looper.getMainLooper());
        this.d = Process.myTid();
        L.lifecycle.d("%s onCreate ", this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        L.lifecycle.d("%s onCreateView ", this);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        L.lifecycle.d("%s onStart ", this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        L.lifecycle.d("%s onResume ", this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        L.lifecycle.d("%s onPause ", this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        L.lifecycle.d("%s onStop ", this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        L.lifecycle.d("%s onDestroyView ", this);
    }

    protected void showToast(String str) {
        showToast(str, false);
    }

    protected void showToast(final String str, final boolean z) {
        if (str != null) {
            if (System.currentTimeMillis() - this.b >= SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS || !str.equals(this.c)) {
                if (this.d != Process.myTid()) {
                    post(new Runnable() { // from class: com.xiaomi.smarthome.base.-$$Lambda$BaseFragment$Npob7TSMRisEtbqoXzr-zv0tQ_o
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseFragment.this.a(z, str);
                        }
                    });
                } else if (z) {
                    Toast.makeText(this.context, str, 1).show();
                } else {
                    Toast.makeText(this.context, str, 0).show();
                }
                this.b = System.currentTimeMillis();
                this.c = str;
            }
        }
    }

    public /* synthetic */ void a(boolean z, String str) {
        if (z) {
            Toast.makeText(this.context, str, 1).show();
        } else {
            Toast.makeText(this.context, str, 0).show();
        }
    }

    protected void post(Runnable runnable) {
        this.a.post(runnable);
    }

    protected boolean isValidContext(Activity activity) {
        return !activity.isDestroyed() && !activity.isFinishing();
    }

    protected AutoDisposeConverter<?> autoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this));
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        L.lifecycle.d("%s onDestroy ", this);
    }
}
