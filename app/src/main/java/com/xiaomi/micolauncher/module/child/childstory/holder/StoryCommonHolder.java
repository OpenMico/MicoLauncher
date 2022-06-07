package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.childsong.ChildSongGroupListActivity;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import java.util.List;

/* loaded from: classes3.dex */
public class StoryCommonHolder extends BaseChildStoryHolder {
    public static final int COLOR_LOOP1 = 1;
    public static final int COLOR_LOOP2 = 2;
    public static final int COLOR_LOOP3 = 3;
    public static final int COLOR_LOOP4 = 4;
    public static final int COLOR_LOOP5 = 0;
    TextView a;
    ConstraintLayout b;
    ImageView c;
    private int d;
    private boolean e;
    private TextView[] f;
    private ImageView[] g;
    private int h;
    private IBlockBean i;

    private int a(int i) {
        switch (i) {
            case 0:
                return R.drawable.child_story_loop5_place_bg;
            case 1:
                return R.drawable.child_story_loop1_place_bg;
            case 2:
                return R.drawable.child_story_loop2_place_bg;
            case 3:
                return R.drawable.child_story_loop3_place_bg;
            case 4:
                return R.drawable.child_story_loop4_place_bg;
            default:
                return R.drawable.child_story_loop1_place_bg;
        }
    }

    public StoryCommonHolder(final Context context, final View view, final boolean z) {
        super(context, view);
        this.a = (TextView) view.findViewById(R.id.common_title);
        this.b = (ConstraintLayout) view.findViewById(R.id.common_cl);
        this.c = (ImageView) view.findViewById(R.id.common_more_iv);
        this.h = UiUtils.getSize(context, R.dimen.story_small_card_size);
        this.f = new TextView[]{(TextView) view.findViewById(R.id.small_card_name1), (TextView) view.findViewById(R.id.small_card_name2), (TextView) view.findViewById(R.id.small_card_name3), (TextView) view.findViewById(R.id.small_card_name4), (TextView) view.findViewById(R.id.small_card_name5), (TextView) view.findViewById(R.id.small_card_name6)};
        this.g = new ImageView[]{(ImageView) view.findViewById(R.id.small_card_img1), (ImageView) view.findViewById(R.id.small_card_img2), (ImageView) view.findViewById(R.id.small_card_img3), (ImageView) view.findViewById(R.id.small_card_img4), (ImageView) view.findViewById(R.id.small_card_img5), (ImageView) view.findViewById(R.id.small_card_img6)};
        for (final int i = 0; i < ContainerUtil.getSize(this.g); i++) {
            this.g[i].setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryCommonHolder$g-NWcnjfKFEMu6pMCldU6u1ibm0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    StoryCommonHolder.this.a(context, i, z, view2);
                }
            });
            AnimLifecycleManager.repeatOnAttach(this.g[i], MicoAnimConfigurator4EntertainmentAndApps.get());
        }
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryCommonHolder$sbo61lW7SglLqJVsPMfYON2eYGI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryCommonHolder.this.a(view, context, view2);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4EntertainmentAndApps.get());
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, int i, boolean z, View view) {
        ChildStory.BlocksBean.ItemsBean itemsBean;
        if (CommonUtil.checkHasNetworkAndShowToast(context) && !ContainerUtil.isOutOfBound(i, this.storyBlock.getItems()) && (itemsBean = this.storyBlock.getItems().get(i)) != null) {
            if (LocalPlayerManager.getInstance().isBlack(itemsBean.getTitle())) {
                SchemaManager.handleSchema(context, HomepageSchemaHandler.PATH_BLACKLIST);
            } else if (!TextUtils.isEmpty(itemsBean.getTarget())) {
                if (!this.i.getBlockType().equals(IBlockBean.BLOCK_TYPE_SONG)) {
                    ChildPlayUtil.playStory(context, itemsBean);
                } else if (!TextUtils.isEmpty(itemsBean.getTarget())) {
                    SchemaManager.handleSchema(context, itemsBean.getTarget());
                }
                if (!TextUtils.isEmpty(itemsBean.getTitle()) && z) {
                    ChildStatHelper.recordChildTabCardClick(itemsBean.getTitle().concat("–").concat(itemsBean.getTitle()));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view, Context context, View view2) {
        if (TextUtils.equals(this.i.getBlockType(), IBlockBean.BLOCK_TYPE_SONG)) {
            ChildSongGroupListActivity.openChildSongGroupListActivity(view.getContext(), 3200L);
        } else {
            StationCategoryListActivity.openStationWithBlock(context, (ChildStory.BlocksBean) this.i);
        }
    }

    @Override // com.xiaomi.micolauncher.module.child.childstory.holder.BaseChildStoryHolder, com.xiaomi.micolauncher.module.child.BaseChildHolder
    public void setBlocksBean(IBlockBean iBlockBean) {
        super.setBlocksBean(iBlockBean);
        this.i = iBlockBean;
        String title = this.storyBlock.getTitle();
        if (!TextUtils.isEmpty(title)) {
            this.a.setText(title);
        }
        if (ContainerUtil.hasData(this.storyBlock.getItems())) {
            List<ChildStory.BlocksBean.ItemsBean> items = this.storyBlock.getItems();
            int min = Math.min(ContainerUtil.getSize(items), 6);
            for (int i = 0; i < min; i++) {
                if (items.get(i) != null) {
                    a(this.f[i], items.get(i).getTitle());
                    a(this.g[i], items.get(i).getItemImageUrl(), this.d);
                }
            }
        }
    }

    public void setBackground(int i) {
        if (this.e) {
            i += 3;
        }
        this.b.setBackground(this.context.getDrawable(a(i % 5, this.e)));
    }

    private int a(int i, boolean z) {
        this.d = i;
        switch (i) {
            case 0:
                return z ? R.drawable.home_kids_big_card_loop5 : R.drawable.child_video_big_card_bg5;
            case 1:
                return z ? R.drawable.home_kids_big_card_loop1 : R.drawable.child_video_big_card_bg2;
            case 2:
                return z ? R.drawable.home_kids_big_card_loop2 : R.drawable.child_video_big_card_bg1;
            case 3:
                return z ? R.drawable.home_kids_big_card_loop3 : R.drawable.child_video_big_card_bg3;
            case 4:
                return z ? R.drawable.home_kids_big_card_loop4 : R.drawable.child_video_big_card_bg4;
            default:
                return z ? R.drawable.home_kids_big_card_loop1 : R.drawable.child_video_big_card_bg2;
        }
    }

    private void a(ImageView imageView, String str, int i) {
        Context context = this.context;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.story_small_card_corner_radius);
        int a = a(i);
        int i2 = this.h;
        GlideUtils.bindImageViewWithRoundUseContext(context, str, imageView, dimensionPixelSize, a, i2, i2);
    }

    private void a(TextView textView, String str) {
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        }
    }
}
