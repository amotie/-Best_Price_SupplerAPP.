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

public class EditMediceneNetwork extends AsyncTask<String,Void,String[]> {
    private WeakReference<ItemCOntent> activityWeakReference;
    EditMediceneNetwork(ItemCOntent activity){
        activityWeakReference=new WeakReference<ItemCOntent>(activity);
    }
    @Override
    protected void onPreExecute() {
        ItemCOntent activity=activityWeakReference.get();
        if(activity==null||activity.isFinishing()){
            return;
        }


        super.onPreExecute();


    }
    @Override
    protected String[] doInBackground(String... strings) {
        String UserUpdateUrl="https://bestdiscounteg.com/Android/EditMedicne.php";
        try {
            String id=strings[0];
            String ProductName=strings[1];
            String Amount=strings[2];
            String Price=strings[3];
            String Sale=strings[4];
            String MedID=strings[5];

            URL url= new URL(UserUpdateUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
            String post_data= URLEncoder.encode("ID","utf-8")+"="+URLEncoder.encode(id,"utf-8")+"&"+
                    URLEncoder.encode("Name","utf-8")+"="+URLEncoder.encode(ProductName,"utf-8")+"&"+
                    URLEncoder.encode("Sale","utf-8")+"="+URLEncoder.encode(Sale,"utf-8")+"&"+
                    URLEncoder.encode("Price","utf-8")+"="+URLEncoder.encode(Price,"utf-8")+"&"+
                    URLEncoder.encode("Amount","utf-8")+"="+URLEncoder.encode(Amount,"utf-8")+"&"+
                    URLEncoder.encode("IDMed","utf-8")+"="+URLEncoder.encode(MedID,"utf-8");
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
            String[] resultall={result,strings[1],strings[2],strings[3],strings[4]};
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return resultall;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String[] s) {
        ItemCOntent  activity=activityWeakReference.get();
        System.out.println(s);
        if(s[0].equals("Record Updated")){
            if(activity==null||activity.isFinishing()){
                return;
            }
            Toast.makeText(activity,"Updated",Toast.LENGTH_SHORT).show();
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
