package com.xiaomi.phonenum;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.bean.Sim;
import com.xiaomi.phonenum.db.PhoneNumDBHelper;
import com.xiaomi.phonenum.obtain.DataProxyParser;
import com.xiaomi.phonenum.obtain.EncryptHttpClient;
import com.xiaomi.phonenum.obtain.HttpProxyParser;
import com.xiaomi.phonenum.obtain.ObtainHandler;
import com.xiaomi.phonenum.obtain.Parser;
import com.xiaomi.phonenum.obtain.PhoneException;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import com.xiaomi.phonenum.obtain.SmsVerifier;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class PhoneNumStore {
    public static final String TAG = "PhoneNumStore";
    private Context a;
    private ObtainHandler b;
    private PhoneUtil c;
    private Logger d = LoggerManager.getLogger();
    private SmsVerifier e;

    public PhoneNumStore(Context context, String str, PhoneUtil phoneUtil) {
        this.a = context;
        this.c = phoneUtil;
        EncryptHttpClient.HttpFactory httpFactory = new EncryptHttpClient.HttpFactory(this.a);
        this.b = new ObtainHandler(this.a, str, phoneUtil, httpFactory);
        DataProxyParser dataProxyParser = new DataProxyParser(httpFactory);
        dataProxyParser.setNext(new HttpProxyParser(httpFactory));
        this.b.setParser(dataProxyParser);
    }

    public void setObtainResponseParser(Parser parser) {
        this.b.setParser(parser);
    }

    public void setSmsVerifier(SmsVerifier smsVerifier) {
        this.e = smsVerifier;
    }

    public PhoneNum peekPhoneNum(int i, PhoneLevel phoneLevel) throws PhoneException {
        a();
        return a(this.c.getSubIdForSlotId(i), phoneLevel);
    }

    private PhoneNum a(int i, PhoneLevel phoneLevel) throws PhoneException {
        Sim simForSubId = this.c.getSimForSubId(i);
        if (simForSubId != null) {
            PhoneNum queryPhoneNum = PhoneNumDBHelper.getInstance(this.a).queryPhoneNum(simForSubId.iccid, i);
            if (queryPhoneNum != null) {
                if (queryPhoneNum.phoneLevel < phoneLevel.value) {
                    Logger logger = this.d;
                    logger.i(TAG, "phoneLevel not match " + queryPhoneNum.phoneLevel + StringUtils.SPACE + phoneLevel.value);
                    return null;
                } else if (System.currentTimeMillis() - Long.valueOf(queryPhoneNum.updateTime).longValue() > 86400000) {
                    Logger logger2 = this.d;
                    logger2.i(TAG, "phoneLevel Expired " + System.currentTimeMillis() + StringUtils.SPACE + Long.valueOf(queryPhoneNum.updateTime));
                    return null;
                }
            }
            return queryPhoneNum;
        }
        this.d.i(TAG, "SIM_NOT_READY");
        throw new PhoneException(Error.SIM_NOT_READY);
    }

    public PhoneNum blockObtainPhoneNum(int i) throws IOException, PhoneException {
        return blockObtainPhoneNum(i, PhoneLevel.CACHE);
    }

    public boolean invalidatePhoneNum(PhoneNum phoneNum) {
        if (phoneNum == null || TextUtils.isEmpty(phoneNum.iccid)) {
            return false;
        }
        return PhoneNumDBHelper.getInstance(this.a).deletePhoneNum(phoneNum.iccid);
    }

    public PhoneNum blockObtainPhoneNum(int i, PhoneLevel phoneLevel) throws IOException, PhoneException {
        if (this.c.checkPermission("android.permission.READ_PHONE_STATE")) {
            int subIdForSlotId = this.c.getSubIdForSlotId(i);
            if (phoneLevel.value >= PhoneLevel.SMS_VERIFY.value) {
                return a(subIdForSlotId);
            }
            return b(subIdForSlotId, phoneLevel);
        }
        throw new PhoneException(Error.NO_READ_PHONE_STATE_PERMISSION);
    }

    @Nullable
    private PhoneNum b(int i, PhoneLevel phoneLevel) throws IOException, PhoneException {
        PhoneNum a = a(i, phoneLevel);
        if (a == null && (a = this.b.obtainPhoneNumber(i, phoneLevel)) != null && a.errorCode == 0) {
            PhoneNumDBHelper.getInstance(this.a).updatePhoneNum(a);
        }
        return a;
    }

    @Nullable
    private PhoneNum a(int i) throws IOException, PhoneException {
        if (this.e != null) {
            PhoneNum verify = this.e.verify(b(i, PhoneLevel.LINE_NUMBER));
            if (verify != null && verify.errorCode == 0) {
                PhoneNumDBHelper.getInstance(this.a).updatePhoneNum(verify);
            }
            return verify;
        }
        throw new PhoneException(Error.NOT_SUPPORT, "not support sms");
    }

    private void a() throws PhoneException {
        if (!this.c.checkPermission("android.permission.READ_PHONE_STATE")) {
            throw new PhoneException(Error.NO_READ_PHONE_STATE_PERMISSION);
        }
    }
}
