package com.wei.mobileoffice.util;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;
 
/**** 
 * {@docRoot    所有的数据转换处理 }
 * @version     1.0
 * @since       2011\4\13
 * ****/
public class DataTypeChange {
/***
 * 表　Java中的简单类型 

简单类型 boolean byte char     short  int     long float double void 
二进制位数 1      8    16        16    32      64   32    64     -- 
字节数     1      1     2        2     4       8    4      8     0
C++范围    1      1     1        2     4       4    4      8     0                            
封装器类 Boolean Byte Character Short Integer Long Float Double Void   
这里的java和c++中的char类型的大小是不同的
 * ***/ 
	// 将字符串转换成一定长度的字节数组
    /***
     * @param  src 需要输入处理的源字符串
     * @param  byte_len 固定大小
     * @return byte[]
     *    String --> byte[]
     * **/
    public  static  byte[] string2Bytes(String src,int byte_len){
  		byte[] b1 = new byte[byte_len];
			StringBuffer tempb = new StringBuffer();     // 临时填充数据
			int temp_len = 0;                            // 临时字符的大 
			int src_len = src.length();                  // 需要转换的字符串的长度
			char zero = '\0';
			if(src_len<byte_len){
				temp_len = byte_len - src_len;           // 计算填充位的大小
				for(int i=0;i<temp_len;i++){             // 循环填充占位符
					tempb.append(zero);
				} 
				b1 = (src+tempb.toString()).getBytes(); 
		//		tempb.delete(0, temp_len);               // 清空临时字符
			}else{                                       // 如果大于此规定的长度，则直接转为btye类型
				b1 = src.getBytes();
			}  
			return b1;
    }
   // 将字符串转换成一定长度的字符串，不足的添加'\0'标识
    /***
     * @param  src 需要输入处理的源字符串
     * @param  byte_len 固定大小
     * @return String 返回的长度为byte_len的字符串
     * 
     *  String --> fillString
     * **/
    public  static  String  fillString(String src,int byte_len){ 
  	        String fill_str = "";
			StringBuffer tempb = new StringBuffer();  // 临时填充数据
			int temp_len = 0;                         // 临时字符的大 
			int src_len = src.length();               // 需要转换的字符串的长度
			char zero = '\0';
			if(src_len<byte_len){
				temp_len = byte_len - src_len;        // 计算填充位的大小
				for(int i=0;i<temp_len;i++){          // 循环填充占位符
					tempb.append(zero);
				}
				fill_str = src+tempb.toString();
			} else{
				fill_str = src;
			}
			return  fill_str;
    } 
	//   byte[] --> int
	public  static int bytes2Int1(byte[] bytes) {
	         int num = bytes[0] & 0xFF;
		      num |= ((bytes[1] << 8) & 0xFF00);
	        return num; 
	}
	//   byte[] --> int
	public static int bytes2Int2(byte[] intByte) { 
		int fromByte = 0;  
	    for (int i = 0; i < 2; i++)
         {
         int n = (intByte[i] < 0 ? (int)intByte[i] + 256 : (int)intByte[i]) << (8 * i);
           System.out.println(n);
          fromByte += n;
         }
          return fromByte;
    } 
	// bytes to int
	public  static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;
    }

	// byte[] --> String
	public static String bytes2String(byte b[]){
		String result_str = new String(b);
		return result_str;
	}
	//  int --> byte[]
	public static byte[] int2Bytes(int res) {
		byte[] targets = new byte[4]; 
		targets[0] = (byte) (res & 0xff);// 最低位 
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
		return targets; 
	} 
	// char --> byte[]
	public static byte[] char2Bytes(char ch){
       int temp=(int)ch;
	   byte[] b=new byte[2];
	   for (int i=b.length-1;i>-1;i--){
	      b[i] = new Integer(temp&0xff).byteValue();//将最高位保存在最低位
	      temp = temp >> 8;         //向右移8位
	     }
        return b;
	}	
	// char --> int 8-->16
	public static int char2Int(char c){
		return  c;
	}
	// int --> char 16-->8需要强制转换
	public static char int2Char(int i){
		return  (char)i;
	} 
	  /***
	   * 数据之间的转换操作
	   ***/
	// 数据大于10000的时候进行处理
	public static String getF10000(float f1){
		   String str = "";
		   double d1 = 0.0d;
		   if(f1>10000.0f&&f1<100000000.0d){// 数据在10万到1亿之间的数据
			   d1 = f1/10000.0f;
			   str = DataTypeChange.save2bit(d1)+"万";
		   }else if(f1>100000000.0d){
			   d1 = (double)f1/100000000.0d;
			   str = DataTypeChange.save2bit(d1)+"亿";
		   }else{ 
			   d1 = f1;
			   str = DataTypeChange.save2bit(d1)+"";
		   }
		   return str;
	   } 
	// 截取byte数组从start到end的位置转换成strng
	 /***
	  * @input   src         待截取字节数组
	  *          start       待截取字节数组的开始位置
	  *          src_size    截取的长度 == 数据类型的长度
	  *           
	  * @output  int 字节截取转换成int后的结果
	  * 
	  * **/
	public static int bytesSub2Int(byte[] src,int start,int src_size) { 
		//if(src.length==0){
			byte[] resBytes = new byte[src_size];
	    	System.arraycopy(src, start, resBytes, 0, src_size);  
		    return  bytesToInt(resBytes);
//		}else{
//			return 0;
//		} 
	}
	
	 /***
	  * @input   src         待截取字节数组
	  *          start       待截取字节数组的开始位置
	  *          src_size    截取的长度 == 数据类型的长度
	  *           
	  * @output  String 字节截取转换成String后的结果
	  * 
	  * **/
	public static String bytesSub2String(byte[] src,int start,int src_size) { 
		byte[] resBytes = new byte[src_size];
		System.arraycopy(src, start, resBytes, 0, src_size); 
	 // 	System.out.println(" len ==" +resBytes.length 
	 //		         + " sub_bytes = " + bytes2Int1(resBytes)); 
		return  bytes2String(resBytes);
	} 
	 /***
	  * @input   src         待截取字节数组
	  *          start       待截取字节数组的开始位置
	  *          src_size    截取的长度 == 数据类型的长度
	  *           
	  * @output  从start下标开始长度为src_size长度的字节数组结果
	  * 
	  * **/
	public static byte[] bytesSub(byte[] src,int start,int src_size) { 
		byte[] resBytes = new byte[src_size];
		System.arraycopy(src, start, resBytes, 0, src_size);  
		return  resBytes;
	}
	
	/**
	 * splite the src with sep, and place them in a array
	 * 
	 * @param src
	 * @param sep
	 * @return
	 */
	public static String[] splite(String src, String sep) {
		Vector v = new Vector();
		int index;
		int fromIndex = 0;
		while ((index = src.indexOf(sep, fromIndex)) != -1) {
			v.addElement(src.substring(fromIndex, index));
			fromIndex = index + sep.length();
		}
		v.addElement(src.substring(fromIndex, src.length()));
		String[] result = new String[v.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (String) v.elementAt(i);
		}
		return result;
	}

	/**
	 * splite the src with sep, place them in a array, and replace sep with
	 * sep_code
	 * 
	 * @param src
	 * @param sep
	 * @param sep_code
	 * @return
	 */
	public static String[] splite(String src, String sep, String sep_code) {
		String[] result = splite(src, sep);
		replace(result, sep_code, sep);
		return result;
	}

	/**
	 * replace the child string with the newStr in the src
	 * 
	 * @param src
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replace(String src, String oldStr, String newStr) {
		int oldSize = oldStr.length();
		int newSize = newStr.length();
		int margin = newSize - oldSize;
		int offset = 0;
		StringBuffer sb = new StringBuffer(src);
		int index;
		int fromIndex = 0;
		while ((index = src.indexOf(oldStr, fromIndex)) != -1) {
			fromIndex = index + oldSize;
			sb.delete(index + offset, fromIndex + offset);
			sb.insert(index + offset, newStr);
			offset += margin;
		}
		return sb.toString();
	}

	/**
	 * replace the child string with the newStr in the String of the array
	 * 
	 * @param src
	 * @param oldStr
	 * @param newStr
	 */
	public static void replace(String[] src, String oldStr, String newStr) {
		for (int i = 0; i < src.length; i++) {
			src[i] = replace(src[i], oldStr, newStr);
		}
	}

	/**
	 * 
	 * @param para
	 *            the parameter name
	 * @param src
	 *            the text
	 * @return 
	 *           the value of the parameter
	 */
	public static String getParaVal(String para, String src) {
		String paraval = null;
		String tempPara = para;
		int s1 = 0;
		int s2 = 0;
		int len = 0;
		int httplen = 0;

		if (tempPara == null || src == null)
			return "";
		tempPara = tempPara + "=";
		len = tempPara.length();
		httplen = src.length();
		if (httplen == 0)
			return "";
		if (len == 0)// || len > 9)
			return "";

		s1 = src.indexOf(tempPara); // find the parameter name
		if (s1 == -1)
			return "";
		s2 = src.indexOf('&', s1);// find next &
		if (s2 == -1) {
			// the last one parameter
			paraval = src.substring(s1 + len);

		} else {
			paraval = src.substring(s1 + len, s2);
		}

		return paraval;
	}
	
	/**
	 * 
	 * @param para
	 *            the parameter name
	 * @param src
	 *            the text
	 * @param sep
	 *            the separator           
	 * @return 
	 *           the value of the parameter
	 */
	public static String getParaValEx(String para, String src, char sep) {
		String paraval = null;
		String tempPara = para;
		int s1 = 0;
		int s2 = 0;
		int len = 0;
		int httplen = 0;

		if (tempPara == null || src == null)
			return "";
		tempPara = tempPara + "=";
		len = tempPara.length();
		httplen = src.length();
		if (httplen == 0)
			return "";
		if (len == 0)// || len > 9)
			return "";

		s1 = src.indexOf(tempPara); //find the parameter name
		if (s1 == -1)
			return "";
		s2 = src.indexOf(sep, s1);// find next &
		if (s2 == -1) {
			// the last one parameter
			paraval = src.substring(s1 + len);

		} else {
			paraval = src.substring(s1 + len, s2);
		}

		return paraval;
	}

	//byte[]转float
	  /**
   * byte[] to float
   * @param b
   * @param index
   * @return float
   */
  public static float getFloat(byte[] b,int index)  throws Exception{   
      int i = 0;   
      i = ((((b[index + 3]&0xff)<<8 | (b[index + 2]&0xff))<<8) | (b[index + 1]&0xff))<<8 | (b[index + 0]&0xff);   
      return Float.intBitsToFloat(i);   
  }
 // byte[] --> float
  public static float bytes2Float(byte[] b){
  	float f = 0f;
  	try {
			  f = getFloat(b,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	return f; 
  } 
//bytes to int
	public  static short bytesToShort(byte[] bytes) {
      int addr = bytes[0] & 0xFF;
      addr |= ((bytes[1] << 8) & 0xFF00); 
      return (short)addr;
  }
  /*********** 保留有效数字的小数点后位数设置 start ************/	
  // 保留2位有效数字
  public static double save2bit(double t1){
	  double result = (double) (int) ((t1 + 0.005) * 100.0) / 100.0; 
	  return result;
  } 
  // 保留1位有效数字
  public static double save1bit(double t1){
	  double result = (double) (int) ((t1 + 0.005) * 10.0) / 10.0; 
	  return result;
  }
  /*********** 保留有效数字的小数点后位数设置 end     ************/ 
  /*********** 日期类型格式转换以及时差参数处理 start ************/
  // Long类型的毫秒转为日期字符
  public static String getStrDate(long time){
		 String str = "";
		 Date date = new Date(time);
		 TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
	     Calendar calendar = Calendar.getInstance(china);
	     calendar.setTime(date);
	     String mon = String.valueOf(calendar.get(Calendar.MONTH)+1); 
	     if(mon.length()==1){
	    	 mon = "0"+mon;
	     }
	     String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)); 
	     if(day.length()==1){
	    	 day = "0"+day;
	     } 
	     str = String.valueOf(calendar.get(Calendar.YEAR))
	          + mon
	          + day;
	    return str;
  } 
  // k线左侧显示的时间格式  月份天数小时分钟
  public static String getStrDTime(long time){
		 String str = "";
		 Date date = new Date(time);
		 TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
	     Calendar calendar = Calendar.getInstance(china);
	     calendar.setTime(date);
	     String mon = String.valueOf(calendar.get(Calendar.MONTH)+1); 
	     if(mon.length()==1){
	    	 mon = "0"+mon;
	     }
	     String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)); 
	     if(day.length()==1){
	    	 day = "0"+day;
	     } 
	     String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)); 
	     if(hour.length()==1){
	    	 hour = "0"+hour;
	     }
	     String minute = String.valueOf(calendar.get(Calendar.MINUTE)); 
	     if(minute.length()==1){
	    	 minute = "0"+minute;
	     }
	     str = mon + day + hour + minute;
	    return str;
} 
  // 获取当天是星期几
  public static int getNowMinute(long time){
	     int week = 1;
	     Date date = new Date(time); 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     week = calendar.get(Calendar.MINUTE);
	     return week;
  }
  // 获取当天是星期几
  public static int getNowWeek(long time){
	     int week = 1;
	     Date date = new Date(time); 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     week = calendar.get(Calendar.DAY_OF_WEEK);
	     return week;
  }
  // 获取当天几点
  public static int getNowHour(long time){
	     int week = 1;
	     Date date = new Date(time); 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     week = calendar.get(Calendar.HOUR_OF_DAY);
	     return week;
  }
  // 获取当天是这个月的第几天
  public static int getNowMonth(long time){
	     int month = 1;
	     Date date = new Date(time); 
	     Calendar calendar = Calendar.getInstance(); 
	     calendar.setTime(date);
	     month = calendar.get(Calendar.WEEK_OF_MONTH);
	     return month;
  } 
  // 获取当天是第几个月
  public static int getNMonth(long time){
	     int month = 1;
	     Date date = new Date(time); 
	     TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
	     Calendar calendar = Calendar.getInstance(china); 
	     calendar.setTime(date);
	     month = calendar.get(Calendar.MONTH);
	     return month;
} 
   
  // k线底部日期的显示格式
  public static String getButtomDate(String timeStr){
		 String str = "";
		 String mon = timeStr.substring(4, 6);
		 String day = timeStr.substring(6, 8);
	     str =   mon +"/"+ day;
	     return str;
 }  
  // step = 时差的大小  type=0 向前  type=1向后
  public static long getTimeCha(long step,int type){
 	 long ret = 0;
 	 long now = System.currentTimeMillis();  //获取当前的时间种子
 	 if(type==0){
 		 ret = now + step;
 	 }else{
 		 ret = now - step;
 	 }	 
 	 return ret; 
  } 
  // 获取时间，精确到分钟  
  public static String getStrTime(long timess){
 	  String str = "";
 	  Date date = new Date(timess);
 	  TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
      Calendar calendar = Calendar.getInstance(china);
      calendar.setTime(date); 
      String hour  = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
      String minute  = String.valueOf(calendar.get(Calendar.MINUTE));
      if(minute.length()==1){
     	 minute = "0"+minute;
      }
      if(hour.length()==1){
     	 hour = "0"+hour;
      }  
      str = hour + ":" + minute;
     return str;
  } 
  // 获取分时时间，当天0点开始
  public static long getNowDayZone(){
	     long  time = System.currentTimeMillis(); 
	     Date date = new Date(time);  
	     TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
	     Calendar calendar = Calendar.getInstance(china); 
	     calendar.setTime(date); 
	     calendar.set(Calendar.HOUR_OF_DAY, 0); 
	     calendar.set(Calendar.MINUTE, 0); 
	     calendar.set(Calendar.SECOND, 0); 
	     return calendar.getTime().getTime(); 
  } 
  /***
   * 根据设置获取当天的时间
   * @param  h = 小时;m = 分钟; s = 秒;
   * @return long 当前时间所代表的毫秒数
   * ***/
  public static long getTimesDataBySet(int h,int m,int s){
	     long  time = System.currentTimeMillis(); 
	     Date date = new Date(time);  
	     TimeZone china=TimeZone.getTimeZone("GMT+08:00"); //时区设置
	     Calendar calendar = Calendar.getInstance(china); 
	     calendar.setTime(date); 
	     calendar.set(Calendar.HOUR_OF_DAY, h); 
	     calendar.set(Calendar.MINUTE, m); 
	     calendar.set(Calendar.SECOND, s); 
	     return calendar.getTime().getTime(); 
 } 
  
  /***
   *  判断今天天是星期几
   * @param   = 天  2 ==星期1， 6 ==星期5
   * @return long 当前时间所代表的毫秒数
   * ***/
  public static int getWeekDayBySet(){    
	     int week = 1;
	     long t = System.currentTimeMillis(); 
	     Date date = new Date(t); 
	     Calendar calendar = Calendar.getInstance(); 
	     calendar.setTime(date);
	     week = calendar.get(Calendar.DAY_OF_WEEK); 
	     return   week;
 }  
	/*  
	 *  判断一下时间是否在开盘时间内
	 * 
	 */ 
	public static boolean isOpenTime(){   
	  long t = System.currentTimeMillis(); 
	  int a = getWeekDayBySet();	// 如果这个数字在2-6之间，则符合要求		
	  long	amOpen = getTimesDataBySet(9, 30, 0);
	  long	amClose = getTimesDataBySet(11, 30, 0);
	  long	pmOpen =  getTimesDataBySet(13, 0, 0);
	  long  pmClose = getTimesDataBySet(15, 0, 0);
	//  System.out.println(" === week == " + a + " t = "+ t +"  pmOpen = " + pmOpen);
	   
	  if(a>1&&a<7){
		  if((t>=amOpen&&t<=amClose)||(t>=pmOpen&&t<=pmClose)){ 
			   return true;  // 只有日期在周一到周五之间，时间为开盘时间的时候才会执行数据刷新
		  }else{ 
			   return false;
		  }  
	  }else{
		  return false;
	  }
	} 
	
	public static boolean isOpenT(){   
		  long t = System.currentTimeMillis(); 
		  int a = getWeekDayBySet();	// 如果这个数字在2-6之间，则符合要求		
		  long	amOpen = getTimesDataBySet(9, 30, 0); 
		  long  pmClose = getTimesDataBySet(15, 0, 0);
		//  System.out.println(" === week == " + a + " t = "+ t +"  pmOpen = " + pmOpen);
		   
		  if(a>1&&a<7){
			  if((t>=amOpen&&t<=pmClose)){ 
				   return true;  // 只有日期在周一到周五之间，时间为开盘时间的时候才会执行数据刷新
			  }else{ 
				   return false;
			  }  
		  }else{
			  return false;
		  }
		} 
  
  /*********** 日期类型格式转换以及时差参数处理 end ************/
  /*** 
   * 数据排序算法
   * ***/
	 /**
	  * 冒泡排序
	  * @input 一个未排序的数组
	  *
	  * @return 按照从小到大排序完成的数组
	  */
	 public static int[] doSort(int source[]) {
	  int length = source.length;
	  for (int i = length - 1; i > 1; i--) {
	   for (int j = 0; j < i; j++)
	    if (source[j] > source[j + 1]) {
	     int tmp = source[j];
	     source[j] = source[j + 1];
	     source[j + 1] = tmp;
	    }
	  }
	  return source;
	 } 
	 // float 类型的方法重载 
	 public static float[] doSort(float source[]) {
		  int length = source.length;
		  for (int i = length - 1; i > 0; i--) {
		   for (int j = 0; j < i; j++)
		    if (source[j] > source[j + 1]) {
		     float tmp = source[j];
		     source[j] = source[j + 1];
		     source[j + 1] = tmp;
		    }
		  }
		  return source;
		 }    
	 
	// vector 类型的方法重载 
	 public static Vector doKLineVectorSort(Vector vv) {
		  Vector vvs = new Vector();
		  int size = vv.size();
		  for(int i=size-1;i>=0;i--){
			  vvs.addElement(vv.elementAt(i));
		  }		  
		  return vvs;
	 }  
	// 过滤特殊字符
	 public static String filterString(String str){
		 StringBuffer buffer = new StringBuffer();
		 String filter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		 char c,cc;  
		 for (int i = 0; i < str.length(); i++) {
	         c = str.charAt(i);
	       //  System.out.println( i + " == " + c);
	         if(c<'0'||c>'9'){
	        //	 System.out.println("含有非法字符：" + c); 
	        	 for(int j=0;j<26;j++){
	         		cc = filter.charAt(j);
	         		if(cc==c){
	         			buffer.append(c);
	         		}
	         	 }     
	         }else{
	        	buffer.append(c); 
	         }
	     }
		 return buffer.toString();
	 }
	 
	 // 数据处理，去空和替换特殊字符 &#8220;="  &#8221;" ,'\0'
	 public static String getFilterText(String src){
	    	// 先替换字符
	      String str = src.trim();
	      String str1 =	DataTypeChange.replace(str, "&#8220;", "\"");
	      String str2 = DataTypeChange.replace(str1, "&#8221;", "\"");
	      String str3 = DataTypeChange.replace(str2, "&#183;", "."); 
	   /*   StringBuffer buffer = new StringBuffer();
	      char c;
	      char fi = '\0';
	      for(int i=0;i<str3.length();i++){
	    	  c = str3.charAt(i);
	    	  if(c!=fi){// 如果存在\0则跳过添加
	    		  buffer.append(c);
	    	  }
	      }
	       buffer.toString();
	      */
	      return str3;
	    }    
	 
}

