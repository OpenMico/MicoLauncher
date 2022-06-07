package com.xiaomi.passport.ui.internal;

import android.app.ProgressDialog;
import android.content.Context;
import com.umeng.analytics.pro.c;
import com.xiaomi.passport.ui.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Utils.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tJ\u001a\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/ProgressHolder;", "", "()V", "mProgressDialog", "Landroid/app/ProgressDialog;", "dismissProgress", "", "showProgress", c.R, "Landroid/content/Context;", "title", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class ProgressHolder {
    private ProgressDialog mProgressDialog;

    public final void showProgress(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        showProgress(context, context.getString(R.string.loading));
    }

    private final void showProgress(Context context, CharSequence charSequence) {
        this.mProgressDialog = new ProgressDialog(context, context.getResources().getIdentifier("CustomProgressDialog", "style", context.getPackageName()));
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(false);
        }
        ProgressDialog progressDialog2 = this.mProgressDialog;
        if (progressDialog2 != null) {
            progressDialog2.setMessage(charSequence);
        }
        ProgressDialog progressDialog3 = this.mProgressDialog;
        if (progressDialog3 != null) {
            progressDialog3.show();
        }
    }

    public final void dismissProgress() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            if (progressDialog == null) {
                Intrinsics.throwNpe();
            }
            if (progressDialog.isShowing()) {
                ProgressDialog progressDialog2 = this.mProgressDialog;
                if (progressDialog2 != null) {
                    progressDialog2.dismiss();
                }
                this.mProgressDialog = null;
            }
        }
    }
}
