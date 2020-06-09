package com.example.childlike.draw;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawCature {
    private static final String LOGTAG = "ImageUtil";

    /*
     * Picture를 Bitmap 파일로 저장
     * viw : View ...
     * fn : 파일 이름
     * return : 성공 유무
     */
    public static boolean PictureSaveToBitmapFile(View viw, final File storage, final String filename)
    {
        boolean bRes = false;
        if( viw == null && filename == null && filename.length() < 1 )
            return bRes;

        viw.setDrawingCacheEnabled(true);
        Bitmap bmp =  viw.getDrawingCache();
        if( bmp == null )
            return bRes;

        File file = new File(storage, filename+".png");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(LOGTAG, e.getMessage());
            return bRes;
        }

        bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        try {
            fOut.flush();
            fOut.close();
            bRes = true;
        } catch (IOException e) {
            Log.e(LOGTAG, e.getMessage());
            return bRes;
        }

        return bRes;
    }

    /*
     * Bitmap 파일을 읽어오기
     * fn : 파일 이름
     * return : bitmap 이미지, 실패했을 경우 null
     */
    public static Bitmap BitmapLoadFromFile( final File storage, final String filename )
    {
        Bitmap bmp = null;
        try
        {
            bmp = BitmapFactory.decodeFile( storage + "/" + filename);
        }catch( Exception e)
        {
            Log.e(LOGTAG, e.getMessage());
        }
        return bmp;
    }
}
