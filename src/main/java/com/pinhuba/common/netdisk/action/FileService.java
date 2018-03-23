package com.pinhuba.common.netdisk.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.pinhuba.common.netdisk.io.FileHelper;
import com.pinhuba.common.netdisk.io.MyDirectory;
import com.pinhuba.common.netdisk.json.bean.FileNode;
import com.pinhuba.core.iservice.IHrmEmployeeService;
import com.pinhuba.core.iservice.INetdiskService;
import com.pinhuba.core.pojo.OaNetdiskShare;

/**
 * FileService Utility
 */
@Service
public class FileService {

	@Resource
	private FileHelper fileHelper;
	@Resource
	private IHrmEmployeeService hrmEmployeeService;
	@Resource
	private INetdiskService netdiskService;

	/**
	 * 获取到当前系统的根文件列表 并将其转换成FileNode节点对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FileNode> getRoots() throws Exception {
		List<FileNode> rootList = new ArrayList<FileNode>();

		List<MyDirectory> rootDirList = fileHelper.listRoots();
		FileNode fileNode = null;
		for (MyDirectory myDirectory : rootDirList) {
			fileNode = new FileNode();

			String dirName = myDirectory.getName();
			//dirName = dirName.substring(0, dirName.length()-1);
			
			fileNode.setId(dirName);
			fileNode.setText(dirName);
			fileNode.setLeaf(!myDirectory.getHasChildren());
			fileNode.setFileName(myDirectory.getPath());

			rootList.add(fileNode);
		}

		return rootList;
	}
	
	/**
	 * 功能:获得共享路径下大所有文件和文件夹信息，把数据封装到nodes返回
	 * 
	 * @param folder
	 *            当前要访问的文件夹目录名称
	 * @param onlyDirectory
	 *            null:获得所有信息，true:只获得文件夹,false:只获得文件信息
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<FileNode> listShareFiles(String rootPath, List<OaNetdiskShare> list) throws FileNotFoundException, IOException {
		List<FileNode> filelist = new ArrayList<FileNode>();
		for(OaNetdiskShare oaNetdiskShare : list) {
		String folder = oaNetdiskShare.getHrmEmployeeId() + oaNetdiskShare.getFolderPath();
		String empName = hrmEmployeeService.getEmployeeByPK(oaNetdiskShare.getHrmEmployeeId()).getHrmEmployeeName();
			File f = new File(rootPath + folder);
			String id = f.getAbsolutePath();
			FileNode nd = new FileNode();
			nd.setId(id.substring(rootPath.length()));
			nd.setText(f.getName()+" 在 【"+empName+"】上");
			nd.setLeaf(f.isFile());
			nd.setFileName(f.getName());
			nd.setLastModifyDate(new Date(f.lastModified()));
			
			filelist.add(nd);
		}
		return filelist;
	}
	

	/**
	 * 功能:获得指定路径下大所有文件和文件夹信息，把数据封装到nodes返回
	 * 
	 * @param folder
	 *            当前要访问的文件夹目录名称
	 * @param onlyDirectory
	 *            null:获得所有信息，true:只获得文件夹,false:只获得文件信息
	 * @param isShare 
	 *            true:共享目录列表  false:正常目录列表
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public List<FileNode> listFiles(String rootPath, String folder,String empId,
			boolean onlyDirectory, boolean isShare) throws FileNotFoundException, IOException {

		File[] arrFiles = new File(rootPath + folder).listFiles();

		if (arrFiles == null)
			return null;

		FileNode nd = null;

		List<FileNode> filelist = new ArrayList<FileNode>();
		for (File f : arrFiles) {

			if (onlyDirectory && !f.isDirectory()) {
				continue;
			}

			String id = f.getAbsolutePath();
			nd = new FileNode();
			nd.setId(id.substring(rootPath.length()));
			nd.setText(f.getName());
			nd.setLeaf(f.isFile());
			nd.setFileName(f.getName());
			FileInputStream fis = null;
			if (f.isFile()) {
				fis = new FileInputStream(f);
				int size = fis.available();
				nd.setFileSize(Integer.toString(size));
				fis.close();
				fis=null;
			}
			nd.setLastModifyDate(new Date(f.lastModified()));
			if(isShare == false) {
				OaNetdiskShare share = new OaNetdiskShare();
				share.setHrmEmployeeId(empId);
				share.setFolderPath(nd.getId());
				OaNetdiskShare temp = netdiskService.getShareByHrmEmpIDandPath(share);
				if(temp!=null ) {
					if((temp.getNetdiskDeps()!=null && !temp.getNetdiskDeps().equals("")) || (temp.getNetdiskEmps()!=null && !temp.getNetdiskEmps().equals(""))) 
						nd.setShare(true);
					else
						nd.setShare(false);
				} else {
					nd.setShare(false);
				}
			}
			filelist.add(nd);
		}

		return filelist;
	}
	
	/**
	 * 重命名
	 * @return
	 */
	public boolean rename(String oldFullPath,String newFullPath) throws Exception{
		boolean outcome = false;
		File oldFile = new File(oldFullPath);
		File newFile = new File(newFullPath);
	    try {
	    	int tryCount = 0;  
	    	while(!outcome && tryCount++ <10)   
		    {   
		    System.gc();   
		    outcome = oldFile.renameTo(newFile);  
		    }   
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	newFile = null;
	    	oldFile = null;
	    }
	    if (!outcome)
	      throw new Exception("File/directory could not be renamed\n\n(Does a file/directory with the new name already exist?");
		
		return outcome;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @throws Exception
	 */
	public boolean createDirectory(String path) {
		boolean flag = false;
		try {
			flag = fileHelper.createDirectory(path);
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	public boolean upload(String uploadFileName, String savePath,
			File uploadFile) {
		return fileHelper.upload(uploadFileName, savePath, uploadFile);
	}
}
