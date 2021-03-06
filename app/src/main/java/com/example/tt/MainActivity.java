package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tt.dialogView.AddAffairActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;
//import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.example.tt.util.myHttp;

import java.io.IOException;

/**shared文件
 * key: email#username value(String): email对应的用户名
 * key: email#pwd value(String): email对应的密码
 * key: currentEmail value(String): 当前登录账号的邮箱
 * key: email#signDate value(String): email对应的最近打卡日期
 * key: email#signTimes value(Int): email对应的连续打卡次数
 * key: email#delAffairNum value(Int): email对应的删除事务数
 * key: email#socialTimes value(Int): email对应的分享次数
 * key: email#settings#isRender value(Boolean): email对应账户的实时渲染设置
 * key: email#settings#isRecycle value(Boolean): email对应账户的自动回收设置
 * key: email#affairID=(事件创建时间)#title value(String): email对应账户下标识为affairID的事件标题
 * key: email#affairID=(事件创建时间)#content value(String): email对应账户下标识为affairID的事件内容 xxxxx
 * key: email#affairID=(事件创建时间)#date value(String): email对应账户下标识为affairID的事件日期
 * key: email#affairID=(事件创建时间)#status value(Boolean): email对应账户下标识为affairID的事件是否完成(true 已完成，false 未完成)
 * key: email#affairIDList value(String[]): email对应账户下注册的所有事件affairID(事件创建时间,...)
 * key: currentAffairDate value(String): 当前编辑事务的日期，用于人工选择日期界面暂时存储
 * key: currentAffairID value(String): 当前点击事务项的ID，在点击事务项按钮时设置，被打开的事务编辑界面获取
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String Tag = "12345";
    private Tencent mTencent;// 新建Tencent实例用于调用分享方法
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageButton settings_img;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    // 需要在登录后改变的控件
    public static Button login_button;
    public static TextView username_show, email_show;
    public static ImageButton login_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag, "Create");
        setContentView(R.layout.activity_main);
//        // 设置状态栏透明
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // 顶部状态栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); // 底部导航栏
//        window.setStatusBarColor(Color.TRANSPARENT);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        // 设置状态栏颜色 有用
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加Flag把状态栏设为可绘制模式
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        // 初始化控件
        initView();
        initEvent();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_done, R.id.nav_outdated)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        mTencent = Tencent.createInstance("222222", this.getApplicationContext());

    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
    }
    private void initEvent(){
        // 添加按钮点击出现弹窗
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 未登录不允许使用
                String currentEmail = preferences.getString("currentEmail", "");
                if(currentEmail.trim().length()==0){
                    // 还未登录
                    Toast.makeText(MainActivity.this, R.string.notLogin, Toast.LENGTH_SHORT).show();
                }else{
                    AddAffairActivity add = new AddAffairActivity();
                    add.initAddDialog(MainActivity.this, preferences);
                }
            }
        });
    }


    // 初始化左侧滑菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(Tag, "onCreateOptions menu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        {/**
         * 菜单渲染完成后找到菜单上按钮并添加事件
         * 菜单上的控件必须在这里初始化
         */
            username_show = findViewById(R.id.username_show);
            email_show = findViewById(R.id.email_show);
            // goto login page
            login_img = findViewById(R.id.loginImg);
            login_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            login_button = findViewById(R.id.button3);
            login_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            // goto settings page
            settings_img = findViewById(R.id.imageButton);
            settings_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }
        Log.d(Tag, "onCreateOptions menu done");
        // 保持登录状态
        keep_login();
        return true;
    }

    // 初始化分享按钮
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(Tag, "onOptionsItemSelected");
        // 社交次数加一
        final String currentEmail = preferences.getString("currentEmail", "");
        if(currentEmail != ""){
            // 网络请求不能在主线程内，防止页面假死
            Thread loginThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    myHttp.postHTTPReq("/increaseShareTimes", "email="+currentEmail);
                }
            });
            loginThread.start();
        }
        switch (item.getItemId()){
            case R.id.action_share:// 分享按钮
                doShareToQQ();
                break;
        }
        Log.d(Tag, "onOptionsItemSelected done");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(Tag, "onSupportNavigateUp");
        Log.d(Tag, "onSupportNavigateUp done");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * 调试用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag, "Pause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag, "Resume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag, "Restart");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag, "Start");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag, "Stop");
    }



    /**
     * 分享到QQ空间
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {// 操作成功
        }
        protected void doComplete(JSONObject values) {
        }
        @Override
        public void onError(UiError e) {// 分享异常
        }
        @Override
        public void onCancel() {// 取消分享
        }
    }
    IUiListener shareListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Tag, "-->onActivityResult " + requestCode + " resultCode=" + resultCode); // 10103 == share
        Tencent.onActivityResultData(requestCode, resultCode, data, shareListener);
        Tencent.handleResultData(data, shareListener);
    }
    private void doShareToQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测试：分享到QQ空间");// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "别人的app，不行。我们的app，行！");// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.zjmpage.com/TT/README.html");// 内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://b182.photo.store.qq.com/psb?/V11Upd1r0SLvmz/UvLQ.j9dYQWZR9scwprHiOB3UQh1t6EB59JnpeBDY64!/b/dLYAAAAAAAAA&bo=iAGWAQAAAAARFz4!&rf=viewer_4");// 网络图片地址　　
         params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "TickTock");// 应用名称
//        分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
//        QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
//        QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        // 分享操作要在主线程中完成
        if(null != mTencent){
            mTencent.shareToQQ(MainActivity.this, params, shareListener);
        }
    }

    /**
     * 登录后重启app会保持登录状态
     */
    private void keep_login(){
        // 判断是否已登录
        String currentEmail = preferences.getString("currentEmail", "");
        if(currentEmail.trim().length()==0){
            // 还未登录，无事发生
        }else{
            after_login(currentEmail);
        }
        Log.d(Tag, "create done");
    }

    /**
     * 登录成功后需要改变界面、控件
     */
    private void after_login(final String email){
        // 登录按钮消失，个人信息展示
        MainActivity.login_button.setVisibility(View.INVISIBLE);
        MainActivity.username_show.setVisibility(View.VISIBLE);
        MainActivity.email_show.setVisibility(View.VISIBLE);
        final String[] username = {""};
        MainActivity.email_show.setText(email);

        // 头像点击事件重写
        MainActivity.login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        // 头像图片
        MainActivity.login_img.setImageResource(R.drawable.logged_user);

        Thread getUsernameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                username[0] = myHttp.getHTTPReq("/getUsername?email="+email);
            }
        });
        getUsernameThread.start();
        try {
            getUsernameThread.join();
            MainActivity.username_show.setText(username[0]);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}




