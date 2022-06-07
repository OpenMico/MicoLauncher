package com.xiaomi.micolauncher.module.faceDetection;

import android.content.DialogInterface;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.faceDetection.-$$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U implements DialogInterface.OnCancelListener {
    public static final /* synthetic */ $$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U INSTANCE = new $$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U();

    private /* synthetic */ $$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U() {
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        ToastUtil.showToast((int) R.string.child_mode_exit_msg2, 1);
    }
}
