package com.vaultlinks.app.data.repository;

import com.vaultlinks.app.data.local.dao.PasswordDao;
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
public final class PasswordRepositoryImpl_Factory implements Factory<PasswordRepositoryImpl> {
  private final Provider<PasswordDao> passwordDaoProvider;

  public PasswordRepositoryImpl_Factory(Provider<PasswordDao> passwordDaoProvider) {
    this.passwordDaoProvider = passwordDaoProvider;
  }

  @Override
  public PasswordRepositoryImpl get() {
    return newInstance(passwordDaoProvider.get());
  }

  public static PasswordRepositoryImpl_Factory create(Provider<PasswordDao> passwordDaoProvider) {
    return new PasswordRepositoryImpl_Factory(passwordDaoProvider);
  }

  public static PasswordRepositoryImpl newInstance(PasswordDao passwordDao) {
    return new PasswordRepositoryImpl(passwordDao);
  }
}
