package org.xm.essayscoring.tokenizer;

import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuming
 */
public class Tokenizer {

    public static List<Word> segment(String text) {
        List<Word> results = new ArrayList<>();
        //Xmnlp
        List<Term> termList = Xmnlp.segment(text);
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.word, term.getNature().name()))
                .collect(Collectors.toList())
        );
        return results;
    }
    public static List<Word> notionalSegment(String text) {
        List<Word> results = new ArrayList<>();
        //Xmnlp
        List<Term> termList = NotionalTokenizer.segment(text);
        results.addAll(termList
                .stream()
                .map(term -> new Word(term.word, term.getNature().name()))
                .collect(Collectors.toList())
        );
        return results;
    }


    public static List<String> extractPhrase(String text,int size){
        return Xmnlp.extractPhrase(text,size);
    }

}
