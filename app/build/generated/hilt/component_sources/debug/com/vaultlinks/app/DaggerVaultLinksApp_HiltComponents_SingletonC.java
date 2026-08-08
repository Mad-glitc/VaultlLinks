package com.vaultlinks.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.vaultlinks.app.data.backup.BackupManager;
import com.vaultlinks.app.data.local.VaultDatabase;
import com.vaultlinks.app.data.local.dao.CategoryDao;
import com.vaultlinks.app.data.local.dao.CollectionDao;
import com.vaultlinks.app.data.local.dao.LinkDao;
import com.vaultlinks.app.data.local.dao.LinkNoteDao;
import com.vaultlinks.app.data.local.dao.PasswordDao;
import com.vaultlinks.app.data.metadata.MetadataFetcher;
import com.vaultlinks.app.data.repository.CategoryRepositoryImpl;
import com.vaultlinks.app.data.repository.CollectionRepositoryImpl;
import com.vaultlinks.app.data.repository.LinkNoteRepositoryImpl;
import com.vaultlinks.app.data.repository.LinkRepositoryImpl;
import com.vaultlinks.app.data.repository.PasswordRepositoryImpl;
import com.vaultlinks.app.datastore.PreferencesManager;
import com.vaultlinks.app.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.vaultlinks.app.di.DatabaseModule_ProvideCollectionDaoFactory;
import com.vaultlinks.app.di.DatabaseModule_ProvideLinkDaoFactory;
import com.vaultlinks.app.di.DatabaseModule_ProvideLinkNoteDaoFactory;
import com.vaultlinks.app.di.DatabaseModule_ProvidePasswordDaoFactory;
import com.vaultlinks.app.di.DatabaseModule_ProvideVaultDatabaseFactory;
import com.vaultlinks.app.di.NetworkModule_ProvideOkHttpClientFactory;
import com.vaultlinks.app.domain.repository.LinkRepository;
import com.vaultlinks.app.domain.usecase.DeleteLinkUseCase;
import com.vaultlinks.app.domain.usecase.GetStatsUseCase;
import com.vaultlinks.app.domain.usecase.MarkOpenedUseCase;
import com.vaultlinks.app.domain.usecase.SaveLinkUseCase;
import com.vaultlinks.app.domain.usecase.SearchLinksUseCase;
import com.vaultlinks.app.domain.usecase.ToggleArchivedUseCase;
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase;
import com.vaultlinks.app.domain.usecase.ToggleReadLaterUseCase;
import com.vaultlinks.app.domain.usecase.UpdateLinkUseCase;
import com.vaultlinks.app.presentation.screen.collections.CollectionDetailViewModel;
import com.vaultlinks.app.presentation.screen.collections.CollectionDetailViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.collections.CollectionsViewModel;
import com.vaultlinks.app.presentation.screen.collections.CollectionsViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.favorites.FavoritesViewModel;
import com.vaultlinks.app.presentation.screen.favorites.FavoritesViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.home.HomeViewModel;
import com.vaultlinks.app.presentation.screen.home.HomeViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailViewModel;
import com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.onboarding.OnboardingViewModel;
import com.vaultlinks.app.presentation.screen.onboarding.OnboardingViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.passwords.PasswordsViewModel;
import com.vaultlinks.app.presentation.screen.passwords.PasswordsViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.pinlock.PinUnlockViewModel;
import com.vaultlinks.app.presentation.screen.pinlock.PinUnlockViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.savelink.SaveLinkViewModel;
import com.vaultlinks.app.presentation.screen.savelink.SaveLinkViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.search.SearchViewModel;
import com.vaultlinks.app.presentation.screen.search.SearchViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.settings.SettingsViewModel;
import com.vaultlinks.app.presentation.screen.settings.SettingsViewModel_HiltModules;
import com.vaultlinks.app.presentation.screen.splash.SplashViewModel;
import com.vaultlinks.app.presentation.screen.splash.SplashViewModel_HiltModules;
import com.vaultlinks.app.security.DatabaseKeyProvider;
import com.vaultlinks.app.security.LockManager;
import com.vaultlinks.app.share.ShareReceiverActivity;
import com.vaultlinks.app.worker.MetadataFetchWorker;
import com.vaultlinks.app.worker.MetadataFetchWorker_AssistedFactory;
import com.vaultlinks.app.worker.ReminderWorker;
import com.vaultlinks.app.worker.ReminderWorker_AssistedFactory;
import com.vaultlinks.app.worker.WorkScheduler;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class DaggerVaultLinksApp_HiltComponents_SingletonC {
  private DaggerVaultLinksApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public VaultLinksApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements VaultLinksApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements VaultLinksApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements VaultLinksApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements VaultLinksApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements VaultLinksApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements VaultLinksApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements VaultLinksApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public VaultLinksApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends VaultLinksApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends VaultLinksApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends VaultLinksApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends VaultLinksApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public void injectShareReceiverActivity(ShareReceiverActivity shareReceiverActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(12).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel, CollectionDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel, CollectionsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel, FavoritesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel, LinkDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel, PasswordsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel, PinUnlockViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel, SaveLinkViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_search_SearchViewModel, SearchViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPreferencesManager(instance, singletonCImpl.preferencesManagerProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_vaultlinks_app_presentation_screen_home_HomeViewModel = "com.vaultlinks.app.presentation.screen.home.HomeViewModel";

      static String com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel = "com.vaultlinks.app.presentation.screen.favorites.FavoritesViewModel";

      static String com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel = "com.vaultlinks.app.presentation.screen.pinlock.PinUnlockViewModel";

      static String com_vaultlinks_app_presentation_screen_settings_SettingsViewModel = "com.vaultlinks.app.presentation.screen.settings.SettingsViewModel";

      static String com_vaultlinks_app_presentation_screen_splash_SplashViewModel = "com.vaultlinks.app.presentation.screen.splash.SplashViewModel";

      static String com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel = "com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailViewModel";

      static String com_vaultlinks_app_presentation_screen_search_SearchViewModel = "com.vaultlinks.app.presentation.screen.search.SearchViewModel";

      static String com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel = "com.vaultlinks.app.presentation.screen.savelink.SaveLinkViewModel";

      static String com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel = "com.vaultlinks.app.presentation.screen.collections.CollectionsViewModel";

      static String com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel = "com.vaultlinks.app.presentation.screen.collections.CollectionDetailViewModel";

      static String com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel = "com.vaultlinks.app.presentation.screen.passwords.PasswordsViewModel";

      static String com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel = "com.vaultlinks.app.presentation.screen.onboarding.OnboardingViewModel";

      @KeepFieldType
      HomeViewModel com_vaultlinks_app_presentation_screen_home_HomeViewModel2;

      @KeepFieldType
      FavoritesViewModel com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel2;

      @KeepFieldType
      PinUnlockViewModel com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel2;

      @KeepFieldType
      SettingsViewModel com_vaultlinks_app_presentation_screen_settings_SettingsViewModel2;

      @KeepFieldType
      SplashViewModel com_vaultlinks_app_presentation_screen_splash_SplashViewModel2;

      @KeepFieldType
      LinkDetailViewModel com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel2;

      @KeepFieldType
      SearchViewModel com_vaultlinks_app_presentation_screen_search_SearchViewModel2;

      @KeepFieldType
      SaveLinkViewModel com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel2;

      @KeepFieldType
      CollectionsViewModel com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel2;

      @KeepFieldType
      CollectionDetailViewModel com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel2;

      @KeepFieldType
      PasswordsViewModel com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel2;

      @KeepFieldType
      OnboardingViewModel com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel2;
    }
  }

  private static final class ViewModelCImpl extends VaultLinksApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CollectionDetailViewModel> collectionDetailViewModelProvider;

    private Provider<CollectionsViewModel> collectionsViewModelProvider;

    private Provider<FavoritesViewModel> favoritesViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LinkDetailViewModel> linkDetailViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<PasswordsViewModel> passwordsViewModelProvider;

    private Provider<PinUnlockViewModel> pinUnlockViewModelProvider;

    private Provider<SaveLinkViewModel> saveLinkViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private ToggleFavoriteUseCase toggleFavoriteUseCase() {
      return new ToggleFavoriteUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private GetStatsUseCase getStatsUseCase() {
      return new GetStatsUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private MarkOpenedUseCase markOpenedUseCase() {
      return new MarkOpenedUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private ToggleReadLaterUseCase toggleReadLaterUseCase() {
      return new ToggleReadLaterUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private ToggleArchivedUseCase toggleArchivedUseCase() {
      return new ToggleArchivedUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private DeleteLinkUseCase deleteLinkUseCase() {
      return new DeleteLinkUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private UpdateLinkUseCase updateLinkUseCase() {
      return new UpdateLinkUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    private SaveLinkUseCase saveLinkUseCase() {
      return new SaveLinkUseCase(singletonCImpl.linkRepositoryImplProvider.get(), singletonCImpl.metadataFetcherProvider.get());
    }

    private SearchLinksUseCase searchLinksUseCase() {
      return new SearchLinksUseCase(singletonCImpl.linkRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.collectionDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.collectionsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.favoritesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.linkDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.passwordsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.pinUnlockViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.saveLinkViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(12).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel, ((Provider) collectionDetailViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel, ((Provider) collectionsViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel, ((Provider) favoritesViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel, ((Provider) linkDetailViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel, ((Provider) passwordsViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel, ((Provider) pinUnlockViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel, ((Provider) saveLinkViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_search_SearchViewModel, ((Provider) searchViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_vaultlinks_app_presentation_screen_splash_SplashViewModel, ((Provider) splashViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel = "com.vaultlinks.app.presentation.screen.collections.CollectionsViewModel";

      static String com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel = "com.vaultlinks.app.presentation.screen.pinlock.PinUnlockViewModel";

      static String com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel = "com.vaultlinks.app.presentation.screen.savelink.SaveLinkViewModel";

      static String com_vaultlinks_app_presentation_screen_splash_SplashViewModel = "com.vaultlinks.app.presentation.screen.splash.SplashViewModel";

      static String com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel = "com.vaultlinks.app.presentation.screen.favorites.FavoritesViewModel";

      static String com_vaultlinks_app_presentation_screen_search_SearchViewModel = "com.vaultlinks.app.presentation.screen.search.SearchViewModel";

      static String com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel = "com.vaultlinks.app.presentation.screen.passwords.PasswordsViewModel";

      static String com_vaultlinks_app_presentation_screen_settings_SettingsViewModel = "com.vaultlinks.app.presentation.screen.settings.SettingsViewModel";

      static String com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel = "com.vaultlinks.app.presentation.screen.collections.CollectionDetailViewModel";

      static String com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel = "com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailViewModel";

      static String com_vaultlinks_app_presentation_screen_home_HomeViewModel = "com.vaultlinks.app.presentation.screen.home.HomeViewModel";

      static String com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel = "com.vaultlinks.app.presentation.screen.onboarding.OnboardingViewModel";

      @KeepFieldType
      CollectionsViewModel com_vaultlinks_app_presentation_screen_collections_CollectionsViewModel2;

      @KeepFieldType
      PinUnlockViewModel com_vaultlinks_app_presentation_screen_pinlock_PinUnlockViewModel2;

      @KeepFieldType
      SaveLinkViewModel com_vaultlinks_app_presentation_screen_savelink_SaveLinkViewModel2;

      @KeepFieldType
      SplashViewModel com_vaultlinks_app_presentation_screen_splash_SplashViewModel2;

      @KeepFieldType
      FavoritesViewModel com_vaultlinks_app_presentation_screen_favorites_FavoritesViewModel2;

      @KeepFieldType
      SearchViewModel com_vaultlinks_app_presentation_screen_search_SearchViewModel2;

      @KeepFieldType
      PasswordsViewModel com_vaultlinks_app_presentation_screen_passwords_PasswordsViewModel2;

      @KeepFieldType
      SettingsViewModel com_vaultlinks_app_presentation_screen_settings_SettingsViewModel2;

      @KeepFieldType
      CollectionDetailViewModel com_vaultlinks_app_presentation_screen_collections_CollectionDetailViewModel2;

      @KeepFieldType
      LinkDetailViewModel com_vaultlinks_app_presentation_screen_linkdetail_LinkDetailViewModel2;

      @KeepFieldType
      HomeViewModel com_vaultlinks_app_presentation_screen_home_HomeViewModel2;

      @KeepFieldType
      OnboardingViewModel com_vaultlinks_app_presentation_screen_onboarding_OnboardingViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.vaultlinks.app.presentation.screen.collections.CollectionDetailViewModel 
          return (T) new CollectionDetailViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.linkRepositoryImplProvider.get(), singletonCImpl.collectionRepositoryImplProvider.get(), viewModelCImpl.toggleFavoriteUseCase());

          case 1: // com.vaultlinks.app.presentation.screen.collections.CollectionsViewModel 
          return (T) new CollectionsViewModel(singletonCImpl.collectionRepositoryImplProvider.get());

          case 2: // com.vaultlinks.app.presentation.screen.favorites.FavoritesViewModel 
          return (T) new FavoritesViewModel(singletonCImpl.linkRepositoryImplProvider.get(), viewModelCImpl.toggleFavoriteUseCase());

          case 3: // com.vaultlinks.app.presentation.screen.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.linkRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get(), singletonCImpl.collectionRepositoryImplProvider.get(), viewModelCImpl.getStatsUseCase(), viewModelCImpl.toggleFavoriteUseCase());

          case 4: // com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailViewModel 
          return (T) new LinkDetailViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.linkRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get(), viewModelCImpl.markOpenedUseCase(), viewModelCImpl.toggleFavoriteUseCase(), viewModelCImpl.toggleReadLaterUseCase(), viewModelCImpl.toggleArchivedUseCase(), viewModelCImpl.deleteLinkUseCase(), viewModelCImpl.updateLinkUseCase(), singletonCImpl.linkNoteRepositoryImplProvider.get());

          case 5: // com.vaultlinks.app.presentation.screen.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.preferencesManagerProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get());

          case 6: // com.vaultlinks.app.presentation.screen.passwords.PasswordsViewModel 
          return (T) new PasswordsViewModel(singletonCImpl.passwordRepositoryImplProvider.get(), singletonCImpl.preferencesManagerProvider.get());

          case 7: // com.vaultlinks.app.presentation.screen.pinlock.PinUnlockViewModel 
          return (T) new PinUnlockViewModel(singletonCImpl.lockManagerProvider.get(), singletonCImpl.preferencesManagerProvider.get());

          case 8: // com.vaultlinks.app.presentation.screen.savelink.SaveLinkViewModel 
          return (T) new SaveLinkViewModel(viewModelCImpl.saveLinkUseCase(), singletonCImpl.metadataFetcherProvider.get(), singletonCImpl.categoryRepositoryImplProvider.get(), singletonCImpl.collectionRepositoryImplProvider.get(), singletonCImpl.workSchedulerProvider.get());

          case 9: // com.vaultlinks.app.presentation.screen.search.SearchViewModel 
          return (T) new SearchViewModel(viewModelCImpl.searchLinksUseCase(), viewModelCImpl.toggleFavoriteUseCase());

          case 10: // com.vaultlinks.app.presentation.screen.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.preferencesManagerProvider.get(), singletonCImpl.lockManagerProvider.get(), singletonCImpl.backupManagerProvider.get(), singletonCImpl.workSchedulerProvider.get());

          case 11: // com.vaultlinks.app.presentation.screen.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.preferencesManagerProvider.get(), singletonCImpl.lockManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends VaultLinksApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends VaultLinksApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends VaultLinksApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<DatabaseKeyProvider> databaseKeyProvider;

    private Provider<VaultDatabase> provideVaultDatabaseProvider;

    private Provider<LinkRepositoryImpl> linkRepositoryImplProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<MetadataFetcher> metadataFetcherProvider;

    private Provider<MetadataFetchWorker_AssistedFactory> metadataFetchWorker_AssistedFactoryProvider;

    private Provider<ReminderWorker_AssistedFactory> reminderWorker_AssistedFactoryProvider;

    private Provider<PreferencesManager> preferencesManagerProvider;

    private Provider<LockManager> lockManagerProvider;

    private Provider<CollectionRepositoryImpl> collectionRepositoryImplProvider;

    private Provider<CategoryRepositoryImpl> categoryRepositoryImplProvider;

    private Provider<LinkNoteRepositoryImpl> linkNoteRepositoryImplProvider;

    private Provider<PasswordRepositoryImpl> passwordRepositoryImplProvider;

    private Provider<WorkScheduler> workSchedulerProvider;

    private Provider<BackupManager> backupManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private LinkDao linkDao() {
      return DatabaseModule_ProvideLinkDaoFactory.provideLinkDao(provideVaultDatabaseProvider.get());
    }

    private CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideVaultDatabaseProvider.get());
    }

    private CollectionDao collectionDao() {
      return DatabaseModule_ProvideCollectionDaoFactory.provideCollectionDao(provideVaultDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(2).put("com.vaultlinks.app.worker.MetadataFetchWorker", ((Provider) metadataFetchWorker_AssistedFactoryProvider)).put("com.vaultlinks.app.worker.ReminderWorker", ((Provider) reminderWorker_AssistedFactoryProvider)).build();
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private LinkNoteDao linkNoteDao() {
      return DatabaseModule_ProvideLinkNoteDaoFactory.provideLinkNoteDao(provideVaultDatabaseProvider.get());
    }

    private PasswordDao passwordDao() {
      return DatabaseModule_ProvidePasswordDaoFactory.providePasswordDao(provideVaultDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.databaseKeyProvider = DoubleCheck.provider(new SwitchingProvider<DatabaseKeyProvider>(singletonCImpl, 3));
      this.provideVaultDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<VaultDatabase>(singletonCImpl, 2));
      this.linkRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<LinkRepositoryImpl>(singletonCImpl, 1));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 5));
      this.metadataFetcherProvider = DoubleCheck.provider(new SwitchingProvider<MetadataFetcher>(singletonCImpl, 4));
      this.metadataFetchWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<MetadataFetchWorker_AssistedFactory>(singletonCImpl, 0));
      this.reminderWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ReminderWorker_AssistedFactory>(singletonCImpl, 6));
      this.preferencesManagerProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesManager>(singletonCImpl, 8));
      this.lockManagerProvider = DoubleCheck.provider(new SwitchingProvider<LockManager>(singletonCImpl, 7));
      this.collectionRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CollectionRepositoryImpl>(singletonCImpl, 9));
      this.categoryRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CategoryRepositoryImpl>(singletonCImpl, 10));
      this.linkNoteRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<LinkNoteRepositoryImpl>(singletonCImpl, 11));
      this.passwordRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<PasswordRepositoryImpl>(singletonCImpl, 12));
      this.workSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<WorkScheduler>(singletonCImpl, 13));
      this.backupManagerProvider = DoubleCheck.provider(new SwitchingProvider<BackupManager>(singletonCImpl, 14));
    }

    @Override
    public void injectVaultLinksApp(VaultLinksApp vaultLinksApp) {
      injectVaultLinksApp2(vaultLinksApp);
    }

    @Override
    public LockManager lockManager() {
      return lockManagerProvider.get();
    }

    @Override
    public LinkRepository linkRepository() {
      return linkRepositoryImplProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private VaultLinksApp injectVaultLinksApp2(VaultLinksApp instance) {
      VaultLinksApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.vaultlinks.app.worker.MetadataFetchWorker_AssistedFactory 
          return (T) new MetadataFetchWorker_AssistedFactory() {
            @Override
            public MetadataFetchWorker create(Context context, WorkerParameters params) {
              return new MetadataFetchWorker(context, params, singletonCImpl.linkRepositoryImplProvider.get(), singletonCImpl.metadataFetcherProvider.get());
            }
          };

          case 1: // com.vaultlinks.app.data.repository.LinkRepositoryImpl 
          return (T) new LinkRepositoryImpl(singletonCImpl.linkDao(), singletonCImpl.categoryDao(), singletonCImpl.collectionDao());

          case 2: // com.vaultlinks.app.data.local.VaultDatabase 
          return (T) DatabaseModule_ProvideVaultDatabaseFactory.provideVaultDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.databaseKeyProvider.get());

          case 3: // com.vaultlinks.app.security.DatabaseKeyProvider 
          return (T) new DatabaseKeyProvider(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.vaultlinks.app.data.metadata.MetadataFetcher 
          return (T) new MetadataFetcher(singletonCImpl.provideOkHttpClientProvider.get());

          case 5: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 6: // com.vaultlinks.app.worker.ReminderWorker_AssistedFactory 
          return (T) new ReminderWorker_AssistedFactory() {
            @Override
            public ReminderWorker create(Context context2, WorkerParameters params2) {
              return new ReminderWorker(context2, params2);
            }
          };

          case 7: // com.vaultlinks.app.security.LockManager 
          return (T) new LockManager(singletonCImpl.preferencesManagerProvider.get());

          case 8: // com.vaultlinks.app.datastore.PreferencesManager 
          return (T) new PreferencesManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.vaultlinks.app.data.repository.CollectionRepositoryImpl 
          return (T) new CollectionRepositoryImpl(singletonCImpl.collectionDao());

          case 10: // com.vaultlinks.app.data.repository.CategoryRepositoryImpl 
          return (T) new CategoryRepositoryImpl(singletonCImpl.categoryDao());

          case 11: // com.vaultlinks.app.data.repository.LinkNoteRepositoryImpl 
          return (T) new LinkNoteRepositoryImpl(singletonCImpl.linkNoteDao());

          case 12: // com.vaultlinks.app.data.repository.PasswordRepositoryImpl 
          return (T) new PasswordRepositoryImpl(singletonCImpl.passwordDao());

          case 13: // com.vaultlinks.app.worker.WorkScheduler 
          return (T) new WorkScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 14: // com.vaultlinks.app.data.backup.BackupManager 
          return (T) new BackupManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.linkRepositoryImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
