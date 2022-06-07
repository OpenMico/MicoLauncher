package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class CompositeDateValidator implements CalendarConstraints.DateValidator {
    @NonNull
    private final a a;
    @NonNull
    private final List<CalendarConstraints.DateValidator> b;
    private static final a c = new a() { // from class: com.google.android.material.datepicker.CompositeDateValidator.1
        @Override // com.google.android.material.datepicker.CompositeDateValidator.a
        public int a() {
            return 1;
        }

        @Override // com.google.android.material.datepicker.CompositeDateValidator.a
        public boolean a(@NonNull List<CalendarConstraints.DateValidator> list, long j) {
            for (CalendarConstraints.DateValidator dateValidator : list) {
                if (dateValidator != null && dateValidator.isValid(j)) {
                    return true;
                }
            }
            return false;
        }
    };
    private static final a d = new a() { // from class: com.google.android.material.datepicker.CompositeDateValidator.2
        @Override // com.google.android.material.datepicker.CompositeDateValidator.a
        public int a() {
            return 2;
        }

        @Override // com.google.android.material.datepicker.CompositeDateValidator.a
        public boolean a(@NonNull List<CalendarConstraints.DateValidator> list, long j) {
            for (CalendarConstraints.DateValidator dateValidator : list) {
                if (dateValidator != null && !dateValidator.isValid(j)) {
                    return false;
                }
            }
            return true;
        }
    };
    public static final Parcelable.Creator<CompositeDateValidator> CREATOR = new Parcelable.Creator<CompositeDateValidator>() { // from class: com.google.android.material.datepicker.CompositeDateValidator.3
        @NonNull
        /* renamed from: a */
        public CompositeDateValidator createFromParcel(@NonNull Parcel parcel) {
            ArrayList readArrayList = parcel.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader());
            int readInt = parcel.readInt();
            return new CompositeDateValidator((List) Preconditions.checkNotNull(readArrayList), readInt == 2 ? CompositeDateValidator.d : readInt == 1 ? CompositeDateValidator.c : CompositeDateValidator.d);
        }

        @NonNull
        /* renamed from: a */
        public CompositeDateValidator[] newArray(int i) {
            return new CompositeDateValidator[i];
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface a {
        int a();

        boolean a(@NonNull List<CalendarConstraints.DateValidator> list, long j);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CompositeDateValidator(@NonNull List<CalendarConstraints.DateValidator> list, a aVar) {
        this.b = list;
        this.a = aVar;
    }

    @NonNull
    public static CalendarConstraints.DateValidator allOf(@NonNull List<CalendarConstraints.DateValidator> list) {
        return new CompositeDateValidator(list, d);
    }

    @NonNull
    public static CalendarConstraints.DateValidator anyOf(@NonNull List<CalendarConstraints.DateValidator> list) {
        return new CompositeDateValidator(list, c);
    }

    @Override // com.google.android.material.datepicker.CalendarConstraints.DateValidator
    public boolean isValid(long j) {
        return this.a.a(this.b, j);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeList(this.b);
        parcel.writeInt(this.a.a());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompositeDateValidator)) {
            return false;
        }
        CompositeDateValidator compositeDateValidator = (CompositeDateValidator) obj;
        return this.b.equals(compositeDateValidator.b) && this.a.a() == compositeDateValidator.a.a();
    }

    public int hashCode() {
        return this.b.hashCode();
    }
}
