package com.icap.test.prime_generator.controller;

import com.icap.test.prime_generator.generator.PrimeGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by saflinhussain.
 */
public class PrimeGeneratorControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private List<PrimeGenerator> generators = new LinkedList<>();

    @Before
    public void setUp(){

        final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver();
        final StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerBeanDefinition("erroHandler",
                new RootBeanDefinition(ErrorHandler.class, null, null));
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.setApplicationContext(applicationContext);
        exceptionResolver.afterPropertiesSet();


        mockMvc = standaloneSetup(new PrimeGeneratorController(generators))
                .setHandlerExceptionResolvers(exceptionResolver)
                .build();
    }


    @Test
    public void itShouldReturnEmptyListOfAlogrithms() throws Exception{
        mockMvc.perform(get("/generator/algorithms"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data",empty()))
                .andExpect(jsonPath("$.result").value("Success"));

    }

    @Test
    public void itShouldReturnListOfSupportedAlogrithms() throws Exception{
        PrimeGenerator mock1 = Mockito.mock(PrimeGenerator.class);
        when(mock1.getGeneratorName()).thenReturn("TrialByDivision");
        PrimeGenerator mock2 = Mockito.mock(PrimeGenerator.class);
        when(mock2.getGeneratorName()).thenReturn("EratosthenesSieve");

       generators.add(mock1);
       generators.add(mock2);

        mockMvc.perform(get("/generator/algorithms"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data").value(contains("TrialByDivision","EratosthenesSieve")))
                .andExpect(jsonPath("$.data",hasSize(2)))
                .andExpect(jsonPath("$.result").value("Success"));

    }

    @Test
    public void itShouldInvokeMatchingPrimeGenerator() throws Exception{
        PrimeGenerator mockPrimeGenerator = Mockito.mock(PrimeGenerator.class);
        when(mockPrimeGenerator.getGeneratorName()).thenReturn("ERATOSTHENES_SIEVE_PARALLEL");
        when(mockPrimeGenerator.generatePrimeNumber(eq(10))).thenReturn(Arrays.asList(2,3,5,7));
        generators.add(mockPrimeGenerator);
        mockMvc.perform(get("/generator/prime?algorithm=ERATOSTHENES_SIEVE_PARALLEL&ceiling=10"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data",hasSize(4)))
                .andExpect(jsonPath("$.data").value(contains(2,3,5,7)))
                .andExpect(jsonPath("$.result").value("Success"));
        verify(mockPrimeGenerator).getGeneratorName();
        verify(mockPrimeGenerator).generatePrimeNumber(eq(10));
    }

    @Test
    public void itShouldValidateMaxCeilingValue() throws Exception {
        mockMvc.perform(get("/generator/prime?algorithm=ERATOSTHENES_SIEVE_PARALLEL&ceiling=2147483648"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data").value("ceiling:2147483648"))
                .andExpect(jsonPath("$.error").value("Ceiling crossed max value limit:2147483647"))
                .andExpect(jsonPath("$.message").value("Ceiling should be less than 2147483647"));
    }


    @Test
    public void itShouldHandleInvalidCeilingValue() throws Exception {
        mockMvc.perform(get("/generator/prime?algorithm=ERATOSTHENES_SIEVE_PARALLEL&ceiling=sf3d32g"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data").value("ceiling:sf3d32g"))
                .andExpect(jsonPath("$.error").value("For input string: \"sf3d32g\""))
                .andExpect(jsonPath("$.message").value("Ceiling should be numeric and non decimal value"));
    }

    @Test
    public void itShouldHandleInvalidAlgorithm() throws Exception {
        mockMvc.perform(get("/generator/prime?algorithm=Invalid&ceiling=123"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().contentType(contentType))
                .andExpect(jsonPath("$.data").value("algoithm: Invalid"))
                .andExpect(jsonPath("$.error").value("Invalid algorithm"))
                .andExpect(jsonPath("$.message").value("Unable to find prime generator matching algorithm: Invalid"));
    }

}