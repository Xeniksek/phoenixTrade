package net.xeniks.trade.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.xeniks.trade.utils.ChatHelper;

import java.util.List;

public class YAMLConfiguration extends OkaeriConfig {
    @Getter public String playerNotFoundMessage = "&4Błąd! &cPodany gracz jest OFFLINE!";
    @Getter public String onlyPlayerMessage = "&4Bład! &cKomenda dostępna tylko dla gracza!";
    @Getter public String playerTradeRequestMessage = " &2✔ &7Zaprosiłeś gracza &f{TARGET} &7do wspólnej &awymiany&7!";
    @Getter public String targetTradeRequestMessage = " &2✔ &7Gracz &b{SENDER} &7zaprosił &eCię &7do wspólnej &fwymiany&7!";
    @Getter public String alreadyRequestedTradeMessage = "&4Błąd! &cWysłałeś już zaproszenie do tego gracza!";
    @Getter public String playerSameNameAsTarget = "&4Błąd! &cNie możesz wpisać tutaj swojego nicku!";
    @Getter public String tradeNotFound = "&4Błąd! &cNie masz oferty wymiany od tego gracza!";
    @Getter public String playerTradeDeclineMessage = " &2✔ &7Anulowałeś &azaproszenie &7do wymiany od gracza &f{TARGET}&7!";
    @Getter public String targetTradeDeclineMessage = " &2✔ &7Gracz &d{SENDER} &7anulował &6zaproszenie &7do wymiany&7!";
    @Getter public String tradeFinishedTitle = "&8* &2&lTRADE &8*";
    @Getter public String tradeFinishedSubTitle = "&aPomyślnie przeprowadzono wymiane!";
    @Getter public String tradeCancelledTitle = "&8* &2&lTRADE &8*";
    @Getter public String tradeCancelledSubTitle = "&cAnulowano przeprowadzenie wymiany!";
}
