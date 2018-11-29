package text.qiao.com.utilslibrary.constants;


public enum DimenTypes {

    //适配Android 3.2以上   大部分手机的sw值集中在  300-460之间
    DP_sw__320(320);
//    DP_sw__360(360),
//    DP_sw__380(380),
//    DP_sw__480(480),  // values-sw300
//    DP_sw__720(720),
//
//    DP_sw__768(768),
//    DP_sw__1080(1080),
//    DP_sw__1440(1440);

    // 想生成多少自己以此类推


    /**
     * 屏幕最小宽度
     */
    private int swWidthDp;


    DimenTypes(int swWidthDp) {

        this.swWidthDp = swWidthDp;
    }

    public int getSwWidthDp() {
        return swWidthDp;
    }

    public void setSwWidthDp(int swWidthDp) {
        this.swWidthDp = swWidthDp;
    }

}
