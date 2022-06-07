package com.xiaomi.micolauncher.common.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class LoadingDialog extends Dialog {
    private LinearLayout a;
    private LottieAnimationView b;
    private ImageView c;
    private TextView d;
    private a e;
    private LoadingListener f;

    /* loaded from: classes3.dex */
    public interface LoadingListener {
        void onRetry();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum a {
        LOADING,
        RETRY
    }

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, LoadingListener loadingListener) {
        super(context);
        this.f = loadingListener;
    }

    public LoadingDialog(@NonNull Context context, int i) {
        super(context, i);
    }

    public LoadingDialog(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.view_loading_dialog);
        a();
        b();
    }

    private void a() {
        this.a = (LinearLayout) findViewById(R.id.view_group);
        this.b = (LottieAnimationView) findViewById(R.id.lottie_view);
        this.c = (ImageView) findViewById(R.id.retry_btn);
        this.d = (TextView) findViewById(R.id.loading_tv);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.view.-$$Lambda$LoadingDialog$fSj3iIHzbRHHQECyMRicUX_DzeI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoadingDialog.this.b(view);
            }
        });
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.view.-$$Lambda$LoadingDialog$C8ULo9gU_-JigWiAnKUs602kidI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoadingDialog.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        showLoading();
        LoadingListener loadingListener = this.f;
        if (loadingListener != null) {
            loadingListener.onRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        if (this.e == a.RETRY) {
            dismiss();
        }
    }

    private void b() {
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.black_50_transparent);
            window.setDimAmount(0.0f);
            window.setType(2038);
            window.clearFlags(2);
            window.addFlags(8);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 17;
            attributes.width = -1;
            attributes.height = -1;
            window.setAttributes(attributes);
        }
        setCancelable(true);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        showLoading();
    }

    public void showLoading() {
        this.e = a.LOADING;
        this.b.setVisibility(0);
        this.c.setVisibility(8);
        this.d.setText(R.string.common_loading_title);
    }

    public void showError() {
        this.e = a.RETRY;
        this.b.setVisibility(8);
        this.c.setVisibility(0);
        this.d.setText(R.string.common_loading_error_title);
    }
}
