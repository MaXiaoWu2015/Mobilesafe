package com.matingting.mobilesafe.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhoneAddressDao {
	public static String path="/data/data/com.matingting.mobilesafe/files/address.db"; // 注意,该路径必须是data/data目录的文件,否则数据库访问不到
	public  static String QueryPhoneAddress(String number){
		SQLiteDatabase addressDB=SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		String location="未知号码";
		//第一位 1  第二位3 4 5 7 8
		if(number.matches("^1[3,4,5,7,8]\\d{9}$"))
		{
			System.out.println("number"+number);
			Cursor cursor=addressDB.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)", new String[]{number.substring(0, 7)});
		    cursor.moveToFirst();
		   location=cursor.getString(0);
		   cursor.close();
		}
		else if(number.matches("^\\d+$")){
			switch (number.length())
			{
			case 3:
				location="报警号码";break;
			case 5:
				location="客服电话";break;
			case 7:
			case 8:
				location = "本地电话";
				break;
			default:
				if(number.startsWith("0")&&number.length()>10)
				{
					Cursor cursor=addressDB.rawQuery("select location from data2 where area =?", new String[]{number.substring(1, 4)});
					if(cursor.moveToNext())
					{
						location=cursor.getString(0);
						
					}else{
						 cursor=addressDB.rawQuery("select location from data2 where area =?", new String[]{number.substring(1, 3)});
						 if(cursor.moveToNext())
						 {
							 location=cursor.getString(0);
								
						 }
						
					}
					cursor.close();
				}
				
				break;	
			}
		}
		
		
		
		
		return location;
		
	}
}
