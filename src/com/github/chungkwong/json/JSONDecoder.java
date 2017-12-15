/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.chungkwong.json;
import java.io.*;
import java.util.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class JSONDecoder{
	private static final int EOFException=-1;
	public static Object decode(String json) throws IOException,SyntaxException{
		return decode(new StringReader(json));
	}
	public static Object decode(Reader in) throws IOException,SyntaxException{
		return walk(new PushbackReader(in),WALKER);
	}
	public static void walk(String json,JSONWalker walker) throws IOException,SyntaxException{
		walk(new StringReader(json),walker);
	}
	public static void walk(Reader in,JSONWalker walker) throws IOException,SyntaxException{
		walk(new PushbackReader(in),walker);
	}
	private static Object walk(PushbackReader in,JSONWalker walker) throws IOException,SyntaxException{
		int c=readNonwhitespace(in);
		switch(c){
			case '{':
				return walkObject(in,walker);
			case '[':
				return walkArray(in,walker);
			case '"':
				return nextString(in);
			case 'n':
				expect(in,'u');expect(in,'l');expect(in,'l');
				return null;
			case 'f':
				expect(in,'a');expect(in,'l');expect(in,'s');expect(in,'e');
				return false;
			case 't':
				expect(in,'r');expect(in,'u');expect(in,'e');
				return true;
			default:
				in.unread(c);
				return nextNumber(in);
		}
	}
	private static Object walkObject(PushbackReader in,JSONWalker walker) throws SyntaxException,IOException{
		Object members=walker.createMap();
		int c=readNonwhitespace(in);
		if(c!='}'){
			in.unread(c);
			while(true){
				Object key=walk(in,walker);
				expectNonwhitespace(in,':');
				Object value=walk(in,walker);
				walker.onEntry(value,key,members);
				c=readNonwhitespace(in);
				if(c=='}')
					break;
				else if(c!=',')
					throw new SyntaxException("Expected ',' or '}'");
			}
		}
		return members;
	}
	private static Object walkArray(PushbackReader in,JSONWalker walker) throws IOException, SyntaxException{
		Object elements=new ArrayList<>();
		int c=readNonwhitespace(in);
		if(c!=']'){
			in.unread(c);
			while(true){
				walker.onComponent(walk(in,walker),elements);
				c=readNonwhitespace(in);
				if(c==']')
					break;
				else if(c!=',')
					throw new SyntaxException("Expected ',' or ']'");
			}
		}
		return elements;
	}
	private static Number nextNumber(PushbackReader in) throws IOException,SyntaxException{
		StringBuilder buf=new StringBuilder();
		int c=in.read();
		if(c=='-')
			c=appendAndNext(c,buf,in);
		while(Character.isDigit(c))
			c=appendAndNext(c,buf,in);
		if(c=='.'){
			c=appendAndNext(c,buf,in);
			while(Character.isDigit(c))
				c=appendAndNext(c,buf,in);
		}
		if(c=='e'||c=='E'){
			c=appendAndNext(c,buf,in);
			if(c=='-'||c=='+')
				c=appendAndNext(c,buf,in);
			while(Character.isDigit(c))
				c=appendAndNext(c,buf,in);
		}
		in.unread(c);
		return Double.valueOf(buf.toString());
	}
	private static int appendAndNext(int c,StringBuilder buf,Reader in) throws IOException{
		buf.appendCodePoint(c);
		return in.read();
	}
	private static String nextString(PushbackReader in) throws IOException,SyntaxException{
		int c;
		StringBuilder buf=new StringBuilder();
		while((c=in.read())!='"'){
			if(c=='\\'){
				c=in.read();
				switch(c){
					case '"':buf.append('\"');break;
					case '\\':buf.append('\\');break;
					case '/':buf.append('/');break;
					case 'b':buf.append('\b');break;
					case 'f':buf.append('\f');break;
					case 'n':buf.append('\n');break;
					case 'r':buf.append('\r');break;
					case 't':buf.append('\t');break;
					case 'u':buf.appendCodePoint(nextFourHexDigit(in));break;
					default:throw new SyntaxException("Illegal escape");
				}
			}else{
				buf.appendCodePoint(c);
			}
		}
		return buf.toString();
	}
	private static int nextFourHexDigit(PushbackReader in) throws IOException{
		int val=0;
		for(int i=0;i<4;i++){
			val=val*16+Character.digit(in.read(),16);
		}
		return val;
	}
	private static int readNonwhitespace(PushbackReader in) throws IOException{
		int c;
		while(Character.isWhitespace(c=in.read())){
		}
		return c;
	}
	private static void expectNonwhitespace(PushbackReader in,int c) throws SyntaxException,IOException{
		if(readNonwhitespace(in)!=c){
			throw new SyntaxException("Expected "+(char)c);
		}
	}
	private static void expect(PushbackReader in,int c) throws SyntaxException,IOException{
		if(in.read()!=c){
			throw new SyntaxException("Expected "+(char)c);
		}
	}
	private static final StandardWalker WALKER=new StandardWalker();
	private static class StandardWalker implements JSONWalker<Map,List>{
		@Override
		public Map createMap(){
			return new HashMap();
		}
		@Override
		public List createList(){
			return new ArrayList();
		}
		@Override
		public void onEntry(Object value,Object index,Map parent){
			parent.put(index,value);
		}
		@Override
		public void onComponent(Object value,List parent){
			parent.add(value);
		}

	}
}
