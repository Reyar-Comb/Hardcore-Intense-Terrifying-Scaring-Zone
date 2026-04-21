package edu.hitsz.audio;

public class AudioManager {
    public boolean IsBoss = false;
    public static AudioManager instance;

    public static AudioManager getInstance(){
        if (instance == null){
            instance = new AudioManager();
        }
        return instance;
    }

    public MusicThread bgmThread;
    public MusicThread bossThread;

    public void Init() {
    }

    public void PlayBGM() {
        IsBoss = false;
        bgmThread = new MusicThread("src/videos/bgm.wav");
        bgmThread.loop = true;
        bgmThread.start();

    }

    public void StopBGM() {
        bgmThread.stopPlaying();
    }

    public void PlayBoss() {
        bossThread = new MusicThread("src/videos/bgm_boss.wav");
        bossThread.loop = true;
        IsBoss = true;
        bossThread.start();
    }
    public void StopBoss() {
        bossThread.stopPlaying();
    }

    public void PlaySFX(String name) {
        String dir = "src/videos/";
        switch(name){
            case "bomb": {
                dir += "bomb_explosion.wav";
                break;
            }
            case "hit": {
                dir += "bullet_hit.wav";
                break;
            }
            case "gameover": {
                dir += "game_over.wav";
                break;
            }
            case "prop": {
                dir += "get_supply.wav";
                break;
            }
            default: {
                break;
            }
        }

        MusicThread mt = new MusicThread(dir);
        mt.start();
    }

}
