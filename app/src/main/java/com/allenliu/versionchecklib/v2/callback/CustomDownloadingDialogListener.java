package com.allenliu.versionchecklib.v2.callback;

import android.app.Dialog;
import android.content.Context;
import com.allenliu.versionchecklib.v2.builder.UIData;

/* loaded from: classes.dex */
public interface CustomDownloadingDialogListener {
    Dialog getCustomDownloadingDialog(Context context, int i, UIData uIData);

    void updateUI(Dialog dialog, int i, UIData uIData);
}
