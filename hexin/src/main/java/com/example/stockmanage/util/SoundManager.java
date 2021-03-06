package com.example.stockmanage.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


import com.example.stockmanage.R;

import java.util.HashMap;


public class SoundManager {
    private static SoundManager single = null;

    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;


    private SoundManager(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_RING, 5);
        soundMap.put(1, soundPool.load(context, R.raw.suc1, 1));
        soundMap.put(2, soundPool.load(context, R.raw.error, 1));
        am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
    }

    public synchronized static SoundManager getInstance(Context context) {
        if (single == null) {
            single = new SoundManager(context);
        }
        return single;

    }



    public void playSound(int id) {

        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_RING);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_RING);
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;

        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}
