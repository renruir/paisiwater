package com.paisiwater.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yuhaichao on 2016/11/17.
 */
public class RuntimeCache {


		private static RuntimeCache cache = null;

		private static ConcurrentHashMap<String, Object> instance = new ConcurrentHashMap<String, Object>();

		private RuntimeCache() {

		}

		public static synchronized RuntimeCache getCacheInstance() {
			if (cache == null)
				cache = new RuntimeCache();
			return cache;
		}

		public void put(String id, Object o) {
			instance.put(id, o);
		}

		public void remove(String id) {
			instance.remove(id);
		}

		public Object get(String id) {
			return instance.get(id);
		}



}
