package util.speex;

import java.io.File;
/**
 * 本地相关的类
 * @author xavier
 * @version 1.0 
 */
public class NativeUtils {
	public static String FILENAME = "libjspeex";
	public static String DEP_FILENAME = "libspeex";
	public static String SUFFIX = getSuffixByOS();
	/**
	 * 获得资源
	 * @return
	 */
	public static String getResource() {
		String arch = getArch();
		String suffix = getSuffixByOS();
		if(arch != null && suffix != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(arch);
			buffer.append(File.separator);
			buffer.append(FILENAME);
			buffer.append(suffix);
			return buffer.toString();
		}
		return null;
	}
	/**
	 * 获得资源后缀
	 * @return
	 */
	public static String getSuffixByOS() {
		switch(getOS()) {
			case LINUX: return ".so";
			case WIN: return ".dll";
			default:
		}
		return null;
	}
	/**
	 * 获得JVM位数
	 * @return
	 */
	public static String getArch() {
		String arch = System.getProperty("sun.arch.data.model");
		if(arch != null && arch.contains("32")) {
			return "32";
		}
		return "64";
	}
	/**
	 * 判断当前操作系统
	 * @return
	 */
	public static OS getOS() {
		String osName = System.getProperty("os.name").toUpperCase();
		if(osName.contains("LINUX") || osName.contains("NIX")) {
			return OS.LINUX;
		}
		if(osName.contains("WIN")) {
			return OS.WIN;
		}
		return OS.OTHER;
	}
}
