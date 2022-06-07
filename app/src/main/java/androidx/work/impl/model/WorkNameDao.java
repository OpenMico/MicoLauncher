package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
/* loaded from: classes.dex */
public interface WorkNameDao {
    @NonNull
    @Query("SELECT name FROM workname WHERE work_spec_id=:workSpecId")
    List<String> getNamesForWorkSpecId(@NonNull String str);

    @Query("SELECT work_spec_id FROM workname WHERE name=:name")
    List<String> getWorkSpecIdsWithName(String str);

    @Insert(onConflict = 5)
    void insert(WorkName workName);
}
