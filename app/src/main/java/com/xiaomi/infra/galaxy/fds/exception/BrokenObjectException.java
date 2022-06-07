package com.xiaomi.infra.galaxy.fds.exception;

import com.xiaomi.infra.galaxy.fds.FDSError;

/* loaded from: classes3.dex */
public class BrokenObjectException extends GalaxyFDSException {
    private static final long serialVersionUID = 1034434809193644033L;

    public BrokenObjectException() {
    }

    public BrokenObjectException(String str) {
        super(str, null);
    }

    @Override // com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException
    public FDSError getError() {
        return FDSError.BrokenObject;
    }
}
