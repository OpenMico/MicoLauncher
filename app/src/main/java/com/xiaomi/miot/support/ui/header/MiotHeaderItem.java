package com.xiaomi.miot.support.ui.header;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.ui.header.MiotHeaderData;

/* loaded from: classes3.dex */
public class MiotHeaderItem extends RelativeLayout {
    private TextView mName;
    private ImageView mUnit;
    private TextView mValue;

    public MiotHeaderItem(Context context) {
        super(context);
    }

    public MiotHeaderItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mName = (TextView) findViewById(R.id.miot_support_header_name);
        this.mValue = (TextView) findViewById(R.id.miot_support_header_value);
        this.mUnit = (ImageView) findViewById(R.id.miot_support_header_unit);
    }

    public void updateItem(MiotDeviceEvent.Type type, MiotHeaderData.Item item, Typeface typeface) {
        int i;
        switch (type) {
            case TEMPERATURE:
                TextView textView = this.mName;
                Context context = getContext();
                int i2 = R.string.miot_support_temperature;
                Object[] objArr = new Object[1];
                objArr[0] = item.roomInfo == null ? "" : item.roomInfo;
                textView.setText(context.getString(i2, objArr));
                this.mUnit.setVisibility(0);
                this.mUnit.setImageResource(R.drawable.miot_support_unit_temperature);
                if (item.value > 21) {
                    if (item.value > 28) {
                        i = R.color.miot_support_header_value_red;
                        break;
                    } else {
                        i = R.color.miot_support_header_value_green;
                        break;
                    }
                } else {
                    i = R.color.miot_support_header_value_blue;
                    break;
                }
            case HUMIDITY:
                TextView textView2 = this.mName;
                Context context2 = getContext();
                int i3 = R.string.miot_support_humidity;
                Object[] objArr2 = new Object[1];
                objArr2[0] = item.roomInfo == null ? "" : item.roomInfo;
                textView2.setText(context2.getString(i3, objArr2));
                this.mUnit.setVisibility(0);
                this.mUnit.setImageResource(R.drawable.miot_support_unit_humidity);
                if (item.value > 39) {
                    if (item.value > 51) {
                        i = R.color.miot_support_header_value_blue;
                        break;
                    } else {
                        i = R.color.miot_support_header_value_green;
                        break;
                    }
                } else {
                    i = R.color.miot_support_header_value_yellow;
                    break;
                }
            case PM25:
                TextView textView3 = this.mName;
                Context context3 = getContext();
                int i4 = R.string.miot_support_pm25;
                Object[] objArr3 = new Object[1];
                objArr3[0] = item.roomInfo == null ? "" : item.roomInfo;
                textView3.setText(context3.getString(i4, objArr3));
                this.mUnit.setVisibility(8);
                if (item.value > 75) {
                    if (item.value > 150) {
                        i = R.color.miot_support_header_value_red;
                        break;
                    } else {
                        i = R.color.miot_support_header_value_yellow;
                        break;
                    }
                } else {
                    i = R.color.miot_support_header_value_green;
                    break;
                }
            default:
                i = -1;
                break;
        }
        this.mValue.setText(String.valueOf(item.value));
        if (typeface != null) {
            this.mValue.setTypeface(typeface);
        }
        if (i != -1) {
            this.mValue.setTextColor(getResources().getColor(i));
        }
    }
}
