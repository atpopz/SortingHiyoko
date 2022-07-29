package com.atpopz.sortinghiyoko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Playgame extends AppCompatActivity implements View.OnClickListener{
    private int clickCount = 0;
    private int goodCount = 0;
    private int missCount = 0;

    private String displaySex = "";//osu or mesu
    // BGM
    private MediaPlayer mediaPlayerBGM;
    // ミリビョウ
    private long startTime;

    // 定数
    // 検査するひよこの数
    final int HIYOKO_NUM = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgame);


        // BGM再生
        mediaPlayerBGM = MediaPlayer.create(this, R.raw.bgm_gameplay);
        mediaPlayerBGM.start();

        // ランダムで画像を設定・表示
        setRandomImage();

        // 残ヒヨコ数　初期値表示
        TextView textViewHiyokoNum = findViewById(R.id.textViewHiyokoNum);
        textViewHiyokoNum.setText(String.valueOf(HIYOKO_NUM));

        findViewById(R.id.buttonOsu).setOnClickListener(this);
        findViewById(R.id.buttonMesu).setOnClickListener(this);

        // ゲーム開始時の現在時刻ミリ秒を取得
        startTime = System.currentTimeMillis();
    }

    // 画面上のボタンクリック時の実装
    public void onClick(View view) {

        String onClickSex = "";
        // 押されたボタンを判定
        if (view.getId() == R.id.buttonOsu) {
            onClickSex = "osu";
        }else if (view.getId() == R.id.buttonMesu){
            onClickSex = "mesu";
        }

        // クリックしたボタンの正否をカウント
        checkClickShiyu(onClickSex);

        // クリック数をカウントアップ
        clickCount += 1;

        if(clickCount == HIYOKO_NUM){
            // クリックが上限数に達した場合

            // ゲーム時間のミリビョウを取得
            int playtime = (int)(System.currentTimeMillis() - startTime);

            //インテント作成
            Intent intent = new Intent(getApplication(), Result.class);
            //入力データをセット
            intent.putExtra("SEND_goodCount",goodCount);
            intent.putExtra("SEND_missCount",missCount);
            intent.putExtra("SEND_nowTime",playtime);

            // BGMのリソースを解放
            mediaPlayerBGM.release();
            mediaPlayerBGM = null;

            //SubActivityに画面遷移
            startActivity(intent);
        }else{
            // クリック数が上限数でない場合
            // ランダムで画像を設定・表示
            setRandomImage();
        }

        // 残ヒヨコ引数
        TextView textViewHiyokoNum = findViewById(R.id.textViewHiyokoNum);
        textViewHiyokoNum.setText(String.valueOf(HIYOKO_NUM - clickCount));
    }

    // クリックしたボタンの正否をカウント
    private void checkClickShiyu(String onClickSex) {

        if(displaySex == onClickSex){
            // 正解
            goodCount ++;
        }else{
            // 不正解
            missCount ++;
        }
    }

    // ランダムで画像を設定
    private void setRandomImage() {

        // 雌雄をランダムで設定。1=osu,2=mesu
        int shiyuI = (int) (Math.floor(Math.random() * 2)) + 1;

        if(shiyuI == 1){
            displaySex = "osu";
        }else if(shiyuI == 2){
            displaySex = "mesu";
        }

        // 画像ファイル名末尾の数字をランダムで設定（0〜9）
        int imageI = (int) (Math.floor(Math.random() * 9));

        // 画像ファイル名を生成（例：hiyoko_osu1）
        String imageName = "hiyoko_";
        imageName += displaySex;
        imageName += String.valueOf(imageI);

        // ImageViewの用意
        ImageView myImage = findViewById(R.id.imageViewHiyoko);
        // 画像のリソースIDを取得
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        // ImageViewに画像をセット
        myImage.setImageResource(resId);
    }

}