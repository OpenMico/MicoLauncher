package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.work.Data;
import java.util.List;

@Dao
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public interface WorkProgressDao {
    @Query("DELETE from WorkProgress where work_spec_id=:workSpecId")
    void delete(@NonNull String str);

    @Query("DELETE FROM WorkProgress")
    void deleteAll();

    @Nullable
    @Query("SELECT progress FROM WorkProgress WHERE work_spec_id=:workSpecId")
    Data getProgressForWorkSpecId(@NonNull String str);

    @NonNull
    @Query("SELECT progress FROM WorkProgress WHERE work_spec_id IN (:workSpecIds)")
    List<Data> getProgressForWorkSpecIds(@NonNull List<String> list);

    @Insert(onConflict = 1)
    void insert(@NonNull WorkProgress workProgress);
}
