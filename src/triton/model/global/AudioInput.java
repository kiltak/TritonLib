package triton.model.global;

public class AudioInput {
    public int unpack (byte[] data, int offset) {
        _level = data[offset++];
        _pan = data[offset++];
        _send1Level = data[offset++];
        _send2Level = data[offset++];
        _busSelect = data[offset++];
        
        return offset;
    }
    
    public int pack (byte[] data, int offset) {
        data[offset++] = (byte)_level;
        data[offset++] = (byte)_pan;
        data[offset++] = (byte)_send1Level;
        data[offset++] = (byte)_send2Level;
        data[offset++] = (byte)_busSelect;
        
        return offset;
    }
    
    private int _level;      // byte 0
    private int _pan;        // byte 1
    private int _send1Level; // byte 2
    private int _send2Level; // byte 3
    private int _busSelect;  // byte 4
    
    public static final int BYTE_SIZE = 5;
}
