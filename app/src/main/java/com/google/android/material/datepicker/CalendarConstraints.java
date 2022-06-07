package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;
import org.fourthline.cling.model.Constants;

/* loaded from: classes2.dex */
public final class CalendarConstraints implements Parcelable {
    public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator<CalendarConstraints>() { // from class: com.google.android.material.datepicker.CalendarConstraints.1
        @NonNull
        /* renamed from: a */
        public CalendarConstraints createFromParcel(@NonNull Parcel parcel) {
            return new CalendarConstraints((f) parcel.readParcelable(f.class.getClassLoader()), (f) parcel.readParcelable(f.class.getClassLoader()), (DateValidator) parcel.readParcelable(DateValidator.class.getClassLoader()), (f) parcel.readParcelable(f.class.getClassLoader()));
        }

        @NonNull
        /* renamed from: a */
        public CalendarConstraints[] newArray(int i) {
            return new CalendarConstraints[i];
        }
    };
    @NonNull
    private final f a;
    @NonNull
    private final f b;
    @NonNull
    private final DateValidator c;
    @Nullable
    private f d;
    private final int e;
    private final int f;

    /* loaded from: classes2.dex */
    public interface DateValidator extends Parcelable {
        boolean isValid(long j);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CalendarConstraints(@NonNull f fVar, @NonNull f fVar2, @NonNull DateValidator dateValidator, @Nullable f fVar3) {
        this.a = fVar;
        this.b = fVar2;
        this.d = fVar3;
        this.c = dateValidator;
        if (fVar3 != null && fVar.compareTo(fVar3) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        } else if (fVar3 == null || fVar3.compareTo(fVar2) <= 0) {
            this.f = fVar.b(fVar2) + 1;
            this.e = (fVar2.b - fVar.b) + 1;
        } else {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
    }

    public boolean a(long j) {
        if (this.a.a(1) <= j) {
            f fVar = this.b;
            if (j <= fVar.a(fVar.d)) {
                return true;
            }
        }
        return false;
    }

    public DateValidator getDateValidator() {
        return this.c;
    }

    @NonNull
    public f a() {
        return this.a;
    }

    @NonNull
    public f b() {
        return this.b;
    }

    @Nullable
    public f c() {
        return this.d;
    }

    public void a(@Nullable f fVar) {
        this.d = fVar;
    }

    public int d() {
        return this.f;
    }

    public int e() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CalendarConstraints)) {
            return false;
        }
        CalendarConstraints calendarConstraints = (CalendarConstraints) obj;
        return this.a.equals(calendarConstraints.a) && this.b.equals(calendarConstraints.b) && ObjectsCompat.equals(this.d, calendarConstraints.d) && this.c.equals(calendarConstraints.c);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b, this.d, this.c});
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.a, 0);
        parcel.writeParcelable(this.b, 0);
        parcel.writeParcelable(this.d, 0);
        parcel.writeParcelable(this.c, 0);
    }

    public f b(f fVar) {
        if (fVar.compareTo(this.a) < 0) {
            return this.a;
        }
        return fVar.compareTo(this.b) > 0 ? this.b : fVar;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        static final long a = k.a(f.a(Constants.UPNP_MULTICAST_PORT, 0).e);
        static final long b = k.a(f.a(2100, 11).e);
        private long c;
        private long d;
        private Long e;
        private DateValidator f;

        public Builder() {
            this.c = a;
            this.d = b;
            this.f = DateValidatorPointForward.from(Long.MIN_VALUE);
        }

        public Builder(@NonNull CalendarConstraints calendarConstraints) {
            this.c = a;
            this.d = b;
            this.f = DateValidatorPointForward.from(Long.MIN_VALUE);
            this.c = calendarConstraints.a.e;
            this.d = calendarConstraints.b.e;
            this.e = Long.valueOf(calendarConstraints.d.e);
            this.f = calendarConstraints.c;
        }

        @NonNull
        public Builder setStart(long j) {
            this.c = j;
            return this;
        }

        @NonNull
        public Builder setEnd(long j) {
            this.d = j;
            return this;
        }

        @NonNull
        public Builder setOpenAt(long j) {
            this.e = Long.valueOf(j);
            return this;
        }

        @NonNull
        public Builder setValidator(@NonNull DateValidator dateValidator) {
            this.f = dateValidator;
            return this;
        }

        @NonNull
        public CalendarConstraints build() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("DEEP_COPY_VALIDATOR_KEY", this.f);
            f a2 = f.a(this.c);
            f a3 = f.a(this.d);
            DateValidator dateValidator = (DateValidator) bundle.getParcelable("DEEP_COPY_VALIDATOR_KEY");
            Long l = this.e;
            return new CalendarConstraints(a2, a3, dateValidator, l == null ? null : f.a(l.longValue()));
        }
    }
}
