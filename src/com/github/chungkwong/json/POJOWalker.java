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
 * Being used to turn Java Object into JSON Object
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public interface POJOWalker{
	/**
	 * Check if a Java object should be turned into a JSON map
	 * @param obj the Java Object
	 * @return should or should not
	 */
	boolean isMap(Object obj);
	/**
	 * Check if a Java object should be turned into a JSON list
	 * @param obj the Java Object
	 * @return should or should not
	 */
	boolean isList(Object obj);
	/**
	 * Get a way to iterate over the fields to be transformed into fields
	 * in the JSON map that come from a Java Object
	 * @param obj the Java object
	 * @return a Iterator
	 */
	Iterator<Map.Entry<?,?>> getEntryIterator(Object obj);
	/**
	 * Get a way to iterate over the elements to be transformed into
	 * elements in the JSON list that come from a Java Object
	 * @param obj the Java object
	 * @return a Iterator
	 */
	Iterator<?> getComponentIterator(Object obj);
}
