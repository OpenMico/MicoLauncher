package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.c;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CountryCodeUtilsExtension.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t"}, d2 = {"countryCodeWithPrefix", "", "Lcom/xiaomi/passport/ui/internal/PhoneNumUtil$CountryPhoneNumData;", "getCountryCodeWithPrefix", "(Lcom/xiaomi/passport/ui/internal/PhoneNumUtil$CountryPhoneNumData;)Ljava/lang/String;", "smartGetCountryCodeData", c.R, "Landroid/content/Context;", "icOrIso", "passportui_release"}, k = 2, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class CountryCodeUtilsExtensionKt {
    @Nullable
    public static final PhoneNumUtil.CountryPhoneNumData smartGetCountryCodeData(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List<PhoneNumUtil.CountryPhoneNumData> sortedData = PhoneNumUtil.getCountryPhoneNumDataList(context);
        Intrinsics.checkExpressionValueIsNotNull(sortedData, "sortedData");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = sortedData.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                return (PhoneNumUtil.CountryPhoneNumData) CollectionsKt.getOrNull(arrayList, 0);
            }
            Object next = it.next();
            PhoneNumUtil.CountryPhoneNumData it2 = (PhoneNumUtil.CountryPhoneNumData) next;
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            if (Intrinsics.areEqual(str, getCountryCodeWithPrefix(it2)) || Intrinsics.areEqual(str, it2.countryISO)) {
                z = true;
            }
            if (z) {
                arrayList.add(next);
            }
        }
    }

    @NotNull
    public static final String getCountryCodeWithPrefix(@NotNull PhoneNumUtil.CountryPhoneNumData receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return '+' + receiver.countryCode;
    }
}
