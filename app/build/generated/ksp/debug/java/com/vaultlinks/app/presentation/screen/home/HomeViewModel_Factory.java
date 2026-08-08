package com.vaultlinks.app.presentation.screen.home;

import com.vaultlinks.app.domain.repository.CategoryRepository;
import com.vaultlinks.app.domain.repository.CollectionRepository;
import com.vaultlinks.app.domain.repository.LinkRepository;
import com.vaultlinks.app.domain.usecase.GetStatsUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<LinkRepository> linkRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<CollectionRepository> collectionRepositoryProvider;

  private final Provider<GetStatsUseCase> getStatsUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  public HomeViewModel_Factory(Provider<LinkRepository> linkRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<GetStatsUseCase> getStatsUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    this.linkRepositoryProvider = linkRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.collectionRepositoryProvider = collectionRepositoryProvider;
    this.getStatsUseCaseProvider = getStatsUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(linkRepositoryProvider.get(), categoryRepositoryProvider.get(), collectionRepositoryProvider.get(), getStatsUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<LinkRepository> linkRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<CollectionRepository> collectionRepositoryProvider,
      Provider<GetStatsUseCase> getStatsUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new HomeViewModel_Factory(linkRepositoryProvider, categoryRepositoryProvider, collectionRepositoryProvider, getStatsUseCaseProvider, toggleFavoriteUseCaseProvider);
  }

  public static HomeViewModel newInstance(LinkRepository linkRepository,
      CategoryRepository categoryRepository, CollectionRepository collectionRepository,
      GetStatsUseCase getStatsUseCase, ToggleFavoriteUseCase toggleFavoriteUseCase) {
    return new HomeViewModel(linkRepository, categoryRepository, collectionRepository, getStatsUseCase, toggleFavoriteUseCase);
  }
}
