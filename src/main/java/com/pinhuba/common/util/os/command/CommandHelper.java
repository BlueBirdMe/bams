package com.pinhuba.common.util.os.command;

import java.io.File;
import java.io.InputStream;

/***********************************************************************************************************************
 * @author guobaoping
 * @purpose:
 */
public class CommandHelper
{
	/***************************************************************************
	 * 
	 */
	private CommandHelper()
	{
		//
	}

	/*********************************************************************************************************************
	 * ,k',java.lang.Runtimeеexec
	 * 
	 * @param command
	 * @param envp
	 * @param dir
	 * @return
	 */
	private static final ResultSet runScript(Object command, String[] envp, File dir, int type)
	{
		Process process = null;
		Throwable ex = null;
		int code = Integer.MAX_VALUE;
		StringBuilder stdOut = new StringBuilder(), stdError = new StringBuilder();
		try
		{
			if (command == null)
			{
				throw new IllegalArgumentException("");//$NON-NLS-1$
			}
			Runtime runTime = Runtime.getRuntime();
			if (command instanceof String)
			{
				process = runTime.exec(command.toString(), envp, dir);
			}
			else if (command instanceof String[])
			{
				process = runTime.exec((String[]) command, envp, dir);
			}
			else
			{
				throw new IllegalArgumentException("");//$NON-NLS-1$
			}
			InputStream input = process.getInputStream();
			byte[] buffer = new byte[512];
			for (int length = input.read(buffer); length != -1; length = input.read(buffer))
			{
				stdOut.append(new String(buffer));
			}
			input.close();

			input = process.getErrorStream();
			for (int length = input.read(buffer); length != -1; length = input.read(buffer))
			{
				stdError.append(new String(buffer));
			}
			input.close();
			process.waitFor();
			code = process.exitValue();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (process != null)
			{
				process.destroy();
			}
		}
		return new ResultSet(code, stdOut.toString(), ex, stdError.toString());
	}

	/*********************************************************************************************************************
	 * ,k',java.lang.Runtimeеexec
	 * 
	 * @param command
	 * @return
	 */
	public static final ResultSet runScript(final String command)
	{
		return runScript(command, null, null, 0);
	}

	/*********************************************************************************************************************
	 * ,k',java.lang.Runtimeеexec
	 * 
	 * @param command
	 * @return
	 */
	public static final ResultSet runScript(final String[] command)
	{
		return runScript(command, null, null, 0);
	}

	/*********************************************************************************************************************
	 * ,k',java.lang.Runtimeеexec
	 * 
	 * @param command
	 * @param envp
	 * @param dir
	 * @return
	 */
	public static final ResultSet runScript(String[] command, String[] envp, File dir)
	{
		return runScript(command, envp, dir, 0);
	}

	/*********************************************************************************************************************
	 * 
	 * 
	 * @param ex
	 * @return
	 */
	public static String getStackTraces(Throwable ex)
	{
		StringBuilder buffer = new StringBuilder();
		StackTraceElement[] stacks = ex.getStackTrace();
		for (int i = 0; i < stacks.length; i++)
		{
			buffer.append(stacks[i].toString());
		}
		return buffer.toString();
	}
}
