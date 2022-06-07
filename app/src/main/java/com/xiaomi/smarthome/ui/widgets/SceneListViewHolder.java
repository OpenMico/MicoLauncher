package com.xiaomi.smarthome.ui.widgets;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.adapter.SceneAdapter;
import com.xiaomi.smarthome.ui.model.MicoMode;
import java.util.List;

/* loaded from: classes4.dex */
public class SceneListViewHolder extends BaseViewHolder {
    private final SceneAdapter a;
    private final ImageView b;
    private final RecyclerView c;

    public SceneListViewHolder(@NonNull final View view, final MicoMode micoMode) {
        super(view);
        this.c = (RecyclerView) view.findViewById(R.id.recyclerview);
        this.b = (ImageView) view.findViewById(R.id.ivArrow);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), 1, false) { // from class: com.xiaomi.smarthome.ui.widgets.SceneListViewHolder.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };
        linearLayoutManager.setItemPrefetchEnabled(true);
        linearLayoutManager.setInitialPrefetchItemCount(1);
        this.c.setLayoutManager(linearLayoutManager);
        this.c.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.smarthome.ui.widgets.SceneListViewHolder.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                int i;
                super.getItemOffsets(rect, view2, recyclerView, state);
                if (micoMode == MicoMode.CATEGORY) {
                    i = view.getResources().getDimensionPixelSize(R.dimen.mico_miot_scene_item_category_padding);
                } else {
                    i = view.getResources().getDimensionPixelSize(R.dimen.mico_miot_scene_item_room_padding);
                }
                rect.bottom = i;
            }
        });
        this.c.setItemAnimator(null);
        this.a = new SceneAdapter(view.getContext(), micoMode);
        this.c.setAdapter(this.a);
        this.c.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.smarthome.ui.widgets.SceneListViewHolder.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i != 0) {
                    return;
                }
                if (recyclerView.canScrollVertically(1)) {
                    SceneListViewHolder.this.b.setVisibility(0);
                } else {
                    SceneListViewHolder.this.b.setVisibility(8);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        });
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4BigButton.get());
    }

    public void bindData(List<CommonUsedScene> list) {
        this.a.updateDataList(list);
        if (list == null || list.size() <= 3) {
            this.b.setVisibility(8);
        } else {
            this.b.setVisibility(0);
        }
    }
}
