package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public abstract class IInfos {

    public static String MAIN_HOST_NAME = ChatColor.GOLD + "Configuration";
    public static String ENCHANT_HOST = ChatColor.GOLD + "Forge des dieux";

    public static ItemStack ENCHANT_ITEM = new ItemBuilder(Material.ANVIL).setName(ChatColor.GOLD + "Forge des dieux").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
    public static ItemStack ENCHANT_RENAME = new ItemBuilder(Material.NAME_TAG).setName("Renommer").toItemStack();

    public static ItemStack ENCHANT_UNBREAKABLE_TRUE = new ItemBuilder(Material.DIAMOND_AXE).setName("Incassable " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
    public static ItemStack ENCHANT_UNBREAKABLE_FALSE = new ItemBuilder(Material.DIAMOND_AXE).setName("Incassable " + ChatColor.RED + " ✘").toItemStack();

    public static ItemStack ORANGE_GLASS_BORDER = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").toItemStack();
    public static ItemStack BLACK_GLASS_BORDER = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").toItemStack();

    public static ItemStack RETURN_ITEM = new ItemBuilder(Material.REDSTONE).setName(ChatColor.GOLD + "Retour").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack();
    public static ItemStack BARS = new ItemBuilder(Material.IRON_BARS).setName(" ").toItemStack();

    // IHOST CONFIGS
    public static ItemStack HOST_CREATE = new ItemBuilder(Material.LAPIS_LAZULI).setName(ChatColor.YELLOW + "Créer une configuration").toItemStack();
    public static ItemStack HOST_SAVE = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Sauvegarder la configuration").toItemStack();

    public static ItemStack HOST_ICONS = new ItemBuilder(Material.PAINTING).setName(ChatColor.GOLD + "Icônes").toItemStack();
    public static ItemStack HOST_DELETE = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Supprimer").toItemStack();
    public static ItemStack HOST_RENAME = new ItemBuilder(Material.NAME_TAG).setName(ChatColor.GREEN + "Renommer").toItemStack();

    // IHOST CONFIG
    public static ItemStack HOST_WHITELIST = new ItemBuilder(Material.WRITABLE_BOOK).toItemStack();
    public static ItemStack HOST_SPECTATORS = new ItemBuilder(Material.GHAST_TEAR).toItemStack();
    public static ItemStack HOST_SPECTATORS_AFTER_BORDER = new ItemBuilder(Material.BARRIER).toItemStack();

    // IHOSTITEM
    public static ItemStack MAIN_HOST_ITEM = new ItemBuilder(Material.COMPARATOR).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().setName(MAIN_HOST_NAME).toItemStack();

    // IHOST
    public static ItemStack MAIN_HOST_CONFIGS = new ItemBuilder(Material.ENDER_CHEST).setName(ChatColor.GREEN + "Configurations").toItemStack();
    public static ItemStack MAIN_HOST_SCENARIOS = new ItemBuilder(Material.GOLDEN_APPLE, 1, (byte) 1).setName(ChatColor.GREEN + "Scénarios").toItemStack();
    public static ItemStack MAIN_HOST_INVENTORIES = new ItemBuilder(Material.CHEST).setName(ChatColor.GREEN + "Inventaires").toItemStack();
    public static ItemStack MAIN_HOST_WORLD = new ItemBuilder(Material.MAP).setName(ChatColor.GREEN + "Gestion des mondes").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack();
    public static ItemStack MAIN_HOST_PLUGIN = new ItemBuilder(Material.WRITABLE_BOOK).setName(ChatColor.GREEN + "Gestion des plugins").toItemStack();
    public static ItemStack MAIN_HOST_TIMERS = new ItemBuilder(Material.BEACON).setName(ChatColor.GREEN + "Timers").toItemStack();
    public static ItemStack MAIN_HOST_HOST = new ItemBuilder(Material.ANVIL).setName(ChatColor.GREEN + "Configuration du host").toItemStack();
    public static ItemStack MAIN_HOST_START = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.DARK_GREEN + "Lancer").toItemStack();
    public static ItemStack MAIN_HOST_STOP = new ItemBuilder(Material.REDSTONE).setName(ChatColor.DARK_RED + "Arrêter").toItemStack();

    // IHost - Inventories
    public static ItemStack INVENTORIES_HOST_BEGINNING = new ItemBuilder(Material.CHEST).setName(ChatColor.GREEN + "Inventaire de départ").toItemStack();
    public static ItemStack INVENTORIES_HOST_DEATH = new ItemBuilder(Material.CHEST).setName(ChatColor.DARK_GRAY + "Inventaire de mort").toItemStack();
    public static ItemStack INVENTORIES_EDIT = new ItemBuilder(Material.SLIME_BALL).setName(ChatColor.GREEN + "Editer").toItemStack();

    // IHOST - Timer
    public static ItemStack HOST_TIMER_ROLES = new ItemStack(Material.WITHER_SKELETON_SKULL);
    public static ItemStack HOST_TIMER_TELEPORT = new ItemStack(Material.DIAMOND_SWORD);

    public static void setInventoryContent(Inventory inventory, ItemStack[] items) {
        for (int slots = 0; slots < 36; slots++)
            if (items[slots] != null)
                inventory.setItem(slots + 9, items[slots]);
        inventory.setItem(45, items[36]);
        inventory.setItem(46, items[37]);
        inventory.setItem(47, items[38]);
        inventory.setItem(48, items[39]);
    }

    public static void placeInvBorders(Inventory inventory) {
        int[] orangeGlass = new int[]{0, 2, 6, 8, 10, 16, 18, 26, 27, 35, 37, 43, 45, 47, 51, 53};
        int[] blackGlass = new int[]{1, 7, 9, 17, 36, 44, 46, 52};

        for (Integer glass : orangeGlass)
            inventory.setItem(glass, ORANGE_GLASS_BORDER);

        for (Integer glass : blackGlass)
            inventory.setItem(glass, BLACK_GLASS_BORDER);
    }

}
