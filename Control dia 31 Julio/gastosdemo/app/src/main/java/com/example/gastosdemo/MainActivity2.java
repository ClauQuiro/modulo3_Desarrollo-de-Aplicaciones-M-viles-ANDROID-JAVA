package com.example.gastosdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private EditText cta,mes,mes2,valor,valor2;
    private TextView tvr;

    private Spinner spctacomp;
    public Spinner listagastosC;
    public Spinner listamesC;
    public String SmesC;
    public String SmesC2;
    public String SctaC;
    public String SvalorC;
    ArrayList<String> listaCuentas;
    ArrayList<Cuentas> cuentasLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cta=findViewById(R.id.editselecomp);
        mes=findViewById(R.id.editmesC);
        mes2=findViewById(R.id.editmesC2);
        valor=findViewById(R.id.editvalorC);
        valor2=findViewById(R.id.editvalorC2);
        tvr=findViewById(R.id.tvresultado);


        //sp gastos
        listagastosC = findViewById(R.id.spcuentasC);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_gastos, android.R.layout.simple_spinner_item);
        listagastosC.setAdapter(adapter);
        listagastosC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                cta.setText(parent.getItemAtPosition(position).toString());
                valor.setText("");
                valor2.setText("");
                tvr.setText("");
               // mes.setText(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //primera consulta
        listamesC = findViewById(R.id.spmesC);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.lista_mes, android.R.layout.simple_spinner_item);
        listamesC.setAdapter(adapter1);
        listamesC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                mes.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //segunda consulta
        listamesC = findViewById(R.id.spmesC2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.lista_mes, android.R.layout.simple_spinner_item);
        listamesC.setAdapter(adapter1);
        listamesC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                mes2.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
    public void buscarCta(View v){
        Admindb admindb = new Admindb(this,"CtasBasic",null,1);
        SQLiteDatabase base = admindb.getWritableDatabase();
        //String Scodigo=codigo.getText().toString();
        //if (!Smes.isEmpty()){
        SmesC=mes.getText().toString();
        SctaC=cta.getText().toString();
        String Scodigo= SmesC+SctaC;
        //Toast.makeText(this,Scodigo,Toast.LENGTH_LONG).show();

        Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '"+Scodigo+"'",null);
        //Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '1Luz'",null);
        if (fila.moveToFirst()){

            //cta.setText(fila.getString(0));
            valor.setText(fila.getString(1));
            base.close();
            //codigo.setText("");
            //nombre.setText("");
            //precio.setText("");

        } else {
            Toast.makeText(this,"Esa cuenta no esta ingresada",Toast.LENGTH_LONG).show();

        }



    }
    public void buscarCta2(View v){
        Admindb admindb = new Admindb(this,"CtasBasic",null,1);
        SQLiteDatabase base = admindb.getWritableDatabase();
        //String Scodigo=codigo.getText().toString();
        //if (!Smes.isEmpty()){
        SmesC=mes2.getText().toString();
        SctaC=cta.getText().toString();
        String Scodigo= SmesC+SctaC;
        //Toast.makeText(this,Scodigo,Toast.LENGTH_LONG).show();

        Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '"+Scodigo+"'",null);
        //Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '1Luz'",null);
        if (fila.moveToFirst()){

            //cta.setText(fila.getString(0));
            valor2.setText(fila.getString(1));
            base.close();
            //codigo.setText("");
            //nombre.setText("");
            //precio.setText("");

        } else {
            Toast.makeText(this,"Esa cuenta no esta ingresada",Toast.LENGTH_LONG).show();

        }



    }
    public void Resultado(View v){

        int val1= Integer.parseInt(valor.getText().toString());
        int val2= Integer.parseInt(valor2.getText().toString());
        int resta= val1-val2;
        SmesC2=mes2.getText().toString();

        SmesC=mes.getText().toString();
        SctaC=cta.getText().toString();

        tvr.setText("La diferencia en la cuenta de "+ SctaC+" entre el mes "+SmesC+" y el mes :"+SmesC2+" es:"+resta);

    }
}