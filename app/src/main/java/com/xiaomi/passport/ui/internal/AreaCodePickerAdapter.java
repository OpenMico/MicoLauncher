package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AreaCodePicker.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0013H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0013H\u0016J\u0015\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rH\u0016¢\u0006\u0002\u0010\u001cJ\"\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00132\b\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020!H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082.¢\u0006\u0004\n\u0002\u0010\u000f¨\u0006\""}, d2 = {"Lcom/xiaomi/passport/ui/internal/AreaCodePickerAdapter;", "Landroid/widget/BaseAdapter;", "Landroid/widget/SectionIndexer;", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mList", "", "Lcom/xiaomi/passport/ui/internal/PhoneNumUtil$CountryPhoneNumData;", "mPositionForSection", "", "mSectionForPosition", "mSections", "", "", "[Ljava/lang/String;", "buildPickerSectionList", "", "getCount", "", "getItem", "position", "getItemId", "", "getPositionForSection", "section", "getSectionForPosition", "getSections", "()[Ljava/lang/String;", "getView", "Landroid/view/View;", "convertView", "parent", "Landroid/view/ViewGroup;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AreaCodePickerAdapter extends BaseAdapter implements SectionIndexer {
    private final Context mContext;
    private List<PhoneNumUtil.CountryPhoneNumData> mList;
    private int[] mPositionForSection;
    private int[] mSectionForPosition;
    private String[] mSections;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AreaCodePickerAdapter(@NotNull Context mContext) {
        Intrinsics.checkParameterIsNotNull(mContext, "mContext");
        this.mContext = mContext;
        buildPickerSectionList();
    }

    private final void buildPickerSectionList() {
        int i;
        List<PhoneNumUtil.CountryPhoneNumData> recommendCountryPhoneNumDataList = PhoneNumUtil.getRecommendCountryPhoneNumDataList(this.mContext);
        List<PhoneNumUtil.CountryPhoneNumData> totalList = PhoneNumUtil.getCountryPhoneNumDataList(this.mContext);
        this.mList = new ArrayList(recommendCountryPhoneNumDataList);
        List<PhoneNumUtil.CountryPhoneNumData> list = this.mList;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mList");
        }
        Intrinsics.checkExpressionValueIsNotNull(totalList, "totalList");
        list.addAll(totalList);
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.alphabet_table);
        Intrinsics.checkExpressionValueIsNotNull(stringArray, "mContext.resources.getSt…y(R.array.alphabet_table)");
        this.mSections = stringArray;
        int[] iArr = new int[getCount()];
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = 0;
        }
        this.mSectionForPosition = iArr;
        String[] strArr = this.mSections;
        if (strArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSections");
        }
        int[] iArr2 = new int[strArr.length];
        int length2 = iArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            iArr2[i3] = 0;
        }
        this.mPositionForSection = iArr2;
        int count = getCount();
        int i4 = -1;
        for (int i5 = 0; i5 < count; i5++) {
            if (i5 >= recommendCountryPhoneNumDataList.size()) {
                String str = getItem(i5).countryName;
                Intrinsics.checkExpressionValueIsNotNull(str, "getItem(i).countryName");
                if (str != null) {
                    String substring = str.substring(0, 1);
                    Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    if (substring != null) {
                        String upperCase = substring.toUpperCase();
                        Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
                        String[] strArr2 = this.mSections;
                        if (strArr2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mSections");
                        }
                        i = ArraysKt.indexOf(strArr2, upperCase);
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            } else {
                i = 0;
            }
            if (i == -1) {
                i = 0;
            }
            int[] iArr3 = this.mSectionForPosition;
            if (iArr3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSectionForPosition");
            }
            iArr3[i5] = i;
            if (i4 != i) {
                while (i4 < i) {
                    int[] iArr4 = this.mPositionForSection;
                    if (iArr4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mPositionForSection");
                    }
                    i4++;
                    iArr4[i4] = i5;
                }
                i4 = i;
            }
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<PhoneNumUtil.CountryPhoneNumData> list = this.mList;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mList");
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    @NotNull
    public PhoneNumUtil.CountryPhoneNumData getItem(int i) {
        List<PhoneNumUtil.CountryPhoneNumData> list = this.mList;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mList");
        }
        return list.get(i);
    }

    @Override // android.widget.Adapter
    @NotNull
    public View getView(int i, @Nullable View view, @NotNull ViewGroup parent) {
        AreaCodePickerListItem areaCodePickerListItem;
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        if (view == null) {
            View inflate = View.inflate(this.mContext, R.layout.passport_area_code_list_item_internal, null);
            if (inflate != null) {
                areaCodePickerListItem = (AreaCodePickerListItem) inflate;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.AreaCodePickerListItem");
            }
        } else {
            areaCodePickerListItem = (AreaCodePickerListItem) view;
        }
        areaCodePickerListItem.bind(getItem(i), null);
        return areaCodePickerListItem;
    }

    @Override // android.widget.SectionIndexer
    @Nullable
    public String[] getSections() {
        String[] strArr = this.mSections;
        if (strArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSections");
        }
        return strArr;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        int[] iArr = this.mPositionForSection;
        if (iArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPositionForSection");
        }
        return iArr[i];
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        int[] iArr = this.mSectionForPosition;
        if (iArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSectionForPosition");
        }
        return iArr[i];
    }
}
