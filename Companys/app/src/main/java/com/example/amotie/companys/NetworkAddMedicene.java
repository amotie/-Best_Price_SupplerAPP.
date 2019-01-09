package com.example.amotie.companys;

import android.content.Intent;
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

public class NetworkAddMedicene extends AsyncTask<String,Void,String> {
    private WeakReference<AddMedecine> activityWeakReference;
    NetworkAddMedicene(AddMedecine activity){
        activityWeakReference=new WeakReference<AddMedecine>(activity);
    }


    @Override
    protected String doInBackground(String... strings) {

        String AddMedicneURL= "http://amotie.000webhostapp.com/AddMedecine.php";


        try {
            String CompaniesID=strings[0];
            String Medication_Name=strings[1];
            String Sale=strings[2];
            String Price=strings[3];
            String Amount=strings[4];
            String Description=strings[5];

            URL url=new URL(AddMedicneURL);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String post_data= URLEncoder.encode("CompaniesID","utf-8")+"="+URLEncoder.encode(CompaniesID,"utf-8")+"&"
                    +URLEncoder.encode("Medication_Name","utf-8")+"="+URLEncoder.encode(Medication_Name,"utf-8")+"&"
                    +URLEncoder.encode("Sale","utf-8")+"="+URLEncoder.encode(Sale,"utf-8")+"&"
                    +URLEncoder.encode("Price","utf-8")+"="+URLEncoder.encode(Price,"utf-8")+"&"
                    +URLEncoder.encode("Amount","utf-8")+"="+URLEncoder.encode(Amount,"utf-8")+"&"
                    +URLEncoder.encode("Description","utf-8")+"="+URLEncoder.encode(Description,"utf-8");
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
        AddMedecine activity=activityWeakReference.get();

        if(strings.equals("Record Added")){
            if(activity==null||activity.isFinishing()){
                return;
            }
            Toast.makeText(activity,"DONE",Toast.LENGTH_SHORT).show();
        }
        else{
            if(activity==null||activity.isFinishing()){
                return;
            }
            Toast.makeText(activity,strings,Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(strings);
    }
}
