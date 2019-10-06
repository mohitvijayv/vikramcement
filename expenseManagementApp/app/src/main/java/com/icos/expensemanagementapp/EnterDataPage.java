package com.icos.expensemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


public class EnterDataPage extends AppCompatActivity {
    EditText canteen, telephone, petrol, hospital, swimming, gym, cinema, generalstore;
    double canteenf=0, telephonef=0, petrolf=0, hospitalf=0, swimmingf=0, gymf=0, cinemaf=0, generalstoref=0;
    Button addExpense;
    HashMap<String,Object> hm=new HashMap<>();
    String documentReference;
    String month_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data_page);
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        final Calendar calendar=Calendar.getInstance();
        month_name = month_date.format(calendar.getTime());
        addExpense=findViewById(R.id.addexpense);
        canteen=findViewById(R.id.canteenid);
        telephone=findViewById(R.id.telephoneid);
        petrol=findViewById(R.id.petrolid);
        hospital=findViewById(R.id.hospitalid);
        swimming=findViewById(R.id.swimmingid);
        gym=findViewById(R.id.gymid);
        cinema=findViewById(R.id.cinemaid);
        generalstore=findViewById(R.id.generalstoreid);

        FirebaseFirestore.getInstance().collection("expenses").whereEqualTo("email",MainActivity.emailHead).whereEqualTo("year",calendar.get(Calendar.YEAR)).whereEqualTo("month",month_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Iterator<QueryDocumentSnapshot> iterator=task.getResult().iterator();
                    while (iterator.hasNext())
                    {
                        DocumentSnapshot documentSnapshot =iterator.next();
                        documentReference=documentSnapshot.getId();

                        //try {
                            canteenf+=Double.parseDouble(documentSnapshot.get("canteen").toString());
                            telephonef+=(Double)documentSnapshot.get("telephone");
                            petrolf+=(Double)documentSnapshot.get("petrol");
                            hospitalf+=(Double)documentSnapshot.get("hospital");
                            swimmingf+=(Double)documentSnapshot.get("swimming");
                            gymf+=(Double)documentSnapshot.get("gym");
                            cinemaf+=(Double)documentSnapshot.get("cinema");
                            generalstoref+=(Double)documentSnapshot.get("generalstore");
                        //} catch (Exception e) {
                            Log.d("yeah","no");
                        //}

                    }
                }
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canteenf+=Float.parseFloat(canteen.getText().toString());
                hm.put("canteen",canteenf);
                telephonef+=Float.parseFloat(telephone.getText().toString());
                hm.put("telephone",telephonef);
                petrolf+=Float.parseFloat(petrol.getText().toString());
                hm.put("petrol",petrolf);
                hospitalf+=Float.parseFloat(hospital.getText().toString());
                hm.put("hospital",hospitalf);
                swimmingf+=Float.parseFloat(swimming.getText().toString());
                hm.put("swimming",swimmingf);
                gymf+=Float.parseFloat(gym.getText().toString());
                hm.put("gym",gymf);
                cinemaf+=Float.parseFloat(cinema.getText().toString());
                hm.put("cinema",cinemaf);
                generalstoref+=Float.parseFloat(generalstore.getText().toString());
                hm.put("generalstore",generalstoref);
                hm.put("email",MainActivity.emailHead);

                hm.put("month",month_name);
                hm.put("year", (float) calendar.get(Calendar.YEAR));
                if(documentReference!=null)
                {
                    FirebaseFirestore.getInstance().collection("expenses").document(documentReference).set(hm);

                }
                else
                {
                    FirebaseFirestore.getInstance().collection("expenses").add(hm);
                }

                startActivity(new Intent(EnterDataPage.this,OptionPage.class));
            }
        });



    }
}
