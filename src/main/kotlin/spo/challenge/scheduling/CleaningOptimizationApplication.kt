package spo.challenge.scheduling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import spo.challenge.scheduling.application.service.ResourceOptimiser
import spo.challenge.scheduling.application.service.ResourceOptimiserFactory
import spo.challenge.scheduling.application.service.ResourceOptimiserType

@SpringBootApplication
class CleaningOptimizationApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<CleaningOptimizationApplication>(*args)
        }
    }

    @Bean
    fun linearOptimiser(): ResourceOptimiser {
        return ResourceOptimiserFactory.makeOptimiser(ResourceOptimiserType.LINEAR)
    }
}