package com.darkprograms.spanishconj.document;

import com.darkprograms.spanishconj.connection.SpanishConnection;
import com.darkprograms.spanishconj.connection.WordInfo;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 6/6/12
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class WriteToDocument {

    private String verbList;
    private String outputDoc;

    private String getVerbList() {
        return verbList;
    }

    private void setVerbList(String verbList) {
        this.verbList = verbList;
    }

    private String getOutputDoc() {
        return outputDoc;
    }

    private void setOutputDoc(String outputDoc) {
        this.outputDoc = outputDoc;
    }

    public void start(String verbList, String outputDoc) {
        setVerbList(verbList);
        setOutputDoc(outputDoc);
        processVerbs();
    }

    public void processVerbs() {
        XWPFDocument document = new XWPFDocument();

        SpanishConnection connection = new SpanishConnection();
        WordInfo info;
        int numberOfVerbs = 0;
        int completed = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(verbList)));

            while (bufferedReader.ready()) {
                bufferedReader.readLine();
                numberOfVerbs++;
            }

            bufferedReader = new BufferedReader(new FileReader(new File(verbList)));

            while (bufferedReader.ready()) {
                String verb = bufferedReader.readLine().trim();
                info = connection.openConnection(verb);

                System.out.print("\rCurrent Verb: " + verb + "  " + completed + "/" + numberOfVerbs);
                System.out.flush();

                String gerund = info.getGerund();
                String[] present = info.getPresentConjugations();
                String[] preterit = info.getPreteritConjugations();


                XWPFParagraph paragraphOne = document.createParagraph();
                XWPFRun paragraphOneRunOne = paragraphOne.createRun();
                paragraphOneRunOne.setText(verb + " = " + info.getDefinition());
                paragraphOneRunOne.setFontSize(16);


                XWPFParagraph paragraphTwo = document.createParagraph();
                XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();

                paragraphTwoRunOne.setText("Gerund: ");
                paragraphTwoRunOne.setFontSize(14);
                paragraphTwoRunOne.setBold(true);

                XWPFRun p2r2 = paragraphTwo.createRun();


                if (gerund.contains("<regular>")) {
                        p2r2.setText("All Regular");
                } else if(gerund.contains("<nodata>")){
                    p2r2.setText("No Data");
            }else{
                    p2r2.setText(gerund);
                    p2r2.setBold(true);
                    p2r2.setItalic(true);
                }

                XWPFParagraph p3 = document.createParagraph();
                XWPFRun p3r1 = p3.createRun();

                p3r1.setText("Present: ");
                p3r1.setBold(true);
                p3r1.setFontSize(14);
                p3r1.addBreak();

                XWPFRun p3r2 = p3.createRun();
                if (present.length == 1) {
                    if(present[0].contains("<regular>")){
                        p3r2.setText("All Regular");
                    }else{
                        p3r2.setText("No Data");
                    }
                } else {
                    XWPFRun p3r3;
                    int index = 0;
                    int readFormat[] = {0, 3, 1, 4, 2, 5};
                    do {
                        String conj = present[readFormat[index]];
                        if (conj.contains("<i>")) {
                            p3r3 = p3.createRun();
                            p3r3.setText(conj.replace("<i>", "").trim());
                            p3r3.setBold(true);
                            p3r3.setItalic(true);
                        } else {
                            p3r3 = p3.createRun();
                            p3r3.setText(conj);
                        }
                        if (index == 0 || index == 2 || index == 4) {
                            p3r3 = p3.createRun();
                            p3r3.setText("            ");
                        } else {
                            p3r3.addBreak();
                        }

                        index++;
                    } while (index != 6);
                }

                XWPFParagraph p4 = document.createParagraph();
                XWPFRun p4r1 = p4.createRun();

                p4r1.setText("Preterit: ");
                p4r1.setBold(true);
                p4r1.setFontSize(14);
                p4r1.addBreak();

                XWPFRun p4r2 = p4.createRun();
                if (preterit.length == 1) {
                    if(preterit[0].contains("<regular>")){
                    p4r2.setText("All Regular");
                    }else{
                        p4r2.setText("No Data");
                    }
                } else {
                    XWPFRun p4r3;
                    int index = 0;
                    int readFormat[] = {0, 3, 1, 4, 2, 5};
                    do {
                        String conj = preterit[readFormat[index]];
                        if (conj.contains("<i>")) {
                            p4r3 = p4.createRun();
                            p4r3.setText(conj.replace("<i>", "").trim());
                            p4r3.setBold(true);
                            p4r3.setItalic(true);
                        } else {
                            p4r3 = p4.createRun();
                            p4r3.setText(conj);
                        }
                        if (index == 0 || index == 2 || index == 4) {
                            p4r3 = p4.createRun();
                            p4r3.setText("            ");
                        } else {
                            p4r3.addBreak();
                        }

                        index++;
                    } while (index != 6);
                }
                document.createParagraph().setBorderBottom(Borders.SINGLE);

                completed++;
            }


            FileOutputStream outStream = new FileOutputStream(new File(getOutputDoc()));
            document.write(outStream);
            outStream.close();
            System.out.println();
            System.out.println("Complete!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
