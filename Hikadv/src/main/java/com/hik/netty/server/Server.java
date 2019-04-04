package com.hik.netty.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hik.Exception.ReallyRunException;
import com.hik.Exception.ReallyStopException;

public abstract class Server {
	protected Log log = LogFactory.getLog(this.getClass()); 
	/**
	 * �Զ���˿�
	 * @param port
	 */
	public abstract void StartServer(final int port)  throws ReallyRunException;

	public abstract void StartServer()  throws ReallyRunException;
	/**
	 * ֹͣ������
	 */
	public abstract void StopServer()  throws ReallyStopException ;
}
