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
public final class SearchLinksUseCase_Factory implements Factory<SearchLinksUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public SearchLinksUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchLinksUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SearchLinksUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new SearchLinksUseCase_Factory(repositoryProvider);
  }

  public static SearchLinksUseCase newInstance(LinkRepository repository) {
    return new SearchLinksUseCase(repository);
  }
}
