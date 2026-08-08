package com.vaultlinks.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.vaultlinks.app.data.local.dao.CategoryDao;
import com.vaultlinks.app.data.local.dao.CategoryDao_Impl;
import com.vaultlinks.app.data.local.dao.CollectionDao;
import com.vaultlinks.app.data.local.dao.CollectionDao_Impl;
import com.vaultlinks.app.data.local.dao.LinkDao;
import com.vaultlinks.app.data.local.dao.LinkDao_Impl;
import com.vaultlinks.app.data.local.dao.LinkNoteDao;
import com.vaultlinks.app.data.local.dao.LinkNoteDao_Impl;
import com.vaultlinks.app.data.local.dao.PasswordDao;
import com.vaultlinks.app.data.local.dao.PasswordDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VaultDatabase_Impl extends VaultDatabase {
  private volatile LinkDao _linkDao;

  private volatile CategoryDao _categoryDao;

  private volatile CollectionDao _collectionDao;

  private volatile LinkNoteDao _linkNoteDao;

  private volatile PasswordDao _passwordDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `links` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `url` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `notes` TEXT NOT NULL, `domain` TEXT NOT NULL, `previewImageUrl` TEXT, `faviconUrl` TEXT, `platform` TEXT NOT NULL, `categoryId` INTEGER, `collectionId` INTEGER, `tagsCsv` TEXT NOT NULL DEFAULT '', `priority` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `isReadLater` INTEGER NOT NULL, `isArchived` INTEGER NOT NULL, `visitCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `lastOpenedAt` INTEGER, `extrasJson` TEXT NOT NULL DEFAULT '{}', FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`collectionId`) REFERENCES `collections`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_categoryId` ON `links` (`categoryId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_collectionId` ON `links` (`collectionId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_isFavorite` ON `links` (`isFavorite`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_isReadLater` ON `links` (`isReadLater`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_isArchived` ON `links` (`isArchived`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_links_createdAt` ON `links` (`createdAt`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `iconName` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `collections` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `parentCollectionId` INTEGER, `colorHex` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`parentCollectionId`) REFERENCES `collections`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_collections_parentCollectionId` ON `collections` (`parentCollectionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `link_notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `linkId` INTEGER NOT NULL, `text` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`linkId`) REFERENCES `links`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_link_notes_linkId` ON `link_notes` (`linkId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `passwords` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `username` TEXT NOT NULL, `passwordEncrypted` TEXT NOT NULL, `website` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'afdb37fadc478f73dd744f0d7c4c9cbf')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `links`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `collections`");
        db.execSQL("DROP TABLE IF EXISTS `link_notes`");
        db.execSQL("DROP TABLE IF EXISTS `passwords`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLinks = new HashMap<String, TableInfo.Column>(21);
        _columnsLinks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("domain", new TableInfo.Column("domain", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("previewImageUrl", new TableInfo.Column("previewImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("faviconUrl", new TableInfo.Column("faviconUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("platform", new TableInfo.Column("platform", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("collectionId", new TableInfo.Column("collectionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("tagsCsv", new TableInfo.Column("tagsCsv", "TEXT", true, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("isReadLater", new TableInfo.Column("isReadLater", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("visitCount", new TableInfo.Column("visitCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("lastOpenedAt", new TableInfo.Column("lastOpenedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinks.put("extrasJson", new TableInfo.Column("extrasJson", "TEXT", true, 0, "'{}'", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLinks = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysLinks.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        _foreignKeysLinks.add(new TableInfo.ForeignKey("collections", "SET NULL", "NO ACTION", Arrays.asList("collectionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLinks = new HashSet<TableInfo.Index>(6);
        _indicesLinks.add(new TableInfo.Index("index_links_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        _indicesLinks.add(new TableInfo.Index("index_links_collectionId", false, Arrays.asList("collectionId"), Arrays.asList("ASC")));
        _indicesLinks.add(new TableInfo.Index("index_links_isFavorite", false, Arrays.asList("isFavorite"), Arrays.asList("ASC")));
        _indicesLinks.add(new TableInfo.Index("index_links_isReadLater", false, Arrays.asList("isReadLater"), Arrays.asList("ASC")));
        _indicesLinks.add(new TableInfo.Index("index_links_isArchived", false, Arrays.asList("isArchived"), Arrays.asList("ASC")));
        _indicesLinks.add(new TableInfo.Index("index_links_createdAt", false, Arrays.asList("createdAt"), Arrays.asList("ASC")));
        final TableInfo _infoLinks = new TableInfo("links", _columnsLinks, _foreignKeysLinks, _indicesLinks);
        final TableInfo _existingLinks = TableInfo.read(db, "links");
        if (!_infoLinks.equals(_existingLinks)) {
          return new RoomOpenHelper.ValidationResult(false, "links(com.vaultlinks.app.data.local.entity.LinkEntity).\n"
                  + " Expected:\n" + _infoLinks + "\n"
                  + " Found:\n" + _existingLinks);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.vaultlinks.app.data.local.entity.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsCollections = new HashMap<String, TableInfo.Column>(6);
        _columnsCollections.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("parentCollectionId", new TableInfo.Column("parentCollectionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCollections = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCollections.add(new TableInfo.ForeignKey("collections", "CASCADE", "NO ACTION", Arrays.asList("parentCollectionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCollections = new HashSet<TableInfo.Index>(1);
        _indicesCollections.add(new TableInfo.Index("index_collections_parentCollectionId", false, Arrays.asList("parentCollectionId"), Arrays.asList("ASC")));
        final TableInfo _infoCollections = new TableInfo("collections", _columnsCollections, _foreignKeysCollections, _indicesCollections);
        final TableInfo _existingCollections = TableInfo.read(db, "collections");
        if (!_infoCollections.equals(_existingCollections)) {
          return new RoomOpenHelper.ValidationResult(false, "collections(com.vaultlinks.app.data.local.entity.CollectionEntity).\n"
                  + " Expected:\n" + _infoCollections + "\n"
                  + " Found:\n" + _existingCollections);
        }
        final HashMap<String, TableInfo.Column> _columnsLinkNotes = new HashMap<String, TableInfo.Column>(4);
        _columnsLinkNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinkNotes.put("linkId", new TableInfo.Column("linkId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinkNotes.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLinkNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLinkNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLinkNotes.add(new TableInfo.ForeignKey("links", "CASCADE", "NO ACTION", Arrays.asList("linkId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLinkNotes = new HashSet<TableInfo.Index>(1);
        _indicesLinkNotes.add(new TableInfo.Index("index_link_notes_linkId", false, Arrays.asList("linkId"), Arrays.asList("ASC")));
        final TableInfo _infoLinkNotes = new TableInfo("link_notes", _columnsLinkNotes, _foreignKeysLinkNotes, _indicesLinkNotes);
        final TableInfo _existingLinkNotes = TableInfo.read(db, "link_notes");
        if (!_infoLinkNotes.equals(_existingLinkNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "link_notes(com.vaultlinks.app.data.local.entity.LinkNoteEntity).\n"
                  + " Expected:\n" + _infoLinkNotes + "\n"
                  + " Found:\n" + _existingLinkNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsPasswords = new HashMap<String, TableInfo.Column>(8);
        _columnsPasswords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("passwordEncrypted", new TableInfo.Column("passwordEncrypted", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("website", new TableInfo.Column("website", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasswords.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPasswords = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPasswords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPasswords = new TableInfo("passwords", _columnsPasswords, _foreignKeysPasswords, _indicesPasswords);
        final TableInfo _existingPasswords = TableInfo.read(db, "passwords");
        if (!_infoPasswords.equals(_existingPasswords)) {
          return new RoomOpenHelper.ValidationResult(false, "passwords(com.vaultlinks.app.data.local.entity.PasswordEntity).\n"
                  + " Expected:\n" + _infoPasswords + "\n"
                  + " Found:\n" + _existingPasswords);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "afdb37fadc478f73dd744f0d7c4c9cbf", "743c5526eeb6eb68ff9601a8533f7b80");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "links","categories","collections","link_notes","passwords");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `links`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `collections`");
      _db.execSQL("DELETE FROM `link_notes`");
      _db.execSQL("DELETE FROM `passwords`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LinkDao.class, LinkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CollectionDao.class, CollectionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LinkNoteDao.class, LinkNoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PasswordDao.class, PasswordDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LinkDao linkDao() {
    if (_linkDao != null) {
      return _linkDao;
    } else {
      synchronized(this) {
        if(_linkDao == null) {
          _linkDao = new LinkDao_Impl(this);
        }
        return _linkDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public CollectionDao collectionDao() {
    if (_collectionDao != null) {
      return _collectionDao;
    } else {
      synchronized(this) {
        if(_collectionDao == null) {
          _collectionDao = new CollectionDao_Impl(this);
        }
        return _collectionDao;
      }
    }
  }

  @Override
  public LinkNoteDao linkNoteDao() {
    if (_linkNoteDao != null) {
      return _linkNoteDao;
    } else {
      synchronized(this) {
        if(_linkNoteDao == null) {
          _linkNoteDao = new LinkNoteDao_Impl(this);
        }
        return _linkNoteDao;
      }
    }
  }

  @Override
  public PasswordDao passwordDao() {
    if (_passwordDao != null) {
      return _passwordDao;
    } else {
      synchronized(this) {
        if(_passwordDao == null) {
          _passwordDao = new PasswordDao_Impl(this);
        }
        return _passwordDao;
      }
    }
  }
}
