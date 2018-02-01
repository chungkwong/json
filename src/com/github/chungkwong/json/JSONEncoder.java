/*
 * Copyright (C) 2017 Chan Chung Kwong <1m02math@126.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.chungkwong.json;
import java.util.*;
/**
 * Being used to generate JSON
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class JSONEncoder{
	/**
	 * Turn a Java object into JSON, where (nested) Map and List are turned
	 * in to JSON map and list respectively, Number becomes number,
	 * Boolean becomes boolean, String becomes string, everything else
	 * becomes null
	 * @param obj the java Object
	 * @return the JSON code
	 */
	public static String encode(Object obj){
		return encode(obj,WALKER);
	}
	/**
	 * Turn a Java object into JSON, where Map and List are transformed
	 * according to a given rule, Number becomes number,
	 * Boolean becomes boolean, String becomes string, everything else
	 * becomes null
	 * @param obj the java Object
	 * @param walker the rule
	 * @return the JSON code
	 */
	public static String encode(Object obj,POJOWalker walker){
		StringBuilder buf=new StringBuilder();
		encode(obj,walker,buf);
		return buf.toString();
	}

	/**
	 * Just like the one-argumented version, except that the output code
	 * is written into a buffer instead of returned
	 * @param obj to be encoded
	 * @param buf the buffer
	 */
	public static void encode(Object obj,StringBuilder buf){
		encode(obj,WALKER,buf);
	}
	/**
	 * Just like the two-argumented version, except that the output code
	 * is written into a buffer instead of returned
	 * @param obj to be encoded
	 * @param walker the rule
	 * @param buf the buffer
	 */
	public static void encode(Object obj,POJOWalker walker,StringBuilder buf){
		if(obj instanceof String)
			encodeString((String)obj,buf);
		else if(obj instanceof Number)
			encodeNumber((Number)obj,buf);
		else if(obj instanceof Boolean)
			encodeBoolean((Boolean)obj,buf);
		else if(walker.isMap(obj))
			encodeMap(obj,walker,buf);
		else if(walker.isList(obj))
			encodeList(obj,walker,buf);
		else
			buf.append("null");
	}
	private static void encodeBoolean(Boolean val,StringBuilder buf){
		buf.append(Boolean.toString(val));
	}
	private static void encodeNumber(Number val,StringBuilder buf){
		buf.append(Double.toString(val.doubleValue()));
	}
	private static void encodeString(String val,StringBuilder buf){
		buf.append("\"").append(val.replace("\\","\\\\").replace("\"","\\\"")).append("\"");
	}
	private static void encodeMap(Object val,POJOWalker walker,StringBuilder buf){
		buf.append('{');
		Iterator<Map.Entry<?,?>> iter=walker.getEntryIterator(val);
		if(iter.hasNext()){
			encodeEntry(iter.next(),walker,buf);
		}
		while(iter.hasNext()){
			buf.append(',');
			encodeEntry(iter.next(),walker,buf);
		}
		buf.append('}');
	}
	private static void encodeEntry(Map.Entry val,POJOWalker walker,StringBuilder buf){
		encode(val.getKey(),walker,buf);
		buf.append(':');
		encode(val.getValue(),walker,buf);
	}
	private static void encodeList(Object val,POJOWalker walker,StringBuilder buf){
		buf.append('[');
		Iterator iter=walker.getComponentIterator(val);
		if(iter.hasNext())
			encode(iter.next(),walker,buf);
		while(iter.hasNext()){
			buf.append(',');
			encode(iter.next(),walker,buf);
		}
		buf.append(']');
	}
	private static final StandardWalker WALKER=new StandardWalker();
	private static class StandardWalker implements POJOWalker{
		@Override
		public boolean isMap(Object obj){
			return obj instanceof Map;
		}
		@Override
		public boolean isList(Object obj){
			return obj instanceof List;
		}
		@Override
		public Iterator<Map.Entry<?,?>> getEntryIterator(Object obj){
			return ((Map)obj).entrySet().iterator();
		}
		@Override
		public Iterator<?> getComponentIterator(Object obj){
			return ((List)obj).iterator();
		}
	}
}
