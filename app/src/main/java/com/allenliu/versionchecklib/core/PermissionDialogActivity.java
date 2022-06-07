package com.allenliu.versionchecklib.core;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;
import com.allenliu.versionchecklib.v2.ui.AllenBaseActivity;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes.dex */
public class PermissionDialogActivity extends AllenBaseActivity {
    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showCustomDialog() {
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity
    public void showDefaultDialog() {
    }

    @Override // com.allenliu.versionchecklib.v2.ui.AllenBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            a(true);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 291);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 291);
        }
    }

    private void a(boolean z) {
        Intent intent = new Intent();
        intent.setAction(AVersionService.PERMISSION_ACTION);
        intent.putExtra("result", z);
        sendBroadcast(intent);
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setEventType(99);
        commonEvent.setSuccessful(true);
        commonEvent.setData(Boolean.valueOf(z));
        EventBus.getDefault().post(commonEvent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 291) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(this, getString(R.string.versionchecklib_write_permission_deny), 1).show();
                a(false);
                return;
            }
            a(true);
        }
    }
}
