package com.zodiac.Support;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class SoundManager {

    static Music Current_Music;
    static ArrayList <Sound> Sounds = new ArrayList<Sound>();
    static ArrayList[] Effect_IDs = new ArrayList[Assets.SOUND_COUNT];

    public static void PlayMusic(Music music)
    {
        if(Utilities.DEBUG_LEVEL==0)
        {
            Current_Music = music;
            Current_Music.play();
        }
    }

    public static void StopMusic()
    {
        Current_Music.stop();
        Current_Music.dispose();
    }

    public static long PlaySound(int soundNumber, boolean looping)
    {
        long ID;
        Sound sound = Assets.getSound(soundNumber);
        Sounds.add(sound);

        if(looping)
            ID = sound.loop();
        else
            ID = sound.play();

        if(Effect_IDs[soundNumber]==null)
            Effect_IDs[soundNumber] = new ArrayList();
        Effect_IDs[soundNumber].add(ID);
        return ID;
    }

    public static int getInstances(int soundNumber)
    {
        if(Effect_IDs[soundNumber]==null)
            return 0;
        return Effect_IDs[soundNumber].size();
    }

    public static void setGlobalVolume(float volume)
    {
        for(int i = 0;i<Effect_IDs.length;i++)
        {
            if(Effect_IDs[i]!=null)
            {
                for(int j=0;j<Effect_IDs[i].size();j++)
                    Assets.getSound(i).setVolume((Long) Effect_IDs[i].get(j),volume);
            }
        }
    }

    public static void StopSound(int soundNumber, long ID)
    {
        Assets.getSound(soundNumber).stop(ID);
    }

    public static void StopSound(int soundNumber, int channels)
    {
        System.out.println(getInstances(soundNumber));
        if(Effect_IDs[soundNumber]!=null)
        {
            if(Effect_IDs[soundNumber].size()<=channels)
            {
                Effect_IDs[soundNumber].clear();
                Assets.getSound(soundNumber).stop();
            }

            else
                for(int i=0;i<Effect_IDs[soundNumber].size()-1||channels==0;i++)
                {
                    if(Effect_IDs[soundNumber].get(i)!=null)
                    {
                        --channels;
                        Assets.getSound(soundNumber).stop((Long) Effect_IDs[soundNumber].get(i));
                        Effect_IDs[soundNumber].remove(i);
                    }
                }
        }
    }

    public static void StopAllSounds()
    {
        for(Sound sound : Sounds)
        {
            sound.stop();
        }
        for(int i = 0;i<Effect_IDs.length;i++)
        {
            if(Effect_IDs[i]!=null)
            {
                Effect_IDs[i].clear();
            }
        }
    }
}
