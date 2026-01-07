package com.example.lab13.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab13.R;
import com.example.lab13.files.StudentsJsonStore;
import com.example.lab13.model.Student;
import com.example.lab13.prefs.AppPrefs;
import com.example.lab13.prefs.SecurePrefs;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etToken;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etToken = findViewById(R.id.etToken);
        tvResult = findViewById(R.id.tvResult);

        findViewById(R.id.btnSave).setOnClickListener(v -> handleSave());
        findViewById(R.id.btnLoad).setOnClickListener(v -> handleLoad());
        findViewById(R.id.btnClear).setOnClickListener(v -> handleClear());
    }

    private void handleSave() {
        try {
            // 1. Sauvegarde Prefs
            AppPrefs.save(this, etName.getText().toString(), "fr", "dark");
            // 2. Sauvegarde Token Chiffré
            SecurePrefs.saveToken(this, etToken.getText().toString());
            // 3. Sauvegarde Fichier JSON
            List<Student> list = Arrays.asList(new Student(1, "Sara", 22), new Student(2, "Ali", 20));
            StudentsJsonStore.save(this, list);

            tvResult.setText("Données sauvegardées avec succès !");
        } catch (Exception e) {
            tvResult.setText("Erreur : " + e.getMessage());
        }
    }

    private void handleLoad() {
        try {
            String[] prefs = AppPrefs.load(this);
            String token = SecurePrefs.loadToken(this);
            List<Student> list = StudentsJsonStore.load(this);

            String res = "NOM: " + prefs[0] + "\nTOKEN: " + token + "\nJSON: " + list.size() + " étudiants";
            tvResult.setText(res);
        } catch (Exception e) {
            tvResult.setText("Erreur de chargement");
        }
    }

    private void handleClear() {
        try {
            AppPrefs.clear(this);
            SecurePrefs.clear(this);
            deleteFile("students.json");
            tvResult.setText("Tout a été effacé.");
        } catch (Exception ignored) {}
    }
}