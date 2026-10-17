# Default balance

Profiles are copied from the supplied Epic-Knights-Addon-1.20.1-crossversion ArmorConfig and WeaponsConfig. Only the occupied armor slot is retained; dark finishes use the same profile. Source stats in import-report.json are historical, not runtime defaults.

| Armor ID | Reference profile |
|---|---|
| baroque_apron_skirt | whitePuffAndSlash |
| baroque_armour | gildedHalfArmor |
| baroque_barred_closed_burgonet | closedBurgonet |
| baroque_barred_lobstertail_helmet | lobsterTailedHelmet |
| baroque_bodice | doublet |
| baroque_breeches | whitePuffAndSlash |
| baroque_buff_coat | chainedGambeson |
| baroque_buff_waistcoat | chainedGambeson |
| baroque_cabasset_helmet | cabasset |
| baroque_cavalier_hat | fancyHat |
| baroque_cavalry_armour | greenwich |
| baroque_cavalry_armour_gauntlet | greenwich |
| baroque_cloak | tunic |
| baroque_closed_helmet | closeHelmet |
| baroque_coat | doublet |
| baroque_cuirass | gildedHalfArmor |
| baroque_cuirassier_armour | heavyCuirassier |
| baroque_dark_barred_closed_burgonet | closedBurgonet |
| baroque_dark_barred_lobstertail_helmet | lobsterTailedHelmet |
| baroque_dark_cabasset_helmet | cabasset |
| baroque_dark_cavalry_armour | greenwich |
| baroque_dark_cavalry_armour_gauntlet | greenwich |
| baroque_dark_closed_helmet | closeHelmet |
| baroque_dark_cuirassier_armour | heavyCuirassier |
| baroque_dark_death_helmet | savoyardHelmet |
| baroque_dark_grilled_closed_burgonet | closedBurgonet |
| baroque_dark_lobstertail_helmet | lobsterTailedHelmet |
| baroque_dark_officer_armour | greenwich |
| baroque_dark_open_burgonet | lightBurgonet |
| baroque_dark_open_closed_burgonet | closedBurgonet |
| baroque_dark_pikeman_armour | pikeman |
| baroque_dark_pikeman_cuirass | pikeman |
| baroque_dark_pikeman_pot_helmet | morion |
| baroque_dark_savoyard_helmet | savoyardHelmet |
| baroque_death_helmet | savoyardHelmet |
| baroque_doublet | doublet |
| baroque_dutch_cap | fancyHat |
| baroque_flat_cap | fancyHat |
| baroque_grilled_closed_burgonet | closedBurgonet |
| baroque_lobstertail_helmet | lobsterTailedHelmet |
| baroque_monmouth_cap | fancyHat |
| baroque_officer_armour | greenwich |
| baroque_officer_doublet | whitePuffAndSlash |
| baroque_open_burgonet | lightBurgonet |
| baroque_open_cloak | tunic |
| baroque_open_closed_burgonet | closedBurgonet |
| baroque_panel_cloak | tunic |
| baroque_pikeman_armour | pikeman |
| baroque_pikeman_cuirass | pikeman |
| baroque_pikeman_pot_helmet | morion |
| baroque_riders_breeches | whitePuffAndSlash |
| baroque_savoyard_helmet | savoyardHelmet |
| baroque_shirt | tunic |
| baroque_skirt | whitePuffAndSlash |
| baroque_skull_cap | cervelliere |
| baroque_slouch_hat | fancyHat |
| baroque_waistcoat | doublet |

| Weapon | Reference profile |
|---|---|
| rapier | rapier |
| cuphilt_rapier | rapier |
| swepthilt_rapier | rapier |
| sidesword | sidesword |
| walloon_sword | sidesword |
| partisan | partisan |
| pike | partisan |
| halberd | frenchHalberd |
| pollax | italianPoleaxe |

Pike uses partisan damage/speed with 2.5 bonus reach and mandatory two-handed use. Weapon damage is the Epic Knights base damage parameter; steel tier and player attributes contribute to final attack damage. Firearms have no reference-addon equivalent, so their separate configurable defaults are retained.

Existing saved config values are preserved. To adopt the new defaults, back up and remove `config/thirtyyearswar/armors.json5` and `weapons.json5`, then restart.
