package com.darkprograms.spanishconj.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 6/6/12
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordInfo {

    private final static String SPANISH_URL = "http://www.spanishdict.com/m";

    URL spanishURL;
    URLConnection spanishURLConnection;

    private String spanishWord;

    private String getSpanishWord() {
        return spanishWord;
    }

    private void setSpanishWord(String spanishWord) {
        this.spanishWord = spanishWord;
    }

    public WordInfo(String spanishWord) {
        setSpanishWord(spanishWord);
    }

    private void openCon() {
        try {
            spanishURL = new URL(SPANISH_URL);
            spanishURLConnection = spanishURL.openConnection();
            spanishURLConnection.setDoOutput(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String getDefinition() {
        try {
            openCon();

            DataOutputStream outputStream = new DataOutputStream(spanishURLConnection.getOutputStream());
            outputStream.writeBytes("word=" + URLEncoder.encode(spanishWord, "UTF-8"));
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spanishURLConnection.getInputStream()));
            String data;
            do {
                data = bufferedReader.readLine();
            } while (!data.contains("<td><h3>=</h3></td>"));
            data = bufferedReader.readLine();
            bufferedReader.close();
            return data.replace("</h3>", "").replace("<h3>", "").replace("<td>", "").replace("</td>", "").trim();

        } catch (Exception ex) {

        }

        try{
            openCon();

            DataOutputStream outputStream = new DataOutputStream(spanishURLConnection.getOutputStream());
            outputStream.writeBytes("word=" + URLEncoder.encode(spanishWord, "UTF-8"));
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spanishURLConnection.getInputStream()));
            String data;
            do {
                data = bufferedReader.readLine();
            } while (!data.contains("<span class='def'>"));
            bufferedReader.close();
            if(data.contains("<p><div class=\"LV0\">")){
                return data.split("</span>")[4].replace("<span class='def'>", "").replace("<I>", "").replace("</I>", "").trim();
            }else{

            return data.replace("<span class='def'>", "").replace("</span>", "").replace("<em>", "").trim();
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String[] getPresentConjugations() {
        try {
            openCon();
            String conjArray[] = new String[6];

            DataOutputStream outputStream = new DataOutputStream(spanishURLConnection.getOutputStream());
            outputStream.writeBytes("word=" + URLEncoder.encode(spanishWord, "UTF-8"));
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spanishURLConnection.getInputStream()));
            String data;
            do {
                data = bufferedReader.readLine();
            } while (!data.contains("<ul><span class=\"pos\">Presente</span>"));

            int readConj = 0;
            int irregulars = 0;

            while (readConj != 6) {
                bufferedReader.readLine();
                bufferedReader.readLine();
                //Skip two lines

                data = bufferedReader.readLine();
                if (data.contains("<em class=\"conj\">")) {
                    conjArray[readConj] = "<i>" + data.replace("<em class=\"conj\">", "").replace("</em> ", "").replace("</li>", "").trim();
                    irregulars++;
                } else {
                    conjArray[readConj] = data.replace("</li>", "").trim();
                }
                readConj++;
            }
            if (irregulars == 0) {
                String[] regular = {"<regular>"};
                return regular;
            }

            bufferedReader.close();

            return conjArray;
        } catch (Exception ex) {

        }
        String[] noData = {"<nodata>"};
        return noData;
    }

    public String[] getPreteritConjugations() {
        try {
            openCon();
            String conjArray[] = new String[6];

            DataOutputStream outputStream = new DataOutputStream(spanishURLConnection.getOutputStream());
            outputStream.writeBytes("word=" + URLEncoder.encode(spanishWord, "UTF-8"));
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spanishURLConnection.getInputStream()));
            String data;
            do {
                data = bufferedReader.readLine();
            } while (!data.contains("<ul><span class=\"pos\">Pretérito</span>"));

            int readConj = 0;
            int irregulars = 0;

            while (readConj != 6) {
                bufferedReader.readLine();
                bufferedReader.readLine();
                //Skip two lines

                data = bufferedReader.readLine();
                if (data.contains("<em class=\"conj\">")) {
                    conjArray[readConj] = "<i>" + data.replace("<em class=\"conj\">", "").replace("</em> ", "").replace("</li>", "").trim();
                    irregulars++;
                } else {
                    conjArray[readConj] = data.replace("</li>", "").trim();
                }
                readConj++;
            }
            if (irregulars == 0) {
                String[] regular = {"<regular>"};
                return regular;
            }

            bufferedReader.close();

            return conjArray;
        } catch (Exception ex) {

        }
        String[] noData = {"<nodata>"};
        return noData;
    }

    public String getGerund() {
        try {
            openCon();

            DataOutputStream outputStream = new DataOutputStream(spanishURLConnection.getOutputStream());
            outputStream.writeBytes("word=" + URLEncoder.encode(spanishWord, "UTF-8"));
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spanishURLConnection.getInputStream()));
            String data;
            do {
                data = bufferedReader.readLine();
            } while (!data.contains("<p><span class=\"pos\">Gerund</span>:"));
            bufferedReader.close();

            String parsedData = data.replace("<p><span class=\"pos\">Gerund</span>:", "");

            if (parsedData.contains("<em class=\"conj\">")) {
                return parsedData.replace("<em class=\"conj\">", "").replace("</em>", "").replace("</p>", "").trim();
            } else {
                return "<regular>";
            }


        } catch (Exception ex) {

        }
        return "<nodata>";
    }

}
