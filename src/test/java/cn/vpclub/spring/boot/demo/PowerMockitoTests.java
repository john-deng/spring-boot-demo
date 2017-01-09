package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.AClassWithPrivateConstructor;
import cn.vpclub.spring.boot.demo.service.AClassWithPrivateMethod;
import cn.vpclub.spring.boot.demo.service.AFinalClass;
import cn.vpclub.spring.boot.demo.service.AStaticClass;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.testng.Assert.assertEquals;

/**
 *
 * Created by johnd on 23/12/2016.
 */
@PrepareForTest({
        AFinalClass.class,
        AStaticClass.class,
        AClassWithPrivateMethod.class,
        AClassWithPrivateConstructor.class
})
@SpringBootTest
@Slf4j
public class PowerMockitoTests {
    private AFinalClass aFinalClass = null;

    private AClassWithPrivateConstructor aClassWithPrivateConstructor;

    private AClassWithPrivateMethod aClassWithPrivateMethod;

    @BeforeMethod
    public void setUp() {
        // aClassWithPrivateMethod = new AClassWithPrivateConstructor(); // wrong, constructor is private.
        aClassWithPrivateConstructor = PowerMockito.mock(AClassWithPrivateConstructor.class); // with PowerMockito, you can do it.
        aClassWithPrivateMethod = PowerMockito.spy(new AClassWithPrivateMethod()); // create Partial Mock
    }


    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Test
    public void testClassWithPrivateConstructor() throws Exception {
        String testInput = "A test input";
        String mockedResult = "echo from a class with private constructor: " + testInput;

        // mock invoke
        PowerMockito.when(aClassWithPrivateConstructor.echoString(testInput)).thenReturn(mockedResult);

        // verify
        assertEquals(aClassWithPrivateConstructor.echoString(testInput), mockedResult);
    }

    @Test
    public void testClassWithPrivateMethod() throws Exception {
        final String methodToTest = "crunchNumbers";
        final String expected = "100%";

        // create a partial mock that can mock out one method */
        PowerMockito.doReturn(true).when(aClassWithPrivateMethod, methodToTest);

        final long startTime = System.currentTimeMillis();
        String result = aClassWithPrivateMethod.calculateStats();
        final long duration = System.currentTimeMillis() - startTime;

        verifyPrivate(aClassWithPrivateMethod).invoke(methodToTest);

        assertEquals(expected, result);
        log.info("Time to run test: " + duration + "mS");
    }


    @Test(dependsOnMethods = {"testStaticClass2"})
    public void testFinalClass1() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a final class: " + testInput;

        aFinalClass = PowerMockito.mock(AFinalClass.class);
        PowerMockito.when(aFinalClass.echoString(testInput)).thenReturn(mockedResult);

        // Assert the mocked result is returned from method call
        assertEquals(aFinalClass.echoString(testInput), mockedResult);

        log.info("finished testFinalClass1()");
    }

    @Test(dependsOnMethods = {"testFinalClass1"})
    public void testFinalClass2() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a final class: " + testInput;

        // Assert the mocked result is returned from method call
        assertEquals(aFinalClass.echoString(testInput), mockedResult);
    }

    @Test
    public void testStaticClass1() {
        PowerMockito.mockStatic(AStaticClass.class);

        final String testInput = "A test input";
        final String mockedResult = "echo from a static class: " + testInput;
        Mockito.when(AStaticClass.echoString(testInput)).thenReturn(mockedResult);

        // Assert the mocked result is returned from method call
        assertEquals(AStaticClass.echoString(testInput), mockedResult);

        PowerMockito.verifyStatic();
        AStaticClass.echoString(testInput);
    }

    @Test(dependsOnMethods = {"testStaticClass1"})
    public void testStaticClass2() {
        final String testInput = "A test input";
        final String mockedResult = "echo from a static class: " + testInput;

        // Assert the mocked result is returned from method call
        assertEquals(AStaticClass.echoString(testInput), mockedResult);
    }
}
