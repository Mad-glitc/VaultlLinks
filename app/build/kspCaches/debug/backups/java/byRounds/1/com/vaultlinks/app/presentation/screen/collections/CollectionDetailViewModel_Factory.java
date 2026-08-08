package com.vaultlinks.app.presentation.screen.collections;

import androidx.lifecycle.SavedStateHandle;
import com.vaultlinks.app.domain.repository.CollectionRepository;
import com.vaultlinks.app.domain.repository.LinkRepository;
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase;
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
public final class CollectionDetailViewModel_Factory implements Factory<CollectionDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<LinkRepository> linkRepositoryProvider;

  private final Provider<CollectionRepository> collectionRepositoryProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  public CollectionDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LinkRepository> linkRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.linkRepositoryProvider = linkRepositoryProvider;
    this.collectionRepositoryProvider = collectionRepositoryProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
  }

  @Override
  public CollectionDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), linkRepositoryProvider.get(), collectionRepositoryProvider.get(), toggleFavoriteUseCaseProvider.get());
  }

  public static CollectionDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LinkRepository> linkRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new CollectionDetailViewModel_Factory(savedStateHandleProvider, linkRepositoryProvider, collectionRepositoryProvider, toggleFavoriteUseCaseProvider);
  }

  public static CollectionDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      LinkRepository linkRepository, CollectionRepository collectionRepository,
      ToggleFavoriteUseCase toggleFavoriteUseCase) {
    return new CollectionDetailViewModel(savedStateHandle, linkRepository, collectionRepository, toggleFavoriteUseCase);
  }
}
