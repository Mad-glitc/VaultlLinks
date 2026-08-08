package com.vaultlinks.app.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.datastore.PreferencesManager
import com.vaultlinks.app.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            categoryRepository.ensureDefaults()
            preferencesManager.setOnboardingDone(true)
        }
    }
}
