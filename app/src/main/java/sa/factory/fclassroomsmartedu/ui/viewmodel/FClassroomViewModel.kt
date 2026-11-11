package sa.factory.fclassroomsmartedu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sa.factory.fclassroomsmartedu.domain.usecase.*
import sa.factory.fclassroomsmartedu.model.FClassroomItem
import sa.factory.fclassroomsmartedu.model.FClassroomOrder
import sa.factory.fclassroomsmartedu.model.FClassroomTelemetry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FClassroomUiState(
    val catalog: List<FClassroomItem> = emptyList(),
    val filteredCatalog: List<FClassroomItem> = emptyList(),
    val selectedCategory: String = "الكل",
    val cart: List<FClassroomItem> = emptyList(),
    val telemetry: FClassroomTelemetry? = null,
    val lastOrder: FClassroomOrder? = null,
    val isCheckingOut: Boolean = false,
    val activeTab: String = "home"
)

class FClassroomViewModel(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val manageCartUseCase: ManageCartUseCase,
    private val processCheckoutUseCase: ProcessCheckoutUseCase,
    private val observeHardwareTelemetryUseCase: ObserveHardwareTelemetryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FClassroomUiState())
    val uiState: StateFlow<FClassroomUiState> = _uiState.asStateFlow()

    init {
        // Observe Catalog
        getCatalogUseCase().onEach { list ->
            _uiState.update { it.copy(catalog = list, filteredCatalog = list) }
        }.launchIn(viewModelScope)

        // Observe Cart
        manageCartUseCase.cart.onEach { cartItems ->
            _uiState.update { it.copy(cart = cartItems) }
        }.launchIn(viewModelScope)

        // Observe Telemetry
        observeHardwareTelemetryUseCase().onEach { telem ->
            _uiState.update { it.copy(telemetry = telem) }
        }.launchIn(viewModelScope)
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            val filtered = if (category == "الكل") state.catalog else state.catalog.filter { it.category == category }
            state.copy(selectedCategory = category, filteredCatalog = filtered)
        }
    }

    fun switchTab(tab: String) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun addToCart(item: FClassroomItem) = manageCartUseCase.add(item)
    fun removeFromCart(item: FClassroomItem) = manageCartUseCase.remove(item)

    fun checkout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCheckingOut = true) }
            val order = processCheckoutUseCase()
            _uiState.update { it.copy(isCheckingOut = false, lastOrder = order, activeTab = "checkout") }
        }
    }
}
