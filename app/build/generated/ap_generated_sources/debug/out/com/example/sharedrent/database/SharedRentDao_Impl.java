package com.example.sharedrent.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sharedrent.models.Flat;
import com.example.sharedrent.models.LivingArea;
import com.example.sharedrent.models.Money;
import com.example.sharedrent.models.Tenant;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SharedRentDao_Impl implements SharedRentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tenant> __insertionAdapterOfTenant;

  private final EntityInsertionAdapter<Flat> __insertionAdapterOfFlat;

  private final EntityDeletionOrUpdateAdapter<Tenant> __deletionAdapterOfTenant;

  private final EntityDeletionOrUpdateAdapter<Flat> __deletionAdapterOfFlat;

  private final EntityDeletionOrUpdateAdapter<Tenant> __updateAdapterOfTenant;

  private final EntityDeletionOrUpdateAdapter<Flat> __updateAdapterOfFlat;

  public SharedRentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTenant = new EntityInsertionAdapter<Tenant>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `tenants` (`relativeLivingAreaRatio`,`rentPercentFormatted`,`name`,`income`,`livingArea`,`rent`,`relativeLivingAreaFraction`,`effectiveLivingArea`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tenant value) {
        stmt.bindDouble(1, value.relativeLivingAreaRatio);
        if (value.rentPercentFormatted == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.rentPercentFormatted);
        }
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        final int _tmp;
        _tmp = Converters.fromMoney(value.income);
        stmt.bindLong(4, _tmp);
        final double _tmp_1;
        _tmp_1 = Converters.fromLivingArea(value.livingArea);
        stmt.bindDouble(5, _tmp_1);
        final int _tmp_2;
        _tmp_2 = Converters.fromMoney(value.getRent());
        stmt.bindLong(6, _tmp_2);
        stmt.bindDouble(7, value.getRelativeLivingAreaFraction());
        final double _tmp_3;
        _tmp_3 = Converters.fromLivingArea(value.getEffectiveLivingArea());
        stmt.bindDouble(8, _tmp_3);
      }
    };
    this.__insertionAdapterOfFlat = new EntityInsertionAdapter<Flat>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Flat` (`name`,`rent`,`livingArea`,`tenants_names`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Flat value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
        final int _tmp;
        _tmp = Converters.fromMoney(value.rent);
        stmt.bindLong(2, _tmp);
        final double _tmp_1;
        _tmp_1 = Converters.fromLivingArea(value.livingArea);
        stmt.bindDouble(3, _tmp_1);
        final String _tmp_2;
        _tmp_2 = Converters.fromHashSet(value.tenants_names);
        if (_tmp_2 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfTenant = new EntityDeletionOrUpdateAdapter<Tenant>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `tenants` WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tenant value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
      }
    };
    this.__deletionAdapterOfFlat = new EntityDeletionOrUpdateAdapter<Flat>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Flat` WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Flat value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
      }
    };
    this.__updateAdapterOfTenant = new EntityDeletionOrUpdateAdapter<Tenant>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `tenants` SET `relativeLivingAreaRatio` = ?,`rentPercentFormatted` = ?,`name` = ?,`income` = ?,`livingArea` = ?,`rent` = ?,`relativeLivingAreaFraction` = ?,`effectiveLivingArea` = ? WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tenant value) {
        stmt.bindDouble(1, value.relativeLivingAreaRatio);
        if (value.rentPercentFormatted == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.rentPercentFormatted);
        }
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        final int _tmp;
        _tmp = Converters.fromMoney(value.income);
        stmt.bindLong(4, _tmp);
        final double _tmp_1;
        _tmp_1 = Converters.fromLivingArea(value.livingArea);
        stmt.bindDouble(5, _tmp_1);
        final int _tmp_2;
        _tmp_2 = Converters.fromMoney(value.getRent());
        stmt.bindLong(6, _tmp_2);
        stmt.bindDouble(7, value.getRelativeLivingAreaFraction());
        final double _tmp_3;
        _tmp_3 = Converters.fromLivingArea(value.getEffectiveLivingArea());
        stmt.bindDouble(8, _tmp_3);
        if (value.name == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.name);
        }
      }
    };
    this.__updateAdapterOfFlat = new EntityDeletionOrUpdateAdapter<Flat>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Flat` SET `name` = ?,`rent` = ?,`livingArea` = ?,`tenants_names` = ? WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Flat value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
        final int _tmp;
        _tmp = Converters.fromMoney(value.rent);
        stmt.bindLong(2, _tmp);
        final double _tmp_1;
        _tmp_1 = Converters.fromLivingArea(value.livingArea);
        stmt.bindDouble(3, _tmp_1);
        final String _tmp_2;
        _tmp_2 = Converters.fromHashSet(value.tenants_names);
        if (_tmp_2 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp_2);
        }
        if (value.name == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.name);
        }
      }
    };
  }

  @Override
  public void insertTenant(final Tenant tenant) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTenant.insert(tenant);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertFlat(final Flat flat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFlat.insert(flat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTenant(final Tenant tenant) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTenant.handle(tenant);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFlat(final Flat flat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFlat.handle(flat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Tenant tenant) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTenant.handle(tenant);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Flat flat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFlat.handle(flat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Flat>> getAllFlats() {
    final String _sql = "SELECT * FROM Flat";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Flat"}, false, new Callable<List<Flat>>() {
      @Override
      public List<Flat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRent = CursorUtil.getColumnIndexOrThrow(_cursor, "rent");
          final int _cursorIndexOfLivingArea = CursorUtil.getColumnIndexOrThrow(_cursor, "livingArea");
          final int _cursorIndexOfTenantsNames = CursorUtil.getColumnIndexOrThrow(_cursor, "tenants_names");
          final List<Flat> _result = new ArrayList<Flat>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Flat _item;
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item = new Flat(_tmpName);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfRent);
            _item.rent = Converters.toMoney(_tmp);
            final double _tmp_1;
            _tmp_1 = _cursor.getDouble(_cursorIndexOfLivingArea);
            _item.livingArea = Converters.toLivingArea(_tmp_1);
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTenantsNames);
            _item.tenants_names = Converters.toHashSet(_tmp_2);
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
  public LiveData<List<Tenant>> getAllTenants() {
    final String _sql = "SELECT * FROM tenants";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"tenants"}, false, new Callable<List<Tenant>>() {
      @Override
      public List<Tenant> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRelativeLivingAreaRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "relativeLivingAreaRatio");
          final int _cursorIndexOfRentPercentFormatted = CursorUtil.getColumnIndexOrThrow(_cursor, "rentPercentFormatted");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIncome = CursorUtil.getColumnIndexOrThrow(_cursor, "income");
          final int _cursorIndexOfLivingArea = CursorUtil.getColumnIndexOrThrow(_cursor, "livingArea");
          final int _cursorIndexOfRent = CursorUtil.getColumnIndexOrThrow(_cursor, "rent");
          final int _cursorIndexOfRelativeLivingAreaFraction = CursorUtil.getColumnIndexOrThrow(_cursor, "relativeLivingAreaFraction");
          final int _cursorIndexOfEffectiveLivingArea = CursorUtil.getColumnIndexOrThrow(_cursor, "effectiveLivingArea");
          final List<Tenant> _result = new ArrayList<Tenant>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Tenant _item;
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item = new Tenant(_tmpName);
            _item.relativeLivingAreaRatio = _cursor.getDouble(_cursorIndexOfRelativeLivingAreaRatio);
            _item.rentPercentFormatted = _cursor.getString(_cursorIndexOfRentPercentFormatted);
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIncome);
            _item.income = Converters.toMoney(_tmp);
            final double _tmp_1;
            _tmp_1 = _cursor.getDouble(_cursorIndexOfLivingArea);
            _item.livingArea = Converters.toLivingArea(_tmp_1);
            final Money _tmpRent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfRent);
            _tmpRent = Converters.toMoney(_tmp_2);
            _item.setRent(_tmpRent);
            final double _tmpRelativeLivingAreaFraction;
            _tmpRelativeLivingAreaFraction = _cursor.getDouble(_cursorIndexOfRelativeLivingAreaFraction);
            _item.setRelativeLivingAreaFraction(_tmpRelativeLivingAreaFraction);
            final LivingArea _tmpEffectiveLivingArea;
            final double _tmp_3;
            _tmp_3 = _cursor.getDouble(_cursorIndexOfEffectiveLivingArea);
            _tmpEffectiveLivingArea = Converters.toLivingArea(_tmp_3);
            _item.setEffectiveLivingArea(_tmpEffectiveLivingArea);
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
}
