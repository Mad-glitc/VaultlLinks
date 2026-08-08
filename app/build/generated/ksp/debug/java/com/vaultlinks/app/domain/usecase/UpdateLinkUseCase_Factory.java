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
public final class UpdateLinkUseCase_Factory implements Factory<UpdateLinkUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public UpdateLinkUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateLinkUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateLinkUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new UpdateLinkUseCase_Factory(repositoryProvider);
  }

  public static UpdateLinkUseCase newInstance(LinkRepository repository) {
    return new UpdateLinkUseCase(repository);
  }
}
