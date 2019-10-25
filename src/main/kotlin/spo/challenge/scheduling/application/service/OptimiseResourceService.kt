package spo.challenge.scheduling.application.service

import org.springframework.stereotype.Service
import spo.challenge.scheduling.application.exception.ValidationException
import spo.challenge.scheduling.model.ApiResponse
import spo.challenge.scheduling.model.Building
import spo.challenge.scheduling.validation.BuildingValidator

@Service
class OptimiseResourceService(private val linearOptimiser: ResourceOptimiser) {


    @Throws(ValidationException::class)
    fun optimiseResource(building: Building): ApiResponse {

        val validationResults = BuildingValidator(building).validate()
        if (validationResults.wasSuccessful()) {
            return ApiResponse(false, "Successfully optimised resources", linearOptimiser.optimiseResource(building))
        }
        throw ValidationException(validationResults.toString())
    }

}