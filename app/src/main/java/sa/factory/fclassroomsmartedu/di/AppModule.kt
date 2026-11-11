package sa.factory.fclassroomsmartedu.di

import sa.factory.fclassroomsmartedu.data.repository.FClassroomRepositoryImpl
import sa.factory.fclassroomsmartedu.domain.repository.FClassroomRepository
import sa.factory.fclassroomsmartedu.domain.usecase.*
import sa.factory.fclassroomsmartedu.hardware.HardwareSensorDriver
import sa.factory.fclassroomsmartedu.ui.viewmodel.FClassroomViewModel

class DiContainer {
    val sensorDriver = HardwareSensorDriver()
    val repository: FClassroomRepository = FClassroomRepositoryImpl(sensorDriver)

    val getCatalogUseCase = GetCatalogUseCase(repository)
    val manageCartUseCase = ManageCartUseCase(repository)
    val processCheckoutUseCase = ProcessCheckoutUseCase(repository)
    val observeHardwareTelemetryUseCase = ObserveHardwareTelemetryUseCase(repository)

    fun provideViewModel(): FClassroomViewModel = FClassroomViewModel(
        getCatalogUseCase,
        manageCartUseCase,
        processCheckoutUseCase,
        observeHardwareTelemetryUseCase
    )
}
