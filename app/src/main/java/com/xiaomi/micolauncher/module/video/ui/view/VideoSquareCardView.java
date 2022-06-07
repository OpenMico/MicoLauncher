package com.xiaomi.micolauncher.module.video.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.music.base.BaseSquareCardView;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class VideoSquareCardView extends BaseSquareCardView {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;
    ImageView e;
    TextView f;
    TextView g;
    private Context h;
    private ValueAnimator i;
    private ValueAnimator j;
    private String k;
    private int l;
    private float m;
    private int n;
    private Drawable o;
    private String p;
    private ValueAnimator.AnimatorUpdateListener q;
    private Animator.AnimatorListener r;
    private ValueAnimator.AnimatorUpdateListener s;
    private Animator.AnimatorListener t;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleType(TitleType titleType) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTimeView() {
    }

    public VideoSquareCardView(Context context) {
        this(context, null);
    }

    public VideoSquareCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoSquareCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.q = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i2 = (int) (255.0f * floatValue);
                VideoSquareCardView.this.b.setImageAlpha(i2);
                VideoSquareCardView.this.f.setAlpha(floatValue);
                VideoSquareCardView.this.d.setImageAlpha(i2);
            }
        };
        this.r = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (VideoSquareCardView.this.o != null) {
                    VideoSquareCardView.this.b.setImageDrawable(VideoSquareCardView.this.o);
                }
                VideoSquareCardView.this.b.setImageAlpha(255);
            }
        };
        this.s = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                VideoSquareCardView.this.g.setAlpha(floatValue);
                VideoSquareCardView.this.e.setImageAlpha((int) (floatValue * 255.0f));
            }
        };
        this.t = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                VideoSquareCardView.this.f.setText(VideoSquareCardView.this.k);
                VideoSquareCardView.this.f.setAlpha(1.0f);
                GlideUtils.bindImageView(VideoSquareCardView.this.h, VideoSquareCardView.this.l, VideoSquareCardView.this.d);
                VideoSquareCardView.this.d.setImageAlpha(255);
                VideoSquareCardView.this.g.setText("");
                VideoSquareCardView.this.e.setImageAlpha(0);
            }
        };
        this.h = context;
        a();
    }

    private void a() {
        View.inflate(this.h, R.layout.view_video_square_card, this);
        this.a = (ImageView) findViewById(R.id.preview_image_iv);
        this.b = (ImageView) findViewById(R.id.image_iv);
        this.c = (ImageView) findViewById(R.id.cover_iv);
        this.d = (ImageView) findViewById(R.id.tag_iv);
        this.e = (ImageView) findViewById(R.id.preview_tag_iv);
        this.f = (TextView) findViewById(R.id.title_tv);
        this.g = (TextView) findViewById(R.id.preview_title_tv);
        this.i = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.i.setDuration(1000L);
        this.i.addUpdateListener(this.q);
        this.i.addListener(this.r);
        this.j = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.j.setDuration(2300L);
        this.j.addUpdateListener(this.s);
        this.j.addListener(this.t);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void refreshPage(Object obj) {
        if (obj != null && (obj instanceof VideoItem)) {
            final VideoItem videoItem = (VideoItem) obj;
            setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.-$$Lambda$VideoSquareCardView$vW9XpCH2wPN6_2sR4B1hEk43hUM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VideoSquareCardView.this.a(videoItem, view);
                }
            });
            if (videoItem != null) {
                a(videoItem.getHorizontalImageUrl(), videoItem.getType(), videoItem.getTitle());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoItem videoItem, View view) {
        if (videoItem != null && !TextUtils.isEmpty(videoItem.getMediaId())) {
            VideoModel.getInstance().setRecommendationList(Arrays.asList(videoItem), null);
            Intent intent = new Intent(this.h, VideoDetailActivity.class);
            intent.putExtra(VideoModel.KEY_VIDEO_ITEM_ID, videoItem.getId());
            this.h.startActivity(intent);
            VideoStatHelper.recordVideoRecommendClick(this.p);
        }
    }

    private void a(String str, String str2, String str3) {
        if (this.c.getDrawable() == null) {
            Glide.with(this.h).load(Integer.valueOf((int) R.drawable.music_card_view_cover)).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().transforms(new CenterCrop(), new RoundedCorners((int) this.m))).into(this.c);
        }
        this.o = null;
        this.k = str3;
        this.l = a(str2);
        RequestOptions transforms = GlideUtils.getDefaultRequestOptions().placeholder(this.n).error(this.n).transforms(new CenterCrop(), new RoundedCorners((int) this.m));
        if (this.b.getDrawable() == null) {
            RequestManager with = Glide.with(this.h);
            boolean isEmpty = TextUtils.isEmpty(str);
            Object obj = str;
            if (isEmpty) {
                obj = Integer.valueOf(this.n);
            }
            with.load(obj).apply((BaseRequestOptions<?>) transforms).into(this.b);
            this.f.setText(str3);
            GlideUtils.bindImageView(this.h, this.l, this.d);
            return;
        }
        Glide.with(this.h).load(str).apply((BaseRequestOptions<?>) transforms).into((RequestBuilder<Drawable>) new ImageViewTarget<Drawable>(this.a) { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void setResource(@Nullable Drawable drawable) {
                VideoSquareCardView.this.o = drawable;
                VideoSquareCardView.this.a.setImageDrawable(drawable);
            }
        });
        this.g.setText(str3);
        GlideUtils.bindImageView(this.h, this.l, this.e);
        this.j.start();
        this.i.start();
    }

    private int a(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return getResources().getIdentifier(String.format("icon_video_tag_%s", str.toLowerCase()), "drawable", this.h.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setRadius(float f) {
        this.m = f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setDefaultResId(int i) {
        this.n = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleName(String str) {
        this.p = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTagView() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layoutParams.setMargins(DisplayUtils.dip2px(getContext(), 8.0f), 0, 0, DisplayUtils.dip2px(getContext(), 2.0f));
        this.d.setLayoutParams(layoutParams);
        this.e.setLayoutParams(layoutParams);
    }
}
