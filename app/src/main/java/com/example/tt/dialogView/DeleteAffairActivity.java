package com.example.tt.dialogView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tt.R;
import com.example.tt.util.myHttp;

import org.json.JSONObject;

import java.io.IOException;

public class DeleteAffairActivity extends AppCompatActivity {

    private AlertDialog.Builder alertDialog;
    private AlertDialog alertDialog_show;
    private TextView delete_reminder, title_text, id_text;
    private SharedPreferences.Editor editor;
    private String currentEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_affair);
    }

    public void initDeleteDialog(final Context context, final SharedPreferences preferences, final String affairID){
        View view1 = View.inflate(context, R.layout.activity_delete_affair, null);
        // 初始化控件
        delete_reminder = view1.findViewById(R.id.textView5);
        title_text = view1.findViewById(R.id.textView25);
        id_text = view1.findViewById(R.id.textView26);
        // 初始化SharedPreferences
        currentEmail = preferences.getString("currentEmail", "");
        editor = preferences.edit();

        final String[] doc_str = new String[1];
        final JSONObject[] doc = new JSONObject[1];
        Thread getOneAffairThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doc_str[0] = myHttp.getHTTPReq("/getOneAffair?email="+currentEmail+"&affairID="+affairID);
                doc[0] = myHttp.getJsonObject(doc_str[0]);
            }
        });
        getOneAffairThread.start();
        try {
            getOneAffairThread.join();
            // 设置事件标题
            title_text.setText("Title: "+doc[0].getString("title"));
            // 设置事件ID
            id_text.setText("AffairID: "+ affairID);
        }catch(Exception e){
            e.printStackTrace();
        }

        // 设置并展示弹窗
        alertDialog = new AlertDialog.Builder(context);
        alertDialog
                .setTitle(R.string.delAffair)
                .setView(view1)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Thread getOneAffairThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                myHttp.getHTTPReq("/deleteOneAffair?email="+currentEmail+"&affairID="+affairID);
                            }
                        });
                        getOneAffairThread.start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 不将数据删除
                    }
                })
                .create();
        alertDialog_show = alertDialog.show();
    }
}