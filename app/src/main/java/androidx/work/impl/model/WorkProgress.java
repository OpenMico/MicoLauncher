package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.work.Data;

@Entity(foreignKeys = {@ForeignKey(childColumns = {"work_spec_id"}, entity = WorkSpec.class, onDelete = 5, onUpdate = 5, parentColumns = {"id"})})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkProgress {
    @NonNull
    @ColumnInfo(name = "progress")
    public final Data mProgress;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "work_spec_id")
    public final String mWorkSpecId;

    public WorkProgress(@NonNull String str, @NonNull Data data) {
        this.mWorkSpecId = str;
        this.mProgress = data;
    }
}
