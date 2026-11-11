package sa.factory.fclassroomsmartedu.domain.usecase

import sa.factory.fclassroomsmartedu.domain.repository.FClassroomRepository
import sa.factory.fclassroomsmartedu.model.FClassroomItem
import sa.factory.fclassroomsmartedu.model.FClassroomOrder
import sa.factory.fclassroomsmartedu.model.FClassroomTelemetry
import kotlinx.coroutines.flow.Flow

class GetCatalogUseCase(private val repository: FClassroomRepository) {
    operator fun invoke(): Flow<List<FClassroomItem>> = repository.catalogFlow
}

class ManageCartUseCase(private val repository: FClassroomRepository) {
    val cart = repository.activeCart
    fun add(item: FClassroomItem) = repository.addToCart(item)
    fun remove(item: FClassroomItem) = repository.removeFromCart(item)
    fun clear() = repository.clearCart()
}

class ProcessCheckoutUseCase(private val repository: FClassroomRepository) {
    suspend operator fun invoke(paymentMethod: String = "Mada Contactless EMV"): FClassroomOrder =
        repository.checkout(paymentMethod)
}

class ObserveHardwareTelemetryUseCase(private val repository: FClassroomRepository) {
    operator fun invoke(): Flow<FClassroomTelemetry> = repository.telemetryFlow
}
