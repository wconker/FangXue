package com.android.fangxue.ui.ExcelOption;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.fangxue.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelAcitvity extends Activity {

    private final String TAG = "wukanghui";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_exceloption_excel_acitvity);
        new ExcelDataLoader().execute("name.xls");
    }

    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<String> getXlsData(String xlsName, int index) {
        ArrayList<String> countryList = new ArrayList<String>();
        AssetManager assetManager = getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.e(TAG, "the num of sheets is " + sheetNum);
            Log.e(TAG, "the name of sheet is  " + sheet.getName());
            Log.e(TAG, "total rows is 行=" + sheetRows);
            Log.e(TAG, "total cols is 列=" + sheetColumns);

            JSONArray jArray = new JSONArray();
            for (int i = 0; i < sheetRows; i++) {
                JSONObject columObj = new JSONObject();
                JSONArray Parent = new JSONArray();
                JSONObject parentObjF = new JSONObject();
                JSONObject parentObjM = new JSONObject();
                for (int c = 0; c < sheetColumns; c++) {
                    String Content = sheet.getCell(c, i).getContents();
                    String Content_Value;

//                    if (Content.equals("学生姓名")) {
//                        Content_Value = sheet.getCell(c + 1, i).getContents();
//                        columObj.put("studentname", Content_Value);
//                    }
//                    if (Content.equals("学号")) {
//                        Content_Value = sheet.getCell(c + 1, i).getContents();
//                        columObj.put("studentno", Content_Value);
//                    }
//                    if (Content.equals("父")) {
//                        Content_Value = sheet.getCell(c + 1, i).getContents();
//                        parentObjF.put("relationship", "父");
//                        parentObjF.put("parentname", Content_Value);
//                    }
//                    if (Content.equals("手机号码")) {
//                        Content_Value = sheet.getCell(c + 1, i).getContents();
//                        parentObjM.put("mobile", Content_Value);
//                        parentObjF.put("mobile", Content_Value);
//                    }
//                    if (Content.equals("母")) {
//                        Content_Value = sheet.getCell(c + 1, i).getContents();
//                        parentObjM.put("relationship", "母");
//                        parentObjM.put("parentname", Content_Value);
//                    }





                }
                Parent.put(parentObjF);
                Parent.put(parentObjM);
                columObj.put("parent", Parent);

                jArray.put(columObj);
            }
            Log.e("Conker", "" + jArray);
            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return countryList;
    }


    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            return getXlsData(params[0], 0);
        }

        @Override
        protected void onPostExecute(ArrayList<String> countryModels) {


            if (countryModels != null && countryModels.size() > 0) {
                //存在数据

            } else {
                //加载失败


            }

        }
    }


}
