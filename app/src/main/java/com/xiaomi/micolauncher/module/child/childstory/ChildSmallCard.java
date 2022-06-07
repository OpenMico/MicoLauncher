package com.xiaomi.micolauncher.module.child.childstory;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;

/* loaded from: classes3.dex */
public class ChildSmallCard extends LinearLayout {
    ImageView a;
    TextView b;
    private String c;
    private int d;
    private Context e;
    private ChildStory.BlocksBean.ItemsBean f;
    private boolean g;

    public int getResId(int i) {
        switch (i) {
            case 1:
                return R.drawable.child_story_loop1_place_bg;
            case 2:
                return R.drawable.child_story_loop2_place_bg;
            default:
                return R.drawable.child_story_loop3_place_bg;
        }
    }

    public ChildSmallCard(Context context) {
        this(context, null);
    }

    public ChildSmallCard(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSmallCard(final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.item_story_small_card, this);
        setOrientation(1);
        this.a = (ImageView) findViewById(R.id.small_card_img);
        this.b = (TextView) findViewById(R.id.small_card_name);
        this.d = UiUtils.getSize(context, R.dimen.story_small_card_size);
        this.a.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.ChildSmallCard.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                if (CommonUtil.checkHasNetworkAndShowToast(context) && ChildSmallCard.this.f != null && !TextUtils.isEmpty(ChildSmallCard.this.f.getTarget())) {
                    ChildPlayUtil.playStory(ChildSmallCard.this.getContext(), ChildSmallCard.this.f);
                    if (!TextUtils.isEmpty(ChildSmallCard.this.c) && ChildSmallCard.this.g) {
                        ChildStatHelper.recordChildTabCardClick(ChildSmallCard.this.c.concat("–").concat(ChildSmallCard.this.f.getTitle()));
                    }
                }
            }
        });
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    private void a(String str, int i) {
        Context context = this.e;
        ImageView imageView = this.a;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.story_small_card_corner_radius);
        int resId = getResId(i);
        int i2 = this.d;
        GlideUtils.bindImageViewWithRoundUseContext(context, str, imageView, dimensionPixelSize, resId, i2, i2);
    }

    private void setSmallCardName(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.setText(str);
        }
    }

    public void setSmallCardData(Context context, ChildStory.BlocksBean.ItemsBean itemsBean, int i, String str, boolean z) {
        this.e = context;
        this.g = z;
        a(itemsBean.getItemImageUrl(), i);
        setSmallCardName(itemsBean.getTitle());
        this.f = itemsBean;
        this.c = str;
    }
}
