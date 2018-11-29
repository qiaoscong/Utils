package text.qiao.com.utilslibrary.generator;


import text.qiao.com.utilslibrary.constants.DimenTypes;
import text.qiao.com.utilslibrary.utils.MakeUtils;

public class DimenGenerator {

    /**
     * 设计稿尺寸(根据自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 384;

    /**
     * 设计稿高度  没用到
     */
    private static final int DESIGN_HEIGHT = 667;

    public static void main(String[] args) {

        DimenTypes[] values = DimenTypes.values();
        for (DimenTypes value : values) {
            MakeUtils.makeAll(DESIGN_WIDTH, value, "./utilslibrary/src/main/res/values/");
        }

    }

}
