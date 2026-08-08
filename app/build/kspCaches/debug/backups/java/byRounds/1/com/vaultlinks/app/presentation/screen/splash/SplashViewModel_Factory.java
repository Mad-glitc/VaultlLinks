package com.vaultlinks.app.presentation.screen.splash;

import com.vaultlinks.app.datastore.PreferencesManager;
import com.vaultlinks.app.security.LockManager;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<LockManager> lockManagerProvider;

  public SplashViewModel_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LockManager> lockManagerProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.lockManagerProvider = lockManagerProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(preferencesManagerProvider.get(), lockManagerProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LockManager> lockManagerProvider) {
    return new SplashViewModel_Factory(preferencesManagerProvider, lockManagerProvider);
  }

  public static SplashViewModel newInstance(PreferencesManager preferencesManager,
      LockManager lockManager) {
    return new SplashViewModel(preferencesManager, lockManager);
  }
}
