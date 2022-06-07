package com.xiaomi.micolauncher.module.homepage.viewholder.skill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.common.view.base.FluidLayout;
import com.xiaomi.micolauncher.module.homepage.bean.Explore;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.manager.SkillAnimationUtils;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* loaded from: classes3.dex */
public class ExploreViewHolder extends BaseViewHolder {
    FluidLayout a;
    private Context b;
    private boolean c = ChildModeManager.getManager().isChildMode();

    public ExploreViewHolder(View view) {
        super(view);
        this.b = view.getContext();
        this.a = (FluidLayout) view.findViewById(R.id.fluid_layout);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void setData(List<Object> list) {
        super.setData(list);
        if (ContainerUtil.hasData(list)) {
            if (this.a.getChildCount() != 0) {
                this.a.removeAllViews();
            }
            Iterator<Integer> it = a(list).iterator();
            int i = 0;
            while (it.hasNext()) {
                Integer next = it.next();
                if (i < 15) {
                    i++;
                    final Explore.ExploreBean exploreBean = (Explore.ExploreBean) list.get(next.intValue());
                    final View inflate = LayoutInflater.from(this.b).inflate(a(), (ViewGroup) null);
                    final TextView textView = (TextView) inflate.findViewById(R.id.title_tv);
                    textView.setText(exploreBean.getContent());
                    ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                    marginLayoutParams.setMargins(0, 0, UiUtils.getSize(this.b, R.dimen.explore_tip_margin_right), UiUtils.getSize(this.b, R.dimen.explore_tip_margin_bottom));
                    this.a.addView(inflate, marginLayoutParams);
                    final ImageView imageView = (ImageView) inflate.findViewById(R.id.image_iv);
                    inflate.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.skill.ExploreViewHolder.1
                        @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                        public void onAvoidFastDoubleClick(View view) {
                            if (CommonUtil.checkHasNetworkAndShowToast(ExploreViewHolder.this.b)) {
                                SpeechManager.getInstance().nlpTtsRequest(exploreBean.getContent(), true);
                                SkillAnimationUtils.startClickAnimation(imageView, textView.getMeasuredWidth() - (UiUtils.getSize(ExploreViewHolder.this.b, R.dimen.explore_tip_click_reduce_size) * 2));
                                ExploreViewHolder.this.a.startAnimationSet(inflate);
                            }
                        }
                    });
                    inflate.setOnTouchListener(MicoAnimationTouchListener.getInstance());
                } else {
                    return;
                }
            }
        }
    }

    private int a() {
        return this.c ? R.layout.explore_tip_layout_child : R.layout.explore_tip_layout;
    }

    private HashSet<Integer> a(List<Object> list) {
        HashSet<Integer> hashSet = new HashSet<>();
        Random random = new Random();
        int size = list.size();
        for (int i = 0; i < 15; i++) {
            hashSet.add(Integer.valueOf(random.nextInt(size)));
        }
        return hashSet;
    }
}
