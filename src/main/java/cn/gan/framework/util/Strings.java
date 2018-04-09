package cn.gan.framework.util;

public class Strings {

    /**
     * 字符串左补全。
     * @param beCom
     *          被补全的字符串。
     * @param fill
     *          填充字符
     * @param digits
     *          规定填充后的位数。
     * @return
     */
    public static String leftComplement(String beCom, char fill, int digits){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<(digits-beCom.length()); i++){
            sb.append(fill);
        }
        sb.append(beCom);
        return sb.toString();
    }
}
