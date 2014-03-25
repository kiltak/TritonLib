package controller.pcg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import triton.model.Triton;
import controller.pcg.bankChunk.ArpeggioBankChunk;
import controller.pcg.bankChunk.CombinationBankChunk;
import controller.pcg.bankChunk.DrumkitBankChunk;
import controller.pcg.bankChunk.GlobalChunk;
import controller.pcg.bankChunk.ProgramBankChunk;
import triton.model.sound.Bank;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;
import triton.model.global.Global;

public class Pcg {
    /**
     * 
     */
    public Pcg () {
        
    }
    
    /**
     * Instantiate the PCG class using an existing Triton.
     * 
     * @param triton
     */
    public Pcg (Triton triton) {
        _progs = triton.getAllProgs();
        _combis = triton.getAllCombis();
        _global = triton.getGlobal();
    }
    
    /**
     * Read the file into a byte array.
     * 
     * @param filename
     * @return
     */
    private byte[] readFileToBytes (String filename) {
        byte[] b;
        try {
            RandomAccessFile f = new RandomAccessFile (filename, "r");
            b = new byte[(int)f.length()];
            f.readFully(b);
            f.close();
            return b;
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
            b = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            b = null;
        }
        
        return b;
    }
    
    /**
     * Read the file.
     * 
     * @param filename
     * @return
     */
    public boolean readFile (String filename) {
        byte _data[] = readFileToBytes (filename);
        
        if (_data == null) {
            return false;
        }
        
        // Unpack the data into the sub structures
        int offset = 0;
        ChunkHeader hdrChunk = new ChunkHeader();
        try {
            offset = _hdr.unpack(_data, offset);
            while ((offset = hdrChunk.unpack(_data, offset)) != 0) {
                //System.out.println ("offset " + (offset - ChunkHeader.BYTE_SIZE));
                //System.out.println (hdrChunk);
                ChunkBody c = hdrChunk.getBodyInstantiation();
                if (c != null) {
                    if (c instanceof controller.pcg.bankChunk.GlobalChunk) {
                        GlobalChunk newGlobalChunk = (GlobalChunk)c;
                        offset = newGlobalChunk.unpack(_data, offset);
                        _global = newGlobalChunk.getGlobal();
                    }
                    else {
                        offset = c.unpack(_data, offset);
                        if (c instanceof controller.pcg.bankChunk.ProgramBankChunk) {
                            ProgramBankChunk newProgBank = (ProgramBankChunk)c;
                            _progs[newProgBank.getBankId()] = newProgBank.getBank();
                        }
                        else if (c instanceof controller.pcg.bankChunk.CombinationBankChunk) {
                            CombinationBankChunk newCombiBank = (CombinationBankChunk)c;
                            _combis[newCombiBank.getBankId()] = newCombiBank.getBank();
                        }
                        else if (c instanceof controller.pcg.bankChunk.DrumkitBankChunk) {
                            _dks.add((DrumkitBankChunk)c);
                        }
                        else if (c instanceof controller.pcg.bankChunk.ArpeggioBankChunk) {
                            _arps.add((ArpeggioBankChunk)c);
                        }
                    }
                }
            }
        }
        catch (BadDataException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Write the file out.
     * 
     * @param filename
     * @return
     */
    public boolean writeFile (String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile (filename, "rw");
            file.setLength(0);
            
            _hdr.write(file);
            
            // PCG chunk
            file.write("PCG1".getBytes());
            long pcgChunkSizeLocation = file.getFilePointer();
            file.seek(pcgChunkSizeLocation + 4);  // skip past the size for now
            
            // PROGRAMS
            writeProgs (file);
            
            // COMBINATIONS
            writeCombis (file);
            
            // Global
            writeGlobal (file);
            
            // Checksum
            file.write("CSM1".getBytes());
            writeInt (file, 44); // hardcode 44 bytes
            for (int i = 0; i < 22; ++i) {
                writeShort (file, (short)0);
            }
            
            // PCG
            long endOfPcgChunk = file.getFilePointer();
            file.seek(pcgChunkSizeLocation);
            writeInt (file, (int)(endOfPcgChunk - pcgChunkSizeLocation - 4)); // 4 for the size of the counter
            file.seek(endOfPcgChunk);
            
            file.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
    
    private void writeProgs (RandomAccessFile file) throws IOException {
        file.write("PRG1".getBytes());
        long progChunkSizeLocation = file.getFilePointer();
        file.seek(progChunkSizeLocation + 4);  // skip past the size for now
        for (int i = 0; i < _progs.length; ++i) {
            if (_progs[i] != null) {
                writeProgBank (file, i);
            }
        }
        
        long endOfprogChunk = file.getFilePointer();
        file.seek(progChunkSizeLocation);
        writeInt (file, (int)(endOfprogChunk - progChunkSizeLocation - 4)); // 4 for the size of the counter
        file.seek(endOfprogChunk);
    }
    
    private void writeProgBank (RandomAccessFile file, int bankId) throws IOException {
        file.write("PBK1".getBytes());
        long bankSizeLocation = file.getFilePointer();
        file.seek(bankSizeLocation + 4);  // skip past the size for now
        
        writeInt (file, 128); // num of program
        writeInt (file, Program.BYTE_SIZE); // size of a program
        writeInt (file, bankId); // bank ID
        
        long progDataStartLocation = file.getFilePointer();
        file.write(_progs[bankId].pack());
        
        long endOfBank = file.getFilePointer();
        file.seek(bankSizeLocation);
        writeInt (file, (int)(endOfBank - progDataStartLocation + 12)); // 12 bytes is num, size, bank id
        file.seek(endOfBank);
    }
    
    private void writeCombis (RandomAccessFile file) throws IOException {
        file.write("CMB1".getBytes());
        long progChunkSizeLocation = file.getFilePointer();
        file.seek(progChunkSizeLocation + 4);  // skip past the size for now
        for (int i = 0; i < _combis.length; ++i) {
            if (_combis[i] != null) {
                writeCombiBank (file, i);
            }
        }
        
        long endOfprogChunk = file.getFilePointer();
        file.seek(progChunkSizeLocation);
        writeInt (file, (int)(endOfprogChunk - progChunkSizeLocation - 4)); // 4 for the size of the counter
        file.seek(endOfprogChunk);
    }
    
    private void writeCombiBank (RandomAccessFile file, int bankId) throws IOException {
        file.write("CBK1".getBytes());
        long bankSizeLocation = file.getFilePointer();
        file.seek(bankSizeLocation + 4);  // skip past the size for now
        
        writeInt (file, 128); // num of combinations
        writeInt (file, Combination.BYTE_SIZE); // size of a program
        writeInt (file, bankId); // bank ID
        
        long progDataStartLocation = file.getFilePointer();
        file.write(_combis[bankId].pack());
        
        long endOfBank = file.getFilePointer();
        file.seek(bankSizeLocation);
        writeInt (file, (int)(endOfBank - progDataStartLocation + 12)); // 12 bytes is num, size, bank id
        file.seek(endOfBank);
    }
    
    private void writeGlobal (RandomAccessFile file) throws IOException {
        file.write("GLB1".getBytes());
        long globalSizeLocation = file.getFilePointer();
        file.seek(globalSizeLocation + 4);  // skip past the size for now
        
        file.write (_global.pack());
        
        long endOfGlobal = file.getFilePointer();
        file.seek(globalSizeLocation);
        writeInt (file, (int)(endOfGlobal - globalSizeLocation));
        file.seek(endOfGlobal);
        
    }
    
    /**
     * Write an int as four bytes.
     * @throws IOException 
     */
    private void writeInt (RandomAccessFile file, int val) throws IOException {
        file.writeInt(val);
    }
    
    private void writeShort (RandomAccessFile file, short val) throws IOException {
        file.writeShort(val);
    }
    
    public String toString() {
        /******************
         *    PROGRAMS
         ******************/
        String retString = "--- progs ---\n";
        // Print the headers
        retString += "     ";
        for (int bank = 0; bank < _progs.length; ++bank) {
            if (_progs[bank] != null) {
                retString += String.format("        %s        ", _progs[bank].getName());
            }
        }
        retString += "\n";
        // Print the names
        for (int prog = 0; prog < 128; ++prog) {
            retString += String.format("%03d: ", prog);
            for (int bank = 0; bank < _progs.length; ++bank) {
                if (_progs[bank] != null) {
                    retString += String.format("%20s",_progs[bank].get(prog).getName());
                }
            }
            retString += "\n";
        }

        /******************
         *   COMBINATIONS
         ******************/
        retString += "\n--- combis ---\n";
        // Print the headers
        retString += "     ";
        for (int bank = 0; bank < _combis.length; ++bank) {
            if (_combis[bank] != null) {
                retString += String.format("        %s        ", _combis[bank].getName());
            }
        }
        retString += "\n";
        // Print the names
        for (int prog = 0; prog < 128; ++prog) {
            retString += String.format("%03d: ", prog);
            for (int bank = 0; bank < _combis.length; ++bank) {
                if (_combis[bank] != null) {
                    retString += String.format("%20s",_combis[bank].get(prog).getName());
                }
            }
            retString += "\n";
        }
        
//             + "\n--- dks ---\n"
//             + _dks
//             + "\n--- arps ---\n"
//             + _arps
        
        if (_global != null) {
            retString += "\n--- global ---\n";
            retString += _global.toString();
        }
        
        return retString;
    }
    
    public Bank[] getProgs () {
        return _progs;
    }
    
    public Bank[] getCombis () {
        return _combis;
    }
    
    public Global getGlobal () {
        return _global;
    }
    
    public static void main(String args[]) {
        String[] filenames = {
//                "./docs/EH_SNDZ/EH_SNDZ.pcg",
                "./docs/PCG/CLS_ROM.pcg",
                "./docs/PCG/CLS_EXB.pcg",
//                "./docs/PCG/S_SBOX.pcg",  // Div
//                "./docs/PCG/Legacy.pcg",
//                "./docs/PCG/Legacy2.pcg",
//                "./docs/PCG/Legacy3.pcg",
//                "./docs/PCG/PRELOAD.pcg",  // Div
//                "./docs/PCG/LEADS.pcg",  // Div
                ""};  // last one is just to make it easier to comment stuff out
        
        Pcg pcg = new Pcg();
        
        for (String filename : filenames) {
            if (filename != "") {
                if (!pcg.readFile(filename)) {
                    System.err.println ("Could not read " + filename);
                    System.exit(1);
                }
            }
        }
        
        System.out.println(pcg);
    }
    
    private Header _hdr = new Header();
    
    // Programs: 5 int banks, 6 ext banks
    private Bank[] _progs = new Bank[Bank.NUMBER_OF_PROG_BANKS];
    // Combinations: 5 int banks, 6 ext banks
    private Bank[] _combis = new Bank[Bank.NUMBER_OF_COMBI_BANKS];
    
    private List<DrumkitBankChunk> _dks = new ArrayList<DrumkitBankChunk>();
    private List<ArpeggioBankChunk> _arps = new ArrayList<ArpeggioBankChunk>();
    private Global _global = null;
}