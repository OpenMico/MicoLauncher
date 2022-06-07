package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.weather.model.WeacherDay;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes3.dex */
public class WeatherCell extends RelativeLayout {
    TextView a;
    ImageView b;
    TextView c;
    private final String d = XMPassport.SIMPLE_DATE_FORMAT;
    private final String e = "yyyyMMdd";
    private String f;

    public WeatherCell(Context context) {
        super(context);
        a(context);
    }

    public WeatherCell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public WeatherCell(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_weather_cell_08a, this);
        this.a = (TextView) findViewById(R.id.weather_date);
        this.b = (ImageView) findViewById(R.id.weather_state);
        this.c = (TextView) findViewById(R.id.weather_weekday);
    }

    public void refreshView(WeacherDay weacherDay) {
        if (weacherDay != null) {
            setDate(weacherDay.date);
            this.b.setImageResource(weacherDay.getDisplayWeather().drawId);
        }
    }

    public void setWidth(int i) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = i;
        setLayoutParams(layoutParams);
    }

    public void setDate(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.f = str.contains(Constants.ACCEPT_TIME_SEPARATOR_SERVER) ? XMPassport.SIMPLE_DATE_FORMAT : "yyyyMMdd";
                Date parse = new SimpleDateFormat(this.f).parse(str);
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(parse);
                this.a.setText(String.valueOf(gregorianCalendar.get(5)));
                this.c.setText(getResources().getStringArray(R.array.weekdays)[(gregorianCalendar.get(7) + 5) % 7]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
