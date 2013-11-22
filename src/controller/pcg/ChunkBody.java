package controller.pcg;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ChunkBody {
    public int unpack (byte data[], int offset) throws BadDataException {
        offset = getHeader (data, offset);
        offset = getBody (data, offset);
        
        return offset;
    }

    public int pack (byte data[], int offset) throws BadDataException {
        offset = putHeader (data, offset);
        offset = putBody (data, offset);
        
        return offset;
    }
    
    protected int getHeader (byte data[], int offset) throws BadDataException {
        ByteBuffer wrapped = ByteBuffer.wrap (data);
        
        _count = wrapped.getInt(offset);
        offset += 4;
        _size = wrapped.getInt(offset);
        offset += 4;
        _bankId = wrapped.getInt(offset);
        offset += 4;
        
        return offset;
    }
    
    protected int putHeader (byte data[], int offset) {
        ByteBuffer wrapped = ByteBuffer.wrap (data);
        
        wrapped.putInt(offset, _count);
        offset += 4;
        wrapped.putInt(offset, _size);
        offset += 4;
        wrapped.putInt(offset, _bankId);
        offset += 4;
        
        return offset;
    }
    
    private int getBody (byte data[], int offset) throws BadDataException {
        int dataSize = _count * _size;
        _progBankData = Arrays.copyOfRange(data, offset, offset + dataSize);
        offset += dataSize;
        
        return offset;
    }
    
    private int putBody (byte data[], int offset) throws BadDataException {
        int dataSize = _count * _size;
        System.arraycopy(_progBankData, 0, data, offset, dataSize);
        offset += dataSize;
        
        return offset;
    }
    
    public String toString () {
        String retString = "Count:   " + _count + "\n"
                         + "Size:    " + _size + "\n"
                         + "Bank ID: " + _bankId + "\n";
        
        return retString;
    }
    
    public int getBankId () {
        return _bankId;
    }
    
    protected int  _count;          // [4byte]
    protected int  _size;           // [4byte]
    protected int  _bankId;         // [4byte]
    protected byte _progBankData[]; // [variable]
}