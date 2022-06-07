package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.utils.PhoneNumUtil;

/* loaded from: classes4.dex */
public class AreaCodePickerListItem extends LinearLayout {
    private static final String TAG = "AreaCodePickerListItem";
    private TextView mAreaCodeView;
    private TextView mAreaView;
    private View mHeaderLayout;
    private TextView mSectionHeader;

    public AreaCodePickerListItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAreaView = (TextView) findViewById(R.id.area);
        this.mAreaCodeView = (TextView) findViewById(R.id.area_code);
        this.mSectionHeader = (TextView) findViewById(R.id.section_header);
        this.mHeaderLayout = findViewById(R.id.section_header_layout);
    }

    public void bind(PhoneNumUtil.CountryPhoneNumData countryPhoneNumData, String str) {
        this.mAreaView.setText(countryPhoneNumData.countryName);
        this.mAreaCodeView.setText(countryPhoneNumData.countryCode);
        if (!TextUtils.isEmpty(str)) {
            this.mSectionHeader.setText(str);
            this.mHeaderLayout.setVisibility(0);
            return;
        }
        this.mHeaderLayout.setVisibility(8);
    }
}
