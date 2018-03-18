package com.hfy.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hfy.base.entity.ExcelHeader;

/**
 * excel操作
 ******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途 复制、修改或发布本软件. Copyright (C)
 * 2016 ShenZhen HFY Co.,Ltd All Rights Reserved.
 *****************************************************************************
 * @author yewentong
 * @date 2016年11月21日 上午9:20:18
 * @version 1.0
 *
 */
public class ExcelUtils {

	private final static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 数据写入excel，并下载
	 * @author yanning
	 * @date 2016年12月19日 下午3:21:10
	 * @version 1.0
	 * @param response
	 * @param request
	 * @param headers
	 * 需要导出的字段
	 * @param list
	 * 需要导出的对象
	 * @param fieldName
	 * 导出文件名称
	 */
	public static <E> void exportExcelDownload(HttpServletResponse response, HttpServletRequest request, List<ExcelHeader> headers, List<E> list, String fieldName) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String title = fieldName;
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			fieldName = fieldName + dateFormat.format(new Date());
			response.reset();
			String excelFieldName = "";
			if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1){//IE
				excelFieldName = URLEncoder.encode(fieldName, "UTF8");
			}else if(request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > -1){//CHROME
				excelFieldName = URLEncoder.encode(fieldName, "UTF8");
			}else{
				excelFieldName = new String(fieldName.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + excelFieldName + ".xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 导出数据到excel
			exportExcel(headers, list, title, out, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 数据写入excel，并下载
	 * @author yanning
	 * @date 2016年12月19日 下午3:19:59
	 * @version 1.0
	 * @param response
	 * @param request
	 * @param headers
	 * 需要导出的字段
	 * @param list
	 * 需要导出的对象
	 * @param fieldName
	 * 导出文件名称
	 * @param pattern
	 * 日期格式
	 */
	public static <E> void exportExcelDownload(HttpServletResponse response, HttpServletRequest request, List<ExcelHeader> headers, List<E> list, String fieldName, String pattern) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String title = fieldName;
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			fieldName = fieldName + dateFormat.format(new Date());
			response.reset();
			String excelFieldName = "";
			if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1){//IE
				excelFieldName = URLEncoder.encode(fieldName, "UTF8");
			}else if(request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > -1){//CHROME
				excelFieldName = URLEncoder.encode(fieldName, "UTF8");
			}else{
				excelFieldName = new String(fieldName.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + excelFieldName + ".xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 导出数据到excel
			exportExcel(headers, list, title, out, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 导出excel
	 * @author yanning
	 * @date 2016年12月19日 下午3:21:31
	 * @version 1.0
	 * @param out
	 * @param headers
	 * @param list
	 * @param fieldName
	 * @param pattern
	 */
	public static <E> void exportExcel(OutputStream out, List<ExcelHeader> headers, List<E> list, String fieldName, String pattern) {
		exportExcel(headers, list, fieldName, out, pattern);
	}
	/**
	 * 导出excel
	 * @author yanning
	 * @date 2016年12月19日 下午3:23:57
	 * @version 1.0
	 * @param out
	 * @param headers
	 * @param list
	 * @param fieldName
	 */
	public static <E> void exportExcel(OutputStream out, List<ExcelHeader> headers, List<E> list, String fieldName) {
		exportExcel(headers, list, fieldName, out, pattern);
	}
	
	/**
	 * 生成excel
	 * @author yanning
	 * @date 2016年12月19日 下午3:41:54
	 * @version 1.0
	 * @param headers
	 * @param list
	 * @param fieldName
	 * @param out
	 * @param pattern
	 */
	private static <E> void exportExcel(List<ExcelHeader> headers, List<E> list, String fieldName, OutputStream out, String pattern) {
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建一个sheet
		HSSFSheet sheet = wb.createSheet(fieldName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		
		HSSFRow headerRow = sheet.createRow(0);
		HSSFRow contentRow = null;
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 设置标题
		for (int i = 0; i < headers.size(); i++) {
			String title = headers.get(i).getTitle();
			if (StringUtils.isNotBlank(title)) {
				headerRow.createCell(i).setCellValue(title);
			}
		}
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			for (int i = 0; i < list.size(); i++) {
				contentRow = sheet.createRow(i + 1);
				// 获取每一个对象
				E o = list.get(i);
				if(o instanceof Map){
					Map<?, ?> map = (Map<?, ?>) o;
					for (int j = 0; j < headers.size(); j++) {
						String title = headers.get(j).getTitle();
						String field = headers.get(j).getField();
						if (StringUtils.isNotBlank(title)) {
							Object value = map.get(field);
							if(value != null){
								if(value instanceof Date){//当导出类型为时间时转换成yyyy-MM-dd
									try {
										contentRow.createCell(j).setCellValue(format.format(value));
									} catch (IllegalArgumentException e) {e.printStackTrace();}
								}else if(value instanceof byte[]){//当导出类型为byte[],生成图片
									// 有图片时，设置行高为80px;
									contentRow.setHeightInPoints(80);
									// 设置图片所在列宽度为200px
									sheet.setColumnWidth(i+1, 200);
									byte[] bsValue = (byte[]) value;
									HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) j, i+1, (short) j, i+1);
									anchor.setAnchorType(1);
									patriarch.createPicture(anchor, wb.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
								}else{
									contentRow.createCell(j).setCellValue(value.toString());
								}
							}
						}
					}
				}else{
					Class<? extends Object> cls = o.getClass();
					for (int j = 0; j < headers.size(); j++) {
						String title = headers.get(j).getTitle();
						String field = headers.get(j).getField();
						if (StringUtils.isNotBlank(title)) {
							String fName = field.substring(0, 1).toUpperCase() + field.substring(1);
							try {
								Method getMethod = cls.getMethod("get" + fName);
								Object value = getMethod.invoke(o);
								if (value != null) {
									if(value instanceof Date){//当导出类型为时间时转换成yyyy-MM-dd
										contentRow.createCell(j).setCellValue(format.format(value));
									}else if(value instanceof byte[]){//当导出类型为byte[],生成图片
										// 有图片时，设置行高为80px;
										contentRow.setHeightInPoints(80);
										// 设置图片所在列宽度为200px
										sheet.setColumnWidth(i+1, 200);
										byte[] bsValue = (byte[]) value;
										HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) j, i+1, (short) j, i+1);
										anchor.setAnchorType(1);
										patriarch.createPicture(anchor, wb.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
									}else{
										contentRow.createCell(j).setCellValue(value.toString());
									}
								}
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {e.printStackTrace();}
						}
					}
				}
			}
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}