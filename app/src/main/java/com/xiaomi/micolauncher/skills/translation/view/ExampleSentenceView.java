package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.translation.bean.ExampleSentence;
import java.util.List;

/* loaded from: classes3.dex */
public class ExampleSentenceView extends LinearLayout {
    public ExampleSentenceView(Context context) {
        this(context, null);
    }

    public ExampleSentenceView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ExampleSentenceView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.sentence_layout, this);
        setOrientation(1);
    }

    public void setData(String str, List<ExampleSentence> list) {
        if (!ContainerUtil.isEmpty(list)) {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            int i = 0;
            while (i < list.size()) {
                SentenceItemView sentenceItemView = new SentenceItemView(getContext());
                int i2 = i + 1;
                sentenceItemView.setData(i2, str, list.get(i));
                addView(sentenceItemView, layoutParams);
                i = i2;
            }
        }
    }
}
