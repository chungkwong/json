/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.chungkwong.json;
import java.util.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class JSONEncoder{
	public static String encode(Object obj){
		StringBuilder buf=new StringBuilder();
		encode(obj,buf);
		return buf.toString();
	}
	public static void encode(Object obj,StringBuilder buf){
		if(obj instanceof String)
			encode((String)obj,buf);
		else if(obj instanceof Number)
			encode((Number)obj,buf);
		else if(obj instanceof Boolean)
			encode((Boolean)obj,buf);
		else if(obj instanceof Map)
			encode((Map)obj,buf);
		else if(obj instanceof List)
			encode((List)obj,buf);
		else
			buf.append("null");
	}
	private static void encode(Boolean val,StringBuilder buf){
		buf.append(Boolean.toString(val));
	}
	private static void encode(Number val,StringBuilder buf){
		buf.append(Double.toString(val.doubleValue()));
	}
	private static void encode(String val,StringBuilder buf){
		buf.append("\""+val.replace("\\","\\\\").replace("\"","\\\"")+"\"");
	}
	private static void encode(Map val,StringBuilder buf){
		buf.append('{');
		Iterator<Map.Entry> iter=val.entrySet().iterator();
		if(iter.hasNext()){
			encode(iter.next(),buf);
		}
		while(iter.hasNext()){
			buf.append(',');
			encode(iter.next(),buf);
		}
		buf.append('}');
	}
	private static void encode(Map.Entry val,StringBuilder buf){
		encode(val.getKey(),buf);
		buf.append(':');
		encode(val.getValue(),buf);
	}
	private static void encode(List val,StringBuilder buf){
		buf.append('[');
		Iterator iter=val.iterator();
		if(iter.hasNext())
			encode(iter.next(),buf);
		while(iter.hasNext()){
			buf.append(',');
			encode(iter.next(),buf);
		}
		buf.append(']');
	}
}
