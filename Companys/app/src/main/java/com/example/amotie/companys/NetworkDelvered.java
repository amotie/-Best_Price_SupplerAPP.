package com.example.amotie.companys;

import android.os.AsyncTask;
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

public class NetworkDelvered extends AsyncTask<String,Void,String> {
    private WeakReference<MainActivity> activityWeakReference;
    NetworkDelvered(MainActivity activity){
        activityWeakReference=new WeakReference<MainActivity>(activity);
    }
    @Override
    protected void onPreExecute() {
        MainActivity activity=activityWeakReference.get();
        if(activity==null||activity.isFinishing()){
            return;
        }
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... strings) {
        String UserUpdateUrl="https://bestdiscounteg.com/Android/OrderDelvered.php";
        try {
            String id=strings[0];

            URL url= new URL(UserUpdateUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String post_data= URLEncoder.encode("ID","utf-8")+"="+URLEncoder.encode(id,"utf-8");
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
    protected void onPostExecute(String strings) {
        MainActivity  activity=activityWeakReference.get();
        System.out.println(strings);

        if(activity==null||activity.isFinishing()){
            return;
        }
        Toast.makeText(activity,strings,Toast.LENGTH_SHORT).show();






        super.onPostExecute(strings);
    }

}

