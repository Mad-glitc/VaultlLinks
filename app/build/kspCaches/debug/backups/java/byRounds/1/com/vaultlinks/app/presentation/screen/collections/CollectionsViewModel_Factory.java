package com.vaultlinks.app.presentation.screen.collections;

import com.vaultlinks.app.domain.repository.CollectionRepository;
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
public final class CollectionsViewModel_Factory implements Factory<CollectionsViewModel> {
  private final Provider<CollectionRepository> collectionRepositoryProvider;

  public CollectionsViewModel_Factory(Provider<CollectionRepository> collectionRepositoryProvider) {
    this.collectionRepositoryProvider = collectionRepositoryProvider;
  }

  @Override
  public CollectionsViewModel get() {
    return newInstance(collectionRepositoryProvider.get());
  }

  public static CollectionsViewModel_Factory create(
      Provider<CollectionRepository> collectionRepositoryProvider) {
    return new CollectionsViewModel_Factory(collectionRepositoryProvider);
  }

  public static CollectionsViewModel newInstance(CollectionRepository collectionRepository) {
    return new CollectionsViewModel(collectionRepository);
  }
}
