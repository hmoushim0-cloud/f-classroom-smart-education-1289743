package sa.factory.fclassroomsmartedu.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import sa.factory.fclassroomsmartedu.model.FClassroomItem

@Entity(tableName = "smart_education_items_cache")
data class FClassroomEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priceSar: Double,
    val badge: String,
    val category: String,
    val status: String
) {
    fun toDomain(): FClassroomItem = FClassroomItem(
        id = id,
        title = title,
        description = description,
        priceSar = priceSar,
        badge = badge,
        category = category,
        status = status
    )
}
