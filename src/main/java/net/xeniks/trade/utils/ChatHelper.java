package net.xeniks.trade.utils;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ChatHelper {
    public static List<String> fixColors(List<String> contents) {
        return contents.stream()
                .map(ChatHelper::fixColors)
                .collect(Collectors.toList());
    }
    private static final String[] TO_REPLACE = { ">>", "<<" };
    private static final String[] REPLACED = { "\u00BB", "\u00AB" };

    public static String fixColors(String content) {
        return ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(content, TO_REPLACE, REPLACED));
    }

}