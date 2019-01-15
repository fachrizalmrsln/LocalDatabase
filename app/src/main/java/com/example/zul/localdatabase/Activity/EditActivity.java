package com.example.zul.localdatabase.Activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zul.localdatabase.LocalDatabase.DataHelper;
import com.example.zul.localdatabase.Model.DataModel;
import com.example.zul.localdatabase.R;

public class EditActivity extends AppCompatActivity {

    private Context context;

    private DataModel dataModel = new DataModel();
    private DataHelper dataHelper;

    private EditText editTextEnglish;
    private EditText editTextTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        context = this;

        editTextEnglish = findViewById(R.id.et_english_edit);
        editTextTranslate = findViewById(R.id.et_translate_edit);

        Button buttonSubmit = findViewById(R.id.btn_submit_edit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataHelper = new DataHelper(context);
                dataHelper.open();
                Cursor cursor = dataHelper.getAllData();

                String textEnglish = editTextEnglish.getText().toString().trim();
                String textTranslate = editTextTranslate.getText().toString().trim();
                int id = cursor.getCount() + 1;

                if (textEnglish.isEmpty())
                    editTextEnglish.setError("This field cannot be empty !");
                else if (textTranslate.isEmpty())
                    editTextTranslate.setError("This field cannot be empty !");
                else {
                    dataModel.setId(id);
                    dataModel.setEnglishWord(textEnglish);
                    dataModel.setEnglishTranslate(textTranslate);

                    dataHelper.insertData(dataModel);

                    editTextEnglish.setText(null);
                    editTextTranslate.setText(null);

                    Toast.makeText(context, "Data Inserted",
                            Toast.LENGTH_SHORT).show();
                }

                dataHelper.close();
            }
        });
    }
}
