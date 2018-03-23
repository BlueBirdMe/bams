package com.pinhuba.common.util.os.command;

import java.io.BufferedInputStream;
import java.io.InputStream;

class CommandReader extends Thread {
	InputStream in;
	byte[] result = new byte[512];
	StringBuilder builder = new StringBuilder();
	Throwable ex = null;

	/***************************************************************************
	 * 
	 * @param in
	 */
	public CommandReader(InputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		BufferedInputStream bufferIn = new BufferedInputStream(in);
		try {
			for (int length = bufferIn.read(result); length != -1; length = bufferIn
					.read(result)) {
				builder.append(new String(result));
			}
		} catch (Exception e) {
			ex = e;
		} finally {
			try {
				bufferIn.close();
			} catch (Exception e) {
				//
			}
		}
	}

	public String getResult() {
		return builder.toString();
	}

	public Throwable getEx() {
		return ex;
	}
}
