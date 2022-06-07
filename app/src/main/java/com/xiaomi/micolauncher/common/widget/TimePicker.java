package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.widget.NumberPicker;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes3.dex */
public class TimePicker extends FrameLayout {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() { // from class: com.xiaomi.micolauncher.common.widget.TimePicker.1
        @Override // com.xiaomi.micolauncher.common.widget.TimePicker.OnTimeChangedListener
        public void onTimeChanged(TimePicker timePicker, int i, int i2) {
        }
    };
    private final Button mAmPmButton;
    private final NumberPicker mAmPmSpinner;
    private final String[] mAmPmStrings;
    private Locale mCurrentLocale;
    private final NumberPicker mHourSpinner;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsEnabled;
    private final NumberPicker mMinuteSpinner;
    private OnTimeChangedListener mOnTimeChangedListener;
    private Calendar mTempCalendar;

    /* loaded from: classes3.dex */
    public interface OnTimeChangedListener {
        void onTimeChanged(TimePicker timePicker, int i, int i2);
    }

    void callOnTimeChanged() {
        onTimeChanged();
    }

    /* loaded from: classes3.dex */
    class OnMinuteChangeListener implements NumberPicker.OnValueChangeListener {
        OnMinuteChangeListener() {
        }

        @Override // com.xiaomi.micolauncher.common.widget.NumberPicker.OnValueChangeListener
        public void onValueChange(NumberPicker numberPicker, int i, int i2) {
            TimePicker.this.callOnTimeChanged();
        }
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsEnabled = true;
        setCurrentLocale(Locale.getDefault());
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.widget_time_picker, (ViewGroup) this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TimePicker);
        this.mHourSpinner = (NumberPicker) findViewById(R.id.hour);
        this.mMinuteSpinner = (NumberPicker) findViewById(R.id.minute);
        View findViewById = findViewById(R.id.amPm);
        if (obtainStyledAttributes != null) {
            this.mHourSpinner.setSelectTextColor(obtainStyledAttributes.getResourceId(2, R.color.number_picker_select_text_color));
            this.mHourSpinner.setNormalTextColor(obtainStyledAttributes.getResourceId(0, R.color.number_picker_normal_text_color));
            this.mHourSpinner.setSelectTextSize(obtainStyledAttributes.getResourceId(3, R.dimen.number_picker_text_size));
            this.mHourSpinner.setNormalTextSize(obtainStyledAttributes.getResourceId(1, R.dimen.number_picker_text_size_small));
            this.mMinuteSpinner.setSelectTextColor(obtainStyledAttributes.getResourceId(2, R.color.number_picker_select_text_color));
            this.mMinuteSpinner.setNormalTextColor(obtainStyledAttributes.getResourceId(0, R.color.number_picker_normal_text_color));
            this.mMinuteSpinner.setSelectTextSize(obtainStyledAttributes.getResourceId(3, R.dimen.number_picker_text_size));
            this.mMinuteSpinner.setNormalTextSize(obtainStyledAttributes.getResourceId(1, R.dimen.number_picker_text_size_small));
            if (findViewById instanceof NumberPicker) {
                NumberPicker numberPicker = (NumberPicker) findViewById;
                numberPicker.setSelectTextColor(obtainStyledAttributes.getResourceId(2, R.color.number_picker_select_text_color));
                numberPicker.setNormalTextColor(obtainStyledAttributes.getResourceId(0, R.color.number_picker_normal_text_color));
                numberPicker.setSelectTextSize(obtainStyledAttributes.getResourceId(3, R.dimen.number_picker_text_size));
                numberPicker.setNormalTextSize(obtainStyledAttributes.getResourceId(1, R.dimen.number_picker_text_size_small));
            }
        }
        this.mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$TimePicker$xzaQKK2ptNt32ZOrXPYxfl_LTq0
            @Override // com.xiaomi.micolauncher.common.widget.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker2, int i2, int i3) {
                TimePicker.lambda$new$0(TimePicker.this, numberPicker2, i2, i3);
            }
        });
        this.mMinuteSpinner.setMinValue(0);
        this.mMinuteSpinner.setMaxValue(59);
        this.mMinuteSpinner.setOnLongPressUpdateInterval(100L);
        this.mMinuteSpinner.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        this.mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$TimePicker$SVJIHmnlB3PpGFs75snsanDOdvE
            @Override // com.xiaomi.micolauncher.common.widget.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker2, int i2, int i3) {
                TimePicker.lambda$new$1(TimePicker.this, numberPicker2, i2, i3);
            }
        });
        this.mMinuteSpinner.setOnValueChangedListener(new OnMinuteChangeListener());
        this.mAmPmStrings = new DateFormatSymbols().getAmPmStrings();
        if (findViewById instanceof Button) {
            this.mAmPmSpinner = null;
            this.mAmPmButton = (Button) findViewById;
            this.mAmPmButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.widget.TimePicker.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.requestFocus();
                    TimePicker timePicker = TimePicker.this;
                    timePicker.mIsAm = !timePicker.mIsAm;
                    TimePicker.this.updateAmPmControl();
                    TimePicker.this.onTimeChanged();
                }
            });
        } else {
            this.mAmPmButton = null;
            this.mAmPmSpinner = (NumberPicker) findViewById;
            this.mAmPmSpinner.setMinValue(0);
            this.mAmPmSpinner.setMaxValue(1);
            this.mAmPmSpinner.setDisplayedValues(this.mAmPmStrings);
            this.mAmPmSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$TimePicker$uCM8Y0dYbnP2EbgYcHF9EvU5Qfc
                @Override // com.xiaomi.micolauncher.common.widget.NumberPicker.OnValueChangeListener
                public final void onValueChange(NumberPicker numberPicker2, int i2, int i3) {
                    TimePicker.lambda$new$2(TimePicker.this, numberPicker2, i2, i3);
                }
            });
        }
        updateHourControl();
        updateAmPmControl();
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);
        setCurrentHour(Integer.valueOf(this.mTempCalendar.get(11)));
        setCurrentMinute(Integer.valueOf(this.mTempCalendar.get(12)));
        if (!isEnabled()) {
            setEnabled(false);
        }
        if (Build.VERSION.SDK_INT >= 16 && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        obtainStyledAttributes.recycle();
    }

    public static /* synthetic */ void lambda$new$0(TimePicker timePicker, NumberPicker numberPicker, int i, int i2) {
        if (!timePicker.is24HourView() && ((i == 11 && i2 == 12) || (i == 12 && i2 == 11))) {
            timePicker.mIsAm = !timePicker.mIsAm;
            timePicker.updateAmPmControl();
        }
        timePicker.onTimeChanged();
    }

    public static /* synthetic */ void lambda$new$1(TimePicker timePicker, NumberPicker numberPicker, int i, int i2) {
        int minValue = timePicker.mMinuteSpinner.getMinValue();
        int maxValue = timePicker.mMinuteSpinner.getMaxValue();
        if (i == maxValue && i2 == minValue) {
            int value = timePicker.mHourSpinner.getValue() + 1;
            if (!timePicker.is24HourView() && value == 12) {
                timePicker.mIsAm = !timePicker.mIsAm;
                timePicker.updateAmPmControl();
            }
            timePicker.mHourSpinner.setValue(value);
        } else if (i == minValue && i2 == maxValue) {
            int value2 = timePicker.mHourSpinner.getValue() - 1;
            if (!timePicker.is24HourView() && value2 == 11) {
                timePicker.mIsAm = !timePicker.mIsAm;
                timePicker.updateAmPmControl();
            }
            timePicker.mHourSpinner.setValue(value2);
        }
        timePicker.onTimeChanged();
    }

    public static /* synthetic */ void lambda$new$2(TimePicker timePicker, NumberPicker numberPicker, int i, int i2) {
        numberPicker.requestFocus();
        timePicker.mIsAm = !timePicker.mIsAm;
        timePicker.updateAmPmControl();
        timePicker.onTimeChanged();
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mIsEnabled != z) {
            super.setEnabled(z);
            this.mMinuteSpinner.setEnabled(z);
            this.mHourSpinner.setEnabled(z);
            NumberPicker numberPicker = this.mAmPmSpinner;
            if (numberPicker != null) {
                numberPicker.setEnabled(z);
            } else {
                this.mAmPmButton.setEnabled(z);
            }
            this.mIsEnabled = z;
        }
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setCurrentLocale(configuration.locale);
    }

    private void setCurrentLocale(Locale locale) {
        if (!locale.equals(this.mCurrentLocale)) {
            this.mCurrentLocale = locale;
            this.mTempCalendar = Calendar.getInstance(locale);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.xiaomi.micolauncher.common.widget.TimePicker.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable parcelable, int i, int i2) {
            super(parcelable);
            this.mHour = i;
            this.mMinute = i2;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mHour = parcel.readInt();
            this.mMinute = parcel.readInt();
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mHour);
            parcel.writeInt(this.mMinute);
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour().intValue(), getCurrentMinute().intValue());
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentHour(Integer.valueOf(savedState.getHour()));
        setCurrentMinute(Integer.valueOf(savedState.getMinute()));
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mOnTimeChangedListener = onTimeChangedListener;
    }

    public Integer getCurrentHour() {
        int value = this.mHourSpinner.getValue();
        if (is24HourView()) {
            return Integer.valueOf(value);
        }
        if (this.mIsAm) {
            return Integer.valueOf(value % 12);
        }
        return Integer.valueOf((value % 12) + 12);
    }

    public void setCurrentHour(Integer num) {
        if (num != null && num != getCurrentHour()) {
            if (!is24HourView()) {
                if (num.intValue() >= 12) {
                    this.mIsAm = false;
                    if (num.intValue() > 12) {
                        num = Integer.valueOf(num.intValue() - 12);
                    }
                } else {
                    this.mIsAm = true;
                    if (num.intValue() == 0) {
                        num = 12;
                    }
                }
                updateAmPmControl();
            }
            this.mHourSpinner.setValue(num.intValue());
            onTimeChanged();
        }
    }

    public void setIs24HourView(Boolean bool) {
        if (this.mIs24HourView != bool.booleanValue()) {
            this.mIs24HourView = bool.booleanValue();
            int intValue = getCurrentHour().intValue();
            updateHourControl();
            setCurrentHour(Integer.valueOf(intValue));
            updateAmPmControl();
            this.mMinuteSpinner.setBackgroundResource(R.drawable.bg_picker_last);
        }
    }

    public boolean is24HourView() {
        return this.mIs24HourView;
    }

    public Integer getCurrentMinute() {
        return Integer.valueOf(this.mMinuteSpinner.getValue());
    }

    public void setCurrentMinute(Integer num) {
        if (num != getCurrentMinute()) {
            this.mMinuteSpinner.setValue(num.intValue());
            onTimeChanged();
        }
    }

    @Override // android.view.View
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onPopulateAccessibilityEvent(accessibilityEvent);
        }
        int i = this.mIs24HourView ? 129 : 65;
        this.mTempCalendar.set(11, getCurrentHour().intValue());
        this.mTempCalendar.set(12, getCurrentMinute().intValue());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(getContext(), this.mTempCalendar.getTimeInMillis(), i));
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.setClassName(TimePicker.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(TimePicker.class.getName());
        }
    }

    private void updateHourControl() {
        if (is24HourView()) {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(23);
            this.mHourSpinner.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
            return;
        }
        this.mHourSpinner.setMinValue(1);
        this.mHourSpinner.setMaxValue(12);
        this.mHourSpinner.setFormatter(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAmPmControl() {
        if (is24HourView()) {
            NumberPicker numberPicker = this.mAmPmSpinner;
            if (numberPicker != null) {
                numberPicker.setVisibility(8);
            } else {
                this.mAmPmButton.setVisibility(8);
            }
        } else {
            int i = !this.mIsAm ? 1 : 0;
            NumberPicker numberPicker2 = this.mAmPmSpinner;
            if (numberPicker2 != null) {
                numberPicker2.setValue(i);
                this.mAmPmSpinner.setVisibility(0);
            } else {
                this.mAmPmButton.setText(this.mAmPmStrings[i]);
                this.mAmPmButton.setVisibility(0);
            }
        }
        sendAccessibilityEvent(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        sendAccessibilityEvent(4);
        OnTimeChangedListener onTimeChangedListener = this.mOnTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(this, getCurrentHour().intValue(), getCurrentMinute().intValue());
        }
    }

    private void trySetContentDescription(View view, int i, int i2) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            findViewById.setContentDescription(getContext().getString(i2));
        }
    }
}
