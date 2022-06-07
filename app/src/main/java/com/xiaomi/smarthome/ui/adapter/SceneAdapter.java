package com.xiaomi.smarthome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.smarthome.ui.model.MicoMode;
import com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager;
import com.xiaomi.smarthome.ui.widgets.SceneViewHolder;

/* loaded from: classes4.dex */
public class SceneAdapter extends BaseMiotListAdapter<CommonUsedScene> {
    private final LayoutInflater a;
    private final MicoMode b;

    public SceneAdapter(Context context, MicoMode micoMode) {
        this.a = LayoutInflater.from(context);
        this.b = micoMode;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return MiotViewCachedManager.getInstance().fetchSceneViewHolder(this.b, viewGroup.getContext());
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (baseViewHolder instanceof SceneViewHolder) {
            ((SceneViewHolder) baseViewHolder).bindScene((CommonUsedScene) this.dataList.get(i));
        }
    }
}
