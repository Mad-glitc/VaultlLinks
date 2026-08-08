package com.vaultlinks.app.data.metadata;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class MetadataFetcher_Factory implements Factory<MetadataFetcher> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public MetadataFetcher_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public MetadataFetcher get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static MetadataFetcher_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new MetadataFetcher_Factory(okHttpClientProvider);
  }

  public static MetadataFetcher newInstance(OkHttpClient okHttpClient) {
    return new MetadataFetcher(okHttpClient);
  }
}
