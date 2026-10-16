
package com.deliveryapp.emailms.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderNotificationDTO(
        String to,
        String customerName,
        String establishmentName,
        String orderCode,
        List<ItemDTO> items,
        BigDecimal total
) {

    public record ItemDTO(
            String name,
            int quantity,
            BigDecimal subtotal
    ) {
    }
}
