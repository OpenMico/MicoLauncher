package com.xiaomi.miot.support.ui;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.R;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

/* loaded from: classes3.dex */
public class MiotCardItem extends RelativeLayout {
    private TextView mDescription;
    private ImageView mIcon;
    private TextView mName;
    private TextView mRoom;
    private LinearLayout mStatusContainer;

    public MiotCardItem(Context context) {
        super(context);
    }

    public MiotCardItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mName = (TextView) findViewById(R.id.miot_support_card_name);
        this.mDescription = (TextView) findViewById(R.id.miot_support_card_description);
        this.mStatusContainer = (LinearLayout) findViewById(R.id.miot_support_card_status_container);
        this.mIcon = (ImageView) findViewById(R.id.miot_support_card_icon);
        this.mRoom = (TextView) findViewById(R.id.miot_support_card_room);
    }

    public void updateDevice(final DeviceInfo deviceInfo, final DeviceOperator deviceOperator) {
        this.mName.setText(deviceInfo.deviceName);
        if (deviceInfo.isOnline) {
            if (deviceInfo.currentStatus) {
                this.mName.setTextColor(getContext().getResources().getColor(R.color.miot_support_name_online));
                this.mDescription.setTextColor(getContext().getResources().getColor(R.color.miot_support_description_online));
            } else {
                this.mName.setTextColor(getContext().getResources().getColor(R.color.miot_support_name_online));
                this.mDescription.setTextColor(getContext().getResources().getColor(R.color.miot_support_description_online_off));
            }
            this.mIcon.setAlpha(1.0f);
        } else {
            this.mName.setTextColor(getContext().getResources().getColor(R.color.miot_support_name_online));
            this.mDescription.setTextColor(getContext().getResources().getColor(R.color.miot_support_description_offline));
            this.mIcon.setAlpha(0.5f);
        }
        if (deviceInfo.subtitleMap.keySet().size() == 0) {
            this.mDescription.setVisibility(0);
            this.mDescription.setText(deviceInfo.subtitle);
            this.mStatusContainer.setVisibility(8);
            this.mIcon.setVisibility(0);
            String str = deviceInfo.did;
            if (str.equals(Build.MODEL + "_ir")) {
                this.mIcon.setImageResource(R.drawable.miot_support_icon_ir);
            } else {
                try {
                    MiotManager.displayImage(this.mIcon, URLDecoder.decode(deviceInfo.icon, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.mDescription.setVisibility(4);
            this.mIcon.setVisibility(8);
            this.mStatusContainer.setVisibility(0);
            this.mStatusContainer.removeAllViews();
            Set<String> keySet = deviceInfo.subtitleMap.keySet();
            LayoutInflater from = LayoutInflater.from(getContext());
            View view = null;
            for (String str2 : keySet) {
                View inflate = from.inflate(R.layout.miot_support_card_status_view, (ViewGroup) this.mStatusContainer, false);
                TextView textView = (TextView) inflate.findViewById(R.id.miio_support_card_status_value);
                TextView textView2 = (TextView) inflate.findViewById(R.id.miio_support_card_status_description);
                textView.setText(deviceInfo.subtitleMap.getString(str2));
                if (deviceInfo.isOnline) {
                    textView.setTextColor(getContext().getResources().getColor(R.color.miot_support_status_online));
                    textView2.setTextColor(getContext().getResources().getColor(R.color.miot_support_status_online));
                } else {
                    textView.setTextColor(getContext().getResources().getColor(R.color.miot_support_status_offline));
                    textView2.setTextColor(getContext().getResources().getColor(R.color.miot_support_status_offline));
                }
                textView2.setText(str2);
                this.mStatusContainer.addView(inflate);
                view = inflate;
            }
            if (view != null) {
                view.findViewById(R.id.miio_support_card_status_line).setVisibility(8);
            }
        }
        if (!TextUtils.isEmpty(deviceInfo.roomInfo)) {
            this.mRoom.setText(deviceInfo.roomInfo);
        } else {
            this.mRoom.setText(R.string.miot_support_default_room);
        }
        setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.miot.support.ui.MiotCardItem.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                deviceOperator.onDevicePageShow(view2, deviceInfo);
            }
        });
    }
}
