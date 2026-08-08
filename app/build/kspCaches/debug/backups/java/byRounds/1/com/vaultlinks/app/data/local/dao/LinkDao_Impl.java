package com.vaultlinks.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vaultlinks.app.data.local.entity.LinkEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LinkDao_Impl implements LinkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LinkEntity> __insertionAdapterOfLinkEntity;

  private final EntityDeletionOrUpdateAdapter<LinkEntity> __deletionAdapterOfLinkEntity;

  private final EntityDeletionOrUpdateAdapter<LinkEntity> __updateAdapterOfLinkEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  private final SharedSQLiteStatement __preparedStmtOfSetReadLater;

  private final SharedSQLiteStatement __preparedStmtOfSetArchived;

  private final SharedSQLiteStatement __preparedStmtOfMarkOpened;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public LinkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLinkEntity = new EntityInsertionAdapter<LinkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `links` (`id`,`url`,`title`,`description`,`notes`,`domain`,`previewImageUrl`,`faviconUrl`,`platform`,`categoryId`,`collectionId`,`tagsCsv`,`priority`,`isFavorite`,`isReadLater`,`isArchived`,`visitCount`,`createdAt`,`updatedAt`,`lastOpenedAt`,`extrasJson`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LinkEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUrl());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getNotes());
        statement.bindString(6, entity.getDomain());
        if (entity.getPreviewImageUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPreviewImageUrl());
        }
        if (entity.getFaviconUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFaviconUrl());
        }
        statement.bindString(9, entity.getPlatform());
        if (entity.getCategoryId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getCategoryId());
        }
        if (entity.getCollectionId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCollectionId());
        }
        statement.bindString(12, entity.getTagsCsv());
        statement.bindLong(13, entity.getPriority());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.isReadLater() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final int _tmp_2 = entity.isArchived() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
        statement.bindLong(17, entity.getVisitCount());
        statement.bindLong(18, entity.getCreatedAt());
        statement.bindLong(19, entity.getUpdatedAt());
        if (entity.getLastOpenedAt() == null) {
          statement.bindNull(20);
        } else {
          statement.bindLong(20, entity.getLastOpenedAt());
        }
        statement.bindString(21, entity.getExtrasJson());
      }
    };
    this.__deletionAdapterOfLinkEntity = new EntityDeletionOrUpdateAdapter<LinkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `links` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LinkEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLinkEntity = new EntityDeletionOrUpdateAdapter<LinkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `links` SET `id` = ?,`url` = ?,`title` = ?,`description` = ?,`notes` = ?,`domain` = ?,`previewImageUrl` = ?,`faviconUrl` = ?,`platform` = ?,`categoryId` = ?,`collectionId` = ?,`tagsCsv` = ?,`priority` = ?,`isFavorite` = ?,`isReadLater` = ?,`isArchived` = ?,`visitCount` = ?,`createdAt` = ?,`updatedAt` = ?,`lastOpenedAt` = ?,`extrasJson` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LinkEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUrl());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getNotes());
        statement.bindString(6, entity.getDomain());
        if (entity.getPreviewImageUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPreviewImageUrl());
        }
        if (entity.getFaviconUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFaviconUrl());
        }
        statement.bindString(9, entity.getPlatform());
        if (entity.getCategoryId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getCategoryId());
        }
        if (entity.getCollectionId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCollectionId());
        }
        statement.bindString(12, entity.getTagsCsv());
        statement.bindLong(13, entity.getPriority());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.isReadLater() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final int _tmp_2 = entity.isArchived() ? 1 : 0;
        statement.bindLong(16, _tmp_2);
        statement.bindLong(17, entity.getVisitCount());
        statement.bindLong(18, entity.getCreatedAt());
        statement.bindLong(19, entity.getUpdatedAt());
        if (entity.getLastOpenedAt() == null) {
          statement.bindNull(20);
        } else {
          statement.bindLong(20, entity.getLastOpenedAt());
        }
        statement.bindString(21, entity.getExtrasJson());
        statement.bindLong(22, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM links WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE links SET isFavorite = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetReadLater = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE links SET isReadLater = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetArchived = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE links SET isArchived = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkOpened = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE links SET visitCount = visitCount + 1, lastOpenedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM links";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final LinkEntity link, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLinkEntity.insertAndReturnId(link);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<LinkEntity> links,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLinkEntity.insert(links);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final LinkEntity link, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLinkEntity.handle(link);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final LinkEntity link, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLinkEntity.handle(link);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setFavorite(final long id, final boolean isFavorite, final long now,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setReadLater(final long id, final boolean isReadLater, final long now,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetReadLater.acquire();
        int _argIndex = 1;
        final int _tmp = isReadLater ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetReadLater.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setArchived(final long id, final boolean isArchived, final long now,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetArchived.acquire();
        int _argIndex = 1;
        final int _tmp = isArchived ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetArchived.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markOpened(final long id, final long now,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkOpened.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkOpened.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super LinkEntity> $completion) {
    final String _sql = "SELECT * FROM links WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LinkEntity>() {
      @Override
      @Nullable
      public LinkEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final LinkEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _result = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<LinkEntity> observeById(final long id) {
    final String _sql = "SELECT * FROM links WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<LinkEntity>() {
      @Override
      @Nullable
      public LinkEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final LinkEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _result = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observeRecent(final int limit) {
    final String _sql = "SELECT * FROM links WHERE isArchived = 0 ORDER BY createdAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observePinned(final int limit) {
    final String _sql = "SELECT * FROM links WHERE isFavorite = 1 AND isArchived = 0 ORDER BY updatedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observeRecentlyViewed(final int limit) {
    final String _sql = "SELECT * FROM links WHERE lastOpenedAt IS NOT NULL AND isArchived = 0 ORDER BY lastOpenedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public PagingSource<Integer, LinkEntity> pagingSource(final Long categoryId,
      final Long collectionId, final String filter, final String sort) {
    final String _sql = "\n"
            + "        SELECT * FROM links\n"
            + "        WHERE isArchived = 0\n"
            + "        AND (? IS NULL OR categoryId = ?)\n"
            + "        AND (? IS NULL OR collectionId = ?)\n"
            + "        AND (\n"
            + "            ? = 'ALL'\n"
            + "            OR (? = 'FAVORITES' AND isFavorite = 1)\n"
            + "            OR (? = 'READ_LATER' AND isReadLater = 1)\n"
            + "            OR (? = 'ARCHIVED' AND isArchived = 1)\n"
            + "            OR (? = 'UNREAD' AND visitCount = 0)\n"
            + "        )\n"
            + "        ORDER BY\n"
            + "        CASE WHEN ? = 'NEWEST' THEN createdAt END DESC,\n"
            + "        CASE WHEN ? = 'OLDEST' THEN createdAt END ASC,\n"
            + "        CASE WHEN ? = 'RECENTLY_OPENED' THEN lastOpenedAt END DESC,\n"
            + "        CASE WHEN ? = 'MOST_OPENED' THEN visitCount END DESC,\n"
            + "        CASE WHEN ? = 'PRIORITY' THEN priority END DESC,\n"
            + "        CASE WHEN ? = 'ALPHABETICAL' THEN title END ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 15);
    int _argIndex = 1;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, categoryId);
    }
    _argIndex = 2;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, categoryId);
    }
    _argIndex = 3;
    if (collectionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, collectionId);
    }
    _argIndex = 4;
    if (collectionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, collectionId);
    }
    _argIndex = 5;
    _statement.bindString(_argIndex, filter);
    _argIndex = 6;
    _statement.bindString(_argIndex, filter);
    _argIndex = 7;
    _statement.bindString(_argIndex, filter);
    _argIndex = 8;
    _statement.bindString(_argIndex, filter);
    _argIndex = 9;
    _statement.bindString(_argIndex, filter);
    _argIndex = 10;
    _statement.bindString(_argIndex, sort);
    _argIndex = 11;
    _statement.bindString(_argIndex, sort);
    _argIndex = 12;
    _statement.bindString(_argIndex, sort);
    _argIndex = 13;
    _statement.bindString(_argIndex, sort);
    _argIndex = 14;
    _statement.bindString(_argIndex, sort);
    _argIndex = 15;
    _statement.bindString(_argIndex, sort);
    return new LimitOffsetPagingSource<LinkEntity>(_statement, __db, "links") {
      @Override
      @NonNull
      protected List<LinkEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(cursor, "url");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(cursor, "title");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
        final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(cursor, "notes");
        final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(cursor, "domain");
        final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(cursor, "previewImageUrl");
        final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(cursor, "faviconUrl");
        final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(cursor, "platform");
        final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(cursor, "categoryId");
        final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(cursor, "collectionId");
        final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(cursor, "tagsCsv");
        final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(cursor, "priority");
        final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(cursor, "isFavorite");
        final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(cursor, "isReadLater");
        final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(cursor, "isArchived");
        final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(cursor, "visitCount");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(cursor, "createdAt");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(cursor, "updatedAt");
        final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(cursor, "lastOpenedAt");
        final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(cursor, "extrasJson");
        final List<LinkEntity> _result = new ArrayList<LinkEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final LinkEntity _item;
          final long _tmpId;
          _tmpId = cursor.getLong(_cursorIndexOfId);
          final String _tmpUrl;
          _tmpUrl = cursor.getString(_cursorIndexOfUrl);
          final String _tmpTitle;
          _tmpTitle = cursor.getString(_cursorIndexOfTitle);
          final String _tmpDescription;
          _tmpDescription = cursor.getString(_cursorIndexOfDescription);
          final String _tmpNotes;
          _tmpNotes = cursor.getString(_cursorIndexOfNotes);
          final String _tmpDomain;
          _tmpDomain = cursor.getString(_cursorIndexOfDomain);
          final String _tmpPreviewImageUrl;
          if (cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
            _tmpPreviewImageUrl = null;
          } else {
            _tmpPreviewImageUrl = cursor.getString(_cursorIndexOfPreviewImageUrl);
          }
          final String _tmpFaviconUrl;
          if (cursor.isNull(_cursorIndexOfFaviconUrl)) {
            _tmpFaviconUrl = null;
          } else {
            _tmpFaviconUrl = cursor.getString(_cursorIndexOfFaviconUrl);
          }
          final String _tmpPlatform;
          _tmpPlatform = cursor.getString(_cursorIndexOfPlatform);
          final Long _tmpCategoryId;
          if (cursor.isNull(_cursorIndexOfCategoryId)) {
            _tmpCategoryId = null;
          } else {
            _tmpCategoryId = cursor.getLong(_cursorIndexOfCategoryId);
          }
          final Long _tmpCollectionId;
          if (cursor.isNull(_cursorIndexOfCollectionId)) {
            _tmpCollectionId = null;
          } else {
            _tmpCollectionId = cursor.getLong(_cursorIndexOfCollectionId);
          }
          final String _tmpTagsCsv;
          _tmpTagsCsv = cursor.getString(_cursorIndexOfTagsCsv);
          final int _tmpPriority;
          _tmpPriority = cursor.getInt(_cursorIndexOfPriority);
          final boolean _tmpIsFavorite;
          final int _tmp;
          _tmp = cursor.getInt(_cursorIndexOfIsFavorite);
          _tmpIsFavorite = _tmp != 0;
          final boolean _tmpIsReadLater;
          final int _tmp_1;
          _tmp_1 = cursor.getInt(_cursorIndexOfIsReadLater);
          _tmpIsReadLater = _tmp_1 != 0;
          final boolean _tmpIsArchived;
          final int _tmp_2;
          _tmp_2 = cursor.getInt(_cursorIndexOfIsArchived);
          _tmpIsArchived = _tmp_2 != 0;
          final int _tmpVisitCount;
          _tmpVisitCount = cursor.getInt(_cursorIndexOfVisitCount);
          final long _tmpCreatedAt;
          _tmpCreatedAt = cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = cursor.getLong(_cursorIndexOfUpdatedAt);
          final Long _tmpLastOpenedAt;
          if (cursor.isNull(_cursorIndexOfLastOpenedAt)) {
            _tmpLastOpenedAt = null;
          } else {
            _tmpLastOpenedAt = cursor.getLong(_cursorIndexOfLastOpenedAt);
          }
          final String _tmpExtrasJson;
          _tmpExtrasJson = cursor.getString(_cursorIndexOfExtrasJson);
          _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public Flow<List<LinkEntity>> search(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM links\n"
            + "        WHERE isArchived = 0 AND (\n"
            + "            title LIKE '%' || ? || '%' OR\n"
            + "            description LIKE '%' || ? || '%' OR\n"
            + "            notes LIKE '%' || ? || '%' OR\n"
            + "            url LIKE '%' || ? || '%' OR\n"
            + "            domain LIKE '%' || ? || '%' OR\n"
            + "            tagsCsv LIKE '%' || ? || '%'\n"
            + "        )\n"
            + "        ORDER BY createdAt DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 6);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    _argIndex = 5;
    _statement.bindString(_argIndex, query);
    _argIndex = 6;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observeFavorites() {
    final String _sql = "SELECT * FROM links WHERE isFavorite = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observeReadLater() {
    final String _sql = "SELECT * FROM links WHERE isReadLater = 1 AND isArchived = 0 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<LinkEntity>> observeArchived() {
    final String _sql = "SELECT * FROM links WHERE isArchived = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> observeTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM links WHERE isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"links"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object countSince(final long startOfDay, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM links WHERE createdAt >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startOfDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countFavorites(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM links WHERE isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countReadLater(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM links WHERE isReadLater = 1 AND isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllForExport(final Continuation<? super List<LinkEntity>> $completion) {
    final String _sql = "SELECT * FROM links ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<LinkEntity>>() {
      @Override
      @NonNull
      public List<LinkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfDomain = CursorUtil.getColumnIndexOrThrow(_cursor, "domain");
          final int _cursorIndexOfPreviewImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImageUrl");
          final int _cursorIndexOfFaviconUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "faviconUrl");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collectionId");
          final int _cursorIndexOfTagsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "tagsCsv");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsReadLater = CursorUtil.getColumnIndexOrThrow(_cursor, "isReadLater");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastOpenedAt");
          final int _cursorIndexOfExtrasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "extrasJson");
          final List<LinkEntity> _result = new ArrayList<LinkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LinkEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpDomain;
            _tmpDomain = _cursor.getString(_cursorIndexOfDomain);
            final String _tmpPreviewImageUrl;
            if (_cursor.isNull(_cursorIndexOfPreviewImageUrl)) {
              _tmpPreviewImageUrl = null;
            } else {
              _tmpPreviewImageUrl = _cursor.getString(_cursorIndexOfPreviewImageUrl);
            }
            final String _tmpFaviconUrl;
            if (_cursor.isNull(_cursorIndexOfFaviconUrl)) {
              _tmpFaviconUrl = null;
            } else {
              _tmpFaviconUrl = _cursor.getString(_cursorIndexOfFaviconUrl);
            }
            final String _tmpPlatform;
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final Long _tmpCollectionId;
            if (_cursor.isNull(_cursorIndexOfCollectionId)) {
              _tmpCollectionId = null;
            } else {
              _tmpCollectionId = _cursor.getLong(_cursorIndexOfCollectionId);
            }
            final String _tmpTagsCsv;
            _tmpTagsCsv = _cursor.getString(_cursorIndexOfTagsCsv);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsReadLater;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsReadLater);
            _tmpIsReadLater = _tmp_1 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            final String _tmpExtrasJson;
            _tmpExtrasJson = _cursor.getString(_cursorIndexOfExtrasJson);
            _item = new LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpNotes,_tmpDomain,_tmpPreviewImageUrl,_tmpFaviconUrl,_tmpPlatform,_tmpCategoryId,_tmpCollectionId,_tmpTagsCsv,_tmpPriority,_tmpIsFavorite,_tmpIsReadLater,_tmpIsArchived,_tmpVisitCount,_tmpCreatedAt,_tmpUpdatedAt,_tmpLastOpenedAt,_tmpExtrasJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
