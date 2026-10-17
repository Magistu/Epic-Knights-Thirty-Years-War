package com.magistuarmory.thirtyyearswar;
import com.magistuarmory.thirtyyearswar.client.EquipmentModels;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
public final class ClientBootstrap {
    public static void init() { new EquipmentModels().init(AddonItems.INSTANCE); }
}
