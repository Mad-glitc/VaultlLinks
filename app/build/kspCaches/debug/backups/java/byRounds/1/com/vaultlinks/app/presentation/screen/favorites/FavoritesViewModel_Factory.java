package com.vaultlinks.app.presentation.screen.favorites;

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
public final class FavoritesViewModel_Factory implements Factory<FavoritesViewModel> {
  private final Provider<LinkRepository> linkRepositoryProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  public FavoritesViewModel_Factory(Provider<LinkRepository> linkRepositoryProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    this.linkRepositoryProvider = linkRepositoryProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
  }

  @Override
  public FavoritesViewModel get() {
    return newInstance(linkRepositoryProvider.get(), toggleFavoriteUseCaseProvider.get());
  }

  public static FavoritesViewModel_Factory create(Provider<LinkRepository> linkRepositoryProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new FavoritesViewModel_Factory(linkRepositoryProvider, toggleFavoriteUseCaseProvider);
  }

  public static FavoritesViewModel newInstance(LinkRepository linkRepository,
      ToggleFavoriteUseCase toggleFavoriteUseCase) {
    return new FavoritesViewModel(linkRepository, toggleFavoriteUseCase);
  }
}
