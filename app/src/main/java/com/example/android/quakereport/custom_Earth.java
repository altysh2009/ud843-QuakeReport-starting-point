package com.example.android.quakereport;

import android.widget.ArrayAdapter;

/**
 * Created by Altysh on 8/14/2016.
 */
public class custom_Earth {
     private String mcityName;
    private String mdata ;
   private double mstreagh=0 ;
    private String mstreag;
    private String Url;

    public custom_Earth(String cityname,String date ,double streagh){
        mcityName = cityname;
        mdata = date;
        mstreagh = streagh;

    }
    public custom_Earth(String cityname,String date ,String streagh,String url){
        mcityName = cityname;
        mdata = date;
        mstreag = streagh;
        Url = url;

    }
    public String getcityname(){return mcityName;}
    public String getdata(){return mdata;}
    public String getstreagh(){
        if(mstreagh!=0)return Double.toString(mstreagh);
    else return mstreag;}
    public String getUrl(){return Url;}

}
