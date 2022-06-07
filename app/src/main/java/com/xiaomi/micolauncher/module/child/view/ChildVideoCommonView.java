package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class ChildVideoCommonView extends ConstraintLayout {
    TextView a;
    ConstraintLayout b;
    ImageView c;
    private TextView[] d;
    private ImageView[] e;
    private float f;
    private float g;
    private float h;
    private float i;
    private float j;
    private float k;

    public ChildVideoCommonView(Context context) {
        this(context, null);
    }

    public ChildVideoCommonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildVideoCommonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChildVideoCommonView);
        this.f = obtainStyledAttributes.getDimension(4, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        this.g = obtainStyledAttributes.getDimension(1, context.getResources().getDimensionPixelSize(R.dimen.child_story_big_card_radius));
        this.h = obtainStyledAttributes.getDimension(5, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        this.i = obtainStyledAttributes.getDimension(2, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        this.k = obtainStyledAttributes.getDimension(3, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        this.j = obtainStyledAttributes.getDimension(0, context.getResources().getDimensionPixelSize(R.dimen.corner_radius));
        obtainStyledAttributes.recycle();
        View inflate = View.inflate(getContext(), R.layout.item_child_video_common, this);
        this.d = new TextView[]{(TextView) inflate.findViewById(R.id.small_card_name1), (TextView) inflate.findViewById(R.id.small_card_name2), (TextView) inflate.findViewById(R.id.small_card_name3), (TextView) inflate.findViewById(R.id.small_card_name4)};
        this.e = new ImageView[]{(ImageView) inflate.findViewById(R.id.small_card_img1), (ImageView) inflate.findViewById(R.id.small_card_img2), (ImageView) inflate.findViewById(R.id.small_card_img3), (ImageView) inflate.findViewById(R.id.small_card_img4)};
        this.a = (TextView) inflate.findViewById(R.id.common_title);
        this.b = (ConstraintLayout) inflate.findViewById(R.id.common_cl);
        this.c = (ImageView) inflate.findViewById(R.id.common_more_iv);
    }
}
