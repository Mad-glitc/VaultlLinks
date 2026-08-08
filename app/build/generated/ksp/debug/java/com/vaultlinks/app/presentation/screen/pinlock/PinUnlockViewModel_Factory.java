package com.vaultlinks.app.presentation.screen.pinlock;

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
public final class PinUnlockViewModel_Factory implements Factory<PinUnlockViewModel> {
  private final Provider<LockManager> lockManagerProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public PinUnlockViewModel_Factory(Provider<LockManager> lockManagerProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.lockManagerProvider = lockManagerProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public PinUnlockViewModel get() {
    return newInstance(lockManagerProvider.get(), preferencesManagerProvider.get());
  }

  public static PinUnlockViewModel_Factory create(Provider<LockManager> lockManagerProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new PinUnlockViewModel_Factory(lockManagerProvider, preferencesManagerProvider);
  }

  public static PinUnlockViewModel newInstance(LockManager lockManager,
      PreferencesManager preferencesManager) {
    return new PinUnlockViewModel(lockManager, preferencesManager);
  }
}
