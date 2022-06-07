package com.xiaomi.micolauncher.common.setting;

import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.setting.-$$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8 implements Function {
    public static final /* synthetic */ $$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8 INSTANCE = new $$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8();

    private /* synthetic */ $$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ThirdPartyResponse.UserCard a;
        a = SystemSetting.UserProfile.a((ThirdPartyResponse.UserCardResponse) obj);
        return a;
    }
}
