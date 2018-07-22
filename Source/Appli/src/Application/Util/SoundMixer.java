package Application.Util;

import java.io.*;
import java.util.*;

import jm.util.*;

import javax.sound.sampled.*;

public class SoundMixer {



    public static void concatWav(String destinationFileName, List<String> sourceFilesList) throws FileNotFoundException, IOException{
        AudioInputStream audioInputStream = null;
        List<AudioInputStream> audioInputStreamList = null;
        AudioFormat audioFormat = null;
        Long frameLength = null;

        try {
            // loop through our files first and load them up
            for (String sourceFile : sourceFilesList) {
                audioInputStream = AudioSystem.getAudioInputStream(new File(sourceFile));

                // get the format of first file
                if (audioFormat == null) {
                    audioFormat = audioInputStream.getFormat();
                }

                // add it to our stream list
                if (audioInputStreamList == null) {
                    audioInputStreamList = new ArrayList<AudioInputStream>();
                }
                audioInputStreamList.add(audioInputStream);

                // keep calculating frame length
                if(frameLength == null) {
                    frameLength = audioInputStream.getFrameLength();
                } else {
                    frameLength += audioInputStream.getFrameLength();
                }
            }

            // now write our concatenated file
            AudioSystem.write(new AudioInputStream(new SequenceInputStream(Collections.enumeration(audioInputStreamList)), audioFormat, frameLength), AudioFileFormat.Type.WAVE, new File(destinationFileName));

            // if all is good, return true

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } finally {
            if (audioInputStream != null) {
                audioInputStream.close();
            }
            if (audioInputStreamList != null) {
                audioInputStreamList = null;
            }
        }
    }

    public void mixWav(String wavFile1,String wavFile2,String resultName){
        float[] part1;
        float[] part2;
        float[] mixedPart;

        part1 = Read.audio(wavFile1);
        part2 = Read.audio(wavFile2);

        if(part1.length>part2.length){
            mixedPart = mix(part1,part2);
        }
        else{
            mixedPart = mix(part2,part1);
        }

        Write.audio(mixedPart, resultName,4,22050,8);
    }

    private float[] mix(float[] longest,float[] shortest){
        float[] temp = new float[longest.length];

        for(int i = 0;i<shortest.length;i++){
            temp[i] = (longest[i] + shortest[i])*0.75f;
        }
        for(int i = shortest.length;i<longest.length;i++){
            temp[i] = longest[i]*0.75f;
        }
        return temp;
    }
}
