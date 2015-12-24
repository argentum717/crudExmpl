package i.wnt.wrk.crudexmpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by argentum717 on 23.12.2015.
 * RU: Это простой пример реализации CRUD на базе стандартных возможностей Android SDK
 * EN: This is simple example of using CRUD.
 */
public class WheatherDBHelper extends SQLiteOpenHelper implements CRUDface<WheatherRecord> {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "WheatherDB.db";
    private static final String DB_TABLENAME = "Wheather";
    private static final String KEY_ID = "id";
    private static final String KEY_CITY = "city";
    private static final String KEY_TEMP = "tmp";


    public WheatherDBHelper(Context context) {
        /* Здесь, в общем то все просто, указывается
         * контекст, имя БД, т.к. мы не используем фабрик курсоров,
         * то пишем null, ну и версия БД для вызова Upgrade и Downgrade методов.
         */
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    /*
     * Тоже все просто, создаем таблицу с нужной нам структурой
     */
        db.execSQL("CREATE TABLE " + DB_TABLENAME
                + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CITY + " TEXT, "
                + KEY_TEMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Все как в обычных примерах, ничего нового
        db.execSQL("DROP TABLE IF EXIST " + DB_TABLENAME);
        onCreate(db);
    }


    /**
     * Создаем запись в БД со значениями полученными их вх. аргумента.
     *
     * @param record - объект, представляющий данные для записи
     */
    @Override
    public void createRecord(WheatherRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Создаем болванку для записи в БД
        ContentValues values = new ContentValues();
        // Говорим куда и что записываем
        values.put(KEY_CITY, record.getCity());
        values.put(KEY_TEMP, record.getTmp());

        // Все вставляеся в БД, причем если данные такие же уже есть
        // то все равно создастся еще одна запись. Это пример,
        // никаких проверок не делается.
        db.insert(DB_TABLENAME, null, values);

        db.close(); // не забываем все за собой прибирать
    }

    /**
     * Читаем одну запись(строку) из таблицы
     *
     * @param key - в данном примере ключ поиска, это название города ))
     * @return возвращаем в виде WheaterRecord объекта
     */
    @Override
    public WheatherRecord readRecord(String key) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLENAME, new String[]{KEY_ID, KEY_CITY, KEY_TEMP},
                KEY_CITY + "=?", new String[]{key}, null, null, null, null);

        WheatherRecord result = null;

        if (cursor != null) {
            cursor.moveToFirst();
            result = new WheatherRecord(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        }

        db.close();

        return result;
    }

    /**
     * Обновляем запись в БД по ключю "город"
     *
     * @param record - содержит в себе ключ(город) и данные для обновления(тпмпература)
     * @return возвращает количство обновленных запсей, у нас ведь могут быть одинаковые
     */
    @Override
    public int updateRecord(WheatherRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CITY, record.getCity());
        values.put(KEY_TEMP, record.getTmp());

        int writedRow = db.update(DB_TABLENAME, values, KEY_CITY + "=?",
                new String[]{record.getCity()});

        db.close();

        return writedRow;
    }

    /**
     * Просто уаляем запись по ключу "город", если она есть.
     *
     * @param key - название города
     */
    @Override
    public void deleteRecord(String key) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DB_TABLENAME, KEY_CITY + "=?", new String[]{key});
        db.close();
    }

    /**
     * Получаем все записи из таблицы
     *
     * @return возвращаем список элементов типа WheatherRecord
     */
    @Override
    public List<WheatherRecord> getAllRecord() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<WheatherRecord> result = null;

        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLENAME, null);

        if (c != null) {
            c.moveToFirst();

            result = new ArrayList<>();

            do {
                result.add(new WheatherRecord(c.getInt(0), c.getString(1), c.getInt(2)));
            } while (c.moveToNext());
        }

        return result;
    }


    /**
     * Узнаем количество строк в нашей таблице
     *
     * @return - количество строк=записей в таблице
     */
    @Override
    public long getCount() {
        // Статический метод-утилита для работы с БД и курсорами,
        // внутри выполняется ряд проверок, но все сводится к запросу
        // "SELECT COUNT(*) FROM TABLENAME".
        return DatabaseUtils.queryNumEntries(this.getReadableDatabase(), DB_TABLENAME);
    }
}
