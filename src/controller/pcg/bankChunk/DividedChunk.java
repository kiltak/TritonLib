package controller.pcg.bankChunk;

import java.nio.ByteBuffer;

import controller.pcg.BadDataException;
import controller.pcg.ChunkBody;

public class DividedChunk extends ChunkBody {
    public int unpack (byte data[], int offset) throws BadDataException {
        offset = unpackHeader(data, offset);

        offset += 4;  // WHY????
        
        return offset;
    }
    
    private int unpackHeader (byte data[], int offset) throws BadDataException {
        ByteBuffer wrapped = ByteBuffer.wrap (data);

        _status = wrapped.getShort(offset);
        offset += 2;

        _randomId = wrapped.getShort(offset);
        offset += 2;
        
        offset = _prog.unpack(data, offset);
        offset = _combi.unpack(data, offset);
        offset = _dkit.unpack(data, offset);
        offset = _arp.unpack(data, offset);
        
        _global = wrapped.getInt(offset);
        offset += 4;
        
        return offset;
    }
    
    public String toString() {
        String retString = "";
        
        retString += "Status    " + _status + "\n";
        retString += "Random ID " + _randomId + "\n";
        retString += "-- Prog --\n";
        retString += _prog.toString();
        retString += "-- Combi --\n";
        retString += _combi.toString();
        retString += "-- DKit --\n";
        retString += _dkit.toString();
        retString += "-- Arp --\n";
        retString += _arp.toString();
        retString += "-- Global --\n";
        retString += "   Info: " + _global + "\n";
        
        return retString;
    }
    
    private class miniChunk {
        public int unpack(byte data[], int offset) {
            ByteBuffer wrapped = ByteBuffer.wrap (data);
            _info = wrapped.getShort(offset);
            offset += 2;
            _bank = wrapped.getShort(offset);
            offset += 2;
            _reserved = wrapped.getInt(offset);
            offset += 4;
            
            return offset;
        }
        
        public String toString () {
            String retString = "";
            
            retString += "   Info      " + _info + "\n";
            retString += "   Bank      " + _bank + "\n";
            retString += "   Reserved  " + _reserved + "\n";
            
            return retString;
        }
        
        private int _info;     // [2byte]
        private int _bank;     // [2byte]
        private int _reserved; // [4byte]
    }
    
    private int _status;        //  [2byte] 0:Undivided/1:Divided
    private int _randomId;      //  [4byte]
    private miniChunk _prog = new miniChunk();
    private miniChunk _combi = new miniChunk();
    private miniChunk _dkit = new miniChunk();
    private miniChunk _arp = new miniChunk();
    private int _global;        //  [4byte] *9

}