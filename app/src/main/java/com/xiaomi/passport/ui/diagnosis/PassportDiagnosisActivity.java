package com.xiaomi.passport.ui.diagnosis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.account.diagnosis.AccountDiagnosisLogger;
import com.xiaomi.account.diagnosis.DiagnosisController;
import com.xiaomi.account.diagnosis.DiagnosisLaunchCallback;
import com.xiaomi.account.diagnosis.task.CollectAndUploadDiagnosisTask;
import com.xiaomi.account.diagnosis.task.ReadLogcatTask;
import com.xiaomi.account.diagnosis.util.DiagnosisPreference;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.utils.DiagnosisLog;
import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class PassportDiagnosisActivity extends AppCompatActivity {
    private View mFooter;
    private ProgressDialog mLoadingDialog;
    private ScrollView mLogScroller;
    private View mSendButton;
    private CompoundButton.OnCheckedChangeListener mDiagnosisCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.1
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            DiagnosisPreference.setDiagnosisEnabled(XMPassportSettings.getApplicationContext(), z);
            PassportDiagnosisActivity.this.setContentVisibility(z);
        }
    };
    private volatile boolean mUploading = false;
    private View.OnClickListener mUploadDiagnosis = new View.OnClickListener() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!PassportDiagnosisActivity.this.mUploading) {
                PassportDiagnosisActivity passportDiagnosisActivity = PassportDiagnosisActivity.this;
                passportDiagnosisActivity.mLoadingDialog = new ProgressDialog(passportDiagnosisActivity);
                PassportDiagnosisActivity.this.mLoadingDialog.setMessage(PassportDiagnosisActivity.this.getString(R.string.sending));
                PassportDiagnosisActivity.this.mLoadingDialog.setCancelable(false);
                PassportDiagnosisActivity.this.mLoadingDialog.getWindow().setGravity(80);
                PassportDiagnosisActivity.this.mLoadingDialog.show();
                PassportDiagnosisActivity.this.mUploading = true;
                new CollectAndUploadDiagnosisTask(new CollectAndUploadDiagnosisTask.Callback() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.2.1
                    @Override // com.xiaomi.account.diagnosis.task.CollectAndUploadDiagnosisTask.Callback
                    public void onFinished(boolean z, String str) {
                        PassportDiagnosisActivity.this.mUploading = false;
                        if (PassportDiagnosisActivity.this.mLoadingDialog != null) {
                            PassportDiagnosisActivity.this.mLoadingDialog.dismiss();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(PassportDiagnosisActivity.this);
                        if (!z || TextUtils.isEmpty(str)) {
                            builder.setMessage(PassportDiagnosisActivity.this.getString(R.string.diagnosis_log_send_failed));
                        } else {
                            builder.setMessage(PassportDiagnosisActivity.this.getString(R.string.diagnosis_log_sent_format, new Object[]{str}));
                        }
                        builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
                        builder.setCancelable(false);
                        builder.show();
                    }
                }, false).execute(new Void[0]);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.passport_diagnosis);
        DiagnosisLog.set(new AccountDiagnosisLogger(getApplicationContext()));
        this.mLogScroller = (ScrollView) findViewById(R.id.log_scroller);
        this.mSendButton = findViewById(R.id.upload_diagnosis);
        this.mFooter = findViewById(R.id.footer);
        CompoundButton compoundButton = (CompoundButton) findViewById(R.id.switch_diagnosis);
        compoundButton.setChecked(isDiagnosisEnabled());
        compoundButton.setOnCheckedChangeListener(this.mDiagnosisCheckedChangeListener);
        this.mSendButton.setOnClickListener(this.mUploadDiagnosis);
        readLogcatAsync();
        setContentVisibility(isDiagnosisEnabled());
    }

    private static boolean isDiagnosisEnabled() {
        return DiagnosisPreference.isDiagnosisEnabled(XMPassportSettings.getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentVisibility(boolean z) {
        int i = z ? 0 : 8;
        this.mLogScroller.setVisibility(i);
        this.mFooter.setVisibility(i);
        this.mSendButton.setVisibility(i);
    }

    private void readLogcatAsync() {
        new ReadLogcatTask(this, new ReadLogcatTask.Callback() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.3
            @Override // com.xiaomi.account.diagnosis.task.ReadLogcatTask.Callback
            public void onReadLog(String str) {
                ((TextView) PassportDiagnosisActivity.this.findViewById(R.id.tv_log)).setText(str);
                PassportDiagnosisActivity.this.mLogScroller.post(new Runnable() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PassportDiagnosisActivity.this.mLogScroller.fullScroll(130);
                    }
                });
            }
        }, 512).execute(new Void[0]);
    }

    public static void start(final Context context) {
        DiagnosisController.get().checkStart(new DiagnosisLaunchCallback() { // from class: com.xiaomi.passport.ui.diagnosis.PassportDiagnosisActivity.4
            @Override // com.xiaomi.account.diagnosis.DiagnosisLaunchCallback
            public void onLaunch() {
                context.startActivity(new Intent(context, PassportDiagnosisActivity.class));
            }

            @Override // com.xiaomi.account.diagnosis.DiagnosisLaunchCallback
            public void onError() {
                Toast.makeText(context, R.string.temporary_not_available, 0).show();
            }
        });
    }
}
