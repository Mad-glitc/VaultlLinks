package com.vaultlinks.app.security;

import android.content.Context;
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
public final class DatabaseKeyProvider_Factory implements Factory<DatabaseKeyProvider> {
  private final Provider<Context> contextProvider;

  public DatabaseKeyProvider_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DatabaseKeyProvider get() {
    return newInstance(contextProvider.get());
  }

  public static DatabaseKeyProvider_Factory create(Provider<Context> contextProvider) {
    return new DatabaseKeyProvider_Factory(contextProvider);
  }

  public static DatabaseKeyProvider newInstance(Context context) {
    return new DatabaseKeyProvider(context);
  }
}
