package com.vaultlinks.app.presentation.screen.search;

import com.vaultlinks.app.domain.usecase.SearchLinksUseCase;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<SearchLinksUseCase> searchLinksUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  public SearchViewModel_Factory(Provider<SearchLinksUseCase> searchLinksUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    this.searchLinksUseCaseProvider = searchLinksUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(searchLinksUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get());
  }

  public static SearchViewModel_Factory create(
      Provider<SearchLinksUseCase> searchLinksUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new SearchViewModel_Factory(searchLinksUseCaseProvider, toggleFavoriteUseCaseProvider);
  }

  public static SearchViewModel newInstance(SearchLinksUseCase searchLinksUseCase,
      ToggleFavoriteUseCase toggleFavoriteUseCase) {
    return new SearchViewModel(searchLinksUseCase, toggleFavoriteUseCase);
  }
}
