package com.vaultlinks.app.di;

import com.vaultlinks.app.data.local.VaultDatabase;
import com.vaultlinks.app.data.local.dao.PasswordDao;
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
public final class DatabaseModule_ProvidePasswordDaoFactory implements Factory<PasswordDao> {
  private final Provider<VaultDatabase> dbProvider;

  public DatabaseModule_ProvidePasswordDaoFactory(Provider<VaultDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PasswordDao get() {
    return providePasswordDao(dbProvider.get());
  }

  public static DatabaseModule_ProvidePasswordDaoFactory create(
      Provider<VaultDatabase> dbProvider) {
    return new DatabaseModule_ProvidePasswordDaoFactory(dbProvider);
  }

  public static PasswordDao providePasswordDao(VaultDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePasswordDao(db));
  }
}
