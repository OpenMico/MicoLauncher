package androidx.work.impl.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
/* loaded from: classes.dex */
public interface PreferenceDao {
    @Nullable
    @Query("SELECT long_value FROM Preference where `key`=:key")
    Long getLongValue(@NonNull String str);

    @NonNull
    @Query("SELECT long_value FROM Preference where `key`=:key")
    LiveData<Long> getObservableLongValue(@NonNull String str);

    @Insert(onConflict = 1)
    void insertPreference(@NonNull Preference preference);
}
