package controller.utils;

import java.util.Iterator;
import java.util.Vector;

import javax.sound.midi.SysexMessage;

import model.Triton;
import model.global.Global;
import model.sound.Bank;
import model.sound.combination.Combination;
import model.sound.program.Program;

public class MidiParser {
    // private static final byte CURRENT_PROGRAM_PARAMETER_DUMP = 0x40;
    private static final byte PROGRAM_PARAMETER_DUMP = 0x4C;
    // private static final byte CURRENT_COMBINATION_PARAMETER_DUMP = 0x49;
    private static final byte COMBINATION_PARAMETER_DUMP = 0x4D;
    // private static final byte SEQUENCE_DATA_DUMP = 0x48;
    private static final byte GLOBAL_DATA_DUMP = 0x51;

    // private static final byte DRUMKIT_DATA_DUMP = 0x52;
    // private static final byte ARPEGGIO_PATTERN_DATA_DUMP = 0x69;
    // private static final byte ALL_DATA_DUMP = 0x50;

    private Vector<Byte> _fullSysex = new Vector<Byte>();
    //private MessageFileDumper fileDump = new MessageFileDumper("everything");

    /**
     * 
     * @param message
     * @param triton
     * @return True if the message is finished
     *         False if the message needs to continue
     */
    public boolean decodeMessage(SysexMessage message, Triton triton) {
        byte[] data = message.getData();
        boolean parseMsg = true;

        // Set up the string to print
        String strMessage = null;
        if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
            strMessage = "Sysex message: F0" + getHexString(data);
            byte[] arr = message.getData();
            for (int i = 0; i < arr.length; ++i) {
                _fullSysex.add(arr[i]);
            }

            if ((message.getData()[message.getLength() - 2] & 0xFF) != 0xF7) {
                // System.out.println ("msg continues " +
                // Integer.toHexString(message.getData()[message.getLength() -
                // 2]));
                parseMsg = false;
            }
        } else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE) {
            strMessage = "Continued Sysex message: F7" + getHexString(data);
            byte[] arr = message.getData();
            for (int i = 0; i < arr.length; ++i) {
                _fullSysex.add(arr[i]);
            }

            if ((message.getData()[message.getLength() - 2] & 0xFF) != 0xF7) {
                parseMsg = false;
            }
        }

        // System.out.println(strMessage);
        //fileDump.writeBytes(data);

        if (parseMsg) {
            parseMsg(_fullSysex, data, triton);
            _fullSysex.clear();
        }

        return parseMsg;
    }

    private static String getHexString(byte[] aByte) {
        StringBuffer sbuf = new StringBuffer(aByte.length * 3 + 2);
        for (int i = 0; i < aByte.length; i++) {
            sbuf.append(' ');
            sbuf.append(String.format("%02X ", aByte[i]));
        }
        return new String(sbuf);
    }

    public static Object parseMsg(Vector<Byte> msg, byte[] data, Triton triton) {
        byte[] bytes = new byte[msg.size()];
        Iterator<Byte> itr = msg.iterator();
        for (int i = 0; itr.hasNext(); ++i) {
            bytes[i] = (byte) itr.next();
        }

         System.out.println ("Parsing " + String.format("%02X", bytes[3]));

        // Check last byte for 0xF7
        if (bytes[bytes.length - 1] != (byte) 0xF7) {
            System.out.println("Msg doesn't end in 0xF7.  Ends in 0x"
                    + String.format("%02X", bytes[bytes.length - 1]));
            return null;
        }

        // Start parsing
        // Triton ID is 0x50 bytes[2]
        if ((bytes[0] == 0x42) && ((bytes[1] & 0xF0) == 0x30)
                && (bytes[2] == 0x50)) {
            switch (bytes[3]) {
                case PROGRAM_PARAMETER_DUMP:
                    parseProgram(bytes, triton);
                    break;
                case COMBINATION_PARAMETER_DUMP:
                    parseCombination(bytes, triton);
                    break;
                case GLOBAL_DATA_DUMP:
                    return parseGlobal(bytes);
                default:
                    System.out.println("Unkown sysex msg (0x"
                            + String.format("%02X", bytes[3]) + ") - "
                            + bytes.length + " bytes");
            }
        } else {
            System.out.println("Don't know how to parse "
                    + String.format("%02X", data[3]));
        }

        return null;
    }

    /**
     * 
     * @param b1
     * @param b2
     * @param b3
     * @return 4 element array: availableBank, kind, bank, progNumber
     */
    private static int[] parseProgCombiHdr(byte b1, byte b2, byte b3) {
        int[] retArr = new int[4];
        
        retArr[0] = b1 & 0x01; // available bank
        retArr[1] = (b2 & 0x30) >> 4; // kind
        retArr[2] = retArr[1] >= 1 ? (b2 & 0x0F) : retArr[0] * 5; // bank 5 is
                                                                  // the int/ext
                                                                  // offset
        retArr[3] = retArr[1] == 2 ? (b3 & 0x7F) : 0; // program/combi number
        
        return retArr;
    }

    /**
     * Parse one or many programs. The program data comes in with a header
     * describing which program(s) are being dumped, then the data.
     * 
     * @param data
     */
    private static void parseProgram(byte[] data, Triton triton) {
        // int availableBank;
        int bank, progNo;

        int[] hdrVals = parseProgCombiHdr(data[4], data[5], data[6]);
        bank = hdrVals[2];
        progNo = hdrVals[3];

        byte[] tmpData = new byte[data.length - 8];
        System.arraycopy(data, 7, tmpData, 0, data.length - 8);
        byte[] progData = midiToInternal(tmpData);

        // The program data is now nice and neat in the progData array.

        for (int offset = 0; offset < progData.length; offset += Program.BYTE_SIZE) {
            Program p = new Program();
            p.unpack(progData, offset);
            triton.setSingleProg(p, bank, progNo);

            //System.out.println (String.format("%s-%03d: ", Bank.BANK_NAMES[bank], progNo) + p);

            ++progNo;
            if (progNo == 128) {
                progNo = 0;
                ++bank;
            }
        }
    }

    /**
     * Parse one or many combis. The combi data comes in with a header
     * describing which combi(s) are being dumped, then the data.
     * 
     * @param data
     */
    private static void parseCombination(byte[] data, Triton triton) {
        int bank, progNo;

        int[] hdrVals = parseProgCombiHdr(data[4], data[5], data[6]);
        // availableBank = hdrVals[0];
        bank = hdrVals[2];
        progNo = hdrVals[3];

        byte[] tmpData = new byte[data.length - 8];
        System.arraycopy(data, 7, tmpData, 0, data.length - 8);
        byte[] combiData = midiToInternal(tmpData);

        // The combi data is now nice and neat in the progData array.

        for (int offset = 0; offset < combiData.length; offset += Combination.BYTE_SIZE) {
            Combination c = new Combination();
            c.unpack(combiData, offset);
            triton.setSingleCombi(c, bank, progNo);

            //System.out.println (String.format("%s%s-%03d: ", Bank.BANK_NAMES[bank], progNo) + c);

            ++progNo;
            if (progNo == 128) {
                progNo = 0;
                ++bank;
            }
        }
    }

    private static Global parseGlobal(byte[] data) {
        Global g = new Global();

        // byte 4 is reserved, start at byte 5
        byte[] tmpData = new byte[data.length - 6];
        System.arraycopy(data, 5, tmpData, 0, data.length - 6);
        byte[] globalData = midiToInternal(tmpData);

        g.unpack(globalData, 0);

        return g;
    }

    /**
     * Convert from the MIDI bytes to Internal memory bytes. MIDI bytes don't
     * use the MSB.
     * 
     * MIDI Internal 0ABCDEFG Aaaaaaaa 0aaaaaaa Bbbbbbbb 0bbbbbbb Cccccccc
     * 0ccccccc Dddddddd
     * 
     * @param midiData
     *            The data in midi format (doesn't use MSB in the byte)
     * @return The data in internal format (uses MSB in byte)
     */
    private static byte[] midiToInternal(byte[] midiData) {
        byte[] tmpData = new byte[midiData.length];

        int j = 0;
        int bitNum;
        byte header = 0x00;
        byte mask = 0x00;
        byte headerBit = 0x00;
        for (int i = 0; i < midiData.length; ++i) {
            bitNum = i % 8;
            if (bitNum == 0) {
                header = midiData[i];
            } else {
                mask = (byte) Math.pow(2, 7 - bitNum);
                headerBit = (byte) (header & mask);
                headerBit <<= bitNum;
                tmpData[j++] = ((byte) (headerBit | midiData[i]));
            }
        }

        byte[] intData = new byte[j];
        System.arraycopy(tmpData, 0, intData, 0, j);

        return intData;
    }
}
