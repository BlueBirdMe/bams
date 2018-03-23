package com.pinhuba.common.util.path;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;


public class PathHelper {
	/**
	 * ^j·
	 * 
	 * @param clz
	 *            ·
	 * @return ·
	 */
	public static String getClassLocalPath(Class clz) {
		URL result = null;
		String path = null;
		Class cls = clz;

		ProtectionDomain pd = cls.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();

		if (cs != null) {
			result = cs.getLocation();
		}

		if (result == null) {
			String filePath = cls.getName().replace('.', '/').concat(".class");
			ClassLoader loader = cls.getClassLoader();
			result = loader != null ? loader.getResource(filePath)
					: ClassLoader.getSystemResource(filePath);
		}

		try {
			path = new File(result.getFile()).getCanonicalPath();
		} catch (IOException e) {
			path = e.getMessage();
		}

		return path;
	}

	/**
	 * l· samples:
	 * getProjectPath(clz,"WebRoot")LWebRoot·
	 * getProjectPath(clz,"WEB-INF")LWEB-INF·
	 * 
	 * @param clz 
	 * @param toPath l
	 * @return
	 */
	public static String getOrderPath(Class clz, String toPath) {
		String path = getClassLocalPath(clz);

		if (path.indexOf(toPath) != -1) {
			path = path
					.substring(0, path.indexOf(toPath) + toPath.length());
		}
		return path;
	}

	/**
	 * WEB-INFl·
	 * 
	 * @return WEB-INF·
	 */
	public static String gotoWebInf() {
		return getOrderPath(PathHelper.class, "WEB-INF");
	}

	/**
	 * ·
	 * @return ·
	 */
	public static String gotoProjectPath() {
		String path = getClassLocalPath(PathHelper.class);
		
		if(path.indexOf("WebRoot") != -1){
			path = path.substring(0, path.indexOf("WebRoot"));
		}else if(path.indexOf("WEB-INF") != -1){
			path = path.substring(0, path.indexOf("WEB-INF"));
		}
		
		return path;
	}
	
	
	public static void main(String[] args){
//		System.out.println(PathHelper.getOrderPath(PathHelper.class,"WEB-INF"));
//		System.out.println(PathHelper.getClassLocalPath(PathHelper.class));
//		System.out.println(PathHelper.gotoProjectPath());
//		System.out.println(PathHelper.gotoWebInf());
	}
}
