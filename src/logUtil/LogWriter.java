package logUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * 
#Level������ȼ�SEVERE�����ֵ�� ��WARNING ��INFO ��CONFIG ��FINE ��FINER ��FINEST�����ֵ��  ��
#Ϊ Handler ָ��Ĭ�ϵļ���Ĭ��Ϊ Level.INFO����
java.util.logging.ConsoleHandler.level=INFO
#ָ��Ҫʹ�õ� Filter ������ƣ�Ĭ��Ϊ�� Filter����
java.util.logging.ConsoleHandler.filter=
#ָ��Ҫʹ�õ� Formatter ������ƣ�Ĭ��Ϊ java.util.logging.SimpleFormatter����
#java.util.logging.ConsoleHandler.formatter=com.ipan.project.test.MyFormatter
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter
#ָ��Ҫʹ�õ��ַ�����������ƣ�Ĭ��Ϊʹ��Ĭ��ƽ̨�ı��룩�� 
java.util.logging.ConsoleHandler.encoding=UTF-8
#Ϊ Handler ָ��Ĭ�ϵļ���Ĭ��Ϊ Level.ALL����
java.util.logging.FileHandler.level=INFO
#ָ��Ҫʹ�õ� Filter ������ƣ�Ĭ��Ϊ�� Filter����
java.util.logging.FileHandler.filter=
#ָ��Ҫʹ�õ� Formatter ������ƣ�Ĭ��Ϊ java.util.logging.XMLFormatter����
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
#ָ��Ҫʹ�õ��ַ�����������ƣ�Ĭ��ʹ��Ĭ�ϵ�ƽ̨���룩�� 
java.util.logging.FileHandler.encoding=UTF-8
#ָ��Ҫд�뵽�����ļ��Ľ�������������ֽ�Ϊ��λ�����������Ϊ 0����û�����ƣ�Ĭ��Ϊ�����ƣ��� 
java.util.logging.FileHandler.limit=10485760
#ָ���ж�������ļ�����ѭ����Ĭ��Ϊ 1����
java.util.logging.FileHandler.count=1
#Ϊ���ɵ�����ļ�����ָ��һ��ģʽ���й�ϸ����μ��������ݣ�Ĭ��Ϊ "%h/java%u.log"����
java.util.logging.FileHandler.pattern=f:/log/test_log_%u.log
#ָ���Ƿ�Ӧ�ý� FileHandler ׷�ӵ��κ������ļ��ϣ�Ĭ��Ϊ false����
java.util.logging.FileHandler.append=true
#ע���Logger
handlers=java.util.logging.ConsoleHandler,java.util.logging.FileHandler 
#������������Ϊ false ʱ����ҪΪ�� logger ���� Handler�����򲻴����κ���Ϣ��
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