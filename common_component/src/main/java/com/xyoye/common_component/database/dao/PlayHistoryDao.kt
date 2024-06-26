package com.xyoye.common_component.database.dao

import androidx.room.*
import com.xyoye.data_component.entity.EpisodeHistoryEntity
import com.xyoye.data_component.entity.PlayHistoryEntity
import com.xyoye.data_component.enums.MediaType
import com.xyoye.data_component.helper.MediaTypeConverter
import kotlinx.coroutines.flow.Flow

/**
 * Created by xyoye on 2021/1/19.
 */

@Dao
interface PlayHistoryDao {

    @Query("SELECT * FROM play_history WHERE url != '' ORDER BY play_time DESC")
    suspend fun getAll(): MutableList<PlayHistoryEntity>

    @Query("SELECT * FROM play_history WHERE url != '' AND media_type = (:mediaType) ORDER BY play_time DESC")
    @TypeConverters(MediaTypeConverter::class)
    suspend fun getSingleMediaType(mediaType: MediaType): MutableList<PlayHistoryEntity>

    @Query("SELECT * FROM play_history WHERE media_type IN (:mediaTypes) AND url != '' ORDER BY play_time DESC LIMIT 1")
    @TypeConverters(MediaTypeConverter::class)
    suspend fun gitLastPlay(vararg mediaTypes: MediaType): PlayHistoryEntity?

    @Query("SELECT * FROM play_history WHERE storage_id = (:storageId) AND url != '' ORDER BY play_time DESC LIMIT 1")
    @TypeConverters(MediaTypeConverter::class)
    suspend fun gitStorageLastPlay(storageId: Int): PlayHistoryEntity?

    @Query("SELECT * FROM play_history WHERE unique_key = (:uniqueKey) AND media_type = (:mediaType)")
    @TypeConverters(MediaTypeConverter::class)
    suspend fun getPlayHistory(uniqueKey: String, mediaType: MediaType): PlayHistoryEntity?

    @Query("SELECT * FROM play_history WHERE unique_key = (:uniqueKey) AND storage_id = (:storageId)")
    suspend fun getPlayHistory(uniqueKey: String, storageId: Int): PlayHistoryEntity?

    @Query("SELECT * FROM play_history WHERE unique_key = (:uniqueKey) AND storage_id = (:storageId)")
    fun getPlayHistoryFlow(uniqueKey: String, storageId: Int): Flow<PlayHistoryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: PlayHistoryEntity)

    @Query("DELETE FROM play_history WHERE id = (:id)")
    suspend fun delete(id: Int)

    @Query("DELETE FROM play_history WHERE media_type IN (:mediaType)")
    @TypeConverters(MediaTypeConverter::class)
    suspend fun deleteTypeAll(mediaType: List<MediaType>)

    @Query("DELETE FROM play_history")
    suspend fun deleteAll()

    @Query("UPDATE play_history SET danmu_path = (:danmuPath), episode_id = (:episodeId) WHERE unique_key = (:uniqueKey) AND storage_id = (:storageId)")
    suspend fun updateDanmu(uniqueKey: String, storageId: Int, danmuPath: String?, episodeId: String?)

    @Query("UPDATE play_history SET subtitle_path = (:subtitlePath) WHERE unique_key = (:uniqueKey) AND storage_id = (:storageId)")
    suspend fun updateSubtitle(uniqueKey: String, storageId: Int, subtitlePath: String?)

    @Query("UPDATE play_history SET audio_path = (:audioPath) WHERE unique_key = (:uniqueKey) AND storage_id = (:storageId)")
    suspend fun updateAudio(uniqueKey: String, storageId: Int, audioPath: String?)

    @Transaction
    @Query("SELECT * FROM play_history WHERE episode_id IN (:episodeIds)")
    fun getEpisodeHistory(episodeIds: List<String>): Flow<List<EpisodeHistoryEntity>>
}