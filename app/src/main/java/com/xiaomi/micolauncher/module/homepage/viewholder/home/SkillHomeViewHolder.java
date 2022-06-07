package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillHomeViewHolder extends BaseHomeViewHolder {
    TextView a;
    TextView b;
    ImageView c;
    TextView[] d = new TextView[5];
    private List<String> e = new ArrayList(this.d.length);
    private Context f;
    private List<MainPage.SkillApp> g;
    private MainPage.SkillApp h;

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    public void sendRefreshMsg() {
    }

    public SkillHomeViewHolder(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.skill_title);
        this.b = (TextView) view.findViewById(R.id.skill_subtitle);
        this.c = (ImageView) view.findViewById(R.id.skill_img);
        this.d[0] = (TextView) view.findViewById(R.id.tip_first);
        this.d[1] = (TextView) view.findViewById(R.id.tip_second);
        this.d[2] = (TextView) view.findViewById(R.id.tip_third);
        this.d[3] = (TextView) view.findViewById(R.id.tip_fourth);
        this.d[4] = (TextView) view.findViewById(R.id.tip_fifth);
        this.f = view.getContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder, com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initViewInMain() {
        super.initViewInMain();
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$SkillHomeViewHolder$tEFKbzkRHrHorppXe84ksQFoo7w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SkillHomeViewHolder.this.a(view);
            }
        });
        final int i = 0;
        while (true) {
            TextView[] textViewArr = this.d;
            if (i < textViewArr.length) {
                RxViewHelp.debounceClicksWithOneSeconds(textViewArr[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$SkillHomeViewHolder$Ju6qEa5IaGBkcB4as1TMd0RX6hU
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SkillHomeViewHolder.this.a(i, obj);
                    }
                });
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        MainPage.SkillApp skillApp;
        if (CommonUtil.checkHasNetworkAndShowToast(this.f) && (skillApp = this.h) != null) {
            SchemaManager.handleSchema(this.f, skillApp.getActionURL());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(this.f)) {
            SpeechManager.getInstance().nlpTtsRequest(this.e.get(i), true);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected boolean empty() {
        this.g = RecommendDataManager.getManager().getRecommendSkillApps();
        return ContainerUtil.isEmpty(this.g);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder
    protected void fillData() {
        this.h = this.g.get(0);
        this.a.setText(this.h.getTitle());
        this.b.setText(this.h.getDescription());
        if (ContainerUtil.hasData(this.h.getTips())) {
            this.e = this.h.getTips();
            a();
        }
        UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.-$$Lambda$SkillHomeViewHolder$IZF-3N-Mx8r8Brqb8B89WfMvBTg
            @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
            public final void onLayoutComplete(boolean z) {
                SkillHomeViewHolder.this.a(z);
            }
        }, this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z) {
        GlideUtils.bindImageView(this.f, this.h.getImageURL(), this.c, (int) R.drawable.home_skillbanner, UiUtils.getSize(this.f, R.dimen.home_skill_item_width), UiUtils.getSize(this.f, R.dimen.home_skill_item_height));
    }

    private void a() {
        for (int i = 0; i < this.d.length; i++) {
            if (i >= this.e.size()) {
                b(this.d[i]);
            } else {
                a(this.d[i]);
                this.d[i].setText(this.e.get(i));
                this.d[i].setTextColor(Color.parseColor(this.h.getTipColor()));
            }
        }
    }

    private void a(TextView textView) {
        if (textView.getVisibility() == 8) {
            textView.setVisibility(0);
        }
    }

    private void b(TextView textView) {
        if (textView.getVisibility() == 0) {
            textView.setVisibility(8);
        }
    }
}
