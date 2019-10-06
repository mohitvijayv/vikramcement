package com.icos.expensemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ShowDataPage2 extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_show_data_page2);

        listView=findViewById(R.id.listviewid2);
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


        FirebaseFirestore.getInstance().collection("expenses").whereEqualTo("email",MainActivity.emailHead).whereEqualTo("month",getIntent().getStringExtra("month")).whereEqualTo("year",Double.parseDouble(getIntent().getStringExtra("year"))).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                task.getResult().forEach(new Consumer<QueryDocumentSnapshot>() {
                    @Override
                    public void accept(QueryDocumentSnapshot queryDocumentSnapshot) {
                        queryDocumentSnapshot.getData().forEach(new BiConsumer<String, Object>() {
                            @Override
                            public void accept(String s, Object o) {
                                arrayList.add(s+": "+o.toString()+"\n");

                            }

                            @Override
                            public BiConsumer<String, Object> andThen(BiConsumer<? super String, ? super Object> after) {
                                return null;
                            }
                        });

                    }
                });
                arrayAdapter.notifyDataSetChanged();

            }
        });


    }
}
