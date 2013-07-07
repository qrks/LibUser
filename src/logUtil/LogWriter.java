package logUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * 
#Level的五个等级SEVERE（最高值） 、WARNING 、INFO 、CONFIG 、FINE 、FINER 、FINEST（最低值）  。
#为 Handler 指定默认的级别（默认为 Level.INFO）。
java.util.logging.ConsoleHandler.level=INFO
#指定要使用的 Filter 类的名称（默认为无 Filter）。
java.util.logging.ConsoleHandler.filter=
#指定要使用的 Formatter 类的名称（默认为 java.util.logging.SimpleFormatter）。
#java.util.logging.ConsoleHandler.formatter=com.ipan.project.test.MyFormatter
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter
#指定要使用的字符集编码的名称（默认为使用默认平台的编码）。 
java.util.logging.ConsoleHandler.encoding=UTF-8
#为 Handler 指定默认的级别（默认为 Level.ALL）。
java.util.logging.FileHandler.level=INFO
#指定要使用的 Filter 类的名称（默认为无 Filter）。
java.util.logging.FileHandler.filter=
#指定要使用的 Formatter 类的名称（默认为 java.util.logging.XMLFormatter）。
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
#指定要使用的字符集编码的名称（默认使用默认的平台编码）。 
java.util.logging.FileHandler.encoding=UTF-8
#指定要写入到任意文件的近似最大量（以字节为单位）。如果该数为 0，则没有限制（默认为无限制）。 
java.util.logging.FileHandler.limit=10485760
#指定有多少输出文件参与循环（默认为 1）。
java.util.logging.FileHandler.count=1
#为生成的输出文件名称指定一个模式。有关细节请参见以下内容（默认为 "%h/java%u.log"）。
java.util.logging.FileHandler.pattern=f:/log/test_log_%u.log
#指定是否应该将 FileHandler 追加到任何现有文件上（默认为 false）。
java.util.logging.FileHandler.append=true
#注册根Logger
handlers=java.util.logging.ConsoleHandler,java.util.logging.FileHandler 
#将此属性设置为 false 时，需要为此 logger 配置 Handler，否则不传递任何消息。
useParentHandlers=true
 * 
 * */

public class LogWriter {


	public Logger getLogWriter(String fileName,boolean append) throws Exception {
//		String d1 = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		java.util.Date d = new java.util.Date();
		String logname = this.getClass().getSimpleName() + d.getTime();
		System.out.println(logname);
		return getEnhancedLogWriter(logname,fileName,append);
	}
	
	private Logger getEnhancedLogWriter(String loggerName, String fileName,
			boolean append) throws Exception {
		Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(Level.INFO);

//		ConsoleHandler consoleHandler = new ConsoleHandler();
//		consoleHandler.setLevel(Level.ALL);
//		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler(fileName, append);
		fileHandler.setLevel(Level.INFO);
		// fileHandler.setFormatter(new MyLogHander());
		fileHandler.setFormatter(new MyLogHander());
		logger.addHandler(fileHandler);
		
//		Handler[] hds = logger.getHandlers();
//		for(int i=0; i<hds.length; i++) {
//			String  s= hds[i].getClass().getName();
//			System.out.println(s);
//		}

		return logger;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class MyLogHander extends Formatter {
	Date dat = new Date();
	private final static String format = "{0,date} {0,time,HH:mm:ss:SSS}";
	private MessageFormat formatter;

	private Object args[] = new Object[1];

	// Line separator string. This is the value of the line.separator
	// property at the moment that the SimpleFormatter was created.
	private String lineSeparator = (String) java.security.AccessController
			.doPrivileged(new sun.security.action.GetPropertyAction(
					"line.separator"));

	/**
	 * Format the given LogRecord.
	 * 
	 * @param record
	 *            the log record to be formatted.
	 * @return a formatted log record
	 */
	public synchronized String format(LogRecord record) {
		StringBuffer sb = new StringBuffer();
		// Minimize memory allocations here.
		dat.setTime(record.getMillis());
		args[0] = dat;
		StringBuffer text = new StringBuffer();
		if (formatter == null) {
			formatter = new MessageFormat(format);
		}
		formatter.format(args, text, null);
		sb.append(text);
		sb.append(" ");

		// if (record.getSourceClassName() != null) {
		// sb.append(record.getSourceClassName());
		// } else {
		// sb.append(record.getLoggerName());
		// }
		// if (record.getSourceMethodName() != null) {
		// sb.append(" ");
		// sb.append(record.getSourceMethodName());
		// }
		// sb.append(lineSeparator);

		String message = formatMessage(record);
		sb.append("[" + record.getLevel() + "]");
		sb.append(": ");
		sb.append(message);
		sb.append(lineSeparator);
		if (record.getThrown() != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch (Exception ex) {
			}
		}
		return sb.toString();
	}

}