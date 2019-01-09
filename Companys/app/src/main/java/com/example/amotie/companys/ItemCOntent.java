package com.example.amotie.companys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemCOntent extends AppCompatActivity {

EditText ProductName,Amount,Sale,Price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProductName=(EditText)findViewById(R.id.product_NameEdit);
        Amount=(EditText)findViewById(R.id.AmountEdit);
        Sale=(EditText)findViewById(R.id.SaleEdit);
        Price=(EditText)findViewById(R.id.PriceEdit);


        Intent intent=getIntent();
        if(intent.hasExtra("ProductName")){

            ProductName.setHint(intent.getStringExtra("ProductName"));
            Amount.setHint(intent.getStringExtra("Amount"));
            Price.setHint(intent.getStringExtra("Price"));
            Sale.setHint(intent.getStringExtra("Sale"));



        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.itemcontentmenu, menu);


        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.Ok){
            String PruductName1,Amount1,Price1,Sale1;
            if(ProductName.getText().toString().isEmpty()){
                PruductName1=ProductName.getHint().toString();

            }
            else{
                PruductName1=ProductName.getText().toString();
            }
            if(Amount.getText().toString().isEmpty()){
                Amount1=Amount.getHint().toString();
            }
            else{
                Amount1=Amount.getText().toString();
            }
            if(Price.getText().toString().isEmpty()){
                Price1=Price.getHint().toString();
            }
            else{
                Price1=Price.getText().toString();
            }
            if(Sale.getText().toString().isEmpty()){
                Sale1=Sale.getHint().toString();
            }
            else{
                Sale1=Sale.getText().toString();
            }
            ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
            if(isConnected==true) {

                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                String idC = sharedPreferences.getString("id", "");
                EditMediceneNetwork editMediceneNetwork = new EditMediceneNetwork(ItemCOntent.this);
                editMediceneNetwork.execute(idC,PruductName1,Amount1,Price1,Sale1);


            }
            else {
                Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();

            }
        }


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }




}
