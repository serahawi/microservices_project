package com.demo.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold Card information"
)
@Data
public class CardsDto {

    @NotEmpty(message = "Mobile Number must be 11 digits")
    @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
    @Schema(
            description = "Mobile number for customer account",
            example = "01552223737"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number must be 12 digits")
    @Pattern(regexp="(^$|[0-9]{12})",message = "Mobile Number must be 12 digits")
    @Schema(
            description = "Card number for customer account",
            example = "545357182781"
    )
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be null or empty")
    @Schema(
            description = "Card type for customer account",
            example = "DEBIT"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total limit for customer card"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Amount used should be greater than or equal to zero")
    @Schema(
            description = "Amount used for customer card"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Available amount should be greater than or equal to zero")
    @Schema(
            description = "Available amount for customer card"
    )
    private int availableAmount;
}
