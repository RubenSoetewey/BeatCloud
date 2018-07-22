package Application.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

import jm.util.*;

public class SoundMixer {


    /*public static void concatWav(String resultName, Vector<String> files) throws FileNotFoundException, IOException{
        //FileInputStream fistream1 = new FileInputStream(wavFile1);  // first source file
        //FileInputStream fistream2 = new FileInputStream(wavFile2);//second source file
        Vector<FileInputStream> streams = new Vector<>();
        for(String file:files){
            streams.add(new FileInputStream(file));
        }
        SequenceInputStream sistream = new SequenceInputStream(streams.elements());
        FileOutputStream fostream = new FileOutputStream(resultName);//destinationfile
        int temp;
        while( ( temp = sistream.read() ) != -1)
        {
            // System.out.print( (char) temp ); // to print at DOS prompt
            fostream.write(temp);   // to write to file
        }
        fostream.close();
        sistream.close();
    }*/
    public static void concatWav(String resultName, Vector<String> files) throws FileNotFoundException, IOException{
        Vector<FileInputStream> streams = new Vector<>();
        for(String file:files){
            try {
                streams.add(new FileInputStream(file));
            }
            catch(Exception e){

            }
        }
        SequenceInputStream sistream = new SequenceInputStream(streams.elements());
        FileOutputStream fostream = new FileOutputStream(resultName);//destinationfile
        int temp;
        while( ( temp = sistream.read() ) != -1)
        {
            // System.out.print( (char) temp ); // to print at DOS prompt
            fostream.write(temp);   // to write to file
        }
        fostream.close();
        sistream.close();
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
