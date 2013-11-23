/**
 * MidiSender throttles the requests being sent to the hardware.  If a command is sent to the hardware
 * while the hardware is responding to a previous request, the new command is dropped.
 * This class will keep all the commands to be sent to the hardware in a queue.  The runFlag is true when
 * hardware is open for business.  After sending a command, sleep for a second to give the hardware a chance
 * to start answering.
 * The class using this class has to set the runFlag to false when it is receiving a multi part message, then
 * back to true when the message is completed.
 */

package controller.utils;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.sound.midi.MidiMessage;

public class MidiSender implements Runnable {
	public MidiSender(TritonMidiIO m) {
		midiIO = m;
	}

	@Override
	public void run() {
		MidiMessage msg;
		while (true) {
			try {
				if (runFlag && !msgToSend.isEmpty()) {
					msg = msgToSend.remove();
					midiIO.reallySendMsg(msg);
					Thread.sleep(1000); // Give the hardware a little breathing room
				}
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				// Sometimes it's good to wake up and get to work!
			}
		}
	}

	public void addMsg(MidiMessage msg) {
		msgToSend.add(msg);
	}

	public void shouldRun(boolean runFlag) {
		this.runFlag = runFlag;
	}

	boolean runFlag = true;
	Queue<MidiMessage> msgToSend = new ArrayDeque<MidiMessage>(); // messages to send to hardware
	TritonMidiIO midiIO;
}
