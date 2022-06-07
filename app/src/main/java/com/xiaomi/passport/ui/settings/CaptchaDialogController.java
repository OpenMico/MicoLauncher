package com.xiaomi.passport.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.CaptchaView;

/* loaded from: classes4.dex */
public class CaptchaDialogController implements CaptchaView.OnCaptchaSwitchChange {
    private AlertDialog captchaDialog;
    private final CaptchaInterface captchaInterface;
    private CaptchaView captchaView;
    private final Activity mActivity;

    /* loaded from: classes4.dex */
    public interface CaptchaInterface {
        void onCaptchaFinished();

        void onCaptchaRequired();

        void tryCaptcha(String str, String str2);
    }

    @Override // com.xiaomi.passport.ui.settings.CaptchaView.OnCaptchaSwitchChange
    public void update(boolean z) {
        AlertDialog alertDialog = this.captchaDialog;
        if (alertDialog != null) {
            alertDialog.setTitle(z ? R.string.passport_input_voice_hint : R.string.passport_input_captcha_hint);
        }
    }

    public CaptchaDialogController(Activity activity, CaptchaInterface captchaInterface) {
        this.mActivity = activity;
        this.captchaInterface = captchaInterface;
    }

    public boolean shouldForceNewCaptcha() {
        AlertDialog alertDialog = this.captchaDialog;
        return alertDialog != null && !alertDialog.isShowing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCaptchaCode() {
        CaptchaView captchaView = this.captchaView;
        if (captchaView != null) {
            return captchaView.getCaptchaCode();
        }
        return null;
    }

    public CaptchaInterface getCaptchaInterface() {
        return this.captchaInterface;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dismiss() {
        AlertDialog alertDialog = this.captchaDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public String getIck() {
        CaptchaView captchaView = this.captchaView;
        if (captchaView != null) {
            return captchaView.getCaptchaIck();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void triggerNewCaptchaTask(String str, String str2) {
        Activity activity = this.mActivity;
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = this.captchaDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                this.captchaView = new CaptchaView(this.mActivity);
                this.captchaView.setOnCaptchaSwitchChange(this);
                this.captchaView.downloadCaptcha(str, str2);
                this.captchaDialog = new AlertDialog.Builder(this.mActivity).setTitle(R.string.passport_input_captcha_hint).setView(this.captchaView).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).show();
                this.captchaDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaDialogController.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        String captchaCode = CaptchaDialogController.this.getCaptchaCode();
                        if (!TextUtils.isEmpty(captchaCode)) {
                            CaptchaDialogController.this.captchaInterface.tryCaptcha(captchaCode, CaptchaDialogController.this.captchaView.getCaptchaIck());
                        }
                    }
                });
                return;
            }
            CaptchaView captchaView = this.captchaView;
            if (captchaView != null) {
                captchaView.onCaptchaError();
            }
        }
    }
}
