package com.example.amotie.companys;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.ArrayList;

public class Network extends AsyncTask<String,Void,ArrayList<String>> {
    private WeakReference<Login> activityWeakReference;
    Network(Login activity){
        activityWeakReference=new WeakReference<Login>(activity);
    }

    @Override
    protected void onPreExecute() {
        Login activity=activityWeakReference.get();
        if(activity==null||activity.isFinishing()){
            return;
        }
        activity.linearLayout.setVisibility(View.GONE);
        activity.logo.setVisibility(View.GONE);
        activity.progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override

    protected ArrayList<String> doInBackground(String... strings) {

        String LoginUrl="https://amotie.000webhostapp.com/Android/LoginCompany.php";
        try {
            String username=strings[0];
            String password=strings[1];
            URL url= new URL(LoginUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String post_data=URLEncoder.encode("username","utf-8")+"="+URLEncoder.encode(username,"utf-8")+"&"+
                    URLEncoder.encode("password","utf-8")+"="+URLEncoder.encode(password,"utf-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

            ArrayList arrayList =new ArrayList<String>();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                arrayList.add(line);

            }
            arrayList.add(username);
            arrayList.add(password);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return arrayList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        Login activity=activityWeakReference.get();

        if(s.get(0).equals("Correct")){
            if(activity==null||activity.isFinishing()){
                return;
            }
            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.linearLayout.setVisibility(View.VISIBLE);
            activity.logo.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences=activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("id",s.get(1));
            editor.putString("username",s.get(6));
            editor.putString("password",s.get(7));
            editor.putString("Email",s.get(2));
            editor.putString("Adress",s.get(3));
            editor.putString("Phone",s.get(4));
            editor.putString("Pharmacy_Name",s.get(5));

            editor.apply();
            Intent intent=new Intent(activity,MainActivity.class);
            activity.startActivity(intent);
        }
        else {
            if(activity==null||activity.isFinishing()){
                return;
            }

            Toast.makeText(activity,"Username or password is Wrong",Toast.LENGTH_SHORT).show();
            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.linearLayout.setVisibility(View.VISIBLE);
            activity.logo.setVisibility(View.VISIBLE);
        }
        super.onPostExecute(s);
    }
}

