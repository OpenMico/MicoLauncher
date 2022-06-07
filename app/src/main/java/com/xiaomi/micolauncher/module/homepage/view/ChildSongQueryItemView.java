package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class ChildSongQueryItemView extends FrameLayout {
    private TextView a;
    private ImageView b;

    public ChildSongQueryItemView(Context context) {
        super(context);
        a(null, 0);
    }

    public ChildSongQueryItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet, 0);
    }

    public ChildSongQueryItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet, i);
    }

    private void a(AttributeSet attributeSet, int i) {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.child_song_query_item_round_view, (ViewGroup) this, true);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ChildSongQueryItem, i, 0);
        String string = obtainStyledAttributes.getString(1);
        this.a = (TextView) findViewById(R.id.tvQueryTitle);
        this.a.setText(string);
        this.b = (ImageView) findViewById(R.id.ivQueryIcon);
        this.b.setImageDrawable(obtainStyledAttributes.getDrawable(0));
        obtainStyledAttributes.recycle();
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildSongQueryItemView$JmGyYlC9camcUrdkXspK-g_kpBc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongQueryItemView.this.a(obj);
            }
        });
        setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        SpeechManager.getInstance().nlpRequest(String.format(getContext().getString(R.string.query_key_word_wrapper), (String) this.a.getText()));
    }

    public void setIconResId(int i) {
        this.b.setImageResource(i);
    }

    public void setTitleResId(int i) {
        this.a.setText(i);
    }
}
