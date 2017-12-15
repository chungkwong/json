/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.chungkwong.json;

/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public interface JSONWalker<M,L>{
	M createMap();
	L createList();
	void onEntry(Object value,Object index,M parent);
	void onComponent(Object value,L parent);
	/*void onList(Object value,int index,Object parent);
	void onList(Object value,Object index,Object parent);
	void onBoolean(boolean value,int index,Object parent);
	void onBoolean(boolean value,Object index,Object parent);
	void onNumber(Number value,int index,Object parent);
	void onNumber(Number value,Object index,Object parent);
	void onString(String value,int index,Object parent);
	void onString(String value,Object index,Object parent);
	void onNull(int index,Object parent);
	void onNull(Object index,Object parent);*/
}
