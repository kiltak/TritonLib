package triton.model.sound.program.oscillator;

import triton.util.Limits;

class Header {
    public int unpack (byte data[], int offset) {
        _hiStartOffset = (data[offset] & 0x80) >> 7;
        _hiReverse = (data[offset] & 0x40) >> 6;
        _hiSampleNoMSB = data[offset] & 0x03;
        ++offset;
        
        _hiSampleNoLSB = data[offset++] & 0xFF;
        _hiBank = data[offset++];
        _hiLevel = data[offset++];
        
        _lowStartOffset = (data[offset] & 0x80) >> 7;
        _lowReverse = (data[offset] & 0x40) >> 6;
        _lowSampleNoMSB = data[offset] & 0x03;
        ++offset;
        
        _lowSampleNoLSB = data[offset++] & 0xFF;
        _lowBank = data[offset++];
        _lowLevel = data[offset++];
        _delayStart = data[offset++];
        _velMSampleSw = data[offset++];
        _velZoneBottom = data[offset++];
        _velZoneTop = data[offset++];
        
        validate();
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset]  = (byte)(_hiStartOffset << 7);
        data[offset] |= (byte)(_hiReverse << 6);
        data[offset] |= (byte)_hiSampleNoMSB;
        ++offset;
        
        data[offset++] = (byte)_hiSampleNoLSB;
        data[offset++] = (byte)_hiBank;
        data[offset++] = (byte)_hiLevel;
        
        data[offset]  = (byte)(_lowStartOffset << 7);
        data[offset] |= (byte)(_lowReverse << 6);
        data[offset] |= (byte)_lowSampleNoMSB;
        ++offset;
        
        data[offset++] = (byte)_lowSampleNoLSB;
        data[offset++] = (byte)_lowBank;
        data[offset++] = (byte)_lowLevel;
        data[offset++] = (byte)_delayStart;
        data[offset++] = (byte)_velMSampleSw;
        data[offset++] = (byte) _velZoneBottom;
        data[offset++] = (byte)_velZoneTop;
        
        return offset;
    }
    
    private boolean validate () {
        boolean retVal = true;

        try {
            Limits.checkLimits ("Header._hiStartOffset", _hiStartOffset, 0, 1);
            Limits.checkLimits ("Header._hiReverse", _hiReverse, 0, 1);
            
            int hiSampleNo = ((_hiSampleNoMSB << 8) + _hiSampleNoLSB) &0xFFFF;
            Limits.checkLimits ("Header.hiSampleNo", hiSampleNo, 0, 0x03E7);
            Limits.checkLimits ("Header._hiSampleNoMSB", _hiSampleNoMSB, 0, 0x03);
            Limits.checkLimits ("Header._hiSampleNoLSB", _hiSampleNoLSB, 0, 0xFF);
            
//            Limits.checkLimits ("Header._hiBank", _hiBank, 0, 1);
            Limits.checkLimits ("Header._hiLevel", _hiLevel, 0, 127);
            Limits.checkLimits ("Header._lowStartOffset", _lowStartOffset, 0, 1);
            Limits.checkLimits ("Header._lowReverse", _lowReverse, 0, 1);
            
            int lowSampleNo = ((_lowSampleNoMSB << 8) + _lowSampleNoLSB) & 0xFFFF;
            Limits.checkLimits ("Header.lowSampleNo", lowSampleNo, 0, 0x03E7);
            Limits.checkLimits ("Header._lowSampleNoMSB", _lowSampleNoMSB, 0, 0x03);
            Limits.checkLimits ("Header._lowSampleNoLSB", _lowSampleNoLSB, 0, 0xFF);
            
//            Limits.checkLimits ("Header._lowBank", _lowBank, 0, 1);
            Limits.checkLimits ("Header._lowLevel", _lowLevel, 0, 127);
            Limits.checkLimits ("Header._delayStart", _delayStart, 0, 0x61);
            Limits.checkLimits ("Header._velMSampleSw", _velMSampleSw, 0, 127);
            Limits.checkLimits ("Header._velZoneBottom", _velZoneBottom, 0, 127);
            Limits.checkLimits ("Header._velZoneTop", _velZoneTop, 0, 127);
        }
        catch (IllegalArgumentException e) {
            System.out.println (e);
            retVal = false;
        }
        
        return retVal;
    }
    
    private int _hiStartOffset;   // byte  0, bit  7
    private int _hiReverse;       // byte  0, bit  6 
    private int _hiSampleNoMSB;   // byte  0, bits 0-5
    private int _hiSampleNoLSB;   // byte  1
    private int _hiBank;          // byte  2
    private int _hiLevel;         // byte  3
    private int _lowStartOffset;  // byte  4, bit  7
    private int _lowReverse;      // byte  4, bit  6
    private int _lowSampleNoMSB;  // byte  4, bit  0-5
    private int _lowSampleNoLSB;  // byte  5
    private int _lowBank;         // byte  6
    private int _lowLevel;        // byte  7
    private int _delayStart;      // byte  8
    private int _velMSampleSw;    // byte  9
    private int _velZoneBottom;   // byte 10
    private int _velZoneTop;      // byte 11
    
    public static int BYTE_SIZE = 12;
}