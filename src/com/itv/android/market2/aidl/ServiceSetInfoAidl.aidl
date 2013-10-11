package com.itv.android.market2.aidl;

interface ServiceSetInfoAidl{
	/**
	 *set business information
	 * if occur error ,will be return 0
	 * esle return 1
     * 
     */
    int setInfo(String info);
}