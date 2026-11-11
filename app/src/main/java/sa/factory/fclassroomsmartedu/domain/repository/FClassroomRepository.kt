package sa.factory.fclassroomsmartedu.domain.repository

import sa.factory.fclassroomsmartedu.model.FClassroomItem
import sa.factory.fclassroomsmartedu.model.FClassroomOrder
import sa.factory.fclassroomsmartedu.model.FClassroomTelemetry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FClassroomRepository {
    val catalogFlow: Flow<List<FClassroomItem>>
    val activeCart: StateFlow<List<FClassroomItem>>
    val telemetryFlow: Flow<FClassroomTelemetry>
    
    fun addToCart(item: FClassroomItem)
    fun removeFromCart(item: FClassroomItem)
    fun clearCart()
    suspend fun checkout(paymentMethod: String): FClassroomOrder
    suspend fun refreshCatalog()
}
