package com.google.android.material.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class CheckableImageButton extends AppCompatImageButton implements Checkable {
    private static final int[] a = {16842912};
    private boolean b;
    private boolean c;
    private boolean d;

    public CheckableImageButton(Context context) {
        this(context, null);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = true;
        this.d = true;
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.CheckableImageButton.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityEvent(View view, @NonNull AccessibilityEvent accessibilityEvent) {
                super.onInitializeAccessibilityEvent(view, accessibilityEvent);
                accessibilityEvent.setChecked(CheckableImageButton.this.isChecked());
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(CheckableImageButton.this.isCheckable());
                accessibilityNodeInfoCompat.setChecked(CheckableImageButton.this.isChecked());
            }
        });
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.c && this.b != z) {
            this.b = z;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.b;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.b);
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        if (this.d) {
            super.setPressed(z);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public int[] onCreateDrawableState(int i) {
        if (this.b) {
            return mergeDrawableStates(super.onCreateDrawableState(i + a.length), a);
        }
        return super.onCreateDrawableState(i);
    }

    @Override // android.view.View
    @NonNull
    protected Parcelable onSaveInstanceState() {
        a aVar = new a(super.onSaveInstanceState());
        aVar.a = this.b;
        return aVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof a)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        a aVar = (a) parcelable;
        super.onRestoreInstanceState(aVar.getSuperState());
        setChecked(aVar.a);
    }

    public void setCheckable(boolean z) {
        if (this.c != z) {
            this.c = z;
            sendAccessibilityEvent(0);
        }
    }

    public boolean isCheckable() {
        return this.c;
    }

    public void setPressable(boolean z) {
        this.d = z;
    }

    public boolean isPressable() {
        return this.d;
    }

    /* loaded from: classes2.dex */
    public static class a extends AbsSavedState {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.ClassLoaderCreator<a>() { // from class: com.google.android.material.internal.CheckableImageButton.a.1
            @NonNull
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new a(parcel, classLoader);
            }

            @NonNull
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel) {
                return new a(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public a[] newArray(int i) {
                return new a[i];
            }
        };
        boolean a;

        public a(Parcelable parcelable) {
            super(parcelable);
        }

        public a(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a ? 1 : 0);
        }

        private void a(@NonNull Parcel parcel) {
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.a = z;
        }
    }
}
