package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/* loaded from: classes3.dex */
public class RequestNotSupportedException extends GalaxyFDSException {
    private static final long serialVersionUID = -1040365665646550923L;

    public RequestNotSupportedException() {
    }

    public RequestNotSupportedException(String str, Throwable th) {
        super(str, th);
    }

    @Override // com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException
    public FDSError getError() {
        return FDSError.RequestNotSupported;
    }
}
