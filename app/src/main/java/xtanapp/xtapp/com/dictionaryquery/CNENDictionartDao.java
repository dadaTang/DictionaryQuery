package xtanapp.xtapp.com.dictionaryquery;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 中文字典查询
 * */
public class CNENDictionartDao {
    //返回的结果
    private static String mResult = "查询有误";
    //记录查询无结果的
    private List<String> mNoResultList = new ArrayList<>();
    //记录多结果的
    private List<String> mList = new ArrayList<>();
    //查询词典类型(0：默认汉语，1:汉英词典,2:成语,)
    private int mType = 0;
    //获取文件库路径
    private static String strPath = Constants.CHINESE_ENGLISH_DIC_PATH;
    //初始化数据库
    private static SQLiteDatabase db = SQLiteDatabase.openDatabase(strPath, null,
            SQLiteDatabase.OPEN_READONLY);


    /**
     * index_key 查询关键字
     */
    //查询匹配
    public static String getValue(String index_key) {
        //匹配汉字
        if (index_key.matches("^[\\u4e00-\\u9fa5]+$")) {
            mResult = characterQueryDB(index_key);
        } else {
            //其他
            mResult = "字典查询无结果";
        }
        return mResult;
    }

    //查询
    private static String characterQueryDB(String index){
        Cursor cursor;
        //查询的结果
        String strResult="";
        // 查询数据库
        cursor = db.query("indexTable", new String[]{"index_value"},
                "index_key=?", new String[]{index},
                null, null, null);
        if (cursor.moveToNext()) {
            //获取查询到的第一行
             strResult = cursor.getString(0);
            strResult=StringFormat(strResult);
           }else{
            strResult="查询无结果！";
        }

        cursor.close();
        return strResult;

    }



    //获取字符串中的所有汉字
    private  static  String regexValue(String string){
        return string.replaceAll( "[^\u4e00-\u9fa5]","");
    }

    //结果格式化
    private static String StringFormat(String str) {
        str=str.replace("$","\n");
        str=str.replace("$","\n");
        str=str.replace("$","\n");
        str=str.replace("$","\n");
        str=str.replace("；","\n");
        return  str;
    }


}
