package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    BarChart barChart;
    public int wait = 1;
    ArrayList<String> nameTxts = new ArrayList<String>();
    ArrayList<Integer> populations = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        barChart = findViewById(R.id.barChart);

        loadDiagram();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadDiagram()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<API>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<API>>() {
            @Override
            public void onResponse(Call<List<API>> call, Response<List<API>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.code(),
                            Toast.LENGTH_SHORT).show();
                }
                List<API> posts = response.body();
                String nameTxt;
                Integer population;

                nameTxts.clear();
                populations.clear();
                for(API country:posts){
                    nameTxt = country.getName();
                    population = country.getPopulation();

                    nameTxts.add(nameTxt);
                    populations.add(population);
                }

                int i = 1;
                int p = 2;

                while(i < populations.size()){
                    if (populations.get(i - 1) > populations.get(i)) {
                    p++;
                    i = p;
                } else {
                        int tmpPop = populations.get(i);
                        populations.set(i, populations.get(i - 1));
                        populations.set(i-1, tmpPop);

                        String tmpName = nameTxts.get(i);
                        nameTxts.set(i, nameTxts.get(i-1));
                        nameTxts.set(i-1, tmpName);
                    i--;
                    if (i == 0) {
                        p++;
                        i = p;
                    }
                }
                }
            }
            @Override
            public void onFailure(Call<List<API>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onChangeRadioGroup(View v){
        switch (v.getId()) {
            case R.id.radio_button_1:
                setDiagram1(nameTxts, populations);
                break;
            case R.id.radio_button_2:
                setDiagram2();
                break;
        }
    }

    public void setDiagram1(ArrayList<String> names, ArrayList<Integer> pop){
        ArrayList<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i<5;i++)
            entries.add(new BarEntry(i, (float)populations.get(i)));

        ArrayList<String> labels = new ArrayList<String>();
        String message = "";
        for(int i = 0; i<5;i++) {
            labels.add(String.valueOf(i+1));
            message = message + (i+1) + " - " + nameTxts.get(i) +"\n";
        }
        TextView tableOfCountries = findViewById(R.id.tableOfCountries);
        tableOfCountries.setText(message);

        BarDataSet dataset = new BarDataSet(entries, "Countries");
        BarData data = new BarData(dataset);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        dataset.setValueTextSize(10f);
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        barChart.getAxisRight().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.animateY(500);
        barChart.getDescription().setEnabled(false);
        barChart.setViewPortOffsets(0f, 0f, 0f, 100f);

        barChart.setData(data);
        barChart.invalidate();
    }

    public void setDiagram2(){
        ArrayList<BarEntry> entries = new ArrayList<>();

        Random rand = new Random();
        ArrayList<Integer> randNumbers = new ArrayList<Integer>();
        for(int i = 0; i<5;i++) {
            boolean flag = false;
            int temp = rand.nextInt(populations.size());
            if(!randNumbers.contains(temp))
                flag = true;
            if(flag)
                randNumbers.add(temp);
        }

        for(int i = 0; i<5;i++)
            entries.add(new BarEntry(i, (float)populations.get(randNumbers.get(i))));
        ArrayList<String> labels = new ArrayList<String>();
        String message = "";
        for(int i = 0; i<5;i++) {
            labels.add((i+1) + "");
            message = message + (i+1) + " - " + nameTxts.get(randNumbers.get(i)) +"\n";
        }
        TextView tableOfCountries = findViewById(R.id.tableOfCountries);
        tableOfCountries.setText(message);

        BarDataSet dataset = new BarDataSet(entries, "Countries");
        BarData data = new BarData(dataset);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        dataset.setValueTextSize(10f);
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        barChart.getAxisRight().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.animateY(500);
        barChart.getDescription().setEnabled(false);
        barChart.setViewPortOffsets(0f, 0f, 0f, 100f);

        barChart.setData(data);
        barChart.invalidate();
    }

    public void onClickA2(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}