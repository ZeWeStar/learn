package com.shanxin.utils.httpClientUtil;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

public enum HttpRequestMethedEnum {
	
	GET {
		public HttpRequestBase createMethod(String url) {
			return new HttpGet(url);
		}
	},
	POST {
		public HttpRequestBase createMethod(String url) {
			return new HttpPost(url);
		}
	},
	PUT {
		public HttpRequestBase createMethod(String url) {
			return new HttpPut(url);
		}
	},
	DELETE {
		public HttpRequestBase createMethod(String url) {
			return new HttpDelete(url);
		}
	};
	
	public HttpRequestBase createMethod(String url) {
		return null;
	}
	

}
