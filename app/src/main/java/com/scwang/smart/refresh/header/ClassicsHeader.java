package com.scwang.smart.refresh.header;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.scwang.smart.drawable.ProgressDrawable;
import com.scwang.smart.refresh.classics.ArrowDrawable;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.header.classics.R;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class ClassicsHeader extends ClassicsAbstract<ClassicsHeader> implements RefreshHeader {
    protected String KEY_LAST_UPDATE_TIME;
    protected boolean mEnableLastTime;
    protected Date mLastTime;
    protected DateFormat mLastUpdateFormat;
    protected TextView mLastUpdateText;
    protected SharedPreferences mShared;
    protected String mTextFailed;
    protected String mTextFinish;
    protected String mTextLoading;
    protected String mTextPulling;
    protected String mTextRefreshing;
    protected String mTextRelease;
    protected String mTextSecondary;
    protected String mTextUpdate;
    public static final int ID_TEXT_UPDATE = R.id.srl_classics_update;
    public static String REFRESH_HEADER_PULLING = null;
    public static String REFRESH_HEADER_REFRESHING = null;
    public static String REFRESH_HEADER_LOADING = null;
    public static String REFRESH_HEADER_RELEASE = null;
    public static String REFRESH_HEADER_FINISH = null;
    public static String REFRESH_HEADER_FAILED = null;
    public static String REFRESH_HEADER_UPDATE = null;
    public static String REFRESH_HEADER_SECONDARY = null;

    public ClassicsHeader(Context context) {
        this(context, null);
    }

    public ClassicsHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        FragmentManager supportFragmentManager;
        this.KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
        this.mEnableLastTime = true;
        View.inflate(context, R.layout.srl_classics_header, this);
        ImageView imageView = (ImageView) findViewById(R.id.srl_classics_arrow);
        this.mArrowView = imageView;
        TextView textView = (TextView) findViewById(R.id.srl_classics_update);
        this.mLastUpdateText = textView;
        ImageView imageView2 = (ImageView) findViewById(R.id.srl_classics_progress);
        this.mProgressView = imageView2;
        this.mTitleText = (TextView) findViewById(R.id.srl_classics_title);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClassicsHeader);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        new LinearLayout.LayoutParams(-2, -2).topMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextTimeMarginTop, SmartUtil.dp2px(0.0f));
        layoutParams2.rightMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlDrawableMarginRight, SmartUtil.dp2px(20.0f));
        layoutParams.rightMargin = layoutParams2.rightMargin;
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, layoutParams.height);
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, layoutParams2.height);
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams.height);
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams2.height);
        this.mFinishDuration = obtainStyledAttributes.getInt(R.styleable.ClassicsHeader_srlFinishDuration, this.mFinishDuration);
        this.mEnableLastTime = obtainStyledAttributes.getBoolean(R.styleable.ClassicsHeader_srlEnableLastTime, this.mEnableLastTime);
        this.mSpinnerStyle = SpinnerStyle.values[obtainStyledAttributes.getInt(R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal)];
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlDrawableArrow)) {
            this.mArrowView.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.ClassicsHeader_srlDrawableArrow));
        } else if (this.mArrowView.getDrawable() == null) {
            this.mArrowDrawable = new ArrowDrawable();
            this.mArrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlDrawableProgress)) {
            this.mProgressView.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.ClassicsHeader_srlDrawableProgress));
        } else if (this.mProgressView.getDrawable() == null) {
            this.mProgressDrawable = new ProgressDrawable();
            this.mProgressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTitle, SmartUtil.dp2px(16.0f)));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSizeTime)) {
            this.mLastUpdateText.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTime, SmartUtil.dp2px(12.0f)));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            super.setPrimaryColor(obtainStyledAttributes.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            setAccentColor(obtainStyledAttributes.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextPulling)) {
            this.mTextPulling = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextPulling);
        } else {
            String str = REFRESH_HEADER_PULLING;
            if (str != null) {
                this.mTextPulling = str;
            } else {
                this.mTextPulling = context.getString(R.string.srl_header_pulling);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextLoading)) {
            this.mTextLoading = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextLoading);
        } else {
            String str2 = REFRESH_HEADER_LOADING;
            if (str2 != null) {
                this.mTextLoading = str2;
            } else {
                this.mTextLoading = context.getString(R.string.srl_header_loading);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextRelease)) {
            this.mTextRelease = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextRelease);
        } else {
            String str3 = REFRESH_HEADER_RELEASE;
            if (str3 != null) {
                this.mTextRelease = str3;
            } else {
                this.mTextRelease = context.getString(R.string.srl_header_release);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextFinish)) {
            this.mTextFinish = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextFinish);
        } else {
            String str4 = REFRESH_HEADER_FINISH;
            if (str4 != null) {
                this.mTextFinish = str4;
            } else {
                this.mTextFinish = context.getString(R.string.srl_header_finish);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextFailed)) {
            this.mTextFailed = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextFailed);
        } else {
            String str5 = REFRESH_HEADER_FAILED;
            if (str5 != null) {
                this.mTextFailed = str5;
            } else {
                this.mTextFailed = context.getString(R.string.srl_header_failed);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSecondary)) {
            this.mTextSecondary = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextSecondary);
        } else {
            String str6 = REFRESH_HEADER_SECONDARY;
            if (str6 != null) {
                this.mTextSecondary = str6;
            } else {
                this.mTextSecondary = context.getString(R.string.srl_header_secondary);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextRefreshing)) {
            this.mTextRefreshing = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextRefreshing);
        } else {
            String str7 = REFRESH_HEADER_REFRESHING;
            if (str7 != null) {
                this.mTextRefreshing = str7;
            } else {
                this.mTextRefreshing = context.getString(R.string.srl_header_refreshing);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextUpdate)) {
            this.mTextUpdate = obtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextUpdate);
        } else {
            String str8 = REFRESH_HEADER_UPDATE;
            if (str8 != null) {
                this.mTextUpdate = str8;
            } else {
                this.mTextUpdate = context.getString(R.string.srl_header_update);
            }
        }
        this.mLastUpdateFormat = new SimpleDateFormat(this.mTextUpdate, Locale.getDefault());
        obtainStyledAttributes.recycle();
        imageView2.animate().setInterpolator(null);
        textView.setVisibility(this.mEnableLastTime ? 0 : 8);
        this.mTitleText.setText(isInEditMode() ? this.mTextRefreshing : this.mTextPulling);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
        try {
            if ((context instanceof FragmentActivity) && (supportFragmentManager = ((FragmentActivity) context).getSupportFragmentManager()) != null && supportFragmentManager.getFragments().size() > 0) {
                setLastUpdateTime(new Date());
                return;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.KEY_LAST_UPDATE_TIME += context.getClass().getName();
        this.mShared = context.getSharedPreferences("ClassicsHeader", 0);
        setLastUpdateTime(new Date(this.mShared.getLong(this.KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));
    }

    @Override // com.scwang.smart.refresh.classics.ClassicsAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        if (z) {
            this.mTitleText.setText(this.mTextFinish);
            if (this.mLastTime != null) {
                setLastUpdateTime(new Date());
            }
        } else {
            this.mTitleText.setText(this.mTextFailed);
        }
        return super.onFinish(refreshLayout, z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        ImageView imageView = this.mArrowView;
        TextView textView = this.mLastUpdateText;
        int i = 8;
        switch (refreshState2) {
            case None:
                if (this.mEnableLastTime) {
                    i = 0;
                }
                textView.setVisibility(i);
                break;
            case PullDownToRefresh:
                break;
            case Refreshing:
            case RefreshReleased:
                this.mTitleText.setText(this.mTextRefreshing);
                imageView.setVisibility(8);
                return;
            case ReleaseToRefresh:
                this.mTitleText.setText(this.mTextRelease);
                imageView.animate().rotation(180.0f);
                return;
            case ReleaseToTwoLevel:
                this.mTitleText.setText(this.mTextSecondary);
                imageView.animate().rotation(0.0f);
                return;
            case Loading:
                imageView.setVisibility(8);
                if (this.mEnableLastTime) {
                    i = 4;
                }
                textView.setVisibility(i);
                this.mTitleText.setText(this.mTextLoading);
                return;
            default:
                return;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.setVisibility(0);
        imageView.animate().rotation(0.0f);
    }

    public ClassicsHeader setLastUpdateTime(Date date) {
        this.mLastTime = date;
        this.mLastUpdateText.setText(this.mLastUpdateFormat.format(date));
        if (this.mShared != null && !isInEditMode()) {
            this.mShared.edit().putLong(this.KEY_LAST_UPDATE_TIME, date.getTime()).apply();
        }
        return this;
    }

    public ClassicsHeader setTimeFormat(DateFormat dateFormat) {
        this.mLastUpdateFormat = dateFormat;
        Date date = this.mLastTime;
        if (date != null) {
            this.mLastUpdateText.setText(this.mLastUpdateFormat.format(date));
        }
        return this;
    }

    public ClassicsHeader setLastUpdateText(CharSequence charSequence) {
        this.mLastTime = null;
        this.mLastUpdateText.setText(charSequence);
        return this;
    }

    @Override // com.scwang.smart.refresh.classics.ClassicsAbstract
    public ClassicsHeader setAccentColor(@ColorInt int i) {
        this.mLastUpdateText.setTextColor((16777215 & i) | (-872415232));
        return (ClassicsHeader) super.setAccentColor(i);
    }

    public ClassicsHeader setEnableLastTime(boolean z) {
        TextView textView = this.mLastUpdateText;
        this.mEnableLastTime = z;
        textView.setVisibility(z ? 0 : 8);
        if (this.mRefreshKernel != null) {
            this.mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextSizeTime(float f) {
        this.mLastUpdateText.setTextSize(f);
        if (this.mRefreshKernel != null) {
            this.mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextSizeTime(int i, float f) {
        this.mLastUpdateText.setTextSize(i, f);
        if (this.mRefreshKernel != null) {
            this.mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextTimeMarginTop(float f) {
        TextView textView = this.mLastUpdateText;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        marginLayoutParams.topMargin = SmartUtil.dp2px(f);
        textView.setLayoutParams(marginLayoutParams);
        return this;
    }

    public ClassicsHeader setTextTimeMarginTopPx(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mLastUpdateText.getLayoutParams();
        marginLayoutParams.topMargin = i;
        this.mLastUpdateText.setLayoutParams(marginLayoutParams);
        return this;
    }
}
