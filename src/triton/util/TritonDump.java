/**
 * Create a dump message that will be sent to the hardware.
 */

package triton.util;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import triton.model.global.Global;
import triton.model.sound.Bank;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;

public class TritonDump {
    public static final byte CURRENT_PROGRAM_PARAMETER_DUMP     = 0x40;
    public static final byte PROGRAM_PARAMETER_DUMP             = 0x4C;
    public static final byte CURRENT_COMBINATION_PARAMETER_DUMP = 0x49;
    public static final byte COMBINATION_PARAMETER_DUMP         = 0x4D;
//    public static final byte SEQUENCE_DATA_DUMP = 0x48;
    public static final byte GLOBAL_DATA_DUMP                   = 0x51;
//    public static final byte DRUMKIT_DATA_DUMP = 0x52;
//    public static final byte ARPEGGIO_PATTERN_DATA_DUMP = 0x69;
//    public static final byte ALL_DATA_DUMP = 0x50;
    
    /*****************************
     * PROGRAM DUMPS
     *****************************/
    
    public static SysexMessage dumpProgram (Program p, int channel, int bank, int offset) {
        // Header data
        byte hdr[] = new byte[3];
        hdr[0] = 0x00; // 0000 000v  available bank    0: Bank Int A-E EXB A-H (no MOSS)
                       //                              1: Bank Int A-F EXB A-H (MOSS)
        hdr[1] = (byte)(0x20 | bank); // 00kk bbbb  kind and bank     k = 1:1 bank (use v & b)
                                      //                                  2:Program (use b & pp)
                                      //                              b = 0-5  Bank Int A-F
                                      //                                  6-13 Bank EXB A-H
        hdr[2] = (byte)offset; // 0ppp pppp  program
        
        // program data
        byte[] midiData = internalToMidi(p.pack());
        
        return buildDump(PROGRAM_PARAMETER_DUMP, channel, hdr, midiData);
    }
    
    public static SysexMessage dumpProgramBank (Bank b, int channel, int bank) {
        // Header data
        byte hdr[] = new byte[3];
        hdr[0] = 0x00; // 0000 000v  available bank    0: Bank Int A-E EXB A-H (no MOSS)
                       //                              1: Bank Int A-F EXB A-H (MOSS)
        hdr[1] = (byte)(0x10 | bank); // 00kk bbbb  kind and bank     k = 1:1 bank (use v & b)
                                      //                                  2:Program (use b & pp)
                                      //                              b = 0-5  Bank Int A-F
                                      //                                  6-13 Bank EXB A-H
        hdr[2] = 0x00; // 0ppp pppp  program
        
        // Bank data
        byte[] midiData = internalToMidi(b.pack());
        
        return buildDump(PROGRAM_PARAMETER_DUMP, channel, hdr, midiData);
    }
    
    public static ArrayList<SysexMessage> dumpAllPrograms (Bank[] banks, int channel) {
        // Dump it bank by bank for now
        ArrayList<SysexMessage> retArr = new ArrayList<SysexMessage>();
        
        for (int i = 0; i < banks.length; ++i) {
            if (banks[i] != null) {
                retArr.add(dumpProgramBank (banks[i], channel, i));
            }
        }
        
        return retArr;
    }
    
    public static SysexMessage dumpCurrentProgram (Program p, int channel) {
        // Header data
        byte hdr[] = new byte[1];
        hdr[0] = 0x00; // 0000 000t  type = 0:PCM, 1:MOSS
        
        // program data
        byte[] midiData = internalToMidi(p.pack());
        
        return buildDump(CURRENT_PROGRAM_PARAMETER_DUMP, channel, hdr, midiData);
    }

    /*****************************
     * COMBINATION DUMPS
     *****************************/
    
    public static SysexMessage dumpCombination (Combination c, int channel, int bank, int offset) {
        // Header data
        byte hdr[] = new byte[3];
        hdr[0] = 0x00; // reserved
        hdr[1] = (byte)(0x20 | bank); // 00kk bbbb  kind and bank     k = 1:1 bank (use b)
                                      //                                  2:Combination (use b & pp)
                                      //                              b = 0-4  Bank Int A-E
                                      //                                  5-13 Bank EXB A-H
        hdr[2] = (byte)offset; // 0ppp pppp  program
        
        // program data
        byte[] midiData = internalToMidi(c.pack());
        
        return buildDump(COMBINATION_PARAMETER_DUMP, channel, hdr, midiData);
    }
    
    public static SysexMessage dumpCombinationBank (Bank b, int channel, int bank) {
        // Header data
        byte hdr[] = new byte[3];
        hdr[0] = 0x00; // reserved
        hdr[1] = (byte)(0x10 | bank); // 00kk bbbb  kind and bank     k = 1:1 bank (use b)
                                      //                                  2:Combination (use b & pp)
                                      //                              b = 0-4  Bank Int A-E
                                      //                                  5-13 Bank EXB A-H
        hdr[2] = 0x00; // 0ppp pppp  program
        
        // Bank data
        byte[] midiData = internalToMidi(b.pack());
        
        return buildDump(COMBINATION_PARAMETER_DUMP, channel, hdr, midiData);
    }
    
    public static ArrayList<SysexMessage> dumpAllCombinations (Bank[] banks, int channel) {
        // Dump it bank by bank for now
        ArrayList<SysexMessage> retArr = new ArrayList<SysexMessage>();
        
        for (int i = 0; i < banks.length; ++i) {
            if (banks[i] != null) {
                retArr.add(dumpCombinationBank (banks[i], channel, i));
            }
        }
        
        return retArr;
    }
    
    public static SysexMessage dumpCurrentCombination (Combination c, int channel) {
        // Header data
        byte hdr[] = new byte[1];
        hdr[0] = 0x00; // 0000 000t  type = 0:PCM, 1:MOSS
        
        // program data
        byte[] midiData = internalToMidi(c.pack());
        
        return buildDump(CURRENT_COMBINATION_PARAMETER_DUMP, channel, hdr, midiData);
    }

    /*****************************
     * GLOBAL DUMPS
     *****************************/
    
    public static SysexMessage dumpGlobal (Global g, int channel) {
        // Header data
        byte hdr[] = new byte[1];
        hdr[0] = 0x00; // reserved
        
        // program data
        byte[] midiData = internalToMidi(g.pack());
        
        return buildDump(GLOBAL_DATA_DUMP, channel, hdr, midiData);
    }
    
    /*****************************
     * UTIL Methods
     *****************************/
    
    public static SysexMessage buildDump (byte type, int channel) {
        return buildDump(type, channel, null, null);
    }
    
    /**
     * Convert from the internal memory bytes to midi bytes. MIDI bytes don't
     * use the MSB.
     * 
     * MIDI      Internal
     * 0ABCDEFG  Aaaaaaaa
     * 0aaaaaaa  Bbbbbbbb
     * 0bbbbbbb  Cccccccc
     * 0ccccccc  Dddddddd
     * 0ddddddd
     * 
     * @param internalData
     *            The data in internal format (uses MSB in the byte)
     * @return The data in midi format (doesn't use MSB in byte)
     */
    private static byte[] internalToMidi (byte[] internalData) {
        double size = internalData.length * 8.0 / 7.0; // every 7 bytes will be expanded to 8 bytes
        byte[] tmpData = new byte[(int)Math.ceil(size)];

        int j = 0;
        int byteNo = 0;
        byte header = 0x00;
        byte msb;
        byte[] sevenBytes = new byte[7]; // temp storage for the seven bytes being worked on
        for (int i = 0; i < internalData.length; ++i) {
            byteNo = i % 7;
            if (byteNo == 0) { // first byte
                header = 0x00;
                for (int x = 0; x < 7; ++x) {
                    sevenBytes[x] = 0x00;
                }
            }
            
            msb = (byte)((internalData[i] & 0x80) >> (byteNo + 1));
            header |= msb;
            sevenBytes[byteNo] = (byte)(internalData[i] & 0x7F);
            
            if (byteNo == 6) { // last byte
                tmpData[j++] = header;
                System.out.print (String.format("j %3d - byteNo %d,        header %02X, seven:", j, byteNo, header));
                for (byte b : sevenBytes) {
                    System.out.print(String.format(",  %02X", b));
                    tmpData[j++] = b;
                }
                System.out.println();
            }
        }
        
        if (byteNo != 6) { // need to write out these bytes
            tmpData[j++] = header;
            for (int i = 0; i <= byteNo; ++i) {
                tmpData[j++] = sevenBytes[i];
            }
        }
        
        byte[] midiData = new byte[j];
        System.arraycopy(tmpData, 0, midiData, 0, j);
        
        return midiData;
    }
    
    /**
     * Build the message(s) to be sent to the hardware.  This assumes that the data is already
     * sevened.
     * 
     * @param type
     * @param channel
     * @param inputData
     * @return
     */
    private static SysexMessage buildDump(byte function, int channel, byte hdrData[], byte inputData[]) {
        SysexMessage msg = new SysexMessage();
        byte[] msgData = new byte[hdrData.length + inputData.length + 6]; // 5 for header, 1 for
                                                                          // last F7
        
        int i = 0;
        msgData[i++] = (byte) 0xF0;
        msgData[i++] = (byte) 0x42; // Korg ID
        msgData[i++] = (byte) (0x30 | channel); // 0x3n Format ID, n = channel
                                                // number
        msgData[i++] = (byte) 0x50; // Triton series ID
        msgData[i++] = (byte) function;
        // Function header
        if (hdrData != null) {
            for (int j = 0; j < hdrData.length; ++j) {
                msgData[i++] = hdrData[j];
            }
        }
        // Data
        if (inputData != null) {
            for (int j = 0; j < inputData.length; ++j) {
                msgData[i++] = inputData[j];
            }
        }
        msgData[i++] = (byte) 0xF7; // end of exclusive
        
        try {
            msg.setMessage(msgData, msgData.length);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return null;
        }
        return msg;
    }
}
