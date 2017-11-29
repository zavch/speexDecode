package util.speex;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * speex解码 
 * @author xavier
 * @version 1.0
 */
public class SpeexUtils {
	static {
		String resource = NativeUtils.getResource();
		if(resource != null) {
			try { 
				InputStream in = SpeexUtils.class.getResourceAsStream("/"+resource);
				File file = File.createTempFile(NativeUtils.FILENAME, NativeUtils.SUFFIX);
				// 依赖文件
				InputStream depIn = SpeexUtils.class.getResourceAsStream("/"+resource.replace(NativeUtils.FILENAME, NativeUtils.DEP_FILENAME));
				// 依赖库文件与动态库文件在同一个目录下
				File depFile = new File(file.getParent()+File.separator+NativeUtils.DEP_FILENAME+NativeUtils.SUFFIX);
				writeStreamToFile(in, file);
				writeStreamToFile(depIn, depFile);
				// 加载动态库的依赖库
				System.load(depFile.getPath());
				// 加载动态库
				System.load(file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("无此动态库");
		}
		
	}
	/**
	 * speex解码为wav
	 * @param src
	 * @param dist
	 * @return
	 */
	public static native boolean decode(String src, String dist);
	/**
	 * 输入流写入文件
	 * @param in
	 * @param file
	 */
	public static void writeStreamToFile(InputStream in, File file) {
		BufferedOutputStream out = null;
		int i;
		byte[] b = new byte[1024];
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			while((i = in.read(b)) != -1) {
				out.write(b, 0, i);
			}
			out.flush();
			// windows下不起作用
			file.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("文件无法写入！");
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
