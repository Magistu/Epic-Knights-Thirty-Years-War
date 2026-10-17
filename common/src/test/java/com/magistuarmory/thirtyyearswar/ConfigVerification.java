package com.magistuarmory.thirtyyearswar;

import com.google.gson.Gson;
import com.magistuarmory.thirtyyearswar.config.*;

/** Regression checks for partial/old config files and non-default values. */
public final class ConfigVerification {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        ArmorConfig armor = gson.fromJson("{\"armor\":{\"baroque_cloak\":{\"chestplateDefense\":7,\"chestplateDurability\":123,\"enabled\":false}}}", ArmorConfig.class);
        if (armor.get("baroque_cloak").chestplateDefense != 7 || armor.get("baroque_cloak").enabled)
            throw new AssertionError("Armor override not retained");
        if (!armor.get("baroque_closed_helmet").enabled) throw new AssertionError("Missing armor default not restored");
        ArmorConfig defaults = new ArmorConfig();
        var helmet = defaults.get("baroque_closed_helmet");
        if (helmet.helmetDefense != 3 || helmet.helmetDurability != 275 || helmet.toughness != 1.25F)
            throw new AssertionError("Close helmet differs from Epic Knights Addon profile");
        var doublet = defaults.get("baroque_doublet");
        if (doublet.chestplateDefense != 1 || doublet.chestplateDurability != 120 || doublet.toughness != 0)
            throw new AssertionError("Cloth defaults are overpowered");
        if (!gson.toJson(defaults.get("baroque_cuirassier_armour")).equals(gson.toJson(defaults.get("baroque_dark_cuirassier_armour"))))
            throw new AssertionError("Dark finish has different protection");
        WeaponsConfig weapons = gson.fromJson("{\"meleeWeapons\":{}}", WeaponsConfig.class);
        if (gson.toJsonTree(weapons.get("baroque_steel_pike")).getAsJsonObject().get("twoHanded").getAsInt() != 2) throw new AssertionError("Missing weapon default not restored");
        DecorationsConfig dyes = gson.fromJson("{\"decorations\":{\"baroque_cloak\":{\"defaultColor\":16711680,\"enabled\":false}}}", DecorationsConfig.class);
        if (dyes.get("baroque_cloak").defaultColor != 0xFF0000 || dyes.get("baroque_cloak").enabled)
            throw new AssertionError("Decoration override not retained");
        if (dyes.get("baroque_shoulder_sash").defaultColor != 0xFFFFFF) throw new AssertionError("Nonwhite dye default");
        GunsConfig guns = gson.fromJson("{\"guns\":{\"baroque_matchlock_musket\":{\"damage\":9,\"range\":30,\"reloadTicks\":60,\"durability\":100,\"spread\":0.01,\"cooldownTicks\":15,\"enabled\":false}}}", GunsConfig.class);
        guns.validatePostLoad();
        var gun = guns.get("baroque_matchlock_musket");
        if (gun.damage != 9 || gun.range != 30 || gun.reloadTicks != 60 || gun.enabled) throw new AssertionError("Gun overrides not retained");
        if (guns.get("baroque_wheellock_pistol").reloadTicks != 20) throw new AssertionError("Pistol default not restored");
        gun.range = Double.NaN;
        boolean rejected = false;
        try { guns.validatePostLoad(); } catch (me.shedaniel.autoconfig.ConfigData.ValidationException expected) { rejected = true; }
        if (!rejected) throw new AssertionError("Invalid gun configuration accepted");
        System.out.println("PASS: equipment config overrides, disabled flags, partial-file fallbacks and invalid gun settings.");
    }
}
