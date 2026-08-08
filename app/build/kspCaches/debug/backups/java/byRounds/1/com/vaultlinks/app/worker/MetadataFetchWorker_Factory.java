package com.vaultlinks.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.vaultlinks.app.data.metadata.MetadataFetcher;
import com.vaultlinks.app.domain.repository.LinkRepository;
import dagger.internal.DaggerGenerated;
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
public final class MetadataFetchWorker_Factory {
  private final Provider<LinkRepository> linkRepositoryProvider;

  private final Provider<MetadataFetcher> metadataFetcherProvider;

  public MetadataFetchWorker_Factory(Provider<LinkRepository> linkRepositoryProvider,
      Provider<MetadataFetcher> metadataFetcherProvider) {
    this.linkRepositoryProvider = linkRepositoryProvider;
    this.metadataFetcherProvider = metadataFetcherProvider;
  }

  public MetadataFetchWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, linkRepositoryProvider.get(), metadataFetcherProvider.get());
  }

  public static MetadataFetchWorker_Factory create(Provider<LinkRepository> linkRepositoryProvider,
      Provider<MetadataFetcher> metadataFetcherProvider) {
    return new MetadataFetchWorker_Factory(linkRepositoryProvider, metadataFetcherProvider);
  }

  public static MetadataFetchWorker newInstance(Context context, WorkerParameters params,
      LinkRepository linkRepository, MetadataFetcher metadataFetcher) {
    return new MetadataFetchWorker(context, params, linkRepository, metadataFetcher);
  }
}
