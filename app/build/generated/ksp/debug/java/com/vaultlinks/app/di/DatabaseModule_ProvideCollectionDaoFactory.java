package com.vaultlinks.app.di;

import com.vaultlinks.app.data.local.VaultDatabase;
import com.vaultlinks.app.data.local.dao.CollectionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideCollectionDaoFactory implements Factory<CollectionDao> {
  private final Provider<VaultDatabase> dbProvider;

  public DatabaseModule_ProvideCollectionDaoFactory(Provider<VaultDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CollectionDao get() {
    return provideCollectionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCollectionDaoFactory create(
      Provider<VaultDatabase> dbProvider) {
    return new DatabaseModule_ProvideCollectionDaoFactory(dbProvider);
  }

  public static CollectionDao provideCollectionDao(VaultDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCollectionDao(db));
  }
}
