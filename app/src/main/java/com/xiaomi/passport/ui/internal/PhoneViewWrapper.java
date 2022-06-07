package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.passport.ui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PhoneViewWrapper.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\rJ\u0006\u0010\u001f\u001a\u00020 J\b\u0010!\u001a\u0004\u0018\u00010\"J\b\u0010#\u001a\u00020$H\u0002J\u0016\u0010%\u001a\u00020 2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002R\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\f\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006&"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhoneViewWrapper;", "", "sid", "", c.R, "Landroid/content/Context;", "phone", "Landroid/widget/AutoCompleteTextView;", "countryCode", "Landroid/widget/TextView;", "deletePhone", "Landroid/view/View;", "passport_operator_license", "(Ljava/lang/String;Landroid/content/Context;Landroid/widget/AutoCompleteTextView;Landroid/widget/TextView;Landroid/view/View;Landroid/widget/TextView;)V", "activateInfoList", "", "Lcom/xiaomi/accountsdk/account/data/ActivatorPhoneInfo;", "getContext", "()Landroid/content/Context;", "getDeletePhone", "()Landroid/view/View;", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassport_operator_license", "()Landroid/widget/TextView;", "getPhone", "()Landroid/widget/AutoCompleteTextView;", "phoneTextWatcher", "Landroid/text/TextWatcher;", "getSid", "()Ljava/lang/String;", "destroy", "", "getPhoneWrapper", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "isInputValid", "", "setPhonePopList", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhoneViewWrapper {
    private List<? extends ActivatorPhoneInfo> activateInfoList;
    @NotNull
    private final Context context;
    private final TextView countryCode;
    @NotNull
    private final View deletePhone;
    private PassportRepo passportRepo = new PassportRepoImpl();
    @Nullable
    private final TextView passport_operator_license;
    @NotNull
    private final AutoCompleteTextView phone;
    private TextWatcher phoneTextWatcher;
    @NotNull
    private final String sid;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: PhoneViewWrapper.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "Lcom/xiaomi/accountsdk/account/data/ActivatorPhoneInfo;", "invoke"}, k = 3, mv = {1, 1, 10})
    /* renamed from: com.xiaomi.passport.ui.internal.PhoneViewWrapper$1 */
    /* loaded from: classes4.dex */
    public static final class AnonymousClass1 extends Lambda implements Function1<List<? extends ActivatorPhoneInfo>, Unit> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1() {
            super(1);
            PhoneViewWrapper.this = r1;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(List<? extends ActivatorPhoneInfo> list) {
            invoke2(list);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke */
        public final void invoke2(@NotNull List<? extends ActivatorPhoneInfo> it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            PhoneViewWrapper.this.activateInfoList = it;
            PhoneViewWrapper.this.setPhonePopList(it);
        }
    }

    public PhoneViewWrapper(@NotNull String sid, @NotNull Context context, @NotNull AutoCompleteTextView phone, @NotNull TextView countryCode, @NotNull View deletePhone, @Nullable TextView textView) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        Intrinsics.checkParameterIsNotNull(countryCode, "countryCode");
        Intrinsics.checkParameterIsNotNull(deletePhone, "deletePhone");
        this.sid = sid;
        this.context = context;
        this.phone = phone;
        this.countryCode = countryCode;
        this.deletePhone = deletePhone;
        this.passport_operator_license = textView;
        if (!PassportUI.INSTANCE.getInternational()) {
            this.passportRepo.getLocalActivatorPhone(this.context, true).getSuccess(new AnonymousClass1());
        }
        this.phoneTextWatcher = new TextWatcher() { // from class: com.xiaomi.passport.ui.internal.PhoneViewWrapper.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable editable) {
                if (TextUtils.isEmpty(editable != null ? editable.toString() : null)) {
                    PhoneViewWrapper.this.getDeletePhone().setVisibility(8);
                    TextView passport_operator_license = PhoneViewWrapper.this.getPassport_operator_license();
                    if (passport_operator_license != null) {
                        passport_operator_license.setVisibility(8);
                        return;
                    }
                    return;
                }
                PhoneViewWrapper.this.getDeletePhone().setVisibility(0);
            }
        };
        this.phone.addTextChangedListener(this.phoneTextWatcher);
        this.deletePhone.setVisibility(8);
        this.deletePhone.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhoneViewWrapper.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PhoneViewWrapper.this.getPhone().setEnabled(true);
                PhoneViewWrapper.this.getPhone().setText("");
            }
        });
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    @NotNull
    public final AutoCompleteTextView getPhone() {
        return this.phone;
    }

    @NotNull
    public final String getSid() {
        return this.sid;
    }

    @NotNull
    public final View getDeletePhone() {
        return this.deletePhone;
    }

    @Nullable
    public final TextView getPassport_operator_license() {
        return this.passport_operator_license;
    }

    private final boolean isInputValid() {
        return !TextUtils.isEmpty(this.phone.getText().toString());
    }

    @Nullable
    public final PhoneWrapper getPhoneWrapper() {
        if (!isInputValid()) {
            return null;
        }
        String obj = this.phone.getText().toString();
        List<? extends ActivatorPhoneInfo> list = this.activateInfoList;
        if (list != null) {
            if (list == null) {
                Intrinsics.throwNpe();
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                if (Intrinsics.areEqual(((ActivatorPhoneInfo) obj2).phone, obj)) {
                    arrayList.add(obj2);
                }
            }
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                return new PhoneWrapper((ActivatorPhoneInfo) it.next(), this.sid);
            }
        }
        if (Intrinsics.areEqual(this.countryCode.getText().toString(), PassportUI.CHINA_COUNTRY_CODE)) {
            return new PhoneWrapper(obj, this.sid);
        }
        return new PhoneWrapper(this.countryCode.getText().toString() + obj, this.sid);
    }

    public final void destroy() {
        this.phone.removeTextChangedListener(this.phoneTextWatcher);
        this.phoneTextWatcher = null;
    }

    public final void setPhonePopList(List<? extends ActivatorPhoneInfo> list) {
        if (!list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (ActivatorPhoneInfo activatorPhoneInfo : list) {
                String str = activatorPhoneInfo.phone;
                Intrinsics.checkExpressionValueIsNotNull(str, "it.phone");
                arrayList.add(str);
            }
            new PhoneViewWrapper$setPhonePopList$adapter$1(this, list, arrayList, this.context, R.layout.phone_list_item, arrayList).initView(this.phone);
        }
    }
}
