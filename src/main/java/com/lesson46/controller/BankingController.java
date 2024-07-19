package com.lesson46.controller;

import com.lesson46.dto.TransferCardToCardDTO;
import com.lesson46.model.Client;
import com.lesson46.service.BankingService;
import com.lesson46.util.AppError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@Tag(name = "Banking Service" , description = "Bank simulation")
@RestController
@RequestMapping("/banking")
public class BankingController {

    @Autowired
    BankingService bankingService;

    @Operation(summary = "Get client by the ID.",
            description = "Test description for method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find the client",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable @Parameter(description = "Description of id parameter") int id){
        Client client = null;
        try {
            client = bankingService.getUserById(id);
            return  new ResponseEntity<>(client, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Transfer amount between cards", description = "Transfers a specified amount from one card to another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class)))
    })
    @PostMapping(value = "/transfer",consumes = "application/json")
    public ResponseEntity<?> transfer(@RequestBody TransferCardToCardDTO dto){
        System.out.println("DTO " + dto);
        try {
            bankingService.transfer(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new AppError(401,e.getMessage()),HttpStatus.BAD_REQUEST);
        }catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
