package com.example.tt.dialogView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tt.MainActivity;
import com.example.tt.R;
import com.example.tt.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 注意：
 * 创建本界面时，这个类里的所有函数是不会自动执行的
 */
public class AddAffairActivity extends AppCompatActivity {
    private EditText editText;
    private ImageButton send, cal;
    String currentEmail;
    private SharedPreferences.Editor editor;
    SimpleDateFormat simpleDateFormat;
    Date date;
    private String date_str;
    private AlertDialog.Builder alertDialog;
    private AlertDialog alertDialog_show;

    /**
     * 这个onCreate函数根本不会执行，Activity也就不会构建
     * 导致一些需要上下文的对象无法调用，只能在外部声明通过参数传进来
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_affair);
    }
    /**
     * 初始化弹窗
     * 弹窗内的元素都要在这里操作，在MainActivity中调用
     */
    public void initAddDialog(final Context context, SharedPreferences preferences){
        View view1 = View.inflate(context, R.layout.activity_add_affair, null);
        // 初始化控件
        editText = view1.findViewById(R.id.newAffair_text);
        editText.setBackground(null);// 取消下划线
        send = view1.findViewById(R.id.imageButton5);// 发送按钮根据文本框是否为空变化
        send.setEnabled(false);
        cal = view1.findViewById(R.id.imageButton4);
        // 初始化SharedPreferences
        currentEmail = preferences.getString("currentEmail", "");
        editor = preferences.edit();
        // 初始化打卡时间
        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS");// 每个事件的创建时间精确到毫秒，作为事件唯一标识

        // 监听文本框内容变化
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String content = editText.getText().toString();
                if(content.isEmpty()){
                    send.setEnabled(false);
                    send.setImageResource(R.drawable.send_disabled);
                }else{
                    send.setEnabled(true);
                    send.setImageResource(R.drawable.send);
                }
            }
        });
        // 发送按钮点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = new Date(System.currentTimeMillis());//获取当前时间
                date_str = simpleDateFormat.format(date);
                editor.putString(currentEmail+"#affairID="+date_str+"#title", editText.getText().toString());
                editor.apply();
                alertDialog_show.dismiss(); // 关闭弹窗
            }
        });
        // 日历按钮点击事件
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetCalActivity cal = new SetCalActivity();
                cal.initCalDialog(context);
            }
        });
        // 设置并展示弹窗
        alertDialog = new AlertDialog.Builder(context);
        alertDialog
                .setTitle("New Affair")
                .setView(view1)
                .create();
        alertDialog_show = alertDialog.show();
    }

}