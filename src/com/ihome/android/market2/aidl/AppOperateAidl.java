/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/Emerson/Akazam/NJTelecomSVN/New/MarketClient_OTT_2.X/src/com/ihome/android/market2/aidl/AppOperateAidl.aidl
 */
package com.ihome.android.market2.aidl;
public interface AppOperateAidl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.ihome.android.market2.aidl.AppOperateAidl
{
private static final java.lang.String DESCRIPTOR = "com.ihome.android.market2.aidl.AppOperateAidl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.ihome.android.market2.aidl.AppOperateAidl interface,
 * generating a proxy if needed.
 */
public static com.ihome.android.market2.aidl.AppOperateAidl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.ihome.android.market2.aidl.AppOperateAidl))) {
return ((com.ihome.android.market2.aidl.AppOperateAidl)iin);
}
return new com.ihome.android.market2.aidl.AppOperateAidl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_appOperate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.appOperate(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_appUpdateCheck:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.appUpdateCheck();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.ihome.android.market2.aidl.AppOperateAidl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	 *
	 * if occur error ,will be return 1
	 * esle return 0
     * 
     */
@Override public java.lang.String appOperate(java.lang.String reqParam) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(reqParam);
mRemote.transact(Stub.TRANSACTION_appOperate, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 第三方应用检测应用是否需要更新
 	 * true 有更新， false 没有更新
     */
@Override public boolean appUpdateCheck() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_appUpdateCheck, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_appOperate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_appUpdateCheck = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
	 *
	 * if occur error ,will be return 1
	 * esle return 0
     * 
     */
public java.lang.String appOperate(java.lang.String reqParam) throws android.os.RemoteException;
/**
	 * 第三方应用检测应用是否需要更新
 	 * true 有更新， false 没有更新
     */
public boolean appUpdateCheck() throws android.os.RemoteException;
}
