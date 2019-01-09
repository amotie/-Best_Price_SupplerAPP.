package com.example.amotie.companys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkAddMed extends AsyncTask<String,Void,String> {
    private WeakReference<AddMedecine> activityWeakReference;
    NetworkAddMed(AddMedecine activity){
        activityWeakReference=new WeakReference<AddMedecine>(activity);
    }
    @Override
    protected void onPreExecute() {
        AddMedecine activity=activityWeakReference.get();
        if(activity==null||activity.isFinishing()){
            return;
        }


        super.onPreExecute();


    }
    @Override
    protected String doInBackground(String... strings) {
        String UserUpdateUrl="https://bestdiscounteg.com/Android/AddMedecine.php";
        try {
            String id=strings[0];
            String ProductName=strings[1];
            String Sale=strings[2];
            String Price=strings[3];
            String Amount=strings[4];
            URL url= new URL(UserUpdateUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String post_data= URLEncoder.encode("CompanyID","utf-8")+"="+URLEncoder.encode(id,"utf-8")+"&"+
                    URLEncoder.encode("ProductName","utf-8")+"="+URLEncoder.encode(ProductName,"utf-8")+"&"+
                    URLEncoder.encode("Sale","utf-8")+"="+URLEncoder.encode(Sale,"utf-8")+"&"+
                    URLEncoder.encode("Price","utf-8")+"="+URLEncoder.encode(Price,"utf-8")+"&"+
                    URLEncoder.encode("Amount","utf-8")+"="+URLEncoder.encode(Amount,"utf-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        AddMedecine  activity=activityWeakReference.get();
        System.out.println(s);
        if(s.equals("Done")){
            if(activity==null||activity.isFinishing()){
                return;
            }
            Toast.makeText(activity,"Added",Toast.LENGTH_SHORT).show();
           }
        else {
            if(activity==null||activity.isFinishing()){
                return;
            }

            Toast.makeText(activity,"try Again Later",Toast.LENGTH_SHORT).show();

        }

        super.onPostExecute(s);
    }
}
