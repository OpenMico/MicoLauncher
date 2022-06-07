package com.xiaomi.micolauncher.skills.alarm.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Consumer;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AlarmItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView a;
    private final TextView b;
    private final TextView c;
    private final TextView d;
    private final ImageView e;
    private final View f;
    private final View g;
    private final View h;
    private final TextView i;
    private final TextView j;
    private String k;
    private final int l;
    private final int m;
    private final int n;

    @SuppressLint({"CheckResult"})
    public AlarmItemViewHolder(View view) {
        super(view);
        Context context = view.getContext();
        this.a = (TextView) view.findViewById(R.id.alarm_cycle);
        this.b = (TextView) view.findViewById(R.id.alarm_time);
        this.c = (TextView) view.findViewById(R.id.alarm_event);
        this.d = (TextView) view.findViewById(R.id.alarm_ring);
        this.e = (ImageView) view.findViewById(R.id.alarm_type_view);
        this.f = view.findViewById(R.id.alarm_info_layout);
        this.g = view.findViewById(R.id.root_view);
        this.h = view.findViewById(R.id.alarm_delete_view);
        this.i = (TextView) view.findViewById(R.id.left_time);
        this.j = (TextView) view.findViewById(R.id.left_time_string);
        this.l = context.getResources().getDimensionPixelSize(R.dimen.alarm_list_delete_margin_top);
        this.n = context.getResources().getDimensionPixelSize(R.dimen.alarm_list_delete_margin_bottom);
        this.m = context.getResources().getDimensionPixelSize(R.dimen.alarm_list_margin_top);
        RxViewHelp.debounceClicks(this.g, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmItemViewHolder$6y_FVz-SCuiftDkxdDrsfAXDByk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AlarmItemViewHolder.this.a(obj);
            }
        });
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmItemViewHolder$-qEW0BmnYi_bx7JqWlk3uV3EzeQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AlarmItemViewHolder.this.b(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        d();
        a();
    }

    public void refreshView(AlarmRealmObjectBean alarmRealmObjectBean, int i) {
        Context context = this.itemView.getContext();
        b();
        this.k = alarmRealmObjectBean.getIdStr();
        if (i != 0 || !a(alarmRealmObjectBean.getLocalTimestamp())) {
            this.a.setVisibility(0);
            this.e.setVisibility(0);
            this.a.setText(a(context, alarmRealmObjectBean));
            this.c.setMaxLines(2);
            this.i.setText("");
            this.j.setText("");
            this.b.setText(a(alarmRealmObjectBean.getLocalDate()));
            if (AlarmHelper.isVideoRingtone(alarmRealmObjectBean.getRingtoneType())) {
                GlideUtils.bindImageView(context, (int) R.drawable.alarm_card_video, this.e);
                this.f.setBackgroundResource(R.drawable.bg_alarm_card_video);
                this.c.setTextColor(context.getResources().getColor(R.color.alarm_card_video_txt_color));
                this.d.setTextColor(context.getResources().getColor(R.color.alarm_card_video_txt_color));
                a(context, R.drawable.alarm_bell_yellow);
            } else if (AlarmHelper.isNatureRingtone(alarmRealmObjectBean.getRingtoneType())) {
                GlideUtils.bindImageView(context, (int) R.drawable.alarm_card_nature, this.e);
                this.f.setBackgroundResource(R.drawable.bg_alarm_card_nature);
                this.c.setTextColor(context.getResources().getColor(R.color.alarm_card_nature_txt_color));
                this.d.setTextColor(context.getResources().getColor(R.color.alarm_card_nature_txt_color));
                a(context, R.drawable.alarm_bell_green);
            } else if (AlarmHelper.isFunRingtone(alarmRealmObjectBean.getRingtoneType())) {
                GlideUtils.bindImageView(context, (int) R.drawable.alarm_card_spice, this.e);
                this.f.setBackgroundResource(R.drawable.bg_alarm_card_fun);
                this.c.setTextColor(context.getResources().getColor(R.color.alarm_card_fun_txt_color));
                this.d.setTextColor(context.getResources().getColor(R.color.alarm_card_fun_txt_color));
                a(context, R.drawable.alarm_bell_purple);
            } else {
                GlideUtils.bindImageView(context, (int) R.drawable.alarm_card_music, this.e);
                this.f.setBackgroundResource(R.drawable.bg_alarm_card_music);
                this.c.setTextColor(context.getResources().getColor(R.color.alarm_card_music_txt_color));
                this.d.setTextColor(context.getResources().getColor(R.color.alarm_card_music_txt_color));
                a(context, R.drawable.alarm_bell_red);
            }
        } else {
            this.f.setBackgroundResource(R.drawable.bg_alarm_card_blue);
            this.a.setVisibility(4);
            this.e.setVisibility(4);
            this.d.setTextColor(context.getResources().getColor(R.color.color_03F5FF));
            this.c.setTextColor(context.getResources().getColor(R.color.white));
            a(context, R.drawable.alarm_bell_cyan);
            this.c.setMaxLines(1);
            this.b.setText("");
            long b = b(alarmRealmObjectBean.getLocalTimestamp());
            this.i.setText(String.valueOf(b));
            if (b <= 1) {
                this.j.setText(context.getString(R.string.alarm_ringtone_less_one_minute));
            } else {
                this.j.setText(context.getString(R.string.alarm_ringtone_time));
            }
        }
        if (!TextUtils.isEmpty(alarmRealmObjectBean.getEvent())) {
            this.c.setText(alarmRealmObjectBean.getEvent());
        } else {
            this.c.setText("");
        }
        if (!TextUtils.isEmpty(alarmRealmObjectBean.getDisplayTxt())) {
            this.d.setText(Uri.decode(alarmRealmObjectBean.getDisplayTxt()));
        } else if (!TextUtils.isEmpty(alarmRealmObjectBean.getRingtone())) {
            this.d.setText(Uri.decode(alarmRealmObjectBean.getRingtone()));
        } else {
            this.d.setText(R.string.alarm_default_ring);
        }
    }

    private void a() {
        new CustomDialog.Builder().setLayoutResId(R.layout.dialog_only_title_layout).setStyle(CustomDialog.DialogStyle.ONLY_TITLE).setTitleResId(R.string.tip_delete_alarm).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmItemViewHolder$fxQsgg5NnIP375b8sOAqggvQoZw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmItemViewHolder.this.a(view);
            }
        }).build().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        AlarmModel.getInstance().removeAlarm(this.k);
    }

    private void b() {
        if (((ConstraintLayout.LayoutParams) this.f.getLayoutParams()).bottomMargin != 0) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.n);
            translateAnimation.setDuration(300L);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmItemViewHolder.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) AlarmItemViewHolder.this.f.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    layoutParams.topMargin = AlarmItemViewHolder.this.m;
                    AlarmItemViewHolder.this.f.setLayoutParams(layoutParams);
                    AlarmItemViewHolder.this.f.clearAnimation();
                    AlarmItemViewHolder.this.h.setVisibility(8);
                }
            });
            this.f.startAnimation(translateAnimation);
        }
    }

    private void c() {
        TranslateAnimation translateAnimation;
        if (((ConstraintLayout.LayoutParams) this.f.getLayoutParams()).bottomMargin == 0) {
            translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -this.n);
        } else {
            translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.n);
        }
        translateAnimation.setDuration(300L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmItemViewHolder.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                AlarmItemViewHolder.this.h.setVisibility(0);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                AlarmItemViewHolder.this.d();
                AlarmItemViewHolder.this.f.clearAnimation();
            }
        });
        this.f.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.f.getLayoutParams();
        if (layoutParams.bottomMargin == 0) {
            layoutParams.bottomMargin = this.n;
            layoutParams.topMargin = -this.l;
        } else {
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = this.m;
            this.h.setVisibility(8);
        }
        this.f.setLayoutParams(layoutParams);
    }

    private String a(Context context, AlarmRealmObjectBean alarmRealmObjectBean) {
        if (!Circle.ONCE.equals(alarmRealmObjectBean.getCircle())) {
            return AlarmHelper.getCircleString(context, alarmRealmObjectBean);
        }
        if (DateUtils.isToday(alarmRealmObjectBean.getLocalTimestamp())) {
            String str = context.getResources().getStringArray(R.array.weekdays)[TimeCalculator.dayOfWeek(alarmRealmObjectBean.getLocalDate())];
            return context.getString(R.string.today) + StringUtils.SPACE + str;
        } else if (!TimeUtils.isTomorrow(alarmRealmObjectBean.getLocalTimestamp())) {
            return TimeUtils.timestampToStr(alarmRealmObjectBean.getLocalTimestamp(), context.getString(R.string.alarm_date_format));
        } else {
            String str2 = context.getResources().getStringArray(R.array.weekdays)[TimeCalculator.dayOfWeek(alarmRealmObjectBean.getLocalDate())];
            return context.getString(R.string.tomorrow) + StringUtils.SPACE + str2;
        }
    }

    private void a(Context context, int i) {
        Drawable drawable = context.getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.d.setCompoundDrawables(drawable, null, null, null);
    }

    private static String a(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(11);
        int i2 = instance.get(12);
        return TimeCalculator.completedString(i) + Constants.COLON_SEPARATOR + TimeCalculator.completedString(i2);
    }

    private static boolean a(long j) {
        long currentTimeMillis = j - System.currentTimeMillis();
        return currentTimeMillis > 0 && currentTimeMillis < TimeUnit.MINUTES.toMillis(15L);
    }

    private static long b(long j) {
        long currentTimeMillis = j - System.currentTimeMillis();
        Logger logger = L.alarm;
        logger.d("interval=" + currentTimeMillis);
        long millis = currentTimeMillis / TimeUnit.SECONDS.toMillis(60L);
        Logger logger2 = L.alarm;
        logger2.d("minute=" + millis);
        if (millis < 1) {
            return 1L;
        }
        return millis;
    }
}
