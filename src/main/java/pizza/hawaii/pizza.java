package pizza.hawaii;

import org.tartarus.snowball.ext.englishStemmer;

import java.io.*;
import java.util.*;

public class pizza {
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> word_count = new HashMap<>();

        //把東西放進Map :D *一個叫做只放入字根的功能順便做了進來
        File file = new File(args[0]);
        List<String> buffered_content = new ArrayList<String>();
        englishStemmer stemmer = new englishStemmer();
        if (file.isDirectory()) {
            for (File fileEntry: Objects.requireNonNull(file.listFiles())) {
                if (!fileEntry.isDirectory()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileEntry.getPath()));
                    while(bufferedReader.ready()) buffered_content.add(bufferedReader.readLine());
                }
            }
        }else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getPath()));
            while (bufferedReader.ready()) buffered_content.add(bufferedReader.readLine());
        }

        //上帝為你關一扇Door，會順便幫你關Windows
        for (String str: buffered_content) {
            for (String buf: pineapple.magic(str)) {
                stemmer.setCurrent(buf);
                if (stemmer.stem()) {
                    buf = stemmer.getCurrent();
                }

                if (word_count.containsKey(buf)) {
                    int tmp = word_count.get(buf);
                    word_count.put(buf, ++tmp);
                } else {
                    if (buf.length() > 1 && !buf.matches("-?\\d+")) word_count.put(buf, 1);
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
        System.out.printf("總共讀取:%d行", buffered_content.size());
    }
}
