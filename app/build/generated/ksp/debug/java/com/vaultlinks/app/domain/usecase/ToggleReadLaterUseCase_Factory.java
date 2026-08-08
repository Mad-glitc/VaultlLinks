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
public final class ToggleReadLaterUseCase_Factory implements Factory<ToggleReadLaterUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public ToggleReadLaterUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleReadLaterUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleReadLaterUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new ToggleReadLaterUseCase_Factory(repositoryProvider);
  }

  public static ToggleReadLaterUseCase newInstance(LinkRepository repository) {
    return new ToggleReadLaterUseCase(repository);
  }
}
