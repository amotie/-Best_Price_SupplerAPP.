package com.example.amotie.companys;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class History extends AppCompatActivity {
    ArrayList<Requested> tableItems=new ArrayList<>();
    ListView listView;
    RequsetAdptor requsetAdptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        listView=(ListView)findViewById(R.id.HistoryData);
        String id = sharedPreferences.getString("id", "");
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
        if(isConnected==true) {

            DesplayHistory desplayHistory = new DesplayHistory(History.this);
            desplayHistory.execute(id);
        }
        else{
            Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();

        }


    }
    class Requested{
        String Product_Name;
        String PharmacyName;
        String Price;
        String Amount;

        String Address;
        String Phone;

        Requested(String Product_Name,String PharmacyName,String Amount,String price,String Address,String Phone){
            this.Product_Name=Product_Name;
            this.PharmacyName=PharmacyName;
            this.Price=price;
            this.Amount=Amount;

            this.Address=Address;
            this.Phone=Phone;
        }

    }
    class RequsetAdptor extends BaseAdapter {
        ArrayList<Requested> tableItems;
        Context context;
        RequsetAdptor(Context c,ArrayList<Requested> tableItems1){
            context=c;
            tableItems=tableItems1;

            System.out.println("Asss");


        }
        @Override
        public int getCount() {
            if(tableItems.size()==0){

            }
            return tableItems.size();
        }

        @Override
        public Object getItem(int position) {
            return tableItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.historydata,parent,false);
            final TextView Name=(TextView)row.findViewById(R.id.product_Name);
            final TextView Company=(TextView)row.findViewById(R.id.Company_Name);
            final TextView Price=(TextView)row.findViewById(R.id.Price);
            final TextView Amount=(TextView) row.findViewById(R.id.Amount);

            TextView Address=(TextView)row.findViewById(R.id.Addres);
            TextView Phone=(TextView)row.findViewById(R.id.Phone);

            final Requested tableItem=tableItems.get(position);


            Name.setText(tableItem.Product_Name);
            Company.setText(tableItem.PharmacyName);
            Price.setText(tableItem.Price + " EGP");
            Amount.setText(tableItem.Amount);
            Address.setText(tableItem.Address);
            Phone.setText(tableItem.Phone);




            return row;
        }

    }
    class DesplayHistory extends AsyncTask<String,Void,ArrayList<Requested>> {
        private WeakReference<History> activityWeakReference;
        DesplayHistory(History activity){
            activityWeakReference=new WeakReference<History>(activity);
        }
        @Override
        protected void onPreExecute() {
            History activity=activityWeakReference.get();
            if(activity==null||activity.isFinishing()){
                return;
            }
            super.onPreExecute();



        }
        @Override
        protected ArrayList<Requested> doInBackground(String... strings) {
            String UserUpdateUrl="https://bestdiscounteg.com/Android/History.php";
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
                ArrayList<Requested> arrayList=new ArrayList<>();
                String []row=new String[6];
                String line;

                int i=0;
                while ((line=bufferedReader.readLine())!=null){
                    row[i]=line;
                    System.out.println(row[i]);

                    if(i==5){
                        i=0;
                        Log.d("A7a",row[0]);
                        arrayList.add( new Requested(row[0],row[1],row[2],row[3],row[4],row[5]));

                    }
                    else {
                        i++;
                    }
                }
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
        protected void onPostExecute(ArrayList<Requested> strings) {
            History  activity=activityWeakReference.get();




            if(activity==null||activity.isFinishing()){
                return;
            }
            if(strings==null){
                Toast.makeText(activity,"No Enternet Connection ",Toast.LENGTH_SHORT).show();

            }
            else {
                tableItems = strings;
                requsetAdptor = new RequsetAdptor(activity, tableItems);

                listView.setAdapter(requsetAdptor);
                super.onPostExecute(strings);
            }
        }


    }


}
