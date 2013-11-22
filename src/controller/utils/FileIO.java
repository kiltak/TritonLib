package controller.utils;

import model.Triton;
import model.global.Global;
import controller.pcg.Pcg;
import model.sound.Bank;

public class FileIO {
    public static void readPcgFile (Triton triton, String filename) {
        Pcg pcg = new Pcg();
        if (pcg.readFile(filename)) {
            // Programs
            Bank[] pcgProgs = pcg.getProgs();
            for (int i = 0; i < pcgProgs.length; ++i) {
                if (pcgProgs[i] != null) {
                    triton.setProgBank(pcgProgs[i], i);
                }
            }

            // Combinations
            Bank[] pcgCombis = pcg.getCombis();
            for (int i = 0; i < pcgCombis.length; ++i) {
                if (pcgCombis[i] != null) {
                    triton.setCombiBank(pcgCombis[i], i);
                }
            }
            
            // Global
            Global pcgGlobal = pcg.getGlobal();
            if (pcgGlobal != null) {
                triton.setGlobal(pcgGlobal);
            }
        }
        else {
            System.out.println ("Could not read " + filename);
        }
    }
    
    public static void writePcgFile (Triton triton, String filename) {
        if (!filename.toLowerCase().endsWith(".pcg")) {
            filename += ".pcg";
        }
        Pcg pcg = new Pcg(triton);
        if (!pcg.writeFile(filename)) {
            System.out.println ("Error writing to " + filename);
        }
    }
}
