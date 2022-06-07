package com.xiaomi.micolauncher.common.widget.itemdecoration;

import android.content.Context;
import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class ItemDecorationHelper {
    public static RecyclerView.ItemDecoration listDivider(Context context, int i, int i2) {
        return listDivider(context, R.color.setting_item_divider_color, i, i2, null);
    }

    public static RecyclerView.ItemDecoration listDivider(Context context, int i, int i2, Ignore ignore) {
        return listDivider(context, R.color.setting_item_divider_color, i, i2, ignore);
    }

    public static RecyclerView.ItemDecoration listBottomDivider(Context context, @ColorRes int i, int i2, int i3, Ignore ignore) {
        return new ListBottomDividerItemDecoration(0, context.getResources().getColor(i), context.getResources().getDimensionPixelSize(R.dimen.common_divider_line_size), i2, i3, ignore);
    }

    public static RecyclerView.ItemDecoration listDivider(Context context, @ColorRes int i, int i2, int i3, Ignore ignore) {
        return new ListDividerItemDecoration(0, context.getResources().getColor(i), context.getResources().getDimensionPixelSize(R.dimen.common_divider_line_size), i2, i3, ignore);
    }

    public static RecyclerView.ItemDecoration listPadding(int i, int i2) {
        return listPadding(i, i2, null);
    }

    public static RecyclerView.ItemDecoration listPadding(int i, int i2, Ignore ignore) {
        return new ListPaddingItemDecoration(0, i, i2, ignore);
    }
}
