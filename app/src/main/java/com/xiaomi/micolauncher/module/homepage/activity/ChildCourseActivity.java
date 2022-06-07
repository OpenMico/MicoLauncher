package com.xiaomi.micolauncher.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ChildCourseActivity extends BaseActivity {
    public static boolean courseFullScreen;
    private View a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        courseFullScreen = true;
        setContentView(R.layout.child_course_layout);
        this.a = findViewById(R.id.main_page_mask);
        setDefaultScheduleDuration();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainPageMaskEvent(MainPageMaskEvent mainPageMaskEvent) {
        L.homepage.i(" on receive MainPageMaskEvent");
        View view = this.a;
        if (view != null) {
            view.setVisibility(mainPageMaskEvent.show ? 0 : 8);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        courseFullScreen = false;
    }
}
