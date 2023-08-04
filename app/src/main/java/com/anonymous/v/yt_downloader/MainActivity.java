//Warning
// Unauthorized use, reproduction, or distribution of this code, in whole or in part, without the explicit permission of the owner, is strictly prohibited and may result in severe legal consequences under the relevant IT Act and other applicable laws.
// To use this code, you must first obtain written permission from the owner. For inquiries regarding licensing, collaboration, or any other use of the code, please contact virendratarte22@gmail.com.
// Thank you for respecting the intellectual property rights of the owner.
package com.anonymous.v.yt_downloader;


import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {

    EditText edittext;
    Button button;
    String downloadurl;
    String dandt;
    String dandt2;
    int random_num;
    //String path = "/storage/emulated/0/Download/YT_Downloader/";
    //youtube pattern
    String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        edittext = findViewById(R.id.edittext);
        button = findViewById(R.id.download_button);

        //removing action bar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        YouTubeUriExtractor downloader = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {


                if(ytFiles != null){

                    int itag = 22;

                    try {
                        downloadurl = ytFiles.get(itag).getUrl();

                        if(downloadurl != null){

                            Toast.makeText(MainActivity.this,"Download Started....",Toast.LENGTH_SHORT).show();

                            downloadvideo(downloadurl);

                            Log.d("Download Url","URL :-"+downloadurl);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"This Url Cannot Be Downloaded...",Toast.LENGTH_SHORT).show();
                        }

                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, "This url is not fetched !!!",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edittext.getText().toString().toString();

                if(url != null && url.matches(pattern)){

                    downloader.execute(url);
                }
                else if(url == null){
                    Toast.makeText(MainActivity.this,"Please Enter A Url",Toast.LENGTH_SHORT).show();
                }
                else if(!url.matches(pattern)){
                    Toast.makeText(MainActivity.this,"Please Enter A Valid Url",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Somthing went wrong Try Again After Some Time ",Toast.LENGTH_SHORT).show();
                }

                //getting current date and time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                dandt = sdf.format(new Date());
                //dandt2 = dandt.toString();
                //random numbers generator
                Random random = new Random();
                random_num = random.nextInt(100 - 0)+0;





            }
        });

    }



    void downloadvideo(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("YT Downloader");
        request.setDescription("Your YouTube Video Is Downloading...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED | DownloadManager.Request.VISIBILITY_VISIBLE);
        //setting value to name of file
        String video_name = "YT_".concat(dandt).concat("_").concat(String.valueOf(random_num)).concat(".mp4");

        request.setDestinationInExternalFilesDir(MainActivity.this, DIRECTORY_DOWNLOADS,video_name);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

}
