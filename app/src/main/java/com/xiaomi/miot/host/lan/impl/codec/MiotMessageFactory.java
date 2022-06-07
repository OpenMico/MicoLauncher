package com.xiaomi.miot.host.lan.impl.codec;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.miot.host.lan.impl.codec.broadcast.MiotLocalBroadcast;
import com.xiaomi.miot.host.lan.impl.codec.broadcast.MiotWifiStart;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotBindStat;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotConfig;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotInfo;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotMdns;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotReportOtcInfoResult;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotRestore;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotSyncTimeRequest;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotToken;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotTransparantRequest;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotUpdateToken;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifi;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotWifiScan;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotAction;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotPropertyGetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotPropertySetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2Action;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2PropertyGetter;
import com.xiaomi.miot.host.lan.impl.codec.operation.MiotSpecV2PropertySetter;
import com.xiaomi.miot.host.lan.impl.codec.voice.MiotVoiceReplyRequest;
import com.xiaomi.miot.typedef.exception.MiotException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotMessageFactory {
    private static final String TAG = "MiotMessageFactory";
    private static List<String> mTransparentMethod = new ArrayList();

    private MiotMessageFactory() {
    }

    public static void addTransparentMethods(List<String> list) {
        if (list != null) {
            for (String str : list) {
                if (!TextUtils.isEmpty(str) && !mTransparentMethod.contains(str)) {
                    mTransparentMethod.add(str);
                }
            }
        }
    }

    public static boolean isContainTransparentMethod(String str) {
        return mTransparentMethod.contains(str);
    }

    public static MiotMessage parse(byte[] bArr) {
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            String optString = jSONObject.optString(SchemaActivity.KEY_METHOD);
            int optInt = jSONObject.optInt("id");
            try {
                if (!TextUtils.equals(optString, MiotSyncTimeRequest.RESPONSE_METHOD) && (!TextUtils.equals(optString, "local.status") || optInt <= 0)) {
                    if (optString == null || optString.length() <= 0) {
                        return createResponse(jSONObject);
                    }
                    return createRequest(optString, jSONObject);
                }
                return createResponse(jSONObject);
            } catch (MiotException e) {
                e.printStackTrace();
                return null;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static MiotRequest createRequest(String str, JSONObject jSONObject) throws MiotException {
        char c;
        Log.e(TAG, "method: " + str);
        switch (str.hashCode()) {
            case -2145877572:
                if (str.equals(MiotSpecV2PropertyGetter.MIOT_SPEC_V2_REQUEST_METHOD)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1818527787:
                if (str.equals("local.status")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1642231846:
                if (str.equals(MiotToken.REQUEST_METHOD)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (str.equals("action")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1369366430:
                if (str.equals(MiotRestore.REQUEST_METHOD)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1115249804:
                if (str.equals("_internal.wifi_scan_req")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -932687088:
                if (str.equals(MiotConfig.REQUEST_METHOD)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -916781368:
                if (str.equals(MiotVoiceReplyRequest.REQUEST_METHOD)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -539447350:
                if (str.equals("_internal.wifi_start")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -143316487:
                if (str.equals(MiotUpdateToken.REQUEST_METHOD)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 204893014:
                if (str.equals("_internal.req_wifi_conf_status")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 377353520:
                if (str.equals(MiotSpecV2PropertySetter.MIOT_SPEC_V2_REQUEST_METHOD)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 997676864:
                if (str.equals(MiotInfo.REQUEST_METHOD)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 997786670:
                if (str.equals(MiotMdns.REQUEST_METHOD)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1257924832:
                if (str.equals(MiotBindStat.REQUEST_METHOD)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1450737929:
                if (str.equals(MiotReportOtcInfoResult.REQUEST_METHOD)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1976562348:
                if (str.equals(MiotPropertyGetter.REQUEST_METHOD)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new MiotConfig(jSONObject);
            case 1:
                return new MiotInfo(jSONObject);
            case 2:
                return new MiotMdns(jSONObject);
            case 3:
                return new MiotBindStat(jSONObject);
            case 4:
                return new MiotRestore(jSONObject);
            case 5:
                return new MiotToken(jSONObject);
            case 6:
                return new MiotUpdateToken(jSONObject);
            case 7:
                return new MiotWifiScan(jSONObject);
            case '\b':
                return new MiotWifi(jSONObject);
            case '\t':
                return new MiotReportOtcInfoResult(jSONObject);
            case '\n':
                return new MiotPropertyGetter(jSONObject);
            case 11:
                return new MiotVoiceReplyRequest(jSONObject);
            case '\f':
                return new MiotSpecV2PropertyGetter(jSONObject);
            case '\r':
                return new MiotSpecV2PropertySetter(jSONObject);
            case 14:
                return new MiotSpecV2Action(jSONObject);
            case 15:
                return new MiotLocalBroadcast(jSONObject);
            case 16:
                MiotWifiStart miotWifiStart = new MiotWifiStart(jSONObject);
                if (!TextUtils.isEmpty(miotWifiStart.getSsid())) {
                    return miotWifiStart;
                }
                break;
        }
        if (mTransparentMethod.contains(str)) {
            return new MiotTransparantRequest(str, jSONObject);
        }
        if (MiotSupportRpcType.isMitvType()) {
            if (str.startsWith("Get")) {
                return new MiotPropertyGetter(jSONObject);
            }
            if (str.startsWith("Set")) {
                return new MiotPropertySetter(jSONObject);
            }
        } else if (MiotSupportRpcType.isYunmiType()) {
            String[] split = str.split("#");
            if (split.length == 2 && split[0].equals(BluetoothConstants.SET)) {
                return new MiotPropertySetter(jSONObject);
            }
        } else {
            String[] split2 = str.split("_");
            Log.d(TAG, "method: " + str);
            if (split2.length == 3 && split2[0].equals(BluetoothConstants.SET)) {
                return new MiotPropertySetter(jSONObject);
            }
        }
        return new MiotAction(jSONObject);
    }

    public static MiotResponse createResponse(JSONObject jSONObject) {
        MiotResponse miotResponse = new MiotResponse();
        if (jSONObject.has("id")) {
            miotResponse.setId(jSONObject.optInt("id"));
        }
        if (jSONObject.has("result")) {
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject != null) {
                miotResponse.setContent(optJSONObject);
            } else {
                miotResponse.setContent(jSONObject);
            }
        } else {
            miotResponse.setContent(jSONObject);
        }
        if (jSONObject.has("error")) {
            miotResponse.setError(jSONObject.optJSONObject("error"));
        }
        miotResponse.setOriginResponse(jSONObject.toString());
        return miotResponse;
    }

    public static boolean isInternalCommand(byte[] bArr) {
        try {
            return new JSONObject(new String(bArr)).optString(SchemaActivity.KEY_METHOD).contains("_internal.");
        } catch (Exception unused) {
            return false;
        }
    }
}
