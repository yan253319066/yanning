package com.hfy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zip操作
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月20日 下午2:53:00
 * @version 1.0 
 *
 */
public class ZipUtils {

	private static Logger log = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 解压
	 * @author yanning
	 * @date 2016年12月20日 下午2:47:57
	 * @version 1.0
	 * @param zipFileName
	 * 	ZIP文件
	 * @param outputDirectory
	 * 解压到哪个目录
	 */
	public static void decompressZip(String zipFileName, String outputDirectory) {
		log.debug("解压开始");
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFileName);
			Enumeration<? extends ZipEntry> e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f1 = new File(parseFileSeperator(outputDirectory));
					f1.mkdir();
					File f = new File(parseFileSeperator(outputDirectory) + name);
					f.mkdir();
				} else {
					String filepath = parseFileSeperator(outputDirectory) + zipEntry.getName();
					File f = new File(filepath);
					File dir = new File(f.getParent());
					InputStream in = null;
					FileOutputStream out = null;
					if (!dir.exists()) {
						dir.mkdirs();
					}
					try {
						f.createNewFile();
						in = zipFile.getInputStream(zipEntry);
						out = new FileOutputStream(f);
						int c;
						byte[] by = new byte[1024];
						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
					} catch (ZipException e1) {
						log.error("解压文件失败。文件名：" + zipFileName + "。错误：" + e1.getMessage());
						throw new RuntimeException(e1);
					} catch (IOException e1) {
						log.error("解压文件失败。文件名：" + zipFileName + "。错误：" + e1.getMessage());
						throw new RuntimeException(e1);
					} finally {
						try {
							if (out != null) {
								out.flush();
								out.close();
							}
							if (in != null) {
								in.close();
							}
						} catch (IOException e1) {
							log.error("解压文件关闭文件流失败。文件名：" + zipFileName + "。错误：" + e1.getMessage());
							throw new RuntimeException(e1);
						}
					}
				}
			}
			log.debug("解压完成");
		} catch (IOException e1) {
			log.error("解压文件失败。文件名：" + zipFileName + "。错误：" + e1.getMessage());
			throw new RuntimeException(e1);
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				log.error("解压文件关闭文件流失败。文件名：" + zipFileName + "。错误：" + e.getMessage());
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 * 压缩
	 * @author yanning
	 * @date 2016年12月20日 下午2:50:07
	 * @version 1.0
	 * @param fromPath
	 * 要压缩的文件或者目录
	 * @param toPath
	 * 压缩后文件名加路径
	 * @return
	 */
	public static boolean compressZip(String fromPath, String toPath) {
		boolean isError = false;
		ZipOutputStream out = null;
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(toFile);
			out = new ZipOutputStream(new BufferedOutputStream(fos));
			fromPath = fromFile.getAbsolutePath() + "\\";
			makeZip(fromFile, out, fromPath);
		} catch (FileNotFoundException e) {

			isError = true;
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return isError;
	}

	private static void makeZip(File fromFile, ZipOutputStream out, String dir) {
		try {
			File[] fileList = fromFile.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File _f = fileList[i];
				if (_f.isDirectory()) {
					makeZip(_f, out, dir);
				} else {
					String path = _f.getAbsolutePath();
					path = getAbsolutePath(path, dir);
					writeEntry(path, _f, out);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getAbsolutePath(String strPath, String dir) throws UnsupportedEncodingException {
		return strPath.substring(dir.length(), strPath.length());
	}

	private static final int BUFFER = 2048;

	private static void writeEntry(String EntryName, File file, ZipOutputStream out) {
		ZipEntry entry = new ZipEntry(EntryName);
		FileInputStream fi = null;
		BufferedInputStream origin = null;
		try {
			out.putNextEntry(entry);
			fi = new FileInputStream(file);
			origin = new BufferedInputStream(fi, BUFFER);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (origin != null)
					origin.close();
				if (fi != null)
					fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查文件路径是否以“/”结尾，如果没有则增加“/”
	 * 
	 * @param dir
	 * @return
	 */
	private static String parseFileSeperator(String dir) {
		if (dir == null || dir.equals("")) {
			return "";
		}
		return dir.endsWith("/") ? dir : (dir + "/");
	}


	public static void main(String[] args) {
		log.info("-----");
		ZipUtils.compressZip("d:/深勘","d:/test3.zip");
		log.info("-----");
		ZipUtils.decompressZip("D:/zrh.zip", "d:/zrh/");
		log.info("-----");
	}
}
