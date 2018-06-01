package xtanapp.xtapp.com.dictionaryquery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;


public class DictionartDao {
    //返回的结果
    private static String mResult = "查询有误";
    //记录查询无结果的
    private List<String> mNoResultList = new ArrayList<>();
    //记录多结果的
    private List<String> mList = new ArrayList<>();
    //查询类型(1:汉字,2:英文字符串)
    private int mType = 0;

    //获取文件库路径
    private static String strPath = Environment.getExternalStorageDirectory().toString() + "/DictionaryFiles/ChineseDictionary.db";
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
            mResult = chineseQueryDB(index_key);
        } else if (index_key.matches("^[A-Za-z]+$")) {
            //将字母转成小写
            index_key = index_key.toLowerCase();
            //匹配字母
            mResult = characterQueryDB(index_key);
        } else {
            //其他
             mResult = "字典查询无结果";
        }
        return mResult;
    }
    //纯汉字查询
    private static String chineseQueryDB(String index) {
        Cursor cursor;
        String string="";
            //查询数据库
            cursor = db.query("valueTable",
                    new String[]{"desc"}, "name=?",
                    new String[]{index}, null, null, null);
            if (cursor.moveToNext()) {
                string = cursor.getString(0);
                string=StringFormat(string);
            }
            cursor.close();
            return string;
        }

    //字母查询
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
                String indexKey = cursor.getString(0);
                //多音字
                String indexWord;
                //判断查到的是否有多个音
                if (indexKey.contains("，")&&indexKey.contains(" ")||indexKey.contains("；")) {
                    //格式化成全汉字
                    String str=regexValue(indexKey);
                    String indexValue="";
                    for (int i=0;i<str.length();i++){
                        indexWord=str.substring(i,i+1);
                        indexValue = chineseQueryDB(indexWord);
                        strResult+=indexValue;
                    }



                }else if (indexKey.contains("，")){
                    String[] arr=indexKey.split("，");
                    String indexValue="";
                    for (String string:arr){
                        strResult= chineseQueryDB(string);
                        strResult+=indexValue;
                    }
                }else {
                    strResult=chineseQueryDB(indexKey);
                }

            }else{
                strResult="查询无结果";
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
      str=str.replace("，【","\n【");
      str=str.replace("。【","\n【");
      str=str.replace("，(","\n(");
      str=str.replace("。(","\n(");
      str=str.replace("；","\n");
        return  str;
    }


}
