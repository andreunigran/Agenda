package br.unigran.agenda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.unigran.agenda.domain.Contato;

public class Segunda extends AppCompatActivity {
    private final int CAMERA=1;
    private final int AGENDA=2;
    private ImageView imageView;
    private EditText numero;
    private EditText nomeContato;
    private Contato contato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        imageView=findViewById(R.id.imagemContato);
        numero=findViewById(R.id.numeroContato);
        nomeContato=findViewById(R.id.nomeContato);
        contato= new Contato();
    }
    public void chamarCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA && resultCode==RESULT_CANCELED){
            Toast.makeText(this,"Captura cancelada",Toast.LENGTH_SHORT).show();
        }
        if(requestCode==CAMERA && resultCode==RESULT_OK){

            Toast.makeText(this,"Captura confirmada",Toast.LENGTH_SHORT).show();
            Bundle dados = data.getExtras();
            Bitmap imagem = (Bitmap) dados.get("data");

            imageView.setImageBitmap(imagem);

            contato.setFoto(imagem);
        }

        if(requestCode==AGENDA && resultCode==RESULT_OK){
            Uri uri = data.getData();
            String[] projecao = {ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};



            Cursor cursor = getContentResolver().query(uri, projecao, null, null, null);

            //SE O CURSOR RETORNAR UM VALOR VALIDO ENTÃO PEGA O NUMERO
            if (cursor != null && cursor.moveToFirst()) {
                contato.setNumeroTelefone(cursor.getString(0));
                contato.setNome(cursor.getString(1));

                numero.setText(contato.getNumeroTelefone());
                nomeContato.setText(contato.getNome());
            //   Toast.makeText(Segunda.this, numero, Toast.LENGTH_LONG).show();
            }


        }
    }

    public void importarContato(View view) {

        Intent contatos = new Intent(Intent.ACTION_PICK); //CHAMANDO UMA ACTIVITY COM A CONSTANTE DE ESCOLHER UM DADO A SER RETORNADO
        contatos.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); //SELECIONANDO O CONTEUDO UTILIZANDO A CONTACTS PROVIDER

        //VALIDANDO
        if (contatos.resolveActivity(getPackageManager()) != null){
            //CHAMO OS CONTATOS
            startActivityForResult(contatos, AGENDA);

        }
    }
}