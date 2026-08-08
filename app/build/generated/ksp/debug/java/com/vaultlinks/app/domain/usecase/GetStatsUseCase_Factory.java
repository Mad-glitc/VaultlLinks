package com.vaultlinks.app.domain.usecase;

import com.vaultlinks.app.domain.repository.LinkRepository;
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
public final class GetStatsUseCase_Factory implements Factory<GetStatsUseCase> {
  private final Provider<LinkRepository> repositoryProvider;

  public GetStatsUseCase_Factory(Provider<LinkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetStatsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetStatsUseCase_Factory create(Provider<LinkRepository> repositoryProvider) {
    return new GetStatsUseCase_Factory(repositoryProvider);
  }

  public static GetStatsUseCase newInstance(LinkRepository repository) {
    return new GetStatsUseCase(repository);
  }
}
