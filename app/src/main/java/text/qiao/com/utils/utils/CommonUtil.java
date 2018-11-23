package text.qiao.com.utils.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.guangyao.friendschat.app.MyApplication;
import com.guangyao.friendschat.app.constant.BaseConstants;
import com.guangyao.friendschat.utils.log.LogUtil;
import com.guangyao.friendschat.view.activity.base.AbstractBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imlib.model.UserInfo;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.guangyao.friendschat.app.MyApplication.*;
import static com.guangyao.friendschat.app.MyApplication.getInstance;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by Administrator on 2018/2/5.
 * 公共类,主要用于一些常用的方法
 */

public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();
    private static final String KEY_LAUNCHER = "com.tomtop.shop.ActivityAlias1";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 获取渠道信息
     *
     * @return 渠道信息
     */
    public static String getChannel() {
        try {
            PackageManager pm = MyApplication.getInstance().getApplication().getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("channel");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * 　获取渠道号
     *
     * @return
     */
    public static String getChannelName(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("LEGU_CHANNEL");
        return msg;
    }

    /**
     * 根据输入法状态打开或隐藏输入法
     *
     * @param context 上下文
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static UserInfo getXinXi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("age", ACache.instance.getAsString(BaseConstants.APP_ACACHE_age) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_age));
            jsonObject.put("nickName", ACache.instance.getAsString(BaseConstants.APP_ACACHE_nickname) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_nickname));
            jsonObject.put("level", ACache.instance.getAsString(BaseConstants.APP_ACACHE_grade) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_grade));
            jsonObject.put("city", ACache.instance.getAsString(BaseConstants.APP_ACACHE_city) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_city));
            jsonObject.put("height", ACache.instance.getAsString(BaseConstants.APP_ACACHE_stature) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_stature));
            jsonObject.put("isVip", "2");
            jsonObject.put("vip_time", ACache.instance.getAsString(BaseConstants.APP_ACACHE_vip_time) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_vip_time));
            jsonObject.put("headerImg", ACache.instance.getAsString(BaseConstants.APP_ACACHE_headimg) == null ? "" : ACache.instance.getAsString(BaseConstants.APP_ACACHE_headimg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.e(jsonObject.toString());
        UserInfo userInfo = new UserInfo(ACache.instance.getAsString(BaseConstants.APP_ACACHE_userid), ACache.instance.getAsString(BaseConstants.APP_ACACHE_nickname), Uri.parse(jsonObject.toString()));
        return userInfo;
    }

    /**
     * 判断当前是否登录
     *
     * @return false 未登录true登录
     */
    public static boolean Islogin() {
        String user = (String) PreferenceUtil.getInstance().getData(BaseConstants.APP_SHARE_USER, "");
        if (user == null || ("").equals(user)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 获取版本号
     *
     * @return 返回版本号
     */
    public static String getAppInfo() {
        try {
            String pkName = getInstance().getApplication().getPackageName();
            String versionName = getInstance().getApplication().getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = getInstance().getApplication().getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    public static int getVersionCode() {
        try {
            String pkName = getInstance().getApplication().getPackageName();
            String versionName = getInstance().getApplication().getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = getInstance().getApplication().getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 隐藏输入键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 移除焦点
     *
     * @param view view
     */
    public static void removeFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }


    /**
     * 创建桌面快捷方式
     *
     * @param context Context
     */
    public static void creatShortcut(Context context) {
//        //创建快捷方式的Intent
//        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        //不允许重复创建
//        shortcutIntent.putExtra("duplicate", false);
//        //需要显示的名称
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
//        //快捷图片
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), R.mipmap.log);
//
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//
//        Intent intent = new Intent(context.getApplicationContext(), MovieActivity.class);
//        //下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//        //点击快捷图片，运行的程序主入口
//        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//        //发送广播。OK
//        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 删除程序的快捷方式
     *
     * @param context Context
     */
    public static void delShortcut(Context context, ComponentName componentName) {
//        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
//        //快捷方式的名称
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
//        Intent intent = new Intent(context.getApplicationContext(), MovieActivity.class);
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//        intent.setComponent(componentName);
//
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//
//        context.sendBroadcast(shortcut);

    }

    /**
     * 获取随机字符串
     *
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        String returnStr;
        char[] ch = new char[len];
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            ch[i] = (char) (rd.nextInt(9) + 97);
        }
        returnStr = new String(ch);
        return returnStr;
    }

    /**
     * 剪裁图片
     *
     * @param uri
     */
    private void startImageZoom(AbstractBaseActivity context, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("ic_return-data", true);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 图片的大小
     *
     * @param size
     * @return
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 对当前屏幕进行截屏操作
     */
    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        return bmp;

    }

    /**
     * 使用 Universal 作为图片加载器时，保存图片到相册使用的方法
     *
     * @param imageView
     */
    public static void saveImageByUniversal(ImageView imageView, Context context) {
        if (checkWriteStoragePermission()) {
            BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
            String bitPath = MediaStore.Images.Media.insertImage(getInstance().getApplication().getContentResolver(), bmpDrawable.getBitmap(), String.valueOf(System.currentTimeMillis()),
                    "");
            Uri uuUri = Uri.parse(bitPath);
            String path = getRealPathFromURI(uuUri, context);
            String path_bian = "图片保存至" + path.substring(20, path.length());
            ToastUtil.show("保存成功\n" + path_bian);
        } else {
            ToastUtil.show("没有开启读写权限");

        }

    }

    //通过本地路经 content://得到URI路径
    public static String getRealPathFromURI(Uri contentUri, Context context) {
        Cursor cursor = null;
        String locationPath = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            locationPath = cursor.getString(column_index);
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return locationPath;
    }

    public static boolean checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(getInstance().getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 判断当前日期是否在给定的两个日期之间
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     */
    public static boolean compareDateState(String beginDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c.setTimeInMillis(System.currentTimeMillis());
            c1.setTime(df.parse(beginDate));
            c2.setTime(df.parse(endDate));
        } catch (java.text.ParseException e) {
            return false;
        }
        int resultBegin = c.compareTo(c1);
        int resultEnd = c.compareTo(c2);
        return resultBegin > 0 && resultEnd < 0;
    }

    //获取设备唯一ID号
    //需加入<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    //和<uses-permission android:name="android.permission.BLUETOOTH"/>权限
    public static String getDeviceUniqueId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        String m_szImei = TelephonyMgr.getDeviceId();

        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = m_BluetoothAdapter == null ? "" : m_BluetoothAdapter.getAddress();

        /**
         * 综上所述，我们一共有五种方式取得设备的唯一标识。它们中的一些可能会返回null，或者由于硬件缺失、权限问题等获取失败。
         但你总能获得至少一个能用。所以，最好的方法就是通过拼接，或者拼接后的计算出的MD5值来产生一个结果。
         */
        String uniqueId = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        return HashCoderUtil.md5Crypt(uniqueId.getBytes());
    }

    /**
     * description 增加view的触摸范围
     */
    public static void expandViewTouchDelegate(final View view, final int top, final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//
//            Display display =getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            Point realSize = new Point();
//            display.getSize(size);
//            display.getRealSize(realSize);
//            boolean  result  = realSize.y!=size.y;
//            return realSize.y!=size.y;
//        }else {
//            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
//            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//            if(menu || back) {
//                return false;
//            }else {
//                return true;
//            }
//        }
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
//    public boolean checkDeviceHasNavigationBar(WindowManager windowManager) {
//        DisplayMetrics dm = new DisplayMetrics();
//        Display display = windowManager.getDefaultDisplay();
//        display.getMetrics(dm);
//        int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;
//        return (screenRealHeight - screenWidth) > 0;//screenRealHeight上面方法中有计算
//    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置占位符
     *
     * @param tv_statusBarView
     * @param height
     */
    public static void getStatusBarView(TextView tv_statusBarView, int height) {
        ViewGroup.LayoutParams layoutParams = tv_statusBarView.getLayoutParams();
        layoutParams.height = height;
//        LogUtil.e("obtainData" + height);
        tv_statusBarView.setLayoutParams(layoutParams);
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取通知栏权限是否开启
     */
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 隐藏状态栏
     */
    public static void getYinCang(Window window) {
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    /**
     *  判断APP是否在前台
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
				/*
				BACKGROUND=400 EMPTY=500 FOREGROUND=100
				GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
    public static String getDeviceInfo(Context context) {
        try {
            JSONObject json = new JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (TextUtils.isEmpty(mac)){
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;

    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

}
