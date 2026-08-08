package com.vaultlinks.app.presentation.screen.savelink;

import com.vaultlinks.app.data.metadata.MetadataFetcher;
import com.vaultlinks.app.domain.repository.CategoryRepository;
import com.vaultlinks.app.domain.repository.CollectionRepository;
import com.vaultlinks.app.domain.usecase.SaveLinkUseCase;
import com.vaultlinks.app.worker.WorkScheduler;
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
public final class SaveLinkViewModel_Factory implements Factory<SaveLinkViewModel> {
  private final Provider<SaveLinkUseCase> saveLinkUseCaseProvider;

  private final Provider<MetadataFetcher> metadataFetcherProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<CollectionRepository> collectionRepositoryProvider;

  private final Provider<WorkScheduler> workSchedulerProvider;

  public SaveLinkViewModel_Factory(Provider<SaveLinkUseCase> saveLinkUseCaseProvider,
      Provider<MetadataFetcher> metadataFetcherProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    this.saveLinkUseCaseProvider = saveLinkUseCaseProvider;
    this.metadataFetcherProvider = metadataFetcherProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.collectionRepositoryProvider = collectionRepositoryProvider;
    this.workSchedulerProvider = workSchedulerProvider;
  }

  @Override
  public SaveLinkViewModel get() {
    return newInstance(saveLinkUseCaseProvider.get(), metadataFetcherProvider.get(), categoryRepositoryProvider.get(), collectionRepositoryProvider.get(), workSchedulerProvider.get());
  }

  public static SaveLinkViewModel_Factory create(Provider<SaveLinkUseCase> saveLinkUseCaseProvider,
      Provider<MetadataFetcher> metadataFetcherProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    return new SaveLinkViewModel_Factory(saveLinkUseCaseProvider, metadataFetcherProvider, categoryRepositoryProvider, collectionRepositoryProvider, workSchedulerProvider);
  }

  public static SaveLinkViewModel newInstance(SaveLinkUseCase saveLinkUseCase,
      MetadataFetcher metadataFetcher, CategoryRepository categoryRepository,
      CollectionRepository collectionRepository, WorkScheduler workScheduler) {
    return new SaveLinkViewModel(saveLinkUseCase, metadataFetcher, categoryRepository, collectionRepository, workScheduler);
  }
}
