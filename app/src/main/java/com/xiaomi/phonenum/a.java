package com.xiaomi.phonenum;

import android.os.RemoteException;
import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.obtain.PhoneException;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MiuiPhoneNumServiceAdapter.java */
/* loaded from: classes4.dex */
public class a implements c {
    private b a;
    private PhoneNumStore b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(b bVar, PhoneNumStore phoneNumStore) {
        this.a = bVar;
        this.b = phoneNumStore;
    }

    @Override // com.xiaomi.phonenum.c
    public void a(PhoneNumKeeper.SetupFinishedListener setupFinishedListener) {
        this.a.a(setupFinishedListener);
    }

    @Override // com.xiaomi.phonenum.c
    public void a() {
        this.a.a();
    }

    @Override // com.xiaomi.phonenum.c
    public PhoneNum a(int i, PhoneLevel phoneLevel) {
        try {
            return this.b.peekPhoneNum(i, phoneLevel);
        } catch (PhoneException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.xiaomi.phonenum.c
    public boolean a(int i, PhoneNum phoneNum) {
        this.b.invalidatePhoneNum(phoneNum);
        try {
            return this.a.a(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // com.xiaomi.phonenum.c
    public PhoneNum b(int i, PhoneLevel phoneLevel) throws IOException {
        if (phoneLevel.value >= PhoneLevel.SMS_VERIFY.value) {
            try {
                return this.a.a(i, true);
            } catch (RemoteException unused) {
                return Error.UNKNOW.result("RemoteException");
            }
        } else {
            try {
                return this.b.blockObtainPhoneNum(i, phoneLevel);
            } catch (PhoneException e) {
                e.printStackTrace();
                return e.error.result();
            }
        }
    }
}
