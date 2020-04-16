package com.springboot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileHelper {

	/** 压缩文件 */
	public static File toZip(String srcPath, String zipPath, String name) {
		try {
			File srcFile = new File(srcPath);
			File zipFile = new File(zipPath + name);
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
			compress(srcFile, zipOutputStream, name);
			zipOutputStream.close();
			fileOutputStream.close();
			return zipFile;
		} catch (Exception e) {
			return null;
		}
	}

	/** 压缩文件 */
	private static boolean compress(File srcFile, ZipOutputStream zip, String name) throws IOException {
		if (srcFile.isFile()) {
			int len = 0;
			byte[] data = new byte[1024];
			ZipEntry entry = new ZipEntry(name);
			zip.putNextEntry(entry);
			FileInputStream input = new FileInputStream(srcFile);
			while ((len = input.read(data)) != -1)
				zip.write(data, 0, len);
			zip.closeEntry();
			input.close();
			return true;
		}
		File[] listFiles = srcFile.listFiles();
		for (int i = 0; listFiles != null && i < listFiles.length; i++) {
			File file = listFiles[i];
			if (file.isFile())
				compress(file, zip, name + "/" + file.getName());
			else {
				ZipEntry entry = new ZipEntry(name + "/" + file.getName() + "/");
				zip.putNextEntry(entry);
				zip.closeEntry();
				for (File temp : file.listFiles())
					compress(temp, zip, name + "/" + file.getName() + "/" + temp.getName());
			}
		}
		return true;
	}
}
