package com.xiaomi.micolauncher.skills.update;

/* loaded from: classes3.dex */
public class UpdateCompleteEvent {
    public int engineErrorCode;
    public ErrorCode errorCode;

    /* loaded from: classes3.dex */
    public enum ErrorCode {
        Success,
        UnknownException,
        PayloadApplicationComplete,
        AnUpdateAlreadyApplied
    }

    public UpdateCompleteEvent(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UpdateCompleteEvent(ErrorCode errorCode, int i) {
        this.errorCode = errorCode;
        this.engineErrorCode = i;
    }
}
