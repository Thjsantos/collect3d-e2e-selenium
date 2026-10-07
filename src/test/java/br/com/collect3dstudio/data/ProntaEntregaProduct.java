package br.com.collect3dstudio.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ProntaEntregaProduct {

    JINX_PISTOL("Pistola - Jinx - Arcane - League og Legends", "R$280,00"),
    SCORPION_MASK("Máscara Scorpion - Mortal Kombat 11", "R$180,00"),
    LINK_ZELDA("Link - The Legend of Zelda - Action figure", "R$550,00"),
    KRATOS_GOD_OF_WAR("Kratos 2018 - God of War - Action figure", "R$1.200,00");

    private final String productName;
    private final String price;

    /** All products as name -> price, in declaration order. */
    public static Map<String, String> asMap() {
        Map<String, String> map = new LinkedHashMap<>();
        Arrays.stream(values()).forEach(p -> map.put(p.productName, p.price));
        return map;
    }
}
