package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.SynonymDetail;

/* loaded from: classes3.dex */
public class SynonymDetailItemView extends LinearLayout {
    TextView a;
    TextView b;

    public SynonymDetailItemView(Context context) {
        this(context, null);
    }

    public SynonymDetailItemView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SynonymDetailItemView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.synonym_detail_item, this);
        this.a = (TextView) findViewById(R.id.word_meaning);
        this.b = (TextView) findViewById(R.id.words);
        setOrientation(1);
    }

    public void setData(int i, SynonymDetail synonymDetail) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getContext().getString(i, synonymDetail.getWordMeaning()));
        spannableStringBuilder.setSpan(new CustomTypefaceSpan(synonymDetail.getWordMeaning(), Typeface.create(TypefaceUtil.FONT_WEIGHT_W600, 1)), 0, synonymDetail.getWordMeaning().length(), 33);
        this.a.setText(spannableStringBuilder);
        StringBuilder sb = new StringBuilder();
        for (String str : synonymDetail.getWords()) {
            sb.append(str);
            sb.append(" / ");
        }
        sb.replace(sb.lastIndexOf("/"), sb.length(), "");
        this.b.setText(sb.toString());
    }
}
