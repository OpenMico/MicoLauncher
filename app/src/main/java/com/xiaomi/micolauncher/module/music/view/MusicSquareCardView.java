package com.xiaomi.micolauncher.module.music.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
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
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.music.base.BaseSquareCardView;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;

/* loaded from: classes3.dex */
public class MusicSquareCardView extends BaseSquareCardView {
    ImageView a;
    TextView b;
    ImageView c;
    TextView d;
    MusicPatchWallTimeView e;
    ImageView f;
    TextView g;
    TextView h;
    private Context i;
    private ValueAnimator j;
    private ValueAnimator k;
    private String l;
    private boolean m;
    private float n;
    private int o;
    private String p;
    private Drawable q;
    private TitleType r;
    private boolean s;
    private ValueAnimator.AnimatorUpdateListener t;
    private Animator.AnimatorListener u;
    private ValueAnimator.AnimatorUpdateListener v;
    private Animator.AnimatorListener w;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTagView() {
    }

    public MusicSquareCardView(Context context) {
        this(context, null);
    }

    public MusicSquareCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicSquareCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.t = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicSquareCardView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                MusicSquareCardView.this.a.setImageAlpha((int) (255.0f * floatValue));
                MusicSquareCardView.this.b.setAlpha(floatValue);
            }
        };
        this.u = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicSquareCardView.3
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
                MusicSquareCardView.this.a.setImageDrawable(MusicSquareCardView.this.q);
                MusicSquareCardView.this.a.setImageAlpha(255);
            }
        };
        this.v = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicSquareCardView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MusicSquareCardView.this.d.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        };
        this.w = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicSquareCardView.5
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
                MusicSquareCardView.this.b.setText(MusicSquareCardView.this.l);
                MusicSquareCardView.this.b.setAlpha(1.0f);
                if (!Hardware.isBigScreen()) {
                    MusicSquareCardView.this.d.setText("");
                }
                MusicSquareCardView.this.m = false;
            }
        };
        this.i = context;
        a();
    }

    private void a() {
        View.inflate(this.i, R.layout.view_music_square_card, this);
        this.a = (ImageView) findViewById(R.id.image_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (ImageView) findViewById(R.id.preview_image_iv);
        this.d = (TextView) findViewById(R.id.preview_title_tv);
        this.e = (MusicPatchWallTimeView) findViewById(R.id.time_view);
        this.f = (ImageView) findViewById(R.id.cover_iv);
        this.j = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.j.setDuration(1000L);
        this.j.addUpdateListener(this.t);
        this.j.addListener(this.u);
        this.k = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.k.setDuration(2300L);
        this.k.addUpdateListener(this.v);
        this.k.addListener(this.w);
    }

    public void setShowPlayIcon(boolean z) {
        this.s = z;
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleType(TitleType titleType) {
        this.r = titleType;
        int i = 8;
        switch (titleType) {
            case NONE:
                this.b.setVisibility(8);
                this.f.setVisibility(8);
                this.d.setVisibility(8);
                return;
            case LEFT:
                this.b.setVisibility(0);
                this.f.setVisibility(0);
                this.d.setVisibility(0);
                return;
            case CENTER:
                this.b.setVisibility(0);
                this.f.setVisibility(0);
                this.b.setGravity(17);
                this.d.setGravity(17);
                return;
            case CATEGORY:
                this.b = (TextView) findViewById(R.id.category_title_tv);
                this.g = (TextView) findViewById(R.id.first_mark);
                this.h = (TextView) findViewById(R.id.second_mark);
                return;
            case BOTTOM:
                this.d.setVisibility(0);
                return;
            case BIG_CIRCLE:
                this.f.setVisibility(8);
                this.d.setVisibility(8);
                View findViewById = findViewById(R.id.play_icon);
                if (this.s) {
                    i = 0;
                }
                findViewById.setVisibility(i);
                this.f = (ImageView) findViewById(R.id.big_circle_cover_iv);
                this.c = (ImageView) findViewById(R.id.big_circle_preview_iv);
                this.a = (ImageView) findViewById(R.id.big_circle_img_iv);
                this.b = (TextView) findViewById(R.id.big_circle_title_tv);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTimeView() {
        this.e.setVisibility(0);
    }

    public void refreshPage(String str, String str2) {
        Drawable drawable;
        if (this.f.getDrawable() == null && !Hardware.isBigScreen()) {
            Glide.with(this.i).load(Integer.valueOf((int) R.drawable.music_card_view_cover)).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().transforms(new CenterCrop(), new RoundedCorners((int) this.n))).into(this.f);
        }
        if (this.m && (drawable = this.q) != null) {
            this.m = false;
            this.a.setImageDrawable(drawable);
            this.a.setImageAlpha(255);
            this.b.setText(str2);
            this.b.setAlpha(1.0f);
            this.k.cancel();
            this.j.cancel();
        }
        this.q = null;
        this.l = str2;
        RequestOptions transforms = GlideUtils.getDefaultRequestOptions().placeholder(this.o).error(this.o).transforms(new CenterCrop(), new RoundedCorners((int) this.n));
        if (this.a.getDrawable() == null) {
            RequestManager with = Glide.with(this.i);
            boolean isEmpty = TextUtils.isEmpty(str);
            Object obj = str;
            if (isEmpty) {
                obj = Integer.valueOf(this.o);
            }
            with.load(obj).apply((BaseRequestOptions<?>) transforms).into(this.a);
            this.b.setText(str2);
            if (this.r == TitleType.CATEGORY) {
                this.g.setVisibility(0);
                this.h.setVisibility(0);
                this.g.setText("#英语");
                this.h.setText("#法语");
            }
            if (this.r == TitleType.BOTTOM) {
                this.d.setText(str2);
                return;
            }
            return;
        }
        Glide.with(this.i).load(str).apply((BaseRequestOptions<?>) transforms).into((RequestBuilder<Drawable>) new ImageViewTarget<Drawable>(this.c) { // from class: com.xiaomi.micolauncher.module.music.view.MusicSquareCardView.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void setResource(@Nullable Drawable drawable2) {
                MusicSquareCardView.this.q = drawable2;
                MusicSquareCardView.this.c.setImageDrawable(drawable2);
            }
        });
        this.d.setText(str2);
        this.k.start();
        this.j.start();
        this.m = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void refreshPage(Object obj) {
        if (obj != null && (obj instanceof SquareItem)) {
            final SquareItem squareItem = (SquareItem) obj;
            String str = "";
            String str2 = "";
            if (squareItem != null) {
                str = squareItem.imageUrl;
                str2 = squareItem.title;
            }
            setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.music.view.-$$Lambda$MusicSquareCardView$5fVIXOXAXTiuwZpBHsxtRZIvPTg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MusicSquareCardView.this.a(squareItem, view);
                }
            });
            refreshPage(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SquareItem squareItem, View view) {
        if (squareItem != null && !TextUtils.isEmpty(squareItem.target)) {
            SchemaManager.handleSchema(getContext(), squareItem.target);
            MusicStatHelper.recordMusicClick(this.p, squareItem.title);
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setRadius(float f) {
        this.n = f;
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setDefaultResId(int i) {
        this.o = i;
    }

    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleName(String str) {
        this.p = str;
    }
}
