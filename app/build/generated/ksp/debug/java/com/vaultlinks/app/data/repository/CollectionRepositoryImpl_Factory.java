package com.vaultlinks.app.data.repository;

import com.vaultlinks.app.data.local.dao.CollectionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CollectionRepositoryImpl_Factory implements Factory<CollectionRepositoryImpl> {
  private final Provider<CollectionDao> collectionDaoProvider;

  public CollectionRepositoryImpl_Factory(Provider<CollectionDao> collectionDaoProvider) {
    this.collectionDaoProvider = collectionDaoProvider;
  }

  @Override
  public CollectionRepositoryImpl get() {
    return newInstance(collectionDaoProvider.get());
  }

  public static CollectionRepositoryImpl_Factory create(
      Provider<CollectionDao> collectionDaoProvider) {
    return new CollectionRepositoryImpl_Factory(collectionDaoProvider);
  }

  public static CollectionRepositoryImpl newInstance(CollectionDao collectionDao) {
    return new CollectionRepositoryImpl(collectionDao);
  }
}
