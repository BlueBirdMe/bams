package com.pinhuba.common.util.file.xml;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.pinhuba.common.util.security.Base64;

public class XmlDAO {

	public static Map<String, String> xmlMap = new HashMap<String, String>();

	/**
	 * 
	 * @param xmlPath
	 * @param clz
	 * @return
	 * @throws XmlDAOException
	 *             ************************************************************************************************
	 */
	public synchronized static boolean initClassXml(String xmlPath, Class<?> clz) {
		String xmlFileName = xmlPath + File.separator + clz.getSimpleName()
				+ ".xml";
		
		File xmlFile = new File(xmlFileName);
		if (xmlMap.get(clz.getName()) == null) {
			xmlMap.put(clz.getName(), xmlPath + File.separator);
		}

		if (!xmlFile.exists()) {
			// save data to xml
			return saveClassToXml(clz);
		} else {
			// read config into to class
//			try {
//				return getClassFromXml(clz);
//			} catch (XmlDAOException e) {
				return false;
//			}
		}
	}

	/**
	 * 
	 * @param xmlPath
	 * @param obj
	 * @return ************************************************************************************************
	 */
	public synchronized static boolean initObjectXml(String xmlPath, Object obj) {
		String xmlFileName = xmlPath + File.separator
				+ obj.getClass().getSimpleName() + ".xml";
		File xmlFile = new File(xmlFileName);

		if (xmlMap.get(obj.getClass().getName()) == null) {
			xmlMap.put(obj.getClass().getName(), xmlPath + File.separator);
		}

		if (xmlFile.exists()) {
			return true;
		}

		Document document = DocumentHelper.createDocument();
		document.addElement("XmlRoot");

		if (!writeToXml(obj.getClass(), document)) {
			return false;
		}

		try {
			addNew(obj);
		} catch (XmlDAOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @param clz
	 * @return
	 * @throws XmlDAOException
	 *             ************************************************************************************************
	 */
	public synchronized static boolean getClassFromXml(Class<?> clz)
			throws XmlDAOException {
		String clzName = clz.getSimpleName();
		File xmFile = new File(xmlMap.get(clz.getName()) + clzName + ".xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(xmFile);
			Element root = document.getRootElement();
			Object obj = clz.newInstance();
			Method[] mths = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < mths.length; i++) {
				String mthName = mths[i].getName();
				if (mthName.startsWith("set")) {
					String fieldName = mthName.substring(3);
					Element et = (Element) root.selectSingleNode(fieldName);
				
					mths[i].invoke(obj, et.getText());
				}
			}
		} catch (Exception e) {
			throw new XmlDAOException(e);
		}

		return true;
	}

	/**
	 * 
	 * @param clz
	 * @return ************************************************************************************************
	 */
	public synchronized static boolean saveClassToXml(Class<?> clz) {
		String clzName = clz.getSimpleName();
		File xmlFile = new File(xmlMap.get(clz.getName()) + clzName + ".xml");
		try {
			if (xmlFile.exists()) {
				xmlFile.delete();
			}

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement(clzName);

			// get filed's name
			Method[] mths = clz.getDeclaredMethods();
			for (int i = 0; i < mths.length; i++) {
				String mthName = mths[i].getName();
				if (mthName.startsWith("get")) {
					String fieldName = mthName.substring(3);
					Element field = root.addElement(fieldName);
					Object fieldValue = mths[i].invoke(clz);
					if (fieldValue == null) {
						field.setText("");
					} else {
						field.setText(fieldValue.toString());
					}
				}
			}

			writeToXml(clz, document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws XmlDAOException
	 *             ************************************************************************************************
	 */
	public synchronized static boolean addNew(Object obj)
			throws XmlDAOException {
		String objName = obj.getClass().getSimpleName();
		File xmFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();

		Document document = null;
		try {
			document = saxReader.read(xmFile);
		} catch (Exception e) {
			throw new XmlDAOException("read file " + xmFile.getName()
					+ "failure.");
		}
		Element root = document.getRootElement();
		// get object's name
		Element row = root.addElement(objName);

		// get id's name
		Object chk = getObjectById(obj);
		if (chk != null) {
			throw new XmlDAOException("the id exists.");
		}

		// get filed's name
		Method[] mths = obj.getClass().getDeclaredMethods();
		for (int i = 0; i < mths.length; i++) {
			String mthName = mths[i].getName();
			if (mthName.startsWith("get")) {
				String fieldName = mthName.substring(3);
				Element field = row.addElement(fieldName);
				Object fieldValue = null;
				try {
					fieldValue = mths[i].invoke(obj);
				} catch (Exception e) {
					throw new XmlDAOException(e);
				}

				if (fieldValue == null) {
					field.setText("");
				} else {
					field.setText(fieldValue.toString());
				}
			}
		}

		return writeToXml(obj.getClass(), document);
	}

	/**
	 * 
	 * @param obj
	 *            ************************************************************************************************
	 */
	public synchronized static void delete(Object obj) {
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();
		String idValue = getIdValue(obj);
		if (idValue == null) {
			return;
		}
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();
			List<?> list = root.selectNodes("//" + objName);
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Element elt = (Element) element
						.selectSingleNode(objName + "Id");
				if (elt.getText().equals(idValue)) {
					root.remove(element);
				}
			}

			writeToXml(obj.getClass(), document);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 
	 * @param obj
	 *            ************************************************************************************************
	 */
	public synchronized static void update(Object obj) {
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();
		String idValue = getIdValue(obj);
		if (idValue == null) {
			return;
		}
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();
			List<?> list = root.selectNodes("//" + objName);
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Element elt = (Element) element
						.selectSingleNode(objName + "Id");
				if (elt.getText().equals(idValue)) {
					Method[] mths = obj.getClass().getDeclaredMethods();
					for (int i = 0; i < mths.length; i++) {
						String mthName = mths[i].getName();
						if (mthName.startsWith("get")) {
							String fieldName = mthName.substring(3);
							Element et = (Element) element
									.selectSingleNode(fieldName);
							Object eobj = mths[i].invoke(obj);
							if (eobj == null) {
								et.setText("");
							} else {
								et.setText(eobj.toString());
							}
						}
					}
					break;
				}
			}

			writeToXml(obj.getClass(), document);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * note: it delete present file.
	 * 
	 * @param obj
	 * @return ************************************************************************************************
	 */
	public synchronized static List getObjectList(Object obj) {
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();
		List rlist = new ArrayList();
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();
			List<?> list = root.selectNodes("//" + objName);
			Iterator<?> iter = list.iterator();
			Method[] mths = obj.getClass().getDeclaredMethods();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Object tmpObj = obj.getClass().newInstance();
				for (int i = 0; i < mths.length; i++) {
					String mthName = mths[i].getName();
					if (mthName.startsWith("set")) {
						String fieldName = mthName.substring(3);
						Element et = (Element) element
								.selectSingleNode(fieldName);
						mths[i].invoke(tmpObj, et.getText());
					}
				}
				rlist.add(tmpObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (rlist.size() == 0)
			return null;

		return rlist;
	}

	/**
	 * 
	 * @param list
	 *            ************************************************************************************************
	 */
	public synchronized static boolean saveListToXml(List<Object> list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		Object obj = list.get(0);
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		if (!xmlFile.exists()) {
			System.out.println("file not be found.");
			return false;
		}
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();

			for (int i = 0; i < list.size(); i++) {
				Object tobj = list.get(i);
				if (tobj.getClass().getName().equals(obj.getClass().getName())) {
					// get object's name
					Element row = root.addElement(objName);
					// get filed's name
					Method[] mths = obj.getClass().getDeclaredMethods();
					for (int j = 0; j < mths.length; j++) {
						String mthName = mths[j].getName();
						if (mthName.startsWith("get")) {
							String fieldName = mthName.substring(3);
							Element field = row.addElement(fieldName);
							Object fieldValue = mths[j].invoke(tobj);
							if (fieldValue == null) {
								field.setText("");
							} else {
								field.setText(fieldValue.toString());
							}
						}
					}
				}
			}

			writeToXml(obj.getClass(), document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param obj
	 * @return ************************************************************************************************
	 */
	public synchronized static Object getObjectById(Object obj) {
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();
		String idValue = getIdValue(obj);
		Object robj = null;
		if (idValue == null) {
			return null;
		}
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();
			List<?> list = root.selectNodes("//" + objName);
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Element elt = (Element) element
						.selectSingleNode(objName + "Id");
				if (elt.getText().equals(idValue)) {
					robj = obj.getClass().newInstance();
					Method[] mths = obj.getClass().getDeclaredMethods();
					for (int i = 0; i < mths.length; i++) {
						String mthName = mths[i].getName();
						if (mthName.startsWith("set")) {
							String fieldName = mthName.substring(3);
							Element et = (Element) element
									.selectSingleNode(fieldName);
							if (et.getText() != null) {
								mths[i].invoke(robj, et.getText());
							}
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return robj;
	}

	/**
	 * 
	 * @param obj
	 * @return ************************************************************************************************
	 */
	private synchronized static String getIdValue(Object obj) {
		String objName = obj.getClass().getSimpleName();
		String rv = null;
		Method mth;
		try {
			mth = obj.getClass().getMethod("get" + objName + "Id");
			Object idValue = mth.invoke(obj);
			if (idValue != null) {
				rv = idValue.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return rv;
	}

	/**
	 * 
	 * @param clz
	 * @param document
	 *            ************************************************************************************************
	 */
	private static boolean writeToXml(Class<?> clz, Document document) {
		String objName = clz.getSimpleName();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		XMLWriter output;
		try {
			output = new XMLWriter(new FileWriter(new File(xmlMap.get(clz
					.getName())
					+ objName + ".xml")), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	
	/**************************　xml通过Base64加密解密　*****************************/
	public synchronized static List getObjectListBase64(Object obj) {
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		SAXReader saxReader = new SAXReader();
		List rlist = new ArrayList();
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();
			List<?> list = root.selectNodes("//" + objName);
			Iterator<?> iter = list.iterator();
			Method[] mths = obj.getClass().getDeclaredMethods();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Object tmpObj = obj.getClass().newInstance();
				for (int i = 0; i < mths.length; i++) {
					String mthName = mths[i].getName();
					if (mthName.startsWith("set")) {
						String fieldName = mthName.substring(3);
						Element et = (Element) element
								.selectSingleNode(fieldName);
						mths[i].invoke(tmpObj, Base64.getStringFromBase64(et.getText()));
					}
				}
				rlist.add(tmpObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (rlist.size() == 0)
			return null;

		return rlist;
	}

	public synchronized static boolean saveListToXmlBase64(List<Object> list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		Object obj = list.get(0);
		String objName = obj.getClass().getSimpleName();
		File xmlFile = new File(xmlMap.get(obj.getClass().getName()) + objName
				+ ".xml");
		if (!xmlFile.exists()) {
			System.out.println("file not be found.");
			return false;
		}
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(xmlFile);
			Element root = document.getRootElement();

			for (int i = 0; i < list.size(); i++) {
				Object tobj = list.get(i);
				if (tobj.getClass().getName().equals(obj.getClass().getName())) {
					// get object's name
					Element row = root.addElement(objName);
					// get filed's name
					Method[] mths = obj.getClass().getDeclaredMethods();
					for (int j = 0; j < mths.length; j++) {
						String mthName = mths[j].getName();
						if (mthName.startsWith("get")) {
							String fieldName = mthName.substring(3);
							Element field = row.addElement(fieldName);
							Object fieldValue = mths[j].invoke(tobj);
							if (fieldValue == null) {
								field.setText("");
							} else {
								field.setText(Base64.getBase64FromString(fieldValue.toString()));
							}
						}
					}
				}
			}

			writeToXml(obj.getClass(), document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public synchronized static boolean getClassFromXmlBase64(Class<?> clz)
			throws XmlDAOException {
		String clzName = clz.getSimpleName();
		File xmFile = new File(xmlMap.get(clz.getName()) + clzName + ".xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(xmFile);
			Element root = document.getRootElement();
			Object obj = clz.newInstance();
			Method[] mths = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < mths.length; i++) {
				String mthName = mths[i].getName();
				if (mthName.startsWith("set")) {
					String fieldName = mthName.substring(3);
					Element et = (Element) root.selectSingleNode(fieldName);
				
					mths[i].invoke(obj, Base64.getStringFromBase64(et.getText()));
				}
			}
		} catch (Exception e) {
			throw new XmlDAOException(e);
		}

		return true;
	}

	public synchronized static boolean saveClassToXmlBase64(Class<?> clz) {
		String clzName = clz.getSimpleName();
		File xmlFile = new File(xmlMap.get(clz.getName()) + clzName + ".xml");
		try {
			if (xmlFile.exists()) {
				xmlFile.delete();
			}

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement(clzName);

			// get filed's name
			Method[] mths = clz.getDeclaredMethods();
			for (int i = 0; i < mths.length; i++) {
				String mthName = mths[i].getName();
				if (mthName.startsWith("get")) {
					String fieldName = mthName.substring(3);
					Element field = root.addElement(fieldName);
					Object fieldValue = mths[i].invoke(clz);
					if (fieldValue == null) {
						field.setText("");
					} else {
						field.setText(Base64.getBase64FromString(fieldValue.toString()));
					}
				}
			}

			writeToXml(clz, document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
