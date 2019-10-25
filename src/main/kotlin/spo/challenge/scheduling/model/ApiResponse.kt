package spo.challenge.scheduling.model

import com.fasterxml.jackson.annotation.JsonInclude
import spo.challenge.scheduling.application.dto.OptimiserResponse

data class ApiResponse(val error: Boolean, val message: String, @JsonInclude(JsonInclude.Include.NON_NULL) val results: List<OptimiserResponse>? = null)