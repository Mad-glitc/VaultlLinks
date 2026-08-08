package com.vaultlinks.app.security;

import com.vaultlinks.app.datastore.PreferencesManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LockManager_Factory implements Factory<LockManager> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  public LockManager_Factory(Provider<PreferencesManager> preferencesManagerProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public LockManager get() {
    return newInstance(preferencesManagerProvider.get());
  }

  public static LockManager_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new LockManager_Factory(preferencesManagerProvider);
  }

  public static LockManager newInstance(PreferencesManager preferencesManager) {
    return new LockManager(preferencesManager);
  }
}
