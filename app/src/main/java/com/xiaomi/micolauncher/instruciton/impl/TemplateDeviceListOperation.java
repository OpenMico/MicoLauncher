package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.miot.multipledevices.MiotMultipleDevicesSelectActivity;
import com.xiaomi.micolauncher.skills.miot.multipledevices.MultipleDevicesItem;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateDeviceListOperation extends BaseOperation<Instruction<Template.DeviceList>> {
    public TemplateDeviceListOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        final List<Template.DeviceItem> items = ((Template.DeviceList) this.instruction.getPayload()).getItems();
        if (ContainerUtil.isEmpty(items)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        if (items.size() == 1) {
            Template.DeviceItem deviceItem = items.get(0);
            if (deviceItem.getInfo().isPresent()) {
                Template.DeviceInfoV1 deviceInfoV1 = deviceItem.getInfo().get();
                final String category = deviceInfoV1.getCategory();
                final String deviceId = deviceInfoV1.getDeviceId();
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$TemplateDeviceListOperation$8prPzaTkWH-KPVc0MvSSSD-6_t0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TemplateDeviceListOperation.this.b(deviceId, category);
                    }
                });
            }
        } else if (items.size() > 1) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$TemplateDeviceListOperation$llJzbL6ilCLiBDIo0itQv79VraM
                @Override // java.lang.Runnable
                public final void run() {
                    TemplateDeviceListOperation.this.b(items);
                }
            });
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(List<Template.DeviceItem> list) {
        List<Template.ImageSource> sources;
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (Template.DeviceItem deviceItem : list) {
            if (deviceItem.getInfo().isPresent()) {
                Template.DeviceInfoV1 deviceInfoV1 = deviceItem.getInfo().get();
                MultipleDevicesItem multipleDevicesItem = new MultipleDevicesItem();
                if (deviceInfoV1.getRoom().isPresent()) {
                    multipleDevicesItem.setRoomName(deviceInfoV1.getRoom().get());
                } else {
                    multipleDevicesItem.setRoomName(MicoSupConstants.ROOM_DEFAULT);
                }
                multipleDevicesItem.setDeviceName(deviceNameMatchBrand(getContext(), deviceItem.getTitle().getMainTitle()));
                multipleDevicesItem.setDid(deviceInfoV1.getDeviceId());
                multipleDevicesItem.setType(deviceInfoV1.getCategory());
                List<Template.Image> list2 = deviceItem.getImages().get();
                if (list2.size() > 1) {
                    Iterator<Template.Image> it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Template.Image next = it.next();
                        if ("icon_url".equals(next.getDescription())) {
                            List<Template.ImageSource> sources2 = next.getSources();
                            if (sources2 != null && !sources2.isEmpty()) {
                                multipleDevicesItem.setViewUrl(sources2.get(0).getUrl());
                            }
                        }
                    }
                } else if (list2.size() == 1 && (sources = list2.get(0).getSources()) != null && !sources.isEmpty()) {
                    multipleDevicesItem.setViewUrl(sources.get(0).getUrl());
                }
                arrayList.add(multipleDevicesItem);
            }
        }
        if (ContainerUtil.hasData(arrayList)) {
            Intent intent = new Intent(getContext(), MiotMultipleDevicesSelectActivity.class);
            intent.putParcelableArrayListExtra(MiotMultipleDevicesSelectActivity.EXTRA_MIOT_DEVICE_INFO, arrayList);
            ActivityLifeCycleManager.startActivityQuietly(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(String str, String str2) {
        MiotControllerOperation.destroyTopMultipleDevicesSelectActivity();
        MiotManager.showDevicePage(getContext(), str, str2);
    }

    public static String deviceNameMatchBrand(Context context, String str) {
        String[] stringArray = context.getResources().getStringArray(R.array.miot_brand);
        if (str.length() <= 4) {
            return str;
        }
        for (String str2 : stringArray) {
            if (str.contains(str2)) {
                L.miot.v("compare ori deviceName: %s", str);
                int length = str2.length();
                L.miot.v("compare after deviceName: %s", str.substring(length));
                return str.substring(length);
            }
        }
        return str;
    }
}
