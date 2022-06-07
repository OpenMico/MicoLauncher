package com.xiaomi.accountsdk.utils;

import android.util.Base64;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.utils.AESStringDef;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class AESWithIVCoder implements CryptCoder {
    private static final int IV_BASE64_FLAG = 11;
    private static final String TAG = "AESWithIVCoder";
    private static final String version = "1";
    protected String aesKey;
    private AESCoder coder;
    private byte[] ivRaw;
    private final int IV_LENGTH = 16;
    private long threadId = 0;

    public AESWithIVCoder(String str) {
        this.aesKey = str;
        this.coder = new AESCoder(str) { // from class: com.xiaomi.accountsdk.utils.AESWithIVCoder.1
            @Override // com.xiaomi.accountsdk.utils.AESCoder
            protected byte[] getInitialVector() {
                return AESWithIVCoder.this.ivRaw;
            }
        };
        initThreadInfo();
    }

    private void initThreadInfo() {
        this.threadId = Thread.currentThread().getId();
    }

    @Override // com.xiaomi.accountsdk.utils.CryptCoder
    public String decrypt(String str) throws CipherException {
        checkThreadIdThrow();
        try {
            AESStringDef instance = AESStringDef.getInstance(str);
            if (instance.getVersion().equals("1")) {
                this.ivRaw = Base64.decode(instance.getIV(), 11);
                return this.coder.decrypt(instance.getData());
            }
            throw new CipherException("aes encrypt format version is wrong" + str);
        } catch (AESStringDef.InvalidAESDataException e) {
            throw new CipherException(e);
        }
    }

    @Override // com.xiaomi.accountsdk.utils.CryptCoder
    public String encrypt(String str) throws CipherException {
        checkThreadIdThrow();
        try {
            this.ivRaw = new byte[16];
            new SecureRandom().nextBytes(this.ivRaw);
            return AESStringDef.getInstance("1", Base64.encodeToString(this.ivRaw, 11), this.coder.encrypt(str)).toString();
        } catch (AESStringDef.InvalidAESDataException e) {
            throw new CipherException(e);
        }
    }

    protected void checkThreadIdThrow() {
        if (Thread.currentThread().getId() != this.threadId) {
            throw new IllegalAccessError("this method can not call concurrently");
        }
    }
}
