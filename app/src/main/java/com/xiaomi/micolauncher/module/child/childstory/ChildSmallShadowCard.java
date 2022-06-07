package com.xiaomi.micolauncher.module.child.childstory;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;

/* loaded from: classes3.dex */
public class ChildSmallShadowCard extends ConstraintLayout {
    ImageView a;
    TextView b;
    private Context c;
    private ChildStory.BlocksBean.ItemsBean d;
    private String e;

    public ChildSmallShadowCard(Context context) {
        this(context, null);
    }

    public ChildSmallShadowCard(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSmallShadowCard(final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View.inflate(context, R.layout.item_story_small_shadow_card, this);
        this.a = (ImageView) findViewById(R.id.small_card_img);
        this.b = (TextView) findViewById(R.id.small_card_name);
        this.c = context;
        this.a.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.ChildSmallShadowCard.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                if (CommonUtil.checkHasNetworkAndShowToast(context) && ChildSmallShadowCard.this.d != null && !TextUtils.isEmpty(ChildSmallShadowCard.this.d.getTarget())) {
                    ChildPlayUtil.playStory(ChildSmallShadowCard.this.getContext(), ChildSmallShadowCard.this.d);
                }
            }
        });
    }

    private void setSmallCardImg(String str) {
        Context context = this.c;
        GlideUtils.bindImageViewWithRoundNoCropUseContext(context, str, this.a, context.getResources().getDimensionPixelSize(R.dimen.story_small_shadow_card_corner_radius), R.drawable.child_story_everyday_place_bg);
    }

    private void setSmallCardName(String str) {
        this.b.setText(str);
    }

    public void setSmallShadowCardData(ChildStory.BlocksBean.ItemsBean itemsBean, String str) {
        setSmallCardImg(itemsBean.getImages().getPoster().getUrl());
        setSmallCardName(itemsBean.getTitle());
        this.d = itemsBean;
        this.e = str;
    }
}
