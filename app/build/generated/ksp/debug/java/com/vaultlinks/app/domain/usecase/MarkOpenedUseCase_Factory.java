package com.vaultlinks.app.domain.usecase;

import com.vaultlinks.app.domain.repository.LinkRepository;
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
public final class MarkOpenedUseCase_Factory implements Factory<MarkOpenedUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public MarkOpenedUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MarkOpenedUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static MarkOpenedUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new MarkOpenedUseCase_Factory(repositoryProvider);
  }

  public static MarkOpenedUseCase newInstance(LinkRepository repository) {
    return new MarkOpenedUseCase(repository);
  }
}
