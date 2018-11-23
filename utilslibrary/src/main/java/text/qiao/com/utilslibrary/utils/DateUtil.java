package text.qiao.com.utilslibrary.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Created by codeest on 16/8/13.
 */

public class DateUtil {

    /**
     * 获取系统时间的10位的时间戳
     *
     * @return
     */
    public static int getCurrentTime() {
        long time = System.currentTimeMillis();
        int time1 = (int) (time / 1000);


        return time1;

    }

    /**
     * 获取现在时间（HH:mm）
     *
     * @return
     */
    public static String getPresentTime() {
        long time = System.currentTimeMillis();
        int targetDay = (int) (time / 86400000L);
        String result = DateUtil.fromatDate(time, "HH:mm");
        return result;
    }
    public static String getHHmmTime(String time){
        long tie=Long.parseLong(time);
        String result = DateUtil.fromatDate(tie*1000, "MM-dd HH:mm");
        return result;
    }



    public static String getShiJianString(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String shijian;

        shijian = df.format(new Date(Long.parseLong(time) * 1000));

        return shijian;
    }

    public static String getShiJianString2(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String shijian = df.format(new Date(Long.parseLong(time) * 1000));
        return shijian;
    }

    public static String getShiJianString1(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm");
        String shijian = df.format(new Date(Long.parseLong(time) * 1000));
        return shijian;
    }









    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }





    /**
     * 系统消息  时间显示
     *
     * @param timeMillis
     * @return
     */
    public static String fromatXiTong(long timeMillis) {
        if (timeMillis == 0L) {
            return "";
        } else {
            String result = null;
            int targetDay = (int) (timeMillis / 86400000L);
            int nowDay = (int) (System.currentTimeMillis() / 86400000L);
            String fordate1 = fromatDate(timeMillis, "yyyy");
            if (TextUtils.equals(DateUtil.fromatDate(System.currentTimeMillis(), "yyyyMMdd"),DateUtil.fromatDate(timeMillis, "yyyyMMdd"))) {
                result = DateUtil.fromatDate(timeMillis, "HH:mm");
            } else {
                if (fordate1.equals(getCurrentYear() + "")) {
                    result = DateUtil.fromatDate(timeMillis, "MM月dd日 HH:mm");
                } else {
                    result = DateUtil.fromatDate(timeMillis, "yyyy年MM月dd日 HH:mm");
                }
            }
            return result;
        }
    }

    private static String fromatDate(long timeMillis, String fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        return sdf.format(new Date(timeMillis));
    }



    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getTomorrowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(df.format(new Date())) + 1);
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getCurrentDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date());
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 判断时间戳是不是当前年
     *
     * @param time
     * @return true是false否
     */
    public static boolean IsThisYear(String time) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String shijian = df.format(new Date(Long.parseLong(time) * 1000));

        if (shijian.equals(getCurrentYear() + "")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTimeStampYear(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String shijian = df.format(new Date(Long.parseLong(time) * 1000));
        return shijian;
    }

    /**
     * 两个时间戳作比较 是否在相同年
     *
     * @param time1
     * @param time2
     * @return true不在false在
     */
    public static boolean IsZaiThisYear(String time1, String time2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String shijian1 = df.format(new Date(Long.parseLong(time1) * 1000));
        String shijian2 = df.format(new Date(Long.parseLong(time2) * 1000));

        if (!shijian1.equals(shijian2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 切割标准时间
     *
     * @param time
     * @return
     */
    @Nullable
    public static String subStandardTime(String time) {
        int idx = time.indexOf(".");
        if (idx > 0) {
            return time.substring(0, idx).replace("T", " ");
        }
        return null;
    }

    /**
     * 将时间戳转化为字符串
     *
     * @param showTime
     * @return
     */
    public static String formatTime2String(long showTime) {
        return formatTime2String(showTime, false);
    }

    public static String formatTime2String(long showTime, boolean haveYear) {
        String str = "";
        long distance = currentTimeMillis() / 1000 - showTime;
        if (distance < 300) {
            str = "刚刚";
        } else if (distance >= 300 && distance < 600) {
            str = "5分钟前";
        } else if (distance >= 600 && distance < 1200) {
            str = "10分钟前";
        } else if (distance >= 1200 && distance < 1800) {
            str = "20分钟前";
        } else if (distance >= 1800 && distance < 2700) {
            str = "半小时前";
        } else if (distance >= 2700) {
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = formatDateTime(sdf.format(date), haveYear);
        }
        return str;
    }

    public static String formatDate2String(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return "未知";
        }
        try {
            long createTime = format.parse(time).getTime() / 1000;
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - createTime - 24 * 3600 > 0) { //超出一天
                return (currentTime - createTime) / (24 * 3600) + "天前";
            } else {
                return (currentTime - createTime) / 3600 + "小时前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    public static String formatDateTime(String time, boolean haveYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return "";
        }
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            if (haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0, index);
            } else {
                int yearIndex = time.indexOf("-") + 1;
                int index = time.indexOf(" ");
                return time.substring(yearIndex, time.length()).substring(0, index);
            }
        }
    }

    public static int[] MEMBER_STAR_DATE = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    public static String[] MEMBER_STAR = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    public static String getXingZuo(String time) {
        long shijian = Long.parseLong(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(shijian * 1000));
        int month = calendar.get(Calendar.MONTH) + 1;// 获取月份
        int day = calendar.get(Calendar.DATE);// 获取日期
        return day < MEMBER_STAR_DATE[month - 1] ? MEMBER_STAR[month - 1] : MEMBER_STAR[month];
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();

            String str = String.valueOf(l);
            re_time = Long.parseLong(str) / 1000 + "";

        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }
}
