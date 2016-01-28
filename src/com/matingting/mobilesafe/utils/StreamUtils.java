package com.matingting.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	public static String readFromStream(InputStream ins) throws IOException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=ins.read(buffer))!=-1)
		{
			out.write(buffer, 0, len);
		}
		String result=out.toString();
		ins.close();
		out.close();
		return result;
	}

}
