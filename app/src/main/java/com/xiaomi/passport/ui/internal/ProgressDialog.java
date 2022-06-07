package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.xiaomi.passport.ui.R;
import java.text.NumberFormat;

/* loaded from: classes4.dex */
public class ProgressDialog extends AlertDialog {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    private boolean mHasStarted;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private int mMax;
    private CharSequence mMessage;
    private TextView mMessageView;
    private ProgressBar mProgress;
    private Drawable mProgressDrawable;
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;
    private int mProgressStyle = 0;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private Handler mViewUpdateHandler;

    public ProgressDialog(Context context) {
        super(context);
        initFormats();
    }

    public ProgressDialog(Context context, int i) {
        super(context, i);
        initFormats();
    }

    private void initFormats() {
        this.mProgressNumberFormat = "%1d/%2d";
        this.mProgressPercentFormat = NumberFormat.getPercentInstance();
        this.mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2) {
        return show(context, charSequence, charSequence2, false);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return show(context, charSequence, charSequence2, z, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2) {
        return show(context, charSequence, charSequence2, z, z2, null);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2, DialogInterface.OnCancelListener onCancelListener) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(charSequence);
        progressDialog.setMessage(charSequence2);
        progressDialog.setIndeterminate(z);
        progressDialog.setCancelable(z2);
        progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.show();
        return progressDialog;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.passport.ui.internal.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        View view;
        LayoutInflater from = LayoutInflater.from(getContext());
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.Passport_AlertDialog, 16842845, 0);
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new Handler() { // from class: com.xiaomi.passport.ui.internal.ProgressDialog.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int progress = ProgressDialog.this.mProgress.getProgress();
                    int max = ProgressDialog.this.mProgress.getMax();
                    if (ProgressDialog.this.mProgressPercentFormat != null) {
                        double d = progress / max;
                        int i = 0;
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                        if (!TextUtils.isEmpty(ProgressDialog.this.mMessage)) {
                            i = ProgressDialog.this.mMessage.length();
                            spannableStringBuilder.append(ProgressDialog.this.mMessage);
                        }
                        String format = ProgressDialog.this.mProgressPercentFormat.format(d);
                        spannableStringBuilder.append((CharSequence) format);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(ProgressDialog.this.getContext().getResources().getColor(R.color.passport_progress_percent_color)), i, format.length() + i, 34);
                        ProgressDialog.this.setMessage(spannableStringBuilder);
                    }
                }
            };
            view = from.inflate(obtainStyledAttributes.getResourceId(R.styleable.Passport_AlertDialog_passport_horizontalProgressLayout, R.layout.passport_alert_dialog_progress), (ViewGroup) null);
        } else {
            view = from.inflate(obtainStyledAttributes.getResourceId(R.styleable.Passport_AlertDialog_passport_progressLayout, R.layout.passport_progress_dialog), (ViewGroup) null);
        }
        this.mProgress = (ProgressBar) view.findViewById(16908301);
        this.mMessageView = (TextView) view.findViewById(R.id.message);
        setView(view);
        obtainStyledAttributes.recycle();
        int i = this.mMax;
        if (i > 0) {
            setMax(i);
        }
        int i2 = this.mProgressVal;
        if (i2 > 0) {
            setProgress(i2);
        }
        int i3 = this.mSecondaryProgressVal;
        if (i3 > 0) {
            setSecondaryProgress(i3);
        }
        int i4 = this.mIncrementBy;
        if (i4 > 0) {
            incrementProgressBy(i4);
        }
        int i5 = this.mIncrementSecondaryBy;
        if (i5 > 0) {
            incrementSecondaryProgressBy(i5);
        }
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            setProgressDrawable(drawable);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            setIndeterminateDrawable(drawable2);
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            setMessage(charSequence);
        }
        setIndeterminate(this.mIndeterminate);
        onProgressChanged();
        super.onCreate(bundle);
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        this.mHasStarted = true;
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setProgress(int i) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(i);
            onProgressChanged();
            return;
        }
        this.mProgressVal = i;
    }

    public void setSecondaryProgress(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setSecondaryProgress(i);
            onProgressChanged();
            return;
        }
        this.mSecondaryProgressVal = i;
    }

    public int getProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public int getMax() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getMax();
        }
        return this.mMax;
    }

    public void setMax(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setMax(i);
            onProgressChanged();
            return;
        }
        this.mMax = i;
    }

    public void incrementProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementProgressBy(i);
            onProgressChanged();
            return;
        }
        this.mIncrementBy += i;
    }

    public void incrementSecondaryProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementSecondaryProgressBy(i);
            onProgressChanged();
            return;
        }
        this.mIncrementSecondaryBy += i;
    }

    public void setProgressDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(drawable);
        } else {
            this.mProgressDrawable = drawable;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(drawable);
        } else {
            this.mIndeterminateDrawable = drawable;
        }
    }

    public void setIndeterminate(boolean z) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(z);
        } else {
            this.mIndeterminate = z;
        }
    }

    public boolean isIndeterminate() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    @Override // com.xiaomi.passport.ui.internal.AlertDialog
    public void setMessage(CharSequence charSequence) {
        if (this.mProgress != null) {
            if (this.mProgressStyle == 1) {
                this.mMessage = charSequence;
            }
            this.mMessageView.setText(charSequence);
            return;
        }
        this.mMessage = charSequence;
    }

    public void setProgressStyle(int i) {
        this.mProgressStyle = i;
    }

    public void setProgressNumberFormat(String str) {
        this.mProgressNumberFormat = str;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat numberFormat) {
        this.mProgressPercentFormat = numberFormat;
        onProgressChanged();
    }

    private void onProgressChanged() {
        Handler handler;
        if (this.mProgressStyle == 1 && (handler = this.mViewUpdateHandler) != null && !handler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }
}
