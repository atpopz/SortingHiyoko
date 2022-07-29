package com.atpopz.sortinghiyoko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //前画面からの値を取得
        Intent intent = getIntent();
        int getGoodCount = intent.getIntExtra("SEND_goodCount", 0);
        int getMissCount = intent.getIntExtra("SEND_missCount", 0);
        int getNowTimeMSec = intent.getIntExtra("SEND_nowTime", 0);

        // 1miss 1000ミリ秒を加算
        getNowTimeMSec = getNowTimeMSec + getMissCount * 10000;

        // セーブデータを取得
        // ファイル名:HiyokoSaveData
        SharedPreferences saveData = getSharedPreferences("HiyokoSaveData", MODE_PRIVATE);
        // セーブデータからハイスコア(ミリ秒)を取得
        int getSaveRecordTimeMSec = saveData.getInt("HIGH_SCORE", 999999);

        // ハイスコア表示用の変数に、ひとまずセーブデータのハイスコアを設定
        int showHighScoreMSec = getSaveRecordTimeMSec;

        // ハイスコアよりも今回のタイムが早い場合
        if(getSaveRecordTimeMSec > getNowTimeMSec){
            // 今回のタイムをミリ秒でセーブ
            saveRecord(getNowTimeMSec);
            // 今回のタイムをハイスコア表示用の変数に設定
            showHighScoreMSec = getNowTimeMSec;
        }

        // 今回のタイム
        // 画面表示のため、ミリ秒を秒に変換　12345ミリ秒 → 0.12秒
        double showNowScoreSec = convertMSecToSec(getNowTimeMSec);

        //　ハイスコア
        // 画面表示のため、ミリ秒を秒に変換　12345ミリ秒 → 0.12秒
        double showHighScoreSec = convertMSecToSec(showHighScoreMSec);

        //値を画面に表示
        TextView textViewGood = findViewById(R.id.textViewGoodCount);
        TextView textViewBut = findViewById(R.id.textViewMissCount);
        TextView textViewNowTime = findViewById(R.id.textViewNowTime);
        TextView textViewHighScore = findViewById(R.id.textViewHighScore);
        textViewGood.setText("Good: " + getGoodCount);
        textViewBut.setText("Miss: " + getMissCount);
        textViewNowTime.setText(showNowScoreSec + " sec");
        textViewHighScore.setText(showHighScoreSec + " sec");
    }

    // 右上バツボタンをクリック
    public void clickButtonBatxu(View view) {
        // 画面遷移
        startActivity(new Intent(this, MainActivity.class));
    }

    // REPLAYボタンをクリック
    public void clickButtonReplay(View view) {
        // 画面遷移
        startActivity(new Intent(this, Playgame.class));
    }

    // レコードを保存
    private void saveRecord(int getThisRecordTime){
        // セーブデータの情報を取得
        SharedPreferences saveData = getSharedPreferences("HiyokoSaveData", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveData.edit();

        // 書き込みデータ追加
        editor.putInt("HIGH_SCORE", getThisRecordTime);

        //非同期の書き込み
        editor.apply();
    }

    // ミリ秒を秒に変換 1234 → 0.12
    private double convertMSecToSec(int ｍSec){

        double sec = Double.valueOf(ｍSec);
        sec = sec / 1000;
        sec = Math.round(sec * 100.0) / 100.0;

        return sec;
    }
}

