package com.hfy.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
//import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 加解密
******************************************************************************
 * 本程序为深圳浩丰源有限公司开发研制。未经本公司正式书面同意，其他任何个人和团体均不得做任何用途
 * 复制、修改或发布本软件.
 * Copyright (C) 2016 ShenZhen HFY Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************
 * @author yanning
 * @date 2016年12月12日 上午10:37:34
 * @version 1.0 
 *
 */
public class DESUtils {
		private static String DES = "DES";
	    private static String KEY_STR="yanning0";  
	      
	    /**
	     * 对字符串进行加密，返回BASE64的加密字符串 
	     * @author yanning
	     * @date 2016年12月12日 上午10:37:46
	     * @version 1.0
	     * @param str
	     * @return
	     */
	    public static String getEncryptString(String str){  
	    	try {
	    		return encrypt(str, KEY_STR);
	            /*//1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            keygen.init(128, new SecureRandom(KEY_STR.getBytes()));
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=str.getBytes("utf-8");
	            //9.根据密码器的初始化方式--加密：将数据加密
	            byte [] byte_AES=cipher.doFinal(byte_encode);
	          //10.将加密后的数据转换为字符串
	            //这里用Base64Encoder中会找不到包
	            //解决办法：
	            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
	            String AES_encode=new String(new Base64().encodeAsString(byte_AES));
	          //11.将字符串返回
	            return AES_encode;*/
	        } catch (Exception e) {
	        	System.out.println("Encrypt error:"+str);
	            e.printStackTrace();
	        } 
	        
	        //如果有错就返加nulll
	        return null;        
	          
	    }  
	      
	   /**
	    * 对BASE64加密字符串进行解密  
	    * @author yanning
	    * @date 2016年12月12日 上午10:37:55
	    * @version 1.0
	    * @param str
	    * @return
	    */
	    public static String getDecryptString(String str){  
	    	try {
	    		return decrypt(str, KEY_STR);
	            /*//1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            keygen.init(128, new SecureRandom(KEY_STR.getBytes()));
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            //8.将加密并编码后的内容解码成字节数组
	            byte [] byte_content= new Base64().decode(str);
	            
	             * 解密
	             
	            byte [] byte_decode=cipher.doFinal(byte_content);
	            String AES_decode=new String(byte_decode,"utf-8");
	            return AES_decode;*/
	        } catch (Exception e) {
	        	System.out.println("decrypt error:"+str);
	            e.printStackTrace();
	        }
	        
	        //如果有错就返加nulll
	        return null;        
	    }  
	    
	    
	    /**
	     * Description 根据键值进行加密
	     * @param data 
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    public static String encrypt(String data, String key) throws Exception {
	    	byte[] bt = Base64.encodeBase64(data.getBytes("UTF-8"));
	        bt = encrypt(bt, key.getBytes());
	        return new String(Base64.encodeBase64(bt),"utf-8");
	    }
	 
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws IOException
	     * @throws Exception
	     */
	    public static String decrypt(String data, String key) throws IOException,
	            Exception {
	        if (data == null)
	            return null;
	        byte[] buf =Base64.decodeBase64(data.getBytes());
	        byte[] bt = decrypt(buf,key.getBytes());
	        return new String(Base64.decodeBase64(bt),"utf-8");
	    }
	 
	    /**
	     * Description 根据键值进行加密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance(DES);
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	     
	     
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance(DES);
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	      
	    public static void main(String[] args)  
	    {  
	        String name ="ECP_CGN_DEV";  
	        String password="ECP_CGN_DEV";  
	        String encryname = getEncryptString(name);  
	        String encrypassword = getEncryptString(password);  
	        System.out.println(encryname);  
	        System.out.println(encrypassword);  
	          
	        System.out.println(getDecryptString(encryname));  
	        System.out.println(getDecryptString(encrypassword));  
	    }  
}
