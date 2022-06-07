package com.xiaomi.miot.support.category;

import android.text.TextUtils;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.util.JsonResolver;
import com.xiaomi.miot.support.util.MiotUtil;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ReplyDeviceInfoCallback implements IMiotMsgCallback {
    private String mLastInfoMd5;
    private final Map<Long, ReplyDeviceInfo> replyDeviceInfoMap = new HashMap();

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public int getAction() {
        return 8;
    }

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public void onMsgCallback(JSONObject jSONObject) {
        clearMap();
        if (jSONObject == null) {
            MiotLog.e("Error: reply device info error; params is null!");
            return;
        }
        long optLong = jSONObject.optLong("pkgId");
        String optString = jSONObject.optString(MicoSupConstants.Other.KEY_REFRESH_DEVICE_TASK_ID);
        ReplyDeviceInfo replyDeviceInfo = this.replyDeviceInfoMap.get(Long.valueOf(optLong));
        if (replyDeviceInfo == null) {
            int optInt = jSONObject.optInt("pkgTotalSize");
            MiotLog.i("Info: Receive taskId: " + optString + ", pkgId: " + optLong + ", totalSize: " + optInt);
            if (optInt == 1) {
                onReceiveReplyDeviceInfo(jSONObject.optString("pkgData"));
            } else {
                replyDeviceInfo = new ReplyDeviceInfo(optLong, optInt);
                this.replyDeviceInfoMap.put(Long.valueOf(optLong), replyDeviceInfo);
            }
        }
        if (replyDeviceInfo != null) {
            int optInt2 = jSONObject.optInt("currentIndex");
            String optString2 = jSONObject.optString("pkgData");
            MiotLog.i("receive deviceInfo: index: " + optInt2 + ", length: " + optString2.length());
            replyDeviceInfo.addPkgData(optInt2, optString2);
        }
        if (replyDeviceInfo != null && replyDeviceInfo.isFinished()) {
            String replyDeviceInfo2 = replyDeviceInfo.getReplyDeviceInfo();
            this.replyDeviceInfoMap.remove(Long.valueOf(optLong));
            onReceiveReplyDeviceInfo(replyDeviceInfo2);
        }
    }

    private synchronized void clearMap() {
        if (!this.replyDeviceInfoMap.isEmpty()) {
            ArrayList<Long> arrayList = new ArrayList();
            for (Long l : this.replyDeviceInfoMap.keySet()) {
                if (this.replyDeviceInfoMap.get(l).canClear()) {
                    arrayList.add(l);
                }
            }
            if (!arrayList.isEmpty()) {
                for (Long l2 : arrayList) {
                    this.replyDeviceInfoMap.remove(Long.valueOf(l2.longValue()));
                }
            }
        }
    }

    public void onReceiveReplyDeviceInfo(final String str) {
        if (TextUtils.isEmpty(str)) {
            MiotLog.i("Info: onReceiveReplyDeviceInfo is null!");
            return;
        }
        MiotLog.i("on device info finished: length: " + str.length());
        String MD5_32 = MiotUtil.MD5_32(str);
        if (MD5_32.equals(this.mLastInfoMd5)) {
            MiotLog.i("Md5 equals: " + this.mLastInfoMd5);
            return;
        }
        this.mLastInfoMd5 = MD5_32;
        MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.category.ReplyDeviceInfoCallback.1
            @Override // java.lang.Runnable
            public void run() {
                MiotLog.i("save prop info to local cache!");
                MiotUtil.saveDeviceCache(str);
            }
        });
        MiotLog.i("resolve info md5: " + this.mLastInfoMd5);
        try {
            JSONObject jSONObject = new JSONObject(str);
            Map<String, ModelInfo> resolveModelInfo = JsonResolver.resolveModelInfo(jSONObject.optJSONObject(JsonResolver.KEY_MODEL_INFO));
            MiotManager.getDeviceCore().setModelInfos(resolveModelInfo);
            Map<String, DeviceItemInfo> resolveDevicePropInfo = JsonResolver.resolveDevicePropInfo(jSONObject.optJSONObject(JsonResolver.KEY_PROP_ACTION_INFO));
            MiotManager.getDeviceCore().setDevicePropInfos(resolveDevicePropInfo);
            boolean optBoolean = jSONObject.optBoolean(JsonResolver.KEY_IS_SPECDEVICE_ALLREADY, false);
            if (resolveModelInfo != null && !resolveModelInfo.isEmpty() && resolveDevicePropInfo != null && !resolveDevicePropInfo.isEmpty()) {
                MiotManager.setDeviceInfoReady(optBoolean);
            }
        } catch (JSONException e) {
            MiotLog.i("onReceiveReplyDeviceInfo ERROR: " + e.getMessage() + Constants.ACCEPT_TIME_SEPARATOR_SP + str);
        }
    }
}
