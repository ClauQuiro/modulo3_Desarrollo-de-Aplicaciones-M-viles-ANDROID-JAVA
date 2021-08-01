package com.example.gastosdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private ContentValues objetodeContentValues;
    private EditText mes,cta,valor;
    public Spinner listagastos;
    public Spinner listames;
    public String Smes;
    public String Scta;
    public String Svalor;
    private Spinner spcta;
    public ArrayList<String> listaCuentas;
    public ArrayList<Cuentas> cuentasLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mes=findViewById(R.id.editmes);
        cta=findViewById(R.id.editsele);
        //mes=findViewById(R.id.editmes);
        valor=findViewById(R.id.editvalor);

       //sp gastos
        listagastos = findViewById(R.id.spctas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_gastos, android.R.layout.simple_spinner_item);
        listagastos.setAdapter(adapter);
        listagastos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                cta.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //spinner mes

        listames = findViewById(R.id.spmes);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.lista_mes, android.R.layout.simple_spinner_item);
        listames.setAdapter(adapter1);
        listames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                mes.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //listar Tabla cuentas
        spcta=findViewById(R.id.splistactas);
        consultarListaCuentas();
        ArrayAdapter<String> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaCuentas);
        spcta.setAdapter(adaptador);
    }

    public void grabarcta(View v){

        Admindb admindb = new Admindb(this,"CtasBasic",null,1);
        SQLiteDatabase base = admindb.getWritableDatabase();
        //Smes=mes.getText().toString();
        Smes=mes.getText().toString();
        Scta=cta.getText().toString();
        String Svalor=valor.getText().toString();


        if(!Smes.isEmpty() && !Scta.isEmpty() && !Svalor.isEmpty())
        {
            ContentValues crear = new ContentValues();
            crear.put("codigo",Smes+Scta);
            crear.put("precio",Svalor);
            base.insert("cuentas",null,crear);
            base.close();
            //mes.setText("");
            cta.setText("");
            valor.setText("");

            Toast.makeText(this,"Registro creado",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Debe completar todos los campos",Toast.LENGTH_LONG).show();
        }
    }
    public void buscarCta(View v){
        Admindb admindb = new Admindb(this,"CtasBasic",null,1);
        SQLiteDatabase base = admindb.getWritableDatabase();
        //String Scodigo=codigo.getText().toString();
        //if (!Smes.isEmpty()){
        Smes=mes.getText().toString();
        Scta=cta.getText().toString();
        String Scodigo= Smes+Scta;
        //Toast.makeText(this,Scodigo,Toast.LENGTH_LONG).show();

        Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '"+Scodigo+"'",null);
        //Cursor fila = base.rawQuery("select codigo,precio from cuentas where codigo = '1Luz'",null);
            if (fila.moveToFirst()){

              cta.setText(fila.getString(0));
              valor.setText(fila.getString(1));
              base.close();
                //codigo.setText("");
                //nombre.setText("");
                //precio.setText("");

            } else {
               Toast.makeText(this,"el producto no existe",Toast.LENGTH_LONG).show();

            }



    }
    public void consultarListaCuentas() {

        Admindb admindb = new Admindb(this, "CtasBasic", null, 1);
        SQLiteDatabase base = admindb.getWritableDatabase();
        Cuentas c1 = null;
        cuentasLista = new ArrayList<Cuentas>();

        Cursor fila = base.rawQuery("select * from cuentas",null);
        while(fila.moveToNext()){
            c1=new Cuentas();
            c1.setCodigo(fila.getString(0));
            c1.setValor(fila.getInt(1));

            cuentasLista.add(c1);
        }
        base.close();
        consultarCtas();

    }
    public void consultarCtas(){
        listaCuentas = new ArrayList<String>();
        for(int i=0;i<cuentasLista.size();i++){
            listaCuentas.add(cuentasLista.get(i).getCodigo()+"-"
                    +cuentasLista.get(i).getValor());
        }
    }
    public void modificarProducto(View view){


        Admindb admin = new Admindb(this, "CtasBasic", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();
        Smes=mes.getText().toString();
        Scta=cta.getText().toString();
        Svalor=valor.getText().toString();
        String Scodigo= Smes+Scta;

        if(!Smes.isEmpty() && !Scta.isEmpty()) {

            ContentValues modificar = new ContentValues();
            modificar.put("codigo", Scodigo);
            modificar.put("precio", Svalor);
            base.update("Cuentas",modificar,"codigo = '"+Scodigo+"'", null);
            base.close();

            //codigo.setText(null);
            valor.setText(null);
            //precio.setText(null);

            Toast.makeText(this, "Registro actualizado exitosamente", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
        }

    }
    public void EliminarProducto(View view){


        Admindb admin = new Admindb(this, "CtasBasic", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        Smes=mes.getText().toString();
        Scta=cta.getText().toString();
        Svalor=valor.getText().toString();
        String Scodigo= Smes+Scta;

        if(!Smes.isEmpty() && !Scta.isEmpty() && !Svalor.isEmpty()){

            ContentValues Eliminar = new ContentValues();
            Eliminar.put("codigo", Scodigo);
            Eliminar.put("precio", Svalor);

            base.delete("Cuentas", "codigo = '"+Scodigo+"'", null);
            base.close();

            valor.setText(null);
           Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
        }

    }
    public void comparar(View v) {
        Intent siguiente = new Intent(this,MainActivity2.class);
        startActivity(siguiente);
    }

}