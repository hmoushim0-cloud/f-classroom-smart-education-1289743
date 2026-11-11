package sa.factory.fclassroomsmartedu.data.remote

import sa.factory.fclassroomsmartedu.model.FClassroomItem
import sa.factory.fclassroomsmartedu.model.FClassroomOrder

interface FClassroomApiService {
    suspend fun fetchCatalog(): List<FClassroomItem>
    suspend fun submitOrder(order: FClassroomOrder): Boolean
}
