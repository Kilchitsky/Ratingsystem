package com.example.vova.ratingsystemtneu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class ShowProgress extends AppCompatActivity {

    private static final String TAG = "myLogs"; //Log.d(TAG, "show");
    ListView listView1;
    Switch toggle;
    String semestr;
    List<Subjects> first;
    StringPars stringPars;
    String parsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_progress);

        parsText = getIntent().getStringExtra("parsText");
        stringPars= new StringPars();

        toggle = (Switch) findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    semestr = "secondSemester";
                    show(parsText,semestr);
                } else {
                    semestr = "firstSemester";
                    show(parsText, semestr);
                }
            }
        });

        if(toggle.isChecked()){
            semestr = "secondSemester";
        }
        else {
            semestr = "firstSemester";
            if(iff(parsText,"secondSemester") == true) {
                toggle.setEnabled(false);
                Context context = getApplicationContext();
                CharSequence text = "Second semester is empty!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }
        show(parsText,semestr);
    }

    private void show(String parsText,String semestr){
        first = stringPars.stringPars(parsText, semestr);

        listView1 = (ListView) findViewById(R.id.listview1);
        YourAdapter adapter = new YourAdapter(this, first);
        listView1.setAdapter(adapter);
    }


    private boolean iff(String parsText,String semestr){
        if(stringPars.stringPars(parsText, semestr).isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
