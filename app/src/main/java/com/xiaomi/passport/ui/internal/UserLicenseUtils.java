package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.umeng.analytics.pro.c;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Utils.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/UserLicenseUtils;", "", "()V", "privacyPolicyUrl", "", "userAgreementUrl", "getPrivacyPolicyClicked", c.R, "Landroid/content/Context;", "getProductAgreement", "Lcom/xiaomi/passport/ui/internal/License;", "getUrl", "urlFormat", "getUserAgreementClicked", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class UserLicenseUtils {
    public static final Companion Companion = new Companion(null);
    private static final String URL_MI_PRIVACY_POLICY = "https://account.xiaomi.com/about/protocol/privacy?_locale=%s";
    private static final String URL_MI_USER_AGREEMENT = "https://account.xiaomi.com/about/protocol/agreement?_locale=%s";
    private static License mProductAgreement;
    private String userAgreementUrl = PassportUI.INSTANCE.getUserAgreementUrl();
    private String privacyPolicyUrl = PassportUI.INSTANCE.getPrivacyPolicyUrl();

    /* compiled from: Utils.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/UserLicenseUtils$Companion;", "", "()V", "URL_MI_PRIVACY_POLICY", "", "URL_MI_USER_AGREEMENT", "mProductAgreement", "Lcom/xiaomi/passport/ui/internal/License;", "addLicense", "", "readableText", "url", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void addLicense(@NotNull String readableText, @NotNull String url) {
            Intrinsics.checkParameterIsNotNull(readableText, "readableText");
            Intrinsics.checkParameterIsNotNull(url, "url");
            UserLicenseUtils.mProductAgreement = new License(readableText, url);
        }
    }

    @NotNull
    public final String getUserAgreementClicked(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (TextUtils.isEmpty(this.userAgreementUrl)) {
            this.userAgreementUrl = getUrl(URL_MI_USER_AGREEMENT, context);
        }
        String str = this.userAgreementUrl;
        if (str == null) {
            Intrinsics.throwNpe();
        }
        return str;
    }

    @NotNull
    public final String getPrivacyPolicyClicked(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (TextUtils.isEmpty(this.privacyPolicyUrl)) {
            this.privacyPolicyUrl = getUrl(URL_MI_PRIVACY_POLICY, context);
        }
        String str = this.privacyPolicyUrl;
        if (str == null) {
            Intrinsics.throwNpe();
        }
        return str;
    }

    @Nullable
    public final License getProductAgreement() {
        return mProductAgreement;
    }

    private final String getUrl(String str, Context context) {
        Resources resources = context.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "context.resources");
        String locale = resources.getConfiguration().locale.toString();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {locale};
        String format = String.format(str, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        return format;
    }
}
