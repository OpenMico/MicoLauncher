package com.allenliu.versionchecklib.v2.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;

/* loaded from: classes.dex */
public class DownloadingActivity extends AllenBaseActivity implements DialogInterface.OnCancelListener {
    public static final String PROGRESS = "progress";
    private Dialog a;
    private int b = 0;
    protected boolean isDestroy = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ALog.e("loading activity create");
        d();
    }

    public void onCancel(boolean z) {
        if (!z) {
            AllenHttp.getHttpClient().dispatcher().cancelAll();
            cancelHandler();
            checkForceUpdate();
        }
        finish();
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        onCancel(false);
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void receiveEvent(CommonEvent commonEvent) {
        switch (commonEvent.getEventType()) {
            case 100:
                this.b = ((Integer) commonEvent.getData()).intValue();
                c();
                return;
            case 101:
                onCancel(true);
                return;
            case 102:
                b();
                return;
            default:
                return;
        }
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showDefaultDialog() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.downloading_layout, (ViewGroup) null);
        this.a = new AlertDialog.Builder(this).setTitle("").setView(inflate).create();
        if (getVersionBuilder().getForceUpdateListener() != null) {
            this.a.setCancelable(false);
        } else {
            this.a.setCancelable(true);
        }
        this.a.setCanceledOnTouchOutside(false);
        ((TextView) inflate.findViewById(R.id.tv_progress)).setText(String.format(getString(R.string.versionchecklib_progress), Integer.valueOf(this.b)));
        ((ProgressBar) inflate.findViewById(R.id.pb)).setProgress(this.b);
        this.a.show();
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showCustomDialog() {
        if (getVersionBuilder() != null) {
            this.a = getVersionBuilder().getCustomDownloadingDialogListener().getCustomDownloadingDialog(this, this.b, getVersionBuilder().getVersionBundle());
            View findViewById = this.a.findViewById(R.id.versionchecklib_loading_dialog_cancel);
            if (findViewById != null) {
                findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.DownloadingActivity.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        DownloadingActivity.this.onCancel(false);
                    }
                });
            }
            this.a.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        a();
        this.isDestroy = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isDestroy = false;
        Dialog dialog = this.a;
        if (dialog != null && !dialog.isShowing()) {
            this.a.show();
        }
    }

    private void a() {
        Dialog dialog = this.a;
        if (dialog != null && dialog.isShowing()) {
            this.a.dismiss();
        }
    }

    private void b() {
        ALog.e("loading activity destroy");
        Dialog dialog = this.a;
        if (dialog != null && dialog.isShowing()) {
            this.a.dismiss();
        }
        finish();
    }

    private void c() {
        if (this.isDestroy) {
            return;
        }
        if (getVersionBuilder() == null || getVersionBuilder().getCustomDownloadingDialogListener() == null) {
            ((ProgressBar) this.a.findViewById(R.id.pb)).setProgress(this.b);
            ((TextView) this.a.findViewById(R.id.tv_progress)).setText(String.format(getString(R.string.versionchecklib_progress), Integer.valueOf(this.b)));
            if (!this.a.isShowing()) {
                this.a.show();
                return;
            }
            return;
        }
        getVersionBuilder().getCustomDownloadingDialogListener().updateUI(this.a, this.b, getVersionBuilder().getVersionBundle());
    }

    private void d() {
        ALog.e("show loading");
        if (!this.isDestroy) {
            if (getVersionBuilder() == null || getVersionBuilder().getCustomDownloadingDialogListener() == null) {
                showDefaultDialog();
            } else {
                showCustomDialog();
            }
            this.a.setOnCancelListener(this);
        }
    }
}
