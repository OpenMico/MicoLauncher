package com.xiaomi.passport.ui.sns;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.xiaomi.passport.ui.internal.AuthProvider;
import com.xiaomi.passport.ui.internal.PassportUI;
import com.xiaomi.passport.ui.internal.WeChatAuthProvider;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import org.jetbrains.annotations.Nullable;

/* compiled from: wechatEntry.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/sns/WXEntryActivity;", "Landroid/app/Activity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class WXEntryActivity extends Activity {
    private HashMap _$_findViewCache;

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

    @Override // android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        AuthProvider provider = PassportUI.INSTANCE.getProvider(PassportUI.WECHAT_AUTH_PROVIDER);
        if (provider != null) {
            ((WeChatAuthProvider) provider).handleWxIntent(this, getIntent());
            finish();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.WeChatAuthProvider");
    }
}
