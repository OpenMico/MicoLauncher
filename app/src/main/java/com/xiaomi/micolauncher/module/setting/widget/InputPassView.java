package com.xiaomi.micolauncher.module.setting.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class InputPassView extends ConstraintLayout implements View.OnClickListener {
    private static final long a = TimeUnit.SECONDS.toMillis(1);
    private boolean A;
    private TextView B;
    private final Context C;
    private final int D;
    private final float[] E;
    private String F;
    private String G;
    private OnInputPassClickListener b;
    private final List<Integer> c;
    private InputProgressView d;
    private LottieAnimationView e;
    private LottieAnimationView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private TextView q;
    private TextView r;
    private TextView s;
    private TextView t;
    private TextView u;
    private TextView v;
    private TextView[] w;
    private TextView[] x;
    private int y;
    @LayoutRes
    private int z;

    /* loaded from: classes3.dex */
    public interface OnInputPassClickListener {
        void onInputDone(String str, boolean z);
    }

    public InputPassView(Context context) {
        this(context, null);
    }

    public InputPassView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InputPassView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = new ArrayList();
        this.D = 600;
        this.E = new float[]{25.0f, -25.0f, 13.0f, -13.0f, 0.0f};
        this.C = context;
        a(context, attributeSet);
        View.inflate(context, this.z, this);
        a();
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.InputPassView, 0, 0);
            int integer = obtainStyledAttributes.getInteger(0, 0);
            this.y = obtainStyledAttributes.getInteger(1, 1);
            this.F = obtainStyledAttributes.getString(3);
            this.G = obtainStyledAttributes.getString(2);
            switch (integer) {
                case 1:
                    this.z = R.layout.layout_input_pass_fullscreen;
                    break;
                case 2:
                    this.z = R.layout.layout_input_pass_dialog;
                    break;
                default:
                    this.z = R.layout.layout_input_pass;
                    break;
            }
            obtainStyledAttributes.recycle();
        }
    }

    private void a() {
        this.d = (InputProgressView) findViewById(R.id.input_progress);
        this.f = (LottieAnimationView) findViewById(R.id.anim_failed);
        this.e = (LottieAnimationView) findViewById(R.id.anim_success);
        this.g = (TextView) findViewById(R.id.edit_1);
        this.h = (TextView) findViewById(R.id.edit_2);
        this.i = (TextView) findViewById(R.id.edit_3);
        this.j = (TextView) findViewById(R.id.edit_4);
        this.t = (TextView) findViewById(R.id.input_0);
        this.k = (TextView) findViewById(R.id.input_1);
        this.l = (TextView) findViewById(R.id.input_2);
        this.m = (TextView) findViewById(R.id.input_3);
        this.n = (TextView) findViewById(R.id.input_4);
        this.o = (TextView) findViewById(R.id.input_5);
        this.p = (TextView) findViewById(R.id.input_6);
        this.q = (TextView) findViewById(R.id.input_7);
        this.r = (TextView) findViewById(R.id.input_8);
        this.s = (TextView) findViewById(R.id.input_9);
        this.u = (TextView) findViewById(R.id.input_delete);
        this.B = (TextView) findViewById(R.id.input_pass_tip);
        this.v = (TextView) findViewById(R.id.title);
        this.w = new TextView[]{this.g, this.h, this.i, this.j};
        this.x = new TextView[]{this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u};
        if (TextUtils.isEmpty(this.F)) {
            this.v.setVisibility(8);
        } else {
            this.v.setVisibility(0);
            this.v.setText(this.F);
        }
        if (TextUtils.isEmpty(this.G)) {
            this.B.setVisibility(8);
        } else {
            this.B.setVisibility(0);
            this.B.setText(this.G);
        }
        if (this.y == 0) {
            this.d.setVisibility(8);
            this.e.setVisibility(8);
            this.f.setVisibility(8);
            for (TextView textView : this.w) {
                textView.setVisibility(0);
            }
        } else {
            this.d.setVisibility(0);
            this.e.setVisibility(0);
            this.f.setVisibility(0);
            for (TextView textView2 : this.w) {
                textView2.setVisibility(8);
            }
        }
        for (TextView textView3 : this.x) {
            textView3.setOnClickListener(this);
        }
    }

    public void setOnInputPassClickListener(OnInputPassClickListener onInputPassClickListener) {
        this.b = onInputPassClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_0 /* 2131362651 */:
            case R.id.input_1 /* 2131362652 */:
            case R.id.input_2 /* 2131362653 */:
            case R.id.input_3 /* 2131362654 */:
            case R.id.input_4 /* 2131362655 */:
            case R.id.input_5 /* 2131362656 */:
            case R.id.input_6 /* 2131362657 */:
            case R.id.input_7 /* 2131362658 */:
            case R.id.input_8 /* 2131362659 */:
            case R.id.input_9 /* 2131362660 */:
                if (ContainerUtil.getSize(this.c) < 4) {
                    this.c.add(Integer.valueOf(Integer.parseInt(((TextView) view).getText().toString())));
                }
                if (ContainerUtil.getSize(this.c) == 4) {
                    if (this.A) {
                        return;
                    }
                    if (this.b != null) {
                        final String str = "";
                        for (Integer num : this.c) {
                            str = str.concat(num.toString());
                        }
                        L.lock.i("onInputDone    %s", str);
                        postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.widget.-$$Lambda$InputPassView$HWiLFIxvgldIb9-21mNac_9-5yA
                            @Override // java.lang.Runnable
                            public final void run() {
                                InputPassView.this.a(str);
                            }
                        }, 300L);
                    }
                }
                d();
                b();
                return;
            case R.id.input_delete /* 2131362661 */:
                if (ContainerUtil.getSize(this.c) > 0) {
                    c();
                }
                if (ContainerUtil.hasData(this.c)) {
                    List<Integer> list = this.c;
                    list.remove(ContainerUtil.getSize(list) - 1);
                }
                d();
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void a(String str) {
        boolean z = false;
        this.A = false;
        OnInputPassClickListener onInputPassClickListener = this.b;
        if (this.y == 1) {
            z = true;
        }
        onInputPassClickListener.onInputDone(str, z);
    }

    private void b() {
        int size = (ContainerUtil.getSize(this.c) - 1) * 25;
        int i = size + 25;
        if (i == 100) {
            this.A = true;
        }
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this.d, "progress", size, i);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.start();
    }

    private void c() {
        int size = ContainerUtil.getSize(this.c) * 25;
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this.d, "progress", size, size - 25);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.start();
    }

    private void d() {
        f();
        int size = ContainerUtil.getSize(this.c);
        boolean z = false;
        for (int i = 0; i < size; i++) {
            this.w[i].setSelected(true);
        }
        int i2 = size;
        while (true) {
            TextView[] textViewArr = this.w;
            if (i2 >= textViewArr.length) {
                break;
            }
            textViewArr[i2].setSelected(false);
            i2++;
        }
        TextView textView = this.u;
        if (size != 0) {
            z = true;
        }
        textView.setEnabled(z);
    }

    public void setSuccess() {
        e();
        this.d.setSuccess();
        this.d.reset();
        clear();
    }

    public void setFailed() {
        e();
        this.d.setFailed();
        this.e.setVisibility(8);
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.g, "TranslationX", this.E).setDuration(600L);
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(this.h, "TranslationX", this.E).setDuration(600L);
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.i, "TranslationX", this.E).setDuration(600L);
        ObjectAnimator duration4 = ObjectAnimator.ofFloat(this.j, "TranslationX", this.E).setDuration(600L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.setting.widget.InputPassView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int length = InputPassView.this.w.length - 1;
                for (int animatedFraction = (int) (valueAnimator.getAnimatedFraction() * 4.0f); animatedFraction > 0; animatedFraction--) {
                    InputPassView.this.w[length].setSelected(false);
                    length--;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(duration, duration2, duration3, duration4);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.setting.widget.InputPassView.2
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
                InputPassView.this.d.reset();
                InputPassView.this.clear();
            }
        });
        animatorSet.start();
        setTip(this.C.getString(R.string.reset_pass_tip));
    }

    private void e() {
        setInputStatus(false);
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.widget.-$$Lambda$InputPassView$70YlArUEs5GmqfN9jJByu5TDMJY
            @Override // java.lang.Runnable
            public final void run() {
                InputPassView.this.g();
            }
        }, a);
    }

    public /* synthetic */ void g() {
        setInputStatus(true);
    }

    private void setInputStatus(boolean z) {
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.x;
            if (i < textViewArr.length - 1) {
                textViewArr[i].setEnabled(z);
                i++;
            } else {
                return;
            }
        }
    }

    public void clear() {
        this.A = false;
        this.c.clear();
        d();
    }

    public void reset() {
        this.c.clear();
        this.d.reset();
        LottieAnimationView lottieAnimationView = this.e;
        if (lottieAnimationView != null) {
            lottieAnimationView.setFrame(0);
        }
    }

    private void f() {
        this.g.setSelected(false);
        this.h.setSelected(false);
        this.i.setSelected(false);
        this.j.setSelected(false);
    }

    public void setTip(String str) {
        this.G = str;
        this.B.setVisibility(TextUtils.isEmpty(str) ? 8 : 0);
        this.B.setText(str);
    }
}
