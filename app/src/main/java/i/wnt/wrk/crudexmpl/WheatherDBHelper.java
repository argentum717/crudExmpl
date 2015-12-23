package i.wnt.wrk.crudexmpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by argentum717 on 23.12.2015.
 */
public class WheatherDBHelper extends SQLiteOpenHelper {

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
}
