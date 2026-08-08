package com.vaultlinks.app.di;

import com.vaultlinks.app.data.local.VaultDatabase;
import com.vaultlinks.app.data.local.dao.LinkNoteDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideLinkNoteDaoFactory implements Factory<LinkNoteDao> {
  private final Provider<VaultDatabase> dbProvider;

  public DatabaseModule_ProvideLinkNoteDaoFactory(Provider<VaultDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LinkNoteDao get() {
    return provideLinkNoteDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideLinkNoteDaoFactory create(
      Provider<VaultDatabase> dbProvider) {
    return new DatabaseModule_ProvideLinkNoteDaoFactory(dbProvider);
  }

  public static LinkNoteDao provideLinkNoteDao(VaultDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLinkNoteDao(db));
  }
}
