package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.skill.AppViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.skill.ExploreViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.skill.SkillViewHolder;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillAdapter extends BaseAdapter<BaseViewHolder> {
    public static final int TYPE_EXPLORE = 1;
    public static final int TYPE_SKILL = 2;
    private List<List<Object>> a;
    private boolean b = ChildModeManager.getManager().isChildMode();

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public SkillAdapter(List<List<Object>> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseViewHolder baseViewHolder;
        int i2 = R.layout.app_layout_child;
        switch (i) {
            case 0:
                if (!this.b) {
                    i2 = R.layout.app_layout;
                }
                baseViewHolder = new AppViewHolder(inflateView(viewGroup, i2));
                break;
            case 1:
                baseViewHolder = new ExploreViewHolder(inflateView(viewGroup, this.b ? R.layout.explore_layout_child : R.layout.explore_layout));
                break;
            case 2:
                baseViewHolder = new SkillViewHolder(inflateView(viewGroup, this.b ? R.layout.skill_layout_child : R.layout.skill_layout));
                break;
            default:
                if (!this.b) {
                    i2 = R.layout.app_layout;
                }
                baseViewHolder = new AppViewHolder(inflateView(viewGroup, i2));
                break;
        }
        return baseViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        L.skillpage.d("Skill Adapter onBindeViewHolder : %d", Integer.valueOf(i));
        baseViewHolder.setData(this.a.get(i));
    }
}
