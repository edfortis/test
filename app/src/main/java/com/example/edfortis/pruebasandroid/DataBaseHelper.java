package com.example.edfortis.pruebasandroid;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by edfortis on 03/05/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //android default system path assets
    private static String DB_PATH = "/data/data/com.example.edfortis.pruebasandroid";
    private static String DB_NAME = "transito";
    private SQLiteDatabase myDataBase;
    private Context myContext;


    public  DataBaseHelper(Context context){
        super(context, DB_NAME, null, 1);

        this.myContext = context;
    }

    private void createDataBase()throws IOException{
        boolean dbExist = checkDataBase();
        if(dbExist){

        }else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


//changes
    private boolean checkDataBase(){
        SQLiteDatabase checDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){

        }
        if(checDB != null){
            checDB.close();
        }
        return checDB != null? true : false;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the InputFile to the OutputFile
        byte[] buffer = new byte[1024];
        int lenght;
        while((lenght = myInput.read(buffer))> 0) {
            myOutput.write(buffer, 0, lenght);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close(){
        if(myDataBase != null){
            myDataBase.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
