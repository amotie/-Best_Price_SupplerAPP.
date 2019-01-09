package com.example.amotie.companys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

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

public class Orders extends AppCompatActivity {
    ArrayList<TableItem> tableItems=new ArrayList<>();
    ListView listView;
    TableListAdapter tableListAdapter;
    MaterialSearchView materialSearchView;
    LinearLayout noresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
            noresult=(LinearLayout)findViewById(R.id.NoResult) ;
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=networkInfo!=null&&networkInfo.isConnected();
        if(isConnected==true ) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            String id = sharedPreferences.getString("id", "");

            Stock stock = new Stock(Orders.this);
            stock.execute(id);
        }
        else{
            Toast.makeText(Orders.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();

        }

        listView=(ListView)findViewById(R.id.ViewMed);
        materialSearchView=(MaterialSearchView)findViewById(R.id.mysearch);
        materialSearchView.closeSearch();


        materialSearchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        materialSearchView.setTextColor(getResources().getColor(R.color.colorAccent));
        materialSearchView.setHintTextColor(getResources().getColor(R.color.colorAccent));
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnected();
                if(isConnected==true) {

                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    SearchClick searchClick = new SearchClick(Orders.this);
                    searchClick.execute(query, id);
                }
                else{
                    Toast.makeText(Orders.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();



                }



                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });


        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }

        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem=menu.findItem(R.id.search_icon);
        materialSearchView.setMenuItem(menuItem);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    class TableItem{
        String Product_Name;

        String Price;
        String Amount;
        String Sale;
        String id;

        TableItem(String Product_Name,String price,String Amount,String Sale,String id){
            this.Product_Name=Product_Name;

            this.Price=price;
            this.Amount=Amount;
            this.Sale=Sale;
            this.id=id;
        }

    }
    class TableListAdapter extends BaseAdapter {
        ArrayList<TableItem> tableItems;
        Context context;
        TableListAdapter(Context c,ArrayList<TableItem> tableItems1){
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.view_medicne,parent,false);
            final TextView Name=(TextView)row.findViewById(R.id.product_Name);

            final TextView Price=(TextView)row.findViewById(R.id.Price);
            final TextView Amount=(TextView) row.findViewById(R.id.Amount);
            final TextView Sale=(TextView) row.findViewById(R.id.Sale);
            Button edit=(Button) row.findViewById(R.id.edit);
            Button Delete=(Button)row.findViewById(R.id.Delete);
            final TableItem tableItem=tableItems.get(position);


            Name.setText(tableItem.Product_Name);

            Price.setText(tableItem.Price + " EGP");
            Amount.setText(tableItem.Amount);
            Sale.setText(tableItem.Sale +"%");
            System.out.println(tableItem.id);



            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Orders.this,ItemCOntent.class);
                    intent.putExtra("ProductName",Name.getText().toString());
                    intent.putExtra("Price",Price.getText().toString());
                    intent.putExtra("Amount",Amount.getText().toString());
                    intent.putExtra("Sale",Sale.getText().toString());
                    intent.putExtra("ID",tableItem.id);
                    startActivity(intent);





                }
            });
           Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DeleteItem deleteItem=new DeleteItem(Orders.this);

                    deleteItem.execute(tableItem.id);
                    tableItems.remove(position);
                    notifyDataSetChanged();



                }
            });


            return row;
        }

    }
    public class Stock extends AsyncTask<String,Void,ArrayList<TableItem>> {
        private WeakReference<Orders> activityWeakReference;
        Stock(Orders activity){
            activityWeakReference=new WeakReference<Orders>(activity);
        }
        @Override
        protected void onPreExecute() {
            Orders activity=activityWeakReference.get();
            if(activity==null||activity.isFinishing()){
                return;
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<TableItem> doInBackground(String... strings) {
            String LoginUrl="https://bestdiscounteg.com/Android/ViewStock.php";
            try {
                String Name=strings[0];

                URL url= new URL(LoginUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
                String post_data=URLEncoder.encode("ID","utf-8")+"="+URLEncoder.encode(Name,"utf-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                ArrayList arrayList=new ArrayList<TableItem>();

                String line;

                while ((line=bufferedReader.readLine())!=null){

                    String x=line;
                    if((line=bufferedReader.readLine()).isEmpty()){
                        System.out.println("A(A");
                        break;
                    }
                    else{
                        arrayList.add(new TableItem(x, line, bufferedReader.readLine(), bufferedReader.readLine(),bufferedReader.readLine()));

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
        protected void onPostExecute(ArrayList<TableItem> strings) {
            Orders activity=activityWeakReference.get();

            //System.out.print(strings.get(0));
            if(activity==null||activity.isFinishing()){
                return;
            }
            if(strings==null){
                Toast.makeText(Orders.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();

            }
            else {
                System.out.println(strings.size());
                strings.remove((strings.size() - 1));
                ArrayList<TableItem> arrayList = strings;
                TableListAdapter tableListAdapter = new TableListAdapter(Orders.this, arrayList);
                listView.setAdapter(tableListAdapter);
                listView.setAdapter(tableListAdapter);
                listView.setVisibility(View.VISIBLE);
                noresult.setVisibility(View.GONE);
                if (strings.isEmpty()) {
                    listView.setVisibility(View.GONE);
                    noresult.setVisibility(View.VISIBLE);
                }
            }



            super.onPostExecute(strings);
        }
    }
    public class SearchClick extends AsyncTask<String,Void,ArrayList<TableItem>> {
        private WeakReference<Orders> activityWeakReference;
        SearchClick(Orders activity){
            activityWeakReference=new WeakReference<Orders>(activity);
        }
        @Override
        protected void onPreExecute() {
            Orders activity=activityWeakReference.get();
            if(activity==null||activity.isFinishing()){
                return;
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<TableItem> doInBackground(String... strings) {
            String LoginUrl="https://bestdiscounteg.com/Android/SearchCompanyClick.php";
            try {
                String Name=strings[0];
                String ID=strings[1];

                URL url= new URL(LoginUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
                String post_data=URLEncoder.encode("Name","utf-8")+"="+URLEncoder.encode(Name,"utf-8")+"&"
                        +URLEncoder.encode("id","utf-8")+"="+URLEncoder.encode(ID,"utf-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                ArrayList arrayList=new ArrayList<TableItem>();

                String line;

                while ((line=bufferedReader.readLine())!=null){

                    String x=line;
                    System.out.println(x);
                    if((line=bufferedReader.readLine())==null){
                        System.out.println("A(A");
                        break;
                    }
                    else{
                        arrayList.add(new TableItem(x, line, bufferedReader.readLine(), bufferedReader.readLine(),bufferedReader.readLine()));

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
        protected void onPostExecute(ArrayList<TableItem> strings) {
            Orders activity=activityWeakReference.get();

            //System.out.print(strings.get(0));
            if(activity==null||activity.isFinishing()){
                return;
            }
            if(strings==null){
                Toast.makeText(Orders.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();

            }
            else {
                System.out.println(strings.size());
                strings.remove((strings.size() - 1));
                ArrayList<TableItem> arrayList = strings;
                TableListAdapter tableListAdapter = new TableListAdapter(Orders.this, arrayList);
                listView.setAdapter(tableListAdapter);
                listView.setVisibility(View.VISIBLE);
                noresult.setVisibility(View.GONE);
                if (strings.isEmpty()) {
                    listView.setVisibility(View.GONE);
                    noresult.setVisibility(View.VISIBLE);
                }

            }

            super.onPostExecute(strings);
        }
    }



}
