package com.example.tt.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tt.LoginActivity;
import com.example.tt.MainActivity;
import com.example.tt.R;
import com.example.tt.dialogView.DeleteAffairActivity;
import com.example.tt.editAffairActivity;
import com.example.tt.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout ll;
    private Button item_button;
    private View root;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String currentEmail;
    private String[] affairIDList;
    private ImageView empty_img;
    private TextView empty_text, item_date, item_title;
    private CheckBox item_check;
    private Boolean isRecycle;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false); // 实例化一个view，即fragment_home.xml

        initView();
        Log.d("12345", "oncreatefragment");

        // 获取所有事件并显示
        try {
            getAllAffair();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }
    private void initView(){
        ll = root.findViewById(R.id.ll); // 元素需要到root里去找

        // sharedPreferences
        preferences = requireActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");
        affairIDList = preferences.getString(currentEmail+"#affairIDList", "").split(",");

        // 事务列表为空的提示
        empty_img = root.findViewById(R.id.imageView11);
        empty_text = root.findViewById(R.id.textView22);

        // 初始化下拉刷新layout
        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT); // LARGE

        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getAllAffair();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 重新加载完成，收起下拉进度
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // 读取所有事务并添加事务项（需要界面重新加载才能显示）
    // todo 优化，提升速度
    private void getAllAffair() throws ParseException {
        isRecycle = preferences.getBoolean(currentEmail+"#settings#isRecycle", false);
        // 清空界面
        ll.removeAllViews();
        // 重新获取当前账户，避免错误
        currentEmail = preferences.getString("currentEmail", "");
        if(currentEmail.trim().length()==0){
            // 未登录
            if_empty(true);
        }else{
            // 已登录
            String affairIDList_str = preferences.getString(currentEmail+"#affairIDList", "");
            if(affairIDList_str.trim().length()==0){
                // 该账户尚无事务，避免用空字符串创建一个事务
                if_empty(true);
                return;
            }else{
                // 该账户已有事务，获取所有事务并显示
                affairIDList = affairIDList_str.split(",");
                if(isRecycle){
                    // 回收模式下，只显示未完成事件
                    for (String affairID : affairIDList){
                        if(!preferences.getBoolean(currentEmail+"#affairID="+affairID+"#status", false)){
                            addItem(affairID);
                        }
                    }
                }else{
                    for (String affairID : affairIDList){
                        addItem(affairID);
                    }
                }
                if_empty(false);
            }
        }
    }

    // 添加事务项
    /**
     * 事务项的所有控件都在这里控制
     * @param affairID
     * @throws ParseException
     */
    private void  addItem(final String affairID) throws ParseException {
        final View new_view = View.inflate(getContext(), R.layout.affair_item, null); // getContext()在fragment中获取上下文
        item_button = new_view.findViewById(R.id.button);
        item_date = new_view.findViewById(R.id.textView24);
        item_title = new_view.findViewById(R.id.textView23);
        item_check = new_view.findViewById(R.id.checkBox);

        // 获取该事务设置的日期，并显示
        String affairDate_str = preferences.getString(currentEmail+"#affairID="+affairID+"#date", "");
        item_date.setText(affairDate_str);
        // 获取当前日期，判断该事务是否已过期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());//获取当前时间
        Date affairDate = simpleDateFormat.parse(affairDate_str);
        if(DateUtil.differentDays(date, affairDate) < 0){
            // 事务已过期
            item_date.setTextColor(getResources().getColor(R.color.outofdate));
        }

        // 显示事务Title
        String title = preferences.getString(currentEmail+"#affairID="+affairID+"#title", "");
        item_title.setText(title);

        // 将事务ID赋给按钮Text，用于调试，实际字体颜色设为透明不显示
        item_button.setText(affairID);
        // 单击跳转到事务编辑界面
        item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("12345", "ohhhhhhh");
                // 将存储数据currentAffairID设置为本按钮对应的事务ID
                editor.putString("currentAffairID", affairID);
                editor.apply();
                Intent intent = new Intent(getActivity(), editAffairActivity.class);
                startActivity(intent);
            }
        });
        // 长按跳出是否删除弹窗
        item_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 删除该事务存储的所有信息
//                deleteItem(new_view);
                DeleteAffairActivity delete = new DeleteAffairActivity();
                delete.initDeleteDialog(getContext(), preferences, affairID);
                return true;
            }
        });

        // checkbox初始化
        Boolean isChecked = preferences.getBoolean(currentEmail + "#affairID=" + affairID + "#status", false);
        item_check.setChecked(isChecked);
        item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // 选中了
                    editor.putBoolean(currentEmail+"#affairID="+affairID+"#status", true);
                }else{
                    // 未选中
                    editor.putBoolean(currentEmail+"#affairID="+affairID+"#status", false);
                }
                editor.apply();
            }
        });
        ll.addView(new_view); //添加一个View
    }

    private void if_empty(Boolean is_empty){
        if(is_empty){
            empty_text.setVisibility(View.VISIBLE);
            empty_img.setVisibility(View.VISIBLE);
        }else{
            empty_text.setVisibility(View.INVISIBLE);
            empty_img.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        Log.d("12345", "onstartfragment");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 登录后以及退出登录后，都会执行onResume()，在这里刷新事务列表可覆盖这两种情况
        try {
            getAllAffair();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("12345", "onresumefragment");
    }

    @Override
    public void onPause() {
        Log.d("12345", "onpausefragment");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("12345", "onstopfragment");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("12345", "ondestroyfragment");
        super.onDestroy();
    }
}