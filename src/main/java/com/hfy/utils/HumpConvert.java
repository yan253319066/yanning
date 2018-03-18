package com.hfy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰命名与下划线转换
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年10月19日 上午11:32:22
 * @version 1.0 
 *
 */
public class HumpConvert {
	private static Pattern linePattern = Pattern.compile("_(\\w)");  
    /**
     * 下划线转驼峰
     * @author yanning
     * @date 2016年11月16日 上午9:34:37
     * @version 1.0
     * @param str
     * @return
     */
    public static String lineToHump(String str){  
        str = str.toLowerCase();  
        Matcher matcher = linePattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }  
    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     * @author yanning
     * @date 2016年11月16日 上午9:34:57
     * @version 1.0
     * @param str
     * @return
     */
    public static String humpToLine(String str){  
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();  
    }  
    private static Pattern humpPattern = Pattern.compile("[A-Z]");  
    /**
     * 驼峰转下划线,效率比上面高
     * @author yanning
     * @date 2016年11月16日 上午9:35:04
     * @version 1.0
     * @param str
     * @return
     */
    public static String humpToLine2(String str){  
        Matcher matcher = humpPattern.matcher(str);  
        StringBuffer sb = new StringBuffer();  
        while(matcher.find()){  
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());  
        }  
        matcher.appendTail(sb);  
        return sb.toString();  
    }  
    public static void main(String[] args) {  
        String lineToHump = lineToHump("f_parent_no_leader");  
        System.out.println(lineToHump);//fParentNoLeader  
        System.out.println(humpToLine(lineToHump));//f_parent_no_leader  
        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader  
    }  
}
