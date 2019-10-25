package spo.challenge.scheduling.application.service

import spo.challenge.scheduling.application.dto.OptimiserResponse
import spo.challenge.scheduling.model.Building

interface ResourceOptimiser {

    fun optimiseResource(building: Building): List<OptimiserResponse>

}