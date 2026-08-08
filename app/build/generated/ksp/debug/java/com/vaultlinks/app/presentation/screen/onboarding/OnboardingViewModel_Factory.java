package com.vaultlinks.app.presentation.screen.onboarding;

import com.vaultlinks.app.datastore.PreferencesManager;
import com.vaultlinks.app.domain.repository.CategoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public OnboardingViewModel_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(preferencesManagerProvider.get(), categoryRepositoryProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new OnboardingViewModel_Factory(preferencesManagerProvider, categoryRepositoryProvider);
  }

  public static OnboardingViewModel newInstance(PreferencesManager preferencesManager,
      CategoryRepository categoryRepository) {
    return new OnboardingViewModel(preferencesManager, categoryRepository);
  }
}
