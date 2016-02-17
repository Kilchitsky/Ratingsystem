package com.example.vova.ratingsystemtneu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vova on 26.01.2016.
 */
public class StringPars {
    public List<Subjects> stringPars(String parsText, String semestr){
        List<Subjects> firstSemestrsList = new ArrayList<>();

        try {
            //JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONObject jsonObject = new JSONObject(parsText);
            JSONObject studentJSON = jsonObject.getJSONObject("student");
            JSONObject firstSemester = studentJSON.getJSONObject(semestr);



            JSONArray subjects = firstSemester.getJSONArray("subjects");

            for (int i = 0; i < subjects.length(); i++) {
                Subjects subject = new Subjects();

                subject.setName(subjects.getJSONObject(i).getString("name"));
                subject.setControlType(subjects.getJSONObject(i).getString("controlType"));
                subject.setTotalScore(subjects.getJSONObject(i).getInt("totalScore"));

                //Log.d(TAG, subject.getName());

                JSONArray moduls;
                moduls = subjects.getJSONObject(i).getJSONArray("modules");


                List<Subjects.Modules> modulesList = new ArrayList<>();


                for (int j = 0; j < moduls.length(); j++) {
                    Subjects.Modules module = new Subjects.Modules();

                    module.setDate(moduls.getJSONObject(j).getString("date"));
                    module.setWeight(moduls.getJSONObject(j).getInt("weight"));
                    module.setScore(moduls.getJSONObject(j).getInt("score"));
                    modulesList.add(module);
                }
                subject.setModulList(modulesList);
                firstSemestrsList.add(subject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return firstSemestrsList;
    }
}
