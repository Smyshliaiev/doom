package com.pager.doom.smyshliaiev.anton.doompager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MainActivity extends ActionBarActivity  {

    private int PICK_IMAGE = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PREFS_NAME = "DOOMER";
    public static final String PREFS_DATA = "PREFS_DATA";
    private DirectionManager mDirectionManager = new DirectionManager();
    private TextView mTextInfo;
    private ImageView mImageView;
    private SharedPreferences mSettings;
    private PreferenceObjectManager mPreferenceObjectManager = new PreferenceObjectManager();
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInfo = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);



    }

    @Override
    protected void onStart() {
        super.onStart();

        loadDataFromPrefs();
        updateCurrentInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToPrefs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickChoosePic(View view){
        startGallery();
    }

    public void onClickMakePhoto(View view){
        dispatchTakePictureIntent();
    }

    public void onClickGoForward(View view){
        mDirectionManager.moveFrw();
        updateCurrentInfo();
    }

    public void onClickTurnLeft(View view){
        mDirectionManager.turnLeft();
        updateCurrentInfo();
    }

    public void onClickTurnRight(View view){
        mDirectionManager.turnRight();
        updateCurrentInfo();
    }

    public void onClickGoBack(View view) {
        mDirectionManager.moveBack();
        updateCurrentInfo();
    }

    private void updateCurrentInfo(){
        mTextInfo.setText("(" + mDirectionManager.getX() + ", " + mDirectionManager.getY() + ") " + mDirectionManager.getDir());
        updateImageViewFromPath();
    }

    private void updateImageViewFromPath(){

        if(checkIsAppStartedFirstTime()) return;

        PrefferenceObject prefObject =  new PrefferenceObject();
        prefObject.x = mDirectionManager.getX();
        prefObject.y = mDirectionManager.getY();
        prefObject.direction = mDirectionManager.getDir();

        String bitmap_path = mPreferenceObjectManager.getPrefferenceObjects().get(prefObject.hashCode());
        if(bitmap_path!=null) {
            File file = new File(bitmap_path);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                mImageView.setImageBitmap(bitmap);
            }
        }
        else{
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void startGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        PrefferenceObject prefObject =  new PrefferenceObject();
        prefObject.x = mDirectionManager.getX();
        prefObject.y = mDirectionManager.getY();
        prefObject.direction = mDirectionManager.getDir();


        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                mImageView.setImageURI(selectedImageUri);
                String realPath = getRealPathFromURI(getApplicationContext(), selectedImageUri);
                System.out.println("selectedImageUri realPath: " + realPath);
                addDataToPrefsHashMap(prefObject, realPath);
            }

            if (requestCode == REQUEST_TAKE_PHOTO) {

                File file = new File(mCurrentPhotoPath);

                if(file.exists()){

                    Log.d(TAG, "onActivityResult file: " + file.getAbsolutePath());
                    galleryAddPic();
                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    mImageView.setImageBitmap(myBitmap);
                }
                addDataToPrefsHashMap(prefObject, mCurrentPhotoPath);
            }
        }


    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private File createImageFile() throws IOException {
        Log.d(TAG, "start createImageFile");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.d(TAG, "end createImageFile with mCurrentPhotoPath: " + mCurrentPhotoPath);

        return image;
    }


    private void dispatchTakePictureIntent() {
        Log.d(TAG, "start dispatch photo");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

        Log.d(TAG, "end dispatch photo");
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    private void saveDataToPrefs(){

        if(mPreferenceObjectManager.getPrefferenceObjects().size()==0){
            return;
        }

        Gson gson = new Gson();

        mSettings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mSettings.edit();
        String jsonText = gson.toJson(mPreferenceObjectManager.getPrefferenceObjects());
        System.out.println("saveDataToPrefs json = " + jsonText);
        ed.putString(PREFS_DATA, jsonText);
        ed.commit();
    }

    private void addDataToPrefsHashMap(PrefferenceObject img_info, String path) {

        mPreferenceObjectManager.getPrefferenceObjects().put(img_info.hashCode(), path);
        saveDataToPrefs();
    }



    private PreferenceObjectManager loadDataFromPrefs(){
        mSettings = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonText = mSettings.getString(PREFS_DATA, null);
        System.out.println("loadDataFromPrefs json = " + jsonText);

        Type stringStringMap = new TypeToken<Map<Integer, String>>(){}.getType();
        Map<Integer, String> map = gson.fromJson(jsonText, stringStringMap);


        if(map!=null) {
            mPreferenceObjectManager.setPrefferenceObjects(map);
        }
        return mPreferenceObjectManager;
    }

    private boolean checkIsAppStartedFirstTime(){
        if(loadDataFromPrefs().getPrefferenceObjects().size() == 0) return true;
        return false;
    }
}
