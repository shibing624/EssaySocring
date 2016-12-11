package org.xm.essayscoring.standard;

/**
 * @author xuming
 */
public enum EssayStandard {
    ThemeConsistency("主题一致性"),
    Expression("修辞与表达"),
    Positive("格调阳光"),
    UseWord("用词能力"),
    VocabularyNum("词汇量"),
    ContentExtension("内容扩展度"),
    WordRefinement("字词文雅"),
    SubjectExpression("主题表达"),
    ArgumentClear("破题立论"),
    Creative("创意性");


    private EssayStandard(String describe){
        this.describe = describe;
    }
    private final String describe;
    public String getDescribe() {
        return describe;
    }
}
