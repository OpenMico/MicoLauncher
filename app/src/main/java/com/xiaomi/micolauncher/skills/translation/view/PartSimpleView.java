package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.skills.translation.bean.PartMeaning;
import com.xiaomi.micolauncher.skills.translation.bean.PartSimple;
import com.xiaomi.micolauncher.skills.translation.bean.PhoneticSymbol;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class PartSimpleView extends LinearLayout {
    TextView a;
    TextView b;
    ImageView c;
    ImageView d;
    private String e;
    private String f;

    public PartSimpleView(Context context) {
        this(context, null);
    }

    public PartSimpleView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PartSimpleView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.partsimple_layout, this);
        this.a = (TextView) findViewById(R.id.phonetic_symbols_en);
        this.b = (TextView) findViewById(R.id.phonetic_symbols_american);
        this.c = (ImageView) findViewById(R.id.play_en);
        this.d = (ImageView) findViewById(R.id.play_american);
        setOrientation(1);
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$PartSimpleView$x-0rc6HbdTufadoPzPLFwWCkNXk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PartSimpleView.this.d(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$PartSimpleView$BHKFoGfC1gbzYIlNjSqnoz1RJBM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PartSimpleView.this.c(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$PartSimpleView$8yXI_270CPwjQXOkIPxgEDvqFiQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PartSimpleView.this.b(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$PartSimpleView$KVX7Xl1tRmznEVfMMmqXz7r3R8Q
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PartSimpleView.this.a(obj);
            }
        });
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(this.e, AudioSource.AUDIO_SOURCE_UI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(this.f, AudioSource.AUDIO_SOURCE_UI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(this.e, AudioSource.AUDIO_SOURCE_UI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(this.f, AudioSource.AUDIO_SOURCE_UI);
    }

    public void setData(PartSimple partSimple) {
        List<PartMeaning> meanings = partSimple.getMeanings();
        List<PhoneticSymbol> phoneticSymbols = partSimple.getPhoneticSymbols();
        Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), "font/lsansuni.ttf");
        for (PhoneticSymbol phoneticSymbol : phoneticSymbols) {
            if (phoneticSymbol.isBritish()) {
                this.e = phoneticSymbol.getPhUrl();
                this.a.setTypeface(createFromAsset);
                this.a.setText(a(phoneticSymbol.getPhSymbol(), R.string.symbols_en, R.string.phonetic_symbols_en));
            }
            if (phoneticSymbol.isAmerican()) {
                this.f = phoneticSymbol.getPhUrl();
                this.b.setTypeface(createFromAsset);
                this.b.setText(a(phoneticSymbol.getPhSymbol(), R.string.symbolx_american, R.string.phonetic_symbols_american));
            }
        }
        if (!ContainerUtil.isEmpty(meanings)) {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            for (PartMeaning partMeaning : meanings) {
                PartMeanView partMeanView = new PartMeanView(getContext());
                partMeanView.setData(partMeaning);
                addView(partMeanView, layoutParams);
            }
        }
    }

    private String a(String str, int i, int i2) {
        return TextUtils.isEmpty(str) ? getContext().getString(i) : getContext().getString(i2, str).replace("/", "");
    }
}
