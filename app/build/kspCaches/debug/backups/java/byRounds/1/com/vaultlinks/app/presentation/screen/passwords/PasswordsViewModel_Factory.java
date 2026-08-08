package com.vaultlinks.app.presentation.screen.passwords;

import com.vaultlinks.app.datastore.PreferencesManager;
import com.vaultlinks.app.domain.repository.PasswordRepository;
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
public final class PasswordsViewModel_Factory implements Factory<PasswordsViewModel> {
  private final Provider<PasswordRepository> passwordRepositoryProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public PasswordsViewModel_Factory(Provider<PasswordRepository> passwordRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.passwordRepositoryProvider = passwordRepositoryProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public PasswordsViewModel get() {
    return newInstance(passwordRepositoryProvider.get(), preferencesManagerProvider.get());
  }

  public static PasswordsViewModel_Factory create(
      Provider<PasswordRepository> passwordRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new PasswordsViewModel_Factory(passwordRepositoryProvider, preferencesManagerProvider);
  }

  public static PasswordsViewModel newInstance(PasswordRepository passwordRepository,
      PreferencesManager preferencesManager) {
    return new PasswordsViewModel(passwordRepository, preferencesManager);
  }
}
