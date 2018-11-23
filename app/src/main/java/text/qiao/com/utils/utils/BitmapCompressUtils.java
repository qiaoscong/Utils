package text.qiao.com.utils.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapCompressUtils {

    private static final String TAG = "BitmapCompressUtils";

    /**
     * 根据分辨率压缩
     *
     * @param srcPath   图片路径
     * @param ImageSize 图片大小 单位kb
     * @return
     */
    public static boolean compressBitmap(String srcPath, int ImageSize, String savePath) {
        int subtract;
        Log.i(TAG, "图片处理开始..");
        Bitmap bitmap = compressByResolution(srcPath, 1024, 720); //分辨率压缩
        if (bitmap == null) {
            Log.i(TAG, "bitmap 为空");
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.i(TAG, "图片分辨率压缩后：" + baos.toByteArray().length / 1024 + "KB");

         int linshi=ImageSize * 1024;
        while (baos.toByteArray().length >ImageSize * 1024 ) {  //循环判断如果压缩后图片是否大于ImageSize kb,大于继续压缩
            subtract = setSubstractSize(baos.toByteArray().length / 1024);
            baos.reset();//重置baos即清空baos
            options -= subtract;//每次都减少10
//            if(options<=20){
//                linshi=baos.toByteArray().length;
//            }
//            if(options>0){
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                Log.i(TAG, "图片压缩后：" + baos.toByteArray().length / 1024 + "KB");
//            }

        }
        Log.i(TAG, "图片处理完成!" + baos.toByteArray().length / 1024 + "KB");
        try {
            FileOutputStream fos = new FileOutputStream(new File(savePath));//将压缩后的图片保存的本地上指定路径中
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap != null) {
            bitmap.recycle();
        }

        return true;  //压缩成功返回ture
    }


    /**
     * 转换成JPG格式图片 并将原照片删除
     *
     * @param pngFilePath png或者bmp照片
     * @param jpgFilePath jpg照片
     */
    public  static void convertToJpg(String pngFilePath, String jpgFilePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(jpgFilePath));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
            }
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
        //删除非JPG照片
        if (!pngFilePath.equals(jpgFilePath)) {
            File oldImg = new File(pngFilePath);
            oldImg.delete();
        }
    }
    /**
     * 根据图片的大小设置压缩的比例，提高速度
     *
     * @param imageMB
     * @return
     */
    private static int setSubstractSize(int imageMB) {

        if (imageMB > 1000) {
            return 60;
        } else if (imageMB > 750) {
            return 40;
        } else if (imageMB > 500) {
            return 20;
        } else {
            return 10;
        }

    }

// 设置为每次减少10时 每次压缩的结果
//	3947 kb
//	1233 kb
//	1013 kb
//	681 kb
//	562 kb
//	498 kb
//	405 kb
//	340 kb
//	263 kb
//	263 kb


    /**
     * 根据分辨率压缩图片比例
     *
     * @param imgPath
     * @param w
     * @param h
     * @return
     */
    private static Bitmap compressByResolution(String imgPath, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, opts);

        int width = opts.outWidth;
        int height = opts.outHeight;
        int widthScale = width / w;
        int heightScale = height / h;

        int scale;
        if (widthScale < heightScale) {  //保留压缩比例小的
            scale = widthScale;
        } else {
            scale = heightScale;
        }

        if (scale < 1) {
            scale = 1;
        }
        Log.i(TAG,"图片分辨率压缩比例：" + scale);

        opts.inSampleSize = scale;

        opts.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, opts);

        return bitmap;
    }


    /**
     * 获取当前页面的截图 用于分享时上传
     *
     * @return
     */
    public static String getActivityBitmap(Activity act) {

        // 获取windows中最顶层的view
        View view = act.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = act.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = null;
        if (heights > view.getDrawingCache().getHeight()) {
            bmp = Bitmap.createBitmap(view.getDrawingCache());
        } else {
            bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                    statusBarHeights, widths, heights - statusBarHeights);
        }
        // 销毁缓存信息
        view.destroyDrawingCache();

        // 将图片保存到sd卡中
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = act.getExternalCacheDir();
            // 判断文件夹是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir, "sharesdkImage");
            // 判断文件是否存在，不存在则创建
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = null;

                fos = new FileOutputStream(file);
                if (fos != null) {
                    // 第一参数是图片格式，第二个是图片质量，第三个是输出流
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    // 用完关闭
                    fos.flush();
                    fos.close();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (file.exists() && file.length() > 1) {
                return file.toString();
            }
        }

        return "";

    }

}
