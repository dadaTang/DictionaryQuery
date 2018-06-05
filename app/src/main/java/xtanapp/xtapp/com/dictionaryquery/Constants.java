package xtanapp.xtapp.com.dictionaryquery;

import android.os.Environment;

//字典查询路径
public class Constants {
    //SD卡主文件夹目录
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().toString() + "/DictionaryFiles";
    //中文词典路径
    public static final String CHINESE_DIC_PATH=ROOT_PATH+"/ChineseDictionary.db";
    //汉英词典路径
    public static final String CHINESE_ENGLISH_DIC_PATH=ROOT_PATH+"/CN_ENDictionary.db";
    //成语词典路径
    public static final String IDIO_DIC_PATH=ROOT_PATH+"/IdioDictionary.db";


    //private static final String CHINESE_DIC_PATH=ROOT_PATH+"/ChineseDictionary.db";
}
