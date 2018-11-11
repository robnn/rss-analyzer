package hu.robnn.rss_analyzer.http;

import hu.robnn.rss_analyzer.model.UrlHolder;
import org.springframework.stereotype.Component;

//TODO amig nincs más lehetőség addig beégetett oldalt adunk vissza egy dummy implementációban
//TODO Ha elkészül a tényleges implementáció, ezt az osztályt töröljük
@Component
public class HttpClientDummyImpl implements HttpClient {
    //TODO dummy oldal:
    private static final String dummyWebpage = "<html>" +
            "<body>" +
            " <div class=\"bom_container\">\n" +
            "                <p id=\"p4524780\" class=\"pp-post\">\n" +
            "                    <a href=\"https://dex.hu/x.php?id=inxcl_bom&amp;url=https%3A%2F%2Findex.hu%2Fmindekozben%2Fposzt%2F2018%2F11%2F07%2Folyan_monologot_vagott_le_az_ujpesti_csatar_hogy_attol_barmelyik_pszeudo-intellektualis_pszichologushallgato_elalelna%2F\">\n" +
            "                        <span class=\"szoveg\">Olyan monológot vágott le az újpesti csatár, hogy attól bármelyik pszeudo-intellektuális pszichológushallgató elalélna</span>\n" +
            "                    </a>\n" +
            "                </p>\n" +
            "                <p id=\"p4524654\" class=\"pp-post\">\n" +
            "                    <a href=\"https://dex.hu/x.php?id=inxcl_bom&amp;url=https%3A%2F%2Findex.hu%2Fmindekozben%2Fposzt%2F2018%2F11%2F07%2Fevekig_harcolt_hogy_ne_mutassak_be_a_rola_keszult_filmet_most_hogy_meghalt_bemutatjak%2F\">\n" +
            "                        <span class=\"szoveg\">Évekig harcolt, hogy ne mutassák be a róla készült filmet, most, hogy meghalt, bemutatják</span>\n" +
            "                    </a>\n" +
            "                </p>\n" +
            "                <p id=\"p4524088\" class=\"pp-post\">\n" +
            "                    <a href=\"https://dex.hu/x.php?id=inxcl_bom&amp;url=https%3A%2F%2Findex.hu%2Fmindekozben%2Fposzt%2F2018%2F11%2F06%2Fa_legnagyobb_magyar_festo%2F\">\n" +
            "                        <span class=\"szoveg\">„Nem kétséges: ... a legnagyobb magyar festő” - mondták a művészről, akinek most hatalmas kiállítása nyílik Szentendrén</span>\n" +
            "                    </a>\n" +
            "                </p>\n" +
            "            </div>" +
            "</body>\n" +
            "</html>";

    @Override
    public String getWebPageAsString(UrlHolder urlHolder) {
        return dummyWebpage;
    }
}
