/**
 * Create a dump message that will be sent to the hardware.
 */

package util;

public class TritonDump {
    public static final byte CURRENT_PROGRAM_PARAMETER_DUMP = 0x40;
    public static final byte PROGRAM_PARAMETER_DUMP = 0x4C;
    public static final byte CURRENT_COMBINATION_PARAMETER_DUMP = 0x49;
    public static final byte COMBINATION_PARAMETER_DUMP = 0x4D;
    public static final byte SEQUENCE_DATA_DUMP = 0x48;
    public static final byte GLOBAL_DATA_DUMP = 0x51;

    public static final byte DRUMKIT_DATA_DUMP = 0x52;
    public static final byte ARPEGGIO_PATTERN_DATA_DUMP = 0x69;
    public static final byte ALL_DATA_DUMP = 0x50;
}
