package io.vertx.ext.web.handler.sse.impl;

import io.vertx.core.buffer.Buffer;

class SSEPacket {

	/* Use constants, but hope this will never change in the future (it should'nt) */
	private static final String END_OF_PACKET = "\n\n";
	private static final String LINE_SEPARATOR = "\n";
	private static final String FIELD_SEPARATOR = ":";

	private final StringBuilder payload;
	String headerName;
	String headerValue;

	SSEPacket() {
		payload = new StringBuilder();
	}

	boolean append(Buffer buffer) {
		String response = buffer.toString();
		if (buffer.toString().startsWith(":")) {
			// If the line starts with a U+003A COLON character (:)
			// Ignore the line.
			return false;
		}
		boolean willTerminate = response.endsWith(END_OF_PACKET);		
		if (!willTerminate){ // it is pointless to process incomplete packet
			return willTerminate;
		}
		String[] lines = response.split(LINE_SEPARATOR);
		for (int i = 0; i < lines.length; i++) {
			final String line = lines[i];
			int idx = line.indexOf(FIELD_SEPARATOR);
			if (idx == -1) {
				continue; // ignore line
			}
			final String type = line.substring(0, idx);
			final String data = line.substring(idx + 2, line.length());
			if (i == 0 && headerName == null && !"data".equals(type)) {
				headerName = type;
				headerValue = data;
			} else {
				payload.append(data).append(LINE_SEPARATOR);
			}
		}
		return willTerminate;
	}

	@Override
	public String toString() {
		return (payload == null) ? "" : payload.toString();
	}
}
