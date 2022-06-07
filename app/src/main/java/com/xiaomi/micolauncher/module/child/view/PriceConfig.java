package com.xiaomi.micolauncher.module.child.view;

import android.content.res.Resources;
import android.util.Pair;
import com.xiaomi.micolauncher.R;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PriceConfig {
    public static final int PRICE_SIZE_BIG = 0;
    public static final int PRICE_SIZE_NORMAL = 1;
    public static final Character CHAR_SIGN_MONEY = 'm';
    public static final Character CHAR_DECIMAL_POINT = 'd';
    private static final Map<Character, Integer> a = new HashMap<Character, Integer>() { // from class: com.xiaomi.micolauncher.module.child.view.PriceConfig.1
        {
            put('0', Integer.valueOf((int) R.dimen.child_vip_price_0_small_width));
            put('1', Integer.valueOf((int) R.dimen.child_vip_price_1_small_width));
            put('2', Integer.valueOf((int) R.dimen.child_vip_price_2_small_width));
            put('3', Integer.valueOf((int) R.dimen.child_vip_price_3_small_width));
            put('4', Integer.valueOf((int) R.dimen.child_vip_price_4_small_width));
            put('5', Integer.valueOf((int) R.dimen.child_vip_price_5_small_width));
            put('6', Integer.valueOf((int) R.dimen.child_vip_price_6_small_width));
            put('7', Integer.valueOf((int) R.dimen.child_vip_price_7_small_width));
            put('8', Integer.valueOf((int) R.dimen.child_vip_price_8_small_width));
            put('9', Integer.valueOf((int) R.dimen.child_vip_price_9_small_width));
            put('d', Integer.valueOf((int) R.dimen.child_vip_price_d_small_width));
            put('m', Integer.valueOf((int) R.dimen.child_vip_price_m_small_width));
        }
    };
    private static final Map<Character, Integer> b = new HashMap<Character, Integer>() { // from class: com.xiaomi.micolauncher.module.child.view.PriceConfig.2
        {
            put('0', Integer.valueOf((int) R.dimen.child_vip_price_0_big_width));
            put('1', Integer.valueOf((int) R.dimen.child_vip_price_1_big_width));
            put('2', Integer.valueOf((int) R.dimen.child_vip_price_2_big_width));
            put('3', Integer.valueOf((int) R.dimen.child_vip_price_3_big_width));
            put('4', Integer.valueOf((int) R.dimen.child_vip_price_4_big_width));
            put('5', Integer.valueOf((int) R.dimen.child_vip_price_5_big_width));
            put('6', Integer.valueOf((int) R.dimen.child_vip_price_6_big_width));
            put('7', Integer.valueOf((int) R.dimen.child_vip_price_7_big_width));
            put('8', Integer.valueOf((int) R.dimen.child_vip_price_8_big_width));
            put('9', Integer.valueOf((int) R.dimen.child_vip_price_9_big_width));
            put('d', Integer.valueOf((int) R.dimen.child_vip_price_d_big_width));
            put('m', Integer.valueOf((int) R.dimen.child_vip_price_m_big_width));
        }
    };

    public static Pair<Integer, Integer> getPriceParams(Resources resources, char c, int i) {
        int i2;
        int i3;
        if (i == 1) {
            i2 = resources.getDimensionPixelSize(R.dimen.child_vip_price_default_small_width);
            i3 = resources.getDimensionPixelSize(R.dimen.child_vip_price_common_small_height);
            Integer num = a.get(Character.valueOf(c));
            if (num != null) {
                i2 = resources.getDimensionPixelSize(num.intValue());
            }
        } else {
            i2 = resources.getDimensionPixelSize(R.dimen.child_vip_price_default_big_width);
            i3 = resources.getDimensionPixelSize(R.dimen.child_vip_price_common_big_height);
            Integer num2 = b.get(Character.valueOf(c));
            if (num2 != null) {
                i2 = resources.getDimensionPixelSize(num2.intValue());
            }
        }
        return new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3));
    }
}
