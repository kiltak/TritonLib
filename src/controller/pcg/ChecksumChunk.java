package controller.pcg;

import java.nio.ByteBuffer;

class ChecksumChunk extends ChunkBody {
    public int unpack (byte data[], int offset) throws BadDataException {
        ByteBuffer wrapped = ByteBuffer.wrap (data);
        
        _progBankA = wrapped.getShort(offset);
        offset += 2;
        _progBankB = wrapped.getShort(offset);
        offset += 2;
        _progBankC = wrapped.getShort(offset);
        offset += 2;
        _progBankD = wrapped.getShort(offset);
        offset += 2;
        _progBankE = wrapped.getShort(offset);
        offset += 2;
        _progBankF = wrapped.getShort(offset);
        offset += 2;
        _progBankGM = wrapped.getShort(offset);
        offset += 2;
        _progBankGMV1 = wrapped.getShort(offset);
        offset += 2;
        _progBankGMDrm = wrapped.getShort(offset);
        offset += 2;
        _combiBankA = wrapped.getShort(offset);
        offset += 2;
        _combiBankB = wrapped.getShort(offset);
        offset += 2;
        _combiBankC = wrapped.getShort(offset);
        offset += 2;
        _combiBankD = wrapped.getShort(offset);
        offset += 2;
        _drum00_15 = wrapped.getShort(offset);
        offset += 2;
        _drum16_31 = wrapped.getShort(offset);
        offset += 2;
        _drum32_47 = wrapped.getShort(offset);
        offset += 2;
        _drum48_63 = wrapped.getShort(offset);
        offset += 2;
        _drum64_72 = wrapped.getShort(offset);
        offset += 2;
        _arpp00_63 = wrapped.getShort(offset);
        offset += 2;
        _arpp64_79 = wrapped.getShort(offset);
        offset += 2;
        _arpp80_95 = wrapped.getShort(offset);
        offset += 2;
        _global = wrapped.getShort(offset);
        offset += 2;
        
        return offset;
    }
    
    public String toString () {
        return "ProgBankA     " + _progBankA + "\n"
             + "ProgBankB     " + _progBankB + "\n"
             + "ProgBankC     " + _progBankC + "\n"
             + "ProgBankD     " + _progBankD + "\n"
             + "ProgBankE     " + _progBankE + "\n"
             + "ProgBankF     " + _progBankF + "\n"
             + "ProgBankGM    " + _progBankGM + "\n"
             + "ProgBankGMV1  " + _progBankGMV1 + "\n"
             + "ProgBankGMDrm " + _progBankGMDrm + "\n"
             + "CombiBankA    " + _combiBankA + "\n"
             + "CombiBankB    " + _combiBankB + "\n"
             + "CombiBankC    " + _combiBankC + "\n"
             + "CombiBankD    " + _combiBankD + "\n"
             + "Drum 00-15    " + _drum00_15 + "\n"
             + "Drum 16-31    " + _drum16_31 + "\n"
             + "Drum 32-47    " + _drum32_47 + "\n"
             + "Drum 48-63    " + _drum48_63 + "\n"
             + "Drum 64-72    " + _drum64_72 + "\n"
             + "Arp 00-63     " + _arpp00_63 + "\n"
             + "Arp 64-79     " + _arpp64_79 + "\n"
             + "Arp 80-95     " + _arpp80_95 + "\n"
             + "Global        " + _global + "\n"
                ;
    }
    
    private int _progBankA;     //  [2byte]
    private int _progBankB;     //  [2byte]
    private int _progBankC;     //  [2byte]
    private int _progBankD;     //  [2byte]
    private int _progBankE;     //  [2byte]
    private int _progBankF;     //  [2byte]
    private int _progBankGM;    //  [2byte]
    private int _progBankGMV1;  //  [2byte]
    private int _progBankGMDrm; //  [2byte]
    private int _combiBankA;    //  [2byte]
    private int _combiBankB;    //  [2byte]
    private int _combiBankC;    //  [2byte]
    private int _combiBankD;    //  [2byte]
    private int _drum00_15;     //  [2byte]
    private int _drum16_31;     //  [2byte]
    private int _drum32_47;     //  [2byte]
    private int _drum48_63;     //  [2byte]
    private int _drum64_72;     //  [2byte]
    private int _arpp00_63;     //  [2byte]
    private int _arpp64_79;     //  [2byte]
    private int _arpp80_95;     //  [2byte]
    private int _global;        //  [2byte]

}