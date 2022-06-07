package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class AccountValuePreference extends Preference {
    private boolean mShowRedDot;
    private boolean mShowValueRedPoint;
    private CharSequence mValue;
    private int mVipLevel;

    public AccountValuePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowRedDot = false;
        this.mShowValueRedPoint = false;
        this.mVipLevel = -1;
        setLayoutResource(R.layout.account_preference_item);
    }

    public AccountValuePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowRedDot = false;
        this.mShowValueRedPoint = false;
        this.mVipLevel = -1;
        setLayoutResource(R.layout.account_preference_item);
    }

    public AccountValuePreference(Context context) {
        this(context, null);
        setLayoutResource(R.layout.account_preference_item);
    }

    public void setValue(String str) {
        setValue(str, false);
    }

    public void setValue(int i) {
        setValue(i, false);
    }

    public CharSequence getValue() {
        return this.mValue;
    }

    public void setValue(int i, boolean z) {
        setValue(getContext().getString(i), z);
    }

    public void setValue(String str, boolean z) {
        if (!TextUtils.equals(str, this.mValue) || this.mShowValueRedPoint != z) {
            this.mValue = str;
            notifyChanged();
        }
    }

    public void setShowRedDot(boolean z) {
        if (this.mShowRedDot != z) {
            this.mShowRedDot = z;
            notifyChanged();
        }
    }

    public void setVipLevel(int i) {
        this.mVipLevel = i;
    }

    public boolean isShowRedDot() {
        return this.mShowRedDot;
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        view.setPadding(0, 0, 0, 0);
        TextView textView = (TextView) view.findViewById(R.id.value_right);
        if (textView != null) {
            CharSequence value = getValue();
            if (!TextUtils.isEmpty(value)) {
                textView.setText(value);
                textView.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
        }
    }
}
