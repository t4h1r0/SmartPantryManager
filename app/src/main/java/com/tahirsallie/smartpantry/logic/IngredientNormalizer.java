package com.tahirsallie.smartpantry.logic;

/** Cleans up ingredient names and units so "Tomatoes" matches "tomato" and 1 kg matches 1000 g. */
public final class IngredientNormalizer {

    private IngredientNormalizer() {}

    /** "Tomatoes " -> "tomato", "Berries" -> "berry", "Potatoes" -> "potato". */
    public static String normalizeName(String raw) {
        if (raw == null) return "";
        String s = raw.trim().toLowerCase();

        if (s.endsWith("ies") && s.length() > 4) {          // berries -> berry
            return s.substring(0, s.length() - 3) + "y";
        }
        if (s.endsWith("oes") && s.length() > 4) {          // tomatoes -> tomato
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("ss") || s.endsWith("us")) {         // couscous, hummus stay as they are
            return s;
        }
        if (s.endsWith("s") && s.length() > 3) {            // eggs -> egg
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    /** Converts a quantity to its family's base unit: grams, millilitres, or a plain count. */
    public static double toBase(double qty, String unit) {
        String u = unit == null ? "" : unit.trim().toLowerCase();
        switch (u) {
            case "kg":   return qty * 1000;
            case "g":    return qty;
            case "l":    return qty * 1000;
            case "ml":   return qty;
            case "cup":
            case "cups": return qty * 240;
            case "tbsp": return qty * 15;
            case "tsp":  return qty * 5;
            default:     return qty;                         // "pcs" and anything else
        }
    }

    /** "mass", "volume" or "count", so grams are never compared with millilitres. */
    public static String family(String unit) {
        String u = unit == null ? "" : unit.trim().toLowerCase();
        switch (u) {
            case "kg":
            case "g":    return "mass";
            case "l":
            case "ml":
            case "cup":
            case "cups":
            case "tbsp":
            case "tsp":  return "volume";
            default:     return "count";
        }
    }

    /** Key used to group stock and requirements, e.g. "tomato|count". */
    public static String key(String name, String unit) {
        return normalizeName(name) + "|" + family(unit);
    }
}
