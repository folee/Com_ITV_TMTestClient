package com.itv.android.market2.aidl;

interface InAppOrderAidl{
	/**
	 *
	 * if occur error ,will be return 0
	 * esle return 1
     * 
     */
   int orderRequest(String reqParam);
}