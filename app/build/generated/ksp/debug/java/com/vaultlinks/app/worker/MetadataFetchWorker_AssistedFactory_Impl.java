package com.vaultlinks.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MetadataFetchWorker_AssistedFactory_Impl implements MetadataFetchWorker_AssistedFactory {
  private final MetadataFetchWorker_Factory delegateFactory;

  MetadataFetchWorker_AssistedFactory_Impl(MetadataFetchWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MetadataFetchWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<MetadataFetchWorker_AssistedFactory> create(
      MetadataFetchWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MetadataFetchWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<MetadataFetchWorker_AssistedFactory> createFactoryProvider(
      MetadataFetchWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MetadataFetchWorker_AssistedFactory_Impl(delegateFactory));
  }
}
