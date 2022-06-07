package com.qiyi.video.pad.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class QYDlnaDeviceList extends QYBaseData {
    public List<QYDlnaDevice> data;

    @Override // com.qiyi.video.pad.service.QYBaseData
    String toJsonStr() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", this.code);
            jSONObject.put("msg", this.msg);
            if (!(this.data == null || this.data.size() == 0)) {
                JSONArray jSONArray = new JSONArray();
                for (QYDlnaDevice qYDlnaDevice : this.data) {
                    JSONObject jsonObject = qYDlnaDevice.toJsonObject();
                    if (jsonObject != null) {
                        jSONArray.put(jsonObject);
                    }
                }
                jSONObject.put("data", jSONArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @Override // com.qiyi.video.pad.service.QYBaseData
    QYBaseData parse(String str) {
        QYDlnaDeviceList qYDlnaDeviceList = new QYDlnaDeviceList();
        qYDlnaDeviceList.data = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(str);
            qYDlnaDeviceList.code = jSONObject.has("code") ? jSONObject.getInt("code") : 0;
            qYDlnaDeviceList.msg = jSONObject.has("msg") ? jSONObject.getString("msg") : "";
            JSONArray jSONArray = jSONObject.has("data") ? jSONObject.getJSONArray("data") : null;
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                    if (jSONObject2 != null) {
                        qYDlnaDeviceList.data.add(new QYDlnaDevice(jSONObject2));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qYDlnaDeviceList;
    }
}
