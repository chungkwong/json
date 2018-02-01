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

/**
 * Being used to turn JSON object into Java object
 * @author Chan Chung Kwong <1m02math@126.com>
 * @param <M> the type that represents maps
 * @param <L> the type that represents lists
 */
public interface JSONWalker<M,L>{
	/**
	 * Create a mutable object corresponding to a empty map in JSON
	 * @return the mutable object
	 */
	M createMap();
	/**
	 * Create a mutable object corresponding to a empty list in JSON
	 * @return the mutable list
	 */
	L createList();
	/**
	 * Put a field from a JSON object into a mutable Java object
	 * @param value the value of the field
	 * @param index the key of the field
	 * @param parent the mutable object
	 */
	void onEntry(Object value,Object index,M parent);

	/**
	 * Put a element from a JSON list into a mutable Java object
	 * @param value the element
	 * @param index the index of that element
	 * @param parent the mutable object
	 */
	void onComponent(Object value,int index,L parent);
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
