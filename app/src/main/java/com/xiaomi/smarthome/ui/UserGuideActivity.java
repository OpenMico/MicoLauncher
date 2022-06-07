package com.xiaomi.smarthome.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.common.collect.Lists;
import com.xiaomi.micolauncher.common.utils.AnimationUtils;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseActivity;
import com.xiaomi.smarthome.ui.adapter.UserGuideImageAdapter;
import com.xiaomi.smarthome.ui.adapter.UserGuideIndicatorAdapter;
import com.xiaomi.smarthome.ui.widgets.PagerIndicatorSnapHelper;
import java.util.List;

/* loaded from: classes4.dex */
public class UserGuideActivity extends BaseActivity {
    private final int[] a = {R.drawable.user_guide_1, R.drawable.user_guide_2, R.drawable.user_guide_3, R.drawable.user_guide_4};
    private final List<Boolean> b = Lists.newArrayList(true, false, false, false);
    private RecyclerView c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_guide);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.userGuideBanner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        recyclerView.setAdapter(new UserGuideImageAdapter(this, this.a));
        this.c = (RecyclerView) findViewById(R.id.bannerIndicators);
        this.c.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.c.setAdapter(new UserGuideIndicatorAdapter(this, this.b));
        PagerIndicatorSnapHelper pagerIndicatorSnapHelper = new PagerIndicatorSnapHelper();
        pagerIndicatorSnapHelper.attachToIndicators(this.c, this.b);
        pagerIndicatorSnapHelper.attachToRecyclerView(recyclerView);
        initViews();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity
    public void initViews() {
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.user_guide_indicator_decoration);
        this.c.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.smarthome.ui.UserGuideActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                rect.right = dimensionPixelOffset;
            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.ivBackCircleBg);
        View findViewById = findViewById(R.id.ivBack);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.-$$Lambda$UserGuideActivity$o7USe0B-fhl1bx-Ytda1BE7kIUM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserGuideActivity.this.a(view);
            }
        });
        findViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.smarthome.ui.UserGuideActivity.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (imageView == null) {
                    return false;
                }
                int action = motionEvent.getAction();
                if (action != 3) {
                    switch (action) {
                        case 0:
                            AnimationUtils.titleIconTouchDown(imageView);
                            break;
                    }
                    return false;
                }
                AnimationUtils.titleIconTouchUp(imageView);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }
}
