package com.xiaomi.phonenum;

import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.obtain.PhoneException;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import com.xiaomi.phonenum.utils.LoggerManager;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PhoneNumStoreAdapter.java */
/* loaded from: classes4.dex */
public class d implements c {
    private String a = "PhoneNumStoreAdapter";
    private PhoneNumStore b;

    @Override // com.xiaomi.phonenum.c
    public void a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(PhoneNumStore phoneNumStore) {
        this.b = phoneNumStore;
    }

    @Override // com.xiaomi.phonenum.c
    public void a(PhoneNumKeeper.SetupFinishedListener setupFinishedListener) {
        setupFinishedListener.onSetupFinished(Error.NONE);
    }

    @Override // com.xiaomi.phonenum.c
    public PhoneNum a(int i, PhoneLevel phoneLevel) {
        try {
            return this.b.peekPhoneNum(i, phoneLevel);
        } catch (PhoneException e) {
            LoggerManager.getLogger().i(this.a, e.toString());
            return null;
        }
    }

    @Override // com.xiaomi.phonenum.c
    public boolean a(int i, PhoneNum phoneNum) {
        return this.b.invalidatePhoneNum(phoneNum);
    }

    @Override // com.xiaomi.phonenum.c
    public PhoneNum b(int i, PhoneLevel phoneLevel) throws IOException {
        try {
            return this.b.blockObtainPhoneNum(i, phoneLevel);
        } catch (PhoneException e) {
            LoggerManager.getLogger().i(this.a, e.toString());
            return e.error.result();
        }
    }
}
