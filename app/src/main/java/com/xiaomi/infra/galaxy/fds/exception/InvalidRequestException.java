package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/* loaded from: classes3.dex */
public class InvalidRequestException extends GalaxyFDSException {
    private static final long serialVersionUID = 1159385783471785922L;

    public InvalidRequestException() {
    }

    public InvalidRequestException(String str, Throwable th) {
        super(str, th);
    }

    @Override // com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException
    public FDSError getError() {
        return FDSError.InvalidRequest;
    }
}
