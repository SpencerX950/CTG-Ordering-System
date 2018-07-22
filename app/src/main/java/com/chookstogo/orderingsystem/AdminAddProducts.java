package com.chookstogo.orderingsystem;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
/** https://learnpainless.com/android/how-to-create-textarea-in-android
 *  http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
 *  http://square.github.io/picasso/
 *  https://github.com/square/picasso
*/
public class AdminAddProducts extends AppCompatActivity {

    Button btnSave,btnCancel,btnChooseFile;
    EditText txtName,txtPrice,txtDescription;
    ImageView imgItem;
    Spinner spinCategory, spinStatus;
    ProgressBar prgbarImage,progbarProduct;
    Product product = new Product();
    String[] category = {"Cooked","Raw","Processed"};
    String[] status = {"Available","Out of Stock"};

    private Uri imgUri;
    private int PICK_IMAGE_REQUEST = 1;
    private DatabaseReference dbRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_products);
       //Instantiation
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnChooseFile = findViewById(R.id.btnChooseFile);
        txtName = findViewById(R.id.txtName);
        spinCategory = findViewById(R.id.spinCategory);
        spinStatus = findViewById(R.id.spinStatus);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        imgItem = findViewById(R.id.imgItem);
        prgbarImage = findViewById(R.id.prgbarImage);
        progbarProduct = findViewById(R.id.progbarProduct);
        //
        dbRef = FirebaseDatabase.getInstance().getReference("Product");
        storageRef = FirebaseStorage.getInstance().getReference("ProductUploads");

        //Array Adapter to fill spinner with category array
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(AdminAddProducts.this,R.layout.support_simple_spinner_dropdown_item,category);
        spinCategory.setAdapter(adapterCategory);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(AdminAddProducts.this,R.layout.support_simple_spinner_dropdown_item,status);
        spinStatus.setAdapter(adapterStatus);

        //Set listeners
        setButtonListeners();
        setTextListeners();

        progbarProduct.setVisibility(View.GONE);
    }

    class AddProductsListener implements View.OnClickListener
    {
        @Override
        public void onClick(View button)
        {
            switch (button.getId())
            {
                case R.id.btnSave : Save(); break;
                case R.id.btnCancel : Cancel(); break;
                case R.id.btnChooseFile: OpenFileChooser(); break;
                default: break;
            }
        }
    }

    // Choosing File Image from Gallery
    public void OpenFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {
        super.onActivityResult(request,result,data); //call itself

        if(request == PICK_IMAGE_REQUEST && result == RESULT_OK && data != null && data.getData() != null)
        {
            imgUri = data.getData(); // transfer data to uri
            Picasso.get().load(imgUri).into(imgItem);
        }
    }
    // End of Choosing File Image from Gallery

    //Uploading Product and Image to Firebase
    public void Save()
    {
        progbarProduct.setVisibility(View.VISIBLE);

       if(txtName.getText().toString().isEmpty())
           MissingFields(txtName);
       if(txtPrice.getText().toString().isEmpty())
            MissingFields(txtPrice);
       if(txtDescription.getText().toString().isEmpty())
            MissingFields(txtDescription);
        if(imgUri == null)
            MissingFields(imgItem);

       product.setName(txtName.getText().toString());
       product.setPrice(txtPrice.getText().toString());
       product.setDescription(txtDescription.getText().toString());
       product.setCategory(((Spinner) findViewById(R.id.spinCategory)).getSelectedItem().toString());
       product.setStatus(((Spinner) findViewById(R.id.spinStatus)).getSelectedItem().toString());
       product.setImage(System.currentTimeMillis() + "." + getFileExtension(imgUri));

       Log.d("Item Map >>", String.valueOf(product.getItemMap()));
//
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
//        dbRef.push().setValue(product.getItemMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                {
//                    progbarProduct.setVisibility(View.GONE);
//                    Toast.makeText(AdminAddProducts.this, "Upload Product on Database Success!\n Please wait for the image to upload", Toast.LENGTH_SHORT).show();
////                    if(imgUri != null)
////                         UploadFile();
////                    else
////                        Toast.makeText(AdminAddProducts.this,"No Files Selected\nPlease proceed to Edit Products to Add Image",Toast.LENGTH_LONG).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progbarProduct.setVisibility(View.GONE);
//                Toast.makeText(AdminAddProducts.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void MissingFields(EditText field)
    {
        field.setError("This Field is Required");
        field.requestFocus();
        return;
    }

    private void MissingFields(ImageView image)
    {
        Toast.makeText(AdminAddProducts.this, "No Image File Selected", Toast.LENGTH_SHORT).show();
        image.requestFocus();
        return;
    }

    public String getFileExtension(Uri uri)
    {
        ContentResolver content = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        Log.d("IMAGE URI", String.valueOf(mime.getExtensionFromMimeType(content.getType(uri))));
        return mime.getExtensionFromMimeType(content.getType(uri));
    }

    public void UploadFile()
    {
        StorageReference fileRef = storageRef.child(product.getImage());
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prgbarImage.setProgress(0);
                    }
                }, 5000);
            }
            }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminAddProducts.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    prgbarImage.setProgress((int) progress);
                }
        });
    }
    //End of Uploading Product and Image to Firebase

    public void Cancel()
    {
        Intent intent = new Intent(AdminAddProducts.this,AdminPanel.class);
        startActivity(intent);
        finish();
    }

    public void setButtonListeners()
    {
        btnChooseFile.setOnClickListener(new AdminAddProducts.AddProductsListener());
        btnSave.setOnClickListener(new AdminAddProducts.AddProductsListener());
        btnCancel.setOnClickListener(new AdminAddProducts.AddProductsListener());
    }

    public void setTextListeners()
    {
        txtDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }
}
