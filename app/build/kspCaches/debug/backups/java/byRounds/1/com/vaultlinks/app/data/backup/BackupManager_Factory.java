package com.vaultlinks.app.data.backup;

import android.content.Context;
import com.vaultlinks.app.domain.repository.LinkRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BackupManager_Factory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<LinkRepository> linkRepositoryProvider;

  public BackupManager_Factory(Provider<Context> contextProvider,
      Provider<LinkRepository> linkRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.linkRepositoryProvider = linkRepositoryProvider;
  }

  @Override
  public BackupManager get() {
    return newInstance(contextProvider.get(), linkRepositoryProvider.get());
  }

  public static BackupManager_Factory create(Provider<Context> contextProvider,
      Provider<LinkRepository> linkRepositoryProvider) {
    return new BackupManager_Factory(contextProvider, linkRepositoryProvider);
  }

  public static BackupManager newInstance(Context context, LinkRepository linkRepository) {
    return new BackupManager(context, linkRepository);
  }
}
