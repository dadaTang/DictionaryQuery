package xtanapp.xtapp.com.dictionaryquery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * 成语词词典查询
 */
public class IdioDictionartDao {
    //返回的结果
    private static String mResult = "查询有误";
    //记录查询无结果的
    private List<String> mNoResultList = new ArrayList<>();
    //记录多结果的
    private List<String> mList = new ArrayList<>();
    //查询词典类型(0：默认汉语，1:汉英词典,2:成语,)
    private int mType = 0;
    //获取文件库路径
    private static String strPath = Constants.IDIO_DIC_PATH;
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
            mResult = "查询无结果";
        }
        return mResult;
    }

    //纯汉字查询
    private static String chineseQueryDB(String index) {
        Cursor cursor;
        String string = "";
        //查询数据库
        cursor = db.query("valueTable",
                new String[]{"value_desc"}, "value_key=?",
                new String[]{index}, null, null, null);
        if (cursor.moveToNext()) {
            string = cursor.getString(0);
            string = StringFormat(string);
        }else{
            string="查询无结果";
        }
        cursor.close();
        return string;
    }

    //字母查询
    private static String characterQueryDB(String index) {
        Cursor cursor;
        //查询的结果
        String strResult="";
        String indexValue;
        // 查询数据库
        cursor = db.query("indexTable", new String[]{"index_value"},
                "index_key=?", new String[]{index},
                null, null, null);



        while (cursor.moveToNext()) {
            indexValue = cursor.getString(0);

            indexValue = chineseQueryDB(indexValue);
            strResult += indexValue + "\n\n";
        }
        if (strResult=="") {
            strResult = "查询无结果";
        }

//        if (cursor.moveToNext()) {
//            for (int i=0;i<cursor.getColumnCount();i++){
//                //获取查询到的第一行
//                indexValue = cursor.getString(i);
//                //多音字
//                indexValue = chineseQueryDB(indexValue);
//                strResult+=indexValue;
//                }
//        }else{
//            strResult="查询无结果";
//        }
        cursor.close();
        return strResult;

    }


    //获取字符串中的所有汉字
    private static String regexValue(String string) {
        return string.replaceAll("[^\u4e00-\u9fa5]", "");
    }

    //结果格式化
    private static String StringFormat(String str) {
        str = str.replace("$", "\n");
        str = str.replace("$", "\n");
        str = str.replace("$", "\n");
        str = str.replace("$", "\n");
        str = str.replace("；", "\n");
        return str;
    }


}
