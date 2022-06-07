package androidx.work.impl.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
/* loaded from: classes.dex */
public interface DependencyDao {
    @Query("SELECT work_spec_id FROM dependency WHERE prerequisite_id=:id")
    List<String> getDependentWorkIds(String str);

    @Query("SELECT prerequisite_id FROM dependency WHERE work_spec_id=:id")
    List<String> getPrerequisites(String str);

    @Query("SELECT COUNT(*)=0 FROM dependency WHERE work_spec_id=:id AND prerequisite_id IN (SELECT id FROM workspec WHERE state!=2)")
    boolean hasCompletedAllPrerequisites(String str);

    @Query("SELECT COUNT(*)>0 FROM dependency WHERE prerequisite_id=:id")
    boolean hasDependents(String str);

    @Insert(onConflict = 5)
    void insertDependency(Dependency dependency);
}
