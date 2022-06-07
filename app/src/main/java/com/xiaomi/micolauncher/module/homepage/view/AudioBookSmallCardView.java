package com.xiaomi.micolauncher.module.homepage.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class AudioBookSmallCardView extends FrameLayout {
    FrameLayout a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    private MainPage.Station f;
    private int g;
    private int h;
    private int i;
    private ValueAnimator j;
    private ValueAnimator k;
    private String l;
    private String m;
    private ValueAnimator.AnimatorUpdateListener n;
    private Animator.AnimatorListener o;
    private ValueAnimator.AnimatorUpdateListener p;
    private Animator.AnimatorListener q;

    public AudioBookSmallCardView(Context context) {
        this(context, null);
    }

    public AudioBookSmallCardView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AudioBookSmallCardView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.n = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.AudioBookSmallCardView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                AudioBookSmallCardView.this.b.setAlpha(floatValue);
                AudioBookSmallCardView.this.c.setAlpha(floatValue);
            }
        };
        this.o = new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.view.AudioBookSmallCardView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AudioBookSmallCardView.this.b.setAlpha(1.0f);
                AudioBookSmallCardView.this.c.setAlpha(1.0f);
            }
        };
        this.p = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.AudioBookSmallCardView.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                AudioBookSmallCardView.this.d.setAlpha(floatValue);
                AudioBookSmallCardView.this.e.setAlpha(floatValue);
            }
        };
        this.q = new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.module.homepage.view.AudioBookSmallCardView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AudioBookSmallCardView.this.d.setText("");
                AudioBookSmallCardView.this.b.setText(AudioBookSmallCardView.this.l);
                AudioBookSmallCardView.this.e.setText("");
                AudioBookSmallCardView.this.c.setText(AudioBookSmallCardView.this.m);
                AudioBookSmallCardView.this.d.setAlpha(0.0f);
                AudioBookSmallCardView.this.e.setAlpha(0.0f);
            }
        };
        View.inflate(context, R.layout.audiobook_small_card_item, this);
        this.a = (FrameLayout) findViewById(R.id.root);
        this.b = (TextView) findViewById(R.id.category_tv);
        this.c = (TextView) findViewById(R.id.title_tv);
        this.d = (TextView) findViewById(R.id.category_preview_tv);
        this.e = (TextView) findViewById(R.id.title_preview_tv);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AudioBookSmallCardView);
        this.g = obtainStyledAttributes.getColor(0, getResources().getColor(R.color.color_FFF16B));
        this.h = obtainStyledAttributes.getColor(2, getResources().getColor(R.color.black));
        this.i = obtainStyledAttributes.getResourceId(1, R.drawable.home_history_card);
        obtainStyledAttributes.recycle();
        this.j = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.j.setDuration(1000L);
        this.j.addUpdateListener(this.n);
        this.j.addListener(this.o);
        this.k = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.k.setDuration(1000L);
        this.k.addUpdateListener(this.p);
        this.k.addListener(this.q);
    }

    public void initInMain() {
        this.b.setTextColor(this.g);
        this.d.setTextColor(this.g);
        this.c.setTextColor(this.h);
        this.e.setTextColor(this.h);
        this.a.setBackground(getContext().getDrawable(this.i));
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AudioBookSmallCardView$54O4sA20vSb-uURHcZ6yNyVq-n0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookSmallCardView.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        if (CommonUtil.checkHasNetworkAndShowToast(getContext())) {
            MainPage.Station station = this.f;
            if (station == null || TextUtils.isEmpty(station.getAction())) {
                ToastUtil.showToast((int) R.string.error_get_data_failed);
            } else {
                ActivityLifeCycleManager.startActivityQuietly(new Intent("android.intent.action.VIEW", Uri.parse(this.f.getAction())));
            }
        }
    }

    public void setData(MainPage.Station station) {
        this.f = station;
        String tag = station.getTag();
        String title = station.getTitle();
        if (TextUtils.isEmpty(this.l) || TextUtils.isEmpty(this.m)) {
            setAudioBookCategory(tag);
            setAudioBookTitle(title);
        } else {
            this.j.start();
            this.d.setText(tag);
            this.e.setText(title);
            this.k.start();
        }
        this.l = tag;
        this.m = title;
    }

    public void setAudioBookCategory(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.setText(str);
        }
    }

    public void setAudioBookTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.setText(str);
        }
    }
}
