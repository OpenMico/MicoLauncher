package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Apps_Card_Gallery implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_gallery_size), (int) resources.getDimension(R.dimen.app_card_gallery_view_height));
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_margin_top);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding(0, 0, 0, (int) resources.getDimension(R.dimen.list_app_item_padding_bottom));
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_gallery_size), (int) resources.getDimension(R.dimen.app_card_gallery_size));
        imageView.setId(R.id.ivPic);
        layoutParams2.gravity = 1;
        imageView.setImageResource(R.drawable.ic_icon_gallery_skill_default);
        imageView.setLayoutParams(layoutParams2);
        linearLayout.addView(imageView);
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.gravity = 1;
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.app_card_item_title_margin_top);
        textView.setText(R.string.label_gallery);
        textView.setTextColor(resources.getColor(R.color.app_name_color));
        textView.setTextSize(0, resources.getDimension(R.dimen.app_name_text_size));
        textView.setLayoutParams(layoutParams3);
        linearLayout.addView(textView);
        return linearLayout;
    }
}
