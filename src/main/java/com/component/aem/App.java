package com.component.aem;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String componentName = "rlmvisulization";
		try {
			String directoryPath = CreateComponentSkelton
					.createComponentFolder("C:\\fuNke", componentName);
			
			CreateComponentSkelton.createFile(directoryPath, componentName + ".html");

			List<String> listOfFiles = CreateComponentSkelton.moveFiles(directoryPath);
			
			CreateComponentSkelton.replaceComponentName(componentName, directoryPath, listOfFiles);

		} catch (Exception ex) {
			System.out.println("Exception ::" + ex);
		}
	}
}
