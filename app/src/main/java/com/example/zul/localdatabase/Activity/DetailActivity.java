package com.example.zul.localdatabase.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zul.localdatabase.LocalDatabase.DataHelper;
import com.example.zul.localdatabase.Model.DataModel;
import com.example.zul.localdatabase.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_ENGLISH = "english";
    public static final String EXTRA_TRANSLATE = "translate";
    private static final String TAG = "DetailActivity";

    private LinearLayout linearLayoutDetail;
    private LinearLayout linearLayoutUpdate;
    private LinearLayout linearLayoutButton;
    private LinearLayout linearLayoutSubmit;

    private EditText editTextEnglish;
    private EditText editTextTranslate;

    private TextView textViewEnglish;
    private TextView textViewTranslate;

    private int id = 0;
    private String english;
    private String translate;

    private DataModel dataModel = new DataModel();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "onCreate: called");

        context = this;

        linearLayoutDetail = findViewById(R.id.linear_detail_detail);
        linearLayoutUpdate = findViewById(R.id.linear_update_detail);
        linearLayoutButton = findViewById(R.id.linear_button_detail);
        linearLayoutSubmit = findViewById(R.id.linear_submit_detail);

        linearLayoutDetail.setVisibility(View.VISIBLE);
        linearLayoutButton.setVisibility(View.VISIBLE);
        linearLayoutUpdate.setVisibility(View.GONE);
        linearLayoutSubmit.setVisibility(View.GONE);

        id = getIntent().getIntExtra(EXTRA_ID, 0);
        english = getIntent().getStringExtra(EXTRA_ENGLISH);
        translate = getIntent().getStringExtra(EXTRA_TRANSLATE);

        textViewEnglish = findViewById(R.id.text_english_detail);
        textViewTranslate = findViewById(R.id.text_translate_detail);

        textViewEnglish.setText(english);
        textViewTranslate.setText(translate);

        editTextEnglish = findViewById(R.id.et_english_detail);
        editTextTranslate = findViewById(R.id.et_translate_detail);

        Button buttonUpdate = findViewById(R.id.btn_update_detail);
        final Button buttonSubmit = findViewById(R.id.btn_submit_detail);
        Button buttonDelete = findViewById(R.id.btn_delete_detail);
        Button buttonCancel = findViewById(R.id.btn_cancel_detail);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutDetail.setVisibility(View.GONE);
                linearLayoutButton.setVisibility(View.GONE);
                linearLayoutUpdate.setVisibility(View.VISIBLE);
                linearLayoutSubmit.setVisibility(View.VISIBLE);

                editTextEnglish.setHint(english);
                editTextTranslate.setHint(translate);

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                english = editTextEnglish.getText().toString().trim();
                translate = editTextTranslate.getText().toString().trim();

                if (english.equals(""))
                    editTextEnglish.setError("This field cannot be empty");
                else if (translate.equals(""))
                    editTextTranslate.setError("This field cannot be empty");
                else {

                    linearLayoutDetail.setVisibility(View.VISIBLE);
                    linearLayoutButton.setVisibility(View.VISIBLE);
                    linearLayoutUpdate.setVisibility(View.GONE);
                    linearLayoutSubmit.setVisibility(View.GONE);

                    textViewEnglish.setText(english);
                    textViewTranslate.setText(translate);

                    updateData();

                    Toast.makeText(context, "Update succeed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteData();

                Toast.makeText(context, "Delete at id " + id + " succeed",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutDetail.setVisibility(View.VISIBLE);
                linearLayoutButton.setVisibility(View.VISIBLE);
                linearLayoutUpdate.setVisibility(View.GONE);
                linearLayoutSubmit.setVisibility(View.GONE);

            }
        });

    }

    private void updateData() {

        DataHelper dataHelper = new DataHelper(context);
        dataHelper.open();

        dataModel.setId(id);
        dataModel.setEnglishWord(english);
        dataModel.setEnglishTranslate(translate);

        dataHelper.updateData(dataModel);

        dataHelper.close();
    }

    private void deleteData() {

        DataHelper dataHelper = new DataHelper(context);
        dataHelper.open();

        dataHelper.deleteData(id);

        dataHelper.close();
    }

}
