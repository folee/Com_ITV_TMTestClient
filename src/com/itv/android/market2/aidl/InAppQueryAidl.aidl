package com.itv.android.market2.aidl;

interface InAppQueryAidl{
	/**
	 *
	 * if occur error ,will be return null
	 * esle return result
     * 
     */
   String detailsQuery(String reqParam);
   
   String orderRecordQuery(String reqParam);
}