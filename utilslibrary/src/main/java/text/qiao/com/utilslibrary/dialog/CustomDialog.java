package text.qiao.com.utilslibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import text.qiao.com.utilslibrary.R;


/**
 *
 * @author 乔少聪
 * @time 2018/11/30 9:13
 * @describe 公用dialog
*/
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int layout) {
        this(context, layout, R.style.DialogTheme);
    }

    public CustomDialog(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //params.alpha = 0.6f; //背景透明
        window.setAttributes(params);
    }

}