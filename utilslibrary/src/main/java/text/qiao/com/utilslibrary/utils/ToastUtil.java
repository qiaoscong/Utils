package text.qiao.com.utilslibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by Administrator on 2018/2/5.
 * 自定义toast
 */

public class ToastUtil {
    private static Toast mToast;
    private static Context mContext = null;


    public static void init(Context context) {
        mContext = context;
//
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 2000ms
     *
     * @param charSequence 字符串
     */
    public static void showShort(CharSequence charSequence) {
        show(charSequence, Toast.LENGTH_SHORT);
    }

    /**
     * /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 3500ms
     *
     * @param charSequence 字符串
     */
    public static void showLong(CharSequence charSequence) {
        show(charSequence, Toast.LENGTH_LONG);
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 2000ms
     *
     * @param resId String资源ID
     */
    public static void showShort(int resId) {
        show(mContext.getText(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 3500ms
     *
     * @param resId String资源ID
     */
    public static void showLong(int resId) {
        show(mContext.getText(resId), Toast.LENGTH_LONG);
    }

    public static void show(CharSequence text, int continuous) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, continuous);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
            mToast.setDuration(continuous);
        }
        mToast.show();
    }

    /**
     * 取消Toast显示
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
