package shoumik.com.zeus.OverflowMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import shoumik.com.zeus.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String [] array={"Metric","Imperial"};
    String TAG="ShoumiksTAG";
    Spinner spinner;
    EditText editText;
    String location;
    String unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner=(Spinner)findViewById(R.id.spinner);
        editText=(EditText)findViewById(R.id.editText);

        editText.setText("Dhaka");
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, array);
        spinner.setAdapter(spinnerAdapter);

    }




    @Override
    protected void onStop() {
        super.onStop();

        location=editText.getText().toString();
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("loc",location);
        editor.putString("unit",unit);

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position==0){

            unit="metric";

        }
        if (position==1){

            unit="imperial";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
