package model.sound.program.oscillator;

class FilterHdr {
    public int unpack (byte data[], int offset) {
        _type = data[offset++];
        _trim = data[offset++];
        _resonance = data[offset++];
        _amSourceReso = data[offset++];
        _intByAmReso = data[offset++];
        _amSourceEg = data[offset++];
        _amSourceLFO1 = data[offset++];
        _amSourceLFO2 = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_type;
        data[offset++] = (byte)_trim;
        data[offset++] = (byte)_resonance;
        data[offset++] = (byte)_amSourceReso;
        data[offset++] = (byte)_intByAmReso;
        data[offset++] = (byte)_amSourceEg;
        data[offset++] = (byte)_amSourceLFO1;
        data[offset++] = (byte)_amSourceLFO2;
        
        return offset;
    }
    
    private int _type;          // byte 0
    private int _trim;          // byte 1
    private int _resonance;     // byte 2
    private int _amSourceReso;  // byte 3
    private int _intByAmReso;   // byte 4
    private int _amSourceEg;    // byte 5
    private int _amSourceLFO1;  // byte 6
    private int _amSourceLFO2;  // byte 7
    
    public static int BYTE_SIZE = 8;
}