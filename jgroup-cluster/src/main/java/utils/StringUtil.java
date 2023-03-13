package utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    protected static Log log = LogFactory.getLog(StringUtil.class);
    public final static String TAG_BR = "<br/>";
    public final static String CRLF_REG_EXP = "(\r\n|\r|\n|\n\r)";
    public final static Pattern CRLF_REG_EXP_PATTERN = Pattern.compile(CRLF_REG_EXP);

    // @formatter:off
    final static String[] hex = {
            "%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
            "%08", "%09", "%0a", "%0b", "%0c", "%0d", "%0e", "%0f",
            "%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
            "%18", "%19", "%1a", "%1b", "%1c", "%1d", "%1e", "%1f",
            "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
            "%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f",
            "%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
            "%38", "%39", "%3a", "%3b", "%3c", "%3d", "%3e", "%3f",
            "%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
            "%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e", "%4f",
            "%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
            "%58", "%59", "%5a", "%5b", "%5c", "%5d", "%5e", "%5f",
            "%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
            "%68", "%69", "%6a", "%6b", "%6c", "%6d", "%6e", "%6f",
            "%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
            "%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f",
            "%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
            "%88", "%89", "%8a", "%8b", "%8c", "%8d", "%8e", "%8f",
            "%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
            "%98", "%99", "%9a", "%9b", "%9c", "%9d", "%9e", "%9f",
            "%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7",
            "%a8", "%a9", "%aa", "%ab", "%ac", "%ad", "%ae", "%af",
            "%b0", "%b1", "%b2", "%b3", "%b4", "%b5", "%b6", "%b7",
            "%b8", "%b9", "%ba", "%bb", "%bc", "%bd", "%be", "%bf",
            "%c0", "%c1", "%c2", "%c3", "%c4", "%c5", "%c6", "%c7",
            "%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf",
            "%d0", "%d1", "%d2", "%d3", "%d4", "%d5", "%d6", "%d7",
            "%d8", "%d9", "%da", "%db", "%dc", "%dd", "%de", "%df",
            "%e0", "%e1", "%e2", "%e3", "%e4", "%e5", "%e6", "%e7",
            "%e8", "%e9", "%ea", "%eb", "%ec", "%ed", "%ee", "%ef",
            "%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7",
            "%f8", "%f9", "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"
    };
    // @formatter:on

    /**
     * Constructs a new String by decoding the specified input string using the specified charset.
     * The length of the new String is a function of the charset, and hence may not be equal to the
     * length of the input string.
     *
     * @param in
     * @param charsetName
     * @return
     */
    public static String encode(String in, String charsetName) {
        if (in != null && charsetName != null) {
            try {
                return new String(in.getBytes(), charsetName);
            } catch (UnsupportedEncodingException e) {
                log.error(e, e);
            }
        }

        return in;
    }

    /**
     * Checks if the String contains only unicode letters.
     *
     * @param s
     * @return
     */
    public static boolean isAlpha(String s) {
        return StringUtils.isAlpha(s);
    }

    /**
     * Checks if the String contains only unicode letters or digits.
     *
     * @param s
     * @return
     */
    public static boolean isAlphanumeric(String s) {
        return StringUtils.isAlphanumeric(s);
    }

    /**
     * Checks if the String contains only unicode digits.
     *
     * @param s
     * @return
     */
    public static boolean isNumeric(String s) {
        return StringUtils.isNumeric(s);
    }

    /**
     * Checks if a String is whitespace, empty ("") or null.
     *
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        return StringUtils.isBlank(s);
    }

    /**
     * Checks if a String is empty ("") or null.
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return StringUtils.isEmpty(s);
    }

    /**
     * Rimuove i \n, \r e spazi dall'inizio e dalla fine della stringa.
     *
     * @param s
     * @return
     */
    public static String removeWhitespaces(String s) {
        return StringUtils.stripToEmpty(StringUtils.trimToEmpty(s));
    }

    /**
     * Returns: left padded String or original String if no padding is necessary, null if null String
     * input
     *
     * @param str
     *           - the String to pad out, may be null
     * @param size
     *           size - the size to pad to
     * @param padChar
     *           padChar - the character to pad with
     * @return
     */
    public static String leftPad(String str, int size, char padChar) {
        return StringUtils.leftPad(str, size, padChar);
    }

    /**
     * Returns: left padded String or original String if no padding is necessary, null if null String
     * input
     *
     * @param str
     *           - the String to pad out, may be null
     * @param size
     *           size - the size to pad to
     * @return
     */
    public static String leftPad(String str, int size) {
        return StringUtils.leftPad(str, size);
    }

    /**
     * Escapes the characters in a String using HTML entities. For example: "bread" & "butter"
     * becomes: &quot;bread&quot; &amp; &quot;butter&quot;. Supports all known HTML 4.0 entities,
     * including funky accents.
     *
     * @param s
     * @return
     */
    public static String escapeHtml(String s) {
        String res = StringEscapeUtils.escapeHtml4(s);
        return res;
    }

    /**
     * Escapes the characters in a String using XML entities. For example: "bread" & "butter" =>
     * &quot;bread&quot; &amp; &quot;butter&quot;. Supports only the five basic XML entities (gt, lt,
     * quot, amp, apos). Does not support DTDs or external entities.
     *
     * @param s
     * @return
     */
    public static String escapeXml(String s) {
        return StringEscapeUtils.escapeXml11(s);
    }

    /**
     * Escapes the characters in a String using JavaScript String rules. Escapes any values it finds
     * into their JavaScript String form. Deals correctly with quotes and control-chars (tab,
     * backslash, cr, ff, etc.) So a tab becomes the characters '\\' and 't'. The only difference
     * between Java strings and JavaScript strings is that in JavaScript, a single quote must be
     * escaped. Example: input string: He didn't say, "Stop!" output string: He didn\'t say,
     * \"Stop!\"
     *
     * @param s
     * @return
     */
    public static String escapeJavascript(String s) {
        return StringEscapeUtils.escapeEcmaScript(s);
    }

    /**
     * Write a quoted and escaped value to the input String s. Escaped char : " , \ , \b , \f , \n ,
     * \r , \t and ISO control. Example :"\ a b" become "\\ a b"
     *
     * @param s
     * @return
     */
    public static String escapeJsonQuote(String s) {
        return StringEscapeUtils.escapeJson(s);
    }

    /**
     * Execute StringUtility.escapeJsonQuote plus StringEscapeUtils.escapeHtml.
     *
     * @param s
     * @return
     */
    public static String escapeJsonQuoteHTML(String s) {
        String res = StringEscapeUtils.escapeHtml4(s);
        return escapeJsonQuote(res);
    }

    /**
     * Unescapes a string containing entity escapes to a string containing the actual Unicode
     * characters corresponding to the escapes. Supports HTML 4.0 entities. For example, the string
     * "&lt;Fran&ccedil;ais&gt;" will become "<Français>" If an entity is unrecognized, it is left
     * alone, and inserted verbatim into the result string. e.g. "&gt;&zzzz;x" will become
     * ">&zzzz;x".
     *
     * @param s
     * @return
     */
    public static String unescapeHtml(String s) {
        return StringEscapeUtils.unescapeHtml4(s);
    }

    /**
     * Unescapes a string containing XML entity escapes to a string containing the actual Unicode
     * characters corresponding to the escapes. Supports only the five basic XML entities (gt, lt,
     * quot, amp, apos). Does not support DTDs or external entities.
     *
     * @param s
     * @return
     */
    public static String unescapeXml(String s) {
        return StringEscapeUtils.unescapeXml(s);
    }

    /**
     * Unescapes any JavaScript literals found in the String. For example, it will turn a sequence of
     * '\' and 'n' into a newline character, unless the '\' is preceded by another '\'.
     *
     * @param s
     * @return
     */
    public static String unescapeJavascript(String s) {
        return StringEscapeUtils.unescapeEcmaScript(s);
    }

    /**
     * Unescapes any Json literals found in the String. For example, it will turn a sequence of '\'
     * and 'n' into a newline character, unless the '\' is preceded by another '\'.
     *
     * @param s
     * @return
     */
    public static String unescapeJson(String s) {
        return StringEscapeUtils.unescapeJson(s);
    }

    /**
     * A differenza di String.valueOf(), questo metodo ritorna "" e non la stringa "null" nel caso in
     * cui il parametro s sia null.
     *
     * @param s
     * @return
     */
    public static String valueOf(Object s) {
        if (s == null) {
            return "";
        } else {
            return s.toString();
        }
    }

    /**
     * Ritorna l'inversa di una stringa.
     *
     * @see StringBuffer#reverse()
     * @param input
     * @return
     */
    public static String invertString(String input) {
        if (input != null) {
            return new StringBuffer(input).reverse().toString();
        }
        return null;
    }

    public static String getExtension(String input, String delimiter) {
        String extension = "";
        if (input != null) {
            StringTokenizer tok = new StringTokenizer(invertString(input), delimiter != null ? delimiter : ".");
            if (tok.hasMoreTokens()) {
                extension = tok.nextToken();
            }
        } else {
            extension = null;
        }
        return invertString(extension);
    }

    public static String getExtension(String input) {
        return getExtension(input, ".");
    }

    /**
     * Check that the given CharSequence is neither {@code null} nor of length 0. Note: Will return
     * {@code true} for a CharSequence that purely consists of whitespace.
     * <p>
     *
     * <pre class="code">
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str
     *           the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given String is neither {@code null} nor of length 0. Note: Will return
     * {@code true} for a String that purely consists of whitespace.
     *
     * @param str
     *           the String to check (may be {@code null})
     * @return {@code true} if the String is not null and has length
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence has actual text. More specifically, returns {@code true}
     * if the string not {@code null}, its length is greater than 0, and it contains at least one
     * non-whitespace character.
     * <p>
     *
     * <pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str
     *           the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not {@code null}, its length is greater than 0,
     *         and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String has actual text. More specifically, returns {@code true} if the
     * string not {@code null}, its length is greater than 0, and it contains at least one
     * non-whitespace character.
     *
     * @param str
     *           the String to check (may be {@code null})
     * @return {@code true} if the String is not {@code null}, its length is greater than 0, and it
     *         does not contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static String removeHtmlTags(String source) {
        if (source != null) {
            return source.replaceAll("<+[.[^>]]+>", "");
        } else {
            return "";
        }
    }

    public static String truncateHTMLText(Object text, int size) {
        String res = removeHtmlTags(text != null ? text.toString() : null);
        if (res.length() > size) {
            res = res.substring(0, size) + "...";
        }

        return res;
    }

    /**
     * Converte ogni tipo di lettera accentata nella forma senza accento mantenendo il case
     *
     * @param url
     * @param eliminaSingoliApici
     *           true toglie i singoli apici
     * @param eliminaDoppieVirgolette
     *           true toglie le virgolette
     * @return
     */
    public static String normalizeString(String url, boolean eliminaSingoliApici, boolean eliminaDoppieVirgolette) {

        url = url.replace("Ç", "c");
        url = url.replace("ü", "u");
        url = url.replace("é", "e");
        url = url.replace("â", "a");
        url = url.replace("ä", "a");
        url = url.replace("à", "a");
        url = url.replace("å", "a");
        url = url.replace("ç", "c");
        url = url.replace("ê", "e");
        url = url.replace("ë", "e");
        url = url.replace("è", "e");
        url = url.replace("ï", "i");
        url = url.replace("î", "i");
        url = url.replace("ì", "i");
        url = url.replace("Ä", "a");
        url = url.replace("Å", "a");
        url = url.replace("É", "e");
        url = url.replace("ô", "o");
        url = url.replace("ö", "o");
        url = url.replace("ò", "o");
        url = url.replace("û", "u");
        url = url.replace("ù", "u");
        url = url.replace("ÿ", "y");
        url = url.replace("Ö", "o");
        url = url.replace("Ü", "u");
        url = url.replace("á", "a");
        url = url.replace("í", "i");
        url = url.replace("ó", "o");
        url = url.replace("ú", "u");
        url = url.replace("ñ", "n");
        url = url.replace("Ñ", "n");
        url = url.replace("¡", "i");
        url = url.replace("Á", "a");
        url = url.replace("Â", "a");
        url = url.replace("À", "a");
        url = url.replace("ã", "a");
        url = url.replace("Ã", "a");
        url = url.replace("Ê", "e");
        url = url.replace("Ë", "e");
        url = url.replace("È", "e");
        url = url.replace("i", "i");
        url = url.replace("Í", "i");
        url = url.replace("Î", "i");
        url = url.replace("Ï", "i");
        url = url.replace("Ì", "i");
        url = url.replace("Ó", "o");
        url = url.replace("ß", "ss");
        url = url.replace("Ô", "o");
        url = url.replace("Ò", "o");
        url = url.replace("õ", "o");
        url = url.replace("Õ", "o");
        url = url.replace("Ú", "u");
        url = url.replace("Û", "u");
        url = url.replace("Ù", "u");
        url = url.replace("ý", "y");
        url = url.replace("Ý", "y");

        if (eliminaSingoliApici) {
            url = url.replace("\'", "");
        }

        if (eliminaDoppieVirgolette) {
            url = url.replace("\"", "");
        }

        return url;
    }

    /**
     * Costruisce una stringa unendo una collection di stringhe con un delimitatore.
     *
     * @param pColl
     *           collezione, è necessario soltanto che estenda l'interfaccia Iterable
     * @param separator
     *           separatore
     * @return stringa unione
     */
    public static String join(Iterable<? extends Object> pColl, String separator) {
        Iterator<? extends Object> oIter;
        if (pColl == null || !(oIter = pColl.iterator()).hasNext()) {
            return "";
        }
        StringBuilder oBuilder = new StringBuilder(String.valueOf(oIter.next()));
        while (oIter.hasNext()) {
            oBuilder.append(separator).append(oIter.next());
        }
        return oBuilder.toString();
    }

    /**
     * Concatena tutti gli elementi dell'array di input, separati dal separatore indicato.
     *
     * @param input
     * @param separator
     * @return
     */
    public static String join(Object[] input, String separator) {
        String res = null;

        if (input != null) {
            res = StringUtils.join(input, separator);
        }

        return res;
    }

    /**
     * Function for decoding UTF8/URL encoded strings Es: L'url encondata (ASCII) converte il
     * carattere ê in %C3%, questo metodo lo riconverte in UTF8 quindi in ê. Created: 17 April 1997
     * Author: Bert Bos <bert@w3.org> unescape: http://www.w3.org/International/unescape.java
     * Copyright © 1997 World Wide Web Consortium, (Massachusetts Institute of Technology, European
     * Research Consortium for Informatics and Mathematics, Keio University). All Rights Reserved.
     * This work is distributed under the W3C® Software License [1] in the hope that it will be
     * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
     * FITNESS FOR A PARTICULAR PURPOSE. [1]
     * http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
     */
    public static String unescapeURL(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = hb << 4 | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
                sumb = sumb << 6 | b & 0x3f; // Add 6 bits to sumb
                if (--more == 0) {
                    sbuf.append((char) sumb); // Add char to sbuf
                }
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b); // Store in sbuf
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes
            } else /* if ((b & 0xfe) == 0xfc) */{ // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }

    /**
     * Provides a method to encode any string into a URL-safe form. Non-ASCII characters are first
     * encoded as sequences of two or three bytes, using the UTF-8 algorithm, before being encoded as
     * %HH escapes. Rispetto alla versione originaria sono stati considerati safe anche i caratteri /
     * e +, così come fa l'escape() javascript. Created: 17 April 1997 Author: Bert Bos <bert@w3.org>
     * URLUTF8Encoder: http://www.w3.org/International/URLUTF8Encoder.java Copyright © 1997 World
     * Wide Web Consortium, (Massachusetts Institute of Technology, European Research Consortium for
     * Informatics and Mathematics, Keio University). All Rights Reserved. This work is distributed
     * under the W3CÂ® Software License [1] in the hope that it will be useful, but WITHOUT ANY
     * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
     * PURPOSE. [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
     */
    public static String escapeURL(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') {
                // 'A'..'Z'
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {
                // 'a'..'z'
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {
                // '0'..'9'
                sbuf.append((char) ch);
            } else if (ch == '&' || ch == ':' || ch == '?' || ch == '=' || ch == '-' || ch == '_' || ch == '/' || ch == '+' || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
                // unreserved
                sbuf.append((char) ch);
            } else if (ch <= 0x007f) {
                // other ASCII
                sbuf.append(hex[ch]);
            } else if (ch <= 0x07FF) {
                // non-ASCII <= 0x7FF
                sbuf.append(hex[0xc0 | ch >> 6]);
                sbuf.append(hex[0x80 | ch & 0x3F]);
            } else {
                // 0x7FF < ch <= 0xFFFF
                sbuf.append(hex[0xe0 | ch >> 12]);
                sbuf.append(hex[0x80 | ch >> 6 & 0x3F]);
                sbuf.append(hex[0x80 | ch & 0x3F]);
            }
        }
        return sbuf.toString();
    }

    /**
     * @param url
     * @param encoding
     * @return
     */
    public static String encodeUrl(String url) {

        if (url != null) {
            try {
                return escapeHtml(escapeURL(url));
            } catch (Exception e) {
                log.error(e, e);
            }
        }
        return null;
    }

    /**
     * Compares two Strings, returning true if they are equal. nulls are handled without exceptions.
     * Two null references are considered to be equal. The comparison is case sensitive.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        return StringUtils.equals(a, b);
    }

    /**
     * Compares two Strings, returning true if they are equal ignoring the case. nulls are handled
     * without exceptions. Two null references are considered equal. Comparison is case insensitive.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return StringUtils.equalsIgnoreCase(a, b);
    }

    /**
     * Capitalizes a String changing the first letter. No other letters are changed.
     *
     * @param s
     * @return
     */
    public static String capitalize(String s) {
        return s != null ? StringUtils.capitalize(s) : null;
    }

    /**
     * Converts all the whitespace separated words in a String into capitalized words, that is each
     * word is made up of a titlecase character and then a series of lowercase characters. Whitespace
     * is defined by Character.isWhitespace(char). A null input String returns null. Capitalization
     * uses the unicode title case, normally equivalent to upper case.
     *
     * @param text
     * @return
     */
    public static String capitalizeAndToLower(String text) {
        return WordUtils.capitalizeFully(text);
    }

    /**
     * Converte tutti gli \n in BR, utile per le stringhe html
     *
     * @param src
     * @return
     */
    public static String convertAllCRLFtoBR(String src) {
        if (src != null) {
            Matcher matcher = CRLF_REG_EXP_PATTERN.matcher(src);
            return matcher.replaceAll(TAG_BR);
        }
        return null;
    }
}
