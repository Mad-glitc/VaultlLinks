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
public final class DeleteLinkUseCase_Factory implements Factory<DeleteLinkUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public DeleteLinkUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteLinkUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteLinkUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new DeleteLinkUseCase_Factory(repositoryProvider);
  }

  public static DeleteLinkUseCase newInstance(LinkRepository repository) {
    return new DeleteLinkUseCase(repository);
  }
}
