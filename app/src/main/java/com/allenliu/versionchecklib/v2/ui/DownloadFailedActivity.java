package com.allenliu.versionchecklib.v2.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AllenEventBusUtil;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;

/* loaded from: classes.dex */
public class DownloadFailedActivity extends AllenBaseActivity implements DialogInterface.OnCancelListener {
    private Dialog a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showDefaultDialog() {
        this.a = new AlertDialog.Builder(this).setMessage(getString(R.string.versionchecklib_download_fail_retry)).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.DownloadFailedActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                DownloadFailedActivity.this.b();
            }
        }).setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.DownloadFailedActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                DownloadFailedActivity downloadFailedActivity = DownloadFailedActivity.this;
                downloadFailedActivity.onCancel(downloadFailedActivity.a);
            }
        }).create();
        this.a.setCanceledOnTouchOutside(false);
        this.a.setCancelable(true);
        this.a.show();
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showCustomDialog() {
        if (getVersionBuilder() != null) {
            this.a = getVersionBuilder().getCustomDownloadFailedListener().getCustomDownloadFailed(this, getVersionBuilder().getVersionBundle());
            View findViewById = this.a.findViewById(R.id.versionchecklib_failed_dialog_retry);
            if (findViewById != null) {
                findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.DownloadFailedActivity.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        DownloadFailedActivity.this.b();
                    }
                });
            }
            View findViewById2 = this.a.findViewById(R.id.versionchecklib_failed_dialog_cancel);
            if (findViewById2 != null) {
                findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.DownloadFailedActivity.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        DownloadFailedActivity downloadFailedActivity = DownloadFailedActivity.this;
                        downloadFailedActivity.onCancel(downloadFailedActivity.a);
                    }
                });
            }
            this.a.show();
        }
    }

    private void a() {
        AllenEventBusUtil.sendEventBus(102);
        if (getVersionBuilder() == null || getVersionBuilder().getCustomDownloadFailedListener() == null) {
            ALog.e("show default failed dialog");
            showDefaultDialog();
        } else {
            ALog.e("show customization failed dialog");
            showCustomDialog();
        }
        this.a.setOnCancelListener(this);
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        ALog.e("on cancel");
        cancelHandler();
        checkForceUpdate();
        AllenVersionChecker.getInstance().cancelAllMission(this);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        AllenEventBusUtil.sendEventBus(98);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Dialog dialog = this.a;
        if (dialog != null && dialog.isShowing()) {
            this.a.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Dialog dialog = this.a;
        if (dialog != null && !dialog.isShowing()) {
            this.a.show();
        }
    }
}
