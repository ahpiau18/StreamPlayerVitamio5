package com.cubixmobile.apps.streamplayervitamio5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.provider.MediaStore;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vitamio.isInitialized(this);

        Button buttonPlay = (Button) findViewById(R.id.btnPlay);
        Button buttonStop = (Button)findViewById(R.id.btnStop);

        if (buttonPlay != null) {
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    streamAudio("http://209.105.250.73:8650/autodj");
                }
            });
        }

        if (buttonStop != null) {
            buttonStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayer.stop();
                    mPlayer.reset();
                }
            });
        }
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

    private void streamAudio(String streamURL) {

        mPlayer = new MediaPlayer(this);
        try {
            mPlayer.setDataSource(streamURL);
            mPlayer.prepareAsync();

            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("XXX", ">>>>>>>>>>>>>>>>>>>>>>>> onPrepared: mp.start();");
                    mp.start();
                }
            });

            mPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_FILE_OPEN_OK: // line added 1
                            Log.d("XXX", ">>>>>>>>>>>>>>>>>>>>>>>> onInfo: MEDIA_INFO_FILE_OPEN_OK");
                            long buffersize = mPlayer.audioTrackInit(); // line added 2
                            mPlayer.audioInitedOk(buffersize); // line added 3
                            break;
                    }
                    return true;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
