package com.testBlueTooth;

import java.io.BufferedInputStream;  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.InputStream;  
import java.lang.ref.SoftReference;  
import java.net.URL;  
import java.util.HashMap;  
  
import android.graphics.drawable.Drawable;  
import android.os.Handler;  
import android.os.Message;  
import android.util.Log;  
import android.widget.ImageView;  
  
/** 
 * ����ͼƬ�첽������ 
 * @author liuxr 
 * 
 */  
public class AsyncImageLoader {  
  
     static ImageView singImageView; //����ڵ���ͼƬ�첽����   
     private static HashMap<String, SoftReference<Drawable>> singleImageCache = null;  
             
        /** 
         * ͨ��ͼƬ��ַ,����drawable 
         * @param url 
         * @return 
         */  
        public static Drawable loadImageFromUrl(String url) {  
            ByteArrayOutputStream out = null;  
            Drawable drawable = null;  
            int BUFFER_SIZE = 1024*16;  
            InputStream inputStream = null;  
            try{  
                inputStream = new URL(url).openStream();  
                BufferedInputStream in = new BufferedInputStream(inputStream, BUFFER_SIZE);  
                out = new ByteArrayOutputStream(BUFFER_SIZE);  
                int length = 0;  
                byte[] tem = new byte[BUFFER_SIZE];  
                length = in.read(tem);  
                while(length != -1){  
                    out.write(tem, 0, length);  
                    length = in.read(tem);  
                }  
                in.close();  
                drawable = Drawable.createFromStream(new ByteArrayInputStream(out.toByteArray()), "src");  
            }catch(Exception e){  
                e.printStackTrace();  
            }finally{  
                if(inputStream != null){  
                    try{  
                        inputStream.close();  
                    }catch(Exception e){}  
                }  
            }  
            return drawable;  
        }  
         /** 
          * �첽���õ���imageviewͼƬ,��ȡ������ 
         * @param url ����ͼƬ��ַ 
         * @param imageView ��Ҫ���õ�imageview 
         */  
        public static void setImageViewFromUrl(final String url, final ImageView imageView){  
            singImageView = imageView;  
            //���������Ϊ��,���½�һ��   
            if(singleImageCache == null){  
                singleImageCache = new HashMap<String, SoftReference<Drawable>>();  
            }  
            //������������Ѿ�������ͬ�ĵ�ַ,�ʹ��������л�ȡ   
            if(singleImageCache.containsKey(url)){  
                SoftReference<Drawable> soft = singleImageCache.get(url);  
                Drawable draw = soft.get();  
                singImageView.setImageDrawable(draw);  
                return;  
            }  
            final Handler handler = new Handler(){  
                @Override  
                public void handleMessage(Message msg) {  
                    singImageView.setImageDrawable((Drawable)msg.obj);  
                }  
            };  
            //���̲߳��ܸ���UI,�ֲ�Ȼ�ᱨ��   
             new Thread(){  
                 public void run() {  
                     Drawable drawable = loadImageFromUrl(url);  
                     if(drawable == null){  
                         Log.e("single imageview", "single imageview of drawable is null");  
                     }else{  
                         //���Ѿ���ȡ����ͼƬ����������   
                         singleImageCache.put(url, new SoftReference<Drawable>(drawable));  
                     }  
                     Message message = handler.obtainMessage(0, drawable);  
                     handler.sendMessage(message);  
                 };  
             }.start();  
         }  
}  