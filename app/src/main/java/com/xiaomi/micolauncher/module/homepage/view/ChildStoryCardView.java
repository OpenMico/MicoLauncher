package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.childstory.ChildStoryDataManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildStoryCardView extends FrameLayout {
    ImageView a;
    TextView b;
    FrameLayout c;
    View d;
    private List<String> e;
    private String f;
    private String g;
    private Station.Item h;
    @Nullable
    private GradientDrawable i;
    private int j;

    public ChildStoryCardView(@NonNull Context context) {
        this(context, null);
    }

    public ChildStoryCardView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildStoryCardView(@NonNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_child_story_card_view, this);
        this.d = inflate.findViewById(R.id.black_fore);
        this.c = (FrameLayout) inflate.findViewById(R.id.titleContainer);
        this.b = (TextView) inflate.findViewById(R.id.title_tv);
        this.a = (ImageView) inflate.findViewById(R.id.image_iv);
        a(context, attributeSet);
        FrameLayout frameLayout = this.c;
        if (frameLayout != null) {
            Drawable background = frameLayout.getBackground();
            if (background instanceof GradientDrawable) {
                this.i = (GradientDrawable) background;
                GradientDrawable gradientDrawable = this.i;
                int i2 = this.j;
                gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, i2, i2, i2, i2});
            }
        }
        setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.ChildStoryCardView.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                if (ChildStoryCardView.this.a()) {
                    SchemaManager.handleSchema(ChildStoryCardView.this.getContext(), HomepageSchemaHandler.PATH_BLACKLIST);
                } else if (ContainerUtil.isEmpty(ChildStoryCardView.this.h.getId())) {
                } else {
                    if (TextUtils.isEmpty(ChildStoryCardView.this.h.getTarget())) {
                        PlayerApi.playStation(context, ChildStoryCardView.this.h.getId(), ChildStoryCardView.this.h.getOrigin(), ChildStoryCardView.this.h.getTypeId());
                    } else {
                        ChildPlayUtil.playStory(context, ChildStoryCardView.this.h);
                    }
                }
            }
        });
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChildHotSongCardView);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        if (dimensionPixelOffset > 0) {
            setTitleContainerHeight(dimensionPixelOffset);
        }
        this.j = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        Drawable drawable = this.a.getDrawable();
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setCornerRadius(this.j);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        if (dimensionPixelSize > 0) {
            this.b.setTextSize(dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    public void setTitleContainerHeight(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.c.getLayoutParams();
        layoutParams.height = i;
        this.c.setLayoutParams(layoutParams);
    }

    public void updateTitleAndImage(Object obj, int i) {
        if (obj instanceof Station.Item) {
            this.h = (Station.Item) obj;
            this.b.setText(this.h.getTitle());
            this.f = this.h.getTitle();
            this.g = this.h.getOriginName();
            GlideUtils.bindImageViewWithRoundCorners(this.a, this.h.getItemImageUrl(), this.j, i, this.a.getWidth(), this.a.getHeight());
        }
        if (a()) {
            this.d.setVisibility(0);
        } else if (this.d.getVisibility() == 0) {
            this.d.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        this.e = ChildStoryDataManager.getManager().getBlackList();
        boolean z = false;
        if (ContainerUtil.isEmpty(this.e) || this.f == null || this.g == null) {
            return false;
        }
        for (String str : this.e) {
            if (this.f.contains(str) || this.g.contains(str)) {
                z = true;
            }
        }
        return z;
    }
}
