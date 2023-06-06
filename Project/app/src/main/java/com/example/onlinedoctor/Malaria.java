package com.example.onlinedoctor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.onlinedoctor.ml.MalariaDetector;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Malaria extends AppCompatActivity {

    TextView result;
    ImageView imageView;
    Button camera,gallery;
    int imageSize = 130;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malaria);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
        camera = findViewById(R.id.camera);
        gallery= findViewById(R.id.gallery);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(m, 2);
            }
        });

    }public void classifyImage(Bitmap image){
        try {
            MalariaDetector model = MalariaDetector.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 130, 130, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());


            for (int value : intValues){
                byteBuffer.putFloat((float) Color.red(value)/255.f);
                byteBuffer.putFloat((float) Color.green(value)/255.f);
                byteBuffer.putFloat((float) Color.blue(value)/255.f);
            }


            inputFeature0.loadBuffer(byteBuffer);

            MalariaDetector.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();

            if((float)confidences[0] > 0.5)
                result.setText("Uninfected: "+" "+String.format("%.2f",(confidences[0])));
            else
                result.setText("Parasitized: "+" "+String.format("%.2f",(1-confidences[0])));

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        else if(requestCode==2 && resultCode == RESULT_OK){
            Uri uri=data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}