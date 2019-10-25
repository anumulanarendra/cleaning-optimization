package spo.challenge.scheduling.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import spo.challenge.scheduling.application.exception.ValidationException
import spo.challenge.scheduling.application.service.OptimiseResourceService
import spo.challenge.scheduling.model.ApiResponse
import spo.challenge.scheduling.model.Building

@RestController
@RequestMapping("/api/optimiser")
class OptimiserController(private val service: OptimiseResourceService) {

    @Throws(ValidationException::class)
    @PostMapping
    fun optimiser(@RequestBody building: Building): ResponseEntity<ApiResponse> {
        return ResponseEntity(service.optimiseResource(building), HttpStatus.OK)
    }

}