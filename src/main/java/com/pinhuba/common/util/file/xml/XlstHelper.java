/*******************************************************************************
 * * Copyright (c) 2008 Koalvision Corporation(ι).
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Koal Weixin Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.koalvision.com
 *  * Contributors:
 *  *     KoalVision Corporation - initial API and implementation
 *  * Creating time: 2008-${time}
 *  * Author: 
 *******************************************************************************/
package com.pinhuba.common.util.file.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*******************************************************************************
 * >XLSglXMLl'
 * 
 * @author BOB
 * @version 1.0
 * @since 1.0
 * @see javax.xml.transform.Transformer
 * 
 */
public final class XlstHelper {
	/***************************************************************************
	 * gXMLll
	 * 
	 * @param xsltSource
	 * @param xmlSource
	 * @param targetFileName
	 */
	public final static void transformXml(String xsltSource, String xmlSource,
			String targetFileName) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		FileWriter out = null;
		try {
			File file = new File(targetFileName.trim());
			if (!file.exists()) {
				String folder = targetFileName.trim().substring(0,
						targetFileName.trim().lastIndexOf(File.separator));
				if (folder.trim().length() > 0) {
					File folderFile = new File(folder.trim());
					if (!folderFile.exists()) {
						if (!folderFile.mkdirs()) {
							throw new IllegalArgumentException("l");
						}
					}
					if (!file.createNewFile()) {
						throw new IllegalArgumentException("l");
					}
				}
			}
			if (file.isDirectory()) {
				throw new IllegalArgumentException("·L 1/4 l");
			}
			out = new FileWriter(targetFileName);
			Transformer transformer = tFactory.newTransformer(new StreamSource(
					xsltSource.trim()));
			transformer.transform(new StreamSource(xmlSource.trim()),
					new StreamResult(out));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException ilE) {
			ilE.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {

			}
		}
	}
}
