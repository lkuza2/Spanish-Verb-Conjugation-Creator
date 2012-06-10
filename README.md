Spanish-Verb-Conjugation-Creator
================================

A Spanish verb conjugator written in Java that automatically conjugates verbs and writes them to a Microsoft Word document.
All interation is command line based, there is no GUI

The conjugator pulls the following forms/information:
* Definition
* Gerund
* Present Conjugation
* Preterit Conjugation

The input verb list must have one verb per line.

Data is received from spanishdict.com with no API. Screen scraping methods are used.

This program uses the following APIs:

* Apache POI 3
* Apache XMLBeans
* Apache DOM4J
