package pizza.hawaii;

public class pineapple {
    //把ID跟日期移除只留下標題和內文
    public static String rm_ID_date(String content) {
        String[] str = content.split("##");
        if (str.length > 3) {
            return str[2] + str[3];
        }else if(str.length > 2){
            return str[2];
        }
        return "";
    }

    //把內文拆成一堆單字(移除符號)
    public static String[] segment(String content) {
        String[] str = content
                .toLowerCase()
                .replaceAll("[\\W]", " ")
                .split("\\s++");
        return str;
    }

    //合再一起 :white_check_mark:
    public static String[] magic(String content) {
        return segment(rm_ID_date(content));
    }
}
