package com.xiaomi.smarthome.ui.widgets;

import com.xiaomi.miot.scene.CommonUsedScene;
import java.util.List;

/* loaded from: classes4.dex */
public interface MicoSceneCallback {
    void onGetSceneFailed(String str, int i, String str2);

    void onGetSceneSuccess(String str, List<CommonUsedScene> list);
}
