package pizza.hawaii;

import org.tartarus.snowball.ext.englishStemmer;

public class test {
    public static void main(String[] args) {
        englishStemmer stemmer = new englishStemmer();
        stemmer.setCurrent("efficiency");
        stemmer.stem();
        System.out.println(stemmer.getCurrent());
    }
}
