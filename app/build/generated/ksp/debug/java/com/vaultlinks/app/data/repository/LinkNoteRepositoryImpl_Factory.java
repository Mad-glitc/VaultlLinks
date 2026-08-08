package com.vaultlinks.app.data.repository;

import com.vaultlinks.app.data.local.dao.LinkNoteDao;
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
public final class LinkNoteRepositoryImpl_Factory implements Factory<LinkNoteRepositoryImpl> {
  private final Provider<LinkNoteDao> linkNoteDaoProvider;

  public LinkNoteRepositoryImpl_Factory(Provider<LinkNoteDao> linkNoteDaoProvider) {
    this.linkNoteDaoProvider = linkNoteDaoProvider;
  }

  @Override
  public LinkNoteRepositoryImpl get() {
    return newInstance(linkNoteDaoProvider.get());
  }

  public static LinkNoteRepositoryImpl_Factory create(Provider<LinkNoteDao> linkNoteDaoProvider) {
    return new LinkNoteRepositoryImpl_Factory(linkNoteDaoProvider);
  }

  public static LinkNoteRepositoryImpl newInstance(LinkNoteDao linkNoteDao) {
    return new LinkNoteRepositoryImpl(linkNoteDao);
  }
}
