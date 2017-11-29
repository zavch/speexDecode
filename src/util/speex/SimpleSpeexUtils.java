package util.speex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleSpeexUtils {
	static{
		InputStream in = null;
		FileOutputStream out = null;
		try {
			// 获得动态库
			in = SimpleSpeexUtils.class.getClassLoader().getResourceAsStream("libjspeex.so");
			// 创建临时文件
			File temp = File.createTempFile("libjspeex", ".so");
			out = new FileOutputStream(temp);
			int i;
			byte[] buf = new byte[1024];
			while((i = in.read(buf)) != -1){
				out.write(buf, 0, i);
			}
			// 释放资源
			in.close();
			out.close();
			temp.deleteOnExit();
			// 加载动态库
			System.load(temp.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * .speex to .wav
	 * @param src 需要解码文件的路径
	 * @param dist  解码文件所在的路径
	 * @return
	 */
	public static native boolean decode(String src, String dist);
	public static void main(String[] args) {
		String src = "/home/xavier/Downloads/b.speex";
		String des = "/home/xavier/Downloads/b-test.wav";
		SimpleSpeexUtils.decode(src, des);
	}
}
