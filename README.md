# StreamPlayerVitamio5
Audio Stream with Vitamio 5.0.0

Vitamio 5.0.0 : http://wscdn.miaopai.com/download/VitamioBundle20151118.zip

This is the Audio Streaming project that works with Vitamio 5.0.0.

In version 5.0.0, some classes/methods no longer available.


```sh
# version 4.x
if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
  return;

# version 5.0.0
Vitamio.isInitialized(this);

```

In version 5.0.0, OnPrepareListener will not fire, if you do not implement @Overwrite onInfo <br>
Below example will works

```sh
public class MainActivity extends AppCompatActivity {
    private MediaPlayer mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vitamio.isInitialized(this);
        streamAudio("http://209.105.250.73:8650/autodj");
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

```
