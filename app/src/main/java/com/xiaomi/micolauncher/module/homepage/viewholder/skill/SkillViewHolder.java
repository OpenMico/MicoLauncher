package com.xiaomi.micolauncher.module.homepage.viewholder.skill;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.skill.ui.view.SkillPatchWallBlockView;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillViewHolder extends BaseViewHolder {
    SkillPatchWallBlockView a;

    public SkillViewHolder(View view) {
        super(view);
        this.a = (SkillPatchWallBlockView) view.findViewById(R.id.skill_block_view);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void setData(List<Object> list) {
        if (this.a.getChildCount() == list.size()) {
            this.a.refreshContent(list);
        } else {
            this.a.removeAllViews();
            this.a.setDataList(list);
        }
        L.skillpage.d("skill beans : %s", list);
    }
}
