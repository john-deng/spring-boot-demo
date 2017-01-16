package cn.vpclub.spring.boot.demo;

import cn.vpclub.spring.boot.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import static org.powermock.api.mockito.PowerMockito.*;
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
        aClassWithPrivateConstructor = mock(AClassWithPrivateConstructor.class); // with PowerMockito, you can do it.
        aClassWithPrivateMethod = spy(new AClassWithPrivateMethod()); // create Partial Mock
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Test
    public void testGenericType() {
        String expected = "mock Generic Type";
        @SuppressWarnings("unchecked") // Safe for a mock
        Foo<Bar> fooBar = (Foo<Bar>)mock(Foo.class);
        when(fooBar.get()).thenReturn(new Bar(expected));
        log.info(fooBar.get().getBar());
        Assert.assertEquals(expected, fooBar.get().getBar());
    }

    @Test
    public void testClassWithPrivateConstructor() throws Exception {
        String testInput = "A test input";
        String mockedResult = "echo from a class with private constructor: " + testInput;

        // mock invoke
        when(aClassWithPrivateConstructor.echoString(testInput)).thenReturn(mockedResult);

        // verify
        assertEquals(aClassWithPrivateConstructor.echoString(testInput), mockedResult);
    }

    @Test()
    public void testClassWithPrivateMethod() throws Exception {
        final String methodToTest = "crunchNumbers";
        final String expected = "100%";

        // create a partial mock that can mock out one method */
        doReturn(true).when(aClassWithPrivateMethod, methodToTest);

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

        aFinalClass = mock(AFinalClass.class);
        when(aFinalClass.echoString(testInput)).thenReturn(mockedResult);

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
        mockStatic(AStaticClass.class);

        final String testInput = "A test input";
        final String mockedResult = "echo from a static class: " + testInput;
        when(AStaticClass.echoString(testInput)).thenReturn(mockedResult);

        // Assert the mocked result is returned from method call
        assertEquals(AStaticClass.echoString(testInput), mockedResult);

        verifyStatic();
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
