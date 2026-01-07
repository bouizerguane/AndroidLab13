package com.example.lab13.files;

import android.content.Context;
import com.example.lab13.model.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class StudentsJsonStore {
    private static final String FILE_NAME = "students.json";

    public static void save(Context context, List<Student> students) throws Exception {
        JSONArray arr = new JSONArray();
        for (Student s : students) {
            JSONObject obj = new JSONObject();
            obj.put("id", s.id);
            obj.put("name", s.name);
            obj.put("age", s.age);
            arr.put(obj);
        }
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(arr.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    public static List<Student> load(Context context) throws Exception {
        try (FileInputStream fis = context.openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String json = new String(bytes, StandardCharsets.UTF_8);
            JSONArray arr = new JSONArray(json);
            List<Student> list = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                list.add(new Student(o.getInt("id"), o.getString("name"), o.getInt("age")));
            }
            return list;
        }
    }
}