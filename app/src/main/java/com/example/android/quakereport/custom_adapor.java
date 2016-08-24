package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Altysh on 8/14/2016.
 */
public class custom_adapor extends ArrayAdapter<custom_Earth> {
public custom_adapor(Activity context ,ArrayList<custom_Earth> word){
   super(context,0,word);
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
View theView = convertView;
        if (theView == null){
            theView = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_layout, parent, false);
        }
        custom_Earth r = getItem(position);
        TextView city = (TextView)theView.findViewById(R.id.city);
        int d;
        String name,loca;
        if (r.getcityname().contains("of")) {
          d = r.getcityname().indexOf("of");
            //Log.d("onCreate",city);
             loca= r.getcityname().substring(0,d+2);
            name=r.getcityname().substring(d+2);

        }
        else {
            loca ="";
            city.setVisibility(View.GONE);
            name=r.getcityname();
        }

        city.setText(loca);
        TextView namet=(TextView)theView.findViewById(R.id.cityname);
        namet.setText(name);
        TextView data =(TextView)theView.findViewById(R.id.data);
        data.setText(r.getdata());
        TextView str = (TextView)theView.findViewById(R.id.streash);
        DecimalFormat formatter = new DecimalFormat("0.0");
        GradientDrawable magnitudeCircle = (GradientDrawable) str.getBackground();


        String stre = formatter.format(Double.parseDouble(r.getstreagh()));
        int t = (int) Double.parseDouble(r.getstreagh());
        int magnitudeColor = getMagnitudeColor(t);
        magnitudeCircle.setColor(ContextCompat.getColor(getContext(),magnitudeColor));

        str.setText(stre);
        return theView;
    }
    private int getMagnitudeColor(int st){


        switch (st){
            case 0 : return (R.color.magnitude0);
            case 1 : return (R.color.magnitude1);
            case 2 : return (R.color.magnitude2);
            case 3 : return (R.color.magnitude3);
            case 4 : return (R.color.magnitude4);
            case 5 : return (R.color.magnitude5);
            case 6 : return (R.color.magnitude6);
            case 7 : return (R.color.magnitude7);
            case 8 : return (R.color.magnitude8);
            case 9 : return (R.color.magnitude9);
            default: return (R.color.magnitude10plus);
        }
    }
}
