package com.example.cranesmart.Api.imageuploadnotforbase64;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class PictureUpload {

    private static final String TAG = PictureUpload.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE1 = 6385;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    private static final int REQUEST_CODE_ASK_PERMISSIONS1 = 123;
    private static PictureUpload pictureUpload;
    Uri fileUri;
    String photoPath = "";
    private Context context;
    private Activity activity;
    private Bitmap bitmap;
    Fragment fragment;


    public PictureUpload(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    /*public PictureUpload(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }*/

    private PictureUpload() {
    }

    public static PictureUpload getInstance() {
        if (pictureUpload == null) {
            pictureUpload = new PictureUpload();
        }
        return pictureUpload;
    }


    public void showChooser() {
        Intent target = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            target = FileUtils.createGetContentIntent();
        }
        Intent intent = Intent.createChooser(
                target,"Select gallery");
        try {

            activity.startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void showCameraChooser() {
        // Use the GET_CONTENT intent from the utility class
        String fileName = System.currentTimeMillis() + ".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        activity.startActivityForResult(intent, REQUEST_CODE1);
    }
    public Uri onActivityResultUri(int requestCode, int resultCode, Intent data, Uri uri_image, ImageView imageView) {
        switch (requestCode) {
            case REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        final Uri uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                         imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "@Uri = " + uri.toString());
                        try {
                            final String path;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                path = FileUtils.getPath(context, uri);
                            }
                            uri_image = uri;
                            Log.d("@@uri", String.valueOf(uri));

                        } catch (Exception e) {
                            Log.e(TAG, "@File select error", e);
                        }
                    }
                }

                break;

            case REQUEST_CODE1:

                if (resultCode == RESULT_OK) {
                    try {
                        photoPath = getPath(fileUri);
                        System.out.println("Image Path : " + photoPath);

                        Bitmap b = decodeUri(fileUri);
                       imageView.setImageBitmap(b);

                        uri_image = fileUri;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
        return uri_image;
    }

    public Uri onActivityResultUriNew(int requestCode, int resultCode, Intent data, Uri uri_image) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        final Uri uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                          //  imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "@Uri = " + uri.toString());
                        try {
                            final String path;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                path = FileUtils.getPath(context, uri);
                            }


                            uri_image = uri;

                        } catch (Exception e) {
                            Log.e(TAG, "@File select error", e);
                        }
                    }
                }

                break;

            case REQUEST_CODE1:

                if (resultCode == RESULT_OK) {
                    try {
                        photoPath = getPath(fileUri);
                        System.out.println("Image Path : " + photoPath);

                        Bitmap b = decodeUri(fileUri);
                       // imageView.setImageBitmap(b);

                        uri_image = fileUri;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
        return uri_image;
    }
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 72, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 50;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;

            height_tmp /= 2;

            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
    }

    @SuppressWarnings("deprecation")
    private String getPath(Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};


        Cursor cursor = activity.managedQuery(selectedImaeUri, projection, null, null,
                null);
        // Cursor  cursor = activity.getContentResolver().query(selectedImaeUri, projection, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }
    public void askForPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Camera and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                activity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                REQUEST_CODE_ASK_PERMISSIONS
                        );
                        showChooser();
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_PERMISSIONS
                );


            }
        } else {
            showChooser();
        }
    }
    public void askForCameraPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Camera and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                activity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                REQUEST_CODE_ASK_PERMISSIONS1
                        );

                        showCameraChooser();
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_PERMISSIONS1
                );


            }
        } else {
            showCameraChooser();
            // Do something, when permissions are already granted
            //Toast.makeText(mContext,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(context, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(context, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }

    public void showPictureDialog() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askForPermission();
        }
       /* AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
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
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    askForPermission();
                                }
                                break;
                            case 1:
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    //askForCameraPermission();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Uri> onActivityResult(int requestCode, int resultCode, Intent data, ArrayList<Uri> arrayList, ImageView imageView) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {

                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;
                            Log.d("@Uri Selected", imageUri.toString());

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                String path = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    path = FileUtils.getPath(context, imageUri);
                                }
                                Log.d("@Multiple File Selected", path);

                                arrayList.add(imageUri);

                            } catch (Exception e) {
                                Log.e(TAG, "@File select error", e);
                            }
                        }
                    } else if (data.getData() != null) {
                        final Uri uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "@Uri = " + uri.toString());
                        try {
                            final String path;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                path = FileUtils.getPath(context, uri);
                            }


                            arrayList.add(uri);

                        } catch (Exception e) {
                            Log.e(TAG, "@File select error", e);
                        }
                    }

                }
                break;

            case REQUEST_CODE1:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    //arrayList.clear();

                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = null;
                                path = FileUtils.getPath(context, imageUri);
                                Log.d("Multiple File Selected", path);

                                arrayList.add(imageUri);

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path;
                            path = FileUtils.getPath(context, uri);
                            Log.d("Single File Selected", path);
                            arrayList.add(uri);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
                break;

        }
        return arrayList;
    }

    @NonNull
    public MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            file = FileUtils.getFile(context, fileUri);
        }

        // create RequestBody instance from file
        RequestBody requestFile =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            requestFile = RequestBody.create(
                    MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(fileUri))),
                    file
            );
        }

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public MultipartBody.Part prepareFilePart1(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(context, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            requestFile = RequestBody.create(
                    MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(fileUri))),
                    file
            );
        }

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public String onActivityResultUri(int requestCode, int resultCode, Intent data, String uri_image, ImageView imageView) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        final Uri uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "@Uri = " + uri.toString());
                        try {
                            final String path;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                path = FileUtils.getPath(context, uri);
                            }
                            uri_image = encodeImage(bitmap);

                        } catch (Exception e) {
                            Log.e(TAG, "@File select error", e);
                        }
                    }
                }

                break;

            case REQUEST_CODE1:
                try {
                    photoPath = getPath(fileUri);
                    System.out.println("Image Path : " + photoPath);

                    Bitmap b = decodeUri(fileUri);
                    imageView.setImageBitmap(b);

                    uri_image = encodeImage(b);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }

        return uri_image;

    }
}
