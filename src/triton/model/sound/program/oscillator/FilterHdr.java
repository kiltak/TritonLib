package triton.model.sound.program.oscillator;

import triton.util.Limits;

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
        
        validate();
        
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
    
    private boolean validate () {
        boolean retVal = true;
        
        try {
            Limits.checkLimits ("FilterHdr._type", _type, 0, 1);
            Limits.checkLimits ("FilterHdr._trim", _trim, 0, 99);
            Limits.checkLimits ("FilterHdr._resonance", _resonance, 0, 99);
            Limits.checkLimits ("FilterHdr._amSourceReso", _amSourceReso, 0, 0x2A);
//            Limits.checkLimits ("FilterHdr._intByAmReso", _intByAmReso, -99, 99);
            Limits.checkLimits ("FilterHdr._amSourceEg", _amSourceEg, 0, 0x2A);
            Limits.checkLimits ("FilterHdr._amSourceLFO1", _amSourceLFO1, 0, 0x2A);
            Limits.checkLimits ("FilterHdr._amSourceLFO2", _amSourceLFO2, 0, 0x2A);
        }
        catch (IllegalArgumentException e) {
            System.out.println (e);
            retVal = false;
        }
        
        return retVal;
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