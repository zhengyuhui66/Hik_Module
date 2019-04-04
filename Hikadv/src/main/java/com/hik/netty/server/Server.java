package com.hik.netty.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hik.Exception.ReallyRunException;
import com.hik.Exception.ReallyStopException;

public abstract class Server {
	protected Log log = LogFactory.getLog(this.getClass()); 
	/**
	 * 自定义端口
	 * @param port
	 */
	public abstract void StartServer(final int port)  throws ReallyRunException;

	public abstract void StartServer()  throws ReallyRunException;
	/**
	 * 停止服务器
	 */
	public abstract void StopServer()  throws ReallyStopException ;
}
