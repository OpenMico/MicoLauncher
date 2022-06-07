package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4ControlDevice;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.support.scene.RunSceneCallback;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.model.MicoMode;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class SceneViewHolder extends BaseViewHolder implements RunSceneCallback {
    private final TextView a;
    private final ImageView b;
    private CommonUsedScene c;
    private MicoMode d;

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    public SceneViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.name);
        this.b = (ImageView) view.findViewById(R.id.icon);
    }

    public void setMicoMode(MicoMode micoMode) {
        this.d = micoMode;
    }

    public void bindScene(CommonUsedScene commonUsedScene) {
        this.c = commonUsedScene;
        CommonUsedScene commonUsedScene2 = this.c;
        if (commonUsedScene2 != null) {
            this.a.setText(commonUsedScene2.sceneName);
            if (TextUtils.equals(this.c.sceneName, MicoSceneUtils.SCENE_NAME_LEFT_HOME)) {
                this.b.setBackgroundResource(R.drawable.scene_left_home);
            } else if (TextUtils.equals(this.c.sceneName, MicoSceneUtils.SCENE_NAME_GOOD_NIGHT)) {
                this.b.setBackgroundResource(R.drawable.scene_good_night);
            } else {
                this.b.setBackgroundResource(R.drawable.scene_custom);
            }
        }
    }

    @Override // com.xiaomi.miot.support.scene.RunSceneCallback
    public void onRunSceneSuccess(final String str) {
        L.miot.d("run scene success.");
        LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_SCENE_CHANGED).post(1);
        if (this.itemView != null) {
            this.itemView.post(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$SceneViewHolder$uDEJTQa8avFY_5lnWFqJHkAvaEc
                @Override // java.lang.Runnable
                public final void run() {
                    SceneViewHolder.this.a(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) {
        ToastWithPicture.showToast(this.itemView.getContext(), MicoSceneUtils.getSceneImage(str), this.itemView.getResources().getString(R.string.run_scene_success, str));
    }

    @Override // com.xiaomi.miot.support.scene.RunSceneCallback
    public void onRunSceneFailed(String str, int i, String str2) {
        Logger logger = L.miot;
        logger.d("run scene fail:" + i + str2);
        final int sceneImage = MicoSceneUtils.getSceneImage(str);
        final String string = this.itemView.getResources().getString(R.string.run_scene_fail, str);
        if (this.itemView != null) {
            this.itemView.post(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$SceneViewHolder$CVBn0YMjbmoP_e5qBRlwo7_kq3c
                @Override // java.lang.Runnable
                public final void run() {
                    SceneViewHolder.this.a(sceneImage, string);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, String str) {
        ToastWithPicture.showToast(this.itemView.getContext(), i, str);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.b, MicoAnimConfigurator4ControlDevice.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$SceneViewHolder$baA7hYZ5KkN-4KN8g533-z7zQ5k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SceneViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        if (this.c != null) {
            MicoMiotDeviceManager.getInstance().runScene(this.c, this);
            if (this.d == MicoMode.CATEGORY) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_SCENE);
            } else if (this.d == MicoMode.ROOM) {
                SmartHomeStatHelper.recordSmartTabRoomModeClick(SmartHomeStatHelper.PARAM_VALUE_SCENE);
            }
        }
    }
}
