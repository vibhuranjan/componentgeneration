package com.component.aem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.component.aem.constants.GlobalConstants;

/**
 * @author vibhu.ranjan
 *
 */
public class CreateComponentSkelton {

	static final Logger logger = Logger.getLogger(CreateComponentSkelton.class);
	
	private CreateComponentSkelton() {
	}

	/**
	 * @param path
	 * @param name
	 * @throws IOException
	 */
	public static String createComponentFolder(String path, String name) {
		logger.debug("inside createComponentFolder :: ");
		String directyPath = "";
		File dir = new File(path + GlobalConstants.FORWARD_SLASH + name);
		if (!dir.exists()) {
			boolean successful = dir.mkdir();
			if (successful) {
				logger.debug("directory was created successfully");
			} else {
				logger.debug("failed trying to create the directory");
			}
		} else {
			logger.debug("component folder is already present in repo.");
		}
		directyPath = dir.getAbsolutePath();
		return directyPath;
	}

	/**
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	public static void createFile(String path, String fileName)
			throws IOException {
		String fileWithPath = path + File.separator + fileName;
		File file = new File(fileWithPath);
		if (!file.exists()) {
			boolean successful = file.createNewFile();
			if (successful) {
				logger.debug("file " + fileName
						+ " is created successfully.");
			} else {
				logger.debug("failed trying to create the file "
						+ fileName);
			}
		} else {
			logger.debug(fileName + " file is already present at path."
					+ path);
		}
	}

	/**
	 * @param directoryPath
	 * @throws IOException
	 */
	public static List<String> moveFiles(String directoryPath)
			throws IOException {
		List<String> fileList = new ArrayList<>();
		Path resourceDirectory = Paths.get("src", "main", "resources");
		File resourcesDirectory = new File(resourceDirectory.toString());
		File[] directoryListing = resourcesDirectory.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (!child.getName().equalsIgnoreCase("log4j.properties")) {
					fileList.add(child.getName());
					Files.copy(child.toPath(), new File(directoryPath
							+ File.separator + child.getName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} else {
			logger.debug("There is no file in the folder.");
		}
		return fileList;
	}

	/**
	 * @param componentName
	 * @param directoryPath
	 * @param listOfFiles
	 * @throws IOException
	 */
	public static void replaceComponentName(String componentName,
			String directoryPath, List<String> listOfFiles) throws IOException {
		for (String fileName : listOfFiles) {
			logger.debug("file name ::" + fileName);
			Path path = Paths.get(directoryPath + File.separator + fileName);
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("componentname", componentName);
			Files.write(path, content.getBytes(charset));
		}
		logger.debug("Renaming is done!");
	}
}
