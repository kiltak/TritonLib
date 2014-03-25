package triton.model.sound.program.oscillator;

import triton.util.Limits;

class Amplifier {
    public int unpack (byte data[], int offset) {
        _level = data[offset++];
        _intByVelocity = data[offset++];
        _amSource = data[offset++];
        _intByAM = data[offset++];
        _intByLFO1 = data[offset++];
        _intByLFO2 = data[offset++];
        _amSourceLFO1 = data[offset++];
        _intByAmLFO1 = data[offset++];
        _amSourceLFO2 = data[offset++];
        _intByAmLFO2 = data[offset++];
        
        validate();
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_level;
        data[offset++] = (byte)_intByVelocity;
        data[offset++] = (byte)_amSource;
        data[offset++] = (byte)_intByAM;
        data[offset++] = (byte)_intByLFO1;
        data[offset++] = (byte)_intByLFO2;
        data[offset++] = (byte)_amSourceLFO1;
        data[offset++] = (byte)_intByAmLFO1;
        data[offset++] = (byte)_amSourceLFO2;
        data[offset++] = (byte)_intByAmLFO2;
        
        return offset;
    }
    
    private boolean validate () {
        boolean retVal = true;
        
        try {
            Limits.checkLimits ("Amplifier._level", _level, 0, 127);
//            Limits.checkLimits ("Amplifier.intByVelocity", _intByVelocity, -99, 99);
            Limits.checkLimits ("Amplifier.amSource", _amSource, 0, 0x2A);
//            Limits.checkLimits ("Amplifier.intByAM", _intByAM, -99, 99);
//            Limits.checkLimits ("Amplifier.intByLFO1", _intByLFO1, -99, 99);
//            Limits.checkLimits ("Amplifier.intByLFO2", _intByLFO2, -99, 99);
            Limits.checkLimits ("Amplifier.amSourceLFO1", _amSourceLFO1, 0, 0x2A);
//            Limits.checkLimits ("Amplifier.intByAmLFO1", _intByAmLFO1, -99, 99);
            Limits.checkLimits ("Amplifier.amSourceLFO2", _amSourceLFO2, 0, 0x2A);
//            Limits.checkLimits ("Amplifier.intByAmLFO2", _intByAmLFO2, -99, 99);
        }
        catch (IllegalArgumentException e) {
            System.out.println (e);
            retVal = false;
        }
        
        return retVal;
    }
    
    private int _level;         // byte 0
    private int _intByVelocity; // byte 1
    private int _amSource;      // byte 2
    private int _intByAM;       // byte 3
    private int _intByLFO1;     // byte 4
    private int _intByLFO2;     // byte 5
    private int _amSourceLFO1;  // byte 6
    private int _intByAmLFO1;   // byte 7
    private int _amSourceLFO2;  // byte 8
    private int _intByAmLFO2;   // byte 9
    
    public static int BYTE_SIZE = 10;
}