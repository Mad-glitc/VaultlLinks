package com.vaultlinks.app.di;

import android.content.Context;
import com.vaultlinks.app.data.local.VaultDatabase;
import com.vaultlinks.app.security.DatabaseKeyProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideVaultDatabaseFactory implements Factory<VaultDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<DatabaseKeyProvider> keyProvider;

  public DatabaseModule_ProvideVaultDatabaseFactory(Provider<Context> contextProvider,
      Provider<DatabaseKeyProvider> keyProvider) {
    this.contextProvider = contextProvider;
    this.keyProvider = keyProvider;
  }

  @Override
  public VaultDatabase get() {
    return provideVaultDatabase(contextProvider.get(), keyProvider.get());
  }

  public static DatabaseModule_ProvideVaultDatabaseFactory create(Provider<Context> contextProvider,
      Provider<DatabaseKeyProvider> keyProvider) {
    return new DatabaseModule_ProvideVaultDatabaseFactory(contextProvider, keyProvider);
  }

  public static VaultDatabase provideVaultDatabase(Context context,
      DatabaseKeyProvider keyProvider) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideVaultDatabase(context, keyProvider));
  }
}
