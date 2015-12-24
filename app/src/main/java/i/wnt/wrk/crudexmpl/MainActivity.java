package i.wnt.wrk.crudexmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WheatherDBHelper wheatherDB = new WheatherDBHelper(getApplicationContext());

        wheatherDB.createRecord(new WheatherRecord("Abinsk", 2));
        wheatherDB.createRecord(new WheatherRecord("Moscow", -1));
        wheatherDB.createRecord(new WheatherRecord("Taganrog", 0));

        List<WheatherRecord> tmp = wheatherDB.getAllRecord();

        wheatherDB.updateRecord(new WheatherRecord("Moscow", -99));

        WheatherRecord result = wheatherDB.readRecord("Moscow");

        wheatherDB.deleteRecord("Abinsk");

        tmp = wheatherDB.getAllRecord();
    }
}
