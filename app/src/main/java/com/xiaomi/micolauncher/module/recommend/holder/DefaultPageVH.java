package com.xiaomi.micolauncher.module.recommend.holder;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.common.widget.CenterAlignImageSpan;
import com.xiaomi.micolauncher.module.recommend.PageFactory;

/* loaded from: classes3.dex */
public class DefaultPageVH implements PageFactory.PageVH {
    TextView a;
    TextView b;

    @Override // com.xiaomi.micolauncher.module.recommend.PageFactory.PageVH
    public void setMainColor(int i) {
    }

    public DefaultPageVH(View view, MainScreen.RecommendPage recommendPage) {
        this.a = (TextView) view.findViewById(R.id.title);
        this.b = (TextView) view.findViewById(R.id.tips);
        if (recommendPage.tips != null && recommendPage.tips.size() > 0) {
            this.b.setText(recommendPage.tips.get(0));
            this.b.setVisibility(0);
        }
        if (TextUtils.isEmpty(recommendPage.tag)) {
            this.a.setText(recommendPage.title);
            return;
        }
        SpannableString spannableString = new SpannableString(String.format("%s   %s", recommendPage.tag, recommendPage.title));
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_title_divider);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new CenterAlignImageSpan(drawable), recommendPage.tag.length() + 1, recommendPage.tag.length() + 2, 17);
        this.a.setText(spannableString);
    }
}
