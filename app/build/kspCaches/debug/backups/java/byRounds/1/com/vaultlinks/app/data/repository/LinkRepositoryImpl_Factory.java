package com.vaultlinks.app.data.repository;

import com.vaultlinks.app.data.local.dao.CategoryDao;
import com.vaultlinks.app.data.local.dao.CollectionDao;
import com.vaultlinks.app.data.local.dao.LinkDao;
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
public final class LinkRepositoryImpl_Factory implements Factory<LinkRepositoryImpl> {
  private final Provider<LinkDao> linkDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<CollectionDao> collectionDaoProvider;

  public LinkRepositoryImpl_Factory(Provider<LinkDao> linkDaoProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<CollectionDao> collectionDaoProvider) {
    this.linkDaoProvider = linkDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.collectionDaoProvider = collectionDaoProvider;
  }

  @Override
  public LinkRepositoryImpl get() {
    return newInstance(linkDaoProvider.get(), categoryDaoProvider.get(), collectionDaoProvider.get());
  }

  public static LinkRepositoryImpl_Factory create(Provider<LinkDao> linkDaoProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<CollectionDao> collectionDaoProvider) {
    return new LinkRepositoryImpl_Factory(linkDaoProvider, categoryDaoProvider, collectionDaoProvider);
  }

  public static LinkRepositoryImpl newInstance(LinkDao linkDao, CategoryDao categoryDao,
      CollectionDao collectionDao) {
    return new LinkRepositoryImpl(linkDao, categoryDao, collectionDao);
  }
}
