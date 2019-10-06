package com.icos.expensemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;

public class ShowDataPage extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_page);
        listView=findViewById(R.id.listviewid);
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        FirebaseFirestore.getInstance().collection("expenses").whereEqualTo("email",MainActivity.emailHead).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                task.getResult().forEach(new Consumer<QueryDocumentSnapshot>() {
                    @Override
                    public void accept(QueryDocumentSnapshot queryDocumentSnapshot) {
                        Log.d("hey","found something");
                        String month_name=queryDocumentSnapshot.get("month").toString();
                        String yr=queryDocumentSnapshot.get("year").toString();
                        yr=yr.substring(0,yr.length()-2);
                        month_name=month_name+" "+yr;

                        arrayList.add(month_name);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ShowDataPage.this,ShowDataPage2.class);
                intent.putExtra("month",arrayList.get(i).substring(0,arrayList.get(i).indexOf(" ")));
                intent.putExtra("year",arrayList.get(i).substring(arrayList.get(i).indexOf(" ")+1));
                startActivity(intent);
            }
        });

    }
}
