package com.example.vova.ratingsystemtneu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.vova.ratingsystemtneu.R.id.Login;

public class Login extends AppCompatActivity  implements View.OnClickListener  {

    Boolean isInternetPresent = false;
    ConnectionDetector cd;


    EditText login;
    EditText password;


    Student resurs = new Student();
    private static final String TAG = "myLogs";


    String parsText;

    Button button;
    JSONTask jsonTask = new JSONTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cd = new ConnectionDetector(getApplicationContext());

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.pass);

        login.setFilters(new InputFilter[]{filter});

        button = (Button) findViewById(Login);
        button.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(this, ShowProgress.class);
        String log = "";
        log = login.getText().toString();
        String pass = "";
        pass = password.getText().toString();

        String uml = "https://moduleok.appspot.com/api/getScoresByPassword?login="+login.getText().toString()
                +"&password="+ password.getText().toString();

        String uml1 ="http://api.openweathermap.org/data/2.5/weather?q=Chortkiv&APPID=e831173d649e9a81ff6610d96dfe0f0a";

        //заповнення полів
        if (log.length() < 1 && pass.length() < 1) {
            message("Enter your login and password!");
        }else if (log != "" && pass .length() < 1){
            message("Enter your password!");
        }else if (log.length() < 1 && pass != ""){
            message("Enter your login!");
        }else

        //наявність інтернету
        if(isInternetPresent = cd.ConnectingToInternet() == false){
            message("Internet connection not found!");
        }else{
            boolean secces = false;
            //String json = "";

            String json = "";

            try {

                json  = getJson(uml);////------------------------------------------------------маєм джейсон


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            secces = parsSuccess(json);


            //коректність даних
            if (secces == false){
                message("Incorrect login or password!");
            }else {

                intent.putExtra("parsText", json);
                startActivity(intent);

            }
        }
    }



    void message(String text){

        AlertDialog.Builder mess = new  AlertDialog.Builder(Login.this);
        mess.setMessage(text);
        mess .setCancelable(false);
        mess.setNegativeButton("ok!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Log.d(TAG, "click");

                    }
                });
        mess.show();

    }


    Boolean parsSuccess(String json){
        Boolean out = false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            resurs.setSuccess(jsonObject.getBoolean("success"));
            Log.d(TAG, resurs.getSuccess().toString());

            out = resurs.getSuccess();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return out;
    }




    String getJson(String uml)throws ExecutionException, InterruptedException {
        String text = new JSONTask().execute(uml).get();
        return text;
    }




public class JSONTask extends AsyncTask<String,String,String> {

    StringBuffer buffer = new StringBuffer();
    String JSONTparsText = "";


    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));


            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            //return buffer.toString();
            JSONTparsText = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSONTparsText;
    }


    @Override
    protected void onPostExecute(String resault) {

        super.onPostExecute(resault);

    }
}





    InputFilter filter = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {

                if (!Character.isLetterOrDigit(source.charAt(i))) {

                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                    return "";
                }
            }
            return null;
        }

    };



/*



public String parssText = "{\n" +
        "\t\"student\":\n" +
        "\t{\n" +
        "\t\t\"name\":\"Кільчицький Володимир Богданович \",\n" +
        "\t\t\"group\":\"ПЗС-41\",\n" +
        "\t\t\"firstSemester\":\n" +
        "\t\t\t{\n" +
        "\t\t\t\t\"subjects\":[\n" +
        "\n" +
        "\t\t\t\t\t\t{\n" +
        "\t\t\t\t\t\t\t\"name\":\"Емпіричні методи програмної інженерії\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Екзамен\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.10.15\",\"weight\":20,\"score\":90},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":20,\"score\":80},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"04.12.15\",\"weight\":20,\"score\":85},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"18.12.15\",\"weight\":40,\"score\":85}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":85\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\n" +
        "\t\t\t\t\t\t\t\"name\":\"Менеджмент проектів програмного забезпечення\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Екзамен\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.10.15\",\"weight\":20,\"score\":83},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":20,\"score\":90},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"04.12.15\",\"weight\":20,\"score\":85},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"11.12.15\",\"weight\":40,\"score\":85}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":86\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\t\n" +
        "\t\t\t\t\t\t\t\"name\":\"Мікропрограмування\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Екзамен\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.10.15\",\"weight\":20,\"score\":81},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":20,\"score\":85},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"05.12.15\",\"weight\":20,\"score\":83},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"08.12.15\",\"weight\":40,\"score\":78}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":81\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\n" +
        "\t\t\t\t\t\t\t\"name\":\"Моделювання та аналіз програмного забезпечення\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Екзамен\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.10.15\",\"weight\":20,\"score\":90},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":20,\"score\":90},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"04.12.15\",\"weight\":20,\"score\":92},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.12.15\",\"weight\":40,\"score\":90}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":90\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\n" +
        "\t\t\t\t\t\t\t\"name\":\"Програмування робототехнічних систем\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Залік\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"24.10.15\",\"weight\":30,\"score\":88},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":30,\"score\":75},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"04.12.15\",\"weight\":40,\"score\":90}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":85\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\t\n" +
        "\t\t\t\t\t\t\t\"name\":\"Системний аналіз та проектування комп\\u0027ютерних інформаційних систем\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Екзамен\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"16.10.15\",\"weight\":20,\"score\":84},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"02.12.15\",\"weight\":20,\"score\":96},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"08.12.15\",\"weight\":20,\"score\":86},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"14.12.15\",\"weight\":40,\"score\":80}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":85\n" +
        "\t\t\t\t\t\t},\n" +
        "\n" +
        "\t\t\t\t\t\t{\t\n" +
        "\t\t\t\t\t\t\t\"name\":\"Системний аналіз та проектування комп\\u0027ютерних інформаційних систем\",\n" +
        "\t\t\t\t\t\t\t\"controlType\":\"Курсовий проект\",\n" +
        "\t\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"03.12.15\",\"weight\":60,\"score\":95},\n" +
        "\t\t\t\t\t\t\t\t{\"date\":\"04.12.15\",\"weight\":40,\"score\":95}\n" +
        "\t\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\t\"totalScore\":95\n" +
        "\t\t\t\t\t\t}\n" +
        "\t\t\t\t\t]\n" +
        "\t\t\t},\n" +
        "\n" +
        "\n" +
        "\t\t\"secondSemester\":\n" +
        "\t\t\t{\"subjects\":[\n" +
        "\t\t\t\t\t{\n" +
        "\t\t\t\t\t\t\"name\":\"Переддипломна практика\",\n" +
        "\t\t\t\t\t\t\"controlType\":\"Звіт про практику\",\n" +
        "\t\t\t\t\t\t\"modules\":[\n" +
        "\t\t\t\t\t\t\t{\"date\":\"25.12.15\",\"weight\":30,\"score\":95},\n" +
        "\t\t\t\t\t\t\t{\"date\":\"15.02.16\",\"weight\":30,\"score\":0},\n" +
        "\t\t\t\t\t\t\t{\"date\":\"19.02.16\",\"weight\":40,\"score\":0}\n" +
        "\t\t\t\t\t\t\t],\n" +
        "\t\t\t\t\t\t\"totalScore\":24\n" +
        "\t\t\t\t\t}\n" +
        "\t\t\t\t]\n" +
        "\t\t\t}\n" +
        "\t},\n" +
        "\"phpsessid\":\"9pie5cv7du75ksl9e9p8aptqc4\",\n" +
        "\"$804c27b4e9d8b5a3183e7ab890c2d8f3\":\"0b1b3a85759bf4613e44c211bfc2dd3127f4ddc2a%3A4%3A%7Bi%3A0%3Bs%3A8%3A%2207269%2F12%22%3Bi%3A1%3Bs%3A9%3A%22kichitsky%22%3Bi%3A2%3Bi%3A2592000%3Bi%3A3%3Ba%3A2%3A%7Bs%3A3%3A%22pip%22%3Bs%3A62%3A%22%D0%9A%D0%86%D0%9B%D0%AC%D0%A7%D0%98%D0%A6%D0%AC%D0%9A%D0%98%D0%99+%D0%92%D0%BE%D0%BB%D0%BE%D0%B4%D0%B8%D0%BC%D0%B8%D1%80+%D0%91%D0%BE%D0%B3%D0%B4%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87%22%3Bs%3A5%3A%22group%22%3Bs%3A9%3A%22%D0%9F%D0%97%D0%A1-41%22%3B%7D%7D\",\n" +
        "\"success\":true\n" +
        "}";*/
}
