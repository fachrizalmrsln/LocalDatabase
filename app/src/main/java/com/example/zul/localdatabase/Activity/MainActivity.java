package com.example.zul.localdatabase.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zul.localdatabase.Adapter.DataAdapter;
import com.example.zul.localdatabase.LocalDatabase.DataHelper;
import com.example.zul.localdatabase.Model.DataModel;
import com.example.zul.localdatabase.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;

    private EditText editTextSearch;
    private TextView textViewShowAll;
    private RecyclerView recyclerView;

    private ArrayList<DataModel> dataModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        context = this;

        editTextSearch = findViewById(R.id.et_search_main);
        textViewShowAll = findViewById(R.id.text_show_all_main);
        recyclerView = findViewById(R.id.recycler_view_main);

        textViewShowAll.setVisibility(View.GONE);

        getAllData();
        setRecyclerView();

        Button buttonSearch = findViewById(R.id.btn_search_main);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewShowAll.setVisibility(View.VISIBLE);
                dataModels.clear();
                setSearchData();
                setRecyclerView();
            }
        });

        textViewShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModels.clear();
                getAllData();
                setRecyclerView();
                textViewShowAll.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        dataModels.clear();

        getAllData();
        setRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_query:
                Intent intent = new Intent(context, EditActivity.class);
                startActivity(intent);
                return true;
            case R.id.delete_query:
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.open();
                dataHelper.deleteAllData();
                dataHelper.close();
                dataModels.clear();
                setRecyclerView();
                Toast.makeText(context, "Succeed delete all data",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSearchData() {

        DataHelper dataHelper = new DataHelper(context);
        dataHelper.open();

        String searchData = editTextSearch.getText().toString().trim();

        if (searchData.equals(""))
            editTextSearch.setError("Search something");
        else {

            final Cursor cursor = dataHelper.getData(searchData);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String english = cursor.getString(1);
                    String translate = cursor.getString(2);
                    dataModels.add(new DataModel(id, english, translate));
                }
            } else {
                Toast.makeText(context, searchData + " not found",
                        Toast.LENGTH_SHORT).show();
            }

            dataHelper.close();
        }
    }

    private void setRecyclerView() {

        DataAdapter adapter = new DataAdapter(context, dataModels);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);

        adapter.notifyDataSetChanged();
    }

    private void getAllData() {

        DataHelper dataHelper = new DataHelper(context);
        dataHelper.open();

        final Cursor cursor = dataHelper.getAllData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String english = cursor.getString(1);
                String translate = cursor.getString(2);
                dataModels.add(new DataModel(id, english, translate));
            }
        } else {
            Toast.makeText(context, "Empty",
                    Toast.LENGTH_SHORT).show();
        }

        dataHelper.close();
    }

}
