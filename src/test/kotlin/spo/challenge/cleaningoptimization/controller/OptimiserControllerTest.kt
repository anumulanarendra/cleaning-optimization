package spo.challenge.cleaningoptimization.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spo.challenge.scheduling.CleaningOptimizationApplication
import spo.challenge.scheduling.application.dto.OptimiserResponse
import spo.challenge.scheduling.model.ApiResponse
import spo.challenge.scheduling.model.Building
import spo.challenge.scheduling.util.MIN_BUILDING_COUNT_ERROR_MSG

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [CleaningOptimizationApplication::class])
class OptimiserControllerTest {

    @Autowired
    internal lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    private lateinit var mockMvc: MockMvc


    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun testForWhenPayloadIsValidExpectSuccess() {
        val building = Building(listOf(24, 28), 11, 6)
        val result = mockMvc.perform(post("/api/optimiser").content(mapper.writeValueAsString(building)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn()
        val apiResponse = mapper.readValue(result.response.contentAsString, ApiResponse::class.java)
        Assertions.assertThat(apiResponse).isNotNull
        Assertions.assertThat(false).isEqualTo(apiResponse.error)
        Assertions.assertThat("Successfully optimised resources").isEqualTo(apiResponse.message)

        val results = apiResponse.results
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(2).isEqualTo(results!!.size)
        val expected = listOf(OptimiserResponse(2, 1), OptimiserResponse(2, 1))
        Assertions.assertThat(expected).isEqualTo(results)
    }

    @Test
    fun testForWhenPayloadIsNotValidExpect422Status() {
        val building = Building(listOf(), 11, 6)
        val result = mockMvc.perform(post("/api/optimiser").content(mapper.writeValueAsString(building)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity)
                .andReturn()

        val apiResponse = mapper.readValue(result.response.contentAsString, ApiResponse::class.java)
        Assertions.assertThat(apiResponse).isNotNull
        Assertions.assertThat(true).isEqualTo(apiResponse.error)
        Assertions.assertThat(MIN_BUILDING_COUNT_ERROR_MSG).isEqualTo(apiResponse.message)

        val results = apiResponse.results
        Assertions.assertThat(results).isNull()
    }

}