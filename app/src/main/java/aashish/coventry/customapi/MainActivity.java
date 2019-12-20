package aashish.coventry.customapi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageview;
    Button btn;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = findViewById(R.id.imageView);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            }
        }

        Uri uri = data.getData();
        imageview.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
 //       previewImage(imagePath);
    }

    private void setimageUri(Uri uri) {
        File imgFIle = new File(imagePath);
        if(imgFIle.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFIle.getAbsolutePath());
            imageview.setImageBitmap(myBitmap);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

}
