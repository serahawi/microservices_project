package com.demo.cards.Controller;

import com.demo.cards.Constants.CardsConstants;
import com.demo.cards.Service.iCardsService;
import com.demo.cards.dto.CardsDto;
import com.demo.cards.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@Tag(
        name = "CRUD REST APIs for Cards",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@Validated
public class CardsController {

    private iCardsService cardsService;

    @Operation(description = "Create new card for customer")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCards(@RequestParam
                                                       @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
                                                       String mobileNumber) {
        cardsService.createCards(mobileNumber);
        return ResponseEntity.ok()
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }

    @Operation(description = "Fetch card for customer by mobile number")
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCards(@RequestParam
                                                   @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
                                                   String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCards(mobileNumber);
        return ResponseEntity.ok()
                .body(cardsDto);
    }

    @Operation(description = "update card for customer")
    @GetMapping("/update")
    public ResponseEntity<ResponseDto> updateCards(@Valid @RequestBody CardsDto cardsDto) {
        if (cardsService.updateCards(cardsDto)){
            return ResponseEntity.ok()
                    .body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(description = "delete card for customer by mobile number")
    @GetMapping("/delete")
    public ResponseEntity<ResponseDto> DeleteCards(@RequestParam
                                                   @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
                                                   String mobileNumber) {
        if (cardsService.deleteCards(mobileNumber)){
            return ResponseEntity.ok()
                    .body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
