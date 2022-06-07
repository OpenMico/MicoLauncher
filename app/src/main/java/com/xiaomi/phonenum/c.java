package com.xiaomi.phonenum;

import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PhoneNumGetter.java */
/* loaded from: classes4.dex */
public interface c {
    PhoneNum a(int i, PhoneLevel phoneLevel);

    void a();

    void a(PhoneNumKeeper.SetupFinishedListener setupFinishedListener);

    boolean a(int i, PhoneNum phoneNum);

    PhoneNum b(int i, PhoneLevel phoneLevel) throws IOException;
}
