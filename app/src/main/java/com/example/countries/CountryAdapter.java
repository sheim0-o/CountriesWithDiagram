package com.example.countries;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {

    private LayoutInflater inflater;
    private int layout;
    private List<Country> countries;

    public CountryAdapter(Context context, int resource, List<Country> countries) {
        super(context, resource, countries);
        this.countries = countries;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView populationView = (TextView) view.findViewById(R.id.population);
        ImageView flagView = (ImageView) view.findViewById(R.id.flag);

        Country country = countries.get(position);

        nameView.setText(country.getNameCountry());
        populationView.setText(country.getPopulationCountry());
        Picasso.get().load(country.getFlagCountry()).resize(120,70).into(flagView);

        return view;
    }
}