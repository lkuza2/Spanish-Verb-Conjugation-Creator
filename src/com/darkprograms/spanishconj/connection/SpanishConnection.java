package com.darkprograms.spanishconj.connection;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 6/6/12
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpanishConnection {


    public WordInfo openConnection(String spanishWord) {
        return new WordInfo(spanishWord);
    }

}
