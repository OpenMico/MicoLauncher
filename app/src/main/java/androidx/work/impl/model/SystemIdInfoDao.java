package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
/* loaded from: classes.dex */
public interface SystemIdInfoDao {
    @Nullable
    @Query("SELECT * FROM SystemIdInfo WHERE work_spec_id=:workSpecId")
    SystemIdInfo getSystemIdInfo(@NonNull String str);

    @NonNull
    @Query("SELECT DISTINCT work_spec_id FROM SystemIdInfo")
    List<String> getWorkSpecIds();

    @Insert(onConflict = 1)
    void insertSystemIdInfo(@NonNull SystemIdInfo systemIdInfo);

    @Query("DELETE FROM SystemIdInfo where work_spec_id=:workSpecId")
    void removeSystemIdInfo(@NonNull String str);
}
