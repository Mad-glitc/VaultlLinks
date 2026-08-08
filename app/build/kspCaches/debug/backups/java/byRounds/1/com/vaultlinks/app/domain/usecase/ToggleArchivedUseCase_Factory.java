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
public final class ToggleArchivedUseCase_Factory implements Factory<ToggleArchivedUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public ToggleArchivedUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleArchivedUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleArchivedUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new ToggleArchivedUseCase_Factory(repositoryProvider);
  }

  public static ToggleArchivedUseCase newInstance(LinkRepository repository) {
    return new ToggleArchivedUseCase(repository);
  }
}
