package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;

/* compiled from: AreaCodePicker.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\b\u0010\u0013\u001a\u00020\u000eH\u0014R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AreaCodePickerListItem;", "Landroid/widget/LinearLayout;", c.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "mAreaCodeView", "Landroid/widget/TextView;", "mAreaView", "mHeaderLayout", "Landroid/view/View;", "mSectionHeader", SchemaActivity.VALUE_REGISTER_BIND, "", "data", "Lcom/xiaomi/passport/ui/internal/PhoneNumUtil$CountryPhoneNumData;", "sectionHeaderText", "", "onFinishInflate", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AreaCodePickerListItem extends LinearLayout {
    private HashMap _$_findViewCache;
    private TextView mAreaCodeView;
    private TextView mAreaView;
    private View mHeaderLayout;
    private TextView mSectionHeader;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AreaCodePickerListItem(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attrs, "attrs");
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAreaView = (TextView) findViewById(R.id.area);
        this.mAreaCodeView = (TextView) findViewById(R.id.area_code);
        this.mSectionHeader = (TextView) findViewById(R.id.section_header);
        this.mHeaderLayout = findViewById(R.id.section_header_layout);
    }

    public final void bind(@NotNull PhoneNumUtil.CountryPhoneNumData data, @Nullable String str) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        TextView textView = this.mAreaView;
        if (textView != null) {
            textView.setText(data.countryName);
        }
        TextView textView2 = this.mAreaCodeView;
        if (textView2 != null) {
            textView2.setText(Marker.ANY_NON_NULL_MARKER + data.countryCode);
        }
        String str2 = str;
        if (!TextUtils.isEmpty(str2)) {
            TextView textView3 = this.mSectionHeader;
            if (textView3 != null) {
                textView3.setText(str2);
            }
            View view = this.mHeaderLayout;
            if (view != null) {
                view.setVisibility(0);
                return;
            }
            return;
        }
        View view2 = this.mHeaderLayout;
        if (view2 != null) {
            view2.setVisibility(8);
        }
    }
}
