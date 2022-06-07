package com.xiaomi.miot.support.scene;

import com.xiaomi.miot.scene.CommonUsedScene;
import java.util.List;

/* loaded from: classes3.dex */
public interface GetSceneCallback {
    void onGetSceneFailed(int i, String str);

    void onGetSceneSuccess(List<CommonUsedScene> list);
}
