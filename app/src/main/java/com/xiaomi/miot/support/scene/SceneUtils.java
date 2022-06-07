package com.xiaomi.miot.support.scene;

import android.os.Bundle;
import android.os.RemoteException;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.service.ICallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes3.dex */
public class SceneUtils {
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_DETAIL = "errorDetail";

    /* loaded from: classes3.dex */
    public static final class GetSceneICallback extends ICallback.Stub {
        private final GetSceneCallback callback;

        public GetSceneICallback(GetSceneCallback getSceneCallback) {
            this.callback = getSceneCallback;
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onSuccess(Bundle bundle) throws RemoteException {
            List<CommonUsedScene> arrayList = new ArrayList<>();
            String string = bundle.getString("scene_list");
            if (string != null) {
                try {
                    arrayList = CommonUsedScene.parseListFrom(new JSONArray(string));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            GetSceneCallback getSceneCallback = this.callback;
            if (getSceneCallback != null) {
                getSceneCallback.onGetSceneSuccess(arrayList);
            }
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onFailure(Bundle bundle) throws RemoteException {
            int i = bundle.getInt("errorCode");
            String str = "";
            if (bundle.containsKey(SceneUtils.ERROR_DETAIL)) {
                str = bundle.getString(SceneUtils.ERROR_DETAIL);
            }
            GetSceneCallback getSceneCallback = this.callback;
            if (getSceneCallback != null) {
                getSceneCallback.onGetSceneFailed(i, str);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class RunSceneICallback extends ICallback.Stub {
        private final RunSceneCallback callback;
        private final String sceneName;

        public RunSceneICallback(RunSceneCallback runSceneCallback, String str) {
            this.callback = runSceneCallback;
            this.sceneName = str;
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onSuccess(Bundle bundle) throws RemoteException {
            RunSceneCallback runSceneCallback = this.callback;
            if (runSceneCallback != null) {
                runSceneCallback.onRunSceneSuccess(this.sceneName);
            }
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onFailure(Bundle bundle) throws RemoteException {
            int i = bundle.getInt("errorCode");
            String str = "";
            if (bundle.containsKey(SceneUtils.ERROR_DETAIL)) {
                str = bundle.getString(SceneUtils.ERROR_DETAIL);
            }
            RunSceneCallback runSceneCallback = this.callback;
            if (runSceneCallback != null) {
                runSceneCallback.onRunSceneFailed(this.sceneName, i, str);
            }
        }
    }

    private SceneUtils() {
    }
}
