package com.demo.cards.Controller;

import com.demo.cards.Constants.CardsConstants;
import com.demo.cards.Service.iCardsService;
import com.demo.cards.dto.CardsDto;
import com.demo.cards.dto.ErrorResponseDto;
import com.demo.cards.dto.ResponseDto;
import com.demo.cards.dto.cardsContactInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD REST APIs for Cards",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@AllArgsConstructor
@Validated
public class CardsController {

    private iCardsService cardsService;

    @Autowired
    private cardsContactInfoDto cardsContactInfoDto;



    @Operation(summary = "Create new card for customer", description = "REST API to Create new card for customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCards(@RequestParam
                                                       @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
                                                       String mobileNumber) {
        cardsService.createCards(mobileNumber);
        return ResponseEntity.ok()
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }



    @Operation(summary = "Fetch card for customer by mobile number", description = "REST API Fetch card for customer by mobile number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCards(@RequestParam
                                                   @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
                                                   String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCards(mobileNumber);
        return ResponseEntity.ok()
                .body(cardsDto);
    }




    @Operation(summary = "update card for customer", description = "REST API to update card for customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCards(@Valid @RequestBody CardsDto cardsDto) {
        if (cardsService.updateCards(cardsDto)){
            return ResponseEntity.ok()
                    .body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
        }
    }



    @Operation(summary = "delete card for customer by mobile number", description = "REST API to delete card for customer by mobile number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
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


    @Operation(summary = "Get Contact Info", description = "REST API to Get Contact Info")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<cardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .ok()
                .body(cardsContactInfoDto);
    }

}