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
public final class GetLinkUseCase_Factory implements Factory<GetLinkUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public GetLinkUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetLinkUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetLinkUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new GetLinkUseCase_Factory(repositoryProvider);
  }

  public static GetLinkUseCase newInstance(LinkRepository repository) {
    return new GetLinkUseCase(repository);
  }
}
