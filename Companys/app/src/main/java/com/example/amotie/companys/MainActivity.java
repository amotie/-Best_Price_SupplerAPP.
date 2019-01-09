package com.example.amotie.companys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.amotie.companys.Orders.SearchClick;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MaterialSearchView materialSearchView;
    String[]list;
    ArrayList<Requested> tableItems=new ArrayList<>();
    ListView listView;
    RequsetAdptor requsetAdptor;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView) findViewById(R.id.OrderRequset);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.Refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnected();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    DesplayOrder desplayOrder = new DesplayOrder(MainActivity.this);
                    desplayOrder.execute(id);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    Toast.makeText(MainActivity.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);

                }


            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=networkInfo!=null&&networkInfo.isConnected();
        if(isConnected==true) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            String id = sharedPreferences.getString("id", "");
            DesplayOrder desplayOrder = new DesplayOrder(MainActivity.this);
            desplayOrder.execute(id);
        }
        else{
            Toast.makeText(MainActivity.this,"No Enternet Connection ",Toast.LENGTH_SHORT).show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        TextView usernamenav=(TextView)headerView.findViewById(R.id.usernamenav);
        SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
        usernamenav.setText(sharedPreferences.getString("username",""));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.add){
            Intent intent=new Intent(getApplicationContext(),AddMedecine.class);
            startActivity(intent);
        }


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
if(id==R.id.settings){
    Intent intent=new Intent(getApplicationContext(),Settings.class);
    startActivity(intent);
}
if(id==R.id.Stock){
    Intent intent=new Intent(getApplicationContext(),Orders.class);
    startActivity(intent);

}
if(id==R.id.History){
    Intent intent=new Intent(getApplicationContext(),History.class);
    startActivity(intent);
}
        if(id==R.id.Logout) {
            SharedPreferences sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE);
            sharedPreference.edit().clear().apply();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }


    class Requested{
        String OrderID;
        String Product_Name;
        String PharmacyName;
        String Price;
        String Amount;

        String Address;
        String Phone;

        Requested(String OrderID,String Product_Name,String PharmacyName,String Amount,String price,String Address,String Phone){
            this.Product_Name=Product_Name;
            this.PharmacyName=PharmacyName;
            this.Price=price;
            this.Amount=Amount;
            this.OrderID=OrderID;
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
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.ordersdata,parent,false);
            final TextView Name=(TextView)row.findViewById(R.id.product_Name);
            final TextView Company=(TextView)row.findViewById(R.id.Company_Name);
            final TextView Price=(TextView)row.findViewById(R.id.Price);
            final TextView Amount=(TextView) row.findViewById(R.id.Amount);

            TextView Address=(TextView)row.findViewById(R.id.Addres);
            TextView Phone=(TextView)row.findViewById(R.id.Phone);
            Button Delvierd=(Button) row.findViewById(R.id.Deliverd);
            final Requested tableItem=tableItems.get(position);


            Name.setText(tableItem.Product_Name);
            Company.setText(tableItem.PharmacyName);
            Price.setText(tableItem.Price + " EGP");
            Amount.setText(tableItem.Amount);

            Address.setText(tableItem.Address);
            Phone.setText(tableItem.Phone);


            Delvierd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    NetworkDelvered networkDelvered=new NetworkDelvered(MainActivity.this);
                    networkDelvered.execute(tableItem.OrderID);
                    tableItems.remove(position);
                    notifyDataSetChanged();



                }
            });

            return row;
        }

    }
    class DesplayOrder extends AsyncTask<String,Void,ArrayList<Requested>>{
        private WeakReference<MainActivity> activityWeakReference;
        DesplayOrder(MainActivity activity){
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
        protected ArrayList<Requested> doInBackground(String... strings) {
            String UserUpdateUrl="https://bestdiscounteg.com/Android/OrderDespaly.php";
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
                String []row=new String[7];
                String line;

                int i=0;
                while ((line=bufferedReader.readLine())!=null){
                    row[i]=line;
                    System.out.println(row[i]);

                    if(i==6){
                        i=0;
                        Log.d("A7a",row[0]);
                        arrayList.add( new Requested(row[0],row[1],row[2],row[3],row[4],row[5],row[6]));

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
            MainActivity  activity=activityWeakReference.get();




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
