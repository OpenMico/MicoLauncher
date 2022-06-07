package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/* loaded from: classes3.dex */
public class BucketAccessDeniedException extends GalaxyFDSException {
    private static final long serialVersionUID = -974711420428687663L;

    public BucketAccessDeniedException() {
    }

    public BucketAccessDeniedException(String str, Throwable th) {
        super(str, th);
    }

    @Override // com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException
    public FDSError getError() {
        return FDSError.BucketAccessDenied;
    }
}
