package com.example.cranesmart.fragment;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.R;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.example.cranesmart.pojo.editprofile.Editprofile;
import com.example.cranesmart.pojo.registerlogin.ex;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyprofileFragment extends Fragment {
    private static final int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/encoded_image";
    Uri imageuri = null;
    public static String PACKAGE_NAME;
    CircleImageView ProfileImg;
    EditText editprofile;
     TextView editemail,editphone,editpassword;
    Button savebutton;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        ProfileImg=view.findViewById(R.id.profileimg);
        editprofile=view.findViewById(R.id.editprofile);
        SharedPreferences preferences=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("statusdash","0");
        editor.commit();
        editor.apply();
        editemail=view.findViewById(R.id.editemail);
        editphone=view.findViewById(R.id.editphone);
        String    TAG="@@method";
        userdetail();
        savebutton=view.findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if(editprofile.getText().toString().isEmpty()){
            editprofile.setError("Enter your name");
        }
        else{
 usereditprofile();}
    }
});
        ProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    return view;
    }


    private void usereditprofile() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //getting the user values

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);
        SharedPreferences sh=getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
        final String nam= pref.getString("name","0");
        final String email= editemail.getText().toString();
        final String name= editprofile.getText().toString();
        final String mobil= editphone.getText().toString();
        //encode image to base64 string
        ProfileImg.buildDrawingCache();
        Bitmap bm = ProfileImg.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
         final String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);


        Log.d("usereditprofile", encodedImage);


//        base64.setText(encodedImage);
        Call<Editprofile> call = service.profile(value,name,encodedImage,mobil,email);
        call.enqueue(new Callback<Editprofile>() {
            @Override
            public void onResponse(Call<Editprofile> call, Response<Editprofile> response) {
                progressDialog.dismiss();
                Log.d("@@userimage", "onResponse: "+response.body().getData().getPhoto());
                Log.d("@@usernameupdate", "onResponse: "+response.body().getData().getName().toString().trim());
            }

            @Override
            public void onFailure(Call<Editprofile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
 private void userdetail() {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //getting the user values

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        // User user = new User(name, email, password, gender);
        SharedPreferences sh=getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String value=sh.getString("userID","0"); assert value != null;



//        base64.setText(encodedImage);
        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
                progressDialog.dismiss();
                Glide.with(getContext()).load(APIUrl.IMG_URL+response.body().getUserDetail().getProfilePhoto()).placeholder(R.drawable.circleimg1).into(ProfileImg);
                editprofile.setText(response.body().getUserDetail().getName().toString().trim());
                editemail.setText(response.body().getUserDetail().getEmail().trim());
                editphone.setText(response.body().getUserDetail().getMobile());
                Log.d("@@userimageprofofile", "onResponse: "+response.body().getUserDetail().getProfilePhoto());
            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }




    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        AlertDialog alertDialog = pictureDialog.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.button);
        alertDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
        else{
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("You have to give permission to take pictures and record video from camera ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package",getContext().getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            Log.d("Addtocart","Cart");
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });



            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.button);
            alertDialog.show();
        }


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
//                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    ProfileImg.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ProfileImg.setImageBitmap(thumbnail);
            saveImage(thumbnail);
//            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}

