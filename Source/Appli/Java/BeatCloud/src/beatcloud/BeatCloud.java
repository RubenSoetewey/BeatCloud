/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beatcloud;

import annotations.HTMLDoc;

/**
 *
 * @author rsoetewey
 */
public class BeatCloud {
    @HTMLDoc(mode = 1)
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SoundDesigner sd = new SoundDesigner();
        System.out.println(System.getProperty("user.dir" ));
        sd.mixWav("test1.wav", "test2.wav");
    }
    
}
