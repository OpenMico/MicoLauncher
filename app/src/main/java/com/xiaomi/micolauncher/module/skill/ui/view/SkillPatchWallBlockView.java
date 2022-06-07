package com.xiaomi.micolauncher.module.skill.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.Random;

/* loaded from: classes3.dex */
public class SkillPatchWallBlockView extends FrameLayout {
    private int a;
    private int b;
    private boolean c;

    public SkillPatchWallBlockView(@NonNull Context context) {
        this(context, null);
    }

    public SkillPatchWallBlockView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SkillPatchWallBlockView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = ChildModeManager.getManager().isChildMode();
        Resources resources = getResources();
        boolean z = this.c;
        int i2 = R.dimen.skill_app_margin;
        this.a = resources.getDimensionPixelSize(z ? R.dimen.skill_item_child_margin_top : R.dimen.skill_app_margin);
        this.b = getResources().getDimensionPixelSize(this.c ? R.dimen.skill_item_child_margin : i2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(b(), View.MeasureSpec.getSize(i2));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < getChildCount(); i8++) {
            View childAt = getChildAt(i8);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i9 = i8 % 2;
            int i10 = i9 == 0 ? i6 : i5;
            childAt.layout(i10, i7, i10 + measuredWidth, i7 + measuredHeight);
            if (i9 == 0) {
                i7 = measuredHeight + this.a;
                i6 += measuredWidth + this.b;
            } else {
                i5 += measuredWidth + this.b;
                i7 = 0;
            }
        }
    }

    private View a() {
        return LayoutInflater.from(getContext()).inflate(getLayoutResourceId(), (ViewGroup) null);
    }

    private int getLayoutResourceId() {
        return Hardware.isBigScreen() ? this.c ? R.layout.view_skill_patch_wall_skill_item_child : R.layout.view_skill_patch_wall_skill_item : R.layout.view_skill_patch_wall_item;
    }

    public void refreshContent(List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof Skill.SkillBean) {
                Skill.SkillBean skillBean = (Skill.SkillBean) obj;
                String[][] strArr = skillBean.tipList;
                int nextInt = new Random().nextInt(strArr.length);
                ((TextView) getChildAt(i).findViewById(R.id.content_tv)).setText(skillBean.tipList[nextInt][new Random().nextInt(strArr[nextInt].length)]);
            }
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void setDataList(List<Object> list) {
        for (Object obj : list) {
            View a = a();
            final ImageView imageView = (ImageView) a.findViewById(R.id.image_iv);
            final TextView textView = (TextView) a.findViewById(R.id.title_tv);
            a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
            if (obj instanceof SkillApp) {
                final SkillApp skillApp = (SkillApp) obj;
                GlideUtils.bindImageView(getContext(), skillApp.getIconRes(), imageView);
                RxViewHelp.debounceClicksWithOneSeconds(a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.view.-$$Lambda$SkillPatchWallBlockView$qetRshC025j-Eab3QfLag8L8MHI
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj2) {
                        SkillPatchWallBlockView.this.a(skillApp, obj2);
                    }
                });
            } else if (obj instanceof Skill.SkillBean) {
                final Skill.SkillBean skillBean = (Skill.SkillBean) obj;
                textView.setText(skillBean.name);
                if (Hardware.isBigScreen()) {
                    GlideUtils.bindImageView(imageView, skillBean.imageUrl, UiUtils.getSize(getContext(), R.dimen.skill_item_width), UiUtils.getSize(getContext(), this.c ? R.dimen.skill_item_height_child : R.dimen.skill_item_height));
                    String[][] strArr = skillBean.tipList;
                    int nextInt = new Random().nextInt(strArr.length);
                    ((TextView) a.findViewById(R.id.content_tv)).setText(skillBean.tipList[nextInt][new Random().nextInt(strArr[nextInt].length)]);
                } else {
                    a.setLayoutParams(new FrameLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.skill_app_width), getResources().getDimensionPixelSize(R.dimen.skill_app_height)));
                    GlideUtils.bindImageView(getContext(), skillBean.imageUrl, imageView);
                }
                RxViewHelp.debounceClicksWithOneSeconds(a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.view.-$$Lambda$SkillPatchWallBlockView$Uc5xKLV76qcfpbojDm8NSv5T37A
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj2) {
                        SkillPatchWallBlockView.this.a(skillBean, obj2);
                    }
                });
            } else if (obj instanceof Skill.SkillType) {
                final Skill.SkillType skillType = (Skill.SkillType) obj;
                GlideUtils.bindImageViewWithListener(getContext(), skillType.imageUrl, imageView, new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.module.skill.ui.view.SkillPatchWallBlockView.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    /* renamed from: a */
                    public void setResource(@Nullable Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        textView.setText(skillType.name);
                    }

                    @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        textView.setText(skillType.name);
                    }
                });
                a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.view.-$$Lambda$SkillPatchWallBlockView$884b8B0jYWJ9lh4t3R_0zizxqdo
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SkillPatchWallBlockView.this.a(skillType, view);
                    }
                });
            } else if (obj instanceof Skill.SkillPlaceHolder) {
                GlideUtils.bindImageView(getContext(), ((Skill.SkillPlaceHolder) obj).imageResId, imageView);
            }
            addView(a, new FrameLayout.LayoutParams(-2, -2));
        }
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SkillApp skillApp, Object obj) throws Exception {
        SchemaManager.handleSchema(getContext(), skillApp.getUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Skill.SkillBean skillBean, Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(getContext())) {
            SchemaManager.handleSchema(getContext(), skillBean.action);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Skill.SkillType skillType, View view) {
        SchemaManager.handleSchema(getContext(), skillType.action);
    }

    private int b() {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if (i3 % 2 != 0) {
                i += childAt.getMeasuredWidth() + this.b;
            } else {
                i2 += childAt.getMeasuredWidth() + this.b;
            }
        }
        return Math.max(i, i2);
    }

    public int getScrollToPositionX() {
        View childAt;
        int currentPosition = SkillDataManager.getManager().getCurrentPosition();
        if (currentPosition <= 0) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 <= Math.min(getChildCount(), currentPosition); i2++) {
            if (i2 % 2 == currentPosition % 2 && (childAt = getChildAt(i2)) != null) {
                i += childAt.getMeasuredWidth() + this.b;
            }
        }
        return i;
    }
}
