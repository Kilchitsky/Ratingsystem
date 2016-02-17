package com.example.vova.ratingsystemtneu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by vova on 26.01.2016.
 */
public class YourAdapter extends ArrayAdapter<Subjects> {
    int[] color = {R.color.Yellow,R.color.fon,R.color.Blue,R.color.Green,R.color.Red,R.color.DarkBlue};


    private static LayoutInflater inflater;

    private static final String TAG = "myLogs"; //Log.d(TAG, "show");

    public YourAdapter(Context context,List<Subjects> semestrList) {
        super(context,R.layout.row, semestrList);
        // TODO Auto-generated constructor stub

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        Subjects semestr = getItem(position);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row, null);
        }

        TextView subName;
        TextView subScore;
        TextView date;
        TextView date2;
        TextView date3;
        TextView date4;
        TextView date5;
        TextView weight;
        TextView weight2;
        TextView weight3;
        TextView weight4;
        TextView weight5;
        TextView score;
        TextView score2;
        TextView score3;
        TextView score4;
        TextView score5;

        ImageView imageV;

        imageV = (ImageView) convertView.findViewById(R.id.subcontrolType);

        switch (semestr.getControlType()) {
            case "Екзамен":
                imageV.setImageResource(R.drawable.exam);
                break;
            case "Курсовий проект":
                imageV.setImageResource(R.drawable.kproj);
                break;
            case  "Залік":
                imageV.setImageResource(R.drawable.zalik);
                break;
            case  "Звіт про практику":
                imageV.setImageResource(R.drawable.pra);
                break;
            default:break;
        }




        subName = (TextView) convertView.findViewById(R.id.subjectNsme);
        subScore = (TextView) convertView.findViewById(R.id.subtotalScore);
        subName.setText(semestr.getName());
        subScore.setText(semestr.getTotalScore().toString());

        date = (TextView) convertView.findViewById(R.id.date);
        date2 = (TextView) convertView.findViewById(R.id.date2);
        date3 = (TextView) convertView.findViewById(R.id.date3);
        date4 = (TextView) convertView.findViewById(R.id.date4);
        date5 = (TextView) convertView.findViewById(R.id.date5);
        weight = (TextView) convertView.findViewById(R.id.weight);
        weight2 = (TextView) convertView.findViewById(R.id.weight2);
        weight3 = (TextView) convertView.findViewById(R.id.weight3);
        weight4 = (TextView) convertView.findViewById(R.id.weight4);
        weight5 = (TextView) convertView.findViewById(R.id.weight5);
        score = (TextView) convertView.findViewById(R.id.score);
        score2 = (TextView) convertView.findViewById(R.id.score2);
        score3 = (TextView) convertView.findViewById(R.id.score3);
        score4 = (TextView) convertView.findViewById(R.id.score4);
        score5 = (TextView) convertView.findViewById(R.id.score5);


        // StringBuffer stringBuffer = new StringBuffer();
        String[] dat = new String[5];
        String[] wei = new String[5];
        String[] sco = new String[5];

        for (int i = 0; i < 5; i++){
            dat[i] = "";
            wei[i] = "";
            sco[i] = "";
        }

        int i = 0;
        for (Subjects.Modules modul : semestr.getModulList(position)){
            dat[i] = modul.getDate();
            wei[i] = modul.getWeight().toString() + "%";
            sco[i] = modul.getScore().toString();
            i++;
        }


        date.setText(dat[0]);
        date2.setText(dat[1]);
        date3.setText(dat[2]);
        date4.setText(dat[3]);
        date5.setText(dat[4]);

        weight.setText(wei[0]);
        weight2.setText(wei[1]);
        weight3.setText(wei[2]);
        weight4.setText(wei[3]);
        weight5.setText(wei[4]);

        score.setText(sco[0]);
        score2.setText(sco[1]);
        score3.setText(sco[2]);
        score4.setText(sco[3]);
        score5.setText(sco[4]);


        convertView.setBackgroundResource(color[position%6]);
        return convertView;
    }

}
