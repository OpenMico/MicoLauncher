package com.xiaomi.passport.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;

/* loaded from: classes4.dex */
public class UserAvatarUpdateActivity extends AppCompatActivity {
    public static final String CAMERA = "camera";
    public static final String EXTRA_UPDATE_AVATAR_TYPE = "update_avatar_type";
    public static final String GALLERY = "gallery";
    private static final String TAG = "UserAvatarUpdateActivity";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        UserAvatarUpdateFragment userAvatarUpdateFragment = new UserAvatarUpdateFragment();
        userAvatarUpdateFragment.setArguments(getIntent().getExtras());
        FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, userAvatarUpdateFragment);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MiAccountManager.get(this).getXiaomiAccount() == null) {
            AccountLog.w(TAG, "no xiaomi account");
            finish();
        }
    }
}
