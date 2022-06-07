package com.xiaomi.micolauncher.skills.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.WakeGuide;
import java.util.List;

/* loaded from: classes3.dex */
public class WakeGuideAdapter extends BaseQuickAdapter<WakeGuide.AnswerBean.SuggestionsBean, BaseViewHolder> {
    private WakeGuide.AnswerBean a;
    private int[] b = {R.drawable.item_wake_guide_bg1, R.drawable.item_wake_guide_bg2, R.drawable.item_wake_guide_bg3, R.drawable.item_wake_guide_bg4};

    public WakeGuideAdapter(@Nullable List<WakeGuide.AnswerBean.SuggestionsBean> list) {
        super(R.layout.item_wake_guide, list);
    }

    public void setAnswerBean(WakeGuide.AnswerBean answerBean) {
        this.a = answerBean;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(@NonNull BaseViewHolder baseViewHolder, WakeGuide.AnswerBean.SuggestionsBean suggestionsBean) {
        baseViewHolder.setText(R.id.query_title, suggestionsBean.getQuery());
        baseViewHolder.setBackgroundRes(R.id.query_title, this.b[baseViewHolder.getAdapterPosition() % this.b.length]);
        WakeStatUtil.recordWakeGuideResourceExpose(this.a, suggestionsBean, baseViewHolder.getAdapterPosition());
    }
}
