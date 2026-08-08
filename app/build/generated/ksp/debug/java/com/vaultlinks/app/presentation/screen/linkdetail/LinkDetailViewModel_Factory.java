package com.vaultlinks.app.presentation.screen.linkdetail;

import androidx.lifecycle.SavedStateHandle;
import com.vaultlinks.app.domain.repository.CategoryRepository;
import com.vaultlinks.app.domain.repository.LinkNoteRepository;
import com.vaultlinks.app.domain.repository.LinkRepository;
import com.vaultlinks.app.domain.usecase.DeleteLinkUseCase;
import com.vaultlinks.app.domain.usecase.MarkOpenedUseCase;
import com.vaultlinks.app.domain.usecase.ToggleArchivedUseCase;
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase;
import com.vaultlinks.app.domain.usecase.ToggleReadLaterUseCase;
import com.vaultlinks.app.domain.usecase.UpdateLinkUseCase;
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
public final class LinkDetailViewModel_Factory implements Factory<LinkDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<LinkRepository> linkRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<MarkOpenedUseCase> markOpenedUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  private final Provider<ToggleReadLaterUseCase> toggleReadLaterUseCaseProvider;

  private final Provider<ToggleArchivedUseCase> toggleArchivedUseCaseProvider;

  private final Provider<DeleteLinkUseCase> deleteLinkUseCaseProvider;

  private final Provider<UpdateLinkUseCase> updateLinkUseCaseProvider;

  private final Provider<LinkNoteRepository> linkNoteRepositoryProvider;

  public LinkDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LinkRepository> linkRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<MarkOpenedUseCase> markOpenedUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<ToggleReadLaterUseCase> toggleReadLaterUseCaseProvider,
      Provider<ToggleArchivedUseCase> toggleArchivedUseCaseProvider,
      Provider<DeleteLinkUseCase> deleteLinkUseCaseProvider,
      Provider<UpdateLinkUseCase> updateLinkUseCaseProvider,
      Provider<LinkNoteRepository> linkNoteRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.linkRepositoryProvider = linkRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.markOpenedUseCaseProvider = markOpenedUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
    this.toggleReadLaterUseCaseProvider = toggleReadLaterUseCaseProvider;
    this.toggleArchivedUseCaseProvider = toggleArchivedUseCaseProvider;
    this.deleteLinkUseCaseProvider = deleteLinkUseCaseProvider;
    this.updateLinkUseCaseProvider = updateLinkUseCaseProvider;
    this.linkNoteRepositoryProvider = linkNoteRepositoryProvider;
  }

  @Override
  public LinkDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), linkRepositoryProvider.get(), categoryRepositoryProvider.get(), markOpenedUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get(), toggleReadLaterUseCaseProvider.get(), toggleArchivedUseCaseProvider.get(), deleteLinkUseCaseProvider.get(), updateLinkUseCaseProvider.get(), linkNoteRepositoryProvider.get());
  }

  public static LinkDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<LinkRepository> linkRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<MarkOpenedUseCase> markOpenedUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<ToggleReadLaterUseCase> toggleReadLaterUseCaseProvider,
      Provider<ToggleArchivedUseCase> toggleArchivedUseCaseProvider,
      Provider<DeleteLinkUseCase> deleteLinkUseCaseProvider,
      Provider<UpdateLinkUseCase> updateLinkUseCaseProvider,
      Provider<LinkNoteRepository> linkNoteRepositoryProvider) {
    return new LinkDetailViewModel_Factory(savedStateHandleProvider, linkRepositoryProvider, categoryRepositoryProvider, markOpenedUseCaseProvider, toggleFavoriteUseCaseProvider, toggleReadLaterUseCaseProvider, toggleArchivedUseCaseProvider, deleteLinkUseCaseProvider, updateLinkUseCaseProvider, linkNoteRepositoryProvider);
  }

  public static LinkDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      LinkRepository linkRepository, CategoryRepository categoryRepository,
      MarkOpenedUseCase markOpenedUseCase, ToggleFavoriteUseCase toggleFavoriteUseCase,
      ToggleReadLaterUseCase toggleReadLaterUseCase, ToggleArchivedUseCase toggleArchivedUseCase,
      DeleteLinkUseCase deleteLinkUseCase, UpdateLinkUseCase updateLinkUseCase,
      LinkNoteRepository linkNoteRepository) {
    return new LinkDetailViewModel(savedStateHandle, linkRepository, categoryRepository, markOpenedUseCase, toggleFavoriteUseCase, toggleReadLaterUseCase, toggleArchivedUseCase, deleteLinkUseCase, updateLinkUseCase, linkNoteRepository);
  }
}
