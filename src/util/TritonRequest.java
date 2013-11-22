package util;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

public class TritonRequest {
	public static final byte MODE_REQUEST =                               0x12;
	public static final byte CURRENT_PROGRAM_PARAMERTER_DUMP_REQUEST =    0x10;
	public static final byte PROGRAM_PARAMETER_DUMP_REQUEST =             0x1C;
	public static final byte CURRENT_COMBINATION_PARAMETER_DUMP_REQUEST = 0x19;
	public static final byte COMBINATION_PARAMETER_DUMP_REQUEST =         0x1D;
	public static final byte SEQUENCE_DATA_DUMP_REQUEST =                 0x18;
	public static final byte GLOBAL_DATA_DUMP_REQUEST =                   0x0E;
	public static final byte DRUMKIT_DATA_DUMP_REQUEST =                  0x0D;
	public static final byte ARPEGGIO_PATTERN_DATA_DUMP_REQUEST =         0x34;
	public static final byte ALL_DATA_DUMP_REQUEST =                      0x0F;
	public static final byte PROGRAM_WRITE_REQUEST =                      0x11;
	public static final byte COMBINATION_WRITE_REQUEST =                  0x1A;
	
    /*****************************
     *     PROGRAM REQUESTS
     *****************************/
    
    public static SysexMessage requestCurrentProgramParameterDump (int channel) {
        byte data[] = new byte[1];
        data[0] = 0x00; // reserved
        return buildRequest(CURRENT_PROGRAM_PARAMERTER_DUMP_REQUEST, channel, data);
    }
    
    public static SysexMessage requestAllPrograms (int channel) {
        return requestProgramParameterDump (channel, 0, 0, 0);
    }
    
    public static SysexMessage requestBankProgams (int channel, int bank) {
        return requestProgramParameterDump (channel, 1, bank, 0);
    }
    
    public static SysexMessage requestSingleProgram (int channel, int bank, int prog) {
        return requestProgramParameterDump (channel, 2, bank, prog);
    }
    
    public static SysexMessage requestProgramParameterDump (int channel, int kind, int bank, int prog) {
        byte data[] = new byte[3];
        data[0]  = (byte) (((byte)kind << 4) & 0x30);  // 00kk 0bbb
        data[0] += (byte) (((byte)bank) & 0x07);
        data[1]  = (byte) (0x7F & (byte)prog);
        data[2]  = 0x00; // reserved
        
        return buildRequest(PROGRAM_PARAMETER_DUMP_REQUEST, channel, data);
    }
    
    public static SysexMessage requestProgramWrite (int channel, int bank, int prog) {
        byte data[] = new byte[2];
        data[0] = (byte) (0x07 & (byte)bank); // 0000 0bbb
        data[1] = (byte) (0x7F & (byte)prog); // 0ppp pppp
        
        return buildRequest(PROGRAM_WRITE_REQUEST, channel, data);
    }
    
    /*****************************
     *     COMBI REQUESTS
     *****************************/
    
    public static SysexMessage requestCurrentCombiParameterDump (int channel) {
        byte data[] = new byte[1];
        data[0] = 0x00; // reserved
        
        return buildRequest(CURRENT_COMBINATION_PARAMETER_DUMP_REQUEST, channel, data);
    }
    
    public static SysexMessage requestAllCombis (int channel) {
        return  requestCombiParameterDump (channel, 0, 0, 0);
    }
    
    public static SysexMessage requestBankCombis (int channel, int bank) {
        return requestCombiParameterDump (channel, 1, bank, 0);
    }
    
    public static SysexMessage requestSingleCombi (int channel, int bank, int prog) {
        return requestCombiParameterDump (channel, 2, bank, prog);
    }
    
    public static SysexMessage requestCombiParameterDump (int channel, int kind, int bank, int prog) {
        byte data[] = new byte[3];
        data[0]  = (byte) (((byte)kind << 4) & 0x30);  // 00kk 0bbb
        data[0] += (byte) (((byte)bank) & 0x07);
        data[1]  = (byte) (0x7F & (byte)prog);
        data[2]  = 0x00; // reserved
        
        return buildRequest(COMBINATION_PARAMETER_DUMP_REQUEST, channel, data);
    }
    
    public static SysexMessage requestCombiWrite (int channel, int bank, int combi) {
        byte data[] = new byte[2];
        data[0] = (byte) (0x07 & (byte)bank);  // 0000 0bbb
        data[1] = (byte) (0x7F & (byte)combi); // 0ccc cccc
        
        return buildRequest(COMBINATION_WRITE_REQUEST, channel, data);
    }
    
    /*****************************
     *     DK REQUESTS
     *****************************/
    
    public static SysexMessage requestAllDrumkits (int channel) {
        return requestDrumkit (channel, 0, 0);
    }
    
    public static SysexMessage requestDrumkit (int channel, int kind, int dumkit) {
        byte data[] = new byte[3];
        data[0] = (byte) (0x01 & (byte)kind);    // 0000 000k
        data[1] = (byte) (0x7F & (byte)dumkit);  // 0ddd dddd
        data[2] = 0x00; // reserved
        
        return buildRequest(DRUMKIT_DATA_DUMP_REQUEST, channel, data);
    }
    
    /*****************************
     *     ARP REQUESTS
     *****************************/
    
    public static SysexMessage requestAllApreggios (int channel) {
        return requestArpeggio (channel, 0, 0);
    }
    
    public static SysexMessage requestArpeggio (int channel, int kind, int arp) {
        byte data[] = new byte[3];
        data[0] = (byte) (0x01 & (byte)kind);        // 0000 000k
        data[1] = (byte) (0x01 & ((byte)arp >> 7));  // 0ddd dddd
        data[2] = (byte) (0x7F & (byte)arp);         // 0ddd dddd
        
        return buildRequest(DRUMKIT_DATA_DUMP_REQUEST, channel, data);
    }
    
    /*****************************
     *     SEQUENCE REQUEST
     *****************************/
    
    public static SysexMessage requestSequenceData (int channel) {
        byte data[] = new byte[1];
        data[0] = 0x00; // reserved
        
        return buildRequest(SEQUENCE_DATA_DUMP_REQUEST, channel, data);
    }
    
    /*****************************
     *     GLOBAL REQUEST
     *****************************/
    
    public static SysexMessage requestGlobalData (int channel) {
        byte data[] = new byte[1];
        data[0] = 0x00; // reserved
        
        return buildRequest(GLOBAL_DATA_DUMP_REQUEST, channel, data);
    }
    
    /*****************************
     *     OTHER REQUESTS
     *****************************/
    
    public static SysexMessage requestAllData (int channel) {
        byte data[] = new byte[1];
        data[0] = 0x00; // reserved
        return buildRequest(ALL_DATA_DUMP_REQUEST, channel, data);
    }
    
    public static SysexMessage requestDeviceInformatin (int channel) {
        SysexMessage msg = new SysexMessage();
        byte[] data = new byte[100];

        int i = 0;
        data[i++] = (byte) 0xF0;    // system exclusiveusive
        data[i++] = (byte) 0x7E;    // non real time
        data[i++] = (byte) channel; // channel number
        data[i++] = (byte) 0x06;    // inquiry message
        data[i++] = (byte) 0x01;    // inquiry request
        data[i++] = (byte) 0xF7;    // end of exclusiveusive

        try {
            msg.setMessage(data, i);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return null;
        }
        return msg;
    }
    
    /*****************************
     *     Util Methods
     *****************************/
    
	public static SysexMessage buildRequest (byte type, int channel) {
		return buildRequest (type, channel, null);
	}
	
	public static SysexMessage buildRequest (byte type, int channel, byte inputData[]) {
		SysexMessage msg = new SysexMessage();
		byte[] msgData = new byte[inputData.length + 6]; // 5 for header, 1 for last F7

		int i = 0;
    	msgData[i++] = (byte)  0xF0;
    	msgData[i++] = (byte)  0x42; // Korg ID
    	msgData[i++] = (byte) (0x30 | channel); // 0x3n Format ID, n = channel number
    	msgData[i++] = (byte)  0x50; // Triton series ID - might just be studio
		msgData[i++] = (byte) type;
		if (inputData != null) {
			for (int j = 0; j < inputData.length; ++j) {
				System.out.println (String.format("%02X", inputData[j]));
				msgData[i++] = inputData[j];
			}
		}
		msgData[i++] = (byte) 0xF7;  // end of exclusive
		
		try {
			msg.setMessage(msgData, msgData.length);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			return null;
		}
		return msg;
	}
}