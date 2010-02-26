/* 
 * Copyright (C) 2002-2010 XimpleWare, info@ximpleware.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package com.ximpleware.extended;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
/**
 * 
 * XMLMemMappedBuffer maps an XML document into memory using memory map.
 * It avoids the complete loading of the document in memory thus making it
 * possible to process very large files. But the trade off is potentially 
 * lower performance due to disk IO.
 */

public class XMLMemMappedBuffer implements IByteBuffer {
    MappedByteBuffer input[];
    long length;
    public XMLMemMappedBuffer(){
        
    }
    
    public long length(){
        return length;
    }
    
    public byte byteAt(long index){
        return input[(int)(index>>30)].get((int)(index & 0x3fffffff));
    }
    
    public void readFile(String fileName) throws java.io.IOException, ParseExceptionHuge {
        File f = new File(fileName);
        long l = f.length();
        length = l;
        if (l>= (1L<< 38)){
            throw new ParseExceptionHuge("document too big > 256 Gbyte");
        }
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        FileChannel fc  = raf.getChannel();
        int pageNumber = (int)(l>>30)+(((l & 0x3fffffffL)==0)?0:1);
                
        input = new MappedByteBuffer[pageNumber];
        long l2 =0;
        for (int i=0;i<pageNumber;i++){
            if (l > (1<<30)){
                //bufferArray[i] = new byte[1<<30];
                input[i]= fc.map(FileChannel.MapMode.READ_ONLY, l2 ,1<<30);
                l2 = l2+(1<<30);
            }
            else{
                //bufferArray[i] = new byte[(int)l];
                input[i]= fc.map(FileChannel.MapMode.READ_ONLY, l2, l - (i<<30));
            }
            //input[i] = new RandomAccessFile(fileName, "r").getChannel()
            //.map(FileChannel.MapMode.READ_ONLY, 0,(1<<32)-1);
        }
        if (fc!=null)
        fc.close();
        if (raf!=null)
        raf.close();
    }
    
	/**
	 * NOt implemented yet
	 */
    public byte[] getBytes(){
	    return null;
	}
	
	/**
	 * not implemented yet
	 */
    public byte[] getBytes(int offset, int len){
	    return (byte[]) null;
	}    
    
    /**
     * 
     */
    public void writeToOutputStream(java.io.OutputStream ost, long os, long len) 
	throws java.io.IOException{
    	//page size is 1<<30
		// then find the remainder
		//ost's page #
    	int pageN = (int)(os>>30);
    	byte[] ba = null;
    	//ost's remainder
    	int pos =  (int)(os&((1<<31)-1));
    	// only write to outputStream once
    	if (pos+len <= 1<<30){
    		ba = input[pageN].array();
    		ost.write(ba, pos,(int) len);
    		return;
    	}
    	//write the head
    	ba = input[pageN].array();
    	ost.write(ba,pos, (1<<30)-pos);
    	pageN++;
    	len -= (1<<30)-pos;
    	
    	//write the mid sections
    	while(len>(1<<30)){
    		ba = input[pageN].array();
    		ost.write(ba,0, (1<<30));
    		pageN++;
    		len -= (1<<30);
    	}
    	ba = input[pageN].array();
    	//write the tail
    	ost.write(ba, 0, (int)len);
    	return;
	}
}
