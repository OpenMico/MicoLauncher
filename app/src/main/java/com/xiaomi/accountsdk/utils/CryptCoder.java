package com.xiaomi.accountsdk.utils;

import com.xiaomi.accountsdk.request.CipherException;

/* loaded from: classes2.dex */
public interface CryptCoder {
    String decrypt(String str) throws CipherException;

    String encrypt(String str) throws CipherException;
}
