/**
 * Project: ${puma-parser.aid}
 * 
 * File Created at 2012-6-24
 * $Id$
 * 
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.puma.parser.mysql.variable.status;

import com.dianping.puma.parser.mysql.StatusVariable;
import com.dianping.puma.utils.PacketUtils;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 
 * TODO Comment of QTimeZoneCode
 * 
 * @see http://code.google.com/p/open-replicator/
 * @author Leo Liang
 * 
 */
public class QTimeZoneCode implements StatusVariable {

	private String	timeZone;

	public QTimeZoneCode(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public static QTimeZoneCode valueOf(ByteBuffer buf) throws IOException {
		int length = PacketUtils.readInt(buf, 1);
		return new QTimeZoneCode(PacketUtils.readFixedLengthString(buf, length));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QTimeZoneCode [timeZone=" + timeZone + "]";
	}

}
