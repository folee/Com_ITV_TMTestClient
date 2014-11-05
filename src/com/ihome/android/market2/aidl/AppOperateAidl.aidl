package com.ihome.android.market2.aidl;

interface AppOperateAidl{
	/**
	 *
	 * if occur error ,will be return 1
	 * esle return 0
     * 
     */
   String appOperate(String reqParam);
   
}