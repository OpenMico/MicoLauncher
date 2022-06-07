package com.allenliu.versionchecklib.v2.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AllenEventBusUtil;
import com.allenliu.versionchecklib.utils.AppUtils;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;
import java.io.File;

/* loaded from: classes.dex */
public class UIActivity extends AllenBaseActivity implements DialogInterface.OnCancelListener {
    private Dialog a;
    private boolean b = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ALog.e("version activity create");
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.b = true;
        ALog.e("version activity destroy");
        super.onDestroy();
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void receiveEvent(CommonEvent commonEvent) {
        if (commonEvent.getEventType() == 97) {
            a();
        }
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showDefaultDialog() {
        if (getVersionBuilder() != null) {
            UIData versionBundle = getVersionBuilder().getVersionBundle();
            String str = "提示";
            String str2 = "检测到新版本";
            if (versionBundle != null) {
                str = versionBundle.getTitle();
                str2 = versionBundle.getContent();
            }
            AlertDialog.Builder positiveButton = new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.UIActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    UIActivity.this.b();
                }
            });
            if (getVersionBuilder().getForceUpdateListener() == null) {
                positiveButton.setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.UIActivity.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UIActivity uIActivity = UIActivity.this;
                        uIActivity.onCancel(uIActivity.a);
                    }
                });
                positiveButton.setCancelable(false);
            } else {
                positiveButton.setCancelable(false);
            }
            this.a = positiveButton.create();
            this.a.setCanceledOnTouchOutside(false);
            this.a.show();
        }
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showCustomDialog() {
        if (getVersionBuilder() != null) {
            ALog.e("show customization dialog");
            this.a = getVersionBuilder().getCustomVersionDialogListener().getCustomVersionDialog(this, getVersionBuilder().getVersionBundle());
            try {
                View findViewById = this.a.findViewById(R.id.versionchecklib_version_dialog_commit);
                if (findViewById != null) {
                    ALog.e("view not null");
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.UIActivity.3
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ALog.e("click");
                            UIActivity.this.b();
                        }
                    });
                } else {
                    throwWrongIdsException();
                }
                View findViewById2 = this.a.findViewById(R.id.versionchecklib_version_dialog_cancel);
                if (findViewById2 != null) {
                    findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.allenliu.versionchecklib.v2.ui.UIActivity.4
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            UIActivity uIActivity = UIActivity.this;
                            uIActivity.onCancel(uIActivity.a);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                throwWrongIdsException();
            }
            this.a.show();
        }
    }

    private void a() {
        if (getVersionBuilder() == null || getVersionBuilder().getCustomVersionDialogListener() == null) {
            showDefaultDialog();
        } else {
            showCustomDialog();
        }
        Dialog dialog = this.a;
        if (dialog != null) {
            dialog.setOnCancelListener(this);
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (getVersionBuilder() != null) {
            if (getVersionBuilder().isSilentDownload()) {
                StringBuilder sb = new StringBuilder();
                sb.append(getVersionBuilder().getDownloadAPKPath());
                int i = R.string.versionchecklib_download_apkname;
                Object[] objArr = new Object[1];
                objArr[0] = getVersionBuilder().getApkName() != null ? getVersionBuilder().getApkName() : getPackageName();
                sb.append(getString(i, objArr));
                AppUtils.installApk(this, new File(sb.toString()));
                checkForceUpdate();
            } else {
                AllenEventBusUtil.sendEventBus(98);
            }
            finish();
        }
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        cancelHandler();
        checkForceUpdate();
        AllenVersionChecker.getInstance().cancelAllMission(this);
        finish();
    }
}
