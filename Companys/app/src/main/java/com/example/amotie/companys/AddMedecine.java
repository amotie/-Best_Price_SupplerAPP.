package com.example.amotie.companys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class AddMedecine extends AppCompatActivity {
EditText medicene ,sale,amount,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medecine);
        medicene=(EditText)findViewById(R.id.medicine);
        sale=(EditText)findViewById(R.id.Sale);
        amount=(EditText)findViewById(R.id.Amount);
        price=(EditText)findViewById(R.id.Price);



    }
    public void add(View view) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (isConnected == true) {
            if (medicene.getText().toString().isEmpty() || sale.getText().toString().isEmpty() || amount.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                Toast.makeText(AddMedecine.this,"Enter Data",Toast.LENGTH_SHORT).show();
            }
            else{
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                String id = sharedPreferences.getString("id", "");
                System.out.println(id);
                NetworkAddMed networkAddMed = new NetworkAddMed(AddMedecine.this);
                networkAddMed.execute(id, medicene.getText().toString(), sale.getText().toString(), price.getText().toString(), amount.getText().toString());
                medicene.setText("");
                sale.setText("");
                amount.setText("");
                price.setText("");
            }
        }
        else{
            Toast.makeText(AddMedecine.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

}
