package com.pinhuba.common.util.file.xml;

public final class XmlDAOException extends Exception
{
	private static final long serialVersionUID = 1L;

	public XmlDAOException()
	{
		super();
	}
	
	public XmlDAOException(String msg)
	{
		super(msg);
	}
	
	public XmlDAOException(String msg, Throwable cause)
	{
		super(msg,cause);
	}
	
	public XmlDAOException(Throwable cause) {
		super(cause);
	}
}
