package pizza.hawaii;

import pizza.hawaii.*;
import org.tartarus.snowball.ext.englishStemmer;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class pizza {
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> word_count = new HashMap<>();
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int line_count = 0;

        //把東西放進Map :D *一個叫做只放入字根的功能順便做了進來
        englishStemmer stemmer = new englishStemmer();
        while (bufferedReader.ready()) {
            line_count++;

            //上帝為你關一扇Door，會順便幫你關Windows
            for (String str : pineapple.magic(bufferedReader.readLine())) {

                stemmer.setCurrent(str);
                if (stemmer.stem()) {
                    str = stemmer.getCurrent();
                }

                if (word_count.containsKey(str)) {
                    int tmp = word_count.get(str);
                    word_count.put(str, ++tmp);
                } else {
                    if (str.length() > 1 && !str.matches("-?\\d+")) word_count.put(str, 1);
                }
            }
        }

        //幹掉清單裡的key
        if (args.length > 2) {
            FileReader read_rm_list = new FileReader(args[2]);
            BufferedReader rm_list = new BufferedReader(read_rm_list);
            String[] list = rm_list.readLine().split(",");

            for (String str: list) {
                word_count.remove(str);
            }
        }

        //整理Map
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        word_count.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        //寫檔案囉
        FileWriter writer = new FileWriter(args[1]);
        BufferedWriter buf_writer = new BufferedWriter(writer);

        for (Object key : sortedMap.keySet()
        ) {
            //System.out.printf("%s:%s\n", key, sortedMap.get(key));
            buf_writer.write(key + "\t" + sortedMap.get(key) + "\n");
        }
        buf_writer.flush();
        writer.close();

        //總共讀了幾行
        System.out.printf("總共讀取:%d行", line_count);
    }
}
