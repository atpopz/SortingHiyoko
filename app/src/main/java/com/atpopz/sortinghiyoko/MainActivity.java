package com.atpopz.sortinghiyoko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayerBGM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BGM再生
        mediaPlayerBGM = MediaPlayer.create(this, R.raw.bgm_start);
        mediaPlayerBGM.start();
    }

    // ボタンクリック
    public void playGame(View view) {

        // BGMのリソースを解放
        mediaPlayerBGM.release();
        mediaPlayerBGM = null;

        // 画面遷移
        startActivity(new Intent(this, Playgame.class));
    }
}