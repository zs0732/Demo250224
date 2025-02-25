package com.example.demo250224;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] arr = new String[20];
    AlertDialog.Builder aDb;
    ArrayAdapter<String> adapter;

    double kefel=0;
    double X=0;
    int num = 1 ;

    double sum= 0 ;
    int position =0;
    Button btn, adHan, adHes;
    boolean sedra = false;
    String error = "Invalid input please enter again number";


    String str1 = "";
    String str2 = "";
    ListView listV;
    LinearLayout dialogCustom;

    EditText ab_a1, ab_kef;
    TextView x1, d, n, Sn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        listV = findViewById(R.id.listV);
        x1 = findViewById(R.id.x1);
        d = findViewById(R.id.d);
        n = findViewById(R.id.n);
        Sn = findViewById(R.id.Sn);

        listV.setOnItemClickListener(this);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void handsit(View view)
    {
        sedra = false;
    }
    public void hesbonit(View view )
    {
        sedra = true ;
    }

    public void enter_alert(View view) {
        dialogCustom = (LinearLayout) getLayoutInflater().inflate(R.layout.ziv_xml,null );
        adHan = (Button) dialogCustom.findViewById(R.id.ad_han);
        adHes = (Button) dialogCustom.findViewById(R.id.ad_hes);
        ab_a1 = (EditText) dialogCustom.findViewById(R.id.ab_a1);
        ab_kef = (EditText) dialogCustom.findViewById(R.id.ab_kef);

        aDb = new AlertDialog.Builder(this);
        aDb.setView(dialogCustom);

        aDb.setPositiveButton("Enter ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                str1 = ab_a1.getText().toString();
                str2 = ab_kef.getText().toString();
                if (str1.isEmpty() || str1.equals("-") || str1.equals("-.") || str1.equals("+") || str1.equals("+.") || str2.isEmpty() || str2.equals("-") || str2.equals("-.") || str2.equals("+") || str2.equals("+."))
                {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    kefel= Double.parseDouble(str2);
                    X = Double.parseDouble(str1);
                    for (int i = 0; i <20; i++) {
                        double term;
                        if (sedra) {
                            term = X + i * kefel;
                        } else {
                            term = X * Math.pow(kefel, i);
                        }
                        if(term>= 1000000 )
                        {
                            arr[i] = String.valueOf(bigNumSimplifier(term));
                        }
                        else
                        {
                            arr[i] = String.valueOf(term);
                        }
                    }
                    listV.setAdapter(adapter);

                }
            }
        });
        aDb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aDb.setNeutralButton("reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ab_a1.setText("Enter the first num ");
                kefel = 0 ;
                ab_kef.setText("Enter the kefel");
                X = 0 ;
                dialog.cancel();
            }

        });
        aDb.setCancelable(false);
        AlertDialog ad = aDb.create();
        ad.show();

    }

    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long rowid) {
        // הצגת האיבר הראשון
        x1.setText("X1 = " + str1);

        // הצגת מיקום האיבר הנבחר ב- D
        int position = pos + 1;
        d.setText("D = " + position);

        // חישוב סכום הסדרה עד האיבר שנבחר
        double sum = 0;
        for (int i = 0; i <= pos; i++) {
            if (arr[i] != null && !arr[i].isEmpty() && arr[i].matches("-?\\d+(\\.\\d+)?")) {
                sum += Double.parseDouble(arr[i]);
            }
        }

        // הצגת סכום הסדרה ב- N
        n.setText("N = " + sum);

        // הצגת סכום הסדרה גם ב- SN (אם את עדיין רוצה את זה)
        Sn.setText("SN = " + sum);
    }

    public boolean onOptionsItemSelected(MenuItem menu)
    {
        String num1 = menu.getTitle().toString();
        if(num1.equals("HOME"))
        {
            if(num == 1 )
            {

            }
            else
                finish();
        }
        else
        {
            Intent si = new Intent(this, page2.class);
            startActivity(si);

        }

        return super.onOptionsItemSelected(menu);

    }

    public  String bigNumSimplifier(double result) {
        String scientificNotation = String.format("%.4e", result);
        String[] parts = scientificNotation.split("e");
        double base = Double.parseDouble(parts[0]) / 10.0;
        int exponent = Integer.parseInt(parts[1]) + 1;
        return String.format("%.4f * 10^%d", base, exponent);
    }

}