#!/usr/bin/python

#
# Convert raw dump to a java file.
#

import sys
import re

def main (argv):
    if len(argv) != 2:
        print 'Usage: ',argv[0],' <input>'
        sys.exit(1)
    
    inFilename = argv[1]
    inFile = open (inFilename, "r")
    
    openFile()
    
    msgCount = 0;
    for l in inFile.readlines():
        if "inside decodeMessage" in l:
            msgCount = msgCount + 1
    print 'msgCount ',msgCount
    
    
    fullMsg = []
    msgNum = 0
    inFile.seek(0)
    for l in inFile.readlines():
        l = l.rstrip()
        if "inside decodeMessage" in l:
            if len(fullMsg) != 0:
                printMsg (fullMsg)
        if re.search ('\w{3}', l):  # get rid of verbage lines
            continue
        elif l.strip() is "": # get rid of blank lines
            continue
        else:
            bytes = l.split()
            fullMsg.extend(bytes)
    printMsg (fullMsg)
     
    closeFile()

    inFile.close()

def printMsg (bytes):
    bytesPerLine = 7
    
    openMsg()
    
    i = 0
    while i < len(bytes):
        if (i % bytesPerLine) == 0:
            if i != 0:
                sys.stdout.write ('\n')
            sys.stdout.write ('            ')   # indent at the beginning of the line
        sys.stdout.write ('(byte)0x'+bytes[i])
        if i != (len(bytes) - 1):
            sys.stdout.write (', ')
        i = i + 1
    
    sys.stdout.write('\n')
    closeMsg()
    closeMethod()

def openFile():
    print 'package StagedData;'
    print
    print 'import javax.sound.midi.InvalidMidiDataException;'
    print 'import javax.sound.midi.SysexMessage;'
    print
    print 'public class xxx {'

def openMsg():
    print '    public static SysexMessage[] bank1patch0 () {'
    print '        SysexMessage[] retMsgs = new SysexMessage[ 1 ];'
    print '        int i = 0;'
    print
    print '        byte[] data = new byte[] {'

def closeMsg ():
    print '        };'
    print
    print '        retMsgs[i] = new SysexMessage();'
    print '        try {'
    print '            retMsgs[i].setMessage(data, data.length);'
    print '        }'
    print '        catch (InvalidMidiDataException e) {'
    print '            e.printStackTrace();'
    print '            return null;'
    print '        }'
    print '        ++i;'
    print

def closeMethod ():
    print '        return retMsgs;'
    print '    }'
    
def closeFile():
    print '}'

if __name__ == "__main__":
    main (sys.argv)
