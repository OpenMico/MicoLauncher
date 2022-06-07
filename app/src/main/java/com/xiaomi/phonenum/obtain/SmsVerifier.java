package com.xiaomi.phonenum.obtain;

import com.xiaomi.phonenum.bean.PhoneNum;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface SmsVerifier {
    PhoneNum verify(PhoneNum phoneNum) throws IOException;
}
