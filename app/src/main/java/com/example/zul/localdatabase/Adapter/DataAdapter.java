package com.example.zul.localdatabase.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zul.localdatabase.Activity.DetailActivity;
import com.example.zul.localdatabase.Model.DataModel;
import com.example.zul.localdatabase.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    private Context context;
    private ArrayList<DataModel> arrayList;

    public DataAdapter(Context context, ArrayList<DataModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycler_template, viewGroup, false);

        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder,
                                 @SuppressLint("RecyclerView") final int i) {

        dataHolder.textViewEnglish.setText(arrayList.get(i).getEnglishWord());
        dataHolder.textViewTranslate.setText(arrayList.get(i).getEnglishTranslate());
        dataHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, ""+arrayList.get(i).getId(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID, arrayList.get(i).getId());
                intent.putExtra(DetailActivity.EXTRA_ENGLISH, arrayList.get(i).getEnglishWord());
                intent.putExtra(DetailActivity.EXTRA_TRANSLATE, arrayList.get(i).getEnglishTranslate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {

        private TextView textViewEnglish;
        private TextView textViewTranslate;
        private LinearLayout linearLayout;

        private DataHolder(@NonNull View itemView) {
            super(itemView);

            textViewEnglish = itemView.findViewById(R.id.text_english_template);
            textViewTranslate = itemView.findViewById(R.id.text_translate_template);
            linearLayout = itemView.findViewById(R.id.linear_layout_template);
        }
    }

}
