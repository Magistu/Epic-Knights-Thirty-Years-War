"""Frozen Epic Knights: Addon 1.20.1 profiles; see docs/balance.md.
Source-pack stats in import-report.json are audit data only.
"""
ARMOR_DEFAULTS = {'baroque_apron_skirt': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true)',
 'baroque_armour': 'ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 315, 0 }, new Integer[] { 0, 0, 6, 0 }, 9, true)',
 'baroque_barred_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_barred_lobstertail_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                      'true)',
 'baroque_bodice': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_breeches': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true)',
 'baroque_buff_coat': 'ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 210, 0 }, new Integer[] { 0, 0, 4, 0 }, 9, true)',
 'baroque_buff_waistcoat': 'ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 210, 0 }, new Integer[] { 0, 0, 4, 0 }, 9, true)',
 'baroque_cabasset_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_cavalier_hat': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true)',
 'baroque_cavalry_armour': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_cavalry_armour_gauntlet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, '
                                    'true)',
 'baroque_cloak': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_closed_helmet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_coat': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_cuirass': 'ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 315, 0 }, new Integer[] { 0, 0, 6, 0 }, 9, true)',
 'baroque_cuirassier_armour': 'ArmorTypeConfig.of(1.40f, 0.8f, new Integer[] { 0, 0, 368, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_barred_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                        'true)',
 'baroque_dark_barred_lobstertail_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, '
                                           '9, true)',
 'baroque_dark_cabasset_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_dark_cavalry_armour': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_cavalry_armour_gauntlet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, '
                                         'true)',
 'baroque_dark_closed_helmet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_dark_cuirassier_armour': 'ArmorTypeConfig.of(1.40f, 0.8f, new Integer[] { 0, 0, 368, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_death_helmet': 'ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_dark_grilled_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                         'true)',
 'baroque_dark_lobstertail_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                    'true)',
 'baroque_dark_officer_armour': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_open_burgonet': 'ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_dark_open_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                      'true)',
 'baroque_dark_pikeman_armour': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_pikeman_cuirass': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_dark_pikeman_pot_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 2 }, 9, '
                                    'true)',
 'baroque_dark_savoyard_helmet': 'ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_death_helmet': 'ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_doublet': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_dutch_cap': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true)',
 'baroque_flat_cap': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true)',
 'baroque_grilled_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, '
                                    'true)',
 'baroque_lobstertail_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_monmouth_cap': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true)',
 'baroque_officer_armour': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_officer_doublet': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 140, 0 }, new Integer[] { 0, 0, 2, 0 }, 9, true)',
 'baroque_open_burgonet': 'ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_open_cloak': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_open_closed_burgonet': 'ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_panel_cloak': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_pikeman_armour': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_pikeman_cuirass': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true)',
 'baroque_pikeman_pot_helmet': 'ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_riders_breeches': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true)',
 'baroque_savoyard_helmet': 'ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true)',
 'baroque_shirt': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)',
 'baroque_skirt': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true)',
 'baroque_skull_cap': 'ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 0, 170 }, new Integer[] { 0, 0, 0, 2 }, 9, true)',
 'baroque_slouch_hat': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true)',
 'baroque_waistcoat': 'ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true)'}

WEAPON_DEFAULTS = {'baroque_steel_cuphilt_rapier': 'WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false)',
 'baroque_steel_halberd': 'WeaponType.of(7.15f, 0.90f, 1.00f, 0.05f, 4.1f, 3, true, 2, 2.00f, true, false, true)',
 'baroque_steel_partisan': 'WeaponType.of(3.35f, 1.17f, 1.90f, 0.00f, 2.5f, 0, true, 1, 5.00f, true, false, false)',
 'baroque_steel_pike': 'WeaponType.of(3.35f, 1.17f, 2.50f, 0.00f, 2.5f, 0, true, 2, 5.00f, true, false, false)',
 'baroque_steel_pollax': 'WeaponType.of(7.40f, 0.90f, 1.00f, 0.04f, 3.8f, 0, true, 2, 4.00f, true, false, true)',
 'baroque_steel_rapier': 'WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false)',
 'baroque_steel_sidesword': 'WeaponType.of(3.68f, 1.46f, 0.10f, 0.00f, 1.5f, 2, true, 0, 6.00f, true, false, false)',
 'baroque_steel_swepthilt_rapier': 'WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false)',
 'baroque_steel_walloon_sword': 'WeaponType.of(3.68f, 1.46f, 0.10f, 0.00f, 1.5f, 2, true, 0, 6.00f, true, false, false)'}
