package com.vaultlinks.app.presentation.screen.settings;

import com.vaultlinks.app.data.backup.BackupManager;
import com.vaultlinks.app.datastore.PreferencesManager;
import com.vaultlinks.app.security.LockManager;
import com.vaultlinks.app.worker.WorkScheduler;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<LockManager> lockManagerProvider;

  private final Provider<BackupManager> backupManagerProvider;

  private final Provider<WorkScheduler> workSchedulerProvider;

  public SettingsViewModel_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LockManager> lockManagerProvider, Provider<BackupManager> backupManagerProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.lockManagerProvider = lockManagerProvider;
    this.backupManagerProvider = backupManagerProvider;
    this.workSchedulerProvider = workSchedulerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(preferencesManagerProvider.get(), lockManagerProvider.get(), backupManagerProvider.get(), workSchedulerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider,
      Provider<LockManager> lockManagerProvider, Provider<BackupManager> backupManagerProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    return new SettingsViewModel_Factory(preferencesManagerProvider, lockManagerProvider, backupManagerProvider, workSchedulerProvider);
  }

  public static SettingsViewModel newInstance(PreferencesManager preferencesManager,
      LockManager lockManager, BackupManager backupManager, WorkScheduler workScheduler) {
    return new SettingsViewModel(preferencesManager, lockManager, backupManager, workScheduler);
  }
}
