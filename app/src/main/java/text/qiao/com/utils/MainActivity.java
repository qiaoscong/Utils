package text.qiao.com.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import text.qiao.com.utilslibrary.dialog.CustomDialog;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_text)
    Button mainText;
    /**
     * 公用弹出类
     */

    private CustomDialog mCustomDialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context=MainActivity.this;
        initDialog();
    }

    private void  initDialog(){
        mCustomDialog = new CustomDialog(context, R.layout.dialog_layout);
        //空白区域是否可点击
        mCustomDialog.setCanceledOnTouchOutside(true);
    }
    /**
     *  模拟公用弹出类
     */
    @OnClick(R.id.main_text)
    public void onViewClicked() {
        if (mCustomDialog != null) {
            mCustomDialog.show();
        }
    }
}
