package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ArrayList<Country> countries = new ArrayList<Country>();
    public CountryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                for(API country:posts){
                    String nameTxt = country.getName();
                    String populationTxt = Integer.toString(country.getPopulation());
                    String[] flagUrl = country.getFlags();

                    countries.add(new Country(nameTxt, populationTxt, flagUrl[1]));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<API>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        list = (ListView) findViewById(R.id.list);
        adapter = new CountryAdapter(this, R.layout.list_item, countries);
        list.setAdapter(adapter);
    }

    public void onClickA1(View v){
        startActivity(new Intent(this, MainActivity2.class));
    }
}

