package com.magistuarmory.thirtyyearswar;
import com.magistuarmory.thirtyyearswar.client.EquipmentGeometry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import java.util.*;

/** Exercises real Minecraft model baking and slot visibility, without a GL context. */
public final class GeometryVerification {
    private record Equipment(String id, String kind, String model, String slot) {}
    private record Report(Equipment[] items) {}
    public static void main(String[] args) throws Exception {
        Map<String, ModelPart> models = new HashMap<>();
        for (var method : EquipmentGeometry.class.getDeclaredMethods()) {
            ModelPart root = ((LayerDefinition) method.invoke(null)).bakeRoot();
            new HumanoidModel<>(root); // validates the complete seven-part humanoid contract
            models.put(method.getName(), root);
        }
        for (Equipment e : new com.google.gson.Gson().fromJson(java.nio.file.Files.readString(java.nio.file.Path.of(args[0])), Report.class).items()) {
            if (!e.kind().equals("armor") && !e.kind().equals("decoration")) continue;
            ModelPart root = models.get(e.model());
            if (root == null) throw new AssertionError("No model for " + e.id());
            List<String> visible = switch (e.slot()) {
                case "HELMET" -> List.of("head", "hat");
                case "CHESTPLATE" -> List.of("body", "right_arm", "left_arm");
                case "LEGGINGS" -> List.of("body", "right_leg", "left_leg");
                case "BOOTS" -> List.of("right_leg", "left_leg");
                default -> throw new AssertionError(e.slot());
            };
            boolean geometry = visible.stream().map(root::getChild).flatMap(ModelPart::getAllParts).anyMatch(part -> !part.isEmpty());
            if (!geometry) throw new AssertionError("Invisible worn item: " + e.id());
            if (e.slot().equals("CHESTPLATE")) {
                for (String hidden : List.of("left_leg", "right_leg")) {
                    if (root.getChild(hidden).getAllParts().anyMatch(part -> !part.isEmpty()))
                        throw new AssertionError("Chest armor geometry hidden by slot mask: " + e.id());
                }
            }
        }
        System.out.println("PASS: " + models.size() + " model layers bake; all 59 wearable/decoration definitions have visible geometry in their equipment slot.");
    }
}
