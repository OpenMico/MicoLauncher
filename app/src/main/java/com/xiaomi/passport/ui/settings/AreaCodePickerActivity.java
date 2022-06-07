package com.xiaomi.passport.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;

/* loaded from: classes4.dex */
public class AreaCodePickerActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setRequestedOrientation(1);
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        AreaCodePickerFragment areaCodePickerFragment = new AreaCodePickerFragment();
        areaCodePickerFragment.setArguments(getIntent().getExtras());
        FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, areaCodePickerFragment);
    }
}
