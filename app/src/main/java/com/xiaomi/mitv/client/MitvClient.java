package com.xiaomi.mitv.client;

import com.google.gson.Gson;
import com.xiaomi.mitv.config.MetaInfo;
import com.xiaomi.mitv.entity.LoginCreateOrderParam;
import com.xiaomi.mitv.entity.OrderInfoParam;
import com.xiaomi.mitv.entity.SignInfoParam;
import com.xiaomi.mitv.entity.SynPaymentFlowParam;
import com.xiaomi.mitv.entity.UnLoginCreateOrderParam;
import com.xiaomi.mitv.exception.MitvCommonException;
import com.xiaomi.mitv.utils.HttpUtil;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import org.apache.commons.codec.binary.Base64;

/* loaded from: classes4.dex */
public class MitvClient extends AbstractMitvClient {
    public static String loginCreateOrder(LoginCreateOrderParam loginCreateOrderParam, boolean z) throws MitvCommonException {
        if (z) {
            return commonCreateOrder(loginCreateOrderParam, MetaInfo.PRE_CREATE_ORDER_LOGIN, true);
        }
        return commonCreateOrder(loginCreateOrderParam, MetaInfo.CREATE_ORDER_LOGIN, false);
    }

    public static String unLoginCreateOrder(UnLoginCreateOrderParam unLoginCreateOrderParam, boolean z) throws MitvCommonException {
        if (z) {
            return commonCreateOrder(unLoginCreateOrderParam, MetaInfo.PRE_CREATE_ORDER_NOT_LOGIN, true);
        }
        return commonCreateOrder(unLoginCreateOrderParam, MetaInfo.CREATE_ORDER_NOT_LOGIN, false);
    }

    public static String synPaymentFlowInfo(SynPaymentFlowParam synPaymentFlowParam, String str) throws MitvCommonException {
        validateParam(synPaymentFlowParam);
        String json = new Gson().toJson(synPaymentFlowParam);
        String doSignature = doSignature(json, str);
        try {
            return HttpUtil.doPost("http://bossadmin.pandora.xiaomi.com/bssadmin/thirdPartyExternal/synPaymentFlowInfo?signature=" + doSignature, json);
        } catch (Exception e) {
            throw new MitvCommonException(e);
        }
    }

    public static String createShortkey(OrderInfoParam orderInfoParam, int i, int i2, boolean z) throws MitvCommonException, UnsupportedEncodingException {
        validateParam(orderInfoParam);
        String json = new Gson().toJson(orderInfoParam);
        HashMap hashMap = new HashMap();
        hashMap.put("orderInfo", json);
        hashMap.put("isRenew", Integer.valueOf(i));
        hashMap.put("isLogin", Integer.valueOf(i2));
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(MetaInfo.CREATE_SHORTKEY_TEST);
            return HttpUtil.doGet(buildUrl(sb, hashMap));
        }
        sb.append(MetaInfo.CREATE_SHORTKEY);
        return HttpUtil.doHttpsGetAllowAllSSL(buildUrl(sb, hashMap));
    }

    public static String createSignShortkey(SignInfoParam signInfoParam, int i, int i2, boolean z) throws MitvCommonException {
        validateParam(signInfoParam);
        String json = new Gson().toJson(signInfoParam);
        HashMap hashMap = new HashMap();
        hashMap.put("orderInfo", json);
        hashMap.put("isRenew", Integer.valueOf(i));
        hashMap.put("isLogin", Integer.valueOf(i2));
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(MetaInfo.CREATE_SHORTKEY_TEST);
            return HttpUtil.doGet(buildUrl(sb, hashMap));
        }
        sb.append(MetaInfo.CREATE_SHORTKEY);
        return HttpUtil.doHttpsGetAllowAllSSL(buildUrl(sb, hashMap));
    }

    public static String queryPayResult(int i, String str, boolean z) throws MitvCommonException {
        HashMap hashMap = new HashMap();
        hashMap.put("isRenew", Integer.valueOf(i));
        hashMap.put("shortKey", str);
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(MetaInfo.QUERY_PAY_RESULT_TEST);
            sb.append(str);
            return HttpUtil.doGet(buildUrl(sb, hashMap));
        }
        sb.append(MetaInfo.QUERY_PAY_RESULT);
        sb.append(str);
        return HttpUtil.doHttpsGetAllowAllSSL(buildUrl(sb, hashMap));
    }

    private static String doSignature(String str, String str2) throws MitvCommonException {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(str2.getBytes("UTF-8"))));
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initSign(generatePrivate);
            instance.update(str.getBytes("UTF-8"));
            byte[] sign = instance.sign();
            StringBuilder sb = new StringBuilder("");
            if (sign != null && sign.length > 0) {
                for (byte b : sign) {
                    String hexString = Integer.toHexString(b & 255);
                    if (hexString.length() < 2) {
                        sb.append(0);
                    }
                    sb.append(hexString);
                }
                return sb.toString();
            }
            return null;
        } catch (Exception e) {
            throw new MitvCommonException(e);
        }
    }
}
