package com.example.amotie.companys;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class Settings extends AppCompatActivity {
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView username=(TextView)findViewById(R.id.username);
        TextView email=(TextView)findViewById(R.id.email);
        TextView adress=(TextView)findViewById(R.id.Addres);
        TextView mobile=(TextView)findViewById(R.id.mobile);
        TextView pharmacyName=(TextView)findViewById(R.id.PharmacyName);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username",""));
        email.setText(sharedPreferences.getString("Email",""));
        adress.setText(sharedPreferences.getString("Adress",""));
        mobile.setText(sharedPreferences.getString("Phone",""));
        pharmacyName.setText(sharedPreferences.getString("Pharmacy_Name",""));

    }
    public void ChangeUsername(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changeusername, null);

        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText NewUsername=(EditText)view1.findViewById(R.id.New_username);
        Button Update=(Button)view1.findViewById(R.id.Updateusername_Btn);
        progressBar=(ProgressBar)view1.findViewById(R.id.progressbar);
        relativeLayout=(RelativeLayout)view1.findViewById(R.id.ReltveUsername);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();
        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    NetworkChangeUsername networkChangeUsername = new NetworkChangeUsername(Settings.this);
                    networkChangeUsername.execute(id, NewUsername.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void ChangeEmail(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changeemail, null);


        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText NewUEmail=(EditText)view1.findViewById(R.id.New_Email);
        Button Update=(Button)view1.findViewById(R.id.UpdateEmail_Btn);
        progressBar=(ProgressBar)view1.findViewById(R.id.progressbar);
        relativeLayout=(RelativeLayout)view1.findViewById(R.id.ReltveUsername);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();

        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();

                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    NetworkChangeEmail networkChangeEmail = new NetworkChangeEmail(Settings.this);
                    networkChangeEmail.execute(id, NewUEmail.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void ChangrPassword(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changepassword, null);
        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText Oldpassword=(EditText)view1.findViewById(R.id.OldPasseord);
        final EditText NewPassword=(EditText)view1.findViewById(R.id.New_Password);
        final EditText NewPassword2=(EditText)view1.findViewById(R.id.New_Password2);
        Button Update=(Button)view1.findViewById(R.id.UpdatePassword_Btn);
        progressBar=(ProgressBar)view1.findViewById(R.id.progressbar);
        relativeLayout=(RelativeLayout)view1.findViewById(R.id.ReltveUsername);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();

        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");

                    if(NewPassword.getText().toString().equals(NewPassword2.getText().toString())){
                        byte[]md5input=NewPassword.getText().toString().getBytes();
                        BigInteger md5data=null;

                        try {
                            md5data=new BigInteger(1,md5.encryptMD5(md5input));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String md5st = md5data.toString(16);
                        byte[]md5inputO=Oldpassword.getText().toString().getBytes();
                        BigInteger md5dataO=null;

                        try {
                            md5dataO=new BigInteger(1,md5.encryptMD5(md5inputO));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String md5stOld = md5dataO.toString(16);
                        NetworkChangePassword networkChangePassword = new NetworkChangePassword(Settings.this);
                        networkChangePassword.execute(id, md5stOld,md5st);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "new Password Does Not Match", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void ChangeAddress(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changeaddress, null);


        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText NewAddress=(EditText)view1.findViewById(R.id.New_Address);
        Button Update=(Button)view1.findViewById(R.id.UpdateAddress_Btn);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();

        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    NetworkChangeAddress changeAddress = new NetworkChangeAddress(Settings.this);
                    changeAddress.execute(id, NewAddress.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void ChangeNumber(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changenumber, null);


        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText NewNumber=(EditText)view1.findViewById(R.id.New_Number);
        Button Update=(Button)view1.findViewById(R.id.Updatenumber_Btn);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();

        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    NetworkChangeNumber changeNumber = new NetworkChangeNumber(Settings.this);
                    changeNumber.execute(id, NewNumber.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void ChangePharmacy(View view){
        AlertDialog.Builder mbelder = new AlertDialog.Builder(Settings.this);
        View view1 = getLayoutInflater().inflate(R.layout.changepharmacyname, null);


        ImageView Close=(ImageView) view1.findViewById(R.id.Close);
        final EditText NewPharmacy=(EditText)view1.findViewById(R.id.New_Pharmacy);
        Button Update=(Button)view1.findViewById(R.id.UpdatePharmacy_Btn);
        mbelder.setView(view1);
        final AlertDialog dialog = mbelder.create();

        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                boolean isConnected=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
                if(isConnected==true) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    NetworkChangePharmacy networkChangePharmacy = new NetworkChangePharmacy(Settings.this);
                    networkChangePharmacy.execute(id, NewPharmacy.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Connect to Network  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
