package net.xeniks.trade;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import lombok.Getter;
import net.xeniks.trade.command.TradeCommand;
import net.xeniks.trade.configuration.TradeConfiguration;
import net.xeniks.trade.configuration.YAMLConfiguration;
import net.xeniks.trade.configuration.litecommands.InvalidUsage;
import net.xeniks.trade.configuration.litecommands.PermissionMessage;
import net.xeniks.trade.trade.TradeFactory;
import net.xeniks.trade.trade.TradeRequestFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;
    @Getter public static Main instance;
    @Getter public static TradeRequestFactory tradeRequestFactory;
    @Getter public static TradeFactory tradeFactory;
    @Getter public static TradeConfiguration tradeConfiguration;
    @Getter public static YAMLConfiguration configuration;

    @Override
    public void onEnable() {
        instance = this;
        configuration = ConfigManager.create(YAMLConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        tradeConfiguration = new TradeConfiguration();
        tradeRequestFactory = new TradeRequestFactory();
        tradeFactory = new TradeFactory();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }
    private void registerCommands(){
        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "phoenixTrade")
                .argument(Player.class, new BukkitPlayerArgument(this.getServer(), Main.getConfiguration().getPlayerNotFoundMessage()))
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual(Main.getConfiguration().getOnlyPlayerMessage()))
                .command(TradeCommand.class)
                .invalidUsageHandler(new InvalidUsage())
                .permissionHandler(new PermissionMessage())
                .register();
    }
    private void registerListeners(){

    }

}
