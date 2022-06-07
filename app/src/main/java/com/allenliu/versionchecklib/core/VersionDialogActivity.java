package com.allenliu.versionchecklib.core;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.callback.CommitClickListener;
import com.allenliu.versionchecklib.callback.DialogDismissListener;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AppUtils;
import com.allenliu.versionchecklib.v2.ui.AllenBaseActivity;
import java.io.File;

/* loaded from: classes.dex */
public class VersionDialogActivity extends AllenBaseActivity implements DialogInterface.OnDismissListener, DownloadListener {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 291;
    public static VersionDialogActivity instance;
    boolean a = false;
    private String b;
    private VersionParams c;
    private String d;
    private String e;
    private CommitClickListener f;
    protected Dialog failDialog;
    private DialogDismissListener g;
    private APKDownloadListener h;
    private View i;
    protected Dialog loadingDialog;
    protected Dialog versionDialog;

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showCustomDialog() {
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showDefaultDialog() {
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        instance = this;
        boolean booleanExtra = getIntent().getBooleanExtra("isRetry", false);
        Log.e("isRetry", booleanExtra + "");
        if (booleanExtra) {
            a(getIntent());
        } else {
            a();
        }
    }

    public String getDownloadUrl() {
        return this.b;
    }

    public VersionParams getVersionParams() {
        return this.c;
    }

    public String getVersionTitle() {
        return this.d;
    }

    public String getVersionUpdateMsg() {
        return this.e;
    }

    public Bundle getVersionParamBundle() {
        return this.c.getParamBundle();
    }

    private void a() {
        this.d = getIntent().getStringExtra("title");
        this.e = getIntent().getStringExtra("text");
        this.c = (VersionParams) getIntent().getParcelableExtra(AVersionService.VERSION_PARAMS_KEY);
        this.b = getIntent().getStringExtra("downloadUrl");
        if (this.d != null && this.e != null && this.b != null && this.c != null) {
            showVersionDialog();
        }
    }

    protected void showVersionDialog() {
        if (!this.a) {
            this.versionDialog = new AlertDialog.Builder(this).setTitle(this.d).setMessage(this.e).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.core.VersionDialogActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (VersionDialogActivity.this.f != null) {
                        VersionDialogActivity.this.f.onCommitClick();
                    }
                    VersionDialogActivity.this.dealAPK();
                }
            }).setNegativeButton(getString(R.string.versionchecklib_cancel), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.core.VersionDialogActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    VersionDialogActivity.this.finish();
                }
            }).create();
            this.versionDialog.setOnDismissListener(this);
            this.versionDialog.setCanceledOnTouchOutside(false);
            this.versionDialog.setCancelable(false);
            this.versionDialog.show();
        }
    }

    public void showLoadingDialog(int i) {
        ALog.e("show default downloading dialog");
        if (!this.a) {
            if (this.loadingDialog == null) {
                this.i = LayoutInflater.from(this).inflate(R.layout.downloading_layout, (ViewGroup) null);
                this.loadingDialog = new AlertDialog.Builder(this).setTitle("").setView(this.i).create();
                this.loadingDialog.setCancelable(true);
                this.loadingDialog.setCanceledOnTouchOutside(false);
                this.loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.allenliu.versionchecklib.core.VersionDialogActivity.3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public void onCancel(DialogInterface dialogInterface) {
                        AllenHttp.getHttpClient().dispatcher().cancelAll();
                    }
                });
            }
            ((TextView) this.i.findViewById(R.id.tv_progress)).setText(String.format(getString(R.string.versionchecklib_progress), Integer.valueOf(i)));
            ((ProgressBar) this.i.findViewById(R.id.pb)).setProgress(i);
            this.loadingDialog.show();
        }
    }

    public void showFailDialog() {
        if (!this.a) {
            VersionParams versionParams = this.c;
            if (versionParams == null || !versionParams.isShowDownloadFailDialog()) {
                onDismiss(null);
                return;
            }
            if (this.failDialog == null) {
                this.failDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.versionchecklib_download_fail_retry)).setPositiveButton(getString(R.string.versionchecklib_confirm), new DialogInterface.OnClickListener() { // from class: com.allenliu.versionchecklib.core.VersionDialogActivity.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (VersionDialogActivity.this.f != null) {
                            VersionDialogActivity.this.f.onCommitClick();
                        }
                        VersionDialogActivity.this.dealAPK();
                    }
                }).setNegativeButton(getString(R.string.versionchecklib_cancel), (DialogInterface.OnClickListener) null).create();
                this.failDialog.setOnDismissListener(this);
                this.failDialog.setCanceledOnTouchOutside(false);
                this.failDialog.setCancelable(false);
            }
            this.failDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra("isRetry", false);
        Log.e("isRetry", booleanExtra + "");
        if (booleanExtra) {
            a(intent);
        }
    }

    private void a(Intent intent) {
        b();
        this.c = (VersionParams) intent.getParcelableExtra(AVersionService.VERSION_PARAMS_KEY);
        this.b = intent.getStringExtra("downloadUrl");
        requestPermissionAndDownloadFile();
    }

    public void setApkDownloadListener(APKDownloadListener aPKDownloadListener) {
        this.h = aPKDownloadListener;
    }

    public void setCommitClickListener(CommitClickListener commitClickListener) {
        this.f = commitClickListener;
    }

    public void setDialogDimissListener(DialogDismissListener dialogDismissListener) {
        this.g = dialogDismissListener;
    }

    public void dealAPK() {
        if (this.c.isSilentDownload()) {
            AppUtils.installApk(this, new File(this.c.getDownloadAPKPath() + getString(R.string.versionchecklib_download_apkname, new Object[]{getPackageName()})));
            finish();
            return;
        }
        if (this.c.isShowDownloadingDialog()) {
            showLoadingDialog(0);
        }
        requestPermissionAndDownloadFile();
    }

    protected void downloadFile() {
        if (this.c.isShowDownloadingDialog()) {
            showLoadingDialog(0);
        }
        DownloadManager.downloadAPK(this.b, this.c, this);
    }

    protected void requestPermissionAndDownloadFile() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            downloadFile();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 291);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 291);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 291) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(this, getString(R.string.versionchecklib_write_permission_deny), 1).show();
                finish();
                return;
            }
            downloadFile();
        }
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloading(int i) {
        if (this.c.isShowDownloadingDialog()) {
            showLoadingDialog(i);
        } else {
            Dialog dialog = this.loadingDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            finish();
        }
        APKDownloadListener aPKDownloadListener = this.h;
        if (aPKDownloadListener != null) {
            aPKDownloadListener.onDownloading(i);
        }
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloadSuccess(File file) {
        APKDownloadListener aPKDownloadListener = this.h;
        if (aPKDownloadListener != null) {
            aPKDownloadListener.onDownloadSuccess(file);
        }
        b();
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerDownloadFail() {
        APKDownloadListener aPKDownloadListener = this.h;
        if (aPKDownloadListener != null) {
            aPKDownloadListener.onDownloadFail();
        }
        b();
        showFailDialog();
    }

    @Override // com.allenliu.versionchecklib.callback.DownloadListener
    public void onCheckerStartDownload() {
        if (!this.c.isShowDownloadingDialog()) {
            finish();
        }
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.a = true;
        instance = null;
        super.onDestroy();
    }

    private void b() {
        if (!this.a) {
            ALog.e("dismiss all dialog");
            Dialog dialog = this.loadingDialog;
            if (dialog != null && dialog.isShowing()) {
                this.loadingDialog.dismiss();
            }
            Dialog dialog2 = this.versionDialog;
            if (dialog2 != null && dialog2.isShowing()) {
                this.versionDialog.dismiss();
            }
            Dialog dialog3 = this.failDialog;
            if (dialog3 != null && dialog3.isShowing()) {
                this.failDialog.dismiss();
            }
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        Dialog dialog;
        if (this.c.isSilentDownload() || ((!this.c.isSilentDownload() && this.loadingDialog == null && this.c.isShowDownloadingDialog()) || (!this.c.isSilentDownload() && (dialog = this.loadingDialog) != null && !dialog.isShowing() && this.c.isShowDownloadingDialog()))) {
            DialogDismissListener dialogDismissListener = this.g;
            if (dialogDismissListener != null) {
                dialogDismissListener.dialogDismiss(dialogInterface);
            }
            finish();
            AllenChecker.cancelMission();
        }
    }
}
