package com.vaultlinks.app.domain.usecase;

import com.vaultlinks.app.data.metadata.MetadataFetcher;
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
public final class SaveLinkUseCase_Factory implements Factory<SaveLinkUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  private final Provider<MetadataFetcher> metadataFetcherProvider;

  public SaveLinkUseCase_Factory(Provider<LinkRepository> repositoryProvider,
      Provider<MetadataFetcher> metadataFetcherProvider) {
    this.repositoryProvider = repositoryProvider;
    this.metadataFetcherProvider = metadataFetcherProvider;
  }

  @Override
  public SaveLinkUseCase get() {
    return newInstance(repositoryProvider.get(), metadataFetcherProvider.get());
  }

  public static SaveLinkUseCase_Factory create(Provider<LinkRepository> repositoryProvider,
      Provider<MetadataFetcher> metadataFetcherProvider) {
    return new SaveLinkUseCase_Factory(repositoryProvider, metadataFetcherProvider);
  }

  public static SaveLinkUseCase newInstance(LinkRepository repository,
      MetadataFetcher metadataFetcher) {
    return new SaveLinkUseCase(repository, metadataFetcher);
  }
}
