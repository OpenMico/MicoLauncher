package com.xiaomi.micolauncher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public interface LauncherState {
    boolean needRegisterEventBus();

    void onActivityResult(int i, int i2, @Nullable Intent intent);

    void onCreate(@Nullable Bundle bundle);

    void onDestroy();

    void onDispatchTouchEvent(MotionEvent motionEvent);

    default boolean onNewIntent(Intent intent) {
        return false;
    }

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle bundle);

    void onTrimMemory(int i);
}
