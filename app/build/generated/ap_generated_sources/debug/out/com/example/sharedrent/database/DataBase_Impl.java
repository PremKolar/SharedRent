package com.example.sharedrent.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DataBase_Impl extends DataBase {
  private volatile SharedRentDao _sharedRentDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tenants` (`relativeLivingAreaRatio` REAL NOT NULL, `rentPercentFormatted` TEXT, `name` TEXT NOT NULL, `income` INTEGER, `livingArea` REAL, `rent` INTEGER, `relativeLivingAreaFraction` REAL NOT NULL, `effectiveLivingArea` REAL, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Flat` (`name` TEXT NOT NULL, `rent` INTEGER, `livingArea` REAL, `tenants_names` TEXT, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '09400bd78e138ca3bab2d7bd4e9fa0dc')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tenants`");
        _db.execSQL("DROP TABLE IF EXISTS `Flat`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTenants = new HashMap<String, TableInfo.Column>(8);
        _columnsTenants.put("relativeLivingAreaRatio", new TableInfo.Column("relativeLivingAreaRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("rentPercentFormatted", new TableInfo.Column("rentPercentFormatted", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("income", new TableInfo.Column("income", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("livingArea", new TableInfo.Column("livingArea", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("rent", new TableInfo.Column("rent", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("relativeLivingAreaFraction", new TableInfo.Column("relativeLivingAreaFraction", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTenants.put("effectiveLivingArea", new TableInfo.Column("effectiveLivingArea", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTenants = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTenants = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTenants = new TableInfo("tenants", _columnsTenants, _foreignKeysTenants, _indicesTenants);
        final TableInfo _existingTenants = TableInfo.read(_db, "tenants");
        if (! _infoTenants.equals(_existingTenants)) {
          return new RoomOpenHelper.ValidationResult(false, "tenants(com.example.sharedrent.Tenant).\n"
                  + " Expected:\n" + _infoTenants + "\n"
                  + " Found:\n" + _existingTenants);
        }
        final HashMap<String, TableInfo.Column> _columnsFlat = new HashMap<String, TableInfo.Column>(4);
        _columnsFlat.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlat.put("rent", new TableInfo.Column("rent", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlat.put("livingArea", new TableInfo.Column("livingArea", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlat.put("tenants_names", new TableInfo.Column("tenants_names", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFlat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFlat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFlat = new TableInfo("Flat", _columnsFlat, _foreignKeysFlat, _indicesFlat);
        final TableInfo _existingFlat = TableInfo.read(_db, "Flat");
        if (! _infoFlat.equals(_existingFlat)) {
          return new RoomOpenHelper.ValidationResult(false, "Flat(com.example.sharedrent.Flat).\n"
                  + " Expected:\n" + _infoFlat + "\n"
                  + " Found:\n" + _existingFlat);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "09400bd78e138ca3bab2d7bd4e9fa0dc", "2c3af6ab92d1d2c2a3b35d1bad6d79a5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tenants","Flat");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tenants`");
      _db.execSQL("DELETE FROM `Flat`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SharedRentDao sharedRentDao() {
    if (_sharedRentDao != null) {
      return _sharedRentDao;
    } else {
      synchronized(this) {
        if(_sharedRentDao == null) {
          _sharedRentDao = new SharedRentDao_Impl(this);
        }
        return _sharedRentDao;
      }
    }
  }
}
