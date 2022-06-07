package com.xiaomi.accountsdk.utils;

/* loaded from: classes2.dex */
public class FidSigningUtil {

    /* loaded from: classes2.dex */
    public interface IFidSigner {
        boolean canSign() throws FidSignException;

        String getFid() throws FidSignException;

        byte[] sign(byte[] bArr) throws FidSignException;
    }

    /* loaded from: classes2.dex */
    public interface IFidSignerExtension {
        String getFidDirect() throws FidSignException;
    }

    /* loaded from: classes2.dex */
    public static class FidSignException extends Exception {
        public FidSignException(Throwable th) {
            super(th);
        }

        public FidSignException(String str) {
            super(str);
        }
    }

    /* loaded from: classes2.dex */
    private static class FidSignerHolder {
        private static volatile IFidSigner sInstance = new FidSignerImplDefault();

        private FidSignerHolder() {
        }
    }

    public static IFidSigner getFidSigner() {
        return FidSignerHolder.sInstance;
    }

    public static void setFidSigner(IFidSigner iFidSigner) {
        if (iFidSigner != null) {
            IFidSigner unused = FidSignerHolder.sInstance = iFidSigner;
            return;
        }
        throw new IllegalArgumentException("signer == null");
    }

    /* loaded from: classes2.dex */
    public static final class FidSignerImplDefault implements IFidSigner {
        @Override // com.xiaomi.accountsdk.utils.FidSigningUtil.IFidSigner
        public boolean canSign() {
            return false;
        }

        @Override // com.xiaomi.accountsdk.utils.FidSigningUtil.IFidSigner
        public byte[] sign(byte[] bArr) throws FidSignException {
            return bArr;
        }

        @Override // com.xiaomi.accountsdk.utils.FidSigningUtil.IFidSigner
        public String getFid() throws FidSignException {
            throw new FidSignException("单发应用场景无法调用");
        }
    }
}
