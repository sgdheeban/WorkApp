package com.workappinc.workappserver.common.logging;

/**
 * IApplicationLogger Interface is a top-level interface for deriving a common
 * Logger Singleton Object
 * 
 * @author dhgovindaraj
 *
 */
public interface IApplicationLogger extends ILogger
{

	/*
	 * Log4J Log Levels ALL All levels including custom levels. DEBUG Designates
	 * fine-grained informational events that are most useful to debug an
	 * application. ERROR Designates error events that might still allow the
	 * application to continue running. FATAL Designates very severe error
	 * events that will presumably lead the application to abort. INFO
	 * Designates informational messages that highlight the progress of the
	 * application at coarse-grained level. OFF The highest possible rank and is
	 * intended to turn off logging. TRACE Designates finer-grained
	 * informational events than the DEBUG. WARN Designates potentially harmful
	 * situations.
	 */

	/**
	 * LogPerf is used to Log Perf Param during each API call
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogPerf(Object ctx, Class<?> className);

	/**
	 * LogException is used to Log exception Info throughout the Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has exception context about
	 *            the current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogException(Object ctx, Class<?> className);

	/**
	 * LogFatal is used to Log Fatal message, which will halt the Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogFatal(Object ctx, Class<?> className);

	/**
	 * LogWarn is used to Log general Error Scenarios throughout the Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogError(Object ctx, Class<?> className);

	/**
	 * LogWarn is used to Log general Warning Scenarios throughout the
	 * Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogWarn(Object ctx, Class<?> className);

	/**
	 * LogInfo is used to Log general Info throughout the Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogInfo(Object ctx, Class<?> className);

	/**
	 * LogWarn is used to Log general Debug Scenarios throughout the Application
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogDebug(Object ctx, Class<?> className);

	/**
	 * LogTrace is used to Log Trace, which is finer detail than Debug
	 * 
	 * @param ctx
	 *            Takes a Object Parameter, which is has all context about the
	 *            current request under processing
	 * @param ClassName
	 *            Takes the caller's ClassName to be used while Logging
	 */
	public void LogTrace(Object ctx, Class<?> className);

}
